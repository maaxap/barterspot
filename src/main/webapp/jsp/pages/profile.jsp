<head>

    <%-- Page title --%>
    <title>${sessionScope.user.info.name} ${sessionScope.user.info.surname}</title>

    <%-- Header part in common usage --%>
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
                <%--<hr>--%>
            </div>
        </div>

        <div class="container target">
            <div class="row">
                <div class="col-md-6">

                    <%-- Name Surname --%>
                    <h1 class=""><c:out value="${sessionScope.user.info.name}"/> <c:out value="${sessionScope.user.info.surname}"/></h1>
                    <br/>

                    <%--<a href="#" class="btn btn-primary"><fmt:message key="page.profile.message"/></a>--%>

                    <br/>
                </div>

                <%-- Profile image --%>
                <div class="col-md-6">
                    <a href="#" class="pull-right">
                        <img title="profile image" class="img-responsive img-thumbnail" src="${pageContext.request.contextPath}/image/users/${sessionScope.user.id}/${sessionScope.user.id}" style="max-height: 400px;">
                    </a>
                </div>

            </div>

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
                        <a href="${pageContext.request.contextPath}/main?command=goto_edit_profile" class="list-group-item">
                            <p class="list-group-item-text"><fmt:message key="page.profile.edit"/></p>
                        </a>
                        <a href="${pageContext.request.contextPath}/main?command=goto_settings" class="list-group-item">
                            <p class="list-group-item-text"><fmt:message key="page.profile.settings"/></p>
                        </a>
                        <a href="${pageContext.request.contextPath}/main?command=goto_bids" class="list-group-item">
                            <p class="list-group-item-text"><fmt:message key="page.profile.all_bids"/></p>
                        </a>
                    </div>

                    <%-- Profile info --%>
                    <ul class="list-group">

                        <%-- Title --%>
                        <li class="list-group-item text-muted" contenteditable="false">
                            <fmt:message key="page.profile.info"/>
                        </li>

                        <%-- Email --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.email"/></strong>
                                    </span>
                            <c:out value="${sessionScope.user.email}"/>
                        </li>

                        <%-- Created at --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.joined"/></strong>
                                    </span>
                            <jct:date-time-formatter format="yyyy-MM-dd" locale="${sessionScope.locale}">${sessionScope.user.createdAt}</jct:date-time-formatter>
                        </li>

                        <%-- Birth date --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.birth_date"/></strong>
                                    </span>
                            ${sessionScope.user.info.birthDate}
                        </li>

                        <%-- Phone number --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.phone_number"/></strong>
                                    </span>
                            <c:out value="${sessionScope.user.info.phoneNumber}"/>
                        </li>

                        <%-- Address --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.address"/></strong>
                                    </span>
                            <c:out value="${sessionScope.user.info.address}"/>
                        </li>

                        <%-- Post code --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.post_code"/></strong>
                                    </span>
                            <c:out value="${sessionScope.user.info.postCode}"/>
                        </li>

                    </ul>

                    <%-- Blocked indicator --%>
                    <c:choose>
                        <c:when test="${sessionScope.user.blocked == true}">
                            <div class="panel panel-default">
                                <div class="panel-heading" style="background-color: #c9302c; color: #f8f8f8; font-weight: bold;"><fmt:message key="page.profile.blocked"/> </div>
                                <div class="panel-body">
                                    <a href="${pageContext.request.contextPath}/main?command=goto_send_email"><fmt:message key="page.profile.contact_admin"/> </a>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>


                    <%-- User activity and statistics --%>
                    <ul class="list-group">
                        <li class="list-group-item text-muted">
                            <fmt:message key="page.profile.activity"/>
                        </li>
                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.profile.ready"/></strong>
                            </span>
                            ${requestScope.readySize}
                        </li>
                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.profile.purchased"/></strong>
                            </span>
                            ${requestScope.purchasedSize}
                        </li>
                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.profile.selling_out"/></strong>
                            </span>
                            ${requestScope.sellingOutSize}
                        </li>
                        <li class="list-group-item text-right">
                            <span class="pull-left">
                                <strong class=""><fmt:message key="page.profile.finished"/> </strong>
                            </span>
                            ${requestScope.finishedSize}
                        </li>
                    </ul>

                </div>

                <%-- Right side --%>
                <div class="col-md-9" style="" contenteditable="false">

                    <%-- Ready lots --%>
                    <c:choose>
                        <c:when test="${not empty requestScope.ready}">
                            <div class="panel panel-default target">
                                <div class="panel-heading" contenteditable="false">
                                    <fmt:message key="page.profile.ready"/>
                                    <a href="${pageContext.request.contextPath}/main?command=goto_lots&type=ready" class="btn btn-default btn-md pull-right" style="margin: -5px;">
                                        <fmt:message key="page.profile.view_all"/>
                                    </a>
                                </div>
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <c:forEach items="${requestScope.ready}" var="lot">
                                            <c:import url="${pageContext.request.contextPath}/jsp/partials/lot-item.jsp">
                                                <c:param name="name" value="${lot.name}"/>
                                                <c:param name="description" value="${lot.description}"/>
                                                <c:param name="id" value="${lot.id}"/>
                                            </c:import>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>

                    <%-- Austions, which user took part in --%>
                    <c:choose>
                        <c:when test="${not empty requestScope.purchased}">
                            <div class="panel panel-default target">
                                <div class="panel-heading" contenteditable="false">
                                    <fmt:message key="page.profile.purchased"/>
                                    <a href="${pageContext.request.contextPath}/main?command=goto_lots&type=purchased" class="btn btn-default btn-md pull-right" style="margin: -5px;">
                                        <fmt:message key="page.profile.view_all"/>
                                    </a>
                                </div>
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <c:forEach items="${requestScope.purchased}" var="lot">
                                            <c:import url="${pageContext.request.contextPath}/jsp/partials/lot-item.jsp">
                                                <c:param name="name" value="${lot.name}"/>
                                                <c:param name="description" value="${lot.description}"/>
                                                <c:param name="id" value="${lot.id}"/>
                                            </c:import>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>

                    <%-- Users active lots --%>
                    <c:choose>
                        <c:when test="${not empty requestScope.sellingOut}">
                            <div class="panel panel-default target">
                                <div class="panel-heading" contenteditable="false">
                                    <fmt:message key="page.profile.selling_out"/>
                                    <a href="${pageContext.request.contextPath}/main?command=goto_lots&type=selling_out" class="btn btn-default btn-md pull-right" style="margin: -5px;">
                                        <fmt:message key="page.profile.view_all"/>
                                    </a>
                                </div>
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <c:forEach items="${requestScope.sellingOut}" var="lot">
                                            <c:import url="${pageContext.request.contextPath}/jsp/partials/lot-item.jsp">
                                                <c:param name="name" value="${lot.name}"/>
                                                <c:param name="description" value="${lot.description}"/>
                                                <c:param name="id" value="${lot.id}"/>
                                            </c:import>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>

                    <%-- Finished and haven't been bought --%>
                    <c:choose>
                        <c:when test="${not empty requestScope.finished}">
                            <div class="panel panel-default target">
                                <div class="panel-heading" contenteditable="false">
                                    <fmt:message key="page.profile.finished"/>
                                    <a href="${pageContext.request.contextPath}/main?command=goto_lots&type=finished" class="btn btn-default btn-md pull-right" style="margin: -5px;">
                                        <fmt:message key="page.profile.view_all"/>
                                    </a>
                                </div>
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <c:forEach items="${requestScope.finished}" var="lot">
                                            <c:import url="${pageContext.request.contextPath}/jsp/partials/lot-item.jsp">
                                                <c:param name="name" value="${lot.name}"/>
                                                <c:param name="description" value="${lot.description}"/>
                                                <c:param name="id" value="${lot.id}"/>
                                            </c:import>
                                        </c:forEach>
                                        <c:import url="${pageContext.request.contextPath}/jsp/partials/pagination.jsp">
                                            <c:param name="lots" value="${requestScope.finished}"/>
                                        </c:import>
                                    </div>
                                </div>
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