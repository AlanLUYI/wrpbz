package com.personel;
/**
 * RoleAddResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class RoleAddResponse  implements java.io.Serializable {
    private java.lang.String roleAddResult;

    public RoleAddResponse() {
    }

    public RoleAddResponse(
           java.lang.String roleAddResult) {
           this.roleAddResult = roleAddResult;
    }


    /**
     * Gets the roleAddResult value for this RoleAddResponse.
     * 
     * @return roleAddResult
     */
    public java.lang.String getRoleAddResult() {
        return roleAddResult;
    }


    /**
     * Sets the roleAddResult value for this RoleAddResponse.
     * 
     * @param roleAddResult
     */
    public void setRoleAddResult(java.lang.String roleAddResult) {
        this.roleAddResult = roleAddResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoleAddResponse)) return false;
        RoleAddResponse other = (RoleAddResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.roleAddResult==null && other.getRoleAddResult()==null) || 
             (this.roleAddResult!=null &&
              this.roleAddResult.equals(other.getRoleAddResult())));
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
        if (getRoleAddResult() != null) {
            _hashCode += getRoleAddResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RoleAddResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">RoleAddResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleAddResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RoleAddResult"));
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
