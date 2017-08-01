/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2013年12月17日 Neal Miao 
 * 
 * Copyright(c) 2013, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Neal Miao
 * @version
 * @Date 2013年12月17日 下午4:11:26
 * 
 * @see
 */
public class MyCaches {
	/**
	 * 暂时不用
	 * 
	 * @author Neal Miao
	 * @version
	 * @Date 2013年12月17日 下午4:17:12
	 * 
	 * @see
	 */
	public static enum Location {
		YZ("鄞州区", "yz");
		// 成员变量
		private String name;
		private String sname;

		// 构造方法
		private Location(String name, String sname) {
			this.name = name;
			this.sname = sname;
		}

		// 覆盖方法
		@Override
		public String toString() {
			return this.sname + "_" + this.name;
		}
	}

	/**
	 * 地区名称
	 */
	public static Map<String, String> locationMap = new HashMap<String, String>();

	/**
	 * 分区县
	 */
	public static List<String> disctrics = new ArrayList<>();

	/**
	 * 大型水库
	 */
	public static List<String> bigRsvrs = new ArrayList<>();
	
	static {
		locationMap.put("nbs", "宁波市");
		locationMap.put("nh", "宁海县");
		locationMap.put("xs", "象山县");
		locationMap.put("fh", "奉化市");
		locationMap.put("yy", "余姚市");
		locationMap.put("cx", "慈溪市");
		locationMap.put("yz", "鄞州区");
		locationMap.put("bl", "北仑区");
		locationMap.put("zh", "镇海区");
		locationMap.put("jd", "江东区");
		locationMap.put("hs", "海曙区");
		locationMap.put("jb", "江北区");
		locationMap.put("sq", "市区");
		locationMap.put("bxsk", "白溪水库");
		locationMap.put("hssk", "横山水库");
		locationMap.put("jksk", "皎口水库");
		locationMap.put("txsk", "亭下水库");
		locationMap.put("zgzsk", "周公宅水库");
		locationMap.put("smhsk", "四明湖水库");

		disctrics.add("宁海县");
		disctrics.add("象山县");
		disctrics.add("奉化市");
		disctrics.add("余姚市");
		disctrics.add("慈溪市");
		disctrics.add("鄞州区");
		disctrics.add("北仑区");
		disctrics.add("镇海区");
		disctrics.add("江东区");
		disctrics.add("海曙区");
		disctrics.add("江北区");
		disctrics.add("市区");
		
		bigRsvrs.add("白溪水库");
		bigRsvrs.add("横山水库");
		bigRsvrs.add("皎口水库");
		bigRsvrs.add("亭下水库");
		bigRsvrs.add("周公宅水库");
		bigRsvrs.add("四明湖水库");
	}
}
