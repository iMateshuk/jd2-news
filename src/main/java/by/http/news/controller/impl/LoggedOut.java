package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.User;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggedOut implements Command {

	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandLoggedOut = CommandName.LOGGEDOUT.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/" + commandMain + ".jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		User user = (User) request.getSession().getAttribute("user");

		String redirect = "Controller?command=" + commandAnswer + "&message=" + user.getLogin() + " "
				+ " logged out!&action=" + commandLoggedOut;

		request.getSession().invalidate();

		response.sendRedirect(redirect);

	}

}
