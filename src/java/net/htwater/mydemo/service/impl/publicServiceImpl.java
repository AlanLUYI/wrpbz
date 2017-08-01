package net.htwater.mydemo.service.impl;

import net.htwater.mydemo.service.publicService;
import cn.miao.framework.util.HttpUtil;
//import net.htwater.cms.util.HttpUtil;

public class publicServiceImpl implements publicService {


	public String gettq() {
		String weather = HttpUtil
				.getResponseFromWebByGET("http://113.108.239.118/data/101210401.html");
		return weather;
	}
	
}
