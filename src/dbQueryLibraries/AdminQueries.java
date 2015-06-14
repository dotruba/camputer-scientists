package dbQueryLibraries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminQueries {
	
	//6.9
	public void assignCabinSupervisor(Statement stmt, String cabinID, String counsellor) throws SQLException
	{
		ResultSet rs = stmt.executeQuery(
			"SELECT facility_ID " +
			"FROM Cabin " +
			"WHERE cabin_ID = '" + cabinID + "' " +
				"AND facility_ID IN (SELECT ca.facility_ID " +
									"FROM Camp ca, Counsellor co " +
									"WHERE co.camp_name = ca.name " +
										"AND co.name = '" + counsellor + "' ");
	
		if (!rs.isBeforeFirst() ) {    
			 System.out.println("No data.");
			 System.out.println("Error - Counsellor not at cabin's facility");
		} 
		else {
			int rowCount = stmt.executeUpdate(
				"UPDATE Counsellor " + 
				"SET cabin_id = '" + cabinID + "' " +
				"WHERE name = " + counsellor + "'");
			
			System.out.println("Completed, " + rowCount + "lines updated.");
		}
	}
	
	//6.10
	public void setWorksAt(Statement stmt, int instructorID, String camp_name) throws SQLException
	{
		ResultSet rs = stmt.executeQuery(
			"SELECT camp_name "+
			"FROM Counsellor " +
			"WHERE id = '" + instructorID + "'");
		
		String existCamp = rs.getString("camp_name");
		if(!rs.wasNull() ) {
			 System.out.println("Error - Counsellor already works at " + existCamp + ".");
		}
		else {
			int rowCount = stmt.executeUpdate(
				"UPDATE Counsellor " + 
				"SET camp_name = '" + camp_name + "' " +
				"WHERE id = " + instructorID + "'");
				
			System.out.println("Completed, " + rowCount + "lines updated.");
		}
	}
	
	//6.11
	public void assignRegToCounsellor(Statement stmt, int instructorID, int confirmNo) throws SQLException
	{
		ResultSet rs = stmt.executeQuery(
			"SELECT R.confirmation#, R.camp_name, R.counsellor_ID, C.counsellor_ID " +
			"FROM Counsellor C, Registration R " +
			"WHERE R.camp_name = C.camp_name " + 
				"AND R.confirmation# = '" + confirmNo + "'");
		
		while (rs.next()) {
			int ID = rs.getInt(3);
			if(!rs.wasNull()) {
				System.out.println("Error - Registration already has assigned counsellor.");
				break;
			}
			else if(rs.getInt(4) == instructorID) {
				int rowCount = stmt.executeUpdate(
					"UPDATE Registration " + 
					"SET counsellor_ID = '" + instructorID + "' " +
					"WHERE confirmation# = " + confirmNo + "'");
					
				System.out.println("Completed, " + rowCount + "lines updated.");
				break;
			}
		}
		
		System.out.println("Error - Counsellor does not work at specified camp.");
	}
	
	//6.13
	public String checkRegPayments(Statement stmt, String camp_name) throws SQLException
	{
		ResultSet rs = stmt.executeQuery(
			"SELECT C.phone# " +
			"FROM Camper C, Registration R " +
			"WHERE C.camper_id = R.camper_ID " +
				"AND R.camp_name = '" + camp_name + "' " +
				"AND R.is_paid IS FALSE");
		
		StringBuilder output = new StringBuilder();
		
		if (!rs.isBeforeFirst() ) {    
			output.append("All paid");
		} 
		else {
			while (rs.next()) {
				output.append(rs.getString(1) + "\n");
			}
		}
		
		return output.toString();
	}
	
	//6.14
	public String multipleCamps(Statement stmt, int[] camperID, String[] camp_name) throws SQLException
	{
		StringBuilder view = new StringBuilder("CREATE VIEW campersFilter AS (SELECT * FROM Registration WHERE ");
		int i;
		
		for (i = 0; i < camperID.length ; i++) {
			if (i == (camperID.length - 1) )
				view.append("camper_ID = '" + camperID[i] + "')");
			else 
				view.append("camper_ID = '" + camperID[i] + "' OR ");
		}
		
		stmt.executeUpdate(view.toString());
		
		StringBuilder query = new StringBuilder("SELECT camper_ID, COUNT(*) FROM campersFilter WHERE ");
		
		for (i = 0; i < camp_name.length ; i++) {
			if (i == (camp_name.length - 1) )
				query.append("camper_ID = '" + camp_name[i] + "' ");
			else 
				query.append("camper_ID = '" + camp_name[i] + "' OR ");
		}
		
		query.append("GROUP BY camper_ID HAVING COUNT(*) > 1");
		ResultSet rs = stmt.executeQuery(query.toString());
		
		StringBuilder output = new StringBuilder();
		
		if (!rs.isBeforeFirst() ) {    
			output.append("None");
		} 
		else {
			while (rs.next()) {
				output.append(rs.getString(1) + "\n");
			}
		}
		
		return output.toString();
	}
}
