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
	//세션 있으면 가고 
	
	public  WebPosManagements (HttpServletRequest req) {
		this.req = req;
	}
	
	public Action backController(int jobCode) {
		Action action = null;
		switch (jobCode) {

		case 1:
		action = mgrMainCtl();
			break;
		
		default:
		}
		return action;
	}
	

	private Action mgrMainCtl() {
		
		session = this.req.getSession(); 
		Action action = new Action();
		ArrayList<Employee> list = null;
		Employee emp = null;
		String page = "index.html";
		boolean isRedirect = true;
		System.out.println(this.req.getParameter("soCode"));
		System.out.println((String)session.getAttribute("soCode"));
		/* Access-Info */
		if(this.req.getParameter("soCode").equals((String)session.getAttribute("soCode"))) {
			if(this.req.getParameter("slCode").equals((String)session.getAttribute("slCode"))) {
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
		
		if((list = dao.getAHInfo(conn, emp)) != null) {
			req.setAttribute("accessInfo", list);
		}
		dao.closeConnection(conn);
		
		action.setPage(page);
		action.setRedirect(isRedirect);
		return action;
	}

}
