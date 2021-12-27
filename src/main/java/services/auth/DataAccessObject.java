package services.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Employee;


class DataAccessObject extends webpos.DataAccessObject{


	/* isSeCode */
	boolean isSeCode(Connection connection, Employee emp) {
	
		ResultSet rs = null;
		boolean result = false;
		String sql = "SELECT COUNT(*) FROM STORES WHERE SO_CODE = ? ";

		try {
			this.pstmt = connection.prepareStatement(sql);
			this.pstmt.setNString(1, emp.getSoCode());

			rs = this.pstmt.executeQuery();
			while (rs.next()) {
				result = this.convertToBoolean(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/* isEmCode && comparePassword */
	boolean isEmployee(Connection connection, Employee emp) {
		
		
		ResultSet rs = null;
		boolean result = false;
		String CNT = null;
		String sql = "SELECT COUNT(*) FROM SL WHERE SL_CODE = ? AND SL_PASSWORD = ?";
		
		try {
			this.pstmt = connection.prepareStatement(sql);
			this.pstmt.setNString(1, emp.getSlCode());
			this.pstmt.setNString(2, emp.getSlPassword());

			rs = this.pstmt.executeQuery();
			while (rs.next()) {
				
				result = this.convertToBoolean(rs.getInt(1));
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed())
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	
	
	
	/*
	 * 
	 * private String toStringFromArray(ArrayList<Employee> data) { StringBuffer sb
	 * = new StringBuffer();
	 * 
	 * for (int recordIndex = 0; recordIndex < data.size(); recordIndex++) {
	 * sb.append(" "); sb.append(data.get(recordIndex).getMemberCode());
	 * sb.append("\t"); sb.append(data.get(recordIndex).getMemberName());
	 * sb.append(data.get(recordIndex).getMemberName().length() < 6 ? "\t" : "");
	 * sb.append("\t"); sb.append(data.get(recordIndex).getCallNumber());
	 * sb.append("\t"); sb.append(data.get(recordIndex).getMemberGdCode());
	 * sb.append("\n"); }
	 * 
	 * return sb.toString(); }
	 */

}
