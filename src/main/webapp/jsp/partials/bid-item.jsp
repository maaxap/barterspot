<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jct" uri="JspCustomTags"%>
<%@taglib prefix="elf" uri="ELFunctions" %>


<fmt:setLocale value="${param.locale}" />
<fmt:setBundle basename="content" />

<tr>
    <td>
        <a href="#"></a>
    </td>
    <td>
        <h4>
            <a href="${pageContext.request.contextPath}/main?command=goto_lot&id=${param.lotId}"><c:out value="${elf:crop(param.lotName, 10)}"/></a>
        </h4>
        <p><jct:date-time-formatter format="d MMM yyyy hh:mm:ss" locale="${param.locale}">${param.createdAt}</jct:date-time-formatter></p>
    </td>
    <td>
        <h4>
            <a href="${pageContext.request.contextPath}/main?command=goto_user&id=${param.userId}"><fmt:message key="partial.bid_item.lot_owner"/>: <c:out value="${param.userName}"/> <c:out value="${param.userSurname}"/></a>
        </h4>
    </td>
    <td>
        <h4>
            <b>${elf:formatBid(param.bid)}&nbsp;$</b>
        </h4>
    </td>
    <td></td>
    <td>
        <div class="btn-group">
            <form action="${pageContext.request.contextPath}/main" method="POST" id="delete-bid-form">
                <input type="hidden" name="command" value="delete-bid"/>
                <input type="hidden" name="id" value="${param.id}"/>
                <input type="hidden" name="page" value="${param.page}"/>
                <button type="submit" class="btn btn-danger btn-lg"><fmt:message key="partial.bids.delete"/></button>
            </form>
        </div>
    </td>
</tr>

