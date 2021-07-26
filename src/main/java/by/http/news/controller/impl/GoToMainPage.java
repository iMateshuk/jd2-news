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

public class GoToMainPage implements Command {

	final static String PATH = "/WEB-INF/jsp/" + CommandName.MAIN.toString().toLowerCase() + ".jsp";
	private final static NewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> newses = null;

		try {

			newses = newsService.load();

			request.setAttribute("newses", newses);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);

			request.setAttribute("message", e.getMessage());
		}
		
		Local.setLocal(request);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
		requestDispatcher.forward(request, response);

	}

}
