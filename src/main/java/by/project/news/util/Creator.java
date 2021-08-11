package by.project.news.util;

public interface Creator <T, V> {
	
	T create(V object) throws UtilException;
	
	T create() throws UtilException;
}
