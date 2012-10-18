package com.breakfast.tomorrow.web.server.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



/**
 * Servlet Filter implementation class FilterLogin
 */
public class FilterLogin implements Filter {

  
    public FilterLogin() {
       
    }
    
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		String pLogin = request.getParameter("login");
		String pSenha = request.getParameter("senha");
	
		if(pLogin!=null && pSenha!=null){
			if("adm".equals(pLogin) && "adm".equals(pSenha)){
				session.setAttribute("login", pLogin);
				chain.doFilter(request, response);
			}
			//for dev mode
			if("dev".equals(pLogin) && "dev".equals(pSenha)){
				session.setAttribute("login", pLogin);
				chain.doFilter(request, response);
			}
		}
		if(session.getAttribute("login")!=null){
			chain.doFilter(request, response);
		}
		else{
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
