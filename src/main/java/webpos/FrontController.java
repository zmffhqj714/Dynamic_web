package webpos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Access")//<프론트에서 서버에 날려주는 방식, container(servlet )  다이나믹인지 확인함
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontController() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("겟방");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("mId").equals("bon")&& request.getParameter("mPassword").equals("1234")) {
			response.sendRedirect("index.jsp");
		}else {
			response.sendRedirect("index.html");
		}
		System.out.println("동적인가");
			
	}

}
