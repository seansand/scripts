//tennisschedule.groovy

class Player
{
    Player(String name)
    {
        this.name = name
    }

    Player(String name, String... unavailable)
    {
        this.name = name
        this.unavailable = unavailable
    }

    Player(String name, ArrayList<String> unavailable)
    {
        this.name = name
        this.unavailable = unavailable
    }

    String name
    List<String> unavailable = []
    int totalMatches = 0

    public String toString()
    {
        //return name padded to 12 characters
        return name.padRight(12)
    }
}

class Week
{
    Week(String name)
    {
        this.name = name
    }

    String name
    List<Player> slots = new ArrayList<String>(4)

    public String toString()
    {
        return name + " : " + slots
    }
}

class Schedule
{
    Schedule(List<Week> weeks)
    {
        this.weeks = weeks
    }

    List<Week> weeks

    public String toString()
    {
        String result = ""
        weeks.each { result += it.toString() + "\r\n" }
        return result
    }
}

// Last update 3-05

//e.g.
//players << new Player("S. Sandquist", "3-28")  // unavail 3-28
//players << new Player("Carolan", weeks - "2-22" - "2-29" - "3-14"])  // only available those three days

// Settings:  (Consecutive week setting == "allowed", "discouraged", "disallowed")

consecutiveWeeks = "allowed"
List<String> weeks = ["09-18", "09-25", "10-02", "10-09", "10-16", "10-23", "10-30"]
Integer minimumMatches = 2

List<Player> players = new ArrayList<Player>()

players << new Player("Berry", "10-30")
players << new Player("Burch")  
players << new Player("Carolan", weeks - "09-25" - "10-02")  // only these
players << new Player("Helps", "09-18")  
players << new Player("Hendrie")  
//players << new Player("Medved", "3-20")  
players << new Player("Ramesh", weeks - "09-18" - "09-25" - "10-02")   
players << new Player("S. Sandquist", "10-16") 
players << new Player("C. Sandquist", "09-18", "10-02", "10-16", "10-23")
players << new Player("Stephenson")  

println("Consecutive weeks: " + consecutiveWeeks);
Schedule schedule = null;
while (schedule == null)
{
    schedule = makeSchedule(players, weeks)
    if (players.any { it.totalMatches < minimumMatches })
    {
        print("<$minimumMatches... ")
        schedule = null
    }
}
adjustAndDisplaySchedule(schedule, players)  

void adjustAndDisplaySchedule(schedule, players)
{
    println()

    // If consecutive weeks are allowed, count and display them there.
    
    //Do rearranges to eliminate any duplicate ball bringers
    while (true)
    {
        List<String> ballBringers = schedule.weeks.collect { it.slots[0] }
        if (ballBringers.size() == ballBringers.unique().size())
        {
             break
        }
        else
        {
            schedule.weeks.each { Collections.shuffle(it.slots) }
        }
    }

    println()
    println schedule
    players.each
    {
        Player player = it

        String graph = ""
        //For each week display a '|' if the player plays, a '' if they don't
        schedule.weeks.each
        {
            if (it.slots.contains(player))
                graph += "|"
            else
                graph += " "
        }

        println player.name.padRight(12) + " " + graph + " (" + player.totalMatches + ") unav. = " + player.unavailable
    }
    println()
}

Schedule makeSchedule(List<Player> players, List<String> weekStrings)
{
    print(".")  // progress indicator

    int retryCount = 10;
    while (retryCount > 0)
    {
        try
        {
            players*.totalMatches = 0  // reinitialize
       
            List<Week> weeksList = new ArrayList<Week>()
            for (String weekString : weekStrings)
            {
                weeksList << new Week(weekString)
            }

            int slotCount = weeksList.size() * 4
            int openSlots = slotCount

            List<Player> playerQueue = []
            (100 * weeksList.size()).times
            {
                Collections.shuffle(players)
                players.each
                {
                    playerQueue << it
                }
            }

            while (openSlots > 0)
            {
                int randomWeek = -1
                while (randomWeek == -1)
                {
                    randomWeek = Math.abs(new Random().nextInt()) % weeksList.size()
                    if (weeksList[randomWeek].slots.size() == 4)
                    {
                        randomWeek = -1
                    }
                }

                // Insert player at top of queue into first available slot (if they are available)
                // And are not already scheduled for that week.
                // If they are unavailable, they miss their chance at a match.
                Player player = playerQueue.remove(0)

                if (weeksList[randomWeek].slots.contains(player))
                {
                    continue
                }

                if ((consecutiveWeeks == "discouraged" || consecutiveWeeks == "disallowed") &&
                    randomWeek != 0 && weeksList[randomWeek-1].slots.contains(player))
                {
                    continue
                }
                
                if (consecutiveWeeks == "disallowed" && 
                    randomWeek != weeksList.size() - 1 && weeksList[randomWeek+1].slots.contains(player))  // disallow consecutive weeks
                {
                    continue
                }
                
                if (!player.unavailable.contains(weeksList[randomWeek].name))
                {
                    weeksList[randomWeek].slots << player
                    player.totalMatches++
                    --openSlots
                }
            }

            return new Schedule(weeksList)
        }
        catch(Exception e)
        {
            retryCount--
        }
    }
}