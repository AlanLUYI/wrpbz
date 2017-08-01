package com.personel;
/**
 * RoleAdd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class RoleAdd  implements java.io.Serializable {
    private java.lang.String auth;

    private java.lang.String oucode;

    private java.lang.String rolename;

    private java.lang.String members;

    public RoleAdd() {
    }

    public RoleAdd(
           java.lang.String auth,
           java.lang.String oucode,
           java.lang.String rolename,
           java.lang.String members) {
           this.auth = auth;
           this.oucode = oucode;
           this.rolename = rolename;
           this.members = members;
    }


    /**
     * Gets the auth value for this RoleAdd.
     * 
     * @return auth
     */
    public java.lang.String getAuth() {
        return auth;
    }


    /**
     * Sets the auth value for this RoleAdd.
     * 
     * @param auth
     */
    public void setAuth(java.lang.String auth) {
        this.auth = auth;
    }


    /**
     * Gets the oucode value for this RoleAdd.
     * 
     * @return oucode
     */
    public java.lang.String getOucode() {
        return oucode;
    }


    /**
     * Sets the oucode value for this RoleAdd.
     * 
     * @param oucode
     */
    public void setOucode(java.lang.String oucode) {
        this.oucode = oucode;
    }


    /**
     * Gets the rolename value for this RoleAdd.
     * 
     * @return rolename
     */
    public java.lang.String getRolename() {
        return rolename;
    }


    /**
     * Sets the rolename value for this RoleAdd.
     * 
     * @param rolename
     */
    public void setRolename(java.lang.String rolename) {
        this.rolename = rolename;
    }


    /**
     * Gets the members value for this RoleAdd.
     * 
     * @return members
     */
    public java.lang.String getMembers() {
        return members;
    }


    /**
     * Sets the members value for this RoleAdd.
     * 
     * @param members
     */
    public void setMembers(java.lang.String members) {
        this.members = members;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoleAdd)) return false;
        RoleAdd other = (RoleAdd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.auth==null && other.getAuth()==null) || 
             (this.auth!=null &&
              this.auth.equals(other.getAuth()))) &&
            ((this.oucode==null && other.getOucode()==null) || 
             (this.oucode!=null &&
              this.oucode.equals(other.getOucode()))) &&
            ((this.rolename==null && other.getRolename()==null) || 
             (this.rolename!=null &&
              this.rolename.equals(other.getRolename()))) &&
            ((this.members==null && other.getMembers()==null) || 
             (this.members!=null &&
              this.members.equals(other.getMembers())));
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
        if (getAuth() != null) {
            _hashCode += getAuth().hashCode();
        }
        if (getOucode() != null) {
            _hashCode += getOucode().hashCode();
        }
        if (getRolename() != null) {
            _hashCode += getRolename().hashCode();
        }
        if (getMembers() != null) {
            _hashCode += getMembers().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RoleAdd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">RoleAdd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "auth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oucode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oucode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rolename");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rolename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("members");
        elemField.setXmlName(new javax.xml.namespace.QName("", "members"));
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
