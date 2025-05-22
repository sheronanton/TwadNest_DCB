
var role_value='';
var desigval='';
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if ((charCode > 47 && charCode < 58)||(charCode ==45)||(charCode ==8))
          {                
              return true;
          }
	else
	{
		alert("Enter Only Numbers");
          return false;
	}
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



 function checking()
 {
	 var fr=parseInt(document.assigtemplatetouser.fromtxt.value);
	    var tto=parseInt(document.assigtemplatetouser.totxt.value);
	    if(fr>tto)
	    {
	    	alert("Enter Valid Range");
	    	clearAll();
	    	return false;
	    }
	    var l=tto-fr;
	      if(l>1000)
	      {
	    	  alert("Range Limit should not be exceed 1000.\n For a Faster Process");
	    	  clearAll();
	    	  return false;
	      }
 }

function frmtemprevoke()
{

	var from=document.assigtemplatetouser.fromtxt.value;
    var to=document.assigtemplatetouser.totxt.value;
    var template = document.getElementById("template").value;
    if(from=="")
    {
    	alert("please enter Fromemployee ID");
    	document.assigtemplatetouser.fromtxt.focus();
    	return false;
    }
    else if(to=="")
    {
    	alert("please enter To employee ID");
    	document.assigtemplatetouser.totxt.focus();
    	return false;
    }
    else if(template==0)
    {
    	alert("please select Template");
    	document.getElementById("template").focus();
    	return false;
    }
    else
    {
    	
  
     
     // alert('inside submit 2');
      
      var url="../../../../../Role_template_to_user?Command=remove&from=" + from +"&to="+to+"&template_id="+template ;
       
   req.open("GET",url,true);       
    req.onreadystatechange=function()
    {
     if(req.readyState==4)
            {
    	 document.body.style.cursor = "default";
 		
 		document.getElementById("PHPDATA").innerHTML="";
            if(req.status==200)
                 { 
                      
                     var response=req.responseXML.getElementsByTagName("response")[0];
                    
                     var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                     
                   // stopwaiting(document.assigtemplatetouser);
                              if (flag=="Noemp" || flag=="fail")
                                   alert("Template deletion failure  ");
                                   
                                
                                if(flag=="success")
                                {
                                    alert("Template are deletion to users Successfully");
                                    clearAll();
                                }
                              
                                
                                
                            
                            }
                        }
     else
     {
    	 document.body.style.cursor = "wait";
 		var temp ="<br/><table align=center CELLPADDING=0 CELLSPACING=0 WIDTH=300><tr><td align=center><FONT FACE=Arial SIZE=4 COLOR=black><b>Processing please wait</b></font></td></tr><tr><td align=center><img width=250 height=40 src=../../../../../images/loading_bar.gif /></td></tr></table>";
 		document.getElementById("PHPDATA").innerHTML=temp;
     }
                    }
                       
                        req.send(null);   
                       
}
}


function frmtempgrant()
{
   
	var from=document.assigtemplatetouser.fromtxt.value;
    var to=document.assigtemplatetouser.totxt.value;
    var template = document.getElementById("template").value;
    if(from=="")
    {
    	alert("please enter Fromemployee ID");
    	document.assigtemplatetouser.fromtxt.focus();
    	return false;
    }
    else if(to=="")
    {
    	alert("please enter To employee ID");
    	document.assigtemplatetouser.totxt.focus();
    	return false;
    }
    else if(template==0)
    {
    	alert("please select Template");
    	document.getElementById("template").focus();
    	return false;
    }
    else
    {
      var url="../../../../../Role_template_to_user?Command=submit&from=" + from +"&to="+to+"&template_id="+template ;
       
   req.open("GET",url,true);       
    req.onreadystatechange=function()
    {
     if(req.readyState==4)
            {
    	 document.body.style.cursor = "default";
  		
  		document.getElementById("PHPDATA").innerHTML="";
            if(req.status==200)
                 { 
                      
                     var response=req.responseXML.getElementsByTagName("response")[0];
                    
                     var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                     
                   // stopwaiting(document.assigtemplatetouser);
                              if (flag=="Noemp" || flag=="fail")
                              {
                                   alert("Roles already assigned");
                                   clearAll();    
                              }
                                if(flag=="success")
                                {
                                    alert("Template are alloted to users Successfully");
                                    clearAll();
                                }
                              
                                
                                
                            
                            }
                        }
     else
     {
    	 document.body.style.cursor = "wait";
  		var temp ="<br/><table align=center CELLPADDING=0 CELLSPACING=0 WIDTH=300><tr><td align=center><FONT FACE=Arial SIZE=4 COLOR=black><b>Processing please wait</b></font></td></tr><tr><td align=center><img width=250 height=40 src=../../../../../images/loading_bar.gif /></td></tr></table>";
  		document.getElementById("PHPDATA").innerHTML=temp;
     }
                    }
                       
                        req.send(null);   
                       
    }  
    
}

function clearAll()
{
      document.getElementById("template").selectedIndex=0;
   document.assigtemplatetouser.fromtxt.value="";
      document.assigtemplatetouser.totxt.value="";
}

