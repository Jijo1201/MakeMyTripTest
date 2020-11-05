package resources;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readData {

	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	public static String getCellData(int RowNum, int ColNum) throws Exception {

		FileInputStream ExcelFile = new FileInputStream(
				"C:\\Users\\{USER}\\new\\eclipse-workspace\\Practical\\MakeMyTrip\\src\\main\\java\\resources\\Data.xlsx");

		ExcelWBook = new XSSFWorkbook(ExcelFile);

		ExcelWSheet = ExcelWBook.getSheet("login");

		String CellValue = ExcelWSheet.getRow(RowNum).getCell(ColNum).getStringCellValue();

		return CellValue;

	}

}
