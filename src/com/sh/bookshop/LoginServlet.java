package com.sh.bookshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private int cnt = 0;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		processRequest(req, resp);
	}
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		synchronized (this) {
			cnt++;			
		}
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		boolean success = false;
		try(LoginDao dao = new LoginDao()){
			dao.open();
			success = dao.validate(user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(success) {
			Cookie c = new Cookie("user", user);
			//c.setMaxAge(3600);
			resp.addCookie(c);
			
			//// cart creation code is moved to BookShopEvents -- HttpSessionListener
		//	HttpSession session = req.getSession();
		//	List<Integer> cart = new ArrayList<>();
		//	session.setAttribute("cart", cart);
			
			resp.sendRedirect("subjects");
			//String encUrl = resp.encodeRedirectURL("subjects");
			//resp.sendRedirect(encUrl);
		} else {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Hello Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h3>Sorry, Invalid username or password.</h3>");
			out.println("<a href='index.html'>Login Again</a>");
			out.println("</body>");
			out.println("</html>");
		}
	}
}



