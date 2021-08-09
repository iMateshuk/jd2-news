package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.User;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggedOut implements Command {

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandLoggedOut = CommandName.LOGGEDOUT.toString().toLowerCase();
	
	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = " logged out!&action=";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");

		String redirect = COMMAND.concat(commandAnswer).concat(MESSAGE).concat(user.getLogin())
				.concat(ACTION).concat(commandLoggedOut);

		request.getSession().invalidate();

		response.sendRedirect(redirect);
	}
}
