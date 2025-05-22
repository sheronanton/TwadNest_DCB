 
function insert_() 
{
	var flag=0;
	var flag1=val_check('dim','Enter Diameter Value');
	var flag2=val_check('len_main','Enter Length Main');
	var flag3=val_check('from','Enter Location From');
	var flag4=val_check('to','Enter Location To');
var sno = document.getElementById('sid').value;
var main_type = document.getElementById('main_type').value;
var class_type = document.getElementById('class_type').value;
var dim = document.getElementById('dim').value;
var len_main = document.getElementById('len_main').value;
var from = document.getElementById('from').value;
var to = document.getElementById('to').value;
var comp = document.getElementById('com_id').value;
var sub_comp = document.getElementById('subcom_id').value;

var xmlHttp0 = createObject();  
var url = "";
if (flag1==0 && flag2==0 && flag3==0 && flag4==0 )
{

	url = "../../../../../Pumping_main?comment=add&sno=" + sno + "&comp=" + comp+"&sub_comp=" + sub_comp+
	"&main_type=" + main_type +  "&class_type=" + class_type +  "&dim="
	+ dim + "&len_main=" + len_main + "&from=" + from + "&to=" + to+"&t="+Math.random();
	xmlHttp0.open("GET", url, true);	
	xmlHttp0.onreadystatechange = function() 
	{
		insert_process(xmlHttp0);
	}
	xmlHttp0.send(null);
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
var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
if (row > 0) {
	alert("Record saved Successfully");
	clear();
	display1_();
	
} else {
	alert("fail");
}
}
function display1_() {  
	 var xmlHttp2 = createObject();
	var sno = document.getElementById('sid').value;
	var dim = document.getElementById('dim').value;
	var len_main = document.getElementById('len_main').value;
	var from = document.getElementById('from').value;
	var to = document.getElementById('to').value;
	var url = "";
	url = "../../../../../Pumping_main?comment=view";
	xmlHttp2.open("GET", url, true);
	xmlHttp2.onreadystatechange = function() {
		view_process(xmlHttp2);
	 } 
	xmlHttp2.send(null);
}

function view_process(xmlHttp) 
{
	if (xmlHttp.readyState == 4) 
	{
		if (xmlHttp.status == 200) 
		{
			view_result(xmlHttp);
		}
	}
}

function view_result(xmlHttp) 
{
	var tbody = document.getElementById('tblList');
	for (t = tbody.rows.length - 1; t >= 0; t--) 
	{
		tbody.deleteRow(0);
	}
	var bR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var len = bR.getElementsByTagName("PMS_ASSET_PMAIN_SNO").length;
	for ( var i = 0; i < len; i++) 
	{
		var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
		var PMS_ASSET_PMAIN_SNO = xmlValue(bR, "PMS_ASSET_PMAIN_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
		var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
		var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
		var TYPE_OF_PMAIN = xmlValue(bR, "TYPE_OF_PMAIN", i);
		var CLASS_OF_MAIN = xmlValue(bR, "CLASS_OF_MAIN", i);
		var DIAMETER = xmlValue(bR, "DIAMETER", i);
		var LENGTH_MAIN = xmlValue(bR, "LENGTH_MAIN", i);
		var LOCATION_FROM = xmlValue(bR, "LOCATION_FROM", i);
		var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
		var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
		var LOCATION_TO = xmlValue(bR, "LOCATION_TO", i);
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_PMAIN_SNO"	+ (i + 1), PMS_ASSET_PMAIN_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
		var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
		var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
		var type_cell = cell("TD","label","", "TYPE_OF_PMAIN" + (i + 1), TYPE_OF_PMAIN,	2, "", "", "", "", "", "", "");					
		var class_cell = cell("TD", "label", "", "CLASS_OF_MAIN" + (i + 1), CLASS_OF_MAIN,	2, "", "", "", "", "", "", "");			
		var diam_cell = cell("TD", "label", "", "DIAMETER" + (i + 1),DIAMETER, 2, "", "", "", "", "", "", "");				
		var len_cell = cell("TD", "label", "", "LENGTH_MAIN" + (i + 1),LENGTH_MAIN, 2, "", "", "", "", "", "", "");				
        var from_cell = cell("TD", "label", "", "LOCATION_FROM" + (i + 1), LOCATION_FROM,	2, "", "", "", "", "", "", "");		
		var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
		var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
		var to_cell = cell("TD", "label", "", "LOCATION_TO" + (i + 1), LOCATION_TO,	2, "", "", "", "", "", "", "");					
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);
		new_row.appendChild(from_cell);
		new_row.appendChild(to_cell);
		new_row.appendChild(type_cell);
		new_row.appendChild(class_cell);
		new_row.appendChild(diam_cell);
		new_row.appendChild(len_cell);
		tbody.appendChild(new_row);
	}
}
function modify_data() {
	 var xmlHttp5 = createObject();
	var flag1=val_check('dim','Enter Diameter Value');
	var flag2=val_check('len_main','Enter Length Main');
	var flag3=val_check('from','Enter Location From');
	var flag4=val_check('to','Enter Location To');
	var sno = document.getElementById('sid').value;
	var main_type = document.getElementById('main_type').value;
	var class_type = document.getElementById('class_type').value;
	var dim = document.getElementById('dim').value;
	var len_main = document.getElementById('len_main').value;
	var from = document.getElementById('from').value;
	var to = document.getElementById('to').value;
	var url = "";
	if (flag1==0 && flag2==0 && flag3==0 && flag4==0 )
	{
	url = "../../../../../Pumping_main?comment=modify&sno=" 
		    + sno + "&main_type=" + main_type +  "&class_type=" + class_type +  "&dim="
		    + dim + "&len_main=" + len_main + "&from=" + from + "&to=" + to+"&t="+Math.random();
	xmlHttp5.open("GET", url, true);
	xmlHttp5.onreadystatechange = function() {
		update_process(xmlHttp5);
	}
	xmlHttp5.send(null);
}  
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
	if (row > 0) 
	{
		alert("Updated Successfully");
		clear();
		 display1_();
	} else 
	{
		alert("fail");
	}
}
function delete_1() 
{
	var sno = document.getElementById("sid").value;
	var url = "";
	var xmlHttp = createObject();
	url = "../../../../../Pumping_main?comment=pop&sno=" + sno+"&t="+Math.random();
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() 
	{
		data_process(xmlHttp);
	}
	xmlHttp.send(null);
}
function data_process(xmlHttp) 
{
	if (xmlHttp.readyState == 4) 
	{
		if (xmlHttp.status == 200) 
		{
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
		 display1_();
	} else {  
		alert("fail");
	}
}
function  clear() 
{
		 
		document.getElementById("main_type").value = "0";
		document.getElementById("class_type").value = "0";
		document.getElementById("dim").value = "";
		document.getElementById("len_main").value = "";
		document.getElementById("from").value = "";
		document.getElementById("to").value ="";
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