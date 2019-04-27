<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="actors" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <spring:message code="actor.show" var="show" />
    <display:column title="${show}">
        <acme:cancel url='administrator/actorList/showActor.do?actorId=${row.id}' code="actor.show" />
    </display:column>

    <spring:message code="actor.name" var="name"/>
    <display:column property="name" title="${name}"/>

    <spring:message code="actor.email" var="email"/>
    <display:column property="email" title="${email}"/>

    <spring:message code="actor.phoneNumber" var="phoneNumber"/>
    <display:column property="phoneNumber" title="${phoneNumber}"/>

    <spring:message code="actor.isSpammer" var="isSpammer"/>
    <display:column title="${isSpammer}">
        <jstl:if test="${row.isSpammer == null}">
            <jstl:out value="N/A"></jstl:out>
        </jstl:if>
        <jstl:if test="${row.isSpammer != null}">
            <jstl:out value="${row.isSpammer}"></jstl:out>
        </jstl:if>
    </display:column>

    <spring:message code="actor.ban" var="ban"/>
    <display:column title="${ban}">
        <jstl:if
                test="${row.isSpammer == true && row.isBanned == false}">
            <acme:cancel url='administrator/actorList/ban.do?actorId=${row.id}'
                         code="administrator.ban"/>
        </jstl:if>
        <jstl:if
                test="${row.isSpammer == true && row.isBanned == true}">
            <acme:cancel url='administrator/actorList/unban.do?actorId=${row.id}'
                         code="administrator.unban"/>
        </jstl:if>
    </display:column>
</display:table>


<acme:cancel url="administrator/actorList/calculateSpam.do"
             code="administrator.calculateSpam"/>

<acme:cancel url="/" code="messageBox.goBack"/>