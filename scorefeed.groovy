import groovy.json.JsonSlurper
import groovy.json.JsonOutput

final String htmlOutputPath = /C:\Temp\scorefeed.html/

// --------------------------------------------------------------------------
// Helper Function to Fetch JSON via System cURL
// --------------------------------------------------------------------------
String fetchJson(String urlString) {
    def command = [
        "curl", "-s", "-L", "--compressed",
        "-A", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
        "-H", "Accept: application/json, text/plain, */*",
        "-H", "Accept-Language: en-US,en;q=0.9",
        urlString
    ]
    
    def process = command.execute()
    def response = process.text.trim()
    
    if (process.exitValue() != 0 || !response) {
        throw new RuntimeException("cURL process failed or returned empty output.")
    }
    
    if (response.startsWith("<")) {
        throw new RuntimeException("API returned HTML instead of JSON. Server output snippet:\n" + response.take(300))
    }
    
    return response
}

// --------------------------------------------------------------------------
// Input Argument & Data Source Selection
// --------------------------------------------------------------------------
String inputArg = args.length > 0 ? args[0].trim() : ""
String firstChar = inputArg ? inputArg.substring(0, 1).toUpperCase() : ""

def data = null
def jsonSlurper = new JsonSlurper()

Map buildGameView(def event) {
    def competition = event.competitions[0]
    def competitors = competition.competitors
    def statusObj = competition.status
    def statusType = statusObj.type.name
    def statusDetail = statusObj.type.shortDetail ?: statusObj.type.description
    def gameClock = statusType == 'STATUS_IN_PROGRESS'
        ? "Q${statusObj.period ?: 1} ${statusObj.displayClock ?: '00:00'}"
        : statusDetail
    def homeTeam = competitors.find { it.homeAway == 'home' }
    def awayTeam = competitors.find { it.homeAway == 'away' }
    def homeName = homeTeam?.team?.displayName ?: 'Home'
    def awayName = awayTeam?.team?.displayName ?: 'Away'
    def homeScore = homeTeam?.score ?: '0'
    def awayScore = awayTeam?.score ?: '0'

    def situation = competition.situation
    def possessionId = situation?.possession?.toString()
    def homeHasPossession = possessionId != null && homeTeam?.team?.id?.toString() == possessionId
    def awayHasPossession = possessionId != null && awayTeam?.team?.id?.toString() == possessionId
    def possessionTeamName = homeHasPossession ? homeName : awayHasPossession ? awayName : ''
    def homeWinDecimal = situation?.lastPlay?.probability?.homeWinPercentage
    if (homeWinDecimal == null && situation?.lastPlay?.probability?.awayWinPercentage != null) {
        homeWinDecimal = 1.0 - situation.lastPlay.probability.awayWinPercentage
    }
    if (homeWinDecimal == null) homeWinDecimal = situation?.homeWinPercentage
    if (homeWinDecimal == null && competition.predictor?.homeTeam?.gameProjection != null) {
        homeWinDecimal = competition.predictor.homeTeam.gameProjection.toDouble() / 100.0
    }

    def winProbability = ''
    if (homeWinDecimal != null) {
        double homeProbability = homeWinDecimal.toDouble()
        double awayProbability = 1.0 - homeProbability
        def favoredName = homeProbability >= awayProbability ? homeName : awayName
        winProbability = "${favoredName} ${(Math.max(homeProbability, awayProbability) * 100).round(1)}%"
    }

    def latestPlay = situation?.lastPlay?.text
    if (!latestPlay && competition.drives?.current?.plays) {
        latestPlay = competition.drives.current.plays.last()?.text
    }
    def downDistance = situation?.downDistanceText ?: ''

    [
        awayName: awayName, awayScore: awayScore.toString(), awayLogo: awayTeam?.team?.logo ?: '',
        homeName: homeName, homeScore: homeScore.toString(), homeLogo: homeTeam?.team?.logo ?: '',
        awayHasPossession: awayHasPossession, homeHasPossession: homeHasPossession,
        possessionTeamName: possessionTeamName,
        status: gameClock ?: '', downDistance: downDistance, latestPlay: latestPlay ?: '', winProbability: winProbability
    ]
}

while (true) {
switch (firstChar) {
    case 'T':
        def jsonFile = new File(/C:\Temp\espn-nfl-feed1.json/)
        println "Mode: Test File ${jsonFile.toString()}"

        if (!jsonFile.exists()) {
            println "Error: File '${jsonFile.toString()}' not found."
            return
        }
        data = jsonSlurper.parse(jsonFile)
        break

    case 'B':
    case 'C':
        println "Mode: College Football (Live ESPN API)"
        String cfbUrl = "https://site.web.api.espn.com/apis/site/v2/sports/football/college-football/scoreboard?groups=80"
        data = jsonSlurper.parseText(fetchJson(cfbUrl))
        break

    default:
        println "Mode: NFL (Default Live ESPN API)"
        String nflUrl = "https://site.web.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard"
        data = jsonSlurper.parseText(fetchJson(nflUrl))
        break
}

// --------------------------------------------------------------------------
// Filtering & Iteration
// --------------------------------------------------------------------------
println "=== Football Game Scores & Status (${new Date().format('yyyy-MM-dd HH:mm:ss')}) ==="

// List of Big Ten school location names and nicknames
def bigTenKeywords = [
    "illinois fighting illini", "indiana hoosiers", "iowa hawkeyes", 
    "maryland terrapins", "michigan wolverines", "michigan state spartans",
    "minnesota golden gophers", "nebraska cornhuskers", "northwestern wildcats", 
    "ohio state buckeyes", "penn state nittany lions", "purdue boilermakers", 
    "rutgers scarlet knights", "wisconsin badgers", "oregon ducks", 
    "ucla bruins", "usc trojans", "washington huskies"
]

def gamesToDisplay = data.events.findAll { event ->
    def competition = event.competitions[0]
    def state = competition.status?.type?.state

    state == 'in' || (state == 'post' && competition.recent == true)
}

if (firstChar == 'B') {
    gamesToDisplay = gamesToDisplay.findAll { event ->
        def competitors = event.competitions[0].competitors
        
        return competitors.any { competitor ->
            def team = competitor.team
            def name = (team?.displayName ?: "").toLowerCase()
            def location = (team?.location ?: "").toLowerCase()
            def nickname = (team?.name ?: "").toLowerCase()

            boolean nameMatch = bigTenKeywords.any { kw -> 
                name.contains(kw) || location == kw || nickname == kw 
            }

            boolean groupMatch = false
            if (team?.groups) {
                if (team.groups instanceof List) {
                    groupMatch = team.groups.any { g -> g?.id?.toString() == "8" }
                } else if (team.groups?.id) {
                    groupMatch = team.groups.id.toString() == "8"
                }
            }

            return nameMatch || groupMatch
        }
    }
}

def gameViews = gamesToDisplay.collect { event -> buildGameView(event) }
writeHtmlPage(gameViews, htmlOutputPath)
println "HTML scores written to ${htmlOutputPath} (${gameViews.size()} games)"

// --------------------------------------------------------------------------
// Output Display
// --------------------------------------------------------------------------
gamesToDisplay.each { event ->
    def competition = event.competitions[0]
    def competitors = competition.competitors
    def gameName = event.name
    def statusObj = competition.status
    def statusType = statusObj.type.name
    def statusDetail = statusObj.type.shortDetail ?: statusObj.type.description
    
    def gameClock = ""
    if (statusType == 'STATUS_IN_PROGRESS') {
        def period = statusObj.period ?: 1
        def displayClock = statusObj.displayClock ?: '00:00'
        gameClock = "Q${period} ${displayClock}"
    } else {
        gameClock = statusDetail
    }

    def homeTeam = competitors.find { it.homeAway == 'home' }
    def awayTeam = competitors.find { it.homeAway == 'away' }
    
    def homeName = homeTeam?.team?.displayName ?: 'Home'
    def homeScore = homeTeam?.score ?: '0'
    
    def awayName = awayTeam?.team?.displayName ?: 'Away'
    def awayScore = awayTeam?.score ?: '0'

    // Extract & Determine Win Probability for Favorited Team
    def situation = competition.situation
    def homeWinDecimal = null

    if (situation?.lastPlay?.probability?.homeWinPercentage != null) {
        homeWinDecimal = situation.lastPlay.probability.homeWinPercentage
    } else if (situation?.lastPlay?.probability?.awayWinPercentage != null) {
        homeWinDecimal = 1.0 - situation.lastPlay.probability.awayWinPercentage
    } else if (situation?.homeWinPercentage != null) {
        homeWinDecimal = situation.homeWinPercentage
    } else if (competition.predictor?.homeTeam?.gameProjection != null) {
        homeWinDecimal = competition.predictor.homeTeam.gameProjection.toDouble() / 100.0
    }

    String winProbDisplay = "N/A"
    if (homeWinDecimal != null) {
        double homeProb = homeWinDecimal.toDouble()
        double awayProb = 1.0 - homeProb
        
        if (homeProb >= awayProb) {
            winProbDisplay = "${(homeProb * 100).round(1)}% (${homeName})"
        } else {
            winProbDisplay = "${(awayProb * 100).round(1)}% (${awayName})"
        }
    }

    def latestPlayText = "N/A"
    if (situation?.lastPlay?.text) {
        latestPlayText = situation.lastPlay.text
    } else if (competition.drives?.current?.plays) {
        def lastPlay = competition.drives.current.plays.last()
        latestPlayText = lastPlay?.text ?: "N/A"
    }

    // Print Game Block using the Helper Method
    println "----------------------------------------"
    println "${gameName}"
    
    printField("Status:", gameClock)
    def possessionTeam = competitors.find { it.team?.id?.toString() == situation?.possession?.toString() }
    printField("Possession:", possessionTeam?.team?.displayName)
    printField("Down & Distance:", situation?.downDistanceText)
    printField("Score:", "${awayName} ${awayScore} - ${homeName} ${homeScore}")
    printField("Win Probability:", winProbDisplay)
    printField("Latest Play:", latestPlayText)
}

Thread.sleep(60000)
}

void printField(String label, Object value) {
    def text = value?.toString()?.trim()
    if (text && text != "N/A") {
        println "${label.padRight(19)}${text}"
    }
}

void writeHtmlPage(List games, String outputPath) {
        int rotationSeconds = games.size() <= 4 ? 60 : games.size() <= 8 ? 30 : games.size() <= 12 ? 20 : games.size() <= 16 ? 15 : games.size() <= 20 ? 12 : 10
        def pageData = JsonOutput.toJson(games.take(24))
        def html = '''<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Football Scores</title>
<style>
:root { --page-background:#101820; --pane-background:#17232d; --pane-border:#344756; --primary-text:#f4f7f8; --secondary-text:#b8c5cc; --score-size:5.2rem; --detail-size:2.25rem; --pane-padding:3.5rem; }
* { box-sizing:border-box; } html,body { width:100%; height:100%; margin:0; }
body { background:var(--page-background); color:var(--primary-text); font-family:Arial,sans-serif; overflow:hidden; }
#scoreboard { display:grid; grid-template-columns:1fr 1fr; grid-template-rows:1fr 1fr; gap:1rem; width:100vw; height:100vh; padding:1rem; }
.pane { display:flex; flex-direction:column; justify-content:center; min-width:0; min-height:0; padding:var(--pane-padding); background:var(--pane-background); border:2px solid var(--pane-border); }
.team-score { font-family:Arial Narrow,sans-serif; margin:0; font-size:var(--score-size); line-height:1.1; font-weight:700; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; }
.possession-marker { display:inline-block; font-size:.62em; vertical-align:middle; }
.detail { margin:.8rem 0 0; color:var(--secondary-text); font-size:var(--detail-size); line-height:1.25; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; }
.status-row { display:flex; align-items:baseline; gap:2rem; margin:.8rem 0 0; color:var(--secondary-text); min-width:0; }
.status-row .status, .status-row .down-distance { margin:0; font-size:3.7rem; line-height:1.25; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; }
.latest-play { white-space:normal; overflow:visible; overflow-wrap:anywhere; text-overflow:clip; }
.empty { visibility:hidden; } .team-logo { display:none; max-height:4rem; max-width:4rem; }
</style></head><body><main id="scoreboard"></main><script>
const games = __PAGE_DATA__;
const rotationSeconds = __ROTATION_SECONDS__;
let page = 0;

// Customize this function to change the contents or order of every pane.
function renderPane(game) {
    if (!game) return '<section class="pane empty" aria-hidden="true"></section>';
    return `<section class="pane">
        <img class="team-logo" src="${game.awayLogo}" alt="">
        <p class="team-score">${game.awayName} ${game.awayScore}${game.awayHasPossession ? ' <span class="possession-marker">' + String.fromCodePoint(0x1F3C8) + '</span>' : ''}</p>
        <img class="team-logo" src="${game.homeLogo}" alt="">
        <p class="team-score">${game.homeName} ${game.homeScore}${game.homeHasPossession ? ' <span class="possession-marker">' + String.fromCodePoint(0x1F3C8) + '</span>' : ''}</p>
        <div class="status-row"><p class="status">${game.status}</p><p class="down-distance">${game.downDistance}</p></div><p class="detail latest-play">${game.latestPlay}</p><p class="detail">${game.winProbability}</p>
    </section>`;
}
function renderPage() { const start = page * 4; document.getElementById('scoreboard').innerHTML = games.slice(start, start + 4).map(renderPane).join(''); }
renderPage();
if (games.length > 4) setInterval(() => { page = (page + 1) % Math.ceil(games.length / 4); renderPage(); }, rotationSeconds * 1000);
setTimeout(() => location.reload(), 60000);
</script></body></html>'''
        html = html.replace('__PAGE_DATA__', pageData).replace('__ROTATION_SECONDS__', rotationSeconds.toString())
        new File(outputPath).text = html
}