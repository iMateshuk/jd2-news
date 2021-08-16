package by.project.news.dao;


import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.User;

public interface NewsDAO {
	
	void add(News news, User user) throws DAOException;
	
	void update(News news, User user) throws DAOException;
	
	void delete(News news) throws DAOException;
	
	List<News> choose(News news) throws DAOException;
	
	News chooseNewsByTitle(News news) throws DAOException;
	
	List<News> load() throws DAOException;

}
