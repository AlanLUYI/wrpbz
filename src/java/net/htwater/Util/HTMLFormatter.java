/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2012-4-9 Neal Miao 
 * 
 * Copyright(c) 2012, by HTWater. All Rights Reserved.
 */
package net.htwater.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.miao.framework.util.DateUtil;

/**
 * 
 * 
 * @author Neal Miao
 * @version
 * @since v 1.0
 * @Date 2012-4-9 上午09:40:32
 * 
 * @see
 */
public class HTMLFormatter {

	static Logger logger = Logger.getLogger(HTMLFormatter.class);

	/**
	 * 转换出dataTable能用的json
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	public static String list2AAData(List<?> list) {
		return list2JsonData("aaData", list);
	}

	/**
	 * 转换出dataTable能用的json
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	public static String array2AAData(JSONArray list) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("aaData", list);
		return jsonObject.toString();
	}

	/**
	 * 转换出dataTable能用的json
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	public static String list2JsonData(String name, List<?> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, list);
		JSONObject json = JSONObject.fromObject(map);
		// logger.info(json.toString());
		return json.toString();
	}

	/**
	 * 转换出dataTable能用的json
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	public static String list2JsonArr(List<?> list) {
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}

	/**
	 * 站点点击链接
	 * 
	 * @param name
	 * @param code
	 * @return String
	 * @since v 1.0
	 */
	public static String formatStationHTML(String name, String code) {
		return "<span style=\"cursor:pointer;\" onclick=\"showStation('" + code
				+ "','" + DateUtil.getToday("yyyy-MM-dd") + "');\">" + name
				+ "</span>";
	}

	public static String updateStationInfo(String name, String code) {
		return "<span style=\"cursor:pointer;\" onclick=\"showDialog('" + code
				+ "','" + name + "');\">" + name + "</span>";
	}

	// onmouseover="this.className="btover""
	public static String updateLabelname(String name, String code, String stnm,
			String id, String power) {
		return "<input style=\"cursor:pointer;\" type=radio name=labchoose onclick=\"getLabelInfo('"
				+ code
				+ "','"
				+ name
				+ "','"
				+ stnm
				+ "','"
				+ id
				+ "','"
				+ power + "');\" />" + name;
	}

	/**
	 * 分区点击链接
	 * 
	 * @param name
	 * @param code
	 * @return String
	 * @since v 1.0
	 */
	public static String formatTypeHTML(String name, String code) {
		return "<span style=\"cursor:pointer;\" onclick=\"showStation('" + name
				+ "','" + code + "');\">" + name + "</span>";
	}

	/**
	 * 去掉html标签
	 * 
	 * @param inputString
	 * @return String
	 * @since v 1.0
	 */
	public static String removeHtmlTag(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			/* 空格 —— */
			// p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = htmlStr.replaceAll(" ", " ");
			textStr = htmlStr;
		} catch (Exception e) {
		}
		return textStr;
	}
}
