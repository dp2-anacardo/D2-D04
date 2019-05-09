<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="provider" id="row" requestURI="provider/show.do"
               class="displaytag">
    <spring:message code="provider.make" var="title"/>
    <display:column property="make" title="${title}"
                    sortable="false"/>
</display:table>

<acme:cancel url="/" code="problem.goBack"/>