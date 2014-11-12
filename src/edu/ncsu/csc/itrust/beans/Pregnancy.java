package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A bean for storing Pregnancy record data.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class Pregnancy {
	private long patientID = 0;
	private long yearOfConception;
	private long numberOfWeeksPregnant; // # weeks [0-42], #days [0-6]: 3digit number or 2digit number, last digit is number of days
	private double numberOfHoursInLabor; //Optional
	private String deliveryType;
	
	public Pregnancy() {
	}
			
	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
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
