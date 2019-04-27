<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="curricula/rookie/create.do" modelAttribute="personalD">

    <security:authorize
            access="hasAnyRole('ROOKIE')">


        <acme:textbox code="curricula.personal.name" path="fullName" />
        

        <acme:textbox code="curricula.personal.statement" path="statement" />
        

        <acme:textbox code="curricula.personal.phoneNumber" path="phoneNumber" />
        

        <acme:textbox code="curricula.personal.githubProfile" path="githubProfile" />
        

        <acme:textbox code="curricula.personal.linkedInProfile" path="linkedInProfile" />
        

        <acme:submit name="save" code="curricula.save"/>

        <acme:cancel url="curricula/rookie/list.do" code="curricula.cancel"/>

    </security:authorize>
</form:form>