<head>
    <%-- Page title --%>
    <title>${sessionScope.user.info.name} ${sessionScope.user.info.surname}</title>

    <%-- Common assets --%>
    <%@include file="../partials/common-assets.jsp"%>

    <%-- Lots page styles --%>
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

    <%-- Page Content --%>
    <div class="container">

        <%-- Delimeter --%>
        <div class="form-group">
            <div class="col-lg-12">
                <hr/>
            </div>
        </div>

        <div class="container target">

            <%-- Delimeter --%>
            <div class="form-group">
                <div class="col-lg-12">
                    <hr>
                </div>
            </div>

            <div class="row">

                <%-- Left side --%>
                <div class="col-md-3">

                    <div class="list-group">
                        <a href="${pageContext.request.contextPath}/main?command=goto_admin&model=category" class="list-group-item">
                            <p class="list-group-item-text"><fmt:message key="page.admin.manage_categories"/></p>
                        </a>
                        <a href="${pageContext.request.contextPath}/main?command=goto_admin&model=bid" class="list-group-item">
                            <p class="list-group-item-text"><fmt:message key="page.admin.all_bids"/></p>
                        </a>
                        <a href="${pageContext.request.contextPath}/main?command=goto_admin&model=user" class="list-group-item">
                            <p class="list-group-item-text"><fmt:message key="page.admin.all_users"/></p>
                        </a>
                    </div>

                    <%-- Site activity and statistics --%>
                    <ul class="list-group">
                        <li class="list-group-item text-muted">
                            <fmt:message key="page.admin.statistic"/>
                        </li>

                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.admin.active_bids"/></strong>
                            </span>
                            ${requestScope.bidsSize}
                        </li>

                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.admin.active_users"/></strong>
                            </span>
                            ${requestScope.usersSize}
                        </li>

                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.admin.active_lots"/></strong>
                            </span>
                            ${requestScope.lotsSize}
                        </li>

                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.admin.categories"/></strong>
                            </span>
                            ${requestScope.categoriesSize}
                        </li>
                    </ul>

                </div>

                <%-- Right side --%>
                <div class="col-md-9" style="" contenteditable="false">

                    <table class="table table-striped">
                        <tbody>
                        <c:choose>
                            <c:when test="${requestScope.model == 'bid'}">
                                <div class="panel-body">
                                    <div class="row text-center ">
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
                                                <c:param name="page" value="admin"/>
                                            </c:import>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.model == 'user'}">
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <c:forEach items="${requestScope.items}" var="item">
                                            <c:import url="${pageContext.request.contextPath}/jsp/partials/user-item.jsp">
                                                <c:param name="id" value="${item.id}"/>
                                                <c:param name="name" value="${item.info.name}"/>
                                                <c:param name="surname" value="${item.info.surname}"/>
                                                <c:param name="createdAt" value="${item.createdAt}"/>
                                                <c:param name="blocked" value="${item.blocked}"/>
                                                <c:param name="locale" value="${sessionScope.locale}"/>
                                                <c:param name="page" value="admin"/>
                                            </c:import>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.model == 'category'}">
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <form action="${pageContext.request.contextPath}/main" id="add-category-form" class="form-horizontal" role="form" method="POST">

                                            <%-- Command type --%>
                                            <input type="hidden" name="command" value="add-category"/>

                                            <%-- New category --%>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label"><fmt:message key="page.admin.category_name"/>:</label>
                                                <div class="col-md-7">
                                                    <input id="category" name="category" class="form-control" type="text">
                                                </div>
                                                <div class="col-md-3">
                                                    <button type="submit" class="btn btn-primary" style="background-color: #5fb053; border-color: #5fb053;"><fmt:message key="page.admin.create"/></button>
                                                </div>
                                            </div>

                                        </form>

                                        <%-- Delimeter --%>
                                        <div class="form-group">
                                            <div class="col-lg-12">
                                                <hr>
                                            </div>
                                        </div>

                                        <form action="${pageContext.request.contextPath}/main" id="delete-category-form" class="form-horizontal" role="form" method="POST">
                                            <input type="hidden" name="command" value="delete-category"/>

                                            <%-- Category to delete --%>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label"></label>
                                                <div class="col-md-7">
                                                    <select id="edit-category" name="category" class="form-control">
                                                        <option disabled selected></option>
                                                        <c:forEach items="${requestScope.items}" var="item">
                                                            <option><c:out value="${item.name}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-3">
                                                    <button type="submit" class="btn btn-danger"><fmt:message key="page.admin.delete"/></button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>

                        </tbody>
                    </table>

                    <%-- Pagination --%>
                    <c:set var="url" value="${pageContext.request.contextPath}/main?command=goto_admin&model=${requestScope.model}"/>
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