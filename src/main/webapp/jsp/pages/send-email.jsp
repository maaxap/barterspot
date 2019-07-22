<head>

    <%-- Page title --%>
    <title><fmt:message key="page.support.title"/></title>

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
        <h1><fmt:message key="page.support.title"/></h1>
        <hr>
        <div class="row">

            <%-- Left side --%>
            <div class="col-md-12 personal-info">

                <%-- Lot registration form --%>
                <form action="${pageContext.request.contextPath}/main"
                      id="send-email-form"
                      class="form-horizontal"
                      role="form"
                      method="POST">

                    <%-- Command type --%>
                    <input type="hidden" name="command" value="send-email"/>

                    <%-- Sender email --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.send_email.sender_email"/>:</label>
                        <div class="col-md-6">
                            <input id="input-sender-email" name="senderEmail" class="form-control" type="text" value="<c:out value="${requestScope.senderEmail}"/>">
                        </div>
                    </div>

                    <%-- Sender password --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.send_email.sender_password"/>:</label>
                        <div class="col-md-6">
                            <input id="input-sender-password" name="password" class="form-control" type="password">
                        </div>
                    </div>

                    <%-- Default price and category --%>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.send_email.receiver_email"/>:</label>
                        <div class="col-md-6">
                            <input id="input-receiver-email" name="receiverEmail" class="form-control" type="text" value="<c:out value="${requestScope.receiverEmail}"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.send_email.subject"/>:</label>
                        <div class="col-md-6">
                            <input id="input-subject" name="subject" class="form-control" type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label"><fmt:message key="page.send_email.text"/>:</label>
                        <div class="col-md-6">
                            <textarea id="input-text" name="text" class="form-control" rows="7"></textarea>
                        </div>
                    </div>



                    <div class="form-group"></div>

                    <%-- Add button --%>
                    <div class="form-group">
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <a id="send-email-submit" class="btn btn-primary btn-lg btn-block" style="background-color: #5fb053; border-color: #5fb053;"><fmt:message key="page.send_email.send"/></a>
                        </div>
                    </div>

                </form>
            </div>

        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
