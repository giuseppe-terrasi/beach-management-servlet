<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h5>Leave a review</h5>
<form id="reviewForm">
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
  		<input type="text" name="title" class="form-control" value="${review.title}" />
  		<span class="text-danger">${review.errors.get("title")}</span>
  	</div>
  	<div class="form-group">
  		<label>Message</label>
  		<textarea rows="3" name="message" class="form-control">${review.message}</textarea>
  		<span class="text-danger">${review.errors.get("message")}</span>
  	</div>
  	<button class="btn btn-primary" type="button" onclick="saveReview()">Save</button>
</form>