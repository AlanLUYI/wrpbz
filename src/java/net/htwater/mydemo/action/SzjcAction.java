/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0   2014-02-17 ChenYong 
 * 
 * Copyright(c) 2014, by HTWater. All Rights Reserved.
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.SzjcService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

/**
 * 水政监察模块相关服务
 * 
 */
public class SzjcAction extends DoAction {

	/**
	 * 获取涉水监管统计信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSSJGStaticInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSSJGStaticInfo(params
				.getParam("projectType"))));
		return responser;
	}

	/**
	 * 获取涉水监管列表信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSSJGListInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSSJGListInfo(
				params.getParam("area"), params.getParam("type"),
				params.getParam("status"), params.getParam("year"))));
		return responser;
	}

	/**
	 * 搜素涉水监管列表信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser searchSSJGListInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSSJGListInfo(params
				.getParam("search"))));
		return responser;
	}

	/**
	 * 根据项目id，获取涉水监管项目详细信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSSJGDetailInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSSJGDetailInfo(params
				.getParam("projectid"))));
		return responser;
	}

	/**
	 * 获取水政巡查记录统计信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSZXCStaticInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSZXCStaticInfo()));
		return responser;
	}

	/**
	 * 获取水政巡查记录列表信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSZXCListInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSZXCListInfo(
				params.getParam("area"), params.getParam("status"),
				params.getParam("year"))));
		return responser;
	}

	/**
	 * 查找水政巡查记录
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser searchSZXCListInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSZXCListInfo(params
				.getParam("search"))));
		return responser;
	}

	/**
	 * 根据水政巡查id，获取水政巡查详细信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSZXCDetailInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSZXCDetailInfo(params
				.getParam("id"))));
		return responser;
	}
	
	

	/**
	 *  根据水政巡查项目Check_ID，获取水政巡查编号信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSZXCDetailInfoByProjectid() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSZXCDetailInfoByProjectid(params
				.getParam("projectid"))));
		return responser;
	}


	/**
	 * 获取水政巡查记录中的附件信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getSZXCAttachmentInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSZXCAttachmentInfo(
				params.getParam("projectid"), params.getParam("timeid"),
				params.getParam("cid"))));
		return responser;
	}
	
	

	/**
	 * 获取水库水位流量雨量信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Responser getResInfo() {
		SzjcService service = (SzjcService) ServiceFactory
				.getService("SzjcService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getResInfo(
				params.getParam("rescd"), params.getParam("start"),
				params.getParam("end"))));
		return responser;
	}


}
