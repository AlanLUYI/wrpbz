package net.htwater.mydemo.service.impl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import net.htwater.mydemo.service.MydemoService;

import org.apache.axis.message.MessageElement;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.PinyinUtil;

import com.personel.BPMAPI;
import com.personel.BPMAPILocator;
import com.personel.GetDataResponseGetDataResult;
import com.personel.GetOUsResponseGetOUsResult;
import com.personel.GetUsersResponseGetUsersResult;

//import org.apache.axis.message.SOAPHeaderElement;

public class MydemoServiceImpl implements MydemoService {
	
	public List<Map<String, Object>> getUsers(String oucode,boolean deep) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		BPMAPI service = new BPMAPILocator();
		GetUsersResponseGetUsersResult responseGetDatasetResult;
		try {
			responseGetDatasetResult = service.getBPMAPISoap().getUsers(oucode, deep);
			MessageElement[] elements = responseGetDatasetResult.get_any();
			MessageElement el = elements[0];
			NodeList nodeList = el.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				NodeList nodeList2 = node.getChildNodes();
				Map<String, Object> newMap = new LinkedHashMap<String, Object>();
				for (int j = 0; j < nodeList2.getLength(); j++) {
					Node node2 = nodeList2.item(j);
					newMap.put(""+node2.getNodeName().toString()+"", ""+node2.getLastChild()+"");
				}
				list.add(newMap);
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	

	public List<Map<String, Object>> getORG(String oucode,boolean deep) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		BPMAPI service = new BPMAPILocator();
		GetOUsResponseGetOUsResult responseGetDatasetResult;
		try {
			responseGetDatasetResult = service.getBPMAPISoap().getOUs(oucode, deep);
			MessageElement[] elements = responseGetDatasetResult.get_any();
			MessageElement el = elements[0];
			NodeList nodeList = el.getFirstChild().getChildNodes();
			Map<String, Object> newMap = new LinkedHashMap<String, Object>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(i<=6){
					newMap.put(""+node.getNodeName().toString()+"", ""+node.getFirstChild()+"");
				}else {
					NodeList nodeList2 = node.getChildNodes();
					Map<String, Object> newMap1 = new LinkedHashMap<String, Object>();
					for (int j = 0; j < nodeList2.getLength(); j++) {
						Node node2 = nodeList2.item(j);
						if(j<=6){
							newMap1.put(""+node2.getNodeName().toString()+"", ""+node2.getFirstChild()+"");
						}else{
							NodeList nodeList3 = node2.getChildNodes();
							Map<String, Object> newMap2 = new LinkedHashMap<String, Object>();
							for (int k = 0; k < nodeList3.getLength(); k++) {
								Node node3 = nodeList3.item(k);
								if(k<=6){
									newMap2.put(""+node3.getNodeName().toString()+"", ""+node3.getFirstChild()+"");
								}else{
									NodeList nodeList4 = node3.getChildNodes();
									Map<String, Object> newMap3 = new LinkedHashMap<String, Object>();
									for (int m = 0; m < nodeList4.getLength(); m++) {
										Node node4 = nodeList4.item(m);
										newMap3.put(""+node4.getNodeName().toString()+"", ""+node4.getFirstChild()+"");
									}
									list.add(newMap3);
								}
							}
							list.add(newMap2);
						}
					}
					list.add(newMap1);
				}
			}
			list.add(newMap);
			if (list != null) {
				for (Map<String, Object> map : list) {
					String OUID = map.get("OUID").toString(); 
					String ParentOUID = map.get("ParentOUID").toString(); 
					String OUName = map.get("OUName").toString(); 
					map.put("id", OUID);
					map.put("pId", ParentOUID);
					map.put("name", OUName);
					if(OUID.equals("2")){
						map.put("open", true);
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> getORG2Users(String oucode) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		BPMAPI service = new BPMAPILocator();
		GetDataResponseGetDataResult responseGetDatasetResult;
		try {
			responseGetDatasetResult = service.getBPMAPISoap().getData(oucode);
			MessageElement[] elements = responseGetDatasetResult.get_any();
			MessageElement el = elements[0];
			NodeList nodeList = el.getFirstChild().getChildNodes();
			Map<String, Object> newMap = new LinkedHashMap<String, Object>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(i<=6){
					newMap.put(""+node.getNodeName().toString()+"", ""+node.getFirstChild()+"");
				}else {
					NodeList nodeList2 = node.getChildNodes();
					Map<String, Object> newMap1 = new LinkedHashMap<String, Object>();
					for (int j = 0; j < nodeList2.getLength(); j++) {
						Node node2 = nodeList2.item(j);
						if(j<=6){
							newMap1.put(""+node2.getNodeName().toString()+"", ""+node2.getFirstChild()+"");
						}else{
							if (node2.getNodeName().toString().equals("User")) {
								NodeList nodeList2User = node2.getChildNodes();
								Map<String, Object> newMap2User = new LinkedHashMap<String, Object>();
								for (int m = 0; m < nodeList2User.getLength(); m++) {
									Node nodeUser = nodeList2User.item(m);
									newMap2User.put(""+nodeUser.getNodeName().toString()+"", ""+nodeUser.getFirstChild()+"");
									Node pId = node.getFirstChild().getFirstChild();
									Node id = node2.getFirstChild().getFirstChild();
									Node name = node2.getFirstChild().getNextSibling().getFirstChild();
									newMap2User.put("id", ""+id+"");
									newMap2User.put("pId", ""+pId+"");
									newMap2User.put("name", ""+name+"");
								}
								list.add(newMap2User);
							}else {   //节点名称为ORG
								NodeList nodeList3 = node2.getChildNodes();
								Map<String, Object> newMap2 = new LinkedHashMap<String, Object>();
								for (int k = 0; k < nodeList3.getLength(); k++) {
									Node node3 = nodeList3.item(k);
									if(k<=6){
										newMap2.put(""+node3.getNodeName().toString()+"", ""+node3.getFirstChild()+"");
									}else{
										if (node3.getNodeName().toString().equals("ORG")) {
											NodeList nodeList3Org = node3.getChildNodes();
											Map<String, Object> newMap3 = new LinkedHashMap<String, Object>();
											for (int m = 0; m < nodeList3Org.getLength(); m++) {
												Node node4 = nodeList3Org.item(m);
												if (m<=6) {
													newMap3.put(""+node4.getNodeName().toString()+"", ""+node4.getFirstChild()+"");
												}else {
													NodeList nodeList3Users = node4.getChildNodes();
													Map<String, Object> newMap3UserDeep = new LinkedHashMap<String, Object>();
													for (int o = 0; o < nodeList3Users.getLength(); o++) {
														Node nodeUser = nodeList3Users.item(o);
														newMap3UserDeep.put(""+nodeUser.getNodeName().toString()+"", ""+nodeUser.getFirstChild()+"");
														Node id = node4.getFirstChild().getFirstChild();
														Node pId = node3.getFirstChild().getFirstChild();
														Node name = node4.getFirstChild().getNextSibling().getFirstChild();
														newMap3UserDeep.put("id", ""+id+"");
														newMap3UserDeep.put("pId", ""+pId+"");
														newMap3UserDeep.put("name", ""+name+"");
													}
													list.add(newMap3UserDeep);
												}
											}
											list.add(newMap3);
										}else if (node3.getNodeName().toString().equals("User")) {
											NodeList nodeList3User = node3.getChildNodes();
											Map<String, Object> newMap3User = new LinkedHashMap<String, Object>();
											for (int m = 0; m < nodeList3User.getLength(); m++) {
												Node nodeUser = nodeList3User.item(m);
												newMap3User.put(""+nodeUser.getNodeName().toString()+"", ""+nodeUser.getFirstChild()+"");
												Node pId = node2.getFirstChild().getFirstChild();
												Node id = node3.getFirstChild().getFirstChild();
												Node name = node3.getFirstChild().getNextSibling().getFirstChild();
												newMap3User.put("id", ""+id+"");
												newMap3User.put("pId", ""+pId+"");
												newMap3User.put("name", ""+name+"");
											}
											list.add(newMap3User);
										}
									}
								}
								list.add(newMap2);
							}
						}
					}
					list.add(newMap1);
				}
			}
			list.add(newMap);
			if (list != null) {
				for (Map<String, Object> map : list) {
					if (map.containsKey("OUID") && map.containsKey("ParentOUID") && map.containsKey("OUName")) {
						String OUID = map.get("OUID").toString(); 
						String ParentOUID = map.get("ParentOUID").toString(); 
						String OUName = map.get("OUName").toString(); 
						map.put("id", OUID);
						map.put("pId", ParentOUID);
						map.put("name", OUName);
						if(OUID.equals("2")){
							map.put("open", true);
						}
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	
	

}
