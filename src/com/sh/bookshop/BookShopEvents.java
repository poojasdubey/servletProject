package com.sh.bookshop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@WebListener
public class BookShopEvents implements ServletContextListener, HttpSessionListener {
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("contextInitialized() called...");
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed() called...");
	}
	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		System.out.println("sessionCreated() called...");
		HttpSession session = hse.getSession();
		List<Integer> cart = new ArrayList<>();
		session.setAttribute("cart", cart);
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("sessionDestroyed() called...");
	}
}
