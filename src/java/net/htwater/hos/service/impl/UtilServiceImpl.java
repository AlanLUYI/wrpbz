package net.htwater.hos.service.impl;

import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.hos.service.UtilService;

public class UtilServiceImpl implements UtilService {
	BaseDao dao = DaoFactory.getDao(DB_HOS);
	@Override
	public List<Map<String, Object>> getregiontree() {
		String sql = "select cd as id, name, pcd as pId,type, 'true' as [open] from sys_code";
		List<Map<String, Object>> plst = dao.executeQuery(sql);
		return plst;
	}

}
