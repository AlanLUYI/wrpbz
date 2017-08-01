package com.personel;
/**
 * TaskProcessResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class TaskProcessResponse  implements java.io.Serializable {
    private TaskProcessResponseTaskProcessResult taskProcessResult;

    public TaskProcessResponse() {
    }

    public TaskProcessResponse(
           TaskProcessResponseTaskProcessResult taskProcessResult) {
           this.taskProcessResult = taskProcessResult;
    }


    /**
     * Gets the taskProcessResult value for this TaskProcessResponse.
     * 
     * @return taskProcessResult
     */
    public TaskProcessResponseTaskProcessResult getTaskProcessResult() {
        return taskProcessResult;
    }


    /**
     * Sets the taskProcessResult value for this TaskProcessResponse.
     * 
     * @param taskProcessResult
     */
    public void setTaskProcessResult(TaskProcessResponseTaskProcessResult taskProcessResult) {
        this.taskProcessResult = taskProcessResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TaskProcessResponse)) return false;
        TaskProcessResponse other = (TaskProcessResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.taskProcessResult==null && other.getTaskProcessResult()==null) || 
             (this.taskProcessResult!=null &&
              this.taskProcessResult.equals(other.getTaskProcessResult())));
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
        if (getTaskProcessResult() != null) {
            _hashCode += getTaskProcessResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TaskProcessResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("", ">TaskProcessResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskProcessResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TaskProcessResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("", ">>TaskProcessResponse>TaskProcessResult"));
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
