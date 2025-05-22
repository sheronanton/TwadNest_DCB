var winemp="";
var winjob="";
var checklist="";
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


function btnsubmit()
{

	 for(i=0;i<document.request1.check_list.length;i++)
     {
             if(document.request1.check_list[i].checked==true)
               checklist= checklist+document.request1.check_list[i].value +",";
                
             
     }

     // opener.document.HRE_EmployeeServiceDetails.txtEmployeeid.value = (v[i].value);
          //  try{self.opener.doFunction('loademp','null');}catch(e){}
           
           
          
            opener.CheckList(checklist);
            opener.focus();
            self.close();
            return true;
 }
       
   

  
 




function processResponse(req)
{   
  if(req.readyState==4)
    {
      if(req.status==200)
      {       
     
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          var command=tagCommand.firstChild.nodeValue; 
          if(command=="dispEmp")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
                 //alert("success");
                  document.request.Emp_Name.value=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
                  document.request.Emp_Ini.value=baseResponse.getElementsByTagName("initial")[0].firstChild.nodeValue;
                  document.request.Date_of_dth.value=baseResponse.getElementsByTagName("dth")[0].firstChild.nodeValue;
                  document.request.Desig_at_dth.value=baseResponse.getElementsByTagName("des")[0].firstChild.nodeValue;          
               }
              else
            	  alert('failure');
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
          else if(command=="Add")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {
            	  alert("inserted");
              }
              else
            	  alert("failure");
          }
          
      }
    }
}

