package by.http.news.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WorkWithObjectField {

	private static final String START_SET = "set";
	private static final String START_GET = "get";
	private static final String VALUE_EMPTY = "";

	public static void value(Object object) throws UtilException {

		Object value;

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {

			field.setAccessible(true);

			try {

				value = field.get(object);
				System.out.println("val: " + value);

			} catch (IllegalArgumentException | IllegalAccessException e) {

				throw new UtilException(e.getMessage(), e);
			}

		}
	}

	public static String methodGet(Object object, String nameMatch) throws UtilException {

		Method[] methods = object.getClass().getMethods();

		String methodName;
		String value = VALUE_EMPTY; 
		nameMatch = START_GET + nameMatch;

		for (Method method : methods) {

			methodName = method.getName();
			
			if (methodName.startsWith(START_GET) && methodName.equalsIgnoreCase(nameMatch)) {
				
				try {

					value = (String) method.invoke(object);

					break;
					
					//System.out.println("method: " + method.invoke(object));

				} catch (IllegalAccessException | InvocationTargetException e) {

					throw new UtilException(e.getMessage(), e);
				}
			}
		}
		
		return value;

	}

	public static void methodSet(Object object, String nameMatch, String value) throws UtilException {

		Method[] methods = object.getClass().getMethods();

		String methodName;
		nameMatch = START_SET + nameMatch;

		for (Method method : methods) {

			methodName = method.getName();

			if (methodName.startsWith(START_SET) && methodName.equalsIgnoreCase(nameMatch)) {

				try {
					
					method.invoke(object, value);
					break;
					
				} catch (IllegalAccessException | InvocationTargetException e) {

					throw new UtilException(e.getMessage(), e);
				}
			}
		}

	}

}
