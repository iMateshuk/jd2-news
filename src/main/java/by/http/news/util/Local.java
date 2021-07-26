package by.http.news.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Local {

	private final static String LOCAL = "local";
	private final static String LOCAL_EN = "en";

	public static void setLocal(HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		if (session.getAttribute(LOCAL) == null) {

			session.setAttribute(LOCAL, LOCAL_EN);
		}

		if (request.getParameter(LOCAL) != null
				&& !((String) session.getAttribute(LOCAL)).equals(request.getParameter(LOCAL))) {

			session.setAttribute(LOCAL, request.getParameter(LOCAL));
		}
	}

}
