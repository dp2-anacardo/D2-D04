<%--
 * action-2.jsp
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

<form:form modelAttribute="problem">

    <security:authorize
            access="hasRole('COMPANY')">

        <form:hidden path="id" />

        <%-- Title --%>
        <acme:textbox code="problem.title" path="title"/>
        <br>

        <%-- Statement --%>
        <acme:textarea code="problem.statement" path="statement"/>
        <br>

        <%-- Hint --%>
        <acme:textbox code="problem.hint" path="hint"/>
        <br>

        <%-- Attachment --%>
        <acme:textbox code="problem.attachment" path="attachment"/>
        <jstl:if test="${not empty attachmentError }">
            <p class="error">${attachmentError }</p>
        </jstl:if>
        <br>

        <%-- Is Final --%>
        <spring:message code="problem.isFinal"/>
        <form:select path="isFinal" multiple="false">
            <form:option value="0"><spring:message code="problem.isFinal.draft"/></form:option>
            <form:option value="1"><spring:message code="problem.isFinal.final"/></form:option>
        </form:select>
        <form:errors class="error" path="isFinal"/>
        <br>

        <%-- Buttons --%>
        <acme:submit name="update" code="problem.update"/>

        <acme:submit name="delete" code="problem.delete"/>

        <acme:cancel url="problem/company/list.do" code="problem.cancel"/>

    </security:authorize>
</form:form>