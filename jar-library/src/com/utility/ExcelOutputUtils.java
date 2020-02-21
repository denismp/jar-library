/**
 * 
 */
package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author denis.putnam.ctr
 *
 */
public class ExcelOutputUtils {
	private String filePath;
	private String sheetName;
	private XSSFWorkbook workbook;
	private XSSFSheet worksheet;
//	private FileOutputStream out;

	/**
	 * Constructor
	 * 
	 * @param filePath  complete path to .xlsx file
	 * @param sheetName name of the worksheet
	 * @throws Exception
	 */
	ExcelOutputUtils(String filePath, String sheetName) throws Exception {
		this.filePath = filePath;
		this.sheetName = sheetName;
		this.createWorksheet();
//		this.out = new FileOutputStream(new File(this.filePath));
	}

	private void createWorksheet() throws Exception {
		if (new File(this.filePath).exists()) {
			FileInputStream excelFile = new FileInputStream(this.filePath);
			this.workbook = new XSSFWorkbook(excelFile);
			try {
				this.worksheet = this.workbook.createSheet(this.sheetName);
			} catch (IllegalArgumentException e) {
				this.worksheet = this.workbook.getSheet(this.sheetName);
			}
//			if (this.workbook.getSheet(this.sheetName) != null) {
//				this.worksheet = this.workbook.getSheet(this.sheetName);
//			} else {
//				this.worksheet = this.workbook.createSheet(this.sheetName);
//			}
		} else {
			this.workbook = new XSSFWorkbook();
			this.worksheet = this.workbook.createSheet(this.sheetName);
		}
	}
	
	public Integer getNumRows() {
		Integer rVal = this.worksheet.getLastRowNum();
		return rVal;
	}

	/**
	 *
     * //This data needs to be written (Object[])
     * Map < String, Object[] > empinfo = 
     * new TreeMap < String, Object[] >();
     * empinfo.put( "1", new Object[] { "EMP ID", "EMP NAME", "DESIGNATION" });
     * empinfo.put( "2", new Object[] { "tp01", "Gopal", "Technical Manager" });
     * empinfo.put( "3", new Object[] { "tp02", "Manisha", "Proof Reader" });
     * empinfo.put( "4", new Object[] { "tp03", "Masthan", "Technical Writer" });
     * empinfo.put( "5", new Object[] { "tp04", "Satish", "Technical Writer" });
     * empinfo.put( "6", new Object[] { "tp05", "Krishna", "Technical Writer" });
	 * 
	 * @param treeMap
	 * @throws IOException
	 */
	public void write(Map<Integer, Object[]> treeMap) throws IOException {
		XSSFRow row;

		Set<Integer> keys = treeMap.keySet();
		int rowid = 0;
		for (Integer key : keys) {
			row = this.worksheet.createRow(rowid++);
			Object[] objectArr = treeMap.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				try {
				cell.setCellValue((String) obj.toString());
				} catch (NullPointerException e) {
					Log.warn(e.getMessage());
				}
			}
		}
		FileOutputStream out = new FileOutputStream(new File(this.filePath));
		this.workbook.write(out);
		out.close();
	}
	
//	public void close() throws IOException {
//		this.out.close();
//	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public XSSFSheet getWorksheet() {
		return worksheet;
	}

	public void setWorksheet(XSSFSheet worksheet) {
		this.worksheet = worksheet;
	}

}
