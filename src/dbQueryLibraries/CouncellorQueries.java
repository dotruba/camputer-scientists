package dbQueryLibraries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CouncellorQueries {

	public CouncellorQueries(){}
	
	// given an instructor ID, return list of all students being supervised (both in cabin and assigned to 
	// instructors group)
	public ArrayList<String> checkCamperSupervision(Connection con, int instructorID) throws SQLException{
		ArrayList<String> campers = new ArrayList<String>();
		PreparedStatement ps = con.prepareStatement("SELECT c.id, c.name "
				+ "FROM Camper c, Registration r "
				+ "WHERE c.id = r.camper_id "
				+ "AND (r.counsellor_id = ? OR r.cabin_id = (SELECT cabin_id FROM Counsellor WHERE id = ?))");
		ps.setInt(1, instructorID);
		ps.setInt(2, instructorID);

		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(1) + " - " + rs.getString(2));
			campers.add(rs.getString(1) + " - " + rs.getString(2));
		}
		return campers;
	}
	
	// assign a student to a cabin --> find facility that is offered, and then assign to cabin
	// with the fewest students in it. 
	public void assignCamperToCabin(Connection con, int confNo) throws SQLException {
		
		String createTable = "CREATE VIEW cabinCount "
				+ "AS SELECT fid, id AS cabin, count(camper_id) AS num_campers "
				+ "FROM Cabin LEFT OUTER JOIN Registration "
				+ "ON Cabin.id = Registration.cabin_id "
				+ "GROUP BY id, fid";
		//PreparedStatement ps = con.prepareStatement("DROP VIEW cabinCount");
		//ps.executeUpdate();
		PreparedStatement ps = con.prepareStatement(createTable);
		System.out.println(createTable);
		ps.executeUpdate();
		
		String query = "UPDATE Registration"
				+ " SET cabin_id = (SELECT fc.cabin "
								+ "FROM cabinCount fc, Camp c1, Registration r "
								+ "WHERE r.conf_num = ? and r.camp_name = c1.name"
								+ " and c1.fid = fc.fid and fc.num_campers <= ALL "
											+ "(SELECT num_campers FROM cabinCount)) "
				+ "WHERE conf_num = ?";
		System.out.println(query);
		PreparedStatement ps2 = con.prepareStatement(query);
		ps2.setInt(1, confNo);
		ps2.setInt(2, confNo);
		int rc = ps2.executeUpdate();
		
		ps = con.prepareStatement("DROP VIEW cabinCount");
		ps.executeUpdate();
		
		ps2.close();
		ps.close();
		System.out.println("Camper assigned to cabin. Rows updated: " + rc);
		
	}
	
	// Activity added to activities table - must be done prior to a camp offering a new activity that doesn't exist
	// TESTED
	public void addActivity(Connection con, String actName, String description, String supplies) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO Activity "
				+ "VALUES (?, ?, ?)");
		ps.setString(1, actName);
		ps.setString(2, description);
		ps.setString(3, supplies);
		int rc = ps.executeUpdate();
		
		System.out.println(actName + " added to activities, rows updated: " + rc);
		
		ps.close();
		
	}
	
	// Add an entry to "campOffers" to indicate that a camp is now offering a certain activity
	// TESTED
	public void offerActivity(Connection con, String campName, String activityName) throws SQLException {
		// TODO: Check to make sure it doesn't contain that activity already?
		// Should be checked by key.
		PreparedStatement ps = con.prepareStatement("INSERT INTO CampOffers "
				+ "VALUES (?, ?)");
		ps.setString(1, campName);
		ps.setString(2, activityName);
		int rc = ps.executeUpdate();
		
		System.out.println(campName + " now offers " + activityName + ", rows updated: " + rc);
		
		ps.close();
	}
	
	// given a camp and session, return a list of all campers registered 
	// TESTED
	public ArrayList<String> getRegisteredCampers(Connection con, String campName, int sessionID) throws SQLException {
		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT c.id, c.name"
				+ " FROM Registration r, Camper c"
				+ " WHERE r.camper_id = c.id AND "
				+ "r.sid = ? AND r.camp_name = ?");
		ps.setInt(1, sessionID);
		ps.setString(2, campName);
		ResultSet rs = ps.executeQuery();
		ArrayList<String> campers = new ArrayList<String>();
		while(rs.next()){
			campers.add(rs.getString(1) + " - " + rs.getString(2));
			System.out.println(rs.getString(1) + " - " + rs.getString(2));
		}
		ps.close();
		return campers;	
	}
}
