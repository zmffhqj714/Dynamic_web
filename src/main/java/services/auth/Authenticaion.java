package services.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Action;
import beans.Employee;

//로그인처리 
public class Authenticaion {
	HttpSession session;
	PreparedStatement psmt;
	private HttpServletRequest req;
	Employee emp = new Employee();
	Action action = new Action();

	DataAccessObject dao;
	boolean tran = false;

	public Authenticaion(HttpServletRequest req) {
		this.req = req;
		
	}

	public Action backController(int jobCode) {
		Action action = null;
		switch (jobCode) {

		case 1:// 로그인
			action = this.accessCtl();
			break;
			
		case -1:// 로그아웃
			action = this.accessOutCtl();
			break;
		case 0:// 새페이지이동(로그인)
			action = this.afterAccess();
			break;
		default:
		}
		return action;
	}

	public Action afterAccess() {
		Action action = new Action();
		ArrayList<Employee> list = null;
		dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		session = this.req.getSession();
		
		this.emp = new Employee();
		this.emp.setSoCode((String)session.getAttribute("soCode"));
		this.emp.setSlCode((String)session.getAttribute("slCode"));
		
		if((list = dao.getAHInfo(conn, emp)) != null) {
			req.setAttribute("accessInfo", list);
			action.setRedirect(false);
			action.setPage("index.jsp");			
		}
		return action;
	}

	private Action accessCtl() {
		Action action = new Action();
		ArrayList<Employee> list = null;
		// 데이터를 빈에 담아서 보냄
		session = this.req.getSession();
		dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		this.emp = new Employee();
		this.emp.setSoCode(this.req.getParameter("soCode"));
		System.out.println(emp.getSoCode());
		this.emp.setSlCode(this.req.getParameter("slCode"));
		this.emp.setSlPassword(this.req.getParameter("mPassword"));
		this.emp.setStCode(2);
		
		
		
		
	
		/*
		 * 2. DAO 연동 2-1. STORES :: SECODE 존재 여부
		 * 
		 *
		 * 2-2. EMPLOYEE :: EMCODE 존재 여부 2-3. EMPLOYEE :: PASSWORD일치 여부 :: RETURN : 1 >>
		 * P2-4 2-4. ACCESSHISTORY : INSERT :: RETURN : 1 2-5. 정보 취합 -->
		 * ARRAYLIST<EMPLOYEE>
		 * 
		 * *** 로그인 성공 :: main.jsp 로그인 실패 :: index.html
		 */
	
		dao.modifyTranStatus(conn, false);
		
		if (dao.isSeCode(conn, emp)) {			
			if (dao.isEmployee(conn, emp)) {
				if (dao.regAccessHistory(conn, emp)) {
					tran = true;
					
					session = this.req.getSession();
					session.setAttribute("soCode", emp.getSoCode());
					session.setAttribute("slCode", emp.getSlCode());
					
					if ((list = dao.getAHInfo(conn, emp)) != null) {				
						req.setAttribute("accessInfo", list);
					}else {
						System.out.println("실패4");
					}

				}else {
					System.out.println("실패3");
				}

			}else {
				System.out.println("실패2");
			}

		}else {
			System.out.println("실패1");
		}

		action.setPage(tran ? "index.jsp" : "index.html");
		action.setRedirect(tran ? false : true);

		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.closeConnection(conn);

		return action;
	}

	private Action accessOutCtl() {
		Action action = new Action();
		ArrayList<Employee> list = null;
		session = this.req.getSession();
		// 데이터를 빈에 담아서 보냄
		this.emp = new Employee();
		this.emp.setSoCode(this.req.getParameter("soCode"));
		this.emp.setSlCode(this.req.getParameter("slCode"));
		this.emp.setStCode(-9);

		dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		dao.modifyTranStatus(conn, false);

		if (dao.regAccessHistory(conn, emp)) {
			tran = true;
			session.invalidate();
		}
		
		
		action.setPage("index.html");
		action.setRedirect(true);

		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.closeConnection(conn);
		return action;

	}
}
