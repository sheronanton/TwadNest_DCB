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
	}
	else if(radRlv=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
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
		document.getElementById("date2").value=firstRelieveDate;
		
		document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
	    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
	    document.Hrm_GpfSettlementForm.date2.readOnly=true;
	    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
	}else if(firstRelieveStatus=='VRS'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
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
    url="../../../../../Generat_Final_Settlement_Final.view?command=get&gpf_no="+gpf_no+"&auth_no="+authNo;
    
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
				  			//alert("LEGAL_HEIR_NAME is ="+LEGAL_HEIR_NAME);
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
				  			//alert("LEGAL_HEIR_NAME is ="+LEGAL_PERCENT);
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
		  			          //document.Hrm_GpfSettlementForm.yy.disabled=true;
		  			          tdds.setAttribute("style", "display:none");
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
	  		//leg_details_CLEAR221();
	  		//record_count();
	  
				  }
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
                		
                		if(relieval_reason=="SAN")
                			{
                				document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
                				document.getElementById('leagl_details').style.display = 'none';
                				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
                			}
                		
                		if(relieval_reason=="VRS")
            			{
            				document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
            				document.getElementById('leagl_details').style.display = 'none';
            				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
            			}
                		
                		if(relieval_reason=="MEV")
            			{
            				document.Hrm_GpfSettlementForm.radRlv[3].checked=true;
            				document.getElementById('leagl_details').style.display = 'none';
            				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
            			}
                		
                		if(relieval_reason=="CMR")
            			{
            				document.Hrm_GpfSettlementForm.radRlv[4].checked=true;
            				document.getElementById('leagl_details').style.display = 'none';
            				document.Hrm_GpfSettlementForm.copy.value=response.getElementsByTagName("COPY_TO_DATA")[0].firstChild.nodeValue;
            			}
                		
                		if(relieval_reason=="DTH")
            			{
            				document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
            				
            				//document.getElementById("dth_table").style.display="block";
            				
            				var copy_array=new Array();
            				var j;
            				var cccounts=response.getElementsByTagName("cccounts")[0].firstChild.nodeValue;
	        				for(var i=0;i<cccounts;i++)
	        					{
	        					//alert("copy to  ====== "+response.getElementsByTagName("COPY_TO")[i].firstChild.nodeValue);
	        					
	        					copy_array[i]=response.getElementsByTagName("COPY_TO")[i].firstChild.nodeValue+"\n\n";
	        						
	        					}
	        				document.Hrm_GpfSettlementForm.copy.value=copy_array;
            				
            				document.Hrm_GpfSettlementForm.dth_Legal_person.value=response.getElementsByTagName("LEGAL_HEIR_NAME")[0].firstChild.nodeValue;
            				document.Hrm_GpfSettlementForm.dth_date2.value=response.getElementsByTagName("LIC_DATE")[0].firstChild.nodeValue;
            				var relation=response.getElementsByTagName("RELATIONSHIP")[0].firstChild.nodeValue;
            				//alert("RELATION===="+relation);
            				document.getElementById("dth_Relation").value=relation;
                    		document.Hrm_GpfSettlementForm.dth_add1.value=response.getElementsByTagName("LEGAL_HEIR_ADD1")[0].firstChild.nodeValue;
                    		document.Hrm_GpfSettlementForm.dth_add2.value=response.getElementsByTagName("LEGAL_HEIR_ADD2")[0].firstChild.nodeValue;
                    		
                    		
                    		var leg_add3=response.getElementsByTagName("LEGAL_HEIR_ADD3")[0].firstChild.nodeValue;
                    		if(leg_add3=='null')
                    		{
                    			document.Hrm_GpfSettlementForm.dth_add3.value="";
                    		}
                    		else
                    			{
                    				document.Hrm_GpfSettlementForm.dth_add3.value=leg_add3;
                    			}
                    		
                    		var leg_pin=response.getElementsByTagName("LEGAL_HEIR_PIN")[0].firstChild.nodeValue;
                    		if(leg_pin=="0" || leg_pin=='null')
                    			{
                    				document.Hrm_GpfSettlementForm.pin.value="";
                       			}
                    		else
                    			{
                    				document.Hrm_GpfSettlementForm.pin.value=leg_pin;
                    			}
                    		
                    		
                    		
                    		
//                    		document.Hrm_GpfSettlementForm.dth_add3.value=response.getElementsByTagName("LEGAL_HEIR_ADD3")[0].firstChild.nodeValue;
//                    		document.Hrm_GpfSettlementForm.pin.value=response.getElementsByTagName("LEGAL_HEIR_PIN")[0].firstChild.nodeValue;
            			}
                		
                		
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
                 		
                 		
                		
                		
                		
                		
                		
                		
                		
                		
//                		var selectdiv=document.getElementById('listYear');
//    					var listOpt=document.createElement("option");
//    					selectdiv.length=0;
//    					selectdiv.appendChild(listOpt);
//    					listOpt.text="select";
//    					listOpt.value="select";
//    					
//    					
//    					for(var i=0; i<1; i++)
//    					{
//							listOpt=document.createElement("option");
//							selectdiv.appendChild(listOpt);
//							listOpt.text=year;
//							listOpt.value=year;
//						}
//                		
//    					
//    					var selectdiv=document.getElementById('listMonth');
//    					var listOpt=document.createElement("option");
//    					selectdiv.length=0;
//    					selectdiv.appendChild(listOpt);
//    					listOpt.text="select";
//    					listOpt.value="select";
//    					
//    					
//    					for(var i=0; i<1; i++)
//    					{
//							listOpt=document.createElement("option");
//							selectdiv.appendChild(listOpt);
//							listOpt.text=month;
//							listOpt.value=month;
//						}
    					
    					
                		
                		document.Hrm_GpfSettlementForm.letter_no.value=response.getElementsByTagName("AUTH_LTR_NO")[0].firstChild.nodeValue;
                		
                		
                		var AUTH_LTR_DATE=response.getElementsByTagName("AUTH_LTR_DATE")[0].firstChild.nodeValue;
                		if(AUTH_LTR_DATE=='null')
                		{
                			document.Hrm_GpfSettlementForm.letter_date.value="";
                		}
                		else
                			{
                				document.Hrm_GpfSettlementForm.letter_date.value=AUTH_LTR_DATE;
                			}
                		
                		
                		//document.Hrm_GpfSettlementForm.letter_date.value=response.getElementsByTagName("AUTH_LTR_DATE")[0].firstChild.nodeValue;
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
//                		document.Hrm_GpfSettlementForm.jur_add3.value=response.getElementsByTagName("JURISDICTION_OFFICE_ADD3")[0].firstChild.nodeValue;
//                		document.Hrm_GpfSettlementForm.jur_pincode.value=response.getElementsByTagName("JURISDICTION_OFFICE_PIN")[0].firstChild.nodeValue;
                		
                		
                		
                		
                		
                		var JURISDICTION_OFFICE_ADD3=response.getElementsByTagName("JURISDICTION_OFFICE_ADD3")[0].firstChild.nodeValue;
                		if(JURISDICTION_OFFICE_ADD3=='null')
                		{
                			document.Hrm_GpfSettlementForm.jur_add3.value="";
                		}
                		else
                			{
                				document.Hrm_GpfSettlementForm.jur_add3.value=JURISDICTION_OFFICE_ADD3;
                			}
                		
                		var JURISDICTION_OFFICE_PIN=response.getElementsByTagName("JURISDICTION_OFFICE_PIN")[0].firstChild.nodeValue;
                		if(JURISDICTION_OFFICE_PIN=="0" || JURISDICTION_OFFICE_PIN=='null')
                			{
                				document.Hrm_GpfSettlementForm.jur_pincode.value="";
                   			}
                		else
                			{
                				document.Hrm_GpfSettlementForm.jur_pincode.value=JURISDICTION_OFFICE_PIN;
                			}
                		
                		
                		
                		
                		var FOWARD_OFFICER_NAME=response.getElementsByTagName("FOWARD_OFFICER_NAME")[0].firstChild.nodeValue;
                		if(FOWARD_OFFICER_NAME=='null')
                		{
                			document.Hrm_GpfSettlementForm.for_off1.value="";
                		}
                		else
                			{
                				document.Hrm_GpfSettlementForm.for_off1.value=FOWARD_OFFICER_NAME;
                			}
                	
                	
                	
                		var FOWARD_OFFICER_DESIG=response.getElementsByTagName("FOWARD_OFFICER_DESIG")[0].firstChild.nodeValue;
                		if(FOWARD_OFFICER_DESIG=='null')
                		{
                			document.Hrm_GpfSettlementForm.for_design1.value="";
                		}
                		else
                			{
                				document.Hrm_GpfSettlementForm.for_design1.value=FOWARD_OFFICER_DESIG;
                			}
                	
                	
                		
                		var ADDL_CONDITIONS=response.getElementsByTagName("ADDL_CONDITIONS")[0].firstChild.nodeValue;
                		if(ADDL_CONDITIONS=='null')
                		{
                			document.Hrm_GpfSettlementForm.any_comments.value="";
                		}
                		else
                			{
                				document.Hrm_GpfSettlementForm.any_comments.value=ADDL_CONDITIONS;
                			}
                		
                		
                		
                		
                		//document.Hrm_GpfSettlementForm.for_off1.value=response.getElementsByTagName("FOWARD_OFFICER_NAME")[0].firstChild.nodeValue;
                		//document.Hrm_GpfSettlementForm.for_design1.value=response.getElementsByTagName("FOWARD_OFFICER_DESIG")[0].firstChild.nodeValue;
                		
                		//document.Hrm_GpfSettlementForm.any_comments.value=response.getElementsByTagName("ADDL_CONDITIONS")[0].firstChild.nodeValue;
                		
                	
                	
//                		var ADDL_CONDITIONS=response.getElementsByTagName("ADDL_CONDITIONS")[0].firstChild.nodeValue;
//                		if(ADDL_CONDITIONS=='null')
//                		{
//                			document.Hrm_GpfSettlementForm.any_comments.value="";
//                		}
//                		else
//                			{
//                				document.Hrm_GpfSettlementForm.any_comments.value=ADDL_CONDITIONS;
//                			}
                	
                	
                		if(relieval_reason=="DTH")
            			{
                			//alert("with in grid ......");
                			 document.getElementById('leagl_details').style.display = 'block';
                			grid_final();
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
                    //	document.Hrm_GpfSettlementForm.addBtn.disabled=false;
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
                	
                    
                    alert("Please validate the record entered for this GPF NO");
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

	
	if(document.getElementById("txtGpfNo").value=='0')
	{
			document.getElementById("txtGpfNo").value="";    
	}
	else
		{
			document.getElementById("txtGpfNo").value="";   
		}
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
	document.getElementById("leagl_details").style.display="none";
	document.Hrm_GpfSettlementForm.dth_Legal_person.value="";
	
	document.Hrm_GpfSettlementForm.dth_date2.value="";
	document.Hrm_GpfSettlementForm.dth_Relation.value="0";
	
	
	document.Hrm_GpfSettlementForm.dth_add1.value="";
	document.Hrm_GpfSettlementForm.dth_add2.value="";
	document.Hrm_GpfSettlementForm.dth_add3.value="";
	document.Hrm_GpfSettlementForm.pin.value="";
	
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
   
    listYear=document.getElementById("listYear").value="0";   
    listMonth=document.getElementById("listMonth").value="0";   
    
 
    intrest_upto=listYear+"-"+listMonth;
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
        var dth_Legal_person,dth_add1,dth_add2,dth_add3,pin;
        var listYear,listMonth,intrest_upto;
        var subject,reference;
        var txtLastSlipClosingBal,regu_acc_unit_code=390305,txtImpoundBal,imp_acc_unit_code=391003,txtSubscriptionCredit,imp_2003=391303,txtSubscriptionDebit,da_acc_unit=391503;
        var any_comments;
        var for_off1,for_design1;
        var copy;
        
        var url="";
        if(command=="Add")
        { 
                            
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
					if(checkAllComboSelected()){
						url="../../../../../Generat_Final_Settlement_Final.view?command=Add&gpf_no="+gpf_no+"&emp_id="+emp_id+"&prefix="+prefix+"&pro_officer_name="+pro_officer_name+"&suffix="+suffix;
						url=url+"&pro_desingnation="+pro_desingnation+"&letter_no="+letter_no+"&letter_date="+letter_date+"&jur_off="+jur_off+"&jur_add1="+jur_add1+"&jur_add2="+jur_add2+"&jur_add3="+jur_add3+"&jur_pincode="+jur_pincode;
						url=url+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&intrest_upto="+intrest_upto+"&subject="+subject+"&reference="+reference;
						url=url+"&txtLastSlipClosingBal="+txtLastSlipClosingBal+"&regu_acc_unit_code="+regu_acc_unit_code+"&txtImpoundBal="+txtImpoundBal+"&imp_acc_unit_code="+imp_acc_unit_code+"&txtSubscriptionCredit="+txtSubscriptionCredit+"&imp_2003="+imp_2003+"&txtSubscriptionDebit="+txtSubscriptionDebit+"&da_acc_unit="+da_acc_unit;
						ulr=url+"&dth_Legal_person="+dth_Legal_person+"&dth_add1="+dth_add1+"&dth_add2="+dth_add2+"&dth_add3="+dth_add3+"&pin="+pin;
						url=url+"&any_comments="+any_comments+"&for_off1="+for_off1+"&for_design1="+for_design1+"&copy="+copy;
						url=url+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
                        xmlhttp.open("GET",url,true);
                        xmlhttp.onreadystatechange=stateChanged;
                        xmlhttp.send(null);
					} 
                
        }
        
        if(command=="report")
        	{
        	
      	gpf_no=document.getElementById("txtGpfNo").value;  
    	if (gpf_no == "" || gpf_no=="null" ) {
		alert("Enter the GPF No");
		return false;
	}
    	var LEGAL_HEIR_NAME=document.getElementById("dth_Legal_person").value;
    	if(LEGAL_HEIR_NAME=='null'){
    		LEGAL_HEIR_NAME="";
    	}
    	var auth_date=document.getElementById("letter_date").value;
    	if(auth_date=='')
    		{
    			alert("Please Enter Authrisation Letter Date");
    			return false;
    		}
    	for(var i=0;i<document.Hrm_GpfSettlementForm.radRlv.length;i++)
		{
	
    		if(document.Hrm_GpfSettlementForm.radRlv[i].checked==true)
    		{
    			var rel_reason=document.Hrm_GpfSettlementForm.radRlv[i].value;
    			//alert("REL_REASON===="+rel_reason);
    		}
		}
    	
    	
    	var copy=document.getElementById("copy").value; 
    	//alert("COPY ===== "+copy);
    	var copy_array=new Array();
    	copy_array=copy.split("\n\n");
    	//alert("copy length is ==="+copy_array.length);
    	var length=copy_array.length;
    	
    	var for_officer=document.getElementById("for_off1").value;
    	var for_designation=document.getElementById("for_design1").value;
    	var comEmpId=document.getElementById("comEmpId").value;
    	var designation=document.getElementById("designation").value;
    	var cmbReportType = document.getElementById("cmbReportType").value;
 		var txtLastSlipClosingBal=document.getElementById("txtLastSlipClosingBal").value;
 			var txtImpoundBal=document.getElementById("txtImpoundBal").value;
 				var txtSubscriptionCredit=document.getElementById("txtSubscriptionCredit").value;
 					var txtSubscriptionDebit=document.getElementById("txtSubscriptionDebit").value;
 					
 					var listYear=document.getElementById("listYear").value;   
 	                 var listMonth=document.getElementById("listMonth").value; 
 	               
 	                    intrest_upto=listMonth+"-"+listYear;
 	                    
 	                    if((listYear=="select") || (listMonth=="select"))
 	                    	{
 	                    		alert("Select year and month for calculated intrest ");
 	                    	}
 					
 	                    else{
    	document.Hrm_GpfSettlementForm.action = "../../../../../Generat_Final_Settlement_Final.view?gpf_no="
    							+ gpf_no+ "&cmbReportType="+cmbReportType+"&auth_date="+auth_date+"&LEGAL_HEIR_NAME="+LEGAL_HEIR_NAME+"&rel_reason="+rel_reason+"&txtLastSlipClosingBal="+txtLastSlipClosingBal+"&txtImpoundBal="+txtImpoundBal+"&txtSubscriptionCredit="+txtSubscriptionCredit+"&txtSubscriptionDebit="+txtSubscriptionDebit+"&intrest_upto="+intrest_upto+"&copy="+copy+"&copy_array="+copy_array+"&length="+length+"&designation="+designation+"&comEmpId="+comEmpId
    							+"&for_officer="+for_officer+"&for_designation="+for_designation;
    							
    					document.Hrm_GpfSettlementForm.method = "POST";
    					document.Hrm_GpfSettlementForm.submit();
    					return true;
    				
 	                    }
    		} 
        	
        	
        
        
    else if(command=="Draft")
    {
                
                	gpf_no=document.getElementById("txtGpfNo").value;  
                	

						url="../../../../../Generat_Final_Settlement_Final.view?command=Draft&gpf_no="+gpf_no;
		               
				        xmlhttp.open("GET",url,true);
				        xmlhttp.onreadystatechange=stateChanged;
				        xmlhttp.send(null);
				
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
			url="../../../../../Generat_Final_Settlement_Final.view?command=Validate&gpf_no="+gpf_no+"&relieve_status="+relieve_status+"&relieve_date="+relieve_date+"&int_tobe_calc_month="+int_tobe_calc_month+"&int_tobe_calc_year="+int_tobe_calc_year;
	        
	        xmlhttp.open("GET",url,true);
	        xmlhttp.onreadystatechange=stateChanged;
	        xmlhttp.send(null);
		}
		
        
        
    }  
    else if(command=="Get")
    {  
    	
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0) ||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==null))
        {
            alert("Null Value not accepted...Select GPF.No");
            document.Hrm_GpfSettlementForm.txtGpfNo.focus();
            return false;
        }
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        
        loadDefault();
        url="../../../../../Generat_Final_Settlement_Final.view?command=get&gpf_no="+gpf_no;        
        
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
        url="../../../../../Generat_Final_Settlement_Final.view?command=Delete&gpf_no="+gpf_no;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }    
    else if(command=="LoadYear"){
    	
    	var relvDate=document.getElementById("date2").value;
        url="../../../../../Generat_Final_Settlement_Final.view?command=LoadYear&relieve_date="+relvDate;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        
    }
    else if(command=="LoadMonth"){
    	
    	var relvDate=document.getElementById("date2").value;
    	var rlvYear=document.getElementById("listYear").value;
    	
        url="../../../../../Generat_Final_Settlement_Final.view?command=LoadMonth&relieve_date="+relvDate+"&listYear="+rlvYear;        
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
     if((relieveStatus=='DTH')||(relieveStatus=='VRS')||(relieveStatus=='MEV') ||(relieveStatus=='CMR')){
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
		if((Iyear%4)==0){
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


function grid_final()
{
	//alert("In gird...");
	 var gpf_no=document.getElementById("txtGpfNo").value;
	 url="../../../../../GPF_Final_Settlement.view?command=loadings1&gpf_no="+gpf_no;
	// alert("url is === "+url);
	 xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.send(null);

}
