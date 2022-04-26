package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inquiry {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pafprojecteletricity?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertInquiry(String CustomerID, String Date, String CustomerName, String Description, String Area) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into inquiryapi(`inquiryID`,`CustomerID`,`Date`,`CustomerName`,`Description`,`Area`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, CustomerID);
			preparedStmt.setString(3, Date);
			preparedStmt.setString(4, CustomerName);
			preparedStmt.setString(5, Description);
			preparedStmt.setString(6, Area);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the inquiry.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readInquiry() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Inquiry ID</th><th>Customer ID</th><th>Date</th><th>Customer Name</th><th>Description</th><th>Area</th></tr>";
			String query = "select * from inquiryapi ";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String inquiryID = Integer.toString(rs.getInt("inquiryID"));
				String CustomerID = rs.getString("CustomerID");
				String Date = rs.getString("Date");
				String CustomerName = rs.getString("CustomerName");
				String Description = rs.getString("Description");
				String Area = rs.getString("Area");

				// Add into the html table
				output += "<tr><td>" + inquiryID + "</td>";
				output += "<td>" + CustomerID + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + CustomerName + "</td>";
				output += "<td>" + Description + "</td>";
				output += "<td>" + Area + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the inquiry.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateInquiry(String inquiryID, String CustomerID, String Date, String CustomerName, String Description, String Area) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE inquiryapi SET CustomerID=?,Date=?,CustomerName=?,Description=?,Area=?" + "WHERE inquiryID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, CustomerID);
			preparedStmt.setString(2, Date);
			preparedStmt.setString(3, CustomerName);
			preparedStmt.setString(4, Description);
			preparedStmt.setString(5, Area);
			preparedStmt.setInt(6, Integer.parseInt(inquiryID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the inquiry.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteInquiry(String inquiryID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from inquiryapi where inquiryID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(inquiryID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the inquiry.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
