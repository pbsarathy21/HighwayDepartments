package com.spiderindia.departmentsofhighway.ModelClasses.ModelLinkResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LinkResponse implements Serializable {

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}
}