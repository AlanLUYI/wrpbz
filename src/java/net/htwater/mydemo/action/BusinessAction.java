package net.htwater.mydemo.action;

import java.io.IOException;

import net.htwater.mydemo.service.BusinessServic;
import net.htwater.mydemo.service.impl.AReportGateServicImpl;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
/**
 * 海塘，标准化上报接口
 * 
 * @author
 * 2016-10-05
 */
public class BusinessAction extends DoAction {
	
	public Responser getHistory() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getHistory(
				params.getParam("rescd").toString(),
				params.getParam("userid").toString(),
				params.getParam("business").toString()
			)));
		return responser;
	}
	
	public Responser getReservoirLM() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.getReservoirLM(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}
	
	public Responser getDike() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.getDike(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}
	
	public Responser Seawall() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.Seawall(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}

	public Responser getUploadFacilityCount() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.getUploadFacilityCount(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}
	
	public Responser UploadFacilityCount() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadFacilityCount(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}
	
	public Responser UploadDanger() throws IOException {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDanger(
				params.getParam("rescd").toString(),
				params.getParam("username").toString(),
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadDangerDealSituation() throws IOException {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDangerDealSituation(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadDispatchSituation() throws IOException {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDispatchSituation(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadGeographicCoordinates0() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadGeographicCoordinates0(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}
	
	public Responser getGeographicCoordinates1() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.getGeographicCoordinates1(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}

	
	public Responser UploadGeographicCoordinates1() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadGeographicCoordinates1(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}

	public Responser getGeographicCoordinates2() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.getGeographicCoordinates2(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}
	
	public Responser UploadGeographicCoordinates2() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadGeographicCoordinates2(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString()
			));
		return responser;
	}

	public Responser UploadProjectMaintenanceItem() throws IOException {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadProjectMaintenanceItem(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadProjectYearUsedFee() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadProjectYearUsedFee(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadRecentReinforcements() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadRecentReinforcements(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadDike() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDike(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}
	
	public Responser UploadDailyPatrol() {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDailyPatrol(
				params.getParam("rescd").toString(),
				"",
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}
	
	public Responser UploadDanger_other() throws IOException {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDanger_other(
				params.getParam("rescd").toString(),
					params.getParam("username").toString(),
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

	public Responser UploadDangerDealSituation_other() throws IOException {
		BusinessServic service = (BusinessServic) ServiceFactory.getService("Business");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDangerDealSituation_other(
				params.getParam("rescd").toString(),
					params.getParam("username").toString(),
				params.getParam("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}

}
