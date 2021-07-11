package by.http.news.util;

public class CheckField {

	private static final String AGE_STRING = "18";
	
	public static void checkKVN(String key, String value) throws UtilException {

		if (checkKVN(value)) {

			throw new UtilException(key + " is empty");
		}
	}
	
	public static boolean checkKVN(String value) {

		return  (value == null || value.isEmpty() || value.isBlank());
	}
	
	public static void checkKVE(String key, String value, String expression) throws UtilException {

		if (value.matches(expression)) {

			throw new UtilException(key + ": find illegal value in: " + value);
		}

	}
	
	public static void checkKVEnot(String key, String value, String expression) throws UtilException {

		if (!value.matches(expression)) {

			throw new UtilException(key + ": find illegal value in: " + value);
		}

	}
	
	
	public static void checkKI(String key, String age) throws UtilException {

		try {
			
			Integer.parseInt(age);
			
		} catch (NumberFormatException | NullPointerException  e) {
			
			throw new UtilException(key + " is not are number " + age);

		}
		

	}
	
	public static void checkKVLMin(String key, String value, int lenght) throws UtilException {

		if (value.length() < lenght) {

			throw new UtilException(key + " lenght must be more then " + lenght);
		}

	}
	
	public static void checkKVLMax(String key, String value, int lenght) throws UtilException {

		if (value.length() > lenght) {

			throw new UtilException(key + " lenght must be less then " + lenght);
		}

	}
	
	public static void checkVA(String value, String age) throws UtilException {

		if (value.equals("adult") && age.compareTo(AGE_STRING) < 0) {

			throw new UtilException(value + ": forbidden style for users younger " + AGE_STRING);
		}

	}

}
