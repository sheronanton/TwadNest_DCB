 

function set(a)
{
	var conf=confirm("Confirm Delete ? ");
	if (conf)
	{
	 del_process(7,a);
	 call_process(5);
	}
	
}
 
 
function call_process(flag)
{
	 	var type=0;
	 	var type2=0;
	 	var ben1_sno=0;
		var obj=createObject();  
		var url="../../../../../applicable_scheme_type?flag="+flag+"&stype="+document.getElementById("stype").value;
		obj.open("GET",url,true);
		obj.onreadystatechange=function ()
		{ 
			process_result(obj,flag);
		}			
		 obj.send(null);
		  
}
 
function del_process(flag,val)
{
	 	var type=0;
	 	var type2=0;
	 	var ben1_sno=0;
	 	var ben2_sno=0;
	 	try
	 	{ 
	 		 
	 		type=document.getElementById("stype"+val).innerHTML;
	 	}catch(e) {}
		var obj=createObject();  
		var url="../../../../../applicable_scheme_type?flag="+flag+"&stype="+type;
		obj.open("GET",url,true);
		obj.onreadystatechange=function ()
		{  
			process_result(obj,flag);
		}			 
		obj.send(null);
		}
function process_result(xmlobj,flag)
{
	 if (xmlobj.readyState==4)
	 {	   
		if (xmlobj.status==200)
	    { 
			if (flag==1)
			{
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				document.getElementById('stype').options.length = 0;								
				var len = bR.getElementsByTagName("SCH_TYPE_ID").length;
				var status = bR.getElementsByTagName("SCH_TYPE_DESC")[0].firstChild.nodeValue;
				addOption(document.getElementById('stype'), "select",0);
				
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("SCH_TYPE_ID")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("SCH_TYPE_DESC")[i].firstChild.nodeValue;
					var name_new=new String(name);
					addOption(document.getElementById('stype'), name + "   ( " + sno+" ) ", sno);
				}
			} 
			if (flag==2)
			{   
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var row_count = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				if (row_count>0)
					alert("DCB Applicable Scheme Type Successfully Added");
				    call_process(5);
			}if (flag==7)
			{   
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var row_count = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				if (row_count>0)
					alert("DCB Applicable Scheme Type Successfully Removed");
				call_process(5);
			}if (flag==5)
			{ 
				  var tbody = document.getElementById("tbody");
				  var table = document.getElementById("ttable");   
				  var t=0;
				  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
				     
 
				  var net_new_row1=cell("TR","","","","","","","","","","","","");
				  var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				  var len=bR.getElementsByTagName("SCH_TYPE_ID").length;
				  var rowcell=cell("TD","label","","","Delete",5,"","","text-align: center;  ","5%","","","");
				  var rowcell1=cell("TD","label","","","Sl.No",5,"","","text-align: center;  ","5%","","","");
				  var SCH_TYPE_ID_cell=cell("TD","label","","add","Scheme Type Code",5,"","","text-align: center;  ","20%","right","",""); 	             
	              var SCH_TYPE_DESC_Type=cell("TD","label","","add","Scheme Type Description ",5,"","","text-align: center;  ","","right","","");	
	              net_new_row1.appendChild(rowcell1); 	
	              net_new_row1.appendChild(rowcell); 
	              net_new_row1.appendChild(SCH_TYPE_ID_cell);
	              net_new_row1.appendChild(SCH_TYPE_DESC_Type);
	              tbody.appendChild(net_new_row1);
				  for (i=0;i<len;i++)  
				  {   
					  var net_new_row=cell("TR","","","","","","","","","","","","");
					  var img_cell=cell("TD","img","","ig","",0,"","../../../../../images/tick2.jpg","","5%","center","onclick","set(" + (i + 1) + ")");
					  var SCH_TYPE_ID=bR.getElementsByTagName("SCH_TYPE_ID")[i].firstChild.nodeValue;					  
					  var SCH_TYPE_DESC=bR.getElementsByTagName("SCH_TYPE_DESC")[i].firstChild.nodeValue;					  
					  var row_cell_=cell("TD","label","","",(i+1),5,"","","text-align: center;  ","2%","center","","");
		              var SCH_TYPE_ID_Type=cell("TD","label","","stype"+(i+1),SCH_TYPE_ID,5,"","","text-align: center;  ","20%","","","");
		              var SCH_TYPE_DESC_cell=cell("TD","label","","desc"+(i+1),SCH_TYPE_DESC,5,"","","text-align: left;  ","","left","",""); 	             
		              net_new_row.appendChild(row_cell_); 
		              net_new_row.appendChild(img_cell);  
		              net_new_row.appendChild(SCH_TYPE_ID_Type);
		              net_new_row.appendChild(SCH_TYPE_DESC_cell);
		              tbody.appendChild(net_new_row);
				  } 
			}
	    }  
	 }
}
