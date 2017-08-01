package com.personel;
/**
 * GetTaskSortResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class GetTaskSortResponse  implements java.io.Serializable {
    private GetTaskSortResponseGetTaskSortResult getTaskSortResult;

    public GetTaskSortResponse() {
    }

    public GetTaskSortResponse(
           GetTaskSortResponseGetTaskSortResult getTaskSortResult) {
           this.getTaskSortResult = getTaskSortResult;
    }


    /**
     * Gets the getTaskSortResult value for this GetTaskSortResponse.
     * 
     * @return getTaskSortResult
     */
    public GetTaskSortResponseGetTaskSortResult getGetTaskSortResult() {
        return getTaskSortResult;
    }


    /**
     * Sets the getTaskSortResult value for this GetTaskSortResponse.
     * 
     * @param getTaskSortResult
     */
    public void setGetTaskSortResult(GetTaskSortResponseGetTaskSortResult getTaskSortResult) {
        this.getTaskSortResult = getTaskSortResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetTaskSortResponse)) return false;
        GetTaskSortResponse other = (GetTaskSortResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getTaskSortResult==null && other.getGetTaskSortResult()==null) || 
             (this.getTaskSortResult!=null &&
              this.getTaskSortResult.equals(other.getGetTaskSortResult())));
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
        if (getGetTaskSortResult() != null) {
            _hashCode += getGetTaskSortResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetTaskSortResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">GetTaskSortResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getTaskSortResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GetTaskSortResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("", ">>GetTaskSortResponse>GetTaskSortResult"));
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
