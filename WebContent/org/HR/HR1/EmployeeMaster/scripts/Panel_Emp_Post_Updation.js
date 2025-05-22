//alert("show me nom admin");
//alert("show me nom admin");
//var a=1;
var seq = 0;
var senio_no=new Array();
var seni_sub__no=new Array();
var Posting_issued=new Array();
var com_rot_code=new Array();
var dob_cadre=new Array();
var post_order_date=new Array();
var remarks=new Array();
var panel=new Array();
var empid=new Array();
var empnames=new Array();

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
document.Panel_Emp_Det_Updation.emp_id.value=emp;
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
        if((document.Panel_Emp_Det_Updation.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.Panel_Emp_Det_Updation.txtDept_Id.value;
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
    document.Panel_Emp_Det_Updation.txtOffice_Id.value=jobid;
    document.Panel_Emp_Det_Updation.txtDept_Id.value=deptid;
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
        if((document.Panel_Emp_Det_Updation.txtEmployeeid.value==null)||(document.Panel_Emp_Det_Updation.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.Panel_Emp_Det_Updation.txtEmployeeid.focus();
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
        var str="EMP_NOMINEE_ListAll.jsp?id="+document.Panel_Emp_Det_Updation.txtEmployeeid.value;
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
        var fromdt=document.Panel_Emp_Det_Updation.txtDateFrom.value;
        var todt=document.Panel_Emp_Det_Updation.txtDateTo.value;
        
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
            //document.Panel_Emp_Det_Updation.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.Panel_Emp_Det_Updation.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.Panel_Emp_Det_Updation.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;
}

function checkquali()
{
checkempid();
     if((document.Panel_Emp_Det_Updation.Panel_Emp_Det_Updation.value==0)||(document.Panel_Emp_Det_Updation.Panel_Emp_Det_Updation.value.length==0))
        {
            alert("Enter Panel_Emp_Det_Updation ");
            document.Panel_Emp_Det_Updation.Panel_Emp_Det_Updation.focus();
            return false;
        }
    return true;
}




function notNull(p)
{

   if((document.Panel_Emp_Det_Updation.txtEmployeeid.value==null)||(document.Panel_Emp_Det_Updation.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.Panel_Emp_Det_Updation.txtEmployee.value="";
        document.Panel_Emp_Det_Updation.txtdob.value="";
        document.Panel_Emp_Det_Updation.txtGpf.value="";
        document.Panel_Emp_Det_Updation.txtEmployeeid.value="";
        
        document.Panel_Emp_Det_Updation.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.Panel_Emp_Det_Updation.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.Panel_Emp_Det_Updation.txtEmployeeid.value="";
        document.Panel_Emp_Det_Updation.txtEmployeeid.focus();
        return false;
    }
    
    if((document.Panel_Emp_Det_Updation.Panel_Emp_Det_Updation.value==0)||(document.Panel_Emp_Det_Updation.Panel_Emp_Det_Updation.value.length==0))
        {
            alert("Enter Panel_Emp_Det_Updation ");
            document.Panel_Emp_Det_Updation.Panel_Emp_Det_Updation.focus();
            return false;
        }
       /*  var c=checkdate();
       if(c==false)
       {
            document.Panel_Emp_Det_Updation.txtDateTo.focus();
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


function offclr()
{
    
    document.Panel_Emp_Det_Updation.txtOffice_Id.value='';
    
    document.Panel_Emp_Det_Updation.txtOffice_Name.value='';
    document.Panel_Emp_Det_Updation.txtOffice_Address1.value='';
   // document.Panel_Emp_Det_Updation.txtOffice_Address2.value='';
   // document.Panel_Emp_Det_Updation.txtOffice_City.value='';
       
   

}

function getPanel()
{
	//clrs();
     document.getElementById("panel_reg_no").innerHTML="";
	document.getElementById("grid").innerHTML="";
var cadre=document.getElementById("cadre_id").value;	
var url="../../../../../Panel_Emp_Det_Updation?Command=getPanel&cadre="+cadre;
 //alert("Admin   ->"+  url);
 
 req.open("POST",url,true);
 req.onreadystatechange=handleResponse;
 if(window.XMLHttpRequest)
         req.send(null);
 else req.send();  
}

function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
        	//alert(req.responseText);
           // stopwaiting(document.Panel_Emp_Det_Updation);
            
             baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // service=baseResponse;
         // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          //  alert('test'+tagcommand);
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loademp")
            {
            	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
               {
                      
                     
                       var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
                       var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
                       var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
                       var DESIGNATION=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
                       document.Panel_Emp_Det_Updation.emp_name.value=ename;
                       if(edob=="-")
                           edob="";
                       document.Panel_Emp_Det_Updation.dob.value=edob;
                     
                       if(egpf==0)
                           egpf="";
                      // document.Panel_Emp_Det_Updation.txtGpf.value=egpf;
                       document.Panel_Emp_Det_Updation.desig_id.value=DESIGNATION;
                       //checkEmp();
                      
                       
                       
               }
                if(flag=="Nocadre")
                	{
                	alert("This Employee Not Eligible for this Cadre");
                	document.getElementById("emp_id").value="";
                	document.getElementById("emp_name").value="";
                	document.getElementById("desig_id").value="";
                	document.getElementById("dob").value="";
                    document.getElementById("emp_id").focus();
                	}
                if(flag=="failure")
                {
                  alert("Enter Valid Employee Id ");
                  document.getElementById("emp_id").value="";
                  //document.getElementById("emp_id").focus();
                }
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
                         document.Panel_Emp_Det_Updation.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.Panel_Emp_Det_Updation.txtEmployeeid.focus();
                         return false;
                }
                else
                {
                    return true;
                }
                    
                
            }
            else if(Command=="getPanel")
            {
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
            	  if(flag=="success")
                	{
                    
      	    	 
      	    	  //var len = baseResponse.getElementsByTagName("tot").length;
      	    	  var proc_nos=document.getElementById("panel_reg_no");
      	    	  var value=baseResponse.getElementsByTagName("tot");
                    var option=document.createElement("OPTION");
                    option.text="--Select Proceeding No--";
                    option.value="0";
                    try
                    {
                    	proc_nos.add(option);
                    }catch(errorObject)
                    {
                    	proc_nos.add(option,null);
                    }
      	    	    	
      	    	    	for ( var i = 0; i < value.length; i++) 
      	    	        {
      	    	    		//alert(value.length)
      	    	    		var tmpoption=value.item(i);
      	    	    	 var panel_id_no_date=tmpoption.getElementsByTagName("panel_id_no_date")[0].firstChild.nodeValue;
      	    	    	 var PANEL_ID=tmpoption.getElementsByTagName("PANEL_ID")[0].firstChild.nodeValue;
      	    	    	 //var PANEL_DATE=tmpoption.getElementsByTagName("PANEL_DATE")[0].firstChild.nodeValue;
      	    	    	
      	    	    	 
      	    	    	 //var proc=proc_no + " - " + PROCEEDINGS_DATE
      	    	    	 var option=document.createElement("OPTION");
                           option.text=panel_id_no_date;
                           option.value=PANEL_ID;
                          // alert(proc_no);
                           //Making Browser Independent
                           try
                           {
                        	   proc_nos.add(option);
                           }
                           catch(errorObject)
                           {
                        	   proc_nos.add(option,null);
                           }
                          // document.getElementById("panel_reg_date").value=PANEL_DATE;
      	    	        }
      	    	    	
      	    	    	document.getElementById("panel_reg_date").value="";
      	       
      	      
                	}
            }
            
             else if(Command=="Delete")
            {}
             else if(Command=="SerGroup")
            {}
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
           
            
            
        }  
        
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
          //document.Panel_Emp_Det_Updation.txtSNo.focus();
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
    if(document.Panel_Emp_Det_Updation.txtEmployeeid.value==null || document.Panel_Emp_Det_Updation.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.Panel_Emp_Det_Updation.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function adds(cmdval)
{
	
	
	var valid=vali();
	
    if(valid==true)
	{
		var j = 0;
		if (cmdval == 0) {
			j = senio_no.length;
			//alert(j)
		} else {
			// alert(z);

			j = z;

		}
		
		//alert(j);

		senio_no[j] = document.getElementById("senio_no").value;	
		seni_sub__no[j] = document.getElementById("seni_sub__no").value;
		post_order_date[j]=document.getElementById("post_order_date").value;
		if(document.Panel_Emp_Det_Updation.post_issued[0].checked==true)
			{
			
			Posting_issued[j]='Yes';
			}
		else if(document.Panel_Emp_Det_Updation.post_issued[1].checked==true)
		{
			Posting_issued[j]='No';
			post_order_date[j]="";
		}
		//alert(Posting_issued[j])
		
		
		remarks[j]=document.getElementById("remarks").value;
		empid[j]=document.getElementById("emp_id").value;
		empnames[j]=document.getElementById("emp_name").value;
		
		grid();
		

	}
}
function delvalues() {
	//document.getElementById("grid").value = "";
	
	 val = 0;
	for ( var i = 0; i < senio_no.length; i++) {

		if (senio_no[i] == document.getElementById("senio_no").value) {
			val = i;
		}
	}

	senio_no.splice(val, 1);
	seni_sub__no.splice(val, 1);
	post_order_date.splice(val, 1);
	Posting_issued.splice(val, 1);
	//dob_cadre.splice(val, 1);
	//date_relg.splice(val, 1);
	empid.splice(val, 1);
	//empid.splice(val, 1);
	empnames.splice(val, 1);
	grid();
	clrs();
	//deletion();
	
}
function grid() {

	z = 0;
	try {
		document.getElementById("grid").innerHTML = "";
	} catch (e) {
		document.getElementById("grid").innerTEXT = "";
	}

	var tbody = document.getElementById("grid");

	for ( var i = 0; i < senio_no.length; i++) {

		
		
		var tr = document.createElement("tr");

		var td = document.createElement("td");
		var text = document.createTextNode("EDIT");
		var a = document.createElement("a");
		a.setAttribute("href", "#");
		a.setAttribute("onclick", "loadvalu(" + i + ")");
		a.appendChild(text);
		td.appendChild(a);
		tr.appendChild(td);

		 
		
		var td = document.createElement("td");
		td.innerHTML = empid[i];
		tr.appendChild(td);
		
		//alert(i)
		//alert(empnames[i]);
		var td = document.createElement("td");
		td.innerHTML = empnames[i];
		tr.appendChild(td);
		
		
		var td = document.createElement("td");
		td.innerHTML = senio_no[i];
		tr.appendChild(td);
		
		var td = document.createElement("td");
		td.innerHTML = seni_sub__no[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = Posting_issued[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = post_order_date[i];
		tr.appendChild(td);

		
		
		var td = document.createElement("td");
		td.innerHTML = remarks[i];
		tr.appendChild(td);

		

		tbody.appendChild(tr);
	}
	document.getElementById("emp_id").value="";
	document.getElementById("emp_name").value="";
	document.getElementById("senio_no").value="";
	document.getElementById("desig_id").value="";
	document.getElementById("dob").value="";
	document.getElementById("seni_sub__no").value="";
	//document.getElementById("turn_no").value = "";
	//document.getElementById("com_rot_code").selectedIndex = 0;
	//document.getElementById("dob_cadre").value="";
	document.getElementById("post_order_date").value="";
	document.getElementById("remarks").value = "";
	document.getElementById('add1').style.display = "";
	document.getElementById("seni_sub__no").readOnly=true;
	document.getElementById("seni_sub").checked=false;
	//document.getElementById('audit_type').readOnly = false;
	document.getElementById('upd').style.display = 'none';
	document.getElementById('del').style.display = 'none';
	document.getElementById('add1').disabled = false;
	//document.getElementById('ins').disabled = false;
	//document.getElementById('delete').disabled = false;
	

	

}

function Updates()
{
	
	var val=nullcheck();
	if(val==true)
	{
	var req= getTransport();
	var surl="";
	var cadre_id = document.getElementById("cadre_id").value;
	var panel_reg_no=document.getElementById("panel_reg_no").value;
	var post_order_date = document.getElementById("post_order_date").value;
	var post_order_no=document.getElementById("post_order_no").value;
	var remarks=document.getElementById("remarks").value;
	for(var i=0;i<empid[i];i++)
		{
		 
		 if(document.getElementById("chk_bk_"+empid[i]).checked==true)
			 {
			 surl=surl+"&emp_id="+empid[i];
			 }
		
		}
if(surl=="")
	{
	alert("Please Select Atleast One Employee Id By Using Grid ");
	return false;
	}
	url="../../../../../Panel_Emp_Post_Updation?Command=Updatevalues&cadre_id="
			+ cadre_id +"&panel_reg_no="+panel_reg_no+"&post_order_date="+post_order_date+"&post_order_no="+post_order_no+"&remarks="+remarks+surl;
	//alert("url is--->"+url);
	req.open("POST",url,true);              
	req.onreadystatechange=function()
    {
      viewresponse(req);
    };
	}
    ///call the date function here
    req.send(null);
}



function viewresponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
        	//alert(req.responseText);
           // stopwaiting(document.Panel_Emp_Det_Updation);
            
             baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // service=baseResponse;
         // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          //  alert('test'+tagcommand);
            var Command=tagcommand.firstChild.nodeValue;
             if(Command=="Updatevalues")
            {
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
            	  if(flag=="success")
                	{
            		  alert("Record updated Successfully !");
            		  
            		  document.getElementById("grid").innerHTML="";
            		  document.getElementById("cadre_id").selectedIndex=0;
        			  document.getElementById("panel_reg_no").selectedIndex=0;
            		  document.getElementById("panel_reg_date").value="";
            		  document.getElementById("post_order_date").value="";
            		  document.getElementById("post_order_no").value="";
            		  document.getElementById("remarks").value="";
            		  /*document.getElementById('add1').disabled = false;
      				document.getElementById('upd').style.display = 'none';
      				document.getElementById('CmdUpdate').style.display = 'none';
      				document.getElementById('Cmdvalidate').style.display = 'none';
      				document.getElementById('del').style.display = 'none';
      				document.getElementById('del').style.display = 'none';
      				document.getElementById("cadre_id").selectedIndex=0;
      				document.getElementById("panel_reg_no").selectedIndex=0;
      				document.getElementById("CmdAdd").style.display="";
      				document.getElementById("panel_reg_date").value="";*/
            		  //getList();
                	}
            }
            
             if(Command=="getDate")
             {
             	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
             	  if(flag=="success")
                 	{
             		 var PANEL_DATE = baseResponse.getElementsByTagName("PANEL_DATE")[0].firstChild.nodeValue;	
             		 
             		 document.getElementById("panel_reg_date").value=PANEL_DATE;
             		getPanelEmp();
             		document.getElementById("emp_id").value="";
             		document.getElementById("emp_name").value="";
             		document.getElementById("desig_id").value="";
             		document.getElementById("dob").value="";
                 	}
             	  
            }
        }
    }
    
}

function getPanelEmp()
{
	
	var req= getTransport();
	document.getElementById("grid").innerHTML = "";
	
	//var cadre_id=document.getElementById("cadre_id").value;
	var panel_reg_no=document.getElementById("panel_reg_no").value;
	
	var url="../../../../../Panel_Emp_Post_Updation?Command=getPanelEmp&panel_reg_no="+panel_reg_no;
   //alert("URL"+url);
    req.open("POST",url,true);              
    req.onreadystatechange=function()
    {
      handRep(req);
    };
    ///call the date function here
    req.send(null);
}

function handRep(req)
{
	
	if(req.readyState==4)
{
	if(req.status==200)
	{
		//alert(req.responseText)
		  baseResponse=req.responseXML.getElementsByTagName("response")[0];
		 
         
           var tagcommand=baseResponse.getElementsByTagName("command")[0];
           //alert('test'+tagcommand);
           var Command=tagcommand.firstChild.nodeValue;
	      
	      changepagesize();
          changepage();
	}
}
}
function changepagesize() 
{
	try {
		document.getElementById("grid").innerHTML = "";
	} catch (e) {
		document.getElementById("grid").innerTEXT = "";
	}
	
    pagesize = document.getElementById("cmbpagination").value;
    var len = baseResponse.getElementsByTagName("tot").length;
    //alert(len)
    var cmbpage = document.getElementById("cmbpage");
    try {
        cmbpage.innerHTML = "";
    } catch (e) {
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

    var tlist = document.getElementById("grid");
    try {
        tlist.innerHTML = "";
    } catch (e) {
        tlist.innerText = "";
    }
    if(baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue=="success")
    {
    var len = baseResponse.getElementsByTagName("tot").length;

    var pageno = document.getElementById("cmbpage").value;

    var ul = 0, ll = 0;

    ul = pageno * pagesize;
    ll = ul - pagesize;
 if(len > 0)
	 {
    for ( var i = ll; i < ul; i++) 
    {
    	
	
	
	var EMPLOYEE_ID = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;	
	var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
	var SENIORITY_NO = baseResponse.getElementsByTagName("SENIORITY_NO")[i].firstChild.nodeValue;
	var SENIORITY_SUB_NUMBER = baseResponse.getElementsByTagName("SENIORITY_SUB_NUMBER")[i].firstChild.nodeValue;
	var POSTING_ISSUED = baseResponse.getElementsByTagName("POSTING_ISSUED")[i].firstChild.nodeValue;	
	var POSTING_ORDER_DATE = baseResponse.getElementsByTagName("POSTING_ORDER_DATE")[i].firstChild.nodeValue;
	var REMARKS = baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
	var PROCESS_FLOW_ID = baseResponse.getElementsByTagName("PROCESS_FLOW_ID")[i].firstChild.nodeValue;
	var PANEL_NUMBER=baseResponse.getElementsByTagName("PANEL_NUMBER")[i].firstChild.nodeValue;
	var empid_name=EMPLOYEE_ID+ "-" +empname;
	//alert(PANEL_NUMBER)
	
	 if(EMPLOYEE_ID==null || EMPLOYEE_ID=="null")
		 {
		 EMPLOYEE_ID="";
		 }
	 if(empname==null || empname=="null")
	 {
		 empname="";
	 }
	 if(SENIORITY_NO==null || SENIORITY_NO=="null")
	 {
		 SENIORITY_NO="";
	 }
	 if(SENIORITY_SUB_NUMBER==null || SENIORITY_SUB_NUMBER=="null")
	 {
		 SENIORITY_SUB_NUMBER="";
	 }
	 if(POSTING_ISSUED==null || POSTING_ISSUED=="null")
	 {
		 POSTING_ISSUED="";
	 }
	 if(POSTING_ORDER_DATE==null || POSTING_ORDER_DATE=="null")
	 {
		 POSTING_ORDER_DATE="";
	 }
	 
	 if(REMARKS==null || REMARKS=="null")
	 {
		 REMARKS="";
	 }
	 if(PANEL_NUMBER==null || PANEL_NUMBER=="null")
	 {
		 PANEL_NUMBER="";
	 }
	 
	
	  empid[i]=EMPLOYEE_ID;
	   panel[i]=PANEL_NUMBER;
	  empnames[i]=empname;
	  senio_no[i]=SENIORITY_NO;
	  seni_sub__no[i]=SENIORITY_SUB_NUMBER;
	  Posting_issued[i]=POSTING_ISSUED;
	  post_order_date[i]=POSTING_ORDER_DATE;
	  
	  remarks[i]=REMARKS;
	  
	  
	  
		
	  
	  
	  
	 var tr = document.createElement("TR");
     tr.id = seq;
     var td = document.createElement("TD");
     var anc = document.createElement("A");

    
        
         
         
         var td1=document.createElement("td");
         var pay=document.createElement("input");
         pay.setAttribute("type", "checkbox");
         pay.setAttribute("name", "chk_bk");
         pay.setAttribute("id", "chk_bk_"+EMPLOYEE_ID);
         //pay.setAttribute("onclick", "javascript:loadvalue('" + i + "')");
        // td1.setAttribute("align", "center");   
         //td1.setAttribute("class", "tdH");
         td1.appendChild(pay); 
         td1.setAttribute("align", "center");
         tr.appendChild(td1);
         //newtab.appendChild(ros);
         
         
   
     
     var td=document.createElement("TD");
     var PANEL_NUMBER=document.createTextNode(PANEL_NUMBER);
     td.appendChild(PANEL_NUMBER);
     tr.appendChild(td);
     
     
     var td = document.createElement("TD");
     var EMPLOYEE_ID = document.createTextNode(EMPLOYEE_ID);
     td.appendChild(EMPLOYEE_ID);
     tr.appendChild(td);
     
     var td = document.createElement("TD");
     var empname = document.createTextNode(empname);
     td.appendChild(empname);
     tr.appendChild(td);
     
     var td = document.createElement("TD");
     var SENIORITY_NO = document.createTextNode(SENIORITY_NO);
     td.appendChild(SENIORITY_NO);
     tr.appendChild(td);
    
     var td = document.createElement("TD");
     var SENIORITY_SUB_NUMBER = document.createTextNode(SENIORITY_SUB_NUMBER);
     td.appendChild(SENIORITY_SUB_NUMBER);
     tr.appendChild(td);

     var td = document.createElement("TD");
     var POSTING_ISSUED = document.createTextNode(POSTING_ISSUED);
     //td.setAttribute("style", "display:none");
     td.appendChild(POSTING_ISSUED);
      tr.appendChild(td);
      
      var td = document.createElement("TD");
      var POSTING_ORDER_DATE = document.createTextNode(POSTING_ORDER_DATE);
      //td.setAttribute("style", "display:none");
      td.appendChild(POSTING_ORDER_DATE);
       tr.appendChild(td);

    
    
        var td = document.createElement("TD");
        var REMARKS = document.createTextNode(REMARKS);
         td.appendChild(REMARKS);
         tr.appendChild(td);
         
        
            
           
        tlist.appendChild(tr);

        seq++;
       // gethidden();
        
    }
	 }
 else{
	 var iframe=document.getElementById("grid");
     iframe.focus();
	 if(navigator.appName.indexOf('Microsoft')!=-1)
		 {
		 
         iframe.innerHTML="<tr><td align=center colspan=10>There is No Data to Display</td></tr>";
         //iframe.style.color="#0000FF";
		 }
     else
    	 {
    	 
         iframe.innerText="There is No Data to Display";
     iframe.innerHTML="<tr bgcolor='b6eaff' ><td align=center colspan=10>There is No Data to Display</td></tr>";
     //iframe.style.color="#0000FF";
    	 }
    }
    }
}
function loadvalue(i)
{
	 
	z = i;
	
	
	  
	  
	  
		document.getElementById("senio_no").value=senio_no[i];
		if(seni_sub__no[i]!==""){
			document.getElementById("seni_sub").checked=true;
		}
		
		document.getElementById("seni_sub__no").value=seni_sub__no[i];
		//document.getElementById("turn_no").value=turn_no[i];
		//document.getElementById("no_of_paras").value=com_rot_code[i];
		//document.getElementById("com_rot_code").value=com_rot_code[i];
		//document.getElementById("dob_cadre").value=dob_cadre[i];
		//document.getElementById("date_relg").value= date_relg[i];
		document.getElementById("remarks").value= remarks[i];
		document.getElementById("emp_id").value=  empid[i];
		document.getElementById("post_order_date").value=  post_order_date[i];
		//document.getElementById("general_status").value=general_sta[i];
		//document.getElementById("desig").value=Member_desig[i];
		//document.getElementById("serv_grp").value=serv_grp_id[i];
		//cate_names=audit_cate[i];
		//getcatedet();
		//alert(seni_sub__no[i]);
		if(seni_sub__no[i]!==""){
			document.getElementById("seni_sub").checked=true;
			document.getElementById("seni_sub__no").readOnly=false;
		}
		else
			{
			document.getElementById("seni_sub").checked=false;
			document.getElementById("seni_sub__no").readOnly=true;
			}
		 doFunction('loademp','null');
		
		if(Posting_issued[i]=='Yes')
			{
			document.Panel_Emp_Det_Updation.post_issued[0].checked=true;
			shows();
			}
		else
			{
			document.Panel_Emp_Det_Updation.post_issued[1].checked=true;
			hides();
			}
		document.getElementById("CmdUpdate").style.display="";
		document.getElementById("Cmdvalidate").style.display="none";
		document.getElementById('del').style.display ="";
		document.getElementById("CmdAdd").style.display="none";
		document.getElementById('add1').disabled = true;
		////document.getElementById('ins').disabled = true;
		//document.getElementById('delete').disabled = true;

		// document.getElementById('upd').disabled=true;
		// document.getElementById('del').disabled=true;
		document.getElementById('upd').style.display = "";
		//document.getElementById('del').style.display = "";
		//document.getElementById('delete').style.display = "none";
		/*document.getElementById("Cmdvalidate").style.display="";
		get_desig();
		GetoffDet();   
		GetAuditDetails();
	*/
		
}
function vali()
{
	 if(document.getElementById("emp_id").value=="")
		{
		alert("Please Enter Employee id ");
		document.getElementById("emp_id").focus();
		return false;
		
		}
	else if(document.getElementById("senio_no").value=="")
	{
	alert("Please Enter  Seniority No");
	document.getElementById("emp_id").focus();
	return false;
	}
	else if(document.Panel_Emp_Det_Updation.post_issued[0].checked==true)
		{
		 if(document.getElementById("post_order_date").value=="")
			 {
			   alert("Please Enter Post Order Date");
				document.getElementById("post_order_date").focus();
				return false;
			 }
		}
	else if(document.getElementById("seni_sub").checked==true)
	{
	 if(document.getElementById("seni_sub__no").value=="")
	  {
			alert("Please Enter Seniority Sub No");
			document.getElementById("seni_sub__no").focus();
			return false;
	  }
	}
	
	return true;
}
function nullcheck()
{
	 if(document.getElementById("cadre_id").selectedIndex==0)
	{
		alert("please select  Cadre Name");
		return false;
	}
	 else if(document.getElementById("panel_reg_no").selectedIndex==0)
		{
			alert("please select Select Proceeding No");
			return false;
		}
	 else if(document.getElementById("post_order_date").value=="")
		{
			alert("please Please Enter Posting Order Date ");
			document.getElementById("post_order_date").focus();
			return false;
		}
	 else if(document.getElementById("post_order_no").value=="")
		{
			alert("please Please Enter Posting Order Number ");
			document.getElementById("post_order_no").focus();
			return false;
		}
	

	return true;
}
function clearall()
{
    document.getElementById("cadre_id").selectedIndex=0;
    document.getElementById("panel_reg_no").selectedIndex=0;
    document.getElementById("post_order_date").value="";
    document.getElementById("post_order_no").value="";
    document.getElementById("remarks").value="";
    //document.getElementById("dob").value="";
   // document.getElementById("senio_no").value="";
    //document.getElementById("seni_sub__no").value="";
    //document.getElementById("turn_no").value="";
    //document.getElementById("com_rot_code").selectedIndex=0;
   // document.getElementById("dob_cadre").value="";
   // document.getElementById("panel_reg_date").value="";
    //document.getElementById("remarks").value="";
    
    document.getElementById("grid").innerHTML="";
    document.getElementById("post_order_date").value="";
}
function clrs()
{
	document.getElementById("emp_id").value="";
    document.getElementById("emp_name").value="";
    document.getElementById("desig_id").value="";
    document.getElementById("dob").value="";
    document.getElementById("senio_no").value="";
    document.getElementById("seni_sub__no").value="";
    
   // document.getElementById("turn_no").value="";
    //document.getElementById("com_rot_code").selectedIndex=0;
    //document.getElementById("dob_cadre").value="";
    //document.getElementById("date_relg").value="";
    document.getElementById("remarks").value="";
    document.getElementById("post_order_date").value="";
}
function Compare(fdate,tdate)
{
	
	var fromDate=document.getElementById(fdate).value;
	var toDate=document.getElementById(tdate).value;
	
	
	
	
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		var fret=check_dateformatss(document.getElementById(fdate));
		var tret=check_dateformatss(document.getElementById(tdate));
		if(fret==true && tret==true)
		{
	var f_date =new Array();
	var t_date =new Array();
	
	f_date=fromDate.split("/");
	t_date=toDate.split("/");
	
	var fromYear=0,toYear=0,fromMonth=0,toMonth=0,fromDay=0,toDay=0;
	
	if(f_date[1]=='08' || f_date[1] =='8')
		f_date[1]="08";
	
	if(f_date[1]=='09' || f_date[1] =='9')
		f_date[1]="09";
	
	if(f_date[0]=='08' || f_date[0] =='8')
		f_date[0]="08";
	
	if(f_date[0]=='09' || f_date[0] =='9')
		f_date[0]="09";
	
	if(t_date[1]=='08' || t_date[1] =='8')
		t_date[1]="08";
	
	if(t_date[1]=='09' || t_date[1] =='9')
		t_date[1]="09";
	
	if(t_date[0]=='08' || t_date[0] =='8')
		t_date[0]="08";
	
	if(t_date[0]=='09' || t_date[0] =='9')
		t_date[0]="09";
	
	fromYear=parseInt(f_date[2],10);
	fromMonth=parseInt(f_date[1],10);
	fromDay=parseInt(f_date[0],10);
	
	toYear=parseInt(t_date[2],10);
	toMonth=parseInt(t_date[1],10);
	toDay=parseInt(t_date[0],10);
	
	if(fromYear>toYear)
	{
		ret= false;
	}
	else if(fromYear < toYear)
	{
		ret= true;
	}
	else if(fromMonth > toMonth)
	{
		ret= false;
		
	}
	else if(fromMonth < toMonth)
	{
		ret= true;
		
	}
	else if(fromDay < toDay)
	{
		ret= true;
		
	}
	else if(fromDay >= toDay)
	{
		ret= false;
		
	}
	
	}
	}
	
	if(ret==false)
	{
		alert("Date of Regularisation Should be greater than Date Of joining ");
		document.getElementById(fdate).value="";
		document.getElementById(tdate).value="";
		return false;
	}
	else
	{
		return true;
	}
	
}
function check_dateformatss(field)
{
	//alert("jai r")
	var arr=new Array();
	
	var field_value=field.value;
	field_value=field_value.trim();

	if(field_value=="")
	{
	}
	else
	{

		
	arr=field_value.split("/");
	
//	alert(arr.length);
	if(arr.length==3)
	{
		var ret=check_validdate(arr[0],arr[1],arr[2]);
		if(ret==false)
		{
			alert("Invalid Date.");
			field.value="";
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		alert("Date format Should be DD/MM/YYYY");
		field.value="";
		return false;
	}
	
	}
	
}
function check_validdate(Day,Mn,Yr){
    var DateVal = Mn + "/" + Day + "/" + Yr;
    var dt = new Date(DateVal);
    
    if(parseInt(Yr)<=1900 || parseInt(Yr)>2100)
	{
		return false;
	}
	else if(dt.getDate()!=Day)
	{
       return(false);
        
	}
    else if(dt.getMonth()!=Mn-1)
    {        
        return(false);
       
    }
    else if(dt.getFullYear()!=Yr)
    {
    	return(false);
    }
    else
    {
    	return true;
    }

 }
function show()
{
	  if(document.getElementById("seni_sub").checked==true)
		{
		  
	      document.getElementById("seni_sub__no").readOnly=false;
		}
	  else
		  {
		  document.getElementById("seni_sub__no").value="";	  
		  document.getElementById("seni_sub__no").readOnly=true;
		  }
	 
}
function shows()
{
	
	if(document.Panel_Emp_Det_Updation.post_issued[0].checked==true)
	{
	 
      document.getElementById("post_date").style.display='';
	}
}
function hides()
{
	if(document.Panel_Emp_Det_Updation.post_issued[1].checked==true)
	{
		
      document.getElementById("post_date").style.display='none';
	}
}
function clrs()
{
	// document.getElementById("cadre_id").selectedIndex=0;
	    document.getElementById("proc_no").selectedIndex=0;
	    document.getElementById("emp_id").value="";
	    document.getElementById("emp_name").value="";
	    document.getElementById("desig_id").value="";
	    document.getElementById("dob").value="";
	    document.getElementById("senio_no").value="";
	    document.getElementById("seni_sub__no").value="";
	   // document.getElementById("turn_no").value="";
	   // document.getElementById("com_rot_code").selectedIndex=0;
	   // document.getElementById("dob_cadre").value="";
	   // document.getElementById("date_relg").value="";
	    document.getElementById("remarks").value="";
}
function checkEmp()
{
	
	try
	{


for ( var i = 0; i < senio_no.length; i++) {
		var tempemp=document.getElementById("emp_id").value;
		
		var temp=empid[i];
		if(tempemp==temp)
			{
			alert("This Employee Id Already Exist !");
			document.getElementById("emp_id").value="";
			document.getElementById("emp_id").focus();
			return false;
			}
		
}
	}
	catch(e)
	{
		
	}
	doFunction('loademp','null');
}
function getDuplicate()
{

	
	try
	{


for ( var i = 0; i < senio_no.length; i++) {
		var tempseni_no="";
		tempseni_no=document.getElementById("senio_no").value;
		var tempseni_sub_no=""
			tempseni_sub_no=	document.getElementById("seni_sub__no").value;
		var sebisub_txt=tempseni_no+tempseni_sub_no;
		var tmoseni=senio_no[i];
		var tmpseni_sub=seni_sub__no[i];
				
		var seni_sub=tmoseni+tmpseni_sub;
				

				if(sebisub_txt==seni_sub)
					{
					alert("This Seniority No Already Exist");
					document.getElementById("senio_no").value="";
					document.getElementById("senio_no").focus();
					return false;
					}
		
		
}
	}
	catch(e)
	{
		
	}
	//doFunction('loademp','null');
	adds('0');
}
function clearArray()
{
	try
	{
		var len=empid.length;
	for ( var i = 0; i < len; i++) {

		senio_no.splice(0, 1);
		empid.splice(0, 1);

		
		
	}
	}
	catch(e)
	{
		
	}
}
function clsfield()
{
	document.getElementById("panel_reg_date").value="";
	  document.getElementById("post_order_date").value="";
	  document.getElementById("post_order_no").value="";
}
function getDate()
{
	clearArray();
    clsfield();
	document.getElementById("grid").innerHTML="";	
	var req= getTransport();
	var panel_reg_no = document.getElementById("panel_reg_no").value;
	url="../../../../../Panel_Emp_Det_Updation?Command=getDate&panel_reg_no="
			+ panel_reg_no;
	//alert("url is--->"+url);
	req.open("POST",url,true);              
	req.onreadystatechange=function()
    {
      viewresponse(req);
    };
	
    ///call the date function here
    req.send(null);



}