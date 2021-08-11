package by.project.news.util;

public enum Path {
	
	TEMP_DIR(System.getProperty("java.io.tmpdir")),
	LOG_FILE(TEMP_DIR.getPath() +"progectJava.log");

	private String string;

	Path(String string) {
		
		this.string = string;
	}

	public String getPath() {
		
		return string;
	}

}
