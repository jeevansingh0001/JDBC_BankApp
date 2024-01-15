package com.Amdocs.problemstatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.*;
public class BankTransaction {
	public static void processTransaction() {
		Connection con;
		try {
				// Loading the Database Driver
				Class.forName("oracle.jdbc.driver.OracleDriver");

				// Establish the Connection
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott", "tiger");
				//Retrieve data
				String selectQuery = "SELECT TransID, AcctNo, OldBal, TransType, TransAmt FROM BankTrans";
				PreparedStatement selectstatement = con.prepareStatement(selectQuery);
				ResultSet rs = selectstatement.executeQuery();
				
				String updateQuery = "UPDATE BankTrans SET NewBal = ?, TransStat = ? WHERE TransID = ?";
	            PreparedStatement updatestatement = con.prepareStatement(updateQuery);

	            String insertValidQuery = "INSERT INTO ValidTrans (TransID, TransType, TransAmt, Validity) VALUES (?, ?, ?, 'Valid')";
	            PreparedStatement insertValidstatement = con.prepareStatement(insertValidQuery);

	            String insertInvalidQuery = "INSERT INTO InvalidTrans (TransID, TransType, TransAmt, Validity) VALUES (?, ?, ?, 'Invalid')";
	            PreparedStatement insertInvalidstatement = con.prepareStatement(insertInvalidQuery);
	            
	            //Retrieving data from statement
	            while(rs.next()) {
	            	String mtransID = rs.getString(1);
	            	int macctNo = rs.getInt(2);
	            	double moldBal = rs.getDouble(3);
	            	String mtransType = rs.getString(4);
	            	double mtransAmt = rs.getDouble(5);
	            	double mnewBal;
	            	String mtransStat;
	            	
	            	if("D".equals(mtransType)) {
	            		mnewBal = moldBal + mtransAmt;	           
	            	} else if("W".equals(mtransType)){
	            		mnewBal = moldBal - mtransAmt;
	            	} else {
	            		mnewBal = moldBal;
	            	}
	            	
	            	
	            	//check validity
	            	if(mnewBal>=0) {
	            		mtransStat = "Valid";
	            	}else {
	            		mtransStat = "Invalid";
	            	}
	          
	            	
	            	 
	            	// Update NewBal and TransStat
	            	updatestatement.setDouble(1, mnewBal);
	                updatestatement.setString(2, mtransStat);
	                updatestatement.setString(3, mtransID);
	                updatestatement.executeUpdate();

	                // Log transaction into ValidTrans or InvalidTrans
	                if (mnewBal>=0) {
	                    insertValidstatement.setString(1, mtransID);
	                    insertValidstatement.setString(2, mtransType);
	                    insertValidstatement.setDouble(3, mtransAmt);
	                    insertValidstatement.executeUpdate();
	                    //System.out.println(j + " Rows Inserted ");
	                } else {
	                    insertInvalidstatement.setString(1, mtransID);
	                    insertInvalidstatement.setString(2, mtransType);
	                    insertInvalidstatement.setDouble(3, mtransAmt);
	                    insertInvalidstatement.executeUpdate();
	           
	                }
	              System.out.println(mtransID + " " + macctNo + " " + moldBal + " " + mtransType + " "+ mtransAmt + " " + mnewBal + " "+ mtransStat);
	            }
	            DisplayTables.DisplayTable(con, "ValidTrans");
	              
	            DisplayTables.DisplayTable(con, "InValidTrans");
	            con.close();
				
		} catch(Exception E) {
			E.printStackTrace();
		}
		
	}
	
}
