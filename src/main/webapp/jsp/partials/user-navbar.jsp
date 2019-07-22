<%-- Navigation --%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <%-- Container --%>
    <div class="container">
        <%-- Brand and toggle get grouped for better mobile display --%>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/main?command=goto_main">Barter<span style="color: #c7254e">spot</span></a>
        </div>
        <%-- Collect the nav links, forms, and other content for toggling --%>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav pull-right">
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_lots"><fmt:message key="partial.navbar.buy"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_add_lot"><fmt:message key="partial.navbar.sell"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_rules"><fmt:message key="partial.navbar.rules"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_about_us"><fmt:message key="partial.navbar.about_us"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_contacts"><fmt:message key="partial.navbar.contacts"/></a>
                </li>

                <%-- Profile dropdown --%>
                <li class="dropdown">
                    <a href="#" style="padding-bottom: 9px;font-weight: bold;padding-top: 12px;" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <span>${sessionScope.user.info.name}</span>
                        <img class="navigation-profile-img" src="${pageContext.request.contextPath}/image/users/${sessionScope.user.id}/${sessionScope.user.id}">
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/main?command=goto_profile"><fmt:message key="partial.navbar.profile"/></a></li>
                        <c:choose>
                            <c:when test="${sessionScope.user.role.getRole() == 'admin'}">
                                <li><a href="${pageContext.request.contextPath}/main?command=goto_admin"><fmt:message key="partial.navbar.management"/></a></li>
                            </c:when>
                        </c:choose>
                        <li role="separator" class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/main?command=goto_edit_profile"><fmt:message key="partial.navbar.edit"/></a></li>
                        <li><a href="${pageContext.request.contextPath}/main?command=goto_settings"><fmt:message key="partial.navbar.settings"/></a></li>

                        <c:choose>
                            <c:when test="${sessionScope.user.role.getRole() != 'admin'}">
                                <li><a href="${pageContext.request.contextPath}/main?command=goto_send_email"><fmt:message key="partial.navbar.help"/></a></li>
                            </c:when>
                        </c:choose>

                        <li role="separator" class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/main?command=logout"><fmt:message key="partial.navbar.logout"/></a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
