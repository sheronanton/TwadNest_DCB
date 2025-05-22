var xmlHttp1 = null, xmlHttpobj = null, xmlHttpreq = null, xmlHttp = null;
var sel_len = 0;
var counter = 0;
var field = new Array();
var winemp = null;
var winemp1 = null;
var checkState = "Before";
var common = "";
var length = 0;
var flag, command, response = "";
var pagesize = 10;
var seq = 0;

var  off_id="";
var txtid1;
var txtid2;

function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}
var winjob;




////////////////////////////////////////////////////////////////////////

function dis_view()
{
document.Hrm_TransJoinForm.view.disabled=false;
}

function getPayrollData()
{

    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
   
if(document.Hrm_TransJoinForm.fin_year.selectedIndex==0)
{
	alert("Select Financial Year");
	return false;
}
if(document.Hrm_TransJoinForm.unit_name.selectedIndex==0)
{alert("Select Accounting unit id");
return false;
}
if(document.Hrm_TransJoinForm.ac_year.selectedIndex==0)
{alert("Select respective year");
return false;
}
if(document.Hrm_TransJoinForm.ac_month.selectedIndex==0)
{alert("Select respective month");
return false;
}
   
     
    var url="../../../../../hrm_gpf_import_payroll_data?command=getPayrollData&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
  
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function(){
      	
    	 {
	    	 if(req.readyState==4)
	    	    {
	    		 
	    	         if(req.status==200)
	    	        {
	    	        	
	    	           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	    	           
	    	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	    	            var Command=tagcommand.firstChild.nodeValue;
	    	            if(Command=='getPayrollData')
	    	            {
	    	            	
	    	            	
	    	            	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    	            	
	    	            	//var flag_status=baseResponse.getElementsByTagName("flag")[0];
	    	            	 
	    	            	  if(flag=='updated')
	    	            	  {
	    	            	
	    	            		alert("Importing GPF Subscription   Data from Payroll Completed !");
	    	            		 document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	  else if(flag=='nodata')
	    	            	  {
	    	            	
	    	            		alert("Please freeze the Pay Bill Process  ");
	    	            		 document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	  
	    	            	  else if(flag=='freezed')
	    	            	  {
	    	            		  alert("Already the records have been freezed for the specified(Acc_unit_id,Year and month)");
	    	            		  
	    	            		  document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	  else if(flag=='exists')
	    	            	  {
	    	            		  alert("Already there are records for the specified(Acc_unit_id,Year and month)");
	    	            		  
	    	            		  document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	
	    	            }
	    	            	
	    	           }
	    	        }
	    	    }
	    };
	    req.send(null);

}


///.................for with drawal data ............



function getwithdet()
{

    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
   
if(document.Hrm_TransJoinForm.fin_year.selectedIndex==0)
{
	alert("Select Financial Year");
	return false;
}
if(document.Hrm_TransJoinForm.unit_name.selectedIndex==0)
{alert("Select Accounting unit id");
return false;
}
if(document.Hrm_TransJoinForm.ac_year.selectedIndex==0)
{alert("Select respective year");
return false;
}
if(document.Hrm_TransJoinForm.ac_month.selectedIndex==0)
{alert("Select respective month");
return false;
}
   
     
    var url="../../../../../hrm_gpf_import_payroll_data?command=getPayrollData_withdet&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
  
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function(){
      	
    	 {
	    	 if(req.readyState==4)
	    	    {
	    		 
	    	         if(req.status==200)
	    	        {
	    	        	
	    	           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	    	           
	    	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	    	            var Command=tagcommand.firstChild.nodeValue;
	    	            if(Command=='getPayrollData_withdet')
	    	            {
	    	            	
	    	            	
	    	            	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    	            	
	    	            	//var flag_status=baseResponse.getElementsByTagName("flag")[0];
	    	            	 
	    	            	  if(flag=='updated')
	    	            	  {
	    	            	
	    	            		alert("Importing  Withdrawal  Data from Payroll Completed !");
	    	            		 document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	  else if(flag=='nodata')
	    	            	  {
	    	            	
	    	            		alert("Please freeze the Pay Bill Process  ");
	    	            		 document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	  else if(flag=='exists')
	    	            	  {
	    	            		  alert("Already there are records for the specified(Acc_unit_id,Year and month)");
	    	            		  
	    	            		  document.Hrm_TransJoinForm.fin_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.unit_name.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_year.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.ac_month.selectedIndex=0;
	    	            		  document.Hrm_TransJoinForm.Acc_unit_code.value="";
	    	            	  }
	    	            	
	    	            }
	    	            	
	    	           }
	    	        }
	    	    }
	    };
	    req.send(null);

}
