package by.http.news.service;

import by.http.news.service.impl.NewsServiceImpl;
import by.http.news.service.impl.UserServiceImpl;

public class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider();
	
	private final UserService userService = new UserServiceImpl();
	
	private final NewsService newService = new NewsServiceImpl();
	
	private ServiceProvider() {}

	public static ServiceProvider getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}

	public NewsService getNewsService() {
		return newService;
	}
	
	
	
	

}
