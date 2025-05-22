
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

function loadMinor(command)
{
	
	var major_value=document.getElementById("cmbMajor").value;
	 var url="../../../ViewAllResponseServlet.con?command=major&major_value="+major_value;
	// alert(url);
	 var req=getTransport();
	    req.open("Get",url,true);
	    req.onreadystatechange=function()
	    {
	        processresponse(req);
	    };
	    req.send(null);
}

function callServer() 
{
   
   txtfromdate= document.frmIssue.txtfromdate.value;
   txttodate=document.frmIssue.txttodate.value;
   cmbstatus=document.frmIssue.cmbstatus.value;
   
   cmbmajor=document.frmIssue.cmbMajor.value;
   var cmbMinor=document.frmIssue.cmbMinor.value;
   
   var office=document.frmIssue.offices.value;
  
    var url="../../../ViewAllResponseServlet.con?command=submit&txtfromdate="+txtfromdate+"&txttodate="+txttodate+"&cmbstatus="+cmbstatus+"&cmbmajor="+cmbmajor+"&cmbMinor="+cmbMinor+"&office="+office;
  // alert(url);
    var req=getTransport();
    req.open("Get",url,true);
    req.onreadystatechange=function()
    {
        processresponse(req);
    };
    req.send(null);
    
   

}

function processresponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            
            var tbody=document.getElementById("tblList");
            
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
                tbody.deleteRow(0);
            }
       
       		
            var response=req.responseXML.getElementsByTagName("response")[0];
            
            
            
            var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            
            if(command=="get")
            	{
            var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
           
            
           
    	 	
       	 var length=document.getElementById("cmbMinor").options.length;
      
 	 	var optionss=document.getElementById("cmbMinor");
 	 	for(var i=length-1;i>=0;i--)
 	 		{
 	 			optionss.remove(i);
 	 		}
 	 	
 	 	
 		var listOpt=document.createElement("option");
 	 	optionss.appendChild(listOpt);
			listOpt.text="Select Minor System";
			listOpt.value="Select Minor System";
    	 	
            for(var j=0;j<=count;j++)
            	{
            	      	
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	
            	
            	if(flag=="success")
            		{
            	 	var MINOR_SYSTEM_ID= response.getElementsByTagName("MINOR_SYSTEM_ID")[j].firstChild.nodeValue;
            	 	
            	 	var MINOR_SYSTEM_DESC= response.getElementsByTagName("MINOR_SYSTEM_DESC")[j].firstChild.nodeValue;
            	 	
            	 	
            	 	var listOpt1=document.createElement("option");
            	 	var option=document.getElementById("cmbMinor");
            	 	option.appendChild(listOpt1);
					listOpt1.text=MINOR_SYSTEM_DESC;
					listOpt1.value=MINOR_SYSTEM_ID;
            		}
            	}
            }
            
            if(command=="load")
            {
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                var issuevalue=response.getElementsByTagName("options");
                
                for(var i=0;i<issuevalue.length;i++)
                {                   
                  
                    
                    var issue=response.getElementsByTagName("issueid")[i].firstChild.nodeValue;
                    var majordesc=response.getElementsByTagName("majorsystemdesc")[i].firstChild.nodeValue;
                    var MINOR_SYSTEM_DESC=response.getElementsByTagName("MINOR_SYSTEM_DESC")[i].firstChild.nodeValue;
                    var subject=response.getElementsByTagName("subject")[i].firstChild.nodeValue;
                    var reportdate=response.getElementsByTagName("reportdate")[i].firstChild.nodeValue;
                    var status=response.getElementsByTagName("status")[i].firstChild.nodeValue;
                    
                    
                    if(response.getElementsByTagName("solution")[i].firstChild == null){
						 var solution = "";
					} else{
						var solution=response.getElementsByTagName("solution")[i].firstChild.nodeValue;
					}
					
                    
                    
                    var desc=response.getElementsByTagName("desc")[i].firstChild.nodeValue;
                    
                    stopwaiting(document.frmIssue);
                    
                    if(solution!="null")
                    {
                    solution=solution;
                    }
                    else
                    {
                    solution="";
                    }
                    if(status=="O")
                    {
                    status="Open";
                    }
                    else
                    {
                    status="Closed";
                    }
                    if(desc!="null")
                    {
                    desc=desc;
                    }
                    else
                    {
                    desc="";
                    }
                    //This coding for adding the values in table grid
                            
                    var table=document.getElementById("Existing");
                    
                    var mycurrent_row=document.createElement("TR");
                    mycurrent_row.id=issue;
                    var cell1=document.createElement("TD");
                    var cell2=document.createElement("TD");
                    var cell3=document.createElement("TD");
                    var cell4=document.createElement("TD");
                    var cell5=document.createElement("TD");
                    var cell6=document.createElement("TD");
                    var cell7=document.createElement("TD");
                    var cell8=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadValuesFromTable('"+issue+"')";
                    anc.href=url;
                    
                    var txtissue=document.createTextNode(issue);
                    cell1.appendChild(txtissue);
                    var hidden1=document.createElement("input");
                    hidden1.type="hidden";
                    hidden1.name="issue";
                    hidden1.value=txtissue;
                    cell1.appendChild(hidden1);
                    mycurrent_row.appendChild(cell1);
                    
                     var empid=response.getElementsByTagName("empid")[i].firstChild.nodeValue;
                     var empname=response.getElementsByTagName("empname")[i].firstChild.nodeValue;
                     var officename=response.getElementsByTagName("officename")[i].firstChild.nodeValue;
                      
                     var cell=document.createElement("TD");
                     var txtempid=document.createTextNode(empid);
                    cell.appendChild(txtempid);
                    mycurrent_row.appendChild(cell);
                    
                     var cell=document.createElement("TD");
                     var txtempname=document.createTextNode(empname);
                    cell.appendChild(txtempname);
                    mycurrent_row.appendChild(cell);
                    
                    var cell=document.createElement("TD");
                     var txtofficename=document.createTextNode(officename);
                    cell.appendChild(txtofficename);
                    mycurrent_row.appendChild(cell);
                    
                    var txtmajordesc=document.createTextNode(majordesc);
                    cell2.appendChild(txtmajordesc);
                    mycurrent_row.appendChild(cell2);
                    
                    var txtminordesc=document.createTextNode(MINOR_SYSTEM_DESC);
                    cell3.appendChild(txtminordesc);
                    mycurrent_row.appendChild(cell3);
                    
                    var txtsubject=document.createTextNode(subject);
                   
                    cell4.appendChild(txtsubject);
                    mycurrent_row.appendChild(cell4);
                    
                    var txtreportdate=document.createTextNode(reportdate);
                    cell5.appendChild(txtreportdate);
                    mycurrent_row.appendChild(cell5);
                    
                    var txtstatus=document.createTextNode(status);
                    cell6.appendChild(txtstatus);
                    mycurrent_row.appendChild(cell6);
                    
                    var txtdesc=document.createTextNode(desc);
                   // cell6.setAttribute("width","25%");
                    cell7.appendChild(txtdesc);
                    mycurrent_row.appendChild(cell7);
                    
                    var txtsolution=document.createTextNode(solution);
                    // alert("b4")
                    cell8.setAttribute("width","25%");
                    //alert("after")
                    cell8.appendChild(txtsolution);
                    mycurrent_row.appendChild(cell8);
                    tbody.appendChild(mycurrent_row);
                    
                    
                    
                }
            }
            
            else
            {
                    alert('No more issues in this period');
            }
            }
        }
    }
}



/****************************************************/

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
//var err;
function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
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
   //     var f=DateFormat(t,c,event,true,'3');
        var f=isValidDate(c);
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

//This Coding for Date Validation and Checking     
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
                return false ;
        }
       

}

function nullcheck()
{

    if((document.frmIssue.txtfromdate.value=="") || (document.frmIssue.txtfromdate.value.length<=0))
    {
        alert("Please Enter From Date");
        document.frmIssue.txtfromdate.focus();
        return false;
    }
    else
    {
           checkdt(document.frmIssue.txtfromdate);
            
    }
    if((document.frmIssue.txttodate.value=="") || (document.frmIssue.txttodate.value.length<=0))
    {
        alert("Please Enter To Date");
        document.frmIssue.txttodate.focus();
        return false;
    }
      else
    {
           checkdt(document.frmIssue.txttodate);
            
    }
    
    if((document.frmIssue.cmbMajor.value=="0") )
    	{
    		alert("Please Select Major System");
    		return false;
    	}
    
    if((document.frmIssue.cmbMinor.value=="Select Minor System") )
	{
		alert("Please Select Minor System");
		return false;
	}
    
return true;
}

function buttonsubmit()
{
    if(nullcheck())
    {
        //alert('ok');
        callServer();
    }
    else
    {
       // alert('no');
    }

}