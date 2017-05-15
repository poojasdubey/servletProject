package com.sh.bookshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/showcart")
public class ShowCartServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		processRequest(req, resp);
	}
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		HttpSession session = req.getSession();
		List<Integer> cart = (List<Integer>) session.getAttribute("cart");
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("<td>ID</td>");
		out.println("<td>Name</td>");
		out.println("<td>Author</td>");
		out.println("<td>Subject</td>");
		out.println("<td>Price</td>");
		out.println("</tr>");
		try(BookDao dao = new BookDao()) {
			dao.open();
			for(int id : cart) {
				Book b = dao.getBook(id);
				out.println("<tr>");
				out.printf("<td>%d</td>\r\n", id);
				out.printf("<td>%s</td>\r\n", b.getName());
				out.printf("<td>%s</td>\r\n", b.getAuthor());
				out.printf("<td>%s</td>\r\n", b.getSubject());
				out.printf("<td>%.2f</td>\r\n", b.getPrice());
				out.println("</tr>");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		out.println("</table>");
		out.println("<br/><a href='logout'>Sign Out</a>");
		out.println("</body>");
		out.println("</html>");
	}
}
