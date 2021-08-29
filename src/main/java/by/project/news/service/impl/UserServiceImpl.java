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

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public void update(UserData userData) throws ServiceException {

		try {

			String value = userData.getName();

			if (!CheckField.thisValueNull(value)) {
				
				CheckField.checkValueExpression(value, EXP_SYMBOLS);
				CheckField.checkValueLengthMin(value, FIELD_LENGHT);
			}

			value = userData.getEmail();

			if (!CheckField.thisValueNull(value)) {
				CheckField.checkValueNotExpression(value, EXP_EMAIL);
			}

			value = userData.getAge();

			if (!CheckField.thisValueNull(value)) {

				CheckField.checkStringIsNumber(value);
			}

			userDAO.update(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}

	}

	@Override
	public void delete(UserData userData) throws ServiceException {

		try {

			String value = userData.getLogin();

			CheckField.checkValueLengthMin(value, FIELD_LENGHT);
			CheckField.checkValueExpression(value, EXP_SYMBOLS);

			userDAO.delete(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}

	}

	@Override
	public void password(UserData userData) throws ServiceException {

		try {

			String newPass = userData.getPassword();
			String oldPass = userData.getOldPassword();

			CheckField.checkValueNull(oldPass);
			CheckField.checkValueNull(newPass);

			CheckField.checkValueLengthMin(newPass, 8);

			userData.setOldPassword(Generator.genStringHash(oldPass));
			userData.setPassword(Generator.genStringHash(newPass));

			userDAO.password(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}

	}

	@Override
	public User authorization(UserData userData) throws ServiceException {

		try {

			String value = userData.getLogin();

			CheckField.checkValueLengthMin(value, FIELD_LENGHT);
			CheckField.checkValueExpression(value, EXP_SYMBOLS);

			CheckField.checkValueLengthMin(userData.getPassword(), FIELD_LENGHT);

			userData.setPassword(Generator.genStringHash(userData.getPassword()));

			return userDAO.authorization(userData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}

	}

	@Override
	public UserData loadUserData(User user) throws ServiceException {

		try {

			return userDAO.loadUserData(user);

		} catch (DAOException e) {

			throw new ServiceException(e);
		}
	}

	@Override
	public List<UserData> loadSgnAuthor(User user) throws ServiceException {

		try {

			String value = user.getLogin();

			CheckField.checkValueLengthMin(value, FIELD_LENGHT);
			CheckField.checkValueExpression(value, EXP_SYMBOLS);

			return userDAO.loadSgnAuthor(user);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}

	}

	private void checkField(UserData userData) throws UtilException {

		String value = userData.getLogin();

		CheckField.checkValueNull(value);
		CheckField.checkValueLengthMin(value, FIELD_LENGHT);
		CheckField.checkValueExpression(value, EXP_SYMBOLS);

		value = userData.getAge();

		CheckField.checkValueNull(value);
		CheckField.checkValueLengthMin(value, AGE_MIN_LENGHT);
		CheckField.checkValueLengthMax(value, AGE_MAX_LENGHT);
		CheckField.checkStringIsNumber(value);

		value = userData.getPassword();

		CheckField.checkValueNull(value);
		CheckField.checkValueLengthMin(value, FIELD_LENGHT);

		value = userData.getEmail();

		if (!CheckField.thisValueNull(value)) {

			CheckField.checkValueNotExpression(value, EXP_EMAIL);
		}

		value = userData.getName();

		if (!CheckField.thisValueNull(value)) {

			CheckField.checkValueExpression(value, EXP_SYMBOLS);
		}

	}

}
