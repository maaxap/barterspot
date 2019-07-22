<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<c:choose>
    <c:when test="${param.lastPage > 1}">
        <div class="col-md-12">
            <div class="row text-center">
                <div class="page-nation">
                    <ul class="pagination pagination-large">

                        <c:choose>
                            <c:when test="${param.lastPage < 8}">
                                <c:forEach begin="1" end="${param.lastPage}" varStatus="loop">
                                    <li ${param.curPage == loop.index ? 'class="active"' : ''}>
                                        <a href="${param.url}&page=${loop.index}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:when test="${param.curPage < 5}">
                                <c:forEach begin="1" end="5" varStatus="loop">
                                    <li ${param.curPage == loop.index ? 'class="active"' : ''}>
                                        <a href="${param.url}&page=${loop.index}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                                <li>
                                    <a style="cursor: none;">...</a>
                                </li>
                                <li>
                                    <a href="${param.url}&page=${param.lastPage}">${param.lastPage}</a>
                                </li>
                            </c:when>
                            <c:when test="${param.curPage < param.lastPage - 1}">
                                <li>
                                    <a href="${param.url}&page=1">1</a>
                                </li>
                                <li>
                                    <a style="cursor: none;">...</a>
                                </li>
                                <c:forEach begin="${param.curPage - 1}" end="${param.curPage + 1}" varStatus="loop">
                                    <li ${param.curPage == loop.index ? 'class="active"' : ''}>
                                        <a href="${param.url}&page=${loop.index}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                                <li>
                                    <a style="cursor: none;">...</a>
                                </li>
                                <li>
                                    <a href="${param.url}&page=${param.lastPage}">${param.lastPage}</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${param.url}&page=1">1</a>
                                </li>
                                <li>
                                    <a style="cursor: not-allowed;">...</a>
                                </li>
                                <c:forEach begin="${param.lastPage - 4}" end="${param.lastPage}" varStatus="loop">
                                    <li ${param.curPage == loop.index ? 'class="active"' : ''}>
                                        <a href="${param.url}&page=${loop.index}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </ul>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>