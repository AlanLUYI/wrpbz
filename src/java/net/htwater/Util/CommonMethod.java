
package net.htwater.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

/**
 * @author yangshengfei
 * @createTime 2015年1月30日
 * @updateTime 2015年1月30日
 * @描述:公共方法
 */
public class CommonMethod {
	
	private static final Logger log=Logger.getLogger(CommonMethod.class.getName());   
	
	
	/**
	 * 
	 * 描述:数据上报处理日志存储方法
	 * @createTime 2015年1月29日
	 * @updateTime 2015年1月30日
	 * @return
	 */
	public synchronized void ProcLogs(String userName,String counties,String dataType,String fileName,
			String status,String[] item){
		BaseDao dao = DaoFactory.getDao("HOS");
		/*String instSQL="insert into DataReportLogs(counties,dataType,"
				+ "fileName,procStatus,reportDatetime,procInfo) values(?,?,?,?,?,?)";*/
		String nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		
		String info = "";
		if (item!=null) {
			for(String s:item){
				info+=s;
			}
		}else {
			info="";
		}
		
		try {
			/*dao.executeSQL(instSQL, new Object[]{counties,dataType,fileName,
					status,nowTime,info});*/
			dao.executeSQL("{call DataReportProcLogs(?,?,?,?,?,?,?)}", new Object[]{userName,counties,dataType,fileName,
					status,nowTime,info});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
	}
	
	
}
