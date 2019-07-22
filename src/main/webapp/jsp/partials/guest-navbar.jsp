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
                    <a href="${pageContext.request.contextPath}/main?command=goto_rules"><fmt:message key="partial.navbar.rules"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_about_us"><fmt:message key="partial.navbar.about_us"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main?command=goto_contacts"><fmt:message key="partial.navbar.contacts"/></a>
                </li>
                <li>
                    <a href="#" data-toggle="modal" data-target="#login-modal"><fmt:message key="partial.navbar.login"/></a>
                </li>

                <%-- Locale dropdown --%>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <c:choose>
                            <c:when test="${sessionScope.locale == 'ru_RU'}">
                                <span><fmt:message key="partial.navbar.lang_ru"/></span>
                            </c:when>
                            <c:otherwise>
                                <span><fmt:message key="partial.navbar.lang_en"/></span>
                            </c:otherwise>
                        </c:choose>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/main?command=localize&locale=ru_RU"><fmt:message key="partial.navbar.lang_ru"/></a></li>
                        <li><a href="${pageContext.request.contextPath}/main?command=localize&locale=en_EN"><fmt:message key="partial.navbar.lang_en"/></a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<%-- Login modal --%>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">

            <h1><fmt:message key="partial.navbar.title"/></h1>
            <br>

            <form action="${pageContext.request.contextPath}/main" method="POST">
                <input type="hidden" name="command" value="login"/>
                <input type="text" name="email" placeholder="<fmt:message key="partial.navbar.email"/>">
                <input type="password" name="password" placeholder="<fmt:message key="partial.navbar.password"/>">
                <input type="submit" name="login" class="login loginmodal-submit" value="<fmt:message key="partial.navbar.login"/>">
            </form>

            <div class="login-help">
                <a href="${pageContext.request.contextPath}/main?command=goto_registration"><fmt:message key="partial.navbar.signup"/></a>
            </div>
        </div>
    </div>
</div>