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
	$('#menu-person').addClass('active');
});
</script>
</head>
<body>
	<%@ include file="/views/headerNav.jsp"%>

	<div style="width: 90%; margin: 50px;">
		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info" data-toggle="modal"
			data-target="#myModal">Add Person</button>

		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Person</h4>
					</div>
					<div class="modal-body">
						<form action="/person/add" method="post">
							<div class="form-group">
							<label>Email:</label>
							<input type="text" name="email" class="form-control"> 
							</div>
							<div class="form-group">
							<label>FirstName:</label>
							<input type="text"name="firstName" class="form-control"> 
							</div>
							<div class="form-group">
							<label>LastName:</label>
							<input type="text" name="lastName" class="form-control">
							</div>
							<div class="form-group">
							<label>City:</label>
							<input type="text" name="city" class="form-control"> 
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
        <!-- Display Person Table-->
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Email</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>city</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${persons}">
					<tr>
						<td>${p.email}</td>
						<td>${p.firstName}</td>
						<td>${p.lastName}</td>
						<td>${p.address.city}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>