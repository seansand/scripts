import java.util.Random


def wordStr = '''yo
soy
un
una
es
el
tiene
veo
y
tengo
esta
en
vamos
al
estamos
dentro
este
para
por
gusta
con
quien
que
grande
aqui
somos
amigos
estaba
mira
tambien
juego
contigo
donde
conmigo'''    
    

def wordStrOrig = '''yo
soy
un
una
es
el
tiene
veo
y
tengo
está
en
vamos
al
estamos
dentro
este
para
por
gusta
con
quién
qué
grande
aquí
somos
amigos
estaba
mira
también
juego
contigo
dónde
conmigo'''

def wordList = []

Random rand = new Random()

wordStr.eachLine()
{
   wordList.add(it)
}

int max = wordList.size();

20.times{println()}

def systemIn = new BufferedReader(new InputStreamReader(System.in))

while (true)
{
   println ("\n\n\n\n\n        " + wordList.get(rand.nextInt(max)).toLowerCase())
   systemIn.readLine()
}

   



