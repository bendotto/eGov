/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

function createNewSubTypeOfWork() {
	window.location = "/egworks/masters/subtypeofwork-newform";
}

$(document).ready(function(){
	var parentId = $('#parentId').val();
	if (parentId != "") {
		$('#typeofwork option').each(function() {
			if ($(this).val() == parentId)
				$(this).attr('selected', 'selected');
		});
	}

});

$('#typeofwork').blur(function(){
 if ($('#typeofwork').val() === '') {
	   $('.typeofworkparams').empty();
	   $('.typeofworkparams').append($('<option>').text('Select from below').attr('value', ''));
		return;
		} else {
		$.ajax({
			type: "GET",
			url: "/egworks/lineestimate/getsubtypeofwork",
			cache: true,
			dataType: "json",
			data:{'id' : $('#typeofwork').val()}
		}).done(function(value) {
			console.log(value);
			$('.typeofworkparams').empty();
			$('.typeofworkparams').append($("<option value=''>Select from below</option>"));
			$.each(value, function(index, val) {
				var selected="";
			    $('#subtypeOfWorkName').append($('<option '+ selected +'>').text(val.name).attr('value', val.name));
			    $('#subtypeOfWorkCode').append($('<option '+ selected +'>').text(val.code).attr('value', val.code));
			});
		});
	}
});
$('#btnsearch').click(function(e) {
	callAjaxSearch();
});

function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
			.dataTable({
				ajax : {
					url : "/egworks/masters/ajaxsearch-subtypeofwork",
					type : "POST",
					"data" : getFormData(jQuery('form'))
				},
				"bDestroy" : true,
				'bAutoWidth' : false,
				"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
				"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
				"oTableTools" : {
					"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
					"aButtons" : []
				},
				"fnRowCallback" : function(row, data, index) {
					$('td:eq(0)', row).html(index + 1);
					$('td:eq(5)', row).html(
							'<a href="javascript:void(0);" onclick="openSubTypeOfWork(\''
									+ data.id + '\')">Modify</a>');
					return row;
					
				},
				aaSorting : [],
				columns : [ {
					"data" : "",
					"sClass" : "text-center","sWidth": "1%"
				}, {
					"data" : "code",
					"sWidth": "15%"
				}, {
					"data" : "name",
					"sWidth": "10%"
				}, {
					"data" : "description",
					"sWidth": "20%"
				},{
					"data" : "parentid",
					"sWidth": "20%"
				}, {
					"data" : "",
					"sClass" : "text-center","sWidth": "10%"
				}
			] });
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}


function openSubTypeOfWork(subTypeOfWorkId) {
	window.location = "/egworks/masters/subtypeofwork-update/"+subTypeOfWorkId;
}