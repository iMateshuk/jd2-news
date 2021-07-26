package by.http.news.util.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import by.http.news.bean.News;
import by.http.news.bean.News.NewsBuilder;
import by.http.news.util.Creator;
import by.http.news.util.CombineEnum;
import by.http.news.util.FieldMapCreator;
import by.http.news.util.Generator;
import by.http.news.util.NewsField;
import by.http.news.util.UtilException;
import by.http.news.util.WorkWithObjectField;

public class NewsCreator implements Creator<News, ResultSet> {

	private Map<CombineEnum, String> fieldsData;

	@Override
	public News create() throws UtilException {

		fieldsDataGetMap();

		return generate();
	}

	@Override
	public News create(ResultSet object) throws UtilException {

		fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {
			
			CombineEnum key = fields.getKey();

			try {
				
				fieldsData.replace(key, object.getString(key.toString().toLowerCase()));
			} catch (SQLException e) {

				throw new UtilException(e.getMessage(), e);
			}
		}

		return createNews();

	}

	private void fieldsDataGetMap() {

		fieldsData = FieldMapCreator.create(NewsField.class.getEnumConstants());
	}

	private News generate() throws UtilException {

		fieldsData.forEach((k, v) -> fieldsData.replace(k, Generator.genString((int) (Math.random() * 10 + 1))));

		return createNews();

	}

	private News createNews() throws UtilException {

		NewsBuilder newsBuilder = new News.NewsBuilder();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(newsBuilder, fields.getKey().toString(), fields.getValue());
		}

		return newsBuilder.builder();

	}

}
