package by.project.news.service.impl;

import java.util.List;
import java.util.Map;

import by.project.news.bean.News;
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
import by.project.news.util.UserDataField;
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

			CheckField.checkKVE(NewsField.BODY.toString(), news.getBody(), EXP_BODY);

			newsDAO.add(news, user);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void update(News news, User user) throws ServiceException {

		try {

			checkField(news);

			CheckField.checkKVE(NewsField.BODY.toString(), news.getBody(), EXP_BODY);

			newsDAO.update(news, user);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void delete(News news) throws ServiceException {

		try {

			CheckField.checkKVN(NewsField.TITLE.toString(), news.getTitle());

			newsDAO.delete(news);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public List<News> load() throws ServiceException {

		try {

			return newsDAO.load();

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		}

	}

	@Override
	public List<News> choose(News news, User user) throws ServiceException {

		try {

			String key = NewsField.TITLE.toString();
			String value = news.getTitle();

			if (!CheckField.checkKVN(value)) {

				CheckField.checkKVLMin(key, value, TITLE_LENGHT);

				CheckField.checkKVE(key, value, EXP_TITLE);

			} else {

				news.setTitle(EMPTY);
			}

			value = news.getStyle();

			if (!CheckField.checkKVN(value)) {

				CheckField.checkVA(value, user.getAge());

			} else {

				news.setStyle(EMPTY);
			}

			return newsDAO.choose(news);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}
	}

	@Override
	public News choose(News news) throws ServiceException {

		String key = NewsField.TITLE.toString();
		String value = news.getTitle();

		try {

			if (!CheckField.checkKVN(value)) {

				CheckField.checkKVLMin(key, value, TITLE_LENGHT);

				CheckField.checkKVE(key, value, EXP_TITLE);
			}

			return newsDAO.chooseNewsByTitle(news);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void sgnAuthor(News news, User user) throws ServiceException {

		String key = NewsField.TITLE.toString();
		String value = news.getTitle();

		try {

			if (!CheckField.checkKVN(value)) {

				CheckField.checkKVLMin(key, value, TITLE_LENGHT);

				CheckField.checkKVE(key, value, EXP_TITLE);
			}

			newsDAO.sgnAuthor(news, user);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}

	@Override
	public void unsgnAuthor(UserData author, User user) throws ServiceException {

		String key = UserDataField.LOGIN.toString();
		String value = author.getLogin();

		try {

			CheckField.checkKVN(key, value);

			CheckField.checkKVLMin(key, value, AUTHOR_LOGIN_LENGHT);

			newsDAO.unsgnAuthor(author, user);

		} catch (DAOException e) {

			throw new ServiceException(e.getMessage(), e);

		} catch (UtilException e) {

			throw new ServiceException("commonerror", e);
		}

	}
	
	@Override
	public List<News> sgnAuthorView(User user) throws ServiceException {

		try {
			
			return newsDAO.sgnAuthorView(user);
		} catch (DAOException e) {
			
			throw new ServiceException(e.getMessage(), e);
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

		String key;
		String value;

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			key = fields.getKey().toString();
			value = fields.getValue();

			CheckField.checkKVN(key, value);

			CheckField.checkKVLMin(key, value, FIELD_LENGHT);

		}

	}

}
