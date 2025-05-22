var xmlHttp;
function push_() 
{
	var flag=0;
	var flag1=val_check('stype','ENTER SOURCE TYPE');
	var flag2=val_check('diam','ENTER DIAMETER OF WELL');
	var flag3=val_check('depth','ENTER DEPTH OF WELL');
	var flag4=val_check('hou','ENTER DIAMETER OF PUMPHOUSE');
	var flag5=val_check('lenth','ENTER LENGTH OF PUMPHOUSE FIELD');
	var flag6=val_check('redial','ENTER REDIAL DIAMETER');
	var flag7=val_check('qty','ENTER QUANTITY FIELD');
	var flag8=val_check('location','ENTER SOURCE LOCATION');
	var flag9=val_check('remark','ENTER REMARK FIELD');
	 
	var sno = document.getElementById('sid').value;
	var type_src = document.getElementById('stype').value;
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var diam = document.getElementById('diam').value;
	var depth = document.getElementById('depth').value;
	var hou = document.getElementById('hou').value;
	var length = document.getElementById('lenth').value;
	var redial = document.getElementById('redial').value;
	var qty = document.getElementById('qty').value;
	var location = document.getElementById('location').value;
	var remark = document.getElementById('remark').value;

	xmlHttp = createObject();
	var url = "";
	if (flag1==0 && flag2==0 && flag3==0 && flag4==0 && flag5==0 && flag6==0 && flag7==0  )
	{
	url = "../../../../../AssTechDetails1?comment=add&sno=" + sno + "&comp=" + comp + "&sub_comp=" + sub_comp +"&type_src="
			+ type_src + "&diam=" + diam + "&depth=" + depth + "&hou=" + hou
			+ "&length=" + length + "&redial=" + redial + "&qty=" + qty
			+ "&location=" + location + "&remark=" + remark+"&t="+Math.random() ;
	 
 
	xmlHttp.open("GET", url, true);	
	xmlHttp.onreadystatechange = function() 
	{
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
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Record saved Successfully");
		 clear();
		show_();
	} else {
		alert("fail");
	}
}
function modify() {

	var flag1=val_check('stype','ENTER SOURCE TYPE');
	var flag2=val_check('diam','ENTER DIAMETER OF WELL');
	var flag3=val_check('depth','ENTER DEPTH OF WELL');
	var flag4=val_check('hou','ENTER DIAMETER OF PUMPHOUSE');
	var flag5=val_check('lenth','ENTER LENGTH OF PUMPHOUSE FIELD');
	var flag6=val_check('redial','ENTER REDIAL DIAMETER');
	var flag7=val_check('qty','ENTER QUANTITY FIELD');
	var flag8=val_check('location','ENTER SOURCE LOCATION');
	var flag9=val_check('remark','ENTER REMARK FIELD');
	var sno = document.getElementById('sid').value;
	var type_src = document.getElementById('stype').value;
	var diam = document.getElementById('diam').value;
	var depth = document.getElementById('depth').value;
	var hou = document.getElementById('hou').value;
	var length = document.getElementById('lenth').value;
	var redial = document.getElementById('redial').value;
	var qty = document.getElementById('qty').value;
	var location = document.getElementById('location').value;
	var remark = document.getElementById('remark').value;		
	xmlHttp = createObject();
	var url = "";
	if (flag1==0 && flag2==0 && flag3==0 && flag4==0 && flag5==0 && flag6==0 && flag7==0  )
	{
	url = "../../../../../AssTechDetails1?comment=modify&sno=" + sno
			+ "&type_src=" + type_src + "&diam=" + diam + "&depth=" + depth
			+ "&hou=" + hou + "&length=" + length + "&redial=" + redial
			+ "&qty=" + qty + "&location=" + location + "&remark=" + remark+"&t="+Math.random();
	 
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
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0) {
		alert("Updated Successfully");
		 clear();
		show_();
	} else {
		alert("fail");
	}
}

function show_() {  
	 
	 var xmlHttp1 = createObject(); 
	 var sno = document.getElementById('sid').value;
//	var type_src = document.getElementById('stype').value;
	
	var comp = document.getElementById('com_id').value;
	var sub_comp = document.getElementById('subcom_id').value;
	var diam = document.getElementById('diam').value;
	var depth = document.getElementById('depth').value;
	var hou = document.getElementById('hou').value;
	var length = document.getElementById('lenth').value;
	var redial = document.getElementById('redial').value;
	var qty = document.getElementById('qty').value;
	var location = document.getElementById('location').value;
	var remark = document.getElementById('remark').value;
    
	 
	
	var url = "";
 
	url = "../../../../../AssTechDetails1?comment=view&sno=" + sno + "&comp=" + comp + "&sub_comp="+sub_comp+"&t="+Math.random();
	
 
       
    	  	xmlHttp1.open("GET", url, true);
    	  	xmlHttp1.onreadystatechange = function() {
    	  		view_process(xmlHttp1); 
    	  	} 
    	  	xmlHttp1.send(null);
      
}  

function view_process(xmlHttp) 
{  
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {	 
			view_result(xmlHttp);
		}
	}
}

function view_result(xmlHttp) {
	 
	var tbody = document.getElementById('tblList');
 
	for (t = tbody.rows.length - 1; t >= 0; t--) 
	{
  
		tbody.deleteRow(0);  
	}
	var bR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var len = bR.getElementsByTagName("PMS_ASSET_HW_SNO").length;
	 
	for ( var i = 0; i < len; i++) {

		var new_row = cell("TR", "", "", "row" + (i + 1), (i + 1), 2, "", "",
				"", "", "", "", "");
		var PMS_ASSET_HW_SNO = xmlValue(bR, "PMS_ASSET_HW_SNO", i);
		var OFFICE_ID = xmlValue(bR, "OFFICE_ID", i);
		
		var PROJECT_ID = xmlValue(bR, "PROJECT_ID", i);
		var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
	 
		var COMP_SNO = xmlValue(bR, "COMP_SNO", i);
		var SUBCOMP_SNO = xmlValue(bR, "SUBCOMP_SNO", i);
		var TYPE_OF_SOURCE = xmlValue(bR, "TYPE_OF_SOURCE", i);  
		var DIAMETER = xmlValue(bR, "DIAMETER", i);
		var DEPTH_WELL = xmlValue(bR, "DEPTH_WELL", i);
		var DIA_PUMPHOUSE = xmlValue(bR, "DIA_PUMPHOUSE", i);
		var LENGTH_PUMPHOUSE = xmlValue(bR, "LENGTH_PUMPHOUSE", i);
		var DIA_RADIAL = xmlValue(bR, "DIA_RADIAL", i);
		var DRAW_QTY = xmlValue(bR, "DRAW_QTY", i);
		var SOURCE_LOCATION = xmlValue(bR, "SOURCE_LOCATION", i);
		var REMARKS = xmlValue(bR, "REMARKS", i);
		var UPDATED_BY_USER_ID = xmlValue(bR, "UPDATED_BY_USER_ID", i);
		var UPDATED_TIME = xmlValue(bR, "UPDATED_TIME", i);
		
		 
		
		var type= xmlValue(bR, "type", i);
		 
		var sno_cell = cell("TD", "input", "hidden", "PMS_ASSET_HW_SNO"	+ (i + 1), PMS_ASSET_HW_SNO, 2, "tdText", "", "", "2%", "", "","");				
		var href_cell = cell("TD", "A", "EDIT", "EDIT", "EDIT", 2, "",	"javascript:tech(" + (i + 1) + ")", "", "5%", "", "", "");			
		var ofid_cell = cell("TD", "input", "hidden", "OFFICE_ID" + (i + 1),OFFICE_ID, 2, "", "", "", "", "", "", "");				
		var projid_cell = cell("TD", "input", "hidden", "PROJECT_ID" + (i + 1),PROJECT_ID, 2, "", "", "", "", "", "", "");			
		var schsno_cell = cell("TD", "input", "hidden", "SCH_SNO" + (i + 1),SCH_SNO, 2, "", "", "", "", "", "", "");			
		var comp_cell = cell("TD", "input", "hidden", "COMP_SNO" + (i + 1),COMP_SNO, 2, "", "", "", "", "", "", "");				
		var subcomp_cell = cell("TD", "input", "hidden","SUBCOMP_SNO" + (i + 1), SUBCOMP_SNO, 2, "", "", "", "", "", "","");			    
		var type_cell = cell("TD", "label", "", "TYPE_OF_SOURCE" + (i + 1), TYPE_OF_SOURCE,	2, "", "", "", "", "", "", "");			
		var typesno_cell = cell("TD", "input", "hidden", "type" + (i + 1),type, 2, "", "", "", "", "", "", "");
		var dia_cell = cell("TD", "label", "", "DIAMETER" + (i + 1), DIAMETER,	2, "", "", "", "", "", "", "");			
		var depth_cell = cell("TD", "label", "", "DEPTH_WELL" + (i + 1),DEPTH_WELL, 2, "", "", "", "", "", "", "");				
		var house_cell = cell("TD", "label", "", "DIA_PUMPHOUSE" + (i + 1),DIA_PUMPHOUSE, 2, "", "", "", "", "", "", "");				
		var length_cell = cell("TD", "label", "", "LENGTH_PUMPHOUSE" + (i + 1),LENGTH_PUMPHOUSE, 2, "", "", "", "", "", "", "");
		var dial_cell = cell("TD", "label", "", "DIA_RADIAL" + (i + 1),DIA_RADIAL, 2, "", "", "", "", "", "", "");			
		var qty_cell = cell("TD", "label", "", "DRAW_QTY" + (i + 1), DRAW_QTY,	2, "", "", "", "", "", "", "");			
		var loc_cell = cell("TD", "label", "", "SOURCE_LOCATION" + (i + 1),SOURCE_LOCATION, 2, "", "", "", "", "", "", "");				
		var remark_cell = cell("TD", "label", "", "REMARKS" + (i + 1), REMARKS,	2, "", "", "", "", "", "", "");		
		var userid_cell = cell("TD", "input", "hidden", "UPDATED_BY_USER_ID"+(i + 1), UPDATED_BY_USER_ID, 2, "", "", "", "", "", "", "");			
		var time_cell = cell("TD", "input", "hidden", "UPDATED_TIME" + (i + 1),UPDATED_TIME, 2, "", "", "", "", "", "", "");
				
		new_row.appendChild(sno_cell);
		new_row.appendChild(href_cell);  
		//new_row.appendChild(type_value_cell);
		//new_row.appendChild(type_cell);
		new_row.appendChild(dia_cell);
		
		new_row.appendChild(typesno_cell);
		new_row.appendChild(depth_cell);
		new_row.appendChild(house_cell);
		new_row.appendChild(length_cell);
		new_row.appendChild(dial_cell);
		new_row.appendChild(qty_cell);
		new_row.appendChild(loc_cell);
		new_row.appendChild(remark_cell);
		tbody.appendChild(new_row);
	}
}
function delete_data() {
	var sno = document.getElementById("sid").value;
	var url = "";
	xmlHttp = createObject();
	url = "../../../../../AssTechDetails1?comment=pop&sno=" + sno+"&t="+Math.random(); 
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
		show_();
	} else {
		alert("fail");
	}
}

function clear() {
	document.getElementById('stype').value = "0"; 
	document.getElementById('sid').value = ""; 
	document.getElementById('diam').value = "";
	document.getElementById('depth').value = "";
	document.getElementById('hou').value = "";
	document.getElementById('lenth').value = "";
	document.getElementById('redial').value = "";
	document.getElementById('qty').value = "";
	document.getElementById('location').value = "";
	document.getElementById('remark').value = "";

}
function validateDecimal(id){
	
	var field = id;
	var valo = new String();
	var numere = "0123456789.";
	var chars = field.value.split("");
	for (i = 0; i < chars.length; i++){
		if (numere.indexOf(chars[i]) != -1) 
			valo += chars[i];
		else{
			alert("Only Numbers And '.' !");
		}
	}
	if (field.value != valo) field.value = valo; 
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
function val(tb,len,dec)
{
	var v=tb.value;
	var regex='^[0-9]{1,'+len+'}[.][0-9]{1,'+dec+'}$';
	if(v.search(regex)==-1)
	{
		alert("!!!"+""+ "Enter number in this format (999.99)");
		return false;
	}
	return true;
	
	
}
function val1(tb,len,dec)
{
	var v=tb.value;
	var regex='^[0-9]{1,'+len+'}[.][0-9]{1,'+dec+'}$';
	if(v.search(regex)==-1)
	{
		alert("!!!"+""+ "Enter number in this format (9999.99)");
		return false;
	}
	return true;
	
	
}

