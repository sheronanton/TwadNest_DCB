var xmlhttp;
var count_record;
var missing_interest_data="",interest_rate_data='',count_rows='';
var finallyRelieved='';
function Exit()
{
    window.close();
}
function doParentEmp(emp)
{
document.getElementById("txtGpfNo").value=emp;
closeEmp();
call('Get');
}
var winemp;

function servicepopup()
{
     if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null;
    }
    // startwaiting(document.Hrm_TransJoinForm);   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

function closeEmp()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
window.onload=function()
{
	loadvalue1();
}


 function loadvalue1()
 {      
	
       document.Hrm_GpfSettlementForm.addBtn.disabled=false;
       document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
       document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
       document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
       
       
       document.Hrm_GpfSettlementForm.date2.readOnly=false;    
       document.Hrm_GpfSettlementForm.imgDate2.onclick=function(){
       	showCalendarControl(document.getElementById('date2'));
       };
       
       document.Hrm_GpfSettlementForm.radRlv[0].disabled=false;
       document.Hrm_GpfSettlementForm.radRlv[1].disabled=false;
       document.Hrm_GpfSettlementForm.radRlv[2].disabled=false;
       
   }
function getxmlhttpObject()
{
	 var req = false;
	 try 
	 {
	       req= new ActiveXObject("Msxml2.XMLHTTP");
	 }
	 catch (e) 
	 {
	       try 
	       {
	            req = new ActiveXObject("Microsoft.XMLHTTP");
	       }
	       catch (e2) 
	       {
	            req = false;
	       }
	 }
	 if (!req && typeof XMLHttpRequest != 'undefined') 
	 {
	       req = new XMLHttpRequest();
	 }   
	 return req;
}




function fillEmployeeDetails(response){
	
	var empname=response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
    var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
    var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
    var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
    var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
    
   
    
    document.getElementById("comEmpId").value=empname;
    document.getElementById("txtDOB").value=dob;
    document.getElementById("txtGpfNo").value=gpfno;
    document.getElementById("designation").value=d_id;
    document.getElementById("txtEmpId").value=emp_id;
	
}

function fillBalanceDetails(response){	
	
	var Last_Slip_Closing_Balance=response.getElementsByTagName("Last_Slip_Closing_Balance")[0].firstChild.nodeValue; 
	var Subscription_Credit=response.getElementsByTagName("fimp2003")[0].firstChild.nodeValue;
	var Subscription_Debit=response.getElementsByTagName("Subscription_Debit")[0].firstChild.nodeValue;
	var Impound_Regular=response.getElementsByTagName("Impound_Regular")[0].firstChild.nodeValue;
	var Imp_Balance_Has_On_Date=response.getElementsByTagName("Imp_Balance_Has_On_Date")[0].firstChild.nodeValue;
	var Balance_Has_On_Date=response.getElementsByTagName("Balance_Has_On_Date")[0].firstChild.nodeValue;
	
	
	document.getElementById("Regular_Balance").value=Last_Slip_Closing_Balance;
	document.getElementById("txtImpound2003bal").value=Subscription_Credit; 
	document.getElementById("DA_Amt").value=Subscription_Debit; 
	document.getElementById("txtImpoundBal").value=Impound_Regular;
	//document.getElementById("txtImpBalHasOnDate").value=Imp_Balance_Has_On_Date;
  //  document.getElementById("txtBalHasOnDate").value=Balance_Has_On_Date;
    
    
	
}
function fillRelievalDetails(response){
	
	var date2=response.getElementsByTagName("firstRelieveDate")[0].firstChild.nodeValue;
	var radRlv=response.getElementsByTagName("relieve_status")[0].firstChild.nodeValue;
	document.getElementById("date2").value=date2;
	if(radRlv=='SAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	}
	else if(radRlv=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
	}
	else if(radRlv=='DTH'){
		//alert("With in DTH METHOD............");
		document.Hrm_GpfSettlementForm.dth_table.style.display="block";
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
	}
	var selectedYear="";
	selectedYear=response.getElementsByTagName("selectedYear")[0].firstChild.nodeValue;
	var selectedMonth="";	
	selectedMonth=response.getElementsByTagName("selectedMonth")[0].firstChild.nodeValue;
	
	//load year
	var listYearValues='';
	var listLength='';
//   
	var listMonthValues='';
//	
//    
	
}
function fillFirstRelievalDetails(response){
	
	var firstRelieveStatus=response.getElementsByTagName("firstRelieveStatus")[0].firstChild.nodeValue;
	var firstRelieveDate=response.getElementsByTagName("firstRelieveDate")[0].firstChild.nodeValue;
	
	if(firstRelieveStatus=='SAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}if(firstRelieveStatus=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}if(firstRelieveStatus=='DTH'){
	//	alert("with In DEATH OPERATION..........");
		
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
		Dth_Function();
//		document.getElementById("date2").value=firstRelieveDate;
//		
//		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
//	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
//	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
//	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
//	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
//	    document.Hrm_GpfSettlementForm.dth_table.style.display="block";
	    
	    
	}if(firstRelieveStatus=='ThisMonthSAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	 //   alert('This employee is going to retire this month');
	}else if(firstRelieveStatus=='NONE'){
		
		clearData();
		alert('This employee has not been relieved this month');
	}else if(firstRelieveStatus=='FINISHED'){
		
		clearData();
		alert('This process has already finished for this employee');
	}
}

function emptyEmployeeDetails(){
	document.getElementById("comEmpId").value="";
    document.getElementById("txtDOB").value="";
    document.getElementById("txtGpfNo").value="";
    document.getElementById("designation").value="";
    document.getElementById("txtEmpId").value=""; 
}

function makeRelieveDetailsDisabled(){
	
	document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
    document.Hrm_GpfSettlementForm.date2.readOnly=true;
    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
    
    document.Hrm_GpfSettlementForm.listYear.disabled=true;
    document.Hrm_GpfSettlementForm.listMonth.disabled=true;
	
}
/*function makeNoteDetailsDisabled(){
	document.Hrm_GpfSettlementForm.txtMDProc.readOnly=true;
    document.Hrm_GpfSettlementForm.date1.readOnly=true;
    document.Hrm_GpfSettlementForm.imgDate1.onclick=null;
    document.Hrm_GpfSettlementForm.listFinanceYear.disabled=true;    
	
}*/
function makeRelieveAndNoteDetailsDefault(){	
	makeRelieveDetailsDisabled();
	//makeNoteDetailsDisabled();
}



function stateChanged1()
{
	//alert("With in ST CH 1...");
	 var flag,command,response,status,yearList,monthList;
	    
	    if(xmlhttp.readyState==4)
	    {
	       if(xmlhttp.status==200)
	       {
	    	   //alert("With in TRY......");
	    	   try {
	    		   //alert("With in TRY......");
	    	   response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	    	  // alert("RESPONSE === "+response);
	    	   command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				//alert("COMMAND=========="+command);
				//status=response.getElementsByTagName("status")[0].firstChild.nodeValue;
				//alert("Status === "+status);
				flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				//alert("Flag  ==" +  flag);
				
				  if(command=="other_details")
	            	{
	            		var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
	            		//alert("Count of Other ==" +count);
	            		if(count!=0)
	            			{
	            				if(flag=="success")
	            					{
		            					//alert("LEGAL HAIR ID================"+response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue);
				        				document.Hrm_GpfSettlementForm.dth_Legal_person_ID.value=response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue;
	            						document.getElementById("subject").value=response.getElementsByTagName("SUBJECT")[0].firstChild.nodeValue;
	            					}
	            			}
	            	}
				
				
	    	   }
	    	   catch (e) {
					// TODO: handle exception
				}
	    	   
	    	   grid1();
	       }
	    }
}



function stateChanged()
{
	
    var flag,command,response,status,yearList,monthList;
    
    if(xmlhttp.readyState==4)
    {
    	//alert("4");
       if(xmlhttp.status==200)
       {
    	   //alert("200");
    	   try {
    		   response=xmlhttp.responseXML.getElementsByTagName("response")[0];
    		  // alert("RESPONSE========="+response);
    		  
    		   
			} catch (e) {
				// TODO: handle exception
			}
			try {
				command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				
			} catch (e) {
				// TODO: handle exception
			}
			try {
				
				flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				
			
		   
				
				  if(command=="get")
		            {
						var relieval_reason=response.getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
		                if(flag=='success1')
		                {	
		                	if(relieval_reason=='VRS' || relieval_reason=='SAN' || relieval_reason=='DTH' || relieval_reason=='MEV' || relieval_reason=='CMR'){
		                		
		                	
		                	
		              	//alert("With in GET DETAILS....");
		                		//alert("*** LEG ID *** "+response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue);
//		                	alert("RESPONSE==="+response);
		                	//alert("GPO_NO==="+response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue);
		                	document.Hrm_GpfSettlementForm.txtGpfNo.value=response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
		                	//alert("GPO_NO==="+response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue);
		                	
		            		document.Hrm_GpfSettlementForm.comEmpId.value=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.txtEmpId.value=response.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.designation.value=response.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.txtDOB.value=response.getElementsByTagName("DOB")[0].firstChild.nodeValue;
		            		var gpf=response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
		            		var emp_name=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
		            		var designation=response.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
		            		
		            		var firstRelieveDate=response.getElementsByTagName("DATE_EFFECTIVE_FROM")[0].firstChild.nodeValue;
		            		
		            	
		            		
		            		
		            		if(relieval_reason=="SAN")
		            		{
		            			document.getElementById("leagl_details").style.display="none";
		            			document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		            			var sub="GENERAL PROVIDENT FUND - Authorisation for the final payment of GPF (Regular) GPF (Impounded) Account in respect of "+emp_name+", "+designation+" -  GPF Account No. "+gpf+"/TWAD - retired on "+firstRelieveDate+" - Final Payment Authorised - Reg.";
		            			//alert("SUB === "+sub);
		            		}
		            		if(relieval_reason=="VRS")
	            			{
		            			document.getElementById("leagl_details").style.display="none";
		            			document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
		            			var sub="GENERAL PROVIDENT FUND - Authorisation for the final payment of GPF (Regular) GPF (Impounded) Account in respect of "+emp_name+", "+designation+" -  GPF Account No. "+gpf+"/TWAD - VRS on "+firstRelieveDate+" - Final Payment Authorised - Reg.";
		            			//alert("SUB === "+sub);
	            			}
		            		
		            		if(relieval_reason=="DTH")
	            			{
		            			document.getElementById("leagl_details").style.display="block";
		            			document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
		            			var sub="GENERAL PROVIDENT FUND - Authorisation for the final payment of GPF (Regular) GPF (Impounded) Account in respect of (Late) "+emp_name+", "+designation+" -  GPF Account No. "+gpf+"/TWAD - Died on "+firstRelieveDate+" - Final Payment Authorised - Reg.";
		            			//alert("SUB === "+sub);
		            		}
		            		
		            		
		            		if(relieval_reason=="MEV")
	            			{
		            			document.getElementById("leagl_details").style.display="none";
		            			document.Hrm_GpfSettlementForm.radRlv[3].checked=true;
		            			var sub="GENERAL PROVIDENT FUND - Authorisation for the final payment of GPF (Regular) GPF (Impounded) Account in respect of "+emp_name+", "+designation+" -  GPF Account No. "+gpf+"/TWAD - MEV on "+firstRelieveDate+" - Final Payment Authorised - Reg.";
		            			//alert("SUB === "+sub);
	            			}
		            		
		            		if(relieval_reason=="CMR")
	            			{
		            			document.getElementById("leagl_details").style.display="none";
		            			document.Hrm_GpfSettlementForm.radRlv[4].checked=true;
		            			var sub="GENERAL PROVIDENT FUND - Authorisation for the final payment of GPF (Regular) GPF (Impounded) Account in respect of "+emp_name+", "+designation+" -  GPF Account No. "+gpf+"/TWAD - CMR on "+firstRelieveDate+" - Final Payment Authorised - Reg.";
		            			//alert("SUB === "+sub);
	            			}
		            		
		            		
		            		
		            		document.Hrm_GpfSettlementForm.date2.value=response.getElementsByTagName("DATE_EFFECTIVE_FROM")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.Regular_Balance.value=response.getElementsByTagName("Balance_Has_On_Date")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.txtImpoundBal.value=response.getElementsByTagName("Imp_Balance_Has_On_Date")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.txtImpound2003bal.value=response.getElementsByTagName("Imp_Balance2003")[0].firstChild.nodeValue;
		            		document.Hrm_GpfSettlementForm.DA_Amt.value=response.getElementsByTagName("DA_AMT")[0].firstChild.nodeValue;
		            		//alert("Subscription_Debit==="+response.getElementsByTagName("Subscription_Debit")[0].firstChild.nodeValue);
		            	
		            		
		            		
		            		
		            		
		            		
		            		document.Hrm_GpfSettlementForm.subject.value=sub;
		            		var copys=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue+"\n";
		            		var off_na=response.getElementsByTagName("office_name")[0].firstChild.nodeValue+"\n";
		            		var town=response.getElementsByTagName("city_town_name")[0].firstChild.nodeValue;
		            		
		            		
	                		 
	                		
		            		
		            		//alert("RELIVAL==="+relieval_reason);
		            		if(relieval_reason=="SAN")
		            			{
		            				document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		            				
		            				if(town!='null'){
		            					document.Hrm_GpfSettlementForm.copy.value=copys+off_na+town;
		            				}
		            				else
		            					{
		            						document.Hrm_GpfSettlementForm.copy.value=copys+off_na;
		            					}
		            			document.getElementById("dth_table").style.display="none";
		            				//document.getElementById('dth_table').style.visibility = "hidden";
		            			}
		            		
		            		if(relieval_reason=="VRS")
		        			{
		        				document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
		        				if(town!='null'){
	            					document.Hrm_GpfSettlementForm.copy.value=copys+off_na+town;
	            				}
	            				else
	            					{
	            						document.Hrm_GpfSettlementForm.copy.value=copys+off_na;
	            					}
		        				document.getElementById("dth_table").style.display="none";
		        				
		        				
		        				//document.Hrm_GpfSettlementForm.copy.value="";
		        				
		        				//document.getElementById('dth_table').style.visibility = "hidden";
		        			}
		            		
		            		
		            		
		            		if(relieval_reason=="MEV")
	            			{
	            				document.Hrm_GpfSettlementForm.radRlv[3].checked=true;
	            				
	            				if(town!='null'){
	            					document.Hrm_GpfSettlementForm.copy.value=copys+off_na+town;
	            				}
	            				else
	            					{
	            						document.Hrm_GpfSettlementForm.copy.value=copys+off_na;
	            					}
	            			document.getElementById("dth_table").style.display="none";
	            				//document.getElementById('dth_table').style.visibility = "hidden";
	            			}
		            		
		            		
		            		if(relieval_reason=="CMR")
	            			{
	            				document.Hrm_GpfSettlementForm.radRlv[4].checked=true;
	            				
	            				if(town!='null'){
	            					document.Hrm_GpfSettlementForm.copy.value=copys+off_na+town;
	            				}
	            				else
	            					{
	            						document.Hrm_GpfSettlementForm.copy.value=copys+off_na;
	            					}
	            			document.getElementById("dth_table").style.display="none";
	            				//document.getElementById('dth_table').style.visibility = "hidden";
	            			}
		            		
		            		
		            		if(relieval_reason=="DTH")
		        			{
		            			//alert("HAI  WITH IN DTH ......");
		            			var copy_array=new Array();
		        				document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
		        				document.getElementById("dth_table").style.display="block";
		        				document.getElementById("legal_buttons").style.display="block";
		        				document.getElementById("leagl_details").style.display="block";
		        				//alert("*** LEG ID *** "+response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue);
		        				var cccounts=response.getElementsByTagName("cccounts")[0].firstChild.nodeValue;
		        				for(var i=0;i<cccounts;i++)
		        					{
		        					//alert("copy to  ====== "+response.getElementsByTagName("COPY_TO")[i].firstChild.nodeValue);
		        					copy_array[i]=response.getElementsByTagName("COPY_TO")[i].firstChild.nodeValue+"\n&&&\n";
		        						
		        					}
		        				document.Hrm_GpfSettlementForm.copy.value=copy_array;
		        				document.Hrm_GpfSettlementForm.count_record.value=response.getElementsByTagName("TOTAL")[0].firstChild.nodeValue;
		        				document.Hrm_GpfSettlementForm.dth_Legal_person_ID.value=response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue;
		        				//var copy=document.getElementById("copy1").value;
		        				//var copies=copy;
		        				//alert("*** LEG ID *** "+response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue);
		        				document.Hrm_GpfSettlementForm.dth_Legal_person_ID.value=response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue;
        						
		        				document.Hrm_GpfSettlementForm.dth_Legal_person_ID.value=response.getElementsByTagName("LEGAL_HIER_ID")[0].firstChild.nodeValue;
		        				
		        				
		        				document.Hrm_GpfSettlementForm.dth_Legal_person.value=response.getElementsByTagName("LEGAL_HEIR_NAME")[0].firstChild.nodeValue;
		        				
		                		document.Hrm_GpfSettlementForm.dth_add1.value=response.getElementsByTagName("LEGAL_HEIR_ADD1")[0].firstChild.nodeValue;
		                		document.Hrm_GpfSettlementForm.dth_add2.value=response.getElementsByTagName("LEGAL_HEIR_ADD2")[0].firstChild.nodeValue;
		                		document.Hrm_GpfSettlementForm.dth_add3.value=response.getElementsByTagName("LEGAL_HEIR_ADD3")[0].firstChild.nodeValue;
		                		document.Hrm_GpfSettlementForm.pin.value=response.getElementsByTagName("LEGAL_HEIR_PIN")[0].firstChild.nodeValue;
		                		document.getElementById("subject").value=response.getElementsByTagName("SUBJECT")[0].firstChild.nodeValue;
		                		
		                	/*	//load month
		                		var listMonthValues='';
		                		
		                	    try {
		                	    	listMonthValues=response.getElementsByTagName("listMonth")[0];
		                	    	listLength=response.getElementsByTagName("listMonth")[0].childNodes.length; 
		                	    	
		                	    	for ( var e = 1; e<document.getElementById("listMonth").options.length; e++) {
		                				document.getElementById("listMonth").remove(e);
		                			} 
		                	    	
		                	    	for ( var u = 1; u <=listLength; u++) {            				
		                				var monthData=listMonthValues.getElementsByTagName("month")[u-1];        				
		                				var monthName=monthData.getElementsByTagName("monthName")[0].firstChild.nodeValue;
		                				var monthValue=monthData.getElementsByTagName("monthValue")[0].firstChild.nodeValue;        				
		                				document.getElementById("listMonth").options[u]=new Option(monthValue,monthName);
		                				if(selectedMonth==monthName){
		                					document.getElementById("listMonth").selectedIndex=u;
		                				}
		                			}
		                	    	
		                		} 
		                	    catch(e)
		                	    {
		                	    }*/
		        			}
		            		
		            		if(relieval_reason=="FINISHED")
		        			{
		        				//alert("This process is Already finished for this GPF_NO");
		        				clearData();
		        			}
		            		
		            		//document.Hrm_GpfSettlementForm.txtGpfNo.value=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
		                	
		                   
		              } 
		                
		              
		               
		                else{
		                	
		                	alert("This GPF_NO does not exist");
		                	document.Hrm_GpfSettlementForm.txtGpfNo.focus();                	
		                	//emptyEmployeeDetails();
		                	//emptyRelievalDetails();
		                	
		                    
		                    
		                }
		                	
		                }
		               call('LoadYear');
		            }
				  
			
			
			
			
			
			else if(command=="LoadYear")
            {
        	  if(flag=='success')
                {   
                	var listYearValues='';
                	var listLength='';
                    try {
                    	listYearValues=response.getElementsByTagName("listYear")[0];                                      
                    	listLength=response.getElementsByTagName("listYear")[0].childNodes.length;
                    	                	                    	
        			} catch (e) {
        				// TODO: handle exception
        				alert(e);
        			}
        			 
        			    			
        		//	document.getElementById("listYear").options[1]=new Option("2013","2013");
        			for ( var u = 1; u <=listLength; u++) {
        				
        				var vall=listYearValues.getElementsByTagName("year")[u-1].firstChild.nodeValue;
        				document.getElementById("listYear").options[u]=new Option(vall,vall);
					} 
        			
        			for ( var e = 1; e<document.getElementById("listYear").options.length; e++) {
        				document.getElementById("listYear").remove(e);
					}  
                }
                else
                {
                    alert("year not loaded");
                 }
            
            }
        	else if(command=="LoadMonth")
            {
        		
                if(flag=='success')
                {   
                	var listMonthValues='';
                	var listLength='';
                    try {
                    	listMonthValues=response.getElementsByTagName("listMonth")[0];
                    	listLength=response.getElementsByTagName("listMonth")[0].childNodes.length; 
                    	
                    	for ( var e = 1; e<document.getElementById("listMonth").options.length; e++) {
            				document.getElementById("listMonth").remove(e);
    					} 
                    	
                    	for ( var u = 1; u <=listLength; u++) {            				
            				var monthData=listMonthValues.getElementsByTagName("month")[u-1];        				
            				var monthName=monthData.getElementsByTagName("monthName")[0].firstChild.nodeValue;
            				var monthValue=monthData.getElementsByTagName("monthValue")[0].firstChild.nodeValue;        				
            				document.getElementById("listMonth").options[u]=new Option(monthValue,monthName);
    					}
                    	
        			} catch (e) {
        				// TODO: handle exception
        				alert(e);
        			}
        			
        			
        			
                }
                else
                {
                    alert("Month not loaded");
                 }
            
        
            
            }
      
            
           if(command=="Check")
            	{
            		//alert("With in response Check");
            		
            		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            		
            			   // alert("With in Count");
            			    
            				
            				
            				if(flag=="exists")
            					{
	            					
	            					
	            						alert("This GPF_NO  Already Exist");
	            						clearData();
            					}
            				else if(flag=="no_partial")
        					{
            					
            					
        						alert("Please complete the Partial Interest Calculation For this GPF_NO Number");
        						clearData();
    					}
            				
            			
            		else 	if(flag=="success")
        					{		
                              
	        					var gpf_no=response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
	        					document.Hrm_GpfSettlementForm.txtGpfNo.value=gpf_no;
	        					calltwo('other_details'); 
	        					
        					}
            		else if(flag=="no_data")
            		{
            			alert("Please Enter a valid GPF_NO Number");
            			clearData();
            		}
            			
            	}
              
             if(command=="add")
            {
            	
    			
    			if(flag=='success')
                {
                	alert("Added Successfully");
                	document.getElementById("legal_buttons").style.display="none";
    				document.getElementById("leagl_details").style.display="none";
                }

    			clearData();
    			
            }
        	  if(command=="update")
		    {
        		       		
    			if(flag=='success' )
	            {
	            	alert("Updated Successfully");
	            	call('Get');	                                   
	            }
	            else
	                alert("Failure in Update");
		    }
        	  if(command=="validate")
		    {
        		
    			if(flag=='success' )
	            {
	            	
    				alert('Validated successfully');
    				call('Get');
                
	            }
	            else
	                alert("Failure in Validate");
		    }
         if(command=="delete")
            {
                if(flag=='success')
                {   
                	alert("Deleted Successfully");      
                	clearData();
                }
                else
                {
                    alert("Not Deleted");
                 }
            
            }
     
        	 if(command=="LoadYear")
            {
        		
                if(flag=='success')
                {   
                	var listYearValues='';
                	var listLength='';
                    try {
                    	listYearValues=response.getElementsByTagName("listYear")[0];
                    	
                    	listLength=response.getElementsByTagName("listYear")[0].childNodes.length;
                    	
        			} catch (e) {
        				// TODO: handle exception
        				alert(e);
        			}
        			for ( var e = 1; e<document.getElementById("listYear").options.length; e++) {
        				document.getElementById("listYear").remove(e);
					}       			
        			
        			for ( var u = 1; u <=listLength; u++) {
        				var vall=listYearValues.getElementsByTagName("year")[u-1].firstChild.nodeValue;
        				document.getElementById("listYear").options[u]=new Option(vall,vall);
					}
        			
        			
                }
                else
                {
                    alert("year not loaded");
                 }
            
            }
        	 if(command=="LoadMonth")
            {
        		
                if(flag=='success')
                {   
                	var listMonthValues='';
                	var listLength='';
                    try {
                    	listMonthValues=response.getElementsByTagName("listMonth")[0];
                    	listLength=response.getElementsByTagName("listMonth")[0].childNodes.length; 
                    	
                    	for ( var e = 1; e<document.getElementById("listMonth").options.length; e++) {
            				document.getElementById("listMonth").remove(e);
    					} 
                    	
                    	for ( var u = 1; u <=listLength; u++) {            				
            				var monthData=listMonthValues.getElementsByTagName("month")[u-1];        				
            				var monthName=monthData.getElementsByTagName("monthName")[0].firstChild.nodeValue;
            				var monthValue=monthData.getElementsByTagName("monthValue")[0].firstChild.nodeValue;        				
            				document.getElementById("listMonth").options[u]=new Option(monthValue,monthName);
    					}
                    	
        			} catch (e) {
        				// TODO: handle exception
        				alert(e);
        			}
        			
        			
        			
                }
                else
                {
                    alert("Month not loaded");
                }
            
            }
			}
			catch (e) {
				// TODO: handle exception
			}
   
        }
    
    }
    }
           




function clearData1()
{
	
	//record_count();
	
	document.getElementById("txtEmpId").value="";
	document.getElementById("comEmpId").value="";
	document.getElementById("designation").value="";
	document.getElementById("txtDOB").value="";
	
	
	document.getElementById("listYear").value="";
	document.getElementById("listMonth").value="";
	
	document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	 relieve_date=document.getElementById("date2").value="";
	
	  /*  Regular_Balance=document.getElementById("Regular_Balance").value="";      
	    txtImpoundBal=document.getElementById("txtImpoundBal").value="";
	    txtImpound2003bal=document.getElementById("txtImpound2003bal").value="";
	    DA_Amt=document.getElementById("DA_Amt").value="";*/
	    
	   
	    
	
	letter_no=document.getElementById("letter_no").value=""; 
	letter_date=document.getElementById("letter_date").value="";   
	
	prefix=document.getElementById("prefix").value="0";    
	pro_officer_name=document.getElementById("pro_officer_name").value="";    
	suffix=document.getElementById("suffix").value="";   
	pro_desingnation=document.getElementById("pro_desingnation").value=""; 
 
	  subject=document.getElementById("subject").value="";   
	    reference=document.getElementById("reference").value="";
	    copy=document.getElementById("copy").value="";
	
	jur_off=document.getElementById("jur_off").value="";   
	jur_add1=document.getElementById("jur_add1").value="";   
	jur_add2=document.getElementById("jur_add2").value=""; 
	jur_add3=document.getElementById("jur_add3").value="";   
	jur_pincode=document.getElementById("jur_pincode").value="";   
	
    for_of=document.getElementById("for_off1").value="";
    for_design1=document.getElementById("for_design1").value="";
    any_comments=document.getElementById("any_comments").value="";
	
	

	document.getElementById("dth_table").style.display="none";
	
	document.Hrm_GpfSettlementForm.dth_Legal_person.value="";
	
	document.Hrm_GpfSettlementForm.dth_date2.value="";
	document.Hrm_GpfSettlementForm.dth_Relation.value="0";
	
	
	document.Hrm_GpfSettlementForm.dth_add1.value="";
	document.Hrm_GpfSettlementForm.dth_add2.value="";
	document.Hrm_GpfSettlementForm.dth_add3.value="";
	document.Hrm_GpfSettlementForm.pin.value="";
	document.Hrm_GpfSettlementForm.percent.value="";
	
	  document.getElementById('legal_buttons').style.display = 'none';
	    document.getElementById('leagl_details').style.display = 'none';
//    
 
     // alert("intrest_upto========="+intrest_upto);
  
   
    

    
  
    
    copy=document.getElementById("copy").value="";

	
	
	emptyEmployeeDetails();
	//emptyRelievalDetails();
	//emptyNoteDetails();
	document.Hrm_GpfSettlementForm.addBtn.disabled=false;
    document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
    document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
    document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
    
  
    
    document.Hrm_GpfSettlementForm.date2.readOnly=false;
    document.Hrm_GpfSettlementForm.imgDate2.onclick=function(){
    	showCalendarControl(document.getElementById('date2'));
    };
    
    document.Hrm_GpfSettlementForm.radRlv[0].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[1].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[2].disabled=false;
    document.Hrm_GpfSettlementForm.listYear.disabled=false;
    document.Hrm_GpfSettlementForm.listMonth.disabled=false;
 
}


function clearData()
{
	//alert("HAI...........");
	//record_count();
	if(document.getElementById("txtGpfNo").value=='0')
	{
			document.getElementById("txtGpfNo").value="";    
	}
	else
		{
			document.getElementById("txtGpfNo").value="";   
		}
	document.getElementById("txtEmpId").value="";
	document.getElementById("comEmpId").value="";
	document.getElementById("designation").value="";
	document.getElementById("txtDOB").value="";
	
	
	document.getElementById("listYear").value="";
	document.getElementById("listMonth").value="";
	
	document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	 relieve_date=document.getElementById("date2").value="";
	
	  /*  Regular_Balance=document.getElementById("Regular_Balance").value="";      
	    txtImpoundBal=document.getElementById("txtImpoundBal").value="";
	    txtImpound2003bal=document.getElementById("txtImpound2003bal").value="";
	    DA_Amt=document.getElementById("DA_Amt").value="";*/
	    
	   
	    
	
	letter_no=document.getElementById("letter_no").value=""; 
	letter_date=document.getElementById("letter_date").value="";   
	
	prefix=document.getElementById("prefix").value="0";    
	pro_officer_name=document.getElementById("pro_officer_name").value="";    
	suffix=document.getElementById("suffix").value="";   
	pro_desingnation=document.getElementById("pro_desingnation").value=""; 
 
	  subject=document.getElementById("subject").value="";   
	    reference=document.getElementById("reference").value="";
	    copy=document.getElementById("copy").value="";
	
	jur_off=document.getElementById("jur_off").value="";   
	jur_add1=document.getElementById("jur_add1").value="";   
	jur_add2=document.getElementById("jur_add2").value=""; 
	jur_add3=document.getElementById("jur_add3").value="";   
	jur_pincode=document.getElementById("jur_pincode").value="";   
	
    for_of=document.getElementById("for_off1").value="";
    for_design1=document.getElementById("for_design1").value="";
    any_comments=document.getElementById("any_comments").value="";
	
	

	document.getElementById("dth_table").style.display="none";
	
	document.Hrm_GpfSettlementForm.dth_Legal_person.value="";
	
	document.Hrm_GpfSettlementForm.dth_date2.value="";
	document.Hrm_GpfSettlementForm.dth_Relation.value="0";
	
	
	document.Hrm_GpfSettlementForm.dth_add1.value="";
	document.Hrm_GpfSettlementForm.dth_add2.value="";
	document.Hrm_GpfSettlementForm.dth_add3.value="";
	document.Hrm_GpfSettlementForm.pin.value="";
	document.Hrm_GpfSettlementForm.percent.value="";
	
	  document.getElementById('legal_buttons').style.display = 'none';
	    document.getElementById('leagl_details').style.display = 'none';
//    
 
     // alert("intrest_upto========="+intrest_upto);
  
   
    

    
  
    
    copy=document.getElementById("copy").value="";

	
	
	emptyEmployeeDetails();
	//emptyRelievalDetails();
	//emptyNoteDetails();
	document.Hrm_GpfSettlementForm.addBtn.disabled=false;
    document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
    document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
    document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
    
  
    
    document.Hrm_GpfSettlementForm.date2.readOnly=false;
    document.Hrm_GpfSettlementForm.imgDate2.onclick=function(){
    	showCalendarControl(document.getElementById('date2'));
    };
    
    document.Hrm_GpfSettlementForm.radRlv[0].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[1].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[2].disabled=false;
    document.Hrm_GpfSettlementForm.listYear.disabled=false;
    document.Hrm_GpfSettlementForm.listMonth.disabled=false;
 
}

function callone(command)
{
	
	//alert("With in Call one function.....")
	xmlhttp=getxmlhttpObject();

	if(xmlhttp==null)
	{
	    alert("Your browser doesnot support AJAX");
	    return;
    }   
	
	 var gpf_no;
     var proc_no;
     var proc_date;
     var relieve_status;
     var relieve_date;
     var int_calc_date;
     var emp_id,prefix,pro_officer_name,suffix;
     var pro_desingnation;
     var letter_no;
     var jur_off,jur_add1,jur_add2,jur_add3,jur_pincode;
     var dth_Legal_person,dth_add1,dth_add2,dth_add3,pin;
     var listYear,listMonth,intrest_upto;
     var subject,reference;
     var Regular_Balance,regu_acc_unit_code=390305,txtImpoundBal,imp_acc_unit_code=391003,txtImpound2003bal,imp_2003=391303,DA_Amt,da_acc_unit=391503;
     var any_comments;
     var for_of,for_design1;
     var copy;
     var dth_Relation;
     var dth_date2;
     
     var url="";
	
      if(command=="Check")
 	{
 		//alert("With in Checking loop....");
 		 gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
 		 //alert("GPF_NO==="+gpf_no);
 		   if(isNaN(gpf_no))
 		   {
 		     alert("Invalid data format.Only numbers are allowed.");
 		    document.Hrm_GpfSettlementForm.txtGpfNo.value="";
 		    // return (false);
 		   }
 		else
 			{
 			   url="../../../../../GPF_Final_Settlement.view?command=Check&gpf_no="+gpf_no;
 			  // alert("URL===="+url);
 			    xmlhttp.open("GET",url,true);
                xmlhttp.onreadystatechange=stateChanged;
                xmlhttp.send(null);
                
 			}
 		 // officedetails1('get');
 	}
	
	
	
}

function calltwo(command)
{
	 if(command=="other_details")
     {
     	 //alert("With in Other Details....");
          	gpf_no=document.getElementById("txtGpfNo").value;  
          	prefix=document.getElementById("prefix").value;
          	pro_officer_name=document.getElementById("pro_officer_name").value;    
     		suffix=document.getElementById("suffix").value;   
     		pro_desingnation=document.getElementById("pro_desingnation").value;
          	
          		url="../../../../../GPF_Final_Settlement.view?command=other_details&gpf_no="+gpf_no;
	               //alert("URL OF OTHER===="+url);
          		xmlhttp.open("GET",url,true);
			        xmlhttp.onreadystatechange=stateChanged1;
			        xmlhttp.send(null);
			        
          		
     }
}

function call(command)
{
	//alert(command);
xmlhttp=getxmlhttpObject();

	if(xmlhttp==null)
	{
	    alert("Your browser doesnot support AJAX");
	    return;
    }   
    
	
        var gpf_no;
        var proc_no;
        var proc_date;
        var relieve_status;
        var relieve_date;
        var int_calc_date;
        var emp_id,prefix,pro_officer_name,suffix;
        var pro_desingnation;
        var letter_no;
        var jur_off,jur_add1,jur_add2,jur_add3,jur_pincode;
        var dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,percent;
        var listYear,listMonth,intrest_upto;
        var subject,reference;
        var Regular_Balance,regu_acc_unit_code=390305,txtImpoundBal,imp_acc_unit_code=391003,txtImpound2003bal,imp_2003=391303,DA_Amt,da_acc_unit=391503;
        var any_comments;
        var for_of,for_design1;
        var copy;
        var dth_Relation;
        var dth_date2;
        var dth_Legal_person_ID;
        
        var url="";
        if(command=="Add")
        { 
                         //alert('With in ADD Function......');
        	//record_count();
        	 var count=document.getElementById("count_record").value;
            
        	
            		gpf_no=document.getElementById("txtGpfNo").value;     
            		emp_id=document.getElementById("txtEmpId").value;
            		prefix=document.getElementById("prefix").value;    
            		pro_officer_name=document.getElementById("pro_officer_name").value;    
            		suffix=document.getElementById("suffix").value;   
            		pro_desingnation=document.getElementById("pro_desingnation").value; 
            		letter_no=document.getElementById("letter_no").value; 
            		letter_date=document.getElementById("letter_date").value;    
            		jur_off=document.getElementById("jur_off").value;   
            		jur_add1=document.getElementById("jur_add1").value;   
            		jur_add2=document.getElementById("jur_add2").value; 
            		jur_add3=document.getElementById("jur_add3").value;   
            		jur_pincode=document.getElementById("jur_pincode").value;   
            		
            		  int_tobe_calc_month= document.getElementById("listMonthvalue").value
            		alert(int_tobe_calc_month);
            		
            		
                    for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
                		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
                			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
                			//alert(relieve_status+"  MMMMMMMMMMMM");
                			if(relieve_status=="DTH")
                	
                			{
                					
                					dth_Legal_person=document.getElementById("dth_Legal_person").value; 
                					dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
                					
                					dth_Relation=document.getElementById("dth_Relation").value;
                					dth_date2=document.getElementById("dth_date2").value;
                					
                					dth_add1=document.getElementById("dth_add1").value; 
                					dth_add2=document.getElementById("dth_add2").value; 
                					dth_add3=document.getElementById("dth_add3").value; 
                					pin=document.getElementById("pin").value; 
                					percent=document.getElementById("percent").value;
                					
                					copy=document.getElementById("copy").value;
                					
                				
                					
                				}
                			if(relieve_status=="SAN")
                				{
//                				alert("With in SuperAN...");
                				 copy=document.getElementById("copy").value;
//                				 alert("COPU================"+copy);
                                 var co=new Array();
                                 co=copy.split("\n");
                                 var na=co[0]+", ";
                                 var of=co[1]+",";
                                 var pl=co[2]+".";
                                 copy=na+of+pl;
//                                 alert("name==="+na);
//                                 alert("off_name==="+of);
//                                 alert("place==="+pl);
                				}
                			if(relieve_status=="VRS")
            				{
//                				alert("With in VRS...");
            				 copy=document.getElementById("copy").value;
//            			
            				}
                			if(relieve_status=="MEV")
            				{
//                				alert("With in VRS...");
            				 copy=document.getElementById("copy").value;
//            			
            				}
                			if(relieve_status=="CMR")
            				{
                				//alert("With in VRS...");
            				 copy=document.getElementById("copy").value;
            				// alert(copy);
//            			
            				}
                		}		
                	}                    
                    relieve_date=document.getElementById("date2").value;
                   
                    
                 
                    
                    subject=document.getElementById("subject").value;   
                    reference=document.getElementById("reference").value;   
                    any_comments=document.getElementById("any_comments").value;
                    // alert('With in ADD Function....ADD.. ADD ==== '+any_comments);
                     for_of=document.getElementById("for_off1").value;
                     //alert('FOR OFF NAME ===' +for_of);
                    // alert('With in ADD Function....ADD.. ADD ==== '+any_comments);
                     for_design1=document.getElementById("for_design1").value;
                    Regular_Balance=document.getElementById("Regular_Balance").value;   
                    
                    txtImpoundBal=document.getElementById("txtImpoundBal").value;
                    txtImpound2003bal=document.getElementById("txtImpound2003bal").value;
                    DA_Amt=document.getElementById("DA_Amt").value;
                    
                    int_tobe_calc_month= document.getElementById("listMonthvalue").value;
                    var flag;
                    var flag2;
				  alert("second"+int_tobe_calc_month);
                    
                    if(relieve_status=="DTH")
    				{
	                    if(count==0)
	                    	{
	                    	//alert("there is no record in leagal table...");
	                    		flag=nullcheck1();
	                    		if(flag==true)
								{
									flag2=nullcheck2();					
								}
						
							if( flag2==true)
								{
								intrest_upto=document.getElementById("listYear").value+"-"+document.getElementById("listMonthvalue").value;
									url="../../../../../GPF_Final_Settlement.view?command=Add&gpf_no="+gpf_no+"&emp_id="+emp_id+"&prefix="+prefix+"&pro_officer_name="+pro_officer_name+"&suffix="+suffix;
									url=url+"&pro_desingnation="+pro_desingnation+"&letter_no="+letter_no+"&letter_date="+letter_date+"&jur_off="+jur_off+"&jur_add1="+jur_add1+"&jur_add2="+jur_add2+"&jur_add3="+jur_add3+"&jur_pincode="+jur_pincode;
									url=url+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&intrest_upto="+intrest_upto+"&subject="+subject+"&reference="+reference+"&copy="+copy;
									url=url+"&Regular_Balance="+Regular_Balance+"&regu_acc_unit_code="+regu_acc_unit_code+"&txtImpoundBal="+txtImpoundBal+"&imp_acc_unit_code="+imp_acc_unit_code+"&txtImpound2003bal="+txtImpound2003bal+"&imp_2003="+imp_2003+"&DA_Amt="+DA_Amt+"&da_acc_unit="+da_acc_unit;
									url=url+"&dth_Legal_person="+dth_Legal_person+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
									url=url+"&dth_Legal_person_ID="+dth_Legal_person_ID;
									url=url+"&any_comments="+any_comments+"&for_of="+for_of+"&for_design1="+for_design1;
									url=url+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
									url=url+"&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&count="+count;
									alert("url1"+url);
			                        xmlhttp.open("GET",url,true);
			                        xmlhttp.onreadystatechange=stateChanged;
			                        xmlhttp.send(null);
			                        							
								}
							else
								{
									
								}
	                    	}
	                    else
	                    	{
	                    	
	                    		var flag2=nullcheck2();
		                    	if( flag2==true)
								{
		                    		intrest_upto=document.getElementById("listYear").value+"-"+document.getElementById("listMonthvalue").value;
									url="../../../../../GPF_Final_Settlement.view?command=Add&gpf_no="+gpf_no+"&emp_id="+emp_id+"&prefix="+prefix+"&pro_officer_name="+pro_officer_name+"&suffix="+suffix;
									url=url+"&pro_desingnation="+pro_desingnation+"&letter_no="+letter_no+"&letter_date="+letter_date+"&jur_off="+jur_off+"&jur_add1="+jur_add1+"&jur_add2="+jur_add2+"&jur_add3="+jur_add3+"&jur_pincode="+jur_pincode;
									url=url+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&intrest_upto="+intrest_upto+"&subject="+subject+"&reference="+reference+"&copy="+copy;
									url=url+"&Regular_Balance="+Regular_Balance+"&regu_acc_unit_code="+regu_acc_unit_code+"&txtImpoundBal="+txtImpoundBal+"&imp_acc_unit_code="+imp_acc_unit_code+"&txtImpound2003bal="+txtImpound2003bal+"&imp_2003="+imp_2003+"&DA_Amt="+DA_Amt+"&da_acc_unit="+da_acc_unit;
									url=url+"&dth_Legal_person="+dth_Legal_person+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
									url=url+"&dth_Legal_person_ID="+dth_Legal_person_ID;
									url=url+"&any_comments="+any_comments+"&for_of="+for_of+"&for_design1="+for_design1;
									url=url+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
									url=url+"&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&count="+count;
									alert("url2"+url);
			                        xmlhttp.open("GET",url,true);
			                        xmlhttp.onreadystatechange=stateChanged;
			                        xmlhttp.send(null);
			                        							
								}
							
	                    	}
    				}
                    
                    if(relieve_status=="VRS" || relieve_status=="SAN" || relieve_status=="MEV" || relieve_status=="CMR")
    				{
                    	
                    	var flag2=nullcheck2();
                    	if( flag2==true)
						{
                    		intrest_upto=document.getElementById("listYear").value.trim()+"-"+document.getElementById("listMonth").value.trim();
                          
                    		url="../../../../../GPF_Final_Settlement.view?command=Add&gpf_no="+gpf_no+"&emp_id="+emp_id+"&prefix="+prefix+"&pro_officer_name="+pro_officer_name+"&suffix="+suffix;
							url=url+"&pro_desingnation="+pro_desingnation+"&letter_no="+letter_no+"&letter_date="+letter_date+"&jur_off="+jur_off+"&jur_add1="+jur_add1+"&jur_add2="+jur_add2+"&jur_add3="+jur_add3+"&jur_pincode="+jur_pincode;
							url=url+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&intrest_upto="+intrest_upto+"&subject="+subject+"&reference="+reference+"&copy="+copy;
							url=url+"&Regular_Balance="+Regular_Balance+"&regu_acc_unit_code="+regu_acc_unit_code+"&txtImpoundBal="+txtImpoundBal+"&imp_acc_unit_code="+imp_acc_unit_code+"&txtImpound2003bal="+txtImpound2003bal+"&imp_2003="+imp_2003+"&DA_Amt="+DA_Amt+"&da_acc_unit="+da_acc_unit;
							url=url+"&dth_Legal_person="+dth_Legal_person+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
							url=url+"&dth_Legal_person_ID="+dth_Legal_person_ID;
							url=url+"&any_comments="+any_comments+"&for_of="+for_of+"&for_design1="+for_design1;
							url=url+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
							url=url+"&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&count="+count;
						alert(url);
	                        xmlhttp.open("GET",url,true);
	                        xmlhttp.onreadystatechange=stateChanged;
	                        xmlhttp.send(null);
						}
							
    				}
					
//									
        }
        
      
        
   if(command=="Update")
    {
                if(nullCheck())
                {
                	gpf_no=document.getElementById("txtGpfNo").value;                    
                    for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
                		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
                			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
                		}		
                    }                    
	                
	                relieve_date=document.getElementById("date2").value;
                    var int_tobe_calc_year="";
                    var int_tobe_calc_month="";                            
                    var finance_year="";
                    try {
                    	var sltlstYr=document.getElementById("listYear");
                    	var sltlstMt=document.getElementById("listMonth");
                    	
                    	int_tobe_calc_year=sltlstYr.options[sltlstYr.selectedIndex].value;
                    	int_tobe_calc_month=sltlstMt.options[sltlstMt.selectedIndex].value;
                    	
                    	
					} catch (e) {
						// TODO: handle exception
						alert(e);
					}
	                
					if(checkAllComboSelected()){
						url="../../../../../GPF_Final_Settlement.view?command=Update&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year+"&proc_no="+proc_no+"&proc_date="+proc_date+"&finance_year="+finance_year;
		                
				        xmlhttp.open("GET",url,true);
				        xmlhttp.onreadystatechange=stateChanged;
				        xmlhttp.send(null);
					}
	                
	                   
                }
    }
     if(command=="Validate")
    {
    	
    	gpf_no=document.getElementById("txtGpfNo").value;
    	for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
    		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
    			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
    		}		
    	}                    
        relieve_date=document.getElementById("date2").value;
        var int_tobe_calc_year="";
        var int_tobe_calc_month="";                            
        
        try {
        	var sltlstYr=document.getElementById("listYear");
        	var sltlstMt=document.getElementById("listMonth");
        	
        	int_tobe_calc_year=sltlstYr.options[sltlstYr.selectedIndex].value;
        	int_tobe_calc_month=sltlstMt.options[sltlstMt.selectedIndex].value;
        	
        	
		} catch (e) {
			// TODO: handle exception
			alert(e);
		}
		
		if(checkAllComboSelected()){
			url="../../../../../GPF_Final_Settlement.view?command=Validate&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
	        
	        xmlhttp.open("GET",url,true);
	        xmlhttp.onreadystatechange=stateChanged;
	        xmlhttp.send(null);
		}
		
        
        
    }  
     if(command=="Get")
    {  
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
           	{
		       /* Previous src --url="../../../../../GPF_Final_Settlement.view?command=get&gpf_no="+gpf_no;        
		        // alert(url);
		        xmlhttp.open("GET",url,true);
		       
		        xmlhttp.onreadystatechange=stateChanged;
		        
		        xmlhttp.send(null);
		       clearData1();*/
           	 url="../../../../../GPF_Final_Settlement.view?command=get&gpf_no="+gpf_no;        
             xmlhttp.open("GET",url,true);
             xmlhttp.onreadystatechange=stateChanged;
             
             xmlhttp.send(null);
        	}
           	
           
      //  copy_to();
        
    }
    
     if(command=="LoadYear"){
    	
    	var relvDate=document.getElementById("date2").value;
        url="../../../../../GPF_Final_Settlement.view?command=LoadYear&relieve_date="+relvDate;
        alert("url"+url);
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
    }
    if(command=="LoadMonth"){
    	
    	var relvDate=document.getElementById("date2").value;
    	var rlvYear=document.getElementById("listYear").value;
    	
        url="../../../../../GPF_Final_Settlement.view?command=LoadMonth&relieve_date="+relvDate+"&listYear="+rlvYear;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
    }
    
   
}
function loadDefault(){
	emptyEmployeeDetails();
	//emptyRelievalDetails();	
	loadDefaultcombo();
	
    document.Hrm_GpfSettlementForm.date2.readOnly=false;    
    
    document.Hrm_GpfSettlementForm.imgDate2.onclick=function(){
    	showCalendarControl(document.getElementById('date2'));
    };
    document.Hrm_GpfSettlementForm.radRlv[0].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[1].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[2].disabled=false;
    
    document.Hrm_GpfSettlementForm.listYear.disabled=false;
    document.Hrm_GpfSettlementForm.listMonth.disabled=false;   
    
    
//	try {    				   
//	    	document.getElementById("spanFinalErrorStatus").firstChild.nodeValue="";
//	    	document.getElementById("spanRelieveDate").firstChild.nodeValue="";
//	    	
//	} catch (e) {
//			document.getElementById("spanFinalErrorStatus").innnerHTML="";
//			document.getElementById("spanRelieveDate").innnerHTML="";
//	}
//	
}
function nullCheck()
{
     
    if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value=="0"))
    {
    alert("Enter GPF no ");
     return 0;
    }
    
    
    
    
    return 1;
}

function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
        
         var c=t.value;
        try{
        
        var f=DateFormat(t,c,event,true,'3');
       
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function checkRelieveDate(t)
{
	
    if(t.value.length==0)
        return false;
    if(!validateRelievalDate(t)){
    	t.value="";
    	alert('relieve date should have last day or date should be in correct format');
    	return false;
    }
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
    	
    	
        // var c=t.value.replace(/-/g,'/');
        
         var c=t.value;
        try{
        	
        var f=DateFormat(t,c,event,true,'3');
        
        }catch(e){
        //exception  start
        	
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            var currDate=new Date();
            try {
            	//alert(currDate.getUTCDate());
			} catch (e) {
				// TODO: handle exception
				alert(e);
			}
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            	
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {	
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
        	
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            	
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}


function datecheck()
{
   if(document.Hrm_GpfSettlementForm.date1.value=="" && document.Hrm_GpfSettlementForm.date1.value.length==0)
   {
     alert('Please enter the Proc  Date');
     document.Hrm_GpfSettlementForm.date1.focus();
     return false;
   }
   
   
   return true;
}


function validateData(){
	var res=true;
	
	
	if(interest_rate_data!='yes'){
		alert('INTEREST RATE not entered for this financial year ');
	}
	
	if((interest_rate_data=='yes')){
		res=true;
	}
	else{
		res=false;
	}
	
	return res;
	
}
function nullCheckRelieveDate(){
	
	
	if(document.getElementById("date2").value=="" && document.getElementById("date2").value.length==0)
	   {
	     
	     document.Hrm_GpfSettlementForm.date2.focus();
	     return false;
	   }
		   
    return true;
}

function checkAllComboSelected(){
	
	if(document.getElementById("listYear").options[0].selected==true){		
		alert("Select Year");
		return false;
	}
	if(document.getElementById("listMonth").options[0].selected==true){
		alert("Select Month");
		return false;
	}
	
	return true;
}

function validateRelievalDate(t){
	
	var boolF=false;
	
	 var sc=t.value.split('/');
     var currenDay =sc[0];
     var currentMonth=sc[1];
     var currentYear=sc[2];
     var relieveStatus='';
     
     for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
 		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
 			relieveStatus=document.Hrm_GpfSettlementForm.radRlv[i].value;
 		}		
     } 
     if((relieveStatus=='DTH')||(relieveStatus=='VRS')){
    	 return true;
     }
     else{
    	 if(checkFinalDate(currenDay,currentMonth,currentYear)){
    		 return true;
    	 }
     }
	//alert('relieve date should have last day or date should be in correct format');
//	boolF=true;
	return boolF;
}

function checkFinalDate(date,month,year){
	var Idate;
	var Imonth;
	var Iyear;
	try {
		 Idate=parseInt(date);
	} catch (e) {
		// TODO: handle exception
		alert(e);
	}
	 Idate=parseInt(date);
	 Imonth=parseInt(month);
	 Iyear=parseInt(year);	
	
	if((month=='01')||(month=='1')){
		if(Idate==31){
			return true;
		}
	}
	else if((month=='02')||(month=='2')){
		if((Iyear/4)==0){
			if(Idate==29){
				return true;
			}
		}
		else{
			if(Idate==28){
				return true;
			}
		}
	}
	else if((month=='03')||(month=='3')){
		if(Idate==31){
			return true;
		}
	}
	else if((month=='04')||(month=='4')){
		if(Idate==30){
			return true;
		}
	}
	else if((month=='05')||(month=='5')){
		if(Idate==31){
			return true;
		}
	}
	else if((month=='06')||(month=='6')){
		if(Idate==30){
			return true;
		}
	}
	else if((month=='07')||(month=='7')){
		if(Idate==31){
			return true;
		}
	}
	else if((month=='08')||(month=='8')){		
		if(Idate==31){
			return true;
		}
	}
	else if((month=='09')||(month=='9')){
		if(Idate==30){
			return true;
		}
	}
	else if((month=='10')||(month=='10')){
		if(Idate==31){
			return true;
		}
	}
	else if((month=='11')||(month=='11')){
		if(Idate==30){
			return true;
		}
	}
	else if((month=='12')||(month=='12')){
		if(Idate==31){
			return true;
		}
	}
	
	
	return false;
}


function Dth_Function()
{
	if(document.Hrm_GpfSettlementForm.radRlv[0].checked==true)
	{
	
		document.getElementById("dth_table").style.display="none";
		
   	}
	if(document.Hrm_GpfSettlementForm.radRlv[1].checked==true)
	{
	
		document.getElementById("dth_table").style.display="none";
	}
	 if(document.Hrm_GpfSettlementForm.radRlv[2].checked==true)
	{
	
		document.getElementById("dth_table").style.display="block";
	}
	else
	{
	
		document.getElementById("dth_table").style.display="none";
	}
	
}


function nullcheck1()
{
	//alert("With in null check");
	var gpf_no=document.getElementById("txtGpfNo").value;
//	if(gpf_no=="" || gpf_no==null)
//	{
//		alert("Please Enter GPF_NO");
//	}
	flag=Number(gpf_no);
	if(flag==false){
		alert("Enter Numeric Value for GPF NO");
		return false;
	}
	
	
	if(document.Hrm_GpfSettlementForm.radRlv[2].checked==true)
		{
	var Legal_person_name=document.getElementById("dth_Legal_person").value;
	
	if(Legal_person_name=="" || Legal_person_name==null)
		{
			alert("Please Enter Leagal Person Name");
			return false;
		}
	
	var relation=document.getElementById("dth_Relation").value;
	if(relation=="0")
		{
			alert("Select The Relation");
			return false;
		}
	
	var Lic_Date=document.getElementById("dth_date2").value;
	if(Lic_Date=="" || Lic_Date==null)
	{
		alert("Please Enter Leagal Person Date");
		return false;
	}
	
	
	
	var add1=document.getElementById("dth_add1").value;
	if(add1=="" || add1==null)
		{
			alert("Enter Legal Person Address1 ");
			return false;
		}
	var add2=document.getElementById("dth_add2").value;
	if(add2=="" || add2==null)
	{
		alert("Enter Legal Person Address2 ");
		return false;
	}
//	var add3=document.getElementById("dth_add3").value;
//	if(add3=="" || add3==null)
//	{
//		alert("Enter Legal Person Address3 ");
//		return false;
//	}
	
//	var pin=document.getElementById("pin").value;
//	if( pin=="" || pin==null)
//	{
//		alert("Pls Enter Six Digit Number");
//		document.Hrm_GpfSettlementForm.pin.value="";
//		return false;
//	}
//	if(pin!="" || pin!="0" || pin!='null')
//		{
//			flag=Pin_Number(pin);
//			if(flag==false)
//			{
//				alert("Enter six digit pin number ");
//				document.Hrm_GpfSettlementForm.pin.value="";
//				return false;
//			}
//		}
	
	var length=document.Hrm_GpfSettlementForm.pin.value.length;
	if(length==0)
		{
		
		}
	//alert("LENGTH==="+length);
	else
		{
			var pin=document.getElementById("pin").value;
			var flag=Pin_Number(pin);
			if(flag==false)
			{
				alert("Enter six digit pin number ");
				document.Hrm_GpfSettlementForm.pin.value="";
				return false;
			}
				if(length!=6 )
				{
					alert("Enter 6 Digits Pin number only");
					document.Hrm_GpfSettlementForm.pin.value="";
					return false;
				}
		}
	
	var percent=document.getElementById("percent").value;
	if(percent=="" || percent==null || percent=='null' || percent=='0')
	{
		alert("Enter Percentage value ");
		return false;
	}
	var length=document.Hrm_GpfSettlementForm.percent.value.length;
	if(length==0)
		{
			alert("Enter Percentage value with in 0 to 100 ");
			return false;
		}
	else
		{
			//var percent=document.Hrm_GpfSettlementForm.percent.value;
			var flag=Pin_Number(percent);
			if(flag==false)
			{
				alert("Percentage Should be between 0 to 100");
				document.Hrm_GpfSettlementForm.percent.value="";
				return false;
			}
			else
				{
					if(percent<0 || percent>100)
						{
							alert("Percentage Should be between 0 to 100");
							document.Hrm_GpfSettlementForm.percent.value="";
							return false;
						}
				}
		}
	
		}
	
	var intrest_year=document.getElementById("listYear").value;
	if(intrest_year=="0")
	{
		alert("Select The Year for Calculated interest");
		return false;
	}

	
	var listMonth=document.getElementById("listMonth").value;
	if(listMonth=="0")
	{
		alert("Select The Month for Calculated interest");
		return false;
	}

	
	
	
	
	
//	gpf_no=document.getElementById("txtGpfNo").value;     
//	emp_id=document.getElementById("txtEmpId").value;
	
//	letter_no=document.getElementById("letter_no").value; 
//	letter_date=document.getElementById("letter_date").value;    
//	jur_off=document.getElementById("jur_off").value;   
//	jur_add1=document.getElementById("jur_add1").value;   
//	jur_add2=document.getElementById("jur_add2").value; 
//	jur_add3=document.getElementById("jur_add3").value;   
//	jur_pincode=document.getElementById("jur_pincode").value;   
	
	
	
	
//    for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
//		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
//			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
//			if(relieve_status=="DTH")
//	
//			{
//					alert("................With in DTH........");
//					dth_Legal_person=document.getElementById("dth_Legal_person").value; 
//					
//					dth_Relation=document.getElementById("dth_Relation").value;
//					dth_date2=document.getElementById("dth_date2").value;
//					
//					dth_add1=document.getElementById("dth_add1").value; 
//					dth_add2=document.getElementById("dth_add2").value; 
//					dth_add3=document.getElementById("dth_add3").value; 
//					pin=document.getElementById("pin").value; 
//					
//				}
//		}		
//	}                    
   // relieve_date=document.getElementById("date2").value;
   
   // listYear=document.getElementById("listYear").value;   
   // listMonth=document.getElementById("listMonth").value;   
    
 
   // intrest_upto=listYear+"-"+listMonth;
   // alert("intrest_upto========="+intrest_upto);
//    var subject=document.getElementById("subject").value;   
//   var  reference=document.getElementById("reference").value;   
//    Regular_Balance=document.getElementById("Regular_Balance").value;   
//    
//    txtImpoundBal=document.getElementById("txtImpoundBal").value;
//    txtImpound2003bal=document.getElementById("txtImpound2003bal").value;
//    DA_Amt=document.getElementById("DA_Amt").value;
    
    
   // any_comments=document.getElementById("any_comments").value;
    
//   var for_of=document.getElementById("for_of").value;
//    var for_design1=document.getElementById("for_design1").value;
//    
//    var copy=document.getElementById("copy").value;
    
   
    
    
    
    
    
    
//    var int_tobe_calc_year="";
//    var int_tobe_calc_month="";  
//    try {
//    	var sltlstYr=document.getElementById("listYear");
//    	var sltlstMt=document.getElementById("listMonth");
//    	
//    	int_tobe_calc_year=sltlstYr.options[sltlstYr.selectedIndex].value;
//    	int_tobe_calc_month=sltlstMt.options[sltlstMt.selectedIndex].value;

	return true;

}


function nullcheck2()
{
	

	
	var letter_no=document.getElementById("letter_no").value; 
	 if(letter_no=="" || letter_no==null)
	{
		alert("Please Enter Authorisation Leter No");
		return false;
	}
//	var letter_date=document.getElementById("letter_date").value; 
//	 if(letter_date=="" || letter_date==null)
//	{
//		alert("Please Enter Authorisation Letter Date Like dd/mm/yyyy");
//		return false;
//	}
//	flag=checkdate(letter_date);
//	if(flag==false)
//		{
//			alert("Enter Correct Date Format Like dd/mm/yyyy");
//			return false;
//		}
	
	
	
	var prefix=document.getElementById("prefix").value;    
	
	if(prefix=="" || prefix==null || prefix==0)
	{
		alert("Please Select Prefix of Presiding Officer Name");
		return false;
	}
	
	var pro_officer_name=document.getElementById("pro_officer_name").value; 
	
	if(pro_officer_name=="" || pro_officer_name==null)
	{
		alert("Please Enter Presiding Officer Name");
		return false;
	}
	
//	flag=Character(pro_officer_name);
//	if(flag==false)
//		{
//			alert("Please Enter Only Characters");
//			return false;
//		}
	
	var suffix=document.getElementById("suffix").value;   
	var pro_desingnation=document.getElementById("pro_desingnation").value; 
	
	if(pro_desingnation=="" || pro_desingnation==null)
	{
		alert("Please Enter Presiding Officer Designation");
		return false;
	}
	
	
	  var subject=document.getElementById("subject").value;   
	  
	  if(subject=="" || subject==null)
		{
			alert("Please Enter Subject");
			return false;
		}
	  
	  
	   var  reference=document.getElementById("reference").value; 
	   
	   if(reference=="" || reference==null)
		{
			alert("Please Enter Reference");
			return false;
		}
	   
	   var copy=document.getElementById("copy").value;
	   
	   if(copy=="" || copy==null)
		{
			alert("Please Enter Copy To");
			return false;
		}
	
	var jur_off=document.getElementById("jur_off").value;   
	
	  if(jur_off=="" || jur_off==null)
		{
			alert("Please Enter Juristiction Officer Designation");
			return false;
		}
	
	var jur_add1=document.getElementById("jur_add1").value;   
	if(jur_add1=="" || jur_add1==null)
	{
		alert("Please Enter Juristiction Officer Address1");
		return false;
	}
	
	var jur_add2=document.getElementById("jur_add2").value; 
	if(jur_add2=="" || jur_add2==null)
	{
		alert("Please Enter Juristiction Officer Address2");
		return false;
	}
//	var jur_add3=document.getElementById("jur_add3").value;   
//	if(jur_add3=="" || jur_add3==null)
//	{
//		alert("Please Enter Juristiction Officer Address3");
//		return false;
//	}
	

//	var pin=document.getElementById("jur_pincode").value;
//	if( pin=="" || pin==null)
//	{
//		alert("Pls Enter Six Digit Number");
//		document.Hrm_GpfSettlementForm.jur_pincode.value="";
//		return false;
//	}
//	flag=Pin_Number(pin);
//	if(flag==false)
//	{
//		alert("Enter six digit pin number ");
//		document.Hrm_GpfSettlementForm.jur_pincode.value="";
//		return false;
//	}
//	var length=document.Hrm_GpfSettlementForm.jur_pincode.value.length;
	//alert("LENGTH==="+length);
//	if(length!=6)
//		{
//			alert("Enter 6 Digits only");
//			document.Hrm_GpfSettlementForm.jur_pincode.value="";
//			return false;
//		}

return true;
}


function Number(gpf_no)
{
	var reg_exp=/^[0-9]/;
	if(!gpf_no.match(reg_exp) || (gpf_no=="" || gpf_no==null) ){
		
		//alert("Enter Numeric Value for GPF NO");
		
		 return false; 
	}
		
       
}


function Pin_Number(pin)
{
	
	var reg_exp=/^[0-9]/;
	
	if(!pin.match(reg_exp)  )
		
	{
			
		//alert("Enter Numeric Value for Pin Code");
		
		return false; 
	}
}


function Character(pro_officer_name)
{
	
	var reg_exp=/[a-zA-z]/;
	if(!pro_officer_name.match(reg_exp) || (pro_officer_name=="" || pro_officer_name==null) )
		{
			return false;
		}
}


function dateValidation()

{
var obj = document.getElementById("letter_date").value;

var day = obj.value.split("/")[0];
var month = obj.value.split("/")[1];

var year = obj.value.split("/")[2];
if ((day<1 || day >31) || (month<1&&month>12)&&(year.length != 4))

{
alert("Invalid Date Format");return false;
}

else

{
var dt = new Date(year, month-1, day);

var today = new Date();
if((dt.getDate() != day) || (dt.getMonth() != month-1) || (dt.getFullYear()!=year) || (dt>today))

{
alert("Invalid Date");return false;

}

}
}




//function checkdate(input){
//	var validformat=/^\d{2}\/\d{2}\/\d{4}$/ //Basic check for format validity
//	var returnval=false
//	if (!validformat.test(input.value))
//	alert("Invalid Date Format. Please correct and submit again.")
//	else{ //Detailed check for valid date ranges
//	var monthfield=input.value.split("/")[0]
//	var dayfield=input.value.split("/")[1]
//	var yearfield=input.value.split("/")[2]
//	var dayobj = new Date(yearfield, monthfield-1, dayfield)
//	if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
//	alert("Invalid Day, Month, or Year range detected. Please correct and submit again.")
//	else
//	returnval=true
//	}
//	if (returnval==false) input.select()
//	return returnval
//	}

function legal()
{
	 var dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,dth_Relation,copy1;
	 dth_Legal_person=document.getElementById("dth_Legal_person").value; 
	//dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	 var copy_to11=dth_Legal_person+",\n";
	 var dth_Relation=document.getElementById("dth_Relation").value;
	 var ena=document.getElementById("comEmpId").value;
	 if(document.getElementById("dth_Relation").value=='0')
		 {
		 
		 }
	 if(document.getElementById("dth_Relation").value=='F')
		 {
		 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
		 }
	 if(document.getElementById("dth_Relation").value=='M')
	 {
	 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='W')
	 {
	 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='H')
	 {
	 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='S')
	 {
	 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='D')
	 {
	 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='G')
	 {
	 	copy_to11+=dth_Relation+"/O, "+ena+",\n";
	 }
	
//	dth_Relation=document.getElementById("dth_Relation").value;
//	
//	if(dth_Relation=='0')
//		{
//			
//			//alert('Select the relation');
//		}
//	else
//		{
//			copy_to11+=dth_Relation+"/O";
//		}
	dth_date2=document.getElementById("dth_date2").value;
	
	dth_add1=document.getElementById("dth_add1").value; 
	copy_to11+=dth_add1+",\n";
	dth_add2=document.getElementById("dth_add2").value; 
	copy_to11+=dth_add2+",\n";
	dth_add3=document.getElementById("dth_add3").value; 
	var leg=document.Hrm_GpfSettlementForm.dth_add3.value.length;
	if(dth_add3=="" || leg==0)
		{
		
		}else{
				copy_to11+=dth_add3+",\n";
		}
	pin=document.getElementById("pin").value; 
	var leng=document.Hrm_GpfSettlementForm.pin.value.length;
	if(pin=="" || leng==0)
		{
		
		}else{
				copy_to11+=pin+".";
		}
	
	//alert("=======COPY TO======"+copy_to11);
	//var copy_to11=dth_Legal_person+"\n"+dth_Relation+"/O";
	document.getElementById("copy").value=copy_to11;
	
	
	// duplicate copy_to.....
	document.getElementById("copy1").value;

	 var copy1=document.getElementById("copy1").value;
	 dth_Legal_person=document.getElementById("dth_Legal_person").value; 
	//dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	  copy1=dth_Legal_person+",\n";
	 var dth_Relation=document.getElementById("dth_Relation").value;
	 var ena=document.getElementById("comEmpId").value;
	 if(document.getElementById("dth_Relation").value=='0')
		 {
		 
		 }
	 if(document.getElementById("dth_Relation").value=='F')
		 {
		 	copy1+=dth_Relation+"/O, "+ena+",\n";
		 }
	 if(document.getElementById("dth_Relation").value=='M')
	 {
	 	copy1+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='W')
	 {
	 	copy1+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='H')
	 {
	 	copy1+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='S')
	 {
	 	copy1+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='D')
	 {
	 	copy1+=dth_Relation+"/O, "+ena+",\n";
	 }
	 if(document.getElementById("dth_Relation").value=='G')
	 {
	 	copy1+=dth_Relation+"/O, "+ena+",\n";
	 }
	
//	dth_Relation=document.getElementById("dth_Relation").value;
//	
//	if(dth_Relation=='0')
//		{
//			
//			//alert('Select the relation');
//		}
//	else
//		{
//			copy1+=dth_Relation+"/O";
//		}
	dth_date2=document.getElementById("dth_date2").value;
	
	dth_add1=document.getElementById("dth_add1").value; 
	copy1+=dth_add1+",\n";
	dth_add2=document.getElementById("dth_add2").value; 
	copy1+=dth_add2+",\n";
	dth_add3=document.getElementById("dth_add3").value; 
	var leg=document.Hrm_GpfSettlementForm.dth_add3.value.length;
	if(dth_add3=="" || leg==0)
		{
		
		}else{
				copy1+=dth_add3+",\n";
		}
	pin=document.getElementById("pin").value; 
	var leng=document.Hrm_GpfSettlementForm.pin.value.length;
	if(pin=="" || leng==0)
		{
		
		}else{
				copy1+=pin+".";
		}
	
	//alert("=======COPY TO======"+copy1);
	//var copy1=dth_Legal_person+"\n"+dth_Relation+"/O";
	document.getElementById("copy1").value=copy1;
	

	
}


function leg_details()
{
	//alert("WITH IN ADD FUNCTION.....");
	
	var dth_Legal_person_ID,dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,dth_Relation,dth_date2,percent,copy_to;
	var gpf_no=document.getElementById("txtGpfNo").value;
	var empid=document.getElementById("txtEmpId").value;
	dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	dth_Legal_person=document.getElementById("dth_Legal_person").value;	
	dth_Relation=document.getElementById("dth_Relation").value; 
	dth_date2=document.getElementById("dth_date2").value;
	dth_add1=document.getElementById("dth_add1").value;
	dth_add2=document.getElementById("dth_add2").value;
	dth_add3=document.getElementById("dth_add3").value;
	if(dth_add3=='null' || dth_add3=='' || dth_add3==null)
	{
		dth_add3="";
	}
	pin=document.getElementById("pin").value;
	
	if(pin=="" || pin == null || pin=='null')
		{
			pin=0;
		}
	percent=document.getElementById("percent").value;
	if(percent=="" || percent=='null' || percent==null)
		{
			percent=0;
		}
	copy_to=document.getElementById("copy").value;
	
	var flag=validate();
	
	if(flag==false)
		{
			
	    }else
		{
	    	  url="../../../../../GPF_Final_Settlement.view?command=legal_details&dth_Legal_person_ID="+dth_Legal_person_ID+"&dth_Legal_person="+dth_Legal_person;
	    	  url+="&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
	    	  url+="&gpf_no="+gpf_no+"&empid="+empid+"&copy_to="+copy_to;
	    	// alert("URL===="+url);
	          xmlhttp.open("GET",url,true);
	          xmlhttp.onreadystatechange=stateChanged22;
	          xmlhttp.send(null);
	         
	          document.getElementById('td1').style.display = 'none';
		      document.getElementById('td2').style.display = 'none';
		}
	// copy_to();
	//record_count();
}


function leg_details_Update()
{

	//alert("WITH IN LEGal UPDATE FUNCTION.....");
	
	var dth_Legal_person_ID,dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,dth_Relation,dth_date2,percent;
	var gpf_no=document.getElementById("txtGpfNo").value;
	var empid=document.getElementById("txtEmpId").value;
	var emp_name=document.getElementById("comEmpId").value;
	dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	dth_Legal_person=document.getElementById("dth_Legal_person").value;	
	dth_Relation=document.getElementById("dth_Relation").value; 
	dth_date2=document.getElementById("dth_date2").value;
	dth_add1=document.getElementById("dth_add1").value;
	dth_add2=document.getElementById("dth_add2").value;
	dth_add3=document.getElementById("dth_add3").value;
	if(dth_add3=='null' || dth_add3=='' || dth_add3==null)
		{
			dth_add3="";
		}
	pin=document.getElementById("pin").value;
	if(pin=="" || pin == null)
		{
			pin=0;
		}
	percent=document.getElementById("percent").value;
	
	var flag=validate();
	    if(flag==false)
		{
			
	    }else
		{
	    	  url="../../../../../GPF_Final_Settlement.view?command=leg_details_Update&dth_Legal_person_ID="+dth_Legal_person_ID+"&dth_Legal_person="+dth_Legal_person;
	    	  url+="&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
	    	  url+="&gpf_no="+gpf_no+"&empid="+empid+"&emp_name="+emp_name;
	          xmlhttp.open("GET",url,true);
	          xmlhttp.onreadystatechange=stateChanged22;
	          xmlhttp.send(null);
	          clearLegal();
	          document.getElementById('td1').style.display = 'none';
	          document.getElementById('td2').style.display = 'none';
	          document.getElementById("legal_add").disabled = false;

		}
	
	 
}

function leg_details_Delete()
{
//alert("WITH IN LEGal DELETE FUNCTION.....");
	
	var dth_Legal_person_ID,dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,dth_Relation,dth_date2;
	var gpf_no=document.getElementById("txtGpfNo").value;
	var empid=document.getElementById("txtEmpId").value;
	dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	dth_Legal_person=document.getElementById("dth_Legal_person").value;	
	dth_Relation=document.getElementById("dth_Relation").value; 
	dth_date2=document.getElementById("dth_date2").value;
	dth_add1=document.getElementById("dth_add1").value;
	dth_add2=document.getElementById("dth_add2").value;
	dth_add3=document.getElementById("dth_add3").value;
	pin=document.getElementById("pin").value;
	if(pin=="" || pin == null)
		{
			pin=0;
		}
	
	var flag=validate1();
	//alert("FALAGH==="+flag);
	if(flag==false)
		{
			
	    }else
		{
	    	  url="../../../../../GPF_Final_Settlement.view?command=leg_details_Delete&dth_Legal_person_ID="+dth_Legal_person_ID+"&dth_Legal_person="+dth_Legal_person;
	    	  url+="&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin;
	    	  url+="&gpf_no="+gpf_no+"&empid="+empid;
	          xmlhttp.open("GET",url,true);
	          xmlhttp.onreadystatechange=stateChanged22;
	          xmlhttp.send(null);
	          clearLegal();
	          document.getElementById('td1').style.display = 'none';
		      document.getElementById('td2').style.display = 'none';
		      document.getElementById("legal_add").disabled = false;
		}
	

}

function validate1()
{
	var dth_Legal_person_ID;
	dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	if(dth_Legal_person_ID=="" || dth_Legal_person_ID==null)
	{
		alert("Deost not fill the Legal Person ID ");
		return false;
	}
	
	return true;
}

function validate()
{
	
	var dth_Legal_person_ID;
	dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
	if(dth_Legal_person_ID=="" || dth_Legal_person_ID==null)
	{
		alert("Deost not fill the Legal Person ID ");
		return false;
	}

	var Legal_person_name=document.getElementById("dth_Legal_person").value;
	
	if(Legal_person_name=="" || Legal_person_name==null)
		{
			alert("Please Enter Leagal Person Name");
			return false;
		}
	
	var relation=document.getElementById("dth_Relation").value;
	if(relation=="0")
		{
			alert("Select The Relation");
			return false;
		}
	
	var Lic_Date=document.getElementById("dth_date2").value;
	if(Lic_Date=="" || Lic_Date==null)
	{
		alert("Please Enter Leagal Person Date");
		return false;
	}
	
	
	
	var add1=document.getElementById("dth_add1").value;
	if(add1=="" || add1==null)
		{
			alert("Enter Legal Person Address1 ");
			return false;
		}
	var add2=document.getElementById("dth_add2").value;
	if(add2=="" || add2==null)
	{
		alert("Enter Legal Person Address2 ");
		return false;
	}
//	var add3=document.getElementById("dth_add3").value;
//	if(add3=="" || add3==null)
//	{
//		alert("Enter Legal Person Address3 ");
//		return false;
//	}
	
//	var pin=document.getElementById("pin").value;
//	if( pin=="" || pin==null)
//	{
//		alert("Pls Enter Six Digit Number");
//		document.Hrm_GpfSettlementForm.pin.value="";
//		return false;
//	}
//	if(pin!="" || pin!="0" || pin!='null')
//		{
//			flag=Pin_Number(pin);
//			if(flag==false)
//			{
//				alert("Enter six digit pin number ");
//				document.Hrm_GpfSettlementForm.pin.value="";
//				return false;
//			}
//		}
	
	
	
	var length=document.Hrm_GpfSettlementForm.pin.value.length;
	if(length==0)
		{
			
		}
	else
		{
			var pin=document.getElementById("pin").value;
			var flag=Pin_Number(pin);
			if(flag==false)
			{
				alert("Enter six digit pin number ");
				document.Hrm_GpfSettlementForm.pin.value="";
				return false;
			}
				if(length!=6 )
				{
					alert("Enter 6 Digits only");
					document.Hrm_GpfSettlementForm.pin.value="";
					return false;
				}
		}
	
	var percent=document.getElementById("percent").value;
	if(percent=="" || percent==null || percent=='null' || percent=='0')
	{
		alert("Enter Percentage value ");
		return false;
	}
	var length=document.Hrm_GpfSettlementForm.percent.value.length;
	if(length==0)
		{
			alert("Enter Percentage value with in 0 to 100 ");
			return false;
		}
	else
		{
			//var percent=document.Hrm_GpfSettlementForm.percent.value;
			var flag=Pin_Number(percent);
			if(flag==false)
			{
				alert("Percentage Should be between 0 to 100");
				document.Hrm_GpfSettlementForm.percent.value="";
				return false;
			}
			else
				{
					if(percent<0 || percent>100)
						{
							alert("Percentage Should be between 0 to 100");
							document.Hrm_GpfSettlementForm.percent.value="";
							return false;
						}
				}
		}
	
	var copy=document.getElementById("copy").value;
	if(copy=="" || copy==null || copy=='null')
		{
			alert("Enter Copy_to Value ");
			return false;
		}
		
	return true;
}

function clearLegal()
{
	//copy_to();
	//record_count();
	document.getElementById("dth_Legal_person").value="";
	document.getElementById("dth_Relation").value="0";
	document.getElementById("dth_date2").value="";
	document.getElementById("dth_add1").value="";
	document.getElementById("dth_add2").value="";
	document.getElementById("dth_add3").value="";
	document.getElementById("pin").value="";
	document.getElementById("percent").value="";
}

function stateChanged22()
{
	//alert(" **** ");
var flag,command,response,status,yearList,monthList;
    
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
    	   try {
    		  // alert(xmlhttp.responseText);
    		   response=xmlhttp.responseXML.getElementsByTagName("response")[0];
    		} catch (e) {
				// TODO: handle exception
			}
			try {
				command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				//alert("COMMAND ===== "+command);
				
			} catch (e) {
			
			}
			try {
				
				flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				
				 if(command=="copy_to")
					{
					//alert("FLAG === ISv ====="+flag);
					  var copy_array=new Array();
						if(flag=="success")
						{
							
							
       				var countss=response.getElementsByTagName("counts")[0].firstChild.nodeValue;
       				//alert("COUNT === "+countss);
       				for(var i=0;i<countss;i++)
       					{
       					//alert("copy to  ====== "+response.getElementsByTagName("COPY_TO")[i].firstChild.nodeValue);
       					copy_array[i]=response.getElementsByTagName("COPY_TO")[i].firstChild.nodeValue+"\n&&&\n";
       						
       					}
       				//alert(copy_array);
       				document.Hrm_GpfSettlementForm.copy.value=copy_array;
							
					
						}
						record_count();
						
					}
				if(command=="record_count")
					{
					//alert("FLAG === "+flag);
						if(flag=="success")
							{
								count_record=response.getElementsByTagName("TOTAL")[0].firstChild.nodeValue;
								document.getElementById("count_record").value=count_record;
								//alert("count_record     <<<<<=====>>>>>"+count_record);
								//return count_record;
								
							}
						if(flag=="failure")
							{
								alert("There is no Record for this gpf No");
							}
					}
				
				  if(command=="legal_details")
		            {
					  if(flag=="failure")
						  {
						  	alert("Record is not Inserted");
						  }
					  if(flag=="success")
						  {
						  	alert("Record is Inserted Successfully");
						  	 clearLegal();
						   	grid111();
						    

						  	
						  }
		            }
				  
				  
				  if(command=="leg_details_Update")
		            {
					  if(flag=="failure")
						  {
						  	alert("Record is not Updatted");
						  }
					  if(flag=="success")
						  {
						  	alert("Record is Updatted Successfully");
						  	
						  	grid111();
						  	
						  	
						  }
		            }
				  
				  if(command=="leg_details_Delete")
		            {
					  if(flag=="failure")
						  {
						  	alert("Record is not Deleted");
						  }
					  if(flag=="success")
						  {
						  	alert("Record is Deleted Successfully");
						  	
						  	grid111();
						  	
						  }
		            }
				  
				  if(command=="legal_ID")
					  {
					  
					  		if(flag=="failure1")
					  			{
					  			
					  			}
					  		if(flag=="success1")
					  			{
					  			clearLegal();
					  			     var legal_ID=response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue;
					  			     document.getElementById("dth_Legal_person_ID").value=legal_ID;
					  			}
					  		copy_TO_FNS();
					  		
					  }
				  
				  if(command=="loads")
				  {
				  		if(flag=="success")
				  			{
			  				var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
				  			
//					  		alert("COUNT ==== "+count);
					
					  				var table=document.getElementById("leagl_details");
					  				 var child=table.childNodes;
					  			    for(var c=child.length-1;c>1;c--)
					  			    {
					  			    	table.removeChild(child[c]);
					  			    }
					  			    
					  				
					  				for(var i=0;i<count;i++)
					  					{
					  					
					  					var LEGAL_HIER_ID=response.getElementsByTagName("LEGAL_HIER_ID")[i].firstChild.nodeValue;
							  			var LEGAL_HEIR_NAME=response.getElementsByTagName("LEGAL_HEIR_NAME")[i].firstChild.nodeValue;
							  			var RELATIONSHIP=response.getElementsByTagName("RELATIONSHIP")[i].firstChild.nodeValue;
							  			var relation;
							  			if(RELATIONSHIP=="F")
							  				{
							  					relation="Father";
							  				}
							  			if(RELATIONSHIP=="M")
						  				{
						  					relation="Mother";
						  				}
							  			if(RELATIONSHIP=="W")
						  				{
						  					relation="Wife";
						  				}
							  			if(RELATIONSHIP=="H")
						  				{
						  					relation="Husband";
						  				}
							  			if(RELATIONSHIP=="S")
						  				{
						  					relation="Son";
						  				}
							  			if(RELATIONSHIP=="D")
						  				{
						  					relation="Daughter";
						  				}
							  			if(RELATIONSHIP=="G")
						  				{
						  					relation="Guardian";
						  				}
							  			var LIC_DATE=response.getElementsByTagName("LIC_DATE")[i].firstChild.nodeValue;
							  			var LEGAL_HEIR_ADD1=response.getElementsByTagName("LEGAL_HEIR_ADD1")[i].firstChild.nodeValue;
							  			var LEGAL_HEIR_ADD2=response.getElementsByTagName("LEGAL_HEIR_ADD2")[i].firstChild.nodeValue;
							  			var LEGAL_HEIR_ADD3=response.getElementsByTagName("LEGAL_HEIR_ADD3")[i].firstChild.nodeValue;
							  			if(LEGAL_HEIR_ADD3=='null')
							  				{
							  					LEGAL_HEIR_ADD3="";
							  				}
							  			var LEGAL_HEIR_PIN=response.getElementsByTagName("LEGAL_HEIR_PIN")[i].firstChild.nodeValue;
							  			if(LEGAL_HEIR_PIN=='0')
							  				{
							  					LEGAL_HEIR_PIN="";
							  				}
							  			var LEGAL_PERCENT=response.getElementsByTagName("LEGAL_PERCENT")[i].firstChild.nodeValue;
							  			
					  						var table1=document.getElementById("leagl_details");
					  						var row=document.createElement("tr");
					  						row.id=i;
					  						
					  						 var tdd=document.createElement("td");
					  			            var y=document.createElement("input");
					  			            y.setAttribute("type", "checkbox");
					  			            y.setAttribute("name", "leg_id");
					  			            y.setAttribute("value", "Y");
					  			            y.setAttribute("onclick", "check_det()");
					  			          tdd.setAttribute("style", "display:none");
					  			            tdd.appendChild(y);					  			           
					  			            row.appendChild(tdd);
					  			            
					  			           
					  			          var tdds=document.createElement("td");
					  			          var yy=document.createElement("a");
					  			            yy.setAttribute("href", "javascript:EDIT_FN('"+i+"')");
					  			            var abc=document.createTextNode("EDIT_BUTTON");
					  			            yy.appendChild(abc);
					  			            tdds.appendChild(yy);
					  			           
					  			            row.appendChild(tdds);
					  						
					  						
					  						var td=document.createElement("td");					  						
					  						var id=document.createTextNode(LEGAL_HIER_ID);
					  			            td.appendChild(id);
					  			        	td.setAttribute("align", "center");
					  			        	row.appendChild(td);
					  			        	
					  			        	var td1=document.createElement("td");					  						
					  						var name=document.createTextNode(LEGAL_HEIR_NAME);
					  			            td1.appendChild(name);
					  			        	td1.setAttribute("align", "center");
					  			        	row.appendChild(td1);
					  			        	
					  			        	
					  			        	var td2=document.createElement("td");
					  			        	//var td3=document.createElement("td");
					  						var relation=document.createTextNode(relation);
					  			            td2.appendChild(relation);
					  			        	td2.setAttribute("align", "center");
					  			        	row.appendChild(td2);
					  			        	
					  			        	
					  			        	
					  			        	
					  			        	
					  			        	var td3=document.createElement("td");					  						
					  						var DATE=document.createTextNode(LIC_DATE);
					  			            td3.appendChild(DATE);
					  			        	td3.setAttribute("align", "center");
					  			        	row.appendChild(td3);
					  						
					  			        	
					  			        	var td4=document.createElement("td");					  						
					  						var add1=document.createTextNode(LEGAL_HEIR_ADD1);
					  			            td4.appendChild(add1);
					  			        	td4.setAttribute("align", "center");
					  			        	row.appendChild(td4);
					  			        	
					  			        	
					  			        	var td5=document.createElement("td");					  						
					  						var add2=document.createTextNode(LEGAL_HEIR_ADD2);
					  			            td5.appendChild(add2);
					  			        	td5.setAttribute("align", "center");
					  			        	row.appendChild(td5);
					  			        	
					  			        	
					  			        	var td6=document.createElement("td");					  						
					  						var add3=document.createTextNode(LEGAL_HEIR_ADD3);
					  			            td6.appendChild(add3);
					  			        	td6.setAttribute("align", "center");
					  			        	row.appendChild(td6);
					  						
					  			        	
					  			        	var td7=document.createElement("td");					  						
					  						var pin=document.createTextNode(LEGAL_HEIR_PIN);
					  			            td7.appendChild(pin);
					  			        	td7.setAttribute("align", "center");
					  			        	row.appendChild(td7);
					  			        	
//How to Hide the column in grid.....					
					  			        	
					  			        	
					  			        	var tds=document.createElement("td");
					  			        	//var td3=document.createElement("td");
					  						var relation=document.createTextNode(RELATIONSHIP);
					  			            tds.appendChild(relation);
					  			        	tds.setAttribute("align", "center");
					  			        	tds.setAttribute("style", "display:none");
					  			        	row.appendChild(tds);  			        	
					  			        	
					  			        	var td8=document.createElement("td");					  						
					  						var percent=document.createTextNode(LEGAL_PERCENT);
					  			            td8.appendChild(percent);
					  			        	td8.setAttribute("align", "center");
					  			        	row.appendChild(td8);
					  			        	
					  			        	table1.appendChild(row);
					  					}
					  			}
				  		if(flag=="failure")
				  			{
				  				
				  			}
				  		leg_details_CLEAR();
				  		//record_count();
				  }
				  
				  if(command=="load")
					  {
					  		if(flag=="success")
					  			{
				  				var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
					  			
//						  		alert("COUNT ==== "+count);
						
						  				var table=document.getElementById("leagl_details");
						  				 var child=table.childNodes;
						  			    for(var c=child.length-1;c>1;c--)
						  			    {
						  			    	table.removeChild(child[c]);
						  			    }
						  			    
						  				
						  				for(var i=0;i<count;i++)
						  					{
						  					
						  					var LEGAL_HIER_ID=response.getElementsByTagName("LEGAL_HIER_ID")[i].firstChild.nodeValue;
								  			var LEGAL_HEIR_NAME=response.getElementsByTagName("LEGAL_HEIR_NAME")[i].firstChild.nodeValue;
								  			var RELATIONSHIP=response.getElementsByTagName("RELATIONSHIP")[i].firstChild.nodeValue;
								  			var relation;
								  			if(RELATIONSHIP=="F")
								  				{
								  					relation="Father";
								  				}
								  			if(RELATIONSHIP=="M")
							  				{
							  					relation="Mother";
							  				}
								  			if(RELATIONSHIP=="W")
							  				{
							  					relation="Wife";
							  				}
								  			if(RELATIONSHIP=="H")
							  				{
							  					relation="Husband";
							  				}
								  			if(RELATIONSHIP=="S")
							  				{
							  					relation="Son";
							  				}
								  			if(RELATIONSHIP=="D")
							  				{
							  					relation="Daughter";
							  				}
								  			if(RELATIONSHIP=="G")
							  				{
							  					relation="Guardian";
							  				}
								  			var LIC_DATE=response.getElementsByTagName("LIC_DATE")[i].firstChild.nodeValue;
								  			var LEGAL_HEIR_ADD1=response.getElementsByTagName("LEGAL_HEIR_ADD1")[i].firstChild.nodeValue;
								  			var LEGAL_HEIR_ADD2=response.getElementsByTagName("LEGAL_HEIR_ADD2")[i].firstChild.nodeValue;
								  			var LEGAL_HEIR_ADD3=response.getElementsByTagName("LEGAL_HEIR_ADD3")[i].firstChild.nodeValue;
								  			var LEGAL_HEIR_PIN=response.getElementsByTagName("LEGAL_HEIR_PIN")[i].firstChild.nodeValue;
								  			var LEGAL_PERCENT=response.getElementsByTagName("LEGAL_PERCENT")[i].firstChild.nodeValue;
								  			
						  						var table1=document.getElementById("leagl_details");
						  						var row=document.createElement("tr");
						  						row.id=i;
						  						
						  						 var tdd=document.createElement("td");
						  			            var y=document.createElement("input");
						  			            y.setAttribute("type", "checkbox");
						  			            y.setAttribute("name", "leg_id");
						  			            y.setAttribute("value", "Y");
						  			            y.setAttribute("onclick", "check_det()");
						  			          tdd.setAttribute("style", "display:none");
						  			            tdd.appendChild(y);					  			           
						  			            row.appendChild(tdd);
						  			            
						  			           
						  			          var tdds=document.createElement("td");
						  			          var yy=document.createElement("a");
						  			            yy.setAttribute("href", "javascript:EDIT_FN('"+i+"')");
						  			            var abc=document.createTextNode("EDIT_BUTTON");
						  			            yy.appendChild(abc);
						  			            tdds.appendChild(yy);
						  			           
						  			            row.appendChild(tdds);
						  						
						  						
						  						var td=document.createElement("td");					  						
						  						var id=document.createTextNode(LEGAL_HIER_ID);
						  			            td.appendChild(id);
						  			        	td.setAttribute("align", "center");
						  			        	row.appendChild(td);
						  			        	
						  			        	var td1=document.createElement("td");					  						
						  						var name=document.createTextNode(LEGAL_HEIR_NAME);
						  			            td1.appendChild(name);
						  			        	td1.setAttribute("align", "center");
						  			        	row.appendChild(td1);
						  			        	
						  			        	
						  			        	var td2=document.createElement("td");
						  			        	//var td3=document.createElement("td");
						  						var relation=document.createTextNode(relation);
						  			            td2.appendChild(relation);
						  			        	td2.setAttribute("align", "center");
						  			        	row.appendChild(td2);
						  			        	
						  			        	
						  			        	
						  			        	
						  			        	
						  			        	var td3=document.createElement("td");					  						
						  						var DATE=document.createTextNode(LIC_DATE);
						  			            td3.appendChild(DATE);
						  			        	td3.setAttribute("align", "center");
						  			        	row.appendChild(td3);
						  						
						  			        	
						  			        	var td4=document.createElement("td");					  						
						  						var add1=document.createTextNode(LEGAL_HEIR_ADD1);
						  			            td4.appendChild(add1);
						  			        	td4.setAttribute("align", "center");
						  			        	row.appendChild(td4);
						  			        	
						  			        	
						  			        	var td5=document.createElement("td");					  						
						  						var add2=document.createTextNode(LEGAL_HEIR_ADD2);
						  			            td5.appendChild(add2);
						  			        	td5.setAttribute("align", "center");
						  			        	row.appendChild(td5);
						  			        	
						  			        	
						  			        	var td6=document.createElement("td");					  						
						  						var add3=document.createTextNode(LEGAL_HEIR_ADD3);
						  			            td6.appendChild(add3);
						  			        	td6.setAttribute("align", "center");
						  			        	row.appendChild(td6);
						  						
						  			        	
						  			        	var td7=document.createElement("td");					  						
						  						var pin=document.createTextNode(LEGAL_HEIR_PIN);
						  			            td7.appendChild(pin);
						  			        	td7.setAttribute("align", "center");
						  			        	row.appendChild(td7);
						  			        	
//How to Hide the column in grid.....					
						  			        	
						  			        	
						  			        	var tds=document.createElement("td");
						  			        	//var td3=document.createElement("td");
						  						var relation=document.createTextNode(RELATIONSHIP);
						  			            tds.appendChild(relation);
						  			        	tds.setAttribute("align", "center");
						  			        	tds.setAttribute("style", "display:none");
						  			        	row.appendChild(tds);  			        	
						  			        	
						  			        	var td8=document.createElement("td");					  						
						  						var percent=document.createTextNode(LEGAL_PERCENT);
						  			            td8.appendChild(percent);
						  			        	td8.setAttribute("align", "center");
						  			        	row.appendChild(td8);
						  			        	
						  			        	table1.appendChild(row);
						  					}
						  			}
					  		if(flag=="failure")
					  			{
					  				
					  			}
					  		officedetails1('get');
					  		call('Get');
					  }
			}
			catch(e)
			{
				
			}
       }
       
    }
}
							// How to hide the column......

//function hide_column(i)
//{
//	//var i=7;
//	alert('with in hide column....'  +i)
//	var t = 'none';
//    var table = document.getElementById('leagl_details');
//    var rows = table.getElementsByTagName('tr');
//    
//    for (var row=0; row<rows.length;row++)
//    {
//    	alert("LENGTH ow === "+rows.length);
//	    var col = rows[row].getElementsByTagName('td')
//	    col[i].style.display=t;
//    }
//}


function grid1()
{
		 var gpf_no=document.getElementById("txtGpfNo").value;
		 url="../../../../../GPF_Final_Settlement.view?command=loading&gpf_no="+gpf_no;
		 xmlhttp.open("GET",url,true);
	     xmlhttp.onreadystatechange=stateChanged22;
	     xmlhttp.send(null);
	     
}

function grid111()
{
		 var gpf_no=document.getElementById("txtGpfNo").value;
		 url="../../../../../GPF_Final_Settlement.view?command=loadings1&gpf_no="+gpf_no;
		 xmlhttp.open("GET",url,true);
	     xmlhttp.onreadystatechange=stateChanged22;
	     xmlhttp.send(null);
	     
}

function f1(j)
{
	
	for(var i=0; i<document.getElementById('leagl_details').rows.length; i++ )
		{
		
		address = document.getElementById('myTable').rows[i].cells[3].childNodes[0].data;
				
		}
	
	
	
	
	var cells=document.getElementById("leagl_details").cells;
	document.getElementById("dth_Legal_person_ID").value=cells.item(1).firstChild.nodeValue;
	
//	dth_Legal_person=document.getElementById("dth_Legal_person").value; 
//	dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
//	
//	dth_Relation=document.getElementById("dth_Relation").value;
//	dth_date2=document.getElementById("dth_date2").value;
//	
//	dth_add1=document.getElementById("dth_add1").value; 
//	dth_add2=document.getElementById("dth_add2").value; 
//	dth_add3=document.getElementById("dth_add3").value; 
//	pin=document.getElementById("pin").value; 
}



function EDIT_FN(i)
{
	//alert("With in Edit function gfgfrgfg....ds =====  "+i);
	document.getElementById('td1').style.display = 'block';
	document.getElementById('td2').style.display = 'block';
	document.getElementById("legal_add").disabled = true;
	//document.Hrm_GpfSettlementForm.td1.style.display="block";
	//document.Hrm_GpfSettlementForm.td2.style.display="block";
	
	
	var element_id=document.getElementById(i);
	//alert("element id  == "+element_id);
    var rcells=element_id.cells;
   // alert("element id  == "+rcells);
     var element_id=rcells.item(2).firstChild.nodeValue;
    // alert("element id  == "+element_id);
     var name=rcells.item(3).firstChild.nodeValue;
     var relation=rcells.item(4).firstChild.nodeValue;
     //alert("relation relation  == "+relation);
     var DOB=rcells.item(5).firstChild.nodeValue;
     //alert("Date of Birth  == "+DOB);
    var add1=rcells.item(6).firstChild.nodeValue;
    //alert("add1  == "+add1);
    var add2=rcells.item(7).firstChild.nodeValue;
   // alert("add2  == "+add2);
    var add3=rcells.item(8).firstChild.nodeValue;
    //alert("add3  == "+add3);
    var pin=rcells.item(9).firstChild.nodeValue;
    
   // alert(" pin  == "+pin);
    var rel=rcells.item(10).firstChild.nodeValue;
   // alert('rel====> '+rel);
    var per=rcells.item(11).firstChild.nodeValue;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    
    document.getElementById("dth_Legal_person").value=name;
    if(rel=="F"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    if(rel=="H"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    if(rel=="M"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    if(rel=="W"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    if(rel=="S"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    if(rel=="D"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    if(rel=="G"){
    	//alert("With in Relation....");
       // document.Hrm_GpfSettlementForm.dth_Relation["F"].checked=true;
        document.getElementById("dth_Relation").value=rel;
    }
    
    document.getElementById("dth_date2").value=DOB;
    document.getElementById("dth_add1").value=add1;
    document.getElementById("dth_add2").value=add2;
    if(add3=='null')
    	{
    	//alert("null "+ add3);
    	var add33="";
    	document.getElementById("dth_add3").value=add33;
    	
    	}
   
    else
    	{
    	//alert("add 3 ====== "+add3);
    	//add3=="";
    	document.getElementById("dth_add3").value=add3;
    	}
    	
    
    if(pin=="0")
	{
		pin="";
	}
    document.getElementById("pin").value=pin;
    if(per=="0")
    	{
    		per=""
    	}
    document.getElementById("percent").value=per;
    
     document.getElementById("dth_Legal_person_ID").value=element_id;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    document.getElementById("dth_Legal_person_ID").value=element_id;
    
    
     document.getElementById("element_id").disabled=true;
     document.getElementById("element_id").value=element_id;
  document.getElementById("element_name").value=element_name;
  document.getElementById("element_short_name").value=element_short_name;
  document.getElementById("element_old_code").value=element_old_code;
  document.getElementById("element_type").value=element_type_id;
  document.getElementById("printOrder").value=printorderseq;
  if(st=="Y"){
          document.Hrm_Pay_elemnts.element_status[0].checked=true;
      }else{
          document.Hrm_Pay_elemnts.element_status[1].checked=true;
      }
  if(app=="a"){
          document.Hrm_Pay_elemnts.applicability[0].checked=true;
      }else{
          document.Hrm_Pay_elemnts.applicability[1].checked=true;
      }

	
}


function leg_details_CLEAR()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=legal_ID&gpf_no="+gpf_no;
	 //alert("URL===="+url);
   xmlhttp.open("GET",url,true);
   xmlhttp.onreadystatechange=stateChanged22;
   xmlhttp.send(null);
	//document.getElementById("dth_Legal_person_ID").value="";    
    document.getElementById("dth_Legal_person").value="";
    document.getElementById("dth_Relation").value="0";
    document.getElementById("dth_date2").value="";
    document.getElementById("dth_add1").value="";
    document.getElementById("dth_add2").value="";
    document.getElementById("dth_add3").value="";
    document.getElementById("pin").value="";
    document.getElementById("percent").value="";
    //call('Get');
    document.getElementById("legal_add").disabled = false;
    document.getElementById('td1').style.display = 'none';
  	document.getElementById('td2').style.display = 'none';
}

function copy_to()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=copy_to&gpf_no="+gpf_no;
	//alert("COPY_TO_URL===="+url);
  xmlhttp.open("GET",url,true);  
  xmlhttp.onreadystatechange=stateChanged22;
  xmlhttp.send(null);
}


function record_count()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=record_count&gpf_no="+gpf_no;
	//alert("COPY_TO_URL===="+url);
 xmlhttp.open("GET",url,true);  
 xmlhttp.onreadystatechange=stateChanged22;
 xmlhttp.send(null);

}


function copy_TO_FNS()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=copy_to&gpf_no="+gpf_no;
	//alert("COPY_TO_URL===="+url);
	 xmlhttp.open("GET",url,true);  
	 xmlhttp.onreadystatechange=stateChanged22;
	 xmlhttp.send(null);

}