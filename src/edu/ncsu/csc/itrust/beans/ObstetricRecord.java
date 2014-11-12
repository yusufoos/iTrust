package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A bean for storing Obstetrics record data.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class ObstetricRecord {
	private long id = 0;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private long patientID = 0;
	private long personnelID = 0;
	private String LMP = "00/00/0000";
	private Date dateRecorded = new Date();

	public ObstetricRecord() {
	}
		
	
	//==================================
	
	public void setLMPStr(String LMP){
		this.LMP = LMP;
	}
	
	public Date getLMPDate() {
		Date d = null; 
		try {
			d = new SimpleDateFormat("MM/dd/yyyy").parse(LMP);
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		return d;
	}
	
	public String getLMPDateStr() {
		return LMP;
	}
	
	
	//Unparseable date exception
	public int getWeeksOfPregnancy() {
		//DateTime dt1 = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		//DateTime dt2 = new DateTime(2010, 1, 1, 0, 0, 0, 0);
		
		SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");

		long d1 = 0;
		long d2 = 0;
		int days = 0;
		try{
			d1=formater.parse(LMP).getTime();
			d2=new Date().getTime();
			days = (int) Math.abs((d2-d1)/(1000*60*60*24));
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		return (days/7)*10 + (days%7);
	}
	
	//==================================

	/**
	 * Calculates estimated due date after Last Menstrual Period
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public String getEDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try{
			c.setTime(sdf.parse(LMP));
		} catch (ParseException e){
			System.out.println(e.toString());
		}
		c.add(Calendar.DATE, 280);  // number of days to add
		return sdf.format(c.getTime()); // c.getTime() is now the new date
	}

	public Date getDateRecorded() {
		return dateRecorded;
	}


	public void setDateRecorded(Date dateRecorded) {
		this.dateRecorded = dateRecorded;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public long getPersonnelID() {
		return personnelID;
	}

	public void setPersonnelID(long personnelID) {
		this.personnelID = personnelID;
	}

}
