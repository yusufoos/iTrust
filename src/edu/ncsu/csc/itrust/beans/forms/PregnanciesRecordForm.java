package edu.ncsu.csc.itrust.beans.forms;

/**
 * A form to contain data coming from editing a Pregnancies record.
 * 
 * A form is a bean, kinda. You could say that it's a form of a bean :) 
 * Think of a form as a real-life administrative form that you would fill out to get 
 * something done, not necessarily making sense by itself.
 */
public class PregnanciesRecordForm {
	private long yearOfConception = 0;
	private long numberOfWeeksPregnant = 0; // # weeks [0-42], #days [0-6]: 3digit number or 2digit number, last digit is number of days
	private double numberOfHoursInLabor = 0; //Optional
	private String deliveryType = "";

	public PregnanciesRecordForm() {
	}

	public long getYearOfConception() {
		return yearOfConception;
	}

	public void setYearOfConception(long yearOfConception) {
		this.yearOfConception = yearOfConception;
	}

	public long getNumberOfWeeksPregnant() {
		return numberOfWeeksPregnant;
	}

	public void setNumberOfWeeksPregnant(long numberOfWeeksPregnant) {
		this.numberOfWeeksPregnant = numberOfWeeksPregnant;
	}

	public double getNumberOfHoursInLabor() {
		return numberOfHoursInLabor;
	}

	public void setNumberOfHoursInLabor(double numberOfHoursInLabor) {
		this.numberOfHoursInLabor = numberOfHoursInLabor;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	

	
}
