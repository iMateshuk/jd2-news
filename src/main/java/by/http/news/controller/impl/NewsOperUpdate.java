package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.News;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.service.NewsService;
import by.http.news.service.ServiceException;
import by.http.news.service.ServiceProvider;
import by.http.news.util.CheckSession;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.LogWriter;
import by.http.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewsOperUpdate implements Command {

	private static final NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static Creator<News, HttpServletRequest> CREATOR = CreatorProvider.getCreatorProvider()
			.getNewsDataCreator();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandUpdate = CommandName.NEWS_UPDATE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/".concat(commandAnswer).concat(".jsp");

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUpdate);
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUpdate)
			.concat(MESSAGE);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

			CheckSession.validateRoleUser(request);

			News news = CREATOR.create(request);

			newsServices.update(news);

			response.sendRedirect(REDIRECT);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(e.getMessage()));
		}

	}

}
