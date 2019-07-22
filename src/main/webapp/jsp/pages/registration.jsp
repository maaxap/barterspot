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
    <c:choose>
        <c:when test="${sessionScope.role == 'guest'}">
            <%@include file="../partials/guest-navbar.jsp"%>
        </c:when>
        <c:otherwise>
            <c:redirect url="${pageContext.request.contextPath}/jsp/pages/main.jsp"/>
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
    <div class="container" style="margin-top: 50px">

        <h1 class="text-center"><fmt:message key="page.registration.title"/></h1>
        <div class="row">
            <div class="col-md-offset-4 col-md-6 personal-info">

                <%-- Registration form --%>
                <form action="${pageContext.request.contextPath}/main" id="registration-form" class="form-horizontal" role="form" method="POST" enctype="multipart/form-data">

                    <%-- Command type --%>
                    <input type="hidden" name="command" value="registration"/>

                    <%-- Delimeter --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <hr>
                        </div>
                    </div>

                    <div class="text-center col-lg-8">
                        <%-- Uploaded image or  --%>
                        <div class="form-group">
                            <input type="file" class="form-control" name="avatar">
                        </div>
                        <h6><fmt:message key="page.registration.upload_appeal"/></h6>

                    </div>

                    <%-- Delimeter --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <hr>
                        </div>
                    </div>

                    <%-- Email --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="input-email" class="form-control" type="text" name="email" placeholder="<fmt:message key="page.registration.email" var="email"/>">
                        </div>
                    </div>

                    <%-- Password --%>

                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="input-password" class="form-control" type="password" name="password" placeholder="<fmt:message key="page.registration.password"/>">
                        </div>
                    </div>

                    <%-- Password confirmation --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="input-confirmation" class="form-control" type="password" name="passwordConfirm" placeholder="<fmt:message key="page.registration.confirmation"/>">
                        </div>
                    </div>

                    <%-- Delimeter --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <hr>
                        </div>
                    </div>

                    <%-- Name --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="input-name" class="form-control" type="text" name="name" placeholder="<fmt:message key="page.registration.name"/>">
                        </div>
                    </div>

                    <%-- Surname --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="input-surname" class="form-control" type="text" name="surname" placeholder="<fmt:message key="page.registration.surname"/>">
                        </div>
                    </div>

                    <%-- Phone number --%>
                    <div class="form-group">
                        <div class="col-lg-8">
                            <input id="input-phone" class="form-control" type="text" name="phoneNumber" placeholder="<fmt:message key="page.registration.phone_number"/>">
                        </div>
                    </div>

                    <%-- Birth date --%>
                    <div class="form-group">

                        <%-- Day --%>
                        <div class="col-lg-2">
                            <input id="birth-day" class="form-control" type="text" name="day" placeholder="<fmt:message key="page.registration.day"/>">
                        </div>

                        <%-- Month --%>
                        <div class="col-lg-3">
                            <select id="birth-month" class="form-control" name="month" required>
                                <option style="color: rgb(157, 157, 157);" selected value="0"><fmt:message key="page.registration.month"/></option>
                                <c:forEach items="${elf:getMonthName(sessionScope.locale)}" var="month">
                                    <option value="${month.key}">${month.value}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <%-- Year --%>
                        <div class="col-lg-3">
                            <input id="birth-year" class="form-control" type="text" name="year" placeholder="<fmt:message key="page.registration.year"/>">
                        </div>
                    </div>

                    <%-- Full ddress --%>
                    <div class="form-group">

                        <%-- Address --%>
                        <div class="col-lg-5">
                            <input id="input-address" class="form-control" type="text" name="address" placeholder="<fmt:message key="page.registration.address"/>">
                        </div>

                        <%-- Post code --%>
                        <div class="col-lg-3">
                            <input id="input-post-index" class="form-control" type="text" name="postCode" placeholder="<fmt:message key="page.registration.post_code"/>">
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
                            <a id="registration-submit" class="btn btn-primary btn-lg btn-block" style="background-color: #5fb053; border-color: #5fb053;" href="#"><fmt:message key="page.registration.signup"/></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%-- Footer --%>
        <%@include file="../partials/footer.jsp"%>

    </div>
</body>
