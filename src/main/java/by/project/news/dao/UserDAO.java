package by.project.news.dao;

import java.util.List;

import by.project.news.bean.User;
import by.project.news.bean.UserData;

public interface UserDAO {

	void registration(UserData userData) throws DAOException;

	void update(UserData userData) throws DAOException;

	void delete(UserData userData) throws DAOException;
	
	void password(UserData userData) throws DAOException;
	
	User authorization(UserData userData) throws DAOException;
	
	List<UserData> loadSgnAuthor(User user) throws DAOException;
	
	UserData loadUserData(User user) throws DAOException;

}
