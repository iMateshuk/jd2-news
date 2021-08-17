package by.project.news.controller.impl;

import java.io.IOException;

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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewsOperSgnAuthor implements Command {

	private static final NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandSGN = CommandName.NEWS_SGNAUTHOR.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private static final String USER = "user";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandSGN);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);
	private final static String REDIRECT_EX = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandSGN)
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
			
			newsServices.sgnAuthor(BeanCreator.createNews(request), (User) request.getSession(false).getAttribute(USER));

			response.sendRedirect(REDIRECT);
			
		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_EX.concat(e.getMessage()));
		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_EX.concat("commonerror"));
		}

	}

}
