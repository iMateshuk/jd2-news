package by.project.news.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Local {

	private final static String LOCAL = "local";
	private final static String LOCAL_DEF = "en";

	public static void setDefoult(HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(LOCAL) == null) {

			session.setAttribute(LOCAL, LOCAL_DEF);
		}
	}

	public static void change(HttpServletRequest request) {
		
		String local = request.getParameter(LOCAL);

		if (local != null) {

			request.getSession(false).setAttribute(LOCAL, local);
		}

	}

}
