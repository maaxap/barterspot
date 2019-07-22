package com.aparovich.barterspot.command;

import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.validator.ParametersValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.CUR_PAGE;
import static com.aparovich.barterspot.command.util.CommandConstant.LAST_PAGE;
import static com.aparovich.barterspot.command.util.CommandConstant.PAGE;

/**
 * {@code interface} for realization of the <i>Command</i> pattern.
 *
 * @author Maxim Aparovich
 */
public interface Command {
    PageNavigator execute(HttpServletRequest request);

    default List paginate(HttpServletRequest request, List items, int perPage) {
        String page = request.getParameter(PAGE);
        int size = items.size();
        int lastPage = (int) Math.ceil((double) size / perPage);
        if (ParametersValidator.checkIntegerFormat(page)) {
            Integer curPage = Integer.valueOf(page);
            if (curPage > lastPage) {
                curPage = lastPage;
            }
            if (size > perPage * curPage) {
                items = items.subList(perPage * (curPage - 1), perPage * curPage);
            } else {
                items = items.subList(perPage * (curPage - 1), size);
            }
            request.setAttribute(CUR_PAGE, curPage);
            request.setAttribute(LAST_PAGE, lastPage);
        } else if (size > perPage) {
            items = items.subList(0, perPage);
            request.setAttribute(CUR_PAGE, 1);
            request.setAttribute(LAST_PAGE, lastPage);
        } else {
            items = items.subList(0, size);
            request.setAttribute(CUR_PAGE, 1);
            request.setAttribute(LAST_PAGE, 1);
        }
        return items;
    }
}
