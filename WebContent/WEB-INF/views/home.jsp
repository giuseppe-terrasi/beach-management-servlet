<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
    <jsp:body>
        Umbrella status at ${now}
        
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
        
    </jsp:body>
</t:maintemplate>