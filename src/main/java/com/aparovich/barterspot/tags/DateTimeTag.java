package com.aparovich.barterspot.tags;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Maxim on 16.04.2017
 */
public class DateTimeTag extends BodyTagSupport {
    private static final Logger LOGGER = LogManager .getLogger(DateTimeTag.class);

    private String format;
    private String locale;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doAfterBody() throws JspException {
        BodyContent content = this.getBodyContent();
        if(content == null) {
            return SKIP_BODY;
        }
        String body = content.getString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        formatter = formatter.withLocale(Locale.forLanguageTag(this.locale.replace('_', '-')));
        JspWriter writer = content.getEnclosingWriter();

        try {
            writer.write(LocalDateTime.parse(body).format(formatter));
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.getMessage());
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }
}
