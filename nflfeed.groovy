import groovy.json.JsonSlurper

def jsonFile = new File(/C:\Temp\espn-nfl-feed1.json/)

if (!jsonFile.exists()) {
    println "Error: File ${jsonFile.toString()} not found."
    return
}

def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(jsonFile)

println "=== NFL Football Game Scores & Status ==="

data.events.each { event ->
    def gameName = event.name
    def competition = event.competitions[0]
    def statusObj = competition.status
    def statusType = statusObj.type.name // e.g., STATUS_SCHEDULED, STATUS_IN_PROGRESS, STATUS_FINAL
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