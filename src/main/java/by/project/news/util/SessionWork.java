package by.project.news.util;

import java.io.IOException;

import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionWork {

	private static final String USER = "user";

	private final static String ATTRIBUTE_NEWSES = "newses";
	private final static String MAX_PAGES = "maxPages";

	private final static String ATTRIBUTE_SESSION_SEARCH_NEWS = "searchNews";
	private final static String ATTRIBUTE_SESSION_NEWS_SGN = "sgnNews";
	private final static String COMMAND_SAVE = "cmdSave";
	private final static String PAGE = "page";

	public static void validateSession(HttpSession session) throws IOException, UtilException {

		if (session == null) {

			throw new UtilException("Session is timeout :: swvs");
		}

		User user = (User) session.getAttribute(USER);

		if (user == null) {

			throw new UtilException("User in session is null :: swvsu");
		}
	}

	public static void validateRoleUser(HttpSession session) throws IOException, UtilException {

		User user = (User) session.getAttribute(USER);

		if (user.getRole().equals(USER)) {

			throw new UtilException("User has wrong role :: swvru");
		}
	}

	public static void cleanAttributes(HttpSession session) {

		session.setAttribute(ATTRIBUTE_SESSION_SEARCH_NEWS, null);
		session.setAttribute(ATTRIBUTE_SESSION_NEWS_SGN, null);
		session.setAttribute(COMMAND_SAVE, null);
		session.setAttribute(PAGE, null);
	}

	public static Integer takePage(HttpServletRequest request, HttpSession session) {

		Integer page;

		try {

			page = Integer.parseInt((String) request.getParameter(PAGE));
		} catch (NumberFormatException | NullPointerException ignore) {

			page = (Integer) session.getAttribute(PAGE);
		}

		if (page == null) {

			page = 1;
		}

		return page;
	}

	public static void setAttributePagination(NewsData newsData, HttpServletRequest request, HttpSession session) {

		final int recordsPerPage = newsData.getRecordsPerPage();
		int maxPages = 1;

		session.setAttribute(PAGE, newsData.getPage());
		request.setAttribute(ATTRIBUTE_NEWSES, newsData.getNewses());

		if (!CheckField.thisValueNull(recordsPerPage) && recordsPerPage > 0) {

			maxPages = (int) Math.ceil(newsData.getMaxNewses() * 1.0 / recordsPerPage);
		}

		request.setAttribute(MAX_PAGES, maxPages);
	}
}
