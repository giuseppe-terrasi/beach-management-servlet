<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<td>
    ${column.getGridRow()} x ${column.getGridColumn()} 
    <c:choose>
    	<c:when test="${column.isAvailable()}">
    		Disponibile
    	</c:when>
    	<c:otherwise>
    		Non disponibile
    	</c:otherwise>
    </c:choose>
</td>