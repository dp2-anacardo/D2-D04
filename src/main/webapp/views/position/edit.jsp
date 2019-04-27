<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="position/company/edit.do" modelAttribute="position">

    <security:authorize
            access="hasAnyRole('COMPANY')">

        <form:input type ="hidden" path="id" readonly="true"/>

        <acme:textbox code="position.title" path="title" />
        

        <acme:textbox code="position.description" path="description" />
        

        <acme:textbox code="position.deadline" path="deadline" />
        

        <acme:textbox code="position.profile" path="profile" />
        

        <acme:textarea code="position.skill" path="skill" />
        

        <acme:textarea code="position.technology" path="technology" />
        

        <acme:textbox code="position.salary" path="salary" />
        

        <form:label path="problems">
            <spring:message code="position.problems"/>
        </form:label>
        <form:select path="problems">
            <form:options items="${problems}" itemValue="id" itemLabel="title"/>
        </form:select>
        <br/>

        <acme:submit name="saveDraft" code="position.saveDraft"/>

        <acme:submit name="saveFinal" code="position.saveFinal"/>

        <acme:cancel code="position.delete" url="position/company/delete.do?positionId=${position.id}"/>

        <acme:cancel url="position/company/list.do" code="position.goBack"/>

    </security:authorize>
</form:form>