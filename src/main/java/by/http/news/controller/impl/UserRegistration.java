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

	final static String PATH = "/WEB-INF/jsp/" + commandAnswer + ".jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.sendRedirect(
					"Controller?command=" + commandAnswer + "&message=User session time out.&action=" + commandReg);
			return;
		}

		String redirect = "Controller?command=" + CommandName.INDEX.toString().toLowerCase();

		try {
			
			UserData userData = CREATOR.create(request);

			userService.registration(userData);

			User user = (User) request.getSession().getAttribute("user");

			if (user != null && user.getRole().equals("admin")) {

				redirect = "Controller?command=" + commandAnswer + "&message=" + userData.getLogin() + " " + commandReg
						+ " success!&action=" + commandReg;

			} else {

				redirect = "Controller?command=" + commandAutho + "&message=" + userData.getLogin() + " " + commandReg
						+ " success!";

			}

		} catch (ServiceException | UtilException e) {

			LogWriter.writeLog(e);

			redirect = "Controller?command=" + commandAnswer + "&message=" + e.getMessage() + "&action=" + commandReg;

		}

		response.sendRedirect(redirect);

	}

}
