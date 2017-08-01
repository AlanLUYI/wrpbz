package com.personel;
/**
 * BPMAPILocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class BPMAPILocator extends org.apache.axis.client.Service implements BPMAPI {

    public BPMAPILocator() {
    }


    public BPMAPILocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BPMAPILocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BPMAPISoap
    private java.lang.String BPMAPISoap_address = "http://bpm.qgj.cn/api/bpmapi.asmx";

    public java.lang.String getBPMAPISoapAddress() {
        return BPMAPISoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BPMAPISoapWSDDServiceName = "BPMAPISoap";

    public java.lang.String getBPMAPISoapWSDDServiceName() {
        return BPMAPISoapWSDDServiceName;
    }

    public void setBPMAPISoapWSDDServiceName(java.lang.String name) {
        BPMAPISoapWSDDServiceName = name;
    }

    public BPMAPISoap_PortType getBPMAPISoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BPMAPISoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBPMAPISoap(endpoint);
    }

    public BPMAPISoap_PortType getBPMAPISoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            BPMAPISoap_BindingStub _stub = new BPMAPISoap_BindingStub(portAddress, this);
            _stub.setPortName(getBPMAPISoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBPMAPISoapEndpointAddress(java.lang.String address) {
        BPMAPISoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (BPMAPISoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                BPMAPISoap_BindingStub _stub = new BPMAPISoap_BindingStub(new java.net.URL(BPMAPISoap_address), this);
                _stub.setPortName(getBPMAPISoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BPMAPISoap".equals(inputPortName)) {
            return getBPMAPISoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("", "BPMAPI");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("", "BPMAPISoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BPMAPISoap".equals(portName)) {
            setBPMAPISoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
