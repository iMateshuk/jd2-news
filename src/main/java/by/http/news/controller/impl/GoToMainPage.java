package by.http.news.controller.impl;

import java.io.IOException;
import java.util.List;

import by.http.news.bean.News;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.service.NewsService;
import by.http.news.service.ServiceException;
import by.http.news.service.ServiceProvider;
import by.http.news.util.Local;
import by.http.news.util.LogWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToMainPage implements Command {

	final static String PATH = "/WEB-INF/jsp/" + CommandName.MAIN.toString().toLowerCase() + ".jsp";
	private final static NewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private final static String CommandChoose = CommandName.NEWS_CHOOSE.toString().toLowerCase();

	private final static String CLEAN = "clean";
	private final static String SESSION_NEWS_SEARCH = "searchNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Local.setLocal(request);
		
		HttpSession session = request.getSession(false);

		if (request.getParameter(CLEAN) != null) {

			session.setAttribute(SESSION_NEWS_SEARCH, null);
		}

		if (session.getAttribute(SESSION_NEWS_SEARCH) != null) {
			
			response.sendRedirect("Controller?command=" + CommandChoose);
			return;

		}

		List<News> newses = null;

		try {

			newses = newsService.load();

			request.setAttribute("newses", newses);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			request.setAttribute("message", e.getMessage());
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
		requestDispatcher.forward(request, response);

	}

}
