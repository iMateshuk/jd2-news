package by.project.news.service;

import java.util.List;

import by.project.news.bean.User;
import by.project.news.bean.UserData;

public interface UserService {

	void registration(UserData userData) throws ServiceException;

	void update(UserData userData) throws ServiceException;

	void delete(UserData userData) throws ServiceException;
	
	void password(UserData userData) throws ServiceException;
	
	User authorization(UserData userData) throws ServiceException;
	
	List<UserData> loadSgnAuthor(User user) throws ServiceException;
	
	UserData loadUserData(User user) throws ServiceException;

}
