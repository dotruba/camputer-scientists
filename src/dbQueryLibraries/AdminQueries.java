package dbQueryLibraries;

import java.sql.*;
import java.util.*;

import application.Popup;

public class AdminQueries {
	
	public AdminQueries(){}
	
	//6.9
	public void assignCabinSupervisor(Connection con, int cabinID, int counsellor) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(
			"SELECT fid " +
			"FROM Cabin " +
			"WHERE id = ? " +
				"AND fid IN (SELECT ca.fid " +
							"FROM Camp ca, Counsellor co " +
							"WHERE co.camp_name = ca.name " +
								"AND co.id = ?)");
		ps.setInt(1, cabinID);
		ps.setInt(2, counsellor);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();
		
		if (!rs.isBeforeFirst() ) {   
			Popup.infoBox("Counsellor not at cabin's facility", "Error");
			System.out.println("Error - Counsellor not at cabin's facility");
		} 
		else {
			ps = con.prepareStatement(
				"UPDATE Counsellor " + 
				"SET cabin_id = ? " +
				"WHERE id = ?");
			ps.setInt(1, cabinID);
			ps.setInt(2, counsellor);
			int rowCount = ps.executeUpdate();
			
			System.out.println("Completed, " + rowCount + "lines updated.");
			Popup.infoBox(rowCount + "Lines updated", "Completed");
		}
		
		ps.close();
	}
	
	//6.10
	public void setWorksAt(Connection con, int instructorID, String camp_name) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(
			"SELECT camp_name "+
			"FROM Counsellor " +
			"WHERE id = ?" +
			"AND camp_name IS NULL");
		ps.setInt(1, instructorID);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();
		
		if(rs.next()) {
			ps = con.prepareStatement(
				"UPDATE Counsellor " + 
				"SET camp_name = ? " +
				"WHERE id = ?");
			ps.setString(1, camp_name);
			ps.setInt(2, instructorID);
			int rowCount = ps.executeUpdate();
			
			Popup.infoBox(rowCount + " lines updated.", "Completed");
			System.out.println("Completed, " + rowCount + " lines updated.");
		}
		else {
			Popup.infoBox("Counsellor already works at some other camp", "Error");
			System.out.println("Error - Counsellor already works at some other camp.");
		}
		
		ps.close();
	}
	
	//6.11
	public void assignRegToCounsellor(Connection con, int instructorID, int confirmNo) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(		
			"SELECT R.conf_num, R.camp_name, R.counsellor_id, C.id " +
			"FROM Counsellor C, Registration R " +
			"WHERE R.camp_name = C.camp_name " + 
				"AND R.conf_num = ?");
		ps.setInt(1, confirmNo);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();		
		
		String output = null;
		while (rs.next()) {
			int ID = rs.getInt(3);
			if(!rs.wasNull()) {
				Popup.infoBox("Registration already has assigned counsellor", "Error");
				output = "Error - Registration already has assigned counsellor.";
				break;
			}
			else if(rs.getInt(4) == instructorID) {
				ps = con.prepareStatement(
					"UPDATE Registration " + 
					"SET counsellor_id = ? " +
					"WHERE conf_num = ?");
					ps.setInt(1, instructorID);
					ps.setInt(2, confirmNo);
				int rowCount = ps.executeUpdate();
				Popup.infoBox(rowCount + " lines updated", "Completed");
				output = "Completed, " + rowCount + "lines updated.";
				break;
			}
		}
		
		if (output == null){
			Popup.infoBox("Counsellor does not work at the specified camp.", "Error");
			output = "Error - Counsellor does not work at specified camp.";
		}
		System.out.println(output);
		ps.close();
	}
	
	//6.12
	public void deleteCamper(Connection con, int camperID) throws SQLException{
		PreparedStatement ps = con.prepareStatement(
			"SELECT id " +
			"FROM Camper " +
			"WHERE id = ? ");
		ps.setInt(1, camperID);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();
		
		if (!rs.isBeforeFirst() ) {    
			Popup.infoBox("Camper does not exist", "Error");
			 System.out.println("Error - Camper does not exist");
		} 
		else {
			ps = con.prepareStatement(
				"DELETE FROM Camper " + 
				"WHERE id = ?");
			ps.setInt(1, camperID);
			int rowCount = ps.executeUpdate();
			
			Popup.infoBox("The camper has been deleted.", "Deleted");
			System.out.println("Completed, " + rowCount + "lines deleted.");
		}
		
		ps.close();
	}
	
	//6.13
	public ArrayList<String> checkRegPayments(Connection con, String camp_name) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(
			"SELECT C.phone_num " +
			"FROM CAMPER C, REGISTRATION R " +
			"WHERE C.id = R.camper_id " +
				"AND R.camp_name = ? " +
				"AND R.is_paid = 0");
		ps.setString(1, camp_name);
		ResultSet rs = ps.executeQuery();

		ArrayList<String> output = new ArrayList<String>();
		
		if (!rs.isBeforeFirst() ) {    
			Popup.infoBox("Every camper has completed the payment.", "All paid");
			System.out.println("All paid.");
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			StringBuilder msg = new StringBuilder();
			String outputMsg;
			for(String o : output){
				msg.append(o);
				msg.append("\n");
			}
			outputMsg = msg.toString();
			Popup.infoBox(outputMsg,"People who haven't paid: ");
		}
		
		ps.close();
		return output;
	}
	
	//6.14
	public ArrayList<String> multipleCamps(Connection con, ArrayList<Integer> camperID, ArrayList<String> camp_name) throws SQLException
	{
		Statement stmt = con.createStatement();
		StringBuilder view = new StringBuilder("CREATE VIEW campersFilter AS (SELECT * FROM Registration WHERE ");
		int i;
		
		for (i = 0; i < camperID.size() ; i++) {
			if (i == (camperID.size() - 1) )
				view.append("camper_id = '" + Integer.toString(camperID.get(i)) + "')");
			else 
				view.append("camper_id = '" + Integer.toString(camperID.get(i)) + "' OR ");
		}
		
		stmt.executeUpdate(view.toString());
		
		StringBuilder query = new StringBuilder("SELECT camper_ID, COUNT(*) FROM campersFilter WHERE ");
		
		for (i = 0; i < camp_name.size() ; i++) {
			if (i == (camp_name.size() - 1) )
				query.append("camp_name = '" + camp_name.get(i) + "' ");
			else 
				query.append("camp_name = '" + camp_name.get(i) + "' OR ");
		}
		
		query.append("GROUP BY camper_id HAVING COUNT(*) > 1");
		ResultSet rs = stmt.executeQuery(query.toString());
		
		ArrayList<String> output = new ArrayList<String>();
		
		if (!rs.isBeforeFirst() ) {    
			Popup.infoBox("None of the given campers registered in more than 1 of the given camps","None");
			System.out.println("None");
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1));
			}
			StringBuilder msg = new StringBuilder();
			String outputMsg;
			for(String o : output){
				msg.append(o);
				msg.append("\n");
			}
			outputMsg = msg.toString();
			Popup.infoBox("Campers registered in more than 1 of the given camps:" + "\n" + outputMsg,"Output");
		}
		
		stmt.executeUpdate("DROP VIEW campersFilter");
		stmt.close();
		return output;
	}
	
	//6.15
	public ArrayList<String> checkCounsellor(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(
			"SELECT * " + 
			"FROM Counsellor " +
			"WHERE camp_name IS NULL");
		
		ArrayList<String> output = new ArrayList<String>();
		
		if (!rs.isBeforeFirst() ) {    
			System.out.println("None");
			Popup.infoBox("There is no idle instructor. ","None");
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1));
				
			}
			StringBuilder msg = new StringBuilder();
			String outputMsg;
			for(String o : output){
				msg.append(o);
				msg.append("\n");
			}
			outputMsg = msg.toString();
			Popup.infoBox(outputMsg,"Idle instructors: ");
		}
		
		stmt.close();
		return output;
	}
	
	//6.20
	public ArrayList<String> multipleSessions(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate(
			"CREATE VIEW campersFilter AS (SELECT R.camper_id, C.name, R.sid " +
			"FROM Registration R, Camper C " + 
			"GROUP BY camper_id " +
			"HAVING COUNT(*) > 1)");
		
		ResultSet rs = stmt.executeQuery(
			"SELECT DISTINCT R.camper_id, C.name " +
			"FROM campersFilter" );
		
		ArrayList<String> output = new ArrayList<String>();
		
		if (!rs.isBeforeFirst() ) {    
			System.out.println("None");
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1) + " - " + rs.getString(2));
				System.out.println(rs.getString(1) + " - " + rs.getString(2));
			}
		}
		
		stmt.executeUpdate("DROP VIEW campersFilter");
		stmt.close();
		return output;
	}
	
	public ArrayList<String> getStats(Connection con) throws SQLException{
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("Registration numbers by camp:");
		PreparedStatement ps = con.prepareStatement("SELECT camp_name, Count(camper_id)"
				+ " FROM Registration"
				+ " GROUP BY camp_name");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			stats.add(rs.getString(1) + " - " + rs.getInt(2));
			System.out.println(rs.getString(1) + " - " + rs.getInt(2));
		}
		ps = con.prepareStatement("SELECT camp_name, MIN(COUNT(camper_id))"
				+ " FROM Registration"
				+ " GROUP BY camp_name"
				+ " HAVING MIN(count(*))");
		rs = ps.executeQuery();
		stats.add("Fewest Registrations:");
		while(rs.next()){
			stats.add(rs.getString(1) + " - " + rs.getInt(2));
			System.out.println(rs.getString(1) + " - " + rs.getInt(2));
		}
		
		ps = con.prepareStatement("SELECT camp_name, COUNT(camper_id)"
				+ " FROM Registration"
				+ " GROUP BY camp_name"
				+ " HAVING MIN(count(camper_id))");
		rs = ps.executeQuery();
		stats.add("Most Registrations:");
		while(rs.next()){
			stats.add(rs.getString(1) + " - " + rs.getInt(2));
			System.out.println(rs.getString(1) + " - " + rs.getInt(2));
		}
		
		return stats;
	}
}
