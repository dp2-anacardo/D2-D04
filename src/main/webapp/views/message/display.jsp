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
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<display:table name="mesage" id="row" requestURI="message/display.do"
	class="displaytag">
	<spring:message code="message.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}"
		sortable="false" />
</display:table>

<display:table name="mesage" id="row" requestURI="message/display.do"
	class="displaytag">
	<spring:message code="message.sender" var="senderHeader" />
	<display:column property="sender.email" title="${senderHeader}"
		sortable="false" />
</display:table>

<display:table name="mesage" id="row"
	requestURI="message/display.do" class="displaytag">
	<spring:message code="message.recipients" var="recipientHeader" />
	<display:column property="recipient.email" title="${recipientHeader}"
		sortable="false" />
</display:table>

<display:table name="mesage" id="row" requestURI="message/display.do"
	class="displaytag">
	<spring:message code="message.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="false" />
</display:table>

<display:table name="mesage" id="row" requestURI="message/display.do"
	class="displaytag">
	<spring:message code="message.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}" sortable="false" />
</display:table>

<display:table name="mesage" id="row" requestURI="message/display.do"
	class="displaytag">
	<spring:message code="message.tags" var="tagsHeader" />
	<display:column title="${tagsHeader}">
		<jstl:forEach var="text" items="${row.tags}" varStatus="loop">
			${text}${!loop.last ? ',' : ''}&nbsp
		</jstl:forEach>
	</display:column>
</display:table>

<acme:cancel url="message/delete.do?messageID=${row.id}" code="message.delete"/>
<acme:cancel url="message/list.do?" code="message.goBack" />
