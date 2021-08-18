package by.project.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.User;
import by.project.news.dao.DAOException;
import by.project.news.dao.NewsDAO;
import by.project.news.dao.util.ConnectionPool;
import by.project.news.dao.util.ConnectionPoolException;
import by.project.news.util.BeanCreator;
import by.project.news.util.CheckField;
import by.project.news.util.NewsSQL;
import by.project.news.util.SgnSQL;
import by.project.news.util.UtilException;

public class NewsDB implements NewsDAO {

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String STYLE_LIKE = "%";

	@Override
	public void add(News news, User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS_W_LOGIN.getSQL());) {

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setString(5, SDF.format(new Date()));
			ps.setString(6, user.getLogin());

			if (ps.executeUpdate() != 1) {

				throw new DAOException("newsdaoaddpss");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsdaoaddcon", e);
		}

	}

	@Override
	public void update(News news, User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_UPDATE_NEWS_W_TITLE.getSQL());) {

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setString(5, SDF.format(new Date()));
			ps.setString(6, user.getLogin());

			if (ps.executeUpdate() != 1) {

				throw new DAOException("newsupdatepss");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsupdatecon", e);
		}

	}

	@Override
	public void delete(News news) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psFirst = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID_W_TITLE.getSQL());
					PreparedStatement psSecond = con.prepareStatement(NewsSQL.SQL_DELETE_NEWS_ID.getSQL());) {

				String newsTitle = news.getTitle();

				psFirst.setString(1, newsTitle);

				ResultSet rs = psFirst.executeQuery();

				if (!rs.next()) {

					throw new DAOException("newsdaodeletepsf");
				}

				psSecond.setInt(1, rs.getInt(NewsSQL.SQL_COLLUM_LABEL_ID.getSQL()));

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

		String sql = NewsSQL.SQL_SELECT_ALL_W_TITLE_A_STYLE_CONCAT.getSQL().concat(checkStyle(newsStyle)).concat(" ")
				.concat(NewsSQL.SQL_ORDER_BY_DATE.getSQL());

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, STYLE_LIKE.concat(news.getTitle()).concat(STYLE_LIKE));

			if (!CheckField.checkKVN(newsStyle)) {

				ps.setString(2, newsStyle);
			}

			return newsCreator(ps.executeQuery());

		} catch (SQLException | ConnectionPoolException | UtilException e) {

			throw new DAOException("newsdaochoose", e);
		}

	}

	@Override
	public List<News> load() throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			return newsCreator(con.createStatement().executeQuery(NewsSQL.SQL_SELECT_ALL_FOR_LOAD.getSQL()));

		} catch (SQLException | ConnectionPoolException | UtilException e) {

			throw new DAOException("newsdaoload", e);
		}

	}

	@Override
	public News chooseNewsByTitle(News news) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_ALL_W_TITLE.getSQL());) {

			ps.setString(1, news.getTitle());

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException("newsdaochoosetitle");
			}

			return BeanCreator.createNews(rs);

		} catch (SQLException | ConnectionPoolException | UtilException e) {

			throw new DAOException("newsdaochoosetitle", e);
		}

	}

	@Override
	public void sgnAuthor(News news, User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(SgnSQL.SQL_INSERT_SGN_AUTHOR_W_LOGIN_TITLE.getSQL());) {

			ps.setString(1, user.getLogin());
			ps.setString(2, news.getTitle());

			if (ps.executeUpdate() != 1) {
				
				throw new DAOException("newsdaosgnauthorps");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsdaosgnauthor", e);
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
