<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasAnyRole('ADMIN, COMPANY, ROOKIE')">
    <form:form action="socialProfile/admin,company,rookie/edit.do" modelAttribute="socialProfile">

        <form:hidden path="id" />

        <form:label path="nick">
            <spring:message code="socialProfile.nick"/>
        </form:label>
        <form:input path="nick"/>
        <form:errors cssClass="error" path="nick"/>
        <br />

        <form:label path="socialNetworkName">
            <spring:message code="socialProfile.socialNetworkName"/>
        </form:label>
        <form:input path="socialNetworkName"/>
        <form:errors cssClass="error" path="socialNetworkName"/>
        <br />

        <form:label path="profileLink">
            <spring:message code="socialProfile.profileLink"/>
        </form:label>
        <form:input path="profileLink"/>
        <form:errors cssClass="error" path="profileLink"/>
        <br />


        <input type="submit" name="save"
               value="<spring:message code="socialProfile.save" />" />&nbsp;
        <jstl:if test="${socialProfile.id != 0}">
            <input type="submit" name="delete"
                   value="<spring:message code="socialProfile.delete" />" />
        </jstl:if>
        <input type="button" name="cancel"
               value="<spring:message code="socialProfile.cancel" />"
               onclick="javascript: relativeRedir('socialProfile/admin,company,rookie/list.do');" />
        <br />


    </form:form>
</security:authorize>