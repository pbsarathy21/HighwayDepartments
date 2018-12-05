package com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvetResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CulvertResponse implements Serializable {

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}
}