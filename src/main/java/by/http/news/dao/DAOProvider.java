package by.http.news.dao;

import by.http.news.dao.impl.NewsDB;
import by.http.news.dao.impl.UserDB;

public class DAOProvider {
	
	private static final DAOProvider instance = new DAOProvider();
	
	private final UserDAO userDAO = new UserDB();
	
	private final NewsDAO newsDAO = new NewsDB();
 	
	private DAOProvider() {}

	public static DAOProvider getInstance() {
		return instance;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public NewsDAO getNewsDAO() {
		return newsDAO;
	};
	
	

}
