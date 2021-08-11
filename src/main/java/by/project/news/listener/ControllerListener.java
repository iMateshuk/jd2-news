package by.project.news.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

@WebListener("Listener for the application")
public class ControllerListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {

		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();

		System.out.println("Request from " + request.getContextPath() + " was destroyed. Remote IP="+request.getRemoteAddr());

	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {

		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();

		System.out.println("Request from " + request.getContextPath() + " was created. Remote IP="+request.getRemoteAddr());

	}

}
