package com.aparovich.barterspot.filter;

import com.aparovich.barterspot.model.util.LocaleType;
import com.aparovich.barterspot.model.util.RoleType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.aparovich.barterspot.filter.util.FilterConstant.LOCALE;
import static com.aparovich.barterspot.filter.util.FilterConstant.ROLE;

/**
 * Created by Maxim on 14.05.2017
 */
@WebFilter(
        filterName = "SessionFilter",
        urlPatterns = {"/*"},
        servletNames = "MainServlet",
        initParams = {
                @WebInitParam(name = "ROLE", value = ROLE),
                @WebInitParam(name = "LOCALE", value = LOCALE)
        },
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD
        }
)
public class SessionFilter implements Filter {

    /**
     * Role attribute name.
     */
    private String role;

    /**
     * Locale attribute name.
     */
    private String locale;


    public void init(FilterConfig config) throws ServletException {
        this.role = config.getInitParameter("ROLE");
        this.locale = config.getInitParameter("LOCALE");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req ;
        HttpSession session = request.getSession(true);

        Object userRole = session.getAttribute(this.role);
        Object userLocale = session.getAttribute(this.locale);

        if (userRole == null) {
            userRole = RoleType.GUEST.getRole();
            session.setAttribute(this.role, userRole);
        }

        if (userLocale == null) {
            userLocale = LocaleType.EN.getLocale();
            session.setAttribute(this.locale, userLocale);
        }

        chain.doFilter(req, resp);
    }

    public void destroy() {
        this.role = null;
        this.locale = null;
    }
}
