<%--
 * textarea.tag
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="bold" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${bold == null}">
	<jstl:set var="bold" value="false" />
</jstl:if>

<%-- Definition --%>

<div class="form-group">
	<form:label path="${path}">
	<jstl:choose>
	<jstl:when test="${bold}">
		<b><spring:message code="${code}" /></b>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="${code}" />
	</jstl:otherwise>
	</jstl:choose>
	</form:label>
	<br/>
	<form:textarea path="${path}" readonly="${readonly}" />
	<form:errors path="${path}" cssClass="error" />
</div>
