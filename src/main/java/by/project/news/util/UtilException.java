package by.project.news.util;

public class UtilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilException() {

	}

	public UtilException(String message) {

		super(message);
	}

	public UtilException(Exception e) {

		super(e);
	}

	public UtilException(String message, Exception e) {
		
		super(message, e);
	}

}
