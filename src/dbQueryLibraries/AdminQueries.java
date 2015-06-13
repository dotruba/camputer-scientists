package dbQueryLibraries;

import java.sql.ResultSet;
import java.sql.Statement;

public class AdminQueries {
	Statement stmt = con.createStatement();
	
	//6.9
	public String assignCabinSupervisor(int cabinID, String counsellor)
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
				"SET cabin_id = '" + cabinID + "' ");
			
			System.out.println("Completed, " + rowCount + "lines updated.");
		}
	}
	
	//6.10
	
	
}
