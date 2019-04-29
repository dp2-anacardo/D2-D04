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

<jstl:set var="count" value="0" scope="page" />

<display:table name="messages" id="row" requestURI="message/list.do"
	pagesize="5" class="displaytag">


	<spring:message code="message.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}" />

	<spring:message code="message.sender" var="senderHeader" />
	<display:column property="sender.name" title="${senderHeader}"
		sortable="true" />

<%--	<spring:message code="message.tags" var="tagsHeader" />--%>
<%--	<display:column title="${tagsHeader}">--%>
<%--		<jstl:forEach var="text" items="${row.tags}" varStatus="loop">--%>
<%--			${text}${!loop.last ? ',' : ''}&nbsp--%>
<%--		</jstl:forEach>--%>
<%--	</display:column>--%>

	<spring:message code="message.tags" var="tagsHeader" />
	<display:column title="${tagsHeader}" sortable="true">
		<jstl:out value="${tgs[count]}"/>
	</display:column>
	<jstl:set var="count" value="${count + 1}" scope="page"/>
	<!-- Display -->
	<display:column>
		<a
			href="message/display.do?messageID=${row.id}">
			<spring:message code="message.display" />
		</a>
	</display:column>

</display:table>

<security:authorize
	access="hasAnyRole('ADMIN', 'ROOKIE', 'COMPANY', 'PROVIDER', 'AUDITOR')">
	<acme:cancel url="message/create.do" code="message.create"/>
</security:authorize>

<acme:cancel url="/" code="message.goBack"/>
