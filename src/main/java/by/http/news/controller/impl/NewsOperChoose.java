package by.http.news.controller.impl;

import java.io.IOException;
import java.util.List;

import by.http.news.bean.News;
import by.http.news.bean.User;
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
import by.http.news.util.View;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewsOperChoose implements Command {
	
	private static final NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static Creator<News, HttpServletRequest> CREATOR = CreatorProvider.getCreatorProvider()
			.getNewsDataCreator();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandChoose = CommandName.NEWS_CHOOSE.toString().toLowerCase();
	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/" + commandMain + ".jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			CheckSession.validate(request);
			
			User user = (User) request.getSession(false).getAttribute("user");

			News news = CREATOR.create(request);

			View.print(news);

			List<News> newses = newsServices.choose(news, user);
			
			View.print(newses);
			
			request.setAttribute("newses", newses);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

			//response.sendRedirect("Controller?command=" + commandAnswer + "&action=" + commandChoose);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(
					"Controller?command=" + commandAnswer + "&message=" + e.getMessage() + "&action=" + commandChoose);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect("Controller?command=" + commandAuth + "&message=" + e.getMessage());
		}
		
	}

}
