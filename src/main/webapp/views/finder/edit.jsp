<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ROOKIE')">
    <form:form action ="finder/rookie/edit.do" modelAttribute="finder">

        <form:hidden path="id" />

        <!-- Single Attributes -->
        <jstl:out value="${messageCode}"/>

        <acme:textbox code="finder.update.keyword" path="keyWord"/>

        <form:label path="deadline">
            <spring:message code="finder.update.deadline" />
        </form:label>
        <form:input path="deadline" placeholder="dd/MM/yyyy HH:mm"/>
        <form:errors cssClass="error" path="deadline" />
        <br/>

        <form:label path="maxDeadline">
            <spring:message code="finder.update.maxDeadline" />
        </form:label>
        <form:input path="maxDeadline" placeholder="dd/MM/yyyy HH:mm"/>
        <form:errors cssClass="error" path="maxDeadline" />
        <br />

        <acme:textbox code="finder.update.minSalary" path="minSalary"/>
        <br>

        <!-- Submit and Cancel -->

        <acme:submit name="save" code="finder.update.update"/>&nbsp

        <acme:cancel url="/" code="finder.update.cancel"/>&nbsp

        <acme:cancel url="finder/rookie/clear.do" code="finder.update.clear"/>&nbsp

    </form:form>
</security:authorize>
