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

    <!-- Single Attributes -->

    <p>
        <acme:showtext code="configuration.edit.maxResults"
                       value="${config.maxResults}"
                       fieldset="true"/>
    </p>
    <p>
        <acme:showtext code="configuration.edit.maxTime"
                       value="${config.maxTime}"
                       fieldset="true"/>
    </p>
    <p>
        <acme:showtext code="configuration.edit.systemName"
                       value="${config.systemName}"
                       fieldset="true"/>
    </p>
    <p>
        <acme:showtext code="configuration.edit.banner"
                       value="${config.banner}"
                       fieldset="true"/>
    </p>
    <p>
        <acme:showtext code="configuration.edit.welcomeEn"
                       value="${config.welcomeMessageEn}"
                       fieldset="true"/>
    </p>
    <p>
        <acme:showtext code="configuration.edit.welcomeEs"
                       value="${config.welcomeMessageEs}"
                       fieldset="true"/>
    </p>
    <p>
        <acme:showtext code="configuration.edit.defaultCC"
                       value="${config.countryCode}"
                       fieldset="true"/>
    </p>

    <!-- Table Attributes -->

    <table>
        <tr>
            <th><spring:message code="configuration.edit.spamWords" /></th>
        </tr>
        <jstl:forEach items="${config.spamWords}"
                      var="spamWords">
            <tr>
                <td><jstl:out value="${spamWords}"/></td>
            </tr>
        </jstl:forEach>
    </table>

    <!-- Buttons -->

    <acme:cancel url="/configuration/administrator/edit.do" code="configuration.edit"/>

    </body>
    </html>
</security:authorize>
