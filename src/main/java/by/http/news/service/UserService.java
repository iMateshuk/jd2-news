package by.http.news.service;

import by.http.news.bean.User;
import by.http.news.bean.UserData;

public interface UserService {

	void registration(UserData userData) throws ServiceException;

	void update(UserData userData) throws ServiceException;

	void delete(UserData userData) throws ServiceException;
	
	void password(UserData userData) throws ServiceException;
	
	User authorization(UserData userData) throws ServiceException;
	
	UserData loadUserData(User user) throws ServiceException;

}
