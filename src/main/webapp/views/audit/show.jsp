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

<acme:showtext fieldset="true" code="audit.moment" value="${audit.moment}"/>
<acme:showtext fieldset="true" code="audit.text" value="${audit.text}"/>
<acme:showtext fieldset="true" code="audit.score" value="${audit.score}"/>

<fieldset><legend><spring:message code="audit.isFinal"/></legend>
    <jstl:if test="${audit.isFinal eq true}">
        <spring:message code="audit.final"/>
    </jstl:if>
    <jstl:if test="${audit.isFinal eq false}">
        <spring:message code="audit.draft"/>
    </jstl:if>
</fieldset>

<acme:cancel url="audit/auditor/list.do" code="audit.back"/>


