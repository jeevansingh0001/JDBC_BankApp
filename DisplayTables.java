package com.Amdocs.problemstatement;
import java.sql.*;
public class DisplayTables {
	String table_name;
	public static void DisplayTable(Connection con, String table_name){
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establish the Connection
			//Connection con1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott", "tiger");
			String selectQuery = "SELECT * From " + table_name;
			PreparedStatement selectstatement = con.prepareStatement(selectQuery);
			ResultSet rs = selectstatement.executeQuery();
			
			System.out.println("\nTransactions in "+ table_name + " Table ");
			while(rs.next()) {
				String mtransID = rs.getString(1);
				String mtransType = rs.getString(2);
				Double mtransAmt = rs.getDouble(3);
				String mvalidity = rs.getString(4);
				System.out.println(mtransID + " " + mtransType + " " + mtransAmt + " " + mvalidity);
			}
			
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
