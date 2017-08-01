package com.personel;
/**
 * BPMAPI.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface BPMAPI extends javax.xml.rpc.Service {
    public java.lang.String getBPMAPISoapAddress();

    public BPMAPISoap_PortType getBPMAPISoap() throws javax.xml.rpc.ServiceException;

    public BPMAPISoap_PortType getBPMAPISoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
