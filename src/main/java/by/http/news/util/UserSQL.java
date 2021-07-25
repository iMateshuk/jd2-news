package by.http.news.util;

public enum UserSQL {
	
	SQL_INSERT_USER("INSERT INTO users(name,login,password,email,role,age) VALUES(?,?,?,?,?,?)"),
	
	SQL_UPDATE_USER("UPDATE users SET name=?, email=?, role=? WHERE login=?"),
	SQL_UPDATE_PASSWORD("UPDATE users SET password=? WHERE login=?"),
	
	SQL_SELECT_LOGIN("SELECT * FROM users WHERE login=?"),
	
	SQL_DELETE_LOGIN("DELETE FROM users WHERE login=?"),

	SQL_COLLUM_LABEL_PASSWORD("password"),
	SQL_COLLUM_LABEL_AGE("age"),
	SQL_COLLUM_LABEL_ROLE("role");
	
	private String string;

	UserSQL (String string) {
		this.string = string;
	}

	public String getSQL() {
		return string;
	}

}
