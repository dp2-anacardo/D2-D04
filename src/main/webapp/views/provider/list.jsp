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

<display:table name="problems" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">


    <spring:message code="problem.title" var="titleHeader"/>
    <display:column property="title" title="${titleHeader}"/>

    <spring:message code="problem.isFinal" var="isFinalHeader"/>
    <display:column title="${isFinalHeader}">
        <jstl:if test="${row.isFinal eq true}">
            <spring:message code="problem.isFinal.final"/>
        </jstl:if>
        <jstl:if test="${row.isFinal eq false}">
            <spring:message code="problem.isFinal.draft"/>
        </jstl:if>
    </display:column>

    <spring:message code="problem.update" var="updateHeader"/>
    <display:column title="${updateHeader}">
        <jstl:if test="${row.isFinal eq false}">
        <a
                href="problem/company/update.do?problemID=${row.id}">
            <spring:message code="problem.update"/>
        </a>
        </jstl:if>
    </display:column>

    <spring:message code="problem.show" var="showHeader"/>
    <display:column title="${showHeader}">
        <a
                href="problem/show.do?problemID=${row.id}">
            <spring:message code="problem.show"/>
        </a>
    </display:column>

</display:table>

<security:authorize
        access="hasAnyRole('COMPANY')">
    <acme:cancel url="problem/company/create.do" code="problem.create"/>
</security:authorize>

<acme:cancel url="/" code="problem.goBack"/>
