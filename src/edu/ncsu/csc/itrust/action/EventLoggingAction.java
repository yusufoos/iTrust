package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.dao.mysql.*;
import edu.ncsu.csc.itrust.beans.*;

/**
 * Handles retrieving the log of record accesses for a given user Used by viewAccessLog.jsp
 * 
 * 
 */
public class EventLoggingAction {
	private TransactionDAO transDAO;

	/**
	 * Set up
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person retrieving the logs.
	 */
	public EventLoggingAction(DAOFactory factory) {
		this.transDAO = factory.getTransactionDAO();
	}

	/**
	 * Log a transaction, with all of the info. The meaning of secondaryMID and addedInfo changes depending on
	 * the transaction type.
	 * 
	 * @param type The {@link TransactionType} enum representing the type this transaction is.
	 * @param loggedInMID The MID of the user who is logged in.
	 * @param secondaryMID Typically, the MID of the user who is being acted upon.
	 * @param addedInfo A note about a subtransaction, or specifics of this transaction (for posterity).
	 * @throws DBException
	 */
	public void logEvent(TransactionType type, long loggedInMID, long secondaryMID, String addedInfo)
			throws DBException {

		PersonnelDAO pDAO = DAOFactory.getProductionInstance().getPersonnelDAO();
		PatientDAO p2DAO = DAOFactory.getProductionInstance().getPatientDAO();
		
		PersonnelBean per1Bean = pDAO.getPersonnel(loggedInMID);
		PatientBean pat1Bean = p2DAO.getPatient(loggedInMID);
		String loggedInRole = "tester";
			if (per1Bean != null)
		{
				loggedInRole = per1Bean.getRoleString();
		}
		if (pat1Bean != null)
		{
				loggedInRole = "patient";
		} 
		
		PersonnelBean per2Bean = pDAO.getPersonnel(secondaryMID);
		PatientBean pat2Bean = p2DAO.getPatient(secondaryMID);
		String secondaryRole = "tester";
			if (per2Bean != null)
		{
				secondaryRole = per2Bean.getRoleString();
		}
		if (pat2Bean != null)
		{
				secondaryRole = "patient";
		} 
		this.transDAO.logTransaction(type, loggedInMID, secondaryMID, addedInfo, loggedInRole, secondaryRole);
	}
}
