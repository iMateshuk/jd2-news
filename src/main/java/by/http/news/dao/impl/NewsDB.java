package by.http.news.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.http.news.bean.News;
import by.http.news.dao.DAOException;
import by.http.news.dao.NewsDAO;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.NewsSQL;
import by.http.news.util.UtilException;

public class NewsDB implements NewsDAO {

	private static final Creator<News, ResultSet> CREATOR = CreatorProvider.getCreatorProvider().getNewsCreator();

	private static final String DRIVER = "org.gjt.mm.mysql.Driver";

	private final static String SERVER = "jdbc:mysql://127.0.0.1/mynews?useSSL=false";
	private final static String USER = "localUser";
	private final static String PASSWORD = "localPassw0rd";

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	{

		try {

			Class.forName(DRIVER);

		} catch (ClassNotFoundException e) {

			throw new ExceptionInInitializerError(e);
		}

	}

	@Override
	public void add(News news) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());

			ResultSet resSet = ps.executeQuery();

			if (!resSet.next()) {

				ps = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS.getSQL());

				ps.setString(1, news.getTitle());
				ps.setString(2, news.getBrief());
				ps.setString(3, news.getBody());
				ps.setString(4, news.getStyle());
				ps.setString(5, SDF.format(new Date()));

				ps.executeUpdate();

			} else {

				throw new DAOException("News whith title:" + news.getTitle() + " exist, check you data.");
			}

		} catch (SQLException e) {

			throw new DAOException("Maybe news exist, check you data.", e);
		}

	}

	@Override
	public void update(News news) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());

			ResultSet resSet = ps.executeQuery();

			if (resSet.next()) {

				ps = con.prepareStatement(NewsSQL.SQL_UPDATE_NEWS.getSQL());

				ps.setString(1, news.getTitle());
				ps.setString(2, news.getBrief());
				ps.setString(3, news.getBody());
				ps.setString(4, news.getStyle());
				ps.setString(5, SDF.format(new Date()));
				ps.setString(6, resSet.getString(NewsSQL.SQL_COLLUM_LABEL_ID.getSQL()));

				ps.executeUpdate();

			} else {

				throw new DAOException("News whith title:" + news.getTitle() + " not exist.");
			}

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(News news) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());

			if (ps.executeQuery().next()) {

				ps = con.prepareStatement(NewsSQL.SQL_DELETE_NEWS_TITLE.getSQL());

				ps.setString(1, news.getTitle());

				ps.executeUpdate();
			} else {

				throw new DAOException("News whith title:" + news.getTitle() + " not exist.");
			}

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public List<News> choose(News news) throws DAOException {

		List<News> newses = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getStyle());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				newses.add(CREATOR.create());
			}

		} catch (SQLException | UtilException e) {

			throw new DAOException(e.getMessage(), e);
		}

		return newses;
	}

	@Override
	public List<News> load() throws DAOException {

		List<News> newses = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			ResultSet rs = con.prepareStatement(NewsSQL.SQL_SELECT_DATE_FOR_LOAD.getSQL()).executeQuery();

			while (rs.next()) {

				newses.add(CREATOR.create(rs));
			}

		} catch (UtilException | SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

		return newses;
	}

}
