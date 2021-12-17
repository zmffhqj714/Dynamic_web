package services.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Employee;


class DataAccessObject {
	// private Connection connection;//모든메서드가 사용한다고함
	PreparedStatement pstmt;
	ResultSet rs;

	DataAccessObject() {

	}

	/*
	 * Driver Loading & Create Connetion 요청 statement , preparestatmet,등 3가지 process
	 * transction 상태변경 transction 상태변경
	 * 
	 */

	Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String[] url = { "jdbc:oracle:thin:@192.168.0.75:1521:XE", "localdba", "1234" };
			connection = DriverManager.getConnection(url[0], url[1], url[2]);
			System.out.println("OracleDataBase 연결 성공");
		} catch (Exception e) {
			System.out.println("OracleDataBase 연결 실패");
		}
		return connection;

	}

	void modifyTranStatus(Connection connection, boolean status) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.setAutoCommit(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void setTransaction(Connection connection, boolean tran) {
		try {
			if (connection != null && !connection.isClosed()) {
				if (tran) {
					connection.commit();
				}
			} else {
				connection.rollback();
			}
			connection.setAutoCommit(true);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* convertToBoolean */
	boolean convertToBoolean(int value) {
		return (value > 0) ? true : false;
	}

	/* isSeCode */
	boolean isSeCode(Connection connection, Employee emp) {
		ResultSet rs = null;
		boolean result = false;
		String sql = "SELECT COUNT(*) FROM SO WHERE SO_CODE = ?";

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
	/* insAccessHistory */

	boolean regAccessHistory(Connection connection, Employee emp) {
		boolean result = false;
		String query = "INSERT INTO AH(AH_SOCODE, AH_SLCODE, AH_ACCESSTIME, AH_ACCESSTYPE)"
				+ "     VALUES(?,?,DEFAULT,?)";
		try {
			// 저장할때 등록형식
			pstmt = connection.prepareStatement(query);
			pstmt.setNString(1, emp.getSoCode());
			pstmt.setNString(2, emp.getSlCode());
			pstmt.setInt(3, emp.getLog());

			result = this.convertToBoolean(pstmt.executeUpdate());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/* getAccessInfo */
	ArrayList<Employee> getAHInfo(Connection connection, Employee emp) {
		ResultSet rs = null;
		ArrayList<Employee> emList = new ArrayList<Employee>();
		String query = "SELECT SOCODE, SONAME, SLCODE, SLNAME, ACCESSTIME FROM ACCESSINFO \r\n"
				+ " WHERE ACCESSTIME = (SELECT TO_CHAR(MAX(AH_ACCESSTIME), 'YYYY-MM-DD HH24:MI:SS')\r\n"
				+ " FROM ACCESSHISTORY WHERE AH_SOCODE = ? AND AH_SLCODE= ? )  ";

		try {
			this.pstmt = connection.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSoCode());
			this.pstmt.setNString(2, emp.getSlCode());

			rs = pstmt.executeQuery();

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
				if (!rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

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
