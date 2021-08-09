package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.service.ServiceException;
import by.http.news.service.ServiceProvider;
import by.http.news.service.UserService;
import by.http.news.util.BeanCreator;
import by.http.news.util.LogWriter;
import by.http.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserAuthorization implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandAutho = CommandName.AUTHORIZATION.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/".concat(commandAnswer).concat(".jsp");

	private final static String ATTRIBUTE_USER = "user";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SESSION = COMMAND.concat(commandAutho).concat(ACTION).concat(commandAutho)
			.concat(MESSAGE).concat("User session time out.");
	private final static String REDIRECT_USER = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandAutho)
			.concat(MESSAGE).concat("Already logged in: ");
	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandAutho);
	private final static String REDIRECT_EX = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandAutho)
			.concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.sendRedirect(REDIRECT_SESSION);
			return;
		}

		User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

		if (user != null) {

			response.sendRedirect(REDIRECT_USER.concat(user.getLogin()));
			return;
		}

		try {

			UserData userData = BeanCreator.createUserData(request);

			user = userService.authorization(userData);

			session = request.getSession(true);

			session.setAttribute(ATTRIBUTE_USER, user);

			response.sendRedirect(REDIRECT);

		} catch (ServiceException | UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_EX.concat(e.getMessage()));

		}

	}

}
