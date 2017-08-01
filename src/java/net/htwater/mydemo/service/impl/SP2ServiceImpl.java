package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.mydemo.service.SP2Service;

public class SP2ServiceImpl implements SP2Service {
	private BaseDao ShiPin = DaoFactory.getDao(DB_ZHSL);
	
	@Override
	public List<Map<String, Object>> QueryShiPinAll() {
		// TODO Auto-generated method stub
		String sql = "select * from shipin_tree where Important=1 order by [order]";
		List<Map<String, Object>> shipintreeAll = ShiPin.executeQuery(sql);
		
		return shipintreeAll;
	}
}
