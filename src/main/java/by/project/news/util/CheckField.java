package by.project.news.util;

public class CheckField {

	private static final String AGE_STRING = "18";
	
	private static final String IS_EMPTY = " is empty";
	private static final String ILLEGAL_VALUE = ": find illegal value in: ";
	private static final String NOT_NUMBER = " is not are number ";
	private static final String LENGHT_MORE = " lenght must be more then ";
	private static final String LENGHT_LESS = " lenght must be less then ";
	private static final String DENY_STYLE = ": forbidden style for users younger ";
	
	public static void checkKVN(String key, String value) throws UtilException {

		if (checkKVN(value)) {

			throw new UtilException(key + IS_EMPTY);
		}
	}
	
	public static boolean checkKVN(String value) {

		return  (value == null || value.isEmpty() || value.isBlank());
	}
	
	public static void checkKVE(String key, String value, String expression) throws UtilException {

		if (value.matches(expression)) {

			throw new UtilException(key + ILLEGAL_VALUE + value);
		}

	}
	
	public static void checkKVEnot(String key, String value, String expression) throws UtilException {

		if (!value.matches(expression)) {

			throw new UtilException(key + ILLEGAL_VALUE + value);
		}

	}
	
	
	public static void checkKI(String key, String age) throws UtilException {

		try {
			
			Integer.parseInt(age);
			
		} catch (NumberFormatException | NullPointerException  e) {
			
			throw new UtilException(key + NOT_NUMBER + age);

		}
		

	}
	
	public static void checkKVLMin(String key, String value, int lenght) throws UtilException {

		if (value.length() < lenght) {

			throw new UtilException(key + LENGHT_MORE + lenght);
		}

	}
	
	public static void checkKVLMax(String key, String value, int lenght) throws UtilException {

		if (value.length() > lenght) {

			throw new UtilException(key + LENGHT_LESS + lenght);
		}

	}
	
	public static void checkVA(String value, String age) throws UtilException {

		if (value.equals("adult") && age.compareTo(AGE_STRING) < 0) {

			throw new UtilException(value + DENY_STYLE + AGE_STRING);
		}

	}

}
