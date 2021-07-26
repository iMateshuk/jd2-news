package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.util.Local;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToNewsForcePage implements Command{
	
	final static String PATH = "/WEB-INF/jsp/" + CommandName.NEWS_FORCE.toString().toLowerCase() + ".jsp";
	
	private static final String COMMAND_REQUEST_PARAM = "action";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Local.setLocal(request);
		
		request.setAttribute("action", request.getParameter(COMMAND_REQUEST_PARAM));
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
		requestDispatcher.forward(request, response);
	}

}
