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
import by.http.news.util.CheckField;
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

	private final static String ANSWER_BEGIN = "News whith title:";
	private final static String ANSWER_END_NOT = " not exist.";
	private final static String ANSWER_END_EX = " exist, check you data.";

	private static final String STYLE_LIKE = "%";

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

			if (ps.executeQuery().next()) {

				throw new DAOException(ANSWER_BEGIN + news.getTitle() + ANSWER_END_EX);
			}

			ps = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS.getSQL());

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setString(5, SDF.format(new Date()));

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(ANSWER_BEGIN + news.getTitle() + ANSWER_END_EX, e);
		}

	}

	@Override
	public void update(News news) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());

			ResultSet resSet = ps.executeQuery();

			if (!resSet.next()) {

				throw new DAOException(ANSWER_BEGIN + news.getTitle() + ANSWER_END_NOT);
			}

			ps = con.prepareStatement(NewsSQL.SQL_UPDATE_NEWS.getSQL());

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setString(5, SDF.format(new Date()));
			ps.setString(6, resSet.getString(NewsSQL.SQL_COLLUM_LABEL_ID.getSQL()));

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(News news) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			String newsTitle = news.getTitle();

			PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, newsTitle);

			if (!ps.executeQuery().next()) {

				throw new DAOException(ANSWER_BEGIN + newsTitle + ANSWER_END_NOT);
			}

			ps = con.prepareStatement(NewsSQL.SQL_DELETE_NEWS_TITLE.getSQL());

			ps.setString(1, newsTitle);

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public List<News> choose(News news) throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			String newsStyle = news.getStyle();

			String sql = NewsSQL.SQL_SELECT_CHOOSE.getSQL() + " " + checkStyle(newsStyle) + " "
					+ NewsSQL.SQL_ORDER_BY_DATE.getSQL();

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, STYLE_LIKE + news.getTitle() + STYLE_LIKE);
			
			if (!CheckField.checkKVN(newsStyle)) {
				
				ps.setString(2, newsStyle);
			}

			return newsCreator(ps.executeQuery());

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public List<News> load() throws DAOException {

		try (Connection con = DriverManager.getConnection(SERVER, USER, PASSWORD)) {

			return newsCreator(con.createStatement().executeQuery(NewsSQL.SQL_SELECT_FOR_LOAD.getSQL()));

		} catch (SQLException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	private String checkStyle(String newsStyle) {

		return CheckField.checkKVN(newsStyle) ? NewsSQL.SQL_NOT_IN_ADULT.getSQL() : NewsSQL.SQL_IN.getSQL();
	}

	private List<News> newsCreator(ResultSet rs) throws DAOException {

		List<News> newses = new ArrayList<>();

		try {

			while (rs.next()) {

				newses.add(CREATOR.create(rs));
			}

		} catch (SQLException | UtilException e) {

			throw new DAOException(e.getMessage(), e);
		}

		return newses;
	}

}
