var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, response="";
var pagesize = 10;
var _Service_Period_Beg_Year=1971;
self.moveTo(0,0);self.resizeTo(screen.availWidth,screen.availHeight);
function stateChanged()
{
    var flag,command,response;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
  
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
    	   
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
           
       }
    }
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode;
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
					


String.prototype.trim = function() 
{
	a = this.replace(/^\s+/, '');
	return a.replace(/\s+$/, '');
	};
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
	   
	    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee_Search_for_Emp_Editing","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	    winemp.moveTo(250,250);  
	    winemp.focus();
	    
	}


function doParentEmp(emp,name,designation)
{

	document.getElementById("txtEmployeeid").value=emp; 
	met();
	//closeWin();
	

}
var empn=0;


function met() 
{

    xmlhttp = getTransport();
    if (xmlhttp == null) {
        alert("Your borwser doesnot support AJAX");
        return;
    }

    empn = document.getElementById("txtEmployeeid").value;   
    var offid = document.getElementById("txtOffId").value;
    if (empn == "") {
    } else {
        var urlhh = "../../../../../EmpSelectionPopupforOffice1?Command=loadempedit&txtEmployeeid="+empn+"&offid="+offid;

        xmlhttp.onreadystatechange = function()
        {
            if (xmlhttp.readyState == 4)
            {
                if (xmlhttp.status == 200) 
                {
                    var responsee = xmlhttp.responseXML
                    .getElementsByTagName("response")[0];
                    var emp_status1=    responsee
                    .getElementsByTagName("emp_status")[0].firstChild.nodeValue;
                   
                    var flag = responsee.getElementsByTagName("status")[0].firstChild.nodeValue;
                    if(flag=="wrong")
                    {
                 	   alert("Please Enter a Valid Employee Id");
                 	  document.hrm_gpf_final_settlement_master.txtEmployeeid.value="";
                 	 document.hrm_gpf_final_settlement_master.txtEmployeeid.focus();
                 	   
                    }
                    else
                    {
                      
                        document.getElementById("emp_name").value = responsee
                        .getElementsByTagName("name")[0].firstChild.nodeValue;
                        document.getElementById("desg_name").value = responsee
                        .getElementsByTagName("desg")[0].firstChild.nodeValue;
                   
                    }
                   

                }
            }

        };
        xmlhttp.open("GET", urlhh, true);
        xmlhttp.send(null);
    }

    callServer('Check','null'); 

}

window.onunload=function()
{
	if (winemp && winemp.open && !winemp.closed) winemp.close();
}
function changepagesize()
{
	
	pagesize = document.getElementById("cmbpagination").value;
	var len = response.getElementsByTagName("EmpId").length;
	 var cmbpage = document.getElementById("cmbpage");

	try 
	{
		cmbpage.innerHTML = "";
	} catch (e) 
	{
		cmbpage.innerText = "";
	}

	

	var i = 1;
	for (i = 1; i <= ((len / pagesize) + 1); i++) 
	{
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try 
		{
			cmbpage.add(option);
		} catch (errorObject) 
		{
			cmbpage.add(option, null);
		}
	}
	changepage();

}
function changepage()
{

	var tlist = document.getElementById("tblList");
	try 
	{
		tlist.innerHTML = "";
	} 
	catch (e) 
	{
		tlist.innerText = "";
	}
	try{

	var len = response.getElementsByTagName("EmpId").length;
	
	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="load")
		{
	var pageno = document.getElementById("cmbpage").value;

	var ul = 0, ll = 0;

	ul = pageno * pagesize;
	ll = ul - pagesize;

	for ( var i = ll; i < ul; i++)
	{

		var EMPLOYEE_ID = response.getElementsByTagName("EmpId")[i].firstChild.nodeValue;
		
		var Employee_Name = response.getElementsByTagName("Employee_Name")[i].firstChild.nodeValue;
		var Designation = response.getElementsByTagName("Designation")[i].firstChild.nodeValue;
		var PROCESS_FLOW_ID=response.getElementsByTagName("process_flow_id")[i].firstChild.nodeValue;
		if(Designation=="null")
			{
			Designation="";
			}
			
			
		var DA_AMOUNT = response.getElementsByTagName("DA_AMOUNT")[i].firstChild.nodeValue;
		if(DA_AMOUNT=="null")
		{
			DA_AMOUNT="";
		}
		
		
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		var anc = document.createElement("A");

		if (PROCESS_FLOW_ID == "FR") 
		{
			var vali = document.createTextNode("Validated");
			td.appendChild(vali);
			tr.appendChild(td);
		} 
		else 
		{
			var url = "javascript:loadvalue('" + seq + "')";
			anc.href = url;
			var edit = document.createTextNode("Edit");
			anc.appendChild(edit);
			td.appendChild(anc);
			tr.appendChild(td);
			
			
			
		}

		var td1 = document.createElement("TD");
		var tid = document.createTextNode(EMPLOYEE_ID);
		td1.appendChild(tid);
		tr.appendChild(td1);

		var td11 = document.createElement("TD");
		var tid1 = document.createTextNode(Employee_Name);
		td11.appendChild(tid1);
		tr.appendChild(td11);
		
		var td2 = document.createElement("TD");
		var cutoff = document.createTextNode(Designation);
		td2.appendChild(cutoff);
		tr.appendChild(td2);

		var td3 = document.createElement("TD");
		var surr = document.createTextNode(DA_AMOUNT);
		td3.appendChild(surr);
		tr.appendChild(td3);
		
		
		
	
		tlist.appendChild(tr);

		seq++;
	}
	}
	
	else
	{
		 var iframe=document.getElementById("tblList");
         iframe.focus();
		 if(navigator.appName.indexOf('Microsoft')!=-1)
             iframe.innerHTML="<tr><td align=center colspan=4>There is No Data to Display</td></tr>";
         else
             iframe.innerText="There is No Date to Display";
         iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";
	}
	}
	catch(s)
	{
		//alert(s);
	}
	
	   document.hrm_gpf_final_settlement_master.CmdAdd.disabled=false;
	   document.hrm_gpf_final_settlement_master.CmdUpdate.disabled=true;
	   document.hrm_gpf_final_settlement_master.CmdDelete.disabled=true;
	   document.hrm_gpf_final_settlement_master.CmdValidate.disabled=true;

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
    self.close();
 }
 function nullCheck()
 {
	
           if((document.hrm_gpf_final_settlement_master.txtEmployeeid.value==""))
           { 
                alert("Please Enter Employee ID");
                document.hrm_gpf_final_settlement_master.txtEmployeeid.focus();
                return false;
           }
           else if(document.hrm_gpf_final_settlement_master.da_Amount.value=="" )
           { 
                alert("Please Enter the 1 Percent D.A Amount");
                document.hrm_gpf_final_settlement_master.da_Amount.focus();
                return false;
           }
          
            
          	
            
              return true;
  } 
 
 
 function callServer(command,param)
 {
	 
        var txtEmployeeid=document.hrm_gpf_final_settlement_master.txtEmployeeid.value;
        var txtOffId=document.hrm_gpf_final_settlement_master.txtOffId.value;
        var da_Amount=document.hrm_gpf_final_settlement_master.da_Amount.value;
       
        var url="";
        if(command=="Add")

        {

        	var flag=nullCheck();
        	
        	if(flag==true)
        	{

        		
        			url="../../../../../GPF_Final_Settlement_master?command=Add&txtEmployeeid="+txtEmployeeid+"&txtOffId="+txtOffId+"&da_Amount="+da_Amount;

        			var req=getTransport();
        			req.open("GET",url,true);   

        			req.onreadystatechange=function()
        			{

        				processResponse(req);
        			};   
        			req.send(null);
        		
        		}

        }
     if(command=="updation")
        {
        	
                    var flag=nullCheck();
                    
                    if(flag==true)
                    {
                    	

                    		url="../../../../../GPF_Final_Settlement_master?command=updation&txtEmployeeid="+txtEmployeeid+"&txtOffId="+txtOffId+"&da_Amount="+da_Amount;

                    		
                    		var req=getTransport();
                    		req.open("GET",url,true);        
                    		req.onreadystatechange=function()
                    		{
                    			processResponse(req);
                    		};   
                    		req.send(null);
                    	
                    }

        }
        
      if(command=="Delete")
        {  
    	  url="../../../../../GPF_Final_Settlement_master?command=Deletion&txtEmployeeid="+txtEmployeeid;
                   var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
      if(command=="Validate")
        {
    	  var flag=nullCheck();
          
          if(flag==true)
          {
          	

          		url="../../../../../GPF_Final_Settlement_master?command=validate&txtEmployeeid="+txtEmployeeid+"&txtOffId="+txtOffId+"&da_Amount="+da_Amount;

          		
          		var req=getTransport();
          		req.open("GET",url,true);        
          		req.onreadystatechange=function()
          		{
          			processResponse(req);
          		};   
          		req.send(null);
          	
          }

        }
      if(command=="Get")
        {            
        	
        	
            url="../../../../../GPF_Final_Settlement_master?command=Get";
           
            seq=0;         
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               viewResponse(req);
            };   
                    req.send(null);
        }
    if(command=="Check")
        {
                    var txtEmployeeid=document.hrm_gpf_final_settlement_master.txtEmployeeid.value;
                    if(txtEmployeeid.length>0)
                    {
                    	 url="../../../../../GPF_Final_Settlement_master?command=check&txtEmployeeid="+txtEmployeeid;
                    
                    	 
                    	 var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }

        }
        
}  
 function processResponse(req)
 {
	
	 if(req.readyState==4)
     { 
		
     	
         if(req.status==200)
         {  
        	
        	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        	
      	     updateResponse(baseResponse);
         }
     }
	 
 }
 var checkflag=false;
 function updateResponse(baseResponse)
 {
	 
 	var res=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
 	if(res=="added")
 	{
 		if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
 		{
 			
 			alert("Record Added Successfully");			
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.value="";
 			document.hrm_gpf_final_settlement_master.emp_name.value="";
 			document.hrm_gpf_final_settlement_master.desg_name.value="";
 			document.hrm_gpf_final_settlement_master.da_Amount.value="";
 			
 			callServer('Get','null');
 		}
 	}
 	
 	if(res=="Check")
 	{
 		if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
 		{
 			
 			alert("Data already added for the particular Employee ID");		
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.value="";
 			document.hrm_gpf_final_settlement_master.emp_name.value="";
 			document.hrm_gpf_final_settlement_master.desg_name.value="";
 			
 			callServer('Get','null');
 		}
 		else if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="failure")
 		{
 			
 		}
 	}
 	if(res=="updation")
 	{
 		if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
 		{
 			
 			alert("Record Updated Successfully");
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.value="";
 			document.hrm_gpf_final_settlement_master.emp_name.value="";
 			document.hrm_gpf_final_settlement_master.desg_name.value="";
 			document.hrm_gpf_final_settlement_master.da_Amount.value="";
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.disabled = false;
 			
 			callServer('Get','null');
 			
 		}
 		else if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="failure")
 		{
 			
 		}
 	}
 	if(res=="validate")
 	{
 		if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
 		{
 			
 			alert("Record Validated Successfully");
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.value="";
 			document.hrm_gpf_final_settlement_master.emp_name.value="";
 			document.hrm_gpf_final_settlement_master.desg_name.value="";
 			document.hrm_gpf_final_settlement_master.da_Amount.value="";
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.disabled = false;
 			
 			callServer('Get','null');
 			
 		}
 		else if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="failure")
 		{
 			
 		}
 	}
	
 	if(res=="Deletion")
 	{
 		if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
 		{
 			
 			alert("Record Deleted Successfully");
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.value="";
 			document.hrm_gpf_final_settlement_master.emp_name.value="";
 			document.hrm_gpf_final_settlement_master.desg_name.value="";
 			document.hrm_gpf_final_settlement_master.da_Amount.value="";
 			document.hrm_gpf_final_settlement_master.txtEmployeeid.disabled = false;
 			
 			callServer('Get','null');
 			
 		}
 		else if(baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue=="failure")
 		{
 			
 		}
 	}	
 
}
 function viewResponse(req)
 {
	 if(req.readyState==4)
     { 
     	
         if(req.status==200)
         {  
        	 response=req.responseXML.getElementsByTagName("response")[0];
           		changepagesize();
				changepage();
             
             statusflag=true;
           
         }
     }
 }
 


function loadvalue(empid) 
{
	
	common = empid;
	var emp_id = document.getElementById(empid);
	var rcells = emp_id.cells;
	
	document.hrm_gpf_final_settlement_master.txtEmployeeid.disabled = true;
	document.hrm_gpf_final_settlement_master.txtEmployeeid.value = rcells.item(1).firstChild.nodeValue;
	document.hrm_gpf_final_settlement_master.emp_name.value = rcells.item(2).firstChild.nodeValue;
	document.hrm_gpf_final_settlement_master.desg_name.value = rcells.item(3).firstChild.nodeValue;
	document.hrm_gpf_final_settlement_master.da_Amount.value = rcells.item(4).firstChild.nodeValue;
	document.hrm_gpf_final_settlement_master.CmdAdd.disabled = true;
	document.hrm_gpf_final_settlement_master.CmdUpdate.disabled = false;
	document.hrm_gpf_final_settlement_master.CmdDelete.disabled = false;
	document.hrm_gpf_final_settlement_master.CmdValidate.disabled = false;

}
  
	  
function clearAll()
{
        location.reload(true);
}