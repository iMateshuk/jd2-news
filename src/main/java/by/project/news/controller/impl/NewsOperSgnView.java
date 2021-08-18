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
import by.project.news.util.CheckSession;
import by.project.news.util.LogWriter;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewsOperSgnView implements Command {

	private static final NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandSgn = CommandName.NEWS_TOOLS_SGNVIEW.toString().toLowerCase();
	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String PATH = "/WEB-INF/jsp/".concat(commandMain).concat(".jsp");
	
	private final static String ATTRIBUTE_NEWSES = "newses";

	private static final String USER = "user";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);
	private final static String REDIRECT_EX = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandSgn)
			.concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("usersessiontimeout"));
			return;
		}

		try {

			List<News> newses = newsServices.sgnAuthorView((User) request.getSession(false).getAttribute(USER));
			
			request.setAttribute(ATTRIBUTE_NEWSES, newses);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_EX.concat(e.getMessage()));
		}

	}

}
