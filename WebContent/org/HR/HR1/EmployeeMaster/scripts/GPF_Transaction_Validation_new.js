var xmlhttp;var seq=0;var common="",mn,yr,v;
var trialVal;

function Exit()
{
 
	window.close();
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

function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
        var ac_month;
        var finyear;
        var ac_year;
       var unit_id;
        var url="";
        if(command=="Add")
        { 
               
        	if(nullCheck())
        	{
        		
        		finyear=document.forms[0].fin_year.value;
        		 ac_month=document.forms[0].ac_month.value;
        	  	ac_year=document.forms[0].ac_year.value;
        	  	unit_id=document.forms[0].Acc_unit_code.value;
                
               
                url="../../../../../GPF_Transaction_Validation_new?command=Add&unit_id="+unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&finyear="+finyear;
           
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
        	
        	}
    }
  
        else     if(command=="Add1")
        { 
               
        	if(nullCheck())
        	{
        		
        		finyear=document.forms[0].fin_year.value;
        		 ac_month=document.forms[0].ac_month.value;
        	  	ac_year=document.forms[0].ac_year.value;
        	  	unit_id=document.forms[0].Acc_unit_code.value;
                
               
                url="../../../../../GPF_Transaction_Validation_new?command=Add&unit_id="+unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&finyear="+finyear;
           
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
        	
        	}
    }
    else if(command=="trialbalance")
    {   
      // var officeid=document.getElementById("txtOffId").value;
       var acmonth=document.forms[0].ac_month.value;
  	   var acyear=document.forms[0].ac_year.value;
  	   var unit=document.forms[0].Acc_unit_code.value;
  	   var fin_year=document.forms[0].fin_year.value;
    	 
        url="../../../../../GPF_Transaction_Validation_new?command=trialbalance&acmonth="+acmonth+"&acyear="+acyear+"&unit="+unit+"&fin_year="+fin_year;
      
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);  
 	   
    }   
 
    else if(command=="UnitName"){
    	var unit_id=document.getElementById("unit_name").value;
        document.getElementById("Acc_unit_code").value=unit_id;
        clear();
    }      
        
        
}

function stateChanged()
{
    var flag,command,response;
 
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
           
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
          
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

            if(command=="trialbalance")
            {
            	
                if(flag=='success')
                {
                	document.getElementById("arrear").value=response.getElementsByTagName("arrear")[0].firstChild.nodeValue; 
                	document.getElementById("refund").value=response.getElementsByTagName("refund")[0].firstChild.nodeValue;
                	document.getElementById("withdrawal").value=response.getElementsByTagName("withdrawal")[0].firstChild.nodeValue;
                //	document.getElementById("w_credit").value=response.getElementsByTagName("w_credit")[0].firstChild.nodeValue;
                	document.getElementById("w_debit").value=response.getElementsByTagName("w_debit")[0].firstChild.nodeValue;
                	document.getElementById("impreg_credit").value=response.getElementsByTagName("impreg_credit")[0].firstChild.nodeValue;
                	document.getElementById("impreg_debit").value=response.getElementsByTagName("impreg_debit")[0].firstChild.nodeValue;
                	document.getElementById("imp2003_credit").value=response.getElementsByTagName("imp2003_credit")[0].firstChild.nodeValue;
                	document.getElementById("imp2003_debit").value=response.getElementsByTagName("imp2003_debit")[0].firstChild.nodeValue;
                	
                	document.getElementById("r_sub").value=response.getElementsByTagName("r_sub")[0].firstChild.nodeValue;
                	document.getElementById("r_ref").value=response.getElementsByTagName("r_ref")[0].firstChild.nodeValue;
                	
                //	document.getElementById("sub_credit").value=response.getElementsByTagName("sub_credit")[0].firstChild.nodeValue;
                	document.getElementById("with_debit").value=response.getElementsByTagName("with_debit")[0].firstChild.nodeValue;
                	
                	document.getElementById("diff_subscription").value=response.getElementsByTagName("diff_subscription")[0].firstChild.nodeValue;
                	document.getElementById("diff_refund").value=response.getElementsByTagName("diff_refund")[0].firstChild.nodeValue;
                	
                	//  new changes
                	var withdrawal_da=response.getElementsByTagName("withdrawal_da")[0].firstChild.nodeValue;
                	var withdrawal_debit_CR=response.getElementsByTagName("withdrawal_debit_CR")[0].firstChild.nodeValue;
                	var withdrawal_debit_null=response.getElementsByTagName("withdrawal_debit_null")[0].firstChild.nodeValue;
                	var withdrawal_debit_DB=response.getElementsByTagName("withdrawal_debit_DB")[0].firstChild.nodeValue;
                	var withdrawal_da_amt=response.getElementsByTagName("withdrawal_da")[0].firstChild.nodeValue;
                	
                	
                	
                	var withdrawal_debit_new=((parseFloat(withdrawal_debit_DB))+(parseFloat(withdrawal_debit_null))-((parseFloat(withdrawal_debit_CR))+(parseFloat(withdrawal_da))));
               
                	//	alert("withdrawal_debit_null"+withdrawal_debit_null+"withdrawal_debit_DB"+withdrawal_debit_DB+"withdrawal_debit_CR"+withdrawal_debit_CR);
                	//alert("withdrawal_debit_CR"+withdrawal_debit_CR+"withdrawal_debit_DB"+withdrawal_debit_DB);
                	document.getElementById("withdrawal_debit").value=withdrawal_debit_new;
                	document.getElementById("diff_withdrawal").value=response.getElementsByTagName("diff_withdrawal")[0].firstChild.nodeValue;
                	
//                	document.getElementById("impoundreg_credit").value=response.getElementsByTagName("impoundreg_credit")[0].firstChild.nodeValue;
//                	
//                	
//                	document.getElementById("impoundreg_debit").value=response.getElementsByTagName("impoundreg_debit")[0].firstChild.nodeValue;
//                	
                	
                	
                	//new changes
                	
                	//  for impreg cr
                	var impoundreg_credit_null=response.getElementsByTagName("impoundreg_credit_null")[0].firstChild.nodeValue;
                	var impoundreg_credit=response.getElementsByTagName("impoundreg_credit")[0].firstChild.nodeValue;
                	
                	var impoundRegularCredit_391002=response.getElementsByTagName("impoundRegularCredit_391002")[0].firstChild.nodeValue;
                	
                	var impoundreg_credit_final=parseInt(impoundreg_credit_null)+parseInt(impoundreg_credit)-parseInt(impoundRegularCredit_391002);
                	
                	
                	//  for impreg db
                	var impoundRegularDebit_null=response.getElementsByTagName("impoundRegularDebit_null")[0].firstChild.nodeValue;
                	var impoundreg_debit=response.getElementsByTagName("impoundreg_debit")[0].firstChild.nodeValue;
                	
                	var impoundreg_debit_391003=response.getElementsByTagName("impoundreg_debit_391003")[0].firstChild.nodeValue;
                	
                	var impoundreg_debit_final=parseInt(impoundRegularDebit_null)+parseInt(impoundreg_debit)-parseInt(impoundreg_debit_391003);
                	
                	
                	
                	document.getElementById("impoundreg_credit").value=impoundreg_credit_final;
               	
               	
               	document.getElementById("impoundreg_debit").value=impoundreg_debit_final;
                	
                	document.getElementById("impound2003_credit").value=response.getElementsByTagName("impound2003_credit")[0].firstChild.nodeValue;
                	document.getElementById("impound2003_debit").value=response.getElementsByTagName("impound2003_debit")[0].firstChild.nodeValue;
                	
                	document.getElementById("diff_impregcredit").value=response.getElementsByTagName("diff_impregcredit")[0].firstChild.nodeValue;
                	document.getElementById("diff_impregdebit").value=response.getElementsByTagName("diff_impregdebit")[0].firstChild.nodeValue;
                	
                	document.getElementById("diff_imp2003credit").value=response.getElementsByTagName("diff_imp2003credit")[0].firstChild.nodeValue;
                	
                	document.getElementById("withdrawal_da").value=response.getElementsByTagName("withdrawal_da")[0].firstChild.nodeValue;
                	
                	document.getElementById("diff_imp2003debit").value=response.getElementsByTagName("diff_imp2003debit")[0].firstChild.nodeValue;
                	vflag=response.getElementsByTagName("vflag")[0].firstChild.nodeValue;
                	
                	
                	var total=parseInt(document.getElementById("diff_subscription").value)+parseInt(document.getElementById("diff_refund").value);
                	
                	total=total+parseInt(document.getElementById("diff_withdrawal").value)+parseInt(document.getElementById("diff_impregcredit").value);
                	//alert(total);
                	total=total+parseInt(document.getElementById("diff_impregdebit").value)+parseInt(document.getElementById("diff_imp2003credit").value);
                	//alert(total);
                	total=total+parseInt(document.getElementById("diff_imp2003debit").value);
                	//alert(total);
                	var sub=parseInt(document.getElementById("r_sub").value);
                	var ref=parseInt(document.getElementById("r_ref").value);
                	var with_tot=parseInt(document.getElementById("withdrawal_debit").value);
                
                	if((sub==0)||(ref==0))
                	{
                		document.Hrm_TransJoinForm.validate.disabled=true;
                	}
                	
                	if((vflag==1))
                	{
                		document.Hrm_TransJoinForm.validate.disabled=false;
                	}
                	else{
                		document.Hrm_TransJoinForm.validate.disabled=true;
                	}
                	
                	var validation=response.getElementsByTagName("validation")[0].firstChild.nodeValue;
                
                	var validation_s=response.getElementsByTagName("validation_s")[0].firstChild.nodeValue;
                	
                	var validation_w=response.getElementsByTagName("validation_w")[0].firstChild.nodeValue;
                
                	
                	if((validation=='Y'))
                	{
                		alert("Already validated");
                		
                	//	clear();
                		
                		document.Hrm_TransJoinForm.validate.disabled=true;
                	}
                	
                	if((validation=='N'))
                	{
                	if((validation_s=='CR') || (validation_w=='CR') || (validation_s=='Nodata'))
                	{
                		alert("Please validate the subscription  / withdrawal entries for this office");
                		document.Hrm_TransJoinForm.validate.disabled=true;
                	}
                	
                	else if((validation=='N' && (validation_s=='FR') )){
                		
                		document.Hrm_TransJoinForm.validate.disabled=false;
                	}
                	}	
                }
            }
            else if(command=="Add")
            {
            	 if(flag=='success')
                 {
            		 alert("Successfully Validated");
            		 document.Hrm_TransJoinForm.validate.disabled=true;
            		 clear();
                 }
            	 else if(flag=='success123')
            		 {
            		 alert("Already Validated");
            		 document.Hrm_TransJoinForm.validate.disabled=true;
            		 }
            	 else{
                	 alerst("Error in Validation"); 
                 }
            }
           
            
 else if(command=="partial"){
            	
            	var gpf="";
		          	  if(flag=='success')
		                {
		          		
				          		  var emp_id=response.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
				          		  var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
				          		  var int_date=response.getElementsByTagName("int_date")[0].firstChild.nodeValue;
				          		  gpf=response.getElementsByTagName("gpfNo")[0].firstChild.nodeValue;
				          		  document.getElementById("txtEmployee").value=name;
				          		  document.getElementById("txtGpf").value=emp_id;
				          		 // alert(int_date);
				          		  document.getElementById("int_date").value=int_date;
				          		  document.getElementById("pa").disabled=false;
				          		getFinancialYear(gpf);
		                }
		          	  else
		          	  {
				          		  alert("Settlement Note not yet been finalized for this employee");
				          		  document.getElementById("txtEmpId1").value="";
				          		  document.getElementById("pa").disabled=true;
		          	  }
		          	
                  }
            
            else if(command=="empDetails"){
	          	  if(flag=='success')
	                {
			          		  var emp_id=response.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
			          		  var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
			          		var design=response.getElementsByTagName("design")[0].firstChild.nodeValue;
			          		         		  
			          		  document.getElementById("txtEmployee").value=name;
			          		  document.getElementById("txtGpf").value=emp_id;
			          		 
	                }
	          	  else
	          	  {
			          		  alert("GPF No not exists");
			          		  document.getElementById("txtEmpId1").value="";
			          		  
	          	  }
            }
            else if(command=="empDetailsWorking"){
	          	  if(flag=='success')
	                {
			          		  var emp_id=response.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
			          		  var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
			          		var design=response.getElementsByTagName("design")[0].firstChild.nodeValue;
			          		         		  
			          		  document.getElementById("txtEmployee").value=name;
			          		  document.getElementById("txtGpf").value=emp_id;
			          		 
	                }
	          	  else if(flag=='retired')
	                {
	          		 document.getElementById("txtEmpId1").value="";
	          		   alert("This Employee is in Retired Status. You can not able to View");
	          		
	          		 
            }
	          	  else
	          	  {
	          		 document.getElementById("txtEmpId1").value="";
			          		  alert("GPF No not exists");
			         
			          		  
	          	  }
          }
            else if(command=="empDetailsRetired"){
	          	  if(flag=='success')
	                {
			          		  var emp_id=response.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
			          		  var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
			          		var design=response.getElementsByTagName("design")[0].firstChild.nodeValue;
			          		         		  
			          		  document.getElementById("txtEmployee").value=name;
			          		  document.getElementById("txtGpf").value=emp_id;
			          		 
	                }
	          	  else if(flag=='working')
	                {
	          		 document.getElementById("txtEmpId1").value="";
	          		   alert("This Employee is in Working Status. You can not able to View");
	          		 
	          		 
          }
	          	  else
	          	  {
	          		 document.getElementById("txtEmpId1").value="";
			          		  alert("GPF No not exists");
			          
			          		  
	          	  }
        }
            
       }
    }
}

function nullCheck()
{
if(document.Hrm_TransJoinForm.fin_year.value=="--Select Financial Year--")
{
	alert("Please Select Financial Year");
	return false;
	}
else if(document.Hrm_TransJoinForm.unit_name.value=="Select Account Unit Id")
{
	alert("Please Select Accounting Unit");
	return false;
	}
else if(document.Hrm_TransJoinForm.ac_year.value=="Select Year")
{
	alert("Please Select Accounting Year");
	return false;
	}
else if(document.Hrm_TransJoinForm.ac_month.value=="Select Month")
{
	alert("Please Select Accounting Month");
	return false;
	}
return true;

}

function clear(){
	
	
  document.getElementById("arrear").value="";
 // document.getElementById("sub_credit").value="";
  document.getElementById("r_sub").value="";
  document.getElementById("diff_subscription").value="";
 // document.getElementById("w_credit").value="";
  document.getElementById("refund").value="";
  document.getElementById("r_ref").value="";
  document.getElementById("diff_refund").value="";
  document.getElementById("withdrawal").value="";
  document.getElementById("with_debit").value="";
  document.getElementById("withdrawal_debit").value="";
  document.getElementById("diff_withdrawal").value="";
  document.getElementById("w_debit").value="";
  document.getElementById("impreg_credit").value="";
  document.getElementById("impoundreg_credit").value="";
  document.getElementById("diff_impregcredit").value="";
  document.getElementById("impreg_debit").value="";
  
  document.getElementById("impoundreg_debit").value="";
  document.getElementById("diff_impregdebit").value="";
  
  document.getElementById("imp2003_credit").value="";
  document.getElementById("impound2003_credit").value="";
  document.getElementById("diff_imp2003credit").value="";
  document.getElementById("imp2003_debit").value="";
  
  document.getElementById("impound2003_debit").value="";
  document.getElementById("diff_imp2003debit").value="";
  
  document.getElementById("ac_month").value=0;
  document.getElementById("ac_year").value=0;
}

function partial()
{	
	document.getElementById("pa").disabled=false;
	xmlhttp=getxmlhttpObject();
	var par_gpfno=document.getElementById("txtEmpId1").value;
	if(par_gpfno==" ")
	{
		alert("Enter GPF No");
	}
	else
	{
	var url="../../../../../GPF_Transaction_Validation_new?command=partial&gpf_no="+par_gpfno;
	 url=url+"&sid="+Math.random();
	// alert(url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null); 
     
	}     
}
function getFinancialYear(par_gpfno)
{
	
	var url="../../../../../GPF_Transaction_Validation_new?command=FinancialYear_partial&gpf_no="+par_gpfno;
	 url=url+"&sid="+Math.random();
	 //alert(url);
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange=showFinancial;
    xmlhttp.send(null); 
}

function emp_details()
{
	//alert('method123');
	xmlhttp=getxmlhttpObject();
	var gpf_no=document.getElementById("txtEmpId1").value;
	if(gpf_no==" ")
	{
		alert("Enter GPF No");
	}
	else
	{
	var url="../../../../../GPF_Transaction_Validation_new?command=empDetails&gpf_no="+gpf_no;
	 url=url+"&sid="+Math.random();
	 //alert(url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
	}     
}

function emp_detailsWorking()
{
	//alert('method123');
	xmlhttp=getxmlhttpObject();
	var gpf_no=document.getElementById("txtEmpId1").value;
	if(gpf_no==" ")
	{
		alert("Enter GPF No");
	}
	else if(gpf_no.length==0){
		alert("Enter GPF No");
	}
	else
	{
	var url="../../../../../GPF_Transaction_Validation_new?command=empDetailsWorking&gpf_no="+gpf_no;
	 url=url+"&sid="+Math.random();
	 //alert(url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
	}     
}
function emp_detailsRetired()
{
	//alert('method123');
	xmlhttp=getxmlhttpObject();
	var gpf_no=document.getElementById("txtEmpId1").value;
	if(gpf_no==" ")
	{
		alert("Enter GPF No");
	}
	else if(gpf_no.length==0){
		alert("Enter GPF No");
	}
	else
	{
	var url="../../../../../GPF_Transaction_Validation_new?command=empDetailsRetired&gpf_no="+gpf_no;
	 url=url+"&sid="+Math.random();
	 //alert(url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=stateChanged;
     xmlhttp.send(null);  
	}     
}

function showFinancial()
{
	 if(xmlhttp.readyState==4)
	    {
	    	
	       if(xmlhttp.status==200)
	       {	
	    	   response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	           
	            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	          
	            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            if(flag=='success')
                {
	            	
	            	var loadedMasterForm=response.getElementsByTagName("record")[0];
	            	
	            	var recordLength=loadedMasterForm.childNodes.length;
	            	
	            	for ( var y = 0; y <recordLength; y++) {
	            		var financialyear=loadedMasterForm.getElementsByTagName("year")[y].firstChild.nodeValue;
	            		var opt = document.createElement("option");
	            		 opt.setAttribute('value',financialyear);
	            		 opt.innerHTML =financialyear;
	            		 document.forms["frmEmployeeProfile"].elements["fin_year"].appendChild(opt);
	            	}
                }
	    	   
	       }
	    }
}

