package com.bigbig.security;


import javax.servlet.*;
import java.io.IOException;

public class BigSecurityFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
    }

}