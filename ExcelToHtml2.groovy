//ExcelToHtml.groovy

import org.apache.poi.ss.examples.html.*;
import org.apache.poi.ss.usermodel.*;

String htmlFile = "Y:\\wffl\\2013\\2013.xlsx.html";
String xlsxFile = "Y:\\wffl\\2013\\2013.xlsx"

//READ

File xlsx = new File(xlsxFile);

InputStream is = xlsx.newDataInputStream();
Workbook workBook = WorkbookFactory.create(is);

//int totalSheets = workBook.getNumberOfSheets();
//for (int i = 0; i <= totalSheets - 1; i++) {
//  Sheet sheet = workBook.getSheetAt(i);
//}


//WRITE

try
{
    FileWriter fw = new FileWriter(htmlFile);
    ToHtml.create(workBook, new PrintWriter(fw));
}
catch (IOException e2)
{
    e2.printStackTrace();
}