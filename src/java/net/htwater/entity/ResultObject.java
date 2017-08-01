package net.htwater.entity;

import java.io.Serializable;

public class ResultObject implements Serializable {
	private String message;
	private Boolean success;
	private Object result;
	
	public void setMessage(String message){
		this.message=message;
	}
	
	public void setSuccess(Boolean success){
		this.success=success;
	}
	
	public void setResult(Object result){
		this.result=result;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public Boolean getSuccess(){
		return this.success;
	}
	
	public Object getResult(){
		return this.result;
	}
}
