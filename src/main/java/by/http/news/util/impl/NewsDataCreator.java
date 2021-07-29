package by.http.news.util.impl;

import java.util.Map;

import by.http.news.bean.News;
import by.http.news.bean.News.NewsBuilder;
import by.http.news.util.Creator;
import by.http.news.util.FieldMapCreator;
import by.http.news.util.CombineEnum;
import by.http.news.util.NewsField;
import by.http.news.util.UtilException;
import by.http.news.util.WorkWithObjectField;
import jakarta.servlet.http.HttpServletRequest;

public class NewsDataCreator implements Creator<News, HttpServletRequest> {

	@Override
	public News create(HttpServletRequest object) throws UtilException {

		Map<CombineEnum, String> fieldsData = fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> entry : fieldsData.entrySet()) {

			entry.setValue(object.getParameter(entry.getKey().toString().toLowerCase()));
		}

		return createNewsData(fieldsData);
	}

	private News createNewsData(Map<CombineEnum, String> fieldsData) throws UtilException {

		NewsBuilder newsBuilder = new NewsBuilder();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(newsBuilder, fields.getKey().toString(), fields.getValue());
		}

		return newsBuilder.builder();
	}

	private Map<CombineEnum, String> fieldsDataGetMap() {

		return FieldMapCreator.create(NewsField.class.getEnumConstants());
	}

	@Override
	public News create() {

		// fieldsDataGetMap();
		return null;
	}

}
