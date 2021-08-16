package by.project.news.controller.impl;

import java.io.IOException;
import java.util.List;

import by.project.news.bean.News;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.util.LogWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToMainPage implements Command {

	private static String COMMAND = CommandName.MAIN.toString().toLowerCase();

	private final static String PATH = "/WEB-INF/jsp/".concat(COMMAND).concat(".jsp");
	private final static NewsService newsService = ServiceProvider.getInstance().getNewsService();

	private final static String CommandChoose = CommandName.NEWS_CHOOSE.toString().toLowerCase();

	private final static String CLEAN = "clean";
	private final static String SESSION_NEWS_SEARCH = "searchNews";

	private final static String PARAM_TITLE = "title";

	private final static String ATTRIBUTE_NEWSES = "newses";
	private final static String ATTRIBUTE_MESSAGE = "message";

	private final static String REDIRECT = "Controller?command=".concat(CommandChoose);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (request.getParameter(CLEAN) != null) {

			session.setAttribute(SESSION_NEWS_SEARCH, null);
		}

		if (session.getAttribute(SESSION_NEWS_SEARCH) != null) {

			response.sendRedirect(REDIRECT);
			return;
		}

		if (session.getAttribute(PARAM_TITLE) != null) {

			session.setAttribute(PARAM_TITLE, null);
		}

		List<News> newses = null;

		try {

			newses = newsService.load();

			request.setAttribute(ATTRIBUTE_NEWSES, newses);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			request.setAttribute(ATTRIBUTE_MESSAGE, e.getMessage());
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
		requestDispatcher.forward(request, response);

	}

}
