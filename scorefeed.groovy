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
    case 'C':
        println "Mode: College Football (Live ESPN API)"
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
// Game Iteration and Output Formatting
// --------------------------------------------------------------------------
println "=== Football Game Scores & Status ==="

data.events.each { event ->
    def gameName = event.name
    def competition = event.competitions[0]
    def statusObj = competition.status
    def statusType = statusObj.type.name
    def statusDetail = statusObj.type.shortDetail ?: statusObj.type.description
    
    // Formatting status display (Quarter and Clock)
    def gameClock = ""
    if (statusType == 'STATUS_IN_PROGRESS') {
        def period = statusObj.period ?: 1
        def displayClock = statusObj.displayClock ?: '00:00'
        gameClock = "Q${period} ${displayClock}"
    } else {
        gameClock = statusDetail
    }

    // Identify Home and Away Teams
    def homeTeam = competition.competitors.find { it.homeAway == 'home' }
    def awayTeam = competition.competitors.find { it.homeAway == 'away' }
    
    def homeName = homeTeam?.team?.displayName ?: 'Home'
    def homeScore = homeTeam?.score ?: '0'
    
    def awayName = awayTeam?.team?.displayName ?: 'Away'
    def awayScore = awayTeam?.score ?: '0'

    // Extract Home Win Percentage
    def situation = competition.situation
    def homeWinProb = "N/A"
    
    if (situation?.homeWinPercentage != null) {
        homeWinProb = "${(situation.homeWinPercentage * 100).round(1)}%"
    } else if (competition.predictor?.homeTeam?.gameProjection != null) {
        homeWinProb = "${competition.predictor.homeTeam.gameProjection.toDouble().round(1)}%"
    }

    // Extract Latest Play
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