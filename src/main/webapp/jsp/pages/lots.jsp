
<head>

    <%-- Page title --%>
    <title><fmt:message key="page.lots.title"/></title>

    <%-- Header part in common usage --%>
    <%@include file="../partials/common-assets.jsp"%>

    <%-- Lots page styles --%>
    <link href="../../css/login-modal.css" rel="stylesheet">
    <link href="../../css/shop-item.css" rel="stylesheet">

</head>
<body>

    <%-- Navbar --%>
    <%@include file="../partials/user-navbar.jsp"%>

    <noscript>
        <div class="container">
            <div class="alert alert-danger">
                <i><strong><fmt:message key="page.no_script.title"/></strong></i>
                <br>
                <fmt:message key="page.no_script.message"/>
            </div>
        </div>
    </noscript>

    <%-- Message display --%>
    <c:choose>
        <c:when test="${not empty sessionScope.message and sessionScope.messsage != ''}">
            <script>
                $(document).ready(function () {
                    $.notify({
                        message: "${sessionScope.message.message}"
                    }, {
                        element: 'body',
                        type: "${sessionScope.message.type}",
                        newest_on_top: true,
                        placement: {
                            from: 'top',
                            align: 'center'
                        },
                        delay: 3000,
                        spacing: 5,
                        offset: 66
                    });
                });
            </script>
            <c:remove var="message" scope="session"/>
        </c:when>
    </c:choose>

    <%-- Page content --%>
    <div class="container">
        <div class="row">

            <%-- Categories --%>
            <c:choose>
                <c:when test="${empty requestScope.type}">
                    <div class="col-md-3">
                        <p class="lead"><fmt:message key="page.lots.title"/></p>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/main?command=goto_lots" class="list-group-item"><fmt:message key="page.lots.all"/></a>
                            <c:forEach items="${requestScope.categories}" var="category">
                                <a href="${pageContext.request.contextPath}/main?command=goto_lots&category=${pageScope.category.name}" class="list-group-item"><c:out value="${pageScope.category.name}"/></a>
                            </c:forEach>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-md-3">
                        <p class="lead"><fmt:message key="page.lots.title"/></p>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/main?command=goto_profile" class="list-group-item"><fmt:message key="page.lots.to_profile"/></a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <%-- Lots --%>
            <div class="col-md-9">
                <div class="row text-center">
                    <c:forEach items="${requestScope.items}" var="item">
                        <c:import url="/jsp/partials/lot-item.jsp">
                            <c:param name="name" value="${item.name}"/>
                            <c:param name="description" value="${item.description}"/>
                            <c:param name="id" value="${item.id}"/>
                        </c:import>
                    </c:forEach>
                </div>

                <%-- Pagination --%>
                <c:choose>
                    <c:when test="${not empty requestScope.category}">
                        <c:set var="url" value="${pageContext.request.contextPath}/main?command=goto_lots&category=${requestScope.category}"/>
                    </c:when>
                    <c:when test="${not empty requestScope.type}">
                        <c:set var="url" value="${pageContext.request.contextPath}/main?command=goto_lots&type=${requestScope.type}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="url" value="${pageContext.request.contextPath}/main?command=goto_lots"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${not empty requestScope.curPage}">
                        <div class="row text-center">
                            <c:import url="${pageContext.request.contextPath}/jsp/partials/pagination.jsp">
                                <c:param name="url" value="${url}"/>
                                <c:param name="lastPage" value="${requestScope.lastPage}"/>
                                <c:param name="curPage" value="${requestScope.curPage}"/>
                            </c:import>
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
