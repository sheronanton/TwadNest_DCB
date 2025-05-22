var xmlHttp;
function insert_benval()
{
	var flag=0;
	var flag1=val_check('location','Enter Location Value');
	var flag2=val_check('capacity','Enter Capacity Value');
	var flag3=val_check('quantity','Enter Quantity Value');
	var sno = document.getElementById('snoid').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var location = document.getElementById('location').value;
	var capacity = document.getElementById('capacity').value;
	var quantity = document.getElementById('quantity').value;
	xmlHttp = createObject();
	var url = "";
	if(flag1==0 && flag2==0 && flag3==0)
	{
	url = "../../../../../LocBeneficiaries?comment=add&sno=" + sno + "&comp=" + comp
			+ "&sub_comp=" + sub_comp + "&location=" + location + "&capacity=" + capacity
			+ "&quantity=" + quantity +"&t="+Math.random();
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() {
		insert_process(xmlHttp);
	}
	xmlHttp.send(null);
	}

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
			view_benval();
		//window.location.reload();
		clear();
		
	} else {
		alert("Record Not Saved ");
	}
}
function view_benval()
{
	var sno=document.getElementById("snoid").value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var location = document.getElementById('location').value;
	var capacity = document.getElementById('capacity').value;
	var quantity = document.getElementById('quantity').value;
	var location_no = document.getElementById('locno_id').value;
	xmlHttp = createObject();
		var url = "";
		url = "../../../../../LocBeneficiaries?comment=view&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp +"&loc="+location+ "&cap="+ capacity + "&qty=" + quantity +"&t="+Math.random();   
		xmlHttp.open("GET", url, true);
		 xmlHttp.onreadystatechange = function() {	
			view_process(xmlHttp);
		 } 
		xmlHttp.send(null);
	}

	function view_process(xmlHttp) {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				view_result_ben(xmlHttp);
			}
		}
	}

	function view_result_ben(xmlHttp) {
		
		var tbody = document.getElementById("tblList");
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--)
		{
		
			tbody.deleteRow(0);
		}	
		
		var bR=xmlHttp.responseXML.getElementsByTagName("response")[0];
	    var len=xmlHttp.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		for ( var i = 0; i < len; i++) {
			var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
			var PMS_ASSET_SR_SNO = xmlValue(bR, "PMS_ASSET_SR_SNO", i);
			var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
			var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
			var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
			var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
			var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
			
			var LOCATION = xmlValue(bR, "LOCATION", i);
			var CAPACITY = xmlValue(bR, "CAPACITY", i);
			var QTY = xmlValue(bR, "QTY", i);
			var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
			var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
			var LOCATION_NO = xmlValue(bR, "LOCATION_NO", i);
			
			//function cell(major,item,type,name,value,size,sclass,url,istyle,tdwidth,tdalign,funtype,funname)
			var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_SR_SNO"	+ (i + 1), PMS_ASSET_SR_SNO, 2, "tdText", "", "", "2%", "", "","");				
			var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
			var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
			var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
			var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
			var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
			var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
			//var bentype_cell = cell("TD","input","hidden", "BENEFICIARY_TYPE_ID" + (i + 1), BENEFICIARY_TYPE_ID,	2, "", "", "", "", "", "", "");					
			//var ben_sno_cell = cell("TD", "input", "hidden", "BENEFICIARY_SNO" + (i + 1), BENEFICIARY_SNO,	2, "", "", "", "", "", "", "");			
			var loc_cell = cell("TD", "label", "", "LOCATION" + (i + 1),LOCATION, 2, "", "", "", "", "", "", "");
			var cap_cell = cell("TD", "label", "", "CAPACITY" + (i + 1),CAPACITY, 2, "", "", "", "", "", "", "");				
	        var qty_cell = cell("TD", "label", "", "QTY" + (i + 1), QTY,	2, "", "", "", "", "", "", "");		
			var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
			var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
			var to_cell = cell("TD", "input", "hidden", "LOCATION_NO" + (i + 1), LOCATION_NO,	2, "", "", "", "", "", "", "");					
			//var ben_button = cell("TD", "A", "Add_Beneficiaries", "Add_Beneficiaries", "Add_Beneficiaries", 10, "",	"", "", "5%", "", "", "");			
			var ben_button = cell("TD", "A", "Add_Beneficiaries", "Add_Beneficiaries", "Add_Beneficiaries", 30, "",	"javascript:tech1(" + (i + 1) + ")", "", "5%", "", "", "");			
			 
			new_row.appendChild(sno_cell);
			new_row.appendChild(href_cell);
			new_row.appendChild(loc_cell);
			new_row.appendChild(cap_cell);
			new_row.appendChild(to_cell);
			new_row.appendChild(qty_cell);
			new_row.appendChild(ben_button);
			tbody.appendChild(new_row);
		}
	
	}
	function delete_loc() {
		var sno = document.getElementById("sid").value;
		var url = "";
		xmlHttp = createObject();
		url = "../../../../../LocBeneficiaries?comment=pop&sno=" + sno +"&t="+Math.random();
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
		var msg = BR.getElementsByTagName("rows")[0].firstChild.nodeValue;

		if (msg > 0) {

			alert("Deleted Successfully");
			view_benval();
			clear();
		} else {
			alert("fail");
		}
	}
	function update_benval(){
		var sno=document.getElementById("sid").value;
		var comp=document.getElementById("com_id").value;
		var sub_comp=document.getElementById("subcom_id").value;
		var loc=document.getElementById("location").value;
		var cap=document.getElementById("capacity").value;
		var qty=document.getElementById("quantity").value;
		xmlHttp = createObject();
		var url = "";
		url = "../../../../../LocBeneficiaries?comment=modify&sno="+sno+"&comp="+comp+"&sub_comp=" + sub_comp + "&loc="+loc+ "&cap="+cap+"&qty=" + qty+"&t="+Math.random();
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
			view_benval();
			clear();
		} else {
			alert("fail");
		}
	}
function clear()
{
	enabledtb('save');
	document.getElementById("location").value="";
	document.getElementById("capacity").value="";
	document.getElementById("quantity").value="";
}
