package services.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Action;
import beans.Employee;
import services.management.DataAccessObject;

public class WebPos {
	HttpSession session;
	PreparedStatement psmt;
	private HttpServletRequest req;

	public WebPos(HttpServletRequest req) {
		this.req = req;

	}

	public Action backController(int jobCode) {
		Action action = null;
		switch (jobCode) {

		case 1:
			action = posCtl();
			break;

		default:
		}
		return action;
	}

	private Action posCtl() {

		session = this.req.getSession();
		Action action = new Action();
		ArrayList<Employee> list = null;
		Employee emp = null;
		String page = "index.html";
		boolean isRedirect = true;
		System.out.println(this.req.getParameter("soCode"));
		System.out.println((String) session.getAttribute("soCode"));
		/* Access-Info */
		if (this.req.getParameter("soCode").equals((String) session.getAttribute("soCode"))) {
			if (this.req.getParameter("slCode").equals((String) session.getAttribute("slCode"))) {
				emp = new Employee();
				emp.setSoCode(this.req.getParameter("soCode"));
				emp.setSlCode(this.req.getParameter("slCode"));
				page = "sales.jsp";
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
