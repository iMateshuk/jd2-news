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

			throw new DAOException("userdaoregistration", e);
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

			throw new DAOException("userdaoupdate", e);
		}

	}

	@Override
	public void delete(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psFirst = con.prepareStatement(UserSQL.SQL_SELECT_LOGIN.getSQL());
					PreparedStatement psSecond = con.prepareStatement(UserSQL.SQL_DELETE_LOGIN.getSQL());) {

				String userLogin = userData.getLogin();

				psFirst.setString(1, userLogin);

				psSecond.setString(1, userLogin);

				if (!psFirst.executeQuery().next()) {

					throw new DAOException("userdaodeletepsf");
				}

				psSecond.executeUpdate();

			} catch (SQLException e) {

				throw new DAOException("userdaodeletepss", e);
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("userdaodeletecon", e);

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

				throw new DAOException("userdaoauthorizationrs");
			}

			return BeanCreator.createUser(rs);

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("userdaoauthorization", e);

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

			throw new DAOException("userdaopassword", e);
		}

	}

	@Override
	public UserData loadUserData(User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_NAME_EMAIL.getSQL())) {

			ps.setString(1, user.getLogin());

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException("userdaoloaduserdatars");
			}

			return new UserData.UserDataBuilder().setEmail(rs.getString(UserSQL.SQL_COLLUM_LABEL_EMAIL.getSQL()))
					.setName(rs.getString(UserSQL.SQL_COLLUM_LABEL_NAME.getSQL())).build();

		} catch (Exception e) {

			throw new DAOException("userdaoloaduserdata", e);
		}
	}

}
