 
function addition_() 
{ 
	var flag=0; 
	flag =val_check('duty_type','Enter Duty value');
	flag =val_check('head','Enter Head value');
	flag =val_check('qty','Enter Quantity value');
	flag =val_check('qty','Enter Remark value');
	flag =val_check('htype','Enter Horse power value');
var sno = document.getElementById('sid').value;
var pump_type = document.getElementById('pump_type').value;
var comp = document.getElementById('com_id').value;
var sub_comp = document.getElementById('subcom_id').value;
var duty_type = document.getElementById('duty_type').value;
var head = document.getElementById('head').value;
var qty = document.getElementById('qty').value;
var remark = document.getElementById('reid').value;
var htype = document.getElementById('htype').value;

var xmlHttp = createObject();
var url = ""; 
    url = "../../../../../Pumpset?comment=add&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp + "&pump_type=" + pump_type + "&duty_type="
        + duty_type + "&head=" + head + "&qty=" + qty + "&remark=" + remark + "&htype=" + htype+"&t="+Math.random();
  xmlHttp.open("GET", url, true);	
  xmlHttp.onreadystatechange = function() 
  {
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
var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
if (row > 0) {
	alert("Record Saved Successfully");
	clear();
	
	display_();
} else {
	alert("fail");
}
}

function display_() {  
	var  xmlHttp2 = createObject();
	var sno = document.getElementById('sid').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var duty_type = document.getElementById('duty_type').value;
	var head = document.getElementById('head').value;
	var qty = document.getElementById('qty').value;
	var remark = document.getElementById('reid').value;
	var htype = document.getElementById('htype').value;
	
	
	var url = "";
	try
	{
		 url = "../../../../../Pumpset?comment=view&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp;
		 xmlHttp2.open("GET", url, true); 
	 
		 xmlHttp2.onreadystatechange= function()
            {
			 view_process(xmlHttp2) 
            }
		  xmlHttp2.send(null);
		 
	}catch(e)
	{
	
	}
}
function view_process(xmlHttp) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			view_result(xmlHttp);
		}
	}
}

function view_result(xmlHttp) {

	var tbody = document.getElementById('tblList');

	for (t = tbody.rows.length - 1; t >= 0; t--) {

		tbody.deleteRow(0);
	}
	var bR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var len = bR.getElementsByTagName("PMS_ASSET_HW_PS_SNO").length;
	for ( var i = 0; i < len; i++) 
	{
		var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
		var PMS_ASSET_HW_PS_SNO = xmlValue(bR, "PMS_ASSET_HW_PS_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
		var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
		var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
		var TYPE_OF_PUMPSET = xmlValue(bR, "TYPE_OF_PUMPSET", i);
		var DUTY = xmlValue(bR, "DUTY", i);
		var HEAD = xmlValue(bR, "HEAD", i);
		var QUANTITY = xmlValue(bR, "QUANTITY", i);
		var REMARKS = xmlValue(bR, "REMARKS", i);
		var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
		var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
		var HORSE_POWER = xmlValue(bR, "HORSE_POWER", i);
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_HW_PS_SNO"	+ (i + 1), PMS_ASSET_HW_PS_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
		var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
		var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
		var type_cell = cell("TD", "label", "", "TYPE_OF_PUMPSET" + (i + 1), TYPE_OF_PUMPSET,	2, "", "", "", "", "", "", "");					
		var duty_cell = cell("TD", "label", "", "DUTY" + (i + 1), DUTY,	2, "", "", "", "", "", "", "");			
		var head_cell = cell("TD", "label", "", "HEAD" + (i + 1),HEAD, 2, "", "", "", "", "", "", "");				
		var qty_cell = cell("TD", "label", "", "QUANTITY" + (i + 1),QUANTITY, 2, "", "", "", "", "", "", "");				
        var remark_cell = cell("TD", "label", "", "REMARKS" + (i + 1), REMARKS,	2, "", "", "", "", "", "", "");		
		var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
		var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
		var HP_cell = cell("TD", "label", "", "HORSE_POWER" + (i + 1), HORSE_POWER,	2, "", "", "", "", "", "", "");					
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);
		new_row.appendChild(type_cell);
		new_row.appendChild(HP_cell);
		new_row.appendChild(duty_cell);
		new_row.appendChild(head_cell);
		new_row.appendChild(qty_cell);
		
		new_row.appendChild(remark_cell);
		tbody.appendChild(new_row);
	}
}
function modify() {
	var sno = document.getElementById('sid').value;
	var pump_type = document.getElementById('pump_type').value;
	var duty_type = document.getElementById('duty_type').value;
	var head = document.getElementById('head').value;
	var qty = document.getElementById('qty').value;
	var remark = document.getElementById('reid').value;
	var htype = document.getElementById('htype').value;	
	var xmlHttp = createObject();
	var url = "";
	url = "../../../../../Pumpset?comment=modify&sno=" 
		    + sno+ "&pump_type=" + pump_type + "&duty_type="
			+ duty_type + "&head=" + head + "&qty=" + qty + "&remark=" + remark + "&htype=" + htype+"&t="+Math.random();
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
function update_result(xmlHttp) 
{
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Updated Successfully");
		clear();
		 display_();
	} else {
		alert("fail");
	}
}
function delete_val() {
	var sno = document.getElementById("sid").value;
	var url = "";
	var xmlHttp = createObject();
	url = "../../../../../Pumpset?comment=pop&sno=" + sno+"&t="+Math.random();
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
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Deleted Successfully");
		clear();
		 display_();
	} else {
		alert("fail");
	}
}

function clear() {
	document.getElementById('sid').value = "";
	document.getElementById('pump_type').value = "0";
	document.getElementById('duty_type').value = "";
	document.getElementById('head').value = "";
	document.getElementById('qty').value = "";
	document.getElementById('reid').value = "";
	document.getElementById('htype').value = "";
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