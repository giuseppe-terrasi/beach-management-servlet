<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:maintemplate>
    <jsp:attribute name="styles">
        <link href="<c:url value="/vendors/datatables/dataTables.bootstrap4.css" />" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="<c:url value="/vendors/datatables/jquery.dataTables.min.js" />"></script>
        <script src="<c:url value="/vendors/datatables/dataTables.bootstrap4.min.js" />"></script>
        <script>
	        $(document).ready(function() {
	        	$('#bookingTable').DataTable();
	        });
        </script>
    </jsp:attribute>
    <jsp:body>
    
   	<div class="row">
   		<div class="col-12 table-responsive">
   			<table class="table" id="bookingTable">
   				<thead>
   					<tr>
   						<th>Id</th>
   						<th>From</th>
   						<th>To</th>
   						<th>Number of persons</th>
   						<th>Grid position</th>
   						<th>Price</th>
   					</tr>
   				</thead>
                <c:forEach items="${bookings}" var="booking">
                    <tr>
                    	<td>${booking.id}</td>
                    	<td>
                    		<fmt:formatDate value="${booking.from}" pattern="dd/MM/yyyy HH:mm" />
                    	</td>
                    	<td>
                    		<fmt:formatDate value="${booking.to}" pattern="dd/MM/yyyy HH:mm" />	
                    	</td>
                    	<td>${booking.numberOfPersons}</td>
                    	<td>
							<c:forEach items="${booking.umbrellas}" var="umbrella" varStatus="loop">
								${umbrella.gridRow} x ${umbrella.gridColumn} <c:if test="${loop.index < booking.umbrellas.size() - 1}"> - </c:if>
                        	</c:forEach>
                    	</td>
                    	<td>
                    		${booking.price} &euro;
                    	</td>
                    </tr>    
                </c:forEach>
   			</table>
   		</div>
   	</div>
        
    </jsp:body>
</t:maintemplate>