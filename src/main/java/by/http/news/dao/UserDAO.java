package by.http.news.dao;

import by.http.news.bean.User;
import by.http.news.bean.UserData;

public interface UserDAO {

	void registration(UserData userData) throws DAOException;

	void update(UserData userData) throws DAOException;

	void delete(UserData userData) throws DAOException;
	
	void password(UserData userData) throws DAOException;
	
	User authorization(UserData userData) throws DAOException;
	
	UserData loadUserData(User user) throws DAOException;

}
