package com.spiderindia.departmentsofhighway.ModelClasses.ModelDivisionResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DataItem implements Serializable {

	@SerializedName("WORKFLOW_STATUS")
	private String wORKFLOWSTATUS;

	@SerializedName("CREATED_DATE")
	private Object cREATEDDATE;

	@SerializedName("CREATED_USER_ID")
	private String cREATEDUSERID;

	@SerializedName("DISTRICT_T")
	private String dISTRICTT;

	@SerializedName("DIVISION_KEY_ID")
	private String dIVISIONKEYID;

	@SerializedName("REC_MODIFIED_DATE")
	private Object rECMODIFIEDDATE;

	@SerializedName("MODIFIED_USER_ID")
	private String mODIFIEDUSERID;

	@SerializedName("PINCODE")
	private String pINCODE;

	@SerializedName("DISTRICT")
	private String dISTRICT;

	@SerializedName("REC_CREATED_DATE")
	private Object rECCREATEDDATE;

	@SerializedName("CITY")
	private String cITY;

	@SerializedName("DIVISION_NAME_T")
	private String dIVISIONNAMET;

	@SerializedName("DIVISION_ID")
	private String dIVISIONID;

	@SerializedName("CIRCLE_KEY_ID")
	private String cIRCLEKEYID;

	@SerializedName("DIVISION_NAME")
	private String dIVISIONNAME;

	@SerializedName("CLOSED_DATE")
	private Object cLOSEDDATE;

	@SerializedName("SESSION_ID")
	private String sESSIONID;

	@SerializedName("ADDRESS_1")
	private String aDDRESS1;

	@SerializedName("ADDRESS_2")
	private Object aDDRESS2;

	public void setWORKFLOWSTATUS(String wORKFLOWSTATUS){
		this.wORKFLOWSTATUS = wORKFLOWSTATUS;
	}

	public String getWORKFLOWSTATUS(){
		return wORKFLOWSTATUS;
	}

	public void setCREATEDDATE(Object cREATEDDATE){
		this.cREATEDDATE = cREATEDDATE;
	}

	public Object getCREATEDDATE(){
		return cREATEDDATE;
	}

	public void setCREATEDUSERID(String cREATEDUSERID){
		this.cREATEDUSERID = cREATEDUSERID;
	}

	public String getCREATEDUSERID(){
		return cREATEDUSERID;
	}

	public void setDISTRICTT(String dISTRICTT){
		this.dISTRICTT = dISTRICTT;
	}

	public String getDISTRICTT(){
		return dISTRICTT;
	}

	public void setDIVISIONKEYID(String dIVISIONKEYID){
		this.dIVISIONKEYID = dIVISIONKEYID;
	}

	public String getDIVISIONKEYID(){
		return dIVISIONKEYID;
	}

	public void setRECMODIFIEDDATE(Object rECMODIFIEDDATE){
		this.rECMODIFIEDDATE = rECMODIFIEDDATE;
	}

	public Object getRECMODIFIEDDATE(){
		return rECMODIFIEDDATE;
	}

	public void setMODIFIEDUSERID(String mODIFIEDUSERID){
		this.mODIFIEDUSERID = mODIFIEDUSERID;
	}

	public String getMODIFIEDUSERID(){
		return mODIFIEDUSERID;
	}

	public void setPINCODE(String pINCODE){
		this.pINCODE = pINCODE;
	}

	public String getPINCODE(){
		return pINCODE;
	}

	public void setDISTRICT(String dISTRICT){
		this.dISTRICT = dISTRICT;
	}

	public String getDISTRICT(){
		return dISTRICT;
	}

	public void setRECCREATEDDATE(Object rECCREATEDDATE){
		this.rECCREATEDDATE = rECCREATEDDATE;
	}

	public Object getRECCREATEDDATE(){
		return rECCREATEDDATE;
	}

	public void setCITY(String cITY){
		this.cITY = cITY;
	}

	public String getCITY(){
		return cITY;
	}

	public void setDIVISIONNAMET(String dIVISIONNAMET){
		this.dIVISIONNAMET = dIVISIONNAMET;
	}

	public String getDIVISIONNAMET(){
		return dIVISIONNAMET;
	}

	public void setDIVISIONID(String dIVISIONID){
		this.dIVISIONID = dIVISIONID;
	}

	public String getDIVISIONID(){
		return dIVISIONID;
	}

	public void setCIRCLEKEYID(String cIRCLEKEYID){
		this.cIRCLEKEYID = cIRCLEKEYID;
	}

	public String getCIRCLEKEYID(){
		return cIRCLEKEYID;
	}

	public void setDIVISIONNAME(String dIVISIONNAME){
		this.dIVISIONNAME = dIVISIONNAME;
	}

	public String getDIVISIONNAME(){
		return dIVISIONNAME;
	}

	public void setCLOSEDDATE(Object cLOSEDDATE){
		this.cLOSEDDATE = cLOSEDDATE;
	}

	public Object getCLOSEDDATE(){
		return cLOSEDDATE;
	}

	public void setSESSIONID(String sESSIONID){
		this.sESSIONID = sESSIONID;
	}

	public String getSESSIONID(){
		return sESSIONID;
	}

	public void setADDRESS1(String aDDRESS1){
		this.aDDRESS1 = aDDRESS1;
	}

	public String getADDRESS1(){
		return aDDRESS1;
	}

	public void setADDRESS2(Object aDDRESS2){
		this.aDDRESS2 = aDDRESS2;
	}

	public Object getADDRESS2(){
		return aDDRESS2;
	}
}