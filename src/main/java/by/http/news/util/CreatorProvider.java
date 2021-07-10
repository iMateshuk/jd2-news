package by.http.news.util;

import by.http.news.bean.News;
import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.util.impl.NewsCreator;
import by.http.news.util.impl.NewsDataCreator;
import by.http.news.util.impl.UserCreator;
import by.http.news.util.impl.UserDataCreator;
import jakarta.servlet.http.HttpServletRequest;

public class CreatorProvider {

	private final static CreatorProvider INSTANCE = new CreatorProvider();

	private final Creator<User, UserData> userCreator = new UserCreator();

	private final Creator<UserData, HttpServletRequest> userDataCreator = new UserDataCreator();

	private final Creator<News, String> newsCreator = new NewsCreator();

	private final Creator<News, HttpServletRequest> newsDataCreator = new NewsDataCreator();

	private CreatorProvider() {

	}

	public static CreatorProvider getCreatorProvider() {

		return INSTANCE;
	}

	public Creator<User, UserData> getUserCreator() {

		return userCreator;
	}

	public Creator<UserData, HttpServletRequest> getUserDataCreator() {

		return userDataCreator;
	}

	public Creator<News, String> getNewsCreator() {

		return newsCreator;
	}

	public Creator<News, HttpServletRequest> getNewsDataCreator() {

		return newsDataCreator;
	}
}
