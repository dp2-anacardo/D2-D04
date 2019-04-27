
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

</head>
<body>
<spring:message code="actor.firstMessage" />
<form:form id="myform" action="company/create.do" modelAttribute="companyForm">

	<form:hidden path="id" />
 
 	<fieldset>
 	<legend><spring:message code="actor.PersonalData" /></legend>
 	*
	<acme:textbox code="actor.username" path="username"/>
	<br />
	
	<form:label path="password" >
		<spring:message code="actor.password" />*
	</form:label>
	<form:password path="password" id="password"/>
	<form:errors cssClass="error" path="password" />
	<br />
	
	<form:label path="confirmPass">
		<spring:message code="actor.confirmPass" />*
	</form:label>
	<form:password path="confirmPass" id="confirmPassword"/>
	<form:errors cssClass="error" path="password" />
	<br />
	
	*
	<acme:textbox code="actor.name" path="name"/>
	<br />
	
	*
	<acme:textbox code="actor.surname" path="surname"/>
	<br />
	*
	<acme:textbox code="actor.commercialName" path="commercialName"/>
	<br />
	
	<acme:textbox code="actor.photo" path="photo"/>
	<br />
	*
	<acme:textbox code="actor.email" path="email"/>
	<br />
	*
	<acme:textbox code="actor.phoneNumber" path="phoneNumber"/>
	<br />
	
	<acme:textbox code="actor.address" path="address"/>
	<br />
	*
	<acme:textbox code="actor.vatNumber" path="vatNumber"/>
	<br />
	</fieldset>
	<fieldset>
 	<legend><spring:message code="actor.CreditCard" /></legend>
 	*
	<acme:textbox code="credit.holderName" path="holderName"/>
	<br />
	*
	<acme:textbox code="credit.brandName" path="brandName"/>
	<br />
	*
	<acme:textbox code="credit.number" path="number"/>
	<br />
	*
	<acme:textbox code="credit.expiration" path="expiration"/>
	<br />
	*
	<acme:textbox code="credit.cvvCode" path="cvvCode"/>
	<br />
	</fieldset>
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
				return window.history.back();
			}else{
				return document.getElementById("myform").submit();
			}
		}
	}
	</script>
	
	
	 <div class=terms>
	 <input type="checkbox" required name="terms">
	 <label for="terms"><spring:message code="terms" /></label>
	 </div>
	
	<input type="button" name="save"
		value="<spring:message code="actor.save"/>"
		onclick="phoneValidation();"
		 />&nbsp; 
	
		<input type="button" name="cancel"
		value="<spring:message code="messageBox.goBack" />"
		onclick="javascript: relativeRedir('/');" />
	<br />
 	
</form:form>
</body>
</html>