
<head>

    <%-- Page title --%>
    <title><fmt:message key="page.edit_profile.title"/></title>

    <%-- Header part in common usage --%>
    <%@include file="../partials/common-assets.jsp"%>

    <%-- Add lot page styles --%>
    <link href="../../css/add-lot.css" rel="stylesheet">

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
        <h1><fmt:message key="page.edit_profile.title"/></h1>
        <hr>
        <div class="row">

            <%-- Left side --%>
            <div class="col-md-12 personal-info">

                <%-- Lot registration form --%>
                <form action="${pageContext.request.contextPath}/main" id="edit-profile-form" class="form-horizontal" role="form" method="POST" enctype="multipart/form-data">

                    <%-- Command type --%>
                    <input type="hidden" name="command" value="edit-profile"/>

                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <img src="${pageContext.request.contextPath}/image/users/${sessionScope.user.id}/${sessionScope.user.id}" class="img-responsive img-thumbnail">
                            <h6><fmt:message key="page.edit_profile.upload_appeal"/></h6>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <input type="file" class="form-control" name="avatar">
                        </div>
                    </div>

                    <%-- Name --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_profile.name"/>:</label>
                        <div class="col-md-6">
                            <input id="edit-name" name="name" class="form-control" type="text" value="<c:out value="${sessionScope.user.info.name}"/>">
                        </div>
                    </div>

                    <%-- Surname --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_profile.surname"/>:</label>
                        <div class="col-md-6">
                            <input id="edit-surname" name="surname" class="form-control" type="text" value="<c:out value="${sessionScope.user.info.surname}"/>">
                        </div>
                    </div>

                    <%-- Birth date --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_profile.birth_date"/>:</label>

                        <%-- Day --%>
                        <div class="col-md-2">
                            <input id="edit-birth-day" class="form-control" type="text" name="day"
                                   value="${sessionScope.user.info.birthDate.dayOfMonth}" placeholder="<fmt:message key="page.edit_profile.birth_day"/>">
                        </div>

                        <%-- Month --%>
                        <div class="col-md-2">
                            <select id="edit-birth-month" class="form-control" name="month" required>
                                <c:forEach items="${elf:getMonthName(sessionScope.locale)}" var="month">
                                    <option value="${month.key}" ${sessionScope.user.info.birthDate.monthValue == month.key ? 'selected' : ''}>${month.value}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <%-- Year --%>
                        <div class="col-md-2">
                            <input id="edit-birth-year" class="form-control" type="text" name="year"
                                   value="${sessionScope.user.info.birthDate.year}" placeholder="<fmt:message key="page.edit_profile.birth_year"/>">
                        </div>
                    </div>

                    <%-- Phone number --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_profile.phone_number"/>:</label>
                        <div class="col-md-6">
                            <input id="edit-phone-number" name="phoneNumber" class="form-control" type="text" value="<c:out value="${sessionScope.user.info.phoneNumber}"/>">
                        </div>
                    </div>

                    <%-- Address --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_profile.address"/>:</label>
                        <div class="col-md-3">
                            <input id="edit-address" name="address" class="form-control" type="text" value="<c:out value="${sessionScope.user.info.address}"/>">
                        </div>
                        <label class="col-md-1 control-label"><fmt:message key="page.edit_profile.post_code"/>:</label>
                        <div class="col-md-2">
                            <input id="edit-post-code" name="postCode" class="form-control" type="text" value="<c:out value="${sessionScope.user.info.postCode}"/>">
                        </div>
                    </div>
                    <div class="form-group"></div>

                    <%-- Save button --%>
                    <div class="form-group">
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <a id="edit-profile-submit" class="btn btn-primary btn-lg btn-block" style="background-color: #5fb053; border-color: #5fb053;"><fmt:message key="page.edit_profile.save"/></a>
                        </div>
                    </div>

                </form>
            </div>

        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
