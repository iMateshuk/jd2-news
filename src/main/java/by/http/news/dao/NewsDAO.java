package by.http.news.dao;


import java.util.List;

import by.http.news.bean.News;

public interface NewsDAO {
	
	void add(News news) throws DAOException;
	
	void update(News news) throws DAOException;
	
	void delete(News news) throws DAOException;
	
	List<News> choose(News news) throws DAOException;
	
	List<News> load() throws DAOException;

}
