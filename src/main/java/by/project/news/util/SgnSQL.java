package by.project.news.util;

public enum SgnSQL {
	
	SQL_DELETE_W_UID_A_NUID("DELETE FROM mynews.sgnauthor WHERE u_id=? AND n_u_id=?"),
	
	SQL_INSERT_UID_NUID("INSERT INTO mynews.sgnauthor(u_id, n_u_id) VALUES(?,?)"),
	SQL_INSERT_W_LOGIN_TITLE("INSERT INTO mynews.sgnauthor(u_id, n_u_id) VALUES((SELECT id FROM mynews.users WHERE login=?),(SELECT u_id FROM mynews.news WHERE title=?))"),
	
	SQL_COLUM_LABEL_U_ID("u_id"),
	SQL_COLUM_LABEL_N_U_ID("n_u_id"),
	
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
