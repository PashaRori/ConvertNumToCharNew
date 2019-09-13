import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.junit.jupiter.api.Assertions.*;

public class NumberTest {

    @Test(dataProvider = "datePro")
    public void convers(String inputTrueLong, String inputThreat) throws IOException {
        Number number = new Number();
        String inputTrue = number.convertNumToString(Long.valueOf(inputTrueLong));
        assertEquals(inputThreat, inputTrue);
    }

    @DataProvider
    public Object[][] datePro() throws IOException {
        FileInputStream fis = new FileInputStream("ExcelTest.xls");
        Workbook wb = new HSSFWorkbook(fis);
        Object[][] array = new Object[wb.getSheetAt(0).getLastRowNum() + 1][2];
        for (int i = 0; i < wb.getSheetAt(0).getLastRowNum() + 1; i++) {
            if(wb.getSheetAt(0).getRow(i).getCell(0).getCellType() == NUMERIC){
                array[i][0] = String.valueOf((long)wb.getSheetAt(0).getRow(i).getCell(0).getNumericCellValue());
                array[i][1] = " " + getCellText(wb.getSheetAt(0).getRow(i).getCell(1));
            }
            else{
                array[i][0] = "Не число!!!";
                array[i][1] = " " + getCellText(wb.getSheetAt(0).getRow(i).getCell(1));
            }
        }
        fis.close();
        return array;
    }

    public static String getCellText(Cell cell)
    {
        String rezult = "";
        switch (cell.getCellType()) {
            case STRING:
                rezult = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    rezult = cell.getDateCellValue().toString();
                } else {
                    rezult = Double.toString(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                rezult = Boolean.toString(cell.getBooleanCellValue());
                break;
            case FORMULA:
                rezult = cell.getCellFormula();
                break;
            default:
                break;
        }
        return rezult;
    }
}