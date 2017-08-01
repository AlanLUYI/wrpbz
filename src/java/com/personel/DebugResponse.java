package com.personel;
/**
 * DebugResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class DebugResponse  implements java.io.Serializable {
    private java.lang.String debugResult;

    public DebugResponse() {
    }

    public DebugResponse(
           java.lang.String debugResult) {
           this.debugResult = debugResult;
    }


    /**
     * Gets the debugResult value for this DebugResponse.
     * 
     * @return debugResult
     */
    public java.lang.String getDebugResult() {
        return debugResult;
    }


    /**
     * Sets the debugResult value for this DebugResponse.
     * 
     * @param debugResult
     */
    public void setDebugResult(java.lang.String debugResult) {
        this.debugResult = debugResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DebugResponse)) return false;
        DebugResponse other = (DebugResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.debugResult==null && other.getDebugResult()==null) || 
             (this.debugResult!=null &&
              this.debugResult.equals(other.getDebugResult())));
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
        if (getDebugResult() != null) {
            _hashCode += getDebugResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DebugResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">debugResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debugResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debugResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
