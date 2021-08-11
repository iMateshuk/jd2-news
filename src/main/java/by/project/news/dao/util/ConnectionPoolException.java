package by.project.news.dao.util;

public class ConnectionPoolException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConnectionPoolException() {

	}

	public ConnectionPoolException(String message) {

		super(message);
	}

	public ConnectionPoolException(Exception e) {

		super(e);
	}

	public ConnectionPoolException(String message, Exception e) {
		
		super(message, e);
	}

}
