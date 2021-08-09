package by.http.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.dao.DAOException;
import by.http.news.dao.UserDAO;
import by.http.news.dao.util.ConnectionPool;
import by.http.news.dao.util.ConnectionPoolException;
import by.http.news.util.BeanCreator;
import by.http.news.util.UserSQL;
import by.http.news.util.UtilException;

public class UserDB implements UserDAO {

	private final static String TRHOW_USER_INCORRECT = "Wrong user data!";

	private final static String ANSWER_BEGIN = "User login:";
	private final static String ANSWER_END = " exist.";
	private final static String ANSWER_END_NOT = " not exist.";
	private final static String ANSWER_CHECK_DATA = "Check you data!";

	@Override
	public void registration(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_INSERT_USER.getSQL());) {

			ps.setString(1, userData.getName());
			ps.setString(2, userData.getLogin());
			ps.setString(3, userData.getPassword());
			ps.setString(4, userData.getEmail());
			ps.setString(5, userData.getRole());
			ps.setString(6, userData.getAge());

			ps.executeUpdate();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(ANSWER_BEGIN + userData.getLogin() + ANSWER_END, e);
		} finally {

		}

	}

	@Override
	public void update(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_UPDATE_USER.getSQL());) {

			ps.setString(1, userData.getName());
			ps.setString(2, userData.getEmail());
			ps.setString(3, userData.getRole());
			ps.setString(4, userData.getLogin());

			ps.executeUpdate();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(UserData userData) throws DAOException {

		PreparedStatement ps = null;

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			String userLogin = userData.getLogin();

			ps = con.prepareStatement(UserSQL.SQL_SELECT_LOGIN.getSQL());

			ps.setString(1, userLogin);

			if (!ps.executeQuery().next()) {

				throw new DAOException(ANSWER_BEGIN + userLogin + ANSWER_END_NOT);
			}

			ps.close();

			ps = con.prepareStatement(UserSQL.SQL_DELETE_LOGIN.getSQL());

			ps.setString(1, userLogin);

			ps.executeUpdate();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(e.getMessage(), e);

		} finally {

			try {

				ps.close();

			} catch (SQLException e) {

				throw new DAOException(e.getMessage(), e);
			}
		}

	}

	@Override
	public User authorization(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_LOGIN_PASSWORD.getSQL());) {

			ps.setString(1, userData.getLogin());
			ps.setString(2, userData.getPassword());

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException(TRHOW_USER_INCORRECT);
			}

			return BeanCreator.createUser(rs);

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(ANSWER_CHECK_DATA, e);

		} catch (UtilException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public void password(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_UPDATE_PASSWORD.getSQL());) {

			ps.setString(1, userData.getPassword());
			ps.setString(2, userData.getLogin());

			ps.executeUpdate();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public UserData loadUserData(User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_LOGIN.getSQL())) {
			
			ps.setString(1, user.getLogin());
			
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException(TRHOW_USER_INCORRECT);
			}
			
			UserData userData = BeanCreator.createUserData(rs);
			
			userData.setPassword(null);
			userData.setLogin(null);
			
			return userData;

		} catch (Exception e) {

			throw new DAOException(e.getMessage(), e);
		}
	}

}
