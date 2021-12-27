package webpos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Employee;

public class DataAccessObject  {
	public PreparedStatement pstmt;
	public DataAccessObject() {
		this.pstmt = null;
	}
	
	public Connection getConnection() {
		Connection connection = null;
		String[] url = {"jdbc:oracle:thin:@192.168.0.75:1521:xe", "localdba", "1234" };
		System.out.println("OracleDataBase 연결 성공");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			connection = DriverManager.getConnection(url[0], url[1], url[2]);
			
		} catch (Exception e) {
			System.out.println("OracleDataBase 연결 실패");
		}
		return connection;

	}

	public void modifyTranStatus(Connection connection, boolean status) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.setAutoCommit(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTransaction(Connection connection, boolean tran) {
		try {
			if (connection != null && !connection.isClosed()) {
				if (tran) {
					connection.commit();
				}
			} else {
				connection.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* convertToBoolean */
	public boolean convertToBoolean(int value) {
		return (value > 0) ? true : false;
	}
	
	/* insAccessHistory */

	public boolean regAccessHistory(Connection connection, Employee emp) {
		
		boolean result = false;
		String query = "INSERT INTO AH(AH_SOCODE, AH_SLCODE, AH_ACCESSTIME, AH_ACCESSTYPE)"
				+ "     VALUES(?,?,DEFAULT,?)";
		try {
			// 저장할때 등록형식
			this.pstmt = connection.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSoCode());
			this.pstmt.setNString(2, emp.getSlCode());
			this.pstmt.setInt(3, emp.getLog());

			result = this.convertToBoolean(pstmt.executeUpdate());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}

		return result;
	}

	/* getAccessInfo */
	public ArrayList<Employee> getAHInfo(Connection connection, Employee emp) {
		
		ResultSet rs = null;
		ArrayList<Employee> emList = new ArrayList<Employee>();
		String query = "SELECT SOCODE, SONAME, SLCODE, SLNAME, ACCESSTIME FROM ACCESSINFO \r\n"
				+ " WHERE ACCESSTIME = (SELECT TO_CHAR(MAX(AH_ACCESSTIME), 'YYYY-MM-DD HH24:MI:SS')\r\n"
				+ " FROM ACCESSHISTORY WHERE AH_SOCODE = ? AND AH_SLCODE= ? )  ";

		try {
			this.pstmt = connection.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSoCode());
			
			this.pstmt.setNString(2, emp.getSlCode());
			
			rs = this.pstmt.executeQuery();

			while (rs.next()) {
				Employee em = new Employee();
				em.setSoCode(rs.getNString("SOCODE"));
				em.setSoName(rs.getNString("SONAME"));
				em.setSlCode(rs.getNString("SLCODE"));
				em.setSlName(rs.getNString("SLNAME"));
				em.setDate(rs.getNString("ACCESSTIME"));

				emList.add(em);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {rs.close();}} catch (SQLException e) {e.printStackTrace();}

		}
		return emList;
	}

	public void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
			connection.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}
