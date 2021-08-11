package by.project.news.controller;

import java.util.HashMap;
import java.util.Map;

import by.project.news.controller.impl.ChangeLocal;
import by.project.news.controller.impl.GoToAuthorizationPage;
import by.project.news.controller.impl.GoToMainPage;
import by.project.news.controller.impl.GoToNewsAddPage;
import by.project.news.controller.impl.GoToNewsAnswerPage;
import by.project.news.controller.impl.GoToNewsChoosePage;
import by.project.news.controller.impl.GoToNewsDeletePage;
import by.project.news.controller.impl.GoToNewsToolsPage;
import by.project.news.controller.impl.GoToNewsUpdatePage;
import by.project.news.controller.impl.GoToRegistrationPage;
import by.project.news.controller.impl.GoToUserAnswerPage;
import by.project.news.controller.impl.GoToUserDeletePage;
import by.project.news.controller.impl.GoToUserPasswordPage;
import by.project.news.controller.impl.GoToUserToolsPage;
import by.project.news.controller.impl.GoToUserUpdatePage;
import by.project.news.controller.impl.LoggedOut;
import by.project.news.controller.impl.NewsOperADD;
import by.project.news.controller.impl.NewsOperChoose;
import by.project.news.controller.impl.NewsOperDelete;
import by.project.news.controller.impl.NewsOperUpdate;
import by.project.news.controller.impl.UnknownCommand;
import by.project.news.controller.impl.UserAuthorization;
import by.project.news.controller.impl.UserOperDelete;
import by.project.news.controller.impl.UserOperPassword;
import by.project.news.controller.impl.UserOperUpdate;
import by.project.news.controller.impl.UserRegistration;
import by.project.news.util.LogWriter;

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
		
		commands.put(CommandName.CHANGE_LOCAL, new ChangeLocal());
		
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
