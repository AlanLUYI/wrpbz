package net.htwater.mydemo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.PinyinUtil;
import net.htwater.mydemo.service.SxbgService;

public class SxbgServiceImpl implements SxbgService {
	private BaseDao SXBG = DaoFactory.getDao(DB_ZHSL);
	
	@Override
	public List<Map<String, Object>> QuerySxbgInfo(String type) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> sxbgs = null;
		String sql = "select ID as sid"
				+ " ,Title as title"
				+ " ,FileContent as content"
				+ " ,FilePublishTime as pubdate"
				+ " from nbxy.dbo.T_DailyFileInfo"
				+ " where Type =" + type
				+ " order by FilePublishTime desc";
		sxbgs = SXBG.executeQuery(sql);
		if (sxbgs != null) {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> map : sxbgs) {
				Date pubDate = (Date)map.get("pubdate");
				if (pubDate != null) {
					map.put("pubdate", sdf.format(pubDate));
				}
				String title = map.get("title").toString();
				map.put("_s", title + PinyinUtil.converterToPinYin(title));
			}
		}
		return sxbgs;
	}
}
