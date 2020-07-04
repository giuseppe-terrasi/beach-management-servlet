<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:maintemplate>
    <jsp:attribute name="scripts">
        <script>
            var selectedRowNumber = 0;
            var selectedColumnNumber = 0;

            $(document).ready(function() {
                $('.addUmbrellaBtn').on('click', newUmbrellaBtnClick)
            });


            function newUmbrellaBtnClick() {
                selectedRowNumber = parseInt($(this).attr('data-rowid'), 10);
                selectedColumnNumber = parseInt($(this).attr('data-columnid'), 10);
                $('#addUmbrellaForm-rowId').val(selectedRowNumber);
                $('#addUmbrellaForm-columnId').val(selectedColumnNumber);
                $('#addUmbrellaModal').modal('toggle');
            }

            function onAddUbrellaSuccess(dataRecived) {
                 var newRow = '';

                if(selectedColumnNumber == 1) {
                    newRow = $('#umbrella-grid-table tr:last-child').clone().prop('id', 'umbrella-grid-row-' + (selectedRowNumber + 1))
                    $(newRow).find('button').attr('data-rowid', selectedRowNumber + 1);
                }

                $(dataRecived).insertBefore('#umbrella-grid-row-' + selectedRowNumber + ' td:last-child')
                $('[data-rowid="' + selectedRowNumber + '"][data-columnid="' + selectedColumnNumber + '"]')
                .attr('data-columnid', selectedColumnNumber + 1);

                if(newRow !== '') {
                    $(newRow).insertAfter   ('#umbrella-grid-table tr:last-child');
                    $(newRow).find('td').on('click', newUmbrellaBtnClick);
                }
                
                $('#addUmbrellaModal').modal('toggle');
                
                document.getElementById('addUmbrellaForm').reset();
            }

            function addUmbrella() {
                var form = document.getElementById('addUmbrellaForm');
               
                var data = new FormData(form);

                $.ajax({

                    url : '/BeachManagement/admin/umbrellagrid',
                    type : 'POST',
                    data : data,
                    dataType:'html',
                    cache: false,
                    processData: false,
                    contentType: false,
                    success : onAddUbrellaSuccess,
                    error : function(request,error)
                    {
                        console.log("error", error);
                    }
                });
            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Umbrella grid</h2>
        <div class="row">
            <div class="col-12">
                <table class="umbrella-grid table table-bordered mt-5" id="umbrella-grid-table">
                    <c:forEach items="${grid}" var="row">
                        <tr id="umbrella-grid-row-${row.getKey()}">
                            <c:forEach items="${row.getValue()}" var="column">
                                <c:set var="column" value="${column}" scope="request"/>
                                <c:import url="../fragments/grid/gridTd.jsp"/>
                            </c:forEach>
                            <td>
                                <button data-rowid="${row.getKey()}" data-columnid="${row.getValue().size() + 1}" class="btn btn-primary addUmbrellaBtn">
                                +
                                </button>
                            </td>
                        </tr>    
                    </c:forEach>
                    <tr id="umbrella-grid-row-${grid.size() + 1}" data-newrow="true">
                        <td>
                            <button data-rowid="${grid.size() + 1}" data-columnid="1" class="btn btn-primary addUmbrellaBtn">
                            +
                            </button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <!-- Add umbrella modal -->
        <div class="modal fade" id="addUmbrellaModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add umbrella</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addUmbrellaForm">
                        <%-- <div class="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Row</label>
                                    <input class="form-control" type="number" name="gridRow" />
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Column</label>
                                    <input class="form-control" type="number" name="gridColumn" />
                                </div>
                            </div>
                        </div> --%>
                        <input class="form-control" type="hidden" name="gridRow" id="addUmbrellaForm-rowId" value="0"/>
                        <input class="form-control" type="hidden" name="gridColumn" id="addUmbrellaForm-columnId" value="0"/>
                        <div class="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Capacity</label>
                                    <input class="form-control" type="number" name="capacity" />
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <label>Price</label>
                                    <input class="form-control" type="number" name="price" />
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addUmbrella()">Save changes</button>
                </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:maintemplate>