package by.http.news.util;

public enum UserField implements CombineEnum {
	
	LOGIN, ROLE, AGE;

	@Override
	public void getDescription() {
		// TODO Auto-generated method stub
		System.out.println(getClass().getName());
	}

}
