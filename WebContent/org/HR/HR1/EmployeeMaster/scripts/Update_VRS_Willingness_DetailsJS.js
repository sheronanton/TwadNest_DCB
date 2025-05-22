var j=0;
var element = new Array();
var vals="";
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

function doFunction(command)
{
   // alert("command"+command);
   
   if(command=="loademp")
   {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        var ofid=document.frmDeptWilling.txtOffId.value;
        var url="../../../../../Create_VRS_Willingness?command=loademp&txtEmployeeid="+eid+"&offid="+ofid;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handleResponse(req);
        };
        
        if(window.XMLHttpRequest)
          req.send(null);
        else req.send();
       }
       
}

function handleResponse(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
      var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
           
     
      if(command=="loademp")
      {      
         loadEmployee(baseResponse);
      }
      if(command=="Add")
      {
    	  funadd(baseResponse);  
      }
      if(command=="Update")
      {
    	  funupdate(baseResponse);  
      }
      if(command=="Delete")
      {
    	  fundelete(baseResponse);  
      }
      if(command=="Load")
      {
    	  funload(baseResponse);  
      }
      if(command=="Validate")
      {
    	  funValidate(baseResponse);  
      }
    }
  }
}
 
 


function loadEmployee(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
    if(flag=="success")
    {  
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
      
      
        var des_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
        
        var posting=baseResponse.getElementsByTagName("curr_post")[0].firstChild.nodeValue;
       

        //document.frmDeptWilling.birthDate.value=dob;
        document.frmDeptWilling.txtEmployeeid.value=eid;
        document.frmDeptWilling.txtEmpName.value=ename;
        document.frmDeptWilling.txtEmpDesig.value=desig;  
        document.frmDeptWilling.txtCurOff.value=posting;
        document.frmDeptWilling.txt_desid.value=des_id;
    }
    
    
    else if(flag=="failure1")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed Willingness record.");
        document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in  status of creating VRS Willingness.");
        document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.frmDeptWilling.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.frmDeptWilling.txtEmployeeid.value;
            alert("Employee Does not belonging to this office");
            document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.frmDeptWilling.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Deputation Willingness!");
            document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.frmDeptWilling.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
           
    }
    else 
    {
    var eid=document.frmDeptWilling.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.frmDeptWilling.txtEmployeeid.value="";
    document.frmDeptWilling.txtEmployeeid.focus();
    document.frmDeptWilling.txtEmpName.value="";
    document.frmDeptWilling.txtEmpDesig.value="";
    }
}

var winemp;
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null;
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpRelievalPopup_TRN_Order.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmDeptWilling.txtEmployeeid.value=emp;
doFunction('loademp','null');
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
function funload(baseResponse)
{
	
	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
	    if(flag=="success")
	    {  
	    	var len=baseResponse.getElementsByTagName("empid").length;
	    	
             var seq1=0;
            // var i=0;
             for(var j=0;j<len;j++)
             {
       
var slno=baseResponse.getElementsByTagName("slno")[j].firstChild.nodeValue;
var empid=baseResponse.getElementsByTagName("empid")[j].firstChild.nodeValue;
var reqdate=baseResponse.getElementsByTagName("reqdate")[j].firstChild.nodeValue;
var reason=baseResponse.getElementsByTagName("reason")[j].firstChild.nodeValue;
var offname=baseResponse.getElementsByTagName("offname")[j].firstChild.nodeValue;
var desig=baseResponse.getElementsByTagName("desig")[j].firstChild.nodeValue;
var reqstatus=baseResponse.getElementsByTagName("reqstatus")[j].firstChild.nodeValue;
var empname=baseResponse.getElementsByTagName("empname")[j].firstChild.nodeValue;
var flow_id=baseResponse.getElementsByTagName("flow_id")[j].firstChild.nodeValue;
var remarks= baseResponse.getElementsByTagName("remarks")[j].firstChild.nodeValue;
var propose_date=baseResponse.getElementsByTagName("propose_date")[j].firstChild.nodeValue;  
var status_date=baseResponse.getElementsByTagName("status_date")[j].firstChild.nodeValue;  
	if(propose_date==null || propose_date=='null')
		propose_date='';
	
    if(status_date==null || status_date=='null')
    	status_date='';
   
         var tbody=document.getElementById("grid_body");
         var mycurrent_row=document.createElement("TR");
          
         
         mycurrent_row.id=slno;
         var cell=document.createElement("TD");
         if(flow_id!='FR')
         {
         var anc=document.createElement("A");
         var url="javascript:loadTable('"+slno+"')";
         anc.href=url;
         var txtedit=document.createTextNode("Edit");
         anc.appendChild(txtedit);
         cell.appendChild(anc);
         }
         else
         {var currentText=document.createTextNode("Validated");
         cell.appendChild(currentText);
         }
         mycurrent_row.appendChild(cell);
         var i=0;
         var cell2;
         var cell3;
         var cell4;     
         var cell5;
         var cell6;
         
         cell2=document.createElement("TD");
         var currentText=document.createTextNode(empid);
         cell2.appendChild(currentText);
         mycurrent_row.appendChild(cell2); 
         
         cell3=document.createElement("TD");
         var currentText=document.createTextNode(empname);
         cell3.appendChild(currentText);
         mycurrent_row.appendChild(cell3); 
         
         cell4=document.createElement("TD");
         var currentText=document.createTextNode(desig);
         cell4.appendChild(currentText);
         mycurrent_row.appendChild(cell4); 
         
         cell5=document.createElement("TD");
         var currentText=document.createTextNode(offname);
         cell5.appendChild(currentText);
         mycurrent_row.appendChild(cell5);
         
         cell6=document.createElement("TD");
         var currentText=document.createTextNode(reqdate);
         cell6.appendChild(currentText);
         mycurrent_row.appendChild(cell6);
         
         cell6=document.createElement("TD");
         var willdat=document.createElement("input");
         willdat.type="hidden";
         willdat.name="status_date";
         willdat.id="status_date";
         willdat.value=status_date;
         cell6.appendChild(willdat);
         var currentText=document.createTextNode(propose_date);
         cell6.appendChild(currentText);
         mycurrent_row.appendChild(cell6);
         
         cell6=document.createElement("TD");
         var currentText=document.createTextNode(reason);
         cell6.appendChild(currentText);
         mycurrent_row.appendChild(cell6);
         
         
         cell6=document.createElement("TD");
         var currentText=document.createTextNode(reqstatus);
         cell6.appendChild(currentText);
         mycurrent_row.appendChild(cell6);
         
         tbody.appendChild(mycurrent_row);
       
         funClear();   
       
     }
	    }
}

function funadd(baseResponse)
{
	
	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
	    if(flag=="success")
	    {  
	     
	      var slno=baseResponse.getElementsByTagName("slno")[0].firstChild.nodeValue;

        
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        var ename=document.frmDeptWilling.txtEmpName.value;
        var desig=document.frmDeptWilling.txtEmpDesig.value;  
        var posting=document.frmDeptWilling.txtCurOff.value;
        var willingdate=document.frmDeptWilling.will_date.value;
        var propose_date=document.frmDeptWilling.propose_date.value;
        var reason=document.frmDeptWilling.vrs_reason.value;
      
        if(flag == 'success')
        {
            
      
            var tbody=document.getElementById("grid_body");
            var mycurrent_row=document.createElement("TR");
         
            
            mycurrent_row.id=slno;
            var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+slno+"')";
            anc.href=url;
            var txtedit=document.createTextNode("Edit");
            anc.appendChild(txtedit);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);
            var i=0;
            var cell2;
            var cell3;
            var cell4;     
            var cell5;
            var cell6;
            
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(eid);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2); 
            
            cell3=document.createElement("TD");
            var currentText=document.createTextNode(ename);
            cell3.appendChild(currentText);
            mycurrent_row.appendChild(cell3); 
            
            cell4=document.createElement("TD");
            var currentText=document.createTextNode(desig);
            cell4.appendChild(currentText);
            mycurrent_row.appendChild(cell4); 
            
            cell5=document.createElement("TD");
            var currentText=document.createTextNode(posting);
            cell5.appendChild(currentText);
            mycurrent_row.appendChild(cell5);

        
            cell6=document.createElement("TD");
            var currentText=document.createTextNode(willingdate);
            cell6.appendChild(currentText);
            mycurrent_row.appendChild(cell6);
            
            cell6=document.createElement("TD");
            
            var currentText=document.createTextNode(prodate);
            cell6.appendChild(currentText);
            mycurrent_row.appendChild(cell6);
            
            cell6=document.createElement("TD");
            var currentText=document.createTextNode(reason);
            cell6.appendChild(currentText);
            mycurrent_row.appendChild(cell6);
             
            cell6=document.createElement("TD");
            var currentText=document.createTextNode("Pending");
            cell6.appendChild(currentText);
           
            mycurrent_row.appendChild(cell6);
            
            tbody.appendChild(mycurrent_row);
            
           /* document.frmDeptWilling.txtRno.disabled=true;
            document.frmDeptWilling.txtdptdate.disabled=true;
            document.frmDeptWilling.cmb_Deptid.disabled=true;
            document.frmDeptWilling.cmb_DeptOffice.disabled=true;
            document.frmDeptWilling.txtpostname.disabled=true;
            document.frmDeptWilling.txtpayscale.disabled=true;
            document.frmDeptWilling.txtremark.disabled=true;*/
            alert("Added Successfully");
            funClear();   
          
            }
        }
	    }


function funClear()
{
	document.frmDeptWilling.txtslno.value="";
    document.frmDeptWilling.txtEmployeeid.value="";
    document.frmDeptWilling.txtEmpName.value="";
    document.frmDeptWilling.txtEmpDesig.value="";  
    document.frmDeptWilling.txtCurOff.value="";
    document.frmDeptWilling.txt_desid.value="";
    document.frmDeptWilling.will_date.value="";
    document.frmDeptWilling.vrs_reason.value="";
    document.frmDeptWilling.propose_date.value="";
    document.frmDeptWilling.status_Date.value="";
    document.frmDeptWilling.Remarks.value="";
    document.frmDeptWilling.group1[0].checked=false;
    document.frmDeptWilling.group1[1].checked=false;
	 document.frmDeptWilling.btupdate.disabled=true;
	 document.frmDeptWilling.btdelete.disabled=true;
	 document.frmDeptWilling.btvalidate.disabled=true;
   
}


function loadTable(scod)
{
    var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("grid_body");
    var table=document.getElementById("mytable");
       document.frmDeptWilling.txtslno.value=scod;
    document.frmDeptWilling.txtEmployeeid.value=rcells.item(1).lastChild.nodeValue;
    document.frmDeptWilling.txtEmpName.value=rcells.item(2).lastChild.nodeValue;
    document.frmDeptWilling.txtEmpDesig.value=rcells.item(3).lastChild.nodeValue;
    document.frmDeptWilling.txtCurOff.value=rcells.item(4).lastChild.nodeValue;
    document.frmDeptWilling.will_date.value=rcells.item(5).lastChild.nodeValue;
  
    if(rcells.item(6).lastChild.nodeValue=='null'||rcells.item(6).lastChild.nodeValue==null)
    		document.frmDeptWilling.propose_date.value='';
        else
        	
    		document.frmDeptWilling.propose_date.value=rcells.item(6).lastChild.nodeValue;
   // alert(rcells.item(6).firstChild.value);
    if(rcells.item(6).firstChild.value=='null'||rcells.item(6).firstChild.value==null)		
                
    	document.frmDeptWilling.status_Date.value='';
    else	
    	document.frmDeptWilling.status_Date.value=rcells.item(6).firstChild.value;
    document.frmDeptWilling.vrs_reason.value=rcells.item(7).lastChild.nodeValue;
     var accept=rcells.item(8).lastChild.nodeValue;
     
     if(accept=='Accepted')
    	 document.frmDeptWilling.group1[0].checked=true;
     else
    	 if(accept=='Rejected')
    	 document.frmDeptWilling.group1[1].checked=true;
    	 else
    		 if(accept=='Withdraw')
    		 document.frmDeptWilling.group1[2].checked=true; 
    if(rcells.item(7).firstChild.value==null||rcells.item(7).firstChild.value=='null')
    document.frmDeptWilling.Remarks.value="";
    else
    	document.frmDeptWilling.Remarks.value=rcells.item(7).firstChild.value;
    document.frmDeptWilling.btupdate.disabled=false;
    document.frmDeptWilling.btdelete.disabled=false;
    document.frmDeptWilling.btvalidate.disabled=false;
    
}
function funValidate(baseResponse)
{
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
    if(flag=="success")
    { 
    	alert("Validated Successfully");
    	 var tlist=document.getElementById("grid_body");
         try{
              tist.innerHTML="";
            }
         catch(e)
              {
               tlist.innerText="";
             }  
         while (tlist.childNodes.length > 0) {
        	 tlist.removeChild(tlist.firstChild);
         }
     loadvalue();
     funClear();
    }
    
}
function funupdate(baseResponse)
{
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
    if(flag=="success")
    {  var slno=document.frmDeptWilling.txtslno.value;
       var empid=document.frmDeptWilling.txtEmployeeid.value;
        var ename=document.frmDeptWilling.txtEmpName.value;
        var desig=document.frmDeptWilling.txtEmpDesig.value;  
        var posting=document.frmDeptWilling.txtCurOff.value;
        var willingdate=document.frmDeptWilling.will_date.value;
        var propose_date=document.frmDeptWilling.propose_date.value;
        var status_date=document.frmDeptWilling.status_Date.value;
        var reason=document.frmDeptWilling.vrs_reason.value;
        var remarks=document.frmDeptWilling.Remarks.value;
        var Accept='';
        if(document.frmDeptWilling.group1[0].checked)
        	Accept='Accepted';
        if(document.frmDeptWilling.group1[1].checked)
        	Accept='Rejected';
        else
        	 if(document.frmDeptWilling.group1[2].checked)
        	Accept='Withdraw';
        	 
        var r=document.getElementById(slno); 
        var rcells=r.cells;
        //alert(status_date);
        rcells.item(1).lastChild.nodeValue=empid;
        rcells.item(2).lastChild.nodeValue=ename;
        rcells.item(3).lastChild.nodeValue=desig;
        rcells.item(4).lastChild.nodeValue=posting;
        rcells.item(5).lastChild.nodeValue=willingdate;
        rcells.item(7).lastChild.nodeValue=reason;
        rcells.item(8).lastChild.nodeValue=Accept;
        rcells.item(7).firstChild.value=remarks;
        rcells.item(6).firstChild.value=status_date;
        rcells.item(6).lastChild.nodeValue=propose_date;
       funClear();
        alert("Record Updated");
        
    }
   
    
     if(flag=='Errors')
    {
    	alert("This Employee Already Having two Rejection Records");
    }
     
}

function fundelete(baseResponse)
{
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
    if(flag=="success")
    {   
    	 var slno=document.frmDeptWilling.txtslno.value;
    	 var r=document.getElementById(slno); 
        
         
         
         var tlist=document.getElementById("grid_body");
         try{
              tist.innerHTML="";
            }
         catch(e)
              {
               tlist.innerText="";
             }  
         while (tlist.childNodes.length > 0) {
        	 tlist.removeChild(tlist.firstChild);
         }
         loadvalue();
     funClear();
     alert("Record Deleted");
     
    }
    else
    	alert("Data has not Deleted");
}

function  fnUpdate()
{  
	if (checkNull())
	{
	 var slno=document.frmDeptWilling.txtslno.value;
     var will_date=document.frmDeptWilling.will_date.value;
     var propose_date=document.frmDeptWilling.propose_date.value;
     var vrs_reason=document.frmDeptWilling.vrs_reason.value;
     var empid=document.frmDeptWilling.txtEmployeeid.value;
     var status_date=document.frmDeptWilling.status_Date.value;
    // alert(status_date);
     if(document.frmDeptWilling.group1[0].checked)
    	 status='Accepted';
     else
    	 if(document.frmDeptWilling.group1[1].checked)
    	 status='Rejected';
    	 else
     if(document.frmDeptWilling.group1[2].checked)
    	 status='Withdraw';
     else
    	 status='Pending';
     var remarks=document.frmDeptWilling.Remarks.value;
   
    var url="../../../../../Update_VRS_Willingness?command=Update";  
        url=url+"&empid="+empid; 
        url=url+"&willingdate="+will_date; 
        url=url+"&vrs_reason="+vrs_reason+"&remarks="+remarks+"&status="+status+"&slno="+slno+"&propose_date="+propose_date+"&status_date="+status_date; 
     
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        };   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
                
	}       
    
}

function fnDelete()
{
	 var empid=document.frmDeptWilling.txtEmployeeid.value;
     var will_date=document.frmDeptWilling.will_date.value;
     var vrs_reason=document.frmDeptWilling.vrs_reason.value;
     var txtslno=document.frmDeptWilling.txtslno.value;
    
    var url="../../../../../Update_VRS_Willingness?command=Delete";  
  
       
        url=url+"&empid="+empid+"&slno="+txtslno; 
        
       
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        };   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
}

function fnValidate()
{  
	if (checkNull())
	{
	 var slno=document.frmDeptWilling.txtslno.value;
     var will_date=document.frmDeptWilling.will_date.value;
     var propose_date=document.frmDeptWilling.propose_date.value;
     var vrs_reason=document.frmDeptWilling.vrs_reason.value;
     var empid=document.frmDeptWilling.txtEmployeeid.value;
     var status_date=document.frmDeptWilling.status_Date.value;
    // alert(status_date);
     if(document.frmDeptWilling.group1[0].checked)
    	 status='Accepted';
     else
    	 if(document.frmDeptWilling.group1[1].checked)
    	 status='Rejected';
    	 else
     if(document.frmDeptWilling.group1[2].checked)
    	 status='Withdraw';
     else
    	 status='Pending';
     var remarks=document.frmDeptWilling.Remarks.value;
   
    var url="../../../../../Update_VRS_Willingness?command=Validate";  
        url=url+"&empid="+empid; 
        url=url+"&willingdate="+will_date; 
        url=url+"&vrs_reason="+vrs_reason+"&remarks="+remarks+"&status="+status+"&slno="+slno+"&propose_date="+propose_date+"&status_date="+status_date; 
     
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        };   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
                
	}       
    
}
function loadvalue()
{
	 var offid=document.frmDeptWilling.txtOffId.value;
	
	 var url="../../../../../Update_VRS_Willingness?command=Load&offid="+offid;
	 var req=getTransport();
     req.open("POST",url,true); 
     req.onreadystatechange=function()
     {
        handleResponse(req);
     };   
             if(window.XMLHttpRequest)
                 req.send(null);
         else req.send();
}

function noEnter(e)
{
   
   isIE=document.all? 1:0;
       
   keyEntry = !isIE? e.which:event.keyCode;
                  
   if(keyEntry=='38')
   {
     return false;
   }
}

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
                return false; 
        }
}
  
function checkNull()
{
	if((document.frmDeptWilling.txtEmployeeid.value=="") || (document.frmDeptWilling.txtEmployeeid.value.length==0))
	  {
	    alert('Please Enter Employee Id.');
	    return false;
	  }
  if((document.frmDeptWilling.vrs_reason.value=="") || (document.frmDeptWilling.vrs_reason.value.length==0))
  {
    alert('Please Enter Reason for VRS.');
    return false;
  }
  
  if((document.frmDeptWilling.will_date.value=="") || (document.frmDeptWilling.will_date.value.length==0))
  {
   alert('Please Enter Request Date.');
    return false;
  }
  
  if((document.frmDeptWilling.propose_date.value=="") || (document.frmDeptWilling.propose_date.value.length==0))
  {
   alert('Please Enter Propose Date.');
    return false;
  }
  if((document.frmDeptWilling.status_Date.value=="") || (document.frmDeptWilling.status_Date.value.length==0))
  {
   alert('Please Enter Status Updated Date.');
    return false;
  }
  if(document.frmDeptWilling.group1[0].checked==false && document.frmDeptWilling.group1[1].checked==false && document.frmDeptWilling.group1[2].checked==false )
  {
	  alert("Please Enter VRS Status");
	  return false;  
  }	  
  var pro_vrs=document.getElementById("propose_date").value;
  var status_Date=document.getElementById("status_Date").value;
  if(status_Date<pro_vrs)
  {
          alert('Status Updation date should be greater than Proposed VRS date');
      return false;
  }



  return true;
}

 