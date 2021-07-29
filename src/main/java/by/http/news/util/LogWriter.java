package by.http.news.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private static final String WRITE_LOG_START_ROW = "writeLog :: ";
	private static final String WRITE_LOG_END_ROW = " ::\n";
	private static final String WRITE_CAN_NOT = "Can't write ";
	private static final String WRITE_LOG_N = "\n";
	
public static void writeLog(Exception e) {
		
		
		
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(Path.LOG_FILE.getPath(), true))) {
			
			
			pw.write(WRITE_LOG_START_ROW + SDF.format(new Date()) + WRITE_LOG_END_ROW);
			
			e.printStackTrace(pw);
			pw.flush();
			pw.write(WRITE_LOG_N);

		} catch (FileNotFoundException ignore) {

			System.out.println(WRITE_CAN_NOT + Path.LOG_FILE);
		}
	}

}
