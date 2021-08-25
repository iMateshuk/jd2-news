package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.bean.News;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.util.SessionWork;
import by.project.news.util.LogWriter;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsViewPage implements Command {

	private final static String PATH = "/WEB-INF/jsp/".concat(CommandName.NEWS_VIEW.toString().toLowerCase())
			.concat(".jsp");

	private final static NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String PARAM_TITLE = "title";
	private final static String ATTRIBUTE_NEWS = "news";

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandView = CommandName.NEWS_VIEW.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String REDIRECT_ERROR = COMMAND.concat(commandAnswer).concat(MESSAGE);
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandView)
			.concat(MESSAGE);

	private final static String REDIRECT_SESSION = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSession(session);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SESSION.concat("usersessiontimeout"));
			return;
		}

		String title = (String) request.getParameter(PARAM_TITLE);

		if (title == null) {

			title = (String) session.getAttribute(PARAM_TITLE);
		} else {

			session.setAttribute(PARAM_TITLE, title);
		}

		if (title == null) {

			response.sendRedirect(REDIRECT_ERROR.concat("commonerror"));
			return;
		}

		try {

			request.setAttribute(ATTRIBUTE_NEWS,
					newsServices.chooseNews(new News.NewsBuilder().setTitle(title).build()));

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(Parser.excRemovePath(e.getMessage())));
		}

	}

}
