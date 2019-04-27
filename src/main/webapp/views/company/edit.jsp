<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

</head>
<body>
<security:authorize access="hasRole('COMPANY')">
<spring:message code="actor.firstMessage" />
<form:form action="company/company/edit.do" modelAttribute="company">

	<form:hidden path="id" />
 
	<form:label path="name">
		<spring:message code="actor.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="surname">
		<spring:message code="actor.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="commercialName">
		<spring:message code="actor.commercialName" />
	</form:label>
	<form:input path="commercialName" />
	<form:errors cssClass="error" path="commercialName" />
	<br />
	
	<form:label path="photo">
		<spring:message code="actor.photo" />
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="email">
		<spring:message code="actor.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="actor.phoneNumber" />
	</form:label>
	<form:input path="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	
	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="vatNumber">
		<spring:message code="actor.vatNumber" />
	</form:label>
	<form:input path="vatNumber" />
	<form:errors cssClass="error" path="vatNumber" />
	<br />
	
	<input type="submit" name="update"
		value="<spring:message code="actor.save" />" />&nbsp; 
	
	<input type="button" name="back"
		value="<spring:message code="message.goBack" />"
		onclick="javascript: relativeRedir('/');" />
	<br />

</form:form>
</security:authorize>
</body>
</html>