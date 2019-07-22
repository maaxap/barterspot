<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elf" uri="ELFunctions"%>
<%@ taglib prefix="jct" uri="JspCustomTags" %>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" />

<!DOCTYPE html>
<html lang="${sessionScope.locale}">