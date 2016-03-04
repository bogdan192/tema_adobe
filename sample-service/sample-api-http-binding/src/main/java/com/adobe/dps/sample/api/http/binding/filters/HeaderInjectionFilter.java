package com.adobe.dps.sample.api.http.binding.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HeaderInjectionFilter implements javax.servlet.Filter {

	private String header;
	private String value;
	  
	public void init(FilterConfig fc) throws ServletException {
		header = fc.getInitParameter("header");
		value = fc.getInitParameter("value");
	}
	  
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader(header, value);
		chain.doFilter(request, resp);
	}

	@Override
	public void destroy() {
	}

}
