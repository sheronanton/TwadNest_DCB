var xmlhttp;
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
       
       document.Hrm_GpfSettlementForm.listYear.onfocus=function(){
    	
    	   var bolXS=validateRelievalDate(document.Hrm_GpfSettlementForm.date2);    	   
    	   if(nullCheckRelieveDate()){
    		   if(bolXS){
    			   try {    				   
      			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="";
      				} catch (e) {
      					document.getElementById("spanRelieveDate").innnerHTML="";
      				}
    			   call('LoadYear');
    			   
    		   }else{
    			   loadDefaultcombo();
    			   try {    				   
    			    	document.getElementById("spanRelieveDate").firstChild.nodeValue=" SAN Date should have last day";
    				} catch (e) {
    					document.getElementById("spanRelieveDate").innnerHTML=" SAN Date should have last day";
    				}
    		   }
    		   
    	   }else{
    		  
    		   loadDefaultcombo();
    		   try {    				   
 			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="Enter Relieve Date";
 				} catch (e) {
 					document.getElementById("spanRelieveDate").innnerHTML="Enter Relieve Date";
 				}
    	   }	
       };
       document.Hrm_GpfSettlementForm.listYear.onchange=function(){
    	   var bolXS=validateRelievalDate(document.Hrm_GpfSettlementForm.date2);    	   
    	   if(nullCheckRelieveDate()){
    		   if(bolXS){
    			   try {    				   
      			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="";
      				} catch (e) {
      					document.getElementById("spanRelieveDate").innnerHTML="";
      				}
    			   call('LoadMonth');
    		   }else{
    			   loadDefaultcombo();
    			   try {    				   
   			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="SAN Date should have last day";
   				} catch (e) {
   					document.getElementById("spanRelieveDate").innnerHTML="SAN Date should have last day";
   				}
    		   }
    		   
    	   }else{
    		   loadDefaultcombo();
    		   try {    				   
			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="Enter Relieve Date";
				} catch (e) {
					document.getElementById("spanRelieveDate").innnerHTML="Enter Relieve Date";
				}
    	   }	
       };
       document.Hrm_GpfSettlementForm.listMonth.onfocus=function(){
    	   var bolXS=validateRelievalDate(document.Hrm_GpfSettlementForm.date2);    	   
    	   if(nullCheckRelieveDate()){  
    		   if(bolXS){
    			   try {    				   
      			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="";
      				} catch (e) {
      					document.getElementById("spanRelieveDate").innnerHTML="";
      				}
    		   }else{
    			   loadDefaultcombo();
    			   try {    				   
   			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="SAN Date should have last day";
   				} catch (e) {
   					document.getElementById("spanRelieveDate").innnerHTML="SAN Date should have last day";
   				}
    		   }
    	   }else{
    		   loadDefaultcombo();
    		   try {    				   
			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="Enter Relieve Date";
				} catch (e) {
					document.getElementById("spanRelieveDate").innnerHTML="Enter Relieve Date";
				}
    	   }	
       };
       /*document.Hrm_GpfSettlementForm.listFinanceYear.onfocus=function(){
    	   var bolXS=validateRelievalDate(document.Hrm_GpfSettlementForm.date2);    	   
    	   if(nullCheckRelieveDate()){
    		   if(bolXS){
    			   try {    				   
      			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="";
      				} catch (e) {
      					document.getElementById("spanRelieveDate").innnerHTML="";
      				}
    			   call('LoadFinanceYear');
    		   }else{
    			   loadDefaultcombo();
    			   try {    				   
   			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="SAN Date should have last day";
   				} catch (e) {
   					document.getElementById("spanRelieveDate").innnerHTML="SAN Date should have last day";
   				}
    		   }
    		   
    	   }else{
    		   loadDefaultcombo();
    		   try {    				   
			    	document.getElementById("spanRelieveDate").firstChild.nodeValue="Enter Relieve Date";
				} catch (e) {
					document.getElementById("spanRelieveDate").innnerHTML="Enter Relieve Date";
				}
    	   }	
       };*/
       document.Hrm_GpfSettlementForm.date2.onchange=function(){
    	   loadDefaultcombo();	
       };
       
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



/*function calculateCommonDetails( response){
	try {
		interest_rate_data=response.getElementsByTagName("interest_rate_data")[0].firstChild.nodeValue;
	} catch (e) {
		// TODO: handle exception
	}	
	try {
		missing_interest_data=response.getElementsByTagName("missing_interest_data")[0].firstChild.nodeValue;
	} catch (e) {
		// TODO: handle exception		
	}
	try {
		count_rows=response.getElementsByTagName("count_rows")[0].firstChild.nodeValue;
	} catch (e) {
		// TODO: handle exception		
	}
	
}*/

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
	var Subscription_Credit=response.getElementsByTagName("Subscription_Credit")[0].firstChild.nodeValue;
	var Subscription_Debit=response.getElementsByTagName("Subscription_Debit")[0].firstChild.nodeValue;
	var Impound_Regular=response.getElementsByTagName("Impound_Regular")[0].firstChild.nodeValue;
	var Imp_Balance_Has_On_Date=response.getElementsByTagName("Imp_Balance_Has_On_Date")[0].firstChild.nodeValue;
	var Balance_Has_On_Date=response.getElementsByTagName("Balance_Has_On_Date")[0].firstChild.nodeValue;
	document.getElementById("txtLastSlipClosingBal").value=Last_Slip_Closing_Balance;
	document.getElementById("txtSubscriptionCredit").value=Subscription_Credit; 
	document.getElementById("txtSubscriptionDebit").value=Subscription_Debit; 
	document.getElementById("txtImpoundBal").value=Impound_Regular;
	document.getElementById("txtImpBalHasOnDate").value=Imp_Balance_Has_On_Date;
    document.getElementById("txtBalHasOnDate").value=Balance_Has_On_Date;
	
}
function fillRelievalDetails(response){
	
	var date2=response.getElementsByTagName("relieve_date")[0].firstChild.nodeValue;
	var radRlv=response.getElementsByTagName("relieve_status")[0].firstChild.nodeValue;
	document.getElementById("date2").value=date2;
	if(radRlv=='SAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		document.getElementById("dth_table").style.display="none";
		document.getElementById("legal_buttons").style.display="none";
		document.getElementById('leagl_details').style.display = 'none';
		
		document.getElementById('td1').style.display = 'none';
		document.getElementById('td2').style.display = 'none';
	}
	else if(radRlv=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
		document.getElementById("dth_table").style.display="none";
		document.getElementById("legal_buttons").style.display="none";
		document.getElementById('leagl_details').style.display = 'none';
		
		document.getElementById('td1').style.display = 'none';
		document.getElementById('td2').style.display = 'none';
	}
	else if(radRlv=='DTH'){
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
	}
	var selectedYear="";
	selectedYear=response.getElementsByTagName("selectedYear")[0].firstChild.nodeValue;
	var selectedMonth="";	
	selectedMonth=response.getElementsByTagName("selectedMonth")[0].firstChild.nodeValue;
	
	//load year
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
		if(selectedYear==vall){
			document.getElementById("listYear").selectedIndex=u;
		}
	}
	
	//load month
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
    	
	} catch (e) {
		// TODO: handle exception
		alert(e);
	}
	
}
function fillFirstRelievalDetails(response){
	var firstRelieveStatus=response.getElementsByTagName("firstRelieveStatus")[0].firstChild.nodeValue;
	var firstRelieveDate=response.getElementsByTagName("firstRelieveDate")[0].firstChild.nodeValue;
	
	if(firstRelieveStatus=='SAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		document.getElementById("dth_table").style.display="none";
		document.getElementById("legal_buttons").style.display="none";
		document.getElementById('leagl_details').style.display = 'none';
		
		document.getElementById('td1').style.display = 'none';
		document.getElementById('td2').style.display = 'none';
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}else if(firstRelieveStatus=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
		document.getElementById("dth_table").style.display="none";
		document.getElementById("legal_buttons").style.display="none";
		document.getElementById('leagl_details').style.display = 'none';
		
		document.getElementById('td1').style.display = 'none';
		document.getElementById('td2').style.display = 'none';
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}else if(firstRelieveStatus=='DTH'){
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}else if(firstRelieveStatus=='ThisMonthSAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		document.getElementById("dth_table").style.display="none";
		document.getElementById("legal_buttons").style.display="none";
		document.getElementById('leagl_details').style.display = 'none';
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
/*function fillNoteDetails(response){
	var authNo=response.getElementsByTagName("auth_no")[0].firstChild.nodeValue;
	var txtMDProc=response.getElementsByTagName("proc_no")[0].firstChild.nodeValue;
	var date1=response.getElementsByTagName("proc_date")[0].firstChild.nodeValue;
	document.getElementById("txtMDProc").value=txtMDProc;
	document.getElementById("date1").value=date1;
	document.getElementById("txtAuthSlNo").value=authNo;
	
	var selectedFinanceYear="";	
	selectedFinanceYear=response.getElementsByTagName("selectedFinanceYear")[0].firstChild.nodeValue;
	
	//load finance year
	
	var listYearValues='';
	var listLength='';
    try {
    	listYearValues=response.getElementsByTagName("listFinanceYear")[0];
    	listLength=response.getElementsByTagName("listFinanceYear")[0].childNodes.length;
    	                    	
	} catch (e) {
		// TODO: handle exception
		alert(e);
	}
	for ( var e = 1; e<document.getElementById("listFinanceYear").options.length; e++) {
		document.getElementById("listFinanceYear").remove(e);
	}       			
	
	for ( var u = 1; u <=listLength; u++) {
		var vall=listYearValues.getElementsByTagName("financeYear")[u-1].firstChild.nodeValue;
		document.getElementById("listFinanceYear").options[u]=new Option(vall,vall);
		if(selectedFinanceYear==vall){
			document.getElementById("listFinanceYear").selectedIndex=u;
		}
	}
}


function fillMasterFormTable(response){
	
	
	
	var loadedMasterForm=response.getElementsByTagName("loadedMasterForm")[0];
	var recordLength=loadedMasterForm.childNodes.length;
	
	for ( var y = 0; y <recordLength; y++) {
		
		var record=loadedMasterForm.getElementsByTagName("record")[y];
		var authSlNo=record.getElementsByTagName("authSlNo")[0].firstChild.nodeValue;
		var rlvReason=record.getElementsByTagName("rlvReason")[0].firstChild.nodeValue;
		var rlvDate=record.getElementsByTagName("rlvDate")[0].firstChild.nodeValue;
		var interestDate=record.getElementsByTagName("interestDate")[0].firstChild.nodeValue;
		var finYear=record.getElementsByTagName("finYear")[0].firstChild.nodeValue;
		var procNo=record.getElementsByTagName("procNo")[0].firstChild.nodeValue;
		var procDate=record.getElementsByTagName("procDate")[0].firstChild.nodeValue;
		var statusId=record.getElementsByTagName("statusId")[0].firstChild.nodeValue;
		var rlvreasonText="";
		if(rlvReason=='R'){
			rlvreasonText='Super-Annuated';
		}
		else if(rlvReason=='V'){
			rlvreasonText='VRS';
		}
		else if(rlvReason=='D'){
			rlvreasonText='Death';
		}
		
		//0
		var tr=document.createElement("TR");
		tr.id=authSlNo;
		var td0=document.createElement("TD");
		
       
        var anc=document.createElement("A");
        var url="javascript:loadDetails('"+authSlNo+"')";
        anc.href=url;
        var edit=document.createTextNode("Select");
        anc.appendChild(edit);
        td0.appendChild(anc);
        tr.appendChild(td0);
        
		
		//1
		var td1=document.createElement("TD");
        var aNo=document.createTextNode(authSlNo);
        td1.appendChild(aNo);
        tr.appendChild(td1);
		
		//2
		var td2=document.createElement("TD");
        var rec2=document.createTextNode(rlvreasonText);
        td2.appendChild(rec2);
        tr.appendChild(td2);     
        
        //3
		var td3=document.createElement("TD");
        var rec3=document.createTextNode(rlvDate);
        td3.appendChild(rec3);
        tr.appendChild(td3);
        //4
		var td4=document.createElement("TD");
        var rec4=document.createTextNode(interestDate);
        td4.appendChild(rec4);
        tr.appendChild(td4);
        //5
		var td5=document.createElement("TD");
        var rec5=document.createTextNode(finYear);
        td5.appendChild(rec5);
        tr.appendChild(td5);
        //6
		var td6=document.createElement("TD");
        var rec6=document.createTextNode(procNo);
        td6.appendChild(rec6);
        tr.appendChild(td6);
        //7
		var td7=document.createElement("TD");
        var rec7=document.createTextNode(procDate);
        td7.appendChild(rec7);
        tr.appendChild(td7);
        
        var tMasterList=document.getElementById("tMasterList");
        tMasterList.appendChild(tr); 
        
	}
	
}*/

/*function loadDetails(authNo){
	
	if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
    {
        alert("Null Value not accepted...Select GPF.No");
        document.Hrm_GpfSettlementForm.txtGpfNo.focus();
        return false;
    }
    var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
    
    loadDefault();
    url="../../../../../GPF_Final_Settlement_Uppdate.view?command=get&gpf_no="+gpf_no+"&auth_no="+authNo;
    
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange=stateChanged;
    
    xmlhttp.send(null);
	
}*/
function emptyEmployeeDetails(){
	document.getElementById("comEmpId").value="";
    document.getElementById("txtDOB").value="";
    document.getElementById("txtGpfNo").value="";
    document.getElementById("designation").value="";
    document.getElementById("txtEmpId").value=""; 
}
function emptyRelievalDetails(){	
	document.getElementById("date2").value="";	
	document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	var count_1=document.getElementById("listYear").options.length;
	for ( var e = 1; e<count_1; e++) {
		document.getElementById("listYear").remove(1);		
	} 
	var count_1=document.getElementById("listMonth").options.length;
	for ( var e = 1; e<count_1; e++) {
			document.getElementById("listMonth").remove(1);			
	}

	document.getElementById("listYear").selectedIndex=0;
	document.getElementById("listMonth").selectedIndex=0;
}
/*function emptyNoteDetails(){
	document.getElementById("txtMDProc").value="";
	document.getElementById("date1").value="";
	document.getElementById("txtAuthSlNo").value="";
	var count_1=document.getElementById("listFinanceYear").options.length;
	for ( var e = 1; e<count_1; e++) {
		document.getElementById("listFinanceYear").remove(1);
	}
	document.getElementById("listFinanceYear").selectedIndex=0;
}*/
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

function loadDefaultcombo(){
	
	try {
		var count_1=document.getElementById("listYear").options.length;
		for ( var e = 1; e<count_1; e++) {
			document.getElementById("listYear").remove(1);			
		} 
		var count_1=document.getElementById("listMonth").options.length;
		for ( var e = 1; e<count_1; e++) {
				document.getElementById("listMonth").remove(1);				
		}
		document.getElementById("listYear").selectedIndex=0;
		document.getElementById("listMonth").selectedIndex=0;	
		
		/*var count_1=document.getElementById("listFinanceYear").options.length;
		for ( var e = 1; e<count_1; e++) {			
			document.getElementById("listFinanceYear").remove(1);			
		}
		document.getElementById("listFinanceYear").selectedIndex=0;*/
	} catch (e) {
		// TODO: handle exception
		
	}
	
}
function stateChangeds()
{
	
    var flag,command,response,status,yearList,monthList;
    
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
    	   try {
    		   response=xmlhttp.responseXML.getElementsByTagName("response")[0];
    		  
    		   
			} catch (e) {
				// TODO: handle exception
			}
			try {
				command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			} catch (e) {
				// TODO: handle exception
			}
			try {
				status=response.getElementsByTagName("status")[0].firstChild.nodeValue;
			} catch (e) {
				// TODO: handle exception
			}
			try {
				flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
			} catch (e) {
				// TODO: handle exception
			}
			try {
				yearList=response.getElementsByTagName("yearList")[0].firstChild.nodeValue;
			} catch (e) {
				// TODO: handle exception
			}try {
				monthList=response.getElementsByTagName("monthList")[0].firstChild.nodeValue;
			} catch (e) {
				// TODO: handle exception
			}
			//calculateCommonDetails( response);
			
            if(command=="Draft")
            {	
                if(flag=='success')
                {	
                	
                		document.Hrm_GpfSettlementForm.txtGpfNo.value=response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
                	
                	
                		document.Hrm_GpfSettlementForm.comEmpId.value=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.txtEmpId.value=response.getElementsByTagName("EMP_ID")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.designation.value=response.getElementsByTagName("desig")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.txtDOB.value=response.getElementsByTagName("DATE_OF_BIRTH")[0].firstChild.nodeValue;
                		
                		
                		var relieval_reason=response.getElementsByTagName("RELIEVAL_REASON_ID")[0].firstChild.nodeValue;
                		
                		
                		
                		
                		document.Hrm_GpfSettlementForm.date2.value=response.getElementsByTagName("RELIEVAL_DATE")[0].firstChild.nodeValue;
                		
                		document.Hrm_GpfSettlementForm.txtLastSlipClosingBal.value=response.getElementsByTagName("GPF_REG_BAL_AMT")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.txtImpoundBal.value=response.getElementsByTagName("GPF_IMP_BAL_AMT")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.txtSubscriptionCredit.value=response.getElementsByTagName("GPF_IMP03_BAL_AMT")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.txtSubscriptionDebit.value=response.getElementsByTagName("GPF_1DA_BAL_AMT")[0].firstChild.nodeValue;
                		
                		
                		var ins=new Array();
                		var intrest_upto=response.getElementsByTagName("INTEREST_UPTO")[0].firstChild.nodeValue;
                		
                		ins =intrest_upto.split("-");
                		var year=ins[0];
                		var month=ins[1];
                		var days=ins[2];
                		
                		
                		if(month==1)
                			{
                				month="JAN";
                			}
                		if(month==2)
            			{
            				month="FEB";
            			}
                		if(month==3)
            			{
            				month="MAR";
            			}
                		if(month==4)
            			{
            				month="APR";
            			}
                		if(month==5)
            			{
            				month="MAY";
            			}
                		if(month==6)
            			{
            				month="JUN";
            			}
                		if(month==7)
            			{
            				month="JUL";
            			}
                		if(month==8)
            			{
            				month="AUG";
            			}
                		if(month==9)
            			{
            				month="SEP";
            			}
                		if(month==10)
            			{
            				month="OCT";
            			}
                		
                		if(month==11)
            			{
            				month="NOV";
            			}
                		if(month==12)
            			{
            				month="DEC";
            			}
                		//alert("days==="+days);
                	//alert("month==="+month);
                		//alert("year==="+year);
                		
                		
                		
                		//var optionss=document.getElementById('listYear');
                		
                		 var length=document.getElementById("listYear").options.length;
                	       //alert("LENGTH===="+length);
                	 	 	var optionss=document.getElementById("listYear");
                	 	 	for(var i=length-1;i>=0;i--)
                	 	 		{
                	 	 			optionss.remove(i);
                	 	 		}
                		
                		
    					var listOpt=document.createElement("option");
    					optionss.length=0;
    					optionss.appendChild(listOpt);
    					listOpt.text="select";
    					listOpt.value="0";
    					
    					
    					for(var i=0; i<1; i++)
    					{
							listOpt=document.createElement("option");
							optionss.appendChild(listOpt);
							listOpt.text=year;
							listOpt.value=year;
						}
                		
    					
    					document.Hrm_GpfSettlementForm.listYear.value=year;
    					
    					//var selectdiv=document.getElementById('listMonth');
    					 var length=document.getElementById("listMonth").options.length;
    					var optionss=document.getElementById("listMonth");
            	 	 	for(var i=length-1;i>=0;i--)
            	 	 		{
            	 	 			optionss.remove(i);
            	 	 		}
            		
    					var listOpt=document.createElement("option");
    					optionss.length=0;
    					optionss.appendChild(listOpt);
    					listOpt.text="select";
    					listOpt.value="0";
    					
    					
    					for(var i=0; i<1; i++)
    					{
							listOpt=document.createElement("option");
							optionss.appendChild(listOpt);
							listOpt.text=month;
							listOpt.value=month;
						}
    					
    					document.Hrm_GpfSettlementForm.listMonth.value=month;
                		
                		document.Hrm_GpfSettlementForm.letter_no.value=response.getElementsByTagName("AUTH_LTR_NO")[0].firstChild.nodeValue;
                		var auth_let_date=response.getElementsByTagName("AUTH_LTR_DATE")[0].firstChild.nodeValue;
                		if(auth_let_date!='null')
                			{
                				document.Hrm_GpfSettlementForm.letter_date.value=auth_let_date;
                			}
                		else{
                			document.Hrm_GpfSettlementForm.letter_date.value="";
                		}
                		document.Hrm_GpfSettlementForm.prefix.value=response.getElementsByTagName("PRESID_OFFICER_PREFIX")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.pro_officer_name.value=response.getElementsByTagName("PRESID_OFFICER_NAME")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.suffix.value=response.getElementsByTagName("PRESID_OFFICER_SUFFIX")[0].firstChild.nodeValue;
                		if(document.Hrm_GpfSettlementForm.suffix.value=='null')
                			{
                				document.Hrm_GpfSettlementForm.suffix.value="";
                			}
                		document.Hrm_GpfSettlementForm.pro_desingnation.value=response.getElementsByTagName("PRESID_OFFICER_DESIG")[0].firstChild.nodeValue;
                		
                		document.Hrm_GpfSettlementForm.subject.value=response.getElementsByTagName("SUBJECT")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.reference.value=response.getElementsByTagName("REFERENCE")[0].firstChild.nodeValue;
                		//document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO")[0].firstChild.nodeValue;
                		
                		document.Hrm_GpfSettlementForm.jur_off.value=response.getElementsByTagName("JURISDICTION_OFFICER_DESIG")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.jur_add1.value=response.getElementsByTagName("JURISDICTION_OFFICE_ADD1")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.jur_add2.value=response.getElementsByTagName("JURISDICTION_OFFICE_ADD2")[0].firstChild.nodeValue;
                		var ju_off=response.getElementsByTagName("JURISDICTION_OFFICE_ADD3")[0].firstChild.nodeValue;
                		if(ju_off!='null'){
                			document.Hrm_GpfSettlementForm.jur_add3.value=ju_off;
                		}
                		else
                			{
                			document.Hrm_GpfSettlementForm.jur_add3.value="";
                			}
                		var ju_pinn=response.getElementsByTagName("JURISDICTION_OFFICE_PIN")[0].firstChild.nodeValue;
                		if(ju_pinn!='0')
                			{
                				document.Hrm_GpfSettlementForm.jur_pincode.value=ju_pinn;
                			}
                		else
                			{
                				document.Hrm_GpfSettlementForm.jur_pincode.value="";
                			}
                		document.Hrm_GpfSettlementForm.for_off1.value=response.getElementsByTagName("FOWARD_OFFICER_NAME")[0].firstChild.nodeValue;
                		if(document.Hrm_GpfSettlementForm.for_off1.value=='null')
                			{
                				document.Hrm_GpfSettlementForm.for_off1.value="";
                			}
                		document.Hrm_GpfSettlementForm.for_design1.value=response.getElementsByTagName("FOWARD_OFFICER_DESIG")[0].firstChild.nodeValue;
                		if(document.Hrm_GpfSettlementForm.for_design1.value=='null')
                			{
                				document.Hrm_GpfSettlementForm.for_design1.value="";
                			}
                		
                		
                		document.Hrm_GpfSettlementForm.any_comments.value=response.getElementsByTagName("ADDL_CONDITIONS")[0].firstChild.nodeValue;
                		if(document.Hrm_GpfSettlementForm.any_comments.value=='null')
                			{
                				document.Hrm_GpfSettlementForm.any_comments.value="";
                			}
                		
                		if(relieval_reason=="SAN")
            			{
            				//document.getElementById("leagl_details").style.display="none";
            				document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
            				
            				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
            				document.getElementById("dth_table").style.display="none";
            				document.getElementById("legal_buttons").style.display="none";
            				document.getElementById('leagl_details').style.display = 'none';
	        				
	        				document.getElementById('td1').style.display = 'none';
	        				document.getElementById('td2').style.display = 'none';
            			}
                	
            		
            		if(relieval_reason=="VRS")
        			{
            			//document.getElementById("leagl_details").style.display="none";
        				document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
        				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
        				document.getElementById("dth_table").style.display="none";
        				document.getElementById("legal_buttons").style.display="none";
        				document.getElementById('leagl_details').style.display = 'none';
        				
        				document.getElementById('td1').style.display = 'none';
        				document.getElementById('td2').style.display = 'none';
        			}
            		//document.getElementById("dth_table").style.display="block";
    			
            		if(relieval_reason=="DTH")
        			{
            			//document.getElementById("leagl_details").style.display="block";
            			var copy_array=new Array();
        				document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
        				leg_details_CLEAR1();
        				document.getElementById("dth_table").style.display="block";
        				document.getElementById("legal_buttons").style.display="block";
        				document.getElementById("leagl_details").style.display="block";
        				var countss=response.getElementsByTagName("countss")[0].firstChild.nodeValue;
        				//alert("COUNT === "+countss);
        				for(var i=0;i<countss;i++)
        					{
        					//alert("copy to  ====== "+response.getElementsByTagName("copy_to")[i].firstChild.nodeValue);
        					copy_array[i]=response.getElementsByTagName("copy_to")[i].firstChild.nodeValue+"\n&&&\n";
        						
        					}
        				//alert(copy_array);
        				document.Hrm_GpfSettlementForm.copy.value=copy_array;
        				//alert(document.Hrm_GpfSettlementForm.copy.value);
        				document.Hrm_GpfSettlementForm.count_record.value=response.getElementsByTagName("TOTAL")[0].firstChild.nodeValue;
        				document.Hrm_GpfSettlementForm.dth_Legal_person_ID.value=response.getElementsByTagName("legal_id")[0].firstChild.nodeValue;
        				document.Hrm_GpfSettlementForm.dth_Legal_person_ID.value="";
        				document.Hrm_GpfSettlementForm.dth_Legal_person.value=response.getElementsByTagName("legal_name")[0].firstChild.nodeValue;
        				document.Hrm_GpfSettlementForm.dth_Legal_person.value="";
        				
        				document.Hrm_GpfSettlementForm.dth_date2.value=response.getElementsByTagName("legal_date")[0].firstChild.nodeValue;
        				document.Hrm_GpfSettlementForm.dth_date2.value="";
        				var relation=response.getElementsByTagName("legal_rel")[0].firstChild.nodeValue;
        				//alert("RELATION===="+relation);
        				document.getElementById("dth_Relation").value=relation;
        				document.getElementById("dth_Relation").value="0";
        				
                		document.Hrm_GpfSettlementForm.dth_add1.value=response.getElementsByTagName("legal_add1")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.dth_add1.value="";
                		document.Hrm_GpfSettlementForm.dth_add2.value=response.getElementsByTagName("legal_add2")[0].firstChild.nodeValue;
                		document.Hrm_GpfSettlementForm.dth_add2.value="";
                		var add3=response.getElementsByTagName("legal_add3")[0].firstChild.nodeValue;
                		
                		if(add3!="null")
                			{
                			//alert("with in true");
                				document.Hrm_GpfSettlementForm.dth_add3.value=add3;
                				document.Hrm_GpfSettlementForm.dth_add3.value="";
                			}
                		else
                			{
                			//alert("with in false");
                				document.Hrm_GpfSettlementForm.dth_add3.value="";
                			}
                		var pinn=response.getElementsByTagName("legal_pin")[0].firstChild.nodeValue;
                		if(pinn!='0' || pinn=="")
                			{
                				document.Hrm_GpfSettlementForm.pin.value=pinn;
                				document.Hrm_GpfSettlementForm.pin.value="";
                			}else
                				{
                					document.Hrm_GpfSettlementForm.pin.value="";
                				}
                		//document.Hrm_GpfSettlementForm.percent.value=response.getElementsByTagName("LEGAL_PERCENT")[0].firstChild.nodeValue;
        			}
            		
            		

            		if(relieval_reason=="MEV")
        			{
        				//document.getElementById("leagl_details").style.display="none";
        				document.Hrm_GpfSettlementForm.radRlv[3].checked=true;
        				
        				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
        				document.getElementById("dth_table").style.display="none";
        				document.getElementById("legal_buttons").style.display="none";
        				document.getElementById('leagl_details').style.display = 'none';
        				
        				document.getElementById('td1').style.display = 'none';
        				document.getElementById('td2').style.display = 'none';
        			}
            		
            		
            		
            		if(relieval_reason=="CMR")
        			{
        				//document.getElementById("leagl_details").style.display="none";
        				document.Hrm_GpfSettlementForm.radRlv[4].checked=true;
        				
        				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
        				document.getElementById("dth_table").style.display="none";
        				document.getElementById("legal_buttons").style.display="none";
        				document.getElementById('leagl_details').style.display = 'none';
        				
        				document.getElementById('td1').style.display = 'none';
        				document.getElementById('td2').style.display = 'none';
        			}
            		
            		
            		
                	
                		if(relieval_reason=="DTH")
            			{
                			var gpf_no=document.getElementById("txtGpfNo").value;
                			 url="../../../../../GPF_Final_Settlement.view?command=legal_ID&gpf_no="+gpf_no;
                			// alert("URL===="+url);
                		     xmlhttp.open("GET",url,true);
                		     xmlhttp.onreadystatechange=stateChanged23;
                		     xmlhttp.send(null);
                			
                			var l_name=response.getElementsByTagName("legal_name")[0].firstChild.nodeValue;
                			var l_rel=response.getElementsByTagName("legal_rel")[0].firstChild.nodeValue;
                			var l_add1=response.getElementsByTagName("legal_add1")[0].firstChild.nodeValue;
                			var l_add2=response.getElementsByTagName("legal_add2")[0].firstChild.nodeValue;
                			var l_add3=response.getElementsByTagName("legal_add3")[0].firstChild.nodeValue;
                			var l_pin=response.getElementsByTagName("legal_pin")[0].firstChild.nodeValue;
                			var e_name=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
                			var l_co=l_name+",\n"+l_rel+"/O "+e_name+",\n"+l_add1+",\n"+l_add2+",\n"+l_add3+",\n"+l_pin;
                			
                			//document.Hrm_GpfSettlementForm.copy.value=l_co;
            			
            			}
                	
                	
                	
                	
                	
                	
                	
                    	document.Hrm_GpfSettlementForm.addBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.updateBtn.disabled=false;
                        document.Hrm_GpfSettlementForm.validateBtn.disabled=false;
                        document.Hrm_GpfSettlementForm.deleteBtn.disabled=false;
                        
                        
                        fillEmployeeDetails(response);
                        fillRelievalDetails(response);
                        fillBalanceDetails(response);
                        
                       
                        document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
                	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
                	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
                	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
                	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
                	    
                	    
                    
                    if(status=='GpfValidated'){
                    	document.Hrm_GpfSettlementForm.addBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
                        
                        
                        fillEmployeeDetails(response);
                        fillRelievalDetails(response);
                        fillBalanceDetails(response);                        
                        makeRelieveDetailsDisabled();
                    	
                       
                        
                    }
                    else if(status=='GpfNotExist'){
                    	document.Hrm_GpfSettlementForm.addBtn.disabled=false;
                        document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
                        
                        fillEmployeeDetails(response)
                        fillFirstRelievalDetails(response);
                        fillBalanceDetails(response);
                    }
                    else if(status=='inValidGpf'){
                    	clearData();
                    	alert("This gpf number does not exist ");
                    	
                    }
                    else if(status=='inValidInteger'){
                    	clearData();
                    	alert("Enter valid Integer ");
                    	
                    }
                    
                }                
                else if(flag=='failure'){
                	
                	document.Hrm_GpfSettlementForm.txtGpfNo.focus();                	
                	emptyEmployeeDetails();
                	emptyRelievalDetails();
                	
                    
                   // alert("Database error");
                }
                
            }
            
            else if(command=="taken_office")
            	{
            		if(flag=="success")
            			{
            			 alert("PDF IS GENERATED.........");
            			}
            		else
            			{
            				alert("OFFICE DOES NOT EXIST....")
            			}
            	}
            
            else if(command=="Check")
        	{
        		//alert("With in response Check");
        		var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
        		if(count!=0)
        			{
        			  
        				if(flag=="success")
        					{  
        					
        						grid1();	
        						
            					        						
        					}
        				
        				
        			}
        		else
        			{
            			
        				//alert("FLAG==="+flag);
        				if(flag=="failure")
    					{		alert("There is no record for this  GPF NO  ");
    							clearData();
        					
    					}
        				
        			}
        	}
            
            else if(command=="add")
            {
            	
    			
    			if(flag=='success')
                {
                	alert("Added Successfully");
                	call('Get');
                }
                else{
                	
                    alert("error in addition");
                }
    			
            }
        	else  if(command=="Update")
		    {
        		       		
    			if(flag=='success' )
	            {
	            	alert("Updated Successfully");
	            	call('Get');	                                   
	            }
	            else
	                alert("Failure in Update");
		    }
        	else  if(command=="validate")
		    {
        		
    			if(flag=='success' )
	            {
	            	
    				alert('Validated successfully');
    				call('Get');
                
	            }
	            else
	                alert("Failure in Validate");
		    }
        	else if(command=="delete")
            {
                if(flag=='success')
                {   
                	alert("Deleted Successfully");      
                	clearData();
                }
                else
                {
                    alert("Record is Not Deleted");
                    
                 }
            
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
        	
   
        }
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
        var txtLastSlipClosingBal,regu_acc_unit_code=390305,txtImpoundBal,imp_acc_unit_code=391003,txtSubscriptionCredit,imp_2003=391303,txtSubscriptionDebit,da_acc_unit=391503;
        var any_comments;
        var for_off1,for_design1;
        var copy;
        var dth_Relation;
        var dth_date2;
        var dth_Legal_person_ID;
        
        var url="";
        if(command=="Update")
        { 
        	//var count=document.getElementById("count_record").value;
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
            		
            		
            		
            		
                    for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
                		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
                			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
                			if(relieve_status=="SAN")
                            	
                			{
                				//grid1();
                				document.getElementById("dth_table").style.display="none";
                				document.getElementById("legal_buttons").style.display="none";
                				document.getElementById('leagl_details').style.display = 'none';
		        				
		        				document.getElementById('td1').style.display = 'none';
		        				document.getElementById('td2').style.display = 'none';
                			}
                			
                			if(relieve_status=="VRS")
                            	
                			{
                				document.getElementById("dth_table").style.display="none";
                				document.getElementById("legal_buttons").style.display="none";
                				document.getElementById('leagl_details').style.display = 'none';
		        				
		        				document.getElementById('td1').style.display = 'none';
		        				document.getElementById('td2').style.display = 'none';
                			}
                			if(relieve_status=="DTH")
                	
                			{
                					//alert("With in DTH........");
                				    dth_Legal_person_ID=document.getElementById("dth_Legal_person_ID").value;
                					dth_Legal_person=document.getElementById("dth_Legal_person").value; 
                					dth_Relation=document.getElementById("dth_Relation").value;
                					dth_date2=document.getElementById("dth_date2").value;
                					dth_add1=document.getElementById("dth_add1").value; 
                					dth_add2=document.getElementById("dth_add2").value; 
                					dth_add3=document.getElementById("dth_add3").value; 
                					pin=document.getElementById("pin").value;
                					//alert("PIN == "+pin);
                					if(pin!="")
                						{
                							pin=document.getElementById("pin").value; 
                							//alert("PIN == " +pin);
                						}
                					else
                						{
                							pin=0;
                						//	alert("PIN else == " +pin);
                						}
                					percent=document.getElementById("percent").value;
                					if(percent!="")
            						{
                						percent=document.getElementById("percent").value; 
            							//alert("PIN == " +pin);
            						}
            					else
            						{
            						percent=0;
            						//	alert("PIN else == " +pin);
            						}
                					
                					
                				}
                			
                			if(relieve_status=="MEV")
                            	
                			{
                				//grid1();
                				document.getElementById("dth_table").style.display="none";
                				document.getElementById("legal_buttons").style.display="none";
                				document.getElementById('leagl_details').style.display = 'none';
		        				
		        				document.getElementById('td1').style.display = 'none';
		        				document.getElementById('td2').style.display = 'none';
                			}
                			
                			
                			if(relieve_status=="CMR")
                            	
                			{
                				//grid1();
                				document.getElementById("dth_table").style.display="none";
                				document.getElementById("legal_buttons").style.display="none";
                				document.getElementById('leagl_details').style.display = 'none';
		        				
		        				document.getElementById('td1').style.display = 'none';
		        				document.getElementById('td2').style.display = 'none';
                			}
                			
                			
                			
                			
                		}		
                	}                    
                    relieve_date=document.getElementById("date2").value;
                   
                    listYear=document.getElementById("listYear").value;   
                    listMonth=document.getElementById("listMonth").value;   
                    
                 
                    intrest_upto=listYear+"-"+listMonth;
                   // alert("intrest_upto========="+intrest_upto);
                    subject=document.getElementById("subject").value;   
                    reference=document.getElementById("reference").value;   
                    txtLastSlipClosingBal=document.getElementById("txtLastSlipClosingBal").value;   
                    
                    txtImpoundBal=document.getElementById("txtImpoundBal").value;
                    txtSubscriptionCredit=document.getElementById("txtSubscriptionCredit").value;
                    txtSubscriptionDebit=document.getElementById("txtSubscriptionDebit").value;
                    
                    
                    any_comments=document.getElementById("any_comments").value;
                    
                    for_off1=document.getElementById("for_off1").value;
                    for_design1=document.getElementById("for_design1").value;
                    
                    copy=document.getElementById("copy").value;
                    
                    
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
					
					//var flag=nullcheck1();
				//alert(flag);
					
						//if(flag==true)
							//{
								var flag2=nullcheck2();		
								//alert(flag2);
							//}
					
					if( flag2==true)
						{
					
						url="../../../../../GPF_Final_Settlement_Uppdate.view?command=Update&gpf_no="+gpf_no+"&emp_id="+emp_id+"&prefix="+prefix+"&pro_officer_name="+pro_officer_name+"&suffix="+suffix;
						url=url+"&pro_desingnation="+pro_desingnation+"&letter_no="+letter_no+"&letter_date="+letter_date+"&jur_off="+jur_off+"&jur_add1="+jur_add1+"&jur_add2="+jur_add2+"&jur_add3="+jur_add3+"&jur_pincode="+jur_pincode;
						url=url+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&intrest_upto="+intrest_upto+"&subject="+subject+"&reference="+reference;
						url=url+"&txtLastSlipClosingBal="+txtLastSlipClosingBal+"&regu_acc_unit_code="+regu_acc_unit_code+"&txtImpoundBal="+txtImpoundBal+"&imp_acc_unit_code="+imp_acc_unit_code+"&txtSubscriptionCredit="+txtSubscriptionCredit+"&imp_2003="+imp_2003+"&txtSubscriptionDebit="+txtSubscriptionDebit+"&da_acc_unit="+da_acc_unit;
						url=url+"&dth_Legal_person_ID="+dth_Legal_person_ID;
						url=url+"&dth_Legal_person="+dth_Legal_person+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin;
						url=url+"&any_comments="+any_comments+"&for_off1="+for_off1+"&for_design1="+for_design1+"&copy="+copy;
						url=url+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
						url=url+"&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&pin="+pin+"&percent="+percent;
                        xmlhttp.open("GET",url,true);
                     // alert(" URL IS ======  "+url);
                        xmlhttp.onreadystatechange=stateChangeds;
                        xmlhttp.send(null);
                        clearData();
                        
						}
					 //document.getElementById('legal_buttons').style.display = 'none';
					// document.getElementById('leagl_details').style.display = 'none';
        }
        
        if(command=="report")
        	{
        	
        	//alert("With in Report..............");
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
    		
    		
    		
    		
            for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
        		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
        			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
        			if(relieve_status=="DTH")
        	
        			{
        					//alert("................With in DTH........");
        					dth_Legal_person=document.getElementById("dth_Legal_person").value; 
        					dth_add1=document.getElementById("dth_add1").value; 
        					dth_add2=document.getElementById("dth_add2").value; 
        					dth_add3=document.getElementById("dth_add3").value; 
        					pin=document.getElementById("pin").value; 
        					
        				}
        		}		
        	}                    
            relieve_date=document.getElementById("date2").value;
           
            listYear=document.getElementById("listYear").value;   
            listMonth=document.getElementById("listMonth").value;   
            
         
            intrest_upto=listYear+"-"+listMonth;
           // alert("intrest_upto========="+intrest_upto);
            subject=document.getElementById("subject").value;   
            reference=document.getElementById("reference").value;   
            txtLastSlipClosingBal=document.getElementById("txtLastSlipClosingBal").value;   
            
            txtImpoundBal=document.getElementById("txtImpoundBal").value;
            txtSubscriptionCredit=document.getElementById("txtSubscriptionCredit").value;
            txtSubscriptionDebit=document.getElementById("txtSubscriptionDebit").value;
            
            
            any_comments=document.getElementById("any_comments").value;
            
            for_off1=document.getElementById("for_off1").value;
            for_design1=document.getElementById("for_design1").value;
            
            copy=document.getElementById("copy").value;
            
            
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
			
			//alert("dth_Legal_person...."+dth_Legal_person);
			
				url="../../../../../GPF_Final_Settlement_Uppdate.view?command=report&gpf_no="+gpf_no+"&emp_id="+emp_id+"&prefix="+prefix+"&pro_officer_name="+pro_officer_name+"&suffix="+suffix;
				url=url+"&pro_desingnation="+pro_desingnation+"&letter_no="+letter_no+"&letter_date="+letter_date+"&jur_off="+jur_off+"&jur_add1="+jur_add1+"&jur_add2="+jur_add2+"&jur_add3="+jur_add3+"&jur_pincode="+jur_pincode;
				url=url+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&intrest_upto="+intrest_upto+"&subject="+subject+"&reference="+reference;
				url=url+"&txtLastSlipClosingBal="+txtLastSlipClosingBal+"&regu_acc_unit_code="+regu_acc_unit_code+"&txtImpoundBal="+txtImpoundBal+"&imp_acc_unit_code="+imp_acc_unit_code+"&txtSubscriptionCredit="+txtSubscriptionCredit+"&imp_2003="+imp_2003+"&txtSubscriptionDebit="+txtSubscriptionDebit+"&da_acc_unit="+da_acc_unit;
				url=url+"&dth_Legal_person="+dth_Legal_person+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin;
				url=url+"&any_comments="+any_comments+"&for_off1="+for_off1+"&for_design1="+for_design1+"&copy="+copy;
				url=url+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
				//alert("URL*****((((((())))))))*****============>"+url);
                xmlhttp.open("POST",url,true);
                xmlhttp.onreadystatechange=stateChangeds;
                xmlhttp.send(null);
        	}
        
        
    else if(command=="Draft")
    {
                
                	gpf_no=document.getElementById("txtGpfNo").value;  
                	//alert(gpf_no);
//                    for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
//                		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
//                			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
//                		}		
//                    }                    
//	                
//	                relieve_date=document.getElementById("date2").value;
//                    var int_tobe_calc_year="";
//                    var int_tobe_calc_month="";                            
//                    var finance_year="";
//                    try {
//                    	var sltlstYr=document.getElementById("listYear");
//                    	var sltlstMt=document.getElementById("listMonth");
//                    	
//                    	int_tobe_calc_year=sltlstYr.options[sltlstYr.selectedIndex].value;
//                    	int_tobe_calc_month=sltlstMt.options[sltlstMt.selectedIndex].value;
//                    	
//                    	
//					} catch (e) {
//						// TODO: handle exception
//						alert(e);
//					}
	                
//					if(checkAllComboSelected()){
						url="../../../../../GPF_Final_Settlement_Uppdate.view?command=Draft&gpf_no="+gpf_no;
		         // alert("URL       ==    "+url);
				        xmlhttp.open("GET",url,true);
				        xmlhttp.onreadystatechange=stateChangeds;
				        xmlhttp.send(null);
				        clearData();
				        leg_details_CLEAR1();
				
//    }
	                   
                
    }
    else if(command=="Validate")
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
			url="../../../../../GPF_Final_Settlement_Uppdate.view?command=Validate&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
	        
	        xmlhttp.open("GET",url,true);
	        xmlhttp.onreadystatechange=stateChangeds;
	        xmlhttp.send(null);
		}
		
        
        
    }  
    else if(command=="Get")
    {  
    	
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0) ||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==null))
        {
          //  alert("Null Value not accepted...Select GPF.No");
            document.Hrm_GpfSettlementForm.txtGpfNo.focus();
            return false;
        }
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        
        loadDefault();
        url="../../../../../GPF_Final_Settlement_Uppdate.view?command=get&gpf_no="+gpf_no;        
        
        xmlhttp.open("GET",url,true);
       
        xmlhttp.onreadystatechange=stateChangeds;
        
        xmlhttp.send(null);
       
        
    }
    else if(command=="Delete")
    {
    	gpf_no=document.getElementById("txtGpfNo").value;  
        
        if((gpf_no=="")||(gpf_no.length==0) || (gpf_no==null))
            {
                alert("Please Enter GPF No");
                document.Hrm_GpfSettlementForm.txtGpfNo.focus();
                
            }
       
        url="../../../../../GPF_Final_Settlement_Uppdate.view?command=Delete&gpf_no="+gpf_no;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChangeds;
        xmlhttp.send(null); 
    }    
    else if(command=="LoadYear"){
    	
    	var relvDate=document.getElementById("date2").value;
        url="../../../../../GPF_Final_Settlement_Uppdate.view?command=LoadYear&relieve_date="+relvDate;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChangeds;
        xmlhttp.send(null);
        
    }
    else if(command=="LoadMonth"){
    	
    	var relvDate=document.getElementById("date2").value;
    	var rlvYear=document.getElementById("listYear").value;
    	
        url="../../../../../GPF_Final_Settlement_Uppdate.view?command=LoadMonth&relieve_date="+relvDate+"&listYear="+rlvYear;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChangeds;
        xmlhttp.send(null);
    }
    
   
}
function loadDefault(){
	emptyEmployeeDetails();
	emptyRelievalDetails();	
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

//function checkAllComboSelected(){
//	
//	if(document.getElementById("listYear").options[0].selected==true){		
//		alert("Select Year");
//		return false;
//	}
//	if(document.getElementById("listMonth").options[0].selected==true){
//		alert("Select Month");
//		return false;
//	}
//	
//	return true;
//}

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


function clearData()
{
	
	
	
	document.getElementById("txtGpfNo").value="";     
	document.getElementById("txtEmpId").value="";
	prefix=document.getElementById("prefix").value="0";    
	pro_officer_name=document.getElementById("pro_officer_name").value="";    
	suffix=document.getElementById("suffix").value="";   
	pro_desingnation=document.getElementById("pro_desingnation").value=""; 
	letter_no=document.getElementById("letter_no").value=""; 
	letter_date=document.getElementById("letter_date").value="";    
	jur_off=document.getElementById("jur_off").value="";   
	jur_add1=document.getElementById("jur_add1").value="";   
	jur_add2=document.getElementById("jur_add2").value=""; 
	jur_add3=document.getElementById("jur_add3").value="";   
	jur_pincode=document.getElementById("jur_pincode").value="";   
	document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	
	
	document.getElementById("dth_table").style.display="none";
	//document.getElementById("dth_table").style.display="none";
	document.getElementById("legal_buttons").style.display="none";
	document.getElementById('leagl_details').style.display = 'none';
	
	document.Hrm_GpfSettlementForm.dth_Legal_person.value="";
	
	document.Hrm_GpfSettlementForm.dth_date2.value="";
	document.Hrm_GpfSettlementForm.dth_Relation.value="0";
	
	
	document.Hrm_GpfSettlementForm.dth_add1.value="";
	document.Hrm_GpfSettlementForm.dth_add2.value="";
	document.Hrm_GpfSettlementForm.dth_add3.value="";
	document.Hrm_GpfSettlementForm.pin.value="";
	document.Hrm_GpfSettlementForm.percent.value="";
	
//    for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
//		if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
//			relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
//			if(relieve_status=="DTH")
//	
//			{
//					//alert("................With in DTH........");
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
    relieve_date=document.getElementById("date2").value="";
    
    document.Hrm_GpfSettlementForm.listYear.selectedIndex=0;
    document.Hrm_GpfSettlementForm.listMonth.selectedIndex=0;
    
    
//    listYear=document.getElementById("listYear").value="0";   
//    listMonth=document.getElementById("listMonth").value="0";   
//    
// 
//    intrest_upto=listYear+"-"+listMonth;
   // alert("intrest_upto========="+intrest_upto);
    subject=document.getElementById("subject").value="";   
    reference=document.getElementById("reference").value="";   
    txtLastSlipClosingBal=document.getElementById("txtLastSlipClosingBal").value="";   
    
    txtImpoundBal=document.getElementById("txtImpoundBal").value="";
    txtSubscriptionCredit=document.getElementById("txtSubscriptionCredit").value="";
    txtSubscriptionDebit=document.getElementById("txtSubscriptionDebit").value="";
    
    
    any_comments=document.getElementById("any_comments").value="";
    
    for_off1=document.getElementById("for_off1").value="";
    for_design1=document.getElementById("for_design1").value="";
    
    copy=document.getElementById("copy").value="";
    
    
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
	
	
	
	
	
	
	emptyEmployeeDetails();
	emptyRelievalDetails();
	//emptyNoteDetails();
	document.Hrm_GpfSettlementForm.addBtn.disabled=false;
    document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
    document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
    document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
    
    
   /* document.Hrm_GpfSettlementForm.txtMDProc.readOnly=false;
    document.Hrm_GpfSettlementForm.date1.readOnly=false;
        
   
    document.Hrm_GpfSettlementForm.imgDate1.onclick=function(){
    	showCalendarControl(document.getElementById('date1'));
    };*/
    
    document.Hrm_GpfSettlementForm.date2.readOnly=false;
    document.Hrm_GpfSettlementForm.imgDate2.onclick=function(){
    	showCalendarControl(document.getElementById('date2'));
    };
    
    document.Hrm_GpfSettlementForm.radRlv[0].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[1].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[2].disabled=false;
    document.Hrm_GpfSettlementForm.listYear.disabled=false;
    document.Hrm_GpfSettlementForm.listMonth.disabled=false;
    //document.Hrm_GpfSettlementForm.listFinanceYear.disabled=false;
    
    /*try {
		document.getElementById("tMasterList").innerHTML="";
	} catch (e) {
		// TODO: handle exception
		document.getElementById("tMasterList").innerText="";
	}
	finallyRelieved='';*/
}

function callone1(command)
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
 var txtLastSlipClosingBal,regu_acc_unit_code=390305,txtImpoundBal,imp_acc_unit_code=391003,txtSubscriptionCredit,imp_2003=391303,txtSubscriptionDebit,da_acc_unit=391503;
 var any_comments;
 var for_off1,for_design1;
 var copy;
 var dth_Relation;
 var dth_date2;
 
 var url="";

  if(command=="Check")
	{
		//alert("With in Checking loop....");
		 gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
		 //alert("GPF_NO==="+gpf_no);
		if(gpf_no=="" || gpf_no==null )
			{
				alert("Please Enter GPF_NO...");
				
			}
		else
			{
			   url="../../../../../GPF_Final_Settlement_Uppdate.view?command=Check&gpf_no="+gpf_no;
			   //alert("URL===="+url);
			   xmlhttp.open("GET",url,true);
               xmlhttp.onreadystatechange=stateChangeds;
               xmlhttp.send(null);
            
			}
	}



}


function legal()
{
	 var dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,dth_Relation;
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
	if(dth_add3=="" || leg==0 || dth_add3=='null')
		{
		
		}else{
				copy_to11+=dth_add3+",\n";
		}
	pin=document.getElementById("pin").value; 
	var leng=document.Hrm_GpfSettlementForm.pin.value.length;
	if(pin=="" || leng==0 || pin=='0')
		{
		
		}else{
				copy_to11+=pin+".";
		}
	
	//alert("=======COPY TO======"+copy_to11);
	//var copy_to11=dth_Legal_person+"\n"+dth_Relation+"/O";
	document.getElementById("copy").value=copy_to11;
	
}



function nullcheck1()
{
//alert("With in null check");
var gpf_no=document.getElementById("txtGpfNo").value;
//if(gpf_no=="" || gpf_no==null)
//{
//	alert("Please Enter GPF_NO");
//}
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
		 document.getElementById('legal_buttons').style.display = 'block';
		 document.getElementById('leagl_details').style.display = 'block';
		alert("Please Enter Leagal Person Name");
		return false;
	}

var relation=document.getElementById("dth_Relation").value;
if(relation=="0")
	{
	 document.getElementById('legal_buttons').style.display = 'block';
	 document.getElementById('leagl_details').style.display = 'block';
		alert("Select The Relation");
		return false;
	}

var Lic_Date=document.getElementById("dth_date2").value;
if(Lic_Date=="" || Lic_Date==null)
{
	 document.getElementById('legal_buttons').style.display = 'block';
	 document.getElementById('leagl_details').style.display = 'block';
	alert("Please Enter Leagal Person Date");
	return false;
}



var add1=document.getElementById("dth_add1").value;
if(add1=="" || add1==null)
	{
	 document.getElementById('legal_buttons').style.display = 'block';
	 document.getElementById('leagl_details').style.display = 'block';
		alert("Enter Legal Person Address1 ");
		return false;
	}



var add2=document.getElementById("dth_add2").value;
if(add2=="" || add2==null)
{
	 document.getElementById('legal_buttons').style.display = 'block';
	 document.getElementById('leagl_details').style.display = 'block';
	alert("Enter Legal Person Address2 ");
	return false;
}


//var add3=document.getElementById("dth_add3").value;
//if(add3=="" || add3==null)
//{
//	alert("Enter Legal Person Address3 ");
//	return false;
//}

//var pin=document.getElementById("pin").value;
//if( pin=="" || pin==null)
//{
//	alert("Pls Enter Six Digit Number");
//	document.Hrm_GpfSettlementForm.pin.value="";
//	return false;
//}
//if(pin!="" || pin!="0" || pin!='null')
//	{
//		flag=Pin_Number(pin);
//		if(flag==false)
//		{
//			alert("Enter six digit pin number ");
//			document.Hrm_GpfSettlementForm.pin.value="";
//			return false;
//		}
//	}

var length=document.Hrm_GpfSettlementForm.pin.value.length;
//alert("Length-===="+length);
if(length==0)
	{
	
	}
//alert("LENGTH==="+length);
else
	{
		var pin=document.getElementById("pin").value;
		//alert("PIN===="+pin);
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


	}

//alert("What is this.....death pinnnnnnn...");

var intrest_year=document.getElementById("listYear").value;
if(intrest_year=="0")
{
	alert("Select The Year for Calculated intrest");
	return false;
}


var listMonth=document.getElementById("listMonth").value;
if(listMonth=="0")
{
	alert("Select The Month for Calculated intrest");
	return false;
}






//gpf_no=document.getElementById("txtGpfNo").value;     
//emp_id=document.getElementById("txtEmpId").value;

//letter_no=document.getElementById("letter_no").value; 
//letter_date=document.getElementById("letter_date").value;    
//jur_off=document.getElementById("jur_off").value;   
//jur_add1=document.getElementById("jur_add1").value;   
//jur_add2=document.getElementById("jur_add2").value; 
//jur_add3=document.getElementById("jur_add3").value;   
//jur_pincode=document.getElementById("jur_pincode").value;   




//for ( var i = 0; i < document.Hrm_GpfSettlementForm.radRlv.length; i++) {	
//	if (document.Hrm_GpfSettlementForm.radRlv[i].checked==true) {
//		relieve_status=document.Hrm_GpfSettlementForm.radRlv[i].value;
//		if(relieve_status=="DTH")
//
//		{
//				alert("................With in DTH........");
//				dth_Legal_person=document.getElementById("dth_Legal_person").value; 
//				
//				dth_Relation=document.getElementById("dth_Relation").value;
//				dth_date2=document.getElementById("dth_date2").value;
//				
//				dth_add1=document.getElementById("dth_add1").value; 
//				dth_add2=document.getElementById("dth_add2").value; 
//				dth_add3=document.getElementById("dth_add3").value; 
//				pin=document.getElementById("pin").value; 
//				
//			}
//	}		
//}                    
// relieve_date=document.getElementById("date2").value;

// listYear=document.getElementById("listYear").value;   
// listMonth=document.getElementById("listMonth").value;   


// intrest_upto=listYear+"-"+listMonth;
// alert("intrest_upto========="+intrest_upto);
//var subject=document.getElementById("subject").value;   
//var  reference=document.getElementById("reference").value;   
//txtLastSlipClosingBal=document.getElementById("txtLastSlipClosingBal").value;   
//
//txtImpoundBal=document.getElementById("txtImpoundBal").value;
//txtSubscriptionCredit=document.getElementById("txtSubscriptionCredit").value;
//txtSubscriptionDebit=document.getElementById("txtSubscriptionDebit").value;


// any_comments=document.getElementById("any_comments").value;

//var for_off1=document.getElementById("for_off1").value;
//var for_design1=document.getElementById("for_design1").value;
//
//var copy=document.getElementById("copy").value;








//var int_tobe_calc_year="";
//var int_tobe_calc_month="";  
//try {
//	var sltlstYr=document.getElementById("listYear");
//	var sltlstMt=document.getElementById("listMonth");
//	
//	int_tobe_calc_year=sltlstYr.options[sltlstYr.selectedIndex].value;
//	int_tobe_calc_month=sltlstMt.options[sltlstMt.selectedIndex].value;

return true;

}



function nullcheck2()
{
	
	var intrest_year=document.getElementById("listYear").value;
	if(intrest_year=="0")
	{
		alert("Select The Year for Calculated intrest");
		return false;
	}


	var listMonth=document.getElementById("listMonth").value;
	if(listMonth=="0")
	{
		alert("Select The Month for Calculated intrest");
		return false;
	}


var letter_no=document.getElementById("letter_no").value; 
 if(letter_no=="" || letter_no==null)
{
	alert("Please Enter Authorisation Leter No");
	return false;
}
//var letter_date=document.getElementById("letter_date").value; 
// if(letter_date=="" || letter_date==null)
//{
//	alert("Please Enter Authorisation Letter Date Like dd/mm/yyyy");
//	return false;
//}
//flag=checkdate(letter_date);
//if(flag==false)
//	{
//		alert("Enter Correct Date Format Like dd/mm/yyyy");
//		return false;
//	}



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

//flag=Character(pro_officer_name);
//if(flag==false)
//	{
//		alert("Please Enter Only Characters");
//		return false;
//	}

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
//var jur_add3=document.getElementById("jur_add3").value;   
//if(jur_add3=="" || jur_add3==null)
//{
//	alert("Please Enter Juristiction Officer Address3");
//	return false;
//}


//var pin=document.getElementById("jur_pincode").value;
//if( pin=="" || pin==null)
//{
//	alert("Pls Enter Six Digit Number");
//	document.Hrm_GpfSettlementForm.jur_pincode.value="";
//	return false;
//}
//flag=Pin_Number(pin);
//if(flag==false)
//{
//	alert("Enter six digit pin number ");
//	document.Hrm_GpfSettlementForm.jur_pincode.value="";
//	return false;
//}
//var length=document.Hrm_GpfSettlementForm.jur_pincode.value.length;
//alert("LENGTH==="+length);
//if(length!=6)
//	{
//		alert("Enter 6 Digits only");
//		document.Hrm_GpfSettlementForm.jur_pincode.value="";
//		return false;
//	}

return true;
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


function grid1()
{
	
	//alert("Grid is CALLING.....");
	
		
		 var gpf_no=document.getElementById("txtGpfNo").value;
		 url="../../../../../GPF_Final_Settlement.view?command=loading&gpf_no="+gpf_no;
		// alert("URL===="+url);
	     xmlhttp.open("GET",url,true);
	    //alert("URL===="+xmlhttp.open("GET",url,true));
	     xmlhttp.onreadystatechange=stateChanged23;
	     
	     //alert("COUNT========0  " );
	     xmlhttp.send(null);
		
			
}


function stateChanged23()
{

	//alert(" ****Hai**** ");
var flag,command,response,status,yearList,monthList;
    
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
    	   try {
    		   response=xmlhttp.responseXML.getElementsByTagName("response")[0];
    		   //alert("RESPONSE========="+response);
 
    		   
			} catch (e) {
				// TODO: handle exception
			}
			try {
				command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				//alert("COMMAND=========="+command);
			} catch (e) {
				// TODO: handle exception
			}
			try {
				//status=response.getElementsByTagName("status")[0].firstChild.nodeValue;
				//alert("Status === "+status);
				flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				
				//var relieval_reason=response.getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
			//alert("Flag  ==" +  flag);
				
				  if(command=="legal_details")
		            {
					  if(flag=="failure")
						  {
						  	alert("Record is not Inserted");
						  }
					  if(flag=="success")
						  {
						  	alert("Record is Inserted Successfully");
						  	
						  	grid111();
						  	
						  }
		            }
				  
				  
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
							
							
							
							//var copy_to=response.getElementsByTagName("COPY_TO")[0].firstChild.nodeValue;
							//document.getElementById("copy").value=copy_to;
							
							
						}
						
						record_counts();
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
				  
				  if(command=="loads")
				  {
			  		if(flag=="success")
			  			{
		  				var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
			  			
				  		//alert("COUNT ==== "+count);
				
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
						  			
						  			//alert(LEGAL_HEIR_ADD3);
						  			if(LEGAL_HEIR_ADD3=='null')
					  				{
						  				LEGAL_HEIR_ADD3="";
					  				}

						  			if(LEGAL_HEIR_PIN=='0')
					  				{
						  				LEGAL_HEIR_PIN="";
					  				}





						  			
						  			var LEGAL_PERCENT=response.getElementsByTagName("LEGAL_PERCENT")[i].firstChild.nodeValue;
						  			
					  			//var LEGAL_HEIR_PIN=response.getElementsByTagName("LEGAL_HEIR_PIN")[i].firstChild.nodeValue;
					  			
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
			  		leg_details_CLEAR221();
			  		//record_count();
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
					  			//clearLegal();
					  			//alert("l _ ID === "+response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue);
					  			     var legal_ID=response.getElementsByTagName("LEGAL_HIER_ID_1")[0].firstChild.nodeValue;
					  			     document.getElementById("dth_Legal_person_ID").value=legal_ID;
					  			}
					  		//record_counts();
					  		copy_TO_FN();
					  }
				  
				  if(command=="load")
					  {
					  		if(flag=="success")
					  			{
				  				var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
					  			
						  		//alert("COUNT ==== "+count);
						
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
								  			
								  			//alert(LEGAL_HEIR_ADD3);
								  			if(LEGAL_HEIR_ADD3=='null')
							  				{
								  				LEGAL_HEIR_ADD3="";
							  				}

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
						  			            //tdd.setAttribute("style", "display:none");
						  			            y.setAttribute("onclick", "check_det()");
						  			            tdd.setAttribute("style", "display:none");
						  			            tdd.appendChild(y);					  			           
						  			            row.appendChild(tdd);
						  			         // row.setAttribute("style", "display:none");
						  			           
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
					  		leg_details_CLEAR1();
					  		call('Draft');
					  }
			}
			catch(e)
			{
				
			}
       }
       
    }

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


function leg_details_CLEAR1()
{
	//alert("with in leg details.....");
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=legal_ID&gpf_no="+gpf_no;
	// alert("URL===="+url);
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange=stateChanged23;
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
   
    document.getElementById("legal_add").disabled = false;
    document.getElementById('td1').style.display = 'none';
  	document.getElementById('td2').style.display = 'none';
}

function officedetailss(ctlOffice)
{
        //alert('hai');
        
        if(ctlOffice=="get")
        {
        	 var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        	// alert(gpf_no);
        	 if(gpf_no==null || gpf_no=="")
        		 {
        		 	//alert("Enter the GPF No....");
        		 	
        		 }
        		
            //document.frmOffice.txtOffice_Id.focus();
       
            var url="../../../../../ViewOfficeDetailsServlet_New.con?gpf_no="+gpf_no;
           // alert(url);
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
                OfficeDetailsResponses(req);
            }
            req.send(null);
        }

}


function OfficeDetailsResponses(req)
{

    
    if(req.readyState==4)
        {
              if(req.status==200)
              {      
                  //  stopwaiting(document.frmOffice) ;
                    //alert(req.responseTEXT);
                    var response=req.responseXML.getElementsByTagName("response")[0];
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        var officename=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
                        var officeshortname=response.getElementsByTagName("officeshortname")[0].firstChild.nodeValue;
                        var headcode=response.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                        var officelevel=response.getElementsByTagName("officelevel")[0].firstChild.nodeValue;
                        
                        if(officelevel=="RN" || officelevel=="HO")
                        {
                            document.frmOffice.cmbControllingLevel.disabled=true;
                        }
                        var primaryid=response.getElementsByTagName("primaryid")[0].firstChild.nodeValue;
                        var controllingofficeid=response.getElementsByTagName("controlofficeid")[0].firstChild.nodeValue;
                        var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                        var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                        var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                        var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                        var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                        var city=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                        var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                        var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                        var std=response.getElementsByTagName("std")[0].firstChild.nodeValue;
                        var phone=response.getElementsByTagName("phone")[0].firstChild.nodeValue;
                        var addphone=response.getElementsByTagName("addphone")[0].firstChild.nodeValue;
                        var fax=response.getElementsByTagName("fax")[0].firstChild.nodeValue;
                        var addfax=response.getElementsByTagName("addfax")[0].firstChild.nodeValue;
                        var email=response.getElementsByTagName("email")[0].firstChild.nodeValue;
                        var addemail=response.getElementsByTagName("addemail")[0].firstChild.nodeValue;
                        var officestatus=response.getElementsByTagName("officestatus")[0].firstChild.nodeValue;
                        document.frmOffice.txtShortName.value="";
                        document.frmOffice.txtOff_Name.value="";
                        document.frmOffice.cmbHeadCode.selectedIndex=0;
                        document.frmOffice.cmbLevelId.selectedIndex=0;
                        document.frmOffice.txtContrllingOfficeID.value="";
                        document.frmOffice.txtHContrllingOfficeID.value="";
                        document.frmOffice.cmbPrimaryID.value="";
                        document.frmOffice.txtDOF.value="";
                        document.frmOffice.txtRemarks.value="";
                        document.frmOffice.txtAdd1.value="";
                        document.frmOffice.txtAdd2.value="";
                        document.frmOffice.txtAdd3.value="";
                        document.frmOffice.cmbDistrict.selectedIndex=0;
                        document.frmOffice.txtPinCode.value="";
                        document.frmOffice.txtSTDCode.value="";
                        document.frmOffice.txtPhoneNo.value="";
                        document.frmOffice.txtAddPhoneNo.value="";
                        document.frmOffice.txtFAXNo.value="";
                        document.frmOffice.txtAddFAXNo.value="";
                        document.frmOffice.txtEMail.value="";
                        document.frmOffice.txtAddEMail.value="";
                        document.frmOffice.txtofficestatus.value="";
                        document.frmOffice.txtShortName.value=officeshortname;
                        document.frmOffice.txtOff_Name.value=officename;
                        
                        
                        setDefaultCadre(officelevel);
                        document.frmOffice.cmbLevelId.value=officelevel;
                        global_headcode=headcode;
                        document.frmOffice.txtContrllingOfficeID.value=controllingofficeid;
                        document.frmOffice.txtContrllingOfficeID.disabled=true;
                        document.frmOffice.txtHContrllingOfficeID.value=controllingofficeid;
                        if(primaryid!="null")
                        {
                        
                        document.frmOffice.cmbPrimaryID.value=primaryid;
                        }
                        else
                        {
                        document.frmOffice.cmbPrimaryID.disabled=true;
                        
                        }
                        if(date!="null")
                        {
                            document.frmOffice.txtDOF.value=date;
                        }
                        if(remarks!="null")
                        {
                            document.frmOffice.txtRemarks.value=remarks;
                        }
                        if(recordstatus=="FR")
                        {
                           // alert("Office Id is Freezed");
                            //document.frmOffice.cmdSub.disabled=true;
                        }
                        else
                        {
                            //document.frmOffice.cmdSub.disabled=false;
                        }
                        if(address1!="null")
                        {
                            document.frmOffice.txtAdd1.value=address1;
                        }
                        if(address2!="null")
                        {
                            document.frmOffice.txtAdd2.value=address2;
                        }
                        if(city!="null")
                        {
                            document.frmOffice.txtAdd3.value=city;
                        }
                        if(district!="0")
                        {
                            if(district!="null")
                            {
                                document.frmOffice.cmbDistrict.value=district;
                            }
                        }
                        if(pincode!="0")
                        {
                            if(pincode!="null")
                            {
                                document.frmOffice.txtPinCode.value=pincode;
                            }
                        }
                        if(std!="null")
                        {
                            if(std!="0")
                            {
                            document.frmOffice.txtSTDCode.value=std;
                            }
                        }
                        if(phone!="null")
                        {
                            document.frmOffice.txtPhoneNo.value=phone;
                        }
                        if(addphone!="null")
                        {
                            document.frmOffice.txtAddPhoneNo.value=addphone;
                        }
                        if(fax!="null")
                        {
                            document.frmOffice.txtFAXNo.value=fax;
                        }
                        if(addfax!="null")
                        {
                            document.frmOffice.txtAddFAXNo.value=addfax;
                        }
                        if(email!="null")
                        {
                            document.frmOffice.txtEMail.value=email;
                        }
                        if(addemail!="null")
                        {
                            document.frmOffice.txtAddEMail.value=addemail;
                        }
                        
                         if(officestatus!="null")
                         {
                            document.frmOffice.txtofficestatus.value=officestatus;
                         }
                        setOfficeLevel();
                        //document.frmOffice.cmbOffice_Id.disabled=true;
                        officename1();
                        //deleteCheck();
                        
                    }
                    else
                    {
                        alert("Invalid Office Id");
                        document.frmOffice.txtOffice_Id.value="";
                        document.frmOffice.txtShortName.value="";
                        document.frmOffice.txtOff_Name.value="";
                        document.frmOffice.cmbHeadCode.selectedIndex=0;
                        document.frmOffice.cmbLevelId.selectedIndex=0;
                        document.frmOffice.txtContrllingOfficeID.value="";
                        document.frmOffice.txtHContrllingOfficeID.value="";
                        document.frmOffice.cmbPrimaryID.value="";
                        document.frmOffice.txtDOF.value="";
                        document.frmOffice.txtRemarks.value="";
                        document.frmOffice.txtconOfficeName.value="";
                        document.frmOffice.txtconOfficeAddress.value="";
                        document.frmOffice.txtAdd1.value="";
                        document.frmOffice.txtAdd2.value="";
                        document.frmOffice.txtAdd3.value="";
                        document.frmOffice.cmbDistrict.selectedIndex=0;
                        document.frmOffice.txtPinCode.value="";
                        document.frmOffice.txtSTDCode.value="";
                        document.frmOffice.txtPhoneNo.value="";
                        document.frmOffice.txtAddPhoneNo.value="";
                        document.frmOffice.txtFAXNo.value="";
                        document.frmOffice.txtAddFAXNo.value="";
                        document.frmOffice.txtEMail.value="";
                        document.frmOffice.txtAddEMail.value="";
                        //document.frmOffice.txtconOfficeAddress1.value="";
                        document.frmOffice.txtOffice_Id.focus();
                    }
               }
        }
                

}


function clearLegal()
{
	//alert('CLEAR LEGAL FUNCTION....');
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=legal_ID&gpf_no="+gpf_no;
	// alert("URL===="+url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged22;
     xmlhttp.send(null);
	
	
	
	document.getElementById("dth_Legal_person").value="";
	document.getElementById("dth_Relation").value="0";
	document.getElementById("dth_date2").value="";
	document.getElementById("dth_add1").value="";
	document.getElementById("dth_add2").value="";
	document.getElementById("dth_add3").value="";
	document.getElementById("pin").value="";
}

function leg_details1()
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
	pin=document.getElementById("pin").value;
	
	if(pin=="" || pin == null)
		{
			pin=0;
		}
	
	percent=document.getElementById("percent").value;
	copy_to=document.getElementById("copy").value;
	var flag=validate();
	//alert("FALAGH==="+flag);
	if(flag==false)
		{
			
	    }else
		{
	    	  url="../../../../../GPF_Final_Settlement.view?command=legal_details&dth_Legal_person_ID="+dth_Legal_person_ID+"&dth_Legal_person="+dth_Legal_person;
	    	  url+="&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
	    	  url+="&gpf_no="+gpf_no+"&empid="+empid+"&copy_to="+copy_to;
	    	 // alert("URL===="+url);
	          xmlhttp.open("GET",url,true);
	          xmlhttp.onreadystatechange=stateChanged23;
	          xmlhttp.send(null);
	         // leg_details_CLEAR1();
	          document.getElementById('td1').style.display = 'none';
		      	document.getElementById('td2').style.display = 'none';
		}
	
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
	
	
		
	return true;
}


function leg_details_Update1()
{

	//alert("WITH IN LEGal UPDATE FUNCTION.....");
	
	var dth_Legal_person_ID,dth_Legal_person,dth_add1,dth_add2,dth_add3,pin,dth_Relation,dth_date2,percent;
	var gpf_no=document.getElementById("txtGpfNo").value;
	var empid=document.getElementById("comEmpId").value;
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
	percent=document.getElementById("percent").value;
	
	var flag=validate();
	    if(flag==false)
		{
			
	    }else
		{
	    	  url="../../../../../GPF_Final_Settlement.view?command=leg_details_Update&dth_Legal_person_ID="+dth_Legal_person_ID+"&dth_Legal_person="+dth_Legal_person;
	    	  url+="&dth_Relation="+dth_Relation+"&dth_date2="+dth_date2+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin+"&percent="+percent;
	    	  url+="&gpf_no="+gpf_no+"&emp_name="+empid;
	          xmlhttp.open("GET",url,true);
	          xmlhttp.onreadystatechange=stateChanged23;
	          xmlhttp.send(null);
	         // clearLegal();
	          document.getElementById('td1').style.display = 'none';
	          document.getElementById('td2').style.display = 'none';
	          document.getElementById("legal_add").disabled = false;

		}
	
	 
}

function leg_details_Delete1()
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
	if(dth_add3=="" || dth_add3=='null' || dth_add3==null)
	{
		dth_add3="";
	}
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
	    	 // alert("URL===="+url);
	          xmlhttp.open("GET",url,true);
	          xmlhttp.onreadystatechange=stateChanged23;
	          xmlhttp.send(null);
	          //leg_details_CLEAR1();
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


function grid111()
{
		 var gpf_no=document.getElementById("txtGpfNo").value;
		 url="../../../../../GPF_Final_Settlement.view?command=loadings1&gpf_no="+gpf_no;
		// alert("url is === "+url);
		 xmlhttp.open("GET",url,true);
	     xmlhttp.onreadystatechange=stateChanged23;
	     xmlhttp.send(null);
	     
}

function leg_details_CLEAR221()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=legal_ID&gpf_no="+gpf_no;
	// alert("URL CLEAR221===="+url);
  xmlhttp.open("GET",url,true);
  xmlhttp.onreadystatechange=stateChanged23;
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

function record_counts()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=record_count&gpf_no="+gpf_no;
	//alert("COPY_TO_URL===="+url);
 xmlhttp.open("GET",url,true);  
 xmlhttp.onreadystatechange=stateChanged23;
 xmlhttp.send(null);

}

function copy_TO_FN()
{
	var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=copy_to&gpf_no="+gpf_no;
//   alert("COPY_TO_URL===="+url);
	 xmlhttp.open("GET",url,true);  
	 xmlhttp.onreadystatechange=stateChanged23;
	 xmlhttp.send(null);

}