package by.http.news.util;

public enum UserField implements CombineEnum {
	
	LOGIN, ROLE, AGE;

	@Override
	public void getDescription() {

		System.out.println(getClass().getName());
	}

}
