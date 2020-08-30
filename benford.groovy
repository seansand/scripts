    //Benford.groovy
    ArrayList<Integer> startsWith = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    int iterations = 1000000
    def random = new Random()
    iterations.times {
       def nextN = random.nextInt(Integer.MAX_VALUE) + 1
	    int next3 = random.nextInt(nextN) + 1
	    int next2 = random.nextInt(next3) + 1
	    int next1 = random.nextInt(next2) + 1
	    int next0 = random.nextInt(next1) + 1
	    intoArray(next0, startsWith)
    }
    (1..9).each {
       println("[$it] = ${startsWith.get(it) / iterations * 100}")
    }
    void intoArray(int n, ArrayList<Integer> startsWith) {
        String s = n.toString()
        int i = s.substring(0, 1).toInteger()
        ++(startsWith[i])
    }
