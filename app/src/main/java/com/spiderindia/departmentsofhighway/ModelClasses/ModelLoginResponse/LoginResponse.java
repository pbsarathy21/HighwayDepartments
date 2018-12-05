package com.spiderindia.departmentsofhighway.ModelClasses.ModelLoginResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {

	@SerializedName("result")
	private String result;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	public String getResult(){
		return result;
	}

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}
}