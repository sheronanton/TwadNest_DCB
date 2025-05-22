var desid;
var winemp=null;
var my_window;
var jobflag;
var GWING;
function servicepopup()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup_Test.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function servicepopupSR()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpPopupSRCtrlOffice.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.Emp_Ret_Test.txtEmployeeid.value=emp;
loademp();
}
var winjob;

var winjob1;


window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winjob1 && winjob1.open && !winjob1.closed) winjob1.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

};
var req=false;
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

function getTransport()
{
 var req1 = false;
 try 
 {
       req1= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req1 = false;
       }
 }
 if (!req1 && typeof XMLHttpRequest != 'undefined') 
 {
       req1 = new XMLHttpRequest();
 }  
 return req1;
}



function checkdate()
{
        var fromdt=document.frmCurrentPosting.txtDateFrom.value;
        var todt=document.frmCurrentPosting.txtDateTo.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('From Date should be less than To Date');
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                            return false;
                        }
                        
                }
        }
        return true;
}


function loademp()
{
	
	var empid=document.getElementById("txtEmployeeid").value;
	 var url="../../../../../Emp_Ret_Test?command=loademp&txtEmployeeid="+empid;
     //alert(url);
	 req.open("GET",url,true);
     req.onreadystatechange=handleResponse;
     if(window.XMLHttpRequest)
             req.send(null);
     else req.send();  
     
}


function getDesignation()
{
	var type=document.Emp_Ret_Test.cmbServiceGroup.options[document.Emp_Ret_Test.cmbServiceGroup.selectedIndex].value;
    if(type!=0)
    {
     
        loadOfficesByType1(type);
    }
    else
    {
       var des=document.getElementById("cmbDesignation");
       des.innerHTML='';
    }
}
    
function loadOfficesByType1(type)
{
   
    var type=document.Emp_Ret_Test.cmbServiceGroup.options[document.Emp_Ret_Test.cmbServiceGroup.selectedIndex].value;
    var url="../../../../../Emp_Ret_Test?command=SGroup1&cmbsgroup=" + type ;
   //alert("url --------->"+url); 
   var req=getTransport();
    req.open("GET",url,true);        
    req.onreadystatechange=function()
    {
         loadDesignation1(req);
    };
     if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
}
function  loadDesignation1(req1)
{
     if(req1.readyState==4)
        {
          if(req1.status==200)
          {           
                var response=req1.responseXML.getElementsByTagName("response")[0];              
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;              
                var des=document.getElementById("cmbDesignation");
                des.innerHTML="";
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else
                {
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="Select Designation";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                }
          }
        }
}

function Updation()
 {
	var val=nullcheck();
	if(val==true)
	{
	var r=confirm("Are You Sure Want to Update Employee Details!");
	if (r==true)
	  {
		var emp_id=document.getElementById("txtEmployeeid").value;	
		var service_grp=document.getElementById("cmbServiceGroup").value;
		var service_desig=document.getElementById("cmbDesignation").value;
		var url="../../../../../Emp_Ret_Test?command=Updation&emp_id="+emp_id+"&service_grp="+service_grp+"&service_desig="+service_desig;
		//alert(url);
		req.open("GET",url,true);
		req.onreadystatechange=handleResponse;
		if(window.XMLHttpRequest)
		        req.send(null);
	  }
	else
	  {
		clearAll();
	  }
	}

 }
 
function handleResponse()
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
         //  alert(req.responseText);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];         
            var tagcommand=baseResponse.getElementsByTagName("command")[0];         
            var Command=tagcommand.firstChild.nodeValue;
                if(Command=="loademp")
        	      {
            	      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	
           	            if(flag=="success")
           		           {
           		              var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
           		              var empname=baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
           		              document.getElementById("txtdesig").value=desig;
           		              document.getElementById("txtEmployee").value=empname;
           		            }
           	             if(flag=="failure")
        		            {
        		                 alert("This Employee Not in Retirement State");
        		                 clearAll();
        		            }
           	  
        	      }
           
            if(Command=="Updation")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                     {
            	          alert(" Record Updated Successfully ");
            	          clearAll();
                     } 
                  else if(flag=="Numformat")
         		   {
         		         alert("Invalid Number");
         		   }
                  else if(flag=="RecordLength")
                   {
                	   alert("Please Check Record Length");
                   }
            	
            }
        }  
        
    }
    
}

function nullcheck()
{
	if(document.getElementById("txtEmployeeid").value=="" || document.getElementById("txtEmployeeid").value==null || document.getElementById("txtEmployeeid").value.length==0)
	{
		alert('Please Enter Employee Id');
        document.Emp_Ret_Test.txtEmployeeid.focus();
        return false;
		
	}
	if (document.getElementById("cmbServiceGroup").value=="0") 
    {
        alert("Please Select Service Group ");
        document.Emp_Ret_Test.cmbServiceGroup.focus();
        return false;
    }
	if (document.getElementById("cmbDesignation").value=="0") 
    {
        alert("Please Select Designation ");
        document.Emp_Ret_Test.cmbDesignation.focus();
        return false;
    }
	return true;
}

function clearAll()
{
	
	document.getElementById("txtEmployeeid").value="";
	document.getElementById("txtEmployee").value="";
	document.getElementById("txtdesig").value="";
	document.getElementById("cmbServiceGroup").selectedIndex=0;
	document.getElementById("cmbDesignation").selectedIndex=0;
}

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false; 
        }
     }
     
function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false; 
        }
     }
     