

function slab()
{
	var xmlobj=createObject();
	var ben_sel=document.getElementById("ben_sel").value
 	var fyear=document.getElementById("fyear").value
	var fmonth=document.getElementById("fmonth").value
	url="../../../../../journal_report?ben_sel="+ben_sel+"&fyear="+fyear+"&fmonth="+fmonth;
	
	combo_name=combo_name;
    xmlobj.open("GET",url,true);
    
	xmlobj.onreadystatechange=function()
    {	 
		slab_pr(xmlobj);
    } 
	
	xmlobj.send(null);
	  
	
}
function slab_pr(xmlobj)
{
	alert("TEST")
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	  var tbody = document.getElementById("data_tbody");
	  var table = document.getElementById("data_table");                                                                                                     
	  var t=0;
}