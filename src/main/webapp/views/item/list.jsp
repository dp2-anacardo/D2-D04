<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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


    <spring:message code="item.name" var="nameHeader"/>
    <display:column property="name" title="${nameHeader}"/>

    <spring:message code="item.description" var="descriptionHeader"/>
    <display:column property="description" title="${descriptionHeader}"/>

    <spring:message code="item.update" var="updateHeader"/>
    <display:column title="${updateHeader}">
        <a
                href="item/provider/update.do?itemID=${row.id}">
            <spring:message code="item.update"/>
        </a>
    </display:column>

    <spring:message code="item.show" var="showHeader"/>
    <display:column title="${showHeader}">
        <a
                href="item/show.do?itemID=${row.id}">
            <spring:message code="item.show"/>
        </a>
    </display:column>

</display:table>

<security:authorize
        access="hasAnyRole('PROVIDER')">
    <acme:cancel url="item/provider/create.do" code="item.create"/>
</security:authorize>

<acme:cancel url="/" code="item.goBack"/>
