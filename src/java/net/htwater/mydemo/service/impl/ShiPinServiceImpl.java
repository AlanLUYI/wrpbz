package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.mydemo.service.ShiPinService;

public class ShiPinServiceImpl implements ShiPinService {
	private BaseDao ShiPin = DaoFactory.getDao(DB_ZHSL);
	
	@Override
	public List<Map<String, Object>> QueryShiPin() {
		// TODO Auto-generated method stub
		String sql = "select * from shipin where Important=1";
		List<Map<String, Object>> shipintree = ShiPin.executeQuery(sql);
		
		return shipintree;
	}

	@Override
	public List<Map<String, Object>> QueryShiPinAll() {
		// TODO Auto-generated method stub
		String sql = "select * from shipin_tree where Important=1 order by [order]";
		List<Map<String, Object>> shipintreeAll = ShiPin.executeQuery(sql);
		
		return shipintreeAll;
	}

	@Override
	public List<Map<String, Object>> QueryCameraXML() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> CameraXMLdata=new ArrayList<Map<String,Object>>();
		Map<String, Object> xml=new HashMap<String, Object>();
		xml.put("CameraXML", "http://61.153.21.221:8081/htos/GetAllProjectXMLListService!base");
		xml.put("CameraXMLzgz", "http://115.238.153.42:8081/htos/GetAllXMLListService!base?callback=?");
		//xml.put("CameraXML", "http://61.153.21.221:8081/htos/GetAllProjectXMLListService!base?callback=?");
		//xml.put("CameraXMLzgz", "http://115.238.153.42:8081/htos/GetAllXMLListService!base?&callback=?");
		CameraXMLdata.add(xml);
		return CameraXMLdata;
	}

}
