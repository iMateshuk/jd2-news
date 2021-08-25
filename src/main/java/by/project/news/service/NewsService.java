package by.project.news.service;


import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import by.project.news.bean.UserData;

public interface NewsService {
	
	void add(News news, User user) throws ServiceException;

	void update(News news, User user) throws ServiceException;
	
	void delete(News news) throws ServiceException;
	
	void sgnAuthor(News news, User user) throws ServiceException;
	
	void unsgnAuthor (UserData author, User user) throws ServiceException;
	
	NewsData choose(News news, User user, NewsData newsData) throws ServiceException;
	
	List<News> load() throws ServiceException;
	
	NewsData sgnAuthorView(User user, NewsData newsData) throws ServiceException;
	
	News chooseNews(News news) throws ServiceException;

}
