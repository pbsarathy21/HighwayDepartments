package com.spiderindia.departmentsofhighway.ModelClasses.ModelCircleResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CircleResponse implements Serializable {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private String success;

	@SerializedName("status")
	private int status;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}