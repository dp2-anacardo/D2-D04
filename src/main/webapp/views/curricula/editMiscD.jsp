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
    <form:form action="miscData/rookie/edit.do" modelAttribute="miscD">
        <form:hidden path="id" readOnly="true"/>
        <input type="hidden" name="curriculaId" value="${curriculaId}" readonly>


        <acme:textarea code="curricula.misc.freeT" path="freeText" />
        

        <acme:textarea code="curricula.misc.attachments" path="attachment" />
        <jstl:if test="${not empty attachmentError }">
            <p class="error">${attachmentError }</p>
        </jstl:if>
        <br/>

        <acme:submit name="save" code="curricula.save"/>

        <acme:cancel url="curricula/rookie/list.do" code="curricula.cancel"/>
    </form:form>
</security:authorize>