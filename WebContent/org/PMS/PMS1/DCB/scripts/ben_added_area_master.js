 

function set(a)
{
	var conf=confirm("Do you want continue ");
	if (conf)
	{
	 del_process(7,a);
	 call_process(5);
	}
	
}
function call_process_2(flag)
{
	var dist1=0;
 	var dist2=0;
 	try
 	{
 		dist1=document.getElementById("d1").value;
 		dist2=document.getElementById("d2").value;
 	}catch(e)
 	{
 		
 	}
	var obj=createObject();  
	var url="../../../../../ben_added_area_master?flag="+flag+"&dist2="+dist2+"&dist1="+dist1;
	obj.open("GET",url,true);
	obj.onreadystatechange=function ()
		{ 
		process_result(obj,flag);
		}			
	 obj.send(null);
}

function call_process(flag)
{
	 	var type=0;
	 	var type2=0;
	 	var ben1_sno=0;
	 	var ben2_sno=0;
	 	var dist1=0;
	 	var dist2=0;
		var block_1=0;
	 	var block_2=0;
	 	try
	 	{
	 		dist1=document.getElementById("d1").value;
	 		dist2=document.getElementById("d2").value;
	 	}catch(e)
	 	{
	 		
	 	}
	 	try
	 	{ 
	 		type=document.getElementById("btype").value;
	 		type2=document.getElementById("btype2").value;
	 		
	 	}catch(e) {}
	 	try
	 	{ 
	 		ben1_sno=document.getElementById("ben_name").value;
	 		ben2_sno=document.getElementById("ben_name2").value;
	 	}catch(e) {}
	 	try
	 	{ 
	 		block_1=document.getElementById("block_1").value;
	 		block_2=document.getElementById("block_2").value;
	 	}catch(e) {}
	 	
		var obj=createObject();  
		var url="../../../../../ben_added_area_master?flag="+flag+"&dist2="+dist2+"&dist1="+dist1+"&type="+type+"&type2="+type2+"&ben1_sno="+ben1_sno+"&ben2_sno="+ben2_sno+"&block_2="+block_2+"&block_1="+block_1;
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
	 		 
	 		ben2_sno=document.getElementById("ben"+val).value;
	 	}catch(e) {}
		var obj=createObject();  
		var url="../../../../../ben_added_area_master?flag="+flag+"&type="+type+"&type2="+type2+"&ben1_sno="+ben1_sno+"&ben2_sno="+ben2_sno;
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
				document.getElementById('btype').options.length = 0;
				document.getElementById('btype2').options.length = 0;				
				var len = bR.getElementsByTagName("BEN_TYPE_ID").length;
				
				
				if(bR.getElementsByTagName("BEN_TYPE_DESC")[0] == undefined){
					var status = ""
				}else {
					var status = bR.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
				}
				
				
				addOption(document.getElementById('btype'), "select",0);
				addOption(document.getElementById('btype2'), "select",0);
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
					addOption(document.getElementById('btype'), name, sno);
					addOption(document.getElementById('btype2'), name, sno);
				}
			} if (flag==2)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				document.getElementById('ben_name').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('ben_name'), "select",0);
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('ben_name'), name, sno);
				}
			} if (flag==3)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				document.getElementById('ben_name2').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('ben_name2'), "select",0);
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('ben_name2'), name, sno);
				}
			}if (flag==9)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				document.getElementById('block_1').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('block_1'), "select",0);
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("BLOCK_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BLOCK_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('block_1'), name, sno);
				}
			}if (flag==10)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				document.getElementById('block_2').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('block_2'), "select",0);
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("BLOCK_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BLOCK_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('block_2'), name, sno);
				}
			}if (flag==4)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var row_count = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				if (row_count>0)
				{
					alert("Successfully Added");
					call_process(5);
				}
			}if (flag==7)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var row_count = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				if (row_count>0)
				{
					alert("  Successfully Deleted");
					call_process(5);
				} 
			}if (flag==5)
			{ 
				  var tbody = document.getElementById("tbody");
				  var table = document.getElementById("ttable");   
				  var t=0;
				  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
				  var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				  var len=bR.getElementsByTagName("BENEFICIARY_NAME").length;
				  var rowcell1=cell("TD","label","","","Sl.No",5,"","","text-align: center;  ","2%","","","");
			//	  var rowcell=cell("TD","label","","","Delete",5,"","","text-align: center;  ","2%","","","");
				  
				  var BENEFICIARY_NAME_cell=cell("TD","label","","add","Add Beneficiary From",5,"","","text-align: center;font-weight: bold;color:#ff0080;  ","10%","right","",""); 	             
	              var BENEFICIARY_NAME_Type=cell("TD","label","","add"," ",5,"","","text-align: center;  ","10%","right","","");	
	              var BENEFICIARY_NAME2_cell=cell("TD","label","","add","Add Beneficiary To",5,"","","text-align: center;color: blue;font-weight: bold;  ","10%","right","",""); 	             
	              var BENEFICIARY_NAME2_Type=cell("TD","label","","add"," ",5,"","","text-align: center;  ","10%","right","","");
	              var net_new_row1=cell("TR","","","","","","","","","","","","");
	              
	              net_new_row1.appendChild(rowcell1);
	            //  net_new_row1.appendChild(rowcell);
	              net_new_row1.appendChild(BENEFICIARY_NAME_cell);
	              net_new_row1.appendChild(BENEFICIARY_NAME_Type);
	              net_new_row1.appendChild(BENEFICIARY_NAME2_cell);
	              net_new_row1.appendChild(BENEFICIARY_NAME2_Type); 
	              tbody.appendChild(net_new_row1); 
				  for (i=0;i<len;i++) 
				  {   
					  var net_new_row=cell("TR","","","","","","","","","","","","");
					//  var img_cell=cell("TD","img","","ig","",0,"","../../../../../images/tick2.jpg","","5%","center","onclick","set(" + (i + 1) + ")");
					  var BENEFICIARY_NAME=bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
					  var BLOCK_NAME=bR.getElementsByTagName("BLOCK_NAME")[i].firstChild.nodeValue;
					  var BLOCK_NAME2=bR.getElementsByTagName("BLOCK_NAME2")[i].firstChild.nodeValue;
					  if (BLOCK_NAME!="0")
					  BENEFICIARY_NAME=BENEFICIARY_NAME+"("+BLOCK_NAME+")";
					  var BENEFICIARY_SNO=bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;					  
					  var BENEFICIARY_TYPE_ID =bR.getElementsByTagName("BENEFICIARY_TYPE_ID")[i].firstChild.nodeValue;					  
					  var typedesc="  -->     " + bR.getElementsByTagName("TYPEDESC")[i].firstChild.nodeValue;					  
					  var oldtypedesc="  -->    " + bR.getElementsByTagName("OLDTYPEDESC")[i].firstChild.nodeValue;
					  var added_name =bR.getElementsByTagName("ADDED_NAME")[i].firstChild.nodeValue;
					
					  if (BLOCK_NAME2!="0")
					  added_name=added_name+"("+BLOCK_NAME2+")";
					  var row_cell_=cell("TD","label","","",(i+1),5,"","","text-align: center;  ","2%","center","","");
		              var BENEFICIARY_SNO_Type=cell("TD","input","hidden","ben"+(i+1),BENEFICIARY_SNO,5,"","","text-align: center;  ","","right","","");
		           
		              var BENEFICIARY_NAME_cell=cell("TD","label","","add"+(i+1),BENEFICIARY_NAME,5,"","","text-align: left;  ","10%","right","",""); 	             
		              var BENEFICIARY_NAME_Type=cell("TD","label","","add"+(i+1),typedesc,5,"","","text-align: left;font  ","10%","right","","");
		              var BENEFICIARY_NAME2_cell=cell("TD","label","","add"+(i+1),added_name,5,"","","text-align: left;  ","10%","right","",""); 	             
		              var BENEFICIARY_NAME2_Type=cell("TD","label","","add"+(i+1),oldtypedesc,5,"","","text-align: left;  ","10%","right","","");
		              net_new_row.appendChild(row_cell_); 
		        //      net_new_row.appendChild(img_cell);  
		              net_new_row.appendChild(BENEFICIARY_NAME_cell);
		              net_new_row.appendChild(BENEFICIARY_NAME2_Type);
		              net_new_row.appendChild(BENEFICIARY_SNO_Type);
		              net_new_row.appendChild(BENEFICIARY_NAME2_cell);
		              net_new_row.appendChild(BENEFICIARY_NAME_Type);
		              tbody.appendChild(net_new_row);
				  }
			}
	    } 
	 }
}
