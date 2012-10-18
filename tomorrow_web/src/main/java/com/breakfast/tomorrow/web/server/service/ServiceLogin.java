package com.breakfast.tomorrow.web.server.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The HttpServlet Class for Service Login, everybody entire here.
 * here, to be used filters and sessions 
 * @author Kleber Ilario
 *
 */
public class ServiceLogin extends HttpServlet {

	@Override
	protected void service(HttpServletRequest  request, 
					    HttpServletResponse response) throws ServletException, 
					    									 IOException {	 
		if(request.getSession().getAttribute("login").equals("dev")){
			response.sendRedirect("academic.jsp?gwt.codesvr=127.0.0.1:9997");
		}
		
	}
	

	private static final long serialVersionUID = -6696074663575267127L;
}
