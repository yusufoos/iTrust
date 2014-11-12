package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.Pregnancy;

/**
 * A loader for Pregnancies.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class PregnanciesBeanLoader implements BeanLoader<Pregnancy> {
	public List<Pregnancy> loadList(ResultSet rs) throws SQLException {
		ArrayList<Pregnancy> list = new ArrayList<Pregnancy>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
/**
 * private long yearOfConception;
	private long numberOfWeeksPregnant; // # weeks [0-42], #days [0-6]: 3digit number or 2digit number, last digit is number of days
	private double numberOfHoursInLabor; //Optional
	private String deliveryType;
 */
	public PreparedStatement loadParameters(PreparedStatement ps, Pregnancy bean) throws SQLException {
		int i = 1;
		ps.setLong(i++, bean.getPatientID());
		ps.setLong(i++, bean.getYearOfConception());
		ps.setLong(i++, bean.getNumberOfWeeksPregnant());
		ps.setDouble(i++, bean.getNumberOfHoursInLabor());
		ps.setString(i++, bean.getDeliveryType());
		return ps;
	}

	public Pregnancy loadSingle(ResultSet rs) throws SQLException {
		Pregnancy hr = new Pregnancy();
		hr.setPatientID(rs.getLong("PatientID"));
		hr.setYearOfConception(rs.getLong("YearOfConception"));
		hr.setNumberOfWeeksPregnant(rs.getLong("NumberOfWeeksPregnant"));
		hr.setNumberOfHoursInLabor(rs.getDouble("NumberOfHoursInLabor"));
		hr.setDeliveryType(rs.getString("DeliveryType"));

		return hr;
	}
}
