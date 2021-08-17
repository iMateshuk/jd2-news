package by.project.news.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/*@WebFilter("/CharsetFilter")*/
public class CharsetFilter implements Filter {

	private String encoding;

	@Override
	public void init(FilterConfig filterConfig) {

		encoding = filterConfig.getInitParameter("chracterEncoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}

}
