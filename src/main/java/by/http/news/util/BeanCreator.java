package by.http.news.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import by.http.news.bean.News;
import by.http.news.bean.User;
import by.http.news.bean.UserData;
import by.http.news.bean.News.NewsBuilder;
import by.http.news.bean.User.UserBuilder;
import by.http.news.bean.UserData.UserDataBuilder;
import jakarta.servlet.http.HttpServletRequest;

public class BeanCreator {
	
	private final static String USER_ROLE = "user"; 

	public static User createUser(ResultSet rs) throws UtilException {

		Map<CombineEnum, String> fieldsData = fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			CombineEnum key = fields.getKey();

			try {

				fieldsData.replace(key, rs.getString(key.toString().toLowerCase()));
			} catch (SQLException e) {

				throw new UtilException(e.getMessage(), e);
			}
		}

		return createUser(fieldsData);
	}
	
	public static News createNews(HttpServletRequest request) throws UtilException {

		Map<CombineEnum, String> fieldsData = fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> entry : fieldsData.entrySet()) {

			entry.setValue(request.getParameter(entry.getKey().toString().toLowerCase()));
		}

		return createNews(fieldsData);
	}
	
	public static News createNews(ResultSet rs) throws UtilException {

		Map<CombineEnum, String> fieldsData = fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			CombineEnum key = fields.getKey();
			
			try {

				fieldsData.replace(key, rs.getString(key.toString().toLowerCase()));
			} catch (SQLException e) {

				throw new UtilException(e.getMessage(), e);
			}
		}

		return createNews(fieldsData);

	}

	public static UserData createUserData(HttpServletRequest request) throws UtilException {

		Map<CombineEnum, String> fieldsData = fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> entry : fieldsData.entrySet()) {

			entry.setValue(request.getParameter(entry.getKey().toString().toLowerCase()));
		}

		return createUserData(fieldsData);
	}

	public static UserData createUserData(ResultSet rs) throws UtilException {

		Map<CombineEnum, String> fieldsData = fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			CombineEnum key = fields.getKey();

			try {

				fieldsData.replace(key, rs.getString(key.toString().toLowerCase()));
			} catch (SQLException e) {

				throw new UtilException(e.getMessage(), e);
			}
		}

		return createUserData(fieldsData);
	}
	
	private static User createUser(Map<CombineEnum, String> fieldsData) throws UtilException {

		UserBuilder userBuilder = new User.UserBuilder();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(userBuilder, fields.getKey().toString(), fields.getValue());
		}

		return userBuilder.build();

	}
	
	private static UserData createUserData(Map<CombineEnum, String> fieldsData) throws UtilException {

		UserDataBuilder userDataBuilder = new UserData.UserDataBuilder();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(userDataBuilder, fields.getKey().toString(), fields.getValue());
		}

		if (fieldsData.get(UserDataField.ROLE) == null || fieldsData.get(UserDataField.ROLE).isEmpty()) {

			userDataBuilder.setRole(USER_ROLE);
		}

		return userDataBuilder.build();

	}
	
	private static News createNews(Map<CombineEnum, String> fieldsData) throws UtilException {

		NewsBuilder newsBuilder = new News.NewsBuilder();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(newsBuilder, fields.getKey().toString(), fields.getValue());
		}

		return newsBuilder.builder();

	}
	
	private static Map<CombineEnum, String> fieldsDataGetMap() {

		return FieldMapCreator.create(UserDataField.class.getEnumConstants());
	}

}
