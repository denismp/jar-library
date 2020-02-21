/**
 * 
 */
package com.utility;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @author denis.putnam.ctr
 *
 */
public class ExcelTestReporter extends ExcelOutputUtils {
	private static Logger Log = Logger.getLogger(ExcelTestReporter.class.getName());
	private String filePath;
	private String testId;
	private String purpose;
	private Map<Integer, Object[]> treeMap;
	private Integer numRows = 0;

	/**
	 * None default constructor.
	 * 
	 * @param filePath
	 * @param sheetName
	 * @throws Exception
	 */
	public ExcelTestReporter(String filePath, String sheetName) throws Exception {
		super(filePath, sheetName);
		this.filePath = super.getFilePath();
		this.testId = super.getSheetName();

		/*
		 * Example: //This data needs to be written (Object[]) Map < String, Object[] >
		 * empinfo = new TreeMap < String, Object[] >(); empinfo.put( "1", new Object[]
		 * { "EMP ID", "EMP NAME", "DESIGNATION" }); empinfo.put( "2", new Object[] {
		 * "tp01", "Gopal", "Technical Manager" }); empinfo.put( "3", new Object[] {
		 * "tp02", "Manisha", "Proof Reader" }); empinfo.put( "4", new Object[] {
		 * "tp03", "Masthan", "Technical Writer" }); empinfo.put( "5", new Object[] {
		 * "tp04", "Satish", "Technical Writer" });
		 */
		treeMap = new TreeMap<Integer, Object[]>();
//		treeMap.put("1", new Object[] {"TEST ID:", testId});
//		treeMap.put("2", new Object[] {"PURPOSE:", purpose});
//		treeMap.put("3", new Object[] {"STEP", "SUMMARY", "TEST RESULT"});
	}

	public void printArray(String[] ar) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < ar.length; i++) {
			buf.append(ar[i] + "|");
		}
		Log.info(buf.toString());
	}

	public void populateOutputData(String[][] data) {
		try {
			for (int i = 0; i < data.length; i++) {
//				Log.info("ROW");
//				this.printArray(data[i]);
				this.addRowData(data[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.debug(e.toString());
		}
	}

	@Override
	public Integer getNumRows() {
//		Integer rVal = 0;
//		rVal = this.treeMap.size();
//		return rVal;
		return this.numRows;
	}

	public Integer getDataSize() {
		return this.treeMap.size();
	}

	/**
	 * Add the "TEST ID: testId row
	 * 
	 * @param testId
	 */
	public void addTestId(String testId) {
		this.treeMap.put(numRows++, new Object[] { "TEST ID:", testId });
		this.setTestId(testId);
	}

	/**
	 * Add the PURPOSE: purpose row
	 * 
	 * @param purpose
	 */
	public void addPurpose(String purpose) {
		this.treeMap.put(numRows++, new Object[] { "PURPOSE:", purpose });
		this.setPurpose(purpose);
	}

	public void printTreeMap() {
		for (Integer key : this.treeMap.keySet()) {
			Object[] row = this.treeMap.get(key);
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < row.length; i++) {

				buf.append(row[i] + "|");
			}
			Log.info("row=" + buf.toString());
		}
	}

	/**
	 * Add Row data. Can be use to add a header for subsequent data rows.
	 * 
	 * @param ar
	 */
	public void addRowData(String ar[]) {
		this.treeMap.put(numRows++, ar);
//		this.printTreeMap();
	}

	/**
	 * write -- call this only once as it closes the file.
	 * 
	 * @throws IOException
	 */
	public void write() throws IOException {
		super.write(this.getTreeMap());
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTestId() {
		return testId;
	}

	private void setTestId(String testId) {
		this.testId = testId;
	}

	public String getPurpose() {
		return purpose;
	}

	private void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Map<Integer, Object[]> getTreeMap() {
		return treeMap;
	}

	public void setTreeMap(Map<Integer, Object[]> treeMap) {
		this.treeMap = treeMap;
	}

	public void setNumRows(Integer numRows) {
		this.numRows = numRows;
	}

}
