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

	private static final String STYLE_LIKE = "%";

	@Override
	public void add(News news) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psFirst = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());
					PreparedStatement psSecond = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS.getSQL());) {

				psFirst.setString(1, news.getTitle());

				psSecond.setString(1, news.getTitle());
				psSecond.setString(2, news.getBrief());
				psSecond.setString(3, news.getBody());
				psSecond.setString(4, news.getStyle());
				psSecond.setString(5, SDF.format(new Date()));

				if (psFirst.executeQuery().next()) {

					throw new DAOException("newsdaoaddpsf");
				}

				psSecond.executeUpdate();

			} catch (SQLException e) {

				throw new DAOException("newsdaoaddpss", e);
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsdaoaddcon", e);
		}

	}

	@Override
	public void update(News news) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psFirst = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());
					PreparedStatement psSecond = con.prepareStatement(NewsSQL.SQL_UPDATE_NEWS.getSQL());) {

				psFirst.setString(1, news.getTitle());

				psSecond.setString(1, news.getTitle());
				psSecond.setString(2, news.getBrief());
				psSecond.setString(3, news.getBody());
				psSecond.setString(4, news.getStyle());
				psSecond.setString(5, SDF.format(new Date()));

				ResultSet rs = psFirst.executeQuery();

				if (!rs.next()) {

					throw new DAOException("newsupdatepsf");
				}

				psSecond.setString(6, rs.getString(NewsSQL.SQL_COLLUM_LABEL_ID.getSQL()));

				psSecond.executeUpdate();

			} catch (SQLException e) {

				throw new DAOException("newsupdatepss", e);
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsupdatecon", e);
		}

	}

	@Override
	public void delete(News news) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psFirst = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID.getSQL());
					PreparedStatement psSecond = con.prepareStatement(NewsSQL.SQL_DELETE_NEWS_TITLE.getSQL());) {

				String newsTitle = news.getTitle();

				psFirst.setString(1, newsTitle);

				if (!psFirst.executeQuery().next()) {

					throw new DAOException("newsdaodeletepsf");
				}

				psSecond.executeUpdate();

			} catch (SQLException e) {
				
				throw new DAOException("newsdaodeletepss", e);
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsdaodeletecon", e);
		}

	}

	@Override
	public List<News> choose(News news) throws DAOException {

		String newsStyle = news.getStyle();

		String sql = NewsSQL.SQL_SELECT_CHOOSE.getSQL().concat(checkStyle(newsStyle)).concat(" ")
				.concat(NewsSQL.SQL_ORDER_BY_DATE.getSQL());

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, STYLE_LIKE.concat(news.getTitle()).concat(STYLE_LIKE));

			if (!CheckField.checkKVN(newsStyle)) {

				ps.setString(2, newsStyle);
			}

			return newsCreator(ps.executeQuery());

		} catch (SQLException | ConnectionPoolException | UtilException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	@Override
	public List<News> load() throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			return newsCreator(con.createStatement().executeQuery(NewsSQL.SQL_SELECT_FOR_LOAD.getSQL()));

		} catch (SQLException | ConnectionPoolException | UtilException e) {

			throw new DAOException(e.getMessage(), e);
		}

	}

	private String checkStyle(String newsStyle) {

		return CheckField.checkKVN(newsStyle) ? NewsSQL.SQL_NOT_IN_ADULT.getSQL() : NewsSQL.SQL_IN.getSQL();
	}

	private List<News> newsCreator(ResultSet rs) throws UtilException, SQLException {

		List<News> newses = new ArrayList<>();

		while (rs.next()) {

			newses.add(BeanCreator.createNews(rs));
		}

		return newses;
	}

}
