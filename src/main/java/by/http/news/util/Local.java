package by.http.news.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Local {

	private final static String LOCAL = "local";
	private final static String LOCAL_DEF = "en";

	public static void setDef(HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(LOCAL) == null) {

			session.setAttribute(LOCAL, LOCAL_DEF);
		}
	}
	
	public static void change(HttpServletRequest request) {
		
		request.getSession(true).setAttribute(LOCAL, request.getParameter(LOCAL));
		
		/*
		 * HttpSession session = request.getSession(true);
		 * 
		 * if (request.getParameter(LOCAL) != null && !((String)
		 * session.getAttribute(LOCAL)).equals(request.getParameter(LOCAL))) {
		 * 
		 * session.setAttribute(LOCAL, request.getParameter(LOCAL)); }
		 */
	}

}
