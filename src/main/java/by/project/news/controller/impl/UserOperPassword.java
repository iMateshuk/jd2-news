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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserOperPassword implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserPass = CommandName.USER_PASSWORD.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String ATTRIBUTE_USER = "user";
	private final static String ROLE_ADMIN = "admin";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_USER = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserPass)
			.concat(MESSAGE);
	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION)
			.concat(commandUserPass).concat(MESSAGE);
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserPass)
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

			if (!(user.getLogin().equals(userData.getLogin()) || user.getRole().equals(ROLE_ADMIN))) {

				response.sendRedirect(REDIRECT_USER.concat("wronguserlogin"));
				return;
			}

			userService.password(userData);

			response.sendRedirect(REDIRECT.concat("userpasswordsuccess"));

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("commonerror"));
		}

	}

}
