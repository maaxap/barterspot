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
            <a href="${pageContext.request.contextPath}/main?command=goto_user&id=${param.id}"><c:out value="${param.name}"/> <c:out value="${param.surname}"/></a>
        </h4>
        <p><jct:date-time-formatter format="d MMM yyyy hh:mm:ss" locale="${param.locale}">${param.createdAt}</jct:date-time-formatter></p>
    </td>
    <td>
        <img src="${pageContext.request.contextPath}/image/users/${param.id}/${param.id}" class="img-thumbnail" width="60">
    </td>
    <td>
        <div class="btn-group">
            <c:choose>
                <c:when test="${param.blocked == false}">
                    <form action="${pageContext.request.contextPath}/main" method="POST" id="block-user-form">
                        <input type="hidden" name="command" value="block-user"/>
                        <input type="hidden" name="id" value="${param.id}"/>
                        <input type="hidden" name="page" value="${param.page}"/>
                        <button type="submit" class="btn btn-danger btn-lg"><fmt:message key="partial.users.block"/></button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/main" method="POST" id="unblock-user-form">
                        <input type="hidden" name="command" value="unblock-user"/>
                        <input type="hidden" name="id" value="${param.id}"/>
                        <input type="hidden" name="page" value="${param.page}"/>
                        <button type="submit" class="btn btn-danger btn-lg"><fmt:message key="partial.users.unblock"/></button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </td>
    <td></td>
    <td>
        <div class="btn-group">
            <form action="${pageContext.request.contextPath}/main" method="POST" id="delete-user-form">
                <input type="hidden" name="command" value="delete-user"/>
                <input type="hidden" name="id" value="${param.id}"/>
                <button type="submit" class="btn btn-danger btn-lg"><fmt:message key="partial.users.delete"/></button>
            </form>
        </div>
    </td>
</tr>

