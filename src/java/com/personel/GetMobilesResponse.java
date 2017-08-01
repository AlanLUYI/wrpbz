package com.personel;
/**
 * GetMobilesResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class GetMobilesResponse  implements java.io.Serializable {
    private GetMobilesResponseGetMobilesResult getMobilesResult;

    public GetMobilesResponse() {
    }

    public GetMobilesResponse(
           GetMobilesResponseGetMobilesResult getMobilesResult) {
           this.getMobilesResult = getMobilesResult;
    }


    /**
     * Gets the getMobilesResult value for this GetMobilesResponse.
     * 
     * @return getMobilesResult
     */
    public GetMobilesResponseGetMobilesResult getGetMobilesResult() {
        return getMobilesResult;
    }


    /**
     * Sets the getMobilesResult value for this GetMobilesResponse.
     * 
     * @param getMobilesResult
     */
    public void setGetMobilesResult(GetMobilesResponseGetMobilesResult getMobilesResult) {
        this.getMobilesResult = getMobilesResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetMobilesResponse)) return false;
        GetMobilesResponse other = (GetMobilesResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getMobilesResult==null && other.getGetMobilesResult()==null) || 
             (this.getMobilesResult!=null &&
              this.getMobilesResult.equals(other.getGetMobilesResult())));
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
        if (getGetMobilesResult() != null) {
            _hashCode += getGetMobilesResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetMobilesResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">GetMobilesResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getMobilesResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GetMobilesResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("", ">>GetMobilesResponse>GetMobilesResult"));
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
