package by.project.news.controller.impl;

import java.io.IOException;
import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.User;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.util.BeanCreator;
import by.project.news.util.CheckSession;
import by.project.news.util.LogWriter;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewsOperChoose implements Command {

	private static final NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandChoose = CommandName.NEWS_CHOOSE.toString().toLowerCase();
	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/".concat(commandMain).concat(".jsp");

	private final static String SESSION_NEWS_SEARCH = "searchNews";
	private final static String CLEAN = "clean";
	private final static String USER = "user";
	private final static String ATTRIBUTE_NEWSES = "newses";
	private final static String ATTRIBUTE_SEARCH_NEWS = "searchNews";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandChoose)
			.concat(MESSAGE);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter(CLEAN) != null) {

			request.getSession(false).setAttribute(SESSION_NEWS_SEARCH, null);
		}

		try {

			CheckSession.validate(request);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("usersessiontimeout"));
		}

		try {

			User user = (User) request.getSession(false).getAttribute(USER);

			News news = (News) request.getSession(false).getAttribute(SESSION_NEWS_SEARCH);

			if (news == null) {

				news = BeanCreator.createNews(request);
			}

			List<News> newses = newsServices.choose(news, user);

			request.setAttribute(ATTRIBUTE_NEWSES, newses);

			if (!newses.isEmpty()) {

				request.getSession(false).setAttribute(ATTRIBUTE_SEARCH_NEWS, news);
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("commonerror"));
		}

	}

}
