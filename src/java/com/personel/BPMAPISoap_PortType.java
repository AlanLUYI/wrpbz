package com.personel;
/**
 * BPMAPISoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface BPMAPISoap_PortType extends java.rmi.Remote {

    /**
     * 获取代办任务|account:用户账号;
     */
    public GetTaskListResponseGetTaskListResult getTaskList(java.lang.String account) throws java.rmi.RemoteException;

    /**
     * 获取分类代办任务|account:用户账号;
     */
    public GetTaskSortResponseGetTaskSortResult getTaskSort(java.lang.String account, java.lang.String sort) throws java.rmi.RemoteException;

    /**
     * 获取分类代办任务|account:用户账号;
     */
    public GetTaskInfoResponseGetTaskInfoResult getTaskInfo(java.lang.String taskid) throws java.rmi.RemoteException;

    /**
     * 获取下层组织架构|oucode:组织架构编码;deep:是否获取子层架构
     */
    public GetOUsResponseGetOUsResult getOUs(java.lang.String oucode, boolean deep) throws java.rmi.RemoteException;

    /**
     * 获取组织架构人员|oucode:组织架构编码;deep:是否获取子层用户
     */
    public GetUsersResponseGetUsersResult getUsers(java.lang.String oucode, boolean deep) throws java.rmi.RemoteException;

    /**
     * 获取数据列表|oucode:组织架构编码;
     */
    public GetDataResponseGetDataResult getData(java.lang.String oucode) throws java.rmi.RemoteException;

    /**
     * 获取数据列表|oucode:组织架构编码;
     */
    public GetMobilesResponseGetMobilesResult getMobiles(java.lang.String oucode) throws java.rmi.RemoteException;

    /**
     * 获取数据列表|oucode:组织架构编码;
     */
    public GetListResponseGetListResult getList(java.lang.String oucode) throws java.rmi.RemoteException;

    /**
     * 获取用户组织架构|profile:用户编号;
     */
    public GetUserDepartResponseGetUserDepartResult getUserDepart(java.lang.String profile) throws java.rmi.RemoteException;

    /**
     * 用户认证|account:用户编号;仅客户端调用;
     */
    public LogAsyncResponseLogAsyncResult logAsync(java.lang.String account) throws java.rmi.RemoteException;

    /**
     * 添加角色|oucode:组织架构编号;rolename:角色名称;
     */
    public java.lang.String roleAdd(java.lang.String auth, java.lang.String oucode, java.lang.String rolename, java.lang.String members) throws java.rmi.RemoteException;

    /**
     * 处理流程
     */
    public TaskProcessResponseTaskProcessResult taskProcess(java.lang.String taskdata) throws java.rmi.RemoteException;

    /**
     * 处理流程
     */
    public java.lang.String debug(java.lang.String taskdata) throws java.rmi.RemoteException;
}
