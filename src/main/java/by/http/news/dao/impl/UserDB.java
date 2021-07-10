package by.http.news.dao.impl;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.dao.DAOException;
import by.http.news.dao.UserDAO;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.Generator;
import by.http.news.util.UtilException;

public class UserDB implements UserDAO {
	
	private static final Creator<User, UserData> CREATOR = CreatorProvider.getCreatorProvider().getUserCreator();

	@Override
	public void registration(UserData userData) throws DAOException {
		// TODO Auto-generated method stub
		if (userData.getLogin().equals("Admin") || !userData.getLogin().equals("qwer")) {

			throw new DAOException("Sorry, user dao registration section under construction");
		}
		
	}

	@Override
	public void update(UserData userData) throws DAOException {
		// TODO Auto-generated method stub
		throw new DAOException("Sorry, user dao update section under construction");

	}

	@Override
	public void delete(UserData userData) throws DAOException {
		// TODO Auto-generated method stub
		throw new DAOException("Sorry, user dao delete section under construction");

	}

	@Override
	public User authorization(UserData userData) throws DAOException {
		// TODO Auto-generated method stub
		if (userData.getLogin().equals("qwer")) {
			
			userData.setAge(Generator.genNumber(2));

			try {
				
				return CREATOR.create(userData);
			} catch (UtilException e) {
				
				throw new DAOException(e.getMessage(), e);
			}
		}

		if (userData.getLogin().equals("Admin")) {
			
			userData.setRole("admin");
			userData.setAge("99");

			try {
				
				return CREATOR.create(userData);
			} catch (UtilException e) {
				
				throw new DAOException(e.getMessage(), e);
			}
		}

		throw new DAOException("Sorry, user dao authorization section under construction");

	}

	@Override
	public void password(UserData userData) throws DAOException {
		// TODO Auto-generated method stub
		throw new DAOException("Sorry, user dao password section under construction");
		
	}

}
