package com.sh.bookshop;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter
public class BookShopFilter implements Filter {
	@Override
	public void init(FilterConfig arg0) throws ServletException {		
		System.out.println("BookShopFilter.init() called...");
	}
	@Override
	public void destroy() {
		System.out.println("BookShopFilter.destroy() called...");
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("BookShopFilter.doFilter() -- PRE-Processing...");
		HttpServletRequest request = (HttpServletRequest) req;
		if(request.getCookies()==null)
			((HttpServletResponse)resp).sendRedirect("index.html");
		else
			chain.doFilter(req, resp);
		System.out.println("BookShopFilter.doFilter() -- POST-Processing...");
	}
}
