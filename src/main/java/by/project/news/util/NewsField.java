package by.project.news.util;

public enum NewsField implements CombineEnum {
	
	TITLE, BRIEF, BODY, STYLE;

	@Override
	public void getDescription() {
		
		System.out.println(getClass().getName());
	}

}
