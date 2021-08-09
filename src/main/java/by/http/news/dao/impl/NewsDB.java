package by.http.news.dao.impl;

import java.sql.Connection;
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
import by.http.news.dao.util.ConnectionPool;
import by.http.news.dao.util.ConnectionPoolException;
import by.http.news.util.BeanCreator;
import by.http.news.util.CheckField;
import by.http.news.util.NewsSQL;
import by.http.news.util.UtilException;

public class NewsDB implements NewsDAO {

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static String ANSWER_BEGIN = "News whith title:";
	private final static String ANSWER_END_NOT = " not exist.";
	private final static String ANSWER_END_EX = " exist, check you data.";

	private static final String STYLE_LIKE = "%";

	@Override
	public void add(News news) throws DAOException {
		
		PreparedStatement ps = null;

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());

			if (ps.executeQuery().next()) {

				throw new DAOException(ANSWER_BEGIN + news.getTitle() + ANSWER_END_EX);
			}
			
			ps.close();

			ps = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS.getSQL());

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setString(5, SDF.format(new Date()));

			ps.executeUpdate();

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(ANSWER_BEGIN + news.getTitle() + ANSWER_END_EX, e);
		} finally {
			
			try {
				
				ps.close();
			} catch (SQLException e) {
				
				throw new DAOException(e.getMessage(), e);
			}
		}

	}

	@Override
	public void update(News news) throws DAOException {
		
		PreparedStatement ps = null;

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, news.getTitle());

			ResultSet resSet = ps.executeQuery();

			if (!resSet.next()) {

				throw new DAOException(ANSWER_BEGIN + news.getTitle() + ANSWER_END_NOT);
			}
			
			ps.close();

			ps = con.prepareStatement(NewsSQL.SQL_UPDATE_NEWS.getSQL());

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setString(5, SDF.format(new Date()));
			ps.setString(6, resSet.getString(NewsSQL.SQL_COLLUM_LABEL_ID.getSQL()));

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
	public void delete(News news) throws DAOException {
		
		PreparedStatement ps = null;

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			String newsTitle = news.getTitle();

			ps = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());

			ps.setString(1, newsTitle);

			if (!ps.executeQuery().next()) {

				throw new DAOException(ANSWER_BEGIN + newsTitle + ANSWER_END_NOT);
			}
			
			ps.close();

			ps = con.prepareStatement(NewsSQL.SQL_DELETE_NEWS_TITLE.getSQL());

			ps.setString(1, newsTitle);

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
	public List<News> choose(News news) throws DAOException {
		
		String newsStyle = news.getStyle();

		String sql = NewsSQL.SQL_SELECT_CHOOSE.getSQL() + " " + checkStyle(newsStyle) + " "
				+ NewsSQL.SQL_ORDER_BY_DATE.getSQL();

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, STYLE_LIKE + news.getTitle() + STYLE_LIKE);
			
			if (!CheckField.checkKVN(newsStyle)) {
				
				ps.setString(2, newsStyle);
			}

			return newsCreator(ps.executeQuery());

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public List<News> load() throws DAOException {
		
		try (Connection con = ConnectionPool.getInstance().takeConnection()) {
			
			return newsCreator(con.createStatement().executeQuery(NewsSQL.SQL_SELECT_FOR_LOAD.getSQL()));

		} catch (SQLException | ConnectionPoolException e) {

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
				
				newses.add(BeanCreator.createNews(rs));
			}

		} catch (SQLException | UtilException e) {

			throw new DAOException(e.getMessage(), e);
		}

		return newses;
	}

}
