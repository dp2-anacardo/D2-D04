<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



    <security:authorize
            access="hasAnyRole('ROOKIE')">
        <form:form action="educationalData/rookie/edit.do" modelAttribute="educationalD">
        <form:hidden path="id" readOnly="true"/>
        <input type="hidden" name="curriculaId" value="${curriculaId}" readonly>


        <acme:textbox code="curricula.educational.degree" path="degree" />
        

        <acme:textbox code="curricula.educational.institution" path="institution" />
        

        <acme:textbox code="curricula.educational.mark" path="mark" />
        

        <acme:textbox code="curricula.educational.startD" path="startDate" />
        

        <acme:textbox code="curricula.educational.endD" path="endDate" />
        

        <acme:submit name="save" code="curricula.save"/>

        <acme:cancel url="curricula/rookie/list.do" code="curricula.cancel"/>
        </form:form>
    </security:authorize>
