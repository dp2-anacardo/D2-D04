<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('COMPANY')">

    <acme:showtext fieldset="true" code="rookie.name" value="${rookie.name}"/>
    <acme:showtext fieldset="true" code="rookie.surname" value="${rookie.surname}"/>

    <fieldset>
        <legend><spring:message code="rookie.photo"/></legend>
        <a href='${rookie.photo}'>${rookie.photo}</a>
    </fieldset>

    <acme:showtext fieldset="true" code="rookie.email" value="${rookie.email}"/>
    <acme:showtext fieldset="true" code="rookie.address" value="${rookie.address}"/>

    <input type="button" name="cancel"
           value="<spring:message code="position.goBack" />"
           onclick="javascript: window.history.back();"/>
</security:authorize>
