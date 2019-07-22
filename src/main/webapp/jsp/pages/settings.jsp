
<head>

    <%-- Page title --%>
    <title><fmt:message key="page.settings.title"/></title>

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

    <%-- Page content --%>
    <div class="container" style="margin-top: 50px">

        <h1 class="text-center"><fmt:message key="page.settings.title"/></h1>
        <div class="row">
            <div class="col-md-offset-4 col-md-6 personal-info">

                <%-- Registration form --%>
                <form action="${pageContext.request.contextPath}/main" id="settings-form" class="form-horizontal" role="form" method="POST">

                    <%-- Command type --%>
                    <input type="hidden" name="command" value="update-settings"/>

                    <%-- Delimeter --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <hr>
                        </div>
                    </div>

                    <%-- Email --%>

                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="settings-email" class="form-control" type="text" name="email" value="<c:out value="${sessionScope.user.email}"/>" placeholder=" <fmt:message key="page.settings.email"/>">
                        </div>
                    </div>

                    <%-- Password --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="settings-old-password" class="form-control" type="password" name="oldPassword" placeholder="<fmt:message key="page.settings.old_password"/>">
                        </div>
                    </div>

                    <%-- Password --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="settings-password" class="form-control" type="password" name="password" placeholder="<fmt:message key="page.settings.new_password"/>">
                        </div>
                    </div>

                    <%-- Password confirmation --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="settings-conf" class="form-control" type="password" name="conf" placeholder="<fmt:message key="page.settings.confirmation"/>">
                        </div>
                    </div>

                    <%-- Delimeter --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <hr>
                        </div>
                    </div>

                    <%-- Birth date --%>
                    <div class="form-group">

                        <%-- Month --%>
                        <div class="col-lg-8">
                            <select id="settings-lang" class="form-control" name="locale" required>
                                <option value="ru_RU" ${sessionScope.locale == "ru_RU" ? 'selected' : ''}><fmt:message key="page.settings.lang_ru" /></option>
                                <option value="en_EN" ${sessionScope.locale == "en_EN" ? 'selected' : ''}><fmt:message key="page.settings.lang_en" /></option>
                            </select>
                        </div>
                    </div>

                    <%-- Delimeter --%>
                    <div class="form-group">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-8">
                        </div>
                    </div>

                    <%-- Registration button --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <a id="settings-submit" class="btn btn-primary btn-lg btn-block" style="background-color: #5fb053; border-color: #5fb053;" href="#"><fmt:message key="page.settings.save"/></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%-- Footer --%>
        <%@include file="../partials/footer.jsp"%>

    </div>
</body>
