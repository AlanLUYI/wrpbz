package net.htwater.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
 * 
 * @author chenchu
 * @version
 * @since v 1.0
 * @Date 2015-8-25
 * 
 * 
 */
public class PropertiesHelper {
	private Properties properties = new Properties();
	
	public PropertiesHelper(String fileName){
		try {
			InputStream in = PropertiesHelper.class.getResourceAsStream(fileName);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String getPropertyValue(String key){
		if(properties.containsKey(key)){
			return properties.getProperty(key);
		}
		return null;
	}
}
