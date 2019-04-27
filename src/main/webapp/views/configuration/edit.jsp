<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">
    <!DOCTYPE>
    <html>
    <head>
        <link rel="stylesheet" href="styles/table.css" type="text/css">
    </head>
    <body>
    <form:form action ="configuration/administrator/edit.do" modelAttribute="configF">

        <form:hidden path="id"/>

        <!-- Single areas -->
        <jstl:out value="${messageCode}"/>

        <acme:textboxbs code="configuration.edit.maxResults" path="maxResults"/>
        <acme:textboxbs code="configuration.edit.maxTime" path="maxTime"/>
        <acme:textboxbs code="configuration.edit.systemName" path="systemName"/>
        <acme:textboxbs code="configuration.edit.banner" path="banner"/>
        <acme:textboxbs code="configuration.edit.welcomeEn"
                        path="welcomeMessageEn"/>
        <acme:textboxbs code="configuration.edit.welcomeEs"
                        path="welcomeMessageEs"/>
        <acme:textboxbs code="configuration.edit.defaultCC" path="countryCode"/>

        <!-- Edit words -->

        <table>
            <tr>
                <th><spring:message code="configuration.edit.spamWords" /></th>
                <th></th>
            </tr>
            <jstl:forEach items="${configF.spamWords}"
                          var="spamWords">
                <tr>
                    <td><jstl:out value="${spamWords}"/></td>
                    <td><acme:cancel url="/configuration/administrator/deleteSWord.do?spamWord=${spamWords}"
                                     code="configuration.edit.delete"/></td>
                </tr>
            </jstl:forEach>
        </table>

        <form:input path="addSW"/>
        <form:errors path="addSW" cssClass="error" />
        <acme:submit name="addWord" code="configuration.edit.addSW"/>&nbsp;
        <br>
        <br>


        <acme:submit name="save" code="configuration.edit"/>&nbsp
        <acme:cancel url="/configuration/administrator/show.do"
                     code="configuration.edit.cancel"/>&nbsp


    </form:form>
    </body>
    </html>
</security:authorize>
