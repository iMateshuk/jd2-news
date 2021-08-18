package by.project.news.util;

public enum SgnSQL {
	
	SQL_INSERT_SGN_AUTHOR("INSERT INTO mynews.sgnauthor(u_id, n_u_id) VALUES(?,?)"),
	SQL_INSERT_SGN_AUTHOR_W_LOGIN_TITLE("INSERT INTO mynews.sgnauthor(u_id, n_u_id) VALUES((SELECT id FROM mynews.users WHERE login=?),(SELECT u_id FROM mynews.news WHERE title=?))"),
	
	SQL_COLLUM_LABEL_U_ID("u_id"),
	SQL_COLLUM_LABEL_N_U_ID("n_u_id"),
	
	SQL_SELECT_NUID_W_UID("SELECT n_u_id FROM mynews.sgnauthor WHERE u_id=?"),
	;	
	
	private String string;

	SgnSQL(String string) {
		this.string = string;
	}

	public String getSQL() {
		return string;
	}

}
