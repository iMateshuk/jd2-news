package by.http.news.util;

import java.io.IOException;

import by.http.news.bean.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CheckSession {

	public static void validate(HttpServletRequest request) throws IOException, UtilException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			throw new UtilException("User session time out.");
		}

		User user = (User) session.getAttribute("user");

		if (user == null) {

			throw new UtilException("User not logged in.");
		}

	}

	public static void validateRoleUser(HttpServletRequest request) throws IOException, UtilException {
		
		User user = (User) request.getSession(false).getAttribute("user");
		
		if (user.getRole().equals("user")) {

			throw new UtilException("Not enough rights.");
		}

	}

}
