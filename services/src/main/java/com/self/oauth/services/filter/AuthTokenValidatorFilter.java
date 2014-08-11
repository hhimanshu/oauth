package com.self.oauth.services.filter;

import java.io.IOException;
import java.util.regex.Pattern;

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

import com.self.oauth.business.oauth.ClientRegistrationManager;
import com.self.oauth.business.oauth.UniqueIdGenerator;

@WebFilter("/rest/*")
public class AuthTokenValidatorFilter implements Filter {
	private static final String BEARER_HEADER = "BEARER";
	private static final String COLON = ":";
	private static final Pattern PATTERN = Pattern.compile(COLON);

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
		final String[] tokenParts = PATTERN.split(header);

		if (tokenParts.length != 3) {
			// hash, uuid, timestamp
			return false;
		}

		final int nanoTimeStamp;
		try {
			nanoTimeStamp = Integer.parseInt(tokenParts[2]);
		} catch (final NumberFormatException e) {
			return false;
		}

		final String hashedAuthToken = new UniqueIdGenerator(ClientRegistrationManager.SERVER_PRIVATE_KEY).getHashedAuthToken(tokenParts[1], nanoTimeStamp);
		return hashedAuthToken.equals(tokenParts[0]);
	}

	@Override
	public void destroy() {
	}
}
