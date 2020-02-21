/**
 * 
 */
package com.utility;

/**
 * @author denis.putnam.ctr
 *
 */
public class ExcelTestInput extends ExcelInputUtils {
	private String[][] currentData;
	private ExcelTestReporter excelReporter;
	private String currentWorksheet;

	public ExcelTestInput(String filePath, String sheetName, ExcelTestReporter excelReporter) throws Exception {
		super(filePath, sheetName);
		this.currentWorksheet = sheetName;
		this.currentData = super.getTableArray();
		this.excelReporter = excelReporter;
	}

	/**
	 * @return the currentData
	 */
	public String[][] getCurrentData() {
		return currentData;
	}

	/**
	 * @param currentData the currentData to set
	 */
	public void setCurrentData(String[][] currentData) {
		this.currentData = currentData;
	}
	
	/**
	 * Set a new worksheet for the reporter output
	 * @param worksheet
	 * @throws Exception
	 */
	public void readNewWorkSheet(String worksheet) throws Exception {
		this.currentWorksheet = worksheet;
		super.setSheetName(this.currentWorksheet);
		this.currentData = super.getTableArray();
		this.excelReporter.populateOutputData(this.currentData);
	}
	
	/**
	 * Add the current data to the report output.
	 */
	public void addCurrentDataToReport() {
		for(int i = 0; i < this.getLastRowUsed(); i++) {
			this.excelReporter.addRowData(this.currentData[i]);
		}		
	}

	/**
	 * @return the excelReporter
	 */
	public ExcelTestReporter getExcelReporter() {
		return excelReporter;
	}

	/**
	 * @param excelReporter the excelReporter to set
	 */
	public void setExcelReporter(ExcelTestReporter excelReporter) {
		this.excelReporter = excelReporter;
	}

}
