//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
//alert('kkk');


function togetFocus()
{
   document.frmEmployee.Employee_Initial.focus();
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
   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpBasicPopup.jsp","Employee_Search_for_Emp_Editing","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer('Existg','null');

}
//this is the function to close the employee popup windows...
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

}

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
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
 
  function clearAll()
 {
   document.frmEmployee.EmpId.value="";
   document.frmEmployee.txtEmpId1.value="";
   document.frmEmployee.Employee_Prefix.selectedIndex=0;
   document.frmEmployee.Employee_Initial.value="";
   document.frmEmployee.Employee_Name.value="";
   document.frmEmployee.Gpf_Number.value="";
   document.frmEmployee.txtEmpId1.disabled=false;
   document.frmEmployee.txtEmpId1.focus();
   document.frmEmployee.Consolidate[0].checked=true;
   document.frmEmployee.Consolidate[0].checked=false;
  // alert('hello');
 }
 
 function bodyload()
 {
 document.frmEmployee.Employee_Initial.focus();
 }
 
 
 
function callServer(command,param)
{
      if(command=="Existg")
       {
            
       var strEmpId=document.frmEmployee.txtEmpId1.value;
       startwaiting(document.frmEmployee) ; 
          url="../../../../../InsertEmployee1.con?command=Existg&EmpId=" + strEmpId;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            };   
                   if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        
       }
       else if(command=="Update")
       {
     
       var strhEmpId=document.frmEmployee.EmpId.value;
       var strEmpPref=document.frmEmployee.Employee_Prefix.value;
       var strEmpInit=document.frmEmployee.Employee_Initial.value.toUpperCase();
       var strEmpName=document.frmEmployee.Employee_Name.value;
       var strEmpGpf=document.frmEmployee.Gpf_Number.value;
       var consolidate;
       if(document.frmEmployee.Consolidate[0].checked==true)
         consolidate='Y';
       else
          consolidate='N';
       var flag=nullCheck1();
          if(flag==true)
          {
          startwaiting(document.frmEmployee) ; 
            url="../../../../../InsertEmployee1.con?command=Update&EmpId=" + strhEmpId + "&EmpPref=" + strEmpPref + "&EmpInit=" + strEmpInit + "&EmpName=" + strEmpName +"&EmpGpf=" + strEmpGpf+"&consolidate="+consolidate;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                  if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
           }         
       }
       else if(command=="Verify")
       {
       //alert('test');
       var strhEmpId=document.frmEmployee.EmpId.value;
       var strEmpPref=document.frmEmployee.Employee_Prefix.value;
       var strEmpInit=document.frmEmployee.Employee_Initial.value.toUpperCase();
       var strEmpName=document.frmEmployee.Employee_Name.value.toUpperCase();
       var strEmpGpf=document.frmEmployee.Gpf_Number.value;
       var consolidate;
       if(document.frmEmployee.Consolidate[0].checked==true)
         consolidate='Y';
       else
          consolidate='N';
       var flag=nullCheck1();
          if(flag==true)
          {
          startwaiting(document.frmEmployee) ; 
            url="../../../../../InsertEmployee1.con?command=Verify&EmpId=" + strhEmpId + "&EmpPref=" + strEmpPref + "&EmpInit=" + strEmpInit + "&EmpName=" + strEmpName +"&EmpGpf=" + strEmpGpf+"&consolidate="+consolidate;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
           }         
       }
        else if(command=="Delete")
       {
       //alert('test');
       var strhEmpId=document.frmEmployee.EmpId.value;
      
        if(document.frmEmployee.txtEmpId1.value=="")
       {
          alert("Please Enter the Employee Id");
          document.frmEmployee.txtEmpId1.focus();
          return false;
       }
          if(confirm("Do you want to Delete this record?"))
          {
          startwaiting(document.frmEmployee) ; 
            url="../../../../../InsertEmployee1.con?command=Delete&EmpId=" + strhEmpId ;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                     if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
           }   
          
       }
        else if(command=="Add")
       {
         
       var strEmpPref=document.frmEmployee.Employee_Prefix.value;
       var strEmpInit=document.frmEmployee.Employee_Initial.value.toUpperCase();
       var strEmpName=document.frmEmployee.Employee_Name.value.toUpperCase();
     //  alert(document.frmEmployee.Gpf_Number.value);
       var strEmpGpf
       if(document.frmEmployee.Gpf_Number.value==null ||document.frmEmployee.Gpf_Number.value=='null'||document.frmEmployee.Gpf_Number.value=='')
         strEmpGpf=0;
       else
         strEmpGpf=document.frmEmployee.Gpf_Number.value;
       var consolidate;
       if(document.frmEmployee.Consolidate[0].checked==true)
         consolidate='Y';
       else
          consolidate='N';
          var flag=nullCheck();
          if(flag==true)
          {
          startwaiting(document.frmEmployee) ; 
            url="../../../../../InsertEmployee1.con?command=Add&EmpPref=" + strEmpPref + "&EmpInit=" + strEmpInit + "&EmpName=" + strEmpName +"&EmpGpf=" + strEmpGpf+"&consolidate="+consolidate;
           // alert(url);
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
           }
           
       }
}


function processResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          stopwaiting(document.frmEmployee) ;
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Existg")
              {
            	    existRow(baseResponse);                 
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
              else if(command=="Update")
              {
                   updateRow(baseResponse);
              }
              else if(command=="Verify")
              {
                   verifyRow(baseResponse);
              }
               else if(command=="Delete")
              {
                   deleteRow(baseResponse);
              }
              else if(command=="Add")
              {//alert("addrw");
                   addRow(baseResponse);
              }
              else if(command=="error")
              {//alert("addrw");
                   error(baseResponse);
              }
             
          }
        }
  }
function error(baseResponse)
{
      alert("Invalid Number");
}
function existRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
                   document.frmEmployee.Employee_Prefix.value=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue;
                 //alert(baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue);
                  if(baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue!='null')
                    document.frmEmployee.Employee_Initial.value=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                else
                    document.frmEmployee.Employee_Initial.value="";
                    document.frmEmployee.Employee_Name.value=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  var gpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(gpf==0)
                    gpf="";
                  var consolidate=baseResponse.getElementsByTagName("consolid")[0].firstChild.nodeValue;
                  if(consolidate=='Y')
                        document.frmEmployee.Consolidate[0].checked=true;
                  else
                        document.frmEmployee.Consolidate[1].checked=true;
                  document.frmEmployee.Gpf_Number.value=gpf;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.disabled=true;
                  
              }
              else if(flag=="frezeed")
                    {
                      alert("Given Temporary Employee Id has already been Validated ");
                      document.frmEmployee.txtEmpId1.focus();
                      document.frmEmployee.txtEmpId1.value='';
                     // alert('test');
                      return;
                    }
             else if(flag=="delete")
            {
              alert("Given Temporary Employee Id has already been Deleted");
              document.frmEmployee.txtEmpId1.focus();
              document.frmEmployee.txtEmpId1.value='';
             // alert('test');
              return;
            }
              else
             {
                     alert("Given Employee Id is not exist ");
                      document.frmEmployee.txtEmpId1.focus();
                      document.frmEmployee.txtEmpId1.value='';
             }
              
     }         
function deleteRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              { 
                //alert("Record is deleted Successfully");
                //clearAll();
                 var b;
                var msg="Employee detail is deleted Successfully";
                var head="Delete Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              
              }
              else if(flag="failure1")
              {
                 // alert("The Employee have a post. So first relieve the post.");
                  var b;
                var msg="The Employee have a post. So first relieve the post.";
                var head="Delete Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              }
              else
              {
                //alert("Failed to delete Record");
                 var b;
                var msg="Employee detail is not deleted Successfully";
                var head="Delete Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              }
   } 


function updateRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              { 
               // alert("Records are updated Successfully");
               // clearAll();
                var b;
                var msg="Employee detail is updated Successfully";
                var head="Update Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              return;
              }
              else if(flag="ExistRec")
              {
                 egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                 alert("Give Some other GPF Number-"+egpf+"- This Number is already in use");
                 
              }
              else
              {
                //alert("Failed to update Records");
                 var b;
                var msg="Employee detail is not updated Successfully";
                var head="Update Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              }
   } 
   
   function verifyRow(baseResponse)
    {
              //alert('test');
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
          //    alert("flag"+flag);
              if(flag=="success")
              { 
                //alert("Records are verified Successfully");
               // clearAll();
               eid=baseResponse.getElementsByTagName("EmpId")[0].firstChild.nodeValue; 
                var b;
                var msg="Employee detail is Validated Successfully. Employee Id assigned is "+eid;
                var head="Validate Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              
              }
              else if(flag=="ExistRec")
              {
                 egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                 alert("Give Some other GPF Number-"+egpf+"- This Number is already in use");
              }
              else 
              {
                
                 var b;
                var msg="Employee detail is not  Validated ";
                var head="Validate Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
     }
              
   } 
   
function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              { 
                //alert("Records are inserted Successfully");
                 eid=baseResponse.getElementsByTagName("EmpId")[0].firstChild.nodeValue; 
                  /*    alert("Submitted Temporary Employee Details Id is: " + eid);
                      document.frmEmployee.Employee_Prefix.selectedIndex=0;
                      document.frmEmployee.Employee_Initial.value="";
                      document.frmEmployee.Employee_Name.value="";
                      document.frmEmployee.Gpf_Number.value="";
                //clearAll();
                */
                 var b;
                var msg="Employee detail is created.\n Temporary Employee Id assigned is: " + eid;
                var head="Create Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              
              }
              else if(flag="ExistRec")
              {
                  egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                 alert("Give Some other GPF Number-"+egpf+"- This Number is already in use");
              }
              else
              {
                //alert("Failed to insert Records");
                var b;
                var msg="Employee detail is not created";
                var head="Create Employee's Basic Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
              }

}

function nullCheck()
{
   if(document.frmEmployee.Employee_Name.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
      else if(document.frmEmployee.Employee_Name.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
 /*  else if(document.frmEmployee.Gpf_Number.value=="")
   {
      alert("Please Enter the GPF Number");
      document.frmEmployee.Gpf_Number.focus();
      return false;
   }*/
  
   
   return true;
}

function nullCheck1()
{
   if(document.frmEmployee.txtEmpId1.value=="")
   {
      alert("Please Enter the Employee Id");
      document.frmEmployee.txtEmpId1.focus();
      return false;
   }
   
   else if(document.frmEmployee.Employee_Name.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
   else if(document.frmEmployee.Employee_Name.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
  
   
   return true;
}

function toCheck()
{

 if(!isNaN(document.frmEmployee.Employee_Name.value))
   {
      alert("Employee Name must be in Character");
      document.frmEmployee.Employee_Name.value="";
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
   
   return true;
}  

function toCheck1()
{
if(document.frmEmployee.Employee_Initial.value.length>0)
   {
     
         if(!isNaN(document.frmEmployee.Employee_Initial.value))
           {
              alert("Employee Initial must be in Character");
              document.frmEmployee.Employee_Initial.value="";
              document.frmEmployee.Employee_Initial.focus();
              return false;
           }
        }
   return true;
}


     
function toFocus()
{
 //alert("test");
  //var FirstField=document.frmEmployee.txtEmpId1.value;
 if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     document.frmEmployee.txtEmpId1.focus();
     return false;
  }
   
}

     
