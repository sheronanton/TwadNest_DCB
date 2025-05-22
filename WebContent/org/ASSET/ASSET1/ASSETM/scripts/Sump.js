var xmlHttp;
function addition_sump() {
	 
	var flag1=val_check('capacity','Enter Capacity Value');
	var flag2=val_check('dim','Enter diameter Value');
	var flag3=val_check('depth','Enter Depth Value');
	var flag4=val_check('detention','Enter detention Value');
	var sno = document.getElementById('sid').value;
	var loc = document.getElementById('loc').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var capacity = document.getElementById('capacity').value;
	var dim = document.getElementById('dim').value;
	var depth = document.getElementById('depth').value;
	var detention = document.getElementById('detention').value;
  
	xmlHttp = createObject();
	if (flag1==0 && flag2==0 && flag3==0 && flag4==0 )
	{
		var url = "";
		url = "../../../../../Sump?comment=add&sno=" + sno + "&comp=" + comp
				+ "&sub_comp=" + sub_comp + "&loc=" + loc + "&capacity=" + capacity
				+ "&dim=" + dim + "&depth=" + depth + "&detention=" + detention+"&t="+Math.random();
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
	var row = BR.getElementsByTagName("rows")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Record Saved Successfully");
		display_sump();
		clear();

	} else {
		alert("fail");
	}
}
function display_sump() {  
	 
	 xmlHttp = createObject();
	 var sno = document.getElementById('sid').value;
		var loc = document.getElementById('loc').value;
		var comp = document.getElementById('com_id').value;
		var sub_comp = document.getElementById('subcom_id').value;
		var capacity = document.getElementById('capacity').value;
		 
		var dim = document.getElementById('dim').value;
		var depth = document.getElementById('depth').value;
		var detention = document.getElementById('detention').value;
	
	var url = "";

	url = "../../../../../Sump?comment=view&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp+"&t="+Math.random();
  
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
	for ( var i = 0; i < len; i++) 
	{
		var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
		var PMS_ASSET_BOOSTER_SUMP_SNO = xmlValue(bR, "PMS_ASSET_BOOSTER_SUMP_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
		var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
		var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
		var CAPACITY = xmlValue(bR, "CAPACITY", i);
		var DEPTH = xmlValue(bR, "DEPTH", i);
		var DIAMETER = xmlValue(bR, "DIAMETER", i);
		var DETENTION = xmlValue(bR, "DETENTION", i);
		var LOCATION_FROM = xmlValue(bR, "LOCATION_FROM", i);
		var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
		var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
		var LOCATION_TO = xmlValue(bR, "LOCATION_TO", i);
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_BOOSTER_SUMP_SNO"	+ (i + 1), PMS_ASSET_BOOSTER_SUMP_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
		var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
		var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
		var capacity_cell = cell("TD", "label", "", "CAPACITY" + (i + 1), CAPACITY,	2, "", "", "", "", "", "", "");					
		var depth_cell = cell("TD", "label", "", "DEPTH" + (i + 1), DEPTH,	2, "", "", "", "", "", "", "");			
		var diam_cell = cell("TD", "label", "", "DIAMETER" + (i + 1),DIAMETER, 2, "", "", "", "", "", "", "");				
		var deten_cell = cell("TD", "label", "", "DETENTION" + (i + 1),DETENTION, 2, "", "", "", "", "", "", "");				
       var from_cell = cell("TD", "label", "", "LOCATION_FROM" + (i + 1), LOCATION_FROM,	2, "", "", "", "", "", "", "");		
		var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
		var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
		var to_cell = cell("TD", "input", "hidden", "LOCATION_TO" + (i + 1), LOCATION_TO,	2, "", "", "", "", "", "", "");					
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);
		new_row.appendChild(from_cell);
		new_row.appendChild(capacity_cell);
		
		new_row.appendChild(diam_cell);
		new_row.appendChild(depth_cell);
		new_row.appendChild(deten_cell);
		
		tbody.appendChild(new_row);
	}
}
function delete_sump() {
	var sno = document.getElementById("sid").value;
	var url = "";
	xmlHttp = createObject();
	url = "../../../../../Sump?comment=pop&sno=" + sno+"&t="+Math.random();
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
	var row = BR.getElementsByTagName("rows")[0].firstChild.nodeValue;

	if (row > 0) {

		alert("Deleted Successfully");
		clear();
		display_sump();
	} else {
		alert("fail");
	}
}
function update_sump() 
{
	var flag1=val_check('capacity','Enter Capacity Value');
	var flag2=val_check('dim','Enter diameter Value');
	var flag3=val_check('depth','Enter Depth Value');
	var flag4=val_check('detention','Enter detention Value');
	
	var sno = document.getElementById('sid').value;
	var loc = document.getElementById('loc').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var capacity = document.getElementById('capacity').value;
	var dim = document.getElementById('dim').value;
	var depth = document.getElementById('depth').value;
	var detention = document.getElementById('detention').value;
	xmlHttp = createObject();
	var url = ""; 

	if (flag1==0 && flag2==0 && flag3==0 && flag4==0 )
	{
		url = "../../../../../Sump?comment=modify&sno=" + sno + "&comp=" + comp
			+ "&sub_comp=" + sub_comp + "&loc=" + loc + "&capacity=" + capacity
			+ "&dim=" + dim + "&depth=" + depth + "&detention=" + detention+"&t="+Math.random();
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = function() {
			update_process(xmlHttp);
		}
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
function update_result(xmlHttp) 
{
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("rows")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Updated Successfully");
	clear();
		display_sump();
	} else {
		alert("fail");
	}
}
function clear()
{
	document.getElementById('loc').value = "";
	document.getElementById('com_id').value = "";
	document.getElementById('subcom_id').value = "";
	document.getElementById('capacity').value = "";
	document.getElementById('dim').value = "";
	document.getElementById('depth').value = "";
	document.getElementById('detention').value = "";
}
function val_check(id,msg)
{
	var field=document.getElementById(id).value; 
	
	if (field == null || field == "") 
	{
		alert(msg);
		document.getElementById(id).focus;
		return 1;
	}else
	{ 
		return 0;
		
	}
}
