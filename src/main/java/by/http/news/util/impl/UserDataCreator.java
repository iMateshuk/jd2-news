package by.http.news.util.impl;

import java.util.Map;

import by.http.news.bean.UserData;
import by.http.news.bean.UserData.UserDataBuilder;
import by.http.news.util.Creator;
import by.http.news.util.CombineEnum;
import by.http.news.util.FieldMapCreator;
import by.http.news.util.Generator;
import by.http.news.util.UserDataField;
import by.http.news.util.UtilException;
import by.http.news.util.WorkWithObjectField;
import jakarta.servlet.http.HttpServletRequest;

public class UserDataCreator implements Creator<UserData, HttpServletRequest> {

	private Map<CombineEnum, String> fieldsData;

	private void fieldsDataGetMap() {

		fieldsData = FieldMapCreator.create(UserDataField.class.getEnumConstants());
	}

	@Override
	public UserData create(HttpServletRequest object) throws UtilException {

		fieldsDataGetMap();

		for (Map.Entry<CombineEnum, String> entry : fieldsData.entrySet()) {

			entry.setValue(object.getParameter(entry.getKey().toString().toLowerCase()));
		}

		return createUserData();
	}

	private UserData createUserData() throws UtilException {

		UserDataBuilder userDataBuilder = new UserData.UserDataBuilder();

		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {

			WorkWithObjectField.methodSet(userDataBuilder, fields.getKey().toString(), fields.getValue());
		}

		if (fieldsData.get(UserDataField.ROLE) == null || fieldsData.get(UserDataField.ROLE).isEmpty()) {

			userDataBuilder.setRole("user");
		}

		return userDataBuilder.build();

	}

	@Override
	public UserData create() throws UtilException {

		fieldsDataGetMap();

		return generate();
	}

	private UserData generate() throws UtilException {
		
		fieldsData.forEach((k,v) -> fieldsData.put(k, Generator.genString((int) (Math.random() * 10 + 1))));

		fieldsData.put(UserDataField.PASSWORD, "noPassword");
		fieldsData.put(UserDataField.ROLE, "user");
		fieldsData.put(UserDataField.AGE, Generator.genNumber(2));
		fieldsData.put(UserDataField.EMAIL,
				"user." + Generator.genString((int) (Math.random() * 10 + 1)) + "@projectnews.by");

		return createUserData();
	}

}
