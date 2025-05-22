var xmlHttp;
function addition_transmission() {
	var flag=0;
	var flag1=val_check('speci','Enter Specification');
	 var flag2=val_check('length','Enter Length'); 
	 var flag3=val_check('loc','Enter Location');
	
	var no_reach=document.getElementById("noftrans_main").value;
	var sno=document.getElementById('sid').value ;
	var length=document.getElementById('length').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var pipe_type=document.getElementById('pipe_type').value;
	var speci=document.getElementById('speci').value;
	var location=document.getElementById('loc').value;
	xmlHttp = createObject();
	var url = "";
	if(flag1==0 && flag2==0 && flag3==0){
	url = "../../../../../Transmission_main?comment=add&sno=" + sno +  "&pipe_type=" + pipe_type
	         +"&comp=" + comp
			+ "&sub_comp=" + sub_comp + "&specification=" + speci + "&location=" + location
			+ "&length=" + length + "&no_reach=" + no_reach +"&t="+Math.random();
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() {
		insert_process(xmlHttp);
	}  
	xmlHttp.send(null);
}
}
function insert_process(xmlHttp) 
{
	if (xmlHttp.readyState == 4) 
	{
		if (xmlHttp.status == 200) 
		{
			insert_result(xmlHttp);
		}
	}
}
function insert_result(xmlHttp) {
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("rows")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Record Saved Successfully");
		disabled1('addb1');
		clear();
		display_transmission();
	} else {
		alert("Record Not Saved!");
	}
} 
function display_transmission() {  
	//var no_pipe=document.getElementById("noftrans_main").value;
	var sno=document.getElementById('sid').value ;
	//var length=document.getElementById('length').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	//var pipe_type=document.getElementById('pipe_type').value;
	//var speci=document.getElementById('speci').value;
	//var location=document.getElementById('loc').value;
	//var no_reach=document.getElementById('no_pump').value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../Transmission_main?comment=view&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp+"&t="+Math.random();
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() 
	{
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
		// function cell(major,item,type,name,value,size,sclass,url,istyle,tdwidth,tdalign,funtype,funname)
		var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
		var PMS_ASSET_TMAIN_SNO = xmlValue(bR, "PMS_ASSET_TMAIN_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
		var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
		var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
		var TYPE_OF_PIPE = xmlValue(bR, "TYPE_OF_PIPE", i);
		var SPECIFICATION = xmlValue(bR, "SPECIFICATION", i);
		var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
		var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
		var LOCATION = xmlValue(bR, "LOCATION", i);
		var REACH_NO = xmlValue(bR, "REACH_NO", i);
		var LENGTH = xmlValue(bR, "LENGTH", i);
		var type_desc = xmlValue(bR, "TYPE_DESC", i);
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_TMAIN_SNO"	+ (i + 1), PMS_ASSET_TMAIN_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
		var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
		var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
		var type_cell = cell("TD", "input", "hidden", "TYPE_OF_PIPE" + (i + 1), TYPE_OF_PIPE,	2, "", "", "", "", "", "", "");
		var type_desc_cell = cell("TD", "label", "", "type_desc"+(i+1),type_desc, 2, "", "", "", "", "", "", "");		
		var speci_cell = cell("TD", "label", "", "SPECIFICATION" + (i + 1), SPECIFICATION,	2, "", "", "", "", "", "", "");			
		var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
		var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
		var loc_cell = cell("TD", "label", "", "LOCATION" + (i + 1),LOCATION, 2, "", "", "", "", "", "", "");				
		var reachno_cell = cell("TD", "label", "", "REACH_NO" + (i + 1),REACH_NO, 2, "", "", "", "", "", "", "");				
       var length_cell = cell("TD", "label", "", "LENGTH" + (i + 1), LENGTH,	2, "", "", "", "", "", "", "");		
		
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);
		new_row.appendChild(reachno_cell);
		new_row.appendChild(type_cell);
		new_row.appendChild(type_desc_cell);
		new_row.appendChild(speci_cell);
		new_row.appendChild(length_cell);
		new_row.appendChild(loc_cell);
		tbody.appendChild(new_row);
	}
}
function delete_transmission() {
	var sno = document.getElementById("sid").value;
	var url = "";
	xmlHttp = createObject();
	url = "../../../../../Transmission_main?comment=pop&sno=" + sno+"&t="+Math.random();
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
		display_transmission();
	} else {
		alert("There is No Record to Delete!  ");
	}
}
function update_transmission() {
	var flag1=val_check('speci','Enter Specification');
	 var flag2=val_check('length','Enter Length'); 
	 var flag3=val_check('loc','Enter Location');
	
	var no_pipe=document.getElementById("noftrans_main").value;
	var sno=document.getElementById('sid').value ;
	var length=document.getElementById('length').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var pipe_type=document.getElementById('pipe_type').value;
	var speci=document.getElementById('speci').value;
	var location=document.getElementById('loc').value;
	var no_reach=document.getElementById('no_pump').value;
	xmlHttp = createObject();
	var url = "";
	if(flag1==0 && flag2==0 && flag3==0){
	url = "../../../../../Transmission_main?comment=modify&sno=" +  sno +  "&pipe_type=" + pipe_type
    + "&no_pipe="  + no_pipe +"&comp=" + comp
	+ "&sub_comp=" + sub_comp + "&specification=" + speci + "&location=" + location
	+ "&length=" + length + "&no_reach=" + no_reach +"&t="+Math.random();
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() {
		update_process(xmlHttp);
	}
	
	xmlHttp.send(null);
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
	var row = BR.getElementsByTagName("rows")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Updated Successfully");
		clear();
		display_transmission();
	
	} else {
		alert("fail");
	}
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
function clear()
{document.getElementById('no_pump').value = "";
	document.getElementById('length').value = "";
	document.getElementById('pipe_type').value = "0";
	document.getElementById('speci').value = "";
	document.getElementById('loc').value = "";
}