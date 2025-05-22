var baseRe;
var winemp;
var my_window;
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
function employee_check()
{
 var valid=nullcheck();
  if(valid==true)
	{
		var emp_id=document.getElementById("emp_id").value;
		var url="../../../../../../Hrm_Punishment_Available?command=employee_check&emp_id="+emp_id;
		var req=getTransport();
		req.open("GET",url,true);
		req.onreadystatechange=function()
		{
		if(req.readyState==4)
			{
			if(req.status==200)
				{
				//alert(req.responseText)
				baseRe=req.responseXML.getElementsByTagName("response")[0];
				var command=baseRe.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="employee_check")
					{
					if(flag=="success")
						{
							var empname=baseRe.getElementsByTagName("ename")[0].firstChild.nodeValue;
							var flags=baseRe.getElementsByTagName("flags")[0].firstChild.nodeValue;
							//alert(flags)
							var DESIGNATION=baseRe.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
							var edob=baseRe.getElementsByTagName("edob")[0].firstChild.nodeValue;
							document.getElementById("emp_name").value=empname;
							document.getElementById("desig").value=DESIGNATION;
							document.getElementById("dob").value=edob;
							if(flags=='Y')
								{
							document.HRM_punish_available.punish_yes[0].checked=true;
							document.HRM_punish_available.punish_yes[1].disabled=true;
							document.HRM_punish_available.punish_yes[0].disabled=false;
								}
							if(flags=='N')
							{
								document.HRM_punish_available.punish_yes[0].disabled=true;
								document.HRM_punish_available.punish_yes[1].disabled=false;
						        document.HRM_punish_available.punish_yes[1].checked=true;
						
							}
						}
					 else if(flag=="failure")
						{
						alert("Please Enter Valid Employee Id");
						document.getElementById("emp_id").value="";
						document.getElementById("emp_name").value="";
						document.getElementById("desig").value="";
						document.getElementById("dob").value="";
						}
			       }
		     }
	    }
    };
   req.send(null);
		}
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
        winemp=null;
    }
        
    winemp= window.open("../../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
function doParentEmp(emp)
{
document.HRM_punish_available.emp_id.value=emp;
employee_check();
closeWin();
}
function closeWin()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
};
function nullcheck()
{
		if(document.getElementById("emp_id").value=="")
		{
			alert("please Enter Employee Id");
			document.getElementById("emp_id").focus();
			return false;
		}
  return true;
}
function gosubmit()
{
	if(document.getElementById("emp_id").value =="")
		{
		alert("Please Enter Employee Id");
		return false;
		}
	else
		{
		

		var emp_id=document.getElementById("emp_id").value;
		var punish_yes="";
		if(document.HRM_punish_available.punish_yes[0].checked)
			{
			punish_yes='Y';
			}
		else if(document.HRM_punish_available.punish_yes[1].checked)
			{
			punish_yes='N';
			}
		document.HRM_punish_available.action='Employee_punish_avail.jsp';
		document.HRM_punish_available.submit();
				
		}
}


