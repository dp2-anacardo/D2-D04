<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="items" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <spring:message code="item.name" var="title"/>
    <display:column property="name" title="${title}"/>

    <spring:message code="item.description" var="title"/>
    <display:column property="description" title="${title}"/>

    <spring:message code="item.show" var="showHeader"/>
    <display:column title="${showHeader}"> <a href="item/show.do?itemID=${row.id}">
        <spring:message code="item.show"/></a> </display:column>

    <spring:message code="item.provider" var="providerHeader"/>
    <display:column title="${providerHeader}"> <a href="provider/show.do?providerID=${row.provider.id}">
        <spring:message code="item.provider"/></a> </display:column>


</display:table>


<acme:cancel url="/provider/listNotLogged.do" code="item.goBack"/>