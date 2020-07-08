<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
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
        <div class="row mt-3">
			<div class="col-12">
				<h5>Leave a review</h5>
        		<form action="<c:url value="/saveReview" />" method="post">
		        	<div class="form-group">
		        		<label>Score</label>
   			        	<select name="score" class="form-control">
			        		<option value="1">1</option>
			        		<option value="2">2</option>
			        		<option value="3">3</option>
			        		<option value="4">4</option>
			        		<option value="5">5</option>
			        	</select>
		        	</div>
		        	<div class="form-group">
		        		<label>Title</label>
		        		<input type="text" name="title" class="form-control" />	
		        	</div>
		        	<div class="form-group">
		        		<label>Message</label>
		        		<textarea rows="3" name="message" class="form-control"></textarea>
		        	</div>
		        	<button class="btn btn-primary" type="submit">Save</button>
	        	</form>
			</div>
        </div>
    </jsp:body>
</t:maintemplate>