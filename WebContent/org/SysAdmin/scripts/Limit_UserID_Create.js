var role_value='';
var desigval='';
var xmlhttp;
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

function getxmlhttpObject() {
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




    




function loademployee()
{
      //alert("under loademp"); 
      var from=parseInt(document.frmValidationSummaryRep.fromtxt.value);
      var to=parseInt(document.frmValidationSummaryRep.totxt.value);
      if(from>to)
      {
    	  alert("Enter Valid Emloyee ID");
    	  clearAll();
    	  return false;
      }
      var l=parseInt(to)-parseInt(from);
      if(l>2000)
      {
    	  alert("Range Limit should not be exceed 2000.\n For a Faster User ID Creation");
    	  clearAll();
    	  return false;
      }
      
      xmlhttp = getxmlhttpObject();
  	if (xmlhttp == null) {
  		alert("Your borwser doesnot support AJAX");
  		return;
  	}
      // var type=document.frmValidationSummaryRep.Desig.options[document.frmValidationSummaryRep.Desig.selectedIndex].value;
       var url="../../../Limit_UserID_Create?command=get&from=" + from +"&to="+to ;
          // alert(url);
           xmlhttp.open("GET", url, true);
			
			
           xmlhttp.onreadystatechange=employee;
      
          xmlhttp.send(null); 
         
 }
function  employee()
{
	
     if(xmlhttp.readyState==4)
        {
    	 document.body.style.cursor = "default";
  		
  		document.getElementById("PHPDATA").innerHTML="";
          if(xmlhttp.status==200)
          { 
        	  var response=xmlhttp.responseXML.getElementsByTagName("response")[0];
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
              var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
        	
              
              var tlist=document.getElementById("tlist");
              while (tlist.childNodes.length > 0) {
             	 tlist.removeChild(tlist.firstChild);
              }
              var len=response.getElementsByTagName("emp_id").length;
              
              if(len<1){
            	  alert("All User Accounts are created for this Range");
            	 // document.frmValidationSummaryRep.subconfirm.disabled=true;
            	  clearAll();  
            	  }else{
            		  document.frmValidationSummaryRep.subconfirm.disabled=false;
            	  }
              
             var seq=0;
              for(var i=0;i<len;i++)
              {
              	
              var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
            
             var desig_id=response.getElementsByTagName("desig_id")[i].firstChild.nodeValue;
             var emp_name=response.getElementsByTagName("emp_name")[i].firstChild.nodeValue;
             var office_name=response.getElementsByTagName("office_name")[i].firstChild.nodeValue;
                                     
              
             var tr=document.createElement("TR");
              tr.id=seq;
              
              var tds=document.createElement("TD");
              var eid=document.createTextNode(emp_id);
              tds.appendChild(eid);
              tr.appendChild(tds);                            
               
              var td1=document.createElement("TD");
              var name=document.createTextNode(emp_name);
              td1.appendChild(name);
              tr.appendChild(td1);    
             
              var td2=document.createElement("TD");
              var des=document.createTextNode(desig_id);
              td2.appendChild(des);
              tr.appendChild(td2); 
              
              var td3=document.createElement("TD");
              var office=document.createTextNode(office_name);
              td3.appendChild(office);
              tr.appendChild(td3); 
                                                              
              tlist.appendChild(tr);             
              seq++;
              } 
              
              
              
              
          }
        }
     else
     {
    	 document.body.style.cursor = "wait";
 		var temp ="<br/><table align=center CELLPADDING=0 CELLSPACING=0 WIDTH=300><tr><td align=center><FONT FACE=Arial SIZE=4 COLOR=black><b>Processing please wait</b></font></td></tr><tr><td align=center><img width=250 height=40 src=../../../images/loading_bar.gif /></td></tr></table>";
 		document.getElementById("PHPDATA").innerHTML=temp;
     }
}
function confirmlogin()
{
	//alert("create under"); 
	if(document.frmValidationSummaryRep.fromtxt.value=="")
	{
		alert("Enter Start from Employee ID ");
		document.frmValidationSummaryRep.fromtxt.focus();
		
	}
	else if(document.frmValidationSummaryRep.totxt.value=="")
	{
		alert("Enter Ending from Employee ID ");
		document.frmValidationSummaryRep.totxt.focus();
	}
	else
	{
    var from=document.frmValidationSummaryRep.fromtxt.value;
    var to=document.frmValidationSummaryRep.totxt.value;
    
    xmlhttp = getxmlhttpObject();
	if (xmlhttp == null) {
		alert("Your borwser doesnot support AJAX");
		return;
	}  
	var url="../../../Limit_UserID_Create?command=create&from=" + from +"&to="+to ;
          // alert(url);
	  xmlhttp.open("GET", url, true);
	  xmlhttp.onreadystatechange=loginsuccess;
      
          xmlhttp.send(null);
	}
         
 }

function  loginsuccess()
{
	
     if(xmlhttp.readyState==4)
        {
    	 document.body.style.cursor = "default";
   		
   		document.getElementById("PHPDATA").innerHTML="";
          if(xmlhttp.status==200)
          { 
        	  var response=xmlhttp.responseXML.getElementsByTagName("response")[0];
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
              var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
              if(flag=="success")
              {
              alert("Successfully Users Account Created");
              }else
              {
            	  alert("Error in Users Account Creation");  
              }
              clearAll();   
                                              
          }
        }
     else
     {
    	 document.body.style.cursor = "wait";
 		var temp ="<br/><table align=center CELLPADDING=0 CELLSPACING=0 WIDTH=300><tr><td align=center><FONT FACE=Arial SIZE=4 COLOR=black><b>Processing please wait</b></font></td></tr><tr><td align=center><img width=250 height=40 src=../../../images/loading_bar.gif /></td></tr></table>";
 		document.getElementById("PHPDATA").innerHTML=temp;
     }
}
function clearAll()
{
	document.frmValidationSummaryRep.fromtxt.value="";
	document.frmValidationSummaryRep.totxt.value="";
	document.getElementById("tlist").innerHTML="";
	
	 
        
}