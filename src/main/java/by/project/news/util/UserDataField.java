package by.project.news.util;

public enum UserDataField implements CombineEnum {
	
	NAME, LOGIN, PASSWORD, EMAIL, ROLE, AGE, OLDPASSWORD;

	@Override
	public void getDescription() {
		
		System.out.println(getClass().getName());
	}

}
