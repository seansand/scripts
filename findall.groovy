URL u = new URL("http://feeds.feedburner.com/TrekWest5");

String s = u.getText();



List list = s.findAll("http://s3\\.amazonaws\\.com.*?\\.mp3");

Set set = list.findAll() {!it.contains(" ")}

set.each() {println it}










