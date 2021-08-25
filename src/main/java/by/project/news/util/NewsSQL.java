package by.project.news.util;

public enum NewsSQL {

	SQL_INSERT_NEWS("INSERT INTO mynews.news(title,brief,body,style,date,u_id) VALUES(?,?,?,?,?,?)"),
	SQL_INSERT_NEWS_W_LOGIN("INSERT INTO mynews.news(title,brief,body,style,date,u_id) "
			+ "VALUES(?,?,?,?,?,(select id from mynews.users where login=?))"),

	SQL_UPDATE_NEWS("UPDATE mynews.news SET brief=?, body=?, style=?, date=? WHERE id=?"),
	SQL_UPDATE_NEWS_W_TITLE("UPDATE mynews.news AS n, (select id from mynews.news where title=?) AS s "
			+ "SET brief=?, body=?, style=?, date=? WHERE n.id=s.id "
			+ "AND n.u_id=(select id from mynews.users where login=?)"),

	SQL_COLUM_LABEL_ID("id"),
	SQL_COLUM_LABEL_TITLE("title"),
	SQL_COLUM_LABEL_U_ID("u_id"),
	SQL_COLUM_LABEL_COUNT("count"),

	SQL_DELETE_NEWS_TITLE("DELETE FROM mynews.news WHERE title=?"),
	SQL_DELETE_NEWS_ID("DELETE FROM mynews.news WHERE id=?"),

	SQL_SELECT_ALL("SELECT * FROM mynews.news"), 
	SQL_SELECT_ALL_W_TITLE("SELECT * FROM mynews.news WHERE title=?"),
	SQL_SELECT_NEWS_ID_W_UID_A_TITLE(
			"SELECT n.id FROM mynews.news n JOIN mynews.users u ON u.id = n.u_id WHERE u_id=? AND title=?"),
	SQL_SELECT_TITLE_ID_W_TITLE("SELECT title, id FROM mynews.news WHERE title=?"),
	SQL_SELECT_UID_W_TITLE("SELECT u_id FROM mynews.news WHERE title=?"),
	SQL_SELECT_ALL_W_TITLE_A_STYLE("SELECT * FROM mynews.news WHERE title=? AND style=?"),
	SQL_SELECT_ALL_FOR_LOAD("SELECT * FROM mynews.news WHERE style NOT IN ('adult') ORDER BY date DESC LIMIT 10"),

	SQL_SELECT_ALL_W_TITLE_A_STYLE_ADULT(
			"SELECT *, count(*) OVER () AS count FROM mynews.news WHERE title LIKE ? "
			+ "AND style IN (?) ORDER BY date DESC LIMIT ?,?"),
	SQL_SELECT_ALL_W_TITLE_A_STYLE_NOTADULT(
			"SELECT *, count(*) OVER () AS count FROM mynews.news WHERE title LIKE ? "
			+ "AND style NOT IN ('adult') ORDER BY date DESC LIMIT ?,?"),

	SQL_SELECT_ALL_W_UID_S_LOGIN_LIMIT("SELECT *, count(*) OVER () AS count FROM mynews.news "
			+ "WHERE u_id IN (SELECT n_u_id FROM mynews.sgnauthor WHERE u_id IN "
			+ "(SELECT id FROM mynews.users WHERE login=?)) ORDER BY date DESC LIMIT ?,?"),
	SQL_SELECT_ALL_W_UID_S_LOGIN_NO_ADULT_LIMIT("SELECT *, count(*) OVER () AS count FROM mynews.news "
			+ "WHERE style NOT IN ('adult') AND u_id IN (SELECT n_u_id FROM mynews.sgnauthor WHERE u_id IN "
			+ "(SELECT id FROM mynews.users WHERE login=?)) ORDER BY date DESC LIMIT ?,?"),
	;

	private String string;

	NewsSQL(String string) {
		this.string = string;
	}

	public String getSQL() {
		return string;
	}

}
