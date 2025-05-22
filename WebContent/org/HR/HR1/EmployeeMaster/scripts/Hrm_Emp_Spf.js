var winemp;
var seq = 0;
var desid;
var miss_entry_sl_no=new Array();
var frm_yr = new Array();
var frm_mn = new Array();
var to_yr = new Array();
var to_mn = new Array();
var no_mth = new Array();
var office_ids = new Array();
var rmks = new Array();





var opted_y_n=new Array();
var com_yr=new Array();
var com_mth=new Array();
var recove_y_n=new Array();
var recove_com_yr=new Array();
var recove_com_mth=new Array();
var missing_entry=new Array();



var url="";

var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");


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
   
    function Ctrloffpopup()
    {	
        if (winemp && winemp.open && !winemp.closed) 
        {
           winemp.resizeTo(500,600);
           winemp.moveTo(200,200); 
           winemp.focus();
           return ;
        }
          // alert('test11111');     
        winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/Emp_Ctrl_Officepopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }
 
    function doParentEmp(emp,name,designation)
    {
        document.getElementById("emp_id").value=emp;
        loademp();
        closeWin();  
    }
    var winjob;
    function closeWin()
    {
    if (winemp && winemp.open && !winemp.closed) winemp.close();
    }
    function jobpopup()
    {
    	//alert("hi karhik")
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
           return;
        }
        else
        {
            winjob=null
        }
            
        winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
        winjob.moveTo(250,250);  
        winjob.focus();
        
    }
    function doParentJob(jobid,deptid)
    {
       
        document.Hrm_Emp_Spf.off_id.value=jobid;
       
        return true;
    }
//************list all *************************
 
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

function loadMonth()
{

	var comm_year=document.Hrm_Emp_Spf.comm_year.value;
	
	var length=comm_year.length;
	
	var comm_month=document.getElementById("comm_month");
	var child=comm_month.childNodes;
	   for(var c=child.length-1;c>1;c--)
	   {
		   comm_month.removeChild(child[c]);
	   }
	
	   
	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
  
     
     
    if(comm_year!=year){
    	       month=12;
    	    
    }
       
    
    if(comm_year>1979)
    {
    	//alert(month)
    	for(i=0;i<=month-1;i++)
    	
	         //for(i=month-1;i>=0;i--)
	           {
	        	// alert(i)
			        var opt =document.createElement("option"); 
			        var text=document.createTextNode(monthNames[i]);
			        
			        opt.setAttribute("value",i+1);
			        opt.appendChild(text);
			        comm_month.appendChild(opt);
	       
	           }
	 
    }
    document.getElementById("comm_month").value="";
}
function load_month1()
{
	


	var recov_year=document.Hrm_Emp_Spf.recov_year.value;
	
	var length=recov_year.length;
	
	var recov_month=document.getElementById("recov_month");
	var child=recov_month.childNodes;
	   for(var c=child.length-1;c>1;c--)
	   {
		   recov_month.removeChild(child[c]);
	   }
	
	   
	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
  
     
     
    if(recov_year!=year){
    	       month=12;
    	    
    }
       
    
    if(recov_year>1979)
    {
      // if(comm_year != 1990)
         // {
    	for(i=0;i<=month-1;i++)
	        // for(i=month-1;i>=0;i--)
	           {
			        var opt =document.createElement("option"); 
			        var text=document.createTextNode(monthNames[i]);
			        
			        opt.setAttribute("value",i+1);
			        opt.appendChild(text);
			        recov_month.appendChild(opt);
	       
	           }
    }
    document.getElementById("recov_month").value="";

}

function load_month2()
{

	


	var from_year=document.Hrm_Emp_Spf.from_year.value;
	
	var length=from_year.length;
	
	var from_month=document.getElementById("from_month");
	var child=from_month.childNodes;
	   for(var c=child.length-1;c>1;c--)
	   {
		   from_month.removeChild(child[c]);
	   }
	
	   
	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
  
     
     
    if(from_year!=year){
    	       month=12;
    	    
    }
       
    
    if(from_year>1979)
    {
    	for(i=0;i<=month-1;i++)
	        // for(i=month-1;i>=0;i--)
	           {
			        var opt =document.createElement("option"); 
			        var text=document.createTextNode(monthNames[i]);
			        
			        opt.setAttribute("value",i+1);
			        opt.appendChild(text);
			        from_month.appendChild(opt);
	       
	           }
	 
    }
    document.getElementById("recov_month").value="";



}
function load_month3()
{


	


	var to_year=document.Hrm_Emp_Spf.to_year.value;
	
	var length=to_year.length;
	
	var to_month=document.getElementById("to_month");
	var child=to_month.childNodes;
	   for(var c=child.length-1;c>1;c--)
	   {
		   to_month.removeChild(child[c]);
	   }
	
	   
	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
  
     
     
    if(to_year!=year){
    	       month=12;
    	    
    }
       
    
    if(to_year>1979)
    {
    	for(i=0;i<=month-1;i++)
	         //for(i=month-1;i>=0;i--)
	           {
			        var opt =document.createElement("option"); 
			        var text=document.createTextNode(monthNames[i]);
			        
			        opt.setAttribute("value",i+1);
			        opt.appendChild(text);
			        to_month.appendChild(opt);
	       
	           }
	 
    }
    document.getElementById("recov_month").value="";





}

function addss(cmdval) {

	var val = nullcheck();
	if (val == true) {

		var j = 0;
		if (cmdval == 0) {
			j = frm_yr.length;
		} else {
			 //alert(z);

			j = z;

		}
		
		miss_entry_sl_no[j]=document.getElementById("miss_sel_no").value;
		frm_yr[j] = document.getElementById("from_year").value;

		frm_mn[j] = document.getElementById("from_month").value;
		to_yr[j] = document.getElementById("to_year").value;
		to_mn[j] = document.getElementById("to_month").value;
		no_mth[j] = document.getElementById("no_of_mths").value;
		office_ids[j] = document.getElementById("off_id").value;
		rmks[j] = document.getElementById("Remarks").value;
		

		grid();
		//loadMon1();

	}

}

function grid() {
	z = 0;

	try {
		document.getElementById("grid").innerHTML = "";
	} catch (e) {
		document.getElementById("grid").innerTEXT = "";
	}

	var tbody = document.getElementById("grid");

	for ( var i = 0; i < no_mth.length; i++) {

		var tr = document.createElement("tr");

		var td = document.createElement("td");
		var text = document.createTextNode("EDIT");
		var a = document.createElement("a");
		a.setAttribute("href", "#");
		a.setAttribute("onclick", "loadvalu(" + i + ")");
		a.appendChild(text);
		td.appendChild(a);
		tr.appendChild(td);

		
		//monthNames[i]=frm_mn[i];
		/*var td = document.createElement("td");
		td.innerHTML = frm_yr[i];
		tr.appendChild(td);*/
		
		
		
		var td = document.createElement("td");
		td.innerHTML =miss_entry_sl_no[i];
		tr.appendChild(td);


		var td = document.createElement("td");
		td.innerHTML = frm_yr[i] + '-' + frm_mn[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = to_yr[i] + '-' + to_mn[i];
		tr.appendChild(td);

		/*var td = document.createElement("td");
		td.innerHTML = to_mn[i];
		tr.appendChild(td);*/

		var td = document.createElement("td");
		td.innerHTML = no_mth[i];
		tr.appendChild(td);

		var td = document.createElement("td");
		td.innerHTML = office_ids[i];
		tr.appendChild(td);
		
		var td = document.createElement("td");
		td.innerHTML = rmks[i];
		tr.appendChild(td);

		tbody.appendChild(tr);
	}

	
	document.getElementById("miss_sel_no").value = "";
	document.getElementById("from_year").selectedIndex = 0;
	document.getElementById("from_month").selectedIndex = 0;
	document.getElementById("to_year").selectedIndex = 0;
	document.getElementById("to_month").selectedIndex = 0;
	document.getElementById("no_of_mths").value = "";
	document.getElementById("off_id").value = "";
	//document.getElementById("off_id").selectedIndex = 0;
	document.getElementById("Remarks").value = "";

	document.getElementById('cmdupdate').style.display = 'none';
	document.getElementById('cmdadd').style.display = "";
	/*document.getElementById('Policy_no').readOnly = false;
	document.getElementById('upd').style.display = 'none';
	document.getElementById('del').style.display = 'none';
	document.getElementById('add1').disabled = false;
	document.getElementById('ins').disabled = false;
	document.getElementById('delete').disabled = false;
	document.getElementById('add1').style.display = "";

	var ln = che_month.length;
	for ( var i = 0; i < ln; i++) {
		try {
			che_month.pop();

		} catch (a) {

		}
	}
	Monthclose();*/

}
function loadvalu(i) {
	//loadMon1();

	z = i;
	
	document.getElementById("miss_sel_no").value = miss_entry_sl_no[i];
	
	
	document.getElementById("from_year").value = frm_yr[i];
	document.getElementById("from_month").value = frm_mn[i];

	document.getElementById("to_year").value = to_yr[i];
	document.getElementById("to_month").value = to_mn[i];
	
	
	document.getElementById("no_of_mths").value = no_mth[i];
	document.getElementById("off_id").value = office_ids[i];
	document.getElementById("Remarks").value = rmks[i];
	
	document.getElementById('cmdupdate').style.display = "";
	document.getElementById('cmdadd').style.display = "none";

}
function loademp()
{
	var val = 0;
	clearAll1();
	try 
	{
		document.getElementById("grid").innerHTML = "";
	} catch (e) {
		document.getElementById("grid").innerTEXT = "";
	}
	var emp_id=document.getElementById("emp_id").value;
	    
	
	miss_entry_sl_no.splice(val, 1);
	frm_yr.splice(val, 1);
	frm_mn.splice(val, 1);
	to_yr.splice(val, 1);
	to_mn.splice(val, 1);
	no_mth.splice(val, 1);
	office_ids.splice(val, 1);
	rmks.splice(val, 1);
	
	
	
	
	url="../../../../../Hrm_Emp_Spf?Command=loademp&emp_id="+emp_id;
	//alert(url);
	var req=getTransport();
    req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
	    {
	      if(req.status==200)
	      { 
	    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	    	  var tagCommand=baseResponse.getElementsByTagName("command")[0];
	          var command=tagCommand.firstChild.nodeValue; 
	          if(command=="loademp")
	          {
	        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	              if(flag=="success")
	              { 
	            	  var empname=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
							var designation=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
							//var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
							var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
							
							document.getElementById("emp_name").value=empname;
							document.getElementById("desig_id").value=designation;
							document.getElementById("off_name").value=office_name;
						
							get_masterdata();
	              }
	              else if(flag=="failure")
					{
					alert("Please Enter Valid Employee Id");
					document.getElementById("emp_id").value="";
					//document.getElementById("emp_name").value="";
					//document.getElementById("desig").value="";
					//document.getElementById("dob").value="";
					}
					else if(flag=="failure1")
					{
					alert("Given Employee Id is not in your controlling office");
					document.getElementById("emp_id").value="";
					document.getElementById("emp_name").value="";
					document.getElementById("desig_id").value="";
					document.getElementById("off_name").value="";
				
					//document.getElementById("emp_id").focus();
					//document.getElementById("emp_name").value="";
					//document.getElementById("desig").value="";
					//document.getElementById("dob").value="";
					}
	          }
	      }
	    }
	};
			
req.send(null)
}
function get_masterdata()
{

	
	var emp_id=document.getElementById("emp_id").value;

	var url="../../../../../Hrm_Emp_Spf?Command=get_masterdata&emp_id="+emp_id;
//alert("url--->"+url)
	var req=getTransport();
	    req.open("POST",url,true);
	    req.onreadystatechange=function()
	    {
			if(req.readyState==4)
		    {
		      if(req.status==200)
		      { 
		    	// alert(req.responseText)
		    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		    	  var tagCommand=baseResponse.getElementsByTagName("command")[0];
		          var command=tagCommand.firstChild.nodeValue; 
		          if(command=="get_masterdata")
		          {
		        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
		              if(flag=="success")
		              { 
		            	  var COMMENCEMENT_YEAR=baseResponse.getElementsByTagName("COMMENCEMENT_YEAR")[0].firstChild.nodeValue;
								var COMMENCEMENT_MONTH=baseResponse.getElementsByTagName("COMMENCEMENT_MONTH")[0].firstChild.nodeValue;
								var RECOVERY_COMPLETED=baseResponse.getElementsByTagName("RECOVERY_COMPLETED")[0].firstChild.nodeValue;
								var COMPLETION_YEAR=baseResponse.getElementsByTagName("COMPLETION_YEAR")[0].firstChild.nodeValue;
								var COMPLETION_MONTH=baseResponse.getElementsByTagName("COMPLETION_MONTH")[0].firstChild.nodeValue;
								var MISSING_ENTRIES=baseResponse.getElementsByTagName("MISSING_ENTRIES")[0].firstChild.nodeValue;
								var OPTED_FOR_SPF=baseResponse.getElementsByTagName("OPTED_FOR_SPF")[0].firstChild.nodeValue;
								
								
								if(OPTED_FOR_SPF=='N')
					        	 {
					        	 	document.Hrm_Emp_Spf.opt[1].checked=true;
					        	 	
					        	 	check_emp();
					        	 }
								 if(RECOVERY_COMPLETED=='N')
					        	 {
									 document.Hrm_Emp_Spf.rec_com[1].checked=true;
				                     get_rec_comp();
				                   
					        	 }
								
								if(OPTED_FOR_SPF=='Y')
					        	 {
					        	 	document.Hrm_Emp_Spf.opt[0].checked=true;
				                    document.getElementById("comm_year").value=COMMENCEMENT_YEAR;
				                    loadMonth();
				                    document.getElementById("comm_month").value=COMMENCEMENT_MONTH;
					        	 }
					         if(RECOVERY_COMPLETED=='Y')
					        	 {
					        	    document.Hrm_Emp_Spf.rec_com[0].checked=true;
				                    document.getElementById("recov_year").value=COMPLETION_YEAR;
				                    load_month1();
				                    document.getElementById("recov_month").value=COMPLETION_MONTH;
					        	 }
					         if(MISSING_ENTRIES=='Y')
					    	 {
					    	   document.Hrm_Emp_Spf.miss_entry[0].checked=true;
					    	 }
					         else
					        	 {
					        	 document.Hrm_Emp_Spf.miss_entry[1].checked=true;
					        	 get_mis_det_n();
					        	 }
								getdata();
		              }
		              else if(flag=="failure")
		            	  {
		            	  
		            	    alert("Employee Not in Master");
		            	    document.getElementById("emp_id").value="";
		            	    return false;
		            	  }
		          }
		      }
		    }
		}
	    req.send(null);


}
function handRep1(req)
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
function getdata()
{
	
var emp_id=document.getElementById("emp_id").value;

var url="../../../../../Hrm_Emp_Spf?Command=getdata&emp_id="+emp_id;

var req=getTransport();
    req.open("POST",url,true);
    req.onreadystatechange=function()
    {
    	
    	 handRep(req);
    }
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
	var month=new Array('January','February','March','April','May','June','July','August','September','October','November','December');
    var tlist = document.getElementById("grid");
    try 
    {
        tlist.innerHTML = "";
    } 
    catch (e)
    {
        tlist.innerText = "";
    }
    if(baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue=="success")
    {
    	get_mis_det_y();
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
					var OPTED_FOR_SPF = baseResponse.getElementsByTagName("OPTED_FOR_SPF")[i].firstChild.nodeValue;	
					var COMMENCEMENT_YEAR = baseResponse.getElementsByTagName("COMMENCEMENT_YEAR")[i].firstChild.nodeValue;	
					var COMMENCEMENT_MONTH = baseResponse.getElementsByTagName("COMMENCEMENT_MONTH")[i].firstChild.nodeValue;	
					var RECOVERY_COMPLETED = baseResponse.getElementsByTagName("RECOVERY_COMPLETED")[i].firstChild.nodeValue;	
					var COMPLETION_YEAR = baseResponse.getElementsByTagName("COMPLETION_YEAR")[i].firstChild.nodeValue;	
					var COMPLETION_MONTH = baseResponse.getElementsByTagName("COMPLETION_MONTH")[i].firstChild.nodeValue;	
					var MISSING_ENTRIES = baseResponse.getElementsByTagName("MISSING_ENTRIES")[i].firstChild.nodeValue;	
					var PROCESS_FLOW_ID = baseResponse.getElementsByTagName("PROCESS_FLOW_ID")[i].firstChild.nodeValue;
					
					var MISSING_ENTRY_SLNO=baseResponse.getElementsByTagName("MISSING_ENTRY_SLNO")[i].firstChild.nodeValue;		
					var FROM_YEAR = baseResponse.getElementsByTagName("from_year")[i].firstChild.nodeValue;
					var FROM_MONTH = baseResponse.getElementsByTagName("FROM_MONTH")[i].firstChild.nodeValue;
					var TO_YEAR = baseResponse.getElementsByTagName("TO_YEAR")[i].firstChild.nodeValue;
					var TO_MONTH = baseResponse.getElementsByTagName("TO_MONTH")[i].firstChild.nodeValue;	
					var NO_OF_MONTHS = baseResponse.getElementsByTagName("NO_OF_MONTHS")[i].firstChild.nodeValue;
					var OFFICE_ID = baseResponse.getElementsByTagName("OFFICE_ID")[i].firstChild.nodeValue;
					var PROCESS_FLOW_ID = baseResponse.getElementsByTagName("PROCESS_FLOW_ID")[i].firstChild.nodeValue;
					var REMARKS=baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
					
	
					
					 if(EMPLOYEE_ID==null || EMPLOYEE_ID=="null")
						 {
						 EMPLOYEE_ID="";
						 }
					 if(FROM_YEAR==null || FROM_YEAR=="null")
					 {
						 FROM_YEAR="";
					 }
					 if(FROM_MONTH==null || FROM_MONTH=="null")
					 {
						 FROM_MONTH="";
					 }
					 if(TO_YEAR==null || TO_YEAR=="null")
					 {
						 TO_YEAR="";
					 }
					 if(TO_MONTH==null || TO_MONTH=="null")
					 {
						 TO_MONTH="";
					 }
					 if(NO_OF_MONTHS==null || NO_OF_MONTHS=="null")
					 {
						 NO_OF_MONTHS="";
					 }
					 
					 if(REMARKS==null || REMARKS=="null")
					 {
						 REMARKS="";
					 }
					 if(OFFICE_ID==null || OFFICE_ID=="null")
					 {
						 OFFICE_ID="";
					 }
					 if(MISSING_ENTRY_SLNO==null || MISSING_ENTRY_SLNO=="null")
						 {
						    MISSING_ENTRY_SLNO="";
						 }
					 
					 
					  frm_yr[i]=FROM_YEAR;
					  frm_mn[i]=FROM_MONTH;
					  to_yr[i]=TO_YEAR;
					  to_mn[i]=TO_MONTH;
					  no_mth[i]=NO_OF_MONTHS;
					  office_ids[i]=OFFICE_ID;
					  rmks[i]=REMARKS;
					  
					  
					  
					  
					   opted_y_n[i]=OPTED_FOR_SPF;
					   com_yr[i]=COMMENCEMENT_YEAR;
					   com_mth[i]=COMMENCEMENT_MONTH;
					   recove_y_n[i]=RECOVERY_COMPLETED;
					   recove_com_yr[i]=COMPLETION_YEAR;
					   recove_com_mth[i]=COMPLETION_MONTH;
					   missing_entry[i]=MISSING_ENTRIES;
					  
					   miss_entry_sl_no[i]=MISSING_ENTRY_SLNO;
					
					  
					   
					   FROM_MONTH=month[FROM_MONTH-1];
					   TO_MONTH=month[TO_MONTH-1];
					   
					   
			
						var frm_ye_frm_mth=FROM_YEAR + "-" +FROM_MONTH;
					    var to_yrm_to_mth=TO_YEAR + "-"+TO_MONTH;
					  
					  
					    var tr = document.createElement("TR");
				        tr.id = seq;
				    
				        var tr = document.createElement("TR");
				        tr.id = seq;
				        var td = document.createElement("TD");
				        //var anc = document.createElement("A");
				        
				        
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
				 
				        
						       
								var text = document.createTextNode("EDIT");
								var a = document.createElement("a");
								a.setAttribute("href", "#");
								a.setAttribute("onclick", "loadva(" + i + ")");
								a.appendChild(text);
								td.appendChild(a);
								tr.appendChild(td);
				        	}
		
		      
				
						 var td = document.createElement("TD");
					     var MISSING_ENTRY_SLNO = document.createTextNode(MISSING_ENTRY_SLNO);
					     td.appendChild(MISSING_ENTRY_SLNO);
					     td.setAttribute("style", "color: #330099;");
					     tr.appendChild(td);
						
		     
					     var td = document.createElement("TD");
					     var FROM_YEAR = document.createTextNode(frm_ye_frm_mth);
					     td.appendChild(FROM_YEAR);
					     td.setAttribute("style", "color: #330099;");
					     tr.appendChild(td);
					     
					     
					     var td = document.createElement("TD");
					     var FROM_MONTH = document.createTextNode(FROM_MONTH);
					     td.appendChild(FROM_MONTH);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     var td = document.createElement("TD");
					     var TO_MONTH = document.createTextNode(TO_MONTH);
					     td.appendChild(TO_MONTH);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     
					     var td = document.createElement("TD");
					     var FROM_MONTH = document.createTextNode(to_yrm_to_mth);
					     td.appendChild(FROM_MONTH);
					     td.setAttribute("style", "color: #330099;");
					     tr.appendChild(td);
					     
					  
					
					     var td = document.createElement("TD");
					     var NO_OF_MONTHS = document.createTextNode(NO_OF_MONTHS);
					     td.setAttribute("style", "color: #330099;");
					     td.appendChild(NO_OF_MONTHS);
					     tr.appendChild(td);
					      
					     var td = document.createElement("TD");
					     var OFFICE_ID = document.createTextNode(OFFICE_ID);
					     td.setAttribute("style", "color: #330099;");
					     td.appendChild(OFFICE_ID);
					     tr.appendChild(td);
		
		    
		    
				         var td = document.createElement("TD");
				         var REMARKS = document.createTextNode(REMARKS);
				         td.setAttribute("style", "color: #330099;");
				         td.appendChild(REMARKS);
				         tr.appendChild(td);
				         
				        
				         
				         
				         
				         var td = document.createElement("TD");
					     OPTED_FOR_SPF = document.createTextNode(OPTED_FOR_SPF);
					     td.appendChild(OPTED_FOR_SPF);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     
					     var td = document.createElement("TD");
					     COMMENCEMENT_YEAR = document.createTextNode(COMMENCEMENT_YEAR);
					     td.appendChild(COMMENCEMENT_YEAR);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     
					     var td = document.createElement("TD");
					     COMMENCEMENT_MONTH = document.createTextNode(COMMENCEMENT_MONTH);
					     td.appendChild(COMMENCEMENT_MONTH);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     
					     var td = document.createElement("TD");
					     RECOVERY_COMPLETED = document.createTextNode(RECOVERY_COMPLETED);
					     td.appendChild(RECOVERY_COMPLETED);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     var td = document.createElement("TD");
					     COMPLETION_YEAR = document.createTextNode(COMPLETION_YEAR);
					     td.appendChild(COMPLETION_YEAR);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     var td = document.createElement("TD");
					     COMPLETION_MONTH = document.createTextNode(COMPLETION_MONTH);
					     td.appendChild(COMPLETION_MONTH);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
					     var td = document.createElement("TD");
					     MISSING_ENTRIES = document.createTextNode(MISSING_ENTRIES);
					     td.appendChild(MISSING_ENTRIES);
					     td.setAttribute("style", "display:none");
					     tr.appendChild(td);
					     
				            
				           
				         tlist.appendChild(tr);
				 
				         seq++;
		       
				       
				        
            }
	 }
  else  
  {
	 var iframe=document.getElementById("grid");
     iframe.focus();
	 if(navigator.appName.indexOf('Microsoft')!=-1)
		 {
		 
         iframe.innerHTML="<tr><td align=center colspan=10>There is No Data to Display</td></tr>";
        
		 }
     else
    	 {
    	 
         iframe.innerText="There is No Data to Display";
     iframe.innerHTML="<tr bgcolor='b6eaff' ><td align=center colspan=10>There is No Data to Display</td></tr>";
    
    	 }
    }
    }
}

function loadva(i) {
	
	 
	z=i;
	 
	
	document.getElementById("miss_sel_no").value=miss_entry_sl_no[i];
	
	
	 if(opted_y_n[i]=='Y')
		 {
		   document.Hrm_Emp_Spf.opt[0].checked=true;
		 }
	 else if(opted_y_n[i]=='N')
		 {
		    document.Hrm_Emp_Spf.opt[1].checked=true;
		    check_emp();
		 }
	 
	 document.getElementById("comm_year").value=com_yr[i];
	        loadMonth();
	 document.getElementById("comm_month").value=com_mth[i];
	 
	

	 if(recove_y_n[i]=='Y')
		 {
		   document.Hrm_Emp_Spf.rec_com[0].checked=true;
		 }
	 else if(recove_y_n[i]=='N')
		 {
		
		    document.Hrm_Emp_Spf.rec_com[1].checked=true;
		    get_rec_comp();
		 }
	 
	 
	 document.getElementById("recov_year").value=recove_com_yr[i];
	        load_month1();
	 document.getElementById("recov_month").value=recove_com_mth[i];
	 
	 
	 if(missing_entry[i]=='Y')
	 {
	   document.Hrm_Emp_Spf.miss_entry[0].checked=true;
	 }
   else if(missing_entry[i]=='N')
	 {
	    document.Hrm_Emp_Spf.miss_entry[1].checked=true;
	 }
	 
	  
	
	
	
	document.getElementById("from_year").value = frm_yr[i];
	       load_month2();
	document.getElementById("from_month").value = frm_mn[i];
	document.getElementById("to_year").value = to_yr[i];
	       load_month3();
	
	document.getElementById("to_month").value = to_mn[i];
	document.getElementById("no_of_mths").value = no_mth[i];
	document.getElementById("off_id").value = office_ids[i];
	document.getElementById("Remarks").value = rmks[i];
	document.getElementById('cmdadd').style.display = "none";
	document.getElementById('cmdupdate').style.display = "";
}
function check_emp()
{
   //hide_comm_det();
	var emp_id=document.getElementById("emp_id").value;
	document.getElementById("comm_year").value="";
	document.getElementById("comm_month").value="";
	url="../../../../../Hrm_Emp_Spf?Command=check_emp&emp_id="+emp_id;
	//alert(url)
	var req=getTransport();
    req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
	    {
	      if(req.status==200)
	      { 
		    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		    	  var tagCommand=baseResponse.getElementsByTagName("command")[0];
		          var command=tagCommand.firstChild.nodeValue; 
	          if(command=="check_emp")
	          {
	        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	              if(flag=="exist")
	              { 
		            	  alert("employee already have record");
		            	  document.getElementById("opt_y").checked = true;
		            	  document.getElementById("rec_comm").style.display="";
	              }
	              else if(flag=="nodata")
	              {
	            	     alert("no data");
	              }
	              else if(flag=="success")
	              {
	            	 // alert("jo")
	            	 
	            	     document.getElementById("rec_comm").style.display="none";
	            	     hide_comm_det();
	            	    // document.getElementById("rec_comp").style.dsiplay="none";
	            	     
	            	     
	              }
	          }
	      }
	    }
	};
req.send(null);
}


function hide_comm_det()
{
	
	 document.getElementById("wth_y_n").style.display="none";	
	 document.getElementById("rec_comp").style.display="none";	
	 document.getElementById("miss_entry_yes_no").style.display="none";	
	 
	 get_mis_det_n();
}

function insert()
{
	
	var val=validation();
	if(val==true)
	{
	var emp_id=document.getElementById("emp_id").value;
	var off_id=document.getElementById("txtCOffice_ID").value;
	
	
	
	
	var opted_y_n=" ";
	var miss_entry_y_n=" ";
	var recover_y_n=" "; 
	
	
	var comm_year="0";
	var comm_month="0";
	var recov_year="0";
	var recov_month="0";
	
	
	if(document.Hrm_Emp_Spf.opt[0].checked==true)
	{
		    opted_y_n='Y';
		    comm_year=document.getElementById("comm_year").value;
			comm_month=document.getElementById("comm_month").value;
	}
	else if(document.Hrm_Emp_Spf.opt[1].checked==true)
	{
			 opted_y_n='N';
			 comm_year="0";
			 comm_month="0";
	}
	
	if(document.Hrm_Emp_Spf.rec_com[0].checked==true)
	{
			recover_y_n='Y';
			recov_year=document.getElementById("recov_year").value;
			recov_month=document.getElementById("recov_month").value;
	}
	else if(document.Hrm_Emp_Spf.rec_com[1].checked==true)
	{
			recover_y_n='N';
			recov_year="0";
			recov_month="0";
	}
	var diff_year=0;
	var diff_comm_month=0;
	//alert(comm_month)
	diff_comm_month=13-comm_month;
	
	
	//alert("diff_comm_month---->"+diff_comm_month);
	
	
	
	
	var diff_comp_rec_month=0;
	diff_comp_rec_month=recov_month;
	//alert("diff_comp_rec_month---->"+diff_comp_rec_month);
	
	
	
	
	
	diff_year=recov_year-comm_year;
	
	//alert("1st diff year====>"+diff_year);
	
	
	diff_year=diff_year-1;
	
	//alert("2nd diff year====>"+diff_year);
	
	var tot_month=0;
	tot_month=diff_year*12;
	
	//alert(tot_mpnth);
	
	
	tot_month=parseInt(tot_month, 10)+parseInt(diff_comm_month, 10)+parseInt(diff_comp_rec_month, 10);
	
	
	
	//alert("final total month-->"+tot_month);
	
	var ss=0;
	var surl = "";
	var ist_mth="";
	if(document.Hrm_Emp_Spf.miss_entry[0].checked==true)
	{
		miss_entry_y_n='Y';
		
		for ( var i = 0; i <frm_yr.length; i++)
		{

			
			var miss_sl_no=miss_entry_sl_no[i];
			var f_year = frm_yr[i];
			var f_mth = frm_mn[i];
			
			var t_year = to_yr[i];
			var t_mth = to_mn[i];
			
			 ist_mth = no_mth[i];
			var int_off_id = office_ids[i];
			
			var remks = rmks[i];
			
			
			ss=parseInt(ss, 10)+parseInt(ist_mth, 10);
			
				surl = surl + "&miss_sl_no=" + miss_sl_no+ "&f_year=" + f_year + "&f_mth=" + f_mth + "&t_year="
				+ t_year + "&t_mth=" + t_mth + "&ist_mth=" + ist_mth + "&int_off_id="+ int_off_id + "&remks=" + remks;
			
			//alert(surl)
			
			
		}

	}
	else if(document.Hrm_Emp_Spf.miss_entry[1].checked==true)
	{
		miss_entry_y_n='N';
		surl="";
	}
	
	if(tot_month>148)
		{
	var ans=	confirm("completion month  greater than 148 !" +
				" Do You want store the data?");
		if(ans)
	{
	if(ss<=148)
		{
		
	url = "../../../../../Hrm_Emp_Spf?Command=Addvalues&off_id="
			+ off_id + "&emp_id=" + emp_id + "&opted_y_n="+opted_y_n+"&comm_year="+comm_year+"&comm_month="+comm_month+"&recover_y_n="+recover_y_n+"&recov_year="+recov_year+"&recov_month="+recov_month+"&miss_entry_y_n="+miss_entry_y_n+surl;
	//alert(url);
		}
	else
		{
		  alert("Total No Of Installment Should be less than or equal to 148 ");
		  try {
				document.getElementById("grid").innerHTML = "";
			} catch (e) {
				document.getElementById("grid").innerTEXT = "";
			}
		  clearAll();
		  return false;
		}
	     var req=getTransport();
		req.open("POST",url,true);
		req.onreadystatechange=function()
		{
			ProcessResponse(req);
		};
			
		req.send(null);
	}
		else
		{
		}	
		}
	
	
    if(tot_month<=148)
    	{

    	if(ss<=148)
    		{
    		
    	url = "../../../../../Hrm_Emp_Spf?Command=Addvalues&off_id="
    			+ off_id + "&emp_id=" + emp_id + "&opted_y_n="+opted_y_n+"&comm_year="+comm_year+"&comm_month="+comm_month+"&recover_y_n="+recover_y_n+"&recov_year="+recov_year+"&recov_month="+recov_month+"&miss_entry_y_n="+miss_entry_y_n+surl;
    	//alert(url);
    		}
    	else
    		{
    		  alert("Total No Of Installment Should be less than or equal to 148 ");
    		  try {
    				document.getElementById("grid").innerHTML = "";
    			} catch (e) {
    				document.getElementById("grid").innerTEXT = "";
    			}
    		  clearAll();
    		  return false;
    		}
    	     var req=getTransport();
    		req.open("POST",url,true);
    		req.onreadystatechange=function()
    		{
    			ProcessResponse(req);
    		};
    			
    		req.send(null);
    	}
    	}
}

function validation_rec()
{
	

	
	var val=validation();
	if(val==true)
	{
	var emp_id=document.getElementById("emp_id").value;
	var off_id=document.getElementById("txtCOffice_ID").value;
	
	
	
	
	var opted_y_n=" ";
	var miss_entry_y_n=" ";
	var recover_y_n=" "; 
	
	
	var comm_year="0";
	var comm_month="0";
	var recov_year="0";
	var recov_month="0";
	
	
	if(document.Hrm_Emp_Spf.opt[0].checked==true)
	{
		    opted_y_n='Y';
		    comm_year=document.getElementById("comm_year").value;
			comm_month=document.getElementById("comm_month").value;
	}
	else if(document.Hrm_Emp_Spf.opt[1].checked==true)
	{
			 opted_y_n='N';
			 comm_year="0";
			 comm_month="0";
	}
	
	if(document.Hrm_Emp_Spf.rec_com[0].checked==true)
	{
			recover_y_n='Y';
			recov_year=document.getElementById("recov_year").value;
			recov_month=document.getElementById("recov_month").value;
	}
	else if(document.Hrm_Emp_Spf.rec_com[1].checked==true)
	{
			recover_y_n='N';
			recov_year="0";
			recov_month="0";
	}
	var diff_year=0;
	var diff_comm_month=0;
	//alert(comm_month)
	diff_comm_month=13-comm_month;
	
	
	//alert("diff_comm_month---->"+diff_comm_month);
	
	
	
	
	var diff_comp_rec_month=0;
	diff_comp_rec_month=recov_month;
	//alert("diff_comp_rec_month---->"+diff_comp_rec_month);
	
	
	
	
	
	diff_year=recov_year-comm_year;
	
	//alert("1st diff year====>"+diff_year);
	
	
	diff_year=diff_year-1;
	
	//alert("2nd diff year====>"+diff_year);
	
	var tot_month=0;
	tot_month=diff_year*12;
	
	//alert(tot_mpnth);
	
	
	tot_month=parseInt(tot_month, 10)+parseInt(diff_comm_month, 10)+parseInt(diff_comp_rec_month, 10);
	
	
	
	//alert("final total month-->"+tot_month);
	
	var ss=0;
	var surl = "";
	var ist_mth="";
	if(document.Hrm_Emp_Spf.miss_entry[0].checked==true)
	{
		miss_entry_y_n='Y';
		
		for ( var i = 0; i <frm_yr.length; i++)
		{

			
			var miss_sl_no=miss_entry_sl_no[i];
			var f_year = frm_yr[i];
			var f_mth = frm_mn[i];
			
			var t_year = to_yr[i];
			var t_mth = to_mn[i];
			
			 ist_mth = no_mth[i];
			var int_off_id = office_ids[i];
			
			var remks = rmks[i];
			
			
			ss=parseInt(ss, 10)+parseInt(ist_mth, 10);
			
				surl = surl + "&miss_sl_no=" + miss_sl_no+ "&f_year=" + f_year + "&f_mth=" + f_mth + "&t_year="
				+ t_year + "&t_mth=" + t_mth + "&ist_mth=" + ist_mth + "&int_off_id="+ int_off_id + "&remks=" + remks;
			
			//alert(surl)
			
			
		}

	}
	else if(document.Hrm_Emp_Spf.miss_entry[1].checked==true)
	{
		miss_entry_y_n='N';
		surl="";
	}
	
	if(tot_month>148)
		{
	var ans=	confirm("completion month  greater than 148 !" +
				" Do You want store the data?");
		if(ans)
	{
	if(ss<=148)
		{
		
	url = "../../../../../Hrm_Emp_Spf?Command=validation_rec&off_id="
			+ off_id + "&emp_id=" + emp_id + "&opted_y_n="+opted_y_n+"&comm_year="+comm_year+"&comm_month="+comm_month+"&recover_y_n="+recover_y_n+"&recov_year="+recov_year+"&recov_month="+recov_month+"&miss_entry_y_n="+miss_entry_y_n+surl;
	//alert(url);
		}
	else
		{
		  alert("Total No Of Installment Should be less than or equal to 148 ");
		  try {
				document.getElementById("grid").innerHTML = "";
			} catch (e) {
				document.getElementById("grid").innerTEXT = "";
			}
		  clearAll();
		  return false;
		}
	     var req=getTransport();
		req.open("POST",url,true);
		req.onreadystatechange=function()
		{
			ProcessResponse(req);
		};
			
		req.send(null);
	}
		else
		{
		}	
		}
	
	
    if(tot_month<=148)
    	{

    	if(ss<=148)
    		{
    		
    	url = "../../../../../Hrm_Emp_Spf?Command=validation_rec&off_id="
    			+ off_id + "&emp_id=" + emp_id + "&opted_y_n="+opted_y_n+"&comm_year="+comm_year+"&comm_month="+comm_month+"&recover_y_n="+recover_y_n+"&recov_year="+recov_year+"&recov_month="+recov_month+"&miss_entry_y_n="+miss_entry_y_n+surl;
    	//alert(url);
    		}
    	else
    		{
    		  alert("Total No Of Installment Should be less than or equal to 148 ");
    		  try {
    				document.getElementById("grid").innerHTML = "";
    			} catch (e) {
    				document.getElementById("grid").innerTEXT = "";
    			}
    		  clearAll();
    		  return false;
    		}
    	     var req=getTransport();
    		req.open("POST",url,true);
    		req.onreadystatechange=function()
    		{
    			ProcessResponse(req);
    		};
    			
    		req.send(null);
    	}
    	}


}
function Add()
{
	var val=nullcheck();
	if(val ==true)
		{
				var cadre=document.getElementById("cadre_id").value;
				var pro_no=document.getElementById("pro_no").value;
				var pro_date=document.getElementById("pro_date").value;
				var ori_reg_no="";
				ori_reg_no= document.getElementById("ori_reg_no").value;
				var remarks=document.getElementById("remarks").value;
				var Amendment="";
				if(document.Seniority_Proceeding_master.Amendment[0].checked==true)
					{
						Amendment="N";
						//document.getElementById("ori_reg_no").value="";
						ori_reg_no="";
					}
				else
					{
						Amendment="A";
						if(document.getElementById("ori_reg_no").value=="")
							{
							alert("Please Enter Original No!");
							return false;
							}
					}
				 url="../../../../../Seniority_Proceeding_master?Command=Add&cadre="+cadre+"&pro_no="+pro_no+"&pro_date="+pro_date+"&ori_reg_no="+ori_reg_no+"&remarks="+remarks+"&Amendment="+Amendment;
				//alert(url);
				 var req=getTransport();
				req.open("POST",url,true);
				req.onreadystatechange=function()
				{
					ProcessResponse(req);
				};
		}
	req.send(null);
}



function ProcessResponse(req)
{
	if(req.readyState==4)
    {
      if(req.status==200)
      {  
    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    	  var tagCommand=baseResponse.getElementsByTagName("command")[0];
          var command=tagCommand.firstChild.nodeValue; 
          if(command=="Addvalues")
          {
        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
               if(flag=="success")
	              { 
	            	  alert("Record Inserted Successfully");
	            	  document.getElementById("grid").innerHTML="";
	            	  document.getElementById('cmdupdate').style.display = "none";
	            	  document.getElementById('cmdadd').style.display = "";
	            	  clearAll();
	              }
          }
          
          else if(command=="validation_rec")
        	  {

        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
               if(flag=="success")
	              { 
	            	  alert("Record Validated Successfully");
	            	  document.getElementById("grid").innerHTML="";
	            	  document.getElementById('cmdupdate').style.display = "none";
	            	  document.getElementById('cmdadd').style.display = "";
	            	  clearAll();
	              }
          
        	  }
         
          
         
      }
    }
}

////////////////

function get_rec_comp()
{
	 document.getElementById("rec_comp").style.display="none";
	 document.getElementById("recov_year").selectedIndex=0;
	 document.getElementById("recov_month").selectedIndex=0;
}
function get_rec_comp_y()
{
	 document.getElementById("rec_comp").style.display="";
	 document.getElementById("miss_entry_yes_no").style.display="";
	 

}


function get_mis_det_n()
{
	 document.getElementById("get_mis_det").style.display="none";
	 document.getElementById("get_mis__table_det").style.display="none";
	 document.getElementById("get_mis__table_y_m").style.display="none";
	 document.getElementById("but_det").style.display="none";
	 document.getElementById("tab_det").style.display="none";
}
function get_mis_det_y()
{
	 document.getElementById("get_mis_det").style.display="";
	 document.getElementById("get_mis__table_det").style.display="";
	 document.getElementById("get_mis__table_y_m").style.display="";
	 document.getElementById("but_det").style.display="";
	 document.getElementById("tab_det").style.display="";
	 
}

function enable()
{
	document.getElementById("wth_y_n").style.display="";
	document.getElementById("rec_comm").style.display="";
	document.getElementById("comm_year").selectedIndex=0;
	document.getElementById("comm_month").selectedIndex=0;
}


function clearAll()
{

	location.reload(true);
	document.getElementById("emp_id").value="";
	document.getElementById("emp_name").value="";
	document.getElementById("desig_id").value="";
	document.getElementById("off_name").value="";
	document.getElementById("comm_year").selectedIndex=0;
	document.getElementById("comm_month").selectedIndex=0;
	document.getElementById("recov_year").selectedIndex=0;
	document.getElementById("recov_month").selectedIndex=0;
	document.getElementById("from_year").selectedIndex=0;
	document.getElementById("from_month").selectedIndex=0;
	document.getElementById("to_year").selectedIndex=0;
	document.getElementById("to_month").selectedIndex=0;
	document.getElementById("no_of_mths").value="";
	document.getElementById("off_id").value="";
	document.getElementById("Remarks").value="";

}
function clearAll1()
{

	//document.getElementById("emp_id").value="";
	//document.getElementById("emp_name").value="";
	//document.getElementById("desig_id").value="";
	//document.getElementById("off_name").value="";
	document.getElementById("comm_year").selectedIndex=0;
	document.getElementById("comm_month").selectedIndex=0;
	document.getElementById("recov_year").selectedIndex=0;
	document.getElementById("recov_month").selectedIndex=0;
	document.getElementById("from_year").selectedIndex=0;
	document.getElementById("from_month").selectedIndex=0;
	document.getElementById("to_year").selectedIndex=0;
	document.getElementById("to_month").selectedIndex=0;
	document.getElementById("no_of_mths").value="";
	document.getElementById("off_id").value="";
	document.getElementById("Remarks").value="";

}

function nullcheck()
{
	if(document.Hrm_Emp_Spf.miss_entry[0].checked==true)
	{
	
	   if(document.getElementById("miss_sel_no").value==null || document.getElementById("miss_sel_no").value=="" || document.getElementById("miss_sel_no").value==0)
	   {
		   alert("Please Select Missing Entry Sl No");
		   return false;
	   }
		
	   if(document.getElementById("from_year").selectedIndex==0 || document.getElementById("from_year").selectedIndex=="null")
	   {
		   alert("Please Select Missing Entry from Year");
		   return false;
	   }
	   if(document.getElementById("from_month").selectedIndex==0 || document.getElementById("from_month").selectedIndex=="null")
	   {
		   alert("Please Select Missing Entry from Month");
		   return false;
	   }
	   if(document.getElementById("to_year").selectedIndex==0 || document.getElementById("to_year").selectedIndex=="null")
	   {
		   alert("Please Select Missing Entry to Year");
		   return false;
	   }
	   if(document.getElementById("to_month").selectedIndex==0 || document.getElementById("to_month").selectedIndex=="null")
	   {
		   alert("Please Select Missing Entry to Month");
		   return false;
	   }
	   if(document.getElementById("no_of_mths").value==null || document.getElementById("no_of_mths").value=="" || document.getElementById("no_of_mths").value==0)
	    {
			alert("Please Enter No of Installment");
			document.getElementById("no_of_mths").focus();
			return false;
	    }
	   if(document.getElementById("off_id").value==null || document.getElementById("off_id").value=="" || document.getElementById("off_id").value==0)
	    {
			alert("Please Enter Missing Office id");
			document.getElementById("off_id").focus();
			return false;
	    }
	  
	}
	return true;

}

function validation()
{
	if(document.getElementById("emp_id").value==null || document.getElementById("emp_id").value=="" || document.getElementById("emp_id").value==0)
	    {
			alert("Please Enter Employee id");
			document.getElementById("emp_id").focus();
			return false;
	    }
	if(document.Hrm_Emp_Spf.opt[0].checked==true)
		{
		
		   if(document.getElementById("comm_year").selectedIndex==0 || document.getElementById("comm_year").selectedIndex=="null")
		   {
			   alert("Please Select Recovery Commencement Year");
			   return false;
		   }
		   if(document.getElementById("comm_month").selectedIndex==0 || document.getElementById("comm_month").selectedIndex=="null")
		   {
			   alert("Please Select Recovery Commencement Month");
			   return false;
		   }
		
		}
	if(document.Hrm_Emp_Spf.rec_com[0].checked==true)
	{
	
	   if(document.getElementById("recov_year").selectedIndex==0 || document.getElementById("recov_year").selectedIndex=="null")
	   {
		   alert("Please Select Recovery Completion Year");
		   return false;
	   }
	   if(document.getElementById("recov_month").selectedIndex==0 || document.getElementById("recov_month").selectedIndex=="null")
	   {
		   alert("Please Select Recovery Completion Month");
		   return false;
	   }
	
	}
	
  return true;
}
function ck_month_comp()
{
	
	var f_year=document.getElementById("comm_year").value;	
	var f_month=document.getElementById("comm_month").value;
	var t_year=document.getElementById("recov_year").value;
	var t_month=document.getElementById("recov_month").value;
	
	var count_t_mth=parseInt(t_month,10);
	var count_f_mth=13-parseInt(f_month,10);
	
	
	//alert("count_f_mth-->"+count_f_mth);
	
	var tot_month=0;
	var tot_count_mth=parseInt(count_t_mth,10)+parseInt(count_f_mth,10);
	
	
	var count_t_yr=parseInt(t_year,10);
	var count_f_yr=parseInt(f_year,10);
	
	if(count_t_yr==count_f_yr)
	{
		alert("Difference Between Recovery Commencement  Month And Recovery Completion Month Should be Greater than or Equal to 148 Months");
		document.getElementById("recov_month").selectedIndex=0;
		return false;
	}
	else if(count_t_yr!=count_f_yr)
	{
		var diff_yr=parseInt(t_year,10)-parseInt(f_year,10);
		
		var final_to_yr=parseInt(t_year,10)-1;
		
		if(diff_yr>1)
			{
			    var count=0;
			      for(var i=f_year;i<final_to_yr;i++)
			        {
				       count++;
				  
			        }
			     tot_month=count*12+tot_count_mth;
			}
		else if(diff_yr==1)
			{
			   alert("Difference Between Recovery Commencement  Month And Recovery Completion Month Should be Greater than or Equal to 148 Months");
			   document.getElementById("recov_month").selectedIndex=0;
			   return false;
		    }
	}
	if(tot_month<50)
		{

			alert("Difference Between Recovery Commencement  Month And Recovery Completion Month Should be Greater than or Equal to 148 Months");
			document.getElementById("recov_month").selectedIndex=0;
			return false;
	
		}
	
	return true;
}
function ck_month()
{
	
	var start_f_yr=document.getElementById("from_year").value;
    var start_f_mth=document.getElementById("from_month").value;
	var f_year=document.getElementById("to_year").value;	
	var f_month=document.getElementById("to_month").value;
	var t_year=document.getElementById("recov_year").value;
	var t_month=document.getElementById("recov_month").value;
	
	
	if(f_year<=t_year && f_year>=start_f_yr)
		{
		  if(f_year==t_year)
			   {
		          if(parseInt(f_month,10)<=parseInt(t_month,10))
			        {
			   
			        }
		           else
			       {
						alert("To Month Should be Less than Recovery Completion Month");
						document.getElementById("to_month").selectedIndex=0;
						return false;
			        }
			    }
		  
		  if(start_f_yr==f_year)
			  {

	          if(parseInt(start_f_mth,10)<=parseInt(f_month,10))
		        {
		   
		        }
	           else
		       {
					alert("To Month Should be Greater than From  Month");
					document.getElementById("to_month").selectedIndex=0;
					return false;
		        }
		    
			    
			  }
		}
		else
		{
			alert("To Year Should be Less than or Equal to Recovery Completion Year and Greater than or Equal to From Year");
			document.getElementById("to_year").selectedIndex=0;
			document.getElementById("to_month").selectedIndex=0;
			return false;
		 }
 totinstmonth_calc();
}

function totinstmonth_calc()
{
	
	f_yr=document.getElementById("from_year").value;
	f_mth=document.getElementById("from_month").value;
	t_yr=document.getElementById("to_year").value;	
	t_mth=document.getElementById("to_month").value;
	
	var count_f_mth=13-parseInt(f_mth,10);
	var count_to_mth=parseInt(t_mth,10);
	
	var tot_no_mth=0;
	
	
	var tot_count_mth=parseInt(count_to_mth,10)+parseInt(count_f_mth,10);
	
	if(f_yr==t_yr)
		{
		 if(t_mth==f_mth)
			 {
			 tot_no_mth=1;
			 document.getElementById("no_of_mths").value=tot_no_mth;
			 }
		 else
			 {
			
			    tot_no_mth=0;
			    
				tot_no_mth=parseInt(t_mth)-parseInt(f_mth);
				tot_no_mth=tot_no_mth+1;
				document.getElementById("no_of_mths").value=tot_no_mth;
			 }
	
		}
	else if(t_yr!=f_yr)
	{
		var diff_yr=parseInt(t_yr,10)-parseInt(f_yr,10);
		
		var final_to_yr=parseInt(t_yr,10)-1;
		
		if(diff_yr>1)
			{
			    var count=0;
			      for(var i=f_yr;i<final_to_yr;i++)
			        {
				       count++;
				  
			        }
			      tot_no_mth=count*12+tot_count_mth;
			     
			      document.getElementById("no_of_mths").value=tot_no_mth;
			}
		else if(diff_yr==1)
		{
			tot_no_mth=count_f_mth+count_to_mth;
		  document.getElementById("no_of_mths").value=tot_no_mth;
	    }
	}
	
}


function check_f_year()
{
  var f_yr=document.getElementById("from_year").value;
  var f_mth=document.getElementById("from_month").value;
  var c_year=document.getElementById("comm_year").value;	
  var c_month=document.getElementById("comm_month").value;
  
  var t__fin_year=document.getElementById("recov_year").value;
	var t_fin_month=document.getElementById("recov_month").value;
 
  
  
  if(f_yr >= c_year && f_yr<=t__fin_year)
	{
	 
	  if(f_yr==c_year)
		   {
		 
	          if(parseInt(f_mth,10)>parseInt(c_month,10))
		        {
	        	   
		   
		        }
	           else
		       {
					alert("From Month Should be Greater than Commencement Month");
					document.getElementById("from_month").selectedIndex=0;
					return false;
		        }
		    }
	   if(f_yr==t__fin_year)
		  {

			 
	          if(parseInt(f_mth,10)<parseInt(t_fin_month,10))
		        {
	        	   
		   
		        }
	           else
		       {
					alert("From Month Should be Les than Completion Month");
					document.getElementById("from_month").selectedIndex=0;
					return false;
		        }
		    
		  }
	}
	else
	{
		alert("From Year Should be Greater than or Equal to Commencement Year");
		document.getElementById("from_year").selectedIndex=0;
		document.getElementById("from_month").selectedIndex=0;
		return false;
	 }
  
  

}

function checkoff()
{
	
  var check_off_id=document.getElementById("off_id").value;	
  
  
	url="../../../../../Hrm_Emp_Spf?Command=checkoff&check_off_id="+check_off_id;
	//alert(url)
	var req=getTransport();
   req.open("POST",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
	    {
			//alert(req.responseText);
	      if(req.status==200)
	      { 
		    	  
		         
		          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		    	  var tagCommand=baseResponse.getElementsByTagName("command")[0];
		          var command=tagCommand.firstChild.nodeValue; 
		          
		          
	          if(command=="checkoff")
	          {
	        	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	        	 
	              if(flag=="success")
	              {
	            	
	            	     
	              }
	              else if(flag=="no_office")
	            	  {
	            	     alert(" Office Id not in Master");
	            	     document.getElementById("off_id").value="";
	            	     document.getElementById("off_id").focus();
	            	     return fasle;
	            	  }
	          }
	      }
	    }
	};
req.send(null);

  
  

}

/*function checkEmp()
{
	
	try
	{


for ( var i = 0; i < no_mth.length; i++) {
		var temno_of_mths=document.getElementById("no_of_mths").value;
		var tot_mth_ins="";
		var temp=no_mth[i];
		tot_mth_ins=parseInt(temno_of_mths,10)+parseInt(temp,10);
		alert(tot_mth_ins);
		if(148>tot_mth_ins)
			{
			alert("Installment No Should be less than 148 ");
			document.getElementById("no_of_mths").value="";
			document.getElementById("no_of_mths").focus();
			return false;
			}
		
}
	}
	catch(e)
	{
		
	}
	doFunction('loademp','null');
}*/



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
          //document.EmpQual.txtSNo.focus();
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
                return false ;
        }
       

}


  


