package by.http.news.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
	
public static void writeLog(Exception e) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
		
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(Path.LOG_FILE.getPath(), true))) {
			
			
			pw.write("writeLog :: " + sdf.format(new Date()) + " ::\n");
			
			e.printStackTrace(pw);
			pw.flush();
			pw.write("\n");

		} catch (FileNotFoundException ignore) {
			// TODO Auto-generated catch block
			System.out.println("Can't write " + Path.LOG_FILE);
		}
	}

}
