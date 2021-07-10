package by.http.news.util;

import java.util.HashMap;
import java.util.Map;

public class FieldMapCreator {
	
	private static final Map<CombineEnum, String> fieldsData = new HashMap<>();
	
	private static final String EMPTY = "";
	
	public static Map<CombineEnum, String> create(CombineEnum... fields){
		
		synchronized (fieldsData) {
			
			fieldsData.clear();
			
			for (CombineEnum field : fields) {

				fieldsData.put(field, EMPTY);
				
			}
			
			return fieldsData;
			
		}
		
		
	}

}
