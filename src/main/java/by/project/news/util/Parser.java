package by.project.news.util;

public class Parser {
	
	private final static String EMPTY = "";
	private final static String EXP_REPLACE_FIRST = ".*:: ";

	public static String excRemovePath(String message) {
		
		return message.replaceFirst(EXP_REPLACE_FIRST, EMPTY);
	}
}
