var xmlHttp;
function insert_beneficiary_type() {
	var sno = document.getElementById('sch_sno').value;
	var sr_sno = document.getElementById('sr_sno').value;
	var location = document.getElementById('loc').value;
	var beneficiary_val = document.getElementById('beneficiary_val').value;
	var beneficiary_sno = document.getElementById('beneficiary_sno').value;
			xmlHttp = createObject();
			var url = "";
			url = "../../../../../LocBeneficiaries?comment=add_ben&sno=" + sno
					+ "&sr_sno=" + sr_sno + "&location=" + location
					+ "&beneficiary_val="+ beneficiary_val + "&beneficiary_sno=" + beneficiary_sno
					+ "&t=" + Math.random();
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
		var ct = xmlHttp.responseXML.getElementsByTagName("ct")[0].firstChild.nodeValue;
		var msg = BR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		if (msg > 0) 
		{
			alert("Record Saved Successfully");	
			window.location.reload();
			document.getElementById('sr_sno').value = ct;
			view_beneficiary_type();
			clear();
		} else {
			alert(" Selected Beneficiary already included... ");
		}
}
function view_beneficiary_type()
{
	var sch_sno = document.getElementById("sch_sno_id").value;
	var sr_sno = document.getElementById('sr_sno').value;
	var location = document.getElementById('loc').value;
	var ben_short_desc = document.getElementById('ben_short_desc').value;
	var beneficiary_val = document.getElementById('beneficiary_val').value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../LocBeneficiaries?comment=view_ben&sch_sno="+ sch_sno+"&ben_short_desc="+ ben_short_desc +"&beneficiary_val="+beneficiary_val+"&sr_sno=" + sr_sno + "&loc=" + location+"&t=" + Math.random();

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
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var bR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var len = xmlHttp.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
	var ct = xmlHttp.responseXML.getElementsByTagName("ct")[0].firstChild.nodeValue;
	 document.getElementsByTagName('srsno').value=ct;
	for ( var i = 0; i < len; i++) {
		var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "","", "", "", "", "");
		var PMS_ASSET_SR_BEN_SNO = xmlValue(bR, "PMS_ASSET_SR_BEN_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
		var PMS_ASSET_SR_SNO = xmlValue(bR, "PMS_ASSET_SR_SNO", i);
		var BENEFICIARY_TYPE_ID = xmlValue(bR, "BENEFICIARY_TYPE_ID", i);
		var BENEFICIARY_SNO = xmlValue(bR, "BENEFICIARY_SNO", i);
		var LOCATION = xmlValue(bR, "LOCATION", i);
		var BENEFICIARY_NAME = xmlValue(bR, "BENEFICIARY_NAME", i);
		var BEN_TYPE_SDESC = xmlValue(bR, "BEN_TYPE_SDESC", i);
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_SR_BEN_SNO"	+ (i + 1), PMS_ASSET_SR_BEN_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),	PROJECT_ID, 2, "", "", "", "", "", "", "");
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");
		var sr_sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_SR_SNO"	+ (i + 1), PMS_ASSET_SR_SNO, 2, "", "", "", "", "", "", "");
		var bentype_cell = cell("TD", "input", "hidden", "BENEFICIARY_TYPE_ID"+ (i + 1), BENEFICIARY_TYPE_ID, 2, "", "", "", "", "", "", "");
		var ben_sno_cell = cell("TD", "input", "hidden", "BENEFICIARY_SNO"+ (i + 1), BENEFICIARY_SNO, 2, "", "", "", "", "", "", "");
		var loc_cell = cell("TD", "label", "", "LOCATION" + (i + 1), LOCATION,2, "", "", "", "", "", "", "");
		var benname_cell = cell("TD", "label", "","BENEFICIARY_NAME" + (i + 1), BENEFICIARY_NAME+" "+"("+BEN_TYPE_SDESC+")", 2, "", "", "","", "", "", "");
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);
		new_row.appendChild(loc_cell);
		new_row.appendChild(benname_cell);
		new_row.appendChild(bentype_cell);
		new_row.appendChild(ben_sno_cell);
		new_row.appendChild(sr_sno_cell);
		tbody.appendChild(new_row);
	}
}
	function delete_beneficiaries_val() {
		var sno = document.getElementById("sch_sno").value;
		var sr_sno = document.getElementById("sr_sno").value;
		var url = "";
		xmlHttp = createObject();
		url = "../../../../../LocBeneficiaries?comment=pop_ben&sno=" + sno +"&sr_sno=" + sr_sno+"&t="+Math.random();
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
	var ct = BR.getElementsByTagName("ct")[0].firstChild.nodeValue;alert('delete'+ct);
	if (msg > 0) {
		alert("Deleted Successfully");
		enabledtb('save');
		window.location.reload();
		document.getElementById('sr_sno').value = ct;
		view_beneficiary_type();
		clear();
	} else {
		alert("fail");
	}
}
function update_beneficiaryval(){
	var sno = document.getElementById("sch_sno").value;
	var sr_sno = document.getElementById("sr_sno").value;
	var ben_id = document.getElementById("beneficiary_val").value;
	var ben_sno = document.getElementById("beneficiary_sno").value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../LocBeneficiaries?comment=modify_ben&sno=" + sno
			+ "&sr_sno=" + sr_sno + "&ben_id=" + ben_id + "&ben_sno=" + ben_sno
			+ "&t=" + Math.random();
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = function() {
		update_process(xmlHttp);
	}

	xmlHttp.send(null);
}
function update_process(xmlHttp) {
	if (xmlHttp.readyState == 4) {
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
		view_beneficiary_type();
		clear();
		enabledtb('EDIT');
	} else {
		alert("fail");
	}
}
function clear()
{
	var sno_value=document.getElementById("sch_sno_id").value;
	document.getElementById('sch_sno').value=sno_value;
	document.getElementById('beneficiary_sno').value="0";
	document.getElementById('beneficiary_sno').value="0";
	enabledtb('save');
}
function combo_value(beneficiary_sno)
{
	var ben_name=document.getElementById('beneficiary_val').value;
	var combo_sno=document.getElementById('sch_sno_id').value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../LocBeneficiaries?comment=combo_ben&combo_sno=" + combo_sno  +"&ben_name=" + ben_name +"&t="+Math.random();   
	xmlHttp.open("GET", url, true);
	 xmlHttp.onreadystatechange = function() {	
		view_process_ben(xmlHttp,beneficiary_sno);
	 } 
	xmlHttp.send(null);
}
function view_process_ben(xmlHttp,beneficiary_sno) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			view_combo_ben(xmlHttp,beneficiary_sno);
		}
	}
}

function view_combo_ben(xmlHttp,beneficiary_sno) {
	
	var bR=xmlHttp.responseXML.getElementsByTagName("response")[0];
    var len=xmlHttp.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
    var sel_id=document.getElementById("beneficiary_sno");
    sel_id.options.length=0;
	for ( var i = 0; i < len; i++) {
		var BENEFICIARY_NAME = xmlValue(bR, "BENEFICIARY_NAME", i);
		var BENEFICIARY_SNO1 = xmlValue(bR, "BENEFICIARY_SNO", i);
		addOption(document.getElementById('beneficiary_sno'),BENEFICIARY_NAME,BENEFICIARY_SNO1);
	}
	var len=sel_id.options.length;
	for ( var i = 0; i <parseInt(len); i++ ) 
	{
			 var val=sel_id.options[i].value;
			 if(val==beneficiary_sno)  
			 {
						 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
						 {
							 sel_id.selectedIndex = i;
						 }
						 else
						 {
							sel_id.options[i].selected = true;
						 }
			} 
			else
			{
				 sel_id.options[i].selected = false;
			 }
	}
	view_beneficiary_type();
}
