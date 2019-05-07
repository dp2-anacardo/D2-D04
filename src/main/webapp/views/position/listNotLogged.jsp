<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="positions" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <spring:message code="position.title" var="title"/>
    <display:column property="title" title="${title}"/>

    <spring:message code="position.description" var="description"/>
    <display:column property="description" title="${description}"/>

    <spring:message code="position.show" var="positionShow"/>
    <display:column title="${positionShow}"> <a href="position/show.do?positionId=${row.id}">
        <spring:message code="position.show"/></a> </display:column>

    <spring:message code="position.company" var="positionCompany"/>
    <display:column title="${positionCompany}"> <a href="company/show.do?companyId=${row.company.id}">
        <spring:message code="position.company"/></a> </display:column>

    <spring:message code="position.audits" var="audits"/>
    <display:column title="${audits}">
        <a href="audit/list.do?positionId=${row.id}">
            <spring:message code="position.audits"/></a>
    </display:column>

    <security:authorize access="hasRole('ROOKIE')">
        <spring:message code="application.create" var="appCreate"/>
        <display:column title="${appCreate}"> <a href="application/rookie/create.do?positionId=${row.id}">
            <spring:message code="application.create"/></a> </display:column>
    </security:authorize>

    <security:authorize access="hasRole('AUDITOR')">
        <spring:message code="audit.create" var="auditCreate"/>
        <display:column title="${auditCreate}"> <a href="audit/auditor/create.do?positionId=${row.id}">
            <spring:message code="audit.create"/></a> </display:column>
    </security:authorize>

</display:table>

<input type="button" name="cancel"
       value="<spring:message code="position.goBack" />"
       onclick="javascript: window.history.back();"/>