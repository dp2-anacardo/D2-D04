<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form id="myform" action="curricula/rookie/create.do" modelAttribute="personalD">

    <security:authorize
            access="hasAnyRole('ROOKIE')">


        <acme:textbox code="curricula.personal.name" path="fullName" />
        

        <acme:textbox code="curricula.personal.statement" path="statement" />
        

        <acme:textbox code="curricula.personal.phoneNumber" path="phoneNumber" />
        

        <acme:textbox code="curricula.personal.githubProfile" path="githubProfile" />
        

        <acme:textbox code="curricula.personal.linkedInProfile" path="linkedInProfile" />

        <script type="text/javascript">
            function phoneValidation(){
                var phoneNumber = document.getElementById("phoneNumber").value;
                var regexPN = /^(\d\d\d\d+)$/;
                var regex1 = /^((\+[1-9][0-9]{0,2}) \(([1-9][0-9]{0,2})\) (\d\d\d\d+))$/;
                var regex2 = /^(\+[1-9][0-9]{0,2}) (\d\d\d\d+)$/;

                if (regexPN.test(phoneNumber)) {
                    return document.getElementById("myform").submit();
                } else if(regex1.test(phoneNumber)) {
                    return document.getElementById("myform").submit();
                }else if(regex2.test(phoneNumber)){
                    return document.getElementById("myform").submit();
                }else{
                    var confirm = window.confirm('<spring:message code = "actor.confirm"/>');
                    if(!confirm){
                        return 0;
                    }else{
                        return document.getElementById("myform").submit();
                    }
                }
            }
        </script>


        <input type="button" name="save"
               value="<spring:message code="curricula.save"/>"
               onclick="phoneValidation();"
        />&nbsp;

        <acme:cancel url="curricula/rookie/list.do" code="curricula.cancel"/>

    </security:authorize>
</form:form>