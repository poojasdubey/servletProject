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

public class SubjectServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		processRequest(req, resp);
	}
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		String user = "";
		Cookie[] arrCookies = req.getCookies();
		if(arrCookies!=null) {
			for (Cookie c : arrCookies) {
				if(c.getName().equals("user"))
					user = c.getValue();
			}
		}

		List<String> list = new ArrayList<String>();
		try (BookDao dao = new BookDao()) {
			dao.open();
			list = dao.getSubjects();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.printf("Hello, %s<hr/>\r\n", user);
		out.println("<form method='post' action='books'>");
		for (String subject : list) {
			out.printf("<input type='radio' name='subject' value='%s'/> %s <br/>\r\n", subject, subject);
		}
		out.println("<a href='showcart'>Show Cart</a>");
		out.println("<input type='submit' value='Show Books'/>");
		out.println("</form>");

		String info = (String) req.getAttribute("info");
		if(info != null)
			out.printf("<div>%s</div>\r\n", info);

		out.println("</body>");
		out.println("</html>");

	}
}
