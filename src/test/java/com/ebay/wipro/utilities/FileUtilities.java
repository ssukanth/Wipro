package com.ebay.wipro.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtilities {

/*	Method name :readEmulatorProp
	Objective : To read and return the emulator properties  and instantiate the emualtor of choice
	Parameters: null
	Return type: Map with emulator keys for emulator instantiation
	*/
	
	public static Map<String,String >  readEmulatorProp() throws IOException {
		Properties p= new Properties();
		File propFile= new File (new File(".").getAbsolutePath()+"\\src\\test\\resources\\TestProperties\\Emulator.properties");
		FileInputStream fis= new FileInputStream(propFile);
		p.load(fis);
		Map<String , String>m= new LinkedHashMap<String, String>();
		Set s= p.keySet();
		for(Object o:s) {
			m.put(o.toString(), p.getProperty(o.toString()));
		}
		
		return m;
	}
	
	
	/*	Method name :readEmulatorCaps
	Objective : To read and return the emulator capabilities  and instantiate the emualtor of choiceth app
	Return type: Map with emulator keys for emulator capabilities
	*/
	
	public static Map<String,String >  readEmulatorCaps() throws IOException {
		Properties p= new Properties();
		File propFile= new File (new File(".").getAbsolutePath()+"\\src\\test\\resources\\TestProperties\\EmulatorCaps.properties");
		FileInputStream fis= new FileInputStream(propFile);
		p.load(fis);
		Map<String , String>m= new LinkedHashMap<String, String>();
		Set s= p.keySet();
		for(Object o:s) {
			m.put(o.toString(), p.getProperty(o.toString()));
		}
		
		return m;
	}
	
	
	/*	Method name :readXl
	Objective : To read the data from the excel
	Parameter: string path of the file
	Return type: String 2-D Array
	*/	
	public String [][] readXl(String fPath) throws IOException{
		File fil= new File(fPath);
		FileInputStream fis= new FileInputStream(fil);
		Workbook wb= new XSSFWorkbook(fis);
		Sheet targetSht=wb.getSheet("TestDat");
		int rowCnt=targetSht.getPhysicalNumberOfRows();
		int clmCnt=targetSht.getRow(0).getPhysicalNumberOfCells();
		String [][] dat = new String [rowCnt-1][clmCnt];
		for(int i=1;i<rowCnt;i++) {
			Row r= targetSht.getRow(i);
			for(int j=0;j<clmCnt;j++) {
				Cell c= r.getCell(j);	
				c.setCellType(Cell.CELL_TYPE_STRING);
				dat[i-1][j]=c.getStringCellValue();
				
			}
		}	
		System.out.println("The size of the data set is :"+dat.length);
		return dat;
	}
	
}
