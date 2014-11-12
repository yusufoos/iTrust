package edu.ncsu.csc.itrust.beans.forms;

/**
 * A form to contain data coming from editing a obstetric record.
 * 
 * A form is a bean, kinda. You could say that it's a form of a bean :) 
 * Think of a form as a real-life administrative form that you would fill out to get 
 * something done, not necessarily making sense by itself.
 */
public class ObstetricRecordForm {
	private String LMP = "00/00/0000";

	public ObstetricRecordForm() {
	}
	
	public String getLMP(){
		return LMP;
	}
	
	public void setLMP(String lMP) {
		LMP = lMP;
	}
	
}
