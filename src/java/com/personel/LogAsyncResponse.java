package com.personel;
/**
 * LogAsyncResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class LogAsyncResponse  implements java.io.Serializable {
    private LogAsyncResponseLogAsyncResult logAsyncResult;

    public LogAsyncResponse() {
    }

    public LogAsyncResponse(
           LogAsyncResponseLogAsyncResult logAsyncResult) {
           this.logAsyncResult = logAsyncResult;
    }


    /**
     * Gets the logAsyncResult value for this LogAsyncResponse.
     * 
     * @return logAsyncResult
     */
    public LogAsyncResponseLogAsyncResult getLogAsyncResult() {
        return logAsyncResult;
    }


    /**
     * Sets the logAsyncResult value for this LogAsyncResponse.
     * 
     * @param logAsyncResult
     */
    public void setLogAsyncResult(LogAsyncResponseLogAsyncResult logAsyncResult) {
        this.logAsyncResult = logAsyncResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogAsyncResponse)) return false;
        LogAsyncResponse other = (LogAsyncResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logAsyncResult==null && other.getLogAsyncResult()==null) || 
             (this.logAsyncResult!=null &&
              this.logAsyncResult.equals(other.getLogAsyncResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLogAsyncResult() != null) {
            _hashCode += getLogAsyncResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogAsyncResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">LogAsyncResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logAsyncResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LogAsyncResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("", ">>LogAsyncResponse>LogAsyncResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
