//alert("show me nom admin");
//alert("show me nom admin");
//var a=1;

var BranchId=new Array();
var seni_no=new Array();
var cadre_name=new Array();
var pro_date=new Array();
var pro_Ref_no=new Array();
var new_or_amd=new Array();
var ori_order=new Array();
var Renm=new Array();
var cadre_id=new Array();
var seq=0;





var service;
var baseResponse;
var __pagination=10;
  var destid;
    var totalblock=0;
var page;
//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
var wininterval;
//alert('kkk');
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.EmpQual.txtEmployeeid.value=emp;
doFunction('loademp','null');
}



///////////////////////////////////////////////////////////////////////////////////

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,600);
       winjob.moveTo(200,200); 
       winjob.focus();
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(200,200);  
 
    winjob.focus();
    
    
}

function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed)
  {
        if((document.EmpQual.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.EmpQual.txtDept_Id.value;
           // winjob.getOtherOffice();
           //var opt4=document.getElementById("optother");
           //winjob.document.HRM_JobSearch.optother.checked=true;
           
        }
    }
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
//if(deptid==null)
{
    document.EmpQual.txtOffice_Id.value=jobid;
    document.EmpQual.txtDept_Id.value=deptid;
    callServer1('Load1','null');
    return true
}
/*else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}*/
}

window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();
if (wininterval && wininterval.open && !wininterval.closed) wininterval.close();
}

///////////////////////////////////////////////////////////////////////////////////


//************list all *************************
 function popwindow()
    {
        if((document.EmpQual.txtEmployeeid.value==null)||(document.EmpQual.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.EmpQual.txtEmployeeid.focus();
            return false;
        }
        if (my_window && my_window.open && !my_window.closed) 
        {
          
           my_window.focus();
           return;
    
        }
        else
        {
            my_window=null
        }
        var str="EMP_NOMINEE_ListAll.jsp?id="+document.EmpQual.txtEmployeeid.value;
        my_window= window.open(str,"mywindow1","status=1,height=400,width="+screen.availWidth+",resizable=yes, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }

var req=false;
try
{
    req=new ActiveXObject("Msxml2.XMLHTTP");
}
catch(e)
{
    try
    {
        req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(ee)
    {
        req=false;
    }
}

if(!req || typeof XMLHTTPRequest !='undefined')
{
    req=new XMLHttpRequest();
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
 //alert(req1);
 return req1;
}


function toExit()
{
  //window.close();
var w=window.open(window.location.href,"_self");
w.close();
}

function checkdate()
{
//alert('check');
        var fromdt=document.EmpQual.txtDateFrom.value;
        var todt=document.EmpQual.txtDateTo.value;
        
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
            //document.EmpQual.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.EmpQual.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.EmpQual.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;
}

function checkquali()
{
checkempid();
     if((document.EmpQual.Qualification.value==0)||(document.EmpQual.Qualification.value.length==0))
        {
            alert("Enter Qualification ");
            document.EmpQual.Qualification.focus();
            return false;
        }
    return true;
}




function notNull(p)
{

   if((document.EmpQual.txtEmployeeid.value==null)||(document.EmpQual.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.EmpQual.txtEmployee.value="";
        document.EmpQual.txtdob.value="";
        document.EmpQual.txtGpf.value="";
        document.EmpQual.txtEmployeeid.value="";
        
        document.EmpQual.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.EmpQual.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.EmpQual.txtEmployeeid.value="";
        document.EmpQual.txtEmployeeid.focus();
        return false;
    }
    
    if((document.EmpQual.Qualification.value==0)||(document.EmpQual.Qualification.value.length==0))
        {
            alert("Enter Qualification ");
            document.EmpQual.Qualification.focus();
            return false;
        }
       /*  var c=checkdate();
       if(c==false)
       {
            document.EmpQual.txtDateTo.focus();
            return false;
        }*/
        
        
        
    
    return true;
}

function ischarValid(evt,item){


var chrcode = (evt.which) ? evt.which : evt.keyCode

if (chrcode!=46 && (chrcode!=08) && (chrcode!=09)&& (chrcode!=32)&&
 (chrcode < 97 || chrcode > 122) && (chrcode < 65 || chrcode > 90)){
	return false;	}
	
				return true;

} 
function hidde()
{
	
	if(document.Seniority_Proceeding_master.Amendment[0].checked==true)
		{
		document.getElementById("amd").style.display="none";
		document.getElementById("ori_reg_no").value="";
		}
}
function show()
{
	if(document.Seniority_Proceeding_master.Amendment[1].checked==true)
		{
		document.getElementById("amd").style.display="";
		}
}
function Add()
{
	var val=nullcheck();
	if(val ==true)
		{
	var cadre=document.getElementById("cadre_id").value;
	var pro_no=document.getElementById("pro_no").value;
	var pro_date=document.getElementById("pro_date").value;
	var ori_reg_no="";
	ori_reg_no= document.getElementById("ori_reg_no").value;
	var remarks=document.getElementById("remarks").value;
	var Amendment="";
	if(document.Seniority_Proceeding_master.Amendment[0].checked==true)
		{
		Amendment="N";
		//document.getElementById("ori_reg_no").value="";
		ori_reg_no="";
		}
	else
		{
		Amendment="A";
		if(document.getElementById("ori_reg_no").value=="")
			{
			alert("Please Enter Original No!");
			return false;
			}
		}
	 url="../../../../../Seniority_Proceeding_master?Command=Add&cadre="+cadre+"&pro_no="+pro_no+"&pro_date="+pro_date+"&ori_reg_no="+ori_reg_no+"&remarks="+remarks+"&Amendment="+Amendment;
	//alert(url);
	 var req=getTransport();
	req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		ProcessResponse(req);
	};
		}
	req.send(null);
}
function Update()
{
	var val=nullcheck();
	if(val ==true)
		{
	var seniority_id=document.getElementById("seniority_id").value;
	var cadre=document.getElementById("cadre_id").value;
	var pro_no=document.getElementById("pro_no").value;
	var pro_date=document.getElementById("pro_date").value;
	var ori_reg_no="";
	ori_reg_no= document.getElementById("ori_reg_no").value;
	var remarks=document.getElementById("remarks").value;
	var Amendment="";
	if(document.Seniority_Proceeding_master.Amendment[0].checked==true)
		{
		Amendment="N";
		}
	else
		{
		Amendment="A";
		if(document.getElementById("ori_reg_no").value=="")
			{
			alert("Please Enter Original No!");
			return false;
			}
		}
	 url="../../../../../Seniority_Proceeding_master?Command=Update&cadre="+cadre+"&pro_no="+pro_no+"&pro_date="+pro_date+"&ori_reg_no="+ori_reg_no+"&remarks="+remarks+"&Amendment="+Amendment+"&seniority_id="+seniority_id;
	//alert(url);
	 var req=getTransport();
	req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		ProcessResponse(req);
	};
		}
	req.send(null);
	
}
function validate()
{
	var val=nullcheck();
	if(val ==true)
		{
	var seniority_id=document.getElementById("seniority_id").value;
	var cadre=document.getElementById("cadre_id").value;
	var pro_no=document.getElementById("pro_no").value;
	var pro_date=document.getElementById("pro_date").value;
	var ori_reg_no="";
	ori_reg_no= document.getElementById("ori_reg_no").value;
	var remarks=document.getElementById("remarks").value;
	var Amendment="";
	if(document.Seniority_Proceeding_master.Amendment[0].checked==true)
		{
		Amendment="N";
		}
	else
		{
		Amendment="A";
		if(document.getElementById("ori_reg_no").value=="")
			{
			alert("Please Enter Original No!");
			return false;
			}
		}
	 url="../../../../../Seniority_Proceeding_master?Command=validate&cadre="+cadre+"&pro_no="+pro_no+"&pro_date="+pro_date+"&ori_reg_no="+ori_reg_no+"&remarks="+remarks+"&Amendment="+Amendment+"&seniority_id="+seniority_id;
	//alert(url);
	 var req=getTransport();
	req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		ProcessResponse(req);
	};
	req.send(null);
		}

}
function Delete()
{
	var seniority_id=document.getElementById("seniority_id").value;
	var rel=confirm("If You Want to Delete this Seniority No !");
	if(rel==true)
		{
	 url="../../../../../Seniority_Proceeding_master?Command=Delete&seniority_id="+seniority_id;
		//alert(url);
		 var req=getTransport();
		req.open("POST",url,true);
		req.onreadystatechange=function()
		{
			ProcessResponse(req);
		};
		req.send(null);
		}
}
function ProcessResponse(req)
{
	if(req.readyState==4)
    {
      if(req.status==200)
      {  
    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    	  var tagCommand=baseResponse.getElementsByTagName("command")[0];
          var command=tagCommand.firstChild.nodeValue; 
          if(command=="Add")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
            	  alert("Record Inserted Successfully");
            	  getdata();
            	  clearAll();
              }
          }
          if(command=="Update")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
            	  alert("Record Updated Successfully");
            	document.getElementById("cmdadd").style.display="";
          		document.getElementById("cmdupdate").style.display="none";
          		document.getElementById("cmdvalidate").style.display="none";
          		document.getElementById("cmddelete").style.display="none";
            	  getdata();
            	  clearAll();
              }
          }
          if(command=="validate")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
            	  alert("Record validated Successfully");
            	    document.getElementById("cmdadd").style.display="";
            		document.getElementById("cmdupdate").style.display="none";
            		document.getElementById("cmdvalidate").style.display="none";
            		document.getElementById("cmddelete").style.display="none";
            	  getdata();
            	  clearAll();
              }
          }
          if(command=="Delete")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
            	  
            	  alert("Record Deleted Successfully");
            	    document.getElementById("cmdadd").style.display="";
            		document.getElementById("cmdupdate").style.display="none";
            		document.getElementById("cmdvalidate").style.display="none";
            		document.getElementById("cmddelete").style.display="none";
            	  getdata();
            	  clearAll();
              }
          }
      }
    }
}
function getdata()
{
	var cadre_id="";
	cadre_id=document.getElementById("cadre_id").value;
	url="../../../../../Seniority_Proceeding_master?Command=getdata&cadre_id="+cadre_id;
	//alert(url)
	 var req=getTransport();
	req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		viewResponse(req);
	};
	req.send(null);
}
function viewResponse(req)
{
	
	if(req.readyState==4)
{
	if(req.status==200)
	{
		//alert(req.responseText);
		 baseresponse=req.responseXML.getElementsByTagName("response")[0];
		 
		 var tagCommand=baseresponse.getElementsByTagName("command")[0];
          command=tagCommand.firstChild.nodeValue; 
         
          changepagesize();
          changepage();
          
         
	}
}
}
function changepagesize() {
	
	pagesize = document.getElementById("cmbpagination").value;
	
	var len=baseresponse.getElementsByTagName("count").length;
	
	 var cmbpage = document.getElementById("cmbpage");
		
	try {
		cmbpage.innerHTML = "";
	} catch (e) {
		cmbpage.innerText = "";
	}
	
	
	var i = 1;
	for (i = 1; i <= ((len / pagesize) + 1); i++) {
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try {
			cmbpage.add(option);
		} catch (errorObject) {
			cmbpage.add(option, null);
		}
	}

	
	changepage();

}
function changepage() {
	
	var tlist = document.getElementById("tb");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	
	
	if(baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue=="success")
		{
	
	var pageno = document.getElementById("cmbpage").value;

	var ul = 0, ll = 0;

	ul = pageno * pagesize;
	ll = ul - pagesize;

	for ( var i = ll; i < ul; i++) {
	
	
		var SENIORITY_LIST_ID=baseresponse.getElementsByTagName("SENIORITY_LIST_ID")[i].firstChild.nodeValue;
		
		var	CADRE_NAME=baseresponse.getElementsByTagName("CADRE_NAME")[i].firstChild.nodeValue;
	  var PROCEEDINGS_DATE=baseresponse.getElementsByTagName("PROCEEDINGS_DATE")[i].firstChild.nodeValue;
	  var PROCEEDINGS_REF_NO=baseresponse.getElementsByTagName("PROCEEDINGS_REF_NO")[i].firstChild.nodeValue;
	  var NEW_OR_AMENDMENT_FLAG=baseresponse.getElementsByTagName("NEW_OR_AMENDMENT_FLAG")[i].firstChild.nodeValue;
	  var ORIGINAL_ORDER_REF_NO=baseresponse.getElementsByTagName("ORIGINAL_ORDER_REF_NO")[i].firstChild.nodeValue;
	  var PROCESS_FLOW_ID=baseresponse.getElementsByTagName("PROCESS_FLOW_ID")[i].firstChild.nodeValue;
	  var   REMARKS=baseresponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
	  var CADRE_ID=baseresponse.getElementsByTagName("CADRE_ID")[i].firstChild.nodeValue;
	 
	 
	
	 
	
	  
	 if(SENIORITY_LIST_ID=="null" || SENIORITY_LIST_ID==null)
		 SENIORITY_LIST_ID="";
	 if(CADRE_NAME=="null" || CADRE_NAME==null)
		 CADRE_NAME="";
	 if(PROCEEDINGS_DATE=="null" || PROCEEDINGS_DATE==null)
		 PROCEEDINGS_DATE="";
	 if(PROCEEDINGS_REF_NO=="null" || PROCEEDINGS_REF_NO==null)
		 PROCEEDINGS_REF_NO="";
	 if(NEW_OR_AMENDMENT_FLAG=="null" || NEW_OR_AMENDMENT_FLAG==null)
		 NEW_OR_AMENDMENT_FLAG="";
	 if(ORIGINAL_ORDER_REF_NO=="null" || ORIGINAL_ORDER_REF_NO==null)
		 ORIGINAL_ORDER_REF_NO="";
	 if(REMARKS=="null" || REMARKS==null)
		 REMARKS="";
	 
	 
	 
	 seni_no[i]=SENIORITY_LIST_ID;
	 cadre_name[i]=CADRE_NAME;
	 cadre_id[i]=CADRE_ID;
	 pro_date[i]=PROCEEDINGS_DATE;
	 pro_Ref_no[i]=PROCEEDINGS_REF_NO;
	 new_or_amd[i]=NEW_OR_AMENDMENT_FLAG;
	 ori_order[i]=ORIGINAL_ORDER_REF_NO; 
	 Renm[i]=REMARKS;
	 
	if(NEW_OR_AMENDMENT_FLAG=="N")
	  {
		NEW_OR_AMENDMENT_FLAG="New Order";
	  }
	 else
		{
		NEW_OR_AMENDMENT_FLAG="Amendment Order";
		}
	 var tr = document.createElement("TR");
	 
	/* if(i % 2 == 0)
		 {
		
		 tr.style.bgcolor ='#0B615E';
		 }
	 else
		 {
		
		 tr.style.bgcolor ='green';
		 }*/
     tr.id = seq;
     var td = document.createElement("TD");
     var anc = document.createElement("A");

     if (PROCESS_FLOW_ID == "FR")
     {
         var am = document.createTextNode("Validated");
         td.appendChild(am);
         td.style.color ='green';
			td.align='center';
         tr.appendChild(td);
     }
     else 
     
     {
         var url = "javascript:loadvalue('" + i + "')";
         anc.href = url;
         var edit = document.createTextNode("Edit");
         anc.appendChild(edit);
         td.style.color ='#0B615E';
         td.align='center';
         td.appendChild(anc);
         tr.appendChild(td);
         
     }
	 
	 var td=document.createElement("td");
     var SENIORITY_LIST_ID=document.createTextNode(SENIORITY_LIST_ID);
     td.appendChild(SENIORITY_LIST_ID);
     //td.style.color ='#04B404';
     td.setAttribute("align", "center");   
     //td.setAttribute("style", "display:none"); 
     tr.appendChild(td);                                          

     var td=document.createElement("td");
     var CADRE_NAME=document.createTextNode(CADRE_NAME);
     td.appendChild(CADRE_NAME);
    // td.style.color ='#FF00FF';
     td.setAttribute("align", "center");                
     tr.appendChild(td);                                          

     var td=document.createElement("td");
     var PROCEEDINGS_DATE=document.createTextNode(PROCEEDINGS_DATE);
     td.appendChild(PROCEEDINGS_DATE);
     //td.style.color ='#400000';
     td.setAttribute("align", "center");                
     tr.appendChild(td);                                          

     var td=document.createElement("td");
     var PROCEEDINGS_REF_NO=document.createTextNode(PROCEEDINGS_REF_NO);
     td.appendChild(PROCEEDINGS_REF_NO);
     //td.style.color ='#400000';
     td.setAttribute("align", "center");                
     tr.appendChild(td);
     
     var td=document.createElement("td");
     var NEW_OR_AMENDMENT_FLAG=document.createTextNode(NEW_OR_AMENDMENT_FLAG);
     td.appendChild(NEW_OR_AMENDMENT_FLAG);
     //td.style.color ='#400000';
     td.setAttribute("align", "center");                
     tr.appendChild(td);
     
     
     var td=document.createElement("td");
     var ORIGINAL_ORDER_REF_NO=document.createTextNode(ORIGINAL_ORDER_REF_NO);
     td.appendChild(ORIGINAL_ORDER_REF_NO);
     //td.style.color ='#400000';
     td.setAttribute("align", "center");                
     tr.appendChild(td); 
                            

     var td=document.createElement("td");
     var REMARKS=document.createTextNode(REMARKS);
     td.appendChild(REMARKS);
     //td.style.color ='#400000';
     td.setAttribute("align", "center");                
     tr.appendChild(td); 

   
   
     tlist.appendChild(tr);


	
	 
	 
	 
  }
  }
	
}
/*function alternate(id){ 

	  if(document.getElementsByTagName){  

	    var table = document.getElementById(id);   

	    var rows = table.getElementsByTagName("tr");   

	    for(var i = 0; i < rows.length; i++){           

	  //manipulate rows 

	      if(i % 2 == 0){ 

	        rows[i].className = "even"; 

	      }else{ 

	        rows[i].className = "odd"; 

	      }       

	    } 

	  } 

	}*/
function loadvalue(i)
{ 

	document.getElementById("seniority_id").value=seni_no[i];
	document.getElementById("cadre_id").value=cadre_id[i];
	//document.getElementById("cadre_id").value=cadre_name[i];
	document.getElementById("pro_date").value=pro_date[i];
	document.getElementById("pro_no").value=pro_Ref_no[i];
	
	document.getElementById("ori_reg_no").value=ori_order[i];
	document.getElementById("remarks").value=Renm[i];
	if(new_or_amd[i]=="N")
		{
		
		document.Seniority_Proceeding_master.Amendment[0].checked=true;
		hidde();
		document.getElementById("amd").style.dispaly="none";
		}
	else
		{
		document.Seniority_Proceeding_master.Amendment[1].checked=true;
		document.getElementById("amd").style.display="";
		}
	
	document.getElementById("cmdadd").style.display="none";
	document.getElementById("cmdupdate").style.display="";
	document.getElementById("cmdvalidate").style.display="";
	document.getElementById("cmddelete").style.display="";
//	document.getElementById("delete").style.display="";
	
}
function nullcheck()
{
   if(document.getElementById("cadre_id").selectedIndex==0)
	{
	alert("Please Select Cadre !");
	return false;
	}
   if(document.getElementById("pro_no").value=="")
	{
	alert("Please Enter Seniority Proceeding No !");
	document.getElementById("pro_no").focus();
	return false;
	}
	if(document.getElementById("pro_date").value=="")
	{
	alert("Please Enter Proceeding Date !");
	document.getElementById("pro_date").focus();
	return false;
	}
	/*if(document.getElementById("ori_reg_no").value=="")
	{
	alert("Please Enter Original Order Reg No !");
	document.getElementById("ori_reg_no").focus();
	return false;
	}*/
	return true;
}
function offclr()
{
    
    document.EmpQual.txtOffice_Id.value='';
    
    document.EmpQual.txtOffice_Name.value='';
    document.EmpQual.txtOffice_Address1.value='';
   // document.EmpQual.txtOffice_Address2.value='';
   // document.EmpQual.txtOffice_City.value='';
       
   

}
function clearAll()
{
	
	document.getElementById("seniority_id").value="";
	document.getElementById("cadre_id").selectedIndex=0;
	document.getElementById("pro_date").value="";
	document.getElementById("pro_no").value="";
	document.getElementById("ori_reg_no").value="";
	document.getElementById("remarks").value="";
	
	//document.getElementById("seniority_id").value="";
	
}
function clr()
{

document.EmpQual.cmdadd.disabled=false;

document.EmpQual.txtSNo.value='';
//document.EmpQual.txtEmployeeid.value="";
document.EmpQual.Qualification.selectedIndex=0;
document.EmpQual.Special.selectedIndex=0;
//document.EmpQual.txtDept_Id.value='0';
//offclr();

//alert('allclear');
//doFunction('LastDate',null);


}

function clr1()
{

document.EmpQual.cmdadd.disabled=false;

document.EmpQual.txtSNo.value='';
//document.EmpQual.txtEmployeeid.value="";
document.EmpQual.Qualification.selectedIndex=0;
document.EmpQual.Special.selectedIndex=0;
document.EmpQual.txtEmployeeid.value='';
document.EmpQual.txtEmployee.value='';
document.EmpQual.txtdob.value='';
document.EmpQual.txtGpf.value='';

}


function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
           // stopwaiting(document.EmpQual);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // service=baseResponse;
         // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          //  alert('test'+tagcommand);
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loademp")
            {
          //  alert("load emp handler");
                loadEmp(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
            else if(Command=="test")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
                if(flag=="failure")
                {
                    
                         alert('Enter a Valid Employee Number');
                         document.EmpQual.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.EmpQual.txtEmployeeid.focus();
                         return false;
                }
                else
                {
                    return true;
                }
                    
                
            }
            else if(Command=="Add")
            {
            
                addfun(baseResponse);
            }
             else if(Command=="Update")
            {
                updatefun(baseResponse);
            }
             else if(Command=="Delete")
            {
                deletefun(baseResponse);
            }
             else if(Command=="SerGroup")
            {
                selectGroupfun(baseResponse);
            }
            else if(Command=="session")
            {
                
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
               
               try
                    {
                    
                    var flag1=baseResponse.getElementsByTagName("flag")[1].firstChild.nodeValue;
                   //  alert(flag1);
                   if(flag1!=null)
                    {
                        alert(flag1);
                        self.close();
                        return;
                    }
                    }catch(e){
                    //alert(e);
                    
                    }  
            }
            else if(Command=="Load")
            {
           // alert("load handlwersffffffffffffffff");
                   // loadPage(1)
                   var tbody=document.getElementById("tb");
                   try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                   var service =baseResponse.getElementsByTagName("EMPLOYEE_ID");
                   //alert(service.length);
                 var j;
                 
                   for(i=0;i<service.length;i++)
                   
                {
                j=i+1;
                        var EMPLOYEE_ID = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
                       // alert("empid"+EMPLOYEE_ID);
                        
                        var LIST_SL_NO =baseResponse.getElementsByTagName("LIST_SL_NO")[i].firstChild.nodeValue;
                        var Qualdesc=baseResponse.getElementsByTagName("Qualdesc")[i].firstChild.nodeValue;
                        var Specdesc=baseResponse.getElementsByTagName("Specdesc")[i].firstChild.nodeValue;
                        var Qualid=baseResponse.getElementsByTagName("Qualid")[i].firstChild.nodeValue;
                        var Specid=baseResponse.getElementsByTagName("Specid")[i].firstChild.nodeValue;
                        var Reguniv=baseResponse.getElementsByTagName("Reguniv")[i].firstChild.nodeValue;
                        if(Reguniv=='Y')
                           Reguniv='Yes';
                           else
                           Reguniv='No';
                        var mycurrent_row=document.createElement("TR");
                        if((Specdesc==null)||(Specdesc=='null'))
                        Specdesc="";
                      
                     /*  if(items[15]=='FR')
                       {
                             var descell=document.createElement("TD");
                             var sn=document.createTextNode('Freezed');
                             descell.setAttribute('style','color:red');
                             descell.appendChild(sn);
                             mycurrent_row.appendChild(descell);
                        }
                        else*/
                        {
                            mycurrent_row.id=j;
                            var cell=document.createElement("TD");
                            var anc=document.createElement("A");
                            //var url="javascript:loadTable('"+EMPLOYEE_ID+"')";
                            var url="javascript:loadTable('"+j+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        }
                        
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(LIST_SL_NO);                         
                      descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                       
                         
                         
                         
                         
                         
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(Qualdesc);
                         sc.type="hidden";
                         sc.name="qual";
                         sc.text=Qualid;
                         sc.value=Qualid;
                         descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(Specdesc);
                         sc.type="hidden";
                         sc.name="spec";
                         sc.text=Specid;
                         sc.value=Specid;
                         descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(Reguniv);                         
                      descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                        
                     /*    var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(fund);
                         sc.type="hidden";
                         sc.name="fund";
                         sc.text=fund;
                         sc.value=fund;
                        //descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                        
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(rel);
                         sc.type="hidden";
                         sc.name="rel";
                         sc.text=rel;
                         sc.value=rel;
                        //descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);*/
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                       
                }
            }
           
            
            
        }  
        
    }
    
}



function addfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);  
    if(flag=="success")
    {
        var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
        document.EmpQual.txtSNo.value=id;
       //////////////////////////////////////////////////////////////////////////////////
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
              }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
             //alert("Record is Added successfully")
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                         //    loadPage(1);
            
           }
           doFunction('Load','null');
            clr();
        //////////////////////////////////////////////////////////////////////////////
        alert("Record  Added successfully");
    
    }
    else if(flag=='failure1')
    {
            
            alert("Share Value is not validated");
            clr();
    }
  
    else
    {
            
            alert("Records r not Saved");
    }


}

function loadEmp(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
    {
           
           if(calendarControl)
                calendarControl.hide();
           
            var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.EmpQual.txtEmployee.value=ename;
            if(edob=="-")
                edob="";
            document.EmpQual.txtdob.value=edob;
          
            if(egpf==0)
                egpf="";
            document.EmpQual.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    //alert(tbody.innerText !='undefined'  && tbody.innerText !=null );
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
                    
                   // for(i=0;i<tbody.rows.length;i++)
                   //     tbody.deleteRows(i);
            }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                //alert(service.length);
                                    /* <SERVICE_LIST_SLNO> <DATE_FROM> <DATE_FROM_SESSION> <DATE_TO> 
                     <DATE_TO_SESSION> <DESIGNATION_ID> <OFFICE_ID> <EMPLOYEE_STATUS_ID>
                     <STATUS_DETAIL> <REMARKS> 
                     
                     */ 
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                            // loadPage(1);
            
                    
               
                    
                       
            
            }
            
           doFunction('Load','null');
           //alert("load");
            clr();
            
    }
    else if(flag=="failure1")
    {
            var id=document.EmpQual.txtEmployeeid.value;
            //alert("Can not Update SR. Because Employee Id "+id+" is not under your Office!");
            alert("SR controling office for this employee is different from your office!");
            document.EmpQual.txtEmployee.value="";
            document.EmpQual.txtdob.value="";
            document.EmpQual.txtGpf.value="";
            document.EmpQual.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpQual.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.EmpQual.txtEmployeeid.value;
            alert("You have no Current Posting. Can not update SR for "+id+"!");
            document.EmpQual.txtEmployee.value="";
            document.EmpQual.txtdob.value="";
            document.EmpQual.txtGpf.value="";
            document.EmpQual.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpQual.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.EmpQual.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR!");
            document.EmpQual.txtEmployee.value="";
            document.EmpQual.txtdob.value="";
            document.EmpQual.txtGpf.value="";
            document.EmpQual.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpQual.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.EmpQual.txtEmployeeid.value;
            alert("Can not update SR. Access Denined!");
            document.EmpQual.txtEmployee.value="";
            document.EmpQual.txtdob.value="";
            document.EmpQual.txtGpf.value="";
            document.EmpQual.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpQual.txtEmployeeid.focus();
            clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        document.EmpQual.txtEmployee.value="";
        document.EmpQual.txtdob.value="";
        document.EmpQual.txtGpf.value="";
        document.EmpQual.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        document.EmpQual.txtEmployeeid.focus();
        clr();
    }


}







function loadPage(page)
{
alert("load page33333333333333333333333333");
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             //alert(baseResponse);
           // service=baseResponse;
          // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          service=baseResponse.getElementsByTagName("servicedata");
            document.EmpQual.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                  
                  // alert(service);
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                 
                
            
                    
                    
                    
                    ///////////////////////////////
                    
                    }          
                       
                       
            // alert(page);
           // alert(page<totalblock);
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
               
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock;
                else
                    cell.innerHTML= ' / ' +totalblock;
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
             if(page>1)
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                //cell.innerText='';
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Previous>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
}





/////////////////////////////////////////////////////////////////////////////


function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
         if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
     
function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          //document.EmpQual.txtSNo.focus();
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
     
     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

/*
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

*/
    

function loadTable(scod)
{
    
    
    
    clr();
    document.EmpQual.cmdupdate.style.display="block";
                document.EmpQual.cmdadd.disabled=true;
               // document.EmpQual.cmdupdate.disabled=true;
                document.EmpQual.cmddelete.disabled=false;
    var r=document.getElementById(scod);
    var rcells=r.cells;
   
    
    try{
   /* document.EmpQual.txtSNo1.value=rcells.item(1).lastChild.nodeValue;
     document.EmpQual.txtSNo.value=rcells.item(1).firstChild.value;*/
    // alert(rcells.item(2).lastChild.value);
    document.EmpQual.Qualification.value=rcells.item(2).firstChild.value;
    document.EmpQual.txtSNo.value=rcells.item(1).firstChild.nodeValue;
     document.EmpQual.Special.value=rcells.item(3).firstChild.value;
     if(rcells.item(4).firstChild.value=='Yes')
              document.EmpQual.reguniv[0].checked=true;
           else   
              document.EmpQual.reguniv[1].checked=true;
    // alert("depend"+rcells.item(5).firstChild.nodeValue);
      }catch(e){
      alert("error");
      }
}



function updatefun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
    {
    var items=new Array();
          document.EmpQual.cmdadd.style.display="block";
         document.EmpQual.cmdupdate.style.display="none";  
            // changeempstatus();
         alert("Records are Updated");
        // doFunction('Load','null');
         
    /*document.EmpQual.txtDateFrom.disabled=false;
    var i=document.getElementById("fromimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EmpQual.optDateFrom[0].disabled=false;
    document.EmpQual.optDateFrom[1].disabled=false;
    
    document.EmpQual.txtDateTo.disabled=false;
    var i=document.getElementById("toimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EmpQual.optDateTo[0].disabled=false;
    document.EmpQual.optDateTo[1].disabled=false;
     */    
     doFunction('Load','null');
         clr();
         //changeempstatus();
    }
   
    else if(flag=='failure2')
    {
            
            alert("To Date is overlaped.\nRecords are not Updated");
    }
    else if(flag=='failure3')
    {
            
            alert("Existing Dates are overlaped.\nRecords are not Updated");
    }
    else
    {
        
       alert("Record not Updated");
    }
}

function deletefun(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//    alert(baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue);
    
    if(flag=="success")
    {
        alert("Records are deleted");
       //  var sc=baseResponse.getElementsByTagName("scd")[0].firstChild.nodeValue;
       /* var tbody=document.getElementById("mytable");
        var r=document.getElementById(document.EmpQual.txtSNo.value);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        */
        //clr();
        document.EmpQual.cmdadd.style.display="block";
        document.EmpQual.cmdupdate.style.display="none";
       // document.EmpQual.cmdadd.disabled=false;
       // document.EmpQual.cmdupdate.disabled=true;
        document.EmpQual.cmddelete.disabled=true;
       //doFunction('loademp','exist');
      
       //////////////////////////////////////////////////////////////////////////////////
  /*      var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
              }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                           //  loadPage(1);
            
           }*/
            clr();
        //////////////////////////////////////////////////////////////////////////////
       doFunction('Load','null');
   }
    else
    {
         
        alert("Records are not deleted");
    }
}


function trm(t)
{
   if(t!=null)
   {
        if(t.value.charAt(0)==String.fromCharCode(32))
        {
            if(t.value.length==1)
                t.value='';
        }
     
    }
}
function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }
function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
       }
            
            t.value=c;
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function checkempid()
{
    if(document.EmpQual.txtEmployeeid.value==null || document.EmpQual.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.EmpQual.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}


