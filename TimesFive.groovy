//TimesFive.groovy (Cthulhu)

String str = '''

NIGHT WATCHMAN, age 32, Inattentive Guard 
STR 13 CON 11 DEX 10 APP 8 SIZ 12 
INT 8 POW 10 EDU 10 SAN 50 HP 12
Weapon: Night-stick 52%, damage 1D6+1D4 
Skills: Sleep on Duty 50%, Brawl 48%, Listen 40%, Spot Hidden 30%

CLARENCE RODGERS, age 27, Master of disguise
STR 13 CON 13 SIZ 14 DEX 14 APP 13 
INT 15 POW 14 EDU 16 SAN 0 HP 14
Weapon: .32 Revolver 65%, damage 1D8 
Skills: Anthropology 50%, Archaeology 55%, Astronomy 10%, Cthulhu Mythos 25%, Disguise 90%, Fast Talk 70%, Hide 65%, History 70%, Library Use 60%, Listen 50%, Occult 50%, Psychology 48%, Quick Change 75%, Sneak 60%, Spot Hidden 60%

RUKUKIK (EDITH BRYANT), age of body 34, 
STR 10 CON 14 SIZ 12 DEX 16 APP 11 
INT 20 POW 14 SAN 0 HP 13
Weapon: .32 Revolver 45%, damage 1D8
Skills: History 99%, Knowledge of Paul LeMond's Mind 40%, Disguise 50%, Listen 60%, Hide 60%, Use Tabula Rasa Device 80%. Languages: English 50%, Pnakotic 90%.

BUGSY WEXLER, age 32, Da Boss 
STR 15 CON 15 DEX 13 APP 8 SIZ 15
INT 13 POW 10 EDU 6 SAN 40 HP 15
Damage Bonus: +1D4. 
Weapons: .45 Automatic 85%, damage 1D10+2 
Fist/Brawl 85%, damage 1D3+1D4 Head Butt 55%, damage 1D4+1D4 Skills: Bargain 50%, Drive Automobile 50%, Hide 75%, Persuade 50%, Pick Pocket 50%, Sneak 75%.

BUGSY'S TYPICAL THUGS 
STR 15 CON 13 SIZ 16 INT 10 POW 8
DEX 13 APP 6 EDU 5 SAN 40 HP 15
Damage Bonus: +1D4.
Weapons: .38 Automatic 80%, damage 1D10 
Fist/Brawl 85%, damage 1D3+1D4
Skills: Drive Automobile 40%, Hide 35%, Move Quietly 30%, Pick Pocket 25%.

TYPICAL SANITARIUM GUARD 
STR 15 CON 14 SIZ 13 INT 9 POW 11
DEX 13 APP 9 EDU 10 SAN 55 HP 14
Damage Bonus: +1D4. Weapons: Night-stick 50%, damage 1D6+1D4 Brawl 65%, 
Skills: Listen 45%, Spot Hidden 55%. 

GUARD DOG 
STR 10 CON 12 SIZ 7 POW 7 DEX 13 HP 10 
Weapons: Bite 40%, damage 1D6 
Skills: Listen 75%, Scent 90%.

'''

str.eachLine() {
   line ->
   line = transformLine(line);
   println line
}


String transformLine(String line) {

   line = transform(line, "STR")
   line = transform(line, "CON")
   line = transform(line, "SIZ")
   line = transform(line, "POW")
   line = transform(line, "DEX")
   line = transform(line, "APP")               
   line = transform(line, "EDU")    
   line = transform(line, "INT")    
   return line
}

String transform(String line, String attr) {

   java.util.regex.Pattern p =
      java.util.regex.Pattern.compile("${attr}\\s([0-9]+)");

   java.util.regex.Matcher m = p.matcher(line)
   
   if (m.find()) {

      //println m.group(0);  //STR 12
      //println m.group(1);  //12
   
      Integer i = 5 * new Integer(m.group(1));
 
      //println line
      //println "$attr - Replaced with"      
   
      line = line.replace(m.group(0), "$attr " + i)
      
      //println line 
      //println ();
     
   }

   return line
}






