package by.http.news.dao.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class UsersDBResourceManager {
	
	private static final String DB_FILENAME = "by.http.news.dao.util.db_users";
	
	private final static UsersDBResourceManager INSTANCE = new UsersDBResourceManager();
	
	private ResourceBundle bundle = ResourceBundle.getBundle(DB_FILENAME, Locale.US);
	
	public static UsersDBResourceManager getInstance() {
		
		return INSTANCE;
	}
	
	public String getValue(String key) {
		
		return bundle.getString(key);
	}

}
