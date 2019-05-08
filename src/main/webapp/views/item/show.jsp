<%--
 * action-1.jsp
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


<display:table name="item" id="row" requestURI="item/show.do"
               class="displaytag">
    <spring:message code="item.name" var="nameHeader"/>
    <display:column property="name" title="${nameHeader}"
                    sortable="false"/>
</display:table>

<display:table name="item" id="row" requestURI="item/show.do"
               class="displaytag">
    <spring:message code="item.description" var="descriptionHeader"/>
    <display:column property="description" title="${descriptionHeader}"
                    sortable="false"/>
</display:table>

<jstl:if test="${not empty item.links}">
    <display:table name="item" id="row" requestURI="item/show.do"
                   class="displaytag">
        <spring:message code="item.links" var="linksHeader"/>
        <display:column title="${linksHeader}" sortable="false">
            <jstl:forEach items="${item.links}" var="elink">
                <tr>
                    <td><a href="<jstl:out value="${elink.link}"/>"><jstl:out
                            value="${elink.link}"/></a></td>
                </tr>
            </jstl:forEach>
        </display:column>
    </display:table>
</jstl:if>

<jstl:if test="${not empty item.pictures}">
    <display:table name="item" id="row" requestURI="item/show.do"
                   class="displaytag">
        <spring:message code="item.pictures" var="picturesHeader"/>
        <display:column title="${picturesHeader}" sortable="false">
            <jstl:forEach items="${item.pictures}" var="picture">
                <tr>
                    <td><a href="<jstl:out value="${picture.link}"/>"><jstl:out
                            value="${picture.link}"/></a></td>
                </tr>
            </jstl:forEach>
        </display:column>
    </display:table>
</jstl:if>

<security:authorize access="hasRole('PROVIDER')">
    <acme:cancel url="item/provider/delete.do?itemID=${row.id}" code="item.delete"/>
    <acme:cancel url="item/provider/list.do" code="item.goBack"/>
</security:authorize>

<security:authorize access="isAnonymous()">
    <acme:cancel url="/" code="item.goBack"/>
</security:authorize>

<security:authorize access="hasAnyRole('ROOKIE', 'ADMIN', 'COMPANY', 'AUDITOR')">
    <acme:cancel url="/" code="item.goBack"/>
</security:authorize>


