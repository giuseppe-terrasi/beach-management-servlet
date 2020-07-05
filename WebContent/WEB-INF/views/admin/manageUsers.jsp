<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
    <jsp:attribute name="styles">
        <link href="<c:url value="/vendors/datatables/dataTables.bootstrap4.css" />" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="<c:url value="/vendors/datatables/jquery.dataTables.min.js" />"></script>
        <script src="<c:url value="/vendors/datatables/dataTables.bootstrap4.min.js" />"></script>
        <script>
	        $(document).ready(function() {
	        	$('#userTable').DataTable();
	        });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2 class="mb-5">Manage users</h2>
        <div class="row">
        	<div class="col-12 table-responsive">
      	        <table class="table table-striped" id="userTable">
		            <thead>
		                <tr>
		                    <th>Id</th>
		                    <th>Username</th>
		                    <th>First name</th>
		                    <th>Last name</th>
		                    <th>Roles</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${users}" var="row">
		                    <tr>
		                        <td>${row.getId()}</td>
		                        <td>${row.getUsername()}</td>
		                        <td>${row.getFirstName()}</td>
		                        <td>${row.getLastName()}</td>
		                        <td>${row.getRolesString()}</td>
		                    </tr>
		                </c:forEach>
		            </tbody>
		        </table>
        	</div>
        </div>
    </jsp:body>
</t:maintemplate>