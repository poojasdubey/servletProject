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

public class BookServlet extends HttpServlet {
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
		for (Cookie c : arrCookies) {
			if(c.getName().equals("user"))
				user = c.getValue();
		}

		
		String subject = req.getParameter("subject");
		String bookid = req.getParameter("bookid");
		int id = 0;
		if(bookid!=null && !bookid.isEmpty())
			id = Integer.parseInt(bookid);
		Book book = null;
		
		List<Book> list = new ArrayList<Book>();
		try (BookDao dao = new BookDao()) {
			dao.open();
			list = dao.getBooksOfSubject(subject);
			book = dao.getBook(id);
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
		out.println("<form method='post' action='addcart'>");
		for (Book b : list) {
			out.printf("<input type='checkbox' name='book' value='%d'/> %s <a href='books?subject=%s&bookid=%d'>Details</a> <br/>\r\n", b.getId(), b.getName(), subject, b.getId());
		}
		out.println("<input type='submit' value='Add Cart'/> <br/>");
		if(book!=null)
			out.println("<div>" + book.toString() + "</div>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}
}
