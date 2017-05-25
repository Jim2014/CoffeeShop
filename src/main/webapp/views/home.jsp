<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/headerScript.jsp"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#menu-home').addClass('active');
});
</script>

</head>
<body>
	<%@ include file="/views/headerNav.jsp" %>
	You can choose any presentation framework that could be integrated with
	Spring
	<p>
	<p>
		The admin user is "<b>admin</b>" and the password is "<b>admin</b>"
	<p>
	<p>
		The public user is "<b>user</b>" and the password is "<b>user</b>"
	<p>
		<a href="<c:url value="/secure" />"> Go to Secure Area </a>
</body>
</html>