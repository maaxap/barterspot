package com.aparovich.barterspot.filter;

import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aparovich.barterspot.filter.util.FilterConstant.PAGE_INDEX;

/**
 * @author Maxim
 */
@WebFilter(
        filterName = "PagesFilter",
        urlPatterns = {"/jsp/*"},
        initParams = {
                @WebInitParam(name = "PAGE_INDEX_PROPERTY", value = PAGE_INDEX)
        }
)
public class PagesFilter implements Filter {

    private String pageIndexProperty;

    public void init(FilterConfig config) throws ServletException {
        this.pageIndexProperty = config.getInitParameter("PAGE_INDEX_PROPERTY");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        response.sendRedirect(PagesManager.getProperty(this.pageIndexProperty));
        chain.doFilter(req, resp);
    }

    public void destroy() {
        this.pageIndexProperty = null;
    }

}
