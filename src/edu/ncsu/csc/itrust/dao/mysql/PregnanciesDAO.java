package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.Pregnancy;
import edu.ncsu.csc.itrust.beans.loaders.PregnanciesBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for all pregnancy records where a whole history is kept.
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
public class PregnanciesDAO {
	private PregnanciesBeanLoader loader = new PregnanciesBeanLoader();
	private DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public PregnanciesDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of all Pregnancies for a particular patient
	 * 
	 * @param mid The MID of the patient to look up.
	 * @return A java.util.List of Pregnancies.
	 * @throws DBException
	 */
	public List<Pregnancy> getAllPregnancies(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Pregnancy> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM pregnancies "
					+ "WHERE PatientID=?");
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
	

	/**
	 * Adds a Pregnancy for a particular patient
	 * 
	 * @param record The Pregnancy object to insert.
	 * @return A boolean indicating whether the insert was successful.
	 * @throws DBException
	 */
	public boolean add(Pregnancy record) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		int numInserted = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO pregnancies(PatientID,YearOfConception,NumberOfWeeksPregnant,NumberOfHoursInLabor,DeliveryType) VALUES(?,?,?,?,?)");
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
