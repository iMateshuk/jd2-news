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

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			CheckSession.validate(request);

			User user = (User) request.getSession().getAttribute("user");

			if (!user.getRole().equals("admin")) {

				response.sendRedirect(
						"Controller?command=" + commandAnswer + "&message=Wrong User!&action=" + commandUserDelete);
				return;
			}

			UserData userData = CREATOR.create(request);

			userService.delete(userData);

			response.sendRedirect("Controller?command=" + commandAnswer + "&message=" + userData.getLogin()
					+ " delete success!&action=" + commandUserDelete);

		} catch (ServiceException e) {
			// TODO: handle exception
			LogWriter.writeLog(e);

			response.sendRedirect("Controller?command=" + commandAnswer + "&message=" + e.getMessage() + "&action="
					+ commandUserDelete);
			
		} catch (UtilException e) {

			LogWriter.writeLog(e);
			response.sendRedirect("Controller?command=" + commandAuth + "&message=" + e.getMessage());
		}
	}

}
