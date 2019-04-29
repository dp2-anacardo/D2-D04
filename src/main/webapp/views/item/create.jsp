<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="item/provider/create.do" modelAttribute="item">

    <security:authorize
            access="hasRole('PROVIDER')">

        <%-- Name --%>
        <acme:textbox code="item.name" path="name"/>
        <br>

        <%-- Description --%>
        <acme:textarea code="item.description" path="description"/>
        <br>

        <%-- Links --%>
        <acme:textbox code="item.links" path="links"/>
        <jstl:if test="${not empty linkError }">
            <p class="error">${linkError }</p>
        </jstl:if>
        <br>

        <%-- Pictures --%>
        <acme:textbox code="item.pictures" path="pictures"/>
        <jstl:if test="${not empty pictureError }">
            <p class="error">${pictureError }</p>
        </jstl:if>
        <br>


        <%-- Buttons --%>
        <acme:submit name="save" code="item.save"/>

        <acme:cancel url="item/provider/list.do" code="item.cancel"/>

    </security:authorize>
</form:form>