package by.project.news.controller.impl;

import java.io.IOException;
import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.NewsData;
import by.project.news.bean.User;
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

public class NewsOperSgnView implements Command {

	private final static NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandSgn = CommandName.NEWS_TOOLS_SGNVIEW.toString().toLowerCase();
	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String PATH = "/WEB-INF/jsp/".concat(commandMain).concat(".jsp");

	private final static String ATTRIBUTE_NEWSES = "newses";

	private final static String CLEAN = "clean";
	private final static String USER = "user";

	private final static String PAGE = "page";
	private final static String MAX_PAGES = "maxPages";
	private final static String RECORDS_NEWSES = "recordsNewses";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String COMMAND_SAVE = "cmdSave";

	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);
	private final static String REDIRECT_EX = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandSgn)
			.concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSession(session);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("usersessiontimeout"));
			return;
		}

		if (request.getParameter(CLEAN) != null) {

			SessionWork.cleanAttributes(session);
		}

		int page = SessionWork.takePage(request, session);

		session.setAttribute(PAGE, page);

		try {

			NewsData newsData = newsServices.sgnAuthorView((User) session.getAttribute(USER),
					new NewsData.NewsDataBuilder().setPage(page).build());

			List<News> newses = newsData.getNewses();

			if (newses == null) {

				SessionWork.cleanAttributes(session);
				response.sendRedirect(REDIRECT_EX.concat("newsdaoload"));
				return;
			}

			if (!newses.isEmpty()) {

				session.setAttribute(COMMAND_SAVE, commandSgn);
			}

			request.setAttribute(ATTRIBUTE_NEWSES, newses);
			request.setAttribute(MAX_PAGES,
					(int) Math.ceil(newsData.getMaxNewses() * 1.0 / newsData.getRecordsPerPage()));
			request.setAttribute(RECORDS_NEWSES, newsData.getRecordsPerPage());

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			SessionWork.cleanAttributes(session);
			response.sendRedirect(REDIRECT_EX.concat(Parser.excRemovePath(e.getMessage())));
		}

	}

}
