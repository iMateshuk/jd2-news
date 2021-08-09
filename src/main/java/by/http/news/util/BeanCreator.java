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

		Map<CombineEnum, String> fieldsData = createDataWithRS(
				FieldMapCreator.create(UserField.class.getEnumConstants()), rs);

		UserBuilder userBuilder = new User.UserBuilder();

		createBeanBuilder(fieldsData, userBuilder);

		return userBuilder.build();
	}

	public static News createNews(HttpServletRequest request) throws UtilException {

		Map<CombineEnum, String> fieldsData = createDataWithRQ(
				FieldMapCreator.create(NewsField.class.getEnumConstants()),	request);

		NewsBuilder newsBuilder = new News.NewsBuilder();

		createBeanBuilder(fieldsData, newsBuilder);

		return newsBuilder.builder();

	}

	public static News createNews(ResultSet rs) throws UtilException {

		Map<CombineEnum, String> fieldsData = createDataWithRS(
				FieldMapCreator.create(NewsField.class.getEnumConstants()), rs);

		NewsBuilder newsBuilder = new News.NewsBuilder();

		createBeanBuilder(fieldsData, newsBuilder);

		return newsBuilder.builder();
	}

	public static UserData createUserData(HttpServletRequest request) throws UtilException {

		Map<CombineEnum, String> fieldsData = createDataWithRQ(
				FieldMapCreator.create(UserDataField.class.getEnumConstants()), request);

		UserDataBuilder userDataBuilder = new UserData.UserDataBuilder();

		createBeanBuilder(fieldsData, userDataBuilder);

		if (fieldsData.get(UserDataField.ROLE) == null || fieldsData.get(UserDataField.ROLE).isEmpty()) {

			userDataBuilder.setRole(USER_ROLE);
		}

		return userDataBuilder.build();
	}

	public static UserData createUserData(ResultSet rs) throws UtilException {

		Map<CombineEnum, String> fieldsData = createDataWithRS(
				FieldMapCreator.create(UserDataField.class.getEnumConstants()), rs);

		UserDataBuilder userDataBuilder = new UserData.UserDataBuilder();

		createBeanBuilder(fieldsData, userDataBuilder);

		return userDataBuilder.build();
	}

	private static Map<CombineEnum, String> createDataWithRS(Map<CombineEnum, String> fieldsData, ResultSet rs)
			throws UtilException {

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			CombineEnum key = fields.getKey();

			try {

				fieldsData.replace(key, rs.getString(key.toString().toLowerCase()));
			} catch (SQLException e) {

				throw new UtilException(e.getMessage(), e);
			}
		}

		return fieldsData;
	}

	private static Map<CombineEnum, String> createDataWithRQ(Map<CombineEnum, String> fieldsData,
			HttpServletRequest request) {

		for (Map.Entry<CombineEnum, String> entry : fieldsData.entrySet()) {

			entry.setValue(request.getParameter(entry.getKey().toString().toLowerCase()));
		}

		return fieldsData;
	}

	private static void createBeanBuilder(Map<CombineEnum, String> fieldsData, Object object) throws UtilException {

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(object, fields.getKey().toString(), fields.getValue());
		}

	}

}