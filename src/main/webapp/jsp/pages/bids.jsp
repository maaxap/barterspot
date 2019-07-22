
<head>

    <%-- Page title --%>
    <title><fmt:message key="partial.bids.title_bids"/></title>

    <%-- Common assets --%>
    <%@include file="../partials/common-assets.jsp"%>

    <%-- Lots page styles --%>
    <link href="../../css/login-modal.css" rel="stylesheet">
    <link href="../../css/bunch.css" rel="stylesheet">

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
    <div class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="text-center"><fmt:message key="partial.bids.title_bids"/></h1>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12 well">
                    <a href="${pageContext.request.contextPath}/main?command=goto_profile" class="btn btn-default btn-lg col-lg-2">
                        <fmt:message key="partial.bids.to_profile"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover table-striped">
                        <tbody>
                        <c:choose>
                            <c:when test="${not empty requestScope.items}">
                                <c:forEach items="${requestScope.items}" var="item">
                                    <c:import url="${pageContext.request.contextPath}/jsp/partials/bid-item.jsp">
                                        <c:param name="id" value="${item.id}"/>
                                        <c:param name="bid" value="${item.bid}"/>
                                        <c:param name="createdAt" value="${item.createdAt}"/>
                                        <c:param name="lotId" value="${item.lot.id}"/>
                                        <c:param name="lotName" value="${item.lot.name}"/>
                                        <c:param name="userId" value="${item.lot.user.id}"/>
                                        <c:param name="userName" value="${item.lot.user.info.name}"/>
                                        <c:param name="userSurname" value="${item.lot.user.info.surname}"/>
                                        <c:param name="locale" value="${sessionScope.locale}"/>
                                    </c:import>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        </tbody>
                    </table>

                    <%-- Pagination --%>
                    <c:set var="url" value="/main?command=goto_bids"/>
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
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
