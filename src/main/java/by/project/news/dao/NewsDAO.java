package by.project.news.dao;


import java.util.List;

import by.project.news.bean.News;
import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import by.project.news.bean.UserData;

public interface NewsDAO {
	
	void add(News news, User user) throws DAOException;
	
	void update(News news, User user) throws DAOException;
	
	void delete(News news) throws DAOException;
	
	void sgnAuthor(News news, User user) throws DAOException;
	
	void unsgnAuthor(UserData author, User user) throws DAOException;
	
	NewsData choose(News news, NewsData newsData) throws DAOException;
	
	NewsData sgnAuthorView(User user, NewsData newsData) throws DAOException;
	
	News chooseNews(News news) throws DAOException;
	
	List<News> load() throws DAOException;

}
