package net.htwater.mydemo.service.impl;

import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.GetGQService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class GetGQServiceImpl implements GetGQService {
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL);
	BaseDao HTSQ = DaoFactory.getDao(HT_NBSQDB);
	BaseDao daoQGJ = DaoFactory.getDao(QGJ_SMP);

	@Override
	public List<Map<String, Object>> QueryGQ() {
		// TODO Auto-generated method stub
		String sql = "select * from SWINF";
		List<Map<String, Object>> GETSW = daoZHSL.executeQuery(sql);
		
		return GETSW;
	}

	@Override
	public int updateGQ(String STCD,String RTUCD,String RTUNM,String RTUCD1,String RTUNM1,String RTUCD2,String RTUNM2,String CZXS,String XZXS) {
		// TODO Auto-generated method stub
		String sql="update SWINF set RTUCD='"+RTUCD+"',"+" RTUNM='"+RTUNM+"',"+" RTUCD1='"+RTUCD1+
				"',"+" RTUNM1='"+RTUNM1+"',"+" RTUCD2='"
				+RTUCD2+"', RTUNM2='"+RTUNM2+"',CZXS='"+CZXS+"',XZXS='"+XZXS+"'  where STCD="+STCD;
		Boolean size=daoZHSL.executeSQL(sql);
		if(size)
		{
			return 1;
		}
		else     
		{
			return 0;
		} 
	}

	@Override
	public List<Map<String, Object>> getSKCHtab() {
		// TODO Auto-generated method stub
		String sql="select * from I_Res";
		List<Map<String, Object>> result=HTSQ.executeQuery(sql);
		return result;
	}

	@Override
	public int updateSKCHtab(String resID, String rateV) {
		// TODO Auto-generated method stub
		if(resID.equals("all")){
			String sql="update I_Res set rateV=?";
			Boolean size=HTSQ.executeSQL(sql, new Object[] {rateV});
			if(size)
			{
				return 1;
			}
			else     
			{
				return 0;
			} 
		}else{
			String sql="update I_Res set rateV=? where resID="+resID;
			Boolean size=HTSQ.executeSQL(sql, new Object[] {rateV});
			if(size)
			{
				return 1;
			}
			else     
			{
				return 0;
			} 
		}
	}

	@Override
	public List<Map<String, Object>> getMenuList() {
		// TODO Auto-generated method stub
		String sql="select * from SystemPZ";
		List<Map<String, Object>> result=daoQGJ.executeQuery(sql);
		return result;
	}
	
}
