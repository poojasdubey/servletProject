package com.sh.bookshop;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/addcart")
public class AddCartServlet extends HttpServlet {
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
		
		String[] arr = req.getParameterValues("book");
		for(String bkid : arr) {
			int id = Integer.parseInt(bkid); 
			cart.add(id);
		}
		
		req.setAttribute("info", arr.length + " more books added in cart.");
		
		RequestDispatcher rd = req.getRequestDispatcher("subjects");
		rd.forward(req, resp);
	}
}
