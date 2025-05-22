var process_code = 0;
var input_row_no = 0;
var display_table = "";
var display_body = "";
var sub_flag = 0;
var _flag = 0;
var gen_flag = 0;
var CUR_SNO = 0;

/*
		 Document   : Bill Demand 
		 Module	   : PMS
		 Created on : 28/1/2010
		 File Name  : Beneficiary_DCB_ob.js
		 Action To  :
		 File Desc  :  
		 Author     : K.Panneer Selvam
		 -----------------------------------------------------
		 Sno	  Date	 	Type	EMP ID	
		 -----------------------------------------------------
		 1	  28/1/2010  N
		
		
		 function 	command 	processs		          Description
		 ========	=======		========		=================================
		 show		1 or 2			select value from look up values of Division and B.Type 
		  		4				new record for DCB opening Balance   
		  		6		 	    new record for Interest opening Balance
		  		7				new recoded for Pumping Return -> all rows 
		  		9				new recoded for Pumping Return -> single row
		 show_2		show		3				List of Ben Name in Sub .Div
		 show_2		show		4				List of Meter for selected Ben. 
		 function 		values 					  Description
		 ---------		------			------------------------------------
		 data_show_dcb	'',7,''		new recoded for Pumping Return -> all rows
		 data_show_dcb	'',9,1		new recoded for Pumping Return -> single row
		 ckset()			1				meter get by this function
		 single_row_update 9,row
*/
		
/*
		 function showInt()
		 {
		 document.getElementById('butInt').disabled=false;
		 }
*/


function wc_cal_prv(ben_value,selsno,row)
{  
	command = "wccalprv";	   
	var tot_read=document.getElementById("qty").value
	var rectmonth=document.getElementById("pmonth").value
	var rectyear=document.getElementById("pyear").value
	window.opener.document.getElementById("rectqty"+row).value=tot_read; 
	window.opener.document.getElementById("rectyear"+row).value=rectyear
	window.opener.document.getElementById("rectmonth"+row).value=rectmonth 
	
 	var url = "pun_ajax.jsp?command="+command+"&tot_read="+tot_read+"&ben_value="+ben_value+"&selsno="+selsno+"&rectmonth="+rectmonth+"&rectyear="+rectyear;
 	var xmlobj2 = createObject();
 	xmlobj2.open("GET", url, true);
 	xmlobj2.onreadystatechange = function() 
	{
 		wc_cal_prv_process(xmlobj2,row,tot_read);
	}
 	xmlobj2.send(null);
} 
function wc_cal_prv_process(xmlobj2,row,qty)
{
	if (xmlobj2.readyState == 4) 
	{
		if (xmlobj2.status == 200) 
		{ 
			var results_str =new String(xmlobj2.responseText);
			var results=results_str.split("|");
			var res = results[1];
			window.opener.document.getElementById("rectamt"+row).value=res;		
			 
			window.opener.document.getElementById("redemption"+row).innerHTML=qty+" / "+res;
			
			self.close()
		}
	}
}
function wc_cal(tot_read,ben_value,selsno,row)  
{  
	 	command = "wccal";	 
	 	var url = "pun_ajax.jsp?command="+command+"&tot_read="+tot_read+"&ben_value="+ben_value+"&selsno="+selsno ;
	 	var xmlobj2 = createObject();
	 	xmlobj2.open("GET", url, true);
	 	xmlobj2.onreadystatechange = function() 
		{
	 		wc_cal_process(xmlobj2,row);
		}
	 	xmlobj2.send(null);
}
function wc_cal_process(xmlobj2,row)
{
	if (xmlobj2.readyState == 4) 
	{
		if (xmlobj2.status == 200) 
		{ 
			var results_str =new String(xmlobj2.responseText);
			var results=results_str.split("|");
			var res = results[1];
			document.getElementById("amt_cell"+row).innerHTML=res; 
			var amt="";
			var tot_amt=0;
			var rowcnt_meter=document.getElementById("rowcnt_meter").value; 	
			for (i=1;i<=rowcnt_meter;i++)
			{	
				amt=document.getElementById("amt_cell"+i).innerHTML;
				if (amt=="") amt=0;
				tot_amt+=parseFloat(amt);
			}	
			document.getElementById("net_wc_amt").value=tot_amt;
		}
	}
}
function urban_details()
{
	command = "ursno";	 
	var ursno = document.getElementById("ursno").value
	var url = "pun_ajax.jsp?ursno=" + ursno+"&command=Urban_Details"  ;
	var xmlobj = createObject();
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		urban_details_process(xmlobj );
	}
	xmlobj.send(null);
}
function urban_details_process(xmlobj ) 
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{ 
			var results_str =new String(xmlobj.responseText);
			var results=results_str.split("|");
			var res = results[1];
			document.getElementById("idtab").innerHTML=res;
		}
	}
}

function frstatuscheck() 
{
	command = "frstatuscheck";
	var fyear = document.getElementById("year").value
	var fmonth = document.getElementById("month").value
	var url = "../../../../../Bill_Demand?command=" + command + "&fmonth="+ fmonth + "&fyear=" + fyear;
	var xmlobj = createObject();
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		frstatuscheck_process(xmlobj, command, fmonth, fyear);
	}
	xmlobj.send(null);
}

function frstatuscheck_process(xmlobj, command, fmonth, fyear) 
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var monthArr = [ '', 'January', 'February', 'March', 'April','May', 'June', 'July', 'August', 'September', 'October','November', 'December' ];
			var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
			var FR_row = bR.getElementsByTagName("FR_row")[0].firstChild.nodeValue;
			if (FR_row > 0) 
			{
				document.getElementById("pr_status").value = 0;
				alert("Pumping Returns Already Freezed For "
						+ monthArr[fmonth]+" "
						+ fyear
						+ ""
						+ " Pumping Return Entry Not Possible...Unfreeze PR ");
				window.close();
			}
		} 
	}
}
function ob_freeze_status() 
{
	//alert("inside");
	var fyear = document.getElementById("year").value	 
	var fmonth = document.getElementById("month").value
	if (parseInt(fmonth)<3)
		fyear=parseInt(fyear)-1;
	//alert("fyear is "+fyear);
//	alert("fmonth is "+fmonth);
	var url = "../../../../../OB_Change_Accept?year=" +fyear;
	var xmlobj = createObject();
	xmlobj.open("GET", url, true);
	xmlobj.onreadystatechange = function() 
	{
		ob_freeze_status_process(xmlobj);
	}
	xmlobj.send(null); 
}
function ob_freeze_status_process(xmlobj) 
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			var ob_freeze_status;
			var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
			try
			{
				 var fyear = document.getElementById("year").value
				// alert("fyear is "+fyear);
				 ob_freeze_status=bR.getElementsByTagName("OB_FREEZE")[0].firstChild.nodeValue;
				 if (ob_freeze_status=='Y')
					 {
				//	 alert("inside is ");
				 alert("Opening Balance Already Freezed for April "+fyear+"  \n \n Cannot be Updated...Raise OB Unfreeze Request!!!   ");
			 	// window.close(); 
					 }

				 document.getElementById("ob_free_status").value=ob_freeze_status;
				 
			}catch(e) 
			{ 
			 	 ob_freeze_status=='';
				 document.getElementById("ob_free_status").value=ob_freeze_status;

			}  
			 
		} 
	}
}
function valueSet(process, command, flag)
{
	process_code = process;
	if (command == "report") 
	{
		if (process_code == 3) 
		{
			document.getElementById("pr_status").value = 0;
			url = "../../../../../ob_report?command="+command+"&bentype="+document.getElementById("bentype").value
					+"&process_code="+process_code+"&input_value="+document.getElementById("selsno").value+"&YEAR="
					+ document.getElementById("year").value + "&MONTH="
					+ document.getElementById("month").value;
		}  
		var xmlobj = createObject();
		xmlobj.open("GET", url, true);
		xmlobj.onreadystatechange = function() 
		{
			valueSet_process(xmlobj, command, "", flag);
		}
		xmlobj.send(null);
	}
}

function valueSet_process(xmlobj, command, input_value, flag) 
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			if (process_code == 3) 
			{
				document.getElementById("pr_status").value = 1;
				var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
				var i = 0;
				var month_sno =0;
				try
				{
					month_sno=bR.getElementsByTagName("month_sno")[i].firstChild.nodeValue;
					 
				}catch(e) 
				{
					   
				}
				var BENEFICIARY_OB_SNO="0";
				try
				{
					BENEFICIARY_OB_SNO= bR.getElementsByTagName("BENEFICIARY_OB_SNO")[i].firstChild.nodeValue;
				}catch(e) {}
				var ob = bR.getElementsByTagName("ob")[i].firstChild.nodeValue;
				var collection = bR.getElementsByTagName("collection")[i].firstChild.nodeValue;
				var yesteryear = bR.getElementsByTagName("yesteryear")[i].firstChild.nodeValue;
				var currentyear = bR.getElementsByTagName("currentyear")[i].firstChild.nodeValue;
				var demandupto = bR.getElementsByTagName("demandupto")[i].firstChild.nodeValue;
				var coll_up_prv_wcharge = bR.getElementsByTagName("coll_up_yester_year")[i].firstChild.nodeValue;
				var coll_up_yester_year = bR.getElementsByTagName("coll_up_prv_wcharge")[i].firstChild.nodeValue;
				var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
				var BENEFICIARY_SNO = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
				var OB_FOR_MTH_MAINT_CHARGES = bR.getElementsByTagName("OB_FOR_MTH_MAINT_CHARGES")[i].firstChild.nodeValue;
				var OB_FOR_MTH_YESTER_YR_WC = bR.getElementsByTagName("OB_FOR_MTH_YESTER_YR_WC")[i].firstChild.nodeValue;
				var OB_FOR_MTH_CUR_YR_WC = bR.getElementsByTagName("OB_FOR_MTH_CUR_YR_WC")[i].firstChild.nodeValue;
				var OB_FOR_MTH_INT_AMT_WC = bR.getElementsByTagName("OB_FOR_MTH_INT_AMT_WC")[i].firstChild.nodeValue;
				var SCH_SNO="0";
				
				try
				{
					SCH_SNO= bR.getElementsByTagName("SCH_SNO")[i].firstChild.nodeValue;
					 
					document.getElementById("mSCH_SNO").value=SCH_SNO;
				}catch(e){}
				document.getElementById("ben_value").value = BENEFICIARY_SNO;
				document.getElementById("mobsno_int").value = BENEFICIARY_OB_SNO;
				if (flag == 3) {
					document.getElementById("OB_YESTER_YR_WC").value = yesteryear;
					document.getElementById("OB_CUR_YR_WC").value = currentyear;
					document.getElementById("OB_MAINT_CHARGES").value = ob;
					document.getElementById("DMD_UPTO_PRV_MTH_WC").value = demandupto;
					document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value = coll_up_prv_wcharge;
					document.getElementById("COLN_UPTO_PRV_MTH_WC").value = coll_up_yester_year;
					document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value = collection;
					document.getElementById("OB_FOR_MTH_MAINT_CHARGES").value = OB_FOR_MTH_MAINT_CHARGES;
					document.getElementById("OB_FOR_MTH_YESTER_YR_WC").value = OB_FOR_MTH_YESTER_YR_WC;
					document.getElementById("OB_FOR_MTH_CUR_YR_WC").value = OB_FOR_MTH_CUR_YR_WC;
				}

				if (flag == 4) {
					var main_int_up_to_prv_fyear = bR.getElementsByTagName("main_int_up_to_prv_fyear")[i].firstChild.nodeValue;
					var main_int_collected = bR.getElementsByTagName("main_int_collected")[i].firstChild.nodeValue;
					var water_int_up_to_prv_fyear = bR.getElementsByTagName("water_int_up_to_prv_fyear")[i].firstChild.nodeValue;
					var water_int_levied = bR.getElementsByTagName("water_int_levied")[i].firstChild.nodeValue;
					var water_int_collected_prv_month = bR.getElementsByTagName("water_int_collected_prv_month")[i].firstChild.nodeValue;
					var ifany = 0;
					try
					{
					  ifany = bR.getElementsByTagName("ifany")[i].firstChild.nodeValue;
					}catch(e) {}
					document.getElementById("OB_INT_CUR_YR_MAINT").value = main_int_up_to_prv_fyear;
					document.getElementById("OB_INT_PRV_YR_MAINT").value = ifany;
					document.getElementById("COLN_INT_UPTO_PRV_MTH_MAINT").value = main_int_collected;
					document.getElementById("OB_INT_AMT_WC").value = water_int_up_to_prv_fyear;
					document.getElementById("DMD_INT_UPTO_PRV_MTH_WC").value = water_int_levied;
					document.getElementById("COLN_INT_UPTO_PRV_MTH_WC").value = water_int_collected_prv_month;
					document.getElementById("OB_FOR_MTH_INT_AMT_WC").value = OB_FOR_MTH_INT_AMT_WC;
				}
			}
		}
	}

}
function pdf_show(process, command) {

	process_code = process;
	if (command == "pdf") {
		var ben_type = document.getElementById("bentype").value;
		var year = document.getElementById("year").value;
		var month = document.getElementById("month").value;

		window.open("../../../../../Beneficiary_DCB_ob?command=" + command
				+ "&process_code=" + process_code + "&ben_type=" + ben_type
				+ "&year=" + year + "&month=" + month);
	}
}
function pdf_show_process(xmlobj, command, input_value) {

	if (xmlobj.readyState == 4) {

		if (xmlobj.status == 200) {

			if (process_code == 1) {

			}
		}
	}
}
 
function ob_dis(process, command, df1, df2, inp2)
{
	url = "../../../../../ob_report?command=" + command + "&bentype="
	+ document.getElementById("bentype").value
	+ "&process_code=" + process_code + "&divcode="
	+ document.getElementById("subdiv").value + "&YEAR="
	+ document.getElementById("year").value + "&MONTH="
	+ document.getElementById("month").value+"&block="+block; 
	document.getElementById("pr_status").value = 0;
}


function grid_show(process, command, df1, df2, inp2) {

	process_code = process;
	// process 4  -> List of metter
	var Child_div=0;
	try {Child_div=document.getElementById("Child_div").value;}catch(e){}
	var bentype = document.getElementById("bentype").value;
	if (command != "report") 
	{
			if (process_code == 3 && bentype == 6) 
			{
				document.getElementById('block').style.visibility = 'visible';
				document.getElementById('dis').style.visibility = 'visible';
				document.getElementById("pr_status").value = 0;
			} else {
		
				document.getElementById('block').style.visibility = 'hidden';
				document.getElementById('dis').style.visibility = 'hidden';
				}
	}
	var option=0;
	
	if (command == "report") 
	{
				if (process_code == 1 || process_code == 7) 
				{
					if (process_code == 7) 
					{
						//alert("i ");ob_free_status
						var ob_freeze_status=document.getElementById("ob_free_status").value;
						var fyear=document.getElementById("year").value;
					//	 alert("ob_freeze_status is "+ob_freeze_status);
				 if (ob_freeze_status=='Y')
					 {
				// alert("inside is ");
				 alert(" Opening Balance Already Freezed for April "+fyear+"  \n \n Cannot be Updated...Raise OB Unfreeze Request!!!   ");
				//return;
			 	 window.close(); 
				
					 }
						
						
						_flag = 1;
						option=1;
					} 
					var block="0";
						try
							{
								block=document.getElementById("selblk").value;
								
							}catch(e) {}
							if (block=="undefined")
							{
								block=0;
							}
							    
							url = "../../../../../ob_report?option="+option+"&command=" + command + "&bentype="
								+ document.getElementById("bentype").value
								+ "&process_code=" + process_code + "&divcode="
								+ document.getElementById("subdiv").value + "&YEAR="
								+ document.getElementById("year").value + "&MONTH="
								+ document.getElementById("month").value+"&block="+block;
							
								document.getElementById("pr_status").value = 0;
							
			}
			if (process_code == 2) 
			{
						url = "../../../../../ob_report?command=" + command + "&bentype="
							+ document.getElementById("bentype").value
							+ "&process_code=" + process_code + "&divcode="
							+ document.getElementById("subdiv").value + "&YEAR="
							+ document.getElementById("year").value + "&MONTH="
							+ document.getElementById("month").value;
			}else if (process_code == 4) 
			{
						url = "../../../../../ob_report?command=" + command
							+ "&input_value="
							+ document.getElementById("benname").value + "&bentype="
							+ document.getElementById("bentype").value
							+ "&process_code=" + process_code + "&divcode="
							+ document.getElementById("subdiv").value + "&YEAR="
							+ document.getElementById("year").value + "&MONTH="
							+ document.getElementById("month").value;
			}
	} else 
	{

			document.getElementById("selbentype").value = document.getElementById(inp2).value;
			display_table = df1;
			display_body = df2;
			
					if (process_code == 4) 
							{
								var div_value = 0;
									try {
											div_value = document.getElementById("subdiv").value;
										} catch (e) {
											div_value = 0;
										}
									
									var ben_ = document.getElementById("ben_value").value;
									
									if (ben_ == "") 
									{
										ben_ = "0";
										gen_flag = 1;
									} else {
										gen_flag = 0;
									}
				
									if (ben_ != "0") 
									{
											url = "../../../../../Beneficiary_DCB_ob?command=" + command
												+ "&bentype=" + document.getElementById(inp2).value
												+ "&process_code=" + process_code + "&divcode="
												+ document.getElementById("subdiv").value;
											url += "&ben_sno=" + ben_;
											url += "&year=" + document.getElementById("year").value;//YEAR
											url += "&month=" + document.getElementById("month").value
												+ "&sub_div=" + div_value;//MONTH
									}
									//   Hide The NoN VB  : DATE 22 // 11 // 2011 
									  
									if (bentype == 6) 
									{
											document.getElementById('block').style.visibility = 'visible';
											document.getElementById('dis').style.visibility = 'visible';
									}
					
		
				} else {

					
					var extraCommand = obPage ? "&ob_show=true" : "";


					 
						if (bentype == 6) {
							sub_process = 1;
							var dis_value = document.getElementById("dis_value").value;
							var block_value = document.getElementById("block_value").value;
							var ben_value = document.getElementById("ben_value").value;
							url = "../../../../../Beneficiary_DCB_ob?command=" + command
									+ "&bentype=" + document.getElementById(inp2).value
									+ "&process_code=" + process_code + "&divcode="
									+ document.getElementById("subdiv").value;
							url += "&dis_value=" + dis_value + "&block_value="
									+ block_value + "&sub_process=" + sub_process
									+ "&ben_value=" + ben_value+"&Child_div="+Child_div+"&"+extraCommand;
			
						} else {
							sub_process = 0;
							url = "../../../../../Beneficiary_DCB_ob?command=" + command
									+ "&bentype=" + document.getElementById(inp2).value
									+ "&process_code=" + process_code + "&divcode="
									+ document.getElementById("subdiv").value+"&Child_div="+Child_div+"&"+extraCommand;
							url += "&sub_process=" + sub_process;
						 
						}

		}
		if (process_code == 5) {
			var dis_value = document.getElementById("dis_value").value;
			var block_value = document.getElementById("block_value").value;
			url = "../../../../../Beneficiary_DCB_ob?command=" + command
					+ "&bentype=" + document.getElementById(inp2).value
					+ "&process_code=" + process_code + "&divcode="
					+ document.getElementById("subdiv").value;
			url += "&dis_value=" + dis_value + "&block_value=" + block_value
					+ "&sub_process=" + sub_process;
		}
	}

	if (gen_flag != 1)
	{
		document.getElementById("pr_status").value = 0;
		var xmlobj = createObject();
		xmlobj.open("GET", url, true);
		xmlobj.onreadystatechange = function() {
			result_process(xmlobj, command, "");
		}
		xmlobj.send(null);
	}
}

function data_show_dcb(command, process, input_value) 
{
	process_code = process;
	if (command == 'show') {
		var ben_type_fltr = 0;
		var sub_div = 0;
		try {
			ben_type_fltr = document.getElementById("ben_type_fltr").value;
			sub_div = document.getElementById("subdiv").value;
		} catch (e) {
			ben_type_fltr = 0;
			sub_div = 0;
		}
		
		var div=0;
		 
		try
		{
			div=document.getElementById("Child_div").value;
		}catch(e){}
		 
		if (process == 1 || process == 2 || process == 3)
									  var url = "../../../../../Beneficiary_DCB_ob?command=" + command
											+ "&div="+div+"&sub_div=" + sub_div + "&ben_type_fltr=" + ben_type_fltr
											+ "&input_value=" + input_value + "&process_code="
											+ process_code + "&divcode="
											+ document.getElementById("subdiv").value + "&bentype="
									 		+ document.getElementById("bentype").value;	
		
		//alert(url);
								var xmlobj = createObject();								
								xmlobj.open("GET", url, true);
								
								xmlobj.onreadystatechange = function() {
							 
								 	result_process(xmlobj, command, input_value);	
								 
								}
								 xmlobj.send(null);
										
	} else if (command == 'add') {
		var flag = 0;
		var xmlobj = createObject();
		if (process_code == 4) {
			// Insert the DCB  ----------------- Store
			document.getElementById("pr_status").value = 0;
			var yeasteryear = document.getElementById("OB_YESTER_YR_WC").value;
			var currentyear = document.getElementById("OB_CUR_YR_WC").value;
			var obmain = document.getElementById("OB_MAINT_CHARGES").value;
			var wc = document.getElementById("DMD_UPTO_PRV_MTH_WC").value;
			var colluptoprvmthyesteryr = document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value;
			var collecprvmonth = document.getElementById("COLN_UPTO_PRV_MTH_WC").value;
			var collectionupto = document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value;
			var Scheme = document.getElementById("Scheme").value;
			if (obmain == "" || collectionupto == "" || yeasteryear == ""
					|| currentyear == "" || wc == "" || collecprvmonth == ""
					|| Scheme == "" || Scheme == 0)
				flag = 1;
			else
				flag = 0;
			
			currentyear=cv(currentyear);
			
			url = "../../../../../Beneficiary_DCB_ob";
			url+="?OB_YESTER_YR_WC=" + yeasteryear;
			url+="&OB_CUR_YR_WC=" + currentyear;
			url+="&OB_MAINT_CHARGES=" + obmain;
			url+="&DMD_UPTO_PRV_MTH_WC=" + wc;
			url+="&COLN_UPTO_PRV_MTH_WC=" + collecprvmonth;
			url+="&COLN_UPTO_PRV_MTH_MAINT=" + collectionupto;
			url+="&COLN_UPTO_PRV_MTH_YESTER_YR=" + colluptoprvmthyesteryr;
			url+="&command=" + command;
			url+="&Scheme=" + Scheme;
			url+="&process_code=" + process_code;
			url+="&year=" + document.getElementById("year").value;
			url+="&month=" + document.getElementById("month").value;
			url+="&BENEFICIARY_SNO="+document.getElementById("ben_value").value;//("BENEFICIARY_SNO").value;
			url+="&OFFICE_ID=" + document.getElementById("OFFICE_ID").value;
			url+="&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;
			url+="&OB_FOR_MTH_MAINT_CHARGES="+(parseFloat(obmain)-parseFloat(collectionupto));
			url+="&OB_FOR_MTH_CUR_YR_WC="+(parseFloat(currentyear)+parseFloat(wc)-parseFloat(collecprvmonth));
			url+="&OB_FOR_MTH_YESTER_YR_WC="+(parseFloat(yeasteryear)-parseFloat(colluptoprvmthyesteryr));
		  
		} else if (process_code == 10) {
			document.getElementById("pr_status").value = 0;
			var obmain = document.getElementById("OB_MAINT_CHARGES").value;
			mSCH_SNO= document.getElementById("mSCH_SNO").value;
			var collectionupto = document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value;
			var yeasteryear = document.getElementById("OB_YESTER_YR_WC").value;
			var currentyear = document.getElementById("OB_CUR_YR_WC").value;
			var wc = document.getElementById("DMD_UPTO_PRV_MTH_WC").value;
			var collecprvmonth = document.getElementById("COLN_UPTO_PRV_MTH_WC").value;
			var colluptoprvmthyesteryr = document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value;
			
			if (obmain == "" || collectionupto == "" || yeasteryear == ""
					|| currentyear == "" || wc == "" || collecprvmonth == "")
				flag = 1;
			else
				flag = 0;

			url = "../../../../../Beneficiary_DCB_ob";
			url += "?OB_MAINT_CHARGES=" + obmain;
			url += "&COLN_UPTO_PRV_MTH_MAINT=" + collectionupto;
			url += "&OB_YESTER_YR_WC=" + yeasteryear;
			url += "&OB_CUR_YR_WC=" + currentyear;
			url += "&DMD_UPTO_PRV_MTH_WC=" + wc;
			url += "&COLN_UPTO_PRV_MTH_WC=" + collecprvmonth;
			url += "&COLN_UPTO_PRV_MTH_YESTER_YR=" + colluptoprvmthyesteryr;
			url += "&command=" + command;
			url += "&process_code=" + process_code;
			url += "&year=" + document.getElementById("year").value;
			url += "&month=" + document.getElementById("month").value;
			url += "&BENEFICIARY_SNO="+ document.getElementById("ben_value").value;
			url += "&OFFICE_ID=0";
			url += "&mSCH_SNO="+mSCH_SNO
			url += "&BENEFICIARY_TYPE_SNO=0";
			url += "&selsno="+document.getElementById("selsno").value;
			url += "&mobsno=" + document.getElementById("mobsno").value;
			url += "&OB_FOR_MTH_MAINT_CHARGES="+(parseFloat(obmain)-parseFloat(collectionupto));
			url += "&OB_FOR_MTH_CUR_YR_WC="+(parseFloat(currentyear)+parseFloat(wc)-parseFloat(collecprvmonth));
			url += "&OB_FOR_MTH_YESTER_YR_WC="+(parseFloat(yeasteryear)-parseFloat(colluptoprvmthyesteryr));
			 
			 
		} else if (process_code == 6 || process_code == 11) {
			document.getElementById("pr_status").value = 0;
			//Insert the Interest  ----------------- Store    
			flag = 0;
			var obint = document.getElementById("OB_INT_CUR_YR_MAINT").value;
			var ifany = document.getElementById("OB_INT_PRV_YR_MAINT").value;
			var colint_main = document.getElementById("COLN_INT_UPTO_PRV_MTH_MAINT").value;
			var ob_int_wc = document.getElementById("OB_INT_AMT_WC").value;
			var int_upto_cur_month = document.getElementById("DMD_INT_UPTO_PRV_MTH_WC").value;
			var colint_wc 		   = document.getElementById("COLN_INT_UPTO_PRV_MTH_WC").value;
			var OB_FOR_MTH_INT_AMT_WC = document.getElementById("OB_FOR_MTH_INT_AMT_WC").value;
			var mobsno_int = document.getElementById("mobsno_int").value;
			var sch_sno = document.getElementById("mSCH_SNO").value;
			var mobsno="0";
			try {mobsno= document.getElementById("mobsno").value}catch (e) {}
			if (obint == "" || colint_main == "" || ob_int_wc == ""
					|| int_upto_cur_month == "" || colint_wc == ""
					|| ifany == "")
				flag = 1;
			else  
				flag = 0;
			url = "../../../../../Beneficiary_DCB_ob";
			url += "?OB_INT_CUR_YR_MAINT=" + obint;
			url += "&OB_INT_PRV_YR_MAINT=" + ifany;
			url += "&COLN_INT_UPTO_PRV_MTH_MAINT=" + colint_main;
			url += "&OB_INT_AMT_WC=" + ob_int_wc;
			url += "&DMD_INT_UPTO_PRV_MTH_WC=" + int_upto_cur_month;
			url += "&COLN_INT_UPTO_PRV_MTH_WC=" + colint_wc;
			url += "&OB_FOR_MTH_INT_AMT_WC=" + OB_FOR_MTH_INT_AMT_WC;
			url += "&year=" + document.getElementById("year").value;
			url += "&month=" + document.getElementById("month").value;
			url += "&mobsno_int=" + mobsno_int;
			url += "&mobsno=" + mobsno;
			url += "&BENEFICIARY_SNO="+document.getElementById("SELBENEFICIARY_SNO").value;
			url += "&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;
			url += "&command=" + command;
			url += "&process_code=6";
			url += "&obsno=" + document.getElementById("obsno").value+"&sch_sno="+sch_sno;			 
		}

		else if (process_code == 7)
		{
			var netunit = document.getElementById("netunit").value;
			if (netunit == "" ) 
			{
				alert("Total Units Missing")
				sub_flag = 1;
			} else 
			{
				//  Pumping Return ----------------- Store  
				var rowcnt_meter = document.getElementById("rowcnt_meter").value;
				var selbentype = document.getElementById("selbentype").value;
				url = "../../../../../Beneficiary_DCB_ob";
				url += "?command=" + command;
				url += "&process_code=" + process_code;
				url += "&year=" + document.getElementById("year").value;//YEAR
				url += "&month=" + document.getElementById("month").value;//MONTH
				url += "&BENEFICIARY_SNO="+ document.getElementById("BENEFICIARY_SNO").value;//BENEFICIARY_SNO
				url += "&OFFICE_ID="+ document.getElementById("OFFICE_ID").value;//OFFICE_ID
				url += "&SUBDIV_OFFICE_ID="+document.getElementById("subdiv").value;//OFFICE_ID
				url += "&rowcnt_meter=" + rowcnt_meter;//OFFICE_ID
				url += "&BENEFICIARY_TYPE_SNO="+ document.getElementById("bentype").value;
				url += "&netunit=" + document.getElementById("netunit").value;//netunit

				for (i = 1; i <= rowcnt_meter; i++) {

					var selsno = document.getElementById("selsno" + i).value;
					var unit = document.getElementById("nounit" + i).value;

					if (unit >= 0) {
						var read = document.getElementById("read" + i).value;
						url += ("&METRE_SNO" + i) + "=" + selsno;
						url += "&METRE_INITIAL_READING"+i+"="+document.getElementById("METRE_INIT_READING"+ i).value;
						url += ("&METRE_CLOSING_READING" + i) + "=" + read;
						url += ("&QTY_CONSUMED" + i) + "="+ document.getElementById("nounit" + i).value;
						url += ("&PRVMETRE_WORKING" + i)+ "="+ document.getElementById("PRVMETRE_WORKING"+ i).value;
						url += ("&CHILD_METER_QTY" + i)+ "="+ document.getElementById("PARENT_METER" + i).value;
						url += ("&difference" + i)+ "="+ document.getElementById("difference" + i).value;//netunit
						url += ("&QTY_RECTIFY" + i) + "="+document.getElementById("rectqty"+i).value;
						url += ("&AMT_RECTIFY" + i) + "="+document.getElementById("rectamt"+i).value;
						url += ("&MONTH_RECTIFY" + i) + "="+document.getElementById("rectmonth"+i).value;
						url += ("&YEAR_RECTIFY" + i) + "="+document.getElementById("rectyear"+i).value;
						var aqty = document.getElementById("ALLOTED_QTY" + i).value;
						// Excess Qty 
						var ben_type = document.getElementById("bentype").value;

						if (aqty > 0) {

							if (ben_type > 6) {

								var eqty = (parseInt(unit) - parseInt(aqty));

								if (eqty > 0)
									eqty = eqty;
								else
									eqty = 0;
							} else {
								eqty = 0;

							}
						} else {
							eqty = 0;

						}
						var meter = "";
						if (document.getElementById("METRE_FIXED" + i).innerHTML == "Yes")
							meter = "Y";
						else
							meter = "N";
						var meterw = "";
						if (document.getElementById("METRE_WORKING" + i).value == "Y")
							meterw = "Y";
						else
							meterw = "N";
						url += "&METRE_WORKING" + i + "=" + meterw;
						url += "&METRE_FIXED" + i + "=" + meter;
						url += ("&ALLOTED_QTY" + i) + "=" + aqty;
						url += ("&EXCESS_QTY" + i) + "=" + eqty;
					}

				}

			}
		}


		if (flag == 0) {
			
			xmlobj.open("GET", url, true);
			xmlobj.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
			xmlobj.send(null);
			xmlobj.onreadystatechange = function() {
				result_process(xmlobj, command, process_code);
			}
		} else {	 
			alert("Input Value Missing !");
			 
		}
	}
} 
 


function report_show(xmlobj, input_value) {
	document.getElementById("pr_status").value = 1;
	document.getElementById("en_rowcnt").value = 0;
	var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
	var tbody = document.getElementById("entred_body");
	var table = document.getElementById("entred_data");

	var t = 0;

	for (t = tbody.rows.length - 1; t >= 0; t--) 
	{
		tbody.deleteRow(0);
	}

	var len = 0;
	try {
		len = bR.getElementsByTagName("prow")[0].firstChild.nodeValue;
		} catch (e) {
		len = 0;
	}

	document.getElementById("en_rowcnt").value = len;

	for (i = 0; i < len; i++) 
	{
		var new_row = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "", "","");//13
		var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
		var BENEFICIARY_SNO= bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
		var BENEFICIARY_OB_SNO = bR.getElementsByTagName("BENEFICIARY_OB_SNO")[i].firstChild.nodeValue;
		var month_sno= bR.getElementsByTagName("month_sno")[i].firstChild.nodeValue;		
		CUR_SNO = BENEFICIARY_OB_SNO;
		var fschar = bR.getElementsByTagName("fschar")[i].firstChild.nodeValue; 
		var href_cell = cell("TD","A","EDIT","EDIT","EDIT",2,"","javascript:select(" + BENEFICIARY_OB_SNO + "," + (i + 1) + ","+month_sno+")","", "5%", "", "", "");
		var ress3=0;
		try
		{
		var ress3 = bR.getElementsByTagName("ress3")[i].firstChild.nodeValue;
		}catch(e) {
			 
		}
		// Dcb Area 

		var SCH_NAME =  bR.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
		var ob = bR.getElementsByTagName("ob")[i].firstChild.nodeValue;
		var ifany =0;
		try
		{
			ifany= bR.getElementsByTagName("ifany")[i].firstChild.nodeValue;
		}
		catch(e) {
			
		}
		var collection = bR.getElementsByTagName("collection")[i].firstChild.nodeValue;
		var yesteryear = bR.getElementsByTagName("yesteryear")[i].firstChild.nodeValue;
		var currentyear = bR.getElementsByTagName("currentyear")[i].firstChild.nodeValue;
		var demandupto = bR.getElementsByTagName("demandupto")[i].firstChild.nodeValue;
		var BENEFICIARY_TYPE = bR.getElementsByTagName("BENEFICIARY_TYPE")[i].firstChild.nodeValue;

		// Interest Area 
		var main_int_up_to_prv_fyear = bR.getElementsByTagName("main_int_up_to_prv_fyear")[i].firstChild.nodeValue;
		var main_int_collected = bR.getElementsByTagName("main_int_collected")[i].firstChild.nodeValue;
		var water_int_up_to_prv_fyear = bR.getElementsByTagName("water_int_up_to_prv_fyear")[i].firstChild.nodeValue;
		var water_int_levied = bR.getElementsByTagName("water_int_levied")[i].firstChild.nodeValue;
		var water_int_collected_prv_month = bR.getElementsByTagName("water_int_collected_prv_month")[i].firstChild.nodeValue;
		var ress = ress3;
		var name_cell = cell("TD", "label", "", fschar, BENEFICIARY_NAME, 7,"tdText", "javascript:void()", "", "12%", "left", "", "");
		var ress_cell = cell("TD", "label", "", "", ress, 7, "tdText","", "", "", "", "", "");

		// Dcb Area cell 
		var ob_cell = cell("TD", "label", "", "", ob, 7, "tdText", "", "", "","right", "", "");
		var ifany_cell = cell("TD", "label", "", "", ifany, 7, "tdText","", "", "", "right", "", "");
		var collection_cell = cell("TD", "label", "", "", collection, 7,"tdText", "", "", "", "right", "", "");
		var yesteryear_cell = cell("TD", "label", "", "", yesteryear, 7,"tdText", "", "", "", "right", "", "");
		var currentyear_cell = cell("TD", "label", "", "", currentyear, 7,"tdText", "", "", "", "right", "", "");
		var demandupto_cell = cell("TD", "label", "", "", demandupto + "  ", 7,"tdText", "", "", "", "right", "", "");

		// Interest Area cell 
		var main_int_up_to_prv_fyear_cell = cell("TD", "label", "", "",main_int_up_to_prv_fyear, 7, "tdText", "", "", "", "right", "","");
		var main_int_collected_cell = cell("TD", "label", "", "",main_int_collected, 7, "tdText", "", "", "", "right", "", "");
		var water_int_up_to_prv_fyear_cell = cell("TD", "label", "", "",water_int_up_to_prv_fyear, 7, "tdText", "", "", "", "right","", "");
		var water_int_levied_cell = cell("TD", "label", "", "",water_int_levied, 7, "tdText", "", "", "", "right", "", "");
		var water_int_collected_prv_month_cell = cell("TD", "label", "", "",water_int_collected_prv_month, 7, "tdText", "", "", "","right", "", "");
		var SCH_NAME_cell = cell("TD", "label", "", "", SCH_NAME + "  ", 7,"tdText", "", "", "25%", "left", "", "");
		var BENEFICIARY_OB_SNO_cell = cell("TD", "input", "hidden", "",BENEFICIARY_OB_SNO + "  ", 7, "tdText", "", "", "", "left", "","");
		var BENEFICIARY_TYPE = cell("TD", "label", "", "", BENEFICIARY_TYPE+ "  ", 7, "tdText", "", "", "35%", "left", "", "");
		
		var BENEFICIARY_SNO_cell = cell("TD", "input", "hidden", "BENEFICIARY_SNO" +(i+1),BENEFICIARY_SNO, 7, "tdText", "", "", "", "left", "","");
		  
		if (_flag == 1) 
		{
			var href_cell = cell("TD", "A", "EDIT", "EDIT", "Edit", 2, "","javascript:select(" + BENEFICIARY_OB_SNO + "," + (i + 1)+ ","+month_sno+")", "", "4%", "", "", "");
			var href_Int_cell = cell("TD", "A", "EDIT", "EDIT", "Click", 2, "","javascript:int_select(" + BENEFICIARY_OB_SNO + ")", "","15%", "right", "", "");
			var dcbhide = "intremoveSelect(" + (i + 1) + ")";
			var inthide = "dcbremoveSelect(" + (i + 1) + ")";
			var DCB_cell = cell("TD", "input", "radio", "chdcb" + (i + 1), 0,7, "", "", "", "2%", "", "onclick", inthide);
			var DCB = cell("TD", "label", "", "", "DCB", 7, "tdText", "", "","2%", "left", "", "");
			var Int_cell = cell("TD", "input", "radio", "chint" + (i + 1), 0,7, "", "", "", "2%", "", "onclick", dcbhide);
			var Int = cell("TD", "label", "", "", "Int", 7, "tdText", "", "","2%", "left", "", "");
			
			new_row.appendChild(DCB_cell);
			new_row.appendChild(DCB);
			new_row.appendChild(Int_cell);
			new_row.appendChild(Int);
			new_row.appendChild(href_cell);
			new_row.appendChild(name_cell);
			new_row.appendChild(SCH_NAME_cell);
			new_row.appendChild(href_Int_cell);
			new_row.appendChild(BENEFICIARY_OB_SNO_cell);

		} else {

			//  Dcb Area cell to table 

			new_row.appendChild(name_cell);
			//	  new_row.appendChild(ress_cell);
			new_row.appendChild(ob_cell);
			new_row.appendChild(ifany_cell);
			new_row.appendChild(collection_cell);
			new_row.appendChild(main_int_up_to_prv_fyear_cell);
			new_row.appendChild(main_int_collected_cell);
			new_row.appendChild(yesteryear_cell);
			new_row.appendChild(currentyear_cell);
			new_row.appendChild(demandupto_cell);
			new_row.appendChild(water_int_up_to_prv_fyear_cell);
			new_row.appendChild(water_int_levied_cell);
			new_row.appendChild(water_int_collected_prv_month_cell);

			//  Int Area cell to table
  
		}
		new_row.appendChild(BENEFICIARY_SNO_cell);  
		tbody.appendChild(new_row);
	}
}

function show(xmlobj, input_value) {

	var bR = xmlobj.responseXML.getElementsByTagName("result")[0];

	if (bR.firstChild.nodeValue == "404") {
		alert("Data Base Server Not Connectecd \n ------------------------------------")
	}  

	if (process_code == 4) {
		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		var astatus = bR.getElementsByTagName("astatus")[0].firstChild.nodeValue;
		if (status== 1)  
		{
			document.getElementById("pr_status").value = 1;
			alert("Record Successfully Updated");
			//grid_show(7, 'report', '', 'bentype', 'subdiv')
			document.getElementById("OB_MAINT_CHARGES").value = "";
			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value = "";
			document.getElementById("DMD_UPTO_PRV_MTH_WC").value = "";
			document.getElementById("OB_YESTER_YR_WC").value = "";
			document.getElementById("OB_CUR_YR_WC").value = "";
			//document.getElementById("DMD_UPTO_PRV_MTH_WC").value="";
			document.getElementById("COLN_UPTO_PRV_MTH_WC").value = "";
			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value = "";

			document.getElementById("OB_FOR_MTH_YESTER_YR_WC").value = "";
			document.getElementById("OB_FOR_MTH_MAINT_CHARGES").value = "";
			document.getElementById("OB_FOR_MTH_CUR_YR_WC").value = "";

			//document.getElementById("DMD_UPTO_PRV_MTH_WC").value="";

			//  document.getElementById("ch"+i).checked=false;
		} 
		else if (astatus>=1 && status==0) {
			alert("Record Already Stored ");
			document.getElementById("pr_status").value = 1;
		} 
	} else if (process_code == 6 || process_code == 11) {
		document.getElementById("pr_status").value = 1;
		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		if (status >= 1) {
			alert("Record Successfully Updated")
			document.getElementById("OB_INT_CUR_YR_MAINT").value = "";
			document.getElementById("OB_INT_PRV_YR_MAINT").value = "";
			document.getElementById("COLN_INT_UPTO_PRV_MTH_MAINT").value = "";
			document.getElementById("OB_INT_AMT_WC").value = "";
			document.getElementById("DMD_INT_UPTO_PRV_MTH_WC").value = "";
			document.getElementById("COLN_INT_UPTO_PRV_MTH_WC").value = "";

			document.getElementById('selprocess').value = 1;
			dcbshow(1);

		} else {
			alert("Record Not Updated");
		}

	} else if (process_code == 7) {
		///  Pumping Return
		document.getElementById("pr_status").value = 1;
		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;

		var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
		var tbody = document.getElementById("entred_body");
		var table = document.getElementById("entred_data");
		var t = 0;
//		if (sub_flag != 1)
	

		if (sub_flag == 0)
		{
			var ins_row = bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;
			
			if (parseInt(ins_row) > 0)
				alert("Successfully Saved...")
				else
				alert("Data Not Successfully Saved...")			  		
		
		}
	} 

	else if (process_code == 10) {

		var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
		document.getElementById("pr_status").value = 1;

		var up_row = bR.getElementsByTagName("up_row")[0].firstChild.nodeValue;

		if (up_row >= 1) {
			alert("Record Successfully Updated...")
			document.getElementById("OB_MAINT_CHARGES").value = "";
			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value = "";
			document.getElementById("DMD_UPTO_PRV_MTH_WC").value = "";
			document.getElementById("OB_YESTER_YR_WC").value = "";
			document.getElementById("OB_CUR_YR_WC").value = "";
			//document.getElementById("DMD_UPTO_PRV_MTH_WC").value="";
			document.getElementById("COLN_UPTO_PRV_MTH_WC").value = "";
			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value = "";

		}
	} else {
		document.getElementById("pr_status").value = 1;
		
		if (process_code == 1 || process_code == 2 || process_code == 3) {

			document.getElementById(input_value).options.length = 0;

			var len = bR.getElementsByTagName("sno").length;
			var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
			//if (len==0) 			alert(status+"\n-------------------------------")
			
			for (i = 0; i < len; i++) {
				var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
				var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
				addOption(document.getElementById(input_value), name, sno)
			}

		}

	}
  
}

function show_2(xmlobj, input_value, disf1, disf2) {
	var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
	if (process_code == 3)
	{
		document.getElementById("pr_status").value = 1;
		var len = bR.getElementsByTagName("sno").length;
		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		var table = document.getElementById(disf1);
		var tbody = document.getElementById(disf2);
		var t = 0;
		try 
		{
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
				}
		} catch (e) 
		{
		}
		
		if (len == 0) {
			alert(status)
			// Meter Location Clear
			try {
				var ben_meter_tbody = document.getElementById("ben_meter_body");
				for (t = ben_meter_tbody.rows.length - 1; t >= 0; t--) {
					ben_meter_tbody.deleteRow(0);
				}
			} catch (e) {
			}
		}
		document.getElementById("ben_value").options.length = 0;

		for ( var i = 0; i < len; i++)
		{
			var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			var new_row = cell("TR", "", "", "row" +(i+1),(i+1),2,"","", "", "", "", "", "");
			addOption(document.getElementById("ben_value"), name, sno)
			var sno_cell = cell("TD", "input", "hidden", "select" + (i + 1),sno, 2, "", "", "", "2%", "", "", "");
			var check_cell = cell("TD", "input", "radio", "ch" + (i + 1), 0, 7,"", "", "", "10%", "", "onclick", "ckset(" + (i + 1) + ")");
			var name_cell = cell("TD", "label", "", "disvalue"+(i+1),name,2,"label", "", "", "90%", "", "", "");
			new_row.appendChild(sno_cell);
			new_row.appendChild(check_cell);
			new_row.appendChild(name_cell);
			// tbody.appendChild(new_row);
		}
		document.getElementById("rowcnt").value = len;
	}
	if (process_code == 4 || process_code == 5) 
	{
		document.getElementById("pr_status").value = 1;
		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
		var len = bR.getElementsByTagName("sno").length;
		var table = document.getElementById(disf1);//ben_meter_data
		var tbody = document.getElementById(disf2);//ben_meter_body
		var t = 0;
		if (len == 0) 
		{
			alert(status); 
		}
		var ben_value=document.getElementById("ben_value").value;
		document.getElementById("rowcnt_meter").value = len;
		for (t = tbody.rows.length - 1; t >= 0; t--) 
		{
			tbody.deleteRow(0);
		}
		for (i = 0; i < len; i++)
		{
			var new_row = cell("TR", "", "", "mrow" + (i + 1), (i + 1), 2, "","", "", "", "", "");
			var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			var METRE_LOCATION = bR.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
			METRE_LOCATION=METRE_LOCATION.replace ("*","&");  
			var PARENT_METRE = bR.getElementsByTagName("PARENT_METRE")[i].firstChild.nodeValue;
			var METRE_FIXED = "";
			var METRE_WORKING = "";
			try {
							
				METRE_FIXED = bR.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;
			} catch (e) {
				METRE_FIXED = "No";
			}
			var METRE_INIT_READING = bR.getElementsByTagName("METRE_INIT_READING")[i].firstChild.nodeValue;
			try {
					METRE_WORKING = bR.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
				} catch (e) 
				{
					METRE_FIXED = "No";
				}
			var ALLOTED_QTY = bR.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
			var SCHEME_NAME = bR.getElementsByTagName("SCHEME_NAME")[i].firstChild.nodeValue;
			var METRE_TYPE = bR.getElementsByTagName("METRE_TYPE")[i].firstChild.nodeValue;
			var mtypestr="";
			if (parseInt(METRE_TYPE)==1 )
					mtypestr="KL";
			else   
				mtypestr="ML";
	
			var mtypevalue_cell = cell("TD", "label", "", "mtypestr"+ (i + 1), mtypestr, 2, "Text", "", "", "5%","center", "", "");				
			var location_cell = cell("TD", "label", "", "METRE_LOCATION"+ (i + 1), METRE_LOCATION, 2, "Text", "", "", "30%","left", "onClick", "show_prv(" + (i + 1) + ")");
			var meter_available_cell = cell("TD", "label", "", "METRE_FIXED"+ (i + 1), METRE_FIXED, 2, "Text	", "", "", "5%","center", "", "");
			var scheme_cell = cell("TD", "label", "", "SCHEME_NAME"+ (i + 1), SCHEME_NAME, 2, "", "", "", "20%", "left","", "");
			var ingreading_cell = "";  
			if (METRE_FIXED == "No" || (METRE_FIXED == "Yes" && METRE_WORKING == "No")) {
			ingreading_cell = cell("TD", "input", "text","METRE_INIT_READING" + (i + 1), METRE_INIT_READING, 5,"tb4", "", "text-align:right;#readonly", "10%","right", "onKeyup", "isInteger(this,9,event),digit_control('METRE_INIT_READING"+(i+1)+"',3)");
			} else {
			ingreading_cell = cell("TD", "input", "text","METRE_INIT_READING" + (i + 1), METRE_INIT_READING, 5,"tb4", "", "text-align:right;#readonly", "7%","right", "onKeyup", "isInteger(this,9,event),digit_control('METRE_INIT_READING"+(i+1)+"',3)");
			}
			var readingentry_cell = "";
			var dis_flag="";
			if (METRE_FIXED == "No"  || (METRE_FIXED == "Yes" && METRE_WORKING == "No")) 
			{
				readingentry_cell = cell("TD", "input", "text", "read"+ (i + 1), 0, 7, "tb4", "","text-align: right;#readonly", "5%", "","onblur#onKeyup", "calcuate("+(i+1)+")#isInteger(this,9,event),digit_control('read"+(i+1)+"',3)");
			} else 
			{
				readingentry_cell = cell("TD", "input", "text", "read"+ (i + 1), 0, 7, "tb4", "", "text-align: right;  ","7%", "", "onblur#onKeyup", "calcuate("+(i+1)+")#isInteger(this,9,event),digit_control('read"+(i+1)+"',3)");
			}
			//       var openingreading_cell=cell("TD","label","","METRE_CLOSE_READING"+(i+1),METRE_CLOSE_READING,5,"","","","10%","right","","");
			var meterworking_hidden_cell = cell("TD", "input", "hidden","PRVMETRE_WORKING" + (i + 1), METRE_WORKING, 2, "", "", "","10%", "center", "", "");
			/*
			 if (METRE_FIXED is no then )
			 meter working also to be disabled
			*/  
			var reading_cell_flag="";
			if (METRE_FIXED == "No")  { dis_flag="#disabled";reading_cell_flag="#readonly"; } else {  dis_flag="#no_disabled";reading_cell_flag=""; } 
			if (METRE_WORKING == "No")  {  reading_cell_flag="#readonly"; } else {   reading_cell_flag=""; }
		
		    var meterworking_cell = cell("TD", "select", "", "METRE_WORKING"+(i+1), METRE_WORKING, 2, "select", "", dis_flag, "5%","center' ", "onchange", "statuschange(" + (i + 1) + ")");
		    var allocatedqty_cell = cell("TD", "input", "hidden", "ALLOTED_QTY"+(i+1), ALLOTED_QTY, 2, "", "", "", "10%", "right", "","");
			// var reading_cell=cell("TD","input","text","read"+(i+1),0,7,"","","text-align: right","10%","","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event)");
		    var reading_cell=cell("TD","input","text","read"+(i+1),0,7,"tb4","","text-align: right"+reading_cell_flag,"10%","","onblur#onKeyup","calcuate("+(i+1)+")#isInteger(this,9,event),digit_control('read"+(i+1)+"',3)");
			var nounit_cell ="";
			var r_net_="";
		 
			if (METRE_FIXED == "Yes" && METRE_WORKING == "Yes")
				r_net_="#readonly";
			else
				r_net_="";
				
				nounit_cell=cell("TD", "input", "text", "nounit" + (i + 1),0, 7, "tb4", "", "text-align: right"+r_net_, "5%", "center","onKeyup#onblur", "isInteger(this,9,event),digit_control('nounit"+(i+1)+"',3)#calcuate("+ (i + 1) + ")");
				var amt_cell = cell("TD", "label", "", "amt_cell"+ (i + 1), "", 2, "Text", "", "text-color:red", "5%","right", "", "");				
				var button_cell = cell("TD", "input", "button", "submit" + (i + 1),"Save", 7, "fb3", "", "text-align: center", "10%", "right","onclick", "single_row_update(9,'" + (i + 1) + "')");
				var difference_cell = cell("TD", "input", "hidden", "difference"+ (i + 1), 0, 2, "", "", "", "10%", "center", "", "");  
				var sno_cell = cell("TD", "input", "hidden", "selsno" + (i + 1),sno, 2, "", "", "", "2%", "", "", "");
				var METRE_TYPE_cell = cell("TD", "input", "hidden", "METRE_TYPE"+ (i + 1), METRE_TYPE, 2, "", "", "", "2%", "", "", "");
				var PARENT_METER_cell = "";
				if (PARENT_METRE == 0)	PARENT_METRE = "n";
				
				if (PARENT_METRE == "n")   
					PARENT_METER_cell = cell("TD", "input", "text", "PARENT_METER"+ (i + 1), 0, 2, "tb2", "","text-align: right;visibility: hidden;", "2%", "", "","");
				else
					PARENT_METER_cell = cell("TD", "input", "text", "PARENT_METER"+ (i + 1), 0, 2, "tb2", "", "text-align: right;", "2%","", "onblur#onKeyup", "calcuate(" + (i + 1) + ")#isInteger(this,9,event),digit_control('PARENT_METER"+(i+1)+"',3)");

				var val_status_cell = cell("TD", "input", "hidden", "val_status"+ (i + 1), 0, 2, "", "", "", "2%", "", "", "");
				var PARENT_H_METRE_cell = cell("TD", "input", "hidden","PARENT_H_METRE" + (i + 1), PARENT_METRE, 2, "", "", "","2%", "", "", "");
				
				var href_cell = cell("TD","A","redemption","redemption"+ (i + 1),"Quantity",2,"","javascript:wc_cal_prv_window("+ben_value+","+sno+","+(i + 1)+")","", "5%", "", "", "");
				var Redemptionamt = cell("TD", "input", "hidden","rectamt" + (i + 1), 0, 2, "", "", "","2%", "", "", "");
				var Redemptionqty = cell("TD", "input", "hidden","rectqty" + (i + 1), 0, 2, "", "", "","2%", "", "", "");
				var Redemptionyear = cell("TD", "input", "hidden","rectyear" + (i + 1), 0, 2, "", "", "","2%", "", "", "");
				var Redemptionmonth = cell("TD", "input", "hidden","rectmonth" + (i + 1), 0, 2, "", "", "","2%", "", "", "");
				new_row.appendChild(location_cell);
				new_row.appendChild(scheme_cell);
				new_row.appendChild(href_cell);
				new_row.appendChild(meter_available_cell);
				new_row.appendChild(mtypevalue_cell);
				new_row.appendChild(meterworking_hidden_cell);
				new_row.appendChild(meterworking_cell);
				new_row.appendChild(ingreading_cell);
				new_row.appendChild(difference_cell);
				new_row.appendChild(allocatedqty_cell);
				new_row.appendChild(reading_cell);
				new_row.appendChild(PARENT_METER_cell);  
				new_row.appendChild(nounit_cell);
				new_row.appendChild(Redemptionqty);
				new_row.appendChild(Redemptionamt);
				new_row.appendChild(Redemptionyear);
				new_row.appendChild(Redemptionmonth);  
				new_row.appendChild(amt_cell);
				new_row.appendChild(button_cell);
				new_row.appendChild(sno_cell);
				new_row.appendChild(val_status_cell);
				new_row.appendChild(PARENT_H_METRE_cell);
				new_row.appendChild(METRE_TYPE_cell);			
				tbody.appendChild(new_row);
				
		}
	}
}

function scheme_Select(command, process) {
	
	
						var extraCommand = obPage ? "&ob_show=true" : "";

	
	url = "../../../../../Beneficiary_DCB_ob?command=scheme_selection&obShow=true&BENEFICIARY_SNO="+document.getElementById("ben_value").value;
	var xmlobj_new = null;
	xmlobj_new = createObject();
	xmlobj_new.open('GET', url, true);
	xmlobj_new.onreadystatechange = function() {
		scheme_selection(xmlobj_new);
	}
	xmlobj_new.send(null);
}

function validate_button(flag)
{
	 var month=document.getElementById("month").value;
 	//	if (month==4)
	if (flag=='undefined') 
		flag='Y';
//	if (flag=='Y' && parseInt(month)!=4) // if month april allowed to new entry 29 /05 /2015  
//	{
//		try
//		{
//			document.getElementById("DMD_UPTO_PRV_MTH_WC").disabled = true;  
//			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").disabled = true;
//			document.getElementById("COLN_UPTO_PRV_MTH_WC").disabled = true;
//			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").disabled = true;
//			document.getElementById("DMD_UPTO_PRV_MTH_WC").value =0;
//			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value =0;
//			document.getElementById("COLN_UPTO_PRV_MTH_WC").value =0;
//			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value =0;
//			// April Column enable if month is other than april
//			// 18 12 2012 
//			document.getElementById("OB_YESTER_YR_WC").disabled = true;
//			document.getElementById("OB_CUR_YR_WC").disabled = true;
//			document.getElementById("OB_MAINT_CHARGES").disabled = true;
//			// April Column enable if month is other than april
//		}catch(e) {}
//		try
//		{
//			document.getElementById("OB_INT_AMT_WC").disabled = true;  
//			document.getElementById("OB_INT_PRV_YR_MAINT").disabled = true;
//			document.getElementById("OB_INT_CUR_YR_MAINT").disabled = true;
//		}catch(e) {}
//	}
	
	if (flag=='Y' && parseInt(month)!=4) // if month april allowed to new entry 29 /05 /2015  
	{
		try
		{
			document.getElementById("DMD_UPTO_PRV_MTH_WC").disabled = false;  
			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").disabled = false;
			document.getElementById("COLN_UPTO_PRV_MTH_WC").disabled = false;
			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").disabled = false;
			document.getElementById("DMD_UPTO_PRV_MTH_WC").value =0;
			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").value =0;
			document.getElementById("COLN_UPTO_PRV_MTH_WC").value =0;
			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").value =0;
			// April Column enable if month is other than april
			// 18 12 2012 
			document.getElementById("OB_YESTER_YR_WC").disabled = false;
			document.getElementById("OB_CUR_YR_WC").disabled = false;
			document.getElementById("OB_MAINT_CHARGES").disabled = false;
			// April Column enable if month is other than april
		}catch(e) {}
		try
		{
			document.getElementById("OB_INT_AMT_WC").disabled = false;  
			document.getElementById("OB_INT_PRV_YR_MAINT").disabled = false;
			document.getElementById("OB_INT_CUR_YR_MAINT").disabled = false;
		}catch(e) {}
	}
	
	else
	{
		//   April Column Disable if month is other than april
		// 18 12 2012 
		try
		{	//prv code
			//document.getElementById("OB_YESTER_YR_WC").disabled = true;
			//new code 
			document.getElementById("OB_YESTER_YR_WC").disabled = false; 
		}catch (e) {
		}
		try 
		{ 
			//prv code
			//document.getElementById("OB_CUR_YR_WC").disabled = true;
			//document.getElementById("OB_MAINT_CHARGES").disabled = true;
			//new code
			document.getElementById("OB_CUR_YR_WC").disabled = false;
			document.getElementById("OB_MAINT_CHARGES").disabled = false;
			
			
			// April Column Disable if month is other than april
			document.getElementById("DMD_UPTO_PRV_MTH_WC").disabled = false;
			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").disabled = false;
			document.getElementById("COLN_UPTO_PRV_MTH_WC").disabled = false;
			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").disabled = false;
		}catch (e) {}
		try  
		{  
			document.getElementById("OB_INT_AMT_WC").disabled = false;  
			document.getElementById("OB_INT_PRV_YR_MAINT").disabled = false;  
			document.getElementById("OB_INT_CUR_YR_MAINT").disabled = false;
		}catch(e) {}
	}
	
//	else
//	{
//		//   April Column Disable if month is other than april
//		// 18 12 2012 
//		try
//		{	//prv code
//			//document.getElementById("OB_YESTER_YR_WC").disabled = true;
//			//new code 
//			document.getElementById("OB_YESTER_YR_WC").disabled = false; 
//		}catch (e) {
//		}
//		try 
//		{ 
//			//prv code
//			//document.getElementById("OB_CUR_YR_WC").disabled = true;
//			//document.getElementById("OB_MAINT_CHARGES").disabled = true;
//			//new code
//			document.getElementById("OB_CUR_YR_WC").disabled = false;
//			document.getElementById("OB_MAINT_CHARGES").disabled = false;
//			
//			
//			// April Column Disable if month is other than april
//			document.getElementById("DMD_UPTO_PRV_MTH_WC").disabled = false;
//			document.getElementById("COLN_UPTO_PRV_MTH_YESTER_YR").disabled = false;
//			document.getElementById("COLN_UPTO_PRV_MTH_WC").disabled = false;
//			document.getElementById("COLN_UPTO_PRV_MTH_MAINT").disabled = false;
//		}catch (e) {}
//		try  
//		{  
//			document.getElementById("OB_INT_AMT_WC").disabled = true;  
//			document.getElementById("OB_INT_PRV_YR_MAINT").disabled = true;  
//			document.getElementById("OB_INT_CUR_YR_MAINT").disabled = true;
//		}catch(e) {}
//	}
}

function single_vlaue(command, process) 
{	
	 validate_button(document.getElementById("ob_free_status").value);	
	 //validate_button();	
	var xmlobj_new = null;
	xmlobj_new = createObject();
	process_code = process;
	url = "";
	url = "../../../../../Beneficiary_DCB_ob";
	url += "?year=" + document.getElementById("year").value;
	url += "&month=" + document.getElementById("month").value;
	url += "&BENEFICIARY_SNO="+document.getElementById("ben_value").value;
	url += "&command=" + command;
	url += "&Scheme=" + document.getElementById("Scheme").value;
	url += "&process_code=" + process_code;
	xmlobj_new.open("GET", url, true);  
	xmlobj_new.onreadystatechange = function() 
	{
		ob_sno_set(xmlobj_new);
	}
	xmlobj_new.send();
}
function single_row_update(process_code, input_value) {

	//   Pumping Return ----------------- Store  
	var mw = document.getElementById("METRE_WORKING" + input_value).value;
	var flag = 0;
	if (mw == "Y") 
	{
		var ob = document.getElementById("METRE_INIT_READING" + input_value).value;
		var cb = document.getElementById("read" + input_value).value;
		if (ob == "" || ob < 0 || cb == "" || cb == 0) 
		{
			alert("Opening and  Closing Reading Missing  (Row no"+ input_value+")")
			flag = 1;
		}
	} else
	{
		var nounit = document.getElementById("nounit" + input_value).value;
		if (nounit == ""  ) 
		{
			alert("No of units missing!(row no " + input_value + ")")
			flag = 1;
		} else 
		{
			flag = 0;
		}
	}

	if (flag != 1) 
	{
		process_code = process_code;
		var ben_type = document.getElementById("bentype").value;
		var selsno = document.getElementById("selsno" + input_value).value;
		var read = document.getElementById("read" + input_value).value;
		url = "../../../../../Beneficiary_DCB_ob";
		url += "?command=add";
		url += "&process_code=" + process_code;
		url += "&year=" + document.getElementById("year").value;//YEAR
		url += "&month=" + document.getElementById("month").value;//MONTH
		url += "&BENEFICIARY_SNO="+document.getElementById("BENEFICIARY_SNO").value;//BENEFICIARY_SNO
		url += "&OFFICE_ID=" + document.getElementById("OFFICE_ID").value;//OFFICE_ID
		url += "&SUBDIV_OFFICE_ID=" + document.getElementById("subdiv").value;//OFFICE_ID
		url += "&rowcnt_meter=1";//OFFICE_ID
		url += "&selected_item=" + input_value;
		url += "&BENEFICIARY_TYPE_SNO="+document.getElementById("bentype").value;		
		url += ("&METRE_SNO" + input_value) + "=" + selsno;
		url += "&METRE_INITIAL_READING"+input_value+"="+document.getElementById("METRE_INIT_READING" + input_value).value;
		url += ("&METRE_CLOSING_READING" + input_value) + "=" + read;
		url += ("&PRVMETRE_WORKING" + input_value)+"="+document.getElementById("PRVMETRE_WORKING" + input_value).value;
		url += ("&CHILD_METER_QTY" + input_value) + "="+document.getElementById("PARENT_METER" + input_value).value;
		url += ("&QTY_CONSUMED" + input_value) + "="+document.getElementById("nounit" + input_value).value;
		url += ("&difference" + input_value) + "="+ document.getElementById("difference" + input_value).value;//netunit
		url += ("&QTY_RECTIFY" + input_value) + "="+document.getElementById("rectqty"+input_value).value;
		url += ("&AMT_RECTIFY" + input_value) + "="+document.getElementById("rectamt"+input_value).value;
		url += ("&MONTH_RECTIFY" + input_value) + "="+document.getElementById("rectmonth"+input_value).value;
		url += ("&YEAR_RECTIFY" + input_value) + "="+document.getElementById("rectyear"+input_value).value;
		
		
		var aqty = document.getElementById("ALLOTED_QTY" + input_value).value;
		var ben_type = document.getElementById("bentype").value;
		if (ben_type > 6) 
		{  
			var eqty = (parseInt(read) - parseInt(aqty));
			
			if (eqty > 0)
				eqty = eqty;
			else
				eqty = 0;
		} else
		{
			eqty = 0;
		}
		var meter = "";
		if (document.getElementById("METRE_FIXED" + input_value).innerHTML == "Yes")
			meter = "Y";
		else
			meter = "N";
		var meterw = "";
		
		url += "&METRE_WORKING" + input_value + "="+document.getElementById("METRE_WORKING" + input_value).value;
		url += "&METRE_FIXED" + input_value + "=" + meter;
		url += ("&ALLOTED_QTY" + input_value) + "=" + aqty;
		url += ("&EXCESS_QTY" + input_value) + "=" + eqty;
		url += "&netunit=" + document.getElementById("netunit").value;//netunit
		var xmlobj = createObject();
		xmlobj.open("GET", url, true);
		xmlobj.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
		xmlobj.send(null);
		xmlobj.onreadystatechange = function() 
		{
			result_process(xmlobj, "add", "9");
		}
	}
}
function result_process(xmlobj, command, input_value) 
{
	 if (xmlobj.readyState == 4) 
	 {
		if (xmlobj.status == 200) 
		{
			if (command == 'pdf') 
			{
			}else if (command == 'show') 
			{
				show(xmlobj, input_value);
			} else if (command == 'data') 
			{
				show_2(xmlobj, input_value, display_table, display_body);
			}else if (command == 'add') 
			{
				document.getElementById("pr_status").value = 0;
				if (process_code == 10) {
					show(xmlobj, process_code);
				} else {
						if (input_value != 9) 
						{
							show(xmlobj, input_value);
						} else 
						{
							single_update_result(xmlobj, input_value);
						}
				}
			}else if (command == 'report') 
			{
				document.getElementById("pr_status").value = 0;  
				report_show(xmlobj, input_value);
			} 
		}   
	} 
}
function single_update_result(xmlobj, input_value) 
{
	document.getElementById("pr_status").value = 1;
	var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
	if (input_value == 9) 
	{
		var ins_row = bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;
	    if (parseInt(ins_row) > 0)
	    {
	    		alert("Saved Successfully")
	    		var rowcnt_meter = document.getElementById("rowcnt_meter").value;
	    		var bR = xmlobj.responseXML.getElementsByTagName("result")[0];
	    		var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
	    		var METRE_SNO = bR.getElementsByTagName("METRE_SNO")[0].firstChild.nodeValue;
	    		var selected_item = bR.getElementsByTagName("selected_item")[0].firstChild.nodeValue;
	    		document.getElementById("mrow" + selected_item).style.backgroundColor = "#FFFFCC"
	    }else
	    {
	    	alert("Data Not Successfully Saved")
	    }
	}
}  

function statuschange(row) 
{
	var METRE_WORKING = document.getElementById("METRE_WORKING" + row).value;
	if (METRE_WORKING == 'N') 
	{
		document.getElementById("nounit" + row).value = "";
		document.getElementById("nounit" + row).readOnly = false;
		document.getElementById("read" + row).value = "";
		document.getElementById("read" + row).disabled = true;
	}
	else 
	{
		document.getElementById("read" + row).disabled = false;
		document.getElementById("read" + row).readOnly = false;
		document.getElementById("read" + row).value = "";
		document.getElementById("nounit" + row).value = "";
		document.getElementById("nounit" + row).readOnly = true;
		document.getElementById("METRE_INIT_READING" + row).readOnly = false;
	}
}
function month_check()
{
	
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("month").value;
	
	if (smonth==4)
	{
		try
		{
		document.getElementById("process_year").innerHTML="";
		document.getElementById("process_year1").innerHTML="";
		document.getElementById("pmnt").innerHTML="";
		document.getElementById("pmnt1").innerHTML="";  
		}catch(e){
			 
		}
		try
		{
		document.getElementById("process_year3").innerHTML="";
		document.getElementById("process_year4").innerHTML="";
		}catch(e){
			 
		}  
	}
	else
	{
		if (smonth<=3)
			process_year=parseInt(mon)-1;
		else
			process_year=parseInt(mon);
  
		try
		{
		document.getElementById("process_year").innerHTML="Apr-"+process_year+"-";
		document.getElementById("process_year1").innerHTML="Apr-"+process_year+"-";
		}catch(e){
			 
		}
		try
		{
		document.getElementById("process_year3").innerHTML="Apr-"+process_year+"-";
		document.getElementById("process_year4").innerHTML="Apr-"+process_year+"-";
		}catch(e){
			 
		}  
	}
}
function select(sno, row,msno) 
{  
	var rows = document.getElementById("en_rowcnt").value;
	for (i = 1; i <= rows; i++)  
	{
		if ((document.getElementById("chdcb" + i).checked)	|| (document.getElementById("chint" + i).checked)) 
		{
 			document.getElementById("selsno").value = sno;
			document.getElementById("obsno").value = sno;
			document.getElementById("mobsno").value = msno;
			document.getElementById("mobsno_int").value = sno;
			document.getElementById("SELBENEFICIARY_SNO").value=document.getElementById("BENEFICIARY_SNO"+i).value;
			if ((document.getElementById("chdcb" + i).checked)) 
			{
				dcbshow(1);
				valueSet(3, 'report', 3);//3->report get ,4 only show dcb
				var month_sel=document.getElementById("month");
				mthch(month_sel);
				month_check();
			}else if ((document.getElementById("chint" + i).checked)) 
			{
				dcbshow(2);
				valueSet(3, 'report', 4);//3->report get ,4 only show int
				var month_sel=document.getElementById("month");
				mthch(month_sel);
				month_check();
			}
		}
	}
	
	document.getElementById("button2").disabled = false;
	document.getElementById("button1").disabled = true;
	try 
	{
						var apr_OB_INT_PRV_YR_MAINT= document.getElementById("apr_OB_INT_PRV_YR_MAINT").value;
						var  apr_OB_INT_CUR_YR_MAINT=document.getElementById("apr_OB_INT_CUR_YR_MAINT").value;
						var  apr_OB_INT_AMT_WC=document.getElementById("apr_OB_INT_AMT_WC").value ;
						document.getElementById("OB_INT_AMT_WC").value=apr_OB_INT_AMT_WC;
						document.getElementById("OB_INT_PRV_YR_MAINT").value=apr_OB_INT_PRV_YR_MAINT;
						document.getElementById("OB_INT_CUR_YR_MAINT").value=apr_OB_INT_CUR_YR_MAINT;
	}catch(e)
	{
		//	alert("int_select->Process->April value get problem"+e);
	}
	 
	
	
	var obj=createObject();
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	var validate_result=isNum(month);
		validate_result+=isNum(year);
	var new_url="../opchange_accept.jsp?";
	 if (parseInt(month)!=4)
	 validate_button(document.getElementById("ob_free_status").value);                
//	validate_button();
}
function removerowSelect(row) 
{
	var rows = document.getElementById("en_rowcnt").value;
	for (i = 1; i <= rows; i++) 
	{
		if (i != row) 
		{
			document.getElementById("chdcb" + i).checked = false;
			document.getElementById("chint" + i).checked = false;
		}
	}
}

function intremoveSelect(i) 
{
	document.getElementById("chdcb" + i).checked = false;
	removerowSelect(i);
}
function dcbremoveSelect(i) 
{
	document.getElementById("chint" + i).checked = false;
	removerowSelect(i);
}
 
function div_sh() 
{
}

function scheme_selection(xmlobj_ob) 
{
	try 
	{
		var bR = xmlobj_ob.responseXML.getElementsByTagName("result")[0];
		if (xmlobj_ob.readyState == 4) 
		{
			if (xmlobj_ob.status == 200) 
			{
				try 
				{
					document.getElementById("Scheme").options.length = 0;
					var len = bR.getElementsByTagName("sno").length;
					for (i = 0; i < parseInt(len); i++) 
					{
						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
						var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
						addOption(document.getElementById("Scheme"), name, sno)
					}
				} catch (e) 
				{
					 
				}
			}
		}
	} catch (e) 
	{
	}
}

function ob_sno_set(xmlobj_ob) 
{
	if (process_code == 5) 
	{
		if (xmlobj_ob.readyState == 4) 
		{
			if (xmlobj_ob.status == 200) 
			{
				try 
				{
					var status = xmlobj_ob.responseXML.getElementsByTagName("status")[0].firstChild.nodeValue;
					var obsno = xmlobj_ob.responseXML.getElementsByTagName("obsno")[0].firstChild.nodeValue;
					if (status != 0) 
					{
						alert("Opening Balance  already exists for the Selected Scheme ! ")
						try
						{
						document.getElementById("dcbbutton").disabled = true;
						}catch(e) {}
						document.getElementById("obsno").value = obsno;
					} else 
					{
						try{
							document.getElementById("dcbbutton").disabled = false;
						}catch(e) {}
						document.getElementById("obsno").value = 0;
					}
					
					var count_apr= xmlobj_ob.responseXML.getElementsByTagName("count_apr")[0].firstChild.nodeValue;
					var apr_OB_MAINT_CHARGES=xmlobj_ob.responseXML.getElementsByTagName("apr_OB_MAINT_CHARGES")[0].firstChild.nodeValue;
					var apr_OB_CUR_YR_WC=xmlobj_ob.responseXML.getElementsByTagName("apr_OB_CUR_YR_WC")[0].firstChild.nodeValue;
					var apr_OB_YESTER_YR_WC=xmlobj_ob.responseXML.getElementsByTagName("apr_OB_YESTER_YR_WC")[0].firstChild.nodeValue;
					var apr_OB_INT_PRV_YR_MAINT=xmlobj_ob.responseXML.getElementsByTagName("apr_OB_INT_PRV_YR_MAINT")[0].firstChild.nodeValue;
					var apr_OB_INT_CUR_YR_MAINT=xmlobj_ob.responseXML.getElementsByTagName("apr_OB_INT_CUR_YR_MAINT")[0].firstChild.nodeValue;
					var apr_OB_INT_AMT_WC=xmlobj_ob.responseXML.getElementsByTagName("apr_OB_INT_AMT_WC")[0].firstChild.nodeValue;
					document.getElementById("apr_OB_MAINT_CHARGES").value = apr_OB_MAINT_CHARGES;
					document.getElementById("apr_OB_CUR_YR_WC").value = apr_OB_CUR_YR_WC;
					document.getElementById("apr_OB_YESTER_YR_WC").value = apr_OB_YESTER_YR_WC;
					document.getElementById("apr_OB_INT_PRV_YR_MAINT").value = apr_OB_INT_PRV_YR_MAINT;
					document.getElementById("apr_OB_INT_CUR_YR_MAINT").value = apr_OB_INT_CUR_YR_MAINT;
					document.getElementById("apr_OB_INT_AMT_WC").value = apr_OB_INT_AMT_WC;
					document.getElementById("OB_YESTER_YR_WC").value = apr_OB_YESTER_YR_WC;
					document.getElementById("OB_MAINT_CHARGES").value = apr_OB_MAINT_CHARGES;
					document.getElementById("OB_CUR_YR_WC").value = apr_OB_CUR_YR_WC;
					
					if (parseInt(count_apr)>0)
					{
						document.getElementById("OB_YESTER_YR_WC").readOnly = true;
						document.getElementById("OB_MAINT_CHARGES").readOnly = true;
						document.getElementById("OB_CUR_YR_WC").readOnly = true;
					}
					else
					{
						document.getElementById("OB_YESTER_YR_WC").readOnly = false;
						document.getElementById("OB_MAINT_CHARGES").readOnly = false;
						document.getElementById("OB_CUR_YR_WC").readOnly = false;
					}
				} catch (e) {
				}
			}
		}
	}
}

function int_select(val) 
{
	document.getElementById("obsno").value = val;
	document.getElementById("selsno").value = val
	dcbshow(2);	
	try
	{
		var apr_OB_INT_PRV_YR_MAINT= document.getElementById("apr_OB_INT_PRV_YR_MAINT").value;
		var  apr_OB_INT_CUR_YR_MAINT=document.getElementById("apr_OB_INT_CUR_YR_MAINT").value;
		var  apr_OB_INT_AMT_WC=document.getElementById("apr_OB_INT_AMT_WC").value ;
		document.getElementById("OB_INT_AMT_WC").value=apr_OB_INT_AMT_WC;
		document.getElementById("OB_INT_PRV_YR_MAINT").value=apr_OB_INT_PRV_YR_MAINT;
		document.getElementById("OB_INT_CUR_YR_MAINT").value=apr_OB_INT_CUR_YR_MAINT;
 	}catch(e)
	{
			alert("int_select->Process->April value get problem"+e);
	}
}

function par_cal(val) {
	var net_qty = document.getElementById("nounit" + val).value;
	var PARENT_METER_qty = document.getElementById("PARENT_METER" + val).value;
	if (net_qty == "")
		net_qty = "0";
	if (net_qty > 0) {
		document.getElementById("nounit" + val).value = parseInt(net_qty)
				- parseInt(PARENT_METER_qty);
	}
}

var monthArr = [ '', 'January', 'February', 'March', 'April', 'May', 'June',
		'July', 'August', 'September', 'October', 'November', 'December' ];

function onMonthChange() 
{
	var month = document.getElementById('month').value;
	var mnt = document.getElementsByName('cmnt');
	
	for ( var i = 0; i < mnt.length; i++) 
	{
		mnt[i].innerHTML = monthArr[month];
	}

	var pmnt = document.getElementsByName('pmnt');
	for ( var i = 0; i < pmnt.length; i++) 
	{
		var premonth = month - 1;
		pmnt[i].innerHTML = monthArr[((premonth % 12) != 0) ? premonth : 12];
		var yr = document.getElementById('year').value;
		if (month == 1) 
		{
			yr--;
		}
		pmnt[i].innerHTML += ", " + yr;
	}
}

function onYearChange() 
{
	var yr = document.getElementsByName('cyr');
	for ( var i = 0; i < yr.length; i++) 
	{
		yr[i].innerHTML = document.getElementById('year').value;
	}
 
	var pyr = document.getElementsByName('pyr');
	for ( var i = 0; i < pyr.length; i++) 
	{
		try
		{
		     pyr[i].innerHTML = document.getElementById('year').value - 1;
		}catch(e){}
	}
}

function cv(val)
{
	if (val =="" ) val=0;
	else
		val=val;
	
	return val;
}