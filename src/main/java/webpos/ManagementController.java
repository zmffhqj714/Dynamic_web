package webpos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Action;

import services.management.WebPosManagements;
import services.pos.WebPos;

/**
 * Servlet implementation class ManagementController
 */
@WebServlet("/EmpList")
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagementController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("get방식호출했음");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);

	}

	// 엑세스, 아웃 요청값비교
	private void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String jobcode = req.getRequestURI().substring(req.getContextPath().length() + 1);
		Action action = null;

		HttpSession session = req.getSession();

		WebPosManagements wpm = null;
		WebPos wp = null;

		if (session.getAttribute("soCode") != null) {
			if (jobcode.equals("EmpList")) {
				wpm = new WebPosManagements(req);
				action = wpm.backController(6);
			}

		} else {
			action = new Action();
			action.setRedirect(true);
			action.setPage("index.html");
		}

		if (action.isRedirect()) {// true 톰캣에서 아파치 서버로 보내주는 법 2개
			res.sendRedirect(action.getPage());
		} else {
			RequestDispatcher dp = req.getRequestDispatcher(action.getPage());
			dp.forward(req, res); // 서버메모리에 있는 setattribute 에 저장된걸 가져옴
		}
	}

}
