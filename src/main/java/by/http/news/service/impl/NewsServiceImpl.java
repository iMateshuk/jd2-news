package by.http.news.service.impl;

import java.util.List;
import java.util.Map;

import by.http.news.bean.News;
import by.http.news.bean.User;
import by.http.news.dao.DAOException;
import by.http.news.dao.DAOProvider;
import by.http.news.dao.NewsDAO;
import by.http.news.service.NewsService;
import by.http.news.service.ServiceException;
import by.http.news.util.CheckField;
import by.http.news.util.CombineEnum;
import by.http.news.util.FieldMapCreator;
import by.http.news.util.NewsField;
import by.http.news.util.UtilException;
import by.http.news.util.WorkWithObjectField;

public class NewsServiceImpl implements NewsService {

	private static final DAOProvider provider = DAOProvider.getInstance();

	private static final NewsDAO newsDAO = provider.getNewsDAO();

	//private static final String STYLE_NOT_ADULT = "NOT IN ('adult')";
	//private static final String STYLE_LIKE = "%";

	private static final String EXP_BODY = ".*(fuck you)+.*";
	private static final String EXP_TITLE = ".*\\*+.*";
	//private static final String EXP_STYLE_LIKE = "^" + STYLE_LIKE + ".*" + STYLE_LIKE + "$";

	private static final String EMPTY = "";

	@Override
	public void add(News news) throws ServiceException {

		checkField(news);

		try {

			CheckField.checkKVE(NewsField.BODY.toString(), news.getBody(), EXP_BODY);

			newsDAO.add(news);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public void update(News news) throws ServiceException {

		checkField(news);

		try {

			CheckField.checkKVE(NewsField.BODY.toString(), news.getBody(), EXP_BODY);

			newsDAO.update(news);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(News news) throws ServiceException {

		try {

			CheckField.checkKVN(NewsField.TITLE.toString(), news.getTitle());

			newsDAO.delete(news);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);
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

				CheckField.checkKVLMin(key, value, 2);

				CheckField.checkKVE(key, value, EXP_TITLE);

			} else {

				news.setTitle(EMPTY);
			}

			value = news.getStyle();
			
			System.out.println(value);

			if (!CheckField.checkKVN(value)) {

				CheckField.checkVA(value, user.getAge());
			
			} else {

				news.setStyle(EMPTY);
			}
			
			System.out.println( news.getStyle());

			return newsDAO.choose(news);

		} catch (DAOException | UtilException e) {

			throw new ServiceException(e.getMessage(), e);

		}
	}

	private void checkField(News news) throws ServiceException {

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

			try {

				CheckField.checkKVN(key, value);

				CheckField.checkKVLMin(key, value, 3);

			} catch (UtilException e) {

				throw new ServiceException(e.getMessage(), e);
			}

		}

	}

}
