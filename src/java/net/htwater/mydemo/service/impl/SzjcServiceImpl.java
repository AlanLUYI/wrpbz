package net.htwater.mydemo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.SzjcService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class SzjcServiceImpl implements SzjcService {

	private BaseDao daoSzjc = DaoFactory.getDao(DB_SZJC);

	private Log myLog = LogFactory.getLog(SzjcServiceImpl.class);

	@Override
	public Map<String, Object> getSSJGStaticInfo(String projectType) {

		myLog.info("getSSJGStaticInfo  projectType=" + projectType);

		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "";

		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		tempList = getAreaList();

		String searchYear = Calendar.getInstance().get(Calendar.YEAR) + "%";

		myLog.info("getSSJGStaticInfo  searchYear=" + searchYear);

		for (Map<String, Object> map : tempList) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("area", map.get("Power"));
			String power = (String) map.get("Power");
			power = power + "%";

			sql = "select(select COUNT(id) from WaterInvolve where power like ? and 项目类别 = ? )as 'totalcount' ,"
					+ "(select COUNT(id) FROM WaterInvolve where 监管状态 = '正在监管' and power like ? and 项目类别 = ? )as 'doingcount' ,"
					+ "(select COUNT(id) FROM WaterInvolve where 监管状态 = '尚未监管' and power like ? and 项目类别 = ?)as 'undocount' ,"
					+ "(select COUNT(id) FROM WaterInvolve where 监管状态 = '完成监管' and power like ? and 项目类别 = ?)as 'donecount' ,"
					+ "(select COUNT(id) FROM WaterInvolve where power like ? and 项目类别 = ?  and  工期起始   like ? )as 'newcount'";
			List<Map<String, Object>> tempList2 = daoSzjc.executeQuery(sql,
					new Object[]{power, projectType, power, projectType, power, projectType, power, projectType, power, projectType, searchYear});

			if (null != tempList2 && tempList2.size() > 0) {

				dataMap.put("totalcount", tempList2.get(0).get("totalcount"));
				dataMap.put("doingcount", tempList2.get(0).get("doingcount"));
				dataMap.put("undocount", tempList2.get(0).get("undocount"));
				dataMap.put("donecount", tempList2.get(0).get("donecount"));
				dataMap.put("newcount", tempList2.get(0).get("newcount"));
			} else {

				retMap.put("error", 1);
				myLog.info("getSSJGStaticInfo error");
				return retMap;
			}

			dataList.add(dataMap);
		}

		retMap.put("error", 0);

		retMap.put("data", dataList);

		myLog.info("getSSJGStaticInfo  retMap=" + retMap);

		return retMap;
	}

	private List<Map<String, Object>> getAreaList() {
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		String sql = "select Power from sys_Unit where SPower=? or Power='市本级' order by UnitID asc";
		retList = daoSzjc.executeQuery(sql, new Object[]{"大队"});
		return retList;
	}

	@Override
	public Map<String, Object> getSSJGListInfo(String area, String type, String status, String year) {

		myLog.info("getSSJGListInfo  area=" + area + "type=" + type + "status=" + status + "year=" + year);

		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "select ChanelProg_ID, 工程名称,建设单位,施工单位,工程地点,涉及河道,备注,  经度  x , 纬度 y ,项目类别,监管状态,工期起始,power city  from WaterInvolve where ";

		if (area.equals("all")) {
			sql += "power != ?";

		} else {
			sql += "power like ?";
		}

		if (type.equals("all")) {
			sql += " and 项目类别 != ?";
		} else {
			sql += " and 项目类别 = ?";
		}

		if (status.equals("all")) {
			sql += " and 监管状态 != ?";
		} else {
			sql += " and 监管状态 = ?";
		}

		if (year.equals("all")) {
			sql += " and 工期起始!= ?";
		} else {
			sql += " and 工期起始  like ?";
		}

		sql += " order by id desc";

		String searchArea = area + '%';

		String searchYear = year + '%';

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		retList = daoSzjc.executeQuery(sql, new Object[]{searchArea, type, status, searchYear});

		retMap.put("error", 0);

		retMap.put("data", retList);

		myLog.info("getSSJGListInfo  retMap=" + retMap);

		return retMap;
	}

	@Override
	public Map<String, Object> getSSJGDetailInfo(String projectid) {

		myLog.info("getSSJGDetailInfo  projectid=" + projectid);

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		String sql = "select * from WaterInvolve where ChanelProg_ID =?";
		tempList = daoSzjc.executeQuery(sql, new Object[]{projectid});

		if (null == tempList || tempList.size() == 0) {

			retMap.put("error", 1);
			myLog.info("getSSJGDetailInfo error");
			return retMap;
		}

		myLog.info("getSSJGDetailInfo tempList=" + tempList);

		Map<String, Object> dataMap = tempList.get(0);

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("xmmc", dataMap.get("工程名称"));
		tempMap.put("jsdw", dataMap.get("建设单位"));
		String jsdwContact = "";
		String jsdwAddress = "";
		String jsdwPhone = "";

		myLog.info("getSSJGDetailInfo 1");
		if (null != dataMap.get("建设单位")) {
			String jsdwName = (String) dataMap.get("建设单位");
			if (!jsdwName.isEmpty()) {
				sql = "select Contact,Address,MobilePhone from Constructionunit where Name  =?";
				List<Map<String, Object>> tempQuery = new ArrayList<Map<String, Object>>();
				tempQuery = daoSzjc.executeQuery(sql, new Object[]{jsdwName});
				if (null != tempQuery && tempQuery.size() > 0) {

					jsdwContact = tempQuery.get(0).get("Contact").toString();
					jsdwAddress = tempQuery.get(0).get("Address").toString();
					jsdwPhone = tempQuery.get(0).get("MobilePhone").toString();
				}
			}

		}
		tempMap.put("jsdwlxr", jsdwContact);
		tempMap.put("jsdwlxdz", jsdwAddress);
		tempMap.put("jsdwlxdh", jsdwPhone);

		myLog.info("getSSJGDetailInfo 2");

		tempMap.put("sgdw", dataMap.get("施工单位"));

		String sgdwContact = "";
		String sgdwAddress = "";
		String sgdwPhone = "";
		if (null != dataMap.get("施工单位")) {
			String sgdwName = (String) dataMap.get("施工单位");
			if (!sgdwName.isEmpty()) {
				sql = "select Contact,Address,MobilePhone from Constructionunit where Name  =?";
				List<Map<String, Object>> tempQuery = new ArrayList<Map<String, Object>>();
				tempQuery = daoSzjc.executeQuery(sql, new Object[]{sgdwName});
				if (null != tempQuery && tempQuery.size() > 0) {

					sgdwContact = tempQuery.get(0).get("Contact").toString();
					sgdwAddress = tempQuery.get(0).get("Address").toString();
					sgdwPhone = tempQuery.get(0).get("MobilePhone").toString();
				}
			}

		}
		myLog.info("getSSJGDetailInfo 3");
		tempMap.put("sgdwlxr", sgdwContact);
		tempMap.put("sgdwlxdz", sgdwAddress);
		tempMap.put("sgdwlxdh", sgdwPhone);

		tempMap.put("gcdd", dataMap.get("工程地点"));
		tempMap.put("xmlb", dataMap.get("项目类别"));
		tempMap.put("sgsp", dataMap.get("施工方案审批"));
		tempMap.put("gqks", dataMap.get("工期起始"));
		tempMap.put("gqjs", dataMap.get("工期结束"));
		tempMap.put("sjsy", dataMap.get("涉及河道"));
		tempMap.put("bz", dataMap.get("备注"));
		tempMap.put("xmzt", dataMap.get("项目状态"));
		tempMap.put("xmgm", dataMap.get("项目规模"));
		tempMap.put("xmlx", dataMap.get("项目类型"));
		tempMap.put("longtitude", dataMap.get("经度"));
		tempMap.put("latitude", dataMap.get("纬度"));
		tempMap.put("fhpj", dataMap.get("防洪评价"));
		tempMap.put("ztfa", dataMap.get("主体方案"));
		tempMap.put("jgdw", dataMap.get("监管单位"));

		tempMap.put("sbfa", dataMap.get("水土保持"));

		String jgdwAddress = "";
		String jgdwPhone = "";
		if (null != dataMap.get("监管单位")) {
			String jgdwName = (String) dataMap.get("监管单位");
			if (!jgdwName.isEmpty()) {
				sql = "select Contact,Address,MobilePhone from Constructionunit where Name  =?";
				List<Map<String, Object>> tempQuery = new ArrayList<Map<String, Object>>();
				tempQuery = daoSzjc.executeQuery(sql, new Object[]{jgdwName});
				if (null != tempQuery && tempQuery.size() > 0) {

					jgdwAddress = tempQuery.get(0).get("Address").toString();
					jgdwPhone = tempQuery.get(0).get("MobilePhone").toString();
				}
			}

		}

		myLog.info("getSSJGDetailInfo 4");

		tempMap.put("jgdwlxr", dataMap.get("监管人"));
		tempMap.put("jgdwlxdz", jgdwAddress);
		tempMap.put("jgdwlxdh", jgdwPhone);

		tempMap.put("jgzt", dataMap.get("监管状态"));

		{

			sql = "select(select COUNT(id) from PatrolRecordInfor where Check_ID = ? )as 'xccs' ," + "(select COUNT(ID) FROM Rectification where ChanelProg_ID =  ? )as 'zgcs' ";

			List<Map<String, Object>> tempQuery = new ArrayList<Map<String, Object>>();

			tempQuery = daoSzjc.executeQuery(sql, new Object[]{projectid, projectid});

			if (null != tempQuery && tempQuery.size() > 0) {
				tempMap.put("xccs", tempQuery.get(0).get("xccs"));
				tempMap.put("zgcs", tempQuery.get(0).get("zgcs"));
			}
		}

		{

			sql = "select 交办单编号, 交办日期   from WaterDistribution where ChanelProg_ID = ?";
			List<Map<String, Object>> tempQuery = new ArrayList<Map<String, Object>>();
			tempQuery = daoSzjc.executeQuery(sql, new Object[]{projectid});
			if (null != tempQuery && tempQuery.size() > 0) {
				String jbqk = "交办时间：" + tempQuery.get(0).get("交办日期  ") + "交办单号：" + tempQuery.get(0).get("交办单编号");
				tempMap.put("jbqk", jbqk);
			}
		}

		{

			sql = "select 立案时间, 督办单号 from WaterIllegal where ChanelProg_ID = ?";
			List<Map<String, Object>> tempQuery = new ArrayList<Map<String, Object>>();
			tempQuery = daoSzjc.executeQuery(sql, new Object[]{projectid});
			if (null != tempQuery && tempQuery.size() > 0) {
				String dbqk = "督办时间：" + tempQuery.get(0).get("立案时间 ") + "督办单号：" + tempQuery.get(0).get("督办单号");
				tempMap.put("dbqk", dbqk);
			}
		}

		retList.add(tempMap);
		retMap.put("error", 0);
		retMap.put("data", retList);

		myLog.info("getSSJGDetailInfo retMap = " + retMap);

		return retMap;
	}

	@Override
	public Map<String, Object> getSZXCStaticInfo() {

		myLog.info("getSZXCStaticInfo");

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		tempList = getAreaList();

		String sql = "";

		Calendar calendar = Calendar.getInstance();

		int curYear = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		String strYearStart = curYear + "-01" + "-01";
		String strYearEnd = curYear + "-12" + "-31";
		String strMonthStart = curYear + "-" + month + "-01";
		Date yearStart = null;
		Date yearEnd = null;
		Date monthStart = null;

		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");

		try {
			yearStart = simple.parse(strYearStart);
			yearEnd = simple.parse(strYearEnd);
			monthStart = simple.parse(strMonthStart);
		} catch (Exception e) {

			e.printStackTrace();
		}

		Date monthEnd = new Date();

		for (Map<String, Object> map : tempList) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("area", map.get("Power"));
			String power = (String) map.get("Power");
			power = power + "%";

			sql = "select(select COUNT(id) from PatrolRecordInfor where power like ? )as 'totalcount' ,"
					+ "(select COUNT(id) FROM PatrolRecordInfor where power like ? and  时间   >= ? and 时间 <= ? )as 'yearcount' ,"
					+ "(select COUNT(id) FROM PatrolRecordInfor where power like ? and  时间   >= ? and 时间 <= ? )as 'monthcount' ";

			List<Map<String, Object>> tempList2 = daoSzjc.executeQuery(sql, new Object[]{power, power, yearStart, yearEnd, power, monthStart, monthEnd});

			if (null != tempList2 && tempList2.size() > 0) {

				dataMap.put("totalcount", tempList2.get(0).get("totalcount"));
				dataMap.put("yearcount", tempList2.get(0).get("yearcount"));
				dataMap.put("monthcount", tempList2.get(0).get("monthcount"));

			} else {

				retMap.put("error", 1);
				myLog.info("getSZXCStaticInfo error");
				return retMap;
			}

			dataList.add(dataMap);
		}

		retMap.put("error", 0);

		retMap.put("data", dataList);

		myLog.info("getSZXCStaticInfo  retMap=" + retMap);

		return retMap;

	}

	@Override
	public Map<String, Object> getSZXCListInfo(String area, String status, String year) {

		myLog.info("getSZXCListInfo  area=" + area + "status=" + status + "year=" + year);

		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "select id, Check_ID,名称  as'巡查对象',巡察编号  as '巡查编号', 涉及水域, 时间  as '巡查时间',power city,处理状态, 备注    from PatrolRecordInfor where ";

		if (area.equals("all")) {
			sql += "power != ?";

		} else {
			sql += "power like ?";
		}

		if (status.equals("all")) {
			sql += " and 处理状态   != ?";
		} else {
			sql += " and 处理状态    = ?";
		}

		sql += " and 时间   >=? and 时间<=?";

		sql += " order by id desc";

		String searchArea = area + '%';

		String startTime = "";
		String endTime = "";

		if (year.equals("all")) {
			startTime = "1981-01-01";
			endTime = "2100-01-01";
		} else {
			startTime = year + "-01-01";
			endTime = year + "-12-31";
		}

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		retList = daoSzjc.executeQuery(sql, new Object[]{searchArea, status, startTime, endTime});

		retMap.put("error", 0);

		retMap.put("data", retList);

		myLog.info("getSZXCListInfo  retMap=" + retMap);

		return retMap;
	}

	@Override
	public Map<String, Object> getSZXCDetailInfo(String id) {
		myLog.info("getSZXCDetailInfo  id=" + id);

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		String sql = "select  Check_ID as'projectid', num_ID  as 'timeid', id  as 'cid',  名称  as'xcdx', 时间  as 'xcsj',地段 as 'xcdd', 巡查情况  as 'xcqk' , 巡查处理  as 'clqk',"
				+ " 记录人  as 'jlr', 单位负责人  as 'dwfzr',  处理状态  as 'clzt', 备注  as 'bz', 巡查人员  as 'xcry', 巡察编号   as 'xcbh'," + " 涉及水域  as 'sjsy'  from PatrolRecordInfor  where id =?";

		tempList = daoSzjc.executeQuery(sql, new Object[]{id});

		if (null != tempList && tempList.size() > 0) {

			retMap.put("error", 0);
			retMap.put("data", tempList);

		} else {
			retMap.put("error", 1);
		}

		myLog.info("getSZXCDetailInfo  retMap=" + retMap);

		return retMap;
	}

	@Override
	public Map<String, Object> getSZXCDetailInfoByProjectid(String projectid) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		String sql = "select  id,Check_ID as'projectid', num_ID  as 'timeid', id  as 'cid',  名称  as'xcdx', 时间  as 'xcsj',地段 as 'xcdd', 巡查情况  as 'xcqk' , 巡查处理  as 'clqk',"
				+ " 记录人  as 'jlr', 单位负责人  as 'dwfzr',  处理状态  as 'clzt', 备注  as 'bz', 巡查人员  as 'xcry', 巡察编号   as 'xcbh',"
				+ " 涉及水域  as 'sjsy'  from PatrolRecordInfor  where Check_ID =? order by 时间  desc";

		tempList = daoSzjc.executeQuery(sql, new Object[]{projectid});

		retMap.put("error", 0);
		retMap.put("data", tempList);

		myLog.info("getSZXCDetailInfoByProjectid  retMap=" + retMap);

		return retMap;

	}

	@Override
	public Map<String, Object> getSZXCAttachmentInfo(String projectid, String timeid, String cid) {

		myLog.info("getSZXCAttachmentInfo  projectid=" + projectid + "timeid=" + timeid + "cid=" + cid);
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

		String sql = "select ENURL as 'url'" + " from Photo  where PTimeID =? and ChanelProg_ID =? and CID =? ";

		tempList = daoSzjc.executeQuery(sql, new Object[]{timeid, projectid, cid});

		retMap.put("error", 0);
		retMap.put("data", tempList);

		myLog.info("getSZXCAttachmentInfo  retMap=" + retMap);
		return retMap;
	}

	@Override
	public Map<String, Object> getSSJGListInfo(String search) {

		myLog.info("getSSJGListInfo  search=" + search);

		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "select ChanelProg_ID, 工程名称,建设单位,施工单位,工程地点,涉及河道,备注  from WaterInvolve where 工程名称 like ? or 涉及河道  like ? order by id desc";

		String searchStr = "%" + search + "%";

		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

		tempList = daoSzjc.executeQuery(sql, new Object[]{searchStr, searchStr});

		retMap.put("error", 0);
		retMap.put("data", tempList);
		myLog.info("getSSJGListInfo search  retMap=" + retMap);
		return retMap;

	}

	@Override
	public Map<String, Object> getSZXCListInfo(String search) {
		myLog.info("getSZXCListInfo  search=" + search);

		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "select id, 名称  as'巡查对象',巡察编号  as '巡查编号', 涉及水域, 时间  as '巡查时间', 备注    from PatrolRecordInfor where 名称 like ? or 巡察编号 like ? order by id desc";

		String searchStr = "%" + search + "%";

		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

		tempList = daoSzjc.executeQuery(sql, new Object[]{searchStr, searchStr});

		retMap.put("error", 0);
		retMap.put("data", tempList);
		myLog.info("getSZXCListInfo search  retMap=" + retMap);
		return retMap;
	}
	@Override
	public Map<String, Object> getResInfo(String rescd, String start, String end) {

		myLog.info("getResInfo  rescd=" + rescd + "start=" + start + "end=" + end);
		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "select CONVERT(varchar(100), time, 120) as 'TM', rainfall as'P', waterstage as 'SW', totalout as'CKLL',totalin as'RKLL' from B_FloodData_New where stcd = ? and time >=?  and time <=? order by time";
		BaseDao dao = DaoFactory.getDao("resos");
		List<Map<String, Object>> tempList = dao.executeQuery(sql, new Object[]{rescd, start, end});
		retMap.put("error", 0);
		retMap.put("data", tempList);
		myLog.info("getSZXCListInfo search  retMap=" + retMap);
		return retMap;
	}

}
