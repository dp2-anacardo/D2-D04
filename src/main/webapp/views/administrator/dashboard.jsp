<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@taglib prefix="security"
              uri="http://www.springframework.org/security/tags"%>
    <%@taglib prefix="display" uri="http://displaytag.sf.net"%>
</head>
<body>

<security:authorize access="hasRole('ADMIN')">

    <b><spring:message code="administrator.avgNumberOfPositions" /></b> ${AvgNumberOfPositions} <br />
    <b><spring:message code="administrator.minNumberOfPositions" /></b> ${MinNumberOfPositions} <br />
    <b><spring:message code="administrator.maxNumberOfPositions" /></b> ${MaxNumberOfPositions} <br />
    <b><spring:message code="administrator.stddevNumberOfPositions" /></b> ${StddevNumberOfPositions} <br />

    <b><spring:message code="administrator.avgNumberOfApps" /></b> ${AvgNumberOfApps} <br />
    <b><spring:message code="administrator.minNumberOfApps" /></b> ${MinNumberOfApps} <br />
    <b><spring:message code="administrator.maxNumberOfApps" /></b> ${MaxNumberOfApps} <br />
    <b><spring:message code="administrator.stddevNumberOfApps" /></b> ${StddevNumberOfApps} <br />

    <b><spring:message code="administrator.companiesWithOfferedMorePositions" /></b>
    <jstl:forEach var="x" items="${CompaniesWithOfferedMorePositions}">
        <br>
        - ${x.commercialName}
    </jstl:forEach>
    <br />


    <b><spring:message code="administrator.rookiesWithMoreApplications" /></b>
    <jstl:forEach var="x" items="${RookiesWithMoreApplications}">
        <br>
        - ${x.userAccount.username}
    </jstl:forEach>
    <br />

    <b><spring:message code="administrator.avgSalaryOffered" /></b> ${AvgSalaryOffered} <br />
    <b><spring:message code="administrator.minSalaryOffered" /></b> ${MinSalaryOffered} <br />
    <b><spring:message code="administrator.maxSalaryOffered" /></b> ${MaxSalaryOffered} <br />
    <b><spring:message code="administrator.stddevSalaryOffered" /></b> ${StddevSalaryOffered} <br />

    <b><spring:message code="administrator.bestPositionSalaryOffered" /></b> ${BestPositionSalaryOffered} <br />

    <b><spring:message code="administrator.worstPositionSalaryOffered" /></b> ${WorstPositionSalaryOffered} <br />

    <b><spring:message code="administrator.avgNumOfCurricula" /></b> ${AvgNumOfCurricula} <br />
    <b><spring:message code="administrator.minNumOfCurricula" /></b> ${MinNumOfCurricula} <br />
    <b><spring:message code="administrator.maxNumOfCurricula" /></b> ${MaxNumOfCurricula} <br />
    <b><spring:message code="administrator.stddevNumOfCurricula" /></b> ${StddevNumOfCurricula} <br />

    <b><spring:message code="administrator.avgResultsOfFinder" /></b> ${AvgResultsOfFinder} <br />
    <b><spring:message code="administrator.minResultsOfFinder" /></b> ${MinResultsOfFinder} <br />
    <b><spring:message code="administrator.maxResultsOfFinder" /></b> ${MaxResultsOfFinder} <br />
    <b><spring:message code="administrator.stddevResultsOfFinder" /></b> ${StddevResultsOfFinder} <br />

    <b><spring:message code="administrator.ratioOfNotEmptyFinders" /></b> ${RatioOfNotEmptyFinders} <br />

    <b><spring:message code="administrator.ratioOfEmptyFinders" /></b> ${RatioOfEmptyFinders} <br />

</security:authorize>
</body>
</html>