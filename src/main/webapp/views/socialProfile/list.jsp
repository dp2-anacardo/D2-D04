<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasAnyRole('ADMIN, COMPANY, ROOKIE')">
    <display:table pagesize="5" class="socialProfiles" name="socialProfiles" requestURI="${requestURI}" id="row">


        <display:column> <a href="socialProfile/admin,company,rookie/edit.do?socialProfileId=${row.id}">
            <spring:message code="socialProfile.edit" /></a> </display:column>

        <spring:message code="socialProfile.nick" var="nick" />
        <display:column property="nick" title="${nick}"/>

        <spring:message code="socialProfile.socialNetworkName" var="socialNetworkName" />
        <display:column property="socialNetworkName" title="${socialNetworkName}"/>

        <spring:message code="socialProfile.profileLink" var="profileLink" />
        <display:column property="profileLink" title="${profileLink}"/>

    </display:table>

    <input type="button" value="<spring:message code="socialProfile.create" />"
           onclick="javascript: relativeRedir('socialProfile/admin,company,rookie/create.do');" />



    <acme:cancel url="profile/myInformation.do" code="messageBox.goBack" />
</security:authorize>