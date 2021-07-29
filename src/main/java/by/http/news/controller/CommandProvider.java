package by.http.news.controller;

import java.util.HashMap;
import java.util.Map;

import by.http.news.controller.impl.GoToUserAnswerPage;
import by.http.news.controller.impl.GoToUserDeletePage;
import by.http.news.controller.impl.GoToUserPasswordPage;
import by.http.news.controller.impl.GoToAuthorizationPage;
import by.http.news.controller.impl.GoToMainPage;
import by.http.news.controller.impl.GoToNewsAddPage;
import by.http.news.controller.impl.GoToNewsToolsPage;
import by.http.news.controller.impl.GoToNewsUpdatePage;
import by.http.news.controller.impl.GoToNewsAnswerPage;
import by.http.news.controller.impl.GoToNewsChoosePage;
import by.http.news.controller.impl.GoToNewsDeletePage;
import by.http.news.controller.impl.GoToRegistrationPage;
import by.http.news.controller.impl.GoToUserToolsPage;
import by.http.news.controller.impl.GoToUserUpdatePage;
import by.http.news.controller.impl.LoggedOut;
import by.http.news.controller.impl.NewsOperADD;
import by.http.news.controller.impl.NewsOperChoose;
import by.http.news.controller.impl.NewsOperDelete;
import by.http.news.controller.impl.NewsOperUpdate;
import by.http.news.controller.impl.UnknownCommand;
import by.http.news.controller.impl.UserAuthorization;
import by.http.news.controller.impl.UserOperDelete;
import by.http.news.controller.impl.UserOperPassword;
import by.http.news.controller.impl.UserOperUpdate;
import by.http.news.controller.impl.UserRegistration;
import by.http.news.util.LogWriter;

public class CommandProvider {

	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandProvider() {
		
		commands.put(CommandName.INDEX, new GoToMainPage());
		commands.put(CommandName.MAIN, new GoToMainPage());
		
		commands.put(CommandName.AUTHORIZATION, new GoToAuthorizationPage());
		commands.put(CommandName.REGISTRATION, new GoToRegistrationPage());
		commands.put(CommandName.USER_TOOLS, new GoToUserToolsPage());
		commands.put(CommandName.USER_ANSWER, new GoToUserAnswerPage());
		commands.put(CommandName.USER_AUTHORIZATION, new UserAuthorization());
		commands.put(CommandName.USER_REGISTRATION, new UserRegistration());
		commands.put(CommandName.USER_TOOLS_UPDATE, new GoToUserUpdatePage());
		commands.put(CommandName.USER_TOOLS_DELETE, new GoToUserDeletePage());
		commands.put(CommandName.USER_TOOLS_PASSWORD, new GoToUserPasswordPage());
		commands.put(CommandName.USER_UPDATE, new UserOperUpdate());
		commands.put(CommandName.USER_DELETE, new UserOperDelete());
		commands.put(CommandName.USER_PASSWORD, new UserOperPassword());
		commands.put(CommandName.LOGGEDOUT, new LoggedOut());
		
		commands.put(CommandName.NEWS_TOOLS, new GoToNewsToolsPage());
		commands.put(CommandName.NEWS_TOOLS_ADD, new GoToNewsAddPage());
		commands.put(CommandName.NEWS_TOOLS_UPDATE, new GoToNewsUpdatePage());
		commands.put(CommandName.NEWS_TOOLS_DELETE, new GoToNewsDeletePage());
		commands.put(CommandName.NEWS_TOOLS_CHOOSE, new GoToNewsChoosePage());
		commands.put(CommandName.NEWS_ADD, new NewsOperADD());
		commands.put(CommandName.NEWS_UPDATE, new NewsOperUpdate());
		commands.put(CommandName.NEWS_DELETE, new NewsOperDelete());
		commands.put(CommandName.NEWS_CHOOSE, new NewsOperChoose());
		commands.put(CommandName.NEWS_ANSWER, new GoToNewsAnswerPage());
		
		commands.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());
		
	}

	public Command findCommand(String name) {

		if (name == null || name.isEmpty() || name.isBlank()) {

			name = CommandName.UNKNOWN_COMMAND.toString();
		}

		CommandName commandName;
		
		try {
			
			commandName = CommandName.valueOf(name.toUpperCase());
			
		} catch (IllegalArgumentException e) {
			
			LogWriter.writeLog(e);
			
			commandName = CommandName.UNKNOWN_COMMAND;
		}

		return commands.get(commandName);
	}

}
