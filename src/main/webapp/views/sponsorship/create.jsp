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
    <div id="card">
            <%-- creditCard --%>
        <form:label path="creditCard">
            <spring:message code="sponsorship.creditCard"/>
        </form:label>
        <br>


        <form:label path="creditCard.holder">
            <spring:message code="sponsorship.creditCard.holderName"/>
        </form:label>
        <form:input path="creditCard.holder"/>
        <form:errors class="error" path="creditCard.holder"/>
        <br>

        <form:label path="creditCard.brandName">
            <spring:message code="sponsorship.creditCard.brandName"/>
        </form:label>
        <form:input path="creditCard.brandName"/>
        <form:errors class="error" path="creditCard.brandName"/>
        <br>

        <form:label path="creditCard.number">
            <spring:message code="sponsorship.creditCard.number"/>
        </form:label>
        <form:input path="creditCard.number" type="number"/>
        <form:errors class="error" path="creditCard.number"/>
        <br>

        <form:label path="creditCard.expirationYear">
            <spring:message code="sponsorship.creditCard.expiration"/>
        </form:label>
        <form:input path="creditCard.expirationYear" placeholder="MM/YY"
                    format="{0,date,MM/YY}"/>
        <form:errors class="error" path="creditCard.expirationYear"/>
        <br>

        <form:label path="creditCard.cvv">
            <spring:message code="sponsorship.creditCard.cvvCode"/>
        </form:label>
        <form:input path="creditCard.cvv" type="number"/>
        <form:errors class="error" path="creditCard.cvv"/>
        <br>

    </div>

    <%-- Buttons --%>
    <security:authorize access="hasRole('PROVIDER')">
        <acme:submit name="save" code="sponsorship.save"/>

        <acme:cancel url="sponsorship/provider/list.do" code="sponsorship.back"/>

    </security:authorize>

</form:form>
</body>
</html>