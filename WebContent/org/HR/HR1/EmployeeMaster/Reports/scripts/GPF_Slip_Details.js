//////////////   FOR EMPLOYEE POPUP WINDOW /////////////////////
//alert("hi");
var winemp;
var my_window;


function togetFocus()
{
   document.frmEmployeeProfile.Employee_Initial.focus();
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
   
    winemp= window.open("../../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee_Search_for_Emp_Editing","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmEmployeeProfile.txtEmpId1.value=emp;
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
   document.frmEmployeeProfile.EmpId.value="";
   document.frmEmployeeProfile.txtEmpId1.value="";
    document.frmEmployeeProfile.txtEmployee.value="";
   document.frmEmployeeProfile.txtGpf.value="";
   //document.frmEmployeeProfile.txtEmpId1.disabled=false;
   document.frmEmployeeProfile.txtEmpId1.focus();
  // alert('hello');
 }
 
 function bodyload()
 {
 document.frmEmployeeProfile.Employee_Initial.focus();
 }
 
 
 
function callServer(command,param)
{
      if(command=="Existg")
       {
       //alert("callserver");
       var strEmpId=document.frmEmployeeProfile.txtEmpId1.value;
     //  var StrGPF=
       var len=strEmpId.length;
       if(len==4)
            strEmpId='0'+strEmpId;
       if(len==3)     
            strEmpId='00'+strEmpId;
        if(len==2)     
            strEmpId='000'+strEmpId; 
        if(len==1)     
            strEmpId='0000'+strEmpId;     
         document.frmEmployeeProfile.gpf.value=strEmpId ;
       //startwaiting(document.frmEmployeeProfile) ; 
          url="../../../../../../Gpf_Slip_Details.av?Command=Existg&EmpGPF=" + strEmpId;
          
         // url="../../../../../../Gpf_Slip_Details.av?Command=Existg&EmpGPF=" + strEmpId;
          //alert(url);
          
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            } ;  
            req.send(null);
        
       }
       
        
}


function processResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          stopwaiting(document.frmEmployeeProfile) ;
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
              
          }
        }
  }

function existRow(baseResponse)
    {
	
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
             // alert(flag);
              if(flag=="success")
              { 
                 //alert("success");
                  document.frmEmployeeProfile.txtEmployee.value=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
       
                  
                  var gpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(gpf==0)
                    gpf="";
                  
                  document.frmEmployeeProfile.txtGpf.value=gpf;
                  document.frmEmployeeProfile.EmpId.value=document.frmEmployeeProfile.txtEmpId1.value
                
                 
                  
              }
              
              else if(flag=="failure")
              {
                alert("Given GPf is not exist");
                document.frmEmployeeProfile.txtEmpId1.focus();
                document.frmEmployeeProfile.txtEmpId1.value='';
              }
              
              else if(flag=="failurea")
              {
                alert("Given gpf is not in your controlling office");
                document.frmEmployeeProfile.txtEmpId1.focus();
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtGpf.value='';
              }
              
              else if(flag=="failureb")
              {
                alert("User doesn't have office id");
                document.frmEmployeeProfile.txtEmpId1.focus();
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtGpf.value='';
              }
              
              else if(flag=="failurec")
              {
                alert("Employee Id is not in your controlling office");
                document.frmEmployeeProfile.txtEmpId1.focus();
                document.frmEmployeeProfile.txtEmpId1.value='';
                document.frmEmployeeProfile.txtEmployee.value='';
                document.frmEmployeeProfile.txtGpf.value='';
              }
             
              else
             {
                     alert("Given Employee Id is not exist ");
                      document.frmEmployeeProfile.txtEmpId1.focus();
                      document.frmEmployeeProfile.txtEmpId1.value='';
                      document.frmEmployeeProfile.txtEmployee.value='';
                      document.frmEmployeeProfile.txtGpf.value='';
             }
              
     }         

function nullCheck()
{
   if(document.frmEmployeeProfile.txtEmployee.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmEmployeeProfile.txtEmployee.focus();
      return false;
   }
      else if(document.frmEmployeeProfile.txtEmployee.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmEmployeeProfile.txtEmployee.focus();
      return false;
   }
 /*  else if(document.frmEmployeeProfile.txtGpf.value=="")
   {
      alert("Please Enter the GPF Number");
      document.frmEmployeeProfile.txtGpf.focus();
      return false;
   }*/
  
   
   return true;
}


function toCheck()
{

 if(!isNaN(document.frmEmployeeProfile.txtEmployee.value))
   {
      alert("Employee Name must be in Character");
      document.frmEmployeeProfile.txtEmployee.value="";
      document.frmEmployeeProfile.txtEmployee.focus();
      return false;
   }
   
   return true;
}  

function toCheck1()
{
if(document.frmEmployeeProfile.Employee_Initial.value.length>0)
   {
     
         if(!isNaN(document.frmEmployeeProfile.Employee_Initial.value))
           {
              alert("Employee Initial must be in Character");
              document.frmEmployeeProfile.Employee_Initial.value="";
              document.frmEmployeeProfile.Employee_Initial.focus();
              return false;
           }
        }
   return true;
}

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false ;
        }
     }
     
function toFocus()
{
 //alert("Hai");
  //var FirstField=document.frmEmployeeProfile.txtEmpId1.value;
 if((document.frmEmployeeProfile.txtEmpId1.value=="") || (document.frmEmployeeProfile.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     document.frmEmployeeProfile.txtEmpId1.focus();
     return false;
  }
  return true;
   
}



function btnsubmit()
{
   
          var strEmpId=document.frmEmployeeProfile.txtEmpId1.value;
          
          if(document.frmEmployeeProfile.fin_year.selectedIndex==0){
       	 	  alert("Select Financial year");
           
              return false;
       	 }
   
       var len=strEmpId.length;
       if(len==4)
            strEmpId='0'+strEmpId;
       if(len==3)     
            strEmpId='00'+strEmpId;
        if(len==2)     
            strEmpId='000'+strEmpId; 
        if(len==1)     
            strEmpId='0000'+strEmpId;     
         document.frmEmployeeProfile.gpf.value=strEmpId ;
              document.frmEmployeeProfile.action="../../../../../../Gpf_Slip_Details.av";
              document.frmEmployeeProfile.method="POST";
              document.frmEmployeeProfile.submit();
              return true;
     
}

