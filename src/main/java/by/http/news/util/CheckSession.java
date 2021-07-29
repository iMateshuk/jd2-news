package by.http.news.util;

import java.io.IOException;

import by.http.news.bean.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CheckSession {
	
	private static final String USER = "user";
	private static final String USER_TIME_OUT = "User session time out.";
	private static final String USER_NOT_LOGGED = "User not logged in.";
	private static final String USER_RIGHTS ="Not enough rights.";

	public static void validate(HttpServletRequest request) throws IOException, UtilException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			throw new UtilException(USER_TIME_OUT);
		}

		User user = (User) session.getAttribute(USER);

		if (user == null) {

			throw new UtilException(USER_NOT_LOGGED);
		}

	}

	public static void validateRoleUser(HttpServletRequest request) throws IOException, UtilException {
		
		User user = (User) request.getSession(false).getAttribute(USER);
		
		if (user.getRole().equals(USER)) {

			throw new UtilException(USER_RIGHTS);
		}

	}

}
