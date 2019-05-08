<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ROOKIE')">
    <display:table pagesize="5" class="position" name="positions" requestURI="finder/rookie/list.do" id="row">

        <!-- Action links -->

        <!-- Attributes -->

        <spring:message code="position.ticker" var="ticker" />
        <display:column property="ticker" title="${ticker}"/>

        <spring:message code="position.title" var="title" />
        <display:column property="title" title="${title}"/>

        <spring:message code="position.description" var="description" />
        <display:column property="description" title="${description}"/>

        <spring:message code="position.deadline" var="deadline" />
        <display:column property="deadline" title="${deadline}" format="{0,date,dd/MM/yyyy HH:mm}"/>

    </display:table>
</security:authorize>