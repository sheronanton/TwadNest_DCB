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


function date_pick_assign2()
{
	//alert("hai");

		showCalendarControl_after(document.getElementById('Extension_date'));

}


function doFunction(command)
{
   // alert("command"+command);
   
   if(command=="loademp")
   {
        var eid=document.extension_vrs_willing.txtEmployeeid.value;
        var ofid=document.extension_vrs_willing.txtOffId.value;
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
       

        //document.extension_vrs_willing.birthDate.value=dob;
        document.extension_vrs_willing.txtEmployeeid.value=eid;
        document.extension_vrs_willing.txtEmpName.value=ename;
        document.extension_vrs_willing.txtEmpDesig.value=desig;  
        document.extension_vrs_willing.txtCurOff.value=posting;
        document.extension_vrs_willing.txt_desid.value=des_id;
    }
    
    
    else if(flag=="failure1")
    {
        var eid=document.extension_vrs_willing.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.extension_vrs_willing.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed Willingness record.");
        document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.extension_vrs_willing.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in  status of creating VRS Willingness.");
        document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.extension_vrs_willing.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.extension_vrs_willing.txtEmployeeid.value;
            alert("Employee Does not belonging to this office");
            document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.extension_vrs_willing.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Deputation Willingness!");
            document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.extension_vrs_willing.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.extension_vrs_willing.txtEmployeeid.value="";
        document.extension_vrs_willing.txtEmployeeid.focus();
        document.extension_vrs_willing.txtEmpName.value="";
        document.extension_vrs_willing.txtEmpDesig.value="";
           
    }
    else 
    {
    var eid=document.extension_vrs_willing.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.extension_vrs_willing.txtEmployeeid.value="";
    document.extension_vrs_willing.txtEmployeeid.focus();
    document.extension_vrs_willing.txtEmpName.value="";
    document.extension_vrs_willing.txtEmpDesig.value="";
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
document.extension_vrs_willing.txtEmployeeid.value=emp;
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
	    	//alert("length is====*****>"+len);
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
var EXTENSION_VRS_DATE= baseResponse.getElementsByTagName("EXTENSION_VRS_DATE")[j].firstChild.nodeValue;
var propose_date=baseResponse.getElementsByTagName("propose_date")[j].firstChild.nodeValue;  
var status_date=baseResponse.getElementsByTagName("status_date")[j].firstChild.nodeValue;  


if(propose_date==null || propose_date=='null')
		propose_date='';

if(EXTENSION_VRS_DATE==null || EXTENSION_VRS_DATE=='null')
	EXTENSION_VRS_DATE='';	


if(remarks==null || remarks=='null')
	remarks='';	
   
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
         
         cell7=document.createElement("TD");
         var currentText=document.createTextNode(reason);
         cell7.appendChild(currentText);
         mycurrent_row.appendChild(cell7);
         
         cell8=document.createElement("TD");
         var currentText=document.createTextNode(EXTENSION_VRS_DATE);
         cell8.appendChild(currentText);
         mycurrent_row.appendChild(cell8);
         

         cell9=document.createElement("TD");
         var currentText=document.createTextNode(remarks);
         cell9.appendChild(currentText);
         mycurrent_row.appendChild(cell9);
         
         
         
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

        
        var eid=document.extension_vrs_willing.txtEmployeeid.value;
        var ename=document.extension_vrs_willing.txtEmpName.value;
        var desig=document.extension_vrs_willing.txtEmpDesig.value;  
        var posting=document.extension_vrs_willing.txtCurOff.value;
        var willingdate=document.extension_vrs_willing.will_date.value;
        var propose_date=document.extension_vrs_willing.propose_date.value;
        var reason=document.extension_vrs_willing.vrs_reason.value;
        var Remarks=document.extension_vrs_willing.Remarks.value;
        var EXTENSION_VRS_DATE=document.extension_vrs_willing.Extension_date.value;
      
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
            
            cell7=document.createElement("TD");
            var currentText=document.createTextNode(reason);
            cell7.appendChild(currentText);
            mycurrent_row.appendChild(cell7);
             
            cell8=document.createElement("TD");
            var currentText=document.createTextNode(EXTENSION_VRS_DATE);
            cell8.appendChild(currentText);
            mycurrent_row.appendChild(cell8);
            
            
            cell9=document.createElement("TD");
            var currentText=document.createTextNode(Remarks);
            cell9.appendChild(currentText);
            mycurrent_row.appendChild(cell9);
            
            
            
            
            tbody.appendChild(mycurrent_row);
            
           /* document.extension_vrs_willing.txtRno.disabled=true;
            document.extension_vrs_willing.txtdptdate.disabled=true;
            document.extension_vrs_willing.cmb_Deptid.disabled=true;
            document.extension_vrs_willing.cmb_DeptOffice.disabled=true;
            document.extension_vrs_willing.txtpostname.disabled=true;
            document.extension_vrs_willing.txtpayscale.disabled=true;
            document.extension_vrs_willing.txtremark.disabled=true;*/
            alert("Added Successfully");
            funClear();   
          
            }
        }
	    }


function funClear()
{
	document.extension_vrs_willing.txtslno.value="";
    document.extension_vrs_willing.txtEmployeeid.value="";
    document.extension_vrs_willing.txtEmpName.value="";
    document.extension_vrs_willing.txtEmpDesig.value="";  
    document.extension_vrs_willing.txtCurOff.value="";
    document.extension_vrs_willing.txt_desid.value="";
    document.extension_vrs_willing.will_date.value="";
    document.extension_vrs_willing.vrs_reason.value="";
    document.extension_vrs_willing.propose_date.value="";
    document.extension_vrs_willing.Extension_date.value="";
    document.extension_vrs_willing.Remarks.value="";
    
    
	 document.extension_vrs_willing.btupdate.disabled=true;
	 document.extension_vrs_willing.btdelete.disabled=true;
	 document.extension_vrs_willing.btvalidate.disabled=true;
   
}


function loadTable(scod)
{
	document.extension_vrs_willing.btupdate.disabled=false;
    document.extension_vrs_willing.btdelete.disabled=false;
    document.extension_vrs_willing.btvalidate.disabled=false;
    var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("grid_body");
    var table=document.getElementById("mytable");
       document.extension_vrs_willing.txtslno.value=scod;
    document.extension_vrs_willing.txtEmployeeid.value=rcells.item(1).lastChild.nodeValue;
    document.extension_vrs_willing.txtEmpName.value=rcells.item(2).lastChild.nodeValue;
    document.extension_vrs_willing.txtEmpDesig.value=rcells.item(3).lastChild.nodeValue;
    document.extension_vrs_willing.txtCurOff.value=rcells.item(4).lastChild.nodeValue;
    document.extension_vrs_willing.will_date.value=rcells.item(5).lastChild.nodeValue;
    document.extension_vrs_willing.vrs_reason.value=rcells.item(7).lastChild.nodeValue;
    document.extension_vrs_willing.Extension_date.value=rcells.item(8).lastChild.nodeValue;
    document.extension_vrs_willing.Remarks.value=rcells.item(9).lastChild.nodeValue;
    
    if(rcells.item(6).lastChild.nodeValue=='null'||rcells.item(6).lastChild.nodeValue==null)
		document.extension_vrs_willing.propose_date.value='';
    else
    	
		document.extension_vrs_willing.propose_date.value=rcells.item(6).lastChild.nodeValue;
// alert(rcells.item(6).firstChild.value);

    
    
    
  
  
   
    
   
    
    
    		
   
    
    
    
    
    
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
    {  var slno=document.extension_vrs_willing.txtslno.value;
       var empid=document.extension_vrs_willing.txtEmployeeid.value;
        var ename=document.extension_vrs_willing.txtEmpName.value;
        var desig=document.extension_vrs_willing.txtEmpDesig.value;  
        var posting=document.extension_vrs_willing.txtCurOff.value;
        var willingdate=document.extension_vrs_willing.will_date.value;
        var propose_date=document.extension_vrs_willing.propose_date.value;
        var Extension_date=document.extension_vrs_willing.Extension_date.value;
        var reason=document.extension_vrs_willing.vrs_reason.value;
        var remarks=document.extension_vrs_willing.Remarks.value;
       
        
        	 
        var r=document.getElementById(slno); 
        var rcells=r.cells;
        
        rcells.item(1).lastChild.nodeValue=empid;
        rcells.item(2).lastChild.nodeValue=ename;
        rcells.item(3).lastChild.nodeValue=desig;
        rcells.item(4).lastChild.nodeValue=posting;
        rcells.item(5).lastChild.nodeValue=willingdate;
        rcells.item(6).lastChild.nodeValue=propose_date;
        rcells.item(7).lastChild.nodeValue=reason;
        rcells.item(8).lastChild.nodeValue=Extension_date;      
        rcells.item(9).firstChild.value=remarks;
       
        
       funClear();
        alert("Record Updated");
        document.extension_vrs_willing.submit();
    }
    else
    	alert("Data has not Updated");
    
}

function fundelete(baseResponse)
{
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
    if(flag=="success")
    {   
    	 var slno=document.extension_vrs_willing.txtslno.value;
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
	var val=validation();
	if (val == true)
	{
	 var slno=document.extension_vrs_willing.txtslno.value;
	
     var will_date=document.extension_vrs_willing.will_date.value;
     
     var propose_date=document.extension_vrs_willing.propose_date.value;
     
     var vrs_reason=document.extension_vrs_willing.vrs_reason.value;
   
    
     var empid=document.getElementById("txtEmployeeid").value;
     
    
    
     var Extension_date=document.getElementById("Extension_date").value;
    
     var remarks=document.getElementById("Remarks").value;
    
    var url="../../../../../Extension_VRS_Willingness_Details?command=Update";  
        url=url+"&empid="+empid; 
        url=url+"&willingdate="+will_date; 
        url=url+"&vrs_reason="+vrs_reason+"&remarks="+remarks+"&Extension_date="+Extension_date+"&slno="+slno+"&propose_date="+propose_date; 
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
	 var empid=document.extension_vrs_willing.txtEmployeeid.value;
     var will_date=document.extension_vrs_willing.will_date.value;
     var vrs_reason=document.extension_vrs_willing.vrs_reason.value;
     var txtslno=document.extension_vrs_willing.txtslno.value;
    
    var url="../../../../../Extension_VRS_Willingness_Details?command=Delete";  
  
       
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
		 var slno=document.extension_vrs_willing.txtslno.value;
			
	     var will_date=document.extension_vrs_willing.will_date.value;
	     
	     var propose_date=document.extension_vrs_willing.propose_date.value;
	     
	     var vrs_reason=document.extension_vrs_willing.vrs_reason.value;
	   
	    
	     var empid=document.getElementById("txtEmployeeid").value;
	     
	    
	    
	     var Extension_date=document.getElementById("Extension_date").value;
	    
	     var remarks=document.getElementById("Remarks").value;
	    
	    var url="../../../../../Extension_VRS_Willingness_Details?command=Validate";  
	        url=url+"&empid="+empid; 
	        url=url+"&willingdate="+will_date; 
	        url=url+"&vrs_reason="+vrs_reason+"&remarks="+remarks+"&Extension_date="+Extension_date+"&slno="+slno+"&propose_date="+propose_date; 
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
function loadvalue()
{
	 var offid=document.extension_vrs_willing.txtOffId.value;
	
	 var url="../../../../../Extension_VRS_Willingness_Details?command=Load&offid="+offid;
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
  
function validation()
{
	
	
  if((document.extension_vrs_willing.txtEmployeeid.value=="") || (document.extension_vrs_willing.txtEmployeeid.value.length==0))
  {
	alert('Please Enter Employee Id');
    return false;
  }
  if((document.extension_vrs_willing.vrs_reason.value=="") || (document.extension_vrs_willing.vrs_reason.value.length==0))
  {
    alert('Please Enter Reason for VRS');
    return false;
  }
  
  if((document.extension_vrs_willing.will_date.value=="") || (document.extension_vrs_willing.will_date.value.length==0))
  {
    alert('Please Enter Request Date');
    return false;
  }
  
  if((document.extension_vrs_willing.propose_date.value=="") || (document.extension_vrs_willing.propose_date.value.length==0))
  {
    alert('Please Enter Propose Date');
    return false;
  }
  if((document.getElementById("Extension_date").value=="") || (document.getElementById("Extension_date").value.length==0) || (document.getElementById("Extension_date").value =="null"))
  {
    alert('Please Enter Extension  Date');
    return false;
  }
  
    var pro_vrs=document.getElementById("propose_date").value;
	var ext_date=document.getElementById("Extension_date").value;
	if(ext_date<=pro_vrs)
	{
		alert('Extension date should be greater than Proposed VRS date');
	    return false;
	}
	
  return true;
}

 