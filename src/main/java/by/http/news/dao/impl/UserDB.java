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
import by.http.news.util.UtilException;

public class UserDB implements UserDAO {

	private static final Creator<User, UserData> CREATOR = CreatorProvider.getCreatorProvider().getUserCreator();

	private static final String DRIVER = "org.gjt.mm.mysql.Driver";

	private final static String SERVER = "jdbc:mysql://127.0.0.1/myusers?useSSL=false";
	private final static String USER = "localUser";
	private final static String PASSWORD = "localPassw0rd";

	private final static String WHERE_LOGIN = "WHERE login=?";

	private final static String SQL_REGISTRATION = "INSERT INTO users(name,login,password,email,role,age) VALUES(?,?,?,?,?,?)";
	private final static String SQL_USER_UPDATE = "UPDATE users SET name=?, email=?, role=? " + WHERE_LOGIN;
	private final static String SQL_PASSWORD_UPDATE = "UPDATE users SET password=? " + WHERE_LOGIN;
	private final static String SQL_SELECT = "SELECT * FROM users WHERE login='";
	private final static String SQL_DELETE = "DELETE FROM users " + WHERE_LOGIN;

	private final static String TRHOW_USER_INCORRECT = "Wrong user data!";

	private final static String COLLUM_LABEL_PASSWORD = "password";
	private final static String COLLUM_LABEL_AGE = "age";
	private final static String COLLUM_LABEL_ROLE = "role";

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

			PreparedStatement ps = con.prepareStatement(SQL_REGISTRATION);

			ps.setString(1, userData.getName());
			ps.setString(2, userData.getLogin());
			ps.setString(3, userData.getPassword());
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

			PreparedStatement ps = con.prepareStatement(SQL_USER_UPDATE);

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
			
			PreparedStatement ps = con.prepareStatement(SQL_DELETE);
			
			ps.setString(1, userData.getLogin());

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public User authorization(UserData userData) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			ResultSet rs = con.createStatement().executeQuery(SQL_SELECT + userData.getLogin() + "'");

			if (rs.next() && rs.getString(COLLUM_LABEL_PASSWORD).equals(userData.getPassword())) {

				userData.setAge(rs.getString(COLLUM_LABEL_AGE));
				userData.setRole(rs.getString(COLLUM_LABEL_ROLE));

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

			PreparedStatement ps = con.prepareStatement(SQL_PASSWORD_UPDATE);
			
			ps.setString(1, userData.getPassword());
			ps.setString(2, userData.getLogin());
			
			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

}
