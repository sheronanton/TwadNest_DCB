//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var des_id;
var des_text;
var winemp=null;
var my_window;
var jobflag;
var desid;
var GWING=0;
var  GCHECK='no';
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
     //   alert('test');     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function servicepopupSR()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
     //   alert('test');     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpPopupSRCtrlOffice.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmCurrentPosting.txtEmployeeid.value=emp;
//document.frmCurrentPosting.reset();
//clr();
doFunction('loadempedit','null');

}

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    jobflag=true;
    
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
    if(jobflag==true)
    {
      if (winjob && winjob.open && !winjob.closed)
      {
       
         winjob.officeSelection(true,true,true,false);
       }
    }
    else
    {
             if (winjob1 && winjob1.open && !winjob1.closed)
              {
               
                 winjob1.officeSelection(false,false,false,true);
               }
    }
}

function doParentJob(jobid,deptid)
{

    if(jobflag==true)
    {
        document.frmCurrentPosting.txtDept_Id_work.value=deptid;
        document.frmCurrentPosting.txtOffice_Id.value=jobid ;
        doFunction('Load',true);
        return true
    }
    else
    {
        document.frmCurrentPosting.txtDept_Id.value=deptid;
        document.frmCurrentPosting.txtOthOffice_Id.value= jobid ;
        doFunction('Load',false);
        return true
    }

}

//////////////   FOR JOB POPUP WINDOW1 //////////////////////
var winjob1;

function jobpopup1()
{
    jobflag=false;
    //alert('hsi');
    if (winjob1 && winjob1.open && !winjob1.closed) 
    {
       winjob1.resizeTo(500,600);
       winjob1.moveTo(200,200); 
       winjob1.focus();
       return;

    }
    else
    {
        winjob1=null
    }
        
    winjob1= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob1.moveTo(200,200);  
 
    winjob1.focus();
    
    
}



 /*function doParentJob(jobid,deptid)
{

    document.frmCurrentPosting.txtOffice_Id.value=jobid;
    document.frmCurrentPosting.txtDept_Id.value=deptid;
    doFunction('Load','null');
    return true

}*/

window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winjob1 && winjob1.open && !winjob1.closed) winjob1.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

}

///////////////////////////////////////////////////////////////////////////////////


//************list all *************************
 function popwindow()
    {
        if((document.frmCurrentPosting.txtEmployeeid.value==null)||(document.frmCurrentPosting.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.frmCurrentPosting.txtEmployeeid.focus();
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
        var str="frmCurrentPosting_ListAll.jsp?id="+document.frmCurrentPosting.txtEmployeeid.value;
        my_window= window.open(str,"mywindow1","status=1,height=400,width="+screen.availWidth+",resizable=yes, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }

var req=false;
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
 //alert(req);


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



function checkdate()
{
//alert('check');
        var fromdt=document.frmCurrentPosting.txtDateFrom.value;
        var todt=document.frmCurrentPosting.txtDateTo.value;
        
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
            //document.frmCurrentPosting.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.frmCurrentPosting.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.frmCurrentPosting.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}


function notNull(p)
{


    if((document.frmCurrentPosting.txtEmployeeid.value==null)||(document.frmCurrentPosting.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.frmCurrentPosting.txtEmployee.value="";
        document.frmCurrentPosting.txtGpf.value="";
        document.frmCurrentPosting.txtEmployeeid.value="";
        
        //clr();
        document.frmCurrentPosting.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.frmCurrentPosting.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.frmCurrentPosting.txtEmployeeid.value="";
        document.frmCurrentPosting.txtEmployeeid.focus();
        return false;
    }
    
    if(p!=null)
    {
      
        
        if((document.frmCurrentPosting.cmbDesignation.value==0)||(document.frmCurrentPosting.cmbDesignation.value.length==0))
        {
            alert("Seelct a Designation");
            if((document.frmCurrentPosting.cmbsgroup.value=="0")||(document.frmCurrentPosting.cmbsgroup.value.length==0))
            {
                document.frmCurrentPosting.cmbsgroup.focus();
            }
            else
            {
                document.frmCurrentPosting.cmbDesignation.focus();
            }
            return false;
        }
        
         if((document.frmCurrentPosting.cmbstatus.value=="")||(document.frmCurrentPosting.cmbstatus.value.length==0))
        {
            alert("Seelct a Employee Status ");
            document.frmCurrentPosting.cmbstatus.focus();
            return false;
        }
        else
        {
                var type=document.frmCurrentPosting.cmbstatus.options[document.frmCurrentPosting.cmbstatus.selectedIndex].value;
                if(type!="")
                {
                    //alert(type);
                    
                    if(type=='DPN')
                    {
                          // alert('deputation');
                            if((document.frmCurrentPosting.txtDept_Id.value=='')||(document.frmCurrentPosting.txtDept_Id.value.length==0))
                            {
                                alert("Select the Department");
                                document.frmCurrentPosting.txtDept_Id.focus();
                                return false;
                            }
                            else if((document.frmCurrentPosting.txtOthOffice_Id.value==null)||(document.frmCurrentPosting.txtOthOffice_Id.value.length==0))
                            {
                                alert("Select Office Id");
                                document.frmCurrentPosting.txtOthOffice_Id.focus();
                                return false;
                            }
                            else if((document.frmCurrentPosting.txtDepDateFrom.value==null)||(document.frmCurrentPosting.txtDepDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtDepDateFrom.focus();
                                return false;
                            }
                            return true;
                    }
                    else  if(type=='WKG' )
                    {
                            //alert('working');
                            if((document.frmCurrentPosting.txtOffice_Id.value==null)||(document.frmCurrentPosting.txtOffice_Id.value.length==0))
                            {
                                alert("Select Office Id");
                                document.frmCurrentPosting.txtOffice_Id.focus();
                                return false;
                            }
                            else if((document.frmCurrentPosting.txtOffice_Id.value<5000))
                            {
                                alert("Office Id should be greater than or equal to 5000");
                                offclr();
                                document.frmCurrentPosting.txtOffice_Id.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtWorkDateFrom.value==null)||(document.frmCurrentPosting.txtWorkDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                //offclr();
                                document.frmCurrentPosting.txtWorkDateFrom.focus();
                                return false;
                            }
                            
                          /*  if( GCHECK=='yes')
                            {
                                 if((document.frmCurrentPosting.cmbWing.value==null)||(document.frmCurrentPosting.cmbWing.value==0))
                                {
                                    alert("Select a wing ");
                                    //offclr();
                                    document.frmCurrentPosting.cmbWing.focus();
                                    return false;
                                }
                            
                            }*/
                            return true;
                    }
                    
                    
                     else  if(type=='STU' )
                    {
                            //alert('working');
                            if((document.frmCurrentPosting.txtInst_Name.value==null)||(document.frmCurrentPosting.txtInst_Name.value.length==0))
                            {
                                alert("Enter Institution Name");
                                document.frmCurrentPosting.txtInst_Name.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtInst_Location.value==null)||(document.frmCurrentPosting.txtInst_Location.value.length==0))
                            {
                                alert("Enter the Location ");
                                document.frmCurrentPosting.txtInst_Location.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtCourse_Name.value==null)||(document.frmCurrentPosting.txtCourse_Name.value.length==0))
                            {
                                alert("Enter the Course Name ");
                                document.frmCurrentPosting.txtCourse_Name.focus();
                                return false;
                            }
                              if((document.frmCurrentPosting.txtStudyDateFrom.value==null)||(document.frmCurrentPosting.txtStudyDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtStudyDateFrom.focus();
                                return false;
                            }
                            return true;
                    }
                    
                    
                     else  if(type=='LLV')
                    {
                           if((document.frmCurrentPosting.cmbleavetype.value=='')||(document.frmCurrentPosting.cmbleavetype.value.length==0))
                            {
                                alert("Select the leave type");
                                document.frmCurrentPosting.cmbleavetype.focus();
                                return false;
                            }
                            
                            if((document.frmCurrentPosting.txtDateFrom.value==null)||(document.frmCurrentPosting.txtDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtDateFrom.focus();
                                return false;
                            }
                         /*    if((document.frmCurrentPosting.txtDateTo.value==null)||(document.frmCurrentPosting.txtDateTo.value.length==0))
                            {
                                alert("Enter a value for Date To");
                                document.frmCurrentPosting.txtDateTo.focus();
                                return false;
                            }
                             var c=checkdate();
                               if(c==false)
                               {
                                    document.frmCurrentPosting.txtDateFrom.focus();
                                    return false;
                                }
                                return true;*/
                    }
                     else  if(type=='SUS')
                    {
                            if((document.frmCurrentPosting.txtSusFrom.value==null)||(document.frmCurrentPosting.txtSusFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtSusFrom.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtsusreson.value==null)||(document.frmCurrentPosting.txtsusreson.value.length==0))
                            {
                                alert("Enter the Reason for suspension ");
                                document.frmCurrentPosting.txtsusreson.focus();
                                return false;
                            }
                            return true;
                            
                    }
                      else  if(type=='ABS')
                    {
                            if((document.frmCurrentPosting.txtAbsFrom.value==null)||(document.frmCurrentPosting.txtAbsFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtAbsFrom.focus();
                                return false;
                            }
                            return true;
                    }
                    
                    
                    
                     else  if(type=='TRT')
                    {
                            if((document.frmCurrentPosting.txtTrtFrom.value==null)||(document.frmCurrentPosting.txtTrtFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtTrtFrom.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtTrtreson.value==null)||(document.frmCurrentPosting.txtTrtreson.value.length==0))
                            {
                                alert("Enter the Reason for Transit ");
                                document.frmCurrentPosting.txtTrtreson.focus();
                                return false;
                            }
                            return true;
                            
                    }
                     else  if(type=='UAL')
                    {
                            if((document.frmCurrentPosting.txtUalFrom.value==null)||(document.frmCurrentPosting.txtUalFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtUalFrom.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtUalreson.value==null)||(document.frmCurrentPosting.txtUalreson.value.length==0))
                            {
                                alert("Enter the Reason for Unautherized Leave ");
                                document.frmCurrentPosting.txtUalreson.focus();
                                return false;
                            }
                            return true;
                            
                    }
                     else  if(type=='CMW')
                    {
                            if((document.frmCurrentPosting.txtCmwFrom.value==null)||(document.frmCurrentPosting.txtCmwFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtCmwFrom.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtCmwreson.value==null)||(document.frmCurrentPosting.txtCmwreson.value.length==0))
                            {
                                alert("Enter the Reason for Compulsary Wait ");
                                document.frmCurrentPosting.txtCmwreson.focus();
                                return false;
                            }
                            return true;
                            
                    }
                        
                return true;;
                
                }
        }
        
        

    }
    
    return true;

}


function doFunction(Command,param)
{
   
   
	if(Command=='load_grid')
		{
		 
	        	
	                startwaiting(document.frmCurrentPosting) ;
	                service=null;
	      
	                var url="../../../../../UpdateAdditionalCharge?Command=load_grid";
	                req.open("GET",url,true);
	                req.onreadystatechange=handleResponse;
	                if(window.XMLHttpRequest)
	                        req.send(null);
	                else req.send();  
	           
		}
    
    var empid=document.frmCurrentPosting.txtEmployeeid.value;
    
    if(Command=='Validatess')
	{
	var empid=document.getElementById("txtEmployeeid").value;
	 alert(empid);
	var slno=document.getElementById("slno").value;
	
	var cmbsgroup1=document.getElementById("cmbsgroup1").value;
	var service=document.getElementById("cmbsgroup1").options[cmbsgroup1].text;
	var cmbDesignation1=des_id;
	//alert(cmbDesignation1);
	
	var grade;
 	
	var len=document.frmCurrentPosting.Office_Grade1.length;
	for(var i=0;i<len;i++)
		{
			if(document.frmCurrentPosting.Office_Grade1[i].checked)
				{
					grade=document.frmCurrentPosting.Office_Grade1[i].value;
				}
		}
	
	var txtOffice_Id1=document.getElementById("txtOffice_Id1").value;
	
	
	var txtWorkDateFrom1=document.getElementById("txtWorkDateFrom1").value;
	var len=document.frmCurrentPosting.optWorkDateFrom1.length;
	var date_effective_session;
	for(var i=0;i<len;i++)
		{
			if(document.frmCurrentPosting.optWorkDateFrom1[i].checked)
				{
				date_effective_session=document.frmCurrentPosting.optWorkDateFrom1[i].value;
				}
		}
	var len=document.frmCurrentPosting.AddlCharge.length;
	var charge_type;
	for(var i=0;i<len;i++)
		{
			if(document.frmCurrentPosting.AddlCharge[i].checked)
				{
				charge_type=document.frmCurrentPosting.AddlCharge[i].value;
				}
		}
	var remarks=document.getElementById("txtworkremark1").value;
	
//    var check=notNull('Validate');
//   // alert(check);
//    if(check)
//    {
	//alert(empid+"=="+cmbDesignation1+"=="+slno);
           statuswaiting(document.frmCurrentPosting) ;
       
document.frmCurrentPosting.action="../../../../../CurrentPosting_AdditionalCharge?Command=Validatess&txtEmployeeid="+empid+"&slno="+slno+"&service_group="+cmbsgroup1+"&designation="+cmbDesignation1+"&grade="+grade+"&office_id="+txtOffice_Id1+"&date_effective="+txtWorkDateFrom1+"&date_effective_session="+date_effective_session+"&charge_type="+charge_type+"&remarks="+remarks;
                     document.frmCurrentPosting.method="POST";
                     document.frmCurrentPosting.submit();
                   return;
	}
   
    if(Command=='loadempview')
    {
      
      
       var array=new Array();
       array=param.split(",");
       var empid=array[0];
       var slno=array[1];
                startwaiting(document.frmCurrentPosting) ;
                service=null;
      
                var url="../../../../../RevokeAdditionalCharge?Command=loadempview&txtEmployeeid="+empid+"&slno="+slno;
              //alert(url);
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
              
    
    }
    
    if(Command=='loadempview1')
    {
      
       
                startwaiting(document.frmCurrentPosting) ;
                service=null;
      
               var empid=document.getElementById("txtEmployeeid").value;
                var url="../../../../../UpdateAdditionalCharge?Command=loadempview&txtEmployeeid="+empid;
         
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
              
    
    }
    
    if(Command=='loadempedit')
    {
       
        var check=notNull(null);
        if(check )
        {
                startwaiting(document.frmCurrentPosting) ;
                service=null;
      
                //alert('load emp');
               var offid=document.frmCurrentPosting.cmbAddlChargeOffId.value;
               //alert("office id is:"+offid);
                var url="../../../../../CurrentPosting_AdditionalCharge?Command=loadempedit&txtEmployeeid="+empid+"&offid="+offid;
             
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
        }        
    
    }
    
    else if(Command=="Load1")
    {
          var  Office_Id;
          var   Dept_Id;
          //alert('load1')
      
         if(param==true)
        {
            jobflag=true;
            Dept_Id=document.frmCurrentPosting.txtDept_Id_work1.value;
           Office_Id =document.frmCurrentPosting.txtOffice_Id1.value;
            
        }
        else if(param==false)
        {
             jobflag=false;
           Dept_Id= document.frmCurrentPosting.txtDept_Id1.value;
           Office_Id =document.frmCurrentPosting.txtOthOffice_Id1.value;
        }
       // alert(Office_Id +'   '+Dept_Id);
       
       var dsg=document.frmCurrentPosting.cmbDesignation1.options[document.frmCurrentPosting.cmbDesignation1.selectedIndex].value;
       var text=document.frmCurrentPosting.cmbDesignation1.options[0].text;
       
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id+"&disigid="+dsg;
       var req1=getTransport();
        req1.open("GET",url,true);        
        req1.onreadystatechange=function()
        {
        processResponse1(req1);
        }
         if(window.XMLHttpRequest)
                        req1.send(null);
                else req1.send();
    }
    
    else if(Command=='loadOffices')
    {
         var url="../../../../../CurrentPosting_AdditionalCharge?Command=loadOffices&txtEmployeeid="+empid;
               // var req=getTransport();
              // alert(req);
              //alert(url);
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
    }
    else if(Command=='Revoke')
    {
  
      
               statuswaiting(document.frmCurrentPosting) ;
                 var empid=document.frmCurrentPosting.txtEmployeeid.value;
                 
                 var from_date=document.frmCurrentPosting.txtWorkDateFrom1.value;
                 var to_date=document.frmCurrentPosting.txtWorkDateFrom2ss.value;
                 
                 var from_array=new Array();
                 var to_array=new Array();
                 
                 from_array=from_date.split("/");
                 to_array=to_date.split("/");
                 
                 var from_year=from_array[2];
                // alert(from_year);
                 var from_month=from_array[1];
                // alert(from_month);
                 var from_day=from_array[0];
                // alert(from_day);
                 var to_year=to_array[2];
                // alert(to_year);
                 var to_month=to_array[1];
                // alert(to_month);
                 var to_day=to_array[0];
                // alert(to_day);
                 
                
              // alert("office id is:"+offid);
                 if(to_date=="" || to_date.length=="0")
                	 {
                	 alert('please enter the To_date ');
                	 document.frmCurrentPosting.txtWorkDateFrom2ss.value="";
                	 return false;
                	 }
                 else
                	 {
		                	 if(to_year<from_year)
		                	 {
		                	 	alert('To_date should not be less than From date');
		                	 	document.frmCurrentPosting.txtWorkDateFrom2ss.value="";
		                	 }
		                	 else if(to_year==from_year)
		                	 {
			                	 if(to_month<from_month)
			                		 {
			                		 alert('To_date should not be less than From date');
			                		 document.frmCurrentPosting.txtWorkDateFrom2ss.value="";
			                		 }
			                	 else if(to_month==from_month)
			                		 {
				                		 if(to_day<=from_day)
				                    	 {
				                			 alert('To_date should not be less than From date');
				                			 document.frmCurrentPosting.txtWorkDateFrom2ss.value="";
				                    	 }
				                		 else
					                	 {
					                	// alert("()())");
					                	 	  document.frmCurrentPosting.action="../../../../../CurrentPosting_AdditionalCharge?Command=Revoke&txtEmployeeid="+empid;
					                    // alert(document.frmCurrentPosting.action);
					                	 	  document.frmCurrentPosting.method="POST";
					                          document.frmCurrentPosting.submit();
					                          return; 
					                	 }
			                		 }
			                	 else
			                	 {
			                		 document.frmCurrentPosting.action="../../../../../CurrentPosting_AdditionalCharge?Command=Revoke&txtEmployeeid="+empid;
					                    // alert(document.frmCurrentPosting.action);
					                	 	  document.frmCurrentPosting.method="POST";
					                          document.frmCurrentPosting.submit();
					                          return; 
			                	 }
		                	 }
			                 else
			                	 {
			                	 //alert("()())");
			                	 	  document.frmCurrentPosting.action="../../../../../CurrentPosting_AdditionalCharge?Command=Revoke&txtEmployeeid="+empid;
			                    // alert(document.frmCurrentPosting.action);
			                	 	  document.frmCurrentPosting.method="POST";
			                          document.frmCurrentPosting.submit();
			                          return; 
			                	 }
                	 }
                   
       
    }
    else if(Command=='Update')
    {
    	var empid=document.getElementById("txtEmployeeid").value;
 
    	var slno=document.getElementById("slno").value;
    	
    	var cmbsgroup1=document.getElementById("cmbsgroup1").value;
    	var service=document.getElementById("cmbsgroup1").options[cmbsgroup1].text;
    	var cmbDesignation1=des_id;
    	//alert(cmbDesignation1);
    	
    	var grade;
     	
    	var len=document.frmCurrentPosting.Office_Grade1.length;
    	for(var i=0;i<len;i++)
    		{
    			if(document.frmCurrentPosting.Office_Grade1[i].checked)
    				{
    					grade=document.frmCurrentPosting.Office_Grade1[i].value;
    				}
    		}
    	
    	var txtOffice_Id1=document.getElementById("txtOffice_Id1").value;
    	
    	
    	var txtWorkDateFrom1=document.getElementById("txtWorkDateFrom1").value;
    	var len=document.frmCurrentPosting.optWorkDateFrom1.length;
    	var date_effective_session;
    	for(var i=0;i<len;i++)
    		{
    			if(document.frmCurrentPosting.optWorkDateFrom1[i].checked)
    				{
    				date_effective_session=document.frmCurrentPosting.optWorkDateFrom1[i].value;
    				}
    		}
    	var len=document.frmCurrentPosting.AddlCharge.length;
    	var charge_type;
    	for(var i=0;i<len;i++)
    		{
    			if(document.frmCurrentPosting.AddlCharge[i].checked)
    				{
    				charge_type=document.frmCurrentPosting.AddlCharge[i].value;
    				}
    		}
    	var remarks=document.getElementById("txtworkremark1").value;
    	
//        var check=notNull('Validate');
//       // alert(check);
//        if(check)
//        {
    	//alert(empid+"=="+cmbDesignation1+"=="+slno);
               statuswaiting(document.frmCurrentPosting) ;
           
    document.frmCurrentPosting.action="../../../../../CurrentPosting_AdditionalCharge?Command=Update&txtEmployeeid="+empid+"&slno="+slno+"&service_group="+cmbsgroup1+"&designation="+cmbDesignation1+"&grade="+grade+"&office_id="+txtOffice_Id1+"&date_effective="+txtWorkDateFrom1+"&date_effective_session="+date_effective_session+"&charge_type="+charge_type+"&remarks="+remarks;
                         document.frmCurrentPosting.method="POST";
                         document.frmCurrentPosting.submit();
                       return;
//               
//        }
    }
  /*  else if(Command=="Delete")
    {
    
        if(confirm('Do You want to delete this record?'))
        {
                statuswaiting(document.frmCurrentPosting) ;
                var url="../../../../../CurrentPosting_AdditionalCharge?Command=Delete&txtEmployeeid="+empid;
                 url=url+"&txtSNo="+slno;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
        }
    
    
    
    }*/
    else if(Command=="Load")
    {
      var  Office_Id;
      var   Dept_Id;
      
      
     if(param==true)
    {   
        jobflag=true;
        Dept_Id=document.frmCurrentPosting.txtDept_Id_work.value;
       Office_Id =document.frmCurrentPosting.txtOffice_Id.value;
       
         if((document.frmCurrentPosting.txtOffice_Id.value==null)||(document.frmCurrentPosting.txtOffice_Id.value.length==0))
        {
            alert("Select Office Id");
            //document.frmCurrentPosting.txtOffice_Id.value="";
            offclr();
            document.frmCurrentPosting.txtOffice_Id.focus();
            return false;
        }
         else if((document.frmCurrentPosting.txtOffice_Id.value<5000))
        {
            alert("Office Id should be greater than or equal to 5000");
            //document.frmCurrentPosting.txtOffice_Id.value="";
            offclr();
            document.frmCurrentPosting.txtOffice_Id.focus();
            return false;
        }
        
    }
    else if(param==false)
    {
         
         jobflag=false;
       Dept_Id= document.frmCurrentPosting.txtDept_Id.value;
       Office_Id =document.frmCurrentPosting.txtOthOffice_Id.value;
    }
       // alert(Office_Id +'   '+Dept_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
        var req1=getTransport();
        req1.open("GET",url,true);        
        req1.onreadystatechange=function()
        {
        processResponse(req1);
        }
         if(window.XMLHttpRequest)
                        req1.send(null);
                else req1.send();
    }
     else if(Command=="Clear")
            {
               
               /* clr();
                document.frmCurrentPosting.cmdadd.style.display="block";
                document.frmCurrentPosting.cmdupdate.style.display="none";
                //document.frmCurrentPosting.cmdadd.disabled=false;
               // document.frmCurrentPosting.cmdupdate.disabled=true;
                document.frmCurrentPosting.cmddelete.disabled=true;
                document.frmCurrentPosting.txtDept_Id.value="";
            */
            //alert('test');
            document.frmCurrentPosting.reset();
            disableAll()
            document.frmCurrentPosting.txtEmployeeid.focus();
                
            }
   
}

function offclr()
{
    
    document.frmCurrentPosting.txtOffice_Id.value='';
    document.frmCurrentPosting.txtOffice_Name.value='';
    document.frmCurrentPosting.txtOffice_Address1.value='';
  //  document.frmCurrentPosting.txtOffice_Address2.value='';
   // document.frmCurrentPosting.txtOffice_City.value='';
       
   

}

function checkdeptsel()
{
   
    document.frmCurrentPosting.txtOthOffice_Id.value='';
    document.frmCurrentPosting.txtOthOffice_Name.value='';
    document.frmCurrentPosting.txtOthOffice_Address1.value='';
    // alert('test');
    /* if((document.frmCurrentPosting.txtDept_Id.value==0)||(document.frmCurrentPosting.txtDept_Id.value.length==0))
        {
                offclr();
        }*/
}
function clr()
{
        /*document.frmCurrentPosting.txtEmployee.value="";
        document.frmCurrentPosting.txtGpf.value="";
        document.frmCurrentPosting.txtEmployeeid.value="";
        document.frmCurrentPosting.cmbsgroup.value="0";
        document.frmCurrentPosting.cmbstatus.value="";
        disableAll();
        document.frmCurrentPosting.txtEmployeeid.focus();*/
        
       // document.frmCurrentPosting.reset();
        disableAll()
        document.frmCurrentPosting.txtEmployeeid.focus();

}


function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            stopwaiting(document.frmCurrentPosting);
           // alert(req.responseText);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];          
            var Command=tagcommand.firstChild.nodeValue;
           // alert(Command);
            if(Command=="load_grid")
            	{
            		load_grid_fn(baseResponse);
            	}
            if(Command=="loadempview")
            {
                loadEmp11(baseResponse);
            }
            if(Command=="loadempedit")
            {
                loadEmp(baseResponse);
            }
            if(Command=="loadempeditss")
            {
                loadEmp1(baseResponse);
            }
            if(Command=="loadOffices")
            {
                loadOff(baseResponse);
            }
            
            else if(Command=="test")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
                if(flag=="failure")
                {
                    
                         alert('Enter a Valid Employee Number');
                         document.frmCurrentPosting.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.frmCurrentPosting.txtEmployeeid.focus();
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
            else if(Command=="Validate")
            {
                validatefun(baseResponse);
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
                        //alert(flag1);
                        self.close();
                        return;
                    }
                    }catch(e){
                    //alert(e);
                    
                    }  
            }
            else if(Command=="LastDate")
            {
            
                    lastDateFun(baseResponse);
            }
          
            
            
        }  
        
    }
    
}



function lastDateFun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //  alert(flag);
    if(flag=="success")
    {
           /* document.frmCurrentPosting.txtDateFrom.disabled=true;
            var i=document.getElementById("fromimg");
            i.disabled=true;
            i.alt="Disabled";
            */
            var nextdate=baseResponse.getElementsByTagName("nextdate")[0].firstChild.nodeValue;
            var nextses=baseResponse.getElementsByTagName("nextsession")[0].firstChild.nodeValue;
            
            try{
            document.frmCurrentPosting.txtDateFrom.value=nextdate;
              }catch(e){}  try{
                    if(nextses=='FN'){
                        document.frmCurrentPosting.optDateFrom[0].checked=true;
                     }
                    else {
                        document.frmCurrentPosting.optDateFrom[1].checked=true;
                    }
            }catch(e){}
            /*document.frmCurrentPosting.optDateFrom[0].disabled=true;
            document.frmCurrentPosting.optDateFrom[1].disabled=true;
            */
            return;
            
    }
  /*  else if(flag=="failure")
    {
            document.frmCurrentPosting.txtDateFrom.disabled=false;
            var i=document.getElementById("fromimg");
            i.disabled=false;
            i.alt="Show Calendar";
            document.frmCurrentPosting.optDateFrom[0].disabled=false;
            document.frmCurrentPosting.optDateFrom[1].disabled=false;
    }*/
}

function loading_grid(baseResponse){
	
	  var countss=baseResponse.getElementsByTagName("countss")[0].firstChild.nodeValue;
    
      var table1=document.getElementById("gridTable");
      
      var row=document.createElement("tr");
      
      
      var th=document.createElement("th");					  						
		var id=document.createTextNode("");
      th.appendChild(id);
  	th.setAttribute("align", "center");
  	th.setAttribute("width", "10%");
//  	th.setAttribute("style", "border-bottom: 1px solid #000;");
  	row.appendChild(th);
      
		    var th=document.createElement("th");					  						
			var id=document.createTextNode("SL.NO");
            th.appendChild(id);
        	th.setAttribute("align", "center");
        	th.setAttribute("width", "10%");
        	th.setAttribute("style", "display:none");
//        	th.setAttribute("style", "border-bottom: 1px solid #000;");
        	row.appendChild(th);
        	
        	var th=document.createElement("th");					  						
			var id=document.createTextNode("Employee_ID");
            th.appendChild(id);
        	th.setAttribute("align", "center");
        	th.setAttribute("width", "8%");	  
        	
//        	th.setAttribute("style", "border-bottom: 1px solid #000;");
        	row.appendChild(th);
        	
        	
        	var th=document.createElement("th");					  						
			var id=document.createTextNode("Designation Name");
            th.appendChild(id);
        	th.setAttribute("align", "center");
        	th.setAttribute("width", "30%");
//        	th.setAttribute("style", "border-bottom: 1px solid #000;");
        	row.appendChild(th);
        	
        	var th=document.createElement("th");					  						
			var id=document.createTextNode("Office Name");
            th.appendChild(id);
        	th.setAttribute("align", "center");
        	th.setAttribute("width", "30%");
//        	th.setAttribute("style", "border-bottom: 1px solid #000;");
        	row.appendChild(th);
        	
        	var th=document.createElement("th");					  						
			var id=document.createTextNode("Date Effective From");
            th.appendChild(id);
        	th.setAttribute("align", "center");
        	th.setAttribute("width", "15%");	  
//        	th.setAttribute("style", "border-bottom: 1px solid #000;");
        	row.appendChild(th);
        	
        
      
        	table1.appendChild(row);
        	
        	
          var table=document.getElementById("gridTable");
			var child=table.childNodes;
		    for(var c=child.length-1;c>0;c--)
		    {
	    	table.removeChild(child[c]);
		    }
		    
		    
		    for(var i=0;i<countss;i++)
         	{
      	   
      	   
         	
      	   var process_flow_status_id=baseResponse.getElementsByTagName("process_flow_status_id")[i].firstChild.nodeValue;
      	   var SLNO=baseResponse.getElementsByTagName("SLNO")[i].firstChild.nodeValue;
              var designations=baseResponse.getElementsByTagName("designations")[i].firstChild.nodeValue;
               var office_names=baseResponse.getElementsByTagName("office_names")[i].firstChild.nodeValue;
              var date_effective_froms=baseResponse.getElementsByTagName("date_effective_froms")[i].firstChild.nodeValue;
              var employee_id=baseResponse.getElementsByTagName("employee_id")[i].firstChild.nodeValue;
              
              if(designations=='null')
              	designations='';
             
           var table1=document.getElementById("gridTable");
					var row=document.createElement("tr");
					row.id=i;
         
              if(process_flow_status_id=="FR")
          	{
            	  
            	// alert("Already Freezed For this GPF.NO");
            	
            	  
              	var td=document.createElement("td");	
              	var yy=document.createElement("a");
              	 yy.setAttribute("href", "javascript:EDIT_FN('"+i+"')");
              	var id=document.createTextNode("Validated");
              	yy.appendChild(id);
              	td.appendChild(yy);
              	row.appendChild(td);
//		        	td.setAttribute("align", "center");
//		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//		        	row.appendChild(td);
              	
                var td=document.createElement("td");					  						
				var id=document.createTextNode(SLNO);
	            td.appendChild(id);
	            td.setAttribute("style", "display:none");
//	        	td.setAttribute("align", "center");
//	        	td.setAttribute("style", "border-bottom: 1px solid #000;");
	        	row.appendChild(td);
	        	
	        	var td=document.createElement("td");					  						
				var id=document.createTextNode(employee_id);
	            td.appendChild(id);
//	        	td.setAttribute("align", "center");
//	        	td.setAttribute("style", "border-bottom: 1px solid #000;");
	        	row.appendChild(td);
	        	
	        	var td=document.createElement("td");					  						
				var id=document.createTextNode(designations);
	            td.appendChild(id);
//	        	td.setAttribute("align", "center");
//	        	td.setAttribute("style", "border-bottom: 1px solid #000;");
	        	row.appendChild(td);
	        	
	        	var td=document.createElement("td");					  						
				var id=document.createTextNode(office_names);
	            td.appendChild(id);
//	        	td.setAttribute("align", "center");
//	        	td.setAttribute("style", "border-bottom: 1px solid #000;");
	        	row.appendChild(td);
//	        	
	        	var td=document.createElement("td");					  						
				var id=document.createTextNode(date_effective_froms);
	            td.appendChild(id);
//	        	td.setAttribute("align", "center");
//	        	td.setAttribute("style", "border-bottom: 1px solid #000;");
	        	row.appendChild(td);
//	        	
//	        	
	    
//			
	        	
	        	
              
              table1.appendChild(row);
          	}
              
          
              
        
         	}
              
      
}

function load_grid_fn(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	 if(flag=="success")
		 {
		
		 	loading_grid(baseResponse);
		 }
}

function loadEmp11(baseResponse)
{

	//alert("(((()))))");

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // alert(flag);
    // alert(req.responseText);
     if(flag=="success1")
    {
           
           if(calendarControl)
                calendarControl.hide();
            var id=document.frmCurrentPosting.txtEmployeeid.value;
           clr();
            document.frmCurrentPosting.txtEmployeeid.value=id;
            var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.frmCurrentPosting.txtEmployee.value=ename;
            if(egpf==0)
                egpf="";
            document.frmCurrentPosting.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
            document.frmCurrentPosting.cmdedit.disabled=false;
            //clr();
    }
    else if(flag=="success")
    {
    	//alert("***");
    	
    	
    	var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
        var EMPLOYEE_NAME=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
        var GPF_NO=baseResponse.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
        var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
        var DESIGNATION_ID=baseResponse.getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
        var DATE_EFFECTIVE_FROM=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM")[0].firstChild.nodeValue;
        //alert(DATE_EFFECTIVE_FROM);
        var REMARKS=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
        var OFFICE_GRADE=baseResponse.getElementsByTagName("OFFICE_GRADE")[0].firstChild.nodeValue;
        var DEPARTMENT_ID=baseResponse.getElementsByTagName("DEPARTMENT_ID")[0].firstChild.nodeValue;
        var PROCESS_FLOW_STATUS_ID=baseResponse.getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
      
        var EMPLOYEE_STATUS_ID=baseResponse.getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
        var LEAVE_TYPE=baseResponse.getElementsByTagName("LEAVE_TYPE")[0].firstChild.nodeValue;
        var DATE_EFFECTIVE_FROM_SESSION=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM_SESSION")[0].firstChild.nodeValue;
        var DEPARTMENT_ID=baseResponse.getElementsByTagName("DEPARTMENT_ID")[0].firstChild.nodeValue;
        var SERVICE_GROUP_ID=baseResponse.getElementsByTagName("SERVICE_GROUP_ID")[0].firstChild.nodeValue;
        if(DATE_EFFECTIVE_FROM=='null')
            DATE_EFFECTIVE_FROM='';
/* if(PROCESS_FLOW_STATUS_ID=="FR")
{
    //alert('Record is Freezed');
    document.frmCurrentPosting.cmdedit.disabled=true;
}
else
{
        document.frmCurrentPosting.cmdedit.disabled=false;
}*/

 if(REMARKS=='null')
            REMARKS="";
        document.frmCurrentPosting.txtEmployee.value=EMPLOYEE_NAME;
        if(GPF_NO==0)
            GPF_NO="";
        document.frmCurrentPosting.txtGpf.value=GPF_NO;
         if(OFFICE_GRADE=='Normal')
        {
                document.frmCurrentPosting.Office_Grade[0].checked=true;
        }
        else if(OFFICE_GRADE=='Selection')
        {
                document.frmCurrentPosting.Office_Grade[1].checked=true;
        }
        else if(OFFICE_GRADE=='Special')
        {
                document.frmCurrentPosting.Office_Grade[2].checked=true;
        }
        else
        {
            document.frmCurrentPosting.Office_Grade[3].checked=true;
        }
        document.frmCurrentPosting.cmbstatus.value=EMPLOYEE_STATUS_ID;
        document.frmCurrentPosting.cmbsgroup.value=SERVICE_GROUP_ID;
        desid=DESIGNATION_ID;
        loadOfficesByType(SERVICE_GROUP_ID);
        //document.frmCurrentPosting.cmbDesignation.value=DESIGNATION_ID;
        
        var type=EMPLOYEE_STATUS_ID;
       // alert(REMARKS);
      
        if(type=='DPN')
        {
                var id=document.getElementById("divdep");
                id.style.display="block";
                
                 document.frmCurrentPosting.txtDept_Id.value=DEPARTMENT_ID;
                 document.frmCurrentPosting.txtOthOffice_Id.value=OFFICE_ID;
                 //doFunction('Load',true);
                 doFunction('Load',false)
                 
                 document.frmCurrentPosting.txtDepDateFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optDepDateFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optDepDateFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtDepremark.value=REMARKS;
        }
       if(type=='WKG')
        {
        	 //alert(type+"=="+"WKG");
                var id=document.getElementById("divwork");
                id.style.display="block";
                
                 document.frmCurrentPosting.txtDept_Id_work.value=DEPARTMENT_ID;
                 document.frmCurrentPosting.txtOffice_Id.value=OFFICE_ID;
                 doFunction('Load',true);
                 document.frmCurrentPosting.txtWorkDateFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optWorkDateFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optWorkDateFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtworkremark.value=REMARKS;
                 
                 /******************************/
                   var wing=baseResponse.getElementsByTagName("WING")[0].firstChild.nodeValue;
                   GWING=wing;
        }
         if(type=='LLV')
        {
                var id=document.getElementById("divll");
                id.style.display="block";
                document.frmCurrentPosting.cmbleavetype.value=LEAVE_TYPE;
                document.frmCurrentPosting.txtDateFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optDateFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optDateFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtleaveremark.value=REMARKS;
                
        }
       if(type=='SUS')
        {
                var id=document.getElementById("divsus");
                id.style.display="block";
                
                document.frmCurrentPosting.txtSusFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optSusFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optSusFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtsusreson.value=REMARKS;
        }
        
        
        
         /* TRANSIT  */
        
         if(type=='TRT')
        {
                var id=document.getElementById("divtrt");
                id.style.display="block";
                
                document.frmCurrentPosting.txtTrtFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optTrtFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optTrtFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtTrtreson.value=REMARKS;
        }
        if(type=='UAL')
        {
                var id=document.getElementById("divual");
                id.style.display="block";
                
                document.frmCurrentPosting.txtUalFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optUalFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optUalFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtUalreson.value=REMARKS;
        }
        if(type=='CMW')
        {
                var id=document.getElementById("divcmw");
                id.style.display="block";
                
                document.frmCurrentPosting.txtCmwFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optCmwFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optCmwFrom[1].checked=true;
                 }
                 document.frmCurrentPosting.txtCmwreson.value=REMARKS;
        }
        
        
        
        
        if(type=='ABS')
        {
                var id=document.getElementById("divabs");
                id.style.display="block";
                document.frmCurrentPosting.txtAbsFrom.value=DATE_EFFECTIVE_FROM;
                 if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                 {
                        document.frmCurrentPosting.optAbsFrom[0].checked=true;
                 }
                 else
                 {
                        document.frmCurrentPosting.optAbsFrom[1].checked=true;
                 }
                 
                 document.frmCurrentPosting.txtabsremark.value=REMARKS;
        }
       
      
           
          
             var PROCESS_FLOW_SID=baseResponse.getElementsByTagName("Freezed")[0].firstChild.nodeValue;
            
             if(PROCESS_FLOW_SID=="Freezed")
            	 {
            	
            	 var SLNOSS=baseResponse.getElementsByTagName("SLNOSS")[0].firstChild.nodeValue;
                 var SERVICE_GROUP_IDSS=baseResponse.getElementsByTagName("SERVICE_GROUP_IDSS")[0].firstChild.nodeValue;
                 var SERVICE_GROUP_NAMESS=baseResponse.getElementsByTagName("SERVICE_GROUP_NAMESS")[0].firstChild.nodeValue;
                 var DESIGNATION_IDSS=baseResponse.getElementsByTagName("DESIGNATION_IDSS")[0].firstChild.nodeValue;
                 var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
                 var DESIGNATION_ID=baseResponse.getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
                // alert(DESIGNATION_IDSS);
                 var DESIGNATIONSS=baseResponse.getElementsByTagName("DESIGNATIONSS")[0].firstChild.nodeValue;//alert('test'); 
                 var OFFICE_GRADESS=baseResponse.getElementsByTagName("OFFICE_GRADESS")[0].firstChild.nodeValue; 
                 var EMPLOYEE_IDSS=baseResponse.getElementsByTagName("EMPLOYEE_IDSS")[0].firstChild.nodeValue;
                 var OFFICE_IDSS=baseResponse.getElementsByTagName("OFFICE_IDSS")[0].firstChild.nodeValue;
                 var OFFICE_NAMESS=baseResponse.getElementsByTagName("OFFICE_NAMESS")[0].firstChild.nodeValue;
                 var OFFICE_ADDRESS1SS=baseResponse.getElementsByTagName("OFFICE_ADDRESS1SS")[0].firstChild.nodeValue;
                 var OFFICE_ADDRESS2SS=baseResponse.getElementsByTagName("OFFICE_ADDRESS2SS")[0].firstChild.nodeValue;
                 var CITY_TOWN_NAMESS=baseResponse.getElementsByTagName("CITY_TOWN_NAMESS")[0].firstChild.nodeValue;
                 var DATE_EFFECTIVE_FROMSS=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROMSS")[0].firstChild.nodeValue;
                 var DATE_EFFECTIVE_FROM_SESSIONSS=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM_SESSIONSS")[0].firstChild.nodeValue;
               
                 var ADDL_CHARGE_TYPESS=baseResponse.getElementsByTagName("ADDL_CHARGE_TYPESS")[0].firstChild.nodeValue;
                 var REMARKSSS=baseResponse.getElementsByTagName("REMARKSSS")[0].firstChild.nodeValue;
                 if(REMARKSSS=='null')
                	 REMARKSSS='';
                 document.getElementById("txtworkremark1").value=REMARKSSS;
                 document.getElementById("txtEmployeeid").value=EMPLOYEE_IDSS;
                 document.getElementById("slno").value=SLNOSS;
                 var len= document.getElementById("cmbsgroup1").length;
                 for(var i=0;i<len;i++)
              	   {
              	   		var val=document.getElementById("cmbsgroup1").options[i].value;
              	   		
              	   		if(val==SERVICE_GROUP_IDSS)
              	   			{
              	   				
              	   		
              	   		    document.getElementById("cmbsgroup1").options[val].selected=true;
              	   		   
       
               	   			}
              	   }
                
                document.getElementById("cmbDesignation1").options[0].value=DESIGNATION_IDSS;
               // alert(document.getElementById("cmbDesignation1").options[0].value);
                des_id=document.getElementById("cmbDesignation1").options[0].value
                document.getElementById("cmbDesignation1").options[0].text=DESIGNATIONSS;
                
                var leng=document.frmCurrentPosting.Office_Grade1.length;   
                for(var i=0;i<leng;i++)
              	  {
              	  		var radio=document.frmCurrentPosting.Office_Grade1[i].value;
              	  		if(radio==OFFICE_GRADESS)
              	  			{
              	  			document.frmCurrentPosting.Office_Grade1[i].checked=true;
              	  			break;
              	  			}
              	  }
              
                document.getElementById("txtOffice_Id1").value=OFFICE_IDSS;
                document.getElementById("txtOffice_Name1").value=OFFICE_NAMESS;
                var address=OFFICE_ADDRESS1SS+"\n"+OFFICE_ADDRESS2SS+",\n"+CITY_TOWN_NAMESS+".";
                document.getElementById("txtOffice_Address11").value=address;
                document.getElementById("txtWorkDateFrom1").value=DATE_EFFECTIVE_FROMSS;
                
               
              	  		
              	  		if(DATE_EFFECTIVE_FROM_SESSIONSS=="FN")
              	  			{
              	  			document.getElementById("optWorkDateFrom11").checked=true;
              	  		document.getElementById("optWorkDateFrom21").checked=false;
              	  			
              	  			}
              	  	if(DATE_EFFECTIVE_FROM_SESSIONSS=="AN")
      	  			{
              	  	document.getElementById("optWorkDateFrom21").checked=true;
      	  			document.getElementById("optWorkDateFrom11").checked=false;
      	  		
      	  			
      	  			}
        
              	  	

          	  		if(ADDL_CHARGE_TYPESS=="A")
          	  			{
          	  			document.getElementById("charge1").checked=true;
          	  		document.getElementById("charge2").checked=false;
          	  			
          	  			}
          	  	if(ADDL_CHARGE_TYPESS=="I")
  	  			{
          	  	document.getElementById("charge2").checked=true;
  	  			document.getElementById("charge1").checked=false;
  	  	
  	  			
  	  			}
                
                
               
                 
            	 }
            
             
            
          
          var add_value_to_combo = document.getElementById("cmbAddlChargeOffId"); 
            add_value_to_combo.length=0;
            var comboitem = document.createElement("option");   
            comboitem.text=offname;
            comboitem.value=OFFICE_ID;
            add_value_to_combo.options.add(comboitem);   
            alert(add_value_to_combo.value);
            
           
            //alert(offname);
             if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
            var dis=0;
            if(ADDL_CHARGE_STATUS=='CL')            
                {
                alert("Employee has revoked from additional charge");
                dis=1;
                }
             if(PROCESS_FLOW_STATUS_ID=="FR")
                {
                  /*  var admin=baseResponse.getElementsByTagName("admin")[0].firstChild.nodeValue;
                if(admin=='YES')
                {
                            if(confirm('Record is already Freezed.'))
                           {
                            document.frmCurrentPosting.cmdedit.disabled=false;
                            }
                            else
                             {
                            document.frmCurrentPosting.cmdedit.disabled=true;
                            }
                
                }
                else
                {*/
                    if(dis==0)
                    alert('Record is Freezed');
                    document.frmCurrentPosting.cmdedit.disabled=true;
                //}
                }
                else
                {
                        document.frmCurrentPosting.cmdedit.disabled=false;
                }
            
             if(REMARKS=='null')
                        REMARKS="";
            document.frmCurrentPosting.txtEmployeeid.value=EMPLOYEE_ID;
            document.frmCurrentPosting.txtEmployee.value=EMPLOYEE_NAME;
            if(GPF_NO==0)
                GPF_NO="";
            document.frmCurrentPosting.txtGpf.value=GPF_NO;
            
            if(OFFICE_GRADE=='Normal')
            {
                    document.frmCurrentPosting.Office_Grade[0].checked=true;
            }
            else if(OFFICE_GRADE=='Selection')
            {
                    document.frmCurrentPosting.Office_Grade[1].checked=true;
            }
            else if(OFFICE_GRADE=='Special')
            {
                    document.frmCurrentPosting.Office_Grade[2].checked=true;
            }
            else
            {
                document.frmCurrentPosting.Office_Grade[3].checked=true;
            }
            document.frmCurrentPosting.cmbstatus.value=EMPLOYEE_STATUS_ID;
            document.frmCurrentPosting.cmbsgroup.value=SERVICE_GROUP_ID;
            desid=DESIGNATION_ID;
           
            loadOfficesByType1(SERVICE_GROUP_ID);
            
            
            var type=EMPLOYEE_STATUS_ID;
             disableAll();
            if(type=='DPN')
            {
                    var id=document.getElementById("divdep");
                    id.style.display="block";
                    
                     document.frmCurrentPosting.txtDept_Id.value=DEPARTMENT_ID;
                     document.frmCurrentPosting.txtOthOffice_Id.value=OFFICE_ID;
                     //doFunction('Load',true);
                     doFunction('Load',false)
                     document.frmCurrentPosting.txtDepDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optDepDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optDepDateFrom[1].checked=true;
                     }
                     
                      document.frmCurrentPosting.txtDepremark.value=REMARKS;
            }
            else  if(type=='WKG')
            {
                    var id=document.getElementById("divwork");
                    id.style.display="block";
                    //alert("hello");
                     document.frmCurrentPosting.txtDept_Id_work.value=DEPARTMENT_ID;
                     document.frmCurrentPosting.txtOffice_Id.value=OFFICE_ID;
                     doFunction('Load',true);
                      if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                     document.frmCurrentPosting.txtWorkDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optWorkDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optWorkDateFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtworkremark.value=REMARKS;
                     
                     /******************************/
                       var wing=baseResponse.getElementsByTagName("WING")[0].firstChild.nodeValue;
                       GWING=wing;
         
                    
            }
            
            
            
             else  if(type=='STU')
            {
                    var id=document.getElementById("divstudy");
                    id.style.display="block";
                    try{
                    var INSTITUTION_NAME=baseResponse.getElementsByTagName("INSTITUTION_NAME")[0].firstChild.nodeValue;
                    var INSTITUTION_LOCATION=baseResponse.getElementsByTagName("INSTITUTION_LOCATION")[0].firstChild.nodeValue;
                    var COURSE_NAME=baseResponse.getElementsByTagName("COURSE_NAME")[0].firstChild.nodeValue;
           
                     document.frmCurrentPosting.txtInst_Name.value=INSTITUTION_NAME;
                     document.frmCurrentPosting.txtInst_Location.value=INSTITUTION_LOCATION;
                     document.frmCurrentPosting.txtCourse_Name.value=COURSE_NAME;
                     document.frmCurrentPosting.txtStudyDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optStudyDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optStudyDateFrom[1].checked=true;
                     }
                     }catch(e){}
                     document.frmCurrentPosting.txtstudyremark.value=REMARKS;
                     
            }
            
            
            
            
             else  if(type=='LLV')
            {
                    var id=document.getElementById("divll");
                    id.style.display="block";
                    document.frmCurrentPosting.cmbleavetype.value=LEAVE_TYPE;
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optDateFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtleaveremark.value=REMARKS;
                    
            }
             else  if(type=='SUS')
            {
                    var id=document.getElementById("divsus");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtSusFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optSusFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optSusFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtsusreson.value=REMARKS;
            }
            
            
            /* TRANSIT  */
            
             else  if(type=='TRT')
            {
                    var id=document.getElementById("divtrt");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtTrtFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optTrtFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optTrtFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtTrtreson.value=REMARKS;
            }
             else  if(type=='UAL')
            {
                    var id=document.getElementById("divual");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtUalFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optUalFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optUalFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtUalreson.value=REMARKS;
            }
             else  if(type=='CMW')
            {
                    var id=document.getElementById("divcmw");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtCmwFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optCmwFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optCmwFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtCmwreson.value=REMARKS;
            }
            
            
              else  if(type=='ABS')
            {
                    var id=document.getElementById("divabs");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtAbsFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optAbsFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optAbsFrom[1].checked=true;
                     }
                     
                     document.frmCurrentPosting.txtabsremark.value=REMARKS;
            }
           
            
            
    }
    else  if(flag=="freezed")
    {
            alert('Given Employee Id is already freezed');
            //clr();  // before 9th 0ct 
            
    }
    else if(flag=="failure1")
    {
          // alert(document.frmCurrentPosting.txtEmployeeid.value);
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Update Current Posting. Because Employee Id "+id+" is not under your Office!");
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("You have no Update Posting. Can not Create Current Posting for "+id+"!");
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not Update Current Posting!");
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Update Current Posting. Access Denined!");
           clr();
    }
    else
    {
        
          //alert("show me------------");     
        alert('Enter a Valid Employee Number');
        document.getElementById("txtEmployeeid").value="";
        clr();
        //document.frmCurrentPosting.reset();
        
    }



}

function loadEmp1(baseResponse)
{


    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
     if(flag=="success1")
    {
           
           if(calendarControl)
                calendarControl.hide();
            var id=document.frmCurrentPosting.txtEmployeeid.value;
           clr();
            document.frmCurrentPosting.txtEmployeeid.value=id;
            var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.frmCurrentPosting.txtEmployee.value=ename;
            if(egpf==0)
                egpf="";
            document.frmCurrentPosting.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
            document.frmCurrentPosting.cmdedit.disabled=false;
            //clr();
    }
    else if(flag=="success")
    {
           
           if(calendarControl)
                calendarControl.hide();
           clr();
            
            var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
            var EMPLOYEE_NAME=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
            var GPF_NO=baseResponse.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
            var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
            var DESIGNATION_ID=baseResponse.getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
            var DATE_EFFECTIVE_FROM=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM")[0].firstChild.nodeValue;//alert('test'); 
            var REMARKS=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue; 
            var OFFICE_GRADE=baseResponse.getElementsByTagName("OFFICE_GRADE")[0].firstChild.nodeValue;
            var DEPARTMENT_ID=baseResponse.getElementsByTagName("DEPARTMENT_ID")[0].firstChild.nodeValue;
            var PROCESS_FLOW_STATUS_ID=baseResponse.getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
            var EMPLOYEE_STATUS_ID=baseResponse.getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
            var LEAVE_TYPE=baseResponse.getElementsByTagName("LEAVE_TYPE")[0].firstChild.nodeValue;
            var DATE_EFFECTIVE_FROM_SESSION=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM_SESSION")[0].firstChild.nodeValue;
            var DEPARTMENT_ID=baseResponse.getElementsByTagName("DEPARTMENT_ID")[0].firstChild.nodeValue;
            var SERVICE_GROUP_ID=baseResponse.getElementsByTagName("SERVICE_GROUP_ID")[0].firstChild.nodeValue;
            var ADDL_CHARGE_STATUS=baseResponse.getElementsByTagName("ADDL_CHARGE_STATUS")[0].firstChild.nodeValue;
                     
            //alert(offname);
             if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
            var dis=0;
            if(ADDL_CHARGE_STATUS=='CL')            
                {
                alert("Employee has revoked from additional charge");
                dis=1;
                }
             if(PROCESS_FLOW_STATUS_ID=="FR")
                {
                  /*  var admin=baseResponse.getElementsByTagName("admin")[0].firstChild.nodeValue;
                if(admin=='YES')
                {
                            if(confirm('Record is already Freezed.'))
                           {
                            document.frmCurrentPosting.cmdedit.disabled=false;
                            }
                            else
                             {
                            document.frmCurrentPosting.cmdedit.disabled=true;
                            }
                
                }
                else
                {*/
                    if(dis==0)
                    alert('Record is Freezed');
                    document.frmCurrentPosting.cmdedit.disabled=true;
                //}
                }
                else
                {
                        document.frmCurrentPosting.cmdedit.disabled=false;
                }
            
             if(REMARKS=='null')
                        REMARKS="";
            document.frmCurrentPosting.txtEmployeeid.value=EMPLOYEE_ID;
            document.frmCurrentPosting.txtEmployee.value=EMPLOYEE_NAME;
            if(GPF_NO==0)
                GPF_NO="";
            document.frmCurrentPosting.txtGpf.value=GPF_NO;
            
            if(OFFICE_GRADE=='Normal')
            {
                    document.frmCurrentPosting.Office_Grade[0].checked=true;
            }
            else if(OFFICE_GRADE=='Selection')
            {
                    document.frmCurrentPosting.Office_Grade[1].checked=true;
            }
            else if(OFFICE_GRADE=='Special')
            {
                    document.frmCurrentPosting.Office_Grade[2].checked=true;
            }
            else
            {
                document.frmCurrentPosting.Office_Grade[3].checked=true;
            }
            document.frmCurrentPosting.cmbstatus.value=EMPLOYEE_STATUS_ID;
            document.frmCurrentPosting.cmbsgroup.value=SERVICE_GROUP_ID;
            desid=DESIGNATION_ID;
           
            loadOfficesByType1(SERVICE_GROUP_ID);
            
            
            var type=EMPLOYEE_STATUS_ID;
             disableAll();
            if(type=='DPN')
            {
                    var id=document.getElementById("divdep");
                    id.style.display="block";
                    
                     document.frmCurrentPosting.txtDept_Id.value=DEPARTMENT_ID;
                     document.frmCurrentPosting.txtOthOffice_Id.value=OFFICE_ID;
                     //doFunction('Load',true);
                     doFunction('Load',false)
                     document.frmCurrentPosting.txtDepDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optDepDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optDepDateFrom[1].checked=true;
                     }
                     
                      document.frmCurrentPosting.txtDepremark.value=REMARKS;
            }
            else  if(type=='WKG')
            {
                    var id=document.getElementById("divwork");
                    id.style.display="block";
                    //alert("hello");
                     document.frmCurrentPosting.txtDept_Id_work.value=DEPARTMENT_ID;
                     document.frmCurrentPosting.txtOffice_Id.value=OFFICE_ID;
                     doFunction('Load',true);
                      if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                     document.frmCurrentPosting.txtWorkDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optWorkDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optWorkDateFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtworkremark.value=REMARKS;
                     
                     /******************************/
                       var wing=baseResponse.getElementsByTagName("WING")[0].firstChild.nodeValue;
                       GWING=wing;
         
                    
            }
            
            
            
             else  if(type=='STU')
            {
                    var id=document.getElementById("divstudy");
                    id.style.display="block";
                    try{
                    var INSTITUTION_NAME=baseResponse.getElementsByTagName("INSTITUTION_NAME")[0].firstChild.nodeValue;
                    var INSTITUTION_LOCATION=baseResponse.getElementsByTagName("INSTITUTION_LOCATION")[0].firstChild.nodeValue;
                    var COURSE_NAME=baseResponse.getElementsByTagName("COURSE_NAME")[0].firstChild.nodeValue;
           
                     document.frmCurrentPosting.txtInst_Name.value=INSTITUTION_NAME;
                     document.frmCurrentPosting.txtInst_Location.value=INSTITUTION_LOCATION;
                     document.frmCurrentPosting.txtCourse_Name.value=COURSE_NAME;
                     document.frmCurrentPosting.txtStudyDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optStudyDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optStudyDateFrom[1].checked=true;
                     }
                     }catch(e){}
                     document.frmCurrentPosting.txtstudyremark.value=REMARKS;
                     
            }
            
            
            
            
             else  if(type=='LLV')
            {
                    var id=document.getElementById("divll");
                    id.style.display="block";
                    document.frmCurrentPosting.cmbleavetype.value=LEAVE_TYPE;
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optDateFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtleaveremark.value=REMARKS;
                    
            }
             else  if(type=='SUS')
            {
                    var id=document.getElementById("divsus");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtSusFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optSusFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optSusFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtsusreson.value=REMARKS;
            }
            
            
            /* TRANSIT  */
            
             else  if(type=='TRT')
            {
                    var id=document.getElementById("divtrt");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtTrtFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optTrtFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optTrtFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtTrtreson.value=REMARKS;
            }
             else  if(type=='UAL')
            {
                    var id=document.getElementById("divual");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtUalFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optUalFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optUalFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtUalreson.value=REMARKS;
            }
             else  if(type=='CMW')
            {
                    var id=document.getElementById("divcmw");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtCmwFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optCmwFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optCmwFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtCmwreson.value=REMARKS;
            }
            
            
              else  if(type=='ABS')
            {
                    var id=document.getElementById("divabs");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtAbsFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optAbsFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optAbsFrom[1].checked=true;
                     }
                     
                     document.frmCurrentPosting.txtabsremark.value=REMARKS;
            }
           
            
            
    }
    else  if(flag=="freezed")
    {
            alert('Given Employee Id is already freezed');
            //clr();  // before 9th 0ct 
            
    }
    else if(flag=="failure1")
    {
          // alert(document.frmCurrentPosting.txtEmployeeid.value);
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Update Current Posting. Because Employee Id "+id+" is not under your Office!");
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("You have no Update Posting. Can not Create Current Posting for "+id+"!");
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not Update Current Posting!");
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Update Current Posting. Access Denined!");
           clr();
    }
    else
    {
        
          //alert("show me------------");     
        alert('Enter a Valid Employee Number');
        document.getElementById("txtEmployeeid").value="";
        clr();
        //document.frmCurrentPosting.reset();
        
    }



}


function loadEmp(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
     if(flag=="success1")
    {
           
           if(calendarControl)
                calendarControl.hide();
            var id=document.frmCurrentPosting.txtEmployeeid.value;
           clr();
            document.frmCurrentPosting.txtEmployeeid.value=id;
            var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.frmCurrentPosting.txtEmployee.value=ename;
            if(egpf==0)
                egpf="";
            document.frmCurrentPosting.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
            document.frmCurrentPosting.cmdedit.disabled=false;
            //clr();
    }
    else if(flag=="success")
    {
           
           if(calendarControl)
                calendarControl.hide();
           clr();
            
            var EMPLOYEE_ID=baseResponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
            var EMPLOYEE_NAME=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
            var GPF_NO=baseResponse.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
            var OFFICE_ID=baseResponse.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
            var DESIGNATION_ID=baseResponse.getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
            var DATE_EFFECTIVE_FROM=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM")[0].firstChild.nodeValue;//alert('test'); 
            var REMARKS=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue; 
            var OFFICE_GRADE=baseResponse.getElementsByTagName("OFFICE_GRADE")[0].firstChild.nodeValue;
            var DEPARTMENT_ID=baseResponse.getElementsByTagName("DEPARTMENT_ID")[0].firstChild.nodeValue;
            var PROCESS_FLOW_STATUS_ID=baseResponse.getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
            var EMPLOYEE_STATUS_ID=baseResponse.getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
            var LEAVE_TYPE=baseResponse.getElementsByTagName("LEAVE_TYPE")[0].firstChild.nodeValue;
            var DATE_EFFECTIVE_FROM_SESSION=baseResponse.getElementsByTagName("DATE_EFFECTIVE_FROM_SESSION")[0].firstChild.nodeValue;
            var DEPARTMENT_ID=baseResponse.getElementsByTagName("DEPARTMENT_ID")[0].firstChild.nodeValue;
            var SERVICE_GROUP_ID=baseResponse.getElementsByTagName("SERVICE_GROUP_ID")[0].firstChild.nodeValue;
            var ADDL_CHARGE_STATUS=baseResponse.getElementsByTagName("ADDL_CHARGE_STATUS")[0].firstChild.nodeValue;
                     
            //alert(offname);
             if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
            var dis=0;
            if(ADDL_CHARGE_STATUS=='CL')            
                {
                alert("Employee has revoked from additional charge");
                dis=1;
                }
             if(PROCESS_FLOW_STATUS_ID=="FR")
                {
                  /*  var admin=baseResponse.getElementsByTagName("admin")[0].firstChild.nodeValue;
                if(admin=='YES')
                {
                            if(confirm('Record is already Freezed.'))
                           {
                            document.frmCurrentPosting.cmdedit.disabled=false;
                            }
                            else
                             {
                            document.frmCurrentPosting.cmdedit.disabled=true;
                            }
                
                }
                else
                {*/
                    if(dis==0)
                    alert('Record is Freezed');
                    document.frmCurrentPosting.cmdedit.disabled=true;
                //}
                }
                else
                {
                        document.frmCurrentPosting.cmdedit.disabled=false;
                }
            
             if(REMARKS=='null')
                        REMARKS="";
            document.frmCurrentPosting.txtEmployeeid.value=EMPLOYEE_ID;
            document.frmCurrentPosting.txtEmployee.value=EMPLOYEE_NAME;
            if(GPF_NO==0)
                GPF_NO="";
            document.frmCurrentPosting.txtGpf.value=GPF_NO;
            
            if(OFFICE_GRADE=='Normal')
            {
                    document.frmCurrentPosting.Office_Grade[0].checked=true;
            }
            else if(OFFICE_GRADE=='Selection')
            {
                    document.frmCurrentPosting.Office_Grade[1].checked=true;
            }
            else if(OFFICE_GRADE=='Special')
            {
                    document.frmCurrentPosting.Office_Grade[2].checked=true;
            }
            else
            {
                document.frmCurrentPosting.Office_Grade[3].checked=true;
            }
            document.frmCurrentPosting.cmbstatus.value=EMPLOYEE_STATUS_ID;
            document.frmCurrentPosting.cmbsgroup.value=SERVICE_GROUP_ID;
            desid=DESIGNATION_ID;
           
            loadOfficesByType1(SERVICE_GROUP_ID);
            
            
            var type=EMPLOYEE_STATUS_ID;
             disableAll();
            if(type=='DPN')
            {
                    var id=document.getElementById("divdep");
                    id.style.display="block";
                    
                     document.frmCurrentPosting.txtDept_Id.value=DEPARTMENT_ID;
                     document.frmCurrentPosting.txtOthOffice_Id.value=OFFICE_ID;
                     //doFunction('Load',true);
                     doFunction('Load',false)
                     document.frmCurrentPosting.txtDepDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optDepDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optDepDateFrom[1].checked=true;
                     }
                     
                      document.frmCurrentPosting.txtDepremark.value=REMARKS;
            }
            else  if(type=='WKG')
            {
                    var id=document.getElementById("divwork");
                    id.style.display="block";
                    //alert("hello");
                     document.frmCurrentPosting.txtDept_Id_work.value=DEPARTMENT_ID;
                     document.frmCurrentPosting.txtOffice_Id.value=OFFICE_ID;
                     doFunction('Load',true);
                      if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                     document.frmCurrentPosting.txtWorkDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optWorkDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optWorkDateFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtworkremark.value=REMARKS;
                     
                     /******************************/
                       var wing=baseResponse.getElementsByTagName("WING")[0].firstChild.nodeValue;
                       GWING=wing;
         
                    
            }
            
            
            
             else  if(type=='STU')
            {
                    var id=document.getElementById("divstudy");
                    id.style.display="block";
                    try{
                    var INSTITUTION_NAME=baseResponse.getElementsByTagName("INSTITUTION_NAME")[0].firstChild.nodeValue;
                    var INSTITUTION_LOCATION=baseResponse.getElementsByTagName("INSTITUTION_LOCATION")[0].firstChild.nodeValue;
                    var COURSE_NAME=baseResponse.getElementsByTagName("COURSE_NAME")[0].firstChild.nodeValue;
           
                     document.frmCurrentPosting.txtInst_Name.value=INSTITUTION_NAME;
                     document.frmCurrentPosting.txtInst_Location.value=INSTITUTION_LOCATION;
                     document.frmCurrentPosting.txtCourse_Name.value=COURSE_NAME;
                     document.frmCurrentPosting.txtStudyDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optStudyDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optStudyDateFrom[1].checked=true;
                     }
                     }catch(e){}
                     document.frmCurrentPosting.txtstudyremark.value=REMARKS;
                     
            }
            
            
            
            
             else  if(type=='LLV')
            {
                    var id=document.getElementById("divll");
                    id.style.display="block";
                    document.frmCurrentPosting.cmbleavetype.value=LEAVE_TYPE;
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtDateFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optDateFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optDateFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtleaveremark.value=REMARKS;
                    
            }
             else  if(type=='SUS')
            {
                    var id=document.getElementById("divsus");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtSusFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optSusFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optSusFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtsusreson.value=REMARKS;
            }
            
            
            /* TRANSIT  */
            
             else  if(type=='TRT')
            {
                    var id=document.getElementById("divtrt");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtTrtFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optTrtFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optTrtFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtTrtreson.value=REMARKS;
            }
             else  if(type=='UAL')
            {
                    var id=document.getElementById("divual");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtUalFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optUalFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optUalFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtUalreson.value=REMARKS;
            }
             else  if(type=='CMW')
            {
                    var id=document.getElementById("divcmw");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtCmwFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optCmwFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optCmwFrom[1].checked=true;
                     }
                     document.frmCurrentPosting.txtCmwreson.value=REMARKS;
            }
            
            
              else  if(type=='ABS')
            {
                    var id=document.getElementById("divabs");
                    id.style.display="block";
                     if(DATE_EFFECTIVE_FROM=='null')
                        DATE_EFFECTIVE_FROM='';
                    document.frmCurrentPosting.txtAbsFrom.value=DATE_EFFECTIVE_FROM;
                     if(DATE_EFFECTIVE_FROM_SESSION=='FN')
                     {
                            document.frmCurrentPosting.optAbsFrom[0].checked=true;
                     }
                     else
                     {
                            document.frmCurrentPosting.optAbsFrom[1].checked=true;
                     }
                     
                     document.frmCurrentPosting.txtabsremark.value=REMARKS;
            }
           
            
            
    }
    else  if(flag=="freezed")
    {
            alert('Given Employee Id is already freezed');
            //clr();  // before 9th 0ct 
            
    }
    else if(flag=="failure1")
    {
          // alert(document.frmCurrentPosting.txtEmployeeid.value);
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Update Current Posting. Because Employee Id "+id+" is not under your Office!");
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("You have no Update Posting. Can not Create Current Posting for "+id+"!");
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not Update Current Posting!");
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Update Current Posting. Access Denined!");
           clr();
    }
    else
    {
        
          //alert("show me------------");     
        alert('Enter a Valid Employee Number');
        document.getElementById("txtEmployeeid").value="";
        clr();
        //document.frmCurrentPosting.reset();
        
    }


}


function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     
function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          //document.frmCurrentPosting.txtSNo.focus();
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
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

function checkempid()
{
    if(document.frmCurrentPosting.txtEmployeeid.value==null || document.frmCurrentPosting.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.frmCurrentPosting.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function checkdeptid()
{
    if(document.frmCurrentPosting.txtDept_Id.value==null || document.frmCurrentPosting.txtDept_Id.value.length==0)
    {
            alert('Select Department Id');
            document.frmCurrentPosting.txtDept_Id.focus();
            return false;
    }
   
    return true;

}


//*********************  sevice group  selection  ************************

    
    function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        startwaiting(document.frmCurrentPosting) ;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup1&cmbsgroup=" + type ;
        var req1=getTransport();
        req1.open("GET",url,true);        
        req1.onreadystatechange=function()
        {
            
             loadDesignation(req1);
        }
        if(window.XMLHttpRequest)
                        req1.send(null);
        else req1.send(); 
    }
    
    function getDesignation()
    {
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
           // var din=document.getElementById("divdes");
            //din.style.visibility="visible";
            //document.frmCurrentPosting.cmbdes.style.visibility="visible";
            desid=0;
            loadOfficesByType(type);
        }
        else
        {
           var des=document.getElementById("cmbDesignation");
           des.innerHTML='';
           desid=0;
        }
    }
    
    function  loadDesignation(req1)
{
     if(req1.readyState==4)
        {
          if(req1.status==200)
          { 
               // alert(req1);            
                var response=req1.responseXML.getElementsByTagName("response")[0];
                //alert(response);
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //des.innerHTML="";
               // alert('test');
                var des=document.getElementById("cmbDesignation");
                des.innerHTML="";
                var i=0;
                 stopwaiting(document.frmCurrentPosting);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="Select Designation";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                       
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                    document.frmCurrentPosting.cmbDesignation.value=desid;
                
                }
        
        }
        
    }
    

}


    function  loadOff(req1)
{

            
               // var response=req1.responseXML.getElementsByTagName("response")[0];
                //alert(response);
                var flag=req1.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //des.innerHTML="";
                //alert('test');
                var des=document.getElementById("cmbAddlChargeOffId");
                des.innerHTML="";
                var i=0;
                //alert(flag);
                // stopwaiting(document.frmCurrentPosting);
                if(flag=="failure")
                {
                	document.getElementById("txtEmployeeid").value="";
                    alert("No Other Offices exists ");
                }
                else
                {
              
                    var value=req1.getElementsByTagName("offid");
                    var option=document.createElement("OPTION");
                    option.text="--Select Office--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                   // alert(value.length)
                       // var tmpoption=value.item(i);
                        var id=req1.getElementsByTagName("offid")[i].firstChild.nodeValue;
                        var name=req1.getElementsByTagName("offname")[i].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                    
                  //  document.frmCurrentPosting.cmbDesignation.value=desid;
     
                }
                
//                var countss=req1.getElementsByTagName("countss")[0].firstChild.nodeValue;
//                
//              
//               // alert(countss);
//                
//                var table1=document.getElementById("gridTable");
//               
//                var row=document.createElement("tr");
//                
//                
//                var th=document.createElement("th");					  						
//    			var id=document.createTextNode("");
//                th.appendChild(id);
//            	th.setAttribute("align", "center");
//            	th.setAttribute("width", "10%");
////            	th.setAttribute("style", "border-bottom: 1px solid #000;");
//            	row.appendChild(th);
//                
//    			    var th=document.createElement("th");					  						
//    				var id=document.createTextNode("SL.NO");
//    	            th.appendChild(id);
//    	        	th.setAttribute("align", "center");
//    	        	th.setAttribute("width", "10%");
////    	        	th.setAttribute("style", "border-bottom: 1px solid #000;");
//    	        	row.appendChild(th);
//    	        	
//    	        	var th=document.createElement("th");					  						
//    				var id=document.createTextNode("Designation Name");
//    	            th.appendChild(id);
//    	        	th.setAttribute("align", "center");
//    	        	th.setAttribute("width", "30%");
////    	        	th.setAttribute("style", "border-bottom: 1px solid #000;");
//    	        	row.appendChild(th);
//    	        	
//    	        	var th=document.createElement("th");					  						
//    				var id=document.createTextNode("Office Name");
//    	            th.appendChild(id);
//    	        	th.setAttribute("align", "center");
//    	        	th.setAttribute("width", "30%");
////    	        	th.setAttribute("style", "border-bottom: 1px solid #000;");
//    	        	row.appendChild(th);
//    	        	
//    	        	var th=document.createElement("th");					  						
//    				var id=document.createTextNode("Date Effective From");
//    	            th.appendChild(id);
//    	        	th.setAttribute("align", "center");
//    	        	th.setAttribute("width", "30%");	  
////    	        	th.setAttribute("style", "border-bottom: 1px solid #000;");
//    	        	row.appendChild(th);
//    	        	
//    	        	var th=document.createElement("th");					  						
//    				var id=document.createTextNode("Office_ID");
//    	            th.appendChild(id);
//    	        	th.setAttribute("align", "center");
//    	        	th.setAttribute("width", "30%");	  
////    	        	th.setAttribute("style", "border-bottom: 1px solid #000;");
//    	        	row.appendChild(th);
//                
//    	        	table1.appendChild(row);
//    	        	
//    	        	
//                    var table=document.getElementById("gridTable");
//    				var child=table.childNodes;
//    			    for(var c=child.length-1;c>0;c--)
//    			    {
//    		    	table.removeChild(child[c]);
//    			    }
//                
//                
//              
//                
//               for(var i=0;i<countss;i++)
//               	{
//            	   
//            	   
//               	
//            	   var process_flow_status_id=req1.getElementsByTagName("process_flow_status_id")[i].firstChild.nodeValue;
//            	   //alert(process_flow_status_id);
//                	 var SLNO=req1.getElementsByTagName("SLNO")[i].firstChild.nodeValue;
//                    var designations=req1.getElementsByTagName("designations")[i].firstChild.nodeValue;
//                     var office_names=req1.getElementsByTagName("office_names")[i].firstChild.nodeValue;
//                    var date_effective_froms=req1.getElementsByTagName("date_effective_froms")[i].firstChild.nodeValue;
//                    
//                    
//                    var offid=req1.getElementsByTagName("off_id")[i].firstChild.nodeValue;
//                    
//                    var table1=document.getElementById("gridTable");
//						var row=document.createElement("tr");
//						row.id=i;
//                 
//                    
//                    if(process_flow_status_id=="CR")
//                    	{
//                    	
//	                    	var td=document.createElement("td");			
//	                    	var yy=document.createElement("a");
//	  			            yy.setAttribute("href", "javascript:EDIT_FN('"+i+"')");
//	    					var id=document.createTextNode("EDIT");
//	    					yy.appendChild(id);
//	    		            td.appendChild(yy);
//	    		        	td.setAttribute("align", "center");
//	//    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//	    		        	row.appendChild(td);
//                    	}
//                    if(process_flow_status_id=="FR")
//                	{
//                    	var td=document.createElement("td");					  						
//    					var id=document.createTextNode("Validated");
//    		            td.appendChild(id);
//    		        	td.setAttribute("align", "center");
////    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//    		        	row.appendChild(td);
//                	}
//                    
//                    
//                   
//     				var td=document.createElement("td");					  						
//    					var id=document.createTextNode(SLNO);
//    		            td.appendChild(id);
//    		        	td.setAttribute("align", "center");
////    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//    		        	row.appendChild(td);
//    		        	
//    		        	var td=document.createElement("td");					  						
//    					var id=document.createTextNode(designations);
//    		            td.appendChild(id);
//    		        	td.setAttribute("align", "center");
////    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//    		        	row.appendChild(td);
//    		        	
//    		        	var td=document.createElement("td");					  						
//    					var id=document.createTextNode(office_names);
//    		            td.appendChild(id);
//    		        	td.setAttribute("align", "center");
////    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//    		        	row.appendChild(td);
//    		        	
//    		        	var td=document.createElement("td");					  						
//    					var id=document.createTextNode(date_effective_froms);
//    		            td.appendChild(id);
//    		        	td.setAttribute("align", "center");
////    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//    		        	row.appendChild(td);
//    		        	
//    		        	
//    		        	var td=document.createElement("td");					  						
//    					var id=document.createTextNode(offid);
//    		            td.appendChild(id);
//    		        	td.setAttribute("align", "center");
////    		        	td.setAttribute("style", "border-bottom: 1px solid #000;");
//    		        	row.appendChild(td);
//     				
//    		        	table1.appendChild(row);
//               	}
//           	
        
        }
        



    
     function loadOfficesByType1(type)
    {
 // alert(type);
//    var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
       // alert(type);
        startwaiting(document.frmCurrentPosting) ;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req1=getTransport();
        req1.open("GET",url,true);        
        req1.onreadystatechange=function()
        {
            
             loadDesignation(req1);
        }
        if(window.XMLHttpRequest)
                        req1.send(null);
        else req1.send(); 
    }
    
    
function loadServiceGroup(val)
{
//alert(val);
       var url="../../../../../CurrentPosting_AdditionalCharge?Command=SerGroup&cmbdes="+val;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
}

function selectGroupfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        var gid=baseResponse.getElementsByTagName("sid")[0].firstChild.nodeValue;
        document.frmCurrentPosting.cmbsgroup.value=gid;
        alert(gid);
        loadOfficesByType1(gid);
    }
}
    
function checkGroup()
    {
        
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.frmCurrentPosting.cmbsgroup.focus();
            return false;
        }
    }
    
//////////////////////////////////////////////New Code////////////////////////////////
function changeStatus()
{

        var type=document.frmCurrentPosting.cmbstatus.options[document.frmCurrentPosting.cmbstatus.selectedIndex].value;
        if(type!="")
        {
            //alert(type);
             disableAll();
            if(type=='DPN')
            {
                    var id=document.getElementById("divdep");
                    id.style.display="block";
            }
            else  if(type=='WKG')
            {
                    var id=document.getElementById("divwork");
                    id.style.display="block";
            }
             else  if(type=='STU')
            {
                    var id=document.getElementById("divstudy");
                    id.style.display="block";
            }
             else  if(type=='LLV')
            {
                    var id=document.getElementById("divll");
                    id.style.display="block";
            }
             else  if(type=='SUS')
            {
                    var id=document.getElementById("divsus");
                    id.style.display="block";
            }
              else  if(type=='ABS')
            {
                    var id=document.getElementById("divabs");
                    id.style.display="block";
            }
              else  if(type=='TRT')
            {
            
                    var id=document.getElementById("divtrt");
                    id.style.display="block";
            }
              else  if(type=='UAL')
            {
                    var id=document.getElementById("divual");
                    id.style.display="block";
            }
              else  if(type=='CMW')
            {
                    var id=document.getElementById("divcmw");
                    id.style.display="block";
            }
            
            
        }
        else
        {
                disableAll();
        }

}


function disableAll()
{

    var id=document.getElementById("divwork");
    id.style.display="none";
     var id=document.getElementById("divstudy");
                    id.style.display="none";
    var id=document.getElementById("divdep");
    id.style.display="none";
    var id=document.getElementById("divll");
    id.style.display="none";
    var id=document.getElementById("divsus");
    id.style.display="none";
    var id=document.getElementById("divabs");
    id.style.display="none";
    
    var id=document.getElementById("divtrt");
    id.style.display="none";
    var id=document.getElementById("divual");
    id.style.display="none";
    var id=document.getElementById("divcmw");
    id.style.display="none";
    
     var id=document.getElementById("wing");
    id.style.display="none";

}



function processResponse(req1)
          {
            if(req1.readyState==4)
            {
                if(req1.status==200)
                {
                      //var OfficeName=document.getElementById("txtOfficeName");
                      //var OfficeId=document.getElementById("txtOfficeId");
                    
                     // alert(req1.responseTEXT);
                      var response=req1.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      

                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                          if(jobflag==true)
                             {
                        document.frmCurrentPosting.txtOffice_Id.value='';
                        document.frmCurrentPosting.txtOffice_Name.value='';
                        document.frmCurrentPosting.txtOffice_Address1.value='';
                        document.frmCurrentPosting.txtOffice_Id.focus();
                        }
                        else
                        {
                        document.frmCurrentPosting.txtDept_Id.value="";
                        document.frmCurrentPosting.txtOthOffice_Id.value="";
                        document.frmCurrentPosting.txtOthOffice_Name.value="";
                         document.frmCurrentPosting.txtOthOffice_Address1.value="";
                          document.frmCurrentPosting.txtDept_Id.focus();
                        }
                      }
                      else
                      {
                     
                                   
                     
                        document.frmCurrentPosting.txtOffice_Name.value='';
                        document.frmCurrentPosting.txtOffice_Address1.value='';
                        //document.frmCurrentPosting.txtOffice_Address2.value='';
                        //document.frmCurrentPosting.txtOffice_City.value='';
                          var value=response.getElementsByTagName("options");
                          //alert(value);
                          for(var i=0;i<value.length;i++)
                          {
                            
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                              //var officestatusid=tmpoption.getElementsByTagName("OfficeStatusId")[0].firstChild.nodeValue;
                              
                              
                             if(jobflag==true)
                             {
                               var officestatusid=tmpoption.getElementsByTagName("OfficeStatusId")[0].firstChild.nodeValue;
                             if(officestatusid=='NC')
                                {
                                    alert('The Selected Office is Closed')
                                    document.getElementById("txtOffice_Id").value="";
                                    document.getElementById("txtOffice_Id").focus();
                                }
                             else if(officestatusid=='CL')   
                                {
                                    alert('The Selected Office is Closed')
                                    document.getElementById("txtOffice_Id").value="";
                                    document.getElementById("txtOffice_Id").focus();
                                }
                            else if(officestatusid=='RD')  
                                {
                                    alert('The Selected Office is Redeployed')
                                    document.getElementById("txtOffice_Id").value="";
                                    document.getElementById("txtOffice_Id").focus();
                                }
                            else
                                {
                             
                                      document.frmCurrentPosting.txtOffice_Name.value=name;
                                      //document.frmCurrentPosting.cmbDistrict.value=district;
                                       var district=tmpoption.getElementsByTagName("district_name")[0].firstChild.nodeValue;
                             
                             if(officeAddress1=='null')
                                officeAddress1='';
                             if(officeAddress2=='null')
                                officeAddress2='';
                            if(officeAddress3=='null')
                                officeAddress3='';
                            if(district=='null')
                                district='';
                                var fulladd=officeAddress1;
                                fulladd=fulladd+'\n'+officeAddress2;
                                fulladd=fulladd+'\n'+officeAddress3;
                                fulladd=fulladd+'\n'+district;
                                      document.frmCurrentPosting.txtOffice_Address1.value=fulladd;
                                      
                                   /*   if(officeAddress2!="null")
                                      {
                                        document.frmCurrentPosting.txtOffice_Address2.value=officeAddress2;
                                      }
                                      if(officeAddress3!="null")
                                      {
                                           document.frmCurrentPosting.txtOffice_City.value=officeAddress3;
                                     }*/
                                     
                                      /************************************/
                                     
                                      GCHECK='no';
                                     var wing=response.getElementsByTagName("WING")[0].firstChild.nodeValue;
                                    // alert(wing);
                                    if(wing == 'yes')
                                    {
                                    
                                         GCHECK=wing;
                                      var id=document.getElementById("wing");
                                      id.style.display="none";
                                      
                                      
                                      /* wind combo  */
                                    
                                        var des=document.getElementById("cmbWing");
                                        des.innerHTML="";
                                        var i=0;
                                        
                                       
                                         
                                        
                                            var value=response.getElementsByTagName("wingoptions");
                                            var option=document.createElement("OPTION");
                                            option.text="Select Wing";
                                            option.value="0";
                                            try
                                            {
                                                des.add(option);
                                            }catch(errorObject)
                                            {
                                                des.add(option,null);
                                            }
                                            for(var i=0;i<value.length;i++)
                                            {
                                                var tmpoption=value.item(i);
                                                var id=tmpoption.getElementsByTagName("wingid")[0].firstChild.nodeValue;
                                                var name=tmpoption.getElementsByTagName("wingname")[0].firstChild.nodeValue;
                                                var option=document.createElement("OPTION");
                                                  option.text=name;
                                                  option.value=id;
                                                  //Making Browser Independent
                                                  try
                                                  {
                                                      des.add(option);
                                                  }
                                                  catch(errorObject)
                                                  {
                                                      des.add(option,null);
                                                  }
                                            }
                                            document.frmCurrentPosting.cmbWing.value=GWING;
                                                              
                                      /*wing combo */
                                      
                                    }
                                    else{
                                    var id=document.getElementById("wing");
                                      id.style.display="none";
                                    }
                                     
                                     /**************************************/
                                     
                                 }    
                                     
                            }  
                             else
                             {
                               //alert(name);
                               document.frmCurrentPosting.txtOthOffice_Name.value=name;
                                      //document.frmCurrentPosting.cmbDistrict.value=district;
                                    
                             if(officeAddress1=='null')
                                officeAddress1='';
                             if(officeAddress2=='null')
                                officeAddress2='';
                            if(officeAddress3=='null')
                                officeAddress3='';
                             var fulladd=officeAddress1;
                                fulladd=fulladd+'\n'+officeAddress2;
                                fulladd=fulladd+'\n'+officeAddress3;
                              
                                      document.frmCurrentPosting.txtOthOffice_Address1.value=fulladd;
                                     
                                     /* if(officeAddress2!="null")
                                      {
                                        document.frmCurrentPosting.txtOthOffice_Address2.value=officeAddress2;
                                      }
                                      if(officeAddress3!="null")
                                      {
                                           document.frmCurrentPosting.txtOthOffice_City.value=officeAddress3;
                                     }*/
                            }    
                             
                             
                          }
                          
                      }   
                     

            }
        }
    }



function EDIT_FN(i)
{
	
	var empid1=document.getElementById(i).cells.item(2).firstChild.nodeValue;
	
	var slno=document.getElementById(i).cells.item(1).firstChild.nodeValue;
	var empid=empid1+","+slno;
  doFunction('loadempview',empid);
//  startwaiting(document.frmCurrentPosting) ;
//  service=null;
//  var url="../../../../../CurrentPosting_AdditionalCharge?Command=loadempeditss&txtEmployeeid="+empid+"&offid="+offid+"&offname="+offname;
//  req.open("GET",url,true);
//  req.onreadystatechange=handleResponse;
//  if(window.XMLHttpRequest)
//          req.send(null);
//  else req.send();  
  
  
}


function getDesignation1()
{
    var type=document.frmCurrentPosting.cmbsgroup1.options[document.frmCurrentPosting.cmbsgroup1.selectedIndex].value;
    if(type!=0)
    {
       // var din=document.getElementById("divdes");
        //din.style.visibility="visible";
        //document.frmCurrentPosting.cmbdes.style.visibility="visible";
        loadOfficesByType1(type);
    }
    else
    {
       var des=document.getElementById("cmbDesignation1");
       des.innerHTML='';
    }
}    
function loadOfficesByType1(type)
{
    //alert(type);
    var type=document.frmCurrentPosting.cmbsgroup1.options[document.frmCurrentPosting.cmbsgroup1.selectedIndex].value;
    startwaiting(document.frmCurrentPosting) ;
   var url="../../../../../EmpServicePopupServ.view?Command=SGroup1&cmbsgroup=" + type ;
    var req=getTransport();
    req.open("GET",url,true);        
    req.onreadystatechange=function()
    {
        
         loadDesignation1(req);
    }
     if(window.XMLHttpRequest)
                    req.send(null);
            else req.send();
}

function  loadDesignation1(req1)
{
     if(req1.readyState==4)
        {
          if(req1.status==200)
          { 
                //alert(req1);            
                var response=req1.responseXML.getElementsByTagName("response")[0];
                //alert(response);
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //des.innerHTML="";
                //alert('test');
                var des=document.getElementById("cmbDesignation1");
                des.innerHTML="";
                var i=0;
                 stopwaiting(document.frmCurrentPosting);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="Select Designation";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}
function processResponse1(req1){

    if(req1.readyState==4)
    {
        if(req1.status==200)
        {
              //var OfficeName=document.getElementById("txtOfficeName");
              //var OfficeId=document.getElementById("txtOfficeId");
            
              
              var response=req1.responseXML.getElementsByTagName("response")[0];
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
              if(flag=="failure")
              {
                 alert("Please Enter the valid Office ID");
                  if(jobflag==true)
                     {
                document.frmCurrentPosting.txtOffice_Id1.value='';
                document.frmCurrentPosting.txtOffice_Name1.value='';
                document.frmCurrentPosting.txtOffice_Address11.value='';
                document.frmCurrentPosting.txtOffice_Id1.focus();
                }
                else
                {
                document.frmCurrentPosting.txtDept_Id1.value="";
                document.frmCurrentPosting.txtOthOffice_Id1.value="";
                document.frmCurrentPosting.txtOthOffice_Name1.value="";
                 document.frmCurrentPosting.txtOthOffice_Address11.value="";
                  document.frmCurrentPosting.txtDept_Id1.focus();
                }
              }
              else if(flag=="sessionout")
    {
        alert('Session is closed');
        //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
        self.close();
        return;
    }
              else
              {
             
                document.frmCurrentPosting.txtOffice_Name1.value='';
                document.frmCurrentPosting.txtOffice_Address11.value='';
               // document.frmCurrentPosting.txtOffice_Address2.value='';
               // document.frmCurrentPosting.txtOffice_City.value='';
                  var value=response.getElementsByTagName("options");
                  //alert(value);
                  for(var i=0;i<value.length;i++)
                  {
                    
                      var tmpoption=value.item(i);
                      var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                     var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                     var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                      var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                      var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                     // var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                     
                     if(jobflag==true)
                     {
                    	 
                              document.frmCurrentPosting.txtOffice_Name1.value=name;
                              //document.frmCurrentPosting.cmbDistrict.value=district;
                              var district=tmpoption.getElementsByTagName("district_name")[0].firstChild.nodeValue;
                             
                     
                     if(officeAddress1=='null')
                        officeAddress1='';
                     if(officeAddress2=='null')
                        officeAddress2='';
                    if(officeAddress3=='null')
                        officeAddress3='';
                    if(district=='null')
                        district='';
                        var fulladd=officeAddress1;
                        fulladd=fulladd+'\n'+officeAddress2;
                        fulladd=fulladd+'\n'+officeAddress3;
                        fulladd=fulladd+'\n'+district;
                              document.frmCurrentPosting.txtOffice_Address11.value=fulladd;
                           /*   if(officeAddress1!="null")
                              {
                              document.frmCurrentPosting.txtOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmCurrentPosting.txtOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                   document.frmCurrentPosting.txtOffice_City.value=officeAddress3;
                             }*/
                             
                              /************************************/
                             
                             
                             var wing=response.getElementsByTagName("WING")[0].firstChild.nodeValue;
                            // alert(wing);
                            if(wing == 'yes')
                            {
                              var id=document.getElementById("wing1");
                              id.style.display="none";
                              
                              
                              /* wind combo  */
                            
                                var des=document.getElementById("cmbWing1");
                                des.innerHTML="";
                                var i=0;
                                
                               
                                 
                                
                                    var value=response.getElementsByTagName("wingoptions");
                                    var option=document.createElement("OPTION");
                                    option.text="Select Wing";
                                    option.value="0";
                                    try
                                    {
                                        des.add(option);
                                    }catch(errorObject)
                                    {
                                        des.add(option,null);
                                    }
                                    for(var i=0;i<value.length;i++)
                                    {
                                        var tmpoption=value.item(i);
                                        var id=tmpoption.getElementsByTagName("wingid")[0].firstChild.nodeValue;
                                        var name=tmpoption.getElementsByTagName("wingname")[0].firstChild.nodeValue;
                                        var option=document.createElement("OPTION");
                                          option.text=name;
                                          option.value=id;
                                          //Making Browser Independent
                                          try
                                          {
                                              des.add(option);
                                          }
                                          catch(errorObject)
                                          {
                                              des.add(option,null);
                                          }
                                    }
                                    document.frmCurrentPosting.cmbWing1.value=GWING;
                                                      
                              /*wing combo */
                              
                            }
                            else{
                            var id=document.getElementById("wing1");
                              id.style.display="none";
                            }
                            
                            
                             
                             /**************************************/
                             
                    }  
                     else
                     {
                              document.frmCurrentPosting.txtOthOffice_Name1.value=name;
                              //document.frmCurrentPosting.cmbDistrict.value=district;
                              
                           
                     if(officeAddress1=='null')
                        officeAddress1='';
                     if(officeAddress2=='null')
                        officeAddress2='';
                    if(officeAddress3=='null')
                        officeAddress3='';
                   // if(district=='null')
                     //   district='';
                        var fulladd=officeAddress1;
                        fulladd=fulladd+'\n'+officeAddress2;
                        fulladd=fulladd+'\n'+officeAddress3;
                       // fulladd=fulladd+'\n'+district;
                              document.frmCurrentPosting.txtOffice_Address11.value=fulladd;
                             /* if(officeAddress1!="null")
                              {
                              document.frmCurrentPosting.txtOthOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmCurrentPosting.txtOthOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                   document.frmCurrentPosting.txtOthOffice_City.value=officeAddress3;
                             }*/
                    }    
                     
                     
                  }
                 // return callCheckPost();
              }   
              var postexists=response.getElementsByTagName("xists")[0].firstChild.nodeValue;
              if(postexists=='yes')
                alert('Employee Id already exists in this post..');
             
              


    }
}

}

function design_id()
{
	des_id=document.getElementById("cmbDesignation1").value;
}