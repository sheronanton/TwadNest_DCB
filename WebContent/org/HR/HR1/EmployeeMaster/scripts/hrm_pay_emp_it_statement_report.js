var xmlHttp1 = null, xmlHttpobj = null, xmlHttpreq = null, xmlHttp = null;
var sel_len = 0;
var counter = 0;
var field = new Array();
var winemp = null;
var winemp1 = null;
var checkState = "Before";
var common = "";
var length = 0;
var flag, command, response = "";
var pagesize = 10;
var seq = 0;
function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function numbersonly1(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;
	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}
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
      // alert('test11111');     
    winemp= window.open("../../../../../org/hrms/payroll/jsp/EmpSelectionPopupforOffice_payacting.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function loadMonth_New()
{
	
//	var sales_year=document.getElementById("frmYear").value;
	var length=sales_year.length;
	//alert(length);
	if(length!=4)
		alert("Check it your Year ");
	
//	sales_year=parseInt(sales_year);
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=parseInt(month)+1;
    year=parseInt(year);
    var sales_year=document.getElementById("frmYear").value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
    var sales_month=document.getElementById("frmMonth");
    var child=sales_month.childNodes;
    for(var c=child.length-1;c>0;c--)
    {
        sales_month.removeChild(child[c]);
    }
    if(sales_year>year)
    {
    	alert("year is should be "+year+" or less than "+year);
    }
    else
    {
    if(sales_year!=year){
    	       month=12;
    	    
    }
       
//    alert(year);
//    alert(month);

   
   if(year==sales_year)
   {
	   month=month-1;
	   for(i=month;i>=0;i--)
	    {
	        var opt =document.createElement("option"); 
	        var text=document.createTextNode(monthNames[i]);
	        
	        opt.setAttribute("value",i+1);
	        opt.appendChild(text);
	        sales_month.appendChild(opt);
	       
	    }
   }
   else
   {
    for(i=0;i<month;i++)
    {
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);
        
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);
       
    }
   }
    }
    document.getElementById("frmMonth").selectedIndex=0;
    type_function('all');
//    changegroup();
   
}
function loadMonth_New1()
{
	
	var sales_year=document.getElementById("toYear").value;
	var length=sales_year.length;
	//alert(length);
	if(length!=4)
		alert("Check it your Year ");
	
	sales_year=parseInt(sales_year);
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=parseInt(month)+1;
    year=parseInt(year);
    var sales_year=document.getElementById("toYear").value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
    var sales_month=document.getElementById("toMonth");
    var child=sales_month.childNodes;
    for(var c=child.length-1;c>0;c--)
    {
        sales_month.removeChild(child[c]);
    }
    if(sales_year>year)
    {
    	alert("year is should be "+year+" or less than "+year);
    }
    else
    {
    if(sales_year!=year){
    	       month=12;
    	    
    }
       
//    alert(year);
//    alert(month);

   
   if(year==sales_year)
   {
	   month=month-1;
	   for(i=month;i>=0;i--)
	    {
	        var opt =document.createElement("option"); 
	        var text=document.createTextNode(monthNames[i]);
	        
	        opt.setAttribute("value",i+1);
	        opt.appendChild(text);
	        sales_month.appendChild(opt);
	       
	    }
   }
   else
   {
    for(i=0;i<month;i++)
    {
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);
        
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);
       
    }
   }
    }
    document.getElementById("toMonth").selectedIndex=0;
    type_function('all');

   
}
function doFunction(Command,param)
{
    var empid=document.hrm_pay_emp_IT_Statement_Emp_Report.eid.value;
    if(Command=='loadempedit')
    {
        var check=notNull(null);
        if(check)
        {
                startwaiting(document.frmCurrentPosting) ;
                service=null;
                var url="../../../../../EmpSelectionPopupforOffice?Command=loadempedit&txtEmployeeid="+empid;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
        }        
    }
}
function doParentEmp(emp, name, designation) {
	document.getElementById("eid").value = emp;
	document.getElementById("ename").value = name;
	document.getElementById("edesg").value = designation;
//	callServer('Get', 'null');
}
function check_finyear()
{
	var fin_year=document.getElementById("fin_year").value;
	var year=new Array();
	year=fin_year.split("-");
	document.getElementById("frm_prd").innerHTML=year[0]+"-March";
	document.getElementById("to_prd").innerHTML=year[1]+"-February";
}
function met2()
{
  var  xmlhttp =getTransport();
    if (xmlhttp == null) {
        alert("Your borwser doesnot support AJAX");
        return;
    }
    //alert("under method");
    empn=document.getElementById("eid").value;
    var offid=document.getElementById("txtOffId").value;
    if(empn=="")
    {
    }else
    {
    var urlhh="../../../../../EmpSelectionPopupforOffice?Command=loadempedit&txtEmployeeid="+empn;
    // alert("add url is"+urlhh);
    xmlhttp.open("GET", urlhh, true);
    xmlhttp.onreadystatechange = function()
    {
        if(xmlhttp.readyState==4)
        {
          if(xmlhttp.status==200)
          {
               //stopwaiting(document.HRM_EmpSearch);
               var responsee=xmlhttp.responseXML.getElementsByTagName("response")[0];
               
                var flag=responsee.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                    clear1();
                    alert("Sorry! Employee ID Not Found");
                   
                }
                else if(flag=="Success")
                {
                    
                    if(document.getElementById("txtOffId").value==responsee.getElementsByTagName("Office_Id")[0].firstChild.nodeValue)
                    {
                    document.getElementById("ename").value=responsee.getElementsByTagName("empn")[0].firstChild.nodeValue;
                   
                    document.getElementById("edesg").value=responsee.getElementsByTagName("Designation")[0].firstChild.nodeValue;
                 //  alert( document.Leave_surrender.formstat.value);                
                    //ExistDetails();
                    }
                    else
                    {
                        alert("Employee ID not belongs to this Office.");
                        clear1();
                    }
                    
                     
                }
          }
        }
       
    };
    xmlhttp.send(null);
    }
   
}
function clearfunction(){
	 document.hrm_pay_emp_IT_Statement_Emp_Report.eff_month.selectedIndex = 0;
	 clearAll();
}
function clearAll() {
	document.getElementById('eid').value = "";
	document.getElementById('ename').value = "";
	document.getElementById('edesg').value = "";
   // document.hrm_pay_emp_IT_Statement_Emp_Report.eff_year.value='';
   
	document.getElementById("pay_group_id").selectedIndex=0;
	document.getElementById("pay_subgroup_id").selectedIndex=0;
}
function check_values(values)
{

if(values=='p')
	document.getElementById("ptype").value="P";
else
	document.getElementById("ptype").value="V";

document.hrm_pay_emp_IT_Statement_Emp_Report.submit();
//nullCheck();
}
function nullCheck()
{
	var ret=false;
	 if (document.hrm_pay_emp_IT_Statement_Emp_Report.eff_year.value.length!=4) {
		alert("Enter Valid Year");
		ret=false;
	}
	else if(document.getElementById("emp").checked==true){
		if (document.hrm_pay_emp_IT_Statement_Emp_Report.eid.value=="") {
			alert("Enter Employee Id");
			ret=false;
		}
		else
			ret=true;
	}
	else
		ret=true;


//	 if (ret==true)
//		 document.getElementById("load").style.display='';
	// alert(ret);
	if(ret==true)
		document.hrm_pay_emp_IT_Statement_Emp_Report.submit();
}
function Exit() {
	self.close();
}

function hidefunction(cmd){
	if(cmd=="subgroup"){
		document.getElementById("tbody_emp").style.display='none';
		document.getElementById("paydetail").style.display='';
		

	}
	else
	{
		document.getElementById("tbody_emp").style.display='';
		document.getElementById("paydetail").style.display='none';
		
	}
	document.getElementById("pay_group_id").selectedIndex=0;
	document.getElementById("pay_subgroup_id").selectedIndex=0;
	document.getElementById("eid").value="";
	document.getElementById("ename").value="";
	document.getElementById("edesg").value="";
}

function getsubgroup(){
	var unit_code=document.getElementById("cmbAcc_UnitCode").value;
	var off_id=document.getElementById("cmbOffice_code").value;
	var group_id=document.getElementById("pay_group_id").value;
	var pay_subgroup_id=document.getElementById("pay_subgroup_id");
    var child=pay_subgroup_id.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
    	pay_subgroup_id.removeChild(child[c]);
    }
var url="../../../../../Hrm_Emp_monthly_Earnings_Updation?command=getsupgroup&unit_code="+unit_code+"&off_id="+off_id+"&group_id="+group_id;
//alert(url);
	 var req=getTransport();
	    req.open("GET",url,true);
	    req.onreadystatechange=function()
	    {
	        processResponse(req);
	    };
	    req.send(null);
}





function loadMonth1()
{
	
	var sales_year=document.getElementById("eff_year").value;
	var length=sales_year.length;
	//alert(length);
	if(length!=4)
		alert("Check it your Year ");
	
	sales_year=parseInt(sales_year);
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=parseInt(month)+1;
    year=parseInt(year);
    var sales_year=document.getElementById("eff_year").value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
    var sales_month=document.getElementById("eff_month");
    var child=sales_month.childNodes;
    for(var c=child.length-1;c>0;c--)
    {
        sales_month.removeChild(child[c]);
    }
    if(sales_year>year)
    {
    	alert("year is should be "+year+" or less than "+year);
    }
    else
    {
    if(sales_year!=year){
    	       month=12;
    	    
    }
       
//    alert(year);
//    alert(month);

   
   if(year==sales_year)
   {
	   month=month-1;
	    for(i=month;i>=0;i--)
	    {
	        var opt =document.createElement("option"); 
	        var text=document.createTextNode(monthNames[i]);
	        
	        opt.setAttribute("value",i+1);
	        opt.appendChild(text);
	        sales_month.appendChild(opt);
	       
	    }
   }
   else
   {
    for(i=0;i<month;i++)
    {
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);
        
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);
       
    }
   }
    }
    document.getElementById("eff_month").selectedIndex=0;
//    type_function('all');
    hidefunction('subgroup');
    getsubgroup();
}