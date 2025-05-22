var xmlHttp;
function add_js() {
	var id = document.getElementById('typeid').value;
	var desc = document.getElementById('desc').value;
	xmlHttp = createObject();
	 if(desc!="")
	{
	var url = "";
	url = "../../../../../Source_type?method=add&desc=" + desc + "&id=" + id;
  
	xmlHttp.onreadystatechange = function() {
		add_js_process(xmlHttp);
	}
	xmlHttp.open("GET", url, true);
	xmlHttp.send(null);
	 }else
	 {
	 alert("fill the value");
	 }

}   

function add_js_process(xmlHttp) {

	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			add_js_result(xmlHttp);
		}
	}
}

function add_js_result(xmlHttp) {
	//alert('result return');
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;

	document.getElementById('desc').value = "";
	if (row > 0)
	{
		alert("Success");
		 clear();
		view_js();
		}
	else{
		alert("fail");
	}
	}
function update_js() {
	//alert('first..');
	var id = document.getElementById('typeid').value;
	var desc = document.getElementById('desc').value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../Source_type?method=update&desc=" + desc + "&id="+ id;
	xmlHttp.onreadystatechange = function() {
		update_js_process(xmlHttp);

	}
	xmlHttp.open("GET", url, true);
	xmlHttp.send("null");
}
function update_js_process(xmlHttp) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			update_js_result(xmlHttp);
		}
	}
}

function update_js_result(xmlHttp) {
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;
	if (row > 0)
	{	clear();
	view_js();
		alert("Success");
	}
	else
	{		alert("fail");
	}
}
function delete_js() {
	var id = document.getElementById('typeid').value;
	xmlHttp = createObject();
	var url = "";
	url = "../../../../../Source_type?method=delete&id="
			+ id;
	xmlHttp.onreadystatechange = function() {
		delete_js_process(xmlHttp);

	}
	xmlHttp.open("GET", url, true);
	xmlHttp.send("null");

}
function delete_js_process(xmlHttp) {
	
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			
			delete_js_result(xmlHttp);
		}
	}
}
function delete_js_result(xmlHttp) {
	//alert('delete t3');
	var BR = xmlHttp.responseXML.getElementsByTagName("response")[0];
	var row = BR.getElementsByTagName("row")[0].firstChild.nodeValue;


	if (row > 0)
	{
		clear();
		view_js();
	
		alert("Success");
	}
	
	else
	{
		alert("fail");
	}
}
function view_js()
{
	//var id=document.getElementById('typeid').value;
	//var desc=document.getElementById('desc').value;
	xmlHttp=createObject();
	var url="";
	url="../../../../../Source_type?method=view";
	xmlHttp.onreadystatechange=function(){
		view_js_process(xmlHttp);}
	xmlHttp.open("GET",url,true);
	xmlHttp.send("null");
}
function view_js_process(xmlHttp)
{
	if(xmlHttp.readyState==4){
		if(xmlHttp.status==200){
			view_js_result(xmlHttp);
		}
	}
}
function view_js_result(xmlHttp)
{
	 var tbody = document.getElementById('tblList');
	 
	 for (t = tbody.rows.length - 1; t >= 0; t--) 
		{
			tbody.deleteRow(0);
		}
	 var bR=xmlHttp.responseXML.getElementsByTagName("response")[0];
     var len=bR.getElementsByTagName("DESCRIPTION").length;  
		for (var i=0;i<len;i++)
		{
		    var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
		    var stid=xmlValue(bR,"stid",i);
			var DESCRIPTION=xmlValue(bR,"DESCRIPTION",i);
			var ID_cell=cell("TD","input","hidden","ID"+(i+1),stid,2,"tdText","","","2%","","","");
			var href_cell = cell("TD","A","EDIT","EDIT","EDIT",2,"","javascript:select("+(i + 1)+")","", "5%", "", "", "");     
			var DESCRIPTION_CELL=cell("TD","label","","DESCRIPTION"+(i+1),DESCRIPTION,2,"","","","","","","");
			new_row.appendChild(ID_cell);
			new_row.appendChild(href_cell);
			new_row.appendChild(DESCRIPTION_CELL);
			tbody.appendChild(new_row);
		}
}
	
	function clear()
	{
		document.getElementById("desc").value="";
	}
