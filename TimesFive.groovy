//TimesFive.groovy (Cthulhu)

String str = '''

THE THING FROM BETWEEN THE PLANES, Horrible Creature (1D8 SAN to see)

This being will appear as a dripping yellowish mass about two feet in diameter. It moves slowly across flat surfaces with a squishing, sucking sound, dripping a sticky fluid that leaves a brown stain on whatever it touches. It sees by mean of a single, large, red eyeball hidden in the center of the mass.
STR 8  CON 16  SIZ 4  INT 2  POW 5
DEX 12  Move 4/12 gliding  HP 1O
Weapons: Jump and Grasp 75%, damage 1D3*
* This damage is caused on the first round and for every round that the creature remains attached. When the creature attacks successfully, it attaches itself to the victim s face by tiny, grasping cilia which secrete a burning acid. Additionally, the creature will form tendrils that will force their way into the victim's nose, ears, and throat. On the third consecutive round of a successful attack, these tendrils have reached the brain and begin to destroy 1 point of INT per round. At the Keeper's option, the investigator could also begin to lose 1 point of APP per round due to permanent facial scarring from the acid.
Armor: All kinetic weapons do minimum damage due to the
soft form of the creature.
Sanity Loss: 0/1D8 points for seeing the Thing. Additionally, a victim of a successful attack must make a SAN roll for 0/1D3 points every round that the being is attached to his face; a failed roll also means that the victim will be unable to take any rational action for the round. A person seeing a human being under attack by the thing must make a SAN roll for another 1D2/1D4 points.


DR. HANS DIETER, age 65, Mad Scientist 

Always wears a bandage around his hand

STR 6  CON 7  SIZ 7   INT 22  POW 6
DEX 8  APP 6  EDU 28  SAN 0   HP 7

Damage Bonus: -1D4.
Skills: Anthropology 25%, Archaeology 20%, Astronomy 35%, Chemistry 90%, Cthulhu Mythos 15%, Electrical Repair 95%, First Aid 75%, Geology 85%, History 20%, Library Use 95%, Mechanical Repair 95%, Medicine 85%, Natural History 90%, Pharmacy 85%.
Languages: English 60%, German 95%.


PHILLIP JURGENS, age 32, Industrial Spy Turned Patriot

If the investigators should remove Jurgens' hat, they will see a shaven patch on his head and a raw, six-inch scar. Dieter did not have enough time to completely program his new "zombie", but left a simple self-destruct program in him. If the investigators should expose Jurgens, the mechanism inside his head will short-circuit itself. As Jurgens' body does spastic flip-flops on the ground or floor, the side of his head will begin to smoke and melt from the heat generated inside his skull. This will, of course, kill him, and any investigator failing his SAN roll will lose 1D6 points from the sight. If Jurgens should remain unexposed, he will self-destruct anyway as soon as Dieter's robot comes on the scene.

STR 15  CON 15  SIZ 14  INT 14  POW 12
DEX 16  APP 13  EDU 16  SAN 60  HP 15

Damage Bonus: +1D4
Skills: Chemistry 75%, Climb 80%, Dodge 85%, Drive Automobile 75%, First Aid 95%, Geology 55%, Hide 75%, Library Use 55%, Listen 80%, Natural History 65%, Psychology 50%, Sneak 75%, Spot Hidden 75%, Track 40%.


TOODEE-6, Robot
Ostensibly designed as a prototype worker robot for industry, this model features some particularly destructive options. It is radiocontrolled and can be operated up to 1,000 feet away. Solid walls will block transmission from the control box, however. The control box is a small palm-sized device with a looped antenna. There are about a dozen unmarked switches on the face of the instrument. The robot itself is a seven-foot-tall metal cylinder. It moves fairly quickly on level ground with motorized treads. though it would have problems outside. It can turn on a dime. It has four telescoping arms with grasping claws and a small death ray mounted in its chest.

STR 40 SIZ 25 (very heavy) DEX 2 * operator's
Move 5 HP 100
Weapons: Claws (4) 25%. damage 1D6*
Death Ray: 20% damage 1D10

* Once held, the investigator will continue to take damage every round unless they overcome the Toodee-6 in a STR vs. STR roll. 
Armor: This machine is made of tough metal. and all weapons will cause minimum damage. An impaling shot from a firearm indicates that a partially exposed joint or circuit has been hit, rendering one function of the robot useless (Keeper's choice). A bucket of water, a fire hose. or a fire extinguisher may short-circuit the entire machine. making it useless-there is a 10% chance of this per bucket or per round the fire hose or extinguisher is played over it.


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






