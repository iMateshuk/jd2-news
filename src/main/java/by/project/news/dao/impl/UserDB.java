package by.project.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.dao.DAOException;
import by.project.news.dao.UserDAO;
import by.project.news.dao.util.ConnectionPool;
import by.project.news.dao.util.ConnectionPoolException;
import by.project.news.util.BeanCreator;
import by.project.news.util.UserSQL;
import by.project.news.util.UtilException;

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

			throw new DAOException("Can't regist user (sql) :: userdaoregistration", e);
		}

	}

	@Override
	public void update(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_UPDATE_USER_DATA_IF_NOT_NULL_EMPTY.getSQL());) {

			ps.setString(1, userData.getName());
			ps.setString(2, userData.getEmail());
			ps.setString(3, userData.getRole());
			ps.setString(4, userData.getAge());
			ps.setString(5, userData.getLogin());

			ps.executeUpdate();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't update user data (sql) :: userdaoupdate", e);
		}

	}

	@Override
	public void delete(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_DELETE_LOGIN.getSQL());) {

			ps.setString(1, userData.getLogin());

			if (ps.executeUpdate() != 1) {

				throw new DAOException("Can't delete user. Not exist :: userdaodeletepsf");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't delete user (sql) :: userdaodeletecon", e);

		}

	}

	@Override
	public User authorization(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_ALL_W_LOGIN__A_PASSWORD.getSQL());) {

			ps.setString(1, userData.getLogin());
			ps.setString(2, userData.getPassword());

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException("Can't auth user. Not exist :: userdaoauthorizationrs");
			}

			return BeanCreator.createUser(rs);

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't auth user (sql) :: userdaoauthorization", e);

		} catch (UtilException e) {

			throw new DAOException(e);
		}

	}

	@Override
	public void password(UserData userData) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con
						.prepareStatement(UserSQL.SQL_UPDATE_PASSWORD_SELECT_LOGIN_OLDPASS.getSQL());) {

			ps.setString(1, userData.getLogin());
			ps.setString(2, userData.getOldPassword());
			ps.setString(3, userData.getPassword());

			if (ps.executeUpdate() != 1) {

				throw new DAOException("Can't change password. Check Login and oldPass :: userdaopassword");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't change password (sql) :: userdaopassword", e);
		}

	}

	@Override
	public UserData loadUserData(User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_NAME_EMAIL_W_LOGIN.getSQL())) {

			ps.setString(1, user.getLogin());

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException("Can't load userData. User not exist :: userdaoloaduserdatars");
			}

			return new UserData.UserDataBuilder().setEmail(rs.getString(UserSQL.SQL_COLUM_LABEL_EMAIL.getSQL()))
					.setName(rs.getString(UserSQL.SQL_COLUM_LABEL_NAME.getSQL())).build();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't load userData (sql) :: userdaoloaduserdata", e);
		}
	}

	@Override
	public List<UserData> loadSgnAuthor(User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_NAME_LOGIN_W_ID_S_LOGIN.getSQL());) {

			ps.setString(1, user.getLogin());

			ResultSet rs = ps.executeQuery();

			List<UserData> usersData = new ArrayList<>();

			while (rs.next()) {

				usersData.add(
						new UserData.UserDataBuilder().setLogin(rs.getString(UserSQL.SQL_COLUM_LABEL_LOGIN.getSQL()))
								.setName(rs.getString(UserSQL.SQL_COLUM_LABEL_NAME.getSQL())).build());
			}

			return usersData;

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't load data sgnAuthor (sql) :: userdaoloadsgndata", e);
		}
	}
}
