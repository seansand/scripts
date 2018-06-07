//Pebble.groovy
println("Enter Pebble Cards Override.txt:");
def systemIn = new BufferedReader(new InputStreamReader(System.in))
      
String text = systemIn.readLine();

File f = new File(/x:\public\Pebble Cards Override.txt/);
f.write(text);