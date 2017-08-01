package net.htwater.mydemo.action;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import net.htwater.mydemo.service.MydemoService;
import net.sf.json.JSONArray;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;


public class MydemoAction extends DoAction {
	
	public Responser getUsers(){
		MydemoService service = (MydemoService) ServiceFactory.getService("mydemo");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUsers(params.getParam("oucode"),Boolean.parseBoolean(params.getParam("deep")))));
		return responser;
	}
	
	public Responser getORG(){
		MydemoService service = (MydemoService) ServiceFactory.getService("mydemo");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getORG(params.getParam("oucode"),Boolean.parseBoolean(params.getParam("deep")))));
		return responser;
	}
	
	public Responser getORG2Users(){
		MydemoService service = (MydemoService) ServiceFactory.getService("mydemo");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getORG2Users(params.getParam("oucode"))));
		return responser;
	}
	
	
	
	
	

	
	
	
}
