package by.http.news.util;

public enum UserDataField implements CombineEnum {
	
	NAME, LOGIN, PASSWORD, EMAIL, ROLE, AGE;

	@Override
	public void getDescription() {
		
		System.out.println(getClass().getName());
	}

}
