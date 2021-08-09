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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserRegistration implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static Creator<UserData, HttpServletRequest> CREATOR = CreatorProvider.getCreatorProvider()
			.getUserDataCreator();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandAutho = CommandName.AUTHORIZATION.toString().toLowerCase();
	private final static String commandReg = CommandName.REGISTRATION.toString().toLowerCase();

	final static String PATH = "/WEB-INF/jsp/".concat(commandAnswer).concat(".jsp");

	private final static String ATTRIBUTE_USER = "user";
	private final static String ROLE_ADMIN = "admin";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SESSION = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandReg)
			.concat(MESSAGE).concat("User session time out.");
	private final static String REDIRECT_USER = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandReg)
			.concat(MESSAGE).concat(commandReg).concat(" success for ");
	private final static String REDIRECT_USER_NEW = COMMAND.concat(commandAutho).concat(MESSAGE).concat(commandReg)
			.concat(" success for ");
	private final static String REDIRECT = COMMAND.concat(CommandName.INDEX.toString().toLowerCase());
	private final static String REDIRECT_EX = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandReg)
			.concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.sendRedirect(REDIRECT_SESSION);
			return;
		}

		String redirect = REDIRECT;

		try {

			UserData userData = CREATOR.create(request);

			userService.registration(userData);

			User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

			if (user != null && user.getRole().equals(ROLE_ADMIN)) {

				redirect = REDIRECT_USER;
			} else {

				redirect = REDIRECT_USER_NEW;
			}

			redirect = redirect.concat(userData.getLogin());

		} catch (ServiceException | UtilException e) {

			LogWriter.writeLog(e);
			redirect = REDIRECT_EX.concat(e.getMessage());

		}

		response.sendRedirect(redirect);

	}

}
