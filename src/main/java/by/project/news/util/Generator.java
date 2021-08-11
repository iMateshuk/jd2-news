package by.project.news.util;

import java.util.Random;

public class Generator {
	
	private static final String AZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMBER = "0123456789";
	
	public static String genString(int stringLenght) {

		return localGenerator(stringLenght, AZ);
	}
	
	public static String genNumber(int stringLenght) {
		
		return localGenerator(stringLenght, NUMBER);
	}
	
	public static String genStringHash(String string) {
		
		char[] chars = string.toCharArray();
		
		int hash = 7;
		
		for (int i = 0; i < string.length(); i++) {
			
		    hash = hash*31 + chars[i];
		}
		
		return String.valueOf(hash);
	}
	
	private static String localGenerator(final int stringLenght, final String AZNUMBER) {
		
		Random random = new Random();

		StringBuilder sb = new StringBuilder();
		
		while (true) {
			
			sb.append(AZNUMBER.charAt(random.nextInt(AZNUMBER.length())));
			
			if (!sb.toString().equals("0")) {

				break;
			}
			
			sb.setLength(0);
			
		}
	
		for (int i = 0; i < stringLenght - 1; i++) {
			sb.append(AZNUMBER.charAt(random.nextInt(AZNUMBER.length())));
		}
		
		String strnig = sb.toString().toLowerCase();
		return strnig.substring(0, 1).toUpperCase().concat(strnig.substring(1));
		
	}

}
