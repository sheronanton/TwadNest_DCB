 
var sub_comp_tot=0;

function mod(sno,comp,subcomp_v,formsno)
{
	document.getElementById("MAP_SNO").value=sno;
	var drac=document.getElementById("comp_");
	var drac_len=drac.options.length;
	for ( var i = 0; i <parseInt(drac_len); i++ ) 
    {
		 var val=drac.options[i].value;
		 if(comp==val)  
		 {
			 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
			 {
				 drac.selectedIndex = i;
			 }
			 else
			 { 
				 drac.options[i].selected = true;
			 }
		 }else
		 {
			 	drac.options[i].selected = false;
		 }
    }
	var drac=document.getElementById("comp_");
	var drac_len=drac.options.length;
	for ( var i = 0; i <parseInt(drac_len); i++ ) 
    {
		 var val=drac.options[i].value;
		 if(comp==val)  
		 {
			 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
			 {
				 drac.selectedIndex = i;
			 }
			 else
			 { 
				 drac.options[i].selected = true;
			 }
		 }else
		 {
			 	drac.options[i].selected = false;
		 }
    }
	
	var form_name=document.getElementById("form_name");
	var form_name_len=form_name.options.length;
	for ( var i = 0; i <parseInt(form_name_len); i++ ) 
    {
		 var val=form_name.options[i].value;
		 if(formsno==val)  
		 {
			 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
			 {
				 form_name.selectedIndex = i;
			 }  
			 else
			 { 
				 form_name.options[i].selected = true;
			 }
		 }else
		 {
			 form_name.options[i].selected = false;
		 }
    }
	
	dis(sno,comp,subcomp_v,formsno) ; 
	document.getElementById("d").value="TEST";
}  
function comp(command,input_value)
{
	var xmlobj = createObject();
	var url = "../../../../../Asset_Mapping?command="+command;
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		comp_process(xmlobj,input_value);
	}
	xmlobj.send(null);
	 form_com('forms','form_name');
}
function comp_process(xmlobj,input_value)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			var len=bR.getElementsByTagName("sno").length;
					for (i=0;i<len;i++)
					{
						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
					    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
					    addOption(document.getElementById(input_value),name,sno)
				    }
			        
			 
		}
		
	}


}


function subcomp(command,id,input_value)
{
	var xmlobj = createObject();
	var url = "../../../../../Asset_Mapping?command="+command+"&comp="+id.value;
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		subcomp_process(xmlobj,input_value);
	}
	xmlobj.send(null);
  
}

function subcomp_process(xmlobj,input_value)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			document.getElementById(input_value).options.length = 0;
			var len=bR.getElementsByTagName("sno").length;
					for (i=0;i<len;i++)
					{
						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
					    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
					    addOption(document.getElementById(input_value),name,sno)
				    }
					sub_comp_tot=len;
			        
			 
		}
		
	}


}
function form_com(command,input_value)
{
	var xmlobj = createObject();
	var url = "../../../../../Asset_Mapping?command="+command;
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		form_com_process(xmlobj,input_value);
	}
	xmlobj.send(null);
	 view();    
}

function form_com_process(xmlobj,input_value)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			var len=bR.getElementsByTagName("sno").length;
					for (i=0;i<len;i++)
					{
						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
					    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
					    addOption(document.getElementById(input_value),name,sno)
				    }
			        
			 
		}
		
	}  


}

function view()
{
	var xmlobj = createObject();
	var url = "../../../../../Asset_Mapping?command=view";
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		view_process(xmlobj);
	}
	xmlobj.send(null);
  
}
function view_process(xmlobj,input_value)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			 var tbody = document.getElementById('tblList');
			 for (t = tbody.rows.length - 1; t >= 0; t--) 
				{
				 tbody.deleteRow(0);
				}
			// MAP_SNO  form_sno comp_desc subcomp_desc comp_id subcomp_id 
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			var len=bR.getElementsByTagName("MAP_SNO").length;
					for (i=0;i<len;i++)
					{
						    var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
						    var MAP_SNO=xmlValue(bR,"MAP_SNO",i);
							var form_sno=xmlValue(bR,"form_sno",i);
							var comp_desc=xmlValue(bR,"comp_desc",i);
							var subcomp_desc=xmlValue(bR,"subcomp_desc",i);
							var comp_id=xmlValue(bR,"comp_id",i);
							var FORM_DESC=xmlValue(bR,"FORM_DESC",i);
							var subcomp_id=xmlValue(bR,"subcomp_id",i);
							var href_cell = cell("TD","A","EDIT","EDIT","EDIT",2,"","javascript:mod("+MAP_SNO+","+comp_id+","+subcomp_id+","+form_sno+")","", "5%", "", "", "");     

						    var MAP_SNO_cell=cell("TD","input","hidden"," MAP_SNO"+(i+1), MAP_SNO,2,"","","","","","","");
					        var form_snocell=cell("TD","input","hidden","form_sno"+(i+1),form_sno,2,"","","","","","","");
					        var comp_desc_cell=cell("TD","label","","comp_desc"+(i+1),comp_desc,2,"","","","","","","");
					        var subcomp_desc_cell=cell("TD","label","","subcomp_desc"+(i+1),subcomp_desc,2,"","","","","","","");
					        var comp_id_desc_cell=cell("TD","input","hidden","comp_id"+(i+1),comp_id,2,"","","","","","","");
					        var subcomp_id_desc_cell=cell("TD","input","hidden","subcomp_id"+(i+1),subcomp_id,2,"","","","","","","");
					        var FORM_DESC_cell=cell("TD","label","","FORM_DESC"+(i+1),FORM_DESC,2,"","","","","","","");
					      
					        
					        new_row.appendChild(href_cell);
					        new_row.appendChild(MAP_SNO_cell);
					        new_row.appendChild(form_snocell);
					        new_row.appendChild(comp_desc_cell);
					        new_row.appendChild(subcomp_desc_cell);
					        new_row.appendChild(comp_id_desc_cell);
					        new_row.appendChild(subcomp_id_desc_cell);
					        new_row.appendChild(FORM_DESC_cell);  
					        tbody.appendChild(new_row);
				    }
			          
			 
		}
		
	}


}

function map_submit()
{    
	var xmlobj = createObject();
	var url = "../../../../../Asset_Mapping?command=insert";
	
	var comp=document.getElementById("comp_").value;
	var subcomp=document.getElementById("sub_comp_").value;
	var form=document.getElementById("form_name").value;
	url+="&comp="+comp+"&subcomp="+subcomp+"&form="+form;
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		map_submit_process(xmlobj);
	}
	xmlobj.send(null);
	 
}



function map_submit_process(xmlobj,input_value)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			var row = bR.getElementsByTagName("row")[0].firstChild.nodeValue;
			if (row > 0)
				alert("Mapping Done Successfully!");
			else
				alert("Mapping Not Done!");
		}
		view();;
	}
	
}
function up_submit()
{   
	var sno=document.getElementById("MAP_SNO").value;
	var xmlobj = createObject();
	
	var url = "../../../../../Asset_Mapping?command=update";
	var comp=document.getElementById("comp_").value;
	var comp=document.getElementById("comp_").value;
	var subcomp=document.getElementById("sub_comp_").value;
	var form=document.getElementById("form_name").value;
	url+="&comp="+comp+"&subcomp="+subcomp+"&form="+form+"&sno="+sno;
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		up_submit_process(xmlobj);
	}
	xmlobj.send(null);
	
}



function up_submit_process(xmlobj)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			var row = bR.getElementsByTagName("row")[0].firstChild.nodeValue;
			if (row > 0)
				alert("Mapping Updated Done Successfully!");
			else
				alert("Mapping Updated Not Done!");
		}
		view();;
	}
	
}
function del_submit()
{   
	var sno=document.getElementById("MAP_SNO").value;
	var xmlobj = createObject();
	
	var url = "../../../../../Asset_Mapping?command=delete&sno="+sno;
	var comp=document.getElementById("comp_").value;
	var comp=document.getElementById("comp_").value;
	var subcomp=document.getElementById("sub_comp_").value;
	var form=document.getElementById("form_name").value;
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		del_submit_process(xmlobj);
	}
	xmlobj.send(null);
	
}


function del_submit_process(xmlobj)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
			var row = bR.getElementsByTagName("row")[0].firstChild.nodeValue;
			if (row > 0)
				alert("Mapping Deleted Successfully!");
			else
				alert("Mapping Deleted Not Done!");
		}
		view();;
	}
	
}

function dis(sno,comp,subcomp_v,formsno)  
{
	subcomp("subcomp",document.getElementById('comp_'),"sub_comp_");
	 
	sub_comp_set(sno,comp,subcomp_v,formsno);    
}

function sub_comp_set(sno,comp,subcomp_v,formsno)
{
   alert(" Component :  "+ comp +" \n Sub Component : " +subcomp_v+" \n Form Sno "+formsno);
   
	var sd=document.getElementById("sub_comp_");
	
	var sub_comp_len=sd.options.length;
	    
	for ( var j = 0; j <parseInt(sub_comp_len); j++ ) 
    {
		 var val1=sd.options[j].value;   
		 
		 if(subcomp_v==val1)  
		 {	
			   
			 try 
			 {
				 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
				 {
					 sd.selectedIndex = j;   
					 
				 }
				 else 
				 { 
					 sd.options[j].selected = true;
					     
				 }
			 }catch(e) 
			 {
				 
				 alert(e)
			 } 
		 }else
		 {	try
		 	{
			 sd.options[j].selected = false;
		 	}catch(e) {
		 		
		 		 
		 	}
		 }
    }
}