import groovy.json.JsonSlurper

// --------------------------------------------------------------------------
// Input Argument & Data Source Selection
// --------------------------------------------------------------------------
String inputArg = args.length > 0 ? args[0].trim() : ""
String firstChar = inputArg ? inputArg.substring(0, 1).toUpperCase() : ""

def data = null
def jsonSlurper = new JsonSlurper()
def testFile = new File(/C:\Temp\espn-nfl-feed1.json/)

switch (firstChar) {
    case 'T':
        println "Mode: Test File ${testFile.toString()}"
        if (!testFile.exists()) {
            println "Error: Test File ${testFile.toString()} not found."
            return
        }
        data = jsonSlurper.parse(testFile)
        break

    case 'B':
        println "Mode: Big Ten College Football (Live ESPN API)"
        // group=7 explicitly requests all Big Ten conference games
        String b1gUrl = "https://site.web.api.espn.com/apis/site/v2/sports/football/college-football/scoreboard?groups=80"
        data = jsonSlurper.parseText(fetchJson(b1gUrl))
        break

    case 'C':
        println "Mode: All College Football (Live ESPN API)"
        // groups=80 fetches top games, or remove parameter entirely for all FBS
        String cfbUrl = "https://site.web.api.espn.com/apis/site/v2/sports/football/college-football/scoreboard"
        data = jsonSlurper.parseText(fetchJson(cfbUrl))
        break

    default:
        println "Mode: NFL (Live ESPN API)"
        String nflUrl = "https://site.web.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard"
        data = jsonSlurper.parseText(fetchJson(nflUrl))
        break
}

// --------------------------------------------------------------------------
// Filtering & Iteration
// --------------------------------------------------------------------------
println "=== Football Game Scores & Status ==="

// List of Big Ten school location names and nicknames
def bigTenKeywords = [
    "illinois fighting illini", "indiana hoosiers", "iowa hawkeyes", 
    "maryland terrapins", "michigan wolverines", "michigan state spartans",
    "minnesota golden gophers", "nebraska cornhuskers", "northwestern wildcats", 
    "ohio state buckeyes", "penn state nittany lions", "purdue boilermakers", 
    "rutgers scarlet knights", "wisconsin badgers", "oregon ducks", 
    "ucla bruins", "usc trojans", "washington huskies"
]

def gamesToDisplay = data.events

if (firstChar == 'B') {
    gamesToDisplay = data.events.findAll { event ->
        def competitors = event.competitions[0].competitors
        
        return competitors.any { competitor ->
            def team = competitor.team
            def name = (team?.displayName ?: "").toLowerCase()
            def location = (team?.location ?: "").toLowerCase()
            def nickname = (team?.name ?: "").toLowerCase()

            // Check if any Big Ten keyword matches name, location, or nickname
            boolean nameMatch = bigTenKeywords.any { kw -> 
                name.contains(kw) || location == kw || nickname == kw 
            }

            // Check groups array if present in JSON payload
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

    def situation = competition.situation
    def homeWinProb = "N/A"
    
    if (situation?.homeWinPercentage != null) {
        homeWinProb = "${(situation.homeWinPercentage * 100).round(1)}%"
    } else if (competition.predictor?.homeTeam?.gameProjection != null) {
        homeWinProb = "${competition.predictor.homeTeam.gameProjection.toDouble().round(1)}%"
    }

    def latestPlayText = "N/A"
    if (situation?.lastPlay?.text) {
        latestPlayText = situation.lastPlay.text
    } else if (competition.drives?.current?.plays) {
        def lastPlay = competition.drives.current.plays.last()
        latestPlayText = lastPlay?.text ?: "N/A"
    }

    println "----------------------------------------"
    println "${gameName}"
    println "Status:            ${gameClock}"
    println "Score:             ${awayName} ${awayScore} - ${homeName} ${homeScore}"
    println "Home Win Prob:     ${homeWinProb}"
    println "Latest Play:       ${latestPlayText}"
}

// END OF MAIN

// --------------------------------------------------------------------------
// Helper Function to Fetch JSON via System cURL
// --------------------------------------------------------------------------
String fetchJson(String urlString) {
    def command = [
        "curl", "-s", "-L",
        "-A", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
        "-H", "Accept: application/json, text/plain, */*",
        "-H", "Accept-Language: en-US,en;q=0.9",
        "-H", "Sec-Fetch-Dest: empty",
        "-H", "Sec-Fetch-Mode: cors",
        "-H", "Sec-Fetch-Site: same-site",
        urlString
    ]
    
    def process = command.execute()
    def response = process.text.trim()
    
    if (process.exitValue() != 0 || !response) {
        throw new RuntimeException("cURL process failed or returned empty output.")
    }
    
    // Check if the response received is HTML instead of JSON
    if (response.startsWith("<")) {
        throw new RuntimeException("API returned HTML instead of JSON. Server output snippet:\n" + response.take(300))
    }
    
    return response
}