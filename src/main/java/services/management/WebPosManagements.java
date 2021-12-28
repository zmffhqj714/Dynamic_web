package services.management;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Action;
import beans.Employee;

public class WebPosManagements {
	HttpSession session;
	PreparedStatement psmt;
	private HttpServletRequest req;
	// 세션 있으면 가고
	

	public WebPosManagements(HttpServletRequest req) {
		this.req = req;
	}

	public Action backController(int jobCode) {
		Action action = null;
		
		switch (jobCode) {

		case 1:
			action = mgrMainCtl();
			break;
		case 6:
			action = getEmpListCtl();
			break;

		default:
		}
		return action;
	}

	private Action getEmpListCtl() {
		Employee emp = new Employee();
		
		Action action = new Action();
		ArrayList<Employee> list = null;
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		session = this.req.getSession();
		
		emp = new Employee();
		emp.setSoCode((String)session.getAttribute("soCode"));
		emp.setSlCode((String)session.getAttribute("slCode"));
		
		
		this.req.setAttribute("list", this.makeHtmlFromList(dao.getEmployeeList(conn,emp)));
		
			this.req.setAttribute("accessInfo", dao.getAHInfo(conn, emp));
			action.setRedirect(false);
			action.setPage("management.jsp");			
		
		return action;
	}
	
	private String makeHtmlFromList(ArrayList<Employee> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>직원코드</td>");
		sb.append("<td>직원이름</td>");
		sb.append("<td>근무여부</td>");
		sb.append("<td>근태상황</td>");
		sb.append("<td>정보수정</td>");
		sb.append("</tr>");
		
		
		for(Employee emp:list) {
			sb.append("<tr>");
			sb.append("<td>"+emp.getSlCode()+"</td>");
			sb.append("<td>"+emp.getSlName()+"</td>");
			sb.append("<td>"+emp.getSlStateName()+"</td>");
			sb.append("<td>"+emp.getTodayInfo()+"</td>");
			sb.append("<td><input type=\"button\" value=\"수정\" onClick=\"modEmp(\'"+emp.getSoCode()+ "\', \'"+ emp.getSlCode()+"\')\" /></td>");
			sb.append("</tr>");
			
		}
		sb.append("</table>");
		return sb.toString();
		
	}

	private Action mgrMainCtl() {

		session = this.req.getSession();
		Action action = new Action();
		ArrayList<Employee> list = null;
		Employee emp = null;
		String page = "index.html";
		boolean isRedirect = true;

		/* Access-Info */
		if (this.req.getParameter("soCode").equals((String) session.getAttribute("soCode"))) {
			if (this.req.getParameter("slCode").equals((String) session.getAttribute("slCode"))) {
				emp = new Employee();
				emp.setSoCode(this.req.getParameter("soCode"));
				emp.setSlCode(this.req.getParameter("slCode"));
				page = "management.jsp";
				isRedirect = false;
			}
		}

		/* Dash-Board */

		/* DAO */
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();

		if ((list = dao.getAHInfo(conn, emp)) != null) {
			req.setAttribute("accessInfo", list);
		}
		dao.closeConnection(conn);

		action.setPage(page);
		action.setRedirect(isRedirect);
		return action;
	}

}
