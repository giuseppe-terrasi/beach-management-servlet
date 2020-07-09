<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
    <jsp:attribute name="scripts">
    	<script>
	        function saveReview() {
	            var form = document.getElementById('reviewForm');
	           
	            var data = new FormData(form);
	
	            $.ajax({
	
	                url : '/BeachManagement/saveReview',
	                type : 'POST',
	                data : data,
	                dataType:'html',
	                cache: false,
	                processData: false,
	                contentType: false,
	                success : function(data, status, xhr) {
	                	if(xhr.status == 201) {
	                		location.reload();
	                	}
	                	else {
	                		$('#reviewFormDiv').html(data);	
	                	}
	                },
	                error : function(request,error)
	                {
	                    console.log("error", error);
	                }
	            });
	        }
    	</script>
    </jsp:attribute>
    <jsp:body>
        <h1>Umbrella status at ${now}</h1>
        
        <div class="row">
            <div class="col-12">
                <table class="umbrella-grid table table-bordered mt-5" id="umbrella-grid-table">
                    <c:forEach items="${grid}" var="row">
                        <tr id="umbrella-grid-row-${row.getKey()}">
                            <c:forEach items="${row.getValue()}" var="column">
                                <c:set var="column" value="${column}" scope="request"/>
                                <c:import url="fragments/grid/homeGridTd.jsp"/>
                            </c:forEach>
                        </tr>    
                    </c:forEach>
                </table>
            </div>
        </div>
        
        <h2>Reviews</h2>
        
       	<div class="row">
       		<ul class="list-group list-group-horizontal reviews">
				<c:forEach items="${reviews}" var="review">
		        	<li class="list-group-item">
			        	<div class="card" style="width: 18rem;">
						  <div class="card-body">
						  	<h5 class="card-title">${review.score}</h5>
						    <h5 class="card-title">${review.title}</h5>
						    <p class="card-text">${review.reviewedOn}</p>
						    <p class="card-text">${review.username}</p>
						    <p class="card-text">${review.message}</p>
						  </div>
						</div>
		        	</li>
	        	</c:forEach>
       		</ul>
        </div>
        <div class="row mt-3" >
			<div class="col-12" id="reviewFormDiv">
				<c:import url="fragments/review/reviewForm.jsp"/>
			</div>
        </div>
    </jsp:body>
</t:maintemplate>