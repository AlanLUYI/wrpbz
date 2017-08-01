/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  Mar 13, 2014 Neal Miao 
 * 
 * Copyright(c) 2014, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper.other;

import java.util.Comparator;
import java.util.Map;
/**
 *  
 * @author Neal Miao
 * @version
 * @Date Mar 13, 2014 3:22:46 PM
 * 
 * @see
 */
public class MaxRainStationSorter implements Comparator<Map<String, Object>> {

	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		String val1 = o1.get("text").toString();
		String val2 = o2.get("text").toString();
		Double d1 = Double.parseDouble(val1.substring(val1.lastIndexOf("(")+1,val1.lastIndexOf("毫")));
		Double d2 = Double.parseDouble(val2.substring(val2.lastIndexOf("(")+1,val2.lastIndexOf("毫")));
		int compare = d1.compareTo(d2);
		if (compare > 0)
			return -1;
		else if (compare < 0)
			return 1;
		else
			return 0;
	}
}
