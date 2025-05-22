var xmlhttp;var seq=0;var common="",mn,yr,v;
var missing_interest_data="",interest_rate_data='',count_rows='',current_fin_end_date='';
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
    winemp= window.open("../../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
	
       
       document.Hrm_GpfSettlementForm.reportBtn.disabled=true;
       document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
       
       document.Hrm_GpfSettlementForm.txtMDProc.readOnly=true;
       document.Hrm_GpfSettlementForm.date1.readOnly=true;
       document.Hrm_GpfSettlementForm.date2.readOnly=true;    
       document.Hrm_GpfSettlementForm.date3.readOnly=true;
       document.Hrm_GpfSettlementForm.imgDate1.onclick=null;
       document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
       document.Hrm_GpfSettlementForm.imgDate3.onclick=null;
       document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
       document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
       document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
       
       try {
       	document.getElementById("spanFin").firstChild.nodeValue="";
       	document.getElementById("spanFrom").firstChild.nodeValue="";
       	document.getElementById("spanTo1").firstChild.nodeValue="";
       	document.getElementById("spanTo2").firstChild.nodeValue="";
       	document.getElementById("spanTo3").firstChild.nodeValue="";
   	} catch (e) {
   		// TODO: handle exception
   	}
       
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
function fillGpfData1( response){
	
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
    var txtFinanceYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;
    document.getElementById("txtFinanceYear").value=txtFinanceYear;
}
function fillGpfData2( response){
	
	var txtFinanceYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;	
	var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
	var txt1GpfReg=response.getElementsByTagName("cb_ac_re")[0].firstChild.nodeValue;	
	var txt2GpfReg=response.getElementsByTagName("subseq_deposit_re")[0].firstChild.nodeValue;
	var txt3GpfReg=response.getElementsByTagName("int_amount_re")[0].firstChild.nodeValue;
	var txt4GpfReg=response.getElementsByTagName("dep_amount_re")[0].firstChild.nodeValue;
	var txt5GpfReg=response.getElementsByTagName("dep_interest_re")[0].firstChild.nodeValue;
	var txt6GpfReg=response.getElementsByTagName("subseq_withdraw_re")[0].firstChild.nodeValue;
	var txt7GpfReg=response.getElementsByTagName("withdraw_amount_re")[0].firstChild.nodeValue;
	var txt8GpfReg=response.getElementsByTagName("withdraw_interest_re")[0].firstChild.nodeValue;
	var txt9GpfReg=response.getElementsByTagName("amount_already_re")[0].firstChild.nodeValue;
	var txt10GpfReg=response.getElementsByTagName("reg_amt_bal_auth")[0].firstChild.nodeValue;
	var txt1GpfIm=response.getElementsByTagName("cb_ac_im")[0].firstChild.nodeValue;
	var txt2GpfIm=response.getElementsByTagName("subseq_deposit_im")[0].firstChild.nodeValue;
	var txt3GpfIm=response.getElementsByTagName("int_amount_im")[0].firstChild.nodeValue;
	var txt4GpfIm=response.getElementsByTagName("dep_amount_im")[0].firstChild.nodeValue;
	var txt5GpfIm=response.getElementsByTagName("dep_interest_im")[0].firstChild.nodeValue;
	var txt6GpfIm=response.getElementsByTagName("subseq_withdraw_im")[0].firstChild.nodeValue;
	var txt7GpfIm=response.getElementsByTagName("withdraw_amount_im")[0].firstChild.nodeValue;
	var txt8GpfIm=response.getElementsByTagName("withdraw_interest_im")[0].firstChild.nodeValue;
	var txt9GpfIm=response.getElementsByTagName("amount_already_im")[0].firstChild.nodeValue;	
	var txt10GpfIm=response.getElementsByTagName("imp_amt_bal_auth")[0].firstChild.nodeValue;
	
	document.getElementById("txtFinanceYear").value=txtFinanceYear;
	document.getElementById("txt1GpfReg").value=txt1GpfReg;
	document.getElementById("txt2GpfReg").value=txt2GpfReg;
	document.getElementById("txt3GpfReg").value=txt3GpfReg;
	document.getElementById("txt4GpfReg").value=txt4GpfReg;
	document.getElementById("txt5GpfReg").value=txt5GpfReg;
	document.getElementById("txt6GpfReg").value=txt6GpfReg;
	document.getElementById("txt7GpfReg").value=txt7GpfReg;
	document.getElementById("txt8GpfReg").value=txt8GpfReg;
	document.getElementById("txt9GpfReg").value=txt9GpfReg;
	document.getElementById("txt10GpfReg").value=txt10GpfReg;
	document.getElementById("txt1GpfIm").value=txt1GpfIm;
	document.getElementById("txt2GpfIm").value=txt2GpfIm;
	document.getElementById("txt3GpfIm").value=txt3GpfIm;
	document.getElementById("txt4GpfIm").value=txt4GpfIm;
	document.getElementById("txt5GpfIm").value=txt5GpfIm;
	document.getElementById("txt6GpfIm").value=txt6GpfIm;
	document.getElementById("txt7GpfIm").value=txt7GpfIm;
	document.getElementById("txt8GpfIm").value=txt8GpfIm;
	document.getElementById("txt9GpfIm").value=txt9GpfIm;
	document.getElementById("txt10GpfIm").value=txt10GpfIm;	
	
	
}

function fillGpfData3( response){
	var txtMDProc=response.getElementsByTagName("proc_no")[0].firstChild.nodeValue;
	var date1=response.getElementsByTagName("proc_date")[0].firstChild.nodeValue;
	var date2=response.getElementsByTagName("relieve_date")[0].firstChild.nodeValue;
	var radRlv=response.getElementsByTagName("relieve_status")[0].firstChild.nodeValue;
	var txtFinanceYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;	
	var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
	var date3=response.getElementsByTagName("int_tobe_calc_date")[0].firstChild.nodeValue;
	
	document.getElementById("txtMDProc").value=txtMDProc;
	document.getElementById("date1").value=date1;
	document.getElementById("date2").value=date2;
	document.getElementById("date3").value=date3;
	
	if(radRlv=='R'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	}
	else if(radRlv=='V'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
	}
	else if(radRlv=='D'){
		document.Hrm_GpfSettlementForm.radRlv[2].checked=true;
	}
	
	var txtFinanceYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;	
	var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
	var intCalcDate=response.getElementsByTagName("int_tobe_calc_date")[0].firstChild.nodeValue;
	document.getElementById("spanFin").firstChild.nodeValue=txtFinanceYear;
	document.getElementById("spanFrom").firstChild.nodeValue='04/'+txtCurrentYear;
	document.getElementById("spanTo1").firstChild.nodeValue=current_fin_end_date;
	document.getElementById("spanTo2").firstChild.nodeValue=current_fin_end_date;
	document.getElementById("spanTo3").firstChild.nodeValue=current_fin_end_date;
	alert(current_fin_end_date);
}


function emptyGpfData1(){
	document.getElementById("comEmpId").value="";
    document.getElementById("txtDOB").value="";
    document.getElementById("txtGpfNo").value="";
    document.getElementById("designation").value="";
    document.getElementById("txtEmpId").value=""; 
    
    document.getElementById("txtFinanceYear").value="";
}
function emptyGpfData2(){
	
	document.getElementById("txtFinanceYear").value="";
	document.Hrm_GpfSettlementForm.txt1GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt2GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt3GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt4GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt5GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt6GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt7GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt8GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt9GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt10GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt1GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt2GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt3GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt4GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt5GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt6GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt7GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt8GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt9GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt10GpfIm.value="";
    
}
function emptyGpfData3(){
	document.getElementById("txtMDProc").value="";
	document.getElementById("date1").value="";
	document.getElementById("date2").value="";
	document.getElementById("date3").value="";
	document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	
}
function calculateCommonDetails( response){
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
	try {
		current_fin_end_date=response.getElementsByTagName("current_fin_end_date")[0].firstChild.nodeValue;
	} catch (e) {
		// TODO: handle exception		
	}
	
}
function fillCalculationDetails(response){
	var txtFinanceYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;	
	var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
	var txt1GpfReg=response.getElementsByTagName("cb_ac_re")[0].firstChild.nodeValue;	
	var txt2GpfReg=response.getElementsByTagName("subseq_deposit_re")[0].firstChild.nodeValue;
	var txt3GpfReg=response.getElementsByTagName("int_amount_re")[0].firstChild.nodeValue;
	var txt4GpfReg=response.getElementsByTagName("dep_amount_re")[0].firstChild.nodeValue;
	var txt5GpfReg=response.getElementsByTagName("dep_interest_re")[0].firstChild.nodeValue;
	var txt6GpfReg=response.getElementsByTagName("subseq_withdraw_re")[0].firstChild.nodeValue;
	var txt7GpfReg=response.getElementsByTagName("withdraw_amount_re")[0].firstChild.nodeValue;
	var txt8GpfReg=response.getElementsByTagName("withdraw_interest_re")[0].firstChild.nodeValue;
	var txt9GpfReg=response.getElementsByTagName("amount_already_re")[0].firstChild.nodeValue;
	var txt10GpfReg=response.getElementsByTagName("reg_amt_bal_auth")[0].firstChild.nodeValue;
	var txt1GpfIm=response.getElementsByTagName("cb_ac_im")[0].firstChild.nodeValue;
	var txt2GpfIm=response.getElementsByTagName("subseq_deposit_im")[0].firstChild.nodeValue;
	var txt3GpfIm=response.getElementsByTagName("int_amount_im")[0].firstChild.nodeValue;
	var txt4GpfIm=response.getElementsByTagName("dep_amount_im")[0].firstChild.nodeValue;
	var txt5GpfIm=response.getElementsByTagName("dep_interest_im")[0].firstChild.nodeValue;
	var txt6GpfIm=response.getElementsByTagName("subseq_withdraw_im")[0].firstChild.nodeValue;
	var txt7GpfIm=response.getElementsByTagName("withdraw_amount_im")[0].firstChild.nodeValue;
	var txt8GpfIm=response.getElementsByTagName("withdraw_interest_im")[0].firstChild.nodeValue;
	var txt9GpfIm=response.getElementsByTagName("amount_already_im")[0].firstChild.nodeValue;	
	var txt10GpfIm=response.getElementsByTagName("imp_amt_bal_auth")[0].firstChild.nodeValue;
	var txtLastFinanceYear=response.getElementsByTagName("last_finance_year")[0].firstChild.nodeValue;
	
	
	document.getElementById("txt1GpfReg").value=txt1GpfReg;
	document.getElementById("txt2GpfReg").value=txt2GpfReg;
	document.getElementById("txt3GpfReg").value=txt3GpfReg;
	document.getElementById("txt4GpfReg").value=txt4GpfReg;
	document.getElementById("txt5GpfReg").value=txt5GpfReg;
	document.getElementById("txt6GpfReg").value=txt6GpfReg;
	document.getElementById("txt7GpfReg").value=txt7GpfReg;
	document.getElementById("txt8GpfReg").value=txt8GpfReg;
	document.getElementById("txt9GpfReg").value=txt9GpfReg;
	document.getElementById("txt10GpfReg").value=txt10GpfReg;
	document.getElementById("txt1GpfIm").value=txt1GpfIm;
	document.getElementById("txt2GpfIm").value=txt2GpfIm;
	document.getElementById("txt3GpfIm").value=txt3GpfIm;
	document.getElementById("txt4GpfIm").value=txt4GpfIm;
	document.getElementById("txt5GpfIm").value=txt5GpfIm;
	document.getElementById("txt6GpfIm").value=txt6GpfIm;
	document.getElementById("txt7GpfIm").value=txt7GpfIm;
	document.getElementById("txt8GpfIm").value=txt8GpfIm;
	document.getElementById("txt9GpfIm").value=txt9GpfIm;
	document.getElementById("txt10GpfIm").value=txt10GpfIm;	
	
	
	var txtCurrentYear=response.getElementsByTagName("current_opening_year")[0].firstChild.nodeValue;
	var intCalcDate=response.getElementsByTagName("int_tobe_calc_date")[0].firstChild.nodeValue;
	
	var current_fin_end_date1='';
	try {
		current_fin_end_date1=response.getElementsByTagName("current_fin_end_date")[0].firstChild.nodeValue;
		
	} catch (e) {
		// TODO: handle exception
		
	}
	
	try {
		document.getElementById("spanFin").firstChild.nodeValue=txtLastFinanceYear;
		document.getElementById("spanFrom").firstChild.nodeValue='04/'+txtCurrentYear;
		document.getElementById("spanTo1").firstChild.nodeValue=current_fin_end_date1;
		document.getElementById("spanTo2").firstChild.nodeValue=current_fin_end_date1;
		document.getElementById("spanTo3").firstChild.nodeValue=current_fin_end_date1;
	} catch (e) {
		document.getElementById("spanFin").innnerHTML=txtLastFinanceYear;
		document.getElementById("spanFrom").innnerHTML='04/'+txtCurrentYear;
		document.getElementById("spanTo1").innnerHTML=current_fin_end_date1;
		document.getElementById("spanTo2").innnerHTML=current_fin_end_date1;
		document.getElementById("spanTo3").innnerHTML=current_fin_end_date1;
	}
	
}
function emptyCalculationDetails(){
	
	document.Hrm_GpfSettlementForm.txt1GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt2GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt3GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt4GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt5GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt6GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt7GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt8GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt9GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt10GpfReg.value="";
    document.Hrm_GpfSettlementForm.txt1GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt2GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt3GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt4GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt5GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt6GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt7GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt8GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt9GpfIm.value="";
    document.Hrm_GpfSettlementForm.txt10GpfIm.value="";
    try {
    	document.getElementById("spanFin").firstChild.nodeValue="";
    	document.getElementById("spanFrom").firstChild.nodeValue="";
    	document.getElementById("spanTo1").firstChild.nodeValue="";
    	document.getElementById("spanTo2").firstChild.nodeValue="";
    	document.getElementById("spanTo3").firstChild.nodeValue="";
	} catch (e) {
		document.getElementById("spanFin").innnerHTML="";
		document.getElementById("spanFrom").innnerHTML="";
		document.getElementById("spanTo1").innnerHTML="";
		document.getElementById("spanTo2").innnerHTML="";
		document.getElementById("spanTo3").innnerHTML="";
	}
    
    
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
function fillRelievalDetails(response){
	
	var date2=response.getElementsByTagName("relieve_date")[0].firstChild.nodeValue;
	var radRlv=response.getElementsByTagName("relieve_status")[0].firstChild.nodeValue;
	document.getElementById("date2").value=date2;
	if(radRlv=='R'){
		document.Hrm_GpfSettlementForm.radRlv[0].checked=true;
	}
	else if(radRlv=='V'){
		document.Hrm_GpfSettlementForm.radRlv[1].checked=true;
	}
	else if(radRlv=='D'){
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
function fillNoteDetails(response){
	var authNo=response.getElementsByTagName("auth_no")[0].firstChild.nodeValue;
	var txtMDProc=response.getElementsByTagName("proc_no")[0].firstChild.nodeValue;
	var date1=response.getElementsByTagName("proc_date")[0].firstChild.nodeValue;
	document.getElementById("txtMDProc").value=txtMDProc;
	document.getElementById("date1").value=date1;
	var totalRecords=response.getElementsByTagName("totalRecords")[0].firstChild.nodeValue;
	
	var txtFinanceYear=response.getElementsByTagName("finance_year")[0].firstChild.nodeValue;
	
    document.getElementById("txtFinanceYear").value=txtFinanceYear;
    var intTotRec=0;
    try {
    	intTotRec=parseInt(totalRecords);
    	
	} catch (e) {
		// TODO: handle exception
		alert(e);
	}
	
	
    //load Note Details
	
	for ( var e = 1; e<document.getElementById("listAuth").options.length; e++) {
		document.getElementById("listAuth").remove(e);
	}       			
	
	for ( var u = 1; u <=intTotRec; u++) {		
		document.getElementById("listAuth").options[u]=new Option(u,u);
		if(authNo==u){
			document.getElementById("listAuth").selectedIndex=u;
		}
	}
	
}

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
	for ( var e = 1; e<document.getElementById("listYear").options.length; e++) {
		document.getElementById("listYear").remove(e);
	} 

	for ( var e = 1; e<document.getElementById("listMonth").options.length; e++) {
			document.getElementById("listMonth").remove(e);
	}

}
function emptyNoteDetails(){
	document.getElementById("txtMDProc").value="";
	document.getElementById("date1").value="";
	
	document.getElementById("txtFinanceYear").value="";
	for ( var e = 1; e<document.getElementById("listAuth").options.length; e++) {
		document.getElementById("listAuth").remove(e);
	} 
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
function makeNoteDetailsDisabled(){
	document.Hrm_GpfSettlementForm.txtMDProc.readOnly=true;
    document.Hrm_GpfSettlementForm.date1.readOnly=true;
    document.Hrm_GpfSettlementForm.imgDate1.onclick=null;
    document.Hrm_GpfSettlementForm.listFinanceYear.disabled=true;    
	
}
function makeRelieveAndNoteDetailsDefault(){	
	makeRelieveDetailsDisabled();
	makeNoteDetailsDisabled();
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
            
			calculateCommonDetails( response);
            if(command=="get")
            {	
                if(flag=='success')
                {	
                	
                    if(status=='GpfEdited'){
                    	clearData();
                    	alert("This authorised no is not yet freezed");
                    }
                    if(status=='GpfPartiallyValidated'){
                                                
                    }
                    else if(status=='GpfNotExist'){
                    	clearData();
                    	 //document.Hrm_GpfSettlementForm.deleteBtn.disabled=true;
                        alert("Gpf for this employee is not yet added");
                    	
                    } 
                    
                    else if(status=='GpffinalReport'){
                    	
                    	var noteGenerated="";
                    	try {
                    		noteGenerated=response.getElementsByTagName("noteGenerated")[0].firstChild.nodeValue;
						} catch (e) {
							// TODO: handle exception
							alert(e);
						}
						
						if(noteGenerated=='Y'){
							
							document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
	                    	document.Hrm_GpfSettlementForm.reportBtn.disabled=false;
	                    	
							fillEmployeeDetails(response);
	                        fillRelievalDetails(response);
	                        fillNoteDetails(response);
	                        fillCalculationDetails(response);
	                        
	                        makeRelieveDetailsDisabled();
	                    	makeNoteDetailsDisabled();
	                    	
	                    	
						}
						else if(noteGenerated=='N'){
							var partialValidated='';
							try {
								partialValidated=response.getElementsByTagName("partialValidated")[0].firstChild.nodeValue;
							} catch (e) {
								// TODO: handle exception
							}
							
							if(partialValidated=='norecord'){
								clearData();
								alert("Gpf for this employee is not yet added");
							}
							else if(partialValidated=='yes'){
								document.Hrm_GpfSettlementForm.validateBtn.disabled=false;
		                    	document.Hrm_GpfSettlementForm.reportBtn.disabled=true;
		                    	
								fillEmployeeDetails(response);
		                        fillRelievalDetails(response);
		                        fillNoteDetails(response);
		                        fillCalculationDetails(response);
		                        
		                        makeRelieveDetailsDisabled();
		                    	makeNoteDetailsDisabled();
							} 
	                    	
	                    	
						}
						
						
                    }
                    
                }                
                else if(flag=='failure'){
                	
                	clearData();
                    
                    alert("This Employee GPF.No doesnot Exist");
                }
               
            }
            if(command=="CalcInterest"){
            	if(flag=='success'){
            		alert('Interest calculated successfully');
            		
            	}
            	else if(flag=='failure'){
            		clearData();
            		alert('interest calculation  failed');
            	}
            }
        }
    }
           
}



function clearData()
{
	emptyEmployeeDetails();
	emptyRelievalDetails();
	emptyNoteDetails();
	emptyCalculationDetails();
	
	document.Hrm_GpfSettlementForm.validateBtn.disabled=true;
	document.Hrm_GpfSettlementForm.reportBtn.disabled=true;
    
    document.Hrm_GpfSettlementForm.txtMDProc.readOnly=true;
    document.Hrm_GpfSettlementForm.date1.readOnly=true;
    document.Hrm_GpfSettlementForm.date2.readOnly=true;
    document.Hrm_GpfSettlementForm.imgDate1.onclick=null;
    document.Hrm_GpfSettlementForm.imgDate2.onclick=null;
    document.Hrm_GpfSettlementForm.radRlv[0].disabled=true;
    document.Hrm_GpfSettlementForm.radRlv[1].disabled=true;
    document.Hrm_GpfSettlementForm.radRlv[2].disabled=true;
    document.Hrm_GpfSettlementForm.listYear.disabled=true;
    document.Hrm_GpfSettlementForm.listMonth.disabled=true;
    
    try {
    	document.getElementById("spanFin").firstChild.nodeValue="";
    	document.getElementById("spanFrom").firstChild.nodeValue="";
    	document.getElementById("spanTo1").firstChild.nodeValue="";
    	document.getElementById("spanTo2").firstChild.nodeValue="";
    	document.getElementById("spanTo3").firstChild.nodeValue="";
	} catch (e) {
		// TODO: handle exception
	}
    
}
function callFromAuth(){
	  
	var URL="";
	if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
    {
        alert("Null Value not accepted...Select GPF.No");
        document.Hrm_GpfSettlementForm.txtGpfNo.focus();
        return false;
    }
    var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;    
    var authNo=0;
    try {
    	var sltAuthNo=document.getElementById("listAuth");
    	authNo=sltAuthNo.options[sltAuthNo.selectedIndex].value;
	} catch (e) {
		// TODO: handle exception
		
	}
	
	
	URL="../../../../../../GPF_Authorisation_Interest_Notification.view?command=get&gpf_no="+gpf_no+"&finalReport=yes"+"&auth_no="+authNo;        
    xmlhttp.open("GET",URL,true);
    xmlhttp.onreadystatechange=stateChanged;
    
    xmlhttp.send(null);
    
   // clearData();callFromAuth
}
function call(command)
{
xmlhttp=getxmlhttpObject();

	if(xmlhttp==null)
	{
	    alert("Your borwser doesnot support AJAX");
	    return;
    }   
    
	
        var gpf_no;
        var proc_no;
        var proc_date;
        var relieve_status;
        var relieve_date;
        var int_calc_date;
        
        
        var url="";
        
    
    
     if(command=="Get")
     {  
    	
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
        {
            alert("Null Value not accepted...Select GPF.No");
            document.Hrm_GpfSettlementForm.txtGpfNo.focus();
            return false;
        }
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        var authNo="";
        try {
        	var sltAuthNo=document.getElementById("listAuth");
        	authNo=sltAuthNo.options[sltAuthNo.selectedIndex].value;
		} catch (e) {
			// TODO: handle exception
		}
		
        url="../../../../../../GPF_Authorisation_Interest_Notification.view?command=get&gpf_no="+gpf_no+"&finalReport=yes"+"&auth_no="+authNo;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        
        xmlhttp.send(null);
        clearData();        
    }    
     if(command=="CalcInterest")
     {  
    	
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
        {
            alert("Null Value not accepted...Select GPF.No");
            document.Hrm_GpfSettlementForm.txtGpfNo.focus();
            return false;
        }
        var gpf_no=document.Hrm_GpfSettlementForm.txtGpfNo.value;
        var authNo="";
        try {
        	var sltAuthNo=document.getElementById("listAuth");
        	authNo=sltAuthNo.options[sltAuthNo.selectedIndex].value;
		} catch (e) {
			// TODO: handle exception
		}
		
		var boolTer=validateInterestDetails();
		if(!boolTer){
			var boolTer1=confirm('Do you want to proceed validation');
			if(!boolTer1){
				return ;
			}
		}
        url="../../../../../../GPF_Authorisation_Interest_Notification.view?command=calcInterest&gpf_no="+gpf_no+"&auth_no="+authNo;        
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        
        xmlhttp.send(null);
        clearData();
        
    } 
    else if(command=="Report")
    {  
        
        if((document.Hrm_GpfSettlementForm.txtGpfNo.value=="")||(document.Hrm_GpfSettlementForm.txtGpfNo.value.length==0))
            {
                alert("Null Value not accepted...Select GPF.No");
                document.Hrm_GpfSettlementForm.txtGpfNo.focus();
                return false;
            }
        
        
        document.Hrm_GpfSettlementForm.action="../../../../../../GPF_Authorisation_Interest_Notification.view";
        document.Hrm_GpfSettlementForm.method="POST";
        document.Hrm_GpfSettlementForm.submit();
        
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
function additionNullCheck(){
	
	var procNo1=document.getElementById("txtMDProc").value;
	
	if(document.Hrm_GpfSettlementForm.txtMDProc.value=="" && document.Hrm_GpfSettlementForm.txtMDProc.value.length==0)
	   {
	     
	     document.Hrm_GpfSettlementForm.txtMDProc.focus();
	     return false;
	   } 
	return true;
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
     alert('Please enter the From Date');
     document.Hrm_GpfSettlementForm.date1.focus();
     return false;
   }
   
   if(document.Hrm_GpfSettlementForm.date1.value=="" && document.Hrm_GpfSettlementForm.date1.value.length==0)
   {
     alert('Please enter the To Date');
     document.Hrm_GpfSettlementForm.date1.focus();
     return false;
   }
   return true;
}
    
function validateInterestDetails(){
	
	var res=false;
	var strTemp1='';
	var strTemp2='';
	var valueV=0;
	
	if(interest_rate_data!='yes'){		
		strTemp1+='INTEREST RATE not entered for this financial year ';
		valueV+=1;
		
	}
	if(missing_interest_data!='yes'){
		strTemp2+='MISSING INTEREST not calculated for this gpf number';
		valueV+=2;
		
	}
	
	
	if(valueV==0){
		return true;
	}
	else if(valueV==3){
		alert(strTemp1+' and '+strTemp2);
		return false;
	}
	else if(valueV==1){
		alert(strTemp1);
		return false;
	}
	else if(valueV==2){
		alert(strTemp2);
		return false;
	}
	
	
	return false;	
	
}






