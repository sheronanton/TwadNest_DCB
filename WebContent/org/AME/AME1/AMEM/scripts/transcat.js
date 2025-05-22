var process_code = 0;
var selected_type;
var sel_type = 0;
var url = "";
var flag = 1;
var flag_c = 0;
var tr_amt = 0;


function ckbox(a)
{
	document.getElementById("famt"+a).value=0;
}
function verify(processcode) {

	var process_code = processcode;
	var xmlobj = createObject();
	url = "../../../../../Pms_Ame_Verification";
	var params = "";
	var sch_sno = 0;
	var pyear=0;
	var pmonth=0;
	var flag=0;
	try
	{
		sch_sno =parseInt(document.getElementById("sch_sno").value);
		pyear = parseInt(document.getElementById("pyear").value);
		pmonth =parseInt(document.getElementById("pmonth").value);
		flag=0;
	} catch (e) 
	{
		alert(e);
		flag=1;
	}
	 
	if (flag==0 )
	{
		params = "p_year="+pyear+"&p_month="+pmonth+"&process_code="+process_code+"&p_sch_sno="+sch_sno;
		xmlobj.open("POST", url, true);
		xmlobj.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlobj.setRequestHeader("Content-length", params.length);
		xmlobj.setRequestHeader("Connection", "close");
		// Annual Maintenance Estimate Entry
		xmlobj.onreadystatechange = function() 
		{
			verify_process(xmlobj);
		}
		xmlobj.send(params);
	}else
	{
		alert("Give valid input!!!!");
	}
	
}
function verify_process(xml_obj)
{
	if (xml_obj.readyState == 4) {
		if (xml_obj.status == 200) {
			var bR = xml_obj.responseXML.getElementsByTagName("response")[0];
			var count = bR.getElementsByTagName("count")[0].firstChild.nodeValue;
			if (parseInt(count) > 0)
				alert("Verification done......");
		}
	}
}
function new_payment_process()
{
	var sch_sno=0;
	try {
		sch_sno = document.getElementById("sch_sno").value;
	} catch (e) {

	}
	var params = "";
	pr_status();
	flag = 0;
	var xmlobj_new = createObject();  
	url = "../../../../../PMS_AME_SCH_MONTHLY";
	document.getElementById("pr_status").value=0;
	params += "?sch_sno=" + sch_sno+"&pmonth=" +document.getElementById("pmonth").value;
	params += "&pyear=" +document.getElementById("pyear").value;
	url=url+params;    
	//xmlobj_new.open("GET", url, true);
	  
	xmlobj_new.open("POST",url,true);	
	xmlobj_new.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlobj_new.setRequestHeader("Content-length", params.length);
	xmlobj_new.setRequestHeader("Connection", "close");
	
	xmlobj_new.onreadystatechange = function()  
	{
		new_payment_process_result(xmlobj_new);
		exp2('famt8','famt2','famt9','famt10','famt11');
	}
	xmlobj_new.send(null);    
}
function new_payment_process_result(xml_obj1) 
{
	if (xml_obj1.readyState == 4) 
	{
		if (xml_obj1.status == 200) 
		{
			try    
			{
				var bR = xml_obj1.responseXML.getElementsByTagName("response")[0];
				var main_total = bR.getElementsByTagName("main_total")[0].firstChild.nodeValue;
				alert( "Actual Expenditure incurred  Rs." + main_total );
				try  
				{
					document.getElementById("famt8").value=roundNumber(parseFloat(main_total)/100000,2);
				}catch(e) { alert(e); }    
				document.getElementById("pr_status").value=1;
			}catch(e)      
			{   
				//document.getElementById("pr_status").value=1;
				alert(e);
			}
		}  
	}
}
function transaction(type, processcode) {

	process_code = processcode;
	sel_type = type;
	var xmlobj = createObject();
	url = "../../../../../Transactions";
	var params = "";
	xmlobj.open("POST", url, true);
	xmlobj.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlobj.setRequestHeader("Content-length", params.length);
	xmlobj.setRequestHeader("Connection", "close");
	// Annual Maintenance Estimate Entry
	if (sel_type == 1) 
	{

		
		/* Master Load */
		if (process_code == 7) {
			var sch_sno = 0;
			try 
			{
				sch_sno = document.getElementById("sch_sno").value;
			} catch (e) { 	}
			params = "process_code=" + process_code + "&type=" + type+ "&sch_sno=" + sch_sno;
			flag = 0;
		}
		
		/*  Annual Maintenance Estimate Entry - List*/
		if (process_code == 5) {
			 
			var sch_sno = 0;
			var pyear=0;
			try {
				sch_sno = document.getElementById("sch_sno").value;
				pyear = document.getElementById("pyear").value;
			} catch (e) { }

			params = "pyear="+pyear+"&process_code="+process_code+"&type="+type+"&sch_sno="+sch_sno;
			flag = 0;
		}      
		if (process_code == 12) 
		{
			var sch_sno = 0;
			var pyear=0;
			var refno=0;
			try
			{
				sch_sno = document.getElementById("sch_sno").value;
				pyear = document.getElementById("pyear").value;
				refno = document.getElementById("TECH_REF_NO").value;
			} catch (e) {	}
			params = "pyear="+pyear+"&refno="+refno+"&process_code=" + process_code + "&type=" + type+ "&sch_sno=" + sch_sno;
			flag = 0;
		}if (process_code == 13) {
			var sch_sno = 0;
			var pyear=0;
			var refno=0;
			try {
				sch_sno = document.getElementById("sch_sno").value;
				pyear = document.getElementById("pyear").value;
				refno = document.getElementById("TECH_REF_NO").value;
			} catch (e) {

			}

			params = "pyear="+pyear+"&refno="+refno+"&process_code=" + process_code + "&type=" + type
					+ "&sch_sno=" + sch_sno;
			flag = 0;
		}
		else if (process_code == 6) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			params = "process_code=" + process_code + "&type=" + type
					+ "&sch_sno=" + sch_sno + "&pyear=" + pyear;
			flag = 0;
		}

		/*  Annual Maintenance Estimate Entry - New*/

		else if (process_code == 1) {
			
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			if (sch_sno != 0 && pyear != 0) {
				var rowcount = document.getElementById("rowcount").value;
				params = "type=" + type + "&SCH_SNO=" + sch_sno + "&pyear="
						+ pyear;
				var j = 0;
				for (i = 1; i <= parseInt(rowcount); i++) {
					j++;
					params += "&AM_EST_AMT"+j+"="
							+ document.getElementById("famt" + i).value
							+ "&BUDGET_EST_AMT"
							+ j
							+ "="
							+ document.getElementById("samt" + i).value
							+ "&MAIN_ITEM_SNO"
							+ j
							+ "="
							+ document.getElementById("MAIN_ITEM_SNO" + i).value
							+ "&SUB_ITEM_SNO" + j + "="
							+ document.getElementById("SUB_ITEM_SNO" + i).value
							+ "&GROUP_SNO" + j + "="
							+ document.getElementById("GROUP_SNO" + i).value;
				}
				params += "&rows=" + j + "&process_code=" + process_code;
				flag = 0;

			} else {
				flag = 1;    
			}
		}  
		else if (process_code == 11 || process_code == 14 ) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			
			if (sch_sno != 0 && pyear != 0) {
				var rowcount = document.getElementById("rowcount").value;
				params = "type=" + type + "&SCH_SNO=" + sch_sno + "&pyear="
						+ pyear+"&TECH_REF_NO="+document.getElementById("TECH_REF_NO").value;
				var j = 0;
				for (i = 1; i <= parseInt(rowcount); i++) {
					j++;
					params += "&chk"+j+"="+document.getElementById("chk" +i).checked
							+"&MAIN_ITEM_SNO"+j+"="+document.getElementById("MAIN_ITEM_SNO" +i).value
							+"&SUB_ITEM_SNO" +j +"="+document.getElementById("SUB_ITEM_SNO" +i).value
							+"&GROUP_SNO" +j +"="+document.getElementById("GROUP_SNO" +i).value;
							
				}  
				params += "&rows=" +j +"&process_code=" +process_code;
				
				flag = 0;

			} else {
				flag = 1;
			}
			
			
			
		}

		/*Annual Maintenance Estimate Entry - Edit Save*/

		else if (process_code == 2) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			try
			{
				document.getElementById("flag_new").value=process_code;
			}catch(e) {}
			if (sch_sno != 0 && pyear != 0) {
				var rowcount = document.getElementById("rowcount").value;
				params = "type=" +type +"&SCH_SNO=" +sch_sno +"&pyear="
						+pyear;
				var j = 0;
				for (i = 1; i <= parseInt(rowcount); i++) {
					j++;
					params += "&AM_EST_AMT" +j +"="
							+document.getElementById("famt" +i).value;params += "&BUDGET_EST_AMT" +j +"="
							+document.getElementById("samt" +i).value;
							
					params += "&MAIN_ITEM_SNO"+j+"="+document.getElementById("MAIN_ITEM_SNO" +i).value;
					params += "&SUB_ITEM_SNO" +j +"="+document.getElementById("SUB_ITEM_SNO" +i).value;
					params += "&GROUP_SNO" +j +"="+document.getElementById("GROUP_SNO" +i).value;
				//	params += "&EST_SNO" +j +"="+document.getElementById("EST_SNO" +i).value;
				}
				document.getElementById("pr_status").value = 0;
				params += "&rows=" +j +"&process_code=" +process_code;
				flag = 0;
			} else {
				flag = 1;
			}

			/*Annual Maintenance Estimate Entry - Edit Select*/

		} else if (process_code == 3) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;

			if (sch_sno != 0 && pyear != 0) {
				document.getElementById("pr_status").value = 0;
				params = "type=" +type +"&sch_sno=" +sch_sno +"&pyear="
						+pyear;
				params += "&process_code=" +process_code;
				flag = 0;
			} else {
				flag = 1;
			}

		}

		/*Delete Process*/
		else if (process_code == 4) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			if (sch_sno != 0 && pyear != 0) {
				params = "process_code=" +process_code +"&type=" +type
						+"&sch_sno=" +sch_sno +"&pyear=" +pyear;
				flag = 0;
			} else {
				flag = 1;
			}
		}
		if (process_code == 5) 
		{
			var sch_sno = 0;
			var pyear=0;
			try {
				sch_sno = document.getElementById("sch_sno").value;
				pyear = document.getElementById("pyear").value;
			} catch (e) {

			}
		}
	}

	/*Scheme Performance Entry*/

	if (sel_type == 2 || sel_type == 4) {
		/*Scheme Performance  New Entry*/
		if (process_code == 1) 
		{  
			flag=0;
			var rowcount = document.getElementById("row_count").value;
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			var dataflag = document.getElementById("dataflag").value;
			var pmonth = 0;
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 3;
			}
			if (sch_sno != 0 && pyear != 0 && pmonth != 0)
			{
				params = "dataflag=" +dataflag +"&type=" +type +"&SCH_SNO="
						+sch_sno +"&pyear=" +pyear +"&pmonth=" +pmonth;
				var j = 0, famt = "0";
				for (i=0;i<parseInt(rowcount);i++) 
				{ 
					var MANDATORY_va ="N";     
					try { document.getElementById("MANDATORY"+(i+1)).value;}catch(e) {}
					try {
						
						 
						famt = document.getElementById("famt"+(i)).value;
						j++;
						params += "&oldamt"+j+"="+document.getElementById("oldamt"+(i+1)).value
								+"&QTY_OR_AMOUNT"+j+"="+famt+"&PERFORM_DESC_SNO"+j+"="
								+document.getElementById("PERFORM_DESC_SNO"+(i)).value+"&DATATYPE"+j+"="+document.getElementById("DATATYPE" +(i)).value
					} catch (e) {

					}  
					 
					if (MANDATORY_va == "y" || MANDATORY_va == "Y") 
					{
						if (famt == "")
						{
							flag = 1;
							document.getElementById("pr_status").value = 1;
							break;
						} else {
							flag = 0;
						}
					}
				}
				params += "&rows=" +j +"&process_code=" +process_code
						+"&flag=" +document.getElementById("flag_c").value;

			} else {
				flag = 1;
			}
			   
		}

		//  Scheme Performance For Financial Year  DETELE REQUEST 
		else if (process_code == 4) {
			// delete for Scheme Performance For Financial Year 
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			if (sch_sno != 0 && pyear != 0) {
				params = "process_code=" +process_code +"&type=" +type
						+"&sch_sno=" +sch_sno +"&pyear=" +pyear;
				flag = 0;
			} else {
				flag = 1;
			}

		}
		/*   Actual Expenditure incurred    List */
		else if (process_code == 5) {
			flag_c = document.getElementById("flag_c").value;
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c;
			flag = 0;
		}

		/*Design Quanttiy get from table & Quantity of Water actually Supplied*/

		else if (process_code == 7) {
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&SCH_SNO=" +document.getElementById("sch_sno").value
					+"&pyear=" +document.getElementById("pyear").value
					+"&pmonth=" +pmonth;
			flag = 0;

		} else if (process_code == 8) {
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&SCH_SNO=" +document.getElementById("sch_sno").value
					+"&pyear=" +document.getElementById("pyear").value
					+"&pmonth=" +pmonth;
			flag = 0;

		} else if (process_code == 9) {
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&SCH_SNO=" +document.getElementById("sch_sno").value
					+"&pyear=" +document.getElementById("pyear").value
					+"&pmonth=" +pmonth;
			flag = 0;
		} else if (process_code == 6) {
			flag_c = document.getElementById("flag_c").value
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;
			var fmonth = 0;
			try {
				fmonth = document.getElementById("pmonth").value;
			} catch (e) {
				fmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c +"&pmonth=" +fmonth +"&fyear="
					+fyear +"&sch=" +sch;
			flag = 0;
		}
		else if (process_code == 19) {
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&SCH_SNO=" +document.getElementById("sch_sno").value
					+"&pyear=" +document.getElementById("pyear").value
					+"&pmonth=" +pmonth;
			flag = 0;

		} else if (process_code == 20) {
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&SCH_SNO=" +document.getElementById("sch_sno").value
					+"&pyear=" +document.getElementById("pyear").value
					+"&pmonth=" +pmonth;
			flag = 0;
  
		} else if (process_code == 21) {
			try {
				pmonth = document.getElementById("pmonth").value;
			} catch (e) {
				pmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&SCH_SNO=" +document.getElementById("sch_sno").value
					+"&pyear=" +document.getElementById("pyear").value
					+"&pmonth=" +pmonth;
			flag = 0;
		}
		else if (process_code == 10) {
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;

			var fmonth = 0;
			var rowcount2 = document.getElementById("rowcount2").value;
			try {
				fmonth = document.getElementById("pmonth").value;
				params = "&process_code=" +process_code +"&type=" +type
						+"&pmonth=" +fmonth +"&fyear=" +fyear +"&sch="
						+sch;
				for (i = 1; i <= parseInt(rowcount2); i++) {
					params += "&eamt"
							+i
							+"="
							+document.getElementById("eamt" +i).value
							+"&PERFORM_DESC_SNO=3&MAIN_ITEM_SNO"
							+i
							+"="
							+document.getElementById("MAIN_ITEM_SNO" +i).value
							+"&GROUP_SNO" +i +"="
							+document.getElementById("GROUP_SNO" +i).value
							+"&SUB_ITEM_SNO" +i +"="
							+document.getElementById("SUB_ITEM_SNO" +i).value;
				}
				params += "&rows=" +rowcount2;
			} catch (e) {
				fmonth = 0;
			} 
			flag = 0;
		}

		// Schemence Performance Edit 
		else if (process_code == 11) {
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;
			var fmonth = 0;
			try {
				fmonth = document.getElementById("pmonth").value;
			} catch (e) {
				fmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c +"&pmonth=" +fmonth +"&fyear="
					+fyear +"&sch=" +sch;
			flag = 0;
		} else if (process_code == 13) 
		{
			// Scheme Performance For Financial Year  Edit Request made to Servlet
			var fyear = 0, fmonth = 0;
			var sch = document.getElementById("sch_sno").value;
			fyear = document.getElementById("pyear").value;
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c +"&fyear=" +fyear +"&sch=" +sch;
			flag = 0;
		} else if (process_code == 15) {
			flag_c = document.getElementById("flag_c").value;
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c;
			flag = 0;
		}else if (process_code == 16) {
			flag_c = document.getElementById("flag_c").value;
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c;
			flag = 0;
		}else if (process_code == 17) {
			pyear = document.getElementById("pyear").value;
			sch_sno = document.getElementById("sch_sno").value;
			params = "process_code=" +process_code +"&type=" +type
					+"&pyear='" +pyear+"'&sch_sno="+sch_sno; 
			flag = 0;
		}else if (process_code == 18) {
			pyear = document.getElementById("pyear").value;
			pmonth = document.getElementById("pmonth").value;
			sch_sno = document.getElementById("sch_sno").value;
			params = "process_code=" +process_code +"&type=" +type
					+"&pyear=" +pyear+"&pmonth="+pmonth+"&sch_sno="+sch_sno; 
			flag = 0;
		}
		//   Thrid Form							
	} else if (sel_type == 3) {
		if (process_code == 1) {
			var rowcount = document.getElementById("rowcount").value;
			params = "type=" +type +"&SCH_SNO="+document.getElementById("sch_sno").value +"&pyear="+document.getElementById("pyear").value;
			var j = 0;
			for (i = 1; i <= parseInt(rowcount); i++) {
				if (document.getElementById("chk" +i).checked) {
					j++;
					params += "&AM_EST_AMT"+j+"="+document.getElementById("famt" +i).value+"&MAIN_ITEM_SNO"+j+"="
							+document.getElementById("MAIN_ITEM_SNO" +i).value+"&SUB_ITEM_SNO" +j +"="
							+document.getElementById("SUB_ITEM_SNO" +i).value+"&GROUP_SNO" +j +"="
							+document.getElementById("GROUP_SNO" +i).value;
				}
			}
			params += "&rows=" +j +"&process_code=" +process_code;
			flag = 0;
		} else if (process_code == 11 || process_code == 14) {
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;
			var fmonth = 0;
			fmonth = document.getElementById("pmonth").value;
			try {
				// row.style.display = ''; 
				fmonth = document.getElementById("pmonth").value;
			} catch (e) {
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&pmonth=" +fmonth +"&fyear=" +fyear +"&sch=" +sch;
			flag = 0;
		} else if (process_code == 12) {
			var cond = document.getElementById("cond").value
			params = "process_code=" +process_code +"&type=" +type
					+"&cond=" +cond;
			flag = 0;
		} else if (process_code == 6) {
			flag_c = document.getElementById("flag_c").value
			params = "process_code=" +process_code +"&type=" +type
					+"&flag_c=" +flag_c;
			flag = 0;
		} else if (process_code == 7) {
			flag_c = document.getElementById("flag_c").value
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;
			var fmonth = 0;
			try {
				fmonth = document.getElementById("pmonth").value;
			} catch (e) {
				fmonth = 0;
			}
			params = "process_code="+process_code+"&type="+type
					+"&flag_c="+flag_c+"&pmonth="+fmonth+"&fyear="
					+fyear +"&sch=" +sch;
			flag = 0;
		} else if (process_code == 5) 
		{
			var row=document.getElementById("exp_id");
			// row.style.display = '';
			var sch_sno=document.getElementById("sch_sno").value;
			var pyear=document.getElementById("pyear").value;
			var pmonth=document.getElementById("pmonth").value;
			flag_c=document.getElementById("flag_c").value
			params="process_code="+process_code+"&type="+type
			     +"&pyear="+pyear+"&sch_sno="+sch_sno+"&pmonth="+pmonth+"&flag_c="+flag_c;			
			flag = 0;
		}else if (process_code == 15) 
		{
			var row=document.getElementById("exp_id");  
			// row.style.display = '';
			var sch_sno=document.getElementById("sch_sno").value;
			var pyear=document.getElementById("pyear").value;
			var pmonth=document.getElementById("pmonth").value;
			flag_c=document.getElementById("flag_c").value
			params="process_code="+process_code+"&type="+type
			     +"&pyear="+pyear+"&sch_sno="+sch_sno+"&pmonth="+pmonth+"&flag_c="+flag_c;			
			flag = 0;
			document.getElementById("pr_status").value=0;
		} else if (process_code == 16) 
		{
			var row=document.getElementById("exp_id");  
			// row.style.display = '';
			var sch_sno=document.getElementById("sch_sno").value;
			var pyear=document.getElementById("pyear").value;
			flag_c=document.getElementById("flag_c").value
			params="process_code="+process_code+"&type="+type
			     +"&pyear="+pyear+"&sch_sno="+sch_sno+"&pmonth=0&flag_c="+flag_c;			
			flag = 0;
			document.getElementById("pr_status").value=0;
		} else if (process_code == 17) 
		{
			var row=document.getElementById("exp_id");  
			// row.style.display = '';
			var sch_sno=document.getElementById("sch_sno").value;
			var pyear=document.getElementById("pyear").value;
			flag_c=document.getElementById("flag_c").value
			params="process_code="+process_code+"&type="+type
			     +"&pyear="+pyear+"&sch_sno="+sch_sno+"&pmonth=0&flag_c="+flag_c;			
			flag = 0;
			document.getElementById("pr_status").value=0;
		}  else if (process_code == 4) {
			// delete for Scheme Performance   
			var sch_sno=document.getElementById("sch_sno").value;
			var pyear=document.getElementById("pyear").value;
			var fyear=document.getElementById("pyear").value;
			var fmonth=0;
			try {
				fmonth=document.getElementById("pmonth").value;
			} catch (e)
			{
				fmonth = 0;
			}			
			if (sch_sno != 0 && pyear != 0) 
			{
				params="process_code="+process_code+"&type="+type
						+"&sch_sno="+sch_sno+"&pyear="+pyear
						+"&pmonth=" +fmonth;
				flag = 0;
			} else 
			{
				flag = 1;
			}
		}
	}

	/*    
	  Scheme Performance  New ,Edit
	  5-1 
	  5-2
	  5-3 
	  5-4
	  5-5
	  5-6
	  5-7  ->  New Data Stored to DB 
	 */

	if (sel_type == 5) // /////////////// TYPE 5 ////////////////////////
	{
		if (process_code == 1) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			//var mainestamt = document.getElementById("mainestamt").value;
		//	var revbudamt = document.getElementById("revbudamt").value;
			var budamt = document.getElementById("budamt").value;			
			var ORG_REV= document. bud.ORG_REV;	
			var val=fun_val(ORG_REV);
			 
			var adminref = document.getElementById("adminref").value;
			var adminrefdate = document.getElementById("adminrefdate").value;
			var ref = document.getElementById("ref").value;
			var refdate = document.getElementById("refdate").value;
			var key_value = document.getElementById("key_value").value;
			if (sch_sno != 0 && pyear != 0 && refdate!="" && ref!="" && adminref!="" && adminrefdate!="") 
			{
				params = "process_code=" +process_code +"&type=" +type
						+"&SCH_SNO=" +sch_sno +"&pyear=" +pyear
						+"&budamt=" +budamt;
				//params += "&key_value=" +key_value +"&ref=" +ref
					//	+"&refdate=" +refdate +"&revbudamt=" +revbudamt
						//+"&mainestamt=" +mainestamt;
				params += "&key_value=" +key_value +"&ref=" +ref
					+"&refdate=" +refdate +"&adminref=" +adminref
					+"&adminrefdate="+adminrefdate+"&ORG_REV="+val;
				
				flag = 0;
			} else {
				flag = 1;
			}
		}

		else if (process_code == 2) 
		{
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
		//	var mainestamt = document.getElementById("mainestamt").value;
		//	var revbudamt = document.getElementById("revbudamt").value;
			var budamt = document.getElementById("budamt").value;
			var ref = document.getElementById("ref").value;
			var refdate = document.getElementById("refdate").value;
			var key_value = document.getElementById("key_value").value;
			var adminref = document.getElementById("adminref").value;
			var adminrefdate = document.getElementById("adminrefdate").value;
			
			
			
			if (sch_sno != 0 && pyear != 0 && refdate!="" && ref!="" && adminref!="" && adminrefdate!="") {
				params = "process_code=" +process_code +"&type=" +type
						+"&SCH_SNO=" +sch_sno +"&pyear=" +pyear
						+"&budamt=" +budamt;
				params += "&key_value=" +key_value +"&ref=" +ref
						+"&refdate=" +refdate+"&adminref=" +adminref
						+"&adminrefdate="+adminrefdate+"&ORG_REV="+val;
				flag = 0;
			} else {
				flag = 1;
			}
		}

		else if (process_code == 6) {
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;
			var fmonth = 0;
			try {
				fmonth = document.getElementById("pmonth").value;
			} catch (e) {
				fmonth = 0;
			}
			params = "process_code=" +process_code +"&type=" +type
					+"&pmonth=" +fmonth +"&fyear=" +fyear +"&sch=" +sch;
			flag = 0;

		}
		//    Budget Estimate DELETE  Request 
		else if (process_code == 4) {
			var sch_sno = document.getElementById("sch_sno").value;
			var pyear = document.getElementById("pyear").value;
			var EST_SNO= document.getElementById("EST_SNO").value;
			if (sch_sno != 0 && pyear != 0) {
				params = "process_code=" +process_code +"&type=" +type
						+"&sch_sno=" +sch_sno +"&pyear=" +pyear+"&EST_SNO="+EST_SNO;
				flag = 0;
			} else {
				flag = 1;
			}
  
		}
		else if (process_code == 9) 
		{
			try
			{
				params = "&process_code=" +process_code
				+"&type=" +type +"&sch=" 
				+document.getElementById("sch_sno").value
				params += "&pmonth=" +document.getElementById("pmonth").value;
				params += "&pyear=" +document.getElementById("pyear").value;
				flag = 0;
			}catch(e) {}
			 
		}
		
		//    Actual Expenditure  Break Up Data Add to DB
		else if (process_code == 7) 
		{
			//         document.forms["breakup"].submit();
			var amount = 0;
			var rowcount = document.getElementById("tot_row").value;
			params = "tot_row=" +rowcount +"&process_code=" +process_code+"&type=" +type +"&sch="+document.getElementById("sch").value
			params += "&pmonth=" +document.getElementById("pmonth").value;
			params += "&pyear=" +document.getElementById("pyear").value;
			params += "&MAIN_ITEM_SNO="+document.getElementById("MAIN_ITEM_SNO").value
			params += "&SUB_ITEM_SNO="+document.getElementById("SUB_ITEM_SNO").value
			params += "&GROUP_SNO="+document.getElementById("GROUP_SNO").value
			params += "&dataflag=" +document.getElementById("dataflag").value
		 
			var c = 0;
			var hidden_tot_row = document.getElementById("hidden_tot_row").value;
			var amt = 0;
			for (i = 1; i <= parseInt(hidden_tot_row); i++) 
			{
				amt = parseFloat(amt)+parseFloat(document.getElementById("hidden_net_amt"+i).value);
				
			}

			tr_amt = amt;
			for (i = 1; i <= parseInt(rowcount); i++) 
			{
				var billno = document.getElementById("billno" +i).value;
				if (billno != "") 
				{
					c++;  
					amount = parseFloat(amount)+parseFloat(document.getElementById("amt" +i).value);
					params += "&billno"+i+"="+document.getElementById("billno" +i).value
					params += "&billdate" +i +"="+document.getElementById("billdate" +i).value;
					params += "&vouno" +i +"="+document.getElementById("vouno" +i).value
					params += "&voudate" +i +"="+document.getElementById("voudate" +i).value;
					params += "&part" +i +"="+document.getElementById("part" +i).value;
					params += "&amt" +i +"="+document.getElementById("amt" +i).value;
					params += "&camt" +i +"="+document.getElementById("camt" +i).value
					params += "&damt" +i +"="+document.getElementById("damt" +i).value
					params += "&cb" +i +"="+document.getElementById("cb" +i).value
					params+= "&crdr" +i +"="+document.getElementById("crdr" +i).value
					flag = 0;
				}
			}

			if (c == 0)
				flag = 1;
			else
				flag = 0;

		} else if (process_code == 10) {
			var sch = document.getElementById("sch_sno").value;
			var fyear = document.getElementById("pyear").value;

			params = "process_code=" +process_code +"&type=" +type
					+"&fyear=" +fyear +"&sch=" +sch;
			flag = 0;
		}

	}
	if (sel_type == 6) 
	{
		if (process_code == 1)
		{
			params = "process_code=" +process_code +"&type=" +type;
			flag = 0;
		} else if (process_code == 2 || process_code == 5) 
		{
			var mainitem = document.getElementById("mainitem").value;
			var subitem = document.getElementById("subitem").value;
			var ac = document.getElementById("ac").value;
			var wef = document.getElementById("wef").value;
			var astatus = document.getElementById("astatus").value;
			var sch_type = document.getElementById("sch_type").value;
			var ACC_GROUP = document.getElementById("ACC_GROUP").value;
			params = "ACC_GROUP_SNO="+ACC_GROUP+"&process_code=" +process_code +"&type=" +type+"&subitem=" +subitem +"&mainitem=" +mainitem;
			params += "&wef=" +wef +"&ac=" +ac +"&astatus=" +astatus+"&sch_type="+sch_type;
			 
			flag = 0;
		} else if (process_code == 3 || process_code == 4)  
		{
			var acsno = document.getElementById("acsno").value;
			params = "process_code=" +process_code +"&type=" +type+"&ACC_CODE_SNO=" +acsno;
			flag = 0;
		}
	}
	//            Commaon for All
	if (parseInt(flag) == 0) 
	{
		xmlobj.onreadystatechange = function() 
		{
			transaction_process(xmlobj);
		}
		xmlobj.send(params);
	} else 
	{
		alert("Please Enter All Mandatory Inputs...")
	}
}
function fun_val(ORG_REV)
{
var fin_val;

 for (i=0; i < ORG_REV.length; i++) 
 {
        if (ORG_REV[i].checked==true) 
        {
        	fin_val=ORG_REV[i].value;
        }
 }
 
 return fin_val;
}
 			
function transaction_process(xml_obj) {
	if (xml_obj.readyState == 4) {
		if (xml_obj.status == 200) {
			transaction_process_result(xml_obj);
			flag = 1;
		}
	}
}



function transaction_process_result(xml_obj) {

	var bR = xml_obj.responseXML.getElementsByTagName("response")[0];

	if (sel_type == 1) 
	{
		if (process_code == 1) 
		{
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) == 1) {
				alert("Record Succesfully Saved  ")				
				//transaction(1, 5);
			} else {
				alert(" Record Not Saved ")
			}

		} else if (process_code == 11 || process_code == 14) 
		{
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) > 0) 
			{
				if (process_code == 11)
				alert("Items added Succesfully  ");
				else if (process_code == 14)
				alert("Items updated Succesfully  ");
				
	//	transaction(1, 7);  
				try
				{    
					transaction(1,13);
					
				}catch(e) {}
			} else {
				alert(" Items already added for selected scheme and Financial Year ")
			}  

		}  
		else if (process_code == 2) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) >= 1) 
			{
				alert("Record Succesfully Updated  ")
				transaction(1, 5);  
			}
		} 
		// Annual Maintenance Estimate Entry - List
		//    List of of A.M Edit 
		else if (process_code == 3) {
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("rowcount").value = len;
			var tbody = document.getElementById("edata");
			var tbody1 = document.getElementById("edata1");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			for (t = tbody1.rows.length - 1; t >= 0; t--) {
				tbody1.deleteRow(0);
			}
			document.getElementById("pr_status").value = 1;
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var Desc = cell("TD", "label", "", "", "Description of Items", 2,"lbl", "", "", "", "center", "", "");
			var Amount1 = cell("TD", "label", "", "","Budget Estimate   Amount", 2, "lbl", "", "", "", "center","", "");
			var Ammount2 = cell("TD", "label", "", "","Annual Maint.Estimate Amount", 2, "lbl", "", "", "","center", "", "");
			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			head_row.appendChild(Ammount2);
			tbody.appendChild(head_row);
			var bottom_head_row = cell("TR", "", "", "row0", "", 2, "", "", "","", "", "", "");
			var bottom_lable1 = cell("TD", "label", "", "", "Sl.No", 2, "lbl","", "", "2%", "", "", "");
			var clr="";
			bottom_head_row.appendChild(bottom_lable1);
			for ( var i = 0; i < len; i++) {
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				try {
					var bottom_cell = cell("TD", "label", "", "MAIN_ITEM_DESC"+(i +1), MAIN_ITEM_DESC, 2, "lbl", "", clr, "","left", "", "");
					bottom_head_row.appendChild(bottom_cell);
					tbody1.appendChild(bottom_head_row);
				} catch (e) {
				}
			}
			for ( var i = 0; i < len; i++) {
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var amt = xmlValue(bR, "amt", i);
				var EST_SNO = xmlValue(bR, "EST_SNO", i);
				var APPLY_CENTAGE = xmlValue(bR, "APPLY_CENTAGE", i);
				if (parseInt(SUB_ITEM_DESC) == 0 || SUB_ITEM_DESC == null)
					SUB_ITEM_DESC = "";
				else
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;
				var clr = "";
				if (parseInt(EST_SNO) == 0)
					clr = "color:red";
				else
					clr = "color:green";
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%", "","", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var APPLY_CENTAGE_cell = cell("TD", "input", "hidden","APPLY_CENTAGE" +(i +1), APPLY_CENTAGE, 2, "tdText","", "", "2%", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "", "", "");
				var clr = "";
				if (parseInt(APPLY_CENTAGE) == 1)
					clr = "color:green";
				else
					clr = "";
				var MAIN_ITEM_DESC_cell = "";
				if (parseInt(GROUP_SNO) == 1)
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", clr, "", "left", "", "");
				else
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", " ", "85%", "left", "", "");
				var centage_readonly = "";
				if (parseInt(MAIN_ITEM_SNO) == 9) {
					centage_readonly = "#readonly";
				}
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 2, "tb4", ""," ", "", "center", "", "");
				var amt1 = cell("TD", "input", "text", "famt" +(i +1), "", 6,"", "", centage_readonly, "", "center", "onKeyup","isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',2)");
				var amt2 = cell("TD", "input", "hidden", "samt" +(i +1), "",6, "", "", " ", "", "center", "onKeyup","isInteger(this,9,event)");
				var EST_SNO_cell = cell("TD", "input", "hidden", "EST_SNO"+(i +1), EST_SNO, 2, "tb2", "", " ", "", "center","", "");
				new_row.appendChild(MAIN_ITEM_SNO_cell);
				new_row.appendChild(GROUP_SNO_cell);
				new_row.appendChild(APPLY_CENTAGE_cell);
				new_row.appendChild(SUB_ITEM_SNO_cell);
				new_row.appendChild(row_cell);
				new_row.appendChild(MAIN_ITEM_DESC_cell);
				new_row.appendChild(amt2);
				new_row.appendChild(amt1);
				new_row.appendChild(EST_SNO_cell);
				tbody.appendChild(new_row);
			}
			var y_count = xml_obj.responseXML.getElementsByTagName("y_count")[0].firstChild.nodeValue;
			if (y_count > 0) {
				//alert(" Record Already Exists for Selected Financial Year and Scheme");
				document.getElementById("add").disabled = true;
				var new_row1 = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var edit_cell = cell("TD", "A", "", "", "EDIT", "EDIT", "","javascript:edit_bud2(" +len +")", "", "", "", "", "");
				new_row1.appendChild(edit_cell);
				for ( var i = 0; i < len; i++) {
					var amt = xmlValue(bR, "amt", i, 2);
					var amt_d = "";
					if (parseFloat(amt) >= 0) 
					{
						amt_d = roundNumber(amt, 2);
					} else {
						amt_d = "";
					}
					var amt1 = cell("TD", "label", "", "lbl" +(i +1), amt_d,2, "", "", " ", "", "center", "onKeyup","isInteger(this,9,event,'famt" +(i +1)
									+"'),digit_control('famt" +(i +1)+"',2)");
					new_row1.appendChild(amt1);

				}
				tbody1.appendChild(new_row1);  
			}
		} else if (process_code == 4) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			alert("Record Succesfully Deleted ");
			transaction(1, 3);
		}
		else if (process_code == 5) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("rowcount").value = len;   
			var rc=xml_obj.responseXML.getElementsByTagName("rc")[0].firstChild.nodeValue;
			var TECH_REF_NO;
			var f_new=0;
			try
			{
				 TECH_REF_NO=xml_obj.responseXML.getElementsByTagName("TECH_REF_NO")[0].firstChild.nodeValue; 
				 TECH_REF_NO=(TECH_REF_NO==0)?"":TECH_REF_NO;	
				 document.getElementById("tech_sanc").innerHTML=" Maint.Estimate Sanction Ref. :&nbsp;&nbsp;&nbsp;&nbsp;<font color=blue>" + TECH_REF_NO+"</blue>";
			}catch(e) {}

			try
			{
				f_new=document.getElementById("flag_new").value;
			}catch(e) {}
			
			if (rc > 0 && f_new!=2)   
				alert(" Annual Maintenance Estimate details already entered.....Click Report ");
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var Desc = cell("TD", "label", "", "", "Description of Items", 2,"lbl", "", "", "", "center", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var Amount1 = cell("TD", "label", "", "","Budget Estimate   Amount", 2, "lbl", "", "", "", "center","", "");
			var Ammount2 = cell("TD", "label", "", "","Annual Maint.Estimate Amount", 2, "lbl", "", "", "","center", "", "");
			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			//head_row.appendChild(rowh_ck);
			head_row.appendChild(Ammount2);
			tbody.appendChild(head_row);
			var row=0;
			for ( var i = 0; i < len; i++)
			{
				row=(i+1);
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var APPLY_CENTAGE = xmlValue(bR, "APPLY_CENTAGE", i);
				if (parseInt(SUB_ITEM_DESC) == 0) {
					SUB_ITEM_DESC = " ";
				} else {
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				}
				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%", "","", "");
				var APPLY_CENTAGE_cell = cell("TD", "input", "hidden","APPLY_CENTAGE" +(i +1), APPLY_CENTAGE, 2, "tdText","", "", "2%", "", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),"y", 2, "", "", "", "", "", "onchange", "ckbox("+(i+1)+")");
				var clr = "";
				if (parseInt(APPLY_CENTAGE) == 1)
					clr = "color:green";
				else
					clr = "";
				var MAIN_ITEM_DESC_cell = "";
				if (parseInt(GROUP_SNO) == 1)
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", clr, "", "left", "", "");
				else
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", " ", "85%", "left", "", "");
				var group_2_readonly = "";
				if (parseInt(GROUP_SNO) == 2) {  
					group_2_readonly = "text-align:right#readonly";
				} else {
					group_2_readonly = "text-align:right";
				}
				var val="";
				try
				{
					if (rc > 0)
					{
					val=xmlValue(bR, "AM_EST_AMT_D", i);
					try
					{
						var s=new String (val);
						var  ind=s.split('.');
						if (ind[0].length==0)
								val="0"+val;							
					}catch(e) {}		  
					}  
				}catch(e)
				{
					val="";
				}
			
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 6, "", ""," ", "", "center", "", "");
				var amt1 = cell("TD", "input", "text", "famt" +(i +1), val, 6,"", "", group_2_readonly, "", "right","onKeyup#onKeyDown", "isInteger(this,9,event,'famt"
								+(i +1) +"'),digit_control('famt" +(i +1)
								+"',2)#key_pass(event,'famt" +(i +1)
								+"','famt" +(i +2) +"')");
				var amt2 = cell("TD", "input", "hidden", "samt" +(i +1), 0,6, "", "", " ", "", "center", "", "");
				new_row.appendChild(MAIN_ITEM_SNO_cell);
				new_row.appendChild(GROUP_SNO_cell);
				new_row.appendChild(APPLY_CENTAGE_cell);
				new_row.appendChild(SUB_ITEM_SNO_cell);
				new_row.appendChild(row_cell);				
				new_row.appendChild(MAIN_ITEM_DESC_cell);
				new_row.appendChild(amt2);
				new_row.appendChild(amt1);    
				tbody.appendChild(new_row);
			}
			document.getElementById("rate").value=xmlValue(bR, "rate",0);;
			
			var cen_per = xmlValue(bR, "rate",0);;
			var cen_per = document.getElementById("rate").value;
			if (cen_per==0 || cen_per=="" || cen_per=="NaN")
			{
				
				alert("\n Please Note: \n-------------\n\n \t Centage percentage Not Entered for selected Scheme \n\n\t To Enter go to AME Lookups  ----> Centage Master");
				window.location.reload(this);
				var b1=document.getElementById("add");
				b1.disabled=true;
			
			}
			
			
			
			var row_cell = cell("TD", "label", "", "", "", 2, "lbl","", "", "2%", "", "", "");
			var row_cell1 = cell("TD", "label", "", "", "", 2, "lbl","", "", "75%", "right", "", "");
			var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
			var total_label_cell = cell("TD", "label", "","" , "Total Centage" , 2,"lbl", "", clr, "", "right", "", "");
			var total_cent = cell("TD", "input", "text","totalcentage" , "0", 6, "", "","text-align:right#readonly", "", "right", "", "");
			var gr_row_cell = cell("TD", "label", "", "", "", 2, "lbl","", "", "2%", "", "", "");
			var gr_row_cell1 = cell("TD", "label", "", "", "", 2, "lbl","", "", "75%", "right", "", "");
			var gr_new_row = cell("TR", "", "", "row" +(i +1), (i +2), 2,"", "", "", "", "", "", "");
			var gr_total_label_cell = cell("TD", "label", "","" , "Grand Total" , 2,"lbl", "", clr, "", "right", "", "");
			var gr_total_cent = cell("TD", "input", "text","grandtotal", "0", 6, "", "","text-align:right#readonly", "", "right", "", "");
			new_row.appendChild(row_cell);
		//	new_row.appendChild(row_cell1);
			new_row.appendChild(total_label_cell);
			new_row.appendChild(total_cent);
			tbody.appendChild(new_row);
			gr_new_row.appendChild(gr_row_cell);   
		//	gr_new_row.appendChild(gr_row_cell1);  
			gr_new_row.appendChild(gr_total_label_cell);
			gr_new_row.appendChild(gr_total_cent);
			tbody.appendChild(gr_new_row);
			
			if (rc > 0)
				centage();
			
			
			
		}else if (process_code == 12) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		//	document.getElementById("rowcount").value = len;
			if (len > 0)
			{
				alert(" Items already added for selected scheme and Financial Year ")
			}  
			var tbody = document.getElementById("edatanew");    
			var table = document.getElementById("etablenew");
			var sch_name=document.getElementById("sch_sno")
		    var sch_name_v=sch_name.options[sch_name.selectedIndex].text;  
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var Desc = cell("TD", "label", "", "", "Selected items list for Scheme - " + sch_name_v, 2,"lbl", "", "color : green", "", "center", "", "");
			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			tbody.appendChild(head_row);
			var row=0;
			for ( var i = 0; i < len; i++)
			{
				row=(i+1);
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var APPLY_CENTAGE = xmlValue(bR, "APPLY_CENTAGE", i);
				if (parseInt(SUB_ITEM_DESC) == 0) {
					SUB_ITEM_DESC = " ";
				} else {
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				}

				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%", "","", "");
				var APPLY_CENTAGE_cell = cell("TD", "input", "hidden","APPLY_CENTAGE" +(i +1), APPLY_CENTAGE, 2, "tdText","", "", "2%", "", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "", "", "");
				var clr = "";
				if (parseInt(APPLY_CENTAGE) == 1)
					clr = "";
				else
					clr = "";

				var MAIN_ITEM_DESC_cell = "";
				if (parseInt(GROUP_SNO) == 1)
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", clr, "", "left", "", "");
				else
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", " ", "85%", "left", "", "");
				var group_2_readonly = "";
				if (parseInt(GROUP_SNO) == 2) {  
					group_2_readonly = "#readonly";
				} else {
					group_2_readonly = "text-align:right";
				}
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 6, "", ""," ", "", "center", "", "");
				var amt1 = cell("TD", "input", "text", "famt" +(i +1), "", 6,"", "", group_2_readonly, "", "right","onKeyup#onKeyDown", "isInteger(this,9,event,'famt"
								+(i +1) +"'),digit_control('famt" +(i +1)
								+"',2)#key_pass(event,'famt" +(i +1)
								+"','famt" +(i +2) +"')");
				var amt2 = cell("TD", "input", "hidden", "samt" +(i +1), 0,6, "", "", " ", "", "center", "", "");
				new_row.appendChild(MAIN_ITEM_SNO_cell);
				new_row.appendChild(GROUP_SNO_cell);
				new_row.appendChild(APPLY_CENTAGE_cell);
				new_row.appendChild(SUB_ITEM_SNO_cell);
				new_row.appendChild(row_cell);
				new_row.appendChild(MAIN_ITEM_DESC_cell);
				tbody.appendChild(new_row);
			}  
			
		}else if (process_code == 13) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		//	document.getElementById("rowcount").value = len;
			 
			var tbody = document.getElementById("edatanew");    
			var table = document.getElementById("etablenew");
			 
			var sch_name=document.getElementById("sch_sno")
		    var sch_name_v=sch_name.options[sch_name.selectedIndex].text;  
			 
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var Desc = cell("TD", "label", "", "", "Selected items list for Scheme - " + sch_name_v, 2,"lbl", "", "color : green", "", "center", "", "");
			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			tbody.appendChild(head_row);
			var row=0;
			for ( var i = 0; i < len; i++)
			{
				row=(i+1);
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var APPLY_CENTAGE = xmlValue(bR, "APPLY_CENTAGE", i);
				if (parseInt(SUB_ITEM_DESC) == 0) {
					SUB_ITEM_DESC = " ";
				} else {
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				}

				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;

				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%", "","", "");
				var APPLY_CENTAGE_cell = cell("TD", "input", "hidden","APPLY_CENTAGE" +(i +1), APPLY_CENTAGE, 2, "tdText","", "", "2%", "", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "", "", "");

				var clr = "";
				if (parseInt(APPLY_CENTAGE) == 1)
					clr = "";
				else
					clr = "";

				var MAIN_ITEM_DESC_cell = "";
				if (parseInt(GROUP_SNO) == 1)
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", clr, "", "left", "", "");
				else
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", " ", "85%", "left", "", "");
				var group_2_readonly = "";
				if (parseInt(GROUP_SNO) == 2) {  
					group_2_readonly = "#readonly";
				} else {
					group_2_readonly = "text-align:right";
				}

				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 6, "", ""," ", "", "center", "", "");
				var amt1 = cell("TD", "input", "text", "famt" +(i +1), "", 6,"", "", group_2_readonly, "", "right","onKeyup#onKeyDown", "isInteger(this,9,event,'famt"
								+(i +1) +"'),digit_control('famt" +(i +1)
								+"',2)#key_pass(event,'famt" +(i +1)
								+"','famt" +(i +2) +"')");
				var amt2 = cell("TD", "input", "hidden", "samt" +(i +1), 0,6, "", "", " ", "", "center", "", "");
				new_row.appendChild(MAIN_ITEM_SNO_cell);
				new_row.appendChild(GROUP_SNO_cell);
				new_row.appendChild(APPLY_CENTAGE_cell);
				new_row.appendChild(SUB_ITEM_SNO_cell);
				new_row.appendChild(row_cell);
				new_row.appendChild(MAIN_ITEM_DESC_cell);
				tbody.appendChild(new_row);
			}  
			
		}
		else if (process_code == 7) 
		{
			 
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("rowcount").value = len;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var Desc = cell("TD", "label", "", "", "Description of Items", 2,"lbl", "", "", "", "center", "", "");
			var Amount1 = cell("TD", "label", "", "","Budget Estimate   Amount", 2, "lbl", "", "", "", "center","", "");
			var Ammount2 = cell("TD", "label", "", "","Select Item", 2, "lbl", "", "", "","center", "", "");
			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			head_row.appendChild(Ammount2);
			tbody.appendChild(head_row);

			for ( var i = 0; i < len; i++)
			{
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var APPLY_CENTAGE = xmlValue(bR, "APPLY_CENTAGE", i);
				if (parseInt(SUB_ITEM_DESC) == 0) {
					SUB_ITEM_DESC = " ";
				} else {
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				}

				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;

				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%", "","", "");
				var APPLY_CENTAGE_cell = cell("TD", "input", "hidden","APPLY_CENTAGE" +(i +1), APPLY_CENTAGE, 2, "tdText","", "", "2%", "", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "", "2%", "", "", "");
				
				var dis="";
				if (GROUP_SNO=="2")
					dis="y";
				else
					dis="n";  
					  
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),dis, 2, "", "","", "", "", "", " ");
  

				var MAIN_ITEM_DESC_cell = "";
				if (parseInt(GROUP_SNO) == 1)
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", "", "", "left", "", "");
				else
					MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2,"lbl", "", " ", "85%", "left", "", "");
				var group_2_readonly = "";
				if (parseInt(GROUP_SNO) == 2) {
					group_2_readonly = "#readonly";
				} else {
					group_2_readonly = "text-align:right";
				}

				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 6, "", ""," ", "", "center", "", "");
				new_row.appendChild(MAIN_ITEM_SNO_cell);
				new_row.appendChild(GROUP_SNO_cell);
				new_row.appendChild(APPLY_CENTAGE_cell);
				new_row.appendChild(SUB_ITEM_SNO_cell);
				new_row.appendChild(row_cell);
				new_row.appendChild(MAIN_ITEM_DESC_cell);
				new_row.appendChild(Chk_cell);
				tbody.appendChild(new_row);
			}
		}
	}

	if (sel_type == 2 || sel_type == 4) 
	{
		if (process_code == 1) 
		{
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			document.getElementById("pr_status").value = 1;

			if (parseInt(rows) >= 1) {
				alert(" Scheme Performance Succesfully Saved  ")
				// transaction(2,5);
				window.close();  
				// window.location.reload(true);
			} else {

				try {
					var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
					alert("Warning :  " +msg)

				} catch (e) {

					alert("Scheme Performance Cannot be Saved  ")
				}
				window.location.reload();
			}
		}
		// Scheme Performance For Financial Year DELETE RESPONSE 
		else if (process_code == 4) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			alert("Scheme Performance  Succesfully Deleted ");
			transaction(2, 5);
		} else if (process_code == 10) {
			exp();
		}
		// Scheme performance show
		else if (process_code == 11) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			if (len == 0) 
			{
				for ( var i = 0; i < 6; i++) 
				{
					document.getElementById("famt" +(i +1)).value = 0;
				}
				
				alert(" Data Not Found for this month and year !!!")
				var b1=document.getElementById("UPDATE");
				b1.disabled=true;
				var b2=document.getElementById("itemexp");
				b2.disabled=true;
				var b3=document.getElementById("DELETE");
				b3.disabled=true;  
				   
			} else 
			{
				var b1=document.getElementById("UPDATE");
				b1.disabled=false;
				var b2=document.getElementById("itemexp");
				b2.disabled=false;    
				var b3=document.getElementById("DELETE");
				b3.disabled=false;  
				for ( var i = 0; i < len; i++)
				{
					var rem = xmlValue(bR, "REMARKS", i);
					var amt = bR.getElementsByTagName("QTY_OR_AMOUNT")[i].firstChild.nodeValue;
					 
					var DATATYPE = bR.getElementsByTagName("DATATYPE")[i].firstChild.nodeValue;
					if (parseInt(DATATYPE) == 4)
					{
						if (rem=="0")
						{
							rem="."
						}
							document.getElementById("famt" +(i)).value = rem;
							document.getElementById("oldamt" +(i +1)).value = rem;
						
					} else 
						{
						var DLIMIT ="0";
						try
						{
						DLIMIT =document.getElementById("DLIMIT" +(i +1)).value;
						}catch(e) 
						{
							 DLIMIT ="0" 
						}
						 
						 try
						 {
						document.getElementById("famt"+(i)).value = roundNumber(amt, parseInt(DLIMIT));
						}catch(e) 
						{ }
						// roundNumber(amt,2);//
						document.getElementById("oldamt" +(i +1)).value = amt;
					}
				}
				//transaction(2, 9);
			}

		} else if (process_code == 13) 
		{
		// Scheme Performance For Financial Year Edit -- Servlet Response  
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			if (len == 0) 
			{
				for ( var i = 0; i < 7; i++) 
				{
					document.getElementById("famt" +(i +1)).value = "";
				}
			} else {
				for ( var i = 0; i < 7; i++) {
					var amt = bR.getElementsByTagName("QTY_OR_AMOUNT")[i].firstChild.nodeValue;
					// document.getElementById("famt"+(i+1)).value=amt;
					// document.getElementById("oldamt"+(i+1)).value=amt;
				}
			}

			var tbody1 = document.getElementById("entdata");
			for (t = tbody1.rows.length - 1; t >= 0; t--)
			{
				tbody1.deleteRow(0);
			}
			if (len > 0) {
				//alert(" Record Already Exists for Selected Financial Year and Scheme");
				document.getElementById("save").disabled = true;

				var new_row1 = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var edit_cell = cell("TD", "A", "", "", "EDIT", "EDIT", "","javascript:edit_bud(" +len +")", "", "", "", "", "");
				new_row1.appendChild(edit_cell);
				for ( var i = 0; i < len; i++) 
				{
					var amt = bR.getElementsByTagName("QTY_OR_AMOUNT")[i].firstChild.nodeValue;
					document.getElementById("famt"+(i)).value=amt;
				//	var amt1cell = cell("TD", "label", "", "lbl" +(i +1),amt, 2, "lbl", "", " ", "", "right", "", "");
			//		new_row1.appendChild(amt1cell);

				}
				//tbody1.appendChild(new_row1);
			} else {
				document.getElementById("save").disabled = false;
			}

		} else if (process_code == 7) {
			var qty = bR.getElementsByTagName("QTY")[0].firstChild.nodeValue;
			var QTY_DESIGN = "", DESIGN_PUMPING_HOURS = "0";
			var TOTAL_NO_STAFF = "";
			var days = "0";
			var dqty = "0";
			var pqty = "0";
			var tab_count_year = "0";
			var count_month = "0";
			try {
				DESIGN_PUMPING_HOURS = bR.getElementsByTagName("DESIGN_PUMPING_HOURS")[0].firstChild.nodeValue;
			//	QTY_DESIGN = bR.getElementsByTagName("DESIGN_QTY")[0].firstChild.nodeValue;
				QTY_DESIGN = bR.getElementsByTagName("QTY_DESIGN")[0].firstChild.nodeValue;
				
				if (QTY_DESIGN=="0")
				{
						alert("Please Enter Scheme Design Qty ( Refer AME Lookups -->  Scheme AME Details ) ");					
				}
				dqty = QTY_DESIGN;
				document.getElementById("desg_hour").value = DESIGN_PUMPING_HOURS;
				try {
					TOTAL_NO_STAFF = bR.getElementsByTagName("TOTAL_NO_STAFF")[0].firstChild.nodeValue;
				} catch (e) {
					TOTAL_NO_STAFF = "0";
				}
				days = bR.getElementsByTagName("totalday")[0].firstChild.nodeValue;
				pqty = bR.getElementsByTagName("PUMPING_QTY")[0].firstChild.nodeValue;
				var PUMED_DPR=0;
				try
				{
					PUMED_DPR= bR.getElementsByTagName("PUMED_DPR")[0].firstChild.nodeValue;
				}catch(e)
				{
					PUMED_DPR=0;
				}
				
				document.getElementById("pumped_dpr").value=PUMED_DPR;  
				document.getElementById("days").value=days;
				days = bR.getElementsByTagName("totalday")[0].firstChild.nodeValue;
				tab_count_year = bR.getElementsByTagName("tab_count_year")[0].firstChild.nodeValue;
				count_month = bR.getElementsByTagName("count_month")[0].firstChild.nodeValue;
				var already=0;
				if (parseInt(count_month) > 0 || parseInt(tab_count_year) > 0)   
				{
					
					alert("Scheme Performance Data Already Exists ! ");
					already=1;
					document.getElementById("save").disabled = true;
					document.getElementById("itemwise").disabled = true;
				 
				} else {
					already=0;
					document.getElementById("save").disabled = false;
					document.getElementById("itemwise").disabled = false;  
				} 

			} catch (e) {

			}
			
			
			
			 
			if (already==0)
			{
			
					if (parseFloat(DESIGN_PUMPING_HOURS) == 0) {
						alert("Design Pumping Hours  Not Entered for Selected Scheme");
					}
					if (parseFloat(QTY_DESIGN) == 0) {
						alert("Design Quantity Not Entered for Selected Scheme");
						document.getElementById("famt0").focus();
						document.getElementById("famt0").value = 0;
					} else {
						document.getElementById("famt0").value = roundNumber(
								parseFloat(QTY_DESIGN) * days, 3);
						// Number of Days multiplied removed
						// ITs old Code
						// document.getElementById("famt0").value=roundNumber(parseFloat(QTY_DESIGN),3);
					}
					document.getElementById("famt7").value = TOTAL_NO_STAFF;// new is 7
		
					if (qty > 0) {
						document.getElementById("famt2").value = roundNumber(qty, 3); // old
																						// 2
						document.getElementById("famt1").focus();
						  
						
						
						
						
						//transaction(2, 9); 
						 transaction(2,8);
					} else {
						alert("Pumping Return Not Entered in Online DCB for Selected Month and Scheme");
					}
					document.getElementById("famt0").value = roundNumber(parseFloat(dqty) * days, 3);
					document.getElementById("famt1").value = roundNumber(parseFloat(pqty) * days, 3);
					
			}	
				  	
			set_value();
			
			
		} else if (process_code == 8) {
			var qty = bR.getElementsByTagName("NETQTY")[0].firstChild.nodeValue;
			if (qty > 0) {

				document.getElementById("famt2").value = roundNumber(qty, 3);
				transaction(2,9);
			} else {
				alert("Pumping Return Not Entered in Online DCB for Selected Month  and Scheme");
			}

		} else if (process_code == 9) {
			var netamt = 0;
			try {
				netamt = bR.getElementsByTagName("NETAMT")[0].firstChild.nodeValue;
			} catch (e) {
				netamt = 0;
			}
			if (parseFloat(netamt) > 0) {
				var flag_dmd = document.getElementById("demand_flag_cell").value;
				document.getElementById("famt" +flag_dmd).value = roundNumber(parseFloat(netamt) / 100000, 2);
				// old is 5 and new is
				  //transaction(2,18);
				  //new_payment_process();   
				 
			} else {
				alert("Pumping Return Not Entered in DCB");
			}
			
			
			
		}
			//-----------------------
			
			
			else if (process_code ==19) {
				var qty = bR.getElementsByTagName("QTY")[0].firstChild.nodeValue;
				var QTY_DESIGN = "", DESIGN_PUMPING_HOURS = "0";
				var TOTAL_NO_STAFF = "";
				var days = "0";
				var dqty = "0";
				var pqty = "0";
				var tab_count_year = "0";
				var count_month = "0";
				try {
					DESIGN_PUMPING_HOURS = bR.getElementsByTagName("DESIGN_PUMPING_HOURS")[0].firstChild.nodeValue;
				//	QTY_DESIGN = bR.getElementsByTagName("DESIGN_QTY")[0].firstChild.nodeValue;
					QTY_DESIGN = bR.getElementsByTagName("QTY_DESIGN")[0].firstChild.nodeValue;
					
					if (QTY_DESIGN=="0")
					{
							alert("Please Enter Scheme Design Qty ( Refer AME Lookups -->  Scheme AME Details ) ");					
					}
					dqty = QTY_DESIGN;
					document.getElementById("desg_hour").value = DESIGN_PUMPING_HOURS;
					try {
						TOTAL_NO_STAFF = bR.getElementsByTagName("TOTAL_NO_STAFF")[0].firstChild.nodeValue;
					} catch (e) {
						TOTAL_NO_STAFF = "0";
					}
					days = bR.getElementsByTagName("totalday")[0].firstChild.nodeValue;
					pqty = bR.getElementsByTagName("PUMPING_QTY")[0].firstChild.nodeValue;
					var PUMED_DPR=0;
					try
					{
						PUMED_DPR= bR.getElementsByTagName("PUMED_DPR")[0].firstChild.nodeValue;
					}catch(e)
					{
						PUMED_DPR=0;
					}
					
					document.getElementById("pumped_dpr").value=PUMED_DPR;  
					document.getElementById("days").value=days;
					days = bR.getElementsByTagName("totalday")[0].firstChild.nodeValue;
					tab_count_year = bR.getElementsByTagName("tab_count_year")[0].firstChild.nodeValue;
					count_month = bR.getElementsByTagName("count_month")[0].firstChild.nodeValue;
					var already=0;
					if (parseInt(count_month) > 0 || parseInt(tab_count_year) > 0)   
					{
						
						alert("Scheme Performance Data Already Exists ! ");
						already=1;
						document.getElementById("save").disabled = true;
						document.getElementById("itemwise").disabled = true;
					 
					} else {
						already=0;
						document.getElementById("save").disabled = false;
						document.getElementById("itemwise").disabled = false;  
					} 

				} catch (e) {

				}
				 
				
						if (parseFloat(DESIGN_PUMPING_HOURS) == 0) {
							alert("Design Pumping Hours  Not Entered for Selected Scheme");
						}
						if (parseFloat(QTY_DESIGN) == 0) {
							alert("Design Quantity Not Entered for Selected Scheme");
							document.getElementById("famt0").focus();
							document.getElementById("famt0").value = 0;
						} else {
							document.getElementById("famt0").value = roundNumber(
									parseFloat(QTY_DESIGN) * days, 3);
							// Number of Days multiplied removed
							// ITs old Code
							// document.getElementById("famt0").value=roundNumber(parseFloat(QTY_DESIGN),3);
						}
						document.getElementById("famt7").value = TOTAL_NO_STAFF;// new is 7
			
						if (qty > 0) {
							document.getElementById("famt2").value = roundNumber(qty, 3); // old
																							// 2
							document.getElementById("famt1").focus();
							 transaction(2,20);
						} else {
							alert("Pumping Return Not Entered in Online DCB for Selected Month and Scheme");
						}
						document.getElementById("famt0").value = roundNumber(parseFloat(dqty) * days, 3);
						document.getElementById("famt1").value = roundNumber(parseFloat(pqty) * days, 3);
				 
				
			} else if (process_code == 20) {
				var qty = bR.getElementsByTagName("NETQTY")[0].firstChild.nodeValue;
				if (qty > 0) {

					document.getElementById("famt2").value = roundNumber(qty, 3);
					transaction(2,21); 
				} else {
					alert("Pumping Return Not Entered in Online DCB for Selected Month  and Scheme");
				}

			} else if (process_code ==21) {
				var netamt = 0;
				try {
					netamt = bR.getElementsByTagName("NETAMT")[0].firstChild.nodeValue;
				} catch (e) {
					netamt = 0;
				}
				if (parseFloat(netamt) > 0) {
					var flag_dmd = document.getElementById("demand_flag_cell").value;
					document.getElementById("famt" +flag_dmd).value = roundNumber(parseFloat(netamt) / 100000, 2);
					// old is 5 and new is
					transaction(2,17);
					//new_payment_process();     
					 
				} else {
					alert("Pumping Return Not Entered in DCB");
				}
			
			
			
			
			
			
			
			
			
			
			
		} else if (process_code == 5) {
			// Scheme Performance Month Wise  List of Item Show  for new Entry 
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("row_count").value = len;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) { 
				tbody.deleteRow(0);
			}

			head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "", "","", "");
			rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "", "","", "", "");
			rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "50%", "","", "");
			var flag_s = flag_c;
			var dis_label;

			if (flag_s == "Y")
				dis_label = "Quantity(ML)";
			else
				dis_label = "Units";

			rowh_blnk = cell("TD", "label", "", "", dis_label, 2, "lbl", "","", "25%", "", "", "");
			var rowh_blnk2 = cell("TD", "label", "", "", "", 2, "lbl", "", "","25%", "", "", "");
			Desc = cell("TD", "label", "", "", "Description", 2, "lbl", "", "","", "center", "", "");
			var Desc1 = cell("TD", "label", "", "", "", 2, "lbl", "", "","", "center", "", "");
			Amount1 = cell("TD", "label", "", "", "", 2, "lbl", "", "", "5%","center", "", "");
			
			head_row.appendChild(rowh);
			// head_row.appendChild(rowh_blnk2);
			head_row.appendChild(Desc);
			
			head_row.appendChild(Desc1);
			head_row.appendChild(rowh_blnk);
			head_row.appendChild(Amount1);
			tbody.appendChild(head_row);

			for ( var i = 0; i < len; i++) {
 
				var PERFORM_DESC_SNO = bR
						.getElementsByTagName("PERFORM_DESC_SNO")[i].firstChild.nodeValue;
				var UNITS = bR.getElementsByTagName("UNITS")[i].firstChild.nodeValue;
				var new_row = cell("TR", "", "", "row" +i, (i +1), 2, "", "","", "", "", "", "");
				var row_cell = cell("TD", "label", "", "", (i +1), 2, "", "","", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +i, 0, 2,"", "", "", "2%", "", "", "");
				var PERFORM_DESC_SNO_cell = cell("TD", "input", "hidden","PERFORM_DESC_SNO" +i, PERFORM_DESC_SNO, 2, "tdText",
						"", "", "2%", "", "", "");
				var UNITS_cell = cell("TD", "label", "", "UNITS" +i, UNITS, 2,
						"amelabel", "", "", "50%", "left", "", "");
				var PERFORM_DESC = bR.getElementsByTagName("PERFORM_DESC")[i].firstChild.nodeValue;
				
				var formula = bR.getElementsByTagName("FORMULA")[i].firstChild.nodeValue;
				var DATATYPE = bR.getElementsByTagName("DATATYPE")[i].firstChild.nodeValue;
				var MANDATORY = bR.getElementsByTagName("MANDATORY")[i].firstChild.nodeValue;
				var DATATYPE_cell = cell("TD", "input", "hidden", "DATATYPE"
						+i, DATATYPE, 2, "tdText", "", "", "2%", "", "", "");
				var DISPLAYORDER = bR.getElementsByTagName("DISPLAYORDER")[i].firstChild.nodeValue;
				var TEXTBOX_NAME = bR.getElementsByTagName("TEXTBOX_NAME")[i].firstChild.nodeValue;
				if (i == 8) {
				}
				var d_limit = 0, readonly = "";

				var flow_ = 0;
				try {
					d_limit = bR.getElementsByTagName("DLIMIT")[i].firstChild.nodeValue;
					readonly = bR.getElementsByTagName("READONLY")[i].firstChild.nodeValue;

					if (parseInt(readonly) == 0 || readonly == "")
						readonly = "";
					else
						readonly = "#readonly";

				} catch (e) {
				}

				try {
					flow_ = document.getElementById("flow_").value;

				} catch (e) {
					flow_ = 0;
				}
				 
				var PERFORM_DESC_cell = "";
				var man = "";
				var sty = "";
				if (MANDATORY == "y" || MANDATORY == "Y") {
					man = "  * ";
					sty = "";
				} else {
					man = "";
				}   
    
				if (parseInt(PERFORM_DESC_SNO) == 3  ) 
				{    
        
					if (parseInt(flow_) == 2) {
						PERFORM_DESC_cell = cell("TD", "label", "","PERFORM_DESC" +(i +1),PERFORM_DESC+man,2,"lbl", "", sty, "85%", "left", "", "");
					} else {
						PERFORM_DESC_cell = cell("TD", "label", "","PERFORM_DESC"+(i+1),PERFORM_DESC,2,"lbl","", sty, "85%", "left",
								"onmouseover","toolin(event,'dv')#key_pass()");					
						}     
				} else   
				{
					PERFORM_DESC_cell = cell("TD", "label", "", "PERFORM_DESC"+(i +1), PERFORM_DESC , 2, "lbl", "", sty,"85%", "left", "", "");
				}
				var amt1 = "";
				var fun_e = "";
				var fun_n = ""

				// if (parseInt(PERFORM_DESC_SNO)==8)
				// {
				// fun_e="onblur#onKeyUp";
				// fun_n="pump_differ()#isInteger(this,9,event,'famt"+(i+1)+"')";
				// }

				 
				if (parseInt(PERFORM_DESC_SNO) == 1) {
					fun_e = "onblur#onKeyUp#onmouseover";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)
							+"')#toolin(event,'dv')";           

					// fun_n="pump_differ(),supply_differ()#isInteger(this,9,event,'famt"+(i+1)+"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 11) {
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)
							+"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 16
						|| parseInt(PERFORM_DESC_SNO) == 17) {
					fun_e = "onblur#onKeyUp";
					fun_n = "nosupply_differ('famt" +(i)
							+"')#isInteger(this,9,event,'famt" +(i) +"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 5) {
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)
							+"'),digit_control('famt" +(i +1) +"',"
							+d_limit +")";
				} else if (parseInt(PERFORM_DESC_SNO) == 3) {
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)
							+"'),digit_control('famt" +(i +1) +"',"
							+d_limit +")";
				} else if (parseInt(PERFORM_DESC_SNO) != 7
						&& parseInt(PERFORM_DESC_SNO) != 1
						&& parseInt(PERFORM_DESC_SNO) != 8
						&& parseInt(PERFORM_DESC_SNO) != 11
						&& parseInt(PERFORM_DESC_SNO) != 16
						&& parseInt(PERFORM_DESC_SNO) != 17) {

					fun_e = "onKeyUp";
					fun_n = "isInteger(this,9,event,'famt" +(i +1)
							+"'),digit_control('famt" +(i +1) +"',"
							+d_limit +")";
				}
				var oldamt = cell("TD", "input", "hidden", "oldamt" +(i +1),0, 6, "", "", "", "25%", "center", fun_e, fun_n);
				var lbl = "";
				if (parseInt(PERFORM_DESC_SNO) == 8) {
					lbl = "famt0";
				} else {
					lbl = "famt" +i;
				}
				if (parseInt(DATATYPE) != 4)
					amt1 = cell("TD", "input", "text", lbl, "", 6, "", "","text-align: right;" +readonly, "25%", "right",fun_e, fun_n);
				// else
				if (parseInt(DATATYPE) == 4)
					amt1 = cell("TD", "textarea", "", lbl, "", 2, "tarea", ""," ", "20%", "center", "", "");

				new_row.appendChild(row_cell);
				new_row.appendChild(PERFORM_DESC_SNO_cell);
				new_row.appendChild(PERFORM_DESC_cell);
				new_row.appendChild(DATATYPE_cell);
				if (DISPLAYORDER == "14") // Actual Expenditure incurred 14 is
											// display order
				{
					var exp_flag_cell = cell("TD", "input", "hidden",
							"exp_flag_cell", TEXTBOX_NAME, 2, "tdText", "", "",
							"2%", "", "", "");
					new_row.appendChild(exp_flag_cell);
				}
				if (DISPLAYORDER == "16") // Demand Raised With Beneficiaries 16 is display order
				{
					var demand_flag_cell = cell("TD", "input", "hidden","demand_flag_cell", i, 2, "tdText", "", "", "2%","", "", "");
					new_row.appendChild(demand_flag_cell);
				}  
				var MANDATORY_label= cell("TD", "label", "", "",  man, 2, "tdText", "", "color:red", "1%", "","", "");
				var MANDATORY_cell = cell("TD", "input", "hidden", "MANDATORY"+(i +1), MANDATORY, 2, "tdText", "", "", "2%", "","", "");
				
				new_row.appendChild(MANDATORY_label);
				new_row.appendChild(UNITS_cell);
				new_row.appendChild(amt1);
				new_row.appendChild(oldamt);
				new_row.appendChild(MANDATORY_cell);
				tbody.appendChild(new_row);
			}
  
		} else if (process_code == 15) {
			// Scheme Performance Month Wise  List of Item Show  for new Entry 
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("row_count").value = len;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "", "","", "");
			rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "", "","", "", "");
			rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "50%", "","", "");
			var flag_s = flag_c;
			var dis_label;

			if (flag_s == "Y")
				dis_label = "Quantity(ML)";
			else
				dis_label = "Units";

			rowh_blnk = cell("TD", "label", "", "", dis_label, 2, "lbl", "","", "25%", "", "", "");
			var rowh_blnk2 = cell("TD", "label", "", "", "", 2, "lbl", "", "","25%", "", "", "");
			Desc = cell("TD", "label", "", "", "Description", 2, "lbl", "", "","", "center", "", "");
			var Desc1 = cell("TD", "label", "", "", "", 2, "lbl", "", "","", "center", "", "");
			Amount1 = cell("TD", "label", "", "", "", 2, "lbl", "", "", "5%","center", "", "");
			
			head_row.appendChild(rowh);
			// head_row.appendChild(rowh_blnk2);
			head_row.appendChild(Desc);
			
			head_row.appendChild(Desc1);
			head_row.appendChild(rowh_blnk);
			head_row.appendChild(Amount1);
			tbody.appendChild(head_row);

			for ( var i = 0; i < len; i++) {

				var PERFORM_DESC_SNO = bR
						.getElementsByTagName("PERFORM_DESC_SNO")[i].firstChild.nodeValue;
				var UNITS = bR.getElementsByTagName("UNITS")[i].firstChild.nodeValue;
				var new_row = cell("TR", "", "", "row" +i, (i +1), 2, "", "","", "", "", "", "");
				var row_cell = cell("TD", "label", "", "", (i +1), 2, "", "","", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +i, 0, 2,"", "", "", "2%", "", "", "");
				var PERFORM_DESC_SNO_cell = cell("TD", "input", "hidden","PERFORM_DESC_SNO" +i, PERFORM_DESC_SNO, 2, "tdText",
						"", "", "2%", "", "", "");
				var UNITS_cell = cell("TD", "label", "", "UNITS" +i, UNITS, 2,
						"amelabel", "", "", "50%", "left", "", "");
				var PERFORM_DESC = bR.getElementsByTagName("PERFORM_DESC")[i].firstChild.nodeValue;
				var formula = bR.getElementsByTagName("FORMULA")[i].firstChild.nodeValue;
				var DATATYPE = bR.getElementsByTagName("DATATYPE")[i].firstChild.nodeValue;
				var MANDATORY = bR.getElementsByTagName("MANDATORY")[i].firstChild.nodeValue;
				var DATATYPE_cell = cell("TD", "input", "hidden", "DATATYPE"
						+i, DATATYPE, 2, "tdText", "", "", "2%", "", "", "");
				var DISPLAYORDER = bR.getElementsByTagName("DISPLAYORDER")[i].firstChild.nodeValue;
				var TEXTBOX_NAME = bR.getElementsByTagName("TEXTBOX_NAME")[i].firstChild.nodeValue;
				if (i == 8) {
				}
				var d_limit = 0, readonly = "";

				var flow_ = 0;
				try {
					d_limit = bR.getElementsByTagName("DLIMIT")[i].firstChild.nodeValue;
					readonly = bR.getElementsByTagName("READONLY")[i].firstChild.nodeValue;

					if (parseInt(readonly) == 0 || readonly == "")
						readonly = "";
					else
						readonly = "#readonly";

				} catch (e) {
				}

				try {
					flow_ = document.getElementById("flow_").value;

				} catch (e) {
					flow_ = 0;
				}
				var PERFORM_DESC_cell = "";
				var man = "";
				var sty = "";
				if (MANDATORY == "y" || MANDATORY == "Y") {
					man = "  * ";
					sty = "";
				} else {
					man = "";
				}

				if (parseInt(PERFORM_DESC_SNO) == 3) 
				{
					if (parseInt(flow_) == 2) {
						PERFORM_DESC_cell = cell("TD", "label", "","PERFORM_DESC" +(i +1),PERFORM_DESC+man,2,"lbl", "", sty, "85%", "left", "", "");
					} else {
						PERFORM_DESC_cell = cell("TD", "label", "","PERFORM_DESC"+(i+1),PERFORM_DESC,2,"lbl","", sty, "85%", "left",
						"onmouseover#onmouseout","toolin(event,'dv')#toolout('dv')#key_pass()");					
				}
				} else 
				{
					PERFORM_DESC_cell = cell("TD", "label", "", "PERFORM_DESC"+(i +1), PERFORM_DESC , 2, "lbl", "", sty,"85%", "left", "", "");
				}
				var amt1 = "";
				var fun_e = "";
				var fun_n = ""
				// if (parseInt(PERFORM_DESC_SNO)==8)
				// {
				// fun_e="onblur#onKeyUp";
				// fun_n="pump_differ()#isInteger(this,9,event,'famt"+(i+1)+"')";
				// }
				if (parseInt(PERFORM_DESC_SNO) == 5)
				{
						formula="";  
				}				 
				if (parseInt(PERFORM_DESC_SNO) == 1) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"')";
					// fun_n="pump_differ(),supply_differ()#isInteger(this,9,event,'famt"+(i+1)+"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 11) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"')";
				}
				
				if (parseInt(PERFORM_DESC_SNO) == 16 || parseInt(PERFORM_DESC_SNO) == 17) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = "nosupply_differ('famt" +(i)+"')#isInteger(this,9,event,'famt" +(i) +"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 5) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',"
							+d_limit +")";
				} else if (parseInt(PERFORM_DESC_SNO) == 3) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',"+d_limit +")";
				} else if (parseInt(PERFORM_DESC_SNO) != 7
						&& parseInt(PERFORM_DESC_SNO) != 1
						&& parseInt(PERFORM_DESC_SNO) != 8
						&& parseInt(PERFORM_DESC_SNO) != 11
						&& parseInt(PERFORM_DESC_SNO) != 16
						&& parseInt(PERFORM_DESC_SNO) != 17)
				{	
					fun_e = "onKeyUp";
					fun_n = "isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',"+d_limit +")";
				}
				var oldamt = cell("TD", "input", "hidden", "oldamt" +(i +1),0, 6, "", "", "", "25%", "center", fun_e, fun_n);
				var lbl = "";
				if (parseInt(PERFORM_DESC_SNO) == 8) {
					lbl = "famt0";
				} else {
					lbl = "famt" +i;
				}
				if (parseInt(DATATYPE) != 4)
					amt1 = cell("TD", "input", "text", lbl, "", 6, "", "","text-align: left;" +readonly, "25%", "left",fun_e, fun_n);
				// else
				if (parseInt(DATATYPE) == 4)
					amt1 = cell("TD", "textarea", "", lbl, "", 2, "tarea", ""," ", "20%", "center", "", "");
					new_row.appendChild(row_cell);
					new_row.appendChild(PERFORM_DESC_SNO_cell);
					new_row.appendChild(PERFORM_DESC_cell);
					new_row.appendChild(DATATYPE_cell);
				if (DISPLAYORDER == "14") // Actual Expenditure incurred 14 is
											// display order
				{
					var exp_flag_cell = cell("TD", "input", "hidden","exp_flag_cell", TEXTBOX_NAME, 2, "tdText", "", "","2%", "", "", "");
					new_row.appendChild(exp_flag_cell);
				}
				if (DISPLAYORDER == "16") // Demand Raised With Beneficiaries 16 is display order
				{
					var demand_flag_cell = cell("TD", "input", "hidden","demand_flag_cell", i, 2, "tdText", "", "", "2%","", "", "");
					new_row.appendChild(demand_flag_cell);
				}  
				var MANDATORY_label= cell("TD", "label", "", "",  man, 2, "tdText", "", "color:red", "1%", "","", "");
				var MANDATORY_cell = cell("TD", "input", "hidden", "MANDATORY"+(i +1), MANDATORY, 2, "tdText", "", "", "2%", "","", "");		
				new_row.appendChild(MANDATORY_label);
				new_row.appendChild(UNITS_cell);
				new_row.appendChild(amt1);
				new_row.appendChild(oldamt);
				new_row.appendChild(MANDATORY_cell);
				tbody.appendChild(new_row);
			}  
		} else if (process_code == 16) 
		{
			// Scheme Performance Month Wise  List of Item Show  for new Entry 
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("row_count").value = len;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) 
			{
				tbody.deleteRow(0);
			}
			head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "", "","", "");
			rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "", "","", "", "");
			rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "50%", "","", "");
			var flag_s = flag_c;
			var dis_label;
			if (flag_s == "Y")
				dis_label = "Quantity(ML)";
			else
				dis_label = "Units";
			rowh_blnk = cell("TD", "label", "", "", dis_label, 2, "lbl", "","", "25%", "", "", "");
			var rowh_blnk2 = cell("TD", "label", "", "", "", 2, "lbl", "", "","25%", "", "", "");
			Desc = cell("TD", "label", "", "", "Description", 2, "lbl", "", "","", "center", "", "");
			var Desc1 = cell("TD", "label", "", "", "", 2, "lbl", "", "","", "center", "", "");
			Amount1 = cell("TD", "label", "", "", "", 2, "lbl", "", "", "5%","center", "", "");			
			head_row.appendChild(rowh);
			// head_row.appendChild(rowh_blnk2);
			head_row.appendChild(Desc);			
			head_row.appendChild(Desc1);
			head_row.appendChild(rowh_blnk);
			head_row.appendChild(Amount1);
			tbody.appendChild(head_row);
			for ( var i = 0; i < len; i++) 
			{
				var PERFORM_DESC_SNO = bR.getElementsByTagName("PERFORM_DESC_SNO")[i].firstChild.nodeValue;
				var UNITS = bR.getElementsByTagName("UNITS")[i].firstChild.nodeValue;
				var new_row = cell("TR", "", "", "row" +i, (i +1), 2, "", "","", "", "", "", "");
				var row_cell = cell("TD", "label", "", "", (i +1), 2, "", "","", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +i, 0, 2,"", "", "", "2%", "", "", "");
				var PERFORM_DESC_SNO_cell = cell("TD", "input", "hidden","PERFORM_DESC_SNO" +i, PERFORM_DESC_SNO, 2, "tdText","", "", "2%", "", "", "");
				var UNITS_cell = cell("TD", "label", "", "UNITS" +i, UNITS, 2,"amelabel", "", "", "50%", "left", "", "");
				var PERFORM_DESC = bR.getElementsByTagName("PERFORM_DESC")[i].firstChild.nodeValue;
				var formula = bR.getElementsByTagName("FORMULA")[i].firstChild.nodeValue;
				var DATATYPE = bR.getElementsByTagName("DATATYPE")[i].firstChild.nodeValue;
				var MANDATORY = bR.getElementsByTagName("MANDATORY")[i].firstChild.nodeValue;
				var DATATYPE_cell = cell("TD", "input", "hidden", "DATATYPE"+i, DATATYPE, 2, "tdText", "", "", "2%", "", "", "");
				var DISPLAYORDER = bR.getElementsByTagName("DISPLAYORDER")[i].firstChild.nodeValue;
				var TEXTBOX_NAME = bR.getElementsByTagName("TEXTBOX_NAME")[i].firstChild.nodeValue;
				if (i == 8) 
				{
				}
				var d_limit = 0, readonly = "";
				var flow_ = 0;
				try 
				{
					d_limit = bR.getElementsByTagName("DLIMIT")[i].firstChild.nodeValue;
					readonly = bR.getElementsByTagName("READONLY")[i].firstChild.nodeValue;
					if (parseInt(readonly) == 0 || readonly == "")
						readonly = "";
					else
						readonly = "#readonly";
				} catch (e) {	}

				try 
				{
					flow_ = document.getElementById("flow_").value;
				} catch (e) 
				{
					flow_ = 0;
				}
				var PERFORM_DESC_cell = "";
				var man = "";
				var sty = "";
				if (MANDATORY == "y" || MANDATORY == "Y") 
				{
					man = "  * ";
					sty = "";
				} else 
				{
					man = "";
				}

				if (parseInt(PERFORM_DESC_SNO) == 3) 
				{
					if (parseInt(flow_) == 2) 
					{
						PERFORM_DESC_cell = cell("TD", "label", "","PERFORM_DESC" +(i +1),PERFORM_DESC+man,2,"lbl", "", sty, "85%", "left", "", "");
					} else 
					{
						PERFORM_DESC_cell = cell("TD", "label", "","PERFORM_DESC"+(i+1),PERFORM_DESC,2,"lbl","", sty, "85%", "left",
						"onmouseover#onmouseout","toolin(event,'dv')#toolout('dv')#key_pass()");					
					}
				} else 
				{
					PERFORM_DESC_cell = cell("TD", "label", "", "PERFORM_DESC"+(i +1), PERFORM_DESC , 2, "lbl", "", sty,"85%", "left", "", "");
				}
				var amt1 = "";
				var fun_e = "";
				var fun_n = ""
				// if (parseInt(PERFORM_DESC_SNO)==8)
				// {
				// fun_e="onblur#onKeyUp";
				// fun_n="pump_differ()#isInteger(this,9,event,'famt"+(i+1)+"')";
				// }
				if (parseInt(PERFORM_DESC_SNO) == 5)
				{
						formula="";  
				}				 
				if (parseInt(PERFORM_DESC_SNO) == 1) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"')";
					// fun_n="pump_differ(),supply_differ()#isInteger(this,9,event,'famt"+(i+1)+"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 11) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"')";
				}
				
				if (parseInt(PERFORM_DESC_SNO) == 16 || parseInt(PERFORM_DESC_SNO) == 17) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = "nosupply_differ('famt" +(i)+"')#isInteger(this,9,event,'famt" +(i) +"')";
				}
				if (parseInt(PERFORM_DESC_SNO) == 5) {
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',"+d_limit +")";
				} else if (parseInt(PERFORM_DESC_SNO) == 3) {
					fun_e = "onblur#onKeyUp";
					fun_n = formula +"#isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',"+d_limit +")";
				} else if (parseInt(PERFORM_DESC_SNO) != 7
						&& parseInt(PERFORM_DESC_SNO) != 1
						&& parseInt(PERFORM_DESC_SNO) != 8
						&& parseInt(PERFORM_DESC_SNO) != 11
						&& parseInt(PERFORM_DESC_SNO) != 16
						&& parseInt(PERFORM_DESC_SNO) != 17) {

					fun_e = "onKeyUp";
					fun_n = "isInteger(this,9,event,'famt" +(i +1)+"'),digit_control('famt" +(i +1) +"',"+d_limit +")";
				}
				var oldamt = cell("TD", "input", "hidden", "oldamt" +(i +1),0, 6, "", "", "", "25%", "center", fun_e, fun_n);
				var lbl = "";
				if (parseInt(PERFORM_DESC_SNO) == 8) 
				{
					lbl = "famt0";
				} else 
				{
					lbl = "famt" +i;
				}
				if (parseInt(DATATYPE) != 4)
				amt1 = cell("TD", "input", "text", lbl, "", 6, "", "","text-align: right;" +readonly, "25%", "center",fun_e, fun_n);
				// else
				if (parseInt(DATATYPE) == 4)
					amt1 = cell("TD", "textarea", "", lbl, "", 2, "tarea", ""," ", "20%", "center", "", "");
				new_row.appendChild(row_cell);
				new_row.appendChild(PERFORM_DESC_SNO_cell);
				new_row.appendChild(PERFORM_DESC_cell);
				new_row.appendChild(DATATYPE_cell);
				if (DISPLAYORDER == "14") // Actual Expenditure incurred 14 is
											// display order
				{
					var exp_flag_cell = cell("TD", "input", "hidden","exp_flag_cell", TEXTBOX_NAME, 2, "tdText", "", "","2%", "", "", "");
					new_row.appendChild(exp_flag_cell);
				}
				if (DISPLAYORDER == "16") // Demand Raised With Beneficiaries 16 is display order
				{
					var demand_flag_cell = cell("TD", "input", "hidden","demand_flag_cell", i, 2, "tdText", "", "", "2%","", "", "");
					new_row.appendChild(demand_flag_cell);
				}  
				var MANDATORY_label= cell("TD", "label", "", "",  man, 2, "tdText", "", "color:red", "1%", "","", "");
				var MANDATORY_cell = cell("TD", "input", "hidden", "MANDATORY"+(i +1), MANDATORY, 2, "tdText", "", "", "2%", "","", "");				
				new_row.appendChild(MANDATORY_label);
				new_row.appendChild(UNITS_cell);
				new_row.appendChild(amt1);
				new_row.appendChild(oldamt);
				new_row.appendChild(MANDATORY_cell);
				tbody.appendChild(new_row);
			}  
		 }else  if (process_code == 17 ) 
			{
			    var count = bR.getElementsByTagName("CNT")[0].firstChild.nodeValue;
			    if (parseInt(count)==0) 
			    {
			    	 alert(" There is no Item Wise Expenditure Entry  for this selected Financial Year");
			    	 document.getElementById("famt8").value ="";			    	 
			    }
			    else  
			    { 
			    	 
			    	 var ACTUAL_EXP = bR.getElementsByTagName("ACTUAL_EXP")[0].firstChild.nodeValue;
			    	 alert("Item Wise Expenditure for this selected Financial Year is Rs."+ACTUAL_EXP);
			    	 document.getElementById("famt8").value = roundNumber(parseFloat(ACTUAL_EXP)/100000,2);  
			    }
				 
			}else  if (process_code == 18 ) 
			{
			    var count = bR.getElementsByTagName("CNT")[0].firstChild.nodeValue;
			    if (parseInt(count)==0) 
			    {
			    	alert(" Total Item Wise Expenditure Figures not verified ...............\n \n Please Verify before scheme performance \n\n Refer: \n \t AME --> Maintenance Expenditure - Monthly --> Item Wise Expenditure ");
			    	document.getElementById("famt8").value ="";
			    	//new_payment_process();
			    }  
			    else    
			    { 
			    	 
			    	 transaction(2,7); 
			    	 var ACTUAL_EXP = bR.getElementsByTagName("ACTUAL_EXP")[0].firstChild.nodeValue;
			    	 alert("Item Wise Expenditure for this selected Month and  Year   is Rs."+ACTUAL_EXP);
			    	 document.getElementById("famt8").value =  roundNumber(parseFloat(ACTUAL_EXP)/100000,2); 
			    	 //transaction(2,9);
			    	 exp2('famt8','famt2','famt9','famt10','famt11');
			    	
			    }
				 
			}
		 else {
 			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			var tbody = document.getElementById("entdata");
			var table = document.getElementById("enttable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "", "","", "");
			rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "", "","", "", "");
			rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "", "","", "");
			var flag_s = flag_c;
			var dis_label;
			if (flag_s == "Y")
				dis_label = "Quantity";
			else
				dis_label = "Amount";

			rowh_blnk = cell("TD", "label", "", "", dis_label, 2, "lbl", "","", "", "", "", "");
			Desc = cell("TD", "label", "", "", "Description of Items", 2,"lbl", "", "", "", "center", "", "");
			Amount1 = cell("TD", "label", "", "", "Budget Estimate   Amount",2, "lbl", "", "", "", "center", "", "");
			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			head_row.appendChild(rowh_ck);
			tbody.appendChild(head_row);

			for ( var i = 0; i < len; i++) 
			{
				var remarks = "";
				var amt1 = "";
				var PERFORM_DESC_SNO = xmlValue(bR, "PERFORM_DESC_SNO", i);
				var PERFORM_DESC = xmlValue(bR, "PERFORM_DESC", i);
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "", "","", "2%", "", "", "");
				var PERFORM_DESC_SNO_cell = cell("TD", "input", "hidden","PERFORM_DESC_SNO" +(i +1), PERFORM_DESC_SNO, 2,"tdText", "", "", "2%", "", "", "");
				var PERFORM_DESC_cell = cell("TD", "label", "", "PERFORM_DESC"+(i +1), PERFORM_DESC, 2, "lbl", "", " ", "", "left","", "");

				if (parseInt(PERFORM_DESC_SNO) == 7) 
				{
					remarks = xmlValue(bR, "REMARKS", i);
					amt1 = cell("TD", "label", "", "famt" +(i +1), remarks,2, "lbl", "", " ", "", "center", "", "");
				} else {
					if (flag_s == "Y")
						value_ = xmlValue(bR, "QTY_OR_AMOUNT", i);
					else
						value_ = xmlValue(bR, "ACTUAL_EXP", i);

					amt1 = cell("TD", "label", "", "famt" +(i +1), value_, 2,"lbl", "", " ", "", "center", "", "");

				}

				new_row.appendChild(row_cell);
				new_row.appendChild(PERFORM_DESC_SNO_cell);
				new_row.appendChild(PERFORM_DESC_cell);
				new_row.appendChild(amt1);
				tbody.appendChild(new_row);
			}

		}
	}
	// /      Form 3    Actual Expenditure
	// /////////////////////////SEL TYPE 3 //////////////////////////////////
	if (sel_type == 3) {
		
		if (process_code == 15 ) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("rowcount2").value = len;
			var tbody = document.getElementById("edatasub");
			var table = document.getElementById("etablesub");
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			} 
			document.getElementById("pr_status").value=1;
			/*
			var new_row1 = cell("TR", "", "", "row2", 1, 2, "", "", "", "","", "", "");
			var Desc1 = cell("TD", "label", "", "", "Sl.No", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var Desc2 = cell("TD", "label", "", "", "Description of Items ", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var Desc_ac = cell("TD", "label", "", "", "Account Head Code ", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var Desc3 = cell("TD", "label", "", "", "Amount(Rs.lks)", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var STATICFLAG="0";
			  
			new_row1.appendChild(Desc1);
			new_row1.appendChild(Desc2);
			new_row1.appendChild(Desc_ac);
			new_row1.appendChild(Desc3);
			tbody.appendChild(new_row1);*/ 
			var s_ = document.getElementById("sch_sno").value;
			var net_total=0;
			for ( var i = 0; i < len; i++) 
			{
				var new_row2 = cell("TR", "", "", "row2", 1, 2, "", "", "", "","", "", "");
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var ACTUAL_EXP = xmlValue(bR, "ACTUAL_EXP", i);
				var ACCOUNT_HEAD_CODE= xmlValue(bR, "ACCOUNT_HEAD_CODE", i);
				if (parseInt(SUB_ITEM_DESC) == 0)
					SUB_ITEM_DESC = "";
				else
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%","center", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "center", "", "");
				var ACCOUNT_HEAD_CODE_cell = "";
				ACCOUNT_HEAD_CODE_cell = cell("TD", "input", "hidden", "ACCOUNT_HEAD_CODE"+(i +1), ACCOUNT_HEAD_CODE, 2, "lbl", "", "font-size:12; ", "70","left", "", "");
				var MAIN_ITEM_DESC_cell = "";
				MAIN_ITEM_DESC_cell = cell("TD", "label", "", "MAIN_ITEM_DESC"+(i +1), MAIN_ITEM_DESC, 2, "lbl", "", "font-size:12; ", "70","left", "", "");
				
				var view_ac=cell("TD", "A", "", "", "View", 2, "lbl", "javascript:acc_view("+(i +1)+")", "font-size:12; ", "70","left", "", "");
				
				var cclr="";
				if (ACCOUNT_HEAD_CODE=="" || ACCOUNT_HEAD_CODE=="0" || ACCOUNT_HEAD_CODE=="NaN")
					cclr="red";
				else
					cclr="black";
				
				var row_cell = cell("TD", "label", "", "", +(i +1)+" ", 2, "lbl","", "font-size:12;color:"+cclr, "2%", "center", "", "");
				
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC_cellEM_DESC" +(i +1), SUB_ITEM_DESC,2, "tb4", "", "", "", "left", "", "");
				var  ACTUAL_EXP_SNO = "";  
				var fun_name = "";
				 try
				 {
					 net_total+=parseFloat(ACTUAL_EXP);
				 }catch(e){}
				var amt1 = cell("TD", "input", "text", "eamt" +(i +1),ACTUAL_EXP, 10, "", "", "text-align:right", "15%","center", "", "");
				new_row2.appendChild(row_cell);
				new_row2.appendChild(MAIN_ITEM_SNO_cell);
				new_row2.appendChild(GROUP_SNO_cell);
				new_row2.appendChild(SUB_ITEM_SNO_cell); 
				new_row2.appendChild(MAIN_ITEM_DESC_cell);
				new_row2.appendChild(view_ac);
				new_row2.appendChild(ACCOUNT_HEAD_CODE_cell);
				new_row2.appendChild(amt1);
				tbody.appendChild(new_row2);
				
				
			} 
			/*var y_ = document.getElementById("pyear").value;
			var m_ = 0;
			try {
				m = document.getElementById("pmonth").value;
			} catch (e) {
				m_ = 0;
			}

			 

			for ( var i = 0; i < len; i++) 
			{  
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				try
				{
					STATICFLAG= xmlValue(bR, "STATICFLAG", i);
				}catch(e) {}				
				if (process_code == 11 || process_code == 14) 
				{
					fun_name = "javascript:exp_break_up_edit(" +MAIN_ITEM_SNO+"," +SUB_ITEM_SNO +"," +GROUP_SNO +"," +i+"," +y_ +"," +m_ +"," +s_ +")";
				} else {
					fun_name = "javascript:exp_break_up(" +MAIN_ITEM_SNO +","+SUB_ITEM_SNO +"," +GROUP_SNO +"," +i +")"
				}
				var click = "";			 
				if (parseInt(STATICFLAG)==0) //if (i !=11 &&  i!=12 && i!=1) 
				{ 
					 click = cell("TD", "A", "", "MAIN_ITEM_DESC" +(i +1),"Verify", 2, "lbl", fun_name, "font-size:12;", "", "center","onmouseover#onmouseout","toolin(event,'dv1')#toolout('dv1')");
				}else   
				{
					click = cell("TD", "A", "", "formula"+(i+1),"Formula", 2, "lbl","javascript:formula("+(i+1)+")", "font-size:12;", "", "center","","");
				}		 
				// Liability and Pension 
				try  
				{
					       
						
						var lib=xmlValue(bR, "head1_211906",0);
						var pen=xmlValue(bR, "head1_212006", 0);				
						var sup_cost=xmlValue(bR, "sup_cost", 0);  
						//document.getElementById("eamt12").value=lib;
						//document.getElementById("eamt13").value=pen;
						if (parseInt(STATICFLAG)!=0)
						{
							if (STATICFLAG==1)
							{
							document.getElementById("eamt"+(i+1)).value=lib;
							 net_total+=parseFloat(lib);
							}
							if (STATICFLAG==2)
							{
							document.getElementById("eamt"+(i+1)).value=pen;
							 net_total+=parseFloat(pen);
							}
							
						}
						document.getElementById("eamt2").value=sup_cost;
						
				}catch(e)
				{   
					alert(e)
				}  
			}
			net_total+=parseFloat(sup_cost);
			
			
			*/
			
			document.getElementById("net_label").innerHTML="Total :" + parseFloat(Math.round(net_total * 100) / 100).toFixed(2); 
			document.getElementById("net_label1").innerHTML="Total :" + parseFloat(Math.round(net_total * 100) / 100).toFixed(2); ;
			var msg_new="Verify expenditure figures for the selected scheme, month  and year..... \n\n Pls. Note: \n\n These figures are as entered in FAS schemewise for the expenditure head of account \n\n Please Scroll down to see the Note.";
			alert(msg_new);
			//verf_cls1();
		}
		if (process_code == 17 ) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			var row_count=document.getElementById("row_count").value;
			if (len==0)
			{
				for(j=0;j<row_count;j++) 
				{
						document.getElementById("famt"+(j+1)).value=0;
				}
			}
			var net_total=0;
			for(i=0;i<len;i++)
			{
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var row_count=document.getElementById("row_count").value;
				var ACTUAL_EXP = xmlValue(bR, "ACTUAL_EXP", i);
				for(j=0;j<row_count;j++) 
				{
					var ms=document.getElementById("SUB_ITEM_SNO"+(j+1)).value;
					if (SUB_ITEM_SNO==ms)
					{
						document.getElementById("famt"+(j+1)).value=ACTUAL_EXP;
						net_total+=parseFloat(ACTUAL_EXP);
					} 
				} 
			}
				document.getElementById("total").value=net_total;
			 if (len > 0)
			 {
				 alert("Data already Exists!!!!!");
				 document.getElementById("save").style.visibility = "hidden";
			 }
			 else
			 {
				 document.getElementById("save").style.visibility = "visible";
				 
			 }
			 
			 
		} 
		if (process_code == 16 ) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("row_count").value=len;
			document.getElementById("rowcount2").value = len;
			var tbody = document.getElementById("edatasub");
			var table = document.getElementById("etablesub");
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			} 
			document.getElementById("pr_status").value=1;
			/*
			var new_row1 = cell("TR", "", "", "row2", 1, 2, "", "", "", "","", "", "");
			var Desc1 = cell("TD", "label", "", "", "Sl.No", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var Desc2 = cell("TD", "label", "", "", "Description of Items ", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var Desc_ac = cell("TD", "label", "", "", "Account Head Code ", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var Desc3 = cell("TD", "label", "", "", "Amount(Rs.lks)", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var STATICFLAG="0";
			  
			new_row1.appendChild(Desc1);
			new_row1.appendChild(Desc2);
			new_row1.appendChild(Desc_ac);
			new_row1.appendChild(Desc3);
			tbody.appendChild(new_row1);*/ 
			var s_ = document.getElementById("sch_sno").value;
			var net_total=0;
			for ( var i = 0; i < len; i++) 
			{
				var new_row2 = cell("TR", "", "", "row2", 1, 2, "", "", "", "","", "", "");
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var ACCOUNT_HEAD_CODE= xmlValue(bR, "ACCOUNT_HEAD_CODE", i);
				if (parseInt(SUB_ITEM_DESC) == 0)
					SUB_ITEM_DESC = "";
				else
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%","center", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "font-size:12;", "2%", "center", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "center", "", "");
				var ACCOUNT_HEAD_CODE_cell = "";
				ACCOUNT_HEAD_CODE_cell = cell("TD", "input", "hidden", "ACCOUNT_HEAD_CODE"+(i +1), ACCOUNT_HEAD_CODE, 2, "lbl", "", "font-size:12; ", "70","left", "", "");
				var MAIN_ITEM_DESC_cell = "";
				MAIN_ITEM_DESC_cell = cell("TD", "label", "", "MAIN_ITEM_DESC"+(i +1), MAIN_ITEM_DESC, 2, "lbl", "", "font-size:12; ", "70","left", "", "");
				
				var view_ac=cell("TD", "A", "", "", "View", 2, "lbl", "javascript:acc_view("+(i +1)+")", "font-size:12; ", "70","left", "", "");
				 
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC_cellEM_DESC" +(i +1), SUB_ITEM_DESC,2, "tb4", "", "", "", "left", "", "");
				var  ACTUAL_EXP_SNO = "";  
				var fun_name = "";
				 try
				 {
					 net_total+=parseFloat(ACTUAL_EXP);
				 }catch(e){}
			//	 var amt1 = cell("TD", "input", "text", "eamt" +(i +1),"", 10, "", "", "text-align:right", "15%","center", "", "");
				
				var amt1 = cell("TD", "input", "text", "famt" +(i +1), "", 6,"", "", "text-align:right", "", "right","onKeyup#onKeyDown#onblur", "isInteger(this,9,event,'famt"
						+(i +1) +"'),digit_control('famt" +(i +1)
						+"',2)#key_pass(event,'famt" +(i +1)
						+"','famt" +(i +2) +"')#page_total("+(i+1)+")");
				
				 
				new_row2.appendChild(row_cell);
				new_row2.appendChild(MAIN_ITEM_SNO_cell);
				new_row2.appendChild(GROUP_SNO_cell);
				new_row2.appendChild(SUB_ITEM_SNO_cell); 
				new_row2.appendChild(MAIN_ITEM_DESC_cell);
				new_row2.appendChild(view_ac);
				new_row2.appendChild(ACCOUNT_HEAD_CODE_cell);
				new_row2.appendChild(amt1);
				tbody.appendChild(new_row2);
				
				
			} 
			 
			
		}
		if (process_code == 5 || process_code == 11 || process_code == 14) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("rowcount2").value = len;
			var tbody = document.getElementById("edatasub");
			var table = document.getElementById("etablesub");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			} 
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "font-size: 11em;","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var y_ = document.getElementById("pyear").value;
			var m_ = 0;
			try {
				m = document.getElementById("pmonth").value;
			} catch (e) {
				m_ = 0;
			}

			var new_row = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");
			var slno = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "font-size:12;","25%", "", "", "");
			new_row.appendChild(slno);
			var k = 0;
			for ( var i = 0; i < len; i++) 
			{
				k++;
				var row_cell = cell("TD", "label", "", "", k, 2, "lbl", "", "font-size:12;","12%", "", "", "");
				new_row.appendChild(row_cell);
				tbody.appendChild(new_row);
			}
  
			var new_row1 = cell("TR", "", "", "row2", 1, 2, "", "", "", "20%","", "", "");
			var Desc = cell("TD", "label", "", "", "Description of Items ", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			var STATICFLAG="0";
			new_row1.appendChild(Desc);
			var s_ = document.getElementById("sch_sno").value;
			for ( var i = 0; i < len; i++) {
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				
				if (parseInt(SUB_ITEM_DESC) == 0)
					SUB_ITEM_DESC = "";
				else
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;
				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;
				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%","center", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "font-size:12;", "2%", "center", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "center", "", "");
				var MAIN_ITEM_DESC_cell = "";
				MAIN_ITEM_DESC_cell = cell("TD", "label", "", "MAIN_ITEM_DESC"+(i +1), MAIN_ITEM_DESC, 2, "lbl", "", "font-size:12; ", "","center", "", "");
				// else
				// MAIN_ITEM_DESC_cell=cell("","label","","MAIN_ITEM_DESC"+(i+1),MAIN_ITEM_DESC,2,"lbl","","
				// ","","left","","");
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC_cellEM_DESC" +(i +1), SUB_ITEM_DESC,2, "tb4", "", "", "", "center", "", "");
				var ACTUAL_EXP = "", ACTUAL_EXP_SNO = "";
				var fun_name = "";
  
				new_row1.appendChild(MAIN_ITEM_SNO_cell);
				new_row1.appendChild(GROUP_SNO_cell);
				new_row1.appendChild(SUB_ITEM_SNO_cell);
				// new_row.appendChild(row_cell);
				new_row1.appendChild(MAIN_ITEM_DESC_cell);
				// new_row.appendChild(amt1);

				tbody.appendChild(new_row1);
			}
			var new_amt_row = cell("TR", "", "", "row3", 1, 2, "", "", "", "","", "", "");
			var Amount1 = cell("TD", "label", "", "","Monthly Expenditure   (Rs. Lakhs)", 2, "lbl", "", "font-size:12;", "","left", "", "");
			new_amt_row.appendChild(Amount1);

			for ( var i = 0; i < len; i++)
			{

				if (process_code == 11 || process_code == 14)
				{
					
					ACTUAL_EXP = xmlValue(bR, "ACTUAL_EXP", i);
				 
					var ACTUAL_EXP_SNO = xmlValue(bR, "ACTUAL_EXP_SNO", i);
					var ACTUAL_EXP_SNO_cell = cell("TD", "input", "hidden","ACTUAL_EXP_SNO" +(i +1), ACTUAL_EXP_SNO, 2,"tdText", "", "", "2%", "center", "", "");
					// new_amt_row.appendChild(ACTUAL_EXP_SNO_cell);
				}
				var group_2_readonly = "text-align:right#readonly";
				var amt1 = cell("TD", "input", "text", "eamt" +(i +1),ACTUAL_EXP, 6, "", "", group_2_readonly, "","center", "onKeyUp", "isInteger(this,9,event,'eamt"
								+(i +1) +"'),digit_control('eamt" +(i +1)    
								+"',2)");
			 
				var click = "";
				click = cell("TD", "A", "", "MAIN_ITEM_DESC" +(i +1),"click", 2, "lbl", fun_name, " ", "", "center","onmouseover#onmouseout",
						"toolin(event,'dv1')#toolout('dv1')");
				new_amt_row.appendChild(amt1);
				tbody.appendChild(new_amt_row);				
				try
				{					
					 // set();   
				}catch(e){}
			}

			var new_row2 = cell("TR", "", "", "row2", 1, 2, "", "", "", "20%","", "", "");
			var Desc2 = cell("TD", "label", "", "", "Bill Wise Details ", 2,"lbl", "", "font-size:12;", "20%", "left", "", "");
			new_row2.appendChild(Desc2);
			for ( var i = 0; i < len; i++) 
			{  
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				try
				{
					STATICFLAG= xmlValue(bR, "STATICFLAG", i);
				}catch(e) {}				
				if (process_code == 11 || process_code == 14) 
				{
					fun_name = "javascript:exp_break_up_edit(" +MAIN_ITEM_SNO+"," +SUB_ITEM_SNO +"," +GROUP_SNO +"," +i+"," +y_ +"," +m_ +"," +s_ +")";
				} else {
					fun_name = "javascript:exp_break_up(" +MAIN_ITEM_SNO +","+SUB_ITEM_SNO +"," +GROUP_SNO +"," +i +")"
				}
				var click = "";			 
				if (parseInt(STATICFLAG)==0) //if (i !=11 &&  i!=12 && i!=1) 
				{ 
					 click = cell("TD", "A", "", "MAIN_ITEM_DESC" +(i +1),"Verify", 2, "lbl", fun_name, "font-size:12;", "", "center","onmouseover#onmouseout","toolin(event,'dv1')#toolout('dv1')");
					 new_row2.appendChild(click);
					 tbody.appendChild(new_row2);
				}else   
				{
					click = cell("TD", "A", "", "formula"+(i+1),"Formula", 2, "lbl","javascript:formula("+(i+1)+")", "font-size:12;", "", "center","","");
					new_row2.appendChild(click);
					tbody.appendChild(new_row2);  
				}		 
				//  Liability and Pension 
				try  
				{
					if (process_code == 5) 
					{      
						
						var lib=xmlValue(bR, "head1_211906",0);
						var pen=xmlValue(bR, "head1_212006", 0);				
						var sup_cost=xmlValue(bR, "sup_cost", 0);  
						//document.getElementById("eamt12").value=lib;
						//document.getElementById("eamt13").value=pen;
						if (parseInt(STATICFLAG)!=0)
						{
							
							if (STATICFLAG==1)
							document.getElementById("eamt"+(i+1)).value=lib;
							if (STATICFLAG==2)
							document.getElementById("eamt"+(i+1)).value=pen;
							
						}
						document.getElementById("eamt2").value=sup_cost;
					}
				}catch(e)
				{
				}  
			}
		}
		/* Scheme Performance - Delete Response */
		else if (process_code == 4) {
			var rows = bR.getElementsByTagName("response")[0].firstChild.nodeValue;
			if (parseInt(rows) >= 1) {
				alert("Record Succesfully Deleted  ");
				transaction(3, 6)
			} else {
				alert("Record Not Deleted  ");
			}
		} else if (process_code == 7) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("rowcount2").value = len;
			var tbody = document.getElementById("edatasub");
			var table = document.getElementById("etablesub");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) 
			{
				tbody.deleteRow(0);
			}
			var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
			var rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","2%", "", "", "");
			var rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "2%","", "", "");
			var y_ = document.getElementById("pyear").value;
			var m_ = 0;
			try {
				m = document.getElementById("pmonth").value;
			} catch (e) {
				m_ = 0;
			}

			var new_row = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");
			var slno = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","25%", "", "", "");
			new_row.appendChild(slno);
			var k = 0;
			for ( var i = 0; i < len; i++) {
				k++;
				var row_cell = cell("TD", "label", "", "", k, 2, "lbl", "", "","12%", "", "", "");
				new_row.appendChild(row_cell);
				tbody.appendChild(new_row);
			}

			var new_row1 = cell("TR", "", "", "row2", 1, 2, "", "", "", "20%","", "", "");
			var Desc = cell("TD", "label", "", "", "Description of Items ", 2,"lbl", "", "", "20%", "left", "", "");
			new_row1.appendChild(Desc);
			var s_ = document.getElementById("sch_sno").value;
			for ( var i = 0; i < len; i++) 
			{
				var GROUP_SNO = xmlValue(bR, "GROUP_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				if (parseInt(SUB_ITEM_DESC) == 0)
					SUB_ITEM_DESC = "";
				else
					SUB_ITEM_DESC = "-" +SUB_ITEM_DESC;

				MAIN_ITEM_DESC = MAIN_ITEM_DESC +SUB_ITEM_DESC;

				var MAIN_ITEM_SNO_cell = cell("TD", "input", "hidden","MAIN_ITEM_SNO" +(i +1), MAIN_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var GROUP_SNO_cell = cell("TD", "input", "hidden", "GROUP_SNO"+(i +1), GROUP_SNO, 2, "tdText", "", "", "2%","center", "", "");
				var SUB_ITEM_SNO_cell = cell("TD", "input", "hidden","SUB_ITEM_SNO" +(i +1), SUB_ITEM_SNO, 2, "tdText","", "", "2%", "center", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "lbl","", "", "2%", "center", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "", "center", "", "");
				var MAIN_ITEM_DESC_cell = "";
				MAIN_ITEM_DESC_cell = cell("TD", "label", "", "MAIN_ITEM_DESC"+(i +1), MAIN_ITEM_DESC, 2, "lbl", "", " ", "","center", "", "");
				var SUB_ITEM_DESC_cell = cell("TD", "input", "text","SUB_ITEM_DESC_cellEM_DESC" +(i +1), SUB_ITEM_DESC,2, "tb4", "", " ", "", "center", "", "");
				var ACTUAL_EXP = "", fun_name = "";
				new_row1.appendChild(MAIN_ITEM_SNO_cell);
				new_row1.appendChild(GROUP_SNO_cell);
				new_row1.appendChild(SUB_ITEM_SNO_cell);
				new_row1.appendChild(MAIN_ITEM_DESC_cell);
				tbody.appendChild(new_row1);
			}

			var new_amt_row = cell("TR", "", "", "row3", 1, 2, "", "", "", "","", "", "");
			var Amount1 = cell("TD", "label", "", "","Annual Expenditure   (Rs. Lakhs)", 2, "lbl", "", "", "","left", "", "");
			new_amt_row.appendChild(Amount1);
			for ( var i = 0; i < len; i++) 
			{
				var amt1=cell("TD","input","text","eamt"+(i+1),ACTUAL_EXP,6,"","","text-align: right;","","center","onKeyUp","isInteger(this,9,event,'eamt"+(i+1)+"'),digit_control('eamt"+(i+1)+"',2)");
				var click = "";
				click = cell("TD", "A", "", "MAIN_ITEM_DESC" +(i +1),"click",2,"lbl",fun_name,"","","center","onmouseover#onmouseout","toolin(event,'dv1')#toolout('dv1')");
				new_amt_row.appendChild(amt1);
				tbody.appendChild(new_amt_row);
			}

			var new_row2 = cell("TR", "", "", "row2", 1, 2, "", "", "", "20%","", "", "");
			var Desc2 = cell("TD", "label", "", "", "Bill Wise Details ", 2,"lbl", "", "", "20%", "left", "", "");
			new_row2.appendChild(Desc2);
			for ( var i = 0; i < len; i++) 
			{
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var SUB_ITEM_DESC = xmlValue(bR, "SUB_ITEM_DESC", i);
				if (process_code == 11) 
				{
					ACTUAL_EXP = xmlValue(bR, "ACTUAL_EXP", i);
					fun_name = "javascript:exp_break_up_edit("+MAIN_ITEM_SNO+","+SUB_ITEM_SNO+","+GROUP_SNO+","+i+","+y_+","+m_+","+s_+")";
				} else {
					fun_name = "javascript:exp_break_up(" +MAIN_ITEM_SNO +","+SUB_ITEM_SNO+","+GROUP_SNO+","+i+")"
				}
				var click = "";
				click = cell("TD", "A", "", "MAIN_ITEM_DESC" +(i +1),"Entry", 2, "lbl", fun_name, " ", "", "center",
						"onmouseover#onmouseout","toolin(event,'dv1')#toolout('dv1')");
				new_row2.appendChild(click);
				tbody.appendChild(new_row2);
			}

		}
		//  Aggrement Value 
		else if (process_code == 12) 
		{
			var agvalue = bR.getElementsByTagName("agvalue")[0].firstChild.nodeValue;
			document.getElementById("agvalue").innerHTML = " Aggrement Value  :  "
					+agvalue;

		}
		// Load for Performance Edit  
		else if (process_code == 6) 
		{
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			document.getElementById("row_count").value = len;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) 
			{
				tbody.deleteRow(0);
			}
			head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "", "","", "");
			rowh = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "", "","", "", "");
			rowh_ck = cell("TD", "label", "", "", "", 2, "", "", "", "50%", "","", "");
			var flag_s = flag_c;
			var dis_label;

			if (flag_s == "Y")
				dis_label = "Quantity(ML)";
			else
				dis_label = "Units";

			rowh_blnk = cell("TD", "label", "", "", dis_label, 2, "lbl", "","", "25%", "", "", "");
			Desc = cell("TD", "label", "", "", "Description of Items", 2,"lbl", "", "", "", "center", "", "");
			Amount1 = cell("TD", "label", "", "", "Budget Estimate   Amount",2, "lbl", "", "", "5%", "center", "", "");

			head_row.appendChild(rowh);
			head_row.appendChild(Desc);
			head_row.appendChild(rowh_blnk);
			head_row.appendChild(rowh_ck);
			tbody.appendChild(head_row);
			var DATATYPE = 0;
			for ( var i = 0; i < len; i++)
			{
				var PERFORM_DESC_SNO = bR.getElementsByTagName("PERFORM_DESC_SNO")[i].firstChild.nodeValue;
				var UNITS = bR.getElementsByTagName("UNITS")[i].firstChild.nodeValue;
				var DLIMIT = xmlValue(bR, "DLIMIT", i);
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var row_cell = cell("TD", "label", "", "", +(i +1), 2, "", "","", "2%", "", "", "");
				var Chk_cell = cell("TD", "input", "checkbox", "chk" +(i +1),0, 2, "", "", "", "2%", "", "", "");
				var PERFORM_DESC_SNO_cell = cell("TD", "input", "hidden","PERFORM_DESC_SNO" +(i), PERFORM_DESC_SNO, 2,"tdText", "", "", "2%", "", "", "");
				var UNITS_cell = cell("TD", "label", "", "UNITS" +(i +1),UNITS, 2, "amelabel", "", "", "50%", "left", "", "");
				var DLIMIT_cell = cell("TD", "input", "hidden", "DLIMIT"+(i +1), DLIMIT, 2, "amelabel", "", "", "50%","left", "", "");
				var PERFORM_DESC = bR.getElementsByTagName("PERFORM_DESC")[i].firstChild.nodeValue;
				var MANDATORY = bR.getElementsByTagName("MANDATORY")[i].firstChild.nodeValue;
				var MANDATORY_cell = cell("TD", "input", "hidden", "MANDATORY"+(i +1), MANDATORY, 2, "amelabel", "", "", "50%","left", "", "");
				DATATYPE = bR.getElementsByTagName("DATATYPE")[i].firstChild.nodeValue;
				var DATATYPE_cell = cell("TD", "input", "hidden", "DATATYPE"+(i), DATATYPE, 2, "amelabel", "", "", "50%", "left","", "");
				var DISPLAYORDER = bR.getElementsByTagName("DISPLAYORDER")[i].firstChild.nodeValue;
				var readonly = "";
				var formula = bR.getElementsByTagName("FORMULA")[i].firstChild.nodeValue;
				readonly = bR.getElementsByTagName("READONLY")[i].firstChild.nodeValue;
				var TEXTBOX_NAME = bR.getElementsByTagName("TEXTBOX_NAME")[i].firstChild.nodeValue;
				if (parseInt(readonly) == 0 || readonly == "")
					readonly = "";
				else
					readonly = "#readonly";
				var PERFORM_DESC_cell = "";
				// if (parseInt(PERFORM_DESC_SNO)==3)
				// PERFORM_DESC_cell=cell("TD","A","","PERFORM_DESC"+(i+1),PERFORM_DESC,2,"lbl","javascript:transaction(3,11)","
				// ","85%","left","onmouseover#onmouseout","toolin(event,'dv1')#toolout('dv1')");
				// else
				PERFORM_DESC_cell = cell("TD", "label", "", "PERFORM_DESC"+(i +1), PERFORM_DESC, 2, "lbl", "", " ", "85%","left", "", "");

				var amt1 = "", fun_e = "", fun_n = "";
				if (parseInt(PERFORM_DESC_SNO) == 5) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = "expenditure('famt10','famt8','famt11')#isInteger(this,9,event)";
				} else if (parseInt(PERFORM_DESC_SNO) == 3) 
				{
					fun_e = "onblur#onKeyUp";
					fun_n = "exp2('famt8','famt2','famt9','famt10','famt11')#isInteger(this,9,event),digit_control('famt"+(i +1) +"',2)";				 
				//	fun_n = "exp2()#isInteger(this,9,event),digit_control('famt"+(i +1) +"',2)";
				} else if (parseInt(PERFORM_DESC_SNO) !== 7) 
				{
					fun_e = "onKeyUp";  
					fun_n = "isInteger(this,9,event),digit_control('famt"+(i +1) +"',2)";
				}
				var old_amt1 = cell("TD", "input", "hidden","oldamt" +(i +1), 0, 6, "", "", "", "25%", "center",fun_e, fun_n);
				var lbl = "";
				if (parseInt(PERFORM_DESC_SNO) == 8) {
					lbl = "famt0";
				} else {
					lbl = "famt" +i;
				}
				if (DISPLAYORDER == "14") // Actual Expenditure incurred 14 is
											// display order
				{
					var exp_flag_cell = cell("TD", "input", "hidden","exp_flag_cell", TEXTBOX_NAME, 2, "tdText", "", "","2%", "", "", "");
					new_row.appendChild(exp_flag_cell);
				}
				if (DISPLAYORDER == "16") // Demand Raised With Beneficiaries 16 is display order
				{
					var demand_flag_cell = cell("TD", "input", "hidden","demand_flag_cell", i, 2, "tdText", "", "", "2%","", "", "");
					new_row.appendChild(demand_flag_cell);
				}
				var amt_cell;
				
				  
				if (parseInt(DATATYPE) == 1 || parseInt(DATATYPE) == 2) 
				{
					amt_cell = cell("TD", "input", "text", lbl, 0, 6, "", "",readonly+"text-align: right;", "25%", "right", fun_e, fun_n);
				}
				if (parseInt(DATATYPE) == 4) 
				{
					amt_cell = cell("TD", "textarea", "", lbl, "", 2, "tarea","", " ", "20%", "left", "", "");
				}
				
				
				new_row.appendChild(MANDATORY_cell);  
				new_row.appendChild(DLIMIT_cell);
				new_row.appendChild(row_cell);
				new_row.appendChild(DATATYPE_cell);
				new_row.appendChild(PERFORM_DESC_SNO_cell);
				new_row.appendChild(PERFORM_DESC_cell);
				new_row.appendChild(UNITS_cell);
				new_row.appendChild(amt_cell);
				new_row.appendChild(old_amt1);
				tbody.appendChild(new_row);
			}
		}
	}
	/////////////////// SEL      TYPE 5 // /////////////////////////////////////
	if (sel_type == 5) {
		if (process_code == 1) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) == 1) 
			{
				alert("Record Succesfully Saved  ")
				 transaction(5, 6);
			//	transaction(5, 10) 
				document.getElementById("save").disabled = true;
				try {
				//	 window.location.reload(true);
				} catch (e) {

				}  
				   
			}

		} else if (process_code == 2) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) >= 1) {
				alert("Record Succesfully Saved  ")
				document.getElementById("save").disabled = true;
				window.location.reload(true);
			 transaction(5, 10)
			}
		} else if (process_code == 6) {

			var total_amt = bR.getElementsByTagName("total_amt")[0].firstChild.nodeValue;
			var count_c = bR.getElementsByTagName("count_c")[0].firstChild.nodeValue;
			var count_c1 = bR.getElementsByTagName("count_c")[0].firstChild.nodeValue;
			  
			document.getElementById("orgcount").value=count_c1;
		 	 transaction(5, 10)
			if (parseInt(count_c) > 0) 
			{
				//   alert("Record Already Saved. ") 
				//document.getElementById("save").disabled = true;  
				// transaction(5,10)
			} else {
				//  transaction(5,10)     
				document.getElementById("save").disabled = false;
			}

			//document.getElementById("mainestamt").value = total_amt;

		} else if (process_code == 7) // 
		{
			var rows = bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;

			if (parseInt(rows) >= 1) {
				var row = document.getElementById("erow").value;

				try {
					//      window.opener.document.getElementById("eamt"+(parseInt(row)+1)).value=tr_amt;
					var hidden_tot_row = document.getElementById("hidden_tot_row").value;
					var amt = 0;
					for (i = 1; i <= parseInt(hidden_tot_row); i++) 
					{ 
						amt = parseFloat(amt)+parseFloat(document.getElementById("amt" +i).value);
					}
				 	//tr_amt = amt;   
				 	tr_amt=document.getElementById("final_amount").value;
					parent.document.getElementById("eamt" +(parseInt(row) +1)).value = roundNumber(parseFloat(tr_amt)  , 2);
					parent.document.getElementById("test").src = "";
					// document.frames[0].getElementById("eamt1").value=tr_amt;
				} catch (e) {
				}

				//  window.setTimeout("window.close()", 1);

			}
		}
		// Budget Estimate  DELETE  Response
		else if (process_code == 4) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			alert("Record Succesfully Deleted ");
			transaction(5, 10);
			// window.location.reload(true);

		} else if (process_code == 10) {
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var bR = xml_obj.responseXML.getElementsByTagName("response")[0];
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			if (len > 0) {
			//	alert("Record Already Exists for Selected Financial Year and Scheme")
				 
				try {
					document.getElementById("rowcount").value = len;
				} catch (e) {
				}

				for ( var i = 0; i < len; i++) 
				{
					var new_row = cell("TR", "", "", "row" +(i +1), (i +1),2, "", "", "", "", "", "", "");
					var EST_SNO = xmlValue(bR, "BUDGET_SNO", i);
					var SCH_SNO = xmlValue(bR, "SCH_SNO", i);
					var SCH_NAME = xmlValue(bR, "SCH_NAME", i);
					var ADMIN_REF_NO = xmlValue(bR, "ADMIN_REF_NO", i);
					var ADMIN_REF_DATE = xmlValue(bR, "ADMIN_REF_DATE", i);
					var TECH_REF_DATE = xmlValue(bR, "TECH_REF_DATE", i);
					var TECH_REF_NO = 0
					
					var BUDGET_EST_AMT =0;
					try
					{
						TECH_REF_NO = xmlValue(bR, "TECH_REF_NO", i);
						BUDGET_EST_AMT=xmlValue(bR, "BUDGET_EST_AMT", i);
					}catch(e){}
					var ORG_REV = xmlValue(bR, "ORG_REV", i);
					var MAINT_EST_AMT = xmlValue(bR, "MAINT_EST_AMT", i);
					var FIN_YEAR = xmlValue(bR, "FIN_YEAR", i);
					
					var FIN_YEAR_cell = cell("TD", "A", "", "", "EDIT", "EDIT","", "javascript:edit(" +EST_SNO +"," +(i +1)+")", "", "", "", "", "");
					var EST_SNO_cell = cell("TD", "input", "hidden", "EST_SNO"+(i +1), EST_SNO, 2, "tdText", "", "", "2%", "","", "");
					var SCH_SNO_cell = cell("TD", "input", "hidden", "SCH_SNO"+(i +1), SCH_SNO, 2, "tdText", "", "", "2%", "","", "");
					
					var SCH_NAME_CELL = cell("TD", "label", "", "SCH_NAME"+(i +1), SCH_NAME, 2, "", "", "", "", "", "", "");
					 
					var adminref_row = cell("TD", "label", "", "ADMIN_REF_NO" +(i +1),ADMIN_REF_NO, 2, "", "", "", "", "", "", "");
					var adminrefd_date = cell("TD", "label", "","ADMIN_REF_DATE" +(i +1), ADMIN_REF_DATE, 2, "", "", "", "","", "", "");

					var ref_row = cell("TD", "label", "", "TECH_REF_NO" +(i +1),TECH_REF_NO, 2, "", "", "", "", "", "", "");
					var refd_row = cell("TD", "label", "","TECH_REF_DATE" +(i +1), TECH_REF_DATE, 2, "", "", "", "","", "", "");
					
					var bud_NO_cell = cell("TD", "label", "", "BUDGET_EST_AMT"+(i +1), BUDGET_EST_AMT, 2, "", "", "", "2%", "","", "");
					
					ORG_REV=(ORG_REV=="O"?"Orignal" : "Revised" );
					var ORG_REV_cell = cell("TD", "label", "", "status"+(i +1), ORG_REV, 2, "", "", "", "2%", "","", "");
					
					//var rev_Chk_cell = cell("TD", "label", "","REVISED_EST_AMT" +(i +1), ORG_REV, 2,"", "", "", "2%", "", "", "");
					//var tot_cell = cell("TD", "label", "", "MAINT_EST_AMT"+(i +1), MAINT_EST_AMT, 2, "", "", " ", "","left", "", "");
					
					new_row.appendChild(FIN_YEAR_cell);
					new_row.appendChild(SCH_NAME_CELL);
					new_row.appendChild(EST_SNO_cell);
					new_row.appendChild(SCH_SNO_cell);
					
				//new_row.appendChild(adminref_row);
					//new_row.appendChild(adminrefd_date); 
					try
					{
						new_row.appendChild(ref_row);
						new_row.appendChild(refd_row);	
					}catch(e) {}
					
					new_row.appendChild(bud_NO_cell);  
					new_row.appendChild(ORG_REV_cell);
					
					//new_row.appendChild(rev_Chk_cell);
				//	new_row.appendChild(tot_cell);
					tbody.appendChild(new_row);
				}
			} // len if check
		}

	}
	if (sel_type == 6) {
		if (process_code == 1) {
			var len = xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			var tbody = document.getElementById("edata");
			var table = document.getElementById("etable");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var bR = xml_obj.responseXML.getElementsByTagName("response")[0];
			try {
				document.getElementById("rowcount").value = len;
			} catch (e) {
			}
		 
			for ( var i = 0; i <len; i++) 
			{
				var new_row = cell("TR", "", "", "row" +(i +1), (i +1), 2,"", "", "", "", "", "", "");
				var MAIN_ITEM_DESC = xmlValue(bR, "MAIN_ITEM_DESC", i);
				var SUB_ITEM_SNO = xmlValue(bR, "SUB_ITEM_SNO", i);
				var MAIN_ITEM_SNO = xmlValue(bR, "MAIN_ITEM_SNO", i);
				var SCH_TYPE_ID= xmlValue(bR, "SCH_TYPE_ID", i);
				var SUB_ITEM_DESC= xmlValue(bR, "SUB_ITEM_DESC", i);
				var SCH_TYPE_DESC = xmlValue(bR, "SCH_TYPE_DESC", i);
				if (SUB_ITEM_DESC == "0")  
					SUB_ITEM_DESC = "";
				var ACTIVE_STATUS = xmlValue(bR, "ACTIVE_STATUS", i);
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
			 
				var EDIT_cell = cell("TD", "A", "", "", "EDIT", "EDIT", "","javascript:select_edit(" +ACC_CODE_SNO +","+(i +1) +","+ACC_GROUP_SNO+","+SCH_TYPE_ID+","+MAIN_ITEM_SNO+","+SUB_ITEM_SNO+")", "", "2%", "", "", "");
				var delete_cell = cell("TD", "A", "", "", "Delete", "Delete", "","javascript:select_edit_delete(" +ACC_CODE_SNO +","+(i +1) +")", "", "2%", "", "", "");
				var slno = cell("TD", "label", "", "", (i +1), 2, "", "", "","2%", "center", "", "");
				var MAIN_ITEM_DESC_cell = cell("TD", "label", "","MAIN_ITEM_DESC" +(i +1), MAIN_ITEM_DESC, 2, "", "","", "7%", "", "", "");
				var SUB_ITEM_DESC_cell = cell("TD", "label", "","SUB_ITEM_DESC" +(i +1), SUB_ITEM_DESC, 2, "", "","", "7%", "", "", "");
				var ACCOUNT_HEAD_CODE_cell = cell("TD", "input", "text","ACCOUNT_HEAD_CODE" +(i +1), ACCOUNT_HEAD_CODE, 2,"", "", "width:35em", "5%", "", "", "");
				var ACC_CODE_SNO_cell = cell("TD", "input", "hidden","ACC_CODE_SNO" +(i +1), ACC_CODE_SNO, 2, "", "", "","2%", "", "", "");
				var ACTIVE_STATUS_cell = cell("TD", "label", "","ACTIVE_STATUS" +(i +1), ACTIVE_STATUS, 2, "", "", "","2%", "center", "", "");
				
				
				
				var ACC_GROUP_SNO_cell = cell("TD", "input", "hidden","ACC_GROUP_SNO" +(i +1), ACC_GROUP_SNO, 2, "", "", "","2%", "", "", "");
				var CODE_W_E_F_cell = cell("TD", "label", "", "CODE_W_E_F"+(i +1), CODE_W_E_F, 2, "", "", "", "10%", "", "", "");
				var SCH_TYPE_DESC_cell= cell("TD", "label", "", "SCH_TYPE_DESC"+(i +1), SCH_TYPE_DESC, 2, "", "", "", "10%", "", "", "");
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
				new_row.appendChild(CODE_W_E_F_cell);
				new_row.appendChild(ACTIVE_STATUS_cell);
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
				transaction(6, 1);
			} else {

				alert(" Record Already Exists");
			}

		} else if (process_code == 3 || process_code == 4) {
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
			if (parseInt(rows) >= 1) 
			{
				if (process_code == 4)
				{
					
				}else
				{
				alert("Record Succesfully Deleted  ")
				}
				transaction(6, 1);
			} else {

				alert(" Record Already Exists");
			}

		}
	}
  
}
function dcb_exp() {
	s = window.open('expenditure.jsp', "", "");
}
function expenditure(a, b, c) {
	/* a --> famt16
	 * b --> famt14 
	 * c --> famt17
	 * */
	var famt5 = 0;

	var famt16 = document.getElementById(a).value; // old is 5 new 17
	var famt14 = document.getElementById(b).value; // old is 3 new 16
	try {
		document.getElementById(c).value =roundNumber(parseFloat(famt16)- parseFloat(famt14),2); // old 6 new 18
	} catch (e) 
	{
		
		alert("error" + e)
	}
}
function expenditure2() {
	var famt5 = 0;

	var famt16 = document.getElementById("famt16").value; // old is 5 new 17
	var famt14 = document.getElementById("famt14").value; // old is 3 new 16
	try {
		document.getElementById("famt17").value =roundNumber(parseFloat(famt16)- parseFloat(famt14),2); // old 6 new 18
	} catch (e) {
	}
}

function roundNumber(number, decimals) {
	var newnumber = new Number(number +'').toFixed(parseInt(decimals));
	return parseFloat(newnumber); // Output the result to the form field
									// (change for your purposes)
}

function exp_break_up_edit(MAIN_ITEM_SNO, SUB_ITEM_SNO, GROUP_SNO, row) {

	var win = "";
	var arg = "";

	try {
		var sch_sno = document.getElementById("sch_sno").value;
		var pyear = document.getElementById("pyear").value;
		var pmonth = document.getElementById("pmonth").value;
		var pmonth = document.getElementById("pmonth").value;
		arg = "&sch_sno=" +sch_sno +"&pyear=" +pyear +"&pmonth=" +pmonth
				+"&GROUP_SNO=" +GROUP_SNO +"&MAIN_ITEM_SNO=" +MAIN_ITEM_SNO
				+"&SUB_ITEM_SNO=" +SUB_ITEM_SNO;
	} catch (e) {
		arg = "&sch_sno=0&pyear=0&pmonth=0&bc=0";

	}
	document.getElementById("test").src = "exp_break_up.jsp?erow=" +row
			+"&desc=" +MAIN_ITEM_SNO +"" +arg;
	// win=window.open("exp_break_up_edit.jsp?row="+row+"&desc="+MAIN_ITEM_SNO+""+arg,"BankAccountNumber","status=1,height=500,width=500,resizable=YES,
	// scrollbars=yes");;
	// win.focus();
}

function exp_break_up(MAIN_ITEM_SNO, SUB_ITEM_SNO, GROUP_SNO, row) {
	var win = "";
	var arg = "";

	var pmonth = 0;
	var sch_sno = document.getElementById("sch_sno").value;
	var pyear = document.getElementById("pyear").value;
	try {
		pmonth = document.getElementById("pmonth").value;
	} catch (e) {
		arg = "&sch_sno=0&pyear=0&pmonth=0&bc=0";
	}
	arg = "&sch_sno=" +sch_sno +"&pyear=" +pyear +"&pmonth=" +pmonth
			+"&GROUP_SNO=" +GROUP_SNO +"&MAIN_ITEM_SNO=" +MAIN_ITEM_SNO
			+"&SUB_ITEM_SNO=" +SUB_ITEM_SNO;

 
	document.getElementById("test").src = "exp_break_up.jsp?erow=" +row
			+"&desc=" +MAIN_ITEM_SNO +"" +arg;
	// win=window.open("exp_break_up.jsp?row="+row+"&desc="+MAIN_ITEM_SNO+""+arg,"BankAccountNumber","status=1,height=500,width=500,resizable=YES,
	// scrollbars=yes");;
	// win.focus();
}
function process_set(type, process_code1) {
	var rowcount = document.getElementById("rowcount").value;
	var sch_sno = document.getElementById("sch_sno").value;
	var pyear = document.getElementById("pyear").value;
	var params = "";
	if (sch_sno != 0 && pyear != 0) 
	{
		for (i = 1; i < rowcount; i++) 
		{
			var xmlobj3 = createObject();
			url = "../../../../../Transactions";
			params = "type=" +type +"&SCH_SNO=" +sch_sno +"&pyear=" +pyear;
			params += "&process_code=" +process_code1 +"&MAIN_ITEM_SNO="+document.getElementById("MAIN_ITEM_SNO" +i).value;
			params += "&GROUP_SNO="+document.getElementById("GROUP_SNO" +i).value;
			params += "&SUB_ITEM_SNO="+document.getElementById("SUB_ITEM_SNO" +i).value;
			xmlobj3.open("post", url, true);
			xmlobj3.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlobj3.setRequestHeader("Content-length", params.length);
			xmlobj3.onreadystatechange = function() 
			{
				process_set_res(xmlobj3, i);
			}
			xmlobj3.send(params);
		}
		flag = 0;
	} else {
		flag = 1;
	}
}
function process_set_res(xml_obj, i) {
	if (xml_obj.readyState == 4) {
		if (xml_obj.status == 200) {
			var bR = xml_obj.responseXML.getElementsByTagName("response")[0];
			var amt = bR.getElementsByTagName("amt")[0].firstChild.nodeValue;
			document.getElementById("famt" +i).value = amt;
		}
	}
}

function centage()
{
	var row = document.getElementById("rowcount").value;
	try {
		var net_amt = 0;
		var net_eeamt = 0;// Electrical Energy Charges
		var net_rramt = 0;// Repairs and Renewals
		var net_lab = 0;
		for (i = 1; i <= parseInt(row) - 1; i++)
		{
			var gr = document.getElementById("GROUP_SNO" +i).value;
			var ap = document.getElementById("APPLY_CENTAGE" +i).value;
			if (parseInt(gr) == 1 && parseInt(ap) == 1) 
			{
				
				
				var amt = document.getElementById("famt" +i).value;
				if (isNaN(amt) || amt=="")
				{
					amt=0;
				}
				net_amt = parseFloat(net_amt) +parseFloat(amt);
			}
		} 
		
		var cen_per = document.getElementById("rate").value;
		
		if (cen_per==0)
			alert("Centage percentage Not Entered for selected Scheme... Update Centage percentage under AME Lookup");
		
		net_eeamt = document.getElementById("famt3").value;
		net_rramt = document.getElementById("famt7").value;
		net_lab = document.getElementById("famt4").value;
		var centage_amt1 = (parseFloat(net_amt)) * parseFloat(cen_per-2) / 100;
		var centage_amt2 = (parseFloat(net_amt)) * parseFloat(cen_per-1) / 100;
		var centage_val = roundNumber(parseFloat(centage_amt1), 2);
		var centage_val2 = roundNumber(parseFloat(centage_amt2), 2);
		if (isNaN(centage_val) || centage_val=="")
			centage_val = "0";  
		 
		document.getElementById("famt" +row).value = centage_val2;
		document.getElementById("famt" +(row-1)).value = centage_val; 
		
		
		document.getElementById("totalcentage").value=0;
		document.getElementById("totalcentage").value=roundNumber(parseFloat(centage_amt1)+parseFloat(centage_amt2),2);
		var grtotal=0;  
		for (i = 1; i <= parseInt(row)  ; i++)    
		{
			var amt = document.getElementById("famt" +i).value;
			if (isNaN(amt) || amt=="")
			{
				amt=0;
			}
			grtotal=parseFloat(grtotal)+parseFloat(amt);
			 
		}
		document.getElementById("grandtotal").value=0;
		document.getElementById("grandtotal").value=roundNumber(parseFloat(grtotal),2);  
		
	} catch (e) {

	}
}

function toolin(e, dv) {
	var x = 0;
	var y = 0;
	if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
		x = e.screenX;
		y = e.screenY;
	} else {
		x = e.pageX;
		y = e.pageY;
	}
try
{ 
	document.getElementById(dv).style.visibility = "visible";
	document.getElementById(dv).style.top = y - 80;
	document.getElementById(dv).style.left = "20%";
	document.getElementById(dv).style.position = "absolute";
	document.getElementById(dv).style.marginleft = "200px";
}catch(e) {}
}
function toolout(dv) 
{
try { 	document.getElementById(dv).style.visibility = "hidden"; } catch(e) {}

}
function edit(op, rid) 
{
	/* 
	 * old code ----------------
	 * document.getElementById('key_value').value = document.getElementById("EST_SNO" +rid).value;
	// document.getElementById('sch_sno').value =document.getElementById("EST_SNO"+rid).value;
	document.getElementById('EST_SNO').value = document.getElementById("EST_SNO" +rid).value;
	document.getElementById('ref').value = document.getElementById("REF_NO"+rid).innerHTML;
	document.getElementById('refdate').value = document.getElementById("REF_DATE" +rid).innerHTML;
	document.getElementById('budamt').value = document.getElementById("BUDGET_EST_AMT" +rid).innerHTML;
	document.getElementById('revbudamt').value = document.getElementById("REVISED_EST_AMT" +rid).innerHTML;
	document.getElementById('mainestamt').value = document.getElementById("MAINT_EST_AMT" +rid).innerHTML;
	   */ 
	  var sch_select_sno=document.getElementById("SCH_SNO" +rid).value;
	  var x = document.getElementById("sch_sno");
	  for (var i = 0; i < x.length; i++) 
	  {
		  if(sch_select_sno==x.options[i].value)
		  { 
			  x.options[i].selected="true";   
		  }
	  } 
	document.getElementById('sch_sno').value =document.getElementById("sch_sno").value;
	document.getElementById('key_value').value = document.getElementById("EST_SNO" +rid).value;
	document.getElementById('EST_SNO').value = document.getElementById("EST_SNO" +rid).value;
	//document.getElementById('adminref').value = document.getElementById("ADMIN_REF_NO"+rid).innerHTML;
	//document.getElementById('adminrefdate').value = document.getElementById("ADMIN_REF_DATE"+rid).innerHTML;
	document.getElementById('ref').value = document.getElementById("TECH_REF_NO" +rid).innerHTML;
	document.getElementById('refdate').value = document.getElementById("TECH_REF_DATE" +rid).innerHTML;
	document.getElementById('budamt').value = document.getElementById("BUDGET_EST_AMT" +rid).innerHTML;
	document.getElementById("save").disabled = true;
	var info=document.getElementById("status" +rid).innerHTML;
	var d=new String(info);     
	if (d=="Orignal")
	{ 
		document.bud.ORG_REV[0].checked=true;
		document.bud.ORG_REV[0].disabled=true;
		document.bud.ORG_REV[1].disabled=true;
	}
	else
	{
		document.bud.ORG_REV[1].checked=true;
		document.bud.ORG_REV[0].disabled=true;   
		document.bud.ORG_REV[1].disabled=true;
	}
	
}
function edit_bud(len) {

	for (i = 1; i <= len; i++) {
		document.getElementById('famt' +(i - 1)).value = document.getElementById('lbl' +i).innerHTML;
	}
}
function edit_bud2(len) {

	for (i = 1; i <= len; i++) {
		document.getElementById('famt' +i).value = document.getElementById('lbl' +i).innerHTML;
	}
}
function pump_differ2(a, b) {

	// difference between the design quantity & pumping quantity
	/*
	 * 'famt0' ===a famt1 ==b (col1 and col2 differecnce) --- ((difference) /
	 * col1) *100 (1-2) / 1 * 100 > 10 %
	 */
	var actually_design = document.getElementById(a).value; // 1
	var actually_pumped = document.getElementById(b).value; // 2
	var differ = 0;
	try {
		var differ = Math.round(((parseFloat(actually_design) - parseFloat(actually_pumped)) / parseFloat(actually_design)) * 100);

	} catch (e) {

	}

	if (differ > 10) {

		alert(" Reason is mandatory if variation between the design quantity and pumping quantity is more than 10% ");
		// document.getElementById("famt3").focus();
		//document.getElementById("save").disabled = true;  
	}
}

function pump_differ() {

	// difference between the design quantity & pumping quantity
	/*
	 * (col1 and col2 differecnce) --- ((difference) / col1) *100 (1-2) / 1 *
	 * 100 > 10 %
	 */
	var actually_design = document.getElementById('famt0').value; // 1
	var actually_pumped = document.getElementById('famt1').value; // 2
	var differ = 0;
	try {
		var differ = Math.round(((parseFloat(actually_design) - parseFloat(actually_pumped)) / parseFloat(actually_design)) * 100);

	} catch (e) {

	}

	if (differ > 10) {

		alert(" Reason is mandatory if variation between the design quantity and pumping quantity is more than 10% ");
		// document.getElementById("famt3").focus();
		document.getElementById("save").disabled = true;
	}
}

function supply_differ() {
	/*     
	 *  (col1 and col2 differecnce) --- ((difference) / col1) *100   
	 (1-2)  / 1 * 100 > 10 % 
	 *    
	 * */ 
	var actually_pumped = document.getElementById('famt1').value; // 1
	var actually_supply = document.getElementById('famt2').value; // 2
	var differ = 0;
	try {
		var differ = Math.round(((parseFloat(actually_pumped) - parseFloat(actually_supply)) / parseFloat(actually_pumped)) * 100);
	} catch (e) {

	}
	if (differ > 10)
	{
		alert(" Reason is mandatory if variation between design quantity and supply quantity is more than 10%  ");
		// document.getElementById("famt3").focus();
		document.getElementById("save").disabled = true;
	}
}
function supply_differ2(a, b) {
	/*      
	 *   (col1 and col2 differecnce) --- ((difference) / col1) *100   
	 (1-2)  / 1 * 100 > 10 % 
	 *  
	 * */
	
	var actually_pumped = document.getElementById(a).value; // 1
 
	var actually_supply = document.getElementById(b).value; // 2
	var differ = 0;    
	try {
		var differ = Math.round(((parseFloat(actually_pumped) - parseFloat(actually_supply)) / parseFloat(actually_pumped)) * 100);
	} catch (e) {

	}

	if (differ > 10) {

		alert(" Reason is mandatory if variation between design quantity and supply quantity is more than 10%  ");
		// document.getElementById("famt3").focus();
		//document.getElementById("save").disabled = true;  
	}
}

function hours_differ(a) {

	// a -> famt5
	var desg_hour = document.getElementById('desg_hour').value; // desg_hour
	var average_hour = document.getElementById(a).value; // average_hour
	try {

		var dif = parseFloat(desg_hour) - parseFloat(average_hour);

		if (dif != 0)
			alert(" Actual Design Pumping Hours = "
					+desg_hour
					+" \n \n  Reason for difference in pumping hours is mandatory")
	} catch (e) {
	}

}
function nosupply_differ(a)
{
	var nosupply = document.getElementById(a).value; // desg_hour
	try {
		//if (a=='famt11')  
		// if (a=='famt11')
		// alert(" Please Enter the Habitation below 50% supply ")
		// if (a=='famt12')

		if (nosupply == "")  
		{
			alert(" Habitation water supply detail is mandatory")
		}
		if (nosupply >= 1 && a == 'famt11') 
		{
			alert(" Please Enter Reason for NO Supply");
			document.getElementById('famt11').focus();
		}
	} catch (e) {
	}

}
function exptest2(a, b, c, d, e) {
	/*	  OLD CODE 	  	 
		var t=0; 
		var sum=0;
		sum=document.getElementById("famt3").value;
		var famt2=parseFloat(document.getElementById("famt2").value);
		document.getElementById("famt4").value= roundNumber( (parseFloat(sum)*100000/(parseFloat(famt2)*1000)),2);
		var famt5=document.getElementById("famt5").value;
		document.getElementById("famt6").value=roundNumber(parseFloat(famt5)-parseFloat(sum),2);
	
	 */
	/* New CODE */
	/*
	 * a - famt14 b - famt2 c - famt15 d - famt16 e - famt17
	 */
	var t = 0;
	var sum = 0;
	// Maintenance cost of the scheme per KL
	sum = document.getElementById(a).value; // old 3 14
	// Quantity of Water actually Supplied
	var famt2 = parseFloat(document.getElementById(b).value);
	// Actual Expenditure incurred
	document.getElementById(c).value = roundNumber((parseFloat(sum) * 100000 / (parseFloat(famt2) * 1000)), 2);
	// 5 Demand Raised With Beneficiaries
	var famt16 = document.getElementById(d).value;
	// 6 Profit/Loss In maintaining the Scheme
	document.getElementById(e).value = roundNumber(parseFloat(famt16)- parseFloat(sum), 2);

	divclr();
}
function exp() {
	/* a -> 
	 * 
	 * 
	 *  
	 * */

	try {
		var exp_flag_cell = new String(opener.document.getElementById("exp_flag_cell").value);
		var name = exp_flag_cell.split(",");
		var famt8 = name[0];
		var famt9 = name[1];
		var famt10 = name[2];
		var famt11 = name[3];
		var rowcount2 = document.getElementById("rowcount2").value;
		var table = document.getElementById("etablesub");
		var t = 0;
		var sum = 0;
		for (t = 1; t <= rowcount2; t++) 
		{
			var a = document.getElementById("eamt" +t).value;
			if (a == "")
				a = "0";
			sum += parseFloat(a);
		}
		
		//var pen = document.getElementById("eamt12").value;
		//var lib = document.getElementById("eamt13").value;		
		//sum += parseFloat(lib) +parseFloat(pen);
		var famt2 = parseFloat(opener.document.getElementById("famt2").value);		
	//	opener.document.getElementById(famt8).value = roundNumber((parseFloat(sum) * 100000 / (parseFloat(famt2) * 1000)), 2);
		opener.document.getElementById(famt8).value = roundNumber((parseFloat(sum)   / (parseFloat(famt2) * 1000)), 2);
		opener.document.getElementById(famt9).value = roundNumber(sum / 100000, 2) ;  
		var famt16 = opener.document.getElementById(famt10).value;
		opener.document.getElementById(famt11).value = roundNumber(parseFloat(famt16) - parseFloat(sum)/ 100000, 2) ;

	} catch (e) {
  
	}
	window.close();  
 
}
function exp2(a, b, c, d, e) 
{

	/*  a -- famt14   -

	/*  a -- famt14   -
	 *  b -- famt2
	 *  c -- famt15
	 *  d -- famt16
	 *  e -- famt17
	 * */
	/*
	 * OLD CODE var t=0; var sum=0; sum=document.getElementById("famt3").value;
	 * var famt2=parseFloat(document.getElementById("famt2").value);
	 * document.getElementById("famt4").value= roundNumber(
	 * (parseFloat(sum)*100000/(parseFloat(famt2)*1000)),2); var
	 * famt5=document.getElementById("famt5").value;
	 * document.getElementById("famt6").value=roundNumber(parseFloat(famt5)-parseFloat(sum),2);
	 */
	/* New CODE */
	var t = 0;
	var sum = 0;
	try
	{
		sum = document.getElementById(a).value; // old 3 14
	}catch(e) {}
	var famt2 = parseFloat(document.getElementById(b).value);
	if (isNaN(famt2)){famt2=0;}
	if (famt2!=0)
	{
	document.getElementById(c).value = roundNumber((parseFloat(sum) * 100000 / (parseFloat(famt2) * 1000)), 2);
	}
	var famt16 = document.getElementById(d).value;
	if (isNaN(famt16)){famt16=0;}
	var res=roundNumber(parseFloat(famt16)- parseFloat(sum), 2);
	if (isNaN(res)){res=0;}
	document.getElementById(e).value =res
	
	
	//divclr();  
}

function del_item_map()
{
	try  
	{
		var sch_sno= document.getElementById("sch_sno").value;
		var pyear= document.getElementById("pyear").value;
		var TECH_REF_NO= document.getElementById("TECH_REF_NO").value;
		var xmlobj = createObject();  
		var url = "../../../../../Transactions";
		url+="?type=8&sch_sno="+sch_sno+"&pyear="+pyear+"&TECH_REF_NO="+TECH_REF_NO;
		 
			xmlobj.open("GET", url, true); 
			xmlobj.onreadystatechange = function() {
				del_item_map_process(xmlobj);  
			}	   
			
	}catch(e) {}
	xmlobj.send(null);

}



function del_end(xmlobj) 
{
	 
	try  
	{
		var bR = xmlobj.responseXML.getElementsByTagName("response")[0];  
		var rows =  xmlobj.responseXML.getElementsByTagName("rows")[0].firstChild.nodeValue;
		if (rows > 1 )
			alert("   Successuflly Deleted ");
		else
			alert(" Data Not Deleted");
	}catch(e) {}  
}

	function del_item_map_process(xmlobj)
	{
		if (xmlobj.readyState==4)
		 {	   
			if (xmlobj.status==200)
		    {
				del_end(xmlobj)				
				   
		    }
		 }  
		
	}
	var ct2=0;
function item_select(process_code1,TECH_REF_NO,sch_sno,pyear,MAIN_ITEM_SNO,SUB_ITEM_SNO,GROUP_SNO,row)
{
	 
	try 
	{
		var xmlobj = createObject();  
		var url = "../../../../../Transactions";
		url+="?type=7&process_code="+process_code1+"&TECH_REF_NO="+TECH_REF_NO+"&sch_sno="+sch_sno+"&pyear="+pyear;
		url+="&MAIN_ITEM_SNO="+MAIN_ITEM_SNO+"&SUB_ITEM_SNO="+SUB_ITEM_SNO+"&GROUP_SNO="+GROUP_SNO ;
			xmlobj.open("GET", url, true); 
			xmlobj.onreadystatechange = function()
			{
				ct2=item_select_process(xmlobj,row );   
			}	   
			
	}catch(e) {}  
	xmlobj.send(null); 
	
	return ct2;   
	
}
function item_select_process(xmlobj1,row)
{
	
	var ct=0;
	if (xmlobj1.readyState==4)
	 {	   
		if (xmlobj1.status==200)
	    {    
			try
			{
				var bR = xmlobj1.responseXML.getElementsByTagName("response")[0];  
				var rows = xmlValue(bR, "COUNT", 0);    	
				if (parseInt(rows)==1)      
				{
					document.getElementById("chk"+row).checked=true;
					document.getElementById("chk"+row).value='y';  
					ct=1;
				}
			}catch(e) {}
			
			
			
	    }
	 }   
 
	return ct;
}
function set_value()
{
	 
	var days= document.getElementById("days").value;
	var val=document.getElementById("pumped_dpr").value;  
	document.getElementById('famt1').value = roundNumber(parseFloat(val) * days, 3);   		
	try { document.getElementById('dv').style.visibility = "hidden"; } catch(e) {}    
}

function formula(no) 
{
	var s=window.open("./formula.html#"+no,"mywindow","menubar=1,resizable=0,width=550,height=350");
	
}