package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.controller.Command;
import by.http.news.util.Local;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocal implements Command{
	
	final static String CONTROLLER = "Controller?command=";
	final static String URL = "url";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Local.change(request);
		
		response.sendRedirect(CONTROLLER.concat((String) request.getSession().getAttribute(URL)));
		
	}

}
