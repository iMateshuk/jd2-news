package by.http.news.util.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import by.http.news.bean.User;
import by.http.news.bean.User.UserBuilder;
import by.http.news.util.Creator;
import by.http.news.util.WorkWithObjectField;
import by.http.news.util.CombineEnum;
import by.http.news.util.FieldMapCreator;
import by.http.news.util.Generator;
import by.http.news.util.UserField;
import by.http.news.util.UtilException;

public class UserCreator implements Creator<User, ResultSet> {

	private Map<CombineEnum, String> fieldsData;

	@Override
	public User create(ResultSet object) throws UtilException {

		fieldsDataGetMap();
		
		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {
			
			CombineEnum key = fields.getKey();

			try {
				
				fieldsData.replace(key, object.getString(key.toString().toLowerCase()));
			} catch (SQLException e) {

				throw new UtilException(e.getMessage(), e);
			}
		}
		
		return createUser();
	}

	@Override
	public User create() throws UtilException {

		fieldsDataGetMap();

		return generate();
	}
	
	private void fieldsDataGetMap() {

		fieldsData = FieldMapCreator.create(UserField.class.getEnumConstants());
	}
	
	private User createUser() throws UtilException {

		//UserBuilder userBuilder = new User.UserBuilder(fieldsData.get(UserField.LOGIN), fieldsData.get(UserField.ROLE));
		UserBuilder userBuilder = new User.UserBuilder();
		
		
		for (Map.Entry<CombineEnum, String> fields : fieldsData.entrySet()) {
			
			
			WorkWithObjectField.methodSet(userBuilder, fields.getKey().toString(), fields.getValue());
		}
		
		return userBuilder.build();

	}

	private User generate() throws UtilException {
		
		fieldsData.forEach((k,v) -> fieldsData.put(k, Generator.genString((int) (Math.random() * 10 + 1))));

		fieldsData.put(UserField.ROLE, "user");
		fieldsData.put(UserField.AGE, Generator.genNumber(2));

		return createUser();
	}

}
