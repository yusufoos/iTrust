package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ObstetricRecord;

/**
 * A loader for ObstetricRecords.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ObstetricRecordsBeanLoader implements BeanLoader<ObstetricRecord> {
	public List<ObstetricRecord> loadList(ResultSet rs) throws SQLException {
		ArrayList<ObstetricRecord> list = new ArrayList<ObstetricRecord>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, ObstetricRecord bean) throws SQLException {
		int i = 1;
		ps.setLong(i++, bean.getPatientID());
		ps.setLong(i++, bean.getPersonnelID());
		ps.setString(i++, bean.getLMPDateStr());
		ps.setDate(i++, new java.sql.Date(bean.getDateRecorded().getTime()));
		return ps;
	}

	public ObstetricRecord loadSingle(ResultSet rs) throws SQLException {
		ObstetricRecord hr = new ObstetricRecord();
		
		hr.setId(rs.getLong("id"));
		hr.setDateRecorded(rs.getDate("dateRecorded"));
		hr.setPersonnelID(rs.getLong("HCPID"));
		hr.setPatientID(rs.getLong("PatientID"));
		hr.setLMPStr((rs.getString("LMP")));
		return hr;
	}
}
