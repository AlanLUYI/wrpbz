package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

import org.apache.axis.message.SOAPHeaderElement;

public interface MydemoService {
	/**
	 * 根据名字获取问候
	 * @param who
	 * @return
	 */
	
	/**
	 * 水工特征：获取文档列表
	 * @param type
	 * @return
	 */
	
	public List<Map<String, Object>> getUsers(String oucode,boolean deep);
	public List<Map<String, Object>> getORG(String oucode,boolean deep);
	public List<Map<String, Object>> getORG2Users(String oucode);
}
