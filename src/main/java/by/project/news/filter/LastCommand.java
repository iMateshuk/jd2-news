package by.project.news.filter;

import java.io.IOException;

import by.project.news.controller.CommandName;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "/LastCommand", urlPatterns = "/Controller")
public class LastCommand implements Filter {

	private static final String COMMAND_REQUEST_PARAM = "command";
	private static final String LAST_URL = "url";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		
		HttpSession session = req.getSession(false);

		if (session == null) {

			session = req.getSession(true);
		}

		final String lastCommand = req.getParameter(COMMAND_REQUEST_PARAM);

		if (lastCommand != null && !CommandName.CHANGE_LOCAL.toString().equalsIgnoreCase(lastCommand)) {

			session.setAttribute(LAST_URL, lastCommand);
		}

		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
