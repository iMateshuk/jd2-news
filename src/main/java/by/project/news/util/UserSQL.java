package by.project.news.util;

public enum UserSQL {
	
	SQL_INSERT_USER("INSERT INTO mynews.users(name,login,password,email,role,age) VALUES(?,?,?,?,?,?)"),
	
	SQL_UPDATE_USER("UPDATE mynews.users SET name=?, email=?, role=? WHERE login=?"),
		SQL_UPDATE_USER_INCLUDE_AGE("UPDATE mynews.users SET name=?, email=?, role=?, age=? WHERE login=?"),
	SQL_UPDATE_USER_DATA_IF_NOT_NULL_EMPTY(
			"UPDATE mynews.users SET name=COALESCE(NULLIF(?, ''), name), email=COALESCE(NULLIF(?, ''), email), "
			+ "role=COALESCE(NULLIF(?, ''), role), age=COALESCE(NULLIF(?, ''), age) WHERE login=?"),
	SQL_UPDATE_PASSWORD("UPDATE mynews.users SET password=? WHERE login=?"),
	SQL_UPDATE_PASSWORD_SELECT_LOGIN_OLDPASS("UPDATE mynews.users AS u, "
			+ "(select id from mynews.users where login=? AND password=?) AS s SET password=? WHERE u.id=s.id"),
	
	SQL_SELECT_ID_W_LOGIN("SELECT id FROM mynews.users WHERE login=?"),
	SQL_SELECT_ALL_W_LOGIN("SELECT * FROM mynews.users WHERE login=?"),
	SQL_SELECT_NAME_EMAIL_W_LOGIN("SELECT name,email FROM mynews.users WHERE login=?"),
	SQL_SELECT_NAME_LOGIN_W_ID("SELECT name, login FROM mynews.users WHERE id=?"),
	SQL_SELECT_ALL_W_LOGIN__A_PASSWORD("SELECT * FROM mynews.users WHERE login=? AND password=?"),
	SQL_SELECT_NAME_LOGIN_W_ID_S_LOGIN("SELECT login, name FROM mynews.users WHERE id IN "
			+ "(SELECT n_u_id FROM mynews.sgnauthor WHERE u_id IN (SELECT id FROM mynews.users WHERE login=?))"),
	SQL_SELECT_ID_W_LOGIN_U_LOGIN("SELECT id FROM mynews.users WHERE login=? UNION "
			+ "SELECT id FROM mynews.users WHERE login=?"),
	
	SQL_DELETE_LOGIN("DELETE FROM mynews.users WHERE login=?"),

	SQL_COLUM_LABEL_ID("id"),
	SQL_COLUM_LABEL_NAME("name"),
	SQL_COLUM_LABEL_LOGIN("login"),
	SQL_COLUM_LABEL_EMAIL("email"),
	SQL_COLUM_LABEL_PASSWORD("password"),
	SQL_COLUM_LABEL_AGE("age"),
	SQL_COLUM_LABEL_ROLE("role");
	
	
	private String string;

	UserSQL (String string) {
		
		this.string = string;
	}

	public String getSQL() {
		
		return string;
	}

}
