package com.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 
 * @author denis.putnam.ctr Original code was stolen from the internet from
 *         here:
 *         https://www.tutorialspoint.com/apache_poi/apache_poi_workbooks.htm
 *         Modifications are mine and were done to make things more generic.
 *
 */
public class ExcelInputUtils {
	private static Logger Log = Logger.getLogger(ExcelInputUtils.class.getName());
	private XSSFSheet inputExcelWSheet;
	private XSSFWorkbook inputExcelWBook;
	private XSSFCell cell;
	private String filePath;
	private String sheetName;
	private FileInputStream excelFile;

	/**
	 * Constructor to open the workbook and sheet name
	 * 
	 * @param filePath  - complete path name to the .xlsx file
	 * @param sheetName - name of the sheet that you wish to read.
	 * @throws Exception
	 */
	public ExcelInputUtils(String filePath, String sheetName) throws Exception {
		this.filePath = filePath;
		this.sheetName = sheetName;
		this.openExcelInputFile();
	}

	/**
	 * This method opens the File path and to open the Excel workbook, and work
	 * sheet.
	 * 
	 * @throws Exception
	 */
	private void openExcelInputFile() throws Exception {
		try {
			// Open the Excel file
			this.excelFile = new FileInputStream(this.filePath);

			// Access the required test data sheet
			inputExcelWBook = new XSSFWorkbook(excelFile);
			inputExcelWSheet = inputExcelWBook.getSheet(this.sheetName);
		} catch (Exception e) {
			throw (e);
		}
	}
	
	private void openWorkSheet() {
		this.inputExcelWSheet = inputExcelWBook.getSheet(this.sheetName);
	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
		this.openWorkSheet();
	}

	/**
	 * Get the two dimensional array of strings that contain the sheet data from the
	 * current worksheet.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[][] getTableArray() throws Exception {
		String[][] tabArray = null;
		try {
			int startRow = 0;
			int startCol = 0;
			int ci, cj;

			int totalRows = inputExcelWSheet.getLastRowNum();

			// you can write a function as well to get Column count
//			int totalCols = 2;
//			int totalCols = inputExcelWSheet.getCTWorksheet().getColsList().size();
			int totalCols = this.getLastColumnUsed();

			tabArray = new String[totalRows+1][totalCols+1];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j <= totalCols; j++, cj++) {
//					System.out.println("ci=" + ci + " cj=" + cj);
					tabArray[ci][cj] = getCellData(i, j);
//					System.out.print(tabArray[ci][cj] + ":");
				}
//				System.out.println();
			}
		} catch (FileNotFoundException e) {
//			System.out.println("Could not read the Excel sheet");
//			e.printStackTrace();
			Log.error(e.toString());
			Log.error("Could not read Excel sheet");
		} catch (IOException e) {
//			System.out.println("Could not read the Excel sheet");
			Log.error(e.toString());
			Log.error("Could not read Excel sheet");
		}

		return (tabArray);
	}

	/**
	 * Get the data from the given sheetName
	 * 
	 * @param sheetName
	 * @return - String[][] containing the data
	 * @throws Exception
	 */
	public String[][] getTableArray(String sheetName) throws Exception {
		String[][] tabArray = null;
		this.sheetName = sheetName;
		try {

			inputExcelWSheet = inputExcelWBook.getSheet(sheetName);

			int startRow = 0;
			int startCol = 0;
			int ci, cj;

			int totalRows = inputExcelWSheet.getLastRowNum();

			// you can write a function as well to get Column count
//			int totalCols = 2;
//			int totalCols = inputExcelWSheet.getCTWorksheet().getColsList().size();
			int totalCols = this.getLastColumnUsed();

			tabArray = new String[totalRows+1][totalCols+1];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j <= totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
//					System.out.println(tabArray[ci][cj]);
				}
			}
		} catch (FileNotFoundException e) {
			Log.error("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("Could not read the Excel sheet");
			e.printStackTrace();
		}

		return (tabArray);
	}

	/**
	 * Get one row of data from the given worksheet.
	 * 
	 * @param sheetName
	 * @param iTestCaseRow - index of the row desired.
	 * @return String[][] of the single row of data.
	 * @throws Exception
	 */
	public String[] getTableArray(String sheetName, int iTestCaseRow) throws Exception {
		String[] tabArray = null;

		try {
			// Access the required test data sheet
			inputExcelWSheet = inputExcelWBook.getSheet(sheetName);

			int startCol = 0;
			int ci = 0, cj = 0;
//			int totalRows = 1;
			int totalRows = inputExcelWSheet.getLastRowNum();
//			int totalCols = 2;
//			int totalCols = inputExcelWSheet.getCTWorksheet().getColsList().size();
			int totalCols = this.getLastColumnUsed();

			tabArray = new String[totalCols+1];

			for (int j = startCol; j <= totalCols; j++, cj++) {
				tabArray[cj] = getCellData(iTestCaseRow, j);
//				System.out.println(tabArray[cj]);
			}
//			System.out.println();
		} catch (FileNotFoundException e) {
			Log.error("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("Could not read the Excel sheet");
			e.printStackTrace();
		}

		return (tabArray);
	}

//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	/**
	 * This method is to read the test data from the Excel cell, in this we are
	 * passing parameters as Row num and Col num
	 * 
	 * @param rowNum - 0 based
	 * @param ColNum - 0 based
	 * @return
	 * @throws Exception
	 */
	public String getCellData(int rowNum, int ColNum) throws Exception {
		String cellData;
		try {
			this.cell = inputExcelWSheet.getRow(rowNum).getCell(ColNum);
			CellType cellType = this.cell.getCellType();
			if (cellType == CellType.NUMERIC) {
				cellData = Double.toString(this.cell.getNumericCellValue());
			} else {
				cellData = this.cell.getStringCellValue();
			}

			return cellData;
		} catch (Exception e) {

			return "";
		}
	}

//	public String getTestCaseName(String sTestCase) throws Exception {
//		String value = sTestCase;
//
//		try {
//			int posi = value.indexOf("@");
//			value = value.substring(0, posi);
//			posi = value.lastIndexOf(".");
//			value = value.substring(posi + 1);
//
//			return value;
//		} catch (Exception e) {
//			throw (e);
//		}
//	}

//	public int getRowContains(String sTestCaseName, int colNum) throws Exception {
//		int i;
//
//		try {
//			int rowCount = this.getRowLastUsed();
//			for (i = 0; i < rowCount; i++) {
//				if (this.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
//					break;
//				}
//			}
//			return i;
//		} catch (Exception e) {
//			throw (e);
//		}
//	}

//	public int getLastRowUsed() throws Exception {
//
//		try {
//			int RowCount = inputExcelWSheet.getLastRowNum();
//			return RowCount;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw (e);
//		}
//	}

	public int getLastRowUsed() {
		return this.inputExcelWSheet.getLastRowNum();
	}

	public int getLastColumnUsed() {
		int rowCount = this.getLastRowUsed();
		int maxCol = 0;
		for (int i = 0; i < rowCount; i++) {
			try {
				if (maxCol < inputExcelWSheet.getRow(i).getLastCellNum()) {
					maxCol = inputExcelWSheet.getRow(i).getLastCellNum();
				}
			} catch (NullPointerException npe) {

			}
		}
		return maxCol;
	}

	public void close() throws IOException {
		this.excelFile.close();
	}
}