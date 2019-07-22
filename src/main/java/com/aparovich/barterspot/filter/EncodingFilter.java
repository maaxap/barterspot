package com.aparovich.barterspot.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

import static com.aparovich.barterspot.filter.util.FilterConstant.ENCODING;

/**
 * Created by Maxim on 17.04.2017
 */
@WebFilter( filterName = "EncodingFilter",
            urlPatterns = {"/*"},
            initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")
            })
public class EncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);
    private String code;

    public void init(FilterConfig config) throws ServletException {
        code = config.getInitParameter(ENCODING);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String codeRequest = req.getCharacterEncoding();

        if(code != null && code.equalsIgnoreCase(codeRequest) || codeRequest == null) {
            req.setCharacterEncoding(code);
            resp.setCharacterEncoding(code);
        }

        chain.doFilter(req, resp);
    }

    public void destroy() {
        code = null;
    }

}
