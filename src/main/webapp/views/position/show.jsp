<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.title" var="title"/>
    <display:column property="title" title="${title}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.description" var="description"/>
    <display:column property="description" title="${description}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.deadline" var="deadline"/>
    <display:column property="deadline" title="${deadline}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.profile" var="profile"/>
    <display:column property="profile" title="${profile}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.skill" var="skill"/>
    <display:column property="skill" title="${skill}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.technology" var="technology"/>
    <display:column property="technology" title="${technology}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.salary" var="salary"/>
    <display:column property="salary" title="${salary}"
                    sortable="false"/>
</display:table>

<display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
    <spring:message code="position.company" var="company"/>
    <display:column property="company.commercialName" title="${company}"
                    sortable="false"/>
</display:table>

<jstl:if test="${row.isCancelled == true}">
    <display:table name="position" id="row" requestURI="position/show.do"
               class="displaytag">
        <spring:message code="position.status" var="status"/>
        <display:column title="${status}" sortable="false">
            Cancelled
        </display:column>
    </display:table>
</jstl:if>

<input type="button" name="cancel"
         value="<spring:message code="position.goBack" />"
         onclick="javascript: window.history.back();" />


