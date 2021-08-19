package by.project.news.service;


import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.User;
import by.project.news.bean.UserData;

public interface NewsService {
	
	void add(News news, User user) throws ServiceException;

	void update(News news, User user) throws ServiceException;
	
	void delete(News news) throws ServiceException;
	
	void sgnAuthor(News news, User user) throws ServiceException;
	
	void unsgnAuthor (UserData author, User user) throws ServiceException;
	
	List<News> choose(News news, User user) throws ServiceException;
	
	List<News> load() throws ServiceException;
	
	List<News> sgnAuthorView(User user) throws ServiceException;
	
	News chooseNewsByTitle(News news) throws ServiceException;

}
