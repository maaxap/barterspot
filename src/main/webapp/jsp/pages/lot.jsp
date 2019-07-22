<head>
    <%-- Page title --%>
    <title>${requestScope.lot.name}</title>

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
    <div class="container">
        <div class="row">

            <div class="col-md-3">

                <p class="lead">${requestScope.lot.name}</p>
                <div class="list-group">
                    <a href="${pageContext.request.contextPath}/main?command=goto_lots" class="list-group-item"><fmt:message key="page.lot.to_lots"/></a>
                    <a href="${pageContext.request.contextPath}/main?command=goto_profile" class="list-group-item"><fmt:message key="page.lot.to_profile"/></a>
                </div>
                <div class="list-group">
                    <c:choose>
                        <c:when test="${sessionScope.role == 'admin'}">
                            <a href="${pageContext.request.contextPath}/main?command=goto_edit_lot&id=${requestScope.lot.id}" class="list-group-item"><fmt:message key="page.lot.edit_lot"/></a>
                            <form action="${pageContext.request.contextPath}/main" id="delete-lot-form" role="form">
                                <input type="hidden" name="command" value="delete-lot"/>
                                <input type="hidden" name="id" value="${requestScope.lot.id}"/>
                                <a id="delete-lot-submit" type="submit" class="list-group-item" onclick="$('#delete-lot-form').submit();" style="cursor: pointer;"><fmt:message key="page.lot.delete_lot"/></a>
                            </form>
                        </c:when>
                        <c:when test="${sessionScope.user.id == requestScope.lot.user.id}">
                            <a href="${pageContext.request.contextPath}/main?command=goto_edit_lot&id=${requestScope.lot.id}" class="list-group-item"><fmt:message key="page.lot.edit_lot"/></a>
                        </c:when>
                    </c:choose>
                </div>

            </div>

            <div class="col-md-9">

                <div class="thumbnail">
                    <img class="img-responsive" src="${pageContext.request.contextPath}/image/lots/${requestScope.lot.id}/${requestScope.lot.id}" alt="">
                    <div class="caption-full">
                        <h4 class="pull-right">${elf:formatBid(requestScope.lastBid)}$</h4>
                        <h4>
                            <a href="#"><c:out value="${requestScope.lot.name}"/></a>
                        </h4>
                        <p><c:out value="${requestScope.lot.description}"/></p>
                    </div>
                    <div class="ratings">
                        <c:choose>
                            <c:when test="${not empty requestScope.bidsAmount}">
                                <p class="pull-right"><fmt:message key="page.lot.bids"/>:&nbsp;${requestScope.bidsAmount}</p>
                            </c:when>
                            <c:otherwise>
                                <p class="pull-right"><fmt:message key="page.lot.no_bids_message"/></p>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.daysLeft > 0}">
                                <p><fmt:message key="page.lot.days_to_finish"/>:&nbsp;${requestScope.daysLeft}</p>
                            </c:when>
                            <c:otherwise>
                                <p><fmt:message key="page.lot.days_after_finish"/>:&nbsp;${-requestScope.daysLeft}</p>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>

                <div class="well">
                    <div class="row">
                        <c:choose>
                            <c:when test="${sessionScope.user.id == requestScope.lot.user.id}">
                                <div class="col-md-6">
                                    <span class="h4 pull-left">
                                        <fmt:message key="page.lot.owner"/>:&nbsp;
                                        <a href="${pageContext.request.contextPath}/main?command=goto_profile">
                                            <c:out value="${requestScope.lot.user.info.name}"/> <c:out value="${requestScope.lot.user.info.surname}"/>
                                        </a>
                                    </span>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.isWinner == true}">
                                <div class="col-md-6">
                                    <span class="h4 pull-left">
                                        <fmt:message key="page.lot.owner"/>:&nbsp;
                                        <a href="${pageContext.request.contextPath}/main?command=goto_user&id=${requestScope.lot.user.id}"><c:out value="${requestScope.lot.user.info.name}"/> <c:out value="${requestScope.lot.user.info.surname}"/></a>
                                    </span>
                                </div>
                                <div class="col-md-3">
                                    <form action="${pageContext.request.contextPath}/main" id="confirm-lot-purchase-form" role="form" class="form-inline">
                                        <input type="hidden" name="command" value="confirm-lot-purchase"/>
                                        <input type="hidden" name="id" value="${requestScope.lot.id}"/>
                                        <button id="confirm-lot-purchase-submit" class="btn btn-success" type="submit"><fmt:message key="page.lot.confirm_lot_purchase"/></button>
                                    </form>
                                </div>
                                <div class="col-md-3">
                                    <form action="${pageContext.request.contextPath}/main" id="deny-lot-purchase-form" role="form" class="form-inline">
                                        <input type="hidden" name="command" value="deny-lot-purchase"/>
                                        <input type="hidden" name="id" value="${requestScope.lot.id}"/>
                                        <button id="deny-lot-purchase-submit" class="btn btn-default" type="submit"><fmt:message key="page.lot.deny_lot_purchase"/></button>
                                    </form>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.user.blocked == true}">
                                <div class="col-md-6">
                                    <span class="h4 pull-left">
                                        <fmt:message key="page.lot.owner"/>:&nbsp;
                                        <a href="${pageContext.request.contextPath}/main?command=goto_user&id=${requestScope.lot.user.id}"><c:out value="${requestScope.lot.user.info.name}"/> <c:out value="${requestScope.lot.user.info.surname}"/></a>
                                    </span>
                                </div>
                                <div class="col-md-5 text-right">
                                    <fmt:message key="page.lot.blocked"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-md-6">
                                    <span class="h4 pull-left">
                                        <fmt:message key="page.lot.owner"/>:&nbsp;
                                        <a href="${pageContext.request.contextPath}/main?command=goto_user&id=${requestScope.lot.user.id}"><c:out value="${requestScope.lot.user.info.name}"/> <c:out value="${requestScope.lot.user.info.surname}"/></a>
                                    </span>
                                </div>
                                <div class="col-md-3">
                                    <form action="${pageContext.request.contextPath}/main" id="add-bid-form" role="form" class="form-inline pull-left" method="post">
                                        <input type="hidden" name="command" value="add-bid"/>
                                        <input type="hidden" name="id" value="${requestScope.lot.id}"/>
                                        <input type="hidden" name="last_bid" value="${requestScope.lastBid}"/>
                                        <input class="form-control" type="text" name="bid" placeholder="USD"/>
                                    </form>
                                </div>
                                <div class="col-md-3">
                                    <a id="add-bid-submit" class="btn btn-success"><fmt:message key="page.lot.make_bid"/></a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../partials/footer.jsp"%>

</body>
