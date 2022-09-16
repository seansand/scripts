//TimesFive.groovy (Cthulhu)

String str = '''
ARAB GUARDS 
These henchmen were trained by Katif and will lay down their lives for Edward Chandler. 
GUARD 1 
STR 14 CON 15 SIZ 14 INT 11 POW 10
DEX 15 APP 9 EDU 5 SAN 0 HP 15
Damage Bonus: +1D4. 
Weapons: Tommygun 55%, damage 1D10+2 
Fighting Knife 65%, damage 1D4+2+1D4 
GUARD 2 
STR 16 CON 12 SIZ 15 INT 10 POW 8
DEX 12 APP 7 EDU 6 SAN 0 HP 14
Damage Bonus: +1D4. 
Weapons: Tommygun 45%, damage 1D10+2 
Fighting Knife 75%, damage 1D4+2+1D4 
GUARD 3 
STR 11 CON 13 SIZ 10 INT 12 POW 11
DEX 14 APP 8 EDU 6 SAN 0 HP 12
Damage Bonus: +0. 
INT 12 
SAN 0 
Weapons: Tommygun 50%, damage 1D10+2 
.45 Revolver 85%, damage 1D10+2 
Fighting Knife 45%, damage 1D4+2 
GUARD 4 
STR 16 CON 15 SIZ 14 INT 10 POW 12
DEX 13 APP 7 EDU 5 SAN 0 HP 15 
Damage Bonus: +1D4. 
Weapons: Tommygun 65%, damage 1D10+2 
Fighting Knife 65%, damage 1D4+2+1D4 

EDWARD CHANDLER, age 49, Child of Destiny, disguised as Arab 
STR 14 CON 14 SIZ 13 INT 20 POW 150 
DEX 16 APP 20 EDU 22 SAN 0 HP 14 
Armor: 9-point bulletproof vest. Worn at all times by Chandler 
in public. Only covers Chandler's torso, and so has a 50% 
chance of stopping any damage. 
Skills: Accounting 90%, Anthropology 65%, Archaeology 85%, Art (Song) 75%, A tronomy 60%, Bargain 95%, Chemistry 
55%, Credit Rating 95%, CthuJhu Mythos 99%, Fast Talk 85%, Geology 50%, History 95%, Law 65%, Library Use 95%, 
Natural History 50%, Occult 75%. Persuade 95%. Pilot Aircraft 75%, Psychology 95%. 
Languages: Ancient Egyptian 85%. Arabic 75%. Chinese 80%, English 95%. French 85%, German 75%. Greek 75%. Latin 
80%. Romanian 75%, Spanish 75%. 
Spells: Call/Dismiss the Beast. 

BARON HAUPTMANN, age 800+, Ancient Sorcerer 
STR 15 CON 15 SIZ 14 INT 18 POW 32 
DEX 13 APP 16 EDU 35 SAN 0 HP 15 
Damage Bonus: +1D4. 
Weapons: .38 Automatic 85%. damage 1D10
Rapier 95%. damage 1D6+1+1D4 
Rifle 60%, none carried 
Skills: Anthropology 75%, Archaeology 85%. Astronomy 95%, Chemistry 80%, Climb 25%. Cthulhu Mythos 99%. Orive 
Automobile 55%, Fast Talk 80%. First Aid 95%, Geology 85%, Hide 85%, Hi tory 95%. Jump 20%. Knife 65%, Law 75%. 
Library Use 95%, Linguist 95%, Listen 75%, Medicine 70%, Natural History 65%, Occult 95%, Persuade 50%, Pharmacy 
90%, Psychology 85%, Ride 90%, Sneak 35% (due to bad leg), Spot Hidden 80%. 
Languages: Nearly all at 90%+. 
Spells: Brew Space-Mead. Call/Dismiss the Beast, Create Gate, Dread Curse of Azathoth. Elder Sign. Enchant Item, Powder of Ibn-Ghazi. Resurrection. Shrivelling. Summon/Bind Star Vampire, Voorish Sign. 

LANG FU, age 2000+, Ancient Sorcerer 
STR 8 CON 12 SIZ 7 INT 20 POW 40 
DEX 15 APP 15 EDU 32 SAN 0 HP 10
Damage Bonus: -1D4 
Armor: Coat of Life reduces all kinetic damage to minimum and prevents impale.
Skills: Anthropology 75%. Archaeology 65%, Astronomy 70%, Chemistry 50%. Cthulhu Mythos 99%. Fast Talk 85%, First Aid 95%. Geology 35%. Hide 75%, History 50%. Library Use 95%, Listen 85%. Medicine 55%. Natural History 60%. Occult 70%, Persuade 85%. Pharmacy 65%. Psychology 75%, Sneak 85%. 
Languages: Nearly all at 90%+. 
Spells: All spells in Call of Cthulhu rulebook, plus Call/Dismiss the Beast. Enchant Incense and Spirit Summoning Ability. 

THE BEAST (costs 1D6/1D20 to see)

STR 200 CON 100 SIZ 500 INT 1 POW 50 
DEX 20  Move 12 HP 300 
Weapons: Paw x2 50%. damage 12D6 
Armor: 20-point stone-like hide. 

'''

/* ----------------------------------------------------------- */


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
   
      line = line.replace(m.group(0), "$attr " + i + "  ")
      
      //println line 
      //println ();
     
   }

   return line
}






