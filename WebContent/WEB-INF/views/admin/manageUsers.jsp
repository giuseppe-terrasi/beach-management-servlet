<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
    <jsp:attribute name="styles">
        <link href="/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script>
            $('#userTable').DataTable();
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2 class="mb-5">Manage users</h2>
        <table class="table table-striped" id="userTable">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>First name</th>
                    <th>Last name</th>
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
    </jsp:body>
</t:maintemplate>