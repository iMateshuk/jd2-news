package by.project.news.service;

import by.project.news.service.impl.NewsServiceImpl;
import by.project.news.service.impl.UserServiceImpl;

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
