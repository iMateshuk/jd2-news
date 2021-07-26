package by.http.news.util;

public enum NewsSQL {
	
	SQL_INSERT_NEWS("INSERT INTO news(title,brief,body,style,date) VALUES(?,?,?,?,?)"),
	SQL_UPDATE_NEWS("UPDATE news SET title=?, brief=?, body=?, style=?, date=? WHERE id=?"),
	SQL_DELETE_NEWS_TITLE("DELETE FROM news WHERE title=?"),
	
	SQL_SELECT_TITLE_ID("SELECT title, id FROM news WHERE title=?"),
	SQL_SELECT_ALL("SELECT * FROM news"),
	SQL_SELECT_TITLE_STYLE("SELECT * FROM news WHERE title=? AND style=?"),
	SQL_SELECT_DATE_FOR_LOAD("SELECT * FROM news where style NOT IN ('adult') ORDER BY date DESC LIMIT 10"),
	SQL_SELECT_CHOOSE("SELECT * FROM news WHERE title LIKE ? AND style "),

	SQL_COLLUM_LABEL_ID("id"),
	SQL_COLLUM_LABEL_TITLE("title"),
	
	SQL_ORDER_BY_DATE(" ORDER BY date DESC");
	
	private String string;

	NewsSQL (String string) {
		this.string = string;
	}

	public String getSQL() {
		return string;
	}

}
