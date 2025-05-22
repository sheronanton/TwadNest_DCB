
 var xmlobj=createObject();   
 var url="";
 var month=0,year=0;
 var cmonth =new Array("-select month-","January","February","March","April","May","June","July","August","September","October","November","December");
 function request_process(command)
 {
	
	 var BEN_TYPE_ID=document.getElementById("BEN_TYPE_ID").value;
	 var ben=document.getElementById("ben").value;
	try
	{
		 try
		 {
			 month=document.getElementById("month").value;
			 year=document.getElementById("year").value;
		 }catch(e){}
		 var startname=document.getElementById("startname").value;
		 url="../../../../../knowyourben?ben_type_id="+BEN_TYPE_ID+"&option="+command+"&startname="+startname+"&ben="+ben+"&year="+year+"&month="+month;		 
		 xmlobj.open("GET",url,true);
	     xmlobj.onreadystatechange=function()
	     { 
	    	 response_process(xmlobj,command);
	     }
	     xmlobj.send(null);	   
	}catch(e)
	{
		
		
	}
 }  
 
 function response_process(xmlobj,command)
 {
	 try    
		{
			var bR = xmlobj.responseXML.getElementsByTagName("response")[0];			
			if (xmlobj.readyState == 4) 
			{
				if (xmlobj.status == 200) 
				{
					if (command==1 || command==2 )
					{
						try      
						{
							document.getElementById("ben").options.length = 0;
							var len =bR.getElementsByTagName("BENEFICIARY_NAME").length;
							for (i = 0; i < parseInt(len); i++) 
							{
								var sno = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
								var name = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
								addOption(document.getElementById("ben"), name, sno)
							}
						} catch (e) 
						{
							 
						} 
					} else if (command==3 )
					{					
						 
						var BEN_TYPE_DESC=xmlValue(bR,"BEN_TYPE_DESC",0,1);
						var BENEFICIARY_NAME =xmlValue(bR,"BENEFICIARY_NAME",0,1);
						var BILLING_ADDRESS1=xmlValue(bR,"BILLING_ADDRESS1",0,1);
						var BILLING_ADDRESS2=xmlValue(bR,"BILLING_ADDRESS2",0,1);
						var BILLING_ADDRESS3=xmlValue(bR,"BILLING_ADDRESS3",0,1);
						var BILLING_PIN_CODE=xmlValue(bR,"BILLING_PIN_CODE",0,1);
						var DSNAME=xmlValue(bR,"DSNAME",0,1);
						var BLOCK_NAME=xmlValue(bR,"BLOCK_NAME",0,1);
						BLOCK_NAME=(BLOCK_NAME=="0")?"-":BLOCK_NAME;  
						var count=xmlValue(bR,"count",0,1);
						var STATUS=bR.getElementsByTagName("STATUS")[0].firstChild.nodeValue;
						
						if (STATUS=="L") STATUS="Active"; else STATUS="Cancel";
						
						var res="<table border='1' cellpadding='0'  cellspacing='0'   width='100%'><tr ><th>&nbsp; </th><th>Address</th><th>Classificaiton</th><th>District</th><th>Block</th>";
						    res+="<th>Status</th><th>Whether Bill Beneficiary </th></tr>";
						    res+="<tr><td width='30%'>"+BENEFICIARY_NAME+"</td><td width='30%'>"+BILLING_ADDRESS1+"<br>"+BILLING_ADDRESS2+"<br>"+BILLING_ADDRESS3+"-"+BILLING_PIN_CODE+"</td>";
						    res+="<td width='15%'>"+BEN_TYPE_DESC+"</td><td width='10%'>"+DSNAME+"</td><td width='10%'>"+BLOCK_NAME+"</td><td align='center' width='10%'>"+STATUS+"</td>";
						    if (parseInt(count)>=1)
						    {
						    	res+="<td>Yes</td>"; 
						    }else
						    {
						    	res+="<td>No</td>";
						    }
						    res+="</tr></table>"
						    if (STATUS=="Active")
						    {
							    res+="<table border='1' width='100%' cellpadding='6' cellspacing='0'><tr  bgcolor='#eeaabb'><td colspan=8>Beneficiary Meter Details</th></tr><tr><th>Meter Location </th><th>Location Status</th><th>Schemes Involved</th><th>Schemes Type</th><th>Schemes Status</th><th>Tariff Flag</td><th>Tariff Rate</th></tr>";
							    var len = bR.getElementsByTagName("METRE_LOCATION").length;
								for (i = 0; i < parseInt(len); i++) 
								{ 
								    var METRE_LOCATION=xmlValue(bR,"METRE_LOCATION",i,1);
								    var METER_STATUS=xmlValue(bR,"METER_STATUS",i,1);
								    if (METER_STATUS=="L") METER_STATUS="Active"; else METER_STATUS="Cancel";
								    var SCH_NAME=xmlValue(bR,"SCH_NAME",i,1);
								    var TYPE_DESC=xmlValue(bR,"TYPE_DESC",i,1);
								    var STATUS_DESC=xmlValue(bR,"STATUS_DESC",i,1);
								    var TARIFF_FLAG=xmlValue(bR,"TARIFF_FLAG",i,1);
								    var TARIFF_RATE=xmlValue(bR,"TARIFF_RATE",i,1);
								    res+="<tr><td>"+METRE_LOCATION+"</td><td  align='center'>"+METER_STATUS+"</td><td>"+SCH_NAME+"</td><td >"+TYPE_DESC+"</td><td  align='center'>"+STATUS_DESC+"</td><td  align='center'>"+TARIFF_FLAG+"</td><td  align='center'>"+TARIFF_RATE+"</td></tr>";
								}
								
	
								res+="<tr><td colspan=7>&nbsp;</td></tr><tr bgcolor='#eeaabb'><td colspan=7>Monthly Statistics </td></tr></table><table border='1' width='100%' cellpadding='7' cellspacing='0'><tr><td>Year :</td><td><select id='year'><option value=0>select year</option>" ;
								var yr=new Date();
								var year=yr.getFullYear();
								for(var j=year-1;j<=year;j++)
								{
									res+="<option value="+j+">"+j+"</option>";
									
								}    
								res+="<td>Month:</td><td><select id='month' onchange='request_process(4)'><option value=0>select Month</option>";
								for(var i=1;i<=12;i++)
								{
									res+="<option value="+i+">"+cmonth[i]+"</option>";
								}
								res+="</select></td>";  
								
								res+="</td></tr></table><div id='pr'></div>";
						    }
						    document.getElementById("bas1").innerHTML=res;					
						    
						      
					}else if (command==4 )
					{	
						var APR=xmlValue(bR,"APR",0,1);
						var CUROB =xmlValue(bR,"CUROB",0,1);
						var WC=xmlValue(bR,"WC",0,1);
						var BILL_CT=xmlValue(bR,"BILL_CT",0,1);
						var BILL_AMT=xmlValue(bR,"BILL_AMT",0,1);
						var COLLECTION=xmlValue(bR,"COLLECTION",0,1);
						var LEDGER_CT=xmlValue(bR,"LEDGER_CT",0,1);
						var year=xmlValue(bR,"year",0,1);
						var month=xmlValue(bR,"month",0,1);  
						var MIN_CHARGES=xmlValue(bR,"MIN_CHARGES",0,1);  
						var ADD_CHARGES=xmlValue(bR,"ADD_CHARGES",0,1);  
						var res="<table ' cellpadding='10' cellspacing='0' border=1 width='100%'><tr ><th>Opening Balance April-"+year+" </th>" +
								"<th>Opening Balance "+cmonth[month]+"-"+year+"</th>" +
								"<th>Water Charges</th><th>Bill Generated </th><th>Bill Amount</th><th>Receipt</th><TH>Journal (DR)</th><th>Journal (CR)</th><th>Ledger Posted</th></tr>";
						BILL_CT=(parseInt(BILL_CT)>0)?" Yes" : "No";
						LEDGER_CT=(parseInt(LEDGER_CT)>0)?" Yes" : "No"
						res+="<tr><td align='right'>"+APR+"</td><td align='right'>"+CUROB+"</td><td align='right'>"+WC+"</td><td align='right'>"+BILL_CT+"</td><td align='right'>"+BILL_AMT+"</td><td align='right'>"+COLLECTION+"</td>";
						res+="<td>"+ADD_CHARGES+"</td><td>"+MIN_CHARGES+"</td><td align='center'>"+LEDGER_CT+"</td>";
						res+="</table>";
						document.getElementById("pr").innerHTML=res;	
					}
				}
			}
		} catch (e) 
		{
		}
	   
 }