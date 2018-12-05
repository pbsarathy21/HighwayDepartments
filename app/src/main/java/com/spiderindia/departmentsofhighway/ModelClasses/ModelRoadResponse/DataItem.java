package com.spiderindia.departmentsofhighway.ModelClasses.ModelRoadResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable {

	@SerializedName("NAME")
	private String nAME;

	@SerializedName("END_PLACE_T")
	private String eNDPLACET;

	@SerializedName("ROAD_NAME_T")
	private Object rOADNAMET;

	@SerializedName("WORKFLOW_STATUS")
	private String wORKFLOWSTATUS;

	@SerializedName("CREATED_DATE")
	private Object cREATEDDATE;

	@SerializedName("CREATED_USER_ID")
	private Object cREATEDUSERID;

	@SerializedName("ROAD_KEY_ID")
	private String rOADKEYID;

	@SerializedName("START_PLACE_T")
	private String sTARTPLACET;

	@SerializedName("REC_MODIFIED_DATE")
	private Object rECMODIFIEDDATE;

	@SerializedName("ROAD_NAME")
	private Object rOADNAME;

	@SerializedName("MODIFIED_USER_ID")
	private Object mODIFIEDUSERID;

	@SerializedName("ROAD_CLASS_ID")
	private String rOADCLASSID;

	@SerializedName("END_KM")
	private String eNDKM;

	@SerializedName("ROAD_INT")
	private String rOADINT;

	@SerializedName("REC_CREATED_DATE")
	private Object rECCREATEDDATE;

	@SerializedName("START_PLACE")
	private String sTARTPLACE;

	@SerializedName("CLOSED_DATE")
	private Object cLOSEDDATE;

	@SerializedName("START_KM")
	private String sTARTKM;

	@SerializedName("SESSION_ID")
	private String sESSIONID;

	@SerializedName("ROAD_NUMBER")
	private String rOADNUMBER;

	@SerializedName("END_PLACE")
	private String eNDPLACE;

	public void setENDPLACET(String eNDPLACET){
		this.eNDPLACET = eNDPLACET;
	}

	public String getENDPLACET(){
		return eNDPLACET;
	}

	public void setROADNAMET(Object rOADNAMET){
		this.rOADNAMET = rOADNAMET;
	}

	public Object getROADNAMET(){
		return rOADNAMET;
	}

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

	public void setCREATEDUSERID(Object cREATEDUSERID){
		this.cREATEDUSERID = cREATEDUSERID;
	}

	public Object getCREATEDUSERID(){
		return cREATEDUSERID;
	}

	public void setROADKEYID(String rOADKEYID){
		this.rOADKEYID = rOADKEYID;
	}

	public String getROADKEYID(){
		return rOADKEYID;
	}

	public void setSTARTPLACET(String sTARTPLACET){
		this.sTARTPLACET = sTARTPLACET;
	}

	public String getSTARTPLACET(){
		return sTARTPLACET;
	}

	public void setRECMODIFIEDDATE(Object rECMODIFIEDDATE){
		this.rECMODIFIEDDATE = rECMODIFIEDDATE;
	}

	public Object getRECMODIFIEDDATE(){
		return rECMODIFIEDDATE;
	}

	public void setROADNAME(Object rOADNAME){
		this.rOADNAME = rOADNAME;
	}

	public Object getROADNAME(){
		return rOADNAME;
	}

	public void setMODIFIEDUSERID(Object mODIFIEDUSERID){
		this.mODIFIEDUSERID = mODIFIEDUSERID;
	}

	public Object getMODIFIEDUSERID(){
		return mODIFIEDUSERID;
	}

	public void setROADCLASSID(String rOADCLASSID){
		this.rOADCLASSID = rOADCLASSID;
	}

	public String getROADCLASSID(){
		return rOADCLASSID;
	}

	public void setENDKM(String eNDKM){
		this.eNDKM = eNDKM;
	}

	public String getENDKM(){
		return eNDKM;
	}

	public void setROADINT(String rOADINT){
		this.rOADINT = rOADINT;
	}

	public String getROADINT(){
		return rOADINT;
	}

	public void setRECCREATEDDATE(Object rECCREATEDDATE){
		this.rECCREATEDDATE = rECCREATEDDATE;
	}

	public Object getRECCREATEDDATE(){
		return rECCREATEDDATE;
	}

	public void setSTARTPLACE(String sTARTPLACE){
		this.sTARTPLACE = sTARTPLACE;
	}

	public String getSTARTPLACE(){
		return sTARTPLACE;
	}

	public void setCLOSEDDATE(Object cLOSEDDATE){
		this.cLOSEDDATE = cLOSEDDATE;
	}

	public Object getCLOSEDDATE(){
		return cLOSEDDATE;
	}

	public void setSTARTKM(String sTARTKM){
		this.sTARTKM = sTARTKM;
	}

	public String getSTARTKM(){
		return sTARTKM;
	}

	public void setSESSIONID(String sESSIONID){
		this.sESSIONID = sESSIONID;
	}

	public String getSESSIONID(){
		return sESSIONID;
	}

	public void setROADNUMBER(String rOADNUMBER){
		this.rOADNUMBER = rOADNUMBER;
	}

	public String getROADNUMBER(){
		return rOADNUMBER;
	}

	public void setENDPLACE(String eNDPLACE){
		this.eNDPLACE = eNDPLACE;
	}

	public String getENDPLACE(){
		return eNDPLACE;
	}

	public String getnAME() {
		return nAME;
	}

	public void setnAME(String nAME) {
		this.nAME = nAME;
	}
}