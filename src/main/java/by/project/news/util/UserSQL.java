package by.project.news.util;

public enum UserSQL {
	
	SQL_INSERT_USER("INSERT INTO mynews.users(name,login,password,email,role,age) VALUES(?,?,?,?,?,?)"),
	
	SQL_UPDATE_USER("UPDATE mynews.users SET name=?, email=?, role=? WHERE login=?"),
	SQL_UPDATE_PASSWORD("UPDATE mynews.users SET password=? WHERE login=?"),
	
	SQL_SELECT_ID_W_LOGIN("SELECT id FROM mynews.users WHERE login=?"),
	SQL_SELECT_ALL_W_LOGIN("SELECT * FROM mynews.users WHERE login=?"),
	SQL_SELECT_NAME_EMAIL_W_LOGIN("SELECT name,email FROM mynews.users WHERE login=?"),
	SQL_SELECT_ALL_W_LOGIN__A_PASSWORD("SELECT * FROM mynews.users WHERE login=? AND password=?"),
	
	SQL_DELETE_LOGIN("DELETE FROM mynews.users WHERE login=?"),


	SQL_COLLUM_LABEL_ID("id"),
	SQL_COLLUM_LABEL_NAME("name"),
	SQL_COLLUM_LABEL_EMAIL("email"),
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