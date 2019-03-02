package com.ebay.wipro.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FileUtilities {

	public static Map<String,String >  readEmulatorProp() throws IOException {
		Properties p= new Properties();
		File propFile= new File ("D:\\Wipro_Ebay\\Wipro\\src\\test\\resources\\TestProperties\\Emulator.properties");
		FileInputStream fis= new FileInputStream(propFile);
		p.load(fis);
		Map<String , String>m= new LinkedHashMap<String, String>();
		Set s= p.keySet();
		for(Object o:s) {
			m.put(o.toString(), p.getProperty(o.toString()));
		}
		
		return m;
	}
	
	public static Map<String,String >  readEmulatorCaps() throws IOException {
		Properties p= new Properties();
		File propFile= new File ("D:\\Wipro_Ebay\\Wipro\\src\\test\\resources\\TestProperties\\EmulatorCaps.properties");
		FileInputStream fis= new FileInputStream(propFile);
		p.load(fis);
		Map<String , String>m= new LinkedHashMap<String, String>();
		Set s= p.keySet();
		for(Object o:s) {
			m.put(o.toString(), p.getProperty(o.toString()));
		}
		
		return m;
	}
	
}
