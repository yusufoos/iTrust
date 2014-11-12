package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ObstetricRecord;
import edu.ncsu.csc.itrust.beans.loaders.ObstetricRecordsBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for all obstetric records where a whole history is kept.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 *  
 * 
 */
public class ObstetricRecordsDAO {
	private ObstetricRecordsBeanLoader loader = new ObstetricRecordsBeanLoader();
	private DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public ObstetricRecordsDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of all health records for a particular patient
	 * 
	 * @param mid The MID of the patient to look up.
	 * @return A java.util.List of ObstetricRecords.
	 * @throws DBException
	 */
	public List<ObstetricRecord> getAllObstetricRecords(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<ObstetricRecord> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetricrecords "
					+ "WHERE PatientID=? ORDER BY dateRecorded DESC");
			ps.setLong(1, mid);
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
	}
	
	public ObstetricRecord getObstetricRecordForID(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ObstetricRecord record;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM obstetricrecords "
					+ "WHERE id=?");
			ps.setLong(1, id);
			ResultSet rs;
			rs = ps.executeQuery();
			rs.next();
			record = loader.loadSingle(rs);
			rs.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return record;
	}
	

	/**
	 * Adds a health record for a particular patient
	 * 
	 * @param record The ObstetricRecord object to insert.
	 * @return A boolean indicating whether the insert was successful.
	 * @throws DBException
	 */
	public boolean add(ObstetricRecord record) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		int numInserted = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO obstetricrecords(PatientID,HCPID,LMP,dateRecorded) VALUES(?,?,?,?)");
			loader.loadParameters(ps, record);
			numInserted = ps.executeUpdate();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return (numInserted == 1);
	}
	
	
}
