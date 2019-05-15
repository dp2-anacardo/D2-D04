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

<security:authorize access="hasRole('AUDITOR')">
    <form:form action="audit/auditor/create.do" modelAttribute="audit">
        <form:hidden path="id" readOnly = "true"/>
        <input type="hidden" name="positionId" value="${positionId}" readonly>

        <acme:textbox code="audit.text" path="text"/>
        <br>

        <form:label path="score" >
            <spring:message code="audit.score" />*
        </form:label>
        <form:input type="number" step="0.5" path="score" id="score"/>
        <form:errors cssClass="error" path="score" />
        <br />

        <spring:message code="audit.isFinal"/>
        <form:select path="isFinal" multiple="false">
            <form:option value="0"><spring:message code="audit.draft"/></form:option>
            <form:option value="1"><spring:message code="audit.final"/></form:option>
        </form:select>
        <form:errors class="error" path="isFinal"/>
        <br>

        <acme:submit name="save" code="audit.save"/>

        <acme:cancel url="audit/auditor/list.do" code="audit.cancel"/>

    </form:form>
</security:authorize>