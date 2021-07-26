package by.http.news.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.dao.DAOException;
import by.http.news.dao.UserDAO;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.Generator;
import by.http.news.util.UserSQL;
import by.http.news.util.UtilException;

public class UserDB implements UserDAO {

	private static final Creator<User, UserData> CREATOR = CreatorProvider.getCreatorProvider().getUserCreator();

	private static final String DRIVER = "org.gjt.mm.mysql.Driver";

	private final static String SERVER = "jdbc:mysql://127.0.0.1/myusers?useSSL=false";
	private final static String USER = "localUser";
	private final static String PASSWORD = "localPassw0rd";

	private final static String TRHOW_USER_INCORRECT = "Wrong user data!";

	{

		try {

			Class.forName(DRIVER);

		} catch (ClassNotFoundException e) {

			throw new ExceptionInInitializerError(e);
		}

	}

	@Override
	public void registration(UserData userData) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(UserSQL.SQL_INSERT_USER.getSQL());

			ps.setString(1, userData.getName());
			ps.setString(2, userData.getLogin());
			ps.setString(3, Generator.genStringHash(userData.getPassword()));
			ps.setString(4, userData.getEmail());
			ps.setString(5, userData.getRole());
			ps.setString(6, userData.getAge());

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException("Maybe user exist, check you data.", e);
		}

	}

	@Override
	public void update(UserData userData) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(UserSQL.SQL_UPDATE_USER.getSQL());

			ps.setString(1, userData.getName());
			ps.setString(2, userData.getEmail());
			ps.setString(3, userData.getRole());
			ps.setString(4, userData.getLogin());

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(UserData userData) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {
			
			String userLogin =  userData.getLogin();

			PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_LOGIN.getSQL());

			ps.setString(1, userLogin);

			if (ps.executeQuery().next()) {

				ps = con.prepareStatement(UserSQL.SQL_DELETE_LOGIN.getSQL());

				ps.setString(1, userLogin);

				ps.executeUpdate();
			} else {
				
				throw new DAOException("User login:" + userLogin + " not exist.");
			}

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public User authorization(UserData userData) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(UserSQL.SQL_SELECT_LOGIN.getSQL());

			ps.setString(1, userData.getLogin());

			ResultSet rs = ps.executeQuery();

			if (rs.next() && rs.getString(UserSQL.SQL_COLLUM_LABEL_PASSWORD.getSQL())
					.equals(Generator.genStringHash(userData.getPassword()))) {

				userData.setAge(rs.getString(UserSQL.SQL_COLLUM_LABEL_AGE.getSQL()));
				userData.setRole(rs.getString(UserSQL.SQL_COLLUM_LABEL_ROLE.getSQL()));

				return CREATOR.create(userData);
			}

		} catch (SQLException | UtilException e) {

			throw new DAOException("Check you data!", e);
		}

		throw new DAOException(TRHOW_USER_INCORRECT);
	}

	@Override
	public void password(UserData userData) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(UserSQL.SQL_UPDATE_PASSWORD.getSQL());

			ps.setString(1, Generator.genStringHash(userData.getPassword()));
			ps.setString(2, userData.getLogin());

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

}
