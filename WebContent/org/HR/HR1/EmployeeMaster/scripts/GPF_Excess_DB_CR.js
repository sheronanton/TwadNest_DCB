window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

function selectMonth1(slipyear)
{
	 v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();

var rel_year=document.getElementById("int_year").value;	
var i;
for(i=document.Hrm_TransJoinForm.int_month.options.length-1;i>=0;i--)
{

	document.Hrm_TransJoinForm.int_month.remove(i);
}

var minorcmb = document.Hrm_TransJoinForm.int_month;
	 var start_months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	    var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	   
if(parseInt(rel_year)==parseInt(slipyear))
{
	for(var i=0; i<(parseInt(mn)-1); i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}
	
}else{
	for(var i=0; i<12; i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}
}
}

gpfcheckstatus=false;
function adding()
{
	
	xmlhttp=getxmlhttpObject();
	var gpf_no=document.getElementById("txtGpfNo").value;
	var checkflag1=true;
	 if(gpf_no==""){
	   alert('Enter GPF No'); 
	 }else{

	  var misstype="";
	  var categorytype="";
	  var flag=0;
	      if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
	      {
	          flag=1;
	          misstype='CR';
	      }else if( document.Hrm_TransJoinForm.missing_type[1].checked==true)
	      {
	          flag=1;
	          misstype='DB';
	      }
	      if(document.Hrm_TransJoinForm.category_type[0].checked==true)
	      {
	          flag=1;
	          categorytype='Regular';
	      }else if( document.Hrm_TransJoinForm.category_type[1].checked==true)
	      {
	          flag=1;
	          categorytype=document.getElementById("imp_types").value;
	      }
	 
	      if(flag!=0)
	      {
	          var relmonth=document.getElementById("rel_month").value;
	          var relyear=document.getElementById("rel_year").value;
	    
	 url="../../../../../GPF_Excess_DB_CR?command=checkgpf&gpf_no="+gpf_no+"&misstype="+misstype+"&categorytype="+categorytype+"&relmonth="+relmonth+"&relyear="+relyear;
	 url=url+"&sid="+Math.random();
	 xmlhttp.open("GET",url,true);
	 xmlhttp.onreadystatechange=function()
	  {
	      //requesthandle(req);
	      if(xmlhttp.readyState==4)
	      {
	         
	          if(xmlhttp.status==200)
	          { 
	             
	              //getDeatils5();
	          var    response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	          var    command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	           var   flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	          
	           if(command=="checkgpf")
	           {
	              
	               if(flag=='success')
	               {
	                
	            	   document.getElementById("txtGpfNo").value="";
	                   document.getElementById("emp_name").value="";
	                  alert('This GPF NO Excess Credit/Debit is already submitted');
	                 
	                   
	                  // checkflag1=false;
	               }
	               else if(flag=='retried')
	               {
	            	   alert("Employee is already retired");
	            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
	            	   if(data=='Failentry')
	            	   {
	            		   alert("Relative month & year should be less than Retired date");
	            		  
	            	   }
	            	   else
	            	   {
	            		   document.getElementById("intrestDiv").style.display='none';
		            	   document.getElementById("intrestDiv1").style.display='none';
		            	   document.getElementById("intrestDiv2").style.display='none';
		            	   document.getElementById("intrestDiv3").style.display='none';
		            	   document.getElementById("int_year").value=0;
	            	   }
	            	   
	               }
	               
	               else
	                   {
	            	   var int_year=document.getElementById("int_year").value;
	            	   
	            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
	            	  
		     	         var relmonth=document.getElementById("rel_month").value;
		   	          var relyear=document.getElementById("rel_year").value;
		   	       var response1=true;
	            	   if(data=='entry')
	            	   {
	            		   document.getElementById("intrestDiv").style.display='block';
		            	   document.getElementById("intrestDiv1").style.display='block';
		            	   document.getElementById("intrestDiv2").style.display='block';
		            	   document.getElementById("intrestDiv3").style.display='block';
		            	   document.getElementById("retiredDate").value=response.getElementsByTagName("retriedDate")[0].firstChild.nodeValue;
	            		if(int_year==0) {  
	            			alert("Employee is already retired");
	            			alert("select Interest calculated month and year");
	            		   return false;
	            		}
	            		else
	            		{
	            			//alert(int_year+relyear);
	            			
			     	          var int_month=document.getElementById("int_month").value;
	            			 if(parseInt(int_year)<parseInt(relyear))
	  	            	   {
	  	            		   alert("Interest calculate Month & year greater than Relating Month & year");
	  	            		   return false;
	  	            	   }
	  	            	   else if(parseInt(int_year)==parseInt(relyear) && parseInt(int_month)<parseInt(relmonth))
	  	            	   {
	  	            		   alert("Interest calculate Month & year greater than Relating Month & year");
	  	            		   return false;
	  	            	   }
	            		}
	            	   }
	            	   else if(data=='Failentry')
	            	   {
// check condition alert for retired after slipyear
	            		   response1 = confirm("Relative month & year is greater than Retired date.. Do u want to Proceed ");
	            		   if (response1 == true)
	            		      {
	            		   
	            		   document.getElementById("intrestDiv").style.display='block';
		            	   document.getElementById("intrestDiv1").style.display='block';
		            	  // alert("df");
		            	   document.getElementById("intrestDiv2").style.display='block';
		            	 //  alert("df");
		            	   document.getElementById("intrestDiv3").style.display='block';
		            	  // alert("df");
		            	 //  alert(response.getElementsByTagName("retriedDate")[0].firstChild.nodeValue);
		            	   document.getElementById("retiredDate").value=response.getElementsByTagName("retriedDate")[0].firstChild.nodeValue;
	            		if(int_year==0) {  
	            			alert("Employee is already retired..select Interest calculated month and year");
	            			//alert("");
	            		   return false;
	            		}
	            		else
	            		{
	            			//alert(int_year+relyear);
	            			
			     	          var int_month=document.getElementById("int_month").value;
	            			 if(parseInt(int_year)<parseInt(relyear))
	  	            	   {
	  	            		   alert("Interest calculate Month & year greater than Relating Month & year");
	  	            		   return false;
	  	            	   }
	  	            	   else if(parseInt(int_year)==parseInt(relyear) && parseInt(int_month)<parseInt(relmonth))
	  	            	   {
	  	            		   alert("Interest calculate Month & year greater than Relating Month & year");
	  	            		   return false;
	  	            	   }
	            		}
	            	   }
	            	   }
	            	   else
	            	   {
	            		   document.getElementById("intrestDiv").style.display='none';
		            	   document.getElementById("intrestDiv1").style.display='none';
		            	   document.getElementById("intrestDiv2").style.display='none';
		            	   document.getElementById("intrestDiv3").style.display='none';
		            	   document.getElementById("int_year").value=0;
	            	   }
	            	  
	            	   if (response1 == true)
         		      {
	            	   if(nullcheck1()){
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
	            		    // startwaiting(document.Hrm_TransJoinForm);  
	            		     var missingtype="";
	            		     if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
	            		     {
	            		    	 missingtype =document.Hrm_TransJoinForm.missing_type[0].value;
	            		     }else if(document.Hrm_TransJoinForm.missing_type[1].checked==true)
	            		     {
	            		    	 missingtype =document.Hrm_TransJoinForm.missing_type[1].value;
	            		     }
	            		     
	            			var gpfno=document.getElementById("txtGpfNo").value;
	            			var amount=document.getElementById("amount").value;
	            			var relmonth=document.getElementById("rel_month").value;
	            			var relyear=document.getElementById("rel_year").value;
	            			 	   
	            			   winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Excess_DB_CR_Popup.jsp?command=add&gpfno="+gpfno+"&amount="+amount+"&relmonth="+relmonth+"&relyear="+relyear+"&missingtype="+missingtype+"","mywi","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	            		   
	            		   
	            		   // winemp.document.Hrm_TransJoinFormpop.remarks.value = document.forms[0].txtOffId.value;
	            			   winemp.moveTo(250,250);  
	            			   winemp.focus();
	            			}
	                   }
	                   }
	           }
	          }
	      }
	  }
	 xmlhttp.send(null);
	      }
	
	
	 }	
		
}
function checkExistingRecords()
{
	xmlhttp=getxmlhttpObject();
nullcheck1();
	     var missingtype="";
	     if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
	     {
	    	 missingtype =document.Hrm_TransJoinForm.missing_type[0].value;
	     }else if(document.Hrm_TransJoinForm.missing_type[1].checked==true)
	     {
	    	 missingtype =document.Hrm_TransJoinForm.missing_type[1].value;
	     }
	     
		var gpfno=document.getElementById("txtGpfNo").value;
		var amount=document.getElementById("amount").value;
		var relmonth=document.getElementById("rel_month").value;
		var relyear=document.getElementById("rel_year").value;
		var categorytype="";
		if(document.Hrm_TransJoinForm.category_type[0].checked==true)
		{
			//flag=1;
			categorytype='Regular';
		}else if( document.Hrm_TransJoinForm.category_type[1].checked==true)
		{
			//flag=1;
			categorytype=document.getElementById("imp_types").value;
		}	   
		url="../../../../../GPF_Excess_DB_CR?command=CheckExistingRecords&gpf_no="+gpf_no+"&misstype="+misstype+"categorytype="+categorytype+"relmonth="+relmonth+"relyear="+relyear;
	       xmlhttp.open("GET",url,true);
	       xmlhttp.onreadystatechange=checkExistingResult;
	       xmlhttp.send(null); 
	
	
	}
function checkExistingResult()
{
	 var flag,command,response;
	   // var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	  
	    if(xmlhttp.readyState==4)
	    {
	    	
	       if(xmlhttp.status==200)
	       {
	            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;

	            if(command=="get")
	            {
	            	if(flag=='success')
	                {
	                  
	                  alert("Records Already Exists");
	            		
	                }
	            	
	            }
	       }
	    }
}
function show1()
{	
    if (document.Hrm_TransJoinForm.missing_type[0].checked==true)
    {
    	 document.getElementById("debit").style.display='block';
         document.getElementById("credit").style.display='none';
         document.getElementById("refund").style.display='none';
         for(i=0;i<document.Hrm_TransJoinForm.slipPrint.length;i++)
      	{
         document.Hrm_TransJoinForm.slipPrint[i].disabled=true;
      	}
    }
    else
    {
    	document.getElementById("debit").style.display='none';
        document.getElementById("credit").style.display='block';
        for(i=0;i<document.Hrm_TransJoinForm.slipPrint.length;i++)
     	{
         document.Hrm_TransJoinForm.slipPrint[i].disabled=false;
     	}
    }   
}   

function show2()
{	
    if (document.Hrm_TransJoinForm.slipPrint[0].checked==true)
    {
    	
         document.getElementById("refund").style.display='none';
    }
    else
    {
    	
        document.getElementById("refund").style.display='block';
    }   
}   
function show_category()
{	
    if (document.Hrm_TransJoinForm.category_type[0].checked==true)
    {
    	 document.getElementById("imp_types").style.display='none';
    	 for(i=0;i<document.Hrm_TransJoinForm.slipPrint.length;i++)
      	{
          document.Hrm_TransJoinForm.slipPrint[i].disabled=false;
      	}
    	 
    }
    else
    {
    	document.getElementById("imp_types").style.display='block';
    	 document.getElementById("refund").style.display='none';
         for(i=0;i<document.Hrm_TransJoinForm.slipPrint.length;i++)
      	{
         document.Hrm_TransJoinForm.slipPrint[i].disabled=true;
      	}
    }  
}   


function getgpf(){
	xmlhttp=getxmlhttpObject();
	   var gpf_no=document.getElementById("txtGpfNo").value;
	   
	   if(gpf_no==""){
		 alert('Enter GPF No');  
	   }else{
	   
       url="../../../../../GPF_Subscription.view?command=get&gpf_no="+gpf_no;
       url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null); 
	   }
}


function checkgpf(){
xmlhttp=getxmlhttpObject();
var gpf_no=document.getElementById("txtGpfNo").value;
var checkflag1=true;
 if(gpf_no==""){
   alert('Enter GPF No'); 
 }else{

  var misstype="";
  var categorytype="";
  var flag=0;
      if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
      {
          flag=1;
          misstype='CR';
      }else if( document.Hrm_TransJoinForm.missing_type[1].checked==true)
      {
          flag=1;
          misstype='DB';
      }
      if(document.Hrm_TransJoinForm.category_type[0].checked==true)
      {
          flag=1;
          categorytype='Regular';
      }else if( document.Hrm_TransJoinForm.category_type[1].checked==true)
      {
          flag=1;
          categorytype=document.getElementById("imp_types").value;
      }
 
      if(flag!=0)
      {
          var relmonth=document.getElementById("rel_month").value;
          var relyear=document.getElementById("rel_year").value;
    
 url="../../../../../GPF_Excess_DB_CR?command=checkgpf&gpf_no="+gpf_no+"&misstype="+misstype+"&categorytype="+categorytype+"&relmonth="+relmonth+"&relyear="+relyear;
 url=url+"&sid="+Math.random();
 xmlhttp.open("GET",url,true);
 xmlhttp.onreadystatechange=function()
  {
      //requesthandle(req);
      if(xmlhttp.readyState==4)
      {
         
          if(xmlhttp.status==200)
          { 
             
              //getDeatils5();
          var    response=xmlhttp.responseXML.getElementsByTagName("response")[0];
          var    command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
           var   flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
          
           if(command=="checkgpf")
           {
              
               if(flag=='success')
               {
                
            	   document.getElementById("txtGpfNo").value="";
                   document.getElementById("emp_name").value="";
                  alert('This GPF NO Excess Credit/Debit is already submitted');
                 
                   
                   checkflag1=false;
               }
               else
                   {
                  
                   }
           }
          }
      }
  }
 xmlhttp.send(null);
      }
    
 }
 return checkflag1;
 }

function getxmlhttpObject()
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

function call1(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   

else if(command=="UnitName"){
	
	var unit_id=document.getElementById("unit_name").value;
    document.getElementById("Acc_unit_code").value=unit_id;
    //clear();
    clearrows();
 } 

}


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

            if(command=="get")
            {
            	if(flag=='success')
                {
                  
                    var empname=response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                    var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                    var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
                    var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
                    var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                    if(empname.length>0){
                    document.getElementById("emp_name").value=empname;
                  //  document.getElementById("txtDOB").value=dob;
                    document.getElementById("txtGpfNo").value=gpfno;
                  //  document.getElementById("designation").value=d_id;
                   // document.getElementById("txtEmpId").value=emp_id;
 
                    
                    }
                    else
                    {
                    	alert("Employee name not found for particular GPF NO");
                    }
                }
                else
                    {
                	alert('GPF No Does not Exist');
                	document.getElementById("txtGpfNo").value="";
                    }
            }
            if(command=="checkgpf")
            {            	
                if(flag=='success')
                {
                  
                   alert('This GPF NO Excess Credit/Debit is already submitted');
                   
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("emp_name").value="";
                    gpfcheckstatus=false;
                }
                else
                    {
                	gpfcheckstatus=true;
                	
                    }
            }
       }
    }
}


function nullcheck1()
{
	
	var flag=0;
//alert('jhh'+document.getElementById("txtGpfNo").value);
	if( document.getElementById("fin_year").value==0)
	{
		alert('Please Select Financial year');
		return false;
	}
	
	else if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
	{
		flag=1;
	}else if( document.Hrm_TransJoinForm.missing_type[1].checked==true)
	{
		flag=1;
	}
	else if(flag==0)
	{
		alert('Please Select Excess Credit/Debit');
		return false;
	}
	 if(document.getElementById("txtGpfNo").value=="")
	{
		alert('Please Enter GPF No');
		return false;
	}
	else if(document.getElementById("amount").value=="")
	{
		alert('Please Enter the Amount');
		return false;
	}
	else if( document.getElementById("jno").value=="")
	{
		alert('Please Enter the Joural No');
		return false;
	}
	
	
	return true;
}




function pick(i){
	  var emp_id=document.getElementById(i); 
	  var rcells=emp_id.cells;
    
	  var achead='';//rcells.item(1).firstChild.nodeValue;
      var ugpfno=rcells.item(1).firstChild.nodeValue;
      var splachead=rcells.item(2).firstChild.nodeValue;
      var uamount=rcells.item(3).firstChild.nodeValue;
      
      var uremark=rcells.item(3).lastChild.value;
      
      
      
      var rel_month_year=rcells.item(4).firstChild.nodeValue.split("/");
      relmonth=rel_month_year[0];
      relyear=rel_month_year[1]; 
      var intmonth=0,intyear=0;
      if(rcells.item(5).firstChild.nodeValue.length>0)
      {
      var int_month_year=rcells.item(5).firstChild.nodeValue.split("/");
      intmonth=int_month_year[0];
      intyear=int_month_year[1]; 
      }
      var usplachead=null;
  
      if(achead==""||achead==null )
      {
    	  achead="No";
      }
      if(ugpfno==""||ugpfno==null )
      {
    	  ugpfno="No";
      }
      if(splachead==""||splachead==null)
      {
    	  usplachead='No';
      }
      
      var missingtype="";
      if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
      {
     	 missingtype =document.Hrm_TransJoinForm.missing_type[0].value;
      }else if(document.Hrm_TransJoinForm.missing_type[1].checked==true)
      {
     	 missingtype =document.Hrm_TransJoinForm.missing_type[1].value;
      }
      
      
 	var gpfno=document.getElementById("txtGpfNo").value;
 	var amount=document.getElementById("amount").value;
      
     // alert("check");
       
     // alert("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Missing_DB_CR_Popup.jsp?command=update&ugpfno="+ugpfno+"&uachead="+achead+"&relmonth="+relmonth+"&relyear="+relyear+"&uamount="+uamount+"&uremark="+uremark+"&gpfno="+gpfno+"&amount="+amount+"&missingtype="+missingtype+"&splachead="+splachead+"&seqno="+i+"","mywi","status=1,height=500,width=500,resizable=YES, scrollbars=yes");     
      
      winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/GPF_Excess_DB_CR_Popup.jsp?command=update&ugpfno="+ugpfno+"&uachead="+achead+"&usplachead="+usplachead+"&relmonth="+relmonth+"&relyear="+relyear+"&uamount="+uamount+"&uremark="+uremark+"&gpfno="+gpfno+"&amount="+amount+"&missingtype="+missingtype+"&splachead="+splachead+"&seqno="+i+"&intmonth="+intmonth+"&intyear="+intyear+"","mywi","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	  winemp.moveTo(250,250);  
	  winemp.focus();     
	
}

function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                        //    alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //            alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //    alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}


function addtable1(headcode,gpfno,spl_ac_head,amount,remarks,rel_year,rel_month,intYear,intMonth,SLIP_PRINT_UNDER,INST_NO,TOT_INST)
{
	//alert("fin"+intYear);
    
	var seq;
	var my_num=Math.random();
	var my_num2=Math.random();
	seq=(my_num*my_num2);
 var tlist=document.getElementById("tlist");
  var tr=document.createElement("TR");
  tr.id=seq;
  
  var td=document.createElement("TD");
  var anc=document.createElement("A");
  var url="javascript:pick('"+seq+"')";
  anc.href=url;
  
  
	   var edit=document.createTextNode("Edit");
 	   anc.appendChild(edit);
     td.appendChild(anc);

  
  tr.appendChild(td);

  
 /* 
  var headcode="";
  var description="";
  var gpfno="";
  var employeename="";
  var amount="";
  remarks="";
  
  if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
  {
	  headcode= document.getElementById("ac_head_code").value; 
	  description=document.getElementById("descr").value;
  }  else
  {
	  gpfno=document.getElementById("other_gpf_no").value;
	  employeename=document.getElementById("other_name").value;
  }
  
  amount=document.getElementById("amount").value;
  remarks=document.getElementById("remarks").value;
  
 var rel_year=document.getElementById("rel_year").value;
 var rel_month=document.getElementById("rel_month").value;
  
 if(remarks==""||remarks==null)
 {
	 remarks="noRemark";
 }
 
/*
 
   var sino=document.createElement("TD");
  var si=document.createTextNode(seq);
  sino.appendChild(si);
  tr.appendChild(sino);
  */ 
  
//  var head=document.createElement("TD");
//  var headc=document.createTextNode(headcode);
//  head.appendChild(headc);
//  tr.appendChild(head); 
  
  
  var tdg=document.createElement("TD");
  var gpd=document.createTextNode(gpfno);
  tdg.appendChild(gpd);
  tr.appendChild(tdg); 
  
  var tdg=document.createElement("TD");
  var shead=document.createTextNode(spl_ac_head);
  tdg.appendChild(shead);
  tr.appendChild(tdg); 
  
  var amt=document.createElement("TD");
  var amou=document.createTextNode(amount);
  amt.appendChild(amou);
  var h7=document.createElement("TEXT");
  h7.type="hidden";
  h7.name="remarks";
  h7.value=remarks;
  amt.appendChild(h7);
  tr.appendChild(amt); 
  var val="";
  var rel=document.createElement("TD");
  var relm=document.createTextNode(rel_month+"/"+rel_year);
  rel.appendChild(relm);
  tr.appendChild(rel); 
   rel=document.createElement("TD");
  
   relm=document.createTextNode(intMonth+"/"+intYear+"/"+SLIP_PRINT_UNDER+"/"+INST_NO+"/"+TOT_INST);
   if(intYear>0)
	   rel.appendChild(relm);
	    else
	    {
	    	
	    	 relm=document.createTextNode("");
	    	rel.appendChild(relm);
	    }
  tr.appendChild(rel); 
    
 tlist.appendChild(tr);
// opener.document.getElementById("tlist").appendChild(tr);
   

 var fullamount=0;
 var currRow = document.getElementById("tlist").rows.length;
		
	for(i=0;i<currRow;i++){
	
	var other_amount=document.getElementById("tlist").rows[i].cells.item(3).firstChild.nodeValue;
 fullamount=parseInt(fullamount)+parseInt(other_amount);
	}
	//fullamount=parseInt(document.getElementById("amount").value)+parseInt(fullamount);
	//alert(fullamount);
	if(parseInt(fullamount)==parseInt(document.getElementById("amount").value))
	{
		document.getElementById("addall").disabled=false;
		document.getElementById("add1").disabled=true;
	}else if(parseInt(fullamount)>parseInt(document.getElementById("amount").value))
	{
		alert('Total Amount is less than Amount to be Adjusted');
	}
 
 
}

function updatetable1(headcode,gpfno,spl_ac_head,amount,remarks,rel_year,rel_month,seq,intYear,intMonth,SLIP_PRINT_UNDER,INST_NO,TOT_INST)
{
	//seq=document.getElementById("seqno").value;
	
	 var tlist=document.getElementById("tlist");
	 
	 var table =document.getElementById("tlist");
	 
	    var rowToDelete =document.getElementById( seq );
	    table.removeChild(rowToDelete);
	
	 
	 
	  var tr=document.createElement("TR");
    tr.id=seq;
    
    var td=document.createElement("TD");
    var anc=document.createElement("A");
    var url="javascript:pick('"+seq+"')";
    anc.href=url;
    
    
	   var edit=document.createTextNode("Edit");
   	   anc.appendChild(edit);
       td.appendChild(anc);
  
    
    tr.appendChild(td);

     
  /*  
    var headcode="";
    var description="";
    var gpfno="";
    var employeename="";
    var amount="";
    remarks="";
    
    if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
    {
  	  headcode= document.getElementById("ac_head_code").value; 
  	  description=document.getElementById("descr").value;
    }  else
    {
  	  gpfno=document.getElementById("other_gpf_no").value;
  	  employeename=document.getElementById("other_name").value;
    }
    
    amount=document.getElementById("amount").value;
    remarks=document.getElementById("remarks").value;
    
   var rel_year=document.getElementById("rel_year").value;
   var rel_month=document.getElementById("rel_month").value;
    
   if(remarks==""||remarks==null)
   {
  	 remarks="noRemark";
   }
   
  /*
   
     var sino=document.createElement("TD");
    var si=document.createTextNode(seq);
    sino.appendChild(si);
    tr.appendChild(sino);
    */ 
    
//    var head=document.createElement("TD");
//    var headc=document.createTextNode(headcode);
//    head.appendChild(headc);
//    tr.appendChild(head); 
    
    
    var tdg=document.createElement("TD");
    var gpd=document.createTextNode(gpfno);
    tdg.appendChild(gpd);
    tr.appendChild(tdg); 
    
    var tdg=document.createElement("TD");
    var shead=document.createTextNode(spl_ac_head);
    tdg.appendChild(shead);
    tr.appendChild(tdg); 
    
    var amt=document.createElement("TD");
    var amou=document.createTextNode(amount);
    amt.appendChild(amou);
    var h7=document.createElement("TEXT");
    h7.type="hidden";
    h7.name="remarks";
    h7.value=remarks;
    amt.appendChild(h7);
    tr.appendChild(amt); 
    
    var rel=document.createElement("TD");
    var relm=document.createTextNode(rel_month+"/"+rel_year);
    rel.appendChild(relm);
    tr.appendChild(rel); 
    rel=document.createElement("TD");
    
    relm=document.createTextNode(intMonth+"/"+intYear+"/"+SLIP_PRINT_UNDER+"/"+INST_NO+"/"+TOT_INST);
    if(intYear>0)
 	   rel.appendChild(relm);
 	    else
 	    {
 	    	
 	    	 relm=document.createTextNode("");
 	    	rel.appendChild(relm);
 	    }
   tr.appendChild(rel); 
    
    tlist.appendChild(tr);           	

    
    var fullamount=0;
    var currRow = document.getElementById("tlist").rows.length;
   		
   	for(i=0;i<currRow;i++){
   	
   	var other_amount=document.getElementById("tlist").rows[i].cells.item(3).firstChild.nodeValue;
    fullamount=parseInt(fullamount)+parseInt(other_amount);
   	}
   	//fullamount=parseInt(document.getElementById("amount").value)+parseInt(fullamount);
   //	alert(fullamount);
   	if(parseInt(fullamount)==parseInt(document.getElementById("amount").value))
   	{
   		document.getElementById("addall").disabled=false;
   		document.getElementById("add1").disabled=true;
   	}else if(parseInt(fullamount)>parseInt(document.getElementById("amount").value))
   	{
   		alert('Total Amount is less than Amount to be Adjusted');
   	}else{
   		document.getElementById("addall").disabled=true;
   		document.getElementById("add1").disabled=false;
   	}
    
    
    
}

function addvalues()
{
	
	var url="";
	var unit=document.getElementById("Acc_unit_code").value;
	var gpfno=document.getElementById("txtGpfNo").value;
	//alert(gpfno);
	var misstype="";
	var slipPrint="",Install_no="0",totalNOInstall="0";
	if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
	{
		misstype="CR";
	}else{
		misstype="DB";
		for(i=0;i<document.Hrm_TransJoinForm.slipPrint.length;i++)
		{
			if(document.Hrm_TransJoinForm.slipPrint [i].checked==true)
			{
				slipPrint=document.Hrm_TransJoinForm.slipPrint[i].value;
				if(i!=0)
				{
					Install_no=document.Hrm_TransJoinForm.Install_no.value;
					totalNOInstall=document.Hrm_TransJoinForm.totalNOInstall.value;
				}
			}
		}
	}
	
	var category_type="";
	if(document.Hrm_TransJoinForm.category_type[0].checked==true){
		category_type="Regular";
	}
	else if(document.Hrm_TransJoinForm.category_type[1].checked==true){
		category_type=document.getElementById("impound_type").value;
	}
	var amount=document.getElementById("amount").value;
	var jno=document.getElementById("jno").value;
	var date=document.getElementById("date").value;
	
	var remark=document.getElementById("remarks").value;
	
	//if(remark=="")
	//{
	//	remark="noRemark";
	//}
	 var relmonth=document.getElementById("rel_month").value;
	   var relyear=document.getElementById("rel_year").value;
	var int_year=0;
	var int_month=0;
	//alert("before try");
	try
	{
		if(parseInt(document.getElementById("int_year").value)>0)
		{
	 int_year=document.getElementById("int_year").value;
      int_month=document.getElementById("int_month").value;
      if(parseInt(int_year)<parseInt(relyear) && parseInt(int_year)>0)
      {
   	   alert("Interest calculate Month & year greater than Relating Month & year");
   	   return false;
      }
      else if(parseInt(int_year)==parseInt(relyear) && parseInt(int_month)<parseInt(relmonth) && parseInt(int_year)>0)
      {
   	   alert("Interest calculate Month & year greater than Relating Month & year");
   	   return false;
      }
		}
	}
	catch (e) {
		//alert("catch");	
	}
	//alert("after try---");
	url="../../../../../GPF_Excess_DB_CR?command=Add&unit="+unit+"&gpfno="+gpfno+"&misstype="+misstype+"&amount="+amount+"&jno="+jno+"&date="+date+"&remark="+remark;
	//alert(url);
	url=url+"&rel_month="+relmonth+"&rel_year="+relyear+"&category_type="+category_type+"&int_year="+int_year+"&int_month="+int_month;
	url=url+"&slipPrint="+slipPrint+"&Install_no="+Install_no+"&totalNOInstall="+totalNOInstall;

	url=url+"&sid="+Math.random();
	
	xmlhttp.open("GET",url,false);
 // xmlhttp.onreadystatechange=stateChanged;
	xmlhttp.send(null);
	
	
	
	
	
	var currRow = document.getElementById("tlist").rows.length;
	//alert(currRow);
	
	for(i=0;i<currRow;i++){
		//idle1();
	var achead='';//document.getElementById("tlist").rows[i].cells.item(1).firstChild.nodeValue;
	var other_gpfno=document.getElementById("tlist").rows[i].cells.item(1).firstChild.nodeValue;
	var spl_ac_head=document.getElementById("tlist").rows[i].cells.item(2).firstChild.nodeValue;
	if(achead==null||achead==""||spl_ac_head>1)
	{
		 achead=spl_ac_head;
	}
	var other_amount=document.getElementById("tlist").rows[i].cells.item(3).firstChild.nodeValue;
	var rel_month_year=document.getElementById("tlist").rows[i].cells.item(4).firstChild.nodeValue.split("/");
	rel_month=rel_month_year[0];
	rel_year=rel_month_year[1];
	var intmonth=0,intyear=0;
    if(document.getElementById("tlist").rows[i].cells.item(5).firstChild.nodeValue.length>0)
    {
    var int_month_year=document.getElementById("tlist").rows[i].cells.item(5).firstChild.nodeValue.split("/");
    intmonth=int_month_year[0];
    intyear=int_month_year[1]; 
    slipPrint=int_month_year[2];
    Install_no=int_month_year[3]; 
    totalNOInstall=int_month_year[4];
    }
	
	
	if(achead==""){
		achead=0;
	}
	if(other_gpfno==""){
		other_gpfno=0;
	}
	//alert(intmonth);
	 var missingtype="";
     if(document.Hrm_TransJoinForm.missing_type[0].checked==true)
     {
    	 missingtype ="DB";
     }else if(document.Hrm_TransJoinForm.missing_type[1].checked==true)
     {
    	 missingtype ="CR";
     }
	
	//alert("6567");
	
	 url="../../../../../GPF_Excess_DB_CR?command=Addtrn&achead="+achead+"&other_gpfno="+other_gpfno+"&other_amount="+other_amount+"&rel_month="+rel_month+"&rel_year="+rel_year+"&missingtype="+missingtype+"&spl_ac_head="+spl_ac_head+"&int_year="+intyear+"&int_month="+intmonth;
              
	 url=url+"&slipPrint="+slipPrint+"&Install_no="+Install_no+"&totalNOInstall="+totalNOInstall;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,false);
//xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null);  	
	
	}

	alert('Added Successfully');
	clearfull();
	
}

function idle1(){
	
}
function servicepopup1()
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
    // startwaiting(document.Hrm_TransJoinForm);  
    
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
   
    
    winemp.moveTo(250,250); 
    winemp.focus();
}

function doParentEmp(emp)
{
	
document.getElementById("txtGpfNo").value=emp;
//call('get','s');
getgpf();
}

function clearrows()
{
	document.getElementById("txtGpfNo").value="";
	document.getElementById("emp_name").value="";
	document.getElementById("amount").value="";
	document.getElementById("jno").value="";
	document.getElementById("date").value="";
	document.getElementById("remarks").value="";
	
	document.getElementById("amount").value="";
	 var tbody1 = document.getElementById("tlist");
	 for(t=tbody1.rows.length-1;t>=0;t--)
     {
        tbody1.deleteRow(0);
     }
	 document.getElementById("addall").disabled=true;
		document.getElementById("add1").disabled=false;
}



function clearfull()
{
	 document.Hrm_TransJoinForm.fin_year[0].selected='selected';
	// document.Hrm_TransJoinForm.unit_name[0].selected='selected';
	 
	document.getElementById("txtGpfNo").value="";
	document.getElementById("emp_name").value="";
	document.getElementById("amount").value="";
	document.getElementById("jno").value="";
	document.getElementById("date").value="";
	document.getElementById("remarks").value="";
	
	document.getElementById("amount").value="";
	 var tbody1 = document.getElementById("tlist");
	 for(t=tbody1.rows.length-1;t>=0;t--)
     {
        tbody1.deleteRow(0);
     }
	 document.getElementById("addall").disabled=true;
		document.getElementById("add1").disabled=false;
}


function selectMonth(slipyear)
{
	
var rel_year=document.getElementById("rel_year").value;	

var v=new Date();
var cur_mnth=v.getMonth();
var cur_year=v.getFullYear();
//alert(cur_mnth+cur_year);
var finyear=document.getElementById("fin_year");
var finyear_val=finyear.options[finyear.selectedIndex].value;
if(finyear_val!=0)
{
var finyear_arr=new Array(2);
finyear_val=finyear_val.split("-");
   
var date="31/03/"+finyear_val[1];
document.getElementById("date").value=date; 
var i;
for(i=document.Hrm_TransJoinForm.rel_month.options.length-1;i>=0;i--)
{

	document.Hrm_TransJoinForm.rel_month.remove(i);
}

var minorcmb = document.Hrm_TransJoinForm.rel_month;
	 var start_months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	    var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);

if(finyear_val[0]==cur_year)
{
	if(parseInt(rel_year)<cur_year)
	{
		for(var i=0; i<12; i++)
		{
			 var opt1 = document.createElement('option');
	         opt1.value = start_months_val[i];
	         opt1.innerHTML = start_months[i];
	         minorcmb.appendChild(opt1);
		}
	}
	else
	{
	for(var i=0; i<cur_mnth; i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}
	}
}
else if(finyear_val[1]==cur_year)
{
	if(parseInt(rel_year)<cur_year)
	{
		for(var i=0; i<12; i++)
		{
			 var opt1 = document.createElement('option');
	         opt1.value = start_months_val[i];
	         opt1.innerHTML = start_months[i];
	         minorcmb.appendChild(opt1);
		}
	}
	else
	{
	for(var i=0; i<3; i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}
	}
}
else{
	for(var i=0; i<12; i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}


}


}




}

function journaldate()
{
	var v=new Date();
	var cur_mnth=v.getMonth();
	var cur_year=v.getFullYear();
	//alert(cur_mnth+cur_year);
	var finyear=document.getElementById("fin_year");
    var finyear_val=finyear.options[finyear.selectedIndex].value;
    if(finyear_val!=0)
    {
    var finyear_arr=new Array(2);
    finyear_val=finyear_val.split("-");
       
    var date="31/03/"+finyear_val[1];
    document.getElementById("date").value=date; 
    var i;
    for(i=document.Hrm_TransJoinForm.rel_month.options.length-1;i>=0;i--)
    {

    	document.Hrm_TransJoinForm.rel_month.remove(i);
    }

    var minorcmb = document.Hrm_TransJoinForm.rel_month;
    	 var start_months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    	    var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
    
    if(finyear_val[0]==cur_year)
    {
    	for(var i=0; i<cur_mnth; i++)
    	{
    		 var opt1 = document.createElement('option');
             opt1.value = start_months_val[i];
             opt1.innerHTML = start_months[i];
             minorcmb.appendChild(opt1);
    	}
    }
    else if(finyear_val[1]==cur_year)
    {
    	for(var i=0; i<3; i++)
    	{
    		 var opt1 = document.createElement('option');
             opt1.value = start_months_val[i];
             opt1.innerHTML = start_months[i];
             minorcmb.appendChild(opt1);
    	}
    }
    else{
    	for(var i=0; i<12; i++)
    	{
    		 var opt1 = document.createElement('option');
             opt1.value = start_months_val[i];
             opt1.innerHTML = start_months[i];
             minorcmb.appendChild(opt1);
    	}
  
    	   
   
    }
    
    
    }
}
function checkAfterdelete()
{
	
	  var fullamount=0;
	    var currRow = document.getElementById("tlist").rows.length;
	   		
	   	for(i=0;i<currRow;i++){
	   	
	   	var other_amount=document.getElementById("tlist").rows[i].cells.item(3).firstChild.nodeValue;
	    fullamount=parseInt(fullamount)+parseInt(other_amount);
	   	}
	   	//fullamount=parseInt(document.getElementById("amount").value)+parseInt(fullamount);
	   //	alert(fullamount);
	   	if(parseInt(fullamount)==parseInt(document.getElementById("amount").value))
	   	{
	   		document.getElementById("addall").disabled=false;
	   		document.getElementById("add1").disabled=true;
	   	}else if(parseInt(fullamount)>parseInt(document.getElementById("amount").value))
	   	{
	   		alert('Total Amount is less than Amount to be Adjusted');
	   	}else{
	   		document.getElementById("addall").disabled=true;
	   		document.getElementById("add1").disabled=false;
	   	}

}

