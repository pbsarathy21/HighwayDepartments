package com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvertEditResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CulvetEditResponse implements Serializable {

	@SerializedName("result")
	private String result;

	@SerializedName("data")
	private boolean data;

	@SerializedName("message")
	private String message;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setData(boolean data){
		this.data = data;
	}

	public boolean isData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}