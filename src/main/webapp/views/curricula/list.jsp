<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ROOKIE')">
    <display:table name="curriculas" id="row" requestURI="${RequestURI}"
                   pagesize="5" class="displaytag">


        <spring:message code="curricula.id" var="subjectHeader" />
        <display:column property="id" title="${subjectHeader}" />


        <!-- Display -->
        <spring:message code="curricula.display" var="display"/>
        <display:column title="${display}">
            <a
                    href="curricula/rookie/display.do?curriculaId=${row.id}">
                <spring:message code="curricula.display" />
            </a>
        </display:column>

        <spring:message code="curricula.delete" var="delete"/>
        <display:column title="${delete}">
            <a
                    href="curricula/rookie/delete.do?curriculaId=${row.id}">
                <spring:message code="curricula.delete" />
            </a>
        </display:column>

    </display:table>

    <acme:cancel url="curricula/rookie/create.do" code="curricula.create"/>
</security:authorize>
