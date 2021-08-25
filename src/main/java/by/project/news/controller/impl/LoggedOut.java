package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggedOut implements Command {

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandLoggedOut = CommandName.LOGGEDOUT.toString().toLowerCase();

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=loggedoutuser";
	private final static String ACTION = "&action=";

	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandLoggedOut)
			.concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession().invalidate();

		response.sendRedirect(REDIRECT);
	}
}
