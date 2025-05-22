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


function date_pick_assign1()
{

		showCalendarControl_after(document.getElementById('propose_date'));

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
        }
        
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
    else if(flag=="finish")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a frezeed VRS Willingness record.");
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
     else if(flag=="failuretest")
     {
            var eid=document.frmDeptWilling.txtEmployeeid.value;
             alert("SR controling office for this employee is different from your office!");
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
        winemp=null
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
                return false 
        }
}
function funload(baseResponse)
{
	
	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
	    if(flag=="success")
	    {  
	    	var len=baseResponse.getElementsByTagName("empid").length;
	    	
             var seq1=0;
         
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
var REQUEST_STATUS_ID=baseResponse.getElementsByTagName("REQUEST_STATUS_ID")[j].firstChild.nodeValue;
var prodate=baseResponse.getElementsByTagName("prodate")[j].firstChild.nodeValue;
 
       
    
         var tbody=document.getElementById("grid_body");
         var mycurrent_row=document.createElement("TR");
          
         
         mycurrent_row.id=slno;
         var cell=document.createElement("TD");
         if(REQUEST_STATUS_ID!='FR')
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
         var curposting=document.createElement("input");
         curposting.type="hidden";
         curposting.name="posting";
         curposting.id="posting";
         curposting.value=offname;
         cell5.appendChild(curposting);
         var currentText=document.createTextNode(offname);
         cell5.appendChild(currentText);
         mycurrent_row.appendChild(cell5);
         
         cell6=document.createElement("TD");
         var currentText=document.createTextNode(reqdate);
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
         var reason1=document.createElement("input");
         reason1.type="hidden";
         reason1.name="status";
         reason1.id="status";
         reason1.value=reqstatus;
         cell6.appendChild(reason1);
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
        var reason=document.frmDeptWilling.vrs_reason.value;
        var prodate=document.frmDeptWilling.propose_date.value;
        
            
      
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
            var willdat=document.createElement("input");
            willdat.type="hidden";
            willdat.name="prodate";
            willdat.id="prodate";
            willdat.value=prodate;
            cell6.appendChild(willdat);
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
	    else if(flag == 'Error')
	    {
	    	alert("This Employee Already having Acceptance Record");
	    	funClear();
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
    document.frmDeptWilling.propose_date.value="";
    document.frmDeptWilling.vrs_reason.value="";
     document.frmDeptWilling.btupdate.disabled=true;
     document.frmDeptWilling.btvalidate.disabled=true;
     document.frmDeptWilling.btdelete.disabled=true;
     //document.frmDeptWilling.btvalidate.disabled=true;
    document.frmDeptWilling.btadd.disabled=false; 
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
    document.frmDeptWilling.propose_date.value=rcells.item(6).lastChild.nodeValue;
    document.frmDeptWilling.vrs_reason.value=rcells.item(7).lastChild.nodeValue;
    document.frmDeptWilling.btupdate.disabled=false;
    document.frmDeptWilling.btvalidate.disabled=false;
    document.frmDeptWilling.btdelete.disabled=false;
    
   // document.frmDeptWilling.btvalidate.disabled=false;
    document.frmDeptWilling.btadd.disabled=true; 
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
        var reason=document.frmDeptWilling.vrs_reason.value;
        var r=document.getElementById(slno); 
        var rcells=r.cells;
    
        rcells.item(1).lastChild.nodeValue=empid;
        rcells.item(2).lastChild.nodeValue=ename;
        rcells.item(3).lastChild.nodeValue=desig;
        rcells.item(4).lastChild.nodeValue=posting;
        rcells.item(5).lastChild.nodeValue=willingdate;
        rcells.item(6).lastChild.nodeValue=propose_date;
        rcells.item(7).lastChild.nodeValue=reason;
        funClear();
        alert("Record Updated");
        
    }
    else
    	alert("Data has not Updated");
    
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

function fnAdd()
{
	
	var valida =  validat();
	//var vali = validation();
	if (valida == true )
	{
		
	 var empid=document.frmDeptWilling.txtEmployeeid.value;
     var will_date=document.frmDeptWilling.will_date.value;
     var vrs_reason=document.frmDeptWilling.vrs_reason.value;
     var propose_date=document.frmDeptWilling.propose_date.value;
     
 
    var url="../../../../../Create_VRS_Willingness?command=Add";  
  
      // alert(url);
        url=url+"&empid="+empid; 
     
        url=url+"&willingdate="+will_date+"&propose_date="+propose_date; 
        
       
       
       
        url=url+"&vrs_reason="+vrs_reason; 
        
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
	}
	    
    
}
function fnUpdate()
{
	//var vali = validation();
	var valida =  validat();
	
	if (valida == true )
	{
	 var empid=document.frmDeptWilling.txtEmployeeid.value;
     var will_date=document.frmDeptWilling.will_date.value;
     var vrs_reason=document.frmDeptWilling.vrs_reason.value;
     var txtslno=document.frmDeptWilling.txtslno.value;
     var propose_date=document.frmDeptWilling.propose_date.value;
     
    var url="../../../../../Create_VRS_Willingness?command=Update";  
  
       
        url=url+"&empid="+empid; 
     
        url=url+"&willingdate="+will_date+"&slno="+txtslno+"&propose_date="+propose_date; 
        
          
       
        url=url+"&vrs_reason="+vrs_reason; 
     
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
	}
}

function fncValidate()
{
	
	//var vali = validation();
	var valida =  validat();
	
	if (valida == true )
	{

var empid=document.frmDeptWilling.txtEmployeeid.value;
var will_date=document.frmDeptWilling.will_date.value;
var vrs_reason=document.frmDeptWilling.vrs_reason.value;
var txtslno=document.frmDeptWilling.txtslno.value;
var propose_date=document.frmDeptWilling.propose_date.value;

var url="../../../../../Create_VRS_Willingness?command=Validate";  

  
   url=url+"&empid="+empid; 

   url=url+"&willingdate="+will_date+"&slno="+txtslno+"&propose_date="+propose_date; 
   
     
  
   url=url+"&vrs_reason="+vrs_reason; 
//alert(url);
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
    
    var url="../../../../../Create_VRS_Willingness?command=Delete";  
  
       
        url=url+"&empid="+empid+"&slno="+txtslno; 
        
       alert(url)
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
}
function fnValidate()
{
	 var empid=document.frmDeptWilling.txtEmployeeid.value;
     var will_date=document.frmDeptWilling.will_date.value;
     var vrs_reason=document.frmDeptWilling.vrs_reason.value;
     var txtslno=document.frmDeptWilling.txtslno.value;
     
    var url="../../../../../Create_VRS_Willingness?command=Validate";  
  
       
        url=url+"&empid="+empid+"&slno="+txtslno; 
        
      
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
                if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
}
function loadvalue()
{
	 var offid=document.frmDeptWilling.txtOffId.value;
	 
	 var url="../../../../../Create_VRS_Willingness?command=Load&offid="+offid;
	 var req=getTransport();
     req.open("POST",url,true); 
     req.onreadystatechange=function()
     {
        handleResponse(req);
     }   
             if(window.XMLHttpRequest)
                 req.send(null);
         else req.send();
}

function noEnter(e)
{
   
   isIE=document.all? 1:0
       
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
                return false 
        }
}
  
function validat()
{
	
	if((document.frmDeptWilling.txtEmployeeid.value=="") || (document.frmDeptWilling.txtEmployeeid.value.length==0))
	  {
	    alert('Please Enter Employee Id');
	    return false;
	  }
  
  
  if((document.frmDeptWilling.will_date.value=="") || (document.frmDeptWilling.will_date.value=="null")|| (document.frmDeptWilling.will_date.value.length==0))
  {
   alert('Please Enter VRS Willingness Date');
    return false;
  }
  
  if((document.frmDeptWilling.propose_date.value=="") || (document.frmDeptWilling.propose_date.value=="null")|| (document.frmDeptWilling.propose_date.value.length==0))
  {
   alert('Please Enter  proposed VRS Date');
    return false;
  }
 
  if((document.frmDeptWilling.vrs_reason.value=="") || (document.frmDeptWilling.vrs_reason.value=="null")|| (document.frmDeptWilling.vrs_reason.value.length==0))
  {
    alert('Please Enter Reason for VRS');
    return false;
  }
  var startDate = document.getElementById("will_date").value;
	 var endDate = document.getElementById("propose_date").value;
	 var year1=startDate.substring(6,10);
	 var month1=startDate.substring(3,5);
	 var day1=startDate.substring(0,2);


	 var year2=endDate.substring(6,10);
	 var month2=endDate.substring(3,5);
	 var day2=endDate.substring(0,2);

	 var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
	 var firstDate = new Date(year1,month1,day1);
	 var secondDate = new Date(year2,month2,day2);

	 var diffDays = Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay));
	
	 //alert(diffDays)
	 if(diffDays>90)
	 {
		// return true;
		 
	 }
	 else
	 {
		 alert("Date interval Should Be greater than 90 days");
		 document.getElementById("propose_date").value="";
		 document.getElementById('propose_date').focus();
		 
		 return false;
		
	 }
	 return true;
}



 