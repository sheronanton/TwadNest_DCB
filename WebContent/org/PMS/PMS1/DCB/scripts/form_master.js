
 var process_code=0;
 
function master(type,processcode)
{
	 
	process_code=processcode;
	sel_type=type;
	var xmlobj=createObject();
	url="../../../../../ame_master";
	var params="";
	// Group - Insert 	
	if (sel_type==1)
	{
		master_process(xmlobj)
	}
}



function master_process(xml_obj)
{
	
	if (xml_obj.readyState==4)
	 {	   
		 
		if (xml_obj.status==200)
	    {
				master_process_result(xml_obj);
				flag=1;
	    }
	 }
}
function master_process_result(xml_obj)
{
	//  Group Message  
	 
			if (process_code==3)
				{
				
						var tbody = document.getElementById("edata");
						var table = document.getElementById("etable");
						var t=0;
						for(t=tbody.rows.length-1;t>=0;t--)
						{
							tbody.deleteRow(0);
						}
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
						
						document.getElementById("rowcount").value=len;
						
						for (var i=0;i<len;i++)
					 	{ 
							 var GROUP_SNO=bR.getElementsByTagName("GROUP_SNO")[i].firstChild.nodeValue;
							 var GROUP_DESC=bR.getElementsByTagName("GROUP_DESC")[i].firstChild.nodeValue;
							 var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
							 var SNO_cell=cell("TD","input","hidden","GROUP_SNO"+(i+1),GROUP_SNO,2,"tdText","","","2%","","","");
							 
							 var Chk_cell=cell("TD","A","","chkGROUP_SNO"+(i+1),"EDIT",2,"tdText","javascript:vset("+(i+1)+")","","2%","","","");
							 var NAME_cell=cell("TD","label","","GROUP_DESC"+(i+1),GROUP_DESC,0,"",""," ","","left","","");
							 new_row.appendChild(SNO_cell);
							 
							 new_row.appendChild(Chk_cell);
							 new_row.appendChild(NAME_cell);
							 
							 tbody.appendChild(new_row);
					 	}
				}
	
}

