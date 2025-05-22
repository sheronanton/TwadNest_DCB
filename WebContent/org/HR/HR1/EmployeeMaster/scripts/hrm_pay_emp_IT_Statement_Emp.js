var winemp;
function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
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

function met_new() {
	if (document.getElementById("txtEmployeeid").value == ""
			|| document.getElementById("txtEmployeeid").length == 0) {

		alert("Enter Employee Id");
		javascript: location.reload(true);
	} else {
		var txtEmployeeid = document.getElementById("txtEmployeeid").value;
		var office = document.getElementById("cmbOffice_code").value;

		// document.getElementById("smonth").selectedIndex=0;
		var url = "../../../../../Hrm_pay_emp_breakup_earn_ded_sup?command=getemployee&employee="
				+ txtEmployeeid + "&office=" + office;
		var req = getTransport();
		req.open("POST", url, true);
		req.onreadystatechange = function() {
			newresponse(req);
		};
		req.send(null);
	}
}

function newresponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			
//			alert(req.responseText);
			document.getElementById("pop1").style.display='none';
			document.getElementById("popup").style.display='none';
			var   baseresponse = req.responseXML.getElementsByTagName("response")[0]; 
	  		command =baseresponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	  		
	  		flag =  baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  		if(command=="getEmployee"){
	  			if(flag=="success"){
	  				
	  				employee_id= baseresponse.getElementsByTagName("employee_id")[0].firstChild.nodeValue;
	  				empname= baseresponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
	  				DESIGNATION= baseresponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
	  				PAY= baseresponse.getElementsByTagName("PAY_BAND")[0].firstChild.nodeValue;
	  				GRA= baseresponse.getElementsByTagName("GRADEPAY")[0].firstChild.nodeValue;
	  				document.getElementById("emp_name").value=empname;
	  				document.getElementById("desg").value=DESIGNATION;

	  				getdetails_new();
	  			var status= baseresponse.getElementsByTagName("status")[0].firstChild.nodeValue;

	  			if(status=="1")
	  			{
	  				apprise('This Employee Already Relieved from Your Office , Do you Want to Include this Employee for Supplement Process?', {'verify':true}, function(r)
	  					    {
	  					    if(r)
	  					        {
	  					    	
	  					        }
	  					    else
	  					        {
	  					    	javascript:location.reload(true);
	  					        }
	  					    });
	  				
	  			}
	  			
	  			}
	  			else
	  			{
	  				//alert("Come here");
	  				alert("Employee Not Belongs to your Paybill Group");
	  				javascript:location.reload(true);
	  				
	  			}
	  			
	  			
	  			}
	  		
	  		else if(command=="details")
	  		{
//	  			loadDADetails(baseresponse);
	  			loadDetails_new(baseresponse);
	  		}
			else if(command=="getPayDetails")
	  		{
//	  			loadDADetails(baseresponse);
	  			loadPayDetails(baseresponse);
	  		}
			else if(command=="processing")
	  		{

				var flag= baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  			if(flag=="success")
	  			{
	  				alert("Processing successfully completed");
	  				javascript:location.reload(true);
	  			}
	  			else
	  			{
	  				alert("process failure");
	  			}
	  		}
	  		else if(command=="SAVEDETAILS")
	  		{
	  			var flag= baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  			if(flag=="success")
	  			{
	  				alert("Pay Details Updated Successfully");
	  				javascript:location.reload(true);
	  			}
	  			else
	  			{
	  				alert("Failure To Update Pay Details");
	  			}
	  		}

		}
	}
}

function check_finyear()
{
	var fin_year=document.getElementById("fin_year").value;
	var year=new Array();
	year=fin_year.split("-");
	document.getElementById("frm_prd").innerHTML=year[0]+"-March";
	document.getElementById("to_prd").innerHTML=year[1]+"-February";
}

function processingEmp()
{
	

	
	var fin_year=document.getElementById("fin_year").value;
//	var from_month=document.getElementById("from_month").value;
//	var to_year=document.getElementById("to_year").value;
//	var to_month=document.getElementById("to_month").value;
//	var proc_year=document.getElementById("proc_year").value;
//	var proc_month=document.getElementById("proc_month").value;
	var office_id=document.getElementById("cmbOffice_code").value;
	var empId=document.getElementById("empId").value;
	document.getElementById('pop1').style.display='block';
	document.getElementById('popup').style.display='block';
	var url = "../../../../../Hrm_pay_emp_IT_Updation_Emp?command=processing&fin_year=" + fin_year+ "&office_id=" + office_id+"&empId="+empId;
	//alert(url);
	var req = getTransport();
	req.open("POST", url, true);
	req.onreadystatechange = function() {
		newresponse(req);
	};
	req.send(null);

	
	
	}
function getdetails_new()
{
	var emp_id=document.getElementById("txtEmployeeid").value;
	var fin_year=document.getElementById("fin_year").value;
//	var from_month=document.getElementById("from_month").value;
//	var to_year=document.getElementById("to_year").value;
//	var to_month=document.getElementById("to_month").value;
//	var proc_year=document.getElementById("proc_year").value;
//	var proc_month=document.getElementById("proc_month").value;
	var office_id=document.getElementById("cmbOffice_code").value;
	document.getElementById('pop1').style.display='block';
	document.getElementById('popup').style.display='block';
	var url = "../../../../../Hrm_pay_emp_IT_Updation?command=getDetails&emp_id="
		+ emp_id + "&fin_year=" + fin_year+ "&office_id=" + office_id;
//	alert(url);
	var req = getTransport();
	req.open("POST", url, true);
	req.onreadystatechange = function() {
		newresponse(req);
	};
	req.send(null);
}

function getPayDetails()
{
	var emp_id=document.getElementById("txtEmployeeid").value;
	var from_year=document.getElementById("from_year").value;
	var from_month=document.getElementById("from_month").value;
	var to_year=document.getElementById("to_year").value;
	var to_month=document.getElementById("to_month").value;
	var url = "../../../../../Hrm_pay_emp_IT_Updation?command=getPayDetails&emp_id="
		+ emp_id + "&from_year=" + from_year+ "&from_month=" + from_month+ "&to_year=" + to_year+ "&to_month=" + to_month;
//	alert(url);
	var req = getTransport();
	req.open("POST", url, true);
	req.onreadystatechange = function() {
		newresponse(req);
	};
	req.send(null);
}
function init()
{
//	loadMonth('proc_year','proc_month');
//	loadMonth('from_year','from_month');
//	loadMonth('to_year','to_month');
}



//********************************************* GET PROCESS ID ********************************************************************

function getPayProcess()
{
      var syear=document.getElementById("eff_year").value;
      var smonth=document.getElementById("eff_month").value;
      var acc_off_id=document.getElementById("cmbOffice_code").value;
      var unit_code=document.getElementById("cmbAcc_UnitCode").value;
      var pay_group_id=document.getElementById("pay_bill_group_id").value;  
	
	var xmlhttp=getTransport();
	
	
	
	
	
	url = "../../../../../hrm_pay_da_arrear_month_updation?command=procid&syear="+syear+"&smonth="+smonth+"&acc_off_id="+acc_off_id+"&unit_code="+unit_code+"&pay_group_id="+pay_group_id;
//	alert(url);
	xmlhttp.open("POST", url, true);
	xmlhttp.onreadystatechange=function()
	{
		 var bank=document.getElementById("process_id");
		  var child=bank.childNodes;
		   for(var c=child.length-1;c>0;c--)
		   {
			   bank.removeChild(child[c]);
		   }
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				 var i=0;
	 	    var responsee="";
	 	  responsee = xmlhttp.responseXML.getElementsByTagName("response")[0];
				command1 = responsee.getElementsByTagName("command")[0].firstChild.nodeValue;
				flag1 = responsee.getElementsByTagName("status")[0].firstChild.nodeValue;
				var bank_id = responsee.getElementsByTagName("pay").length;
				if (command1 == "PAY_PROCESS_ID") {
					if (flag1 == 'success') {
						var value=responsee.getElementsByTagName("pay").length;
	                  var option=document.createElement("OPTION");
	                 for(var i=0;i<value;i++)
	                  {
	                      var id=responsee.getElementsByTagName("PAY_PROCESS_ID")[i].firstChild.nodeValue;
	                      var name=responsee.getElementsByTagName("PAY_PROCESS_DESC")[i].firstChild.nodeValue;
	                      var option=document.createElement("OPTION");
	                        option.text=id+"-"+name;
	                        option.value=id;
	                        try
	                        {
	                       	 bank.add(option);
	                        }
	                        catch(errorObject)
	                        {
	                       	 bank.add(option,null);
	                        }
	                  }
					}else if(flag1 == 'notexist'){
						//alert("Data Not Found");
					} else {
						//alert("Failure in loading values");
					}
				} 
			}
		}
	};
	xmlhttp.send(null);

}





var pay_element_type_id=new Array();
var pay_element_short_name=new Array();
var pay_element_id=new Array();
var salary_years=new Array();
var salary_months=new Array();
var pay_total=new Array();

var SAL_YEAR=new Array();
var SAL_MONTH=new Array();
var PAY_ELEMENT=new Array();
var PAY_TYPE=new Array();
var PAY_AMOUNT=new Array();
var TOTAL=new Array();
var ADDITIONAL_PAY_DESC=new Array();
var ADDITIONAL_PAY_VALUE=new Array();
var ADDITIONAL_PAY_TYPE=new Array();



function LoadGrid()
{
	var tbody=document.getElementById("pay");
	tbody.innerHTML="";
	var earncount=0;
	
	var dedcount=0;

	var trh=document.createElement("tr");
	var tdh=document.createElement("td");
	var st=document.createElement("strong");
	tdh.appendChild(st);
	tdh.innerHTML="Month";
	tdh.setAttribute("style","color:#5A0000");
	trh.appendChild(tdh);
	
	
	
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	tr.appendChild(td);
	
	
	
	for(var i=0;i<pay_element_type_id.length;i++)
		{
		if(pay_element_type_id[i]=="E")
			{
			var td1=document.createElement("td");
			var font=document.createElement("strong");
//			font.setAttribute("size", "10");
			td1.appendChild(font);
			td1.innerHTML=pay_element_short_name[i];
			td1.setAttribute("style","color:#5A0000;size:2");
			tr.appendChild(td1);
			earncount++;
			}
			
	
		}
	var td1=document.createElement("td");
	var font=document.createElement("font");
	td1.appendChild(font);
	td1.innerHTML="TOTAL";
	td1.setAttribute("style","color:#5A0000");
	tr.appendChild(td1);
	
	
	var tdh=document.createElement("td");
	var st=document.createElement("strong");
	tdh.appendChild(st);
	tdh.innerHTML="Earnings";
	tdh.setAttribute("style","color:#5A0000");
	tdh.setAttribute("colspan",earncount+1);
	trh.appendChild(tdh);
	
	
	
	
	
	

	for(var i=0;i<pay_element_type_id.length;i++)
		{
		if(pay_element_type_id[i]=="D")
			{
			var td1=document.createElement("td");
			var b=document.createElement("b");
			td1.appendChild(b);
			td1.innerHTML=pay_element_short_name[i];
			td1.setAttribute("style","color:#5A0000");
			tr.appendChild(td1);
			dedcount++;
			}
			
	
		}
	
	var td1=document.createElement("td");
	var font=document.createElement("font");
	td1.appendChild(font);
	td1.innerHTML="Details";
	td1.setAttribute("style","color:#5A0000;size:2");
	tr.appendChild(td1);
	
	
	var tdh=document.createElement("td");
	var st=document.createElement("strong");
	tdh.appendChild(st);
	tdh.innerHTML="Deductions";
	tdh.setAttribute("style","color:#5A0000");
	tdh.setAttribute("colspan",dedcount+1);
	trh.appendChild(tdh);
	
	tbody.appendChild(trh);
	tbody.appendChild(tr);
	
	
	
		
		
//		alert(salary_years.length);
		
		var months =new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	
	
		var tr=document.createElement("tr");
		var td=document.createElement("td");
		var select =document.createElement("select");
		select.setAttribute("id", "year");
		select.setAttribute("onchange", "setMonth()");
		select.setAttribute("name", "year");
		select.setAttribute("style", "width:50px");
		var old_year=1;
		var new_year=0;
		old_year=parseInt(old_year);
		new_year=parseInt(new_year);
		for(var m=0;m<salary_years.length;m++)
		{
			new_year=salary_years[m];
			if(old_year!=new_year)
				{
				var opt=document.createElement("option");
				var txt=document.createTextNode(salary_years[m]);
				opt.setAttribute("value", salary_years[m]);
				opt.appendChild(txt);
				select.appendChild(opt);
				old_year=new_year;
				}
			
		}
		td.appendChild(select);
		var select =document.createElement("select");
		select.setAttribute("id", "month");
		select.setAttribute("name", "month");
		select.setAttribute("style", "width:50px");
		var opt=document.createElement("option");
		var txt=document.createTextNode("Month");
		opt.setAttribute("value", "0");
		opt.appendChild(txt);
		select.appendChild(opt);
		
		for(var m=0;m<salary_years.length;m++)
		{
			
			
			if(salary_years[m]==salary_years[0])
				{
				var opt=document.createElement("option");
				var txt=document.createTextNode(months[salary_months[m]-1]);
				opt.setAttribute("value", salary_months[m]);
				opt.appendChild(txt);
				select.appendChild(opt);
				
				}
			
		}
		td.appendChild(select);
		tr.appendChild(td);
		
		
		
		
		for(var i=0;i<pay_element_type_id.length;i++)
			{
//			alert(pay_element_type_id[i]);
				if(pay_element_type_id[i]=="E")
				{
				var td1=document.createElement("td");
				var input=document.createElement("input");
				input.setAttribute("id", pay_element_id[i]);
				input.setAttribute("name", pay_element_id[i]);
				input.setAttribute("size", "2");
				input.setAttribute("onblur", "toalvalue()");
				input.setAttribute("type", "text");
				td1.appendChild(input);
				tr.appendChild(td1);
				}
				
		
			}
		var td1=document.createElement("td");
		var input=document.createElement("input");
		input.setAttribute("id", "total");
		input.setAttribute("name", "total");
		input.setAttribute("type", "text");
		input.setAttribute("size", "2");
		td1.appendChild(input);
		tr.appendChild(td1);
		
		
		
		
		
		
		
	
		for(var i=0;i<pay_element_type_id.length;i++)
			{
			if(pay_element_type_id[i]=="D")
				{
				var td1=document.createElement("td");
				var input=document.createElement("input");
				input.setAttribute("id", pay_element_id[i]);
				input.setAttribute("name", pay_element_id[i]);
				input.setAttribute("size", "2");
				input.setAttribute("type", "text");
				td1.appendChild(input);
				tr.appendChild(td1);
				}
				
		
			}
		
		
		
		var td1=document.createElement("td");
		var input=document.createElement("input");
		input.setAttribute("id","submit");
		input.setAttribute("name", "submit");
		input.setAttribute("onclick", "adddetails()");
		input.setAttribute("type", "button");
		input.setAttribute("value", "Submit");
		td1.appendChild(input);
		tr.appendChild(td1);
		
		
		tbody.appendChild(tr);
		
		
//		try
//		{
			
			for(var j=0;j<salary_years.length;j++)
				{
				
				
					var total=0;
					var tr=document.createElement("tr");
					var td=document.createElement("td");
					td.setAttribute("style", "color:#610B0B");
					
					td.innerHTML=months[salary_months[j]-1]+"/"+salary_years[j];
					tr.appendChild(td);
					
					
					
					
					for(var i=0;i<pay_element_type_id.length;i++)
					{
						
						if(pay_element_type_id[i]=="E")
						{
						
						for(var k=0;k<PAY_ELEMENT.length;k++)
						{
							if(PAY_ELEMENT[k]==pay_element_id[i]  && SAL_YEAR[k]==salary_years[j] && SAL_MONTH[k]==salary_months[j])
							{
								var td1=document.createElement("td");	
								td1.setAttribute("style", "color:green");
								td1.innerHTML=PAY_AMOUNT[k];				
								tr.appendChild(td1);
								total=parseInt(total)+parseInt(PAY_AMOUNT[k]);
							}
						}
						}
						
				
					}
					
					TOTAL[j]=total;
					var td1=document.createElement("td");			
					td1.setAttribute("style", "color:red");
					td1.innerHTML=TOTAL[j];
				
					tr.appendChild(td1);			
					for(var i=0;i<pay_element_type_id.length;i++)
					{
						
						if(pay_element_type_id[i]=="D")
						{
						
						for(var k=0;k<PAY_ELEMENT.length;k++)
						{
							if(PAY_ELEMENT[k]==pay_element_id[i]  && SAL_YEAR[k]==salary_years[j] && SAL_MONTH[k]==salary_months[j])
							{
								var td1=document.createElement("td");	
								td1.setAttribute("style", "color:green");
								td1.innerHTML=PAY_AMOUNT[k];				
								tr.appendChild(td1);
								total=parseInt(total)+parseInt(PAY_AMOUNT[k]);
							}
						
						}
						}
						
				
					}
					
					
					var td1=document.createElement("td");		
					var a=document.createElement("a");
					a.setAttribute("href", "#");
					a.setAttribute("style", "color:#254117");
					var t=document.createTextNode("Edit");
					a.setAttribute("onclick", "EditPayDetails('"+salary_years[j]+"','"+salary_months[j]+"')");
					a.appendChild(t);
					td1.appendChild(a);	
					var t=document.createTextNode("   ");
					td1.appendChild(t);
					var a=document.createElement("a");
					a.setAttribute("href", "#");
					a.setAttribute("style", "color:red");
					var t=document.createTextNode("Delete");
					a.setAttribute("onclick", "DeletePayDetails('"+salary_years[j]+"','"+salary_months[j]+"')");
					a.appendChild(t);
//					td1.appendChild(a);	
					
					
					td1.setAttribute("style","color:#0B0B3B;size:2");
					tr.appendChild(td1);	
					
					
					tbody.appendChild(tr);
				
				}
//		}
//	catch(e)
//	{
//		
//	}
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	td.setAttribute("colspan",earncount+1);
	td.innerHTML="SLS Pay Amount ";
	tr.appendChild(td);
	
	var td1=document.createElement("td");
	var input=document.createElement("input");
	input.setAttribute("id","SLS");
	input.setAttribute("name", "SLS");
	input.setAttribute("size", "4");
	input.setAttribute("type", "text");		
	input.setAttribute("onchange", "SetAddPayValue('SLS')");	
	input.setAttribute("value", "0");		
	td1.appendChild(input);
	tr.appendChild(td1);
	
	var td=document.createElement("td");
	td.setAttribute("colspan",dedcount+1);
	td.innerHTML="";
	tr.appendChild(td);
	
	tbody.appendChild(tr);
	
	
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	td.setAttribute("colspan",earncount+1);
	td.innerHTML="Bonus Amount ";
	tr.appendChild(td);
	
	var td1=document.createElement("td");
	var input=document.createElement("input");
	input.setAttribute("id","bonus");
	input.setAttribute("name", "bonus");
	input.setAttribute("size", "4");
	input.setAttribute("type", "text");	
	input.setAttribute("onchange", "SetAddPayValue('BON')");
	input.setAttribute("value", "0");		
	td1.appendChild(input);
	tr.appendChild(td1);
	
	var td=document.createElement("td");
	td.setAttribute("colspan",dedcount+1);
	td.innerHTML="";
	tr.appendChild(td);
	
	tbody.appendChild(tr);
	
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	td.setAttribute("colspan",earncount+1);
	td.innerHTML="PAY Arrear Amount ";
	tr.appendChild(td);
	
	var td1=document.createElement("td");
	var input=document.createElement("input");
	input.setAttribute("id","payarr");
	input.setAttribute("name", "payarr");
	input.setAttribute("onchange", "SetAddPayValue('PAY')");
	input.setAttribute("value", "0");		
	input.setAttribute("size", "4");
	input.setAttribute("type", "text");		
	td1.appendChild(input);
	tr.appendChild(td1);
	
	var td=document.createElement("td");
	td.setAttribute("colspan",dedcount+1);
	td.innerHTML="";
	tr.appendChild(td);
	
	tbody.appendChild(tr);
	
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	td.setAttribute("colspan",earncount+1);
	td.innerHTML="DA Arrear Amount ";
	tr.appendChild(td);
	
	var td1=document.createElement("td");
	var input=document.createElement("input");
	input.setAttribute("id","daarr");
	input.setAttribute("name", "daarr");
	input.setAttribute("size", "4");
	input.setAttribute("type", "text");	
	input.setAttribute("onchange", "SetAddPayValue('DA')");
	input.setAttribute("value", "0");		
	td1.appendChild(input);
	tr.appendChild(td1);
	
	var td=document.createElement("td");
	td.setAttribute("colspan",dedcount+1);
	td.innerHTML="";
	tr.appendChild(td);
	
	tbody.appendChild(tr);
	
	
	
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	td.setAttribute("style", "color:#610B0B");
	td.innerHTML="TOTAL ";
	tr.appendChild(td);
	
	
	
	var total=0;
	for(var i=0;i<pay_element_type_id.length;i++)
	{
		
		if(pay_element_type_id[i]=="E")
		{
		
	
				var td1=document.createElement("td");				
				td1.innerHTML=pay_total[i];		
				td1.setAttribute("style", "color:red;");
				tr.appendChild(td1);
				total=parseInt(total)+parseInt(pay_total[i]);
			}
		}
		
	var tot=0;
	for(var i=0; i<ADDITIONAL_PAY_TYPE.length;i++)
	{			
				
		tot=parseInt(ADDITIONAL_PAY_VALUE[i])+parseInt(tot);
		
		
	}
//	alert(tot);
	total=parseInt(total)+tot;
	var td1=document.createElement("td");				
	td1.innerHTML=total;
	
	td1.setAttribute("style", "color:blue");
	tr.appendChild(td1);			
	for(var i=0;i<pay_element_type_id.length;i++)
	{
		
		if(pay_element_type_id[i]=="D")
		{
		
	
				var td1=document.createElement("td");
				td1.setAttribute("style", "color:red");
				td1.innerHTML=pay_total[i];				
				tr.appendChild(td1);
				total=parseInt(total)+parseInt(pay_total[i]);
			}
		}
	var td1=document.createElement("td");				
	td1.innerHTML="";
	td1.setAttribute("style","color:#0B0B3B;size:2");
	tr.appendChild(td1);		
	tbody.appendChild(tr);
	
	
	try
	{
		for(var p=0;p<ADDITIONAL_PAY_TYPE.length;p++)
			{
		if(ADDITIONAL_PAY_TYPE[p]=="SLS")
		{
		document.getElementById("SLS").value=ADDITIONAL_PAY_VALUE[p];
		}
		else if(ADDITIONAL_PAY_TYPE[p]=="BON")
		{
		document.getElementById("bonus").value=ADDITIONAL_PAY_VALUE[p];
		}
		else if(ADDITIONAL_PAY_TYPE[p]=="PAY")
		{
		document.getElementById("payarr").value=ADDITIONAL_PAY_VALUE[p];
		}
		else if(ADDITIONAL_PAY_TYPE[p]=="DA")
		{
		document.getElementById("daarr").value=ADDITIONAL_PAY_VALUE[p];
		}
			}
	}
	catch(e)
	{
		
	}
	
	
}
//************************************* DELETE PAY DETAILS ************************************
function toalvalue()
{
//	alert("HELLO");
	var tot=0;
	for(var i=0;i<pay_element_type_id.length;i++)
	{
//	alert(pay_element_type_id[i]);
		if(pay_element_type_id[i]=="E")
		{
			tot=parseInt(tot)+parseInt(document.getElementById(pay_element_id[i]).value);
		}
	}
	document.getElementById("total").value=tot;
}
//************************************ SET ADD PAY DETAILS ************************************
function SetAddPayValue(id)
{
	var cnt=0;
	for(var p=0;p<ADDITIONAL_PAY_TYPE.length;p++)
	{
		if(ADDITIONAL_PAY_TYPE[p]=="SLS" && id=="SLS")
		{
			ADDITIONAL_PAY_VALUE[p]=document.getElementById("SLS").value;
			cnt++;
		}
		else if(ADDITIONAL_PAY_TYPE[p]=="BON"  && id=="BON")
		{
			ADDITIONAL_PAY_VALUE[p]=document.getElementById("bonus").value;
			cnt++;
		}
		else if(ADDITIONAL_PAY_TYPE[p]=="PAY"  && id=="PAY")
		{
			ADDITIONAL_PAY_VALUE[p]=document.getElementById("payarr").value;
			cnt++;
		}
		else if(ADDITIONAL_PAY_TYPE[p]=="DA"  && id=="DA")
		{
			ADDITIONAL_PAY_VALUE[p]=document.getElementById("daarr").value;
			cnt++;
		}
	}
	
	if(cnt==0)
		{
			cnt=ADDITIONAL_PAY_TYPE.length;
			
			
			
			if(id=="SLS")
			{
				ADDITIONAL_PAY_DESC[cnt]="SLS on 15 days";			
				ADDITIONAL_PAY_VALUE[cnt]=document.getElementById("SLS").value;
				ADDITIONAL_PAY_TYPE[cnt]="SLS";
			}
			else if(id=="BON")
			{
				ADDITIONAL_PAY_DESC[cnt]="Bonus Amount";			
				ADDITIONAL_PAY_VALUE[cnt]=document.getElementById("bonus").value;
				ADDITIONAL_PAY_TYPE[cnt]="BON";
				
			}
			else if(id=="PAY")
			{
				ADDITIONAL_PAY_DESC[cnt]="PAY Arrear Amount";			
				ADDITIONAL_PAY_VALUE[cnt]=document.getElementById("payarr").value;
				ADDITIONAL_PAY_TYPE[cnt]="PAY";
			}
			else if(id=="DA")
			{
				ADDITIONAL_PAY_DESC[cnt]="DA Arrear Amount";			
				ADDITIONAL_PAY_VALUE[cnt]=document.getElementById("daarr").value;
				ADDITIONAL_PAY_TYPE[cnt]="DA";
				
			}
			
			
			
		}
	LoadGrid();

}
//**************************************** ADD PAY DETAILS ************************************
function adddetails()
{
var syear=document.getElementById("year").value;
var smonth=document.getElementById("month").value;
var total=0;

for(var i=0;i<SAL_YEAR.length;i++)
{
	if(SAL_YEAR[i]==syear && SAL_MONTH[i] ==smonth)
	{
		PAY_AMOUNT[i]=document.getElementById(PAY_ELEMENT[i]).value;
		
		if(PAY_TYPE[i]=="E")
		{
		total=parseInt(total)+parseInt(PAY_AMOUNT[i]);
		}
	}
}
for(var j=0;j<salary_years.length;j++)
{
	if(salary_years[j]==syear && salary_months[j]==smonth)
		{
		TOTAL[j]=total;
		}
}
sumpayDetails();
}
//****************************************EDIT PAY DETAILS ************************************

function EditPayDetails(syear,smonth)
{
	
	document.getElementById("year").value=syear;
	setMonth();
	var total=0;
	for(var i=0;i<SAL_YEAR.length;i++)
		{
				if(SAL_YEAR[i]==syear && SAL_MONTH[i] ==smonth)
				{
					document.getElementById(PAY_ELEMENT[i]).value=PAY_AMOUNT[i];
					
					if(PAY_TYPE[i]=="E")
						{
						total=parseInt(total)+parseInt(PAY_AMOUNT[i]);
						}
				}
		}
	
	document.getElementById("total").value=total;
	document.getElementById("month").value=smonth;
	
//	sumpayDetails();
}


//***************************************** SUM PAY AMOUNT *************************************
function sumpayDetails()
{
	for(var i=0;i<pay_element_type_id.length;i++)
	{
		var total=0;
		for(var j=0; j<PAY_ELEMENT.length;j++)
		{
			
			if(pay_element_id[i]==PAY_ELEMENT[j])
				{
				total=parseInt(total)+parseInt(PAY_AMOUNT[j]);
				}
			
		}
		pay_total[i]=total;
	}
	
	
	LoadGrid();
}


//***************************************** loadDADetails(baseresponse); ***********************

function loadDetails_new(baseresponse)
{
	
	flag =  baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//	alert(flag);
	var tbody=document.getElementById("pay");
	
	
	if(flag=="success")
	{
		
		var earncount=0;
		
		var dedcount=0;
		
		var cnt =  baseresponse.getElementsByTagName("count");

		for(var i=0; i<cnt.length;i++)
		{			
			pay_element_id[i]=baseresponse.getElementsByTagName("pay_element_id")[i].firstChild.nodeValue;			
			pay_element_short_name[i]=baseresponse.getElementsByTagName("pay_element_short_name")[i].firstChild.nodeValue;
			pay_element_type_id[i]=baseresponse.getElementsByTagName("pay_element_type_id")[i].firstChild.nodeValue;
			pay_total[i]=baseresponse.getElementsByTagName("PAYTOTAL")[i].firstChild.nodeValue;
		
		}
		
		var fin_year=document.getElementById("fin_year").value;
		var year=new Array();
		year=fin_year.split("-");
		
		
		var from_year=year[0];
		var from_month=3;
		var to_year=year[1];
		var to_month=2;
		from_year=parseInt(from_year);
		from_month=parseInt(from_month);
		to_year=parseInt(to_year);
		to_month=parseInt(to_month);
		
		var cnt=0;
		var len=0;

		
		for(var j=from_year;j<=to_year;j++)
		{
		
				if(j==from_year)
				{
					var k=0;
					if(from_year==to_year)
						k=to_month;
					else
						k=12;
					for(var i=from_month;i<=k;i++)
					{
						salary_years[cnt]=j;
						salary_months[cnt]=i;						
						cnt++;
						
					}
				}
				else if(j==to_year)
					{
					for(var i=1;i<=to_month;i++)
					{
						salary_years[cnt]=j;
						salary_months[cnt]=i;							
						cnt++;
					}
					}
				else
					{
					for(var i=1;i<=12;i++)
					{
						salary_years[cnt]=j;
						salary_months[cnt]=i;

						
						cnt++;
					}
					
					}
		
		
		}
			
			
			
			
		try
		{
			var PAYCOUNT =  baseresponse.getElementsByTagName("PAYCOUNT");

			for(var i=0; i<PAYCOUNT.length;i++)
			{			
				PAY_ELEMENT[i]=baseresponse.getElementsByTagName("PAY_ID")[i].firstChild.nodeValue;			
				SAL_YEAR[i]=baseresponse.getElementsByTagName("SALARY_YEAR")[i].firstChild.nodeValue;
				SAL_MONTH[i]=baseresponse.getElementsByTagName("SALARY_MONTH")[i].firstChild.nodeValue;
				PAY_AMOUNT[i]=baseresponse.getElementsByTagName("PAY_ELEMENT_VALUE")[i].firstChild.nodeValue;
				PAY_TYPE[i]=baseresponse.getElementsByTagName("PAY_ELEMENT_TYPE_ID")[i].firstChild.nodeValue;
				
			}
			
		
		
		}
		catch(e)
		{
			
		}
		

		
		try
		{
			var ADDCOUNT =  baseresponse.getElementsByTagName("ADDCOUNT");

			for(var i=0; i<ADDCOUNT.length;i++)
			{			
				ADDITIONAL_PAY_DESC[i]=baseresponse.getElementsByTagName("ADDITIONAL_PAY_DESC")[i].firstChild.nodeValue;			
				ADDITIONAL_PAY_VALUE[i]=baseresponse.getElementsByTagName("ADDITIONAL_PAY_VALUE")[i].firstChild.nodeValue;
				ADDITIONAL_PAY_TYPE[i]=baseresponse.getElementsByTagName("ADDITIONAL_PAY_TYPE")[i].firstChild.nodeValue;
				
			}
		
		}
		catch(e)
		{
			
		}
		
		LoadGrid();
	}
	else
	{
		var tr=document.createElement("tr");
		var div=document.createElement("div");
		div.setAttribute("align","center");
		tr.appendChild(div);
		var tds=document.createElement("td");
		tds.setAttribute("align","center");
		tds.setAttribute("style","color:#5A0000");
		tds.setAttribute("colspan","3");
		tds.innerHTML=" This Employee Doesn't have Record for this period..." ;
		tr.appendChild(tds);
		tbody.appendChild(tr);
	}
}
function setMonth()
{
	var months =new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	
	var select = document.getElementById("month");
	select.innerHTML="";
	var year=document.getElementById("year").value;
	var opt=document.createElement("option");
	var txt=document.createTextNode("Month");
	opt.setAttribute("value", "0");
	opt.appendChild(txt);
	select.appendChild(opt);
	
	for(var m=0;m<salary_years.length;m++)
	{
		
		
		if(salary_years[m]==year)
			{
			var opt=document.createElement("option");
			var txt=document.createTextNode(months[salary_months[m]-1]);
			opt.setAttribute("value", salary_months[m]);
			opt.appendChild(txt);
			select.appendChild(opt);
			
			}
		
	}
}




function closeWin()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
};



function LastDayOfMonth(year,month)
{
	
		var m = [31,28,31,30,31,30,31,31,30,31,30,31];
		if (month != 2) return m[month - 1];
		if (year%4 != 0) return m[1];
		if (year%100 == 0 && year%400 != 0) return m[1];
		
		return m[1] + 1;
		 
}



function saveFunction()
{

	var emp_id=document.getElementById("txtEmployeeid").value;
	var fin_year=document.getElementById("fin_year").value;
//	var from_month=document.getElementById("from_month").value;
//	var to_year=document.getElementById("to_year").value;
//	var to_month=document.getElementById("to_month").value;
//	var proc_year=document.getElementById("proc_year").value;
//	var proc_month=document.getElementById("proc_month").value;
	var office_id=document.getElementById("cmbOffice_code").value;
	var surl="";
	for(var i=0;i<SAL_YEAR.length;i++)
		{
		
		surl=surl+"&SAL_YEAR="+SAL_YEAR[i]+"&SAL_MONTH="+SAL_MONTH[i]+"&PAY_ELEMENT="+PAY_ELEMENT[i]+"&PAY_AMOUNT="+PAY_AMOUNT[i];
		}
	
	for(var i=0;i<ADDITIONAL_PAY_DESC.length;i++)
	{
	
	surl=surl+"&ADDITIONAL_PAY_DESC="+ADDITIONAL_PAY_DESC[i]+"&ADDITIONAL_PAY_VALUE="+ADDITIONAL_PAY_VALUE[i]+"&ADDITIONAL_PAY_TYPE="+ADDITIONAL_PAY_TYPE[i];
	}
	
	
	
	var url = "../../../../../Hrm_pay_emp_IT_Updation?command=SAVEDETAILS&emp_id="
		+ emp_id + "&fin_year=" + fin_year+ "&office_id=" + office_id+surl;
//	alert(url);
	var req = getTransport();
	req.open("POST", url, true);
	req.onreadystatechange = function() {
		newresponse(req);
	};
	req.send(null);

	
	
	
}
