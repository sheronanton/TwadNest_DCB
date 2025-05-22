var winemp;
function servicepopup1()
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
	        
	    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/Gpf_notopted_EmpServicePopup.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
	    winemp.moveTo(250,250);  
	    winemp.focus();
  
}

function getgpf(){
	xmlhttp=getTransport();
	   var gpf_no=document.getElementById("gpfname").value;
	 
	   if(gpf_no==""){
		 alert('Enter GPF No');  
	   }
	   else{
		  
		   checkforValidGpf(gpf_no);
		   
		 
		  
	   }
}
function checkforValidGpf(gpf_no)
{
	
	
	url="../../../../../HRM_GPF_Interest_not_opted?command=checkvalidGPF&gpfno="+gpf_no;

    var req=getTransport();
    req.open("GET",url,true);  
   
    req.onreadystatechange=function()
    {
    	
    	processResponse(req);
    	
    }   
    req.send(null);	
    
    url="../../../../../GPF_Subscription.view?command=get&gpf_no="+gpf_no;
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
   xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.send(null); 
    
}


function stateChanged()
{
    var flag,command,response;
   
  
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
                     // var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                      // var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
                     //   var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
                      //  var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                    document.getElementById("emp_name").value=empname;
                  //  document.getElementById("txtDOB").value=dob;
                    //document.getElementById("txtGpfNo").value=gpfno;
                  //  document.getElementById("designation").value=d_id;
                   // document.getElementById("txtEmpId").value=emp_id;
 
                     
                }
                else
                    {
                	alert('GPF No Does not Exist.');
                	
                	document.getElementById("gpfname").value="";
                	document.getElementById("emp_name").value="";
                	document.getElementById("designame").value="";
                	document.getElementById("off_name").value="";
                	
                    }
            } if(command=="checkgpf")
            {
            	
                if(flag=='success')
                {
                  
                   alert('This GPF NO Missing Credit/Debit is already submitted');
                   
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("emp_name").value="";
                    
                }
                
            }
       }
    }
}
function doParentEmp(emp)
{
	
document.getElementById("gpfname").value=emp;
closeWin();
//call('get','s');
getgpf();
}
function closeWin()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
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
 
 function clearAll()
 {
   document.CadreForm.gpfname.value="";
   document.CadreForm.fin_year.value=0;
   document.CadreForm.dateofreq.value="";
   document.CadreForm.emp_name.value="";
   document.CadreForm.fin_year.disabled=false;
   document.CadreForm.gpfname.disabled=false;
   document.getElementById("designame").value="";
	document.getElementById("off_name").value="";
   document.CadreForm.CmdAdd.disabled=false;
   document.CadreForm.CmdUpdate.disabled=true;
   document.CadreForm.CmdDelete.disabled=true;
   
  
 }
 
 function Exit()
 {
    self.close();
 }
 function nullCheck()
 {
	
           if((document.CadreForm.gpfname.value=="") || (document.CadreForm.gpfname.value<=0))
           { 
                alert("Please Enter the GPF Number");
                document.CadreForm.gpfname.focus();
                return false;
           }
          else if(document.CadreForm.fin_year.value==0 )
           { 
                alert("Please select the Financial Year");
                document.CadreForm.fin_year.focus();
                return false;
           }
           else if(document.CadreForm.dateofreq.value=="" )
          { 
               alert("Please enter Date of request");
               document.CadreForm.dateofreq.focus();
               return false;
          }
          
          else if(document.CadreForm.dateofreq.value.length!=10 )
          { 
               alert("Please enter correct Date");
               document.CadreForm.dateofreq.focus();
               return false;
          }
           var input=document.CadreForm.dateofreq.value;
          
           var monthfield=input.split("/")[1];
         
            var dayfield=input.split("/")[0];
           var yearfield=input.split("/")[2];
           
           var myDate =new Date(monthfield+"/"+dayfield+"/"+yearfield);
          var today = new Date();
        
          if (myDate>today)
        	     {
        	     alert('You cannot enter a date in the future!');
        	     document.CadreForm.dateofreq.focus();
        	    return false;
        	     }
                     
           return true;
     } 
 function callServer(command,param)
 {
	 
       var gpfno=document.CadreForm.gpfname.value;
        var finyear=document.CadreForm.fin_year.value;
       var dateofreq=document.CadreForm.dateofreq.value;
      
      // var strRemarks=document.CadreForm.txtRemarks.value;
        var url="";
        
       
       if(command=="Add")
    	          {
    	   
    	  
    	   var flag=nullCheck();
                    if(flag==true)
                    {
                    	
                    url="../../../../../HRM_GPF_Interest_not_opted?command=Add&gpfno=" + gpfno+"&finyear=" + finyear+"&dateofreq="+dateofreq;
                    var req=getTransport();
                    req.open("GET",url,true);   
                    
                    req.onreadystatechange=function()
                    {
                    	
                       processResponse(req);
                    }   
                    req.send(null);
                    }
                   
                    
        }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    	 url="../../../../../HRM_GPF_Interest_not_opted?command=update&gpfno=" + gpfno+"&finyear=" + finyear+"&dateofreq="+dateofreq;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }

        }
        
        else if(command=="Delete")
        {  
        	 url="../../../../../HRM_GPF_Interest_not_opted?command=delete&gpfno=" + gpfno+"&finyear=" + finyear+"&dateofreq="+dateofreq;
                   var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
        }
        else if(command=="Get")
        {            
        	
                          url="../../../../../HRM_GPF_Interest_not_opted?command=Get";
                         
                         var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               viewResponse(req);
            }   
                    req.send(null);
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
 function updateResponse(response)
 {
	//alert("response"+response); 
 	var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
 	if(res=="success")
 	{
 		if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="added")
 		{
 			
 			alert("Records Added Successfully");
 			
 			clearAll();
 			callServer('Get','null');
 		}
 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="databaseError")
 		{
 			
 			alert("Database service Not available");
 			
 			clearAll();
 			
 		}
 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="Notadded")
 		{
 			
 			alert("Data already added for the particular GPF NO & Finacial year");
 			
 			clearAll();
 			
 		}
 		
 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="update")
 		{
 			alert("Records Updated Successfully");
 			clearAll();
 			callServer('Get','null');
 		}
 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="checkvalidGPF")
 		{
 			
 			//alert("adfdsafdas");
 			document.CadreForm.designame.value=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
 			document.CadreForm.off_name.value=response.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
 			document.CadreForm.emp_name.value=response.getElementsByTagName("employee_name")[0].firstChild.nodeValue;
 			checkflag=true;
 		}
 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="checkvalidGPFFailure")
 		{
 			
 			alert("Not a Valid User");
 			document.CadreForm.gpfname.value="";
 			document.CadreForm.emp_name.value="";
 			document.CadreForm.designame.value="";
 			document.CadreForm.off_name.value="";
 			checkflag=false;
 		}
 		else{
 		
 		alert("Records Deleted Successfully");
 		clearAll();
 		callServer('Get','null');
 		}
 	}
 	else
 	{
 		
 		alert("Process failure");
 	}
 		
 }
 function viewResponse(req)
 {
	 if(req.readyState==4)
     { 
     	
         if(req.status==200)
         {  
                          
             var iframe=document.getElementById("tblList");
             iframe.focus();
             // alert(navigator.appName);
             // alert(navigator.appName.indexOf('Microsoft'));
             
             
             if(navigator.appName.indexOf('Microsoft')!=-1)
                 iframe.innerHTML=req.responseText;
             else
                 iframe.innerText=req.responseText;
             iframe.innerHTML=req.responseText;
             
             statusflag=true;
           
         }
     }
 }
 function viewDetails(id)
 {
	
	 var jid=document.getElementById("id"+id).value;
	
	 url="../../../../../HRM_GPF_Interest_not_opted?command=edit&"+jid;
     var req=getTransport();
      req.open("GET",url,true);        
      req.onreadystatechange=function()
      {
         editview(req);
      }   
      req.send(null);
 }
 function editview(req)
 {
	
	 if(req.readyState==4)
     { 
     	
         if(req.status==200)
         {  
        	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      	   editResponse(baseResponse);
         }
        
     } 
 }
 function editResponse(response)
 {
	 var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 	if(res=="success")
	 	{
	 		if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="databaseError")
	 		{
	 			
	 			alert("Database service Not available");
	 			
	 			clearAll();
	 			
	 		}
	 		else
	 		{
	 		var gpf=response.getElementsByTagName("gpfno")[0].firstChild.nodeValue;
	 		var FIN_YEAR=response.getElementsByTagName("FIN_YEAR")[0].firstChild.nodeValue;
	 		var DATE_OF_REQUEST=response.getElementsByTagName("DATE_OF_REQUEST")[0].firstChild.nodeValue;
	 		document.CadreForm.gpfname.value=gpf;
	 		document.CadreForm.fin_year.value=FIN_YEAR;
	 		document.CadreForm.dateofreq.value=DATE_OF_REQUEST;
	 		getgpf();
	 		document.CadreForm.gpfname.disabled=true;
	 		document.CadreForm.fin_year.disabled=true;
	 		document.CadreForm.CmdAdd.disabled=true;
	 	   document.CadreForm.CmdUpdate.disabled=false;
	 	   document.CadreForm.CmdDelete.disabled=false;
	 	}
	 	}
	 	else
	 	{
	 		alert("Process Failure");
	 	}
	 		
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
              if(err!=0)
                 {
                     t.value="";
                     return false;
                 }
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
         else
         {
                 if(err!=0)
                 {
                     t.value="";
                     return false;
                 }
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
	  
	  
	  