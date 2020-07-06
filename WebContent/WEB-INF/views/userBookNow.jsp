<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
    <jsp:attribute name="styles">
        <link href="<c:url value="/vendors/datetimepicker/build/jquery.datetimepicker.min.css"/>" rel="stylesheet" type="text/css"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="<c:url value="/vendors/datetimepicker/build/jquery.datetimepicker.full.min.js"/>"></script>
        <script>
            $( function() {
                $( ".datetimepicker" ).datetimepicker({
                    allowTimes:[
                    '08:00', '14:00', '20:00' 
                    ]
                });
            });
            
            function getGrid() {
            	$('#bookBtnRow').hide();
            	
            	var fromDate = $('#dateFrom').val();
            	var toDate = $('#dateTo').val();
            	var dataObj = {
            		fromDate: fromDate,
            		toDate: toDate
            	};
            	
            	
            	$.ajax({
            	    type: 'POST',
            	    url: '/BeachManagement/getBookings',
            	    dataType: 'JSON',
            	    dataType:'html',
            	    data:  {
            	    	data: JSON.stringify(dataObj)
            	    },
            	    success: function(revicedData) {
            	    	$('#bookingTable').html(revicedData);
            	    	$('#bookBtnRow').show();
            	    },
            	    error: function(data) {
            	        console.log("error")
            	    }
            	});	
			}
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Book umbrella</h2>

		<form action="<c:url value="/book"/>" method="post">
	        <div class="row mt-5">
	            <div class="col-4">
	                <div class="form-group">
	                    <label>From</label>
	                    <input type="text" class="form-control datetimepicker" name="dateFrom" id="dateFrom" readonly="readonly">
	                </div>
	            </div>
	            <div class="col-4">
	                <div class="form-group">
	                    <label>To</label>
	                    <input type="text" class="form-control datetimepicker" name="dateTo" id="dateTo" readonly="readonly">
	                </div>
	            </div>
	            <div class="col-4">
	                <div class="form-group">
	                    <label style="min-height: 17.23px;"></label>
	                    <button class="btn btn-primary form-control" type="button" onclick="getGrid()">Find</button>
	                </div>
	            </div>
	        </div>
	        <div class="row">
	        	<div class="col-12" id="bookingTable">
	        	</div>
	        </div>
	        <div class="row" id="bookBtnRow" style="display: none">
	        	<div class="col-4">
	        		<button class="btn btn-primary" type="submit">Book</button>
	        	</div>
	        </div>
        </form>
    </jsp:body>
</t:maintemplate>