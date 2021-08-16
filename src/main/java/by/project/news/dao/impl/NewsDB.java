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
import by.project.news.util.UserSQL;
import by.project.news.util.UtilException;

public class NewsDB implements NewsDAO {

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String STYLE_LIKE = "%";

	@Override
	public void add(News news, User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psFirst = con.prepareStatement(UserSQL.SQL_SELECT_ID_W_LOGIN.getSQL());
					PreparedStatement psSecond = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS.getSQL());) {

				psFirst.setString(1, user.getLogin());

				psSecond.setString(1, news.getTitle());
				psSecond.setString(2, news.getBrief());
				psSecond.setString(3, news.getBody());
				psSecond.setString(4, news.getStyle());
				psSecond.setString(5, SDF.format(new Date()));

				ResultSet rs = psFirst.executeQuery();

				if (!rs.next()) {

					throw new DAOException("newsdaoaddpsf");
				}

				psSecond.setString(6, rs.getString(UserSQL.SQL_COLLUM_LABEL_ID.getSQL()));

				psSecond.executeUpdate();

			} catch (SQLException e) {

				throw new DAOException("newsdaoaddpss", e);
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsdaoaddcon", e);
		}

	}

	@Override
	public void update(News news, User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			try (PreparedStatement psUsersId = con.prepareStatement(UserSQL.SQL_SELECT_ID_W_LOGIN.getSQL());
					PreparedStatement psFirst = con.prepareStatement(NewsSQL.SQL_SELECT_NEWS_ID_W_UID_A_TITLE.getSQL());
					PreparedStatement psSecond = con.prepareStatement(NewsSQL.SQL_UPDATE_NEWS.getSQL());) {

				psUsersId.setString(1, user.getLogin());
				
				String title = news.getTitle();
				
				psFirst.setString(2, title);
				
				psSecond.setString(1, title);
				psSecond.setString(2, news.getBrief());
				psSecond.setString(3, news.getBody());
				psSecond.setString(4, news.getStyle());
				psSecond.setString(5, SDF.format(new Date()));

				ResultSet rsUser = psUsersId.executeQuery();

				if (!rsUser.next()) {

					throw new DAOException("newsupdatepsu");
				}
				
				psFirst.setString(1, rsUser.getString(UserSQL.SQL_COLLUM_LABEL_ID.getSQL()));
				
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

			try (PreparedStatement psFirst = con.prepareStatement(NewsSQL.SQL_SELECT_TITLE_ID_W_TITLE.getSQL());
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
