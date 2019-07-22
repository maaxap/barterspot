
<head>
    <%-- Page title --%>
    <title><fmt:message key="page.payment.title"/></title>

    <%-- Header part in common usage --%>
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
        <div class="row">
            <div class="col-lg-6 col-lg-offset-4">
                <iframe
                        frameborder="0"
                        allowtransparency="true"
                        scrolling="no"
                        src="https://money.yandex.ru/quickpay/shop-widget?account=410012988421425&quickpay=shop&payment-type-choice=off&writer=buyer&targets-hint=&default-sum=&button-text=01&successURL=localhost%3A8080%2Fmain%3Fcommand%3Dgoto_lots"
                        width="450"
                        height="198">
                </iframe>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
