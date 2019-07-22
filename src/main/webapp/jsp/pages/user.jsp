<head>

    <%-- Page title --%>
    <title>${requestScope.user.info.name} ${requestScope.user.info.surname}</title>

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
                    <h1 class=""><c:out value="${requestScope.user.info.name}"/> <c:out value="${requestScope.user.info.surname}"/></h1>
                    <br/>

                    <a href="${pageContext.request.contextPath}/main?command=goto_send_email&receiverId=${requestScope.user.id}" class="btn btn-primary " style="margin-bottom: 10px;"><fmt:message key="page.profile.message"/></a>

                    <br/>

                    <c:choose>
                        <c:when test="${sessionScope.user.role.toString().toLowerCase() == 'admin'}">
                            <c:choose>
                                <c:when test="${requestScope.user.blocked == false}">
                                    <form action="${pageContext.request.contextPath}/main" method="POST" id="block-user-form">
                                        <input type="hidden" name="command" value="block-user"/>
                                        <input type="hidden" name="id" value="${requestScope.user.id}"/>
                                        <button type="submit" class="btn btn-danger"><fmt:message key="partial.users.block"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/main" method="POST" id="unblock-user-form">
                                        <input type="hidden" name="command" value="unblock-user"/>
                                        <input type="hidden" name="id" value="${requestScope.user.id}"/>
                                        <button type="submit" class="btn btn-danger"><fmt:message key="partial.users.unblock"/></button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                    </c:choose>

                    <br/>
                </div>

                <%-- Profile image --%>
                <div class="col-md-6">
                    <a href="#" class="pull-right">
                        <img title="profile image" class="img-responsive img-thumbnail" src="${pageContext.request.contextPath}/image/users/${requestScope.user.id}" style="max-height: 400px;">
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
                            <c:out value="${requestScope.user.email}"/>
                        </li>

                        <%-- Created at --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.joined"/></strong>
                                    </span>
                            <jct:date-time-formatter format="d LLL yyyy" locale="${sessionScope.locale}" >${requestScope.user.createdAt}</jct:date-time-formatter>
                        </li>

                        <%-- Birth date --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.birth_date"/></strong>
                                    </span>
                            <c:out value="${requestScope.user.info.birthDate}"/>
                        </li>

                        <%-- Phone number --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.phone_number"/></strong>
                                    </span>
                            <c:out value="${requestScope.user.info.phoneNumber}"/>
                        </li>

                        <%-- Address --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.address"/></strong>
                                    </span>
                            <c:out value="${requestScope.user.info.address}"/>
                        </li>

                        <%-- Post code --%>
                        <li class="list-group-item text-right">
                                    <span class="pull-left">
                                        <strong class=""><fmt:message key="page.profile.post_code"/></strong>
                                    </span>
                            <c:out value="${requestScope.user.info.postCode}"/>
                        </li>

                    </ul>

                    <%-- Blocked indicator --%>
                    <c:choose>
                        <c:when test="${requestScope.user.blocked == true}">
                            <div class="panel panel-default">
                                <div class="panel-heading" style="background-color: #c9302c; color: #f8f8f8; font-weight: bold;"><fmt:message key="page.profile.blocked"/> </div>
                            </div>
                        </c:when>
                    </c:choose>


                    <%-- User activity and statistics --%>
                    <ul class="list-group">
                        <li class="list-group-item text-muted">
                            <fmt:message key="page.profile.activity"/>
                            <i class="fa fa-dashboard fa-1x"></i>
                        </li>
                        <li class="list-group-item text-right">
                            <span class="pull-left"><strong class=""><fmt:message key="page.profile.selling_out"/></strong></span>
                            ${requestScope.sellingOutSize}
                        </li>
                    </ul>
                </div>

                <%-- Right side --%>
                <div class="col-md-9" style="" contenteditable="false">

                    <%-- Users active lots --%>
                    <c:choose>
                        <c:when test="${not empty requestScope.sellingOut}">
                            <div class="panel panel-default target">
                                <div class="panel-heading" contenteditable="false">
                                    <fmt:message key="page.profile.selling_out"/>
                                </div>
                                <div class="panel-body">
                                    <div class="row text-center ">
                                        <c:forEach items="${requestScope.sellingOut}" var="lot">
                                            <c:import url="/jsp/partials/lot-item.jsp">
                                                <c:param name="name" value="${lot.name}"/>
                                                <c:param name="description" value="${lot.description}"/>
                                                <c:param name="id" value="${lot.id}"/>
                                            </c:import>
                                        </c:forEach>
                                        <c:import url="/jsp/partials/pagination.jsp">
                                            <c:param name="lots" value="${requestScope.sellingOut}"/>
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