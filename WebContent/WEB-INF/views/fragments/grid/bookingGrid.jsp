<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <table class="umbrella-grid table table-bordered mt-5" id="umbrella-grid-table">
     <c:forEach items="${grid}" var="row">
         <tr id="umbrella-grid-row-${row.getKey()}">
             <c:forEach items="${row.getValue()}" var="column">
				<td>
				    ${column.getGridRow()} x ${column.getGridColumn()} 
				    <c:choose>
				    	<c:when test="${column.isAvailable()}">
				    		<input type="checkbox" name="grid-id" value="${column.id}" />
				    	</c:when>
				    	<c:otherwise>
				    		Non disponibile
				    	</c:otherwise>
				    </c:choose>
				</td>
             </c:forEach>
         </tr>    
     </c:forEach>
 </table>