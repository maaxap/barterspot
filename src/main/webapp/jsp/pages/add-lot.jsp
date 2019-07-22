<head>
    <%-- Page title --%>
    <title><fmt:message key="page.add_lot.title"/></title>

    <%-- Common assets --%>
    <%@include file="../partials/common-assets.jsp"%>
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
        <h1><fmt:message key="page.add_lot.title"/></h1>
        <hr>
        <div class="row">
            <div class="col-md-12 personal-info">
                <form action="${pageContext.request.contextPath}/main" id="add-lot-form" role="form"
                      class="form-horizontal" method="post" enctype="multipart/form-data">

                    <%-- Command type --%>
                    <input type="hidden" name="command" value="add-lot"/>

                    <%-- Image uploader     --%>
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <input type="file" class="form-control" name="lot-image" accept="image/*">
                            <h6><fmt:message key="page.add_lot.upload_appeal"/></h6>
                        </div>
                    </div>

                    <%-- Name --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.add_lot.name"/>:</label>
                        <div class="col-md-6">
                            <input id="lot-name" name="name" class="form-control" type="text">
                        </div>
                    </div>

                    <%-- Description --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.add_lot.description"/>:</label>
                        <div class="col-md-6">
                            <textarea id="lot-description" name="description" class="form-control" rows="7"></textarea>
                        </div>
                    </div>

                    <%-- Category --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.add_lot.default_price"/>:</label>
                        <div class="col-md-2">
                            <input id="lot-default-price" name="defaultPrice" class="form-control" type="text" placeholder="USD">
                        </div>
                        <label class="col-md-1 control-label"><fmt:message key="page.add_lot.category"/>:</label>
                        <div class="col-md-3">
                            <select id="lot-category" name="category" class="form-control">
                                <option disabled selected></option>
                                <c:forEach items="${requestScope.categories}" var="category">
                                    <option><c:out value="${category.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <%-- Finishing --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.add_lot.finishing"/>:</label>

                        <%-- Day --%>
                        <div class="col-md-2">
                            <input id="lot-day" class="form-control" type="text" name="day"
                                   placeholder="<fmt:message key="page.add_lot.day"/>">
                        </div>

                        <%-- Month --%>
                        <div class="col-md-2">
                            <select id="lot-month" class="form-control" name="month" required>
                                <option style="color: rgb(157, 157, 157);" selected value="0"><fmt:message key="page.add_lot.month"/></option>
                                <c:forEach items="${elf:getMonthName(sessionScope.locale)}" var="month">
                                    <option value="${month.key}">${month.value}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <%-- Year --%>
                        <div class="col-md-2">
                            <input id="lot-year" class="form-control" type="text" name="year"
                                   placeholder="<fmt:message key="page.add_lot.year"/>">
                        </div>
                    </div>

                    <%-- Delimeter --%>
                    <div class="form-group"></div>

                    <%-- Submit button --%>
                    <div class="form-group">
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <a id="add-lot-submit" class="btn btn-primary btn-lg btn-block" style="background-color: #5fb053; border-color: #5fb053;"><fmt:message key="page.add_lot.add"/></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>
</body>

