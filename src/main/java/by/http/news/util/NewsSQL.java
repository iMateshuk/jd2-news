package by.http.news.util;

public enum NewsSQL {

	SQL_INSERT_NEWS("INSERT INTO mynews.news(title,brief,body,style,date) VALUES(?,?,?,?,?)"),
	SQL_UPDATE_NEWS("UPDATE mynews.news SET title=?, brief=?, body=?, style=?, date=? WHERE id=?"),

	SQL_ORDER_BY_DATE("ORDER BY date DESC"),
	SQL_DESC_LIMIT("LIMIT ?"),
	SQL_NEWS_WHERE_TITLE("WHERE title=?"),
	SQL_NEWS_WHERE_TITLE_LIKE("WHERE title LIKE ?"),
	SQL_NEWS_WHERE_TITLE_AND_STYLE(SQL_NEWS_WHERE_TITLE.getSQL() + " AND style=?"),

		SQL_IN("IN (?)"),
	SQL_NOT_IN("NOT " + SQL_IN.getSQL()),
	SQL_NOT_IN_ADULT("NOT IN ('adult')"),
	
	SQL_COLLUM_LABEL_ID("id"),
	SQL_COLLUM_LABEL_TITLE("title"),
	
	SQL_DELETE_NEWS_TITLE("DELETE FROM " + SQL_NEWS_WHERE_TITLE.getSQL()),
	
	SQL_SELECT_ALL("SELECT * FROM mynews.news"),
	SQL_SELECT_TITLE_ID("SELECT title, id FROM " + SQL_NEWS_WHERE_TITLE.getSQL()),
	SQL_SELECT_TITLE_STYLE("SELECT * FROM mynews.news "+ SQL_NEWS_WHERE_TITLE_AND_STYLE.getSQL()),
	SQL_SELECT_FOR_LOAD("SELECT * FROM mynews.news WHERE style NOT IN ('adult') " 
			+ SQL_ORDER_BY_DATE.getSQL() + " LIMIT 10"),
	SQL_SELECT_CHOOSE("SELECT * FROM mynews.news WHERE title LIKE ? AND style "),
	;
	private String string;

	NewsSQL(String string) {
		this.string = string;
	}

	public String getSQL() {
		return string;
	}

}
