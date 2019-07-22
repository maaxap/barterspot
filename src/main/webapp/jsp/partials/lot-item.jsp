<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elf" uri="ELFunctions"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" />

<div class="col-md-3 col-sm-6 main-feature">
    <div class="thumbnail" >
        <img src="${pageContext.request.contextPath}/image/lots/${param.id}/${param.id}" alt="" class="img-responsive">
        <div class="caption" style="min-height: 226px;">
            <h3><c:out value="${elf:crop(param.name, 10)}"/></h3>
            <p style="min-height: 97px;"><c:out value="${elf:crop(param.description, 90)}"/></p>
            <p>
                <c:choose>
                    <c:when test="${sessionScope.role != 'client' && sessionScope.role != 'admin'}">
                        <a href="#" data-toggle="modal" data-target="#login-modal" class="btn btn-primary"><fmt:message key="partial.lot_item.more_info"/></a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/main?command=goto_lot&id=${param.id}" class="btn btn-primary"><fmt:message key="partial.lot_item.more_info"/></a>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
    </div>
</div>