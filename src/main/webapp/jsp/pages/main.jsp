<head>
    <%-- Page title --%>
    <title><fmt:message key="page.main.title"/></title>

    <%-- Header part in common usage --%>
        <%@include file="../partials/common-assets.jsp"%>
</head>
<body>

    <%-- Navbar --%>
    <c:choose>
        <c:when test="${sessionScope.role == 'client' or sessionScope.role == 'admin'}">
            <%@include file="../partials/user-navbar.jsp"%>
        </c:when>
        <c:otherwise>
            <%@include file="../partials/guest-navbar.jsp"%>
        </c:otherwise>
    </c:choose>

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

        <header class="jumbotron main-spacer">
            <h1><fmt:message key="page.main.barterspot"/></h1>
            <p><fmt:message key="page.main.welcome"/></p>
            <p>
                <c:choose>
                    <c:when test="${sessionScope.role != 'client' && sessionScope.role != 'admin'}">
                        <a href="#" data-toggle="modal" data-target="#login-modal" class="btn btn-primary btn-lg"><fmt:message key="page.main.all_lots"/></a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/main?command=goto_lots"><fmt:message key="page.main.all_lots"/></a>
                    </c:otherwise>
                </c:choose>
            </p>
        </header>

        <hr>

        <%-- Title --%>
        <div class="row">
            <div class="col-lg-12">
                <h3><fmt:message key="page.main.end_soon"/></h3>
            </div>
        </div>

        <%-- Latest added lots --%>
        <div class="row text-center">
            <c:forEach items="${requestScope.lots}" var="lot">
                <c:import url="${pageContext.request.contextPath}/jsp/partials/lot-item.jsp">
                    <c:param name="name" value="${lot.name}"/>
                    <c:param name="description" value="${lot.description}"/>
                    <c:param name="id" value="${lot.id}"/>
                </c:import>
            </c:forEach>
        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
