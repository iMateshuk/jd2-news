package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.service.ServiceException;
import by.http.news.service.ServiceProvider;
import by.http.news.service.UserService;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.LogWriter;
import by.http.news.util.UtilException;
import by.http.news.util.View;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserAuthorization implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static Creator<UserData, HttpServletRequest> CREATOR = CreatorProvider.getCreatorProvider()
			.getUserDataCreator();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandAutho = CommandName.AUTHORIZATION.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/" + commandAnswer + ".jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {
			
			response.sendRedirect(
					"Controller?command=" + commandAutho + "&message=User session time out.&action=" + commandAutho);
			return;
		}

		User user = (User) request.getSession().getAttribute("user");

		if (user != null) {
			
			response.sendRedirect("Controller?command=" + commandAnswer + "&message=" + user.getLogin() + " "
					+ " already logged in!&action=" + commandAutho);
			return;

		}

		try {
			
			UserData userData = CREATOR.create(request);

			View.print(userData);

			user = userService.authorization(userData);

			View.print(user);

			session = request.getSession(true);

			session.setAttribute("user", user);

			response.sendRedirect("Controller?command=" + commandAnswer + "&action=" + commandAutho);

		} catch (ServiceException | UtilException e) {

			LogWriter.writeLog(e);

			response.sendRedirect(
					"Controller?command=" + commandAnswer + "&message=" + e.getMessage() + "&action=" + commandAutho);

		}

	}

}
