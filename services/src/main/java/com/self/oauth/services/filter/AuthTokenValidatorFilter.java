package com.self.oauth.services.filter;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/rest/*")
public class AuthTokenValidatorFilter implements Filter {
	public static final String BEARER_HEADER = "BEARER";

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		if (httpRequest.getHeader(BEARER_HEADER) == null || !isValidAuthToken(httpRequest.getHeader(BEARER_HEADER))) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private static boolean isValidAuthToken(@Nonnull final String header) {
		// (todo: harit) validate token
		return true;
	}

	@Override
	public void destroy() {
	}
}
