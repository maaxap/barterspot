<head>

    <%-- Page title --%>
    <title><fmt:message key="page.edit_lot.title"/></title>

    <%-- Header part in common usage --%>
    <%@include file="../partials/common-assets.jsp"%>

    <%-- Edit lot page styles --%>
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
        <h1><fmt:message key="page.edit_lot.title"/></h1>
        <hr>
        <div class="row">

            <%-- Left side --%>
            <div class="col-md-12 personal-info">

                <%-- Lot registration form --%>
                <form action="${pageContext.request.contextPath}/main" id="edit-lot-form"
                      class="form-horizontal" role="form" method="post" enctype="multipart/form-data">

                    <%-- Command type --%>
                    <input type="hidden" name="command" value="edit-lot"/>

                    <%-- Id --%>
                    <input type="hidden" name="id" value="${requestScope.lot.id}"/>

                    <%-- Image --%>
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <img src="${pageContext.request.contextPath}/image/lots/${requestScope.lot.id}/${requestScope.lot.id}" class="img-responsive img-thumbnail" alt="">
                            <h6 class="text-center"><fmt:message key="page.edit_lot.upload_appeal"/></h6>
                        </div>
                    </div>

                    <%-- Image upload --%>
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <input type="file" class="form-control" name="lot-image" accept="image/*">
                        </div>
                    </div>

                    <%-- Name --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_lot.name"/>:</label>
                        <div class="col-md-6">
                            <input id="edit-name" name="name" class="form-control" type="text" value="<c:out value="${requestScope.lot.name}"/>">
                        </div>
                    </div>

                    <%-- Description --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_lot.description"/>:</label>
                        <div class="col-md-6">
                            <textarea id="edit-description" name="description" class="form-control" rows="7" ><c:out value="${requestScope.lot.description}"/></textarea>
                        </div>
                    </div>

                    <%-- Category --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.edit_lot.category"/>:</label>
                        <div class="col-md-6">
                            <select id="edit-category" name="category" class="form-control">
                                <option disabled selected></option>
                                <c:forEach items="${requestScope.categories}" var="category">
                                    <option ${requestScope.lot.category == category ? 'selected' : ''}>${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-md-3 control-label"><fmt:message key="page.edit_lot.finishing"/>:</label>

                        <%-- Day --%>
                        <div class="col-md-2">
                            <input id="input-day" class="form-control" type="text" name="day" value="${requestScope.lot.finishing.dayOfMonth}">
                        </div>

                        <%-- Month --%>
                        <div class="col-md-2">
                            <select id="input-month" class="form-control" name="month" required>
                                <c:forEach items="${elf:getMonthName(sessionScope.locale)}" var="month">
                                    <option value="${month.key}" ${requestScope.lot.finishing.monthValue == month.key ? 'selected' : ''}>${month.value}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <%-- Year --%>
                        <div class="col-md-2">
                            <input id="input-year" class="form-control" type="text" name="year" value="${requestScope.lot.finishing.year}">
                        </div>

                    </div>

                    <div class="form-group"></div>

                    <%-- Submit button --%>
                    <div class="form-group">
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <a id="edit-lot-submit" class="btn btn-primary btn-lg btn-block" style="background-color: #5fb053; border-color: #5fb053;"><fmt:message key="page.edit_lot.save"/></a>
                        </div>
                    </div>

                </form>
            </div>

        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
