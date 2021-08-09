package by.http.news.controller.impl;

import java.io.IOException;

import by.http.news.bean.User;
import by.http.news.controller.Command;
import by.http.news.controller.CommandName;
import by.http.news.service.ServiceException;
import by.http.news.service.ServiceProvider;
import by.http.news.service.UserService;
import by.http.news.util.CheckSession;
import by.http.news.util.LogWriter;
import by.http.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToUserUpdatePage implements Command {

	final static String PATH = "/WEB-INF/jsp/".concat(CommandName.USER_TOOLS_UPDATE.toString().toLowerCase())
			.concat(".jsp");

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserUpdate = CommandName.USER_UPDATE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();
	
	private final static String ROLE_ADMIN = "admin";
	private final static String ATTRIBUTE_USER_DATA = "userData";

	private final static String REDIRECT_SE = "Controller?command=".concat(commandAnswer).concat("&action=")
			.concat(commandUserUpdate).concat("&message=");
	private final static String REDIRECT_UE = "Controller?command=".concat(commandAuth).concat("&message=");
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

			User user = (User) request.getSession().getAttribute("user");

			if (!user.getRole().equals(ROLE_ADMIN)) {

				request.setAttribute(ATTRIBUTE_USER_DATA, userService.loadUserData(user));
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat(e.getMessage()));
		}

	}

}
