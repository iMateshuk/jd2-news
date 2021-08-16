package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.service.UserService;
import by.project.news.util.BeanCreator;
import by.project.news.util.CheckSession;
import by.project.news.util.LogWriter;
import by.project.news.util.UtilException;
import by.project.news.util.View;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserOperUpdate implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserUpdate = CommandName.USER_UPDATE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String ATTRIBUTE_USER = "user";
	private final static String ROLE_ADMIN = "admin";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SESSION = COMMAND.concat(commandAnswer).concat(ACTION)
			.concat(commandUserUpdate).concat(MESSAGE);
	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION)
			.concat(commandUserUpdate).concat(MESSAGE);
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserUpdate)
			.concat(MESSAGE);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("usersessiontimeout"));
		}
		
		try {


			User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

			UserData userData = BeanCreator.createUserData(request);

			if (!userData.getRole().equals(user.getRole()) && !user.getRole().equals(ROLE_ADMIN)) {

				userData.setRole(user.getRole());
			}

			View.print(userData);

			if (!(user.getLogin().equals(userData.getLogin()) || user.getRole().equals(ROLE_ADMIN))) {

				response.sendRedirect(REDIRECT_SESSION.concat("wronguserlogin"));
				return;
			}

			userService.update(userData);

			response.sendRedirect(REDIRECT.concat("userdatasuccess"));

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("commonerror"));
		}

	}

}