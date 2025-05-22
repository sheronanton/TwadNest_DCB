 

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
		var url="../../../../../applicable_journal_type?flag="+flag+"&jtype="+document.getElementById("jtype").value;
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
	 		 
	 		type=document.getElementById("jtype"+val).innerHTML;
	 	}catch(e) {}
		var obj=createObject();  
		var url="../../../../../applicable_journal_type?flag="+flag+"&jtype="+type;
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
				document.getElementById('jtype').options.length = 0;								
				var len = bR.getElementsByTagName("JOURNAL_TYPE_CODE").length;
				var status = bR.getElementsByTagName("JOURNAL_TYPE_DESC")[0].firstChild.nodeValue;
				addOption(document.getElementById('jtype'), "select",0);
				
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("JOURNAL_TYPE_CODE")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("JOURNAL_TYPE_DESC")[i].firstChild.nodeValue;
					addOption(document.getElementById('jtype'), name + "   ( " + sno+" ) ", sno);
				}
			} 
			if (flag==2)
			{   
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var row_count = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				if (row_count>0)
					alert("DCB Applicable Journal Type Successfully Added");
				    call_process(5);
			}if (flag==7)
			{   
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var row_count = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				if (row_count>0)
					alert("DCB Applicable Journal Type Successfully Removed");
				call_process(5);
			}if (flag==5)
			{ 
				  var tbody = document.getElementById("tbody");
				  var table = document.getElementById("ttable");   
				  var t=0;
				  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
				  
 
				  var net_new_row1=cell("TR","","","","","","","","","","","","");
				  var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				  var len=bR.getElementsByTagName("JOURNAL_TYPE_CODE").length;
				  var rowcell=cell("TD","label","","","Delete",5,"","","text-align: center;  ","5%","","","");
				  var rowcell1=cell("TD","label","","","Sl.No",5,"","","text-align: center;  ","5%","","","");
				  var JOURNAL_TYPE_CODE_cell=cell("TD","label","","add","Type Code ",5,"","","text-align: center;  ","5%","right","",""); 	             
	              var JOURNAL_TYPE_DESC_Type=cell("TD","label","","add","Type Description ",5,"","","text-align: center;  ","","right","","");	
	              var display_restricted_cell=cell("TD","label","","add","Display Restriction",5,"","","text-align: center;  ","5%","right","",""); 	             
	              var category_Type=cell("TD","label","","add","Category",5,"","","text-align: center;  ","5%","right","","");
	              
	              net_new_row1.appendChild(rowcell1); 	
	              net_new_row1.appendChild(rowcell); 
	              net_new_row1.appendChild(JOURNAL_TYPE_CODE_cell);
	              net_new_row1.appendChild(JOURNAL_TYPE_DESC_Type);
	           //   net_new_row1.appendChild(display_restricted_cell);
	              net_new_row1.appendChild(category_Type); 
	              tbody.appendChild(net_new_row1);
				  for (i=0;i<len;i++)  
				  {   
					  var net_new_row=cell("TR","","","","","","","","","","","","");
					  var img_cell=cell("TD","img","","ig","",0,"","../../../../../images/tick2.jpg","","5%","center","onclick","set(" + (i + 1) + ")");
					  var JOURNAL_TYPE_CODE=bR.getElementsByTagName("JOURNAL_TYPE_CODE")[i].firstChild.nodeValue;					  
					  var JOURNAL_TYPE_DESC=bR.getElementsByTagName("JOURNAL_TYPE_DESC")[i].firstChild.nodeValue;					  
					  var display_restricted =bR.getElementsByTagName("DISPLAY_RESTRICTED")[i].firstChild.nodeValue;					  
					  var category=bR.getElementsByTagName("CATEGORY")[i].firstChild.nodeValue;					  
					  var row_cell_=cell("TD","label","","",(i+1),5,"","","text-align: center;  ","2%","center","","");
		              var JOURNAL_TYPE_CODE_Type=cell("TD","label","","jtype"+(i+1),JOURNAL_TYPE_CODE,5,"","","text-align: center;  ","5%","","","");
		              var JOURNAL_TYPE_DESC_cell=cell("TD","label","","desc"+(i+1),JOURNAL_TYPE_DESC,5,"","","text-align: left;  ","","left","",""); 	             
		              var display_restrictedcell=cell("TD","label","","dis"+(i+1),display_restricted,5,"","","text-align: center;font  ","5%","center","","");
		              category=(category=="G")?"GJV":category;
		              var category_cell=cell("TD","label","","add"+(i+1),category,5,"","","text-align: center;  ","5%","center","",""); 	             
		              net_new_row.appendChild(row_cell_); 
		              net_new_row.appendChild(img_cell);  
		              net_new_row.appendChild(JOURNAL_TYPE_CODE_Type);
		              net_new_row.appendChild(JOURNAL_TYPE_DESC_cell);
		            //  net_new_row.appendChild(display_restrictedcell);
		              net_new_row.appendChild(category_cell); 
		              
		              tbody.appendChild(net_new_row);
				  } 
			}
	    }  
	 }
}
