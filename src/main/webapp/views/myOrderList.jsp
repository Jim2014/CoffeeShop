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
	$('#menu-order-userList').addClass('active');
});
</script>

</head>
<body>
	<%@ include file="/views/headerNav.jsp"%>
	<div style="width: 90%; margin: 50px;">
		<table class="table table-striped">
    <thead>
      <tr>
        <th>User</th>
        <th>Product</th>
        <th>Price</th>
        <th>Quantity</th>
      </tr>
    </thead>
    <tbody>

		<c:forEach var="o" items="${orders}">
			<tr>
			<td>${o.user}</td>
			<td>${o.orderLines[0].product.productName}</td>
			<td>${o.orderLines[0].price}</td>
			<td>${o.orderLines[0].quantity}</td>
			</tr>
		</c:forEach>
		</tbody>
  </table>
	</div>
</body>
</html>