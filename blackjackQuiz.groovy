//Blackjack.groovy

import java.util.Random

String data = '''
9     H  D  D  D  D  H  H  H  H  H
10    D  D  D  D  D  D  D  D  H  H
11    D  D  D  D  D  D  D  D  D  D
12    H  H  St St St H  H  H  H  H
13    St St St St St H  H  H  H  H
14    St St St St St H  H  H  H  H
15    St St St St St H  H  H  H  H
16    St St St St St H  H  H  H  H
A,A   Sp Sp Sp Sp Sp Sp Sp Sp Sp Sp
10,10 St St St St St St St St St St
9,9   Sp Sp Sp Sp Sp Sp Sp Sp St St
8,8   Sp Sp Sp Sp Sp Sp Sp Sp Sp Sp
7,7   Sp Sp Sp Sp Sp Sp H  H  H  H
6,6   Sp Sp Sp Sp Sp H  H  H  H  H
5,5   D  D  D  D  D  D  D  D  H  H
4,4   H  H  H  H  H  H  H  H  H  H  
3,3   H  H  Sp Sp Sp Sp H  H  H  H 
2,2   H  H  Sp Sp Sp Sp H  H  H  H 
A,2   H  H  D  D  D  H  H  H  H  H
A,3   H  H  D  D  D  H  H  H  H  H
A,4   H  H  D  D  D  H  H  H  H  H
A,5   H  H  D  D  D  H  H  H  H  H
A,6   H  H  D  D  D  H  H  H  H  H
A,7   St St D  D  D  St St H  H  H
A,8   St St St St St St St St St St
A,9   St St St St St St St St St St
'''

def random = new Random()
Map<String, List> handMap = new LinkedHashMap()
def tokens = data.replaceAll(/\r?\n/, " ").split(" ")
tokens = tokens.findAll{ it?.trim() }

List<String> upcard = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "A"]

for (int i = 0; i < 286; i += 11)
{
    List currList = new ArrayList()
    handMap.put(tokens[i], currList)
    (1..10).each { currList << tokens[i + it] }
}

List<String> rows = new ArrayList<>(handMap.keySet())
def scanner = new java.util.Scanner(System.in)

for (int i = 1; i <= 10; ++i)
{
    println()
    println()
    println("HAND $i")
    println()
    int row = random.nextInt(handMap.size())
    int col = random.nextInt(upcard.size())

    String hand = rows.get(row)
    String dealerCard = upcard.get(col)
    println("DEALER HAS: [$dealerCard]")
    println()
    println("YOU HAVE: [$hand]")
    println()
    String play = handMap.get(hand).get(col).toLowerCase()
    print("What is the proper play? ")
    def guess = scanner.nextLine()
    
    guess = guess.toLowerCase()

    println()
    if (guess.startsWith(play))
    {
        println("${fullPlay(play)} IS CORRECT")
    }
    else
    {
        println("INCORRECT, proper play is: ${fullPlay(play)}")
    }
    Thread.sleep(1000)
}

String fullPlay(play)
{
    switch(play)
    {
        case "h": return "HIT"
        case "sp": return "SPLIT"
        case "st": return "STAND"
        case "d": return "DOUBLE-DOWN"
    }
}





