package by.http.news.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.http.news.bean.News;
import by.http.news.dao.DAOException;
import by.http.news.dao.NewsDAO;
import by.http.news.util.Creator;
import by.http.news.util.CreatorProvider;
import by.http.news.util.UtilException;

public class NewsDB implements NewsDAO {

	private static final Creator<News, String> CREATOR = CreatorProvider.getCreatorProvider().getNewsCreator();

	@Override
	public void add(News news) throws DAOException {

		throw new DAOException("Sorry, news dao add section under construction");

	}

	@Override
	public void update(News news) throws DAOException {

		throw new DAOException("Sorry, news dao update section under construction");

	}

	@Override
	public void delete(News news) throws DAOException {

		throw new DAOException("Sorry, news dao delete section under construction");

	}
	
	@Override
	public List<News> choose(News news) throws DAOException {

		throw new DAOException("Sorry, news dao choose section under construction");
	}


	@Override
	public List<News> load() throws DAOException {

		List<News> newses = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			try {

				newses.add(CREATOR.create());
				
			} catch (UtilException e) {

				throw new DAOException(e.getMessage(), e);
			}
		}

		// throw new DAOException("Sorry, news dao load section under construction");

		return newses;
	}

}
