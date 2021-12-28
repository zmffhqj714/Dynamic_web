package services.management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.Employee;

public class DataAccessObject extends webpos.DataAccessObject {

	ArrayList<Employee> getEmployeeList(Connection conn, Employee emp) {
		ResultSet rs = null;
		ArrayList<Employee> list = new ArrayList<Employee>();
		String query = "SELECT * FROM EMLIST WHERE SOCODE = ? ";

		try {
			this.pstmt = conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSoCode());

			rs = this.pstmt.executeQuery();
			while (rs.next()) {
				Employee e = new Employee();
				e.setSoCode(rs.getNString("SOCODE"));
				e.setSlCode(rs.getNString("SLCODE"));
				e.setSlName(rs.getNString("SLNAME"));
				e.setSlStateCode(rs.getNString("SLSTATE"));
				e.setSlStateName(rs.getNString("STNAME"));
				e.setTodayInfo(rs.getInt("CNT") == 0? "결근": (rs.getInt("CNT") == 1?"출근":"퇴근"));
			
				list.add(e);
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
