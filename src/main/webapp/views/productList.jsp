<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/headerScript.jsp"%>
<script type="text/javascript">
$(document).ready(function () {
   $('#menu-product-list').addClass('active');
});
</script>

</head>
<body>
	<%@ include file="/views/headerNav.jsp"%>
	<div style="width: 90%; margin: 50px;">
		<sec:authorize access="hasRole('ADMIN')">
		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info" data-toggle="modal"
			data-target="#myModal">Add Product</button>
		<p></p>
		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Product</h4>
					</div>
					<div class="modal-body">
						<form action="/product/add" method="post">
							<div class="form-group">
							<label>Product Name:</label>
							<input type="text" name="productName" class="form-control"> 
							</div>
							<div class="form-group">
							<label>Price:</label>
							<input type="text"name="price" class="form-control"> 
							</div>
							<div class="form-group">
							<label>Description:</label>
							<textarea class="form-control" rows="5" name="description"></textarea>
							</div>
							
							<button type="submit" class="btn btn-default">Add</button>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>	
		</sec:authorize>

		<c:forEach var="p" items="${products}">
			<div class="panel panel-default">
				<div class="panel-heading">
					<strong>${p.productName} </strong>
				</div>
				<div class="panel-body">
					<p>Name:${p.productName}</p>
					<p>Price:$${p.price}</p>
					<p>Description:${p.description}</p>
					<a href="/order/add/${p.id}" class="btn btn-info" role="button">Buy</a>
					<sec:authorize access="hasRole('ADMIN')">
						<a href="/product/delete/${p.id}" class="btn btn-danger"
							role="button">Delete Product</a>
					</sec:authorize>
				</div>

			</div>
		</c:forEach>
	</div>
</body>
</html>