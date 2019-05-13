<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:set var="count" value="0" scope="page" />

<display:table name="positions" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <spring:message code="position.ticker" var="ticker"/>
    <display:column property="ticker" title="${ticker}"/>

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
        <display:column title="${auditCreate}">
            <jstl:if test="${created[count] eq false}">
                <a href="audit/auditor/create.do?positionId=${row.id}">
                    <spring:message code="audit.create"/></a>
            </jstl:if>
        </display:column>
        <jstl:set var="count" value="${count + 1}" scope="page"/>
    </security:authorize>

</display:table>

<acme:cancel code="position.goBack" url="/"/>