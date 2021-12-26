package com.lz.crm.web.filter;

import com.lz.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns ={"*.jsp","*.do"})
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();

        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path))

            filterChain.doFilter(request, response);
        else {
            System.out.println("------loginFilter----");
            User user = (User) request.getSession().getAttribute("user");

            if (user == null)
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            else filterChain.doFilter(request, response);
        }
    }
}