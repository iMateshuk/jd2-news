package by.project.news.util;

import java.util.HashMap;
import java.util.Map;

public class FieldMapCreator {

	private static final String EMPTY = "";

	public static Map<CombineEnum, String> create(CombineEnum... fields) {

		Map<CombineEnum, String> fieldsData = new HashMap<>();

		for (CombineEnum field : fields) {

			fieldsData.put(field, EMPTY);
		}

		return fieldsData;

	}

}
