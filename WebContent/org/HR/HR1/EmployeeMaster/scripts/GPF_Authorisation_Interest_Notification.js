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
        winemp=null
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
       document.Hrm_GpfSettlementForm.radRlv[3].disabled=false;
       document.Hrm_GpfSettlementForm.radRlv[4].disabled=false;
       
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
	
//	var firstRelieveStatus=response.getElementsByTagName("firstRelieveStatus")[0].firstChild.nodeValue;
//	if((firstRelieveStatus!='FINISHED')&&(firstRelieveStatus!='NONE'))
	{
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
	
}
function fillRelievalDetails(response){
	
	var date2=response.getElementsByTagName("relieve_date")[0].firstChild.nodeValue;
	var radRlv=response.getElementsByTagName("relieve_status")[0].firstChild.nodeValue;
	document.getElementById("date2").value=date2;
	if(radRlv=='SAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	}
	else if(radRlv=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
	}
	else if(radRlv=='DTH'){
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
	}
	else if(radRlv=='MEV'){
		document.Hrm_GpfSettlementForm.radRlv[3].checked=true;
	}
	else if(radRlv=='CMR'){
		document.Hrm_GpfSettlementForm.radRlv[4].checked=true;
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
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}else if(firstRelieveStatus=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}else if(firstRelieveStatus=='DTH'){
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}
	else if(firstRelieveStatus=='MEV'){
		document.Hrm_GpfSettlementForm.radRlv[3].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}
	
	else if(firstRelieveStatus=='CMR'){
		document.Hrm_GpfSettlementForm.radRlv[4].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}
	
	else if(firstRelieveStatus=='ThisMonthSAN'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	 //   alert('This employee is going to retire this month');
	}else if(firstRelieveStatus=='NONE'){
		
		
		alert('This employee has not been relieved this month');
		document.Hrm_GpfSettlementForm.txtGpfNo.value="";
		clearData();
	}else if(firstRelieveStatus=='FINISHED'){
		
		
		alert('This process has already finished for this employee');
		document.Hrm_GpfSettlementForm.txtGpfNo.value="";
		clearData();
		emptyRelievalDetails();
		
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
    url="../../../../../GPF_Authorisation_Interest_Notification.view?command=get&gpf_no="+gpf_no+"&auth_no="+authNo;
    
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange=stateChanged;
    
    xmlhttp.send(null);
	
}*/
function emptyEmployeeDetails(){
	document.getElementById("comEmpId").value="";
    document.getElementById("txtDOB").value="";
  //  document.getElementById("txtGpfNo").value="";
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
	document.getElementById("txtLastSlipClosingBal").value="";
	document.getElementById("txtImpoundBal").value="";
	document.getElementById("txtSubscriptionCredit").value="";
	document.getElementById("txtSubscriptionDebit").value="";
	document.getElementById("txtBalHasOnDate").value="";
	document.getElementById("txtImpBalHasOnDate").value="";
	
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
    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
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
function stateChanged()
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
			
            if(command=="get")
            {	
                if(flag=='success')
                {
                	
                	if(status=='GpfEdited'){
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
                	    document.Hrm_GpfSettlementForm.radRlv[3].disabled=true;
                	    document.Hrm_GpfSettlementForm.radRlv[4].disabled=true;
                	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
                	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
                	    
                	    
                    }
                    if(status=='GpfValidated'){
                    	document.Hrm_GpfSettlementForm.addBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
                        
                        
                        fillEmployeeDetails(response);
                       
                        fillBalanceDetails(response); 
                        fillRelievalDetails(response);
                        makeRelieveDetailsDisabled();
                    	
                       
                        
                    }
                    else if(status=='GpfNotExist'){
                    	document.Hrm_GpfSettlementForm.addBtn.disabled=false;
                        document.Hrm_GpfSettlementForm.updateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
                        document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
                        
                        fillEmployeeDetails(response);
                        fillBalanceDetails(response);
                        fillFirstRelievalDetails(response);
                        
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
                	
                    
                    alert("Database error");
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
        	else  if(command=="update")
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
                    alert("Not Deleted");
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



function clearData()
{
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
    document.Hrm_GpfSettlementForm.radRlv[3].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[4].disabled=false;
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
        
        
        var url="";
        if(command=="Add")
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
						url="../../../../../GpfInterestAuthorisationNotification.view?command=Add&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
                        xmlhttp.open("GET",url,true);
                        xmlhttp.onreadystatechange=stateChanged;
                        xmlhttp.send(null);
					} 
                }
        }
    else if(command=="Update")
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
						url="../../../../../GpfInterestAuthorisationNotification.view?command=Update&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year+"&proc_no="+proc_no+"&proc_date="+proc_date+"&finance_year="+finance_year;
		                
				        xmlhttp.open("GET",url,true);
				        xmlhttp.onreadystatechange=stateChanged;
				        xmlhttp.send(null);
					}
	                
	                   
                }
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
			url="../../../../../GpfInterestAuthorisationNotification.view?command=Validate&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
	        
	        xmlhttp.open("GET",url,true);
	        xmlhttp.onreadystatechange=stateChanged;
	        xmlhttp.send(null);
		}
		
        
        
    }  
    else if(command=="Get")
    {  
    	
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
        {
            alert("Null Value not accepted...Select GPF.No");
            document.Hrm_GpfSettlementForm.txtGpfNo.focus();
            return false;
        }
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        loadDefault();
        url="../../../../../GpfInterestAuthorisationNotification.view?command=get&gpf_no="+gpf_no;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        
        xmlhttp.send(null);
       
        
    }
    else if(command=="Delete")
    {  
        
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
            {
                alert("Null Value not accepted...Select GPF.No");
                document.Hrm_GpfSettlementForm.txtGpfNo.focus();
                return false;
            }
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        url="../../../../../GpfInterestAuthorisationNotification.view?command=Delete&gpf_no="+gpf_no;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }    
    else if(command=="LoadYear"){
    	
    	var relvDate=document.getElementById("date2").value;
        url="../../../../../GpfInterestAuthorisationNotification.view?command=LoadYear&relieve_date="+relvDate;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
    }
    else if(command=="LoadMonth"){
    	
    	var relvDate=document.getElementById("date2").value;
    	var rlvYear=document.getElementById("listYear").value;
    	
        url="../../../../../GpfInterestAuthorisationNotification.view?command=LoadMonth&relieve_date="+relvDate+"&listYear="+rlvYear;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
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
    document.Hrm_GpfSettlementForm.radRlv[3].disabled=false;
    document.Hrm_GpfSettlementForm.radRlv[4].disabled=false;
    document.Hrm_GpfSettlementForm.listYear.disabled=false;
    document.Hrm_GpfSettlementForm.listMonth.disabled=false;   
    
    
	try {    				   
	    	document.getElementById("spanFinalErrorStatus").firstChild.nodeValue="";
	    	document.getElementById("spanRelieveDate").firstChild.nodeValue="";
	    	
	} catch (e) {
			document.getElementById("spanFinalErrorStatus").innnerHTML="";
			document.getElementById("spanRelieveDate").innnerHTML="";
	}
	
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
     if((relieveStatus=='DTH')||(relieveStatus=='VRS')||(relieveStatus=='CMR')||(relieveStatus=='MEV')){
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
	 
	// alert("Idate....."+Idate+"Imonth...."+Imonth+"Iyear...."+Iyear);
	
	if((month=='01')||(month=='1')){
		if(Idate==31){
			return true;
		}
	}
	else if((month=='02')||(month=='2')){
		if((Iyear%4)==0){
			
			//alert("Idate....in..if"+Idate);
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



