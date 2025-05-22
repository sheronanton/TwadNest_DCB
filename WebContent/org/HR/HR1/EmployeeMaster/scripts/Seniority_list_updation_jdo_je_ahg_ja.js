//alert("show me nom admin");
//alert("show me nom admin");
//var a=1;
var seq = 0;
var senio_no=new Array();
var seni_sub__no=new Array();
var turn_no=new Array();
var com_rot_code=new Array();
var dob_cadre=new Array();
var date_relg=new Array();
var remarks=new Array();
var ref_in_posted=new Array();


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
        winemp=null;
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.Seniority_Proceeding_list.emp_id.value=emp;
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
        if((document.Seniority_Proceeding_list.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.Seniority_Proceeding_list.txtDept_Id.value;
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
    document.Seniority_Proceeding_list.txtOffice_Id.value=jobid;
    document.Seniority_Proceeding_list.txtDept_Id.value=deptid;
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
        if((document.Seniority_Proceeding_list.txtEmployeeid.value==null)||(document.Seniority_Proceeding_list.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.Seniority_Proceeding_list.txtEmployeeid.focus();
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
        var str="EMP_NOMINEE_ListAll.jsp?id="+document.Seniority_Proceeding_list.txtEmployeeid.value;
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
        var fromdt=document.Seniority_Proceeding_list.txtDateFrom.value;
        var todt=document.Seniority_Proceeding_list.txtDateTo.value;
        
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
            //document.Seniority_Proceeding_list.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.Seniority_Proceeding_list.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.Seniority_Proceeding_list.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;
}

function checkquali()
{
checkempid();
     if((document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.value==0)||(document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.value.length==0))
        {
            alert("Enter Seniority_Proceeding_master_updation ");
            document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.focus();
            return false;
        }
    return true;
}




function notNull(p)
{

   if((document.Seniority_Proceeding_list.txtEmployeeid.value==null)||(document.Seniority_Proceeding_list.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.Seniority_Proceeding_list.txtEmployee.value="";
        document.Seniority_Proceeding_list.txtdob.value="";
        document.Seniority_Proceeding_list.txtGpf.value="";
        document.Seniority_Proceeding_list.txtEmployeeid.value="";
        
        document.Seniority_Proceeding_list.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.Seniority_Proceeding_list.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.Seniority_Proceeding_list.txtEmployeeid.value="";
        document.Seniority_Proceeding_list.txtEmployeeid.focus();
        return false;
    }
    
    if((document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.value==0)||(document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.value.length==0))
        {
            alert("Enter Seniority_Proceeding_master_updation ");
            document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.focus();
            return false;
        }
       /*  var c=checkdate();
       if(c==false)
       {
            document.Seniority_Proceeding_list.txtDateTo.focus();
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

function doFunction(Command,param)
{
  
//alert("command :"+Command);
//alert();

    
    var empid=document.Seniority_list_updation_jdo_je_ahg_ja.emp_id.value;
    //var slno=document.Seniority_Proceeding_list.txtSNo.value;
   
  //  alert(empid);
    
    
    
  /*   if(param!='null')
    {
        alert(param);
                var url="../../../../../emp_nomination_sevlet.av?Command=loademp&txtEmployeeid="+param;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
         
    
    }*/
    if(Command=='efocus')
    {
    document.Seniority_list_updation_jdo_je_ahg_ja.txtEmployeeid.focus();
       //alert("focus");
    }
    else
    if(Command=='loademp')
    {
       //alert("load emp");
       // var check=notNull(null);
      //  if(check )
        {
               // startwaiting(document.Seniority_Proceeding_list) ;
                service=null;
/*var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';*/
                //alert('load emp');
               /* if(param=='exist')
                {
                var url="../../../../../emp_nomination_sevlet.av?Command=loademp&txtEmployeeid="+empid+"&param=exist";
                }
                else
                {*/
                var cadre_id=document.Seniority_list_updation_jdo_je_ahg_ja.cadre_id.value;
                var proc_no=document.Seniority_list_updation_jdo_je_ahg_ja.proc_no.value;
                var url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=loademp&txtEmployeeid="+empid+"&cadre_id="+cadre_id+"&proc_no="+proc_no;
               // alert("Admin   ->"+  url);
                //}
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
    
    if(Command=='Add')
    {
   // alert("added");
        var check=notNull('Add');
        //alert(check);
        if(check)
        {
               // statuswaiting(document.Seniority_Proceeding_list) ;
                //if(check)
                var Qual=document.Seniority_Proceeding_list.Seniority_Proceeding_master_updation.value;
                var Spec=document.Seniority_Proceeding_list.Special.value;
                if(document.Seniority_Proceeding_list.reguniv[0].checked==true)
                       Reguniv='Y';
                else
                       Reguniv='N'; 
     var tbody=document.getElementById("tb");
     record1=new Array();
     record2=new Array();
    var d=0;
 for(var i=1;i<=tbody.rows.length;i++)
            {
            var r=document.getElementById(i);
           
                var rcells=r.cells;
                record1[i]=rcells.item(2).firstChild.value;;
                record2[i]=rcells.item(3).firstChild.value;
                if(record1[i]==Qual)
                {
                alert("You have already inserted ");
                d=1;
                clr();
                }
                }
                if(d==0)
                {   
                
                        
                
                
                    var url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=Add&txtEmployeeid="+empid;
                    url=url+"&Qual="+Qual+"&Spec="+Spec+"&Reguniv="+Reguniv;

                             // alert("Admin ->"+url);
                    req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();     
                    
                }    // alert('in add');
                }
        }
    
    
    else if(Command=="Delete")
    {
    
        if(confirm('Do You want to delete this record?'))
        {
               // statuswaiting(document.Seniority_Proceeding_list) ;
                  //var Nominee_Name=document.Seniority_Proceeding_list.Nominee_Name.value;
                  var txtSNo=document.Seniority_Proceeding_list.txtSNo.value;
               //   alert(txtSNo);
                var url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=Delete&txtEmployeeid="+empid;
                url=url+"&txtSNO="+txtSNo;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
        }
    
    
    
    }
     else if(Command=="Clear")
            {
               
                clr1();
                document.Seniority_Proceeding_list.cmdadd.style.display="block";
                document.Seniority_Proceeding_list.cmdupdate.style.display="none";
                //document.Seniority_Proceeding_list.cmdadd.disabled=false;
               // document.Seniority_Proceeding_list.cmdupdate.disabled=true;
                document.Seniority_Proceeding_list.cmddelete.disabled=true;
               
               
                
            }
    else if(Command=="Load")
    {
           if(!(empid==null || empid.length==0))
           {
              //startwaiting(document.Seniority_Proceeding_list) ;
                var url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=Load&txtEmployeeid="+empid;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
            }
    
    }
    
        
    
}

function offclr()
{
    
    document.Seniority_Proceeding_list.txtOffice_Id.value='';
    
    document.Seniority_Proceeding_list.txtOffice_Name.value='';
    document.Seniority_Proceeding_list.txtOffice_Address1.value='';
   // document.Seniority_Proceeding_list.txtOffice_Address2.value='';
   // document.Seniority_Proceeding_list.txtOffice_City.value='';
       
   

}

function getProc()
{
	clrs();
	document.getElementById("proc_no").innerHTML="";
	document.getElementById("grid").innerHTML="";
var cadre=document.getElementById("cadre_id").value;	
var url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=getProc&cadre="+cadre;
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
           // stopwaiting(document.Seniority_Proceeding_list);
            
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
                       document.Seniority_list_updation_jdo_je_ahg_ja.emp_name.value=ename;
                       if(edob=="-")
                           edob="";
                       document.Seniority_list_updation_jdo_je_ahg_ja.dob.value=edob;
                     
                       if(egpf==0)
                           egpf="";
                      // document.Seniority_Proceeding_list.txtGpf.value=egpf;
                       document.Seniority_list_updation_jdo_je_ahg_ja.desig_id.value=DESIGNATION;
                       
                      
                       
                       
               }
                if(flag=="nodata")
                	{
                	alert("Given Employee id Not in Master !");
                	document.getElementById("emp_id").value="";
                	document.getElementById("emp_name").value="";
                	document.getElementById("desig_id").value="";
                	document.getElementById("dob").value="";
                    //document.getElementById("emp_id").focus();
                	}
                if(flag=="AlreadyExsist")
                	{
                	alert("This Employee Already Exsist !");
                	document.getElementById("emp_id").value="";
                	document.getElementById("emp_name").value="";
                	document.getElementById("desig_id").value="";
                	document.getElementById("dob").value="";
                    document.getElementById("emp_id").focus();
                	}
                if(flag=="Nocadre")
                	{
                	alert("Employee Dose not Come Under Selected Carde !");
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
                  document.getElementById("emp_name").value="";
              	  document.getElementById("desig_id").value="";
              	  document.getElementById("dob").value="";
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
                         document.Seniority_list_updation_jdo_je_ahg_ja.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.Seniority_list_updation_jdo_je_ahg_ja.txtEmployeeid.focus();
                         return false;
                }
                else
                {
                    return true;
                }
                    
                
            }
            else if(Command=="getProc")
            {
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
            	  if(flag=="success")
                	{
                    
      	    	 
      	    	  //var len = baseResponse.getElementsByTagName("tot").length;
      	    	  var proc_nos=document.getElementById("proc_no");
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
      	    	    	 var SENIORITY_LIST_ID=tmpoption.getElementsByTagName("SENIORITY_LIST_ID")[0].firstChild.nodeValue;
      	    	    	 var proc_no=tmpoption.getElementsByTagName("proc_no")[0].firstChild.nodeValue;
      	    	    	 var PROCEEDINGS_DATE=tmpoption.getElementsByTagName("PROCEEDINGS_DATE")[0].firstChild.nodeValue;
      	    	    	var proc=proc_no + " - " + PROCEEDINGS_DATE
      	    	    	 var option=document.createElement("OPTION");
                           option.text=proc;
                           option.value=SENIORITY_LIST_ID;
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
      	    	    	
      	    	        }
      	    	    	//document.getElementById("aud_sub_class").value=sub_clss;
      	    	    
      	       
      	      
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
          //document.Seniority_Proceeding_list.txtSNo.focus();
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
    if(document.Seniority_Proceeding_list.txtEmployeeid.value==null || document.Seniority_Proceeding_list.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.Seniority_Proceeding_list.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function adds(cmdval)
{
	
	//document.getElementById("grid").innerHTML="";
	var valid=vali();
	//var val=getDuplicate();
	
    if(valid==true)
	{
    	//if(val==true)
    	//{
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
		seni_sub__no[j] =seni_sub__no[j].toUpperCase();
		turn_no[j] = document.getElementById("turn_no").value;	
		com_rot_code[j]=document.getElementById("com_rot_code").value;
		dob_cadre[j] = document.getElementById("dob_cadre").value;
		date_relg[j]=document.getElementById("date_relg").value;
		remarks[j]=document.getElementById("remarks").value;
		ref_in_posted[j]=document.getElementById("ref_in_wh_posted").value;
		empid[j]=document.getElementById("emp_id").value;
		empnames[j]=document.getElementById("emp_name").value;
		//alert(remarks[j])
		grid();
	}

	//}
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
		tempseni_sub_no=tempseni_sub_no.toUpperCase();
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
function getDuplicate1()
{

	
	try
	{


for ( var i = 0; i < senio_no.length; i++) {
		var tempseni_no="";
		tempseni_no=document.getElementById("senio_no").value;
		var tempseni_sub_no="";
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
	adds('1');
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
/*function dupSeni()
{
	//document.getElementById("grid").innerHTML="";
	var seni="";
	seni=document.getElementById("senio_no").value;
	var seni_sub="";
	seni_sub=document.getElementById("seni_sub__no").value;
	
	
   var seni_sub_text=seni+seni_sub;
	
   
	for ( var i = 0; i < senio_no.length; i++) {
		var senio = senio_no[i];
		var seni_sub = seni_sub__no[i];
		if(seni_sub=='undefined')
			{
			seni_sub="";
			}
	}
	var senarray= senio+seni_sub;
	//alert("seni_sub_text-->"+seni_sub_text);
	//alert("senarray senarray-->"+senarray);
	if(seni_sub_text==senarray)
		{
		alert("This Seniority Number Already Exsist !");
		document.getElementById("senio_no").value="";
		document.getElementById("senio_no").focus();
		
		return false;
		}
	return true;
}*/
function delvalues() {
	//document.getElementById("grid").value = "";
deletion();
	 val = 0;
	for ( var i = 0; i < senio_no.length; i++) {

		if (senio_no[i] == document.getElementById("senio_no").value) {
			val = i;
		}
	}

	senio_no.splice(val, 1);
	seni_sub__no.splice(val, 1);
	turn_no.splice(val, 1);
	com_rot_code.splice(val, 1);
	dob_cadre.splice(val, 1);
	date_relg.splice(val, 1);
	empid.splice(val, 1);
	ref_in_posted.splice(val, 1);
	empnames.splice(val, 1);
	grid();
	clrs1();
	//deletion();
	
}
function grid() {
//alert("hadi")
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
		td.innerHTML = turn_no[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = com_rot_code[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = dob_cadre[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = date_relg[i];
		tr.appendChild(td);
		
		var  td = document.createElement("td");
		td.innerHTML = ref_in_posted[i];
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
	document.getElementById("turn_no").value = "";
	document.getElementById("ref_in_wh_posted").value="";
	document.getElementById("com_rot_code").selectedIndex = 0;
	document.getElementById("dob_cadre").value="";
	document.getElementById("date_relg").value="";
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
function loadvalu(i) {
	//loadMon1();
	//document.getElementById("grid").innerHTML="";
	z = i;
	document.getElementById("emp_id").value = empid[i];
	document.getElementById("senio_no").value = senio_no[i];
	if(seni_sub__no[i]!==""){
		document.getElementById("seni_sub").checked=true;
	}
	document.getElementById("seni_sub__no").value = seni_sub__no[i];
	document.getElementById("turn_no").value = turn_no[i];
	document.getElementById("com_rot_code").value = com_rot_code[i];
	document.getElementById("dob_cadre").value=dob_cadre[i];
	document.getElementById("date_relg").value=date_relg[i];
	document.getElementById("remarks").value = remarks[i];
	document.getElementById("ref_in_wh_posted").value=ref_in_posted[i];


	document.getElementById('upd').style.display = "";
	document.getElementById('del').style.display = "";
	document.getElementById('add1').style.display = 'none';
	doFunction('loademp','null');
}
function insert()
{
	
	var val=nullcheck();
	if(val==true)
	{
	var req= getTransport();
	var cadre_id = document.getElementById("cadre_id").value;
	var proc_no = document.getElementById("proc_no").value;
	//var emp_id = document.getElementById("emp_id").value;
	
//alert(emp_id)
	var surl = "";
	for ( var i = 0; i < senio_no.length; i++) {

		
		
		
		var employee_id=empid[i];
		var senio = senio_no[i];
		var seni_sub = seni_sub__no[i];
		if(turn_no[i]=="")
		{
			turn_no[i]=0;
		}
		var turn = turn_no[i];
		
		var com_rot=com_rot_code[i];
		var dob_ca=dob_cadre[i];
		var date_re = date_relg[i];
		var remar=remarks[i];
		var ref_in_wh_post=ref_in_posted[i];
		surl = surl + "&senio=" + senio + "&seni_sub=" + seni_sub + "&turn="
				+ turn + "&com_rot=" + com_rot+"&dob_ca="+dob_ca+"&date_re="+date_re+"&remar="+remar+"&employee_id="+employee_id+"&ref_in_wh_post="+ref_in_wh_post;

	}

	url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=Addvalues&cadre_id="
			+ cadre_id +"&proc_no="+proc_no+surl;
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
function updation ()
{

	
	var val=nullcheck();
	if(val==true)
	{
	var req= getTransport();
	var cadre_id = document.getElementById("cadre_id").value;
	var proc_no = document.getElementById("proc_no").value;
	//var emp_id = document.getElementById("emp_id").value;
	
//alert(emp_id)
	var surl = "";
	for ( var i = 0; i < senio_no.length; i++) {

		
		
		
		var employee_id=empid[i];
		var senio = senio_no[i];
		var seni_sub = seni_sub__no[i];
		
		if(turn_no[i]=="")
		{
			turn_no[i]=0;
		}
		var turn = turn_no[i];
		var com_rot=com_rot_code[i];
		var dob_ca=dob_cadre[i];
		var date_re = date_relg[i];
		var ref_in_wh_post=ref_in_posted[i];
		var remar=remarks[i];
		surl = surl + "&senio=" + senio + "&seni_sub=" + seni_sub + "&turn="
				+ turn + "&com_rot=" + com_rot+"&dob_ca="+dob_ca+"&date_re="+date_re+"&remar="+remar+"&employee_id="+employee_id+"&ref_in_wh_post="+ref_in_wh_post;;

	}

	url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=updation&cadre_id="
			+ cadre_id +"&proc_no="+proc_no+surl;
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
function validation()
{


	
	var val=nullcheck();
	if(val==true)
	{
	var req= getTransport();
	var cadre_id = document.getElementById("cadre_id").value;
	var proc_no = document.getElementById("proc_no").value;
	//var emp_id = document.getElementById("emp_id").value;
	
//alert(emp_id)
	var surl = "";
	for ( var i = 0; i < senio_no.length; i++) {

		
		
		
		var employee_id=empid[i];
		var senio = senio_no[i];
		var seni_sub = seni_sub__no[i];
		if(turn_no[i]=="")
		{
			turn_no[i]=0;
		}
		var turn = turn_no[i];
		var com_rot=com_rot_code[i];
		var dob_ca=dob_cadre[i];
		var date_re = date_relg[i];
		var remar=remarks[i];
		var ref_in_wh_post=ref_in_posted[i];
		surl = surl + "&senio=" + senio + "&seni_sub=" + seni_sub + "&turn="
				+ turn + "&com_rot=" + com_rot+"&dob_ca="+dob_ca+"&date_re="+date_re+"&remar="+remar+"&employee_id="+employee_id+"&ref_in_wh_post="+ref_in_wh_post;;

	}

	url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=validation&cadre_id="
			+ cadre_id +"&proc_no="+proc_no+surl;
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
function deletion()
{


	
	var val=nullcheck();
	if(val==true)
	{
	var req= getTransport();
	var cadre_id = document.getElementById("cadre_id").value;
	var emp_id = document.getElementById("emp_id").value;
	var proc_no = document.getElementById("proc_no").value;
	
	
//alert(emp_id)
	
	url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=deletion&cadre_id="
			+ cadre_id +"&emp_id="+emp_id+"&proc_no="+proc_no;
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
           // stopwaiting(document.Seniority_Proceeding_list);
            
             baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // service=baseResponse;
         // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          //  alert('test'+tagcommand);
            var Command=tagcommand.firstChild.nodeValue;
             if(Command=="Addvalues")
            {
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
            	  if(flag=="success")
                	{
            		  alert("Record Inserted Successfully !");
            		  
            		  document.getElementById("grid").innerHTML="";
            		  document.getElementById('add1').disabled = false;
      				document.getElementById('upd').style.display = 'none';
      				document.getElementById('CmdUpdate').style.display = 'none';
      				document.getElementById('Cmdvalidate').style.display = 'none';
      				document.getElementById('del').style.display = 'none';
      				document.getElementById('del').style.display = 'none';
      				document.getElementById("cadre_id").selectedIndex=0;
      				document.getElementById("proc_no").selectedIndex=0;
      				document.getElementById("CmdAdd").style.display="";
            		  //getList();
                	}
            }
             if(Command=="updation")
             {
             	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
             	  if(flag=="success")
                 	{
             		  alert("Record updated Successfully !");
             		 document.getElementById("grid").innerHTML="";
             		 //getList();

         	    	document.getElementById('add1').disabled = false;
         	    	//document.getElementById('ins').disabled = true;
     				//document.getElementById('add1').disabled = false;
     				document.getElementById('upd').style.display = 'none';
     				//document.getElementById('Cmdvalidate').style.display = 'none';
         	    	document.getElementById("CmdUpdate").style.display="none";
         	    	document.getElementById('del').style.display = 'none';
         	    	document.getElementById("Cmdvalidate").style.display="none";
         			document.getElementById("CmdAdd").style.display="";
         			document.getElementById("cadre_id").selectedIndex=0;
      				document.getElementById("proc_no").selectedIndex=0;
         			//document.getElementById("audit_id").selectedIndex=0;
                 	}
             }
             if(Command=="validation")
            	 {
             	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
             	  if(flag=="success")
                 	{
             		  alert("Record validated Successfully !");
             		 document.getElementById("grid").innerHTML="";
             		 //getList();

         	    	document.getElementById('add1').disabled = false;
         	    	//document.getElementById('ins').disabled = true;
     				//document.getElementById('add1').disabled = false;
     				document.getElementById('upd').style.display = 'none';
     				//document.getElementById('Cmdvalidate').style.display = 'none';
         	    	document.getElementById("CmdUpdate").style.display="none";
         	    	document.getElementById('del').style.display = 'none';
         	    	document.getElementById("Cmdvalidate").style.display="none";
         			document.getElementById("CmdAdd").style.display="";
         			//document.getElementById("audit_id").selectedIndex=0;
                 	}
             
            	 
            	 }
             if(Command=="deletion")
        	 {
         	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
             
         	  if(flag=="success")
             	{
         		  //alert("Record deleted Successfully !");
         		 document.getElementById("grid").innerHTML="";
         		 grid() ;

     	    	document.getElementById('add1').disabled = false;
     	    	//document.getElementById('ins').disabled = true;
 				//document.getElementById('add1').disabled = false;
 				document.getElementById('upd').style.display = 'none';
 				//document.getElementById('Cmdvalidate').style.display = 'none';
     	    	document.getElementById("CmdUpdate").style.display="none";
     	    	document.getElementById('del').style.display = 'none';
     	    	document.getElementById("Cmdvalidate").style.display="none";
     			document.getElementById("CmdAdd").style.display="";
     			//document.getElementById("audit_id").selectedIndex=0;
     			//document.Seniority_Proceeding_list.submit();
     			clrs();
             	}
         
        	 
        	 }
        }
    }
    
}
function clrsarray()
{
	val = 0;
	for ( var i = 0; i < senio_no.length; i++) {

		if (senio_no[i] == document.getElementById("senio_no").value) {
			val = i;
		}
	}

	senio_no.splice(val, 1);
	seni_sub__no.splice(val, 1);
	turn_no.splice(val, 1);
	com_rot_code.splice(val, 1);
	dob_cadre.splice(val, 1);
	date_relg.splice(val, 1);
	empid.splice(val, 1);
	ref_in_posted.splice(val, 1);
	empnames.splice(val, 1);
	document.getElementById("emp_id").value="";
	document.getElementById("emp_name").value="";
	document.getElementById("desig_id").value="";
	document.getElementById("dob").value="";
    document.getElementById("emp_id").focus();
	 
}
function getList()
{
	
	
	 var tlist = document.getElementById("grid");
	    try {
	        tlist.innerHTML = "";
	    } catch (e) {
	        tlist.innerText = "";
	    }
	
	    clearArray();
   
    var req= getTransport();
	var cadre_id=document.getElementById("cadre_id").value;
	var proc_no=document.getElementById("proc_no").value;
	var url="../../../../../Seniority_list_updation_jdo_je_ahg_ja?Command=getList&proc_no="+proc_no+"&cadre_id="+cadre_id;
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
		
		  baseResponse=req.responseXML.getElementsByTagName("response")[0];
		 
         
           var tagcommand=baseResponse.getElementsByTagName("command")[0];
         //  alert('test'+tagcommand);
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
	var SENIORITY_SUB_NO = baseResponse.getElementsByTagName("SENIORITY_SUB_NO")[i].firstChild.nodeValue;
	var TURN_NO = baseResponse.getElementsByTagName("TURN_NO")[i].firstChild.nodeValue;	
	var COMMUNITY_ROTATION_CODE = baseResponse.getElementsByTagName("COMMUNITY_ROTATION_CODE")[i].firstChild.nodeValue;
	var COMMUNITY_NAME = baseResponse.getElementsByTagName("COMMUNITY_NAME")[i].firstChild.nodeValue;
	var DATE_OF_JOINING = baseResponse.getElementsByTagName("DATE_OF_JOINING")[i].firstChild.nodeValue;
	var DATE_OF_REGULARISATION = baseResponse.getElementsByTagName("DATE_OF_REGULARISATION")[i].firstChild.nodeValue;
	var REMARKS = baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
	var PROCESS_FLOW_ID = baseResponse.getElementsByTagName("PROCESS_FLOW_ID")[i].firstChild.nodeValue;
	var REFERENCE_IN_WHICH_POSTED= baseResponse.getElementsByTagName("REFERENCE_IN_WHICH_POSTED")[i].firstChild.nodeValue;
	//var empid_name=EMPLOYEE_ID+ "-" +empname;
	
	
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
	 if(SENIORITY_SUB_NO==null || SENIORITY_SUB_NO=="null")
	 {
		 SENIORITY_SUB_NO="";
	 }
	 if(TURN_NO==null || TURN_NO=="null")
	 {
		 TURN_NO="";
	 }
	 if(COMMUNITY_ROTATION_CODE==null || COMMUNITY_ROTATION_CODE=="null")
	 {
		 COMMUNITY_ROTATION_CODE="";
	 }
	 if(COMMUNITY_NAME==null || COMMUNITY_NAME=="null")
	 {
		 COMMUNITY_NAME="";
	 }
	 if(DATE_OF_JOINING==null || DATE_OF_JOINING=="null")
	 {
		 DATE_OF_JOINING="";
	 }
	 if(DATE_OF_REGULARISATION==null || DATE_OF_REGULARISATION=="null")
	 {
		 DATE_OF_REGULARISATION="";
	 }if(REMARKS==null || REMARKS=="null")
	 {
		 REMARKS="";
	 }
	 if(REFERENCE_IN_WHICH_POSTED==null || REFERENCE_IN_WHICH_POSTED=="null")
	 {
		 REFERENCE_IN_WHICH_POSTED="";
	 }
	if(TURN_NO=="0")
	{
		TURN_NO="";
	}
	
	
	  empid[i]=EMPLOYEE_ID;
	
	  empnames[i]=empname;
	  senio_no[i]=SENIORITY_NO;
	  seni_sub__no[i]=SENIORITY_SUB_NO;
	  turn_no[i]=TURN_NO;
	  com_rot_code[i]=COMMUNITY_ROTATION_CODE;
	  dob_cadre[i]=DATE_OF_JOINING;
	  date_relg[i]=DATE_OF_REGULARISATION;
	  remarks[i]=REMARKS;
	  ref_in_posted[i]=REFERENCE_IN_WHICH_POSTED;
	  
	  
	 var tr = document.createElement("TR");
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
     var SENIORITY_SUB_NO = document.createTextNode(SENIORITY_SUB_NO);
     td.appendChild(SENIORITY_SUB_NO);
     tr.appendChild(td);

     var td = document.createElement("TD");
     var TURN_NO = document.createTextNode(TURN_NO);
     //td.setAttribute("style", "display:none");
     td.appendChild(TURN_NO);
      tr.appendChild(td);
      
      var td = document.createElement("TD");
      var COMMUNITY_NAME = document.createTextNode(COMMUNITY_NAME);
      //td.setAttribute("style", "display:none");
      td.appendChild(COMMUNITY_NAME);
       tr.appendChild(td);

    
      var td = document.createElement("TD");
      var DATE_OF_JOINING = document.createTextNode(DATE_OF_JOINING);
       td.appendChild(DATE_OF_JOINING);
       tr.appendChild(td);
       
       var td = document.createElement("TD");
       var DATE_OF_REGULARISATION = document.createTextNode(DATE_OF_REGULARISATION);
        td.appendChild(DATE_OF_REGULARISATION);
        tr.appendChild(td);
        
        var td = document.createElement("TD");
        var REFERENCE_IN_WHICH_POSTED = document.createTextNode(REFERENCE_IN_WHICH_POSTED);
         td.appendChild(REFERENCE_IN_WHICH_POSTED);
         tr.appendChild(td);
         
         
        
        var td = document.createElement("TD");
        var REMARKS = document.createTextNode(REMARKS);
         td.appendChild(REMARKS);
         tr.appendChild(td);
         
        
            
           
        tlist.appendChild(tr);

        seq++;
        
        
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
     iframe.innerHTML="<tr><td align=center colspan=10>There is No Data to Display</td></tr>";
     //iframe.style.color="#0000FF";
    	 }
    }
    }
}
function loadvalue(i)
{
	 //alert("Member_desig--->"+Auditor_type_id[i]);
	z = i;
	

	  
	  
		document.getElementById("senio_no").value=senio_no[i];
		if(seni_sub__no[i]!==""){
			document.getElementById("seni_sub").checked=true;
		}
		
		document.getElementById("seni_sub__no").value=seni_sub__no[i];
		document.getElementById("turn_no").value=turn_no[i];
		//document.getElementById("no_of_paras").value=com_rot_code[i];
		document.getElementById("com_rot_code").value=com_rot_code[i];
		document.getElementById("dob_cadre").value=dob_cadre[i];
		document.getElementById("date_relg").value= date_relg[i];
		document.getElementById("ref_in_wh_posted").value= ref_in_posted[i];
		document.getElementById("remarks").value= remarks[i];
		document.getElementById("emp_id").value=  empid[i];
		//document.getElementById("stock_rep").value=  stock[i];
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
		document.getElementById("CmdUpdate").style.display="";
		//document.getElementById("Cmdvalidate").style.display="";
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
	
	
	else if(document.getElementById("dob_cadre").value=="")
	{
	alert("Please Enter Date Of joining in the Cadre");
	document.getElementById("dob_cadre").focus();
	return false;
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
	 else if(document.getElementById("proc_no").selectedIndex==0)
		{
			alert("please select Select Proceeding No");
			return false;
		}
	
	
	return true;
}
function clearall()
{
    document.getElementById("cadre_id").selectedIndex=0;
    document.getElementById("proc_no").selectedIndex=0;
    document.getElementById("emp_id").value="";
    document.getElementById("emp_name").value="";
    document.getElementById("desig_id").value="";
    document.getElementById("dob").value="";
    document.getElementById("senio_no").value="";
    document.getElementById("seni_sub__no").value="";
    document.getElementById("turn_no").value="";
    document.getElementById("com_rot_code").selectedIndex=0;
    document.getElementById("dob_cadre").value="";
    document.getElementById("date_relg").value="";
    document.getElementById("remarks").value="";
    document.getElementById("grid").innerHTML="";
}
function clrs()
{
	document.getElementById("emp_id").value="";
    document.getElementById("emp_name").value="";
    document.getElementById("desig_id").value="";
    document.getElementById("dob").value="";
    document.getElementById("senio_no").value="";
    document.getElementById("seni_sub__no").value="";
    document.getElementById("turn_no").value="";
    document.getElementById("com_rot_code").selectedIndex=0;
    document.getElementById("dob_cadre").value="";
    document.getElementById("date_relg").value="";
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
	else if(fromDay <= toDay)
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
function clrs1()
{
	// document.getElementById("cadre_id").selectedIndex=0;
	   // document.getElementById("proc_no").selectedIndex=0;
	    document.getElementById("emp_id").value="";
	    document.getElementById("emp_name").value="";
	    document.getElementById("desig_id").value="";
	    document.getElementById("dob").value="";
	    document.getElementById("senio_no").value="";
	    document.getElementById("seni_sub__no").value="";
	    document.getElementById("turn_no").value="";
	    document.getElementById("com_rot_code").selectedIndex=0;
	    document.getElementById("dob_cadre").value="";
	    document.getElementById("date_relg").value="";
	    document.getElementById("remarks").value="";
}