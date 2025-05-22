
var xmlHttp;

function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {      
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {     
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function add() {


	var sno = document.getElementById('sid').value;
	var desc =new String(document.getElementById('did').value);
	desc=desc.replace('%', '#');
	
	//var userid = document.getElementById('bid').value;
	//var time = document.getElementById('aid').value;
	var flag = document.getElementById('fid').value;
	
	var unit = document.getElementById('uid').value;
	var ronly = document.getElementById('rid').value;

	var dlim = document.getElementById('lid').value;
	var dord = document.getElementById('oid').value;
	var man= document.getElementById('man').value;
	var dtype = document.getElementById('tid').value;
	var astatus= document.getElementById('astatus').value;
	var Formula = document.getElementById('Formula').value;
	xmlHttp = createObject();     
	var url = "";  
	url = "../../../../../Performance_new?method=add&desc=" + desc + "&flag=" + flag + "&unit=" + unit +  
	"&ronly=" + ronly  + "&man="+man+"&dlim=" + dlim + "&dord=" + dord + "&dtype=" + dtype+"&Formula="+Formula+"&astatus="+astatus ;

	xmlHttp.onreadystatechange = function() {
		add_process(xmlHttp);
	}
	xmlHttp.open("GET", url, true);
	xmlHttp.send(null);
	
} 

function add_process(xmlHttp) {

	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			add_result(xmlHttp);
		}
	}
}



function add_result(xmlHttp) {
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;

	
	
	if (row > 0)
	{
		alert("Success");
	clear();
	view();
	
		}
	else{
		alert("fail");  
	}
	}
function update() {

	var sno = document.getElementById('sid').value;
	var desc = document.getElementById('did').value;
	desc=desc.replace('%', '*');
	
	//var userid = document.getElementById('bid').value;
	//var time = document.getElementById('aid').value;
	var flag = document.getElementById('fid').value;
	
	var unit = document.getElementById('uid').value;
	var ronly = document.getElementById('rid').value
	var dlim = document.getElementById('lid').value;
	var dord = document.getElementById('oid').value;
	var dtype = document.getElementById('tid').value;
	var ssno = document.getElementById('ssno').value;
	var astatus= document.getElementById('astatus').value;
	var man= document.getElementById('man').value;
	var Formula = document.getElementById('Formula').value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../Performance_new?method=update&desc=" + desc + "&flag=" + flag + "&unit=" + unit +  
	"&ronly=" + ronly  + "&man="+man+"&dlim=" + dlim + "&dord=" + dord + "&dtype=" + dtype + "&ssno=" + ssno+"&Formula="+Formula+"&astatus="+astatus   ;
	xmlHttp.onreadystatechange = function() {
		update_process(xmlHttp);
	}
	xmlHttp.open("GET", url, true);
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
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;



	if (row > 0)
	{	

	   clear();
	   view();
		alert("Success");
	}
	else
	{		alert("fail");
	}
}
function delete_()
{
			var sno=document.getElementById("ssno").value;
			var url = "";
			xmlHttp = createObject();
			url = "../../../../../Performance_new?method=delete&ssno=" + sno  ;
		    //alert(url);
			xmlHttp.onreadystatechange = function() {
				delete_process(xmlHttp);
			}
			xmlHttp.open("GET", url, true);
			xmlHttp.send(null);
	 }
function delete_process(xmlHttp)
{
	
	if(xmlHttp.readyState == 4){
		if(xmlHttp.status == 200){
			delete_result(xmlHttp);
		}
	}}
function delete_result(xmlHttp) {
	
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;

	if (row > 0)
	{	
	   view();
	   clear();
		alert("Success");
	}
	else
	{   alert("fail");
	} 
}
function view()    
{
	var url="";
	url = "../../../../../Performance_new?method=view";
	var xmlobj2 = GetXmlHttpObject();
	xmlobj2.open("GET", url, true); 
	xmlobj2.onreadystatechange = function() {
		view_process(xmlobj2 );  
	}
	xmlobj2.send(null);  
}
function view_process(xmlHttp1)
{
	    
	if(xmlHttp1.readyState== 4)
	{
		
		if(xmlHttp1.status== 200)
		{
			view_result(xmlHttp1);    
		}
	}
}
function view_result(xmlHttp1)
{
	
	 var tbody = document.getElementById('tblList');
	 
	 try
	 {
	 for (t = tbody.rows.length - 1; t >= 0; t--) 
		{
		
		 tbody.deleteRow(0);
		 }
	 }
	catch(e)
	{
		   
	} 
	var bR = xmlHttp1.responseXML.getElementsByTagName("response")[0];
     var len=bR.getElementsByTagName("PERFORM_DESC_SNO").length;
     	var formula_sq=0;
		for (var i=0;i<len;i++)
		{
			
			
		    var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
		    var PERFORM_DESC_SNO=xmlValue(bR,"PERFORM_DESC_SNO",i);
			var PERFORM_DESC=xmlValue(bR,"PERFORM_DESC",i);
			var FYEAR_MTH_FLAG=xmlValue(bR,"FYEAR_MTH_FLAG",i);
			var UPDATED_BY_USER_ID=xmlValue(bR,"UPDATED_BY_USER_ID",i);
			var FORMULA=xmlValue(bR,"FORMULA",i);
			var ACTIVE_STATUS=xmlValue(bR,"ACTIVE_STATUS",i);
			var UPDATED_TIME=xmlValue(bR,"UPDATED_TIME",i);
			var UNITS=xmlValue(bR,"UNITS",i);
			var READONLY=xmlValue(bR,"READONLY",i);
			var DLIMIT=xmlValue(bR,"DLIMIT",i);
			var DISPLAYORDER=xmlValue(bR,"DISPLAYORDER",i);
			var DATATYPE=xmlValue(bR,"DATATYPE",i);
			var MANDATORY=xmlValue(bR,"MANDATORY",i);
			var FYEAR_MTH_FLAG_label;
			switch(FYEAR_MTH_FLAG) 
			{
			case 'M':
				FYEAR_MTH_FLAG_label="Month".toUpperCase();
				break;
			case 'Y':
				FYEAR_MTH_FLAG="Year".toUpperCase();
				break;
			}
			var READONLY_var;
			switch(READONLY)
			{
			case 'Y':
				READONLY_var="Yes".toUpperCase();
				break;
			case 'N':
				READONLY_var="No".toUpperCase();
				break;
			default:
				READONLY_var=" - ".toUpperCase();
			break;
			}
			var dtypecell;
			
			switch(parseInt(DATATYPE))
			{
			case 1:
				dtypecell="Integer".toUpperCase();
				break;
			case 2:
				dtypecell="Float".toUpperCase();
				break;
			case 3:
				dtypecell="Date".toUpperCase();
				break;
			case 4:
				dtypecell="Char".toUpperCase();
				break;
			}
			var sno_cell=cell("TD","input","hidden","PERFORM_DESC_SNO"+(i+1),PERFORM_DESC_SNO,2,"tdText","","","2%","","","");
			var href_cell = cell("TD","A","EDIT","EDIT","EDIT",2,"","javascript:select("+(i + 1)+")","", "5%", "", "", "");     
			var desc_cell=cell("TD","label","","PERFORM_DESC"+(i+1),PERFORM_DESC,2,"","","","","","","");
			var flag_cell=cell("TD","label","","FYEAR_MTH_FLAG"+(i+1),FYEAR_MTH_FLAG,2,"","","","","","","");
			var uid_cell=cell("TD","input","hidden","UPDATED_BY_USER_ID"+(i+1),UPDATED_BY_USER_ID,2,"","","","","","","");
			var time_cell=cell("TD","input","hidden","UPDATED_TIME"+(i+1),UPDATED_TIME,2,"","","","","","","");
			var unit_cell=cell("TD","label","","UNITS"+(i+1),UNITS,2,"","","","","","","");
			var FYEAR_MTH_FLAG_cell=cell("TD","label","","" ,FYEAR_MTH_FLAG_label,2,"","","","","","","");
			var read_cell_val=cell("TD","label","","" ,READONLY_var,2,"","","","","","","");
			var read_cell=cell("TD","input","hidden","READONLY"+(i+1),READONLY,2,"","","","","","","");
			var dlim_cell=cell("TD","label","","DLIMIT"+(i+1),DLIMIT,2,"","","","","","","");
			var dord_cell=cell("TD","label","","DISPLAYORDER"+(i+1),DISPLAYORDER,2,"","","","","","","");
			var type_cell=cell("TD","input","hidden","DATATYPE"+(i+1),DATATYPE,2,"","","","","","","");
			var type_cell_val=cell("TD","label","","",dtypecell,2,"","","","","","","");
			var FORMULA_cell_val=cell("TD","label","","Formula"+(i+1),FORMULA,2,"","","","","","","");
			var ACTIVE_STATUS_cell_val=cell("TD","label","","ACTIVE_STATUS"+(i+1),ACTIVE_STATUS,2,"","","","","","","");
			var MANDATORY_cell_val=cell("TD","label","","MANDATORY"+(i+1),MANDATORY,2,"","","","","","","");
			new_row.appendChild(sno_cell);
			new_row.appendChild(href_cell);
			new_row.appendChild(desc_cell);
			new_row.appendChild(flag_cell);
			new_row.appendChild(uid_cell);
			new_row.appendChild(time_cell);
			new_row.appendChild(unit_cell);
			new_row.appendChild(read_cell_val);
			new_row.appendChild(read_cell);  
		
			new_row.appendChild(dlim_cell);
			
			var sq=cell("TD","label","",formula_sq,formula_sq,2,"","","","","","","");
			
			if (ACTIVE_STATUS=="A")
			{
				formula_sq++;
				 
			}
			
			new_row.appendChild(sq);  
			
			  
			
			
			new_row.appendChild(dord_cell);
			new_row.appendChild(type_cell);
			
			new_row.appendChild(type_cell_val);
			new_row.appendChild(FORMULA_cell_val);
			new_row.appendChild(MANDATORY_cell_val);
			new_row.appendChild(ACTIVE_STATUS_cell_val);
			
			tbody.appendChild(new_row);
				}
	
}
function clear()
{
	document.getElementById("did").value="";
	document.getElementById("fid").value="";
	document.getElementById("uid").value="";
	document.getElementById("rid").value="";
	document.getElementById("lid").value="";
	document.getElementById("oid").value="";
	document.getElementById("tid").value="";

}
