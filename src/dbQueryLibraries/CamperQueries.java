package dbQueryLibraries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Camper;
import application.Session;

public class CamperQueries {

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
	

	public void completeRegistration(Connection con, int camperID, Session session, String campName) throws SQLException
	{
		Statement stmt = con.createStatement();
		int rowCount = stmt.executeUpdate(
			"INSERT INTO Registration(confNo, sessionID, campName, camperID, isPaid)"
			+ " VALUES (registration_counter.nextval" + 
				session.getSessionID() +
				campName +
				camperID + 
				false
				);
			
			System.out.println("Registration Completed, rows updated: " + rowCount);
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
	
	public void findActivitiesByCamp(Connection con, ArrayList<String> activityName) throws SQLException{
		Statement stmt = con.createStatement();
		// TODO
	}
	
	public void findSession(Connection con, Date startDate) throws SQLException{
		Statement stmt = con.createStatement();
		// TODO
	}
	
	public void cancelRegistration(Connection con, int confNo) throws SQLException{
		Statement stmt = con.createStatement();
		// TODO
	}
	
	public void switchSession(Connection con, int confNo, Session newSession) throws SQLException{
		Statement stmt = con.createStatement();
		// TODO
	}
	
	
}