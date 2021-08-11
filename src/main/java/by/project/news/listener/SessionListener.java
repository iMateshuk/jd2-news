package by.project.news.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;


@WebListener("Session listener for the application")
public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent sessionEvent) {
    	System.out.println("Session Created:: ID="+sessionEvent.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	System.out.println("Session Destroyed:: ID="+sessionEvent.getSession().getId());
    }
	
}
