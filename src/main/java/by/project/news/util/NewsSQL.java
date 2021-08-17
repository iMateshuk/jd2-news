package by.project.news.util;

public enum NewsSQL {

	SQL_INSERT_NEWS("INSERT INTO mynews.news(title,brief,body,style,date, u_id) VALUES(?,?,?,?,?,?)"),
	
	SQL_UPDATE_NEWS("UPDATE mynews.news SET title=?, brief=?, body=?, style=?, date=? WHERE id=?"),

	SQL_ORDER_BY_DATE("ORDER BY date DESC"),

	SQL_IN("IN (?)"),
	SQL_NOT_IN_ADULT("NOT IN ('adult')"),

	SQL_COLLUM_LABEL_ID("id"),
	SQL_COLLUM_LABEL_TITLE("title"),
	SQL_COLLUM_LABEL_U_ID("u_id"),

	SQL_DELETE_NEWS_TITLE("DELETE FROM mynews.news WHERE title=?"),
	SQL_DELETE_NEWS_ID("DELETE FROM mynews.news WHERE id=?"),

	SQL_SELECT_ALL("SELECT * FROM mynews.news"),
	SQL_SELECT_NEWS_ID_W_UID_A_TITLE(
			"SELECT n.id FROM mynews.news n JOIN mynews.users u ON u.id = n.u_id WHERE u_id =? AND title=?"),
	SQL_SELECT_TITLE_ID_W_TITLE("SELECT title, id FROM mynews.news WHERE title=?"),
	SQL_SELECT_UID_W_TITLE("SELECT u_id FROM mynews.news WHERE title=?"),
	SQL_SELECT_ALL_W_TITLE_A_STYLE("SELECT * FROM mynews.news WHERE title=? AND style=?"),
	SQL_SELECT_ALL_FOR_LOAD("SELECT * FROM mynews.news WHERE style NOT IN ('adult') ORDER BY date DESC LIMIT 10"),
	SQL_SELECT_ALL_W_TITLE_A_STYLE_CONCAT("SELECT * FROM mynews.news WHERE title LIKE ? AND style "),
	SQL_SELECT_ALL_W_TITLE("SELECT * FROM mynews.news WHERE title=?"),
	;

	private String string;

	NewsSQL(String string) {
		this.string = string;
	}

	public String getSQL() {
		return string;
	}

}
