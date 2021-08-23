package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.bean.User;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.service.UserService;
import by.project.news.util.CheckSession;
import by.project.news.util.LogWriter;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToNewsSgnPage implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String PATH = "/WEB-INF/jsp/".concat(CommandName.NEWS_TOOLS_SGN.toString().toLowerCase())
			.concat(".jsp");

	private final static String USER = "user";
	private final static String ATTRIBUTE_USER_SGN = "userSgn";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();
	private final static String commandSgn = CommandName.NEWS_TOOLS_SGN.toString().toLowerCase();
	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();

	private final static String REDIRECT_UE = COMMAND.concat(commandAuth).concat(MESSAGE);
	private final static String REDIRECT_SE = COMMAND.concat(commandAnswer).concat(ACTION).concat(commandSgn)
			.concat(MESSAGE);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

			request.setAttribute(ATTRIBUTE_USER_SGN,
					userService.loadSgnAuthor((User) request.getSession(false).getAttribute(USER)));

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_SE.concat(e.getMessage()));
		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect(REDIRECT_UE.concat("usersessiontimeout"));
		}

	}

}
