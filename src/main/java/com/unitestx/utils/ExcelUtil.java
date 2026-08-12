package com.unitestx.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	// Method to get Test Data from Excel based on Column Name & Row Name for Data

	public static String getTestData(String testCaseID, String columnName) throws IOException {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\resources\\UnitestX_Testdata.xlsx");
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheet("Super_Sheet");
		Row headerRow = sheet.getRow(0);
		int colIndex = -1;
		for (Cell cell : headerRow) {
			if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName.trim())) {
				colIndex = cell.getColumnIndex();
				break;
			}
		}
		if (colIndex == -1) {
			workbook.close();
			throw new IllegalArgumentException("Column '" + columnName + "' not found in Excel.");
		}
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell idCell = row.getCell(0);
				if (idCell != null && idCell.getStringCellValue().equalsIgnoreCase(testCaseID)) {
					String value = row.getCell(colIndex).getStringCellValue();
					workbook.close();
					return value;
				}
			}
		}
		workbook.close();
		throw new IllegalArgumentException("Test case ID '" + testCaseID + "' not found in Excel.");
	}

}
