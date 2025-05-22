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
	var url="../../../../../Hrm_Emp_Aadhar_updation?command=getemp_details&emp_id="+emp_id;
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
   							
   							document.getElementById("Employee_Name").value=empname;
   							document.getElementById("desig").value=designation;
   							document.getElementById("dob").value=edob;
   							getaadher();
   							
   							
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
	
	 var Aadher_no=document.getElementById("Aadher_no").value;
	   var Aadher_no1=document.getElementById("Aadher_no1").value;
	   var Aadher_no2=document.getElementById("Aadher_no2").value;
	   
   if(document.getElementById("txtEmpId").value=="" || document.getElementById("txtEmpId").value==0)
	   {
	   alert("Please Enter Employee Id");
	   document.getElementById("txtEmpId").focus();
	   return false;
	   }
  
   if(Aadher_no.length==4)
   		{
   		Aadher_no=Aadher_no;
   		}
   	else
   		{
   		alert("please enter valid Aadher No! Aadher No Should Be 4 Digits in Each Box!");
   		document.getElementById("Aadher_no").focus();
   		document.getElementById("Aadher_no").value="";
   		
   		return false;
   		}
   
     if(Aadher_no1.length==4)
   	{
   		Aadher_no1=Aadher_no1;
   	}
   else
   	{
   	alert("please enter valid Aadher No! Aadher No Should Be 4 Digits in Each Box!");
   	document.getElementById("Aadher_no1").focus();
   	document.getElementById("Aadher_no1").value="";
   	
   	return false;
   	}
   	if(Aadher_no2.length==4)
   	{
   		Aadher_no2=Aadher_no2;
   	}
   else
   	{
   	alert("please enter valid Aadher No! Aadher No Should Be 4 Digits in Each Box!");
   	document.getElementById("Aadher_no2").focus();
   	document.getElementById("Aadher_no2").value="";
   	
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
		document.getElementById("Aadher_no").value="";
		document.getElementById("Aadher_no1").value="";
		document.getElementById("Aadher_no2").value="";
	
}
function update_det()
{
	
	var val=nullCheck();
	
	if(val==true )
		{
		
	var emp_id=document.getElementById("txtEmpId").value;
	var Aadher_no=document.getElementById("Aadher_no").value;
	
	
	var Aadher_no1=document.getElementById("Aadher_no1").value;
	var Aadher_no2=document.getElementById("Aadher_no2").value;
	//var concate1 = Aadher_no.concat(Aadher_no1); 
	//var final_aadherno=concate1.concat(Aadher_no2); 
	//var url="../../../../../Hrm_Emp_Aadhar_updation?command=Update_aadher&emp_id="+emp_id+"&final_aadherno="+final_aadherno;
	var url="../../../../../Hrm_Emp_Aadhar_updation?command=Update_aadher&emp_id="+emp_id+"&Aadher_no="+Aadher_no+"&Aadher_no1="+Aadher_no1+"&Aadher_no2="+Aadher_no2;
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
              
               if(command=="Update_aadher")
               { 
            	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  
   					if(flag=="success")
   						{
   						alert("Successfully updated ");
   						document.getElementById("txtEmpId").value="";
   						document.getElementById("Employee_Name").value="";
   						document.getElementById("desig").value="";
   						document.getElementById("dob").value="";
   						document.getElementById("Aadher_no").value="";
   						document.getElementById("Aadher_no1").value="";
   						document.getElementById("Aadher_no2").value="";
   						}
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
	var url="../../../../../Hrm_Emp_Aadhar_updation?command=getaadher&emp_id="+emp_id;
	//alert(url);
	 var req=getTransport();
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
    	 if(req.readyState==4)
         {
           if(req.status==200)
           {      
              alert(req.responseText)
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               var tagCommand=baseResponse.getElementsByTagName("command")[0];
               var command=tagCommand.firstChild.nodeValue; 
              
               if(command=="getaadher")
               { 
            	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	  
   					if(flag=="success")
   						{
   						
   						var AADHAR_NO=baseResponse.getElementsByTagName("AADHAR_NO")[0].firstChild.nodeValue;
   						var AADHAR_NO=baseResponse.getElementsByTagName("EAADHAR_NO")[0].firstChild.nodeValue;
   						alert(baseResponse.getElementsByTagName("AADHAR_NO")[0].firstChild.nodeValue);
   						var flags=baseResponse.getElementsByTagName("flags")[0].firstChild.nodeValue;
   						alert(flags);
							var  res = AADHAR_NO.substring(-1, 4);
							var  res1 = AADHAR_NO.substring(4, 8);
							var  res2 = AADHAR_NO.substring(8, 12);
							
							if(res==0 || res=="null" || res==null)
								{
								res="";
								}
							alert(res);
							document.getElementById("Aadher_no").value=res;
							document.getElementById("Aadher_no1").value=res1;
							document.getElementById("Aadher_no2").value=res2;
						/*	if(AADHAR_NO !=" " || AADHAR_NO>0)
								{
								if(flags=="Y")
									 {
									 document.getElementById("cmbupdate").disabled=false;
									 }
								 else if(flags=="N")
									 {
									 document.getElementById("cmbupdate").disabled=true;
								//	 }
								
							        
								}
							else
								{
								    document.getElementById("cmbupdate").disabled=false;
								}*/
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

