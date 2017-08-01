package net.htwater.mydemo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;


public class LogHelper {
	
	static final String QGJ_SMP = "qgj_smp";
	/**
	 * @param args
	 * @return void
	 * @since v 1.0
	 */
	public static void main(String[] args) {
		
	}
	
	
	public void remark(String rescd, String loginname, String username, String oper, String ip, String address) {
		String sql = "insert into tb_log (rescd, loginname, username, oper, ip, address, tm) values (?,?,?,?,?,?,?)";
		BaseDao dao = DaoFactory.getDao(QGJ_SMP);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		dao.executeSQL(sql, new Object[]{rescd, loginname, username, oper, ip, address,df.format(new Date())});
	}

	/**
	 * 记录组织人事管理模块的操作
	 * @param rescd
	 * @param loginname
	 * @param username
	 * @param oper
	 * @param ip
	 * @param address
	 */
	public void remark_auth(String rescd, String loginname, String username, String oper, String ip, String address,String status) {
		String sql = "insert into tb_auth_log (rescd, loginname, username, oper, ip, address, status,tm) values (?,?,?,?,?,?,?,?)";
		BaseDao dao = DaoFactory.getDao(QGJ_SMP);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		dao.executeSQL(sql, new Object[]{rescd, loginname, username, oper, ip, address,status,df.format(new Date())});
	}
}
