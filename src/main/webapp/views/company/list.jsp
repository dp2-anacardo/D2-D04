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


    <display:table name="companies" id="row" requestURI="${RequestURI}"
                   pagesize="5" class="displaytag">


        <spring:message code="actor.commercialName" var="subjectHeader" />
        <display:column property="commercialName" title="${subjectHeader}" />


        <!-- Display -->
        <spring:message code="company.positions" var="positions"/>
        <display:column title="${positions}">
            <a
                    href="position/listByCompany.do?companyId=${row.id}">
                <spring:message code="company.positions" />
            </a>

        </display:column>

        <spring:message code="company.auditScore" var="auditScore"/>
        <display:column  title="${auditScore}">
        <jstl:choose>
            <jstl:when test="${row.auditScore == 0.0}">
                nil
            </jstl:when>
            <jstl:otherwise>
                ${row.auditScore}
            </jstl:otherwise>
        </jstl:choose>
        </display:column>

    </display:table>

