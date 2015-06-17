package dbQueryLibraries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Registration;
import application.Session;

public class CamperQueries {

	// Constructor
	public CamperQueries(){}
	
	// 6.1 - Add Camper and Complete Registration
	// TESTED
	public int addCamper(Connection con, String name, String address, String phone, String email) throws SQLException
	{
		Statement stmt = con.createStatement();
		String query = "INSERT INTO Camper(id, name, phone_num, address, email)" +
				" VALUES (camper_counter.nextval, '" + 
				name + "', '" +
				phone + "', '" +
				address + "', '" +
				email + "')";
		System.out.println(query);
		int rowCount = stmt.executeUpdate(query);
		
		ResultSet rs = stmt.executeQuery("SELECT camper_counter.currval"
				+ " FROM Camper");
		
		int camperID = 0;
		while(rs.next()){
			camperID = rs.getInt(1);	
		}
		System.out.println("Camper " + camperID + " added, rows updated:" + rowCount);
		
		stmt.close();
		return camperID;
	}
	
	// Adds registration to table
	// TESTED
	public int completeRegistration(Connection con, int camperID, int sessionID, String campName) throws SQLException
	{
		Statement stmt = con.createStatement();
		String query = "INSERT INTO Registration(conf_num, sid, camp_name, camper_id, is_paid)"
			+ " VALUES (registration_counter.nextval, " + 
				sessionID + ", '" +
				campName + "', " +
				camperID + ", " +
				0 + ")";
		System.out.println(query);
		int rowCount = stmt.executeUpdate(query);
		ResultSet rs = stmt.executeQuery("SELECT registration_counter.currval"
				+ " FROM Registration");
		
		int confNo = 0;
		rs.next();
		confNo = rs.getInt(1);

		System.out.println("Registration Completed, rows updated: " + rowCount);
		
		
		stmt.close();
		return confNo;
	}
	
	// Get all sessions - for creating dropdown in registration page
	// TESTED
	public ArrayList<Session> getAllSessions(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ArrayList<Session> sessions = new ArrayList<Session>();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM CampSession");
		while(rs.next()){
			Session s = new Session(rs.getString("name"), rs.getString("description"));
			System.out.println(rs.getString("name"));
			sessions.add(s);
		}
		
		System.out.println();
		stmt.close();
		return sessions;
	}

	// 6.2 - Make Payment
	// TESTED
	public void makePayment(Connection con, int confNo) throws SQLException{
		Statement stmt = con.createStatement();
		int rowCount = stmt.executeUpdate("UPDATE Registration"
				+ " SET is_paid = 1"
				+ " WHERE conf_num = " + confNo);
		
		System.out.println("Payment made, rows updated: " + rowCount);
		stmt.close();
	}
	
	// Finds all activities offered at a certain camp
	// TESTED
	public ArrayList<String> findCampActivities(Connection con, String campName) throws SQLException{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT activity_name"
				+ " FROM CampOffers"
				+ " WHERE camp_name = '" + campName + "'");
		ArrayList<String> activities = new ArrayList<String>();
		while(rs.next()){
			activities.add(rs.getString("activity_name"));
			System.out.println(rs.getString("activity_name"));
		}
		
		System.out.println("Num of activities offered: " + activities.size());
		stmt.close();
		return activities;
	}
	
	// gets all activities
	// TESTED
	public ArrayList<String> getAllActivities(Connection con) throws SQLException{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT name"
				+ " FROM Activity");
		
		ArrayList<String> activities = new ArrayList<String>();
		while(rs.next()){
			activities.add(rs.getString("name"));
			System.out.println(rs.getString("name"));
		}
		
		stmt.close();
		return activities;
	}
	
	// Given a list of activities, returns a list of camps offering all of those activities
	public ArrayList<String> findCampsOfferingActivities(Connection con, ArrayList<String> activities) throws SQLException{
		Statement stmt = con.createStatement();
		// create string of SelectedActivities
		StringBuilder selectedActivities = new StringBuilder();
		for (int i = 0; i < activities.size() ; i++) {
			selectedActivities.append("name = '" + activities.get(i) + "'");
			// if not the last element, add "or"
			if (i+1 != activities.size()){
				selectedActivities.append(" OR ");
			}
		}
		
		String query = "SELECT c1.name"
				+ " FROM Camp c1"
				+ " WHERE NOT EXISTS ((SELECT a.name"
								  + " FROM Activity a"
								  + " WHERE " + selectedActivities
								  + ") EXCEPT"
								  + " (SELECT activity_name"
								  + " FROM CampOffers c2"
								  + " WHERE c1.name = c2.camp_name))";
		System.out.println(query);
		// Query using division
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<String> camps = new ArrayList<String>();
		while(rs.next()){
			camps.add(rs.getString("camp_name"));
		}
		
		System.out.println("Number of camps offering all selected activities: " + camps.size());
		
		stmt.close();
		return camps;
	}
	
	//Returns registration object with information about registration
	// TESTED
	public Registration getRegistration(Connection con, int confNo) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Registration"
				+ " WHERE conf_num = " + confNo);
		Registration reg = new Registration();
		
		rs.next();
		
		// set registration
		reg.setConfNo(rs.getInt("conf_num"));
		reg.setCamperID(rs.getInt("camper_id"));
		reg.setCampName(rs.getString("camp_name"));
		int is_paid = rs.getInt("is_paid");
		if (is_paid == 1){
			reg.setPaid(true);
		}
		reg.setSessionID(rs.getInt("sid"));
		reg.setCounsellor(rs.getInt("counsellor_id"));
		reg.setCabin(rs.getInt("cabin_id"));
		
		System.out.println(reg.toString());
		
		stmt.close();
		
		return reg;
	}
	
	// Deletes registration - no cascade
	// TESTED
	public void cancelRegistration(Connection con, int confNo) throws SQLException{
		Statement stmt = con.createStatement();
		int rc = stmt.executeUpdate("DELETE FROM Registration "
				+ "WHERE conf_num = " + confNo);
		
		System.out.println("Registration " + confNo + " deleted, rows updated: " + rc);
		
		stmt.close();
	}
	
	// takes a confirmation number and a new session Name and changes the session
	// TESTED
	public void switchSession(Connection con, int confNo, int sessionID) throws SQLException{
		Statement stmt = con.createStatement();
		int rc = stmt.executeUpdate("UPDATE Registration"
				+ " SET sid = " + sessionID
				+ " WHERE conf_num = " + confNo);
		
		System.out.println("Session switched to " + sessionID + ", rows updated: " + rc);
		
		stmt.close();
	}
	
	
}
