package by.project.news.filter;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/*@WebFilter(filterName = "/RequestLogging", urlPatterns = "/Controller")*/
public class RequestLogging implements Filter {

	private ServletContext context;

	public void init(FilterConfig filterConfig) {

		this.context = filterConfig.getServletContext();
		this.context.log("LoggingFilter initialized");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		Enumeration<String> param = req.getParameterNames();

		while (param.hasMoreElements()) {
			String name = (String) param.nextElement();
			String value = request.getParameter(name);

			this.context.log(req.getRemoteAddr() + " :: Request Param :: {" + name + " = " + value + "}");

			Cookie[] cookies = req.getCookies();

			if (cookies != null) {

				for (Cookie cookie : cookies) {

					this.context.log(req.getRemoteAddr() + " :: Cookie :: {" + cookie.getName() + ", "
							+ cookie.getValue() + "}");

				}
			}

			chain.doFilter(request, response);

		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
