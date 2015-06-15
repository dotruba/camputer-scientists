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
	public int addCamper(Connection con, String name, String address, String phone, String email) throws SQLException
	{
		Statement stmt = con.createStatement();
		int rowCount = stmt.executeUpdate(
				"INSERT INTO Camper" +
				" VALUES (camper_counter.nextval" + 
					name +
					phone +
					address + 
					email);
		
		ResultSet rs = stmt.executeQuery("SELECT camper_id"
				+ "FROM Camper"
				+ "WHERE 'camper_id' = camper_counter.currval");
		
		int camperID = rs.getInt("camper_id");		
		System.out.println("Camper added, rows updated:" + rowCount);
		
		return camperID;
	}
	

	public int completeRegistration(Connection con, int camperID, String sessionName, String campName) throws SQLException
	{
		Statement stmt = con.createStatement();
		int rowCount = stmt.executeUpdate(
			"INSERT INTO Registration(conf_num, s_name, camp_name, camper_id, is_paid)"
			+ " VALUES (registration_counter.nextval" + 
				sessionName +
				campName +
				camperID + 
				false
				);
		ResultSet rs = stmt.executeQuery("SELECT conf_num"
				+ "FROM Registration"
				+ "WHERE 'conf_num' = registration_counter.currval");
		
		int confNo= rs.getInt("camper_id");	
		System.out.println("Registration Completed, rows updated: " + rowCount);
		
		return confNo;
	}
	
	// Get all sessions - for creating dropdown in registration page
	public ArrayList<Session> getAllSessions(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ArrayList<Session> sessions = new ArrayList<Session>();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM Session");
		while(rs.next()){
			Session s = new Session(rs.getString("name"), rs.getString("description"));
			sessions.add(s);
		}
		
		return sessions;
	}

	// 6.2 - Make Payment
	public void makePayment(Connection con, int confNo) throws SQLException{
		Statement stmt = con.createStatement();
		int rowCount = stmt.executeUpdate("UPDATE Registration"
				+ "SET paid = true"
				+ "WHERE conf_no = " + confNo);
		
		System.out.println("Payment made, rows updated: " + rowCount);
	}
	
	public ArrayList<String> findCampActivities(Connection con, String campName) throws SQLException{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT activity_name"
				+ "FROM CampOffers"
				+ "WHERE camp_name = " + campName);
		ArrayList<String> activities = new ArrayList<String>();
		while(rs.next()){
			activities.add(rs.getString("activity_name"));
		}
		
		System.out.println("Num of activities offered: " + activities.size());
		return activities;
	}
	
	public ArrayList<String> getAllActivities(Connection con) throws SQLException{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT activity_name"
				+ "FROM Activity");
		
		ArrayList<String> activities = new ArrayList<String>();
		while(rs.next()){
			activities.add(rs.getString("activity_name"));
		}
		
		return activities;
	}
	
	// Given a list of activities, returns a list of camps offering all of those activities
	public ArrayList<String> findCampsOfferingActivities(Connection con, ArrayList<String> activities) throws SQLException{
		Statement stmt = con.createStatement();
		// create string of SelectedActivities
		StringBuilder selectedActivities = new StringBuilder();
		for (int i = 0; i < activities.size() ; i++) {
			selectedActivities.append("activity_name = " + activities.get(i));
			// if not the last element, add "or"
			if (i+1 != activities.size()){
				selectedActivities.append(" OR ");
			}
		}
		
		// Query using division
		ResultSet rs = stmt.executeQuery("SELECT camp_name"
				+ "FROM Camp c1"
				+ "WHERE NOT EXISTS ((SELECT activity_name"
								  + "FROM Activity a"
								  + "WHERE " + selectedActivities
								  + ") EXCEPT"
								  + "SELECT activity_name"
								  + "FROM CampOffers c2"
								  + "WHERE c1.camp_name = c2.camp_name");
		ArrayList<String> camps = new ArrayList<String>();
		while(rs.next()){
			camps.add(rs.getString("camp_name"));
		}
		
		System.out.println("Number of camps offering all selected activities: " + camps.size());
		return camps;
	}
	
	public Registration getRegistration(Connection con, int confNo) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Registration"
				+ "WHERE conf_num = " + confNo);
		Registration reg = new Registration();
		
		// set registration
		reg.setConfNo(rs.getInt("conf_num"));
		reg.setCamperID(rs.getInt("camper_id"));
		reg.setCampName(rs.getString("camp_name"));
		// TODO - figure out int to bool situation
		//reg.setIsPaid(rs.getInt("is_paid"));
		reg.setSessionID(rs.getInt("sid"));
		
		return reg;
	}
	
	// Deletes registration - no cascade
	public void cancelRegistration(Connection con, int confNo) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DELETE FROM Registration"
				+ "WHERE conf_num = " + confNo);
	}
	
	// takes a confirmation number and a new session Name and changes the session
	public void switchSession(Connection con, int confNo, String sessionName) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("UPDATE Registration"
				+ "SET session_name = " + sessionName
				+ "WHERE conf_num = " + confNo);
	}
	
	
}