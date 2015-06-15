package dbQueryLibraries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Registration;
import application.Session;

public class CouncellorQueries {

	public CouncellorQueries(){}
	
	// given an instructor ID, return list of all students being supervised (both in cabin and assigned to 
	// instructors group)
	public ArrayList<String> checkCamperSupervision(Connection con, int instructorID) throws SQLException{
		ArrayList<String> campers = new ArrayList<String>();
		//PreparedStatement ps = con.prepareStatement("SELECT c1.name, c1.id"
		//		+ "FROM Registration r, Camper c1,  "
		//		+ "WHERE ");
		// TODO
		return null;
	}
	
	// assign a student to a cabin --> find facility that is offered, and then assign to cabin
	// with the fewest students in it. 
	public void assignCamperToCabin(Connection con, int confNo) throws SQLException {
		PreparedStatement ps = con.prepareStatement(null);
		//TODO
		
	}
	
	public void addActivity(Connection con, String actName, String description, String supplies) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO Activity"
				+ "VALUES (?, ?, ?)");
		ps.setString(1, actName);
		ps.setString(2, description);
		ps.setString(3, supplies);
		ps.executeUpdate();
		
	}
	
	// Add an entry to "campOffers" to indicate that a camp is now offering a certain activity
	public void offerActivity(Connection con, String campName, String activityName) throws SQLException {
		// TODO: Check to make sure it doesn't contain that activity already?
		// Should be checked by key.
		PreparedStatement ps = con.prepareStatement("INSERT INTO CampOffers "
				+ "VALUES (?, ?)");
		ps.setString(1, campName);
		ps.setString(2, activityName);
		ps.executeUpdate();
	}
	
	// given a camp and session, return a list of all campers registered 
	public ArrayList<String> getRegisteredCampers(Connection con, String campName, String sessionName) throws SQLException {
		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT c.id, c.name"
				+ "FROM Registration r, Camper c"
				+ "WHERE r.camper_id = c.id AND "
				+ "r.session_name = ? AND r.camp_name = ?");
		ps.setString(1, sessionName);
		ps.setString(2, campName);
		ResultSet rs = ps.executeQuery();
		ArrayList<String> campers = new ArrayList<String>();
		while(rs.next()){
			campers.add(rs.getString(1) + " - " + rs.getString(2));
		}
		return campers;	
		}
}
