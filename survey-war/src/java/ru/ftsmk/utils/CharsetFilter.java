/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author plintus
 */
public class CharsetFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        Logger.getLogger(CharsetFilter.class.getName()).log(Level.INFO, "CharsetFilter encoding {0}", encoding);
        if (encoding == null) {
            encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        Logger.getLogger(CharsetFilter.class.getName()).log(Level.INFO, "doFilter");
        //Logger.getLogger(CharsetFilter.class.getName()).log(Level.INFO, "object: {0} ", request.getParameter("object"));
        request.setCharacterEncoding("UTF-8");
        //application/x-www-form-urlencoded; charset=UTF-8
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        next.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
