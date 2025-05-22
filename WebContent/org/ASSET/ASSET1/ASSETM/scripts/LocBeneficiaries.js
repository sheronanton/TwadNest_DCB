function addrow(row,c)
{
	var tbody = document.getElementById("c"+row) ;
	var rowc=document.getElementById("rowc"+row).value;
	var next=parseInt(rowc)+1;
	var new_row = cell("TR", "", "", "row",0, 2, "", "","", "", "", "", "");
	var loc_cell = cell("TD", "input", "text", "loc"+row+""+next,"", 20, "tdText", "", "", "20%", "", "","");				
	var cap_cell = cell("TD", "input", "text", "cap"+row+""+next,"", 7, "tdText", "", "", "10%", "", "","");				
	var qty_cell = cell("TD", "input", "text", "qty"+row+""+next,"", 7, "tdText", "", "", "10%", "", "","");				

	new_row.appendChild(loc_cell);
	new_row.appendChild(cap_cell);
	new_row.appendChild(qty_cell);
	tbody.appendChild(new_row);
	document.getElementById("rowc"+row).value=next;
	

}
function add(row,c){

	var no=document.getElementById("rowc"+row).value;
	var sno=document.getElementById("sid").value;
	var comp=document.getElementById("comp_id").value;
	var sub_comp=document.getElementById("sub_id").value;
	var ben_sno=document.getElementById("ben_sno"+row).value;
	var ben_id=document.getElementById("ben_id"+row).value;
	
	xmlHttp = createObject();
	var url = "";var i;
	url = "../../../../../LocBeneficiaries?comment=add&row="+row+"&ben_sno="+ben_sno+"&sno=" + sno + "&comp=" + comp
	+ "&ben_id=" + ben_id+ "&sub_comp=" + sub_comp +  "&no=" + no +"&t="+Math.random();
	for( i=1;i<=no;i++)
	{
		var loc=document.getElementById("loc"+row+""+i).value;
		var cap=document.getElementById("cap"+row+""+i).value;
		var qty=document.getElementById("qty"+row+""+i).value;
	
	 url+=  "&loc"+row+""+i+"="+loc+ "&cap"+row+""+i+"=" + cap + "&qty"+row+""+i+"=" + qty ;
                      
	}  
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() {
		insert_process(xmlHttp);
	}
	xmlHttp.send(null);
}

function insert_process(xmlHttp) {
	if (xmlHttp.readyState == 4) {

		if (xmlHttp.status == 200) {
			insert_result(xmlHttp);
		}
	}
}
function insert_result(xmlHttp) {
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var msg = BR.getElementsByTagName("msg")[0].firstChild.nodeValue;
	if (msg > 0) {
		alert("Record Saved Successfully");	
		window.location.reload();
		
	} else {
		alert("Record Not Saved ");
	}
}

	function update(row,c){
		var no=document.getElementById("rowc"+row).value;
		var sno=document.getElementById("sid").value;
		var comp=document.getElementById("comp_id").value;
		var sub_comp=document.getElementById("sub_id").value;
		var ben_sno=document.getElementById("ben_sno"+row).value;
		var ben_id=document.getElementById("ben_id"+row).value;
		var prs_sno=0;
		xmlHttp = createObject();
		var url = "";

		url = "../../../../../LocBeneficiaries?comment=modify&row="+row+"&ben_sno="+ben_sno+"&ben_id="+ben_id+
	     "&c="+c+"&sub_comp=" + sub_comp +  "&no=" + no +"&t="+Math.random();
		
			var loc=document.getElementById("loc"+row+""+c).value;
			var cap=document.getElementById("cap"+row+""+c).value;
			var qty=document.getElementById("qty"+row+""+c).value;
			prs_sno= document.getElementById("prs_sno"+row+""+c).value;
		 url+=  "&loc="+loc+ "&cap="+cap+"&prs_sno="+prs_sno+"&qty=" + qty ;
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = function() {
			update_process(xmlHttp);
		}
		
		xmlHttp.send(null);
	}  
	function update_process(xmlHttp) {
		if (xmlHttp.readyState == 4) 
		{
			if (xmlHttp.status == 200) {
				update_result(xmlHttp);
			}
		}  
	}
	function update_result(xmlHttp) {
		var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
		var msg = BR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		if (msg > 0) {
			alert("Updated Successfully");
			
		} else {
			alert("fail");
		}
	}

	
	function view_ben(row,c)
	{

		var no=document.getElementById("rowc"+row).value;
		var sno=document.getElementById("sid").value;
		var comp=document.getElementById("comp_id").value;
		var sub_comp=document.getElementById("sub_id").value;
		var ben_sno=document.getElementById("ben_sno"+row).value;
		var ben_id=document.getElementById("ben_id"+row).value;
		xmlHttp = createObject();
			var url = "";

			url = "../../../../../LocBeneficiaries?comment=view&row="+row+"&ben_sno="+ben_sno+"&ben_id="+ben_id
	
		+ "&sub_comp=" + sub_comp +  "&no=" + no +"&t="+Math.random();   
			for( i=1;i<=no;i++)
			{
				var loc=document.getElementById("loc"+row+""+i).value;
				var cap=document.getElementById("cap"+row+""+i).value;
				var qty=document.getElementById("qty"+row+""+i).value;
			
			 url+=  "&loc"+row+""+i+"="+loc+ "&cap"+row+""+i+"=" + cap + "&qty"+row+""+i+"=" + qty ;
		                      
			}  
			 xmlHttp.open("GET", url, true);
			 xmlHttp.onreadystatechange = function() {
				view_process(xmlHttp);
			 } 
			xmlHttp.send(null);
		}

		function view_process(xmlHttp) {
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					view_result(xmlHttp);
				}
			}
		}

		function view_result(xmlHttp) {
			var tbody = document.getElementById("tblList");
			var t=0;
			for(t=tbody.rows.length-1;t>=0;t--)
			{
			
				tbody.deleteRow(0);
			}	
			
			var bR=xmlHttp.responseXML.getElementsByTagName("response")[0];
		    var len=xmlHttp.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			for ( var i = 0; i < len; i++) {
				//function cell(major,item,type,name,value,size,sclass,url,istyle,tdwidth,tdalign,funtype,funname)

				var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
				var PMS_ASSET_SR_SNO = xmlValue(bR, "PMS_ASSET_SR_SNO", i);
				var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
				var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
				var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
				var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
				var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
				var BENEFICIARY_TYPE_ID = xmlValue(bR, "BENEFICIARY_TYPE_ID", i);
				var BENEFICIARY_SNO = xmlValue(bR, "BENEFICIARY_SNO", i);
				var LOCATION = xmlValue(bR, "LOCATION", i);
				var CAPACITY = xmlValue(bR, "CAPACITY", i);
				var QTY = xmlValue(bR, "QTY", i);
				var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
				var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
				var LOCATION_TO = xmlValue(bR, "LOCATION_TO", i);
			
				var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_SR_SNO"	+ (i + 1), PMS_ASSET_SR_SNO, 2, "tdText", "", "", "2%", "", "","");				
				var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
				var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
				var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
				var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
				var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
				var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
				var bentype_cell = cell("TD","input","hidden", "BENEFICIARY_TYPE_ID" + (i + 1), BENEFICIARY_TYPE_ID,	2, "", "", "", "", "", "", "");					
				var ben_sno_cell = cell("TD", "input", "hidden", "BENEFICIARY_SNO" + (i + 1), BENEFICIARY_SNO,	2, "", "", "", "", "", "", "");			
				var loc_cell = cell("TD", "label", "", "LOCATION" + (i + 1),LOCATION, 2, "", "", "", "", "", "", "");
				var cap_cell = cell("TD", "label", "", "CAPACITY" + (i + 1),CAPACITY, 2, "", "", "", "", "", "", "");				
		        var qty_cell = cell("TD", "label", "", "QTY" + (i + 1), QTY,	2, "", "", "", "", "", "", "");		
				var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
				var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
				var to_cell = cell("TD", "label", "", "LOCATION_TO" + (i + 1), LOCATION_TO,	2, "", "", "", "", "", "", "");					
			     
				new_row.appendChild(sno_cell);
				new_row.appendChild(href_cell);
				new_row.appendChild(loc_cell);
				new_row.appendChild(cap_cell);
				new_row.appendChild(to_cell);
				new_row.appendChild(qty_cell);
				tbody.appendChild(new_row);
			}
		}
		function delete_loc(row,c) {
			var sno = document.getElementById("sid").value;
			var no=document.getElementById("rowc"+row).value;
			var url = "";
			xmlHttp = createObject();
			url = "../../../../../LocBeneficiaries?comment=pop&sno=" + sno +"&no="+no+ "&row="+row+"&c="+c+"&t="+Math.random();
			//alert(url);
			xmlHttp.open("GET", url, true);
			xmlHttp.onreadystatechange = function() {
				data_process(xmlHttp);
			}
			xmlHttp.send(null);
		}
		function data_process(xmlHttp) {
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					data_result(xmlHttp);
				}
			}
		}
		function data_result(xmlHttp) {
			var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
			var msg = BR.getElementsByTagName("msg")[0].firstChild.nodeValue;

			if (msg > 0) {

				alert("Deleted Successfully");
			} else {
				alert("fail");
			}
		}
		function validateNumber(id){
			var field = id;
			var valo = new String();
			var numere = "0123456789";
			var chars = field.value.split("");
			for (i = 0; i < chars.length; i++){
				if (numere.indexOf(chars[i]) != -1) 
					valo += chars[i];
				else{
					alert("Numbers Only Allowed !");
				}
			}
			if (field.value != valo) field.value = valo; 
		}

		/**function clear(row,i)
		
		{int i=1;
			var loc=document.getElementById("loc"+row+""+i).value;
			var cap=document.getElementById("cap"+row+""+i).value;
			var qty=document.getElementById("qty"+row+""+i).value;
		}**/
