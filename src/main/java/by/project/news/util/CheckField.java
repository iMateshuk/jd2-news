package by.project.news.util;

import java.util.List;

public class CheckField {

	private static final String AGE_STRING = "18";
	private static final String ADULT = "adult";

	public static void checkValueNull(String value) throws UtilException {

		if (isValueNull(value)) {

			throw new UtilException("cvn");
		}
	}

	public static boolean isValueNull(String value) {

		return (value == null || value.isEmpty() || value.isBlank());
	}
	
	public static void checkValueNull(Integer value) throws UtilException {

		if (isValueNull(value)) {

			throw new UtilException("cvn");
		}
	}
	
	public static boolean isValueNull(Integer value) {

		return (value == null);
	}

	public static void checkValueExpression(String value, String expression) throws UtilException {

		if (value.matches(expression)) {

			throw new UtilException("cve");
		}

	}

	public static void checkValueNotExpression(String value, String expression) throws UtilException {

		if (!value.matches(expression)) {

			throw new UtilException("cvne");
		}

	}

	public static void checkStringIsNumber(String number) throws UtilException {

		try {

			Integer.parseInt(number);

		} catch (NumberFormatException | NullPointerException e) {

			throw new UtilException("csin");
		}

	}

	public static void checkValueLengthMin(String value, int length) throws UtilException {

		if (value.length() < length) {

			throw new UtilException("cvlmi");
		}

	}

	public static void checkValueLengthMax(String value, int length) throws UtilException {

		if (value.length() > length) {

			throw new UtilException("cvlma");
		}

	}

	public static void checkAdultAndAge(String adult, String age) throws UtilException {

		if (adult.equals(ADULT) && checkAge(age)) {

			throw new UtilException("caaa");
		}

	}

	public static boolean checkAge(String age) {

		return age.compareTo(AGE_STRING) < 0;
	}

	public static void checkListNullEmp(List<?> list) throws UtilException{

		if (list == null || list.isEmpty()) {

			throw new UtilException("newsdaoload");
		}
	}
}
