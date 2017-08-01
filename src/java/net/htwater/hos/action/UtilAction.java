package net.htwater.hos.action;

import net.htwater.hos.service.UtilService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class UtilAction extends DoAction {
	public Responser getregiontree() {
		UtilService service = (UtilService) ServiceFactory.getService("util");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getregiontree()));
		return responser;
	}
}
