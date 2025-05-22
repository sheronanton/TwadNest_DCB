//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
//alert('asdfasdfasdfkkk');
function servicepopup1()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
       return;
    }
    else
    {
        winemp=null
    }
     startwaiting(document.frmEmployee) ;   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function servicepopup()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
     
    //winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee Selection for New UpdateEmployee","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
     winemp= window.open("EmpServicePopup.jsp","mywindow_NECO","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function doParentEmp(emp)
{
document.getElementById("txtEmpId").value=emp;
getemp_details();

}



///////////////////////////////////////////////////////////////////////////////////


//////////////   FOR JOB POPUP WINDOW //////////////////////



var winjob;

function jobpopup1()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_NECO","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
 
    winjob.focus();
    
}
function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed) 
         winjob.officeSelection(true,true,true,false);
}



window.onunload=function()
{
//alert('hello');
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}

 //***************************************************************************//

function getTransport()
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

function getemp_details()
{
	var emp_id=document.getElementById("txtEmpId").value;
	var url="../../../../../Hrm_Emp_Retirement_Updation?command=getemp_details&emp_id="+emp_id;
	//alert(url);
	 var req=getTransport();
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
    	 if(req.readyState==4)
         {
           if(req.status==200)
           {      
              //alert(req.responseText)
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               var tagCommand=baseResponse.getElementsByTagName("command")[0];
               var command=tagCommand.firstChild.nodeValue; 
              
               if(command=="getemp_details")
               { 
            	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  
   					if(flag=="success")
   						{
   							var empname=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
   							var designation=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
   							var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
   							var retire_tobe=baseResponse.getElementsByTagName("retire_tobe")[0].firstChild.nodeValue;
   							//alert(baseResponse.getElementsByTagName("retire")[0].firstChild.nodeValue);
   							var retire_date=baseResponse.getElementsByTagName("retire")[0].firstChild.nodeValue;
   							var remarkss=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
   							var process_flow_id=baseResponse.getElementsByTagName("process_flow_id")[0].firstChild.nodeValue;
   							var employee_status_id=baseResponse.getElementsByTagName("employee_status_id")[0].firstChild.nodeValue;
   							var retire_tobe=baseResponse.getElementsByTagName("retire_tobe")[0].firstChild.nodeValue;
   							//alert(retire_tobe);
   						   if(retire_tobe=='null'||retire_tobe==null)
   							retire_tobe="";
   							if(remarkss=='null')
   								remarkss='';
   							//alert(eretire);
   							if(employee_status_id=='MEV' || employee_status_id=='VRS' || employee_status_id=='SAN' || employee_status_id=='DTH' || employee_status_id=='CMR' || employee_status_id=='RES' || employee_status_id=='DIS')
   							{
   								alert(' This employee is Retired already' );
   								document.getElementById("txtEmpId").value="";
   		   						document.getElementById("Employee_Name").value="";
   		   						document.getElementById("desig").value="";
   		   						document.getElementById("dob").value="";
   		   						document.getElementById("retire").value="";
   		   						document.getElementById("remarks").value="";
   							}else
   								{
   								document.getElementById("Employee_Name").value=empname;
   	   							document.getElementById("desig").value=designation;
   	   							document.getElementById("dob").value=edob;
   	   							document.getElementById("retire").value=retire_tobe;
   	   						    document.getElementById("retire_tobe").value=retire_date;
   	   							document.getElementById("remarks").value=remarkss;
	   	   							//getaadher();
	   	   							if(process_flow_id=='FR')
	   	   								{
	   	   								alert('This Employee is already validated...');
	   	   								document.getElementById("Validate").disabled=true;
	   	   								document.getElementById("cmbValidate").disabled=true;
	   	   								//document.getElementById("cmbValidate").style.display="block";
	   	   								}
	   	   							else
	   	   								{
	   	   								document.getElementById("Validate").disabled=false;
	   	   								document.getElementById("cmbValidate").disabled=false;
	   	   								}
   	   							
   								}
   							
   							
   						}
   					 else if(flag=="failure")
   						{
   						alert("Please Enter Valid Employee Id");
   						
   						}
   						else if(flag=="failure1")
   						{
   						    alert("Given Employee Id is not in your controlling office");
   						    document.getElementById("txtEmpId").value="";
   						    document.getElementById("Employee_Name").value="";
							document.getElementById("desig").value="";
							document.getElementById("dob").value="";
   						}
   					
               }
            }
         }
     }
     req.send(null);
}
function movetoNext(current, nextFieldID) {
	if (current.value.length >= current.maxLength) {
	document.getElementById(nextFieldID).focus();
	}
	}

function nullCheck()
{
	//alert("hhhh....");
	
	
	//var emp_id=document.getElementById("txtEmpId").value;
	//var retireDate=document.getElementById("retire").value;
	//var remarks=document.getElementById("remarks").value;
	var dob=document.getElementById("dob").value;
	 var retire=document.getElementById("retire").value;
	 var remarks=document.getElementById("remarks").value;
	 
	 var dateofbirth=dob.split("/");
	 var retirement=retire.split("/");	 
	 
	 var year_60th=parseInt(dateofbirth[2])+60;
	
	 
	 if(retirement[2]>year_60th)
	 {
		 alert('Retirement date does not exceed 60 years (from date of birth)');
		 document.getElementById("retire").value="";
		 return false;
	 }
	 else if(retirement[2]==year_60th)
	 {
		 if(dateofbirth[1]<retirement[1])
		 {
			 alert('Retirement month should not be exceed birthday month');
			 document.getElementById("retire").value="";
			 return false;
		 }
	 }
	 
	 else if(remarks==null || remarks=="")
		 {
		 alert("Please Enter the Remarks");
		 return false;
		 }
	   
	 else if(document.getElementById("txtEmpId").value=="" || document.getElementById("txtEmpId").value==0)
	   {
	   alert("Please Enter Employee Id");
	   document.getElementById("txtEmpId").focus();
	   return false;
	   }
  
	 else if(retire.length==10)
   		{
	   retire=retire;
   		}
   	else
   		{
   		alert("Please enter valid Retire Date!");
   		document.getElementById("retire").focus();
   	
   		
   		return false;
   		}
   
 
   return true;
}   
function clearAll()
{

	document.getElementById("txtEmpId").value="";
		document.getElementById("Employee_Name").value="";
		document.getElementById("desig").value="";
		document.getElementById("dob").value="";
		document.getElementById("retire_tobe").value="";
		document.getElementById("Aadher_no").value="";
		document.getElementById("Aadher_no1").value="";
		document.getElementById("Aadher_no2").value="";
	
}

function Vlidate_det()
{

	
	var val=nullCheck();
	
	if(val==true )
		{
		
		var emp_id=document.getElementById("txtEmpId").value;
		var retireDate=document.getElementById("retire").value;
		var remarks=document.getElementById("remarks").value;
		var dob=document.getElementById("dob").value;
		var retire_tobe=document.getElementById("retire_tobe").value;
	
	
	//var concate1 = Aadher_no.concat(Aadher_no1); 
	//var final_aadherno=concate1.concat(Aadher_no2); 
	//var url="../../../../../Hrm_Emp_Retirement_Updation?command=Update_aadher&emp_id="+emp_id+"&final_aadherno="+final_aadherno;
	var url="../../../../../Hrm_Emp_Retirement_Updation?command=Validate_retireDate&emp_id="+emp_id+"&retireDate="+retireDate+"&remarks="+remarks+"&dob="+dob+"&retire_tobe="+retire_tobe;
	//alert(url);
	 var req=getTransport();
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
    	 if(req.readyState==4)
         {
           if(req.status==200)
           {      
              
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               var tagCommand=baseResponse.getElementsByTagName("command")[0];
               var command=tagCommand.firstChild.nodeValue; 
              
               if(command=="Validate_retireDate")
               { 
            	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  
   					if(flag=="success")
   						{
   						alert("Successfully Validated ");
   						document.getElementById("txtEmpId").value="";
   						document.getElementById("Employee_Name").value="";
   						document.getElementById("desig").value="";
   						document.getElementById("dob").value="";
   						document.getElementById("retire").value="";
   						document.getElementById("remarks").value="";
   						document.getElementById("retire_tobe").value="";
   						}
               }
               
           }
         }
       }
     req.send(null);
		}
		
    

}
function update_det()
{
	
	var val=nullCheck();
	
	if(val==true )
		{
		
	var emp_id=document.getElementById("txtEmpId").value;
	var retireDate=document.getElementById("retire").value;
	var remarks=document.getElementById("remarks").value;
	var dob=document.getElementById("dob").value;
	var retire_tobe=document.getElementById("retire_tobe").value;
	
	//var concate1 = Aadher_no.concat(Aadher_no1); 
	//var final_aadherno=concate1.concat(Aadher_no2); 
	//var url="../../../../../Hrm_Emp_Retirement_Updation?command=Update_aadher&emp_id="+emp_id+"&final_aadherno="+final_aadherno;
	var url="../../../../../Hrm_Emp_Retirement_Updation?command=Update_retireDate&emp_id="+emp_id+"&retireDate="+retireDate+"&remarks="+remarks+"&dob="+dob+"&retire_tobe="+retire_tobe;
	//alert(url);
	 var req=getTransport();
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
    	 if(req.readyState==4)
         {
           if(req.status==200)
           {      
              
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               var tagCommand=baseResponse.getElementsByTagName("command")[0];
               var command=tagCommand.firstChild.nodeValue; 
              
               if(command=="Update_retireDate")
               { 
            	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  
   					if(flag=="success")
   						{
   						alert("Successfully updated ");
   						document.getElementById("txtEmpId").value="";
   						document.getElementById("Employee_Name").value="";
   						document.getElementById("desig").value="";
   						document.getElementById("dob").value="";
   						document.getElementById("retire").value="";
   						document.getElementById("remarks").value="";
   						document.getElementById("retire_tobe").value="";
   						}
   					else
   						if(flag=="failure")
   							alert("data has not updated");
               }
               
           }
         }
       }
     req.send(null);
		}
		
    
}

function getaadher()
{
	var emp_id=document.getElementById("txtEmpId").value;
	var url="../../../../../Hrm_Emp_Retirement_Updation?command=getaadher&emp_id="+emp_id;
	//alert(url);
	 var req=getTransport();
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
    	 if(req.readyState==4)
         {
           if(req.status==200)
           {      
              //alert(req.responseText)
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               var tagCommand=baseResponse.getElementsByTagName("command")[0];
               var command=tagCommand.firstChild.nodeValue; 
              
               if(command=="getaadher")
               { 
            	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  
   					if(flag=="success")
   						{
   						
   						var AADHAR_NO=baseResponse.getElementsByTagName("AADHAR_NO")[0].firstChild.nodeValue;
						
   						var flags=baseResponse.getElementsByTagName("flags")[0].firstChild.nodeValue;
   						//alert(flags)
							var  res = AADHAR_NO.substring(-1, 4);
							var  res1 = AADHAR_NO.substring(4, 8);
							var  res2 = AADHAR_NO.substring(8, 12);
							
							if(res==0 || res=="null" || res==null)
								{
								res="";
								}
							
							document.getElementById("Aadher_no").value=res;
							document.getElementById("Aadher_no1").value=res1;
							document.getElementById("Aadher_no2").value=res2;
							if(AADHAR_NO !=" " || AADHAR_NO>0)
								{
								 if(flags=="Y")
									 {
									 document.getElementById("cmbupdate").disabled=false;
									 }
								 else if(flags=="N")
									 {
									 document.getElementById("cmbupdate").disabled=true;
									 }
								
							        
								}
							else
								{
								    document.getElementById("cmbupdate").disabled=false;
								}
   						}
               }
           }
         }
     }
req.send(null);
}

function numbersonly1(e, t) {
	//alert("t.length "+t.value.length);
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {			
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}	
}
     
//This Coding for Date Validation and Checking     
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

