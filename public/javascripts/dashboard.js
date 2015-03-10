//Onclick event handlers for sidenav links and perform ajax request to fetch the dashboard data
$(document).ready(
		function() {
			
			$("#dashboard").click(
				function(event) {
					event.preventDefault();
					
					$.getJSON("/dashboard", function(data) {
						var dashboardData="";
						
						$("#homeSection").hide();
						$("#dashboardSection").show();
						$("div[name=customerOrderEntries]").remove();
						
						$.each(data, function(i, val) {
							console.log(i + " " + val);
						    if(i == "ordersPerCustomer") {
						    	$.each(val, function(index, item) {
						    		$.each(item, function(key, value) {
						    			//console.log(key + " " + value);
						    			var appendTableRow = "<div class='tableRow' name='customerOrderEntries'>" +
						    								 "<div class='tableColumn'>" + key + "</div>" +
						    								 "<div class='tableColumn'>" + value + "</div>" +
						    								 "</div>";
						    			$("#orderPerCustomerTable").append(appendTableRow);
						    		});
						    	});
						    }
						    else {
						    	$("#"+i).text(val);
						    }
						});
						
						
					})
				});
});