package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.ObstetricRecord;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.ObstetricRecord;
import edu.ncsu.csc.itrust.beans.Pregnancy;
import edu.ncsu.csc.itrust.beans.forms.HealthRecordForm;
import edu.ncsu.csc.itrust.beans.forms.ObstetricRecordForm;
import edu.ncsu.csc.itrust.beans.forms.PregnanciesRecordForm;
import edu.ncsu.csc.itrust.validate.ObstetricRecordFormValidator;
import edu.ncsu.csc.itrust.validate.PregnanciesRecordFormValidator;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.NormalDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PregnanciesDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ObstetricRecordsAction is an action class used for viewing or creating a patient's obstetric records.
 * Allows an hcp to view or create a patient's obstetric records
 */
@SuppressWarnings("unused")
public class ObstetricRecordAction {
	
	private PatientDAO patientDAO;
	private ObstetricRecordsDAO hrDAO;
	private PregnanciesDAO pregnancyDAO;
	private AuthDAO authDAO;

	private long patientID = 0;
	
	private Role user;
	
	private long loggedInMID;
	
	private EventLoggingAction loggingAction;
	
	private ObstetricRecordFormValidator validator = new ObstetricRecordFormValidator();
	private PregnanciesRecordFormValidator pregnancyValidator = new PregnanciesRecordFormValidator();

	/**
	 * Constructor for ViewObstetricRecordsHistoryAction. Initializes all DAO field objects with the DAOFactory that is passed
	 * in. Also sets the pid of the patient whose records are to be viewed and saves the logged in user's mid for logging.
	 * @param factory the DAOFactory to have database interactions with
	 * @param pidString the String representing the patient's mid
	 * @param loggedInMID long for the logged in user's mid
	 * @throws ITrustException
	 */
	public ObstetricRecordAction(DAOFactory factory, String pidString, long loggedInMID) throws ITrustException{
		init(factory);
		this.patientID = Long.parseLong(pidString);
		this.user = authDAO.getUserRole(loggedInMID);
		this.loggedInMID = loggedInMID;
			
	}
	
	/**
	 * Initializes the DAO fields by getting them from the DAOFactory passed in from the constructor
	 * @param factory DAOFactory to initialize the DAO fields with
	 */
	private void init(DAOFactory factory){
		authDAO = factory.getAuthDAO();
		hrDAO = factory.getObstetricRecordsDAO();
		patientDAO = factory.getPatientDAO();
		pregnancyDAO = factory.getPregnancyDAO();
		
		loggingAction = new EventLoggingAction(factory);		
	}
	
	/**
	 * Gets the patient's MID as a long
	 * @return the patient's mid
	 */
	public long getPatientID(){
		return patientID;
	}
	
	/**
	 * Get the patient's name as a String
	 * @return the patient's name
	 * @throws ITrustException
	 */
	public String getPatientName() throws ITrustException{
		return patientDAO.getName(patientID);
	}
	
	/**
	 * Gets all of a patient's obstetric records. Returns an ordered list by desc date
	 * @return list of health records ordered by oldest to most recent
	 * @throws ITrustException
	 */
	public List<ObstetricRecord> getAllPatientObstetricRecords() throws ITrustException{		
		List<ObstetricRecord> allRecords = hrDAO.getAllObstetricRecords(patientID);
		return allRecords;		
	}
	
	public ObstetricRecord getObstetricRecordForID(long id) throws ITrustException{
		
		ObstetricRecord record = hrDAO.getObstetricRecordForID(id);

		if(user.getUserRolesString().equals("hcp")){
			//Log for an HCP
			loggingAction.logEvent(TransactionType.HCP_VIEW_OBSTETRIC_RECORDS, loggedInMID, patientID, record.getEDD());
			
		}	
		
		
		return record;		
	}
	
	/**
	 * Gets all of a patient's pregnancies Returns an ordered list by desc date
	 * @return list of health records ordered by oldest to most recent
	 * @throws ITrustException
	 */
	public List<Pregnancy> getAllPatientPregnancies() throws ITrustException{
		List<Pregnancy> allRecords = pregnancyDAO.getAllPregnancies(patientID);
		return allRecords;		
	}
	
	/**
	 * Adds a pregnancy record for the given patient
	 * 
	 * @param pid  The patient record who is being edited.
	 * @param hr  The filled out pregnancy record form to be added.
	 * @return message - "Information Recorded" or exception's message
	 * @throws FormValidationException
	 */
	public String addPregnancyRecord(long pid, PregnanciesRecordForm pr) throws FormValidationException,
			ITrustException {	
		
		Pregnancy record;
		
		pregnancyValidator.validate(pr);
		record = transferPregnancyForm(pid, pr);
		
		pregnancyDAO.add(record);
		
		return "Information Recorded";
	}
	
	private Pregnancy transferPregnancyForm(long pid, PregnanciesRecordForm pr) {
		Pregnancy record = new Pregnancy();
		record.setPatientID(pid);
		record.setYearOfConception(pr.getYearOfConception());
		record.setNumberOfWeeksPregnant(pr.getNumberOfWeeksPregnant());
		record.setNumberOfHoursInLabor(pr.getNumberOfHoursInLabor());
		record.setDeliveryType(pr.getDeliveryType());
		
		return record;
	}

	/**
	 * Adds a health record for the given patient
	 * 
	 * @param pid  The patient record who is being edited.
	 * @param hr  The filled out health record form to be added.
	 * @return message - "Information Recorded" or exception's message
	 * @throws FormValidationException
	 */
	public String addObstetricRecord(long pid, ObstetricRecordForm hr) throws FormValidationException,
			ITrustException {	
		
		ObstetricRecord record;
		
		validator.validate(hr);
		record = transferForm(pid, hr);
		
		loggingAction.logEvent(TransactionType.HCP_CREATE_OBSTETRIC_RECORD, loggedInMID, pid, record.getEDD());

		hrDAO.add(record);
		
		return "Information Recorded";
	}

	private ObstetricRecord transferForm(long pid, ObstetricRecordForm hr) {
		ObstetricRecord record = new ObstetricRecord();
		record.setPatientID(pid);
		record.setPersonnelID(loggedInMID);
		record.setLMPStr(hr.getLMP());
		record.setDateRecorded(new Date());
		
		return record;
	}
}
