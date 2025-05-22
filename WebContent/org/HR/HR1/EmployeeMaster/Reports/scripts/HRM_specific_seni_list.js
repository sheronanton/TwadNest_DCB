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
		var url="../../../../../../HRM_specific_seni_list?command=employee_check&emp_id="+emp_id;
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
							var DESIGNATION=baseRe.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
							var edob=baseRe.getElementsByTagName("edob")[0].firstChild.nodeValue;
							document.getElementById("emp_name").value=empname;
							document.getElementById("desig").value=DESIGNATION;
							document.getElementById("dob").value=edob;
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
document.HRM_seniority_rep.emp_id.value=emp;
servicepopup();
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
function getemp()
{
   var val=nullcheck();
   if(val == true)
   {
     var url="../../../../../../HRM_specific_seni_list?OLevel=submit";
     var emp_id="";
     emp_id=document.getElementById("emp_id").value;
	 url=url+"&emp_id="+emp_id;
	 
		if(document.HRM_seniority_rep.optoutputtype[0].checked==true)
		{
		        url=url+"&optoutputtype=pdf";
		      
		}
		else if(document.HRM_seniority_rep.optoutputtype[1].checked==true)
		{
		        url=url+"&optoutputtype=excel";
		        
		}
		else if(document.HRM_seniority_rep.optoutputtype[2].checked==true)
		{
		        url=url+"&optoutputtype=html";
		        
		}

	document.HRM_seniority_rep.action="../../../../../../HRM_specific_seni_list";
	document.HRM_seniority_rep.method="POST";
	document.HRM_seniority_rep.submit();
  }
}

