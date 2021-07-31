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
import by.http.news.util.View;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserOperUpdate implements Command {

	private final static Creator<UserData, HttpServletRequest> CREATOR = CreatorProvider.getCreatorProvider()
			.getUserDataCreator();

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserUpdate = CommandName.USER_UPDATE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

			User user = (User) request.getSession().getAttribute("user");

			UserData userData = CREATOR.create(request);

			if (!userData.getRole().equals(user.getRole()) && !user.getRole().equals("admin")) {

				userData.setRole(user.getRole());
			}

			View.print(userData);

			if (!(user.getLogin().equals(userData.getLogin()) || user.getRole().equals("admin"))) {

				response.sendRedirect("Controller?command=" + commandAnswer + "&message=Wrong Login for update!&action="
						+ commandUserUpdate);
				return;
			}

			userService.update(userData);

			response.sendRedirect("Controller?command=" + commandAnswer + "&message=" + userData.getLogin()
					+ " update success!&action=" + commandUserUpdate);

		} catch (ServiceException e) {

			LogWriter.writeLog(e);
			response.sendRedirect("Controller?command=" + commandAnswer + "&message=" + e.getMessage() + "&action="
					+ commandUserUpdate);

		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect("Controller?command=" + commandAuth + "&message=" + e.getMessage());
		}

	}

}
