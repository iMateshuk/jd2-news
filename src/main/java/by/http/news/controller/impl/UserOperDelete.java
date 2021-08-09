package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.service.ServiceException;
import by.http.news.service.ServiceProvider;
import by.http.news.service.UserService;
import by.http.news.util.CheckSession;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.LogWriter;
import by.http.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserOperDelete implements Command {

	private final static Creator<UserData, HttpServletRequest> CREATOR = CreatorProvider.getCreatorProvider()
			.getUserDataCreator();

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserDelete = CommandName.USER_DELETE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String ATTRIBUTE_USER = "user";
	private final static String ROLE_ADMIN = "admin";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SESSION = COMMAND.concat(commandAnswer).concat(ACTION)
			.concat(commandUserDelete).concat(MESSAGE).concat("Wrong User!");
	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserDelete)
			.concat(MESSAGE).concat("Delete success: ");
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserDelete)
			.concat(MESSAGE);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

			User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

			if (!user.getRole().equals(ROLE_ADMIN)) {

				response.sendRedirect(REDIRECT_SESSION);
				return;
			}

			UserData userData = CREATOR.create(request);

			userService.delete(userData);

			response.sendRedirect(REDIRECT.concat(userData.getLogin()));

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(e.getMessage()));
		}
	}

}
