<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('COMPANY')">
<form:form action="application/company/reject.do" modelAttribute="application">
	<form:hidden path="id" />
	
	<form:label path="rejectComment">
	<spring:message code="application.rejectComment"/>
	</form:label>
	<form:textarea path="rejectComment"/>
	<form:errors cssClass="error" path="rejectComment"/>
	<br/>
	
	<input type="submit" name="reject"
		value="<spring:message code="application.reject" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/company/list.do');" />
	<br />
	
	
</form:form>
</security:authorize>