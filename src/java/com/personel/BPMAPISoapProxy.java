package com.personel;
import java.rmi.RemoteException;


public class BPMAPISoapProxy implements BPMAPISoap_PortType {
  private String _endpoint = null;
  private BPMAPISoap_PortType bPMAPISoap = null;
  
  public BPMAPISoapProxy() {
    _initBPMAPISoapProxy();
  }
  
  public BPMAPISoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initBPMAPISoapProxy();
  }
  
  private void _initBPMAPISoapProxy() {
    try {
      bPMAPISoap = (new BPMAPILocator()).getBPMAPISoap();
      if (bPMAPISoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bPMAPISoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bPMAPISoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bPMAPISoap != null)
      ((javax.xml.rpc.Stub)bPMAPISoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public BPMAPISoap_PortType getBPMAPISoap() {
    if (bPMAPISoap == null)
      _initBPMAPISoapProxy();
    return bPMAPISoap;
  }

@Override
public GetTaskListResponseGetTaskListResult getTaskList(String account)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetTaskSortResponseGetTaskSortResult getTaskSort(String account,
		String sort) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetTaskInfoResponseGetTaskInfoResult getTaskInfo(String taskid)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetOUsResponseGetOUsResult getOUs(String oucode, boolean deep)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetUsersResponseGetUsersResult getUsers(String oucode, boolean deep)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetDataResponseGetDataResult getData(String oucode)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetMobilesResponseGetMobilesResult getMobiles(String oucode)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetListResponseGetListResult getList(String oucode)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public GetUserDepartResponseGetUserDepartResult getUserDepart(String profile)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public LogAsyncResponseLogAsyncResult logAsync(String account)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String roleAdd(String auth, String oucode, String rolename,
		String members) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public TaskProcessResponseTaskProcessResult taskProcess(String taskdata)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String debug(String taskdata) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}
  
  
}