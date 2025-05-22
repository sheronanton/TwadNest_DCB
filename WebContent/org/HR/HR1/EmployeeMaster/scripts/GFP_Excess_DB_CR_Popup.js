var seq=0;
window.onunload=function()
{
if (winemp1 && winemp1.open && !winemp1.closed)
	winemp1.close();
}

function show()
{
  //  if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
   // {
   // 	document.getElementById("AC_Head").style.display='block';
   //     document.getElementById("Other_gpfno").style.display='none';
   //     document.getElementById("SPL_AC_Head").style.display='none';                                                   
	//    document.getElementById("ac_head_code").value="";  
	// 	document.getElementById("descr").value="";
 //	}
      if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
    {
    	
			document.getElementById("Other_gpfno").style.display='block';
			//document.getElementById("AC_Head").style.display='none';
			document.getElementById("SPL_AC_Head").style.display='none'; 
			document.getElementById("other_gpf_no").value="";
			document.getElementById("other_name").value="";     

    }
    else 
    	
    {
    	document.getElementById("SPL_AC_Head").style.display="block";     
    	document.getElementById("Other_gpfno").style.display='none';
		//document.getElementById("AC_Head").style.display='none';	
		document.getElementById("other_gpf_no").value="";
		document.getElementById("other_name").value="";
		document.getElementById("spl_ac_head_code").value="";
    }
    
}


function add1(){
	
	xmlhttp=getxmlhttpObject();
	//alert("start");
	var gpf_no="";
	 if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
     {
		 gpf_no=document.getElementById("other_gpf_no").value;
   	  employeename=document.getElementById("other_name").value;
   
	
	 if(gpf_no==""){
	   alert('Enter GPF No'); 
	 }else{
		
	  var misstype="";
	  var categorytype="";
	  var flag=0;
	      if(window.opener.document.Hrm_TransJoinForm.missing_type[0].checked==true)
	      {
	          flag=1;
	          misstype='DB';
	      }else if( window.opener.document.Hrm_TransJoinForm.missing_type[1].checked==true)
	      {
	          flag=1;
	          misstype='CR';
	      }
	     // alert("2");
	      if(window.opener.document.Hrm_TransJoinForm.category_type[0].checked==true)
	      {
	          flag=1;
	          categorytype='Regular';
	      }else if( window.opener.document.Hrm_TransJoinForm.category_type[1].checked==true)
	      {
	          flag=1;
	          categorytype=document.getElementById("imp_types").value;
	      }
	 
	      if(flag!=0)
	      {
	          var relmonth=document.getElementById("rel_month").value;
	          var relyear=document.getElementById("rel_year").value;
	        //  alert("2");
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
	               else if(flag=='retried')
	               {
	            	   alert("Employee is already retired");
	            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
	            	   if(data=='Failentry')
	            	   {
	            		   alert("Relative month & year should be less than Retired date");
	            		  
	            	   }
	            	   document.getElementById("intrestDiv").style.display='block';
	            	   document.getElementById("intrestDiv1").style.display='block';
	            	   
	               }
	               else
	                   {
	            	   var   int_year=0, int_month=0;
	            	
	            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
	            	  
		     	      var relmonth=document.getElementById("rel_month").value;
		   	          var relyear=document.getElementById("rel_year").value;
		   	       //   alert(relyear);
	            	   if(data=='entry')
	            	   {
	            		   int_year=document.getElementById("int_year").value;
	            		   document.getElementById("intrestDiv").style.display='block';
		            	   document.getElementById("intrestDiv1").style.display='block';
	            		if(int_year==0) {  
	            			alert("Employee is already retired");
	            			alert("select Interest calculated month and year");
	            		   return false;
	            		}
	            		else
	            		{
	            			//alert(int_year+relyear);
	            			  
			     	           int_month=document.getElementById("int_month").value;
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
	            	   else
	            	   {
	            		   document.getElementById("intrestDiv").style.display='none';
		            	   document.getElementById("intrestDiv1").style.display='none';
		            	   document.getElementById("int_year").value=0;
	            	   }
	            	  // alert("before null check");
	if(nullcheck())
	{ 
	
		var headcode='';
		var description="";
		var gpfno='';
		var employeename="";
		var amount=0;
		var	remarks="";
        var spl_ac_head='';
//      if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
//      {
//    	  
//    	  headcode= document.getElementById("ac_head_code").value; 
//    	  description=document.getElementById("descr").value;
//      }  
//      else
    	  if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
      {
    	  gpfno=document.getElementById("other_gpf_no").value;
    	  employeename=document.getElementById("other_name").value;
    	//  checkgpf();
      }
      else if (document.Hrm_TransJoinForm.typecredit[1].checked==true)
      {
    	  spl_ac_head=document.getElementById("spl_ac_head_code").value;
    	 
      }
      amount=document.getElementById("amount").value;
      remarks=document.getElementById("remarks").value;
    
     var rel_year=document.getElementById("rel_year").value;
     var rel_month=document.getElementById("rel_month").value;
 // alert(rel_month);
    // opener.document.getElementById("fullamount").value=parseInt(opener.document.getElementById("fullamount").value)+parseInt(amount);
     opener.addtable1(headcode,gpfno,spl_ac_head,amount,remarks,rel_year,rel_month,int_year,int_month);
   
  // alert(opener.document.getElementById("fullamount").value);
     
    
     alert("Added Succesfully");
     window.close();
   	
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
	 else
	 {
		// alert("");
		 document.getElementById("intrestDiv").style.display='none';
  	   document.getElementById("intrestDiv1").style.display='none';
  	   document.getElementById("int_year").value=0;
		 if(nullcheck())
			{ 
			var headcode='';
			var description="";
			var gpfno='';
			var employeename="";
			var amount=0;
			var	remarks="";
	        var spl_ac_head='';
//	      if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
//	      {
//	    	  
//	    	  headcode= document.getElementById("ac_head_code").value; 
//	    	  description=document.getElementById("descr").value;
//	      }  
//	      else
	    	  if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
	      {
	    	  gpfno=document.getElementById("other_gpf_no").value;
	    	  employeename=document.getElementById("other_name").value;
	    	//  checkgpf();
	      }
	      else if (document.Hrm_TransJoinForm.typecredit[1].checked==true)
	      {
	    	  spl_ac_head=document.getElementById("spl_ac_head_code").value;
	    	 
	      }
	      amount=document.getElementById("amount").value;
	      remarks=document.getElementById("remarks").value;
	    
	     var rel_year=document.getElementById("rel_year").value;
	     var rel_month=document.getElementById("rel_month").value;
	  
	    // opener.document.getElementById("fullamount").value=parseInt(opener.document.getElementById("fullamount").value)+parseInt(amount);
	     opener.addtable1(headcode,gpfno,spl_ac_head,amount,remarks,rel_year,rel_month,0,0);
	   
	  // alert(opener.document.getElementById("fullamount").value);
	     
	    
	     alert("Added Succesfully");
	     window.close();
	   	
	 }
	 }
}

function update1()
{
	
	xmlhttp=getxmlhttpObject();
	var gpf_no="";
	 if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
     {
		 gpf_no=document.getElementById("other_gpf_no").value;
   	  employeename=document.getElementById("other_name").value;
    
	
	 if(gpf_no==""){
	   alert('Enter GPF No'); 
	 }else{

	  var misstype="";
	  var categorytype="";
	  var flag=0;
	  if(window.opener.document.Hrm_TransJoinForm.missing_type[0].checked==true)
      {
          flag=1;
          misstype='DB';
      }else if( window.opener.document.Hrm_TransJoinForm.missing_type[1].checked==true)
      {
          flag=1;
          misstype='CR';
      }
     // alert("2");
      if(window.opener.document.Hrm_TransJoinForm.category_type[0].checked==true)
      {
          flag=1;
          categorytype='Regular';
      }else if( window.opener.document.Hrm_TransJoinForm.category_type[1].checked==true)
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
	               else if(flag=='retried')
	               {
	            	   alert("Employee is already retired");
	            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
	            	   if(data=='Failentry')
	            	   {
	            		   alert("Relative month & year should be less than Retired date");
	            		  
	            	   }
	            	   document.getElementById("intrestDiv").style.display='block';
	            	   document.getElementById("intrestDiv1").style.display='block';
	            	   
	               }
	               else
	                   {
	            	   var   int_year=0, int_month=0;
	            	   
	            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
	            	  
		     	         var relmonth=document.getElementById("rel_month").value;
		   	          var relyear=document.getElementById("rel_year").value;
	            	   if(data=='entry')
	            	   {
	            		   int_year=document.getElementById("int_year").value;
	            		   document.getElementById("intrestDiv").style.display='block';
		            	   document.getElementById("intrestDiv1").style.display='block';
	            		if(int_year==0) {  
	            			alert("Employee is already retired");
	            			alert("select Interest calculated month and year");
	            		   return false;
	            		}
	            		else
	            		{
	            			//alert(int_year+relyear);
	            			  int_year=document.getElementById("int_year").value;
			     	           int_month=document.getElementById("int_month").value;
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
	            	   else
	            	   {
	            		   document.getElementById("intrestDiv").style.display='none';
		            	   document.getElementById("intrestDiv1").style.display='none';
		            	   document.getElementById("int_year").value=0;
	            	   }
	            	   
	
			seq=document.getElementById("seqno").value;
			var emp_id=opener.document.getElementById(seq); 
			var rcells=emp_id.cells;
			var uamount=rcells.item(3).firstChild.nodeValue; 
			var fullamount=0;
			var currRow = opener.document.getElementById("tlist").rows.length;
		 		
		 	for(i=0;i<currRow;i++){
		 	
		 	var other_amount=opener.document.getElementById("tlist").rows[i].cells.item(4).firstChild.nodeValue;
		 	fullamount=parseInt(fullamount)+parseInt(other_amount);
		 	}
		 	fullamount=parseInt(document.getElementById("amount").value)+parseInt(fullamount);
		 	fullamount=parseInt(fullamount)-parseInt(uamount);
		 	if(parseInt(fullamount)>parseInt(opener.document.getElementById("amount").value))
		 	{
		 		
		 		alert('Amount should not greater than Excess amount');
		 		
		 		return false;
		 	}
		  
     var headcode="";
     var description="";
     var gpfno="";
     var employeename="";
     var amount="";
     var spl_ac_head='';
     remarks="";
     
//     if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
//     {
//   	  headcode= document.getElementById("ac_head_code").value; 
//   	  description=document.getElementById("descr").value;
//     } 
//     else 
    	 if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
     {
   	  gpfno=document.getElementById("other_gpf_no").value;
   	  employeename=document.getElementById("other_name").value;
   	checkgpf();
     }
     else
    	 spl_ac_head=document.getElementById("spl_ac_head_code").value;
     
     amount=document.getElementById("amount").value;
     remarks=document.getElementById("remarks").value;
     
    var rel_year=document.getElementById("rel_year").value;
    var rel_month=document.getElementById("rel_month").value;
     
    if(remarks==""||remarks==null)
    {
   	 remarks="noRemark";
    }
    
    
    opener.updatetable1(headcode,gpfno,spl_ac_head,amount,remarks,rel_year,rel_month,seq,int_year,int_month); 
   
    
	alert("Updated successfully");
	 window.close();
	                   
	                   }
               }
       }
      }
  }

xmlhttp.send(null);
  }


}
     }
	 else
	 {
		 
		 document.getElementById("intrestDiv").style.display='none';
  	   document.getElementById("intrestDiv1").style.display='none';
  	   document.getElementById("int_year").value=0;
	
			seq=document.getElementById("seqno").value;
			var emp_id=opener.document.getElementById(seq); 
			var rcells=emp_id.cells;
			var uamount=rcells.item(3).firstChild.nodeValue; 
			var fullamount=0;
			var currRow = opener.document.getElementById("tlist").rows.length;
		 		
		 	for(i=0;i<currRow;i++){
		 	
		 	var other_amount=opener.document.getElementById("tlist").rows[i].cells.item(4).firstChild.nodeValue;
		 	fullamount=parseInt(fullamount)+parseInt(other_amount);
		 	}
		 	fullamount=parseInt(document.getElementById("amount").value)+parseInt(fullamount);
		 	fullamount=parseInt(fullamount)-parseInt(uamount);
		 	if(parseInt(fullamount)>parseInt(opener.document.getElementById("amount").value))
		 	{
		 		
		 		alert('Amount should not greater than Excess amount');
		 		
		 		return false;
		 	}
		  
     var headcode="";
     var description="";
     var gpfno="";
     var employeename="";
     var amount="";
     var spl_ac_head='';
     remarks="";
    
//     if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
//     {
//   	  headcode= document.getElementById("ac_head_code").value; 
//   	  description=document.getElementById("descr").value;
//     } 
//     else 
    	 if (document.Hrm_TransJoinForm.typecredit[0].checked==true)
     {
   	  gpfno=document.getElementById("other_gpf_no").value;
   	  employeename=document.getElementById("other_name").value;
   	checkgpf();
     }
     else
    	 spl_ac_head=document.getElementById("spl_ac_head_code").value;
     
     amount=document.getElementById("amount").value;
     remarks=document.getElementById("remarks").value;
     
    var rel_year=document.getElementById("rel_year").value;
    var rel_month=document.getElementById("rel_month").value;
     
    if(remarks==""||remarks==null)
    {
   	 remarks="noRemark";
    }
    
    
    opener.updatetable1(headcode,gpfno,spl_ac_head,amount,remarks,rel_year,rel_month,seq,0,0); 
   
    
	alert("Updated successfully");
	 window.close();
	                   
	 }
}


function delete111()
{
	//alert("enter delete");
	seq=document.getElementById("seqno").value;
	  
	
	 var tlist=opener.document.getElementById("tlist");
	 
	 var table =opener.document.getElementById("tlist");
	 
	    var rowToDelete =opener.document.getElementById( seq );
	    table.removeChild(rowToDelete);
	  
	    window.opener.checkAfterdelete();
	 
	    alert("Deleted successfully");
	    window.close();
}



function getgpf1(){
	xmlhttp=getxmlhttpObject();
	   var gpf_no=document.getElementById("other_gpf_no").value;
	   if(gpf_no!=""){
       url="../../../../../GPF_Subscription.view?command=get&gpf_no="+gpf_no;
       url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null); 
	   }else{
		   alert('Enter Other GPF No');
	   }
}

function acheadcode(){
	xmlhttp=getxmlhttpObject();
	   var acheadcode=document.getElementById("ac_head_code").value;
	   if(acheadcode!=""){
       url="../../../../../GPF_Excess_DB_CR?command=achead&acheadcode="+acheadcode;
       url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null); 
	   }else{
		   alert('Enter Account Head Code');
	   }
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
function checkgpf(){
	
	xmlhttp=getxmlhttpObject();
	   var gpf_no=document.getElementById("other_gpf_no").value;
	  
	   if(gpf_no==""){
		 alert('Enter GPF No');  
	   }else{
	   
		var misstype=document.getElementById("missingType").value; 
		if(misstype="Debit")
		{
			misstype="DB";
		}
		else
		{
			misstype="CR";
		}
		var categorytype="";
		  
		    	var relmonth=document.getElementById("rel_month").value;
				var relyear=document.getElementById("rel_year").value;
		   
       url="../../../../../GPF_Excess_DB_CR?command=checkgpf&gpf_no="+gpf_no+"&misstype="+misstype+"&categorytype="+categorytype+"&relmonth="+relmonth+"&relyear="+relyear;
       //alert("check"+url);
       //  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null); 
		  
		   
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
                    document.getElementById("other_name").value=empname;
                  //  document.getElementById("txtDOB").value=dob;
                    document.getElementById("other_gpf_no").value=gpfno;
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
                	document.getElementById("other_gpf_no").value="";
                    }
                
                              
                
            }
            if(command=="checkgpf")
            {            	
                if(flag=='success')
                {
                  
                   alert('This GPF NO Excess Credit/Debit is already submitted');
                   
                    document.getElementById("other_gpf_no").value="";
                    document.getElementById("other_name").value="";
                    return false;
                }
                else
                    {
                	return true;
                    }
            }
            if(command=="achead")
            {
            	
                if(flag=='success')
                {
                  
                    var hcode=response.getElementsByTagName("hcocde")[0].firstChild.nodeValue;
                     
                    document.getElementById("descr").value=hcode;
                 
                    
                }
                else
                    {
                	alert('Enter Correct Head Code');
                	document.getElementById("ac_head_code").value="";
                    }
            }
            if(command=="achead")
            {
            	
                if(flag=='success')
                {
                  
                    var hcode=response.getElementsByTagName("hcocde")[0].firstChild.nodeValue;
                     
                    document.getElementById("descr").value=hcode;
                 
                    
                }
                else
                    {
                	alert('Enter Correct Head Code');
                	document.getElementById("ac_head_code").value="";
                    }
            }
            
            
       }
    }
}

function nullcheck()
{
	
	var flag=0;
//if(document.Hrm_TransJoinForm.typecredit[0].checked==true)	
//{
//
//	if( document.getElementById("ac_head_code").value==""){
//		alert('Please Enter Account Head Code');
//		return false;
//	}
//	flag=1;
//	
//	
//}else if(document.Hrm_TransJoinForm.typecredit[1].checked==true)
//{
//	if( document.getElementById("other_gpf_no").value==""){
//	alert('Please Enter GPF No');
//	return false;
//}
//flag=1;
//	
//}
if(document.Hrm_TransJoinForm.typecredit[0].checked==true)
{
	if( document.getElementById("other_gpf_no").value==""){
	alert('Please Enter GPF No');
	return false;
}
flag=1;
	
}
else if (document.Hrm_TransJoinForm.typecredit[1].checked==true)
{
	if( document.getElementById("spl_ac_head_code").value==""){
	alert('Please Spl acc head');
	return false;
}
flag=1;
	
}
else if(flag==0)
{
	alert('Please Select Either GPF No or Spl Acc head');
	return false;
	
	}


if(document.getElementById("other_gpf_no").value==opener.document.getElementById("txtGpfNo").value)
{
	alert('Other GPF No must be differ from GPF No ');
	return false;	
}

 if(document.getElementById("amount").value=="")
{
	alert('Please Enter Amount');
	return false;
	}
else if(parseInt(document.getElementById("amount").value)>parseInt(document.getElementById("missamount").value))
{
	alert('Amount should not greater than Excess amount');
	return false;
	}

var fullamount=0;
 var currRow = opener.document.getElementById("tlist").rows.length;
	
	for(i=0;i<currRow;i++){
	
	var other_amount=opener.document.getElementById("tlist").rows[i].cells.item(4).firstChild.nodeValue;
	
 fullamount=parseInt(fullamount)+parseInt(other_amount);
	}
	fullamount=parseInt(document.getElementById("amount").value)+parseInt(fullamount);
	
	if(parseInt(fullamount)>parseInt(opener.document.getElementById("amount").value))
	{
		
		alert('Amount should not greater than Excess amount');
		
		return false;
	}
 
return true;


}
function selectMonth1(slipyear)
{
	
var rel_year=document.getElementById("int_year").value;	
//alert(rel_year);
var v=new Date();
var cur_mnth=v.getMonth();
var cur_year=v.getFullYear();
//alert(cur_mnth+cur_year);
var finyear=window.opener.document.getElementById("fin_year");

var finyear_val=finyear.options[finyear.selectedIndex].value;
if(finyear_val!=0)
{
var finyear_arr=new Array(2);
finyear_val=finyear_val.split("-");
//alert(finyear_val);
//var date="31/03/"+finyear_val[1];
//document.getElementById("date").value=date; 
var i;
for(i=document.Hrm_TransJoinForm.int_month.options.length-1;i>=0;i--)
{

	document.Hrm_TransJoinForm.int_month.remove(i);
}

var minorcmb = document.Hrm_TransJoinForm.int_month;
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


function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode;
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
var winemp1;
function servicepopup11()
{
	
	   if (winemp1 && winemp1.open && !winemp1.closed) 
	    {
	       winemp1.resizeTo(500,500);
	       winemp1.moveTo(250,250); 
	       winemp1.focus();
	    }
	    else
	    {
	        winemp1=null;
	    }
	    // startwaiting(document.Hrm_TransJoinForm);  
	    
	    winemp1= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpJoiningPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	   
	    
	    winemp1.moveTo(250,250); 
	    winemp1.focus();
}

function doParentEmp(emp)
{
	
document.getElementById("other_gpf_no").value=emp;
//call('get','s');
getgpf1();
}

var winAccHeadCode;
function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed)
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250);
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null;
    }
       
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    winAccHeadCode.moveTo(250,250); 
    winAccHeadCode.focus();
   
}
function doParentAccHead(code)
{
   document.getElementById("ac_head_code").value=code;
  // doFunction('checkCode',true);
   acheadcode();
   return true;
}




function selectMonth(slipyear)
{
	
	 v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();

var rel_year=document.getElementById("rel_year").value;	
var i;
for(i=document.Hrm_TransJoinForm.rel_month.options.length-1;i>=0;i--)
{

	document.Hrm_TransJoinForm.rel_month.remove(i);
}

var minorcmb = document.Hrm_TransJoinForm.rel_month;
	 var start_months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	    var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	   
if(parseInt(rel_year)==parseInt(slipyear))
{
	for(var i=0; i<3; i++)
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

