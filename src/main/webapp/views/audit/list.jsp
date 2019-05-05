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

<display:table name="audits" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">


    <spring:message code="audit.moment" var="moment"/>
    <display:column property="moment" title="${moment}"/>

    <spring:message code="audit.text" var="text"/>
    <display:column property="text" title="${text}"/>

    <spring:message code="audit.score" var="score"/>
    <display:column property="score" title="${score}"/>

    <spring:message code="audit.isFinal" var="isFinal"/>
    <display:column title="${isFinal}">
        <jstl:if test="${row.isFinal eq true}">
            <spring:message code="audit.final"/>
        </jstl:if>
        <jstl:if test="${row.isFinal eq false}">
            <spring:message code="audit.draft"/>
        </jstl:if>
    </display:column>

    <spring:message code="audit.update" var="update"/>
    <display:column title="${update}">
        <jstl:if test="${row.isFinal eq false}">
        <a
                href="audit/auditor/update.do?auditId=${row.id}">
            <spring:message code="audit.update"/>
        </a>
        </jstl:if>
    </display:column>

    <spring:message code="audit.delete" var="delete"/>
    <display:column title="${delete}">
        <jstl:if test="${row.isFinal eq false}">
            <a
                    href="audit/auditor/delete.do?auditId=${row.id}">
                <spring:message code="audit.delete"/>
            </a>
        </jstl:if>
    </display:column>

    <spring:message code="audit.show" var="show"/>
    <display:column title="${show}">
        <a
                href="audit/auditor/show.do?auditId=${row.id}">
            <spring:message code="audit.show"/>
        </a>
    </display:column>
</display:table>

<acme:cancel url="/" code="audit.back"/>
