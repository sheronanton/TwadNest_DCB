var xmlHttp;
function addition_distribution() {
	var flag=0;
	
	var flag1=val_check('exit','Enter Existing Value');
	var flag2=val_check('new_laid','Enter Newly Laid  Value');
	var sno = document.getElementById('sid').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var exit = document.getElementById('exit').value;
	var new_laid = document.getElementById('new_laid').value;

	xmlHttp = createObject();
	var url = "";
	if(flag1==0 && flag2==0)
	{
	url = "../../../../../Distribution_sys?comment=add&sno=" + sno + "&comp=" + comp
			+ "&sub_comp=" + sub_comp + "&exit=" + exit + "&new_laid=" + new_laid
			+ "&t="+Math.random();
	 
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
		display_distribution();
		clear();

	} else {
		alert("fail");
	}
}
function display_distribution() {  
	 
	 xmlHttp = createObject();
	 var sno = document.getElementById('sid').value;
		var comp = document.getElementById('com_id').value;
		var sub_comp = document.getElementById('subcom_id').value;
		var exit = document.getElementById('exit').value;
		var new_laid = document.getElementById('new_laid').value;
	
	var url = "";

	url = "../../../../../Distribution_sys?comment=view&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp+"&t="+Math.random();
  
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
		var PMS_ASSET_DIST_SNO = xmlValue(bR, "PMS_ASSET_DIST_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
		var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
		var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
		var EXISTING = xmlValue(bR, "EXISTING", i);
		var NEW = xmlValue(bR, "NEW", i);
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_DIST_SNO"	+ (i + 1), PMS_ASSET_DIST_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
		var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
		var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
		var exit_cell = cell("TD", "label", "", "EXISTING" + (i + 1), EXISTING,	2, "", "", "", "", "", "", "");					
		var new_cell = cell("TD", "label", "", "NEW" + (i + 1), NEW,	2, "", "", "", "", "", "", "");			
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);
		new_row.appendChild(exit_cell);
		new_row.appendChild(new_cell);
		tbody.appendChild(new_row);
	}
}
function delete_distribution() {
	var sno = document.getElementById("sid").value;
	var url = "";
	xmlHttp = createObject();
	url = "../../../../../Distribution_sys?comment=pop&sno=" + sno+"&t="+Math.random();
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
		display_distribution();
	} else {
		alert("fail");
	}
}
function update_distribution() {
	var flag1=val_check('exit','Enter Existing Value');
	var flag2=val_check('new_laid','Enter Newly Laid  Value');
	 var sno = document.getElementById('sid').value;
		var comp = document.getElementById('com_id').value;
		var sub_comp = document.getElementById('subcom_id').value;
		var exit = document.getElementById('exit').value;
		var new_laid = document.getElementById('new_laid').value;
	xmlHttp = createObject();
	var url = "";
if(flag1==0 && flag2==0)
{
	url = "../../../../../Distribution_sys?comment=modify&sno=" + sno + "&comp=" + comp
	+ "&sub_comp=" + sub_comp + "&exit=" + exit + "&new_laid=" + new_laid
	+ "&t="+Math.random();
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
		display_distribution();
	} else {
		alert("fail");
	}
}
function clear()
{
	 document.getElementById('exit').value="";
     document.getElementById('new_laid').value="";
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
