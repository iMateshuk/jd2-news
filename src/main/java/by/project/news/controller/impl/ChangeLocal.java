package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.controller.Command;
import by.project.news.util.Local;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocal implements Command {

	private final static String CONTROLLER = "Controller?command=";
	private final static String URL = "url";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Local.change(request);

		response.sendRedirect(CONTROLLER.concat((String) request.getSession(false).getAttribute(URL)));
	}
}
