<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="security"
              uri="http://www.springframework.org/security/tags" %>
    <%@taglib prefix="display" uri="http://displaytag.sf.net" %>
    <%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

</head>
<body>
<form:form action="sponsorship/provider/create.do" modelAttribute="sponsorship">

    <%--  Hidden properties --%>
    <form:hidden path="id"/>

    <%-- Parade--%>
    <acme:select items="${positionList}" itemLabel="title"
                 code="sponsorship.position" path="position"/>
    <br>

    <%-- Banner --%>
    <acme:textbox code="sponsorship.banner" path="banner"/>
    <br>

    <%-- CreditCard --%>
    <fieldset>
        <legend><spring:message code="actor.CreditCard"/></legend>

        <acme:textbox code="credit.holderName" path="creditCard.holder"/>
        <br/>

        <acme:textbox code="credit.brandName" path="creditCard.brandName"/>
        <br/>

        <acme:textbox code="credit.number" path="creditCard.number"/>
        <br/>

        <acme:textbox code="credit.expiration" path="creditCard.expirationYear" placeholder="MM/YY"/>
        <br/>

        <acme:textbox code="credit.cvvCode" path="creditCard.cvv"/>
        <br/>
    </fieldset>

    <%-- Buttons --%>
    <security:authorize access="hasRole('PROVIDER')">
        <acme:submit name="save" code="sponsorship.save"/>

        <acme:cancel url="sponsorship/provider/list.do" code="sponsorship.back"/>

    </security:authorize>

</form:form>
</body>
</html>