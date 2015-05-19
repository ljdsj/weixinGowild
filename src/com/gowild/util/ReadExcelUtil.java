package com.gowild.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * 读取 key ， value 的excel文件 2003
 * 放在 Map<String,String>里
 * @author Administrator
 *
 */
public class ReadExcelUtil {
	private static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	private static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static Map<String,String> readExcel(String path){
		if(null == path || "".equals(path)){
			return null;
		}else{
			String postfix = StringUtil.getPostfix(path);
			if(null != postfix && !"".equals(postfix)){
				if(OFFICE_EXCEL_2003_POSTFIX.equals(postfix)){
					return readXls(path);
				}
			}
		}
		return null;
	}
	public static Map<String,String> readXls(String path){
		Map<String,String> keywords = new HashMap<String,String>();
		try {
			InputStream is = new FileInputStream(path);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if(hssfSheet == null){
					continue;
				}
				for(int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if(null != hssfRow){
						keywords.put(getValue(hssfRow.getCell(0)), getValue(hssfRow.getCell(1)));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keywords;
	}
	@SuppressWarnings("static-access")
	public static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}
