
<head>

    <%-- Header part in common usage --%>
    <%@include file="../../partials/common-assets.jsp"%>

    <%-- Page title --%>
    <title><fmt:message key="page.rules.title"/></title>

    <%-- Lots page styles --%>
    <link href="../../../css/login-modal.css" rel="stylesheet">

</head>
<body>

    <%-- Navbar --%>
    <c:choose>
        <c:when test="${sessionScope.role != 'client' && sessionScope.role != 'admin'}">
            <%@include file="../../partials/guest-navbar.jsp"%>
        </c:when>
        <c:otherwise>
            <%@include file="../../partials/user-navbar.jsp"%>
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

    <%-- Page content --%>
    <div class="container" style="margin-top: 50px">

        <h1 class="text-center"><fmt:message key="page.rules.title"/></h1>
        <div class="row">
            <div class="col-md-offset-3 col-md-6 personal-info">
                <div class="form-group">
                    <div class="col-lg-16">
                        <hr>
                    </div>
                </div>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Fusce pulvinar vitae est sit amet eleifend. Donec libero massa,
                    feugiat sit amet ante vitae, vulputate sollicitudin elit. Aenean
                    id libero cursus, porttitor nisi sed, imperdiet ex. Integer odio
                    lacus, laoreet et sagittis eu, tristique aliquet massa. Phasellus
                    in ipsum vestibulum, ullamcorper risus eget, consequat tellus. Maecenas
                    molestie tempus massa, sit amet molestie leo iaculis quis. Nulla facilisi.

                    Nunc nec lectus nec orci condimentum vehicula. Pellentesque nisi mi,
                    mattis vel rhoncus sit amet, molestie id velit. Integer risus tellus,
                    posuere at felis id, luctus auctor turpis. Donec posuere nisl sit amet
                    sodales egestas. Sed quam nibh, eleifend in elementum sed, vulputate ac
                    augue. Proin quam metus, gravida accumsan tristique id, volutpat at tellus.
                    Vestibulum non mattis orci, vel efficitur massa. Donec faucibus tristique
                    magna eu maximus. Donec efficitur ultricies nisl, sed euismod felis iaculis
                    sit amet. Fusce consectetur velit vitae felis aliquam, non convallis diam
                    ornare. Ut eget malesuada justo, ut suscipit felis.

                    Proin nec lorem efficitur, tincidunt quam a, facilisis elit. Nunc sed
                    ultricies metus. Cras elit purus, porttitor mollis nisl non, mollis mollis
                    neque. Nullam suscipit diam a enim bibendum, sed scelerisque leo maximus.
                    Duis aliquet at felis non pharetra. In faucibus augue massa, vitae iaculis
                    ex eleifend at. Duis quis malesuada lorem. Suspendisse ultricies tortor
                    leo, ac blandit mauris posuere eget. Donec ac posuere quam. Nam iaculis
                    neque eget lacus condimentum, in molestie ante cursus. Morbi eu ex ornare,
                    molestie urna eu, vulputate ex. Nullam egestas purus et ligula sagittis,
                    condimentum mollis erat aliquet. Mauris fermentum bibendum justo, posuere
                    sodales turpis varius nec. Nulla facilisi.

                    Sed vel interdum tortor, id tincidunt lorem. Nulla quis purus dolor. Cras
                    pharetra leo ut est vestibulum semper. Mauris arcu est, semper vitae sapien
                    eu, faucibus egestas turpis. Aenean non congue nisi. Integer euismod velit
                    metus, et consequat tortor imperdiet ac. Nullam aliquam lobortis urna ut
                    tristique. Curabitur auctor augue in tincidunt accumsan. Curabitur leo arcu,
                    viverra id ligula at, malesuada porta ipsum.

                    Phasellus vehicula eu dui nec pharetra. Fusce in est felis. Nam sed ultrices
                    est. Maecenas sit amet nunc arcu. Praesent id augue massa. Mauris vel vehicula
                    ex. Morbi a enim sed ipsum mollis rutrum. Etiam venenatis lectus et ligula
                    congue vestibulum.
                </p>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@include file="../../partials/footer.jsp"%>

</body>
