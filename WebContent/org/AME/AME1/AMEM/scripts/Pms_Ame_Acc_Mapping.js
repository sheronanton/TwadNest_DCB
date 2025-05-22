
 
function new_transaction(type, processcode) {
	
	process_code = processcode;
	sel_type = type;
	var xmlobj = createObject();
	url = "../../../../../PMS_AME_ACC_MAPPING";
	var params = "";
	xmlobj.open("POST", url, true);
	xmlobj.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlobj.setRequestHeader("Content-length", params.length);
	xmlobj.setRequestHeader("Connection", "close");
	// Annual Maintenance Estimate Entry
	if (sel_type == 9)   
	{
		if (process_code == 1)
		{
			var mainitem =0;
			var subitem = 0;
			var sch_type =0;
			var ACC_GROUP =0;
			
			try { ACC_GROUP=document.getElementById("ACC_GROUP").value;} catch(e) {};
			try { mainitem=document.getElementById("mainitem").value;} catch(e) {};
			try { subitem = document.getElementById("subitem").value;} catch(e) {};
			try { sch_type = document.getElementById("sch_type").value;} catch(e) {};
			
			params = "process_code=" +process_code +"&type=" +type+"&subitem=" +subitem +"&mainitem=" +mainitem;
			params += "&sch_type="+sch_type+"&ACC_GROUP_SNO="+ACC_GROUP;
			flag = 0; 
		} else if (process_code == 2 || process_code == 5)  
		{
			var mainitem = document.getElementById("mainitem").value;
			var subitem = document.getElementById("subitem").value;
			var ac = document.getElementById("ac").value;
			var acdesc = document.getElementById("acdesc").value;
			var sch_type = document.getElementById("sch_type").value;
			var ACC_GROUP = document.getElementById("ACC_GROUP").value;
			
			if (parseInt(mainitem)==0 ||   parseInt(sch_type)==0  || parseInt(ACC_GROUP)==0  || ac=="" || ac==0)
				flag=1;
			else
				flag = 0;
				
			params = "ACC_GROUP_SNO="+ACC_GROUP+"&acdesc="+acdesc+"&process_code=" +process_code +"&type=" +type+"&subitem=" +subitem +"&mainitem=" +mainitem;
			params += "&ac=" +ac +"&sch_type="+sch_type;
			
		} else if (process_code == 3 || process_code == 4)  
		{
			var acsno = document.getElementById("acsno").value;
			params = "process_code=" +process_code +"&type=" +type+"&ACC_CODE_SNO=" +acsno;
			flag = 0;
		}else if (process_code == 6 )  
		{ 
			var mainitem = document.getElementById("mainitem").value;
			var subitem = document.getElementById("subitem").value;
			var sch_type = document.getElementById("sch_type").value;
			var ACC_GROUP = document.getElementById("ACC_GROUP").value;
			var ac = document.getElementById("ac").value;
			params = "ACC_GROUP_SNO="+ACC_GROUP+"&sch_type="+sch_type+"&process_code=" +process_code +"&type=" +type+"&ac=" +ac+"&subitem=" +subitem +"&mainitem=" +mainitem;;
			flag = 0;
		}else if (process_code ==7)     
		{
			var mainitem = document.getElementById("mainitem").value;
			params = "process_code=" +process_code +"&type=" +type+"&mainitem=" +mainitem;
			flag = 0;
		}
	}
	if (flag==0)
	{
		xmlobj.onreadystatechange = function() 
		{
			new_transaction_process_in(xmlobj,sel_type,process_code);
		} 
		xmlobj.send(params);
	}else
	{
		alert("Please Check All field");
	}
}
function new_transaction_process_in(xml_obj,type,process_code ) 
{
	 if(xml_obj.readyState==4)
     {
         if(xml_obj.status==200)
         {
        	 new_transaction_process(xml_obj,type,process_code )
         }
     }
}  

function new_transaction_process(xml_obj,sel_type,process_code ) 
{
	if (sel_type == 9) 
	{   
		var bR = xml_obj.responseXML.getElementsByTagName("response")[0]; 
		if (process_code == 1) 
		 {
			 
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			
			try {
				document.getElementById("rowcount").value = len;
			} catch (e) {
			}   
		    var cc="#CEF6E3";
			var c=""; 
			
			for ( var i = 0; i <len; i++) 
			{
				if (i%2==0) c=cc;else c=""; 
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "'background-color: maroon'", "", "", "", c);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SCH_TYPE_ID= xmlValue(bR, "SCH_TYPE_ID", i);
				var SUB_ITEM_DESC= xmlValue(bR, "SUB_ITEM_DESC", i);
				var SCH_TYPE_DESC = xmlValue(bR, "SCH_TYPE_DESC", i);
				if (SUB_ITEM_DESC == "0")  
					SUB_ITEM_DESC = "";
				var ACC_DESC = xmlValue(bR, "ACC_DESC", i);
				var ACCOUNT_HEAD_CODE = xmlValue(bR, "ACCOUNT_HEAD_CODE", i);
				if (ACCOUNT_HEAD_CODE == "0") 
					ACCOUNT_HEAD_CODE = "";
				//var ACC_GROUP_SNO= xmlValue(bR, "ACC_CODE_SNO", i);
				var ACC_GROUP_DESC= xmlValue(bR, "ACC_GROUP_DESC", i);
				var ACC_GROUP_SNO= xmlValue(bR, "ACC_GROUP_SNO", i);
				var ACC_CODE_SNO = xmlValue(bR, "ACC_CODE_SNO", i);
				var CODE_W_E_F = xmlValue(bR, "CODE_W_E_F", i);
				if (ACC_GROUP_DESC == "0") 	ACC_GROUP_DESC = "-"; 
				if (CODE_W_E_F == "0") 	CODE_W_E_F = "-"; 
				
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "10%", "", "", "");
				var EDIT_cell = cell("TD", "A", "", "", "EDIT", "EDIT", "","javascript:select_edit_new(" +ACC_CODE_SNO +","+(i +1) +","+ACC_GROUP_SNO+","+SCH_TYPE_ID+","+MAIN_ITEM_SNO+","+SUB_ITEM_SNO+")", "", "2%", "", "", "");
				var delete_cell = cell("TD", "A", "", "", "Delete", "Delete", "","javascript:select_edit_delete(" +ACC_CODE_SNO +","+(i +1) +")", "", "2%", "", "", "");
				var slno = cell("TD", "label", "", "", (i +1), 2, "", "", "","2%", "center", "", "");    
				var MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2, "", "","", "7%", "", "", "");
				var SUB_ITEM_DESC_cell = cell("TD", "label", "","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 2, "", "","", "7%", "", "", "");
				var ACCOUNT_HEAD_CODE_cell = cell("TD", "label", " ","ACCOUNT_HEAD_CODE" +(i +1), ACCOUNT_HEAD_CODE, 2,"", "", "", "2%", "", "", "");
				var ACC_CODE_SNO_cell = cell("TD", "input", "hidden","ACC_CODE_SNO" +(i +1), ACC_CODE_SNO, 2, "", "", "","2%", "", "", "");
				var ACC_DESC_cell = cell("TD", "label", "","ACC_DESC" +(i +1), ACC_DESC, 2, "", "", "","2%", "left", "", "");
				var ACC_GROUP_SNO_cell = cell("TD", "input", "hidden","ACC_GROUP_SNO" +(i +1), ACC_GROUP_SNO, 2, "", "", "","2%", "", "", "");
				var CODE_W_E_F_cell = cell("TD", "label", "", "CODE_W_E_F"+(i +1), CODE_W_E_F, 2, "", "", "", "10%", "", "", "");
				var SCH_TYPE_DESC_cell= cell("TD", "label", "", "SCH_TYPE_DESC"+(i +1), SCH_TYPE_DESC, 2, "", "", "", "6%", "", "", "");
				var ACC_GROUP_DESC_cell= cell("TD", "label", "", "ACC_GROUP_DESC"+(i +1), ACC_GROUP_DESC, 2, "", "", "", "10%", "", "", "");
				new_row.appendChild(slno);       
				new_row.appendChild(EDIT_cell); 
				new_row.appendChild(SCH_TYPE_DESC_cell);
				new_row.appendChild(ACC_GROUP_DESC_cell);	
				
				new_row.appendChild(ACC_GROUP_SNO_cell);
				new_row.appendChild(MAIN_ITEM_DESC_cell); 
				new_row.appendChild(SUB_ITEM_DESC_cell);
				new_row.appendChild(ACCOUNT_HEAD_CODE_cell);
				new_row.appendChild(ACC_CODE_SNO_cell);
				new_row.appendChild(SUB_ITEM_SNO_cell);
				new_row.appendChild(MAIN_ITEM_SNO_cell);
				new_row.appendChild(ACC_DESC_cell);
				new_row.appendChild(delete_cell);
				tbody.appendChild(new_row);
			}
		} else if (process_code == 2 || process_code == 5) {
			 
			var rows = bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;
			if (parseInt(rows) >= 1) { 
				if (process_code == 5)
				{
					alert("Record Succesfully Updated  ")
					
				}else
				{
					alert("Record Succesfully Saved  ")
					 
				}
				/*document.getElementById("mainitem")[0].selected = "1";
				document.getElementById("subitem")[0].selected = "1";
				document.getElementById("ACC_GROUP")[0].selected = "1";
				document.getElementById("sch_type")[0].selected = "1";
				document.getElementById("ac").value = "";
				document.getElementById("acdesc").value = "";*/
				new_transaction(9, 1);
			} else {

				alert(" Record Already Exists");
			}    

		} else if (process_code == 3 || process_code == 4) 
		{ 
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) >= 1) 
			{
				if (process_code == 4)
				{
					
				}else
				{
				alert("Record Succesfully Deleted  ")
				}
				new_transaction(9, 1); 
			} else {

				alert(" Record Already Exists");
			}

		}else if (process_code == 6) 
		{	
			var acv = bR.getElementsByTagName("acv")[0].firstChild.nodeValue;
			document.getElementById("acdesc").value = acv;
			var count = bR.getElementsByTagName("count")[0].firstChild.nodeValue;
			var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
			if (parseInt(status)==1)
			{
				alert("Invalid Account Head Code!!!!");				 
				document.getElementById("ac").value = "";
				document.getElementById("acdesc").value = "";
		
			}else
			{
				if (parseInt(count) > 0)
				{
					alert("This Account Head Code Already Exists  !!!!");
					document.getElementById("mainitem")[0].selected = "1";
					document.getElementById("subitem")[0].selected = "1";
					document.getElementById("ACC_GROUP")[0].selected = "1";
					document.getElementById("sch_type")[0].selected = "1";
					document.getElementById("ac").value = "";
					document.getElementById("acdesc").value = "";
				}
			}
		} else if (process_code == 7) 
		{
			
			document.getElementById("subitem").options.length = 1;  
			var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			//if (len==0) 			alert(status+"\n-------------------------------")
			
			for (i = 0; i < len; i++) {
				var sno = bR.getElementsByTagName("SUB_ITEM_SNO")[i].firstChild.nodeValue;
				var name = bR.getElementsByTagName("SUB_ITEM_DESC")[i].firstChild.nodeValue;
				addOption(document.getElementById("subitem"), name, sno) 
			}
			
		}
		
	} 
}

