<head>

    <%-- Page title. --%>
    <title><fmt:message key="page.error403.title"/></title>

        <%-- Header common assets. --%>
    <%@include file="../../partials/common-assets.jsp"%>

</head>
<body>

    <noscript>
        <div class="container">
            <div class="alert alert-danger">
                <i><strong><fmt:message key="page.no_script.title"/></strong></i>
                <br>
                <fmt:message key="page.no_script.message"/>
            </div>
        </div>
    </noscript>
    <%-- Message display. --%>
    <c:choose>
        <c:when test="${not empty sessionScope.message and sessionScope.messsage != ''}">
            <script>
                $(document).ready(function () {
                    $.notify({
                        message: "${sessionScope.message.message}"
                    }, {
                        element: 'body',
                        type: '${sessionScope.message.type}',
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

    <%-- Navbar. --%>
    <c:choose>
        <c:when test="${sessionScope.role != 'client' && sessionScope.role != 'admin'}">
            <%@include file="../../partials/guest-navbar.jsp"%>
        </c:when>
        <c:otherwise>
            <%@include file="../../partials/user-navbar.jsp"%>
        </c:otherwise>
    </c:choose>

    <%-- Page content. --%>
    <div class="container">

        <header class="jumbotron main-spacer">
            <h1><fmt:message key="page.error403.title"/></h1>
            <p><fmt:message key="page.error403.message"/></p>
        </header>

    </div>

    <%-- Footer. --%>
    <%@include file="../../partials/footer.jsp"%>

</body>
