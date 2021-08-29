package by.project.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.dao.DAOException;
import by.project.news.dao.NewsDAO;
import by.project.news.dao.util.ConnectionPool;
import by.project.news.dao.util.ConnectionPoolException;
import by.project.news.util.BeanCreator;
import by.project.news.util.CheckField;
import by.project.news.util.NewsSQL;
import by.project.news.util.SgnSQL;
import by.project.news.util.UserSQL;
import by.project.news.util.UtilException;

public class NewsDB implements NewsDAO {

	private static final String STYLE_LIKE = "%";

	private static final int RECORDS_PER_PAGE = 3;

	@Override
	public void add(News news, User user) throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_INSERT_NEWS_W_LOGIN.getSQL());) {

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getBody());
			ps.setString(4, news.getStyle());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setString(6, user.getLogin());

			if (ps.executeUpdate() != 1) {

				throw new DAOException("Can't add news. News exist :: newsdaoaddpss");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't add news (sql) :: newsdaoaddcon", e);
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
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setString(6, user.getLogin());

			if (ps.executeUpdate() != 1) {

				throw new DAOException("Can't update news. News not exist :: newsupdatepss");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't update news (sql) :: newsupdatecon", e);
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

					throw new DAOException("Can't delete news. News not exist :: newsdaodeletepsf");
				}

				psSecond.setInt(1, rs.getInt(NewsSQL.SQL_COLUM_LABEL_ID.getSQL()));

				psSecond.executeUpdate();

			} catch (SQLException e) {

				throw new DAOException("Can't delete news (sql) :: newsdaodeletepss", e);
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("newsdaodeletecon", e);
		}

	}

	@Override
	public NewsData choose(News news, NewsData newsData) throws DAOException {

		final String newsStyle = news.getStyle();
		final String newsTitle = news.getTitle();

		final String sql = CheckField.thisValueNull(newsStyle)
				? NewsSQL.SQL_SELECT_ALL_W_TITLE_A_STYLE_NOTADULT.getSQL()
				: NewsSQL.SQL_SELECT_ALL_W_TITLE_A_STYLE_ADULT.getSQL();

		newsData.setRecordsPerPage(RECORDS_PER_PAGE);

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, STYLE_LIKE.concat(newsTitle).concat(STYLE_LIKE));

			int count = 2;

			if (!CheckField.thisValueNull(newsStyle)) {

				ps.setString(2, newsStyle);
				count++;
			}

			ps.setInt(count++, (newsData.getPage() - 1) * RECORDS_PER_PAGE);
			ps.setInt(count, RECORDS_PER_PAGE);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				newsData.setMaxNewses(rs.getInt(NewsSQL.SQL_COLUM_LABEL_COUNT.getSQL()));
				newsData.setNewses(newsCreator(rs, new ArrayList<News>()));
			} else {

				throw new DAOException("Can't choose newses. Not exist newses title:" + newsTitle + ", and style:"
						+ newsStyle + ", :: newsdaoload");
			}

			return newsData;

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException(" Can't choose newses (sql) :: newsdaochoose", e);
		} catch (UtilException e) {

			throw new DAOException(e);
		}

	}

	@Override
	public List<News> load() throws DAOException {

		try (Connection con = ConnectionPool.getInstance().takeConnection()) {

			return newsCreator(con.createStatement().executeQuery(NewsSQL.SQL_SELECT_ALL_FOR_LOAD.getSQL()));

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't load newses for main page (sql) :: newsdaoload", e);
		} catch (UtilException e) {

			throw new DAOException(e);
		}

	}

	@Override
	public News chooseNews(News news) throws DAOException {

		final String newsTitle = news.getTitle();

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(NewsSQL.SQL_SELECT_ALL_W_TITLE.getSQL());) {

			ps.setString(1, newsTitle);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {

				throw new DAOException(
						"Can't choose news. News not exist, title: " + newsTitle + ", :: newsdaochoosetitle");
			}

			return BeanCreator.createNews(rs);

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't choose news (sql) :: newsdaochoosetitle", e);
		} catch (UtilException e) {

			throw new DAOException(e);
		}

	}

	@Override
	public void sgnAuthor(News news, User user) throws DAOException {

		final String newsTitle = news.getTitle();
		final String userLogin = user.getLogin();

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(SgnSQL.SQL_INSERT_W_LOGIN_TITLE.getSQL());) {

			ps.setString(1, userLogin);
			ps.setString(2, newsTitle);

			if (ps.executeUpdate() != 1) {

				throw new DAOException("Can't sgn to author news title: " + newsTitle + ", user:" + userLogin
						+ ", :: newsdaosgnauthorps");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't sgn to author (sql) :: newsdaosgnauthor", e);
		}

	}

	@Override
	public void unsgnAuthor(UserData author, User user) throws DAOException {

		final String userLogin = user.getLogin();
		final String authorLogin = author.getLogin();

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement psIds = con.prepareStatement(UserSQL.SQL_SELECT_ID_W_LOGIN_U_LOGIN.getSQL());
				PreparedStatement psUnsgn = con.prepareStatement(SgnSQL.SQL_DELETE_W_UID_A_NUID.getSQL());) {

			psIds.setString(1, userLogin);
			psIds.setString(2, authorLogin);

			ResultSet rs = psIds.executeQuery();

			int count = 1;

			while (rs.next()) {

				psUnsgn.setInt(count++, rs.getInt(UserSQL.SQL_COLUM_LABEL_ID.getSQL()));
			}

			if (psUnsgn.executeUpdate() != 1) {

				throw new DAOException(
						"Can't unsgn from author " + authorLogin + ", user " + userLogin + " :: newsdaounsgnauthorps");
			}

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't unsgn from author (sql) :: newsdaounsgnauthor", e);
		}

	}

	@Override
	public NewsData sgnAuthorView(User user, NewsData newsData) throws DAOException {
		
		final String userLogin = user.getLogin();

		final String sql = CheckField.checkAge(user.getAge())
				? NewsSQL.SQL_SELECT_ALL_W_UID_S_LOGIN_NO_ADULT_LIMIT.getSQL()
				: NewsSQL.SQL_SELECT_ALL_W_UID_S_LOGIN_LIMIT.getSQL();

		newsData.setRecordsPerPage(RECORDS_PER_PAGE);

		try (Connection con = ConnectionPool.getInstance().takeConnection();
				PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, userLogin);
			ps.setInt(2, (newsData.getPage() - 1) * RECORDS_PER_PAGE);
			ps.setInt(3, RECORDS_PER_PAGE);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				newsData.setMaxNewses(rs.getInt(NewsSQL.SQL_COLUM_LABEL_COUNT.getSQL()));
				newsData.setNewses(newsCreator(rs, new ArrayList<News>()));
			} else {

				throw new DAOException("Can't choose newses for sgnAuthor, user "+ userLogin +" :: newsdaoload");
			}

			return newsData;

		} catch (SQLException | ConnectionPoolException e) {

			throw new DAOException("Can't choose newses for sgnAuthor (sql) :: newsdaosgnauthorview", e);
		} catch (UtilException e) {

			throw new DAOException(e);
		}
	}

	private List<News> newsCreator(ResultSet rs) throws UtilException, SQLException {

		List<News> newses = new ArrayList<>();

		while (rs.next()) {

			newses.add(BeanCreator.createNews(rs));
		}

		return newses;
	}

	private List<News> newsCreator(ResultSet rs, List<News> newses) throws UtilException, SQLException {

		newses.add(BeanCreator.createNews(rs));

		while (rs.next()) {

			newses.add(BeanCreator.createNews(rs));
		}

		return newses;
	}
}
