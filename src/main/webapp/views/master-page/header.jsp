<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${configuration.banner}" alt="${configuration.systemName}" height="150" width="400"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/administrator/broadcast.do"><spring:message code="master.page.administrator.broadcast" /></a></li>
					<li><a href="administrator/actorList.do"><spring:message code="master.page.administrator.actorList" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/administrator/create.do"><spring:message code="master.page.administrator.register" /></a></li>
					<li><a href="administrator/auditor/create.do"><spring:message code="master.page.administrator.registerAuditor" /></a></li>
					<jstl:if test="${rebranding==true}">
						<li><a href="message/admin/rebranding.do"><spring:message code="master.page.administrator.rebranding" /></a></li>
					</jstl:if>
					<li><a href="company/administrator/computeAS.do"><spring:message code="master.page.administrator.computeAS" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="configuration/administrator/show.do"><spring:message code="master.page.configuration" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv"><spring:message	code="master.page.rookie" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curricula/rookie/list.do"><spring:message code="master.page.rookie.curricula" /></a></li>
					<li><a href="application/rookie/list.do"><spring:message code="master.page.application.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/rookie/edit.do"><spring:message code="master.page.finder.edit" /></a></li>
					<li><a href="finder/rookie/list.do"><spring:message code="master.page.finder.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="problem/company/list.do"><spring:message code="master.page.company.problem" /></a></li>
					<li><a href="position/company/list.do"><spring:message code="master.page.position.list" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv"><spring:message	code="master.page.provider" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="item/provider/list.do"><spring:message code="master.page.provider.item" /></a></li>
					<li><a href="sponsorship/provider/list.do"><spring:message code="master.page.provider.sponsorship" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="audit/auditor/list.do"><spring:message code="master.page.auditor.audits" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="position/listNotLogged.do"><spring:message code="master.page.position.available" /></a></li>
			<li><a href="provider/listNotLogged.do"><spring:message code="master.page.provider.all" /></a></li>
			<li><a href="item/listAllNotLogged.do"><spring:message code="master.page.item.all" /></a></li>
			<li><a href="company/list.do"><spring:message code="master.page.company.all" /></a></li>
			<li><a href="position/search.do"><spring:message code="master.page.search" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a href="position/listNotLogged.do"><spring:message code="master.page.position.available" /></a></li>
			<li><a href="provider/listNotLogged.do"><spring:message code="master.page.provider.all" /></a></li>
			<li><a href="item/listAllNotLogged.do"><spring:message code="master.page.item.all" /></a></li>
			<li><a href="company/list.do"><spring:message code="master.page.company.all" /></a></li>
			<li><a href="position/search.do"><spring:message code="master.page.search" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/myInformation.do"><spring:message code="master.page.profile.myInformation" /></a></li>
					<li><a href="message/list.do"><spring:message code="master.page.message" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>				
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

