package by.project.news.service.impl;

import java.util.List;

import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.dao.DAOException;
import by.project.news.dao.DAOProvider;
import by.project.news.dao.UserDAO;
import by.project.news.service.ServiceException;
import by.project.news.service.UserService;
import by.project.news.util.CheckField;
import by.project.news.util.Generator;
import by.project.news.util.UserDataField;
import by.project.news.util.UserField;
import by.project.news.util.UtilException;

public class UserServiceImpl implements UserService {

	private static final DAOProvider provider = DAOProvider.getInstance();

	private static final UserDAO userDAO = provider.getUserDAO();

	private static final String EXP_SYMBOLS = ".*\\W+.*";
	private static final String EXP_EMAIL = "[\\w_+-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,4}";

	private static final int FIELD_LENGHT = 3;
	private static final int AGE_MIN_LENGHT = 1;
	private static final int AGE_MAX_LENGHT = 3;

	@Override
	public void registration(UserData userData) throws ServiceException {

		try {

			checkField(userData);

			userData.setPassword(Generator.genStringHash(userData.getPassword()));

			userDAO.registration(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void update(UserData userData) throws ServiceException {

		try {

			String key = UserDataField.NAME.toString();
			String value = userData.getName();

			CheckField.checkKVN(key, value);
			CheckField.checkKVE(key, value, EXP_SYMBOLS);
			CheckField.checkKVLMin(key, value, FIELD_LENGHT);

			key = UserDataField.EMAIL.toString();
			value = userData.getEmail();

			CheckField.checkKVN(key, value);
			CheckField.checkKVEnot(key, value, EXP_EMAIL);

			userDAO.update(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void delete(UserData userData) throws ServiceException {

		try {

			String key = UserDataField.LOGIN.toString();
			String value = userData.getLogin();

			CheckField.checkKVLMin(key, value, FIELD_LENGHT);
			CheckField.checkKVE(key, value, EXP_SYMBOLS);

			userDAO.delete(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void password(UserData userData) throws ServiceException {

		try {

			CheckField.checkKVLMin(UserDataField.PASSWORD.toString(), userData.getPassword(), 8);

			userData.setPassword(Generator.genStringHash(userData.getPassword()));

			userDAO.password(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public User authorization(UserData userData) throws ServiceException {

		try {

			String key = UserDataField.LOGIN.toString();
			String value = userData.getLogin();

			CheckField.checkKVLMin(key, value, FIELD_LENGHT);
			CheckField.checkKVE(key, value, EXP_SYMBOLS);

			CheckField.checkKVLMin(UserDataField.PASSWORD.toString(), userData.getPassword(), FIELD_LENGHT);

			userData.setPassword(Generator.genStringHash(userData.getPassword()));

			return userDAO.authorization(userData);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public UserData loadUserData(User user) throws ServiceException {

		try {

			return userDAO.loadUserData(user);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserData> loadSgnAuthor(User user) throws ServiceException {

		try {

			String key = UserField.LOGIN.toString();
			String value = user.getLogin();

			CheckField.checkKVLMin(key, value, FIELD_LENGHT);
			CheckField.checkKVE(key, value, EXP_SYMBOLS);

			return userDAO.loadSgnAuthor(user);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	private void checkField(UserData userData) throws UtilException {

		String key = UserDataField.LOGIN.toString();
		String value = userData.getLogin();

		CheckField.checkKVN(key, value);
		CheckField.checkKVLMin(key, value, FIELD_LENGHT);
		CheckField.checkKVE(key, value, EXP_SYMBOLS);

		key = UserDataField.AGE.toString();
		value = userData.getAge();

		CheckField.checkKVN(key, value);
		CheckField.checkKVLMin(key, value, AGE_MIN_LENGHT);
		CheckField.checkKVLMax(key, value, AGE_MAX_LENGHT);
		CheckField.checkKI(key, value);

		key = UserDataField.PASSWORD.toString();
		value = userData.getPassword();

		CheckField.checkKVN(key, value);
		CheckField.checkKVLMin(key, value, FIELD_LENGHT);

		value = userData.getEmail();

		if (!CheckField.checkKVN(value)) {

			CheckField.checkKVEnot(UserDataField.EMAIL.toString(), value, EXP_EMAIL);
		}

		value = userData.getName();

		if (!CheckField.checkKVN(value)) {

			CheckField.checkKVE(UserDataField.NAME.toString(), value, EXP_SYMBOLS);
		}

	}

}
