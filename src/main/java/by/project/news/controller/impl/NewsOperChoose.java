package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.bean.News;
import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.util.BeanCreator;
import by.project.news.util.SessionWork;
import by.project.news.util.LogWriter;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NewsOperChoose implements Command {

	private final static NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandChoose = CommandName.NEWS_CHOOSE.toString().toLowerCase();
	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String PATH = "/WEB-INF/jsp/".concat(commandMain).concat(".jsp");

	private final static String CLEAN = "clean";
	private final static String USER = "user";

	private final static String COMMAND_SAVE = "cmdSave";

	private final static String ATTRIBUTE_SESSION_SEARCH_NEWS = "searchNews";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandChoose)
			.concat(MESSAGE);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSession(session);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(Parser.excRemovePath(e.getMessage())));
			return;
		}

		try {

			if (request.getParameter(CLEAN) != null) {

				SessionWork.cleanAttributes(session);
			}

			User user = (User) session.getAttribute(USER);

			News news = (News) session.getAttribute(ATTRIBUTE_SESSION_SEARCH_NEWS);

			if (news == null) {

				news = BeanCreator.createNews(request);
			}

			NewsData newsData = newsServices.choose(news, user,
					new NewsData.NewsDataBuilder().setPage(SessionWork.takePage(request, session)).build());

			session.setAttribute(ATTRIBUTE_SESSION_SEARCH_NEWS, news);
			session.setAttribute(COMMAND_SAVE, commandChoose);

			SessionWork.setAttributePagination(newsData, request, session);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			SessionWork.cleanAttributes(session);
			response.sendRedirect(REDIRECT_SE.concat(Parser.excRemovePath(e.getMessage())));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(Parser.excRemovePath(e.getMessage())));
		}

	}

}
