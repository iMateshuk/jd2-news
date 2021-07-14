package by.http.news.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/CharsetFilter")
public class CharsetFilter implements Filter {

	private String encoding;

	@Override
	public void init(FilterConfig filterConfig) {

		encoding = filterConfig.getInitParameter("chracterEncoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse responce, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding(encoding);
		responce.setCharacterEncoding(encoding);

		chain.doFilter(request, responce);

	}

	@Override
	public void destroy() {

	}

}
