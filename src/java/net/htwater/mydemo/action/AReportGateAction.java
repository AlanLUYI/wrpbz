package net.htwater.mydemo.action;

import java.io.IOException;

import net.htwater.hrpf.stainterface.service.serviceImpl.StaInterfaceServiceImpl;
import net.htwater.mydemo.service.impl.AReportGateServicImpl;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
/**
 * 
 * 
 * @author mcg
 * 2016-10-05
 */
public class AReportGateAction extends DoAction {
	

/////1 ok
	public Responser Sluice() {
		AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
		responser.setRtType(JSON);
		responser.setRtString(service.Sluice(
				session.getAttribute("rescd").toString(),
				session.getAttribute("name").toString(),
				session.getAttribute("userid").toString()
			));
		return responser;
	}
	///2 ok
	public Responser UploadDailyPatrol() {
		AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDailyPatrol(
				session.getAttribute("rescd").toString(),
				session.getAttribute("name").toString(),
				session.getAttribute("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}
/////3 no
	public Responser UploadDanger() throws IOException {
		AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
		responser.setRtType(JSON);
		responser.setRtString(service.UploadDanger(
				session.getAttribute("rescd").toString(),
				session.getAttribute("name").toString(),
				session.getAttribute("userid").toString(),
				params.getParam("formid").toString()
			));
		return responser;
	}
	///4 no
		public Responser UploadDangerDealSituation() throws IOException {
			AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
			responser.setRtType(JSON);
			responser.setRtString(service.UploadDangerDealSituation(
					session.getAttribute("rescd").toString(),
					session.getAttribute("name").toString(),
					session.getAttribute("userid").toString(),
					params.getParam("formid").toString()
				));
			return responser;
		}
	
		
		///5_1
		public Responser UploadGeographicCoordinates() {
			AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
			String lineType="";
			responser.setRtType(JSON);
			responser.setRtString(service.UploadGeographicCoordinates(
					session.getAttribute("rescd").toString(),
					 lineType,
					session.getAttribute("name").toString(),
					session.getAttribute("userid").toString()
				));
			return responser;
		}
		
		///6
		public Responser UploadProjectMaintenanceItem() throws IOException {
			AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
			responser.setRtType(JSON);
			responser.setRtString(service.UploadProjectMaintenanceItem(
					session.getAttribute("rescd").toString(),
					session.getAttribute("name").toString(),
					session.getAttribute("userid").toString(),
					params.getParam("formid").toString()
				));
			return responser;
		}
	///7 ok
		public Responser UploadProjectYearUsedFee() {
			AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
			responser.setRtType(JSON);
			responser.setRtString(service.UploadProjectYearUsedFee(
					session.getAttribute("rescd").toString(),
					session.getAttribute("name").toString(),
					session.getAttribute("userid").toString(),
					params.getParam("formid").toString()
				));
			return responser;
		}
		public Responser UploadDanger_other() throws IOException {
			AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
			responser.setRtType(JSON);
			responser.setRtString(service.UploadDanger_other(
					session.getAttribute("rescd").toString(),
					session.getAttribute("name").toString(),
					session.getAttribute("userid").toString(),
					params.getParam("formid").toString()
				));
			return responser;
		}
		public Responser UploadDangerDealSituation_other() throws IOException {
			AReportGateServicImpl service = (AReportGateServicImpl) ServiceFactory.getService("stabusiness");
			responser.setRtType(JSON);
			responser.setRtString(service.UploadDangerDealSituation_other(
					session.getAttribute("rescd").toString(),
					session.getAttribute("name").toString(),
					session.getAttribute("userid").toString(),
					params.getParam("formid").toString()
				));
			return responser;
		}
	
	public static void main(String args[]) {
		StaInterfaceServiceImpl staImpl = new StaInterfaceServiceImpl();
		String rescd = "gc_dzxsz-330283000164";
		String userid = "1BE65960-12E2-41D8-A2CC-76520E4D2798";
		staImpl.GetAdminDivCode(rescd, userid);
	}

}
