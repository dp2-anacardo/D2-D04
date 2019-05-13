<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="company" id="row" requestURI="company/show.do"
               class="displaytag">
    <spring:message code="company.name" var="commercialName"/>
    <display:column property="commercialName" title="${commercialName}"
                    sortable="false"/>
    <spring:message code="company.auditScore" var="auditScore"/>
    <display:column  title="${auditScore}">
        <jstl:choose>
            <jstl:when test="${company.auditScore == 0.0}">
                nil
            </jstl:when>
            <jstl:otherwise>
                ${company.auditScore}
            </jstl:otherwise>
        </jstl:choose>

    </display:column>
</display:table>

<acme:cancel code="position.goBack" url="position/listNotLogged.do"/>