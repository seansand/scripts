//MakePodcastXml.groovy

String DIRNAME = /C:\Users\Sean\Documents\saved audio\TrekWest5/

File DIR = new File(DIRNAME)

File xmlFile = new File(/C:\Users\Sean\Documents\saved audio\TrekWest5-podcast.xml/)

List alphaList = []
DIR.eachFile()
{
   if (!it.isDirectory())
      alphaList.add(it.getName())
}
alphaList.sort();

alphaList.each(){println it}

xmlFile.write("");
xmlFile.append('''<?xml version="1.0" encoding="utf-8"?>\n''')
xmlFile.append('''<rss version="2.0" xmlns:itunes="http://www.itunes.com/DTDs/Podcast-1.0.dtd" xmlns:media="http://search.yahoo.com/mrss/">\n''')
xmlFile.append('''<channel>\n''')
xmlFile.append('''<title>TrekWest5 Archives</title>\n''')
xmlFile.append('''<description>TrekWest5 Archived shows</description>\n''')
xmlFile.append('''<itunes:author>Sean Sandquist </itunes:author>\n''')
xmlFile.append('''<link> http://www.yourserver.com/YourPodcastHomepage/ </link>\n''')
xmlFile.append('''<itunes:image href="" />\n''')
xmlFile.append('''<pubDate> Sun, 13 Jul 2014 21:00:00 CST </pubDate>\n''')
xmlFile.append('''<language>en-us</language>\n''')
xmlFile.append('''<copyright> Copyright 2014 Sean Sandquist </copyright>\n''')

alphaList.each()
{
   xmlFile.append('''<item>\n''')
   xmlFile.append("  <title>$it</title>\n")
   xmlFile.append("  <description>$it description</description>\n")
   xmlFile.append('''  <itunes:author>TrekWest5</itunes:author>\n''')
   xmlFile.append('''  <pubDate> Thu, 13 Jul 2014 21:00:00 CST </pubDate>\n''')
   xmlFile.append("  <enclosure url=\"http://s3.amazonaws.com/TrekWest5/$it\" type=\"audio/mpeg\" /> \n")
   xmlFile.append('''</item>\n''')
}

xmlFile.append('''</channel>\n''')
xmlFile.append('''</rss>\n''')

