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
import by.project.news.util.SessionWork;
import by.project.news.util.LogWriter;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserOperDelete implements Command {

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
			.concat(commandUserDelete).concat(MESSAGE);
	private final static String REDIRECT = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserDelete)
			.concat(MESSAGE);
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandUserDelete)
			.concat(MESSAGE);
	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		try {

			SessionWork.validateSession(session);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(Parser.excRemovePath(e.getMessage())));
		}

		try {

			User user = (User) session.getAttribute(ATTRIBUTE_USER);

			if (!user.getRole().equals(ROLE_ADMIN)) {

				response.sendRedirect(REDIRECT_SESSION.concat("wronguserlogin"));
				return;
			}

			UserData userData = BeanCreator.createUserData(request);

			userService.delete(userData);

			response.sendRedirect(REDIRECT.concat("userdeletesuccess"));

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(Parser.excRemovePath(e.getMessage())));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(Parser.excRemovePath(e.getMessage())));
		}
	}

}
