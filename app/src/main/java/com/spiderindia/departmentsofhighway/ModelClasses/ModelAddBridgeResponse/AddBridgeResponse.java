package com.spiderindia.departmentsofhighway.ModelClasses.ModelAddBridgeResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class AddBridgeResponse implements Serializable {

	@SerializedName("result")
	private String result;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}