package by.project.news.service;


import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.User;

public interface NewsService {
	
	void add(News news, User user) throws ServiceException;

	void update(News news, User user) throws ServiceException;
	
	void delete(News news) throws ServiceException;
	
	void sgnAuthor(News news, User user) throws ServiceException;
	
	List<News> choose(News news, User user) throws ServiceException;
	
	List<News> load() throws ServiceException;
	
	News choose(News news) throws ServiceException;

}
