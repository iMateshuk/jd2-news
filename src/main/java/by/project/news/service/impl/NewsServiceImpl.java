package by.project.news.service.impl;

import java.util.List;
import java.util.Map;

import by.project.news.bean.News;
import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.dao.DAOException;
import by.project.news.dao.DAOProvider;
import by.project.news.dao.NewsDAO;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.util.CheckField;
import by.project.news.util.CombineEnum;
import by.project.news.util.FieldMapCreator;
import by.project.news.util.NewsField;
import by.project.news.util.UtilException;
import by.project.news.util.WorkWithObjectField;

public class NewsServiceImpl implements NewsService {

	private static final DAOProvider provider = DAOProvider.getInstance();
	private static final NewsDAO newsDAO = provider.getNewsDAO();

	private static final String EXP_BODY = ".*(fuck you)+.*";
	private static final String EXP_TITLE = ".*\\*+.*";

	private static final String EMPTY = "";
	
	private static final int TITLE_LENGHT = 2;
	private static final int FIELD_LENGHT = 3;
	private static final int AUTHOR_LOGIN_LENGHT = 3;

	@Override
	public void add(News news, User user) throws ServiceException {

		try {

			checkField(news);

			CheckField.checkValueExpression(news.getBody(), EXP_BODY);

			newsDAO.add(news, user);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public void update(News news, User user) throws ServiceException {

		try {

			checkField(news);

			CheckField.checkValueExpression(news.getBody(), EXP_BODY);

			newsDAO.update(news, user);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public void delete(News news) throws ServiceException {

		try {

			CheckField.checkValueNull(news.getTitle());

			newsDAO.delete(news);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public List<News> load() throws ServiceException {

		try {

			return newsDAO.load();

		} catch (DAOException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public NewsData choose(News news, User user, NewsData newsData) throws ServiceException {

		try {

			String value = news.getTitle();

			if (!CheckField.thisValueNull(value)) {

				CheckField.checkValueLengthMin(value, TITLE_LENGHT);

				CheckField.checkValueExpression(value, EXP_TITLE);

			} else {

				news.setTitle(EMPTY);
			}

			value = news.getStyle();

			if (!CheckField.thisValueNull(value)) {

				CheckField.checkAdultAndAge(value, user.getAge());

			} else {

				news.setStyle(EMPTY);
			}

			return newsDAO.choose(news, newsData);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public News chooseNews(News news) throws ServiceException {

		String value = news.getTitle();

		try {

			if (!CheckField.thisValueNull(value)) {

				CheckField.checkValueLengthMin(value, TITLE_LENGHT);

				CheckField.checkValueExpression(value, EXP_TITLE);
			}

			return newsDAO.chooseNews(news);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public void sgnAuthor(News news, User user) throws ServiceException {

		String value = news.getTitle();

		try {

			if (!CheckField.thisValueNull(value)) {

				CheckField.checkValueLengthMin(value, TITLE_LENGHT);

				CheckField.checkValueExpression(value, EXP_TITLE);
			}

			newsDAO.sgnAuthor(news, user);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public void unsgnAuthor(UserData author, User user) throws ServiceException {

		String value = author.getLogin();

		try {

			CheckField.checkValueNull(value);

			CheckField.checkValueLengthMin(value, AUTHOR_LOGIN_LENGHT);

			newsDAO.unsgnAuthor(author, user);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public NewsData sgnAuthorView(User user, NewsData newsData) throws ServiceException {

		try {
			
			CheckField.checkValueNull(newsData.getPage());

			return newsDAO.sgnAuthorView(user, newsData);
		} catch (DAOException | UtilException e) {

			throw new ServiceException(e);
		}
	}

	private void checkField(News news) throws UtilException {

		Map<CombineEnum, String> fieldsData = FieldMapCreator.create(NewsField.class.getEnumConstants());

		fieldsData.forEach((k, v) -> {
			try {

				fieldsData.put(k, WorkWithObjectField.methodGet(news, k.toString()));
			} catch (UtilException ignore) {

				fieldsData.put(k, EMPTY);
			}
		});

		String value;

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			value = fields.getValue();

			CheckField.checkValueNull(value);

			CheckField.checkValueLengthMin(value, FIELD_LENGHT);
		}
	}
}
