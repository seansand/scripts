//HalfFifth.groovy

File f = new File(/C:\Users\Sean\Google Drive\eBooks\Games\Cthulhu\handouts\All Scenarios\Half-fifth.html/)

f.write("<HTML><HEAD>")
f.append('''<STYLE>
            tr, td, th {
                border-width: thin;
                border-spacing: 2px;
                border-style: none;
                border-color: black;
                font-family:verdana;
                font-size: 11pt
            }
           </STYLE>
        ''')
f.append("</HEAD><BODY>")

f.append("<TABLE CELLPADDING=6 BORDER=1><TR><TD>");
f.append("<TABLE CELLPADDING=2 BORDER=0>");
f.append("<TH><TD COLSPAN=15>")
f.append("<CENTER><B>Half / fifth percentage dice table</B></CENTER>")
f.append("</TD></TH>")
f.append("<TR><TD COLSPAN=15>")
f.append("&nbsp;")
f.append("</TD></TR>")

for (int k = 0; k < 20; ++k) {
   f.append("<TR>")

   for (int j = 0; j < 5; ++j) {
      
      int i = 1 + k + j*20
      f.append("<TD align=center><B>$i</B></TD>")
      f.append("<TD align=center>( ${i.intdiv(2)} / ")
      f.append("${i.intdiv(5)} )</TD>")
      
      if (j != 4) {
         f.append("<TD> &nbsp; &nbsp; </TD>");
      }
      
   }
   f.append("</TR>")
}

f.append("</TABLE>");
f.append("</TD></TR></TABLE>");
f.append("</BODY></HTML>")


