package by.http.news.service.impl;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.dao.DAOException;
import by.http.news.dao.DAOProvider;
import by.http.news.dao.UserDAO;
import by.http.news.service.ServiceException;
import by.http.news.service.UserService;
import by.http.news.util.CheckField;
import by.http.news.util.UserDataField;
import by.http.news.util.UtilException;

public class UserServiceImpl implements UserService {

	private static final DAOProvider provider = DAOProvider.getInstance();

	private static final UserDAO userDAO = provider.getUserDAO();

	private static final String EXP_LOGIN = "[^.*\\w+.*]";
	private static final String EXP_EMAIL = "^\\w{3}\\w*@\\w{3}\\w*\\.\\w{2}\\w*$";

	@Override
	public void registration(UserData userData) throws ServiceException {

		checkField(userData);

		try {

			userDAO.registration(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public void update(UserData userData) throws ServiceException {

		userData.setPassword("noNeedToUpdate");

		checkField(userData);

		try {

			userDAO.update(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(UserData userData) throws ServiceException {

		try {
			
			CheckField.checkKVLMin(UserDataField.LOGIN.toString(), userData.getLogin(), 3);

			userDAO.delete(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public void password(UserData userData) throws ServiceException {

		try {
			
			CheckField.checkKVLMin(UserDataField.PASSWORD.toString(), userData.getPassword(), 8);

			userDAO.password(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public User authorization(UserData userData) throws ServiceException {

		try {
			
			CheckField.checkKVLMin(UserDataField.LOGIN.toString(), userData.getLogin(), 3);
			CheckField.checkKVE(UserDataField.LOGIN.toString(), userData.getLogin(), EXP_LOGIN);
			CheckField.checkKVLMin(UserDataField.PASSWORD.toString(), userData.getPassword(), 3);

			return userDAO.authorization(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	private void checkField(UserData userData) throws ServiceException {

		try {

			CheckField.checkKVLMin(UserDataField.AGE.toString(), userData.getAge(), 1);
			
			CheckField.checkKVLMax(UserDataField.AGE.toString(), userData.getAge(), 3);

			CheckField.checkKI(UserDataField.AGE.toString(), userData.getAge());

			CheckField.checkKVLMin(UserDataField.PASSWORD.toString(), userData.getPassword(), 3);

			CheckField.checkKVLMin(UserDataField.LOGIN.toString(), userData.getLogin(), 3);

			CheckField.checkKVE(UserDataField.LOGIN.toString(), userData.getLogin(), EXP_LOGIN);

			CheckField.checkKVE(UserDataField.EMAIL.toString(), userData.getEmail(), EXP_EMAIL);

		} catch (UtilException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

}
