package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.ObstetricRecord;
import edu.ncsu.csc.itrust.beans.Pregnancy;
import edu.ncsu.csc.itrust.beans.forms.ObstetricRecordForm;
import edu.ncsu.csc.itrust.beans.forms.PregnanciesRecordForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ObstetricRecordActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ObstetricRecordAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
/*
	public void testGetImmunizations() throws Exception {
		action = new ObstetricRecordAction(factory, 9000000000L, "5", "1000");
		List<ProcedureBean> list = action.getImmunizations();
		assertEquals(1, list.size());
		assertEquals("90371", list.get(0).getCPTCode());

		action = new ObstetricRecordAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getImmunizations().size());

		// An ObstetricRecordAction without an ovID returns an empty list.
		action = new ObstetricRecordAction(factory, 9000000000L, "2");
		assertEquals(0, action.getImmunizations().size());
	}*/

	public void testAddAndGetSingleObstetricRecord() throws Exception {
		action = new ObstetricRecordAction(factory,"2", 9000000000L);
		assertEquals(0, action.getAllPatientObstetricRecords().size());
		
		ObstetricRecordForm hr = new ObstetricRecordForm();
		hr.setLMP("10/02/1992");
		
		String confirm = action.addObstetricRecord(2L, hr);
		assertEquals("Information Recorded", confirm);
		List<ObstetricRecord> records = action.getAllPatientObstetricRecords();
		ObstetricRecord recordOld = records.get(0);
		ObstetricRecord recordSame = action.getObstetricRecordForID(recordOld.getId());		
		assertNotNull(recordSame);
		assertEquals(recordOld.getId(),recordSame.getId());
	}
	
	public void testAddAndGetObstetricRecord() throws Exception {
		action = new ObstetricRecordAction(factory,"2", 9000000000L);
		assertEquals(0, action.getAllPatientObstetricRecords().size());
		
		ObstetricRecordForm hr = new ObstetricRecordForm();
		hr.setLMP("10/02/1992");
		
		String confirm = action.addObstetricRecord(2L, hr);
		assertEquals("Information Recorded", confirm);
		List<ObstetricRecord> records = action.getAllPatientObstetricRecords();
		assertEquals(1, records.size());
	}

	public void testAddAndGetPregnancies() throws Exception {
		action = new ObstetricRecordAction(factory,"2", 9000000000L);
		assertEquals(0, action.getAllPatientPregnancies().size());
		PregnanciesRecordForm hr = new PregnanciesRecordForm();
		hr.setDeliveryType("Miscarriage");
		hr.setNumberOfHoursInLabor(1);
		hr.setNumberOfWeeksPregnant(231);
		hr.setYearOfConception(2000);
		
		String confirm = action.addPregnancyRecord(2L, hr);
		assertEquals("Information Recorded", confirm);
		List<Pregnancy> records = action.getAllPatientPregnancies();
		assertEquals(1, records.size());
	}

}
