//////////////   FOR JOB POPUP WINDOW //////////////////////
//alert("test");


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


function Check_Date_Difference()
{
	var dob=document.getElementById("Date_Of_Birth").value;
	 var retire=document.getElementById("Date_Of_Retirment").value;
	 var dateofbirth=dob.split("/");
	 var retirement=retire.split("/");	 
	 
	 var year_60th=parseInt(dateofbirth[2])+60;
	alert(year_60th);
	 
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
			 alert('Retirement Month should not be exceed birthday month');
			 document.getElementById("retire").value="";
			 return false;
		 }
	 }
	 
return true;
}

function callUpdateFunction(command,param)
{
	
	
    if(command=="toValidate")
       {
    	
       
           var strEmpId=document.frmEmployee.txtEmpId1.value;
       var strhEmpId=document.frmEmployee.EmpId.value;
       var strEmpPref=document.frmEmployee.Employee_Prefix.value;
       var strEmpInit=document.frmEmployee.Employee_Initial.value;
       var strEmpName=document.frmEmployee.Employee_Name.value;
       var strEmpGpf=document.frmEmployee.Gpf_Number.value;
        var hstrEmpId=document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value;;
                  var strDob=document.frmEmployee.Date_Of_Birth.value;
                  var strDoR=document.frmEmployee.Date_Of_Retirment.value;
                  

               	 var dateofbirth=strDob.split("/");
               	 var retirement=strDoR.split("/");	 
                   
                 var year_60th=parseInt(dateofbirth[2])+60;
                  
                  var strGender;
                  if(document.frmEmployee.Gender[0].checked)
                  {
                       strGender=document.frmEmployee.Gender[0].value;
                       }
                  else
                  {
                       strGender=document.frmEmployee.Gender[1].value;    
                       }
               
                  var strCommunity=document.frmEmployee.Community.value;
                  var strDistrict=document.frmEmployee.Native_District.value;
                  var strTaluk=document.frmEmployee.Native_Taluk.value;
                  var strOthers=document.frmEmployee.txtOther.value;
                  var strOtherState=document.frmEmployee.txtOtherState.value;
                  
               
                  var strQual=document.frmEmployee.Qualification.value;
                  var strEmpmStatus=document.frmEmployee.Post_Status.value;
                 
                  var strDesig=document.frmEmployee.Designation.value;
                  var strEmpeStatus=document.frmEmployee.Employee_Status.value;
                  var strMarital;
                  if(document.frmEmployee.Marital_Status[0].checked==true)
                      strMarital=document.frmEmployee.Marital_Status[0].value;
                  else
                      strMarital=document.frmEmployee.Marital_Status[1].value;
                  var Handicapped;
                  if(document.frmEmployee.handicapped[0].checked==true)
                      Handicapped=document.frmEmployee.handicapped[0].value;
                  else
                      Handicapped=document.frmEmployee.handicapped[1].value;    
                 var getting;
                  if(document.frmEmployee.getting[0].checked==true)
                      getting=document.frmEmployee.getting[0].value;
                  else
                      getting=document.frmEmployee.getting[1].value;  
                  var consolid;
                  if(document.frmEmployee.consolid[0].checked==true)
                	  consolid=document.frmEmployee.consolid[0].value;
                  else
                	  consolid=document.frmEmployee.consolid[1].value;  
                  var strRemarks=document.frmEmployee.Remarks.value;
                  
                  if(strRemarks==null)
                  {
                     strRemarks="Value Not Entered";
                  }
                  else
                   strRemarks=strRemarks;
                  
                var probation;
                  if(document.frmEmployee.optprobation[0].checked==true)
                      probation=document.frmEmployee.optprobation[0].value;
                  else
                      probation=document.frmEmployee.optprobation[1].value;
                    
                var dateOfRegPro=document.frmEmployee.Date_Of_Reg_Pro.value;
                var reg_date=document.frmEmployee.reg_date.value;
               
                var check=nullCheck(null);
                if(check)
                {
                	if(retirement[2]>year_60th)
                	 {
                		 alert('Retirement date does not exceed 60 years (from date of birth)');
                		document.frmEmployee.Date_Of_Retirment.value="";
                		return false;
                		
                	 }
                	 else if(retirement[2]==year_60th)
                	 {
                		 if(dateofbirth[1]<retirement[1])
                		 {
                			 alert('Retirement month should not be exceed birthday month');
                			document.frmEmployee.Date_Of_Retirment.value="";
                			return false;
                		 }
                	 }
                	  if(strDistrict!=999)
                  {
                     startwaiting(document.frmEmployee) ; 
          url="../../../../../New_Validate_EmpDet_Servlet.view?command=toValidate&EmpId="+hstrEmpId+"&EmpDOB="+strDob+"&EmpGender="+strGender+"&Comm="+strCommunity+"&District="+strDistrict+"&Taluk="+strTaluk+"&EmpQual="+strQual+"&EmpmStatus="+strEmpmStatus+"&EmpDesig="+strDesig+"&EmpeStatus="+strEmpeStatus+"&EmpMarital="+strMarital+"&Remarks="+strRemarks;
          url=url+"&probation="+probation+"&dateOfRegPro="+dateOfRegPro+"&getting="+getting+"&Handicapped="+Handicapped+"&Consolid="+consolid+"&reg_date="+reg_date+"&retirement="+strDoR;
         // alert(url);
             var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processUpdateHandleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    }
                    else
                    {
                    startwaiting(document.frmEmployee) ; 
                        url="../../../../../New_Validate_EmpDet_Servlet.view?command=toValidate&EmpId="+hstrEmpId+"&EmpDOB="+strDob+"&EmpGender="+strGender+"&Comm="+strCommunity+"&District="+strDistrict+"&EmpQual="+strQual+"&EmpmStatus="+strEmpmStatus+"&EmpDesig="+strDesig+"&EmpeStatus="+strEmpeStatus+"&EmpMarital="+strMarital+"&Remarks="+strRemarks+ "&Others=" +strOthers+ "&OtherState=" +strOtherState;
                        url=url+"&probation="+probation+"&dateOfRegPro="+dateOfRegPro+"&getting="+getting+"&Handicapped="+Handicapped+"&Consolid="+consolid+"&reg_date="+reg_date+"&retirement="+strDoR;
         // alert(url);
             var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processUpdateHandleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    
                    }
            }  
         }   
     
                    
        }

function processUpdateHandleResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {            
          stopwaiting(document.frmEmployee) ;
          
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             //alert(baseResponse);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
               if(command=="toValidate")
              {
                //alert("IN PRO REPS");
                  validateRow(baseResponse);                 
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
             
               }
        }
  }
 
function validateRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              //alert(flag);
              if(flag=="success")
              {
                 
                 var EmpId=document.frmEmployee.EmpId.value;
               
                 if(confirm("Records are updated Successfully - Do You want to Add or Update Your Photo"))
                 {
                     my_window= window.open("ImageSession.jsp?Command=init&Emp="+EmpId,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
                     my_window.moveTo(250,250); 
                     my_window.focus();
                 }
                  clearAll();
                  if (my_window && my_window.open && !my_window.closed)  my_window.focus();
              }
              
              else
              {
                 alert("Failed to Update Records");
              }
}    

