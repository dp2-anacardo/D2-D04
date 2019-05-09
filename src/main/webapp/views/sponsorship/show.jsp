<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('PROVIDER')">
	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/provider/show.do" class="displaytag">
		<spring:message code="position.title" var="paradeHeader" />
		<display:column property="position.title" title="${paradeHeader}"
			sortable="false" />
		<spring:message code="position.ticker" var="tickerHeader" />
		<display:column property="position.ticker" title="${tickerHeader}"
						sortable="false" />
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/provider/display.do" class="displaytag">
		<spring:message code="sponsorship.targetURL" var="targetURL" />
		<display:column title="${targetURL}">
			<a href="<%=request.getContextPath()%>/position/show.do?positionId=${row.position.id}">Target URL</a>
		</display:column>
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/provider/display.do" class="displaytag">
		<spring:message code="sponsorship.banner" var="bannerHeader" />
		<display:column title="${bannerHeader}" sortable="false">
			<img src="${row.banner}" />
		</display:column>
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/provider/show.do" class="displaytag">

		<spring:message code="sponsorship.creditCard.holderName"
			var="creditCardholderHeader" />
		<display:column property="creditCard.holder"
			title="${creditCardholderHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.brandName"
			var="creditCardBrandNameHeader" />
		<display:column property="creditCard.brandName"
			title="${creditCardBrandNameHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.number"
			var="creditCardNumberHeader" />
		<display:column property="creditCard.number"
			title="${creditCardNumberHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.cvvCode"
			var="creditCardcvvCodeHeader" />
		<display:column property="creditCard.cvv"
			title="${creditCardcvvCodeHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.expiration"
			var="creditCardExpirationHeader" />
		<display:column property="creditCard.expirationYear"
			title="${creditCardExpirationHeader}" sortable="false" format="{0,date,MM/YY}" />
	</display:table>

	<acme:cancel url="sponsorship/provider/update.do?sponsorshipId=${row.id}"
		code="sponsorship.update" />

	<acme:cancel url="sponsorship/provider/delete.do?sponsorshipId=${row.id}" code="problem.delete"/>

	<acme:cancel url="sponsorship/provider/list.do" code="sponsorship.back" />


</security:authorize>