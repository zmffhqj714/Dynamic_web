package webpos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Action;
import services.auth.Authenticaion;


@WebServlet({ "/Access", "/AccessOut" }) // < 서버에서 날려주고 container(servlet ) 다이나믹인지 확인함
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);

	}

	// 엑세스, 아웃 요청값비교
	private void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String jobcode = req.getRequestURI().substring(req.getContextPath().length()+1);
		Action action = null;
		Authenticaion auth = null;
		
		
		if(jobcode.equals("Access")) {
	
				auth = new Authenticaion(req);
				action = auth.backController(1);
			
			}else if (jobcode.equals("AccessOut")){
				auth = new Authenticaion(req);
				action = auth.backController(-1);
			}else{

			}
		
		if(action.isRedirect()) {// true 톰캣에서 아파치 서버로 보내주는 법 2개
			res.sendRedirect(action.getPage()); 
		}else {
			RequestDispatcher dp = req.getRequestDispatcher(action.getPage()); //로그인
			dp.forward(req,res); //서버메모리에 있는 setattribute 에 저장된걸 가져옴
		}
		}

	}
