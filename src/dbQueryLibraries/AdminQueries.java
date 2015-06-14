package dbQueryLibraries;

import java.sql.*;
import java.util.*;

public class AdminQueries {
	
	//6.9
	public void assignCabinSupervisor(Connection con, int cabinID, String counsellor) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(
			"SELECT fid " +
			"FROM Cabin " +
			"WHERE id = ? " +
				"AND fid IN (SELECT ca.fid " +
							"FROM Camp ca, Counsellor co " +
							"WHERE co.camp_name = ca.name " +
								"AND co.name = ?");
		ps.setInt(1, cabinID);
		ps.setString(2, counsellor);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();
		
		if (!rs.isBeforeFirst() ) {    
			 System.out.println("Error - Counsellor not at cabin's facility");
		} 
		else {
			ps = con.prepareStatement(
				"UPDATE Counsellor " + 
				"SET cabin_id = ? " +
				"WHERE name = ?");
			ps.setInt(1, cabinID);
			ps.setString(2, counsellor);
			int rowCount = ps.executeUpdate();
			
			System.out.println("Completed, " + rowCount + "lines updated.");
		}
		
		ps.close();
	}
	
	//6.10
	public void setWorksAt(Connection con, int instructorID, String camp_name) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(
			"SELECT camp_name "+
			"FROM Counsellor " +
			"WHERE id = ?");
		ps.setInt(1, instructorID);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();
		
		String existCamp = rs.getString("camp_name");
		if(!rs.wasNull() ) {
			 System.out.println("Error - Counsellor already works at " + existCamp + ".");
		}
		else {
			ps = con.prepareStatement(
				"UPDATE Counsellor " + 
				"SET camp_name = ? " +
				"WHERE id = ?");
			ps.setString(1, camp_name);
			ps.setInt(2, instructorID);
			int rowCount = ps.executeUpdate();
			
			System.out.println("Completed, " + rowCount + "lines updated.");
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
				
				output = "Completed, " + rowCount + "lines updated.";
				break;
			}
		}
		
		if (output == null){
			output = "Error - Counsellor does not work at specified camp.";
		}
		System.out.println(output);
		ps.close();
	}
	
	//6.12
	public void deleteCamper(Connection con, int camperID) throws SQLException{
		PreparedStatement ps = con.prepareStatement(
			"SELECT fid " +
			"FROM Camper " +
			"WHERE id = ? ");
		ps.setInt(1, camperID);
		
		ResultSet rs = ps.executeQuery();
		ps.clearBatch();
		ps.clearParameters();
		
		if (!rs.isBeforeFirst() ) {    
			 System.out.println("Error - Camper does not exist");
		} 
		else {
			ps = con.prepareStatement(
				"DELETE FROM Camper " + 
				"WHERE id = ?");
			ps.setInt(1, camperID);
			int rowCount = ps.executeUpdate();
			
			System.out.println("Completed, " + rowCount + "lines deleted.");
		}
		
		ps.close();
	}
	
	//6.13
	public ArrayList<String> checkRegPayments(Connection con, String camp_name) throws SQLException
	{
		PreparedStatement ps = con.prepareStatement(
			"SELECT C.phone_num " +
			"FROM Camper C, Registration R " +
			"WHERE C.id = R.camper_ID " +
				"AND R.camp_name = ? " +
				"AND R.is_paid IS FALSE");
		ps.setString(1, camp_name);
		ResultSet rs = ps.executeQuery();

		ArrayList<String> output = new ArrayList<String>();
		
		if (!rs.isBeforeFirst() ) {    
			System.out.println("All paid.");
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1));
			}
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
			System.out.println("None");
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1));
			}
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
		} 
		else {
			while (rs.next()) {
				output.add(rs.getString(1));
			}
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
			}
		}
		
		stmt.executeUpdate("DROP VIEW campersFilter");
		stmt.close();
		return output;
	}
}
