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

	// 6.1 - Complete Registration
	public void completeRegistration(Statement stmt, Camper camper, Session session, String campName) throws SQLException
	{
		
		stmt.executeUpdate("INSERT INTO branch VALUES (20, 'Richmond Main', " +
                "'18122 No.5 Road', 'Richmond', 5252738)");
		stmt.executeUpdate(
			"INSERT INTO branch REGISTRATION (" + 
				// TODO 
				// CONFIRMATION NUMBER?
				""
				// complete registration update
				);
			
			System.out.println("Registration Completed");
	}

	public void makePayment(){
		// TODO
	}
	
	public void findCampActivities(Connection con, String campName){
		// TODO 
	}
	
	public void findActivitiesByCamp(Connection con, ArrayList<String> activityName){
		// TODO
	}
	
	public void findSession(Connection con, Date startDate){
		// TODO
	}
	
	public void cancelRegistration(Connection con, int confNo){
		// TODO
	}
	
	public void switchSession(Connection con, int confNo, Session newSession){
		// TODO
	}
	
	
}