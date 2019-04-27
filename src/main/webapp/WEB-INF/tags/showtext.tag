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
 
<%@ attribute name="code" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="fieldset" required="false" %>

<%-- Definition --%>

<jstl:choose>
<jstl:when test="${fieldset == false}">
	<b><spring:message code="${code}"/>:</b>
	<br />
	<jstl:out value="${value}"/>
</jstl:when>
<jstl:when test="${fieldset == true}">
	<fieldset>
	<legend><b><spring:message code="${code}"/></b></legend>
	<jstl:out value="${value}"/>
	</fieldset>
</jstl:when>
</jstl:choose>

