var process_code=0;
var input_row_no=0;
var display_table="";
var display_body="";
var command="";
var flag=0;
var sp_flag=0;
function receipt_deails()
{
	var  url="../../../../../Fas_Receipt_Check";
 	var xmlobj=createObject();
    xmlobj.open("GET",url,true);
    xmlobj.onreadystatechange=function()
    {
    	 receipt_deails_process(xmlobj);
    }
    xmlobj.send(null);	  
}

function receipt_deails_process(xmlobj)
{	 
	 if (xmlobj.readyState==4)  
	 {	   
		 if (xmlobj.status==200)
	    {
			var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
			var tbody = document.getElementById("receipt_check");
			for (t = tbody.rows.length - 1; t >= 0; t--) 
			{
					tbody.deleteRow(0);
			}
			var len = 0;
			try 
			{
			len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			} catch (e) 
			{ 
					len = 0;  
			}		
					
			if (len > 0)    
			{				 
			
			var new_row2 = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");
			var title = cell("TD", "label", "", "", "DCB Receipt Details", 7, "tdText", "", "", "","center", "", "");
			new_row2.appendChild(title);
			tbody.appendChild(new_row2);
			document.getElementById('receipt_check').rows[0].cells[0].colSpan = 9  ; 
			var beneficiary_name_cell_head = cell("TD", "label", "", "", "Beneficiary Name", 7, "tdText", "", "", "","center", "", "");
			var receipt_no_cell_head = cell("TD", "label", "", "", "Receipt No", 7, "tdText","", "", "", "center", "", "");
			var amount_cell_head = cell("TD", "label", "", "", "Amount", 7, "tdText","", "", "", "center", "", "");					
			var sub_ledger_code_cell_head = cell("TD", "label", "", "", "SubLedger Code", 7,"tdText", "", "", "", "center", "", "");
			var sub_ledger_type_code_cell_head = cell("TD", "label", "", "","SubLedger Type Code", 7,"tdText", "", "", "", "center", "", "");
			var CR_DR_INDICATOR_cell_head = cell("TD", "label", "", "", "CR/DR", 7,"tdText", "", "", "", "center", "", "");
			var ACCOUNT_HEAD_CODE_cell_head= cell("TD", "label", "", "", "ACCOUNT HEAD CODE" ,  7,"tdText", "", "", "", "center", "", "");
			var Remarks_cell_head= cell("TD", "label", "", "", "Remarks" ,   7,"tdText", "", "", "", "center", "", "");
			var new_row1 = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");  
  			//	var msg1="Water Charges cannot be freezed due to DCB Receipt errrors ,Journal Cannot be posted. Refer error list below"
			var msg1=" DCB Receipt errrors found......"  
			var new_row3 = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "center","", "");
			var title1 = cell("TD", "label", "", "", msg1, 7, "", "", "color:red", "","center", "", "");
			new_row3.appendChild(title1);
			tbody.appendChild(new_row3);
			document.getElementById('receipt_check').rows[1].cells[0].colSpan = 9  ; 
			new_row1.appendChild(beneficiary_name_cell_head);
			new_row1.appendChild(receipt_no_cell_head);
			new_row1.appendChild(amount_cell_head);
			new_row1.appendChild(CR_DR_INDICATOR_cell_head);
			new_row1.appendChild(sub_ledger_type_code_cell_head);
			new_row1.appendChild(sub_ledger_code_cell_head);
			new_row1.appendChild(ACCOUNT_HEAD_CODE_cell_head);
			new_row1.appendChild(Remarks_cell_head);
			tbody.appendChild(new_row1);
		}
			var row=0;
		for (i = 0; i < len; i++) 
		{
			var new_row = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");  
			var beneficiary_name= xmlValue(bR, "BENEFICIARY_NAME", i);
			var receipt_no=xmlValue(bR, "RECEIPT_NO", i);
			var amount=xmlValue(bR, "AMOUNT", i);  
			var sub_ledger_code=xmlValue(bR, "SUB_LEDGER_CODE", i);
			var sub_ledger_type_code=xmlValue(bR, "SUB_LEDGER_TYPE_CODE", i);
			var ACCOUNT_HEAD_CODE=xmlValue(bR, "ACCOUNT_HEAD_CODE", i);
			var CR_DR_INDICATOR=xmlValue(bR, "CR_DR_INDICATOR", i);
			var beneficiary_name_cell = cell("TD", "label", "", "", beneficiary_name, 7, "tdText", "", "text-align: left;", "","center", "", "");
			var amount_cell = cell("TD", "label", "", "", amount, 7, "tdText","", "", "", "center", "", "");
			var receipt_no_cell = cell("TD", "label", "", "", receipt_no, 7, "tdText","", "", "", "center", "", "");
			var sub_ledger_code_cell = cell("TD", "label", "", "", sub_ledger_code, 7,"tdText", "", "", "", "center", "", "");
			var sub_ledger_type_code_cell = cell("TD", "label", "", "", sub_ledger_type_code, 7,"tdText", "", "", "", "center", "", "");
			var CR_DR_INDICATOR_cell = cell("TD", "label", "", "", CR_DR_INDICATOR, 7,"tdText", "", "", "", "center", "", "");
			var ACCOUNT_HEAD_CODE_cell= cell("TD", "label", "", "", ACCOUNT_HEAD_CODE + "  ", 7,"tdText", "", "", "", "center", "", "");
			var Remarks_cell_head_cell = cell("TD", "label", "", "", "Invalid SubLedger Type/Code " , 7,"tdText", "", "", "", "center", "", "");
			new_row.appendChild(beneficiary_name_cell);
			new_row.appendChild(receipt_no_cell);
			new_row.appendChild(amount_cell);
			new_row.appendChild(CR_DR_INDICATOR_cell);
			new_row.appendChild(sub_ledger_type_code_cell);
			new_row.appendChild(sub_ledger_code_cell);
			new_row.appendChild(ACCOUNT_HEAD_CODE_cell);		
			new_row.appendChild(Remarks_cell_head_cell);
			tbody.appendChild(new_row);
			row=i;
		}
			var msg2="Note : Above collection will not be adjusted to respective beneficiaries in demand bill and also DCB statements."
			var msg3="Please cancel above DCB Receipt and Re-Enter all Receipt listed above before Freezing Trial Balance  "
		if (len > 0)  
		{
				row=row+4;
			var new_row_4 = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");
			var title2 = cell("TD", "label", "", "",msg2, 7, "", "", "color:green", "","left", "", "");
			new_row_4.appendChild(title2);
			tbody.appendChild(new_row_4);
			document.getElementById('receipt_check').rows[row].cells[0].colSpan = 9  ;
			row++;
			var new_row5 = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");
			var title3= cell("TD", "label", "", "", msg3, 7, "", "", "color:red", "","center", "", "");
			new_row5.appendChild(title3)					  
			tbody.appendChild(new_row5); 
			document.getElementById('receipt_check').rows[row].cells[0].colSpan = 9  ;
			row++;
			var msgh5="Click Report Beneficiary Receipt Checklist"; 
			var new_row7 = cell("TR", "", "", "row1", 1, 2, "", "", "", "", "","", "");
			var title4 = cell("TD", "A", "", "",msgh5, msgh5,"", "ben_receipt.jsp", "color:green", "", "center", "", "");
			new_row7.appendChild(title4)					  
			tbody.appendChild(new_row7);
			document.getElementById('receipt_check').rows[row].cells[0].colSpan = 9  ;
		}
	    }
	 }  
}
function WC_pdf_show(process,command)
{
		process_code=process;
		if (command=="pdf")
		{
			var year=document.getElementById("year").value;
			var oid=document.getElementById("oid").value;
			var month=document.getElementById("month").value;
		 
			window.open("../../../../../Pumping_Return_List?command="+command+"&process_code="+process_code+"&year="+year+"&month="+month+"&oid="+oid);
		} 
		
}
function pumping_pdf_show(process,command)
{
	    var option=0;
	    try
	    {
	    	option=document.getElementById("pr_type").value;
	    }catch(e) {
	    	
	    	option=1;
	    }
		process_code=process;
		var year=document.getElementById("year").value;
		var month=document.getElementById("month").value;
		if (year!=0 && month!=0 )
		{ 
			if (command=="pdf" && process==1)
			{
				var ben=document.getElementById("bensno").value;
				window.open("../../../../../Bill_Demand?command="+command+"&process_code="+process_code+"&bensno="+ben+"&year="+year+"&month="+month+"&option="+option);
			}else
			{
				var year=document.getElementById("year").value;
				var month=document.getElementById("month").value;
				window.open("../../../../../Bill_Demand?command="+command+"&process_code="+process_code+"&bensno=0&year="+year+"&month="+month+"&option="+option);
			}
		}else
		{
			alert("Please Select Month and Year ");  
		}
}
function unFreeze(command,process,input_value)
{
	var year=0;
	var bentype=0;
	var subdiv=0;
	var month=0;
	month =document.getElementById("month").value;
	year =document.getElementById("year").value;
	try
	{
		subdiv =document.getElementById("subdiv").value;
		bentype =document.getElementById("bentype").value;
	}catch(e) {
		
		
	}	 
	 	//if (subdiv==0 || bentype==0) process=1;
	process_code=process;
	this.command=command;
	if (command=='WCUFR')
	{
		url="../../../../../Pumping_Return_List?month="+month+"&year="+year+"&command="+command;;
	 	 document.getElementById("pr_status").value=0;	 	 
	 } else	 if (command=='UFR')  
	 {
	 	url="../../../../../Pumping_Return_List?month="+month+
		"&year="+year+
		"&bentype="+bentype+
		"&subdiv="+subdiv+
		"&process="+process+
		"&command="+command;
		 document.getElementById("pr_status").value=0;; 
	 }
	
	 var xmlobj=createObject();
     xmlobj.open("GET",url,true);
     xmlobj.onreadystatechange=function()
     {
     	result_process_pumping(xmlobj,command,input_value);
     }
     xmlobj.send(null);
}
function FR_(command,process,input_value)
{
	var year=0;
	var bentype=0;
	var subdiv=0;
	var month=0;
	var flag=0;
	process_code=process;
	month =document.getElementById("month").value;
	year =document.getElementById("year").value;
	try
	{
		subdiv =document.getElementById("subdiv").value;
		bentype =document.getElementById("bentype").value;
	}catch(e) 
	{
		bentype=0;
	}	 
	if (bentype==0)
	{
		alert("Select Beneficiary Type");
	}else
	{
		//   if (subdiv==0 || bentype==0) process=1;
		process_code=process;
		this.command=command;
	   	var  url="../../../../../Pumping_Return_List?month="+month+
	    "&year="+year+
	    "&month="+month+ 
	 	"&bentype="+bentype+
	 	"&subdiv="+subdiv+
	 	"&process="+process+
	 	"&command="+command;
	    document.getElementById("pr_status").value=0;				 	 
	    var xmlobj=createObject();
	    	xmlobj.open("GET",url,true);
			xmlobj.onreadystatechange=function()
			{
			 	result_process_pumping(xmlobj,command,input_value);
			}
		try
		{  
			xmlobj.send(null);	  
		}catch(e) {}
	 }
}

function list94(command,process,input_value)


 {
 
 var year=0;
	var bentype=0;
	var subdiv=0;
	var month=0;
	var flag=0;
	process_code=process;
	month =document.getElementById("month").value;
	year =document.getElementById("year").value;
	try
	{
		subdiv =document.getElementById("subdiv").value;
		bentype =document.getElementById("bentype").value;
	}catch(e) 
	{
		bentype=0;
	}	 
	//  if (subdiv==0 || bentype==0) process=1;
	process_code=process;
	this.command=command;
 
 if (command=='DCB_REP94')  
	{
				url="../../../../../Pumping_Return_List?month="+month+"&year="+year+
			 	"&bentype="+bentype+
			 	"&subdiv="+subdiv+
			 	"&process="+process+
			 	"&command="+command;
			 	 document.getElementById("pr_status").value=0;; 
	}
 
 
 }

function list(command,process,input_value)
 {
	   
	var year=0;
	var bentype=0;
	var subdiv=0;
	var month=0;
	var flag=0;
	process_code=process;
	month =document.getElementById("month").value;
	year =document.getElementById("year").value;
	try
	{
		subdiv =document.getElementById("subdiv").value;
		bentype =document.getElementById("bentype").value;
	}catch(e) 
	{
		bentype=0;
	}	 
	//  if (subdiv==0 || bentype==0) process=1;
	process_code=process;
	this.command=command;
	if ((command=='show')&&(process==4))  
	{
		 var reporttype=document.getElementById('reporttype').value;
		//		url="../../../../../Pumping_Return_List?month="+month+"&year="+year+
		var	url1="../../../../../Water_Charges_Report?command=DCB_REP94&month1="+month+"&year1="+year+			
			
	//     var src="../../../../../../Water_Charges_Report?command=DCB_REP93&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
		
	//	window.open(src);

		
		
			 	"&bentype="+bentype+
			 	"&reporttype1="+reporttype+
			 	"&subdiv="+subdiv+
			 	"&process="+process;
			// 	"&command=DCB_REP93;
		//	 	 document.getElementById("pr_status").value=0;; 
				window.open(url1);
	}
	
	else if ((command=='show')&&(process==5))  
		
		
		{
			//		url="../../../../../Pumping_Return_List?month="+month+"&year="+year+
			var	url1="../../../../../Water_Charges_Report?command=DCB_REP95&month1="+month+"&year1="+year+			
				
		//     var src="../../../../../../Water_Charges_Report?command=DCB_REP93&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
			
		//	window.open(src);

			
			
				 	"&bentype="+bentype+
				 	"&subdiv="+subdiv+
				 	"&process="+process;
				// 	"&command=DCB_REP93;
			//	 	 document.getElementById("pr_status").value=0;; 
					window.open(url1);
	}
	
	else if ((command=='show')&&(process==6))  
		
		
	{
		var	url1="../../../../../Water_Charges_Report?command=DCB_REP96&month1="+month+"&year1="+year+			
			
			 	"&bentype="+bentype+
			 	"&subdiv="+subdiv+
			 	"&process="+process;
				window.open(url1);
}
else if ((command=='show')&&(process==7))  
		
		
	{
		var	url1="../../../../../Water_Charges_Report?command=DCB_REP97&month1="+month+"&year1="+year+			
			
			 	"&bentype="+bentype+
			 	"&subdiv="+subdiv+
			 	"&process="+process;
				window.open(url1);
}
	
else if ((command=='show')&&(process==8))  
	
	
{
	var	url1="../../../../../Water_Charges_Report?command=DCB_REP98&month1="+month+"&year1="+year+			
		
		 	"&bentype="+bentype+
		 	"&subdiv="+subdiv+
		 	"&process="+process;
			window.open(url1);
}
	
else if ((command=='show')&&(process==9))  
	
	
{
	var	url1="../../../../../Water_Charges_Report?command=DCB_REP99&month1="+month+"&year1="+year+			
		
		 	"&bentype="+bentype+
		 	"&subdiv="+subdiv+
		 	"&process="+process;
			window.open(url1);
}
	
	else
	{
		url="../../../../../Pumping_Return_List?month="+month+"&year="+year+
	 	"&bentype="+bentype+
	 	"&subdiv="+subdiv+
	 	"&process="+process+
	 	"&command="+command;
	 	 document.getElementById("pr_status").value=0;; 
}
	
		
	
	if (command=='delete' || command=='FR')  
	{
				 url="../../../../../Pumping_Return_List?month="+month+
			    "&year="+year+"&month="+month+"&bentype="+bentype+
				"&subdiv="+subdiv+"&process="+process+"&command="+command;
				 document.getElementById("pr_status").value=0;;
	}
	if (command=='pr_select' )  
	{
				 
				 url="../../../../../Pumping_Return_List?month="+month+
				 "&year="+year+"&bentype="+bentype+"&subdiv="+subdiv+
				 "&process="+process+"&command="+command+"&oid="+document.getElementById("oid").value+
				 "&sp_flag="+document.getElementById("sp_flag").value;
				 sp_flag=document.getElementById("sp_flag").value;
				 document.getElementById("pr_status").value=0;;				 	 
	}	 
	if (command=='add' )
	{	 
				 document.getElementById("pr_status").value=0;
				 var freezs=document.getElementById("freezs").value;
				 var count =document.getElementById("row_count").value;
				 url="../../../../../Pumping_Return_List?month="+month+
				 "&year="+year+"&bentype="+bentype+"&subdiv="+subdiv+
				 "&process="+process+"&command="+command+
				     "&count="+count;
				      "&freezs="+freezs;
				
				 for (var i=1;i<=count;i++)
				 {
					 var Prv_NET_CONSUMED=document.getElementById("Prv_NET_CONSUMED"+i).innerHTML;
					 var CUR_NET_CONSUMED=document.getElementById("CUR_NET_CONSUMED"+i).innerHTML;
					 var BENEFICIARY_SNO=document.getElementById("BENEFICIARY_SNO"+i).value;
					 url+="&Prv_NET_CONSUMED"+i+"="+Prv_NET_CONSUMED;
					 url+="&CUR_NET_CONSUMED"+i+"="+CUR_NET_CONSUMED;
					 url+="&BENEFICIARY_SNO"+i+"="+BENEFICIARY_SNO;
				 }
				 
	} else if (command=='unpr' )
	{
					 document.getElementById("freezs").value=2;
					 document.getElementById("pr_status").value=0;
					 var count =document.getElementById("row_count").value;
					 url="../../../../../Pumping_Return_List?month="+month+
					 "&year="+year+
					  	"&bentype="+bentype+
					 	"&subdiv="+subdiv+
					 	"&process="+process+
					 	"&command="+command+
					     "&count="+count;
					
					 for (var i=1;i<=count;i++)
					 {
			 			 var BENEFICIARY_SNO=document.getElementById("BENEFICIARY_SNO"+i).value;
			  			 url+="&BENEFICIARY_SNO"+i+"="+BENEFICIARY_SNO;
					 }
					 
				 }
				if (parseInt(flag)==0)
				{
					
					var res=month_year_check(month,year);
					if (res!=1)
					{
						 var xmlobj=createObject();
					     xmlobj.open("GET",url,true);
					     xmlobj.onreadystatechange=function()
					     {
					    	 
					     	result_process_pumping(xmlobj,command,input_value);
					     }
					    try
					    {  
					    	xmlobj.send(null);	  
					    }catch(e) {}  
					}
				}   
	 
 }
function result_process_pumping(xmlobj,command,input_value)
{	 
	 
 if (xmlobj.readyState==4)
 {	   
 	 
	if (xmlobj.status==200)
    {
		 
		if (command=='show')
		{
        show_pumping(xmlobj,input_value);
		}
		if (command=='delete' || command=='FR')
		{
        show_pumping(xmlobj,input_value);
		}
		if (command=='pr_select')
		{
        show_pumping(xmlobj,input_value);
		}
		if (command=='add' || command=='unpr')
		{
        show_pumping(xmlobj,input_value);
		}
		if (command=='UFR' )
		{
        show_pumping(xmlobj,input_value);
		}
		if (command=='WCUFR' )
		{
        show_pumping(xmlobj,input_value);
		}
    }
  }
    
}
function show_pumping(xmlobj,input_value)
{
	
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
 
	  if (command=='WCUFR' )
		{
			document.getElementById("pr_status").value=1;
			var srow=bR.getElementsByTagName("record_c1")[0].firstChild.nodeValue;
		    var prflag=bR.getElementsByTagName("prflag")[0].firstChild.nodeValue;
		    switch(parseInt(prflag))
		    {
		    case 1:
		    		alert("Watercharges Successfully Unfreezed ! ");
		    		break;
		    case 2:
		    	alert("Watercharges Not Yet Freezed ! ");
		    		break;
		    case 3:
		    	alert("Data Not found !");
	    		break;
		    case 4:
		    	alert("Journal Already Posted ...Cannot Unfreeze ! ");
	    		break;		
		    
		    }
					
			  
		}
	  if (command=='UFR')
	{ 
		  	document.getElementById("pr_status").value=1;
		  var srow=bR.getElementsByTagName("srow")[0].firstChild.nodeValue;
		  var frow=bR.getElementsByTagName("frow")[0].firstChild.nodeValue;
		  var sflag=bR.getElementsByTagName("sflag")[0].firstChild.nodeValue;
		  
		  
		if (sflag==1)
		{
			alert("Pumping Return can't be UnFreezed...Please validate PR !");
		}else
		{
		  if (srow==1)
		  {
			  // msgload("Pumping Return UnFreezed.....Water Charges will be Re-calculated !",2)
			  alert("Pumping Return UnFreezed.....Water Charges will be Re-calculated !");
		  }
		  else
		  {
			  /*  
			  msgload("Journal Already Posted For This Month Through FAS!! " +
			  		"Pumping Return Entry/Update/Delete Not Possible ..." +
			  		"First Cancel Journal through FAS ... Then Unfreeze PR !!" +
			  		"",2)
			  		*/
			  alert("Journal Already Posted For This Month Through FAS!! " +
				  		"Pumping Return Entry/Update/Delete Not Possible ..." +
				  		"First Cancel Journal through FAS ... Then Unfreeze PR !!" );
		  }
		}
	}
	if (command=='show')
	{
	  document.getElementById("pr_status").value=1;
	  var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
	  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	  var tbody = document.getElementById("entred_body");
	  var table = document.getElementById("entred_data");
	 console.log("Response is ",bR);
	  var t=0;	
	  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
	 
	  var len=bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
	  if (len==0)
	  {
		  alert("Data Not Found...")
		  
	  }
	  
	  var prflag=0;
	  try
	  {
		    prflag = document.getElementById("prflag").value;
	  }catch(e) {prflag=0;}
	 
	  
	  var new_row_H=cell("TR","","","row","","","tdHeader","","","","","","");
	  var head01=cell("TD","label","","","Sl.No",2,"tdHeader",""," ","","center","","");
	  var head1=cell("TD","label","","","Beneficiary Name",2,"tdHeader",""," ","","center","","");
	  var head2=cell("TD","label","","","Beneficiary Type",2,"tdHeader",""," ","","center","","");
	  var head3=cell("TD","label","","","Total Units[KL]",2,"tdHeader",""," ","","center","","");
	  var head4=cell("TD","label","","","",2,"",""," ","","center","","");
	  new_row_H.appendChild(head01);
	  new_row_H.appendChild(head1);
	  new_row_H.appendChild(head2);
	  new_row_H.appendChild(head3);
	  new_row_H.appendChild(head4);
	  tbody.appendChild(new_row_H);
	  for (i=0;i<len;i++)
		{
		    var BENEFICIARY_SNO = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
			var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
			var BENEFICIARY_TYPE = bR.getElementsByTagName("BENEFICIARY_TYPE")[i].firstChild.nodeValue;
			var BENEFICIARY_TYPE_SNO= bR.getElementsByTagName("BENEFICIARY_TYPE_SNO")[i].firstChild.nodeValue;
			console.log("Original qty for beneficiary_sno value:", BENEFICIARY_SNO,"--", bR.getElementsByTagName("qty")[i].firstChild.nodeValue);

			var qty = bR.getElementsByTagName("qty")[i].firstChild.nodeValue;
			var pr_record = bR.getElementsByTagName("pr_record")[i].firstChild.nodeValue;
			
		      var BENEFICIARY_TYPE_SNO_cell=cell("TD","input","hidden","BENEFICIARY_TYPE_SNO"+(i+1),BENEFICIARY_TYPE_SNO,2,"","","","2%","","","");
		    var row_cell=cell("TD","label","","",(i+1),2,"","","","5%","center","","");
			var new_row=cell("TR","","","row"+(i+1),(i+1),2,"tdtablerow1","","","","","","");
			var SNO_cell=cell("TD","input","hidden","select"+(i+1),BENEFICIARY_SNO,2,"","","","2%","","","");
		    var NAME_cell=cell("TD","label","","BENEFICIARY_NAME"+(i+1),BENEFICIARY_NAME,2,"tdtablerow1","","","35%","left","","");
		    var TYPE_cell=cell("TD","label","","BENEFICIARY_TYPE"+(i+1),BENEFICIARY_TYPE,2,"tdtablerow1","","","15%","left","","");
		    var qty_cell=cell("TD","label","","qty"+(i+1),qty,2,"tdtablerow1","","","10%","right","","");
		    var temp_cell=cell("TD","label","","","",2,"","","","10%","","onclick","Demand_Report_List.jsp");
		    var temp_cell2=cell("TD","label","","","",2,"","","font-size: 15px","10%","","left","");
		    
		  	var href_all_cell=cell("TD","A","EDIT","EDIT","Edit",2,"hf","javascript:rld(1,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","20%","center","onClick","");
		  	var href_report_cell=cell("TD","A","EDIT","EDIT","Report",2,"hf","javascript:rld(3,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","10%","right","","");
		  	
		  	var freeze_report_cell=cell("TD","A","EDIT","EDIT","validate " ,2,"tdText","javascript:rld(4,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","5%","right","","");
		  	var href_demand_cell=cell("TD","A","EDIT","EDIT","Demand",2,"hf","javascript:rld(2,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","19%","right","","");
		  	var del_report_cell=cell("TD","A","EDIT","EDIT","Delete",2,"hf","javascript:del(3,'"+BENEFICIARY_SNO+"',"+BENEFICIARY_TYPE_SNO+")","font-size: 15px","10%","right","","");
		  	 new_row.appendChild(row_cell);
		  	new_row.appendChild(BENEFICIARY_TYPE_SNO_cell);
 		    new_row.appendChild(SNO_cell);
 		    new_row.appendChild(NAME_cell);
 		    new_row.appendChild(TYPE_cell);
 		    new_row.appendChild(qty_cell);
 		  
 		    if (parseInt(prflag)==2)
 		    	new_row.appendChild(href_report_cell);
 	 
 		   		var PR_FLAG="N";
 		  		pr_record=0;
		 		if (PR_FLAG!="Y")
		 	 	{  
		 			if (parseInt(prflag)==1)
		 		    	new_row.appendChild(href_all_cell);
		 		    else
		 		    	new_row.appendChild(temp_cell);	
		 	 	}
		 		tbody.appendChild(new_row);
 		    
		}
	  
	}
	 
	if (command=='delete')
	{
		 var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		 var del_row = bR.getElementsByTagName("del_row")[0].firstChild.nodeValue;
		 alert("Affeceted Records is " +del_row );
	}
	if (command=='FR')
	{
		 var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		 document.getElementById("pr_status").value=1;
		 var up_row = bR.getElementsByTagName("up_row")[0].firstChild.nodeValue;
		 var type=document.getElementById("bentype").options[document.getElementById("bentype").selectedIndex].text;
		  alert(msg);   
		// msgload( msg,1);
		// journal_start()        
		// window.location.reload();
		 
	}  
	if (command=='add')
	{
 		 var up_row = bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;
 		 document.getElementById("pr_status").value=1;
 		//msgload("Affeceted Records is " +up_row ,2);
		alert("Affeceted Records is "+up_row); 
		
		 
	}
	if (command=='unpr')
	{
 		 var up_row = bR.getElementsByTagName("ins_row")[0].firstChild.nodeValue;
 		 document.getElementById("pr_status").value=1;
 		alert("Affeceted Records is "+up_row);
		 
		
		 
	}
	if (command=='pr_select')
	{	   
		  var net_CONSUMED=0;
		 
		  document.getElementById("pr_status").value=1;
		  var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
		  var tbody = document.getElementById("pr_ben_body");
		  var table = document.getElementById("pr_ben_data");
		  var t=0;	
		  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
		  
		  var len=bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		  
		  document.getElementById("row_count").value=len;
		  var total_amt=0;
		   
		  for (i=0;i<len;i++)
			{
			    var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
			    var BENEFICIARY_SNO = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
 				var BENEFICIARY_NAME = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
 				var sno_cell2=cell("TD","label","","",(i+1),2,"tdText","","","5%","","left","");
 				
 				var SNO_cell=cell("TD","input","hidden","BENEFICIARY_SNO"+(i+1),BENEFICIARY_SNO,2,"tdText","","","2%","","","");
 			    var NAME_cell=cell("TD","label","","BENEFICIARY_NAME"+(i+1),BENEFICIARY_NAME,2,"tdText",""," ","","left","","");
 			    var NAME_link_cell=cell("TD","A","","BENEFICIARY_NAME"+(i+1),BENEFICIARY_NAME,2," ","benschamount.jsp?BENEFICIARY_SNO="+BENEFICIARY_SNO+"&spl_flag=2&flag=1"," ","","left","","");
 			    var BEN_TYPE_DESC = bR.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
			    var BEN_TYPE_DESC_cell=cell("TD","label","","BENEFICIARY_NAME"+(i+1),BEN_TYPE_DESC,2,"tdText",""," ","15%","left","","");
			    var CUR_NET_CONSUMED = bR.getElementsByTagName("CUR_NET_CONSUMED")[i].firstChild.nodeValue;
 			    var CUR_NET_CONSUMED_cell=cell("TD","label","","CUR_NET_CONSUMED"+(i+1),CUR_NET_CONSUMED,2,"tdText","","","15%","right","","");
 			    var Prv_NET_CONSUMED = bR.getElementsByTagName("Prv_NET_CONSUMED")[i].firstChild.nodeValue;
 			    var Prv_NET_CONSUMED_cell=cell("TD","label","","Prv_NET_CONSUMED"+(i+1),Prv_NET_CONSUMED,2,"tdText","","","15%","right","","");
 			    
			    
 			   net_CONSUMED=parseFloat(net_CONSUMED)+parseFloat(CUR_NET_CONSUMED)
			    new_row.appendChild(SNO_cell);
			    new_row.appendChild(sno_cell2);
			    new_row.appendChild(NAME_link_cell);			    
			  //  new_row.appendChild(BEN_TYPE_DESC);
 			    new_row.appendChild(BEN_TYPE_DESC_cell);
 			    new_row.appendChild(Prv_NET_CONSUMED_cell);
 			    new_row.appendChild(CUR_NET_CONSUMED_cell);
 			   
 			    if (sp_flag==2)
 			    {
 			    	var cal_amt=bR.getElementsByTagName("cal_amt")[i].firstChild.nodeValue;
 			    	try {
 			    	total_amt=parseFloat(total_amt)+parseFloat(cal_amt);
 			    	}catch(e){alert(e)}
 			    	var cal_amt_CONSUMED_cell=cell("TD","label","","cal_amt"+(i+1),cal_amt,2,"tdText","","","15%","right","","");
 			    	new_row.appendChild(cal_amt_CONSUMED_cell);
 			    }
			   
 			    tbody.appendChild(new_row);
			}    
		   
		 
		  try {
		 	document.getElementById("net_cons").innerHTML=""+roundNumber(parseFloat(net_CONSUMED),3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		  }catch(e) {}
		   
		  try {   
			  	
		  		document.getElementById("total").innerHTML="" +total_amt;
		  }catch(e)
		  {		  
			  
		  }	  
	}	
}
function journal_start()
{  
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	url="../../../../../Pumping_Return_List?command=journal&month="+month+"&year="+year;
	 var xmlobj=createObject();
     xmlobj.open("GET",url,true);     
     xmlobj.onreadystatechange=function()
     {    	 
    	 journal_process(xmlobj);
     }
     xmlobj.send(null);     
}
function journal_process(xmlobj)
{
	 if (xmlobj.readyState==4)
	 {	 	   
		if (xmlobj.status==200)
	    {
			var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
			var jrow=xmlobj.responseXML.getElementsByTagName("jrow")[0].firstChild.nodeValue;
			 
			/*
			if (jrow==0)
				msgload("Water Charges Calculation NOT Done.....",1)
			else
				msgload("Water Charges Calculation  Done........",2)
			*/	
			 
			if (jrow==0)
				alert("Water Charges Calculation NOT Done.....");
			else
				alert("Water Charges Calculation  Done........");
			//document.forms["wccal"].submit();
		} 
	  }
}

function del(process,sno)
{
	list('delete',process,sno)
}
function frz(process,sno)
{
	list('FR',process,sno)
}
function journal_report(flag,val)
{
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	var subdiv=0;
	try
	{
		subdiv =document.getElementById("subdiv").value;
 		
	}catch(e) {
	}	
	
	this.flag=flag;
	var command="journal_report";
	if (command=='journal_report')  
 	{
	if (flag==1)
	{
		 document.getElementById("pr_status").value=0;
		 	url="../../../../../Pumping_Return_List?"+
	       		"&year="+year+
	       		"&month="+month+	    
	       		"&subdiv="+subdiv+
	       		"&process=1"+
	       		"&command="+command+
		 		"&flag="+flag;
	}
	else if (flag==3)
	{
		document.getElementById("pr_status").value=0;
	 	url="../../../../../Pumping_Return_List?"+
       		"&year=0"+year+
       		"&month="+month+	    
       		"&subdiv="+subdiv+
       		"&process=1"+
       		"&command="+command+
	 		"&flag="+flag;
	}
	else
	{  
		 
		var schtype =val;
		 document.getElementById("pr_status").value=0;
	 	url="../../../../../Pumping_Return_List?"+
	 	"&month="+month+	    
   		"&year="+year+	       		
   		"&subdiv="+subdiv+
   		"&process=1"+
   		"&command="+command+
 		"&flag="+flag+
 		"&schtype="+schtype;
	}
	}
	 var xmlobj=createObject();
     xmlobj.open("GET",url,true);
     
     xmlobj.onreadystatechange=function()
     {
    	 
    	 journal_report_details(xmlobj);
     }
     xmlobj.send(null);
}

function journal_report_details(xmlobj)
{
	 if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
			 
			
			if (flag==1)
			{
			
				 document.getElementById("pr_status").value=1;
						var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
						var tbody = document.getElementById("journ_ben_body");
						var table = document.getElementById("journ_ben_data");    
						var len=bR.getElementsByTagName("SCH_TYPE_ID").length;
				if(len!=0)
				{
						var ACCOUNTING_UNIT_NAME=bR.getElementsByTagName("ACCOUNTING_UNIT_NAME")[0].firstChild.nodeValue;;
						document.getElementById("off").innerHTML=ACCOUNTING_UNIT_NAME;
						var net_flag=0;
						for (i=0;i<len;i++)
						{
							  var new_row=cell("TR","","","row1",1,2,"tdText","","","","","","");//13
							  var cramt = bR.getElementsByTagName("cramt")[i].firstChild.nodeValue;
							  var flag_new = bR.getElementsByTagName("flag_new")[i].firstChild.nodeValue;					  
							  var dramt = bR.getElementsByTagName("dramt")[i].firstChild.nodeValue;
						 	  var SCH_TYPE_DESC = bR.getElementsByTagName("SCH_TYPE_DESC")[i].firstChild.nodeValue;	
						 	  var SCH_TYPE_ID = bR.getElementsByTagName("SCH_TYPE_ID")[i].firstChild.nodeValue;
						 	  var cramt_cell=cell("TD","label","","",cramt,7,"tdText","","","","right","","");
						 	  var sno_cell=cell("TD","label","","",(i+1),7,"tdText","","","","right","","");
							  var dramt_cell=cell("TD","label","","",dramt,7,"tdText","","","","right","","");
							  var SCH_TYPE_DESC_cell=cell("TD","label","","",SCH_TYPE_DESC,7,"tdText","","","","left","","");
						      var button_cell=cell("TD","input","button","submit"+(i+1),"Click",7,"fb2","","text-align: center","10%","","onclick","details("+SCH_TYPE_ID+")");
						      var button_cell2=cell("TD","input","button","submit"+(i+1),"PDF Report",7,"fb2","","text-center: right","10%","","onclick","detailspdf("+SCH_TYPE_ID+")");
 							  var SCH_TYPE_ID_cell=cell("TD","input","hidden","SCH_TYPE_ID"+(i+1),SCH_TYPE_ID,7,"","","","","right","","");
 							  var chk_cell=cell("TD","input","checkbox","ch"+(i+1),"",7,"","","","","center","","");
 							  net_flag+=flag_new;
 							  if (flag_new==1)
							  { 
 								 var img_info=cell("TD","A","","","",7,"img_but_wr","","","","right","","");  
							  }else
							  {
								  var img_info=cell("TD","A","","","",7,"img_but","","","","right","","");								  
							  }					 	  						 	  
      
 							   	new_row.appendChild(sno_cell);
 							   	new_row.appendChild(SCH_TYPE_DESC_cell);
							    new_row.appendChild(cramt_cell);  
							    new_row.appendChild(dramt_cell);
							    new_row.appendChild(img_info);
							    new_row.appendChild(SCH_TYPE_ID_cell);
							    new_row.appendChild(button_cell);
							    new_row.appendChild(button_cell2);							  
							    //new_row.appendChild(chk_cell);			    
							    tbody.appendChild(new_row);
						}						
				}	else
				{					
				//	alert("Select Valid month and year...");
					//document.getElementById("FR_BUT").disabled=true;
				}
				
				if (net_flag > 0)
				{  
					alert("Total CR and Total DR Not Tallied.... Water Charges Cannot Be Freezed");
					//document.getElementById("FR_BUT").disabled=true;  
				}
							 
			}else if (flag==3)
			{
				     document.getElementById("pr_status").value=1;
				     var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
					  var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
					  //msgload(msg,2)
					  alert(msg);
			}
			else
			{
				
				
				 document.getElementById("pr_status").value=1;
				var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
				var tbody1 = document.getElementById("journ_ben_body1");
				var table = document.getElementById("journ_ben_data1");    
				var len=bR.getElementsByTagName("SCH_TYPE_ID").length;
				
				 
				for (i=0;i<len;i++)
				{
					  var new_row=cell("TR","","","row1",1,2,"tdText","","","","","","");//13
					  var amt = bR.getElementsByTagName("AMOUNT")[i].firstChild.nodeValue;
					  var REMARKS= bR.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
					  
					  var CR_DR_INDICATOR = bR.getElementsByTagName("CR_DR_INDICATOR")[i].firstChild.nodeValue;
					 
					  var ACCOUNT_HEAD_CODE = bR.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
					 
					  var projectid=bR.getElementsByTagName("Projectid")[i].firstChild.nodeValue;
					  if (projectid!=0)
						  projectid="  ("+projectid+")";
					  else
						  projectid="";
					  
					  var SCH_NAME="";
					  try
					  {
						  SCH_NAME= bR.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
					  }catch(e) {SCH_NAME="";}
				 
					 	 var sno_cell=cell("TD","label","","",(i+1),7,"tdText",""," ","","left","","");
					 	  
					  var ccramt_cell,ddramt_cell;
				 	  if (CR_DR_INDICATOR=='CR')
				 		 ccramt_cell=cell("TD","label","","",amt,7,"tdText",""," ","","right","","");
				 	  else
				 		 ccramt_cell=cell("TD","label","",""," ",7,"tdText",""," ","","left","","");
				 	  
				 	 if (CR_DR_INDICATOR=='DR')
				 		ddramt_cell=cell("TD","label","","",amt,7,"tdText",""," ","","right","","");
				 	 else
				 		ddramt_cell=cell("TD","label","","","",7,"tdText",""," ","","left","","");
				 	  var sno_cell=cell("TD","label","","",(i+1),7,"tdText","","","","left","","");
				 	  var ACCOUNT_HEAD_CODE_cell=cell("TD","label","","",ACCOUNT_HEAD_CODE,7,"tdText","","","","left","","");
					  var SCH_NAME_cell=cell("TD","label","","",SCH_NAME,7,"tdText","","","","left","","");
					  var project_cell=cell("TD","label","","","project"+projectid,7,"tdText","","","","left","","");
					  var REMARK_NAME_cell=cell("TD","label","","",REMARKS,7,"tdText","","","","left","","");
					    new_row.appendChild(sno_cell);
					    new_row.appendChild(ACCOUNT_HEAD_CODE_cell);
					    new_row.appendChild(project_cell);
					    new_row.appendChild(SCH_NAME_cell);				  
					    new_row.appendChild(ccramt_cell);					     
					    new_row.appendChild(ddramt_cell);
					    new_row.appendChild(REMARK_NAME_cell);
					    tbody1.appendChild(new_row);
				}
			}
		}
	  }
}

function detailspdf(val)
{
	a=window.open("../../../../../Pumping_Return_List?command=journalpdfreport&val="+val)
}

function details(val)
{
	a=window.open("journal_report_details.jsp?val="+val)
}