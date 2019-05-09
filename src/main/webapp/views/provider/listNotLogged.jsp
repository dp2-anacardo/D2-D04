<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="providers" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <spring:message code="provider.make" var="title"/>
    <display:column property="make" title="${title}"/>

    <spring:message code="provider.items" var="itemsHeader"/>
    <display:column title="${itemsHeader}"> <a href="item/listNotLogged.do?providerId=${row.id}">
        <spring:message code="provider.items" /></a> </display:column>
</display:table>

<acme:cancel url="/" code="item.goBack"/>