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
function show2()
{	
    if (document.GPF_missing_cr_db_validation.slipPrint[0].checked==true)
    {
    	
         document.getElementById("refund").style.display='none';
    }
    else
    {
    	
        document.getElementById("refund").style.display='block';
    }   
}   
function getDeatils5()
{
	
	// var fin_year=document.getElementById("fin_year").value;
	//	if(fin_year=="0")
	//	{
	//		alert("Select Financial Year");
	//		return false;
	//	}
	 var jid=document.getElementById("jid").value;
	if(jid==0)
	{
		alert("Enter Journal Id");
		return false;
	}
	var url="../../../../../gpfMissingCRUpdation?command=get1&jid="+jid;
    var req=getTransport();
    req.open("GET",url,true);  
    req.onreadystatechange=function()
    {       
        if(req.readyState==4)
        { 
        	
            if(req.status==200)
            {  
            	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            	   getMaster(baseResponse);
            }
        }
    }
    req.send(null);
	
   

}
function viewjournal(id)
{
	 updatevalues1("no");
	 var journalId=document.getElementById("id"+id).value;
	 var type=document.getElementById("type"+id).value;
	 var amt=document.getElementById("amt"+id).value;
	 var year=document.getElementById("year"+id).value;
	 var month=document.getElementById("month"+id).value;
	 var intyear=document.getElementById("intyear"+id).value;
	 var intmonth=document.getElementById("intmonth"+id).value;
	 var select=document.getElementById("select"+id).value;
	var fin_year=document.getElementById("fin_year").value;
	var category=document.getElementById("category"+id).value;
	
	 var SLIP_PRINT_UNDER=document.getElementById("SLIP_PRINT_UNDER"+id).value;
		var INST_NO=document.getElementById("INST_NO"+id).value;
		var TOT_INST=document.getElementById("TOT_INST"+id).value;
	
	 var gpf="";
	 var url="";
	 if(document.getElementById("gpf"+id)){
	  gpf=document.getElementById("gpf"+id).value;
	  url='journalId='+journalId+'&type='+type+'&amt='+amt+'&gpf='+gpf+'&year='+year+'&month='+month+'&select='+select+'&category='+category+'&intyear='+intyear+'&intmonth'+intmonth;
	 }
	 else
	 {
		var head=document.getElementById("head"+id).value;
		url='journalId='+journalId+'&type='+type+'&amt='+amt+'&head='+head+'&year='+year+'&month='+month+'&select='+select+'&category='+category;
	 }
	 url=url+"&fin_year="+fin_year+"&SLIP_PRINT_UNDER="+SLIP_PRINT_UNDER+"&INST_NO="+INST_NO+"&TOT_INST="+TOT_INST;
	//alert(url);
	    window.open('GPF_Missing_DB_CR_updationPopup.jsp?command=view&'+url,'Summaryreport','width=700,height=600,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes');
}
function edit(queryString)
{

	
	selectMonth();
	 var url="../../../../../gpfMissingCRUpdation?"+queryString;
	
	   
	    var req=getTransport();
	    req.open("GET",url,true);     
	   
	    req.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(req.readyState==4)
	        { 
	        	
	            if(req.status==200)
	            {  
	            	   var baseResponse=req.responseXML.getElementsByTagName("details")[0];
	            	  
	            	   editData(baseResponse);
	            }
	        }
	    }
	    req.send(null);
	
}

function editData(baseResponse){
	
var journalId=baseResponse.getElementsByTagName("journalId")[0].firstChild.nodeValue;
var gpfNo=baseResponse.getElementsByTagName("gpfNo")[0].firstChild.nodeValue;
var amount=baseResponse.getElementsByTagName("amount")[0].firstChild.nodeValue;
var year=baseResponse.getElementsByTagName("year")[0].firstChild.nodeValue;
var month=baseResponse.getElementsByTagName("month")[0].firstChild.nodeValue;
var type=baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
document.GPF_missing_cr_db_validation.journalId.value=journalId;
document.GPF_missing_cr_db_validation.gpfname.value=gpfNo;
document.GPF_missing_cr_db_validation.amt.value=amount;
document.GPF_missing_cr_db_validation.rel_year.value=year;
document.GPF_missing_cr_db_validation.missing_type.value=type;
document.GPF_missing_cr_db_validation.rel_month.value=month;

getgpf();
if(type=='CR')
{
	
	document.GPF_missing_cr_db_validation.missing_type[0].checked=true;
	document.GPF_missing_cr_db_validation.missing_type[1].checked=false;
}
else
{
	document.GPF_missing_cr_db_validation.missing_type[0].checked=false;
	document.GPF_missing_cr_db_validation.missing_type[1].checked=true;
}
}
var financial_year;
function selectMonth1(slipyear)
{
	 v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();

var rel_year=document.getElementById("int_year").value;	
var i;
for(i=document.GPF_missing_cr_db_validation.int_month.options.length-1;i>=0;i--)
{

	document.GPF_missing_cr_db_validation.int_month.remove(i);
}

var minorcmb = document.GPF_missing_cr_db_validation.int_month;
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
function selectMonth2(slipyear)
{
	 v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();

var rel_year=document.getElementById("int_year").value;	
var i;
for(i=document.GPF_missing_cr_db_validation.int_month.options.length-1;i>=0;i--)
{

	document.GPF_missing_cr_db_validation.int_month.remove(i);
}

var minorcmb = document.GPF_missing_cr_db_validation.int_month;
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
function show1()
{	
    if (document.GPF_missing_cr_db_validation.missing_type[0].checked==true)
    {
    	// document.getElementById("debit").style.display='block';
       //  document.getElementById("credit").style.display='none';
//         for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
//     	{
//         document.GPF_missing_cr_db_validation.slipPrint[i].disabled=false;
//     	}
    }
    else
    {
    	//alert("dfd");
//    	document.getElementById("debit").style.display='none';
    //    document.getElementById("credit").style.display='block';
        document.getElementById("refund").style.display='none';
//        for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
//     	{
//        document.GPF_missing_cr_db_validation.slipPrint[i].disabled=true;
//     	}
    }   
}   

function subDetails(select,year,month,type,fin_year,category,intyear,intmonth,head,gpf,SLIP_PRINT_UNDER,INST_NO,TOT_INST)
{
	//alert(category);
	var v=new Date();
	var cur_mnth=v.getMonth();
	var cur_year=v.getFullYear();
	if(cur_year<=year && month>=3)
	{
	financial_year=cur_year+'-'+(cur_year+1);
	}
	else
	{
		financial_year=(cur_year-1)+'-'+(cur_year);
	}
	document.GPF_missing_cr_db_validation.rel_year.value=year;
	

	if(head.length>0){
	document.GPF_missing_cr_db_validation.ac_head_code.value=head;
	acheadcode();
	}
	else if(gpf.length>0)
	{
		document.GPF_missing_cr_db_validation.txtGpfNo.value=gpf;
		getgpf();
	}
	//alert(select);
	selectMonth();
	//alert("hai");
	document.GPF_missing_cr_db_validation.rel_month.value=month;
	if(intyear>0)
	{
		document.getElementById("intrestDiv").style.display='block';
 	   document.getElementById("intrestDiv1").style.display='block';
 	  document.GPF_missing_cr_db_validation.int_year.value=intyear;
 		//alert(select);
 		selectMonth1();
 		//alert("hai");
 		document.GPF_missing_cr_db_validation.int_month.value=intmonth;
	}
	else
	{
		  document.getElementById("intrestDiv").style.display='none';
   	   document.getElementById("intrestDiv1").style.display='none';
   	   document.getElementById("int_year").value=0;
	}
	//alert(select);
	if(select==0)
	{
		
		document.GPF_missing_cr_db_validation.typecredit[0].checked=true;
		document.GPF_missing_cr_db_validation.typecredit[1].checked=false;
		document.getElementById("AC_Head").style.display='block';
        document.getElementById("Other_gpfno").style.display='none';
	}
	else
	{
		//alert(select);
		document.GPF_missing_cr_db_validation.typecredit[0].checked=false;
		//alert("1");
		document.GPF_missing_cr_db_validation.typecredit[1].checked=true;
		document.getElementById("AC_Head").style.display='none';
        document.getElementById("Other_gpfno").style.display='block';
	}
	if(category=="Regular")
	{
		document.GPF_missing_cr_db_validation.category_type[0].checked=true;
		document.GPF_missing_cr_db_validation.category_type[1].checked=false;
		show_category();
	}
	else
	{
		document.GPF_missing_cr_db_validation.category_type[1].checked=true;
		document.GPF_missing_cr_db_validation.category_type[0].checked=false;
		show_category();
		document.GPF_missing_cr_db_validation.impound_type.value=category;
	}
	if(type=='CR')
	{
		
		document.GPF_missing_cr_db_validation.missing_type[0].checked=true;
		document.GPF_missing_cr_db_validation.missing_type[1].checked=false;
		 for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
	     	{
	         document.GPF_missing_cr_db_validation.slipPrint[i].disabled=false;
	     	}
	}
	else
	{
		document.GPF_missing_cr_db_validation.missing_type[0].checked=false;
		document.GPF_missing_cr_db_validation.missing_type[1].checked=true;
		 document.getElementById("refund").style.display='none';
         for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
      	{
         document.GPF_missing_cr_db_validation.slipPrint[i].disabled=true;
      	}
	}

}
function show2()
{	
    if (document.GPF_missing_cr_db_validation.slipPrint[0].checked==true)
    {
    	
         document.getElementById("refund").style.display='none';
    }
    else
    {
    	
        document.getElementById("refund").style.display='block';
    }   
}  
function journaldate1()
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
    for(i=document.GPF_missing_cr_db_validation.rel_month.options.length-1;i>=0;i--)
    {

    	document.GPF_missing_cr_db_validation.rel_month.remove(i);
    }

    var minorcmb = document.GPF_missing_cr_db_validation.rel_month;
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

function getMaster(baseResponse)
{
	
	var stat=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
	
	if(stat=="success")
	{
	var gpfNo=baseResponse.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
	var amount=baseResponse.getElementsByTagName("AMOUNT")[0].firstChild.nodeValue;
	var year=baseResponse.getElementsByTagName("RELATIVE_YEAR")[0].firstChild.nodeValue;
	
	var month=baseResponse.getElementsByTagName("RELATIVE_MONTH")[0].firstChild.nodeValue;
	var type=baseResponse.getElementsByTagName("MISSING_TRANS_TYPE")[0].firstChild.nodeValue;
	var name=baseResponse.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
	var journal=baseResponse.getElementsByTagName("JOURNAL_REF_NO")[0].firstChild.nodeValue;
	var journalDate=baseResponse.getElementsByTagName("JOURNAL_REF_DATE")[0].firstChild.nodeValue;
	var referal=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
	//alert(referal);
	var FUND_CATEGORY=baseResponse.getElementsByTagName("FUND_CATEGORY")[0].firstChild.nodeValue;
	var intyear=baseResponse.getElementsByTagName("Interest_YEAR")[0].firstChild.nodeValue;
	var intmonth=baseResponse.getElementsByTagName("Interest_MONTH")[0].firstChild.nodeValue;
	
	var SLIP_PRINT_UNDER=baseResponse.getElementsByTagName("SLIP_PRINT_UNDER")[0].firstChild.nodeValue;
	var INST_NO=baseResponse.getElementsByTagName("INST_NO")[0].firstChild.nodeValue;
	var TOT_INST=baseResponse.getElementsByTagName("TOT_INST")[0].firstChild.nodeValue;
	
	for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
	{
		if(document.GPF_missing_cr_db_validation.slipPrint [i].value==SLIP_PRINT_UNDER)
		{
			document.GPF_missing_cr_db_validation.slipPrint[i].checked=true;
			
			if(i!=0)
			{
				document.GPF_missing_cr_db_validation.Install_no.value=INST_NO;
				document.GPF_missing_cr_db_validation.totalNOInstall.value=TOT_INST;
				  document.getElementById("refund").style.display='block';
			}
			else
			{
				  document.getElementById("refund").style.display='none';
			}
		}
	}
//	alert(FUND_CATEGORY);
	
	if(FUND_CATEGORY=="Regular")
	{
		document.GPF_missing_cr_db_validation.category_type[0].checked=true;
		document.GPF_missing_cr_db_validation.category_type[1].checked=false;
		show_category();
	}
	else
	{
		document.GPF_missing_cr_db_validation.category_type[1].checked=true;
		document.GPF_missing_cr_db_validation.category_type[0].checked=false;
		show_category();
		document.GPF_missing_cr_db_validation.impound_type.value=FUND_CATEGORY;
	}
	//alert(referal);
	var v=new Date();
	var cur_mnth=v.getMonth();
	var cur_year=v.getFullYear();
	if(cur_year<=parseInt(year) && month>=3)
	{
	financial_year=(cur_year-1)+'-'+(cur_year);
	}
	else
	{
		financial_year=(cur_year-2)+'-'+(cur_year-1);
	}
	
	//alert(financial_year);
	document.getElementById("fin_year").value=financial_year;
	if(referal=='null')
		{
		referal='';
		}
	if(type=='CR')
	{
		
		document.GPF_missing_cr_db_validation.missing_type[0].checked=true;
		document.GPF_missing_cr_db_validation.missing_type[1].checked=false;
		//document.GPF_missing_cr_db_validation.slipPrint.disabled=false;
		
         for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
      	{
         document.GPF_missing_cr_db_validation.slipPrint[i].disabled=false;
      	}
	}
	else
	{
		document.GPF_missing_cr_db_validation.missing_type[0].checked=false;
		document.GPF_missing_cr_db_validation.missing_type[1].checked=true;
		 document.getElementById("refund").style.display='none';
         for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
      	{
         document.GPF_missing_cr_db_validation.slipPrint[i].disabled=true;
      	}
		//document.GPF_missing_cr_db_validation.slipPrint.disabled=true;
	}
	document.GPF_missing_cr_db_validation.txtGpfNo.value=gpfNo;
	document.GPF_missing_cr_db_validation.emp_name.value=name;
	document.GPF_missing_cr_db_validation.amount.value=amount;
	document.GPF_missing_cr_db_validation.rel_year.value=year;
	selectMonth();
	document.GPF_missing_cr_db_validation.rel_month.value=month;
	document.GPF_missing_cr_db_validation.jno.value=journal;
	document.GPF_missing_cr_db_validation.date.value=journalDate;
	document.GPF_missing_cr_db_validation.remarks.value=referal;
	if(intyear>0)
	{
		document.getElementById("intrestDiv").style.display='block';
 	   document.getElementById("intrestDiv1").style.display='block';
 	  document.GPF_missing_cr_db_validation.int_year.value=intyear;
 		//alert(select);
 		selectMonth2();
 		//alert("hai");
 		document.GPF_missing_cr_db_validation.int_month.value=intmonth;
	}
	else
	{
		  document.getElementById("intrestDiv").style.display='none';
   	   document.getElementById("intrestDiv1").style.display='none';
   	   document.getElementById("int_year").value=0;
	}
	gettransactiondetails();
	checkBalance();
	
}
	else if(stat=="validated")
	{
		alert("Already validated for the Journal Id");
	}
	return;
}
function checkBalance()
{
	 var jid=document.getElementById("jid").value;
	 var url="../../../../../gpfMissingCRUpdation?command=checkbalnce&jid="+jid;
	// alert(url)
    var req=getTransport();
    req.open("GET",url,true);     
   
    req.onreadystatechange=function()
    {
        //requesthandle(req);
        if(req.readyState==4)
        { 
        	
            if(req.status==200)
            {  
            	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            	  var result= baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
            	//  alert("dfd"+result);
            	  if(result=="balance")
            	  {
            		  document.GPF_missing_cr_db_validation.validate.disabled=false;
            		//  document.GPF_missing_cr_db_validation.update.disabled=false;
            	  }
            	  else
            	  {
            		  document.GPF_missing_cr_db_validation.validate.disabled=true;
            		  //document.GPF_missing_cr_db_validation.update.disabled=true;
            	  }
            	   
            }
        }
    }
    req.send(null);
}

function gettransactiondetails()
{
	
	 var jid=document.getElementById("jid").value;
	var req1=getTransport();
    var url="../../../../../gpfMissingCRUpdation?command=get&jid="+jid;
    req1.open("GET",url,true);     
   // document.GPF_missing_cr_db_validation1.valid.disabled=false;
    req1.onreadystatechange=function()
    {
        
        if(req1.readyState==4)
        { 
        	
            if(req1.status==200)
            {  
                    // alert(req1.responseText)  ;       
                var iframe=document.getElementById("tlist");
                iframe.style.visibility='visible';
                iframe.focus();
                // alert(navigator.appName);
                // alert(navigator.appName.indexOf('Microsoft'));
                
               // alert(req1.responseText);
                if(navigator.appName.indexOf('Microsoft')!=-1)
                    iframe.innerHTML=req1.responseText;
                else
                    iframe.innerText=req1.responseText;
                iframe.innerHTML=req1.responseText;
                
              
            }
        }
    }
    req1.send(null);
}

function selectMonth()
{
	
	var rel_year=document.getElementById("rel_year").value;	

	var v=new Date();
	var cur_mnth=v.getMonth();
	var cur_year=v.getFullYear();
	//alert(cur_mnth+cur_year);
	var finyear_val="";
	var falg=true;
	try{
	var finyear=document.getElementById("fin_year");
	 finyear_val=finyear.options[finyear.selectedIndex].value;
	}
	catch (e) {
		finyear_val=financial_year;
		falg=false;
	}
	//alert(finyear_val);
	if(finyear_val!=0)
	{
	var finyear_arr=new Array(2);
	finyear_val=finyear_val.split("-");
	   if(falg)
	   {
	var date="31/03/"+finyear_val[1];
	document.getElementById("date").value=date; 
	   }
	var i;
	for(i=document.GPF_missing_cr_db_validation.rel_month.options.length-1;i>=0;i--)
	{

		document.GPF_missing_cr_db_validation.rel_month.remove(i);
	}

	var minorcmb = document.GPF_missing_cr_db_validation.rel_month;
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


function update()
{
	xmlhttp=getTransport();
//alert("");
	  var gpf_no="";
	  var achead=false;
		 
			 if(document.GPF_missing_cr_db_validation.typecredit[0].checked)
			 {
				 
//alert("fdfd");
					var type="";
					var parm="";
					parm=parm+"journalId="+document.GPF_missing_cr_db_validation.jid.value;
					var falg=false;
					if(document.GPF_missing_cr_db_validation.typecredit[0].checked)
					{
						if(document.GPF_missing_cr_db_validation.ac_head_code.value!=0){
					parm=parm+"&ac_head_code="+document.GPF_missing_cr_db_validation.ac_head_code.value;
					falg=true;
						}
					}
					
					if(document.GPF_missing_cr_db_validation.typecredit[1].checked){
						if(document.GPF_missing_cr_db_validation.txtGpfNo.value.length>0)
						{
					parm=parm+"&gpfname="+document.GPF_missing_cr_db_validation.txtGpfNo.value;
					falg=true;
						}
					}
					//alert("fdfd"+falg);
				if(falg){
					parm=parm+"&amt="+document.GPF_missing_cr_db_validation.amt.value;
					parm=parm+"&rel_year="+document.GPF_missing_cr_db_validation.rel_year.value;
					parm=parm+"&rel_month="+document.GPF_missing_cr_db_validation.rel_month.value;
					 var slipPrint="",Install_no="0",totalNOInstall="0";
					if(document.GPF_missing_cr_db_validation.missing_type[0].checked){
					parm=parm+"&missing_type=CR";
					for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
					{
						if(document.GPF_missing_cr_db_validation.slipPrint [i].checked==true)
						{
							slipPrint=document.GPF_missing_cr_db_validation.slipPrint[i].value;
							if(i!=0)
							{
								Install_no=document.GPF_missing_cr_db_validation.Install_no.value;
								totalNOInstall=document.GPF_missing_cr_db_validation.totalNOInstall.value;
							}
						}
					}
					type="CR";
					}else
					{
						parm=parm+"&missing_type=DB";
						type="DB";
					}
					//alert(type);
					if(window.opener.document.GPF_missing_cr_db_validation.missing_type[0].checked && type=="CR")
					{
						alert("Select Corresponding CR/DB");
						return false;
					}
					if(window.opener.document.GPF_missing_cr_db_validation.missing_type[1].checked && type=="DB")
					{
						alert("Select Corresponding CR/DB");
						return false;
					}
					 parm=parm+"&int_year=0&int_month=0";
				
						
					 //alert(parm);
					parm=parm+"&oldamt="+document.GPF_missing_cr_db_validation.oldamt.value;
					parm=parm+"&oldtype="+document.GPF_missing_cr_db_validation.oldtype.value;
					parm=parm+"&oldgpf="+document.GPF_missing_cr_db_validation.oldgpf.value;
					parm=parm+"&oldhead="+document.GPF_missing_cr_db_validation.oldhead.value;
					parm=parm+"&slipPrint="+slipPrint+"&Install_no="+Install_no+"&totalNOInstall="+totalNOInstall;
					//alert(parm);
					 var url="../../../../../gpfMissingCRUpdation?command=update&"+parm;

						//alert(url);
					    var req=getTransport();
					    req.open("GET",url,true);     
					    
					    req.onreadystatechange=function()
					    {
					        //requesthandle(req);
					        if(req.readyState==4)
					        { 
					        	
					            if(req.status==200)
					            {  
					            	
					            	 var month=	window.opener.document.GPF_missing_cr_db_validation.rel_month.value;
					            	window.opener.top.getDeatils5();
					            	window.opener.focus();// opener - mainpage formName , getFieldonload -mainpage javascript function
						    		window.close();	
						    		window.opener.document.GPF_missing_cr_db_validation.rel_month.value=month;
					            	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
					            	   updateResponse(baseResponse);
					            }
					        }
					    }
					    req.send(null);	
				}
				else
				{
					alert("Enter GPF/SPL A/C Head");
					return false;
				}
					                     
			 }
			 if(document.GPF_missing_cr_db_validation.typecredit[1].checked)
			 {
				 gpf_no= document.getElementById("txtGpfNo").value;
				 achead=true;
			
		
		  
	 //alert(gpf_no);
	   if(gpf_no==""){
		   if(achead)
		   {
		 alert('Enter GPF No');  
		   }
	   }else{
	   
		var misstype=""; 
		var categorytype="";
		var flag=0;
		    if(document.GPF_missing_cr_db_validation.missing_type[0].checked==true)
			{
				flag=1;
				misstype='CR';
			}else if( document.GPF_missing_cr_db_validation.missing_type[1].checked==true)
			{
				flag=1;
				misstype='DB';
			}
		  //  alert(misstype);
		    if(document.GPF_missing_cr_db_validation.category_type[0].checked==true)
			{
				flag=1;
				categorytype='Regular';
			}else if( document.GPF_missing_cr_db_validation.category_type[1].checked==true)
			{
				flag=1;
				categorytype=document.getElementById("imp_types").value;
			}
		
		    if(flag!=0)
		    {
		    	//alert("getcheck");
		    	var relmonth=document.getElementById("rel_month").value;
				var relyear=document.getElementById("rel_year").value;
				var jid=document.getElementById("jid").value;
				
     url="../../../../../GPF_Missing_DB_CR?command=checkgpf1&gpf_no="+gpf_no+"&misstype="+misstype+"&categorytype="+categorytype+"&relmonth="+relmonth+"&relyear="+relyear+"&jid="+jid;
     url=url+"&sid="+Math.random();
   //  alert(url);
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(xmlhttp.readyState==4)
	        { 
	        	
	            if(xmlhttp.status==200)
	            {  
	            	
	            	//getDeatils5();
	            var	response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	            var    command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	             var   flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	             
	             if(command=="checkgpf")
	             {
	             	
	                 if(flag=='success')
	                 {
	                	 
	                   
	                    alert('This GPF NO Missing Credit/Debit is already submitted');
	                   
	                   // alert(checkflag1);
	                     document.getElementById("txtGpfNo").value="";
	                    // document.getElementById("emp_name").value="";
	                    
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
		            	   var int_year=0,int_month=0;
		            	   
		            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
		            	  
			     	         var relmonth=document.getElementById("rel_month").value;
			   	          var relyear=document.getElementById("rel_year").value;
		            	   if(data=='entry')
		            	   {
		            		   document.getElementById("intrestDiv").style.display='block';
			            	   document.getElementById("intrestDiv1").style.display='block';
			            	   document.getElementById("intrestDiv2").style.display='block';
			            	   document.getElementById("intrestDiv3").style.display='block';
			            	   document.getElementById("retiredDate").value=response.getElementsByTagName("retriedDate")[0].firstChild.nodeValue;
		            		
			            	   int_year=document.getElementById("int_year").value;
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
		            	   else if(data=='Failentry')
		            	   {
	// check condition alert for retired after slipyear
		            		   var response1 = confirm("Relative month & year is greater than Retired date.. Do u want to Proceed ");
		            		   int_year=document.getElementById("int_year").value;
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
		            	  
//		            	   var int_month1=0,int_year1=0;
//		            	   try
//		            	   {
//		            		   if(parseInt(document.getElementById("int_month").value)>0)
//		            		   {
//	 int_month1=parseInt(document.getElementById("int_month").value);
//	 int_year1=parseInt(document.getElementById("int_year").value);
//		            		   }
//		            	   }
//		            	   catch (e) {
//							// TODO: handle exception
//						}
		            	 //  alert("enter123");
	var type="";
	var parm="";
	 parm=parm+"&int_year="+int_year+"&int_month="+int_month;
	parm=parm+"&journalId="+document.GPF_missing_cr_db_validation.jid.value;
	var falg=false;
	if(document.GPF_missing_cr_db_validation.typecredit[0].checked)
	{
		if(document.GPF_missing_cr_db_validation.ac_head_code.value!=0){
	parm=parm+"&ac_head_code="+document.GPF_missing_cr_db_validation.ac_head_code.value;
	falg=true;
		}
	}
	//alert("enter111");
	if(document.GPF_missing_cr_db_validation.typecredit[1].checked){
		if(document.GPF_missing_cr_db_validation.txtGpfNo.value.length>0)
		{
	parm=parm+"&gpfname="+document.GPF_missing_cr_db_validation.txtGpfNo.value;
	falg=true;
		}
	}
if(falg){
	parm=parm+"&amt="+document.GPF_missing_cr_db_validation.amt.value;
	parm=parm+"&rel_year="+document.GPF_missing_cr_db_validation.rel_year.value;
	parm=parm+"&rel_month="+document.GPF_missing_cr_db_validation.rel_month.value;
	var slipPrint="",Install_no="0",totalNOInstall="0";
	if(document.GPF_missing_cr_db_validation.missing_type[0].checked){
	parm=parm+"&missing_type=CR";
	type="CR";
	for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
	{
		if(document.GPF_missing_cr_db_validation.slipPrint [i].checked==true)
		{
			slipPrint=document.GPF_missing_cr_db_validation.slipPrint[i].value;
			if(i!=0)
			{
				Install_no=document.GPF_missing_cr_db_validation.Install_no.value;
				totalNOInstall=document.GPF_missing_cr_db_validation.totalNOInstall.value;
			}
		}
	}
	}else
	{
		parm=parm+"&missing_type=DB";
		type="DB";
	}
	//alert(type);
	if(window.opener.document.GPF_missing_cr_db_validation.missing_type[0].checked && type=="CR")
	{
		alert("Select Corresponding CR/DB");
		return false;
	}
	if(window.opener.document.GPF_missing_cr_db_validation.missing_type[1].checked && type=="DB")
	{
		alert("Select Corresponding CR/DB");
		return false;
	}
	//alert("enter153");
	
	
	parm=parm+"&oldamt="+document.GPF_missing_cr_db_validation.oldamt.value;
	parm=parm+"&oldtype="+document.GPF_missing_cr_db_validation.oldtype.value;
	parm=parm+"&oldgpf="+document.GPF_missing_cr_db_validation.oldgpf.value;
	parm=parm+"&oldhead="+document.GPF_missing_cr_db_validation.oldhead.value;
	parm=parm+"&slipPrint="+slipPrint+"&Install_no="+Install_no+"&totalNOInstall="+totalNOInstall;
	 var url="../../../../../gpfMissingCRUpdation?command=update&"+parm;

		//alert(url);
	    var req=getTransport();
	    req.open("GET",url,true);     
	    
	    req.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(req.readyState==4)
	        { 
	        	
	            if(req.status==200)
	            {  
	            	// window.opener.top.updatevalues1("yes");
	            	 var month=	window.opener.document.GPF_missing_cr_db_validation.rel_month.value;
	            	window.opener.top.getDeatils5();
	            	window.opener.focus();// opener - mainpage formName , getFieldonload -mainpage javascript function
		    		window.close();	
		    		window.opener.document.GPF_missing_cr_db_validation.rel_month.value=month;
	            	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            	   updateResponse(baseResponse);
	            }
	        }
	    }
	    req.send(null);	
}
else
{
	alert("Enter GPF/SPL A/C Head");
	return false;
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
			
}
function updateResponse(response)
{
	var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(res=="success")
	{
		if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="validate")
		{
			
			//location.reload(true);
			
			
			
			alert("validated Successfully");
			window.location.reload(true);
			document.GPF_missing_cr_db_validation.validate.disable=false;
			//document.GPF_missing_cr_db_validation.update.disable=false;
			 //document.GPF_missing_cr_db_validation.action = document.GPF_missing_cr_db_validation.path.value+"/org/HR/HR1/EmployeeMaster/jsps/GPF_Missing_DB_CR_Validate.jsp";
			 //   document.GPF_missing_cr_db_validation.submit();  
			//document.GPF_missing_cr_db_validation.jid.value=0;
		}
		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="validated")
		{
			alert("Already validated");
		}
		else{
		
		alert("updated Successfully");
		window.close();
		}
	}
	else
	{
		
		alert("Process failure");
	}
		
}
function acheadcode(){
	 var req=getTransport();
	   var acheadcode=document.getElementById("ac_head_code").value;
	   if(acheadcode!=""){
       url="../../../../../gpfMissingCRUpdation?command=achead&acheadcode="+acheadcode;
       url=url+"&sid="+Math.random();
       req.open("GET",url,true);
       req.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(req.readyState==4)
	        { 
	        	
	            if(req.status==200)
	            {  
	            	response=req.responseXML.getElementsByTagName("response")[0];
	                command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	                flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
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
       req.send(null); 
	   }else{
		   alert('Enter Account Head Code');
	   }
}
function validatenew()
{
	var rel_month=parseInt(document.getElementById("rel_month").value);
	var rel_year=parseInt(document.getElementById("rel_year").value);
	var amount=parseInt(document.getElementById("amount").value);
	var unit=document.getElementById("Acc_unit_code").value;
	var gpfno=document.getElementById("txtGpfNo").value;
	//alert(gpfno);
	var misstype="";
	if(document.GPF_missing_cr_db_validation.missing_type[0].checked==true)
	{
		misstype="CR";
	}else{
		misstype="DB";
	}
	//alert(""+rel_year);
	var amt1=0;
	var currRow = document.getElementById("row").value;
	//alert(""+currRow);
	for(i=1;i<=parseInt(currRow);i++){ 
		//idle1();
	
		var rel_month1=document.getElementById("month"+i).value;
		var rel_year1=document.getElementById("year"+i).value;
		 amt1=amt1+parseInt(document.getElementById("amt"+i).value);
		var type1=document.getElementById("type"+i).value;
		//alert(rel_year1);
		if(rel_year!=rel_year1 || rel_month!=rel_month1)
		{
			alert("Month & Year Mismatch for CR/DB Entry");
			return false;
		}
		if(misstype==type1)
		{
			alert("select corresponding CR/DB");
			return false;
		}
		
	}
		
	if(amount!=amt1)
	{
		alert("Amount not equal");
		return false;
	}
	var answer = confirm  ("Please click on OK if data is updated");
	if (answer)
	{
	}
	else
	{
		//alert("click update button to update");
	return false;
	}
	var jid=document.getElementById("jid").value;
	 var url="../../../../../gpfMissingCRUpdation?command=validate&jid="+jid;

	  
	    var req=getTransport();
	    req.open("GET",url,true);     
	 
	    req.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(req.readyState==4)
	        { 
	        	
	            if(req.status==200)
	            {  
	            	
	            	//getDeatils5();
	            	
	            	   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            	   updateResponse(baseResponse);
	            }
	        }
	    }
	    req.send(null);
}


function getgpf(){
	xmlhttp=getTransport();
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
//var gpfcheckstatus;
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
                    document.getElementById("emp_name").value=empname;
                  //  document.getElementById("txtDOB").value=dob;
                    document.getElementById("txtGpfNo").value=gpfno;
                  //  document.getElementById("designation").value=d_id;
                   // document.getElementById("txtEmpId").value=emp_id;
 
                   // checkgpf();   
                }
                else
                    {
                	alert('GPF No Does not Exist');
                	document.getElementById("txtGpfNo").value="";
                    }
            }
       }
    }
}

function checkgpf(){
	xmlhttp=getTransport();
	var checkflag1=true;
	  var gpf_no=document.getElementById("txtGpfNo").value;
	  var achead=false;
		 try
		 {
			 if(document.GPF_missing_cr_db_validation.typecredit[0].checked)
			 {
				 achead=false;
			 }
			 if(document.GPF_missing_cr_db_validation.typecredit[1].checked)
			 {
				 achead=true;
			 }
		 }
		 catch (e)
		 {
			 achead=true;
		 }
		  
	 
	   if(gpf_no==""){
		   if(achead)
		   {
		 alert('Enter GPF No');  
		   }
	   }else{
	   
		var misstype=""; 
		var categorytype="";
		var flag=0;
		    if(document.GPF_missing_cr_db_validation.missing_type[0].checked==true)
			{
				flag=1;
				misstype='CR';
			}else if( document.GPF_missing_cr_db_validation.missing_type[1].checked==true)
			{
				flag=1;
				misstype='DB';
			}
		  //  alert(misstype);
		    if(document.GPF_missing_cr_db_validation.category_type[0].checked==true)
			{
				flag=1;
				categorytype='Regular';
			}else if( document.GPF_missing_cr_db_validation.category_type[1].checked==true)
			{
				flag=1;
				categorytype=document.getElementById("imp_types").value;
			}
		
		    if(flag!=0)
		    {
		    	//alert("getcheck");
		    	var relmonth=document.getElementById("rel_month").value;
				var relyear=document.getElementById("rel_year").value;
				var jid=document.getElementById("jid").value;
				
       url="../../../../../GPF_Missing_DB_CR?command=checkgpf1&gpf_no="+gpf_no+"&misstype="+misstype+"&categorytype="+categorytype+"&relmonth="+relmonth+"&relyear="+relyear+"&jid="+jid;
       url=url+"&sid="+Math.random();
     //  alert(url);
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(xmlhttp.readyState==4)
	        { 
	        	
	            if(xmlhttp.status==200)
	            {  
	            	
	            	//getDeatils5();
	            var	response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	            var    command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	             var   flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	             
	             if(command=="checkgpf")
	             {
	             	
	                 if(flag=='success')
	                 {
	                	 
	                   
	                    alert('This GPF NO Missing Credit/Debit is already submitted');
	                   
	                   // alert(checkflag1);
	                     document.getElementById("txtGpfNo").value="";
	                    // document.getElementById("emp_name").value="";
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
	   //alert(checkflag1);
	   return checkflag1;
}
function show_category()
{	
    if (document.GPF_missing_cr_db_validation.category_type[0].checked==true)
    {
    	 document.getElementById("imp_types").style.display='none';
    	 for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
       	{
           document.GPF_missing_cr_db_validation.slipPrint[i].disabled=false;
       	}
    }
    else
    {
    	document.getElementById("imp_types").style.display='block';
    	 for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
       	{
          document.GPF_missing_cr_db_validation.slipPrint[i].disabled=true;
       	}
    }   
}   
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
	        
	    winemp= window.open("EmpServicePopup.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
	    winemp.moveTo(250,250);  
	    winemp.focus();
  
}
function doParentEmp(emp)
{
	
document.getElementById("txtGpfNo").value=emp;
closeWin();
//call('get','s');
//alert("1");
getgpf();
}

function getrefresh()
{
	 var jid=document.getElementById("jid").value;
	
    var url="../../../../../gpfMissingCRUpdation?command=getCR&jid="+jid;

   
    var req=getTransport();
    req.open("GET",url,true);     
    document.GPF_missing_cr_db_validation.valid.disable=false;
    req.onreadystatechange=function()
    {
        //requesthandle(req);
        if(req.readyState==4)
        { 
        	
            if(req.status==200)
            {  
                              
                var iframe=document.getElementById("showdetails");
                iframe.style.visibility='visible';
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
    req.send(null);

}

function closeWin()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}


//************** popup script ************//

function show()
{
    if (document.GPF_missing_cr_db_validation.typecredit[0].checked==true)
    {
    	
         document.getElementById("AC_Head").style.display='block';
         document.getElementById("Other_gpfno").style.display='none';
         document.getElementById("txtGpfNo").value="";
 	 	document.getElementById("emp_name").value="";
    document.getElementById("ac_head_code").value="0";
 	document.getElementById("descr").value="";
 	 document.getElementById("intrestDiv").style.display='none';
	   document.getElementById("intrestDiv1").style.display='none';
	   document.getElementById("int_year").value=0;
	   if(window.opener.document.GPF_missing_cr_db_validation.missing_type[1].checked)
		{
		 for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
	      	{
	         document.GPF_missing_cr_db_validation.slipPrint[i].disabled=false;
	      	}
		}
    }
    else
    {
    	
    	 document.getElementById("Other_gpfno").style.display='block';
    	 document.getElementById("AC_Head").style.display='none';
         
    	    document.getElementById("txtGpfNo").value="";
    	 	document.getElementById("emp_name").value="";
    	 	document.getElementById("ac_head_code").value="0";
    	 	document.getElementById("descr").value="";
    	 	document.getElementById("refund").style.display='none';
            for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
         	{
            document.GPF_missing_cr_db_validation.slipPrint[i].disabled=true;
         	}
    }

    
}

function updatevalues1(check)
{
	xmlhttp=getTransport();

	  var gpf_no=document.getElementById("txtGpfNo").value;
	  var achead=false;
		 try
		 {
			 if(document.GPF_missing_cr_db_validation.typecredit[0].checked)
			 {
				 achead=false;
			 }
			 if(document.GPF_missing_cr_db_validation.typecredit[1].checked)
			 {
				 achead=true;
			 }
		 }
		 catch (e)
		 {
			 achead=true;
		 }
		  
	 
	   if(gpf_no==""){
		   if(achead)
		   {
		 alert('Enter GPF No');  
		   }
	   }else{
	   
		var misstype=""; 
		var categorytype="";
		var flag=0;
		    if(document.GPF_missing_cr_db_validation.missing_type[0].checked==true)
			{
				flag=1;
				misstype='CR';
			}else if( document.GPF_missing_cr_db_validation.missing_type[1].checked==true)
			{
				flag=1;
				misstype='DB';
			}
		  //  alert(misstype);
		    if(document.GPF_missing_cr_db_validation.category_type[0].checked==true)
			{
				flag=1;
				categorytype='Regular';
			}else if( document.GPF_missing_cr_db_validation.category_type[1].checked==true)
			{
				flag=1;
				categorytype=document.getElementById("imp_types").value;
			}
		
		    if(flag!=0)
		    {
		    	//alert("getcheck");
		    	var relmonth=document.getElementById("rel_month").value;
				var relyear=document.getElementById("rel_year").value;
				var jid=document.getElementById("jid").value;
				
   url="../../../../../GPF_Missing_DB_CR?command=checkgpf1&gpf_no="+gpf_no+"&misstype="+misstype+"&categorytype="+categorytype+"&relmonth="+relmonth+"&relyear="+relyear+"&jid="+jid;
   url=url+"&sid="+Math.random();
 //  alert(url);
   xmlhttp.open("GET",url,true);
   xmlhttp.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(xmlhttp.readyState==4)
	        { 
	        	
	            if(xmlhttp.status==200)
	            {  
	            	
	            	//getDeatils5();
	            var	response=xmlhttp.responseXML.getElementsByTagName("response")[0];
	            var    command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	             var   flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	             
	             if(command=="checkgpf")
	             {
	             	
	            	 if(flag=='success')
		               {
		                
		            	   document.getElementById("txtGpfNo").value="";
		                   document.getElementById("emp_name").value="";
		                  alert('This GPF NO Missing Credit/Debit is already submitted');
		                 
		                   
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
		            	   var int_year=0,int_month=0;
		            	   
		            	   var   data=response.getElementsByTagName("date")[0].firstChild.nodeValue;
		            	  
			     	         var relmonth=document.getElementById("rel_month").value;
			   	          var relyear=document.getElementById("rel_year").value;
			   	       int_year= document.getElementById("int_year").value;
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
		            	   else if(data=='Failentry')
		            	   {
	// check condition alert for retired after slipyear
		            		   var response1 = confirm("Relative month & year is greater than Retired date.. Do u want to Proceed ");

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
		            	  
	var rel_month=parseInt(document.getElementById("rel_month").value);
	var rel_year=parseInt(document.getElementById("rel_year").value);
	
	var amount=parseInt(document.getElementById("amount").value);
	var unit=document.getElementById("Acc_unit_code").value;
	var gpfno=document.getElementById("txtGpfNo").value;
	//alert(gpfno);
	var slipPrint="",Install_no="0",totalNOInstall="0";
	var misstype="";
	if(document.GPF_missing_cr_db_validation.missing_type[0].checked==true)
	{
		misstype="CR";
		for(i=0;i<document.GPF_missing_cr_db_validation.slipPrint.length;i++)
		{
			if(document.GPF_missing_cr_db_validation.slipPrint [i].checked==true)
			{
				slipPrint=document.GPF_missing_cr_db_validation.slipPrint[i].value;
				if(i!=0)
				{
					Install_no=document.GPF_missing_cr_db_validation.Install_no.value;
					totalNOInstall=document.GPF_missing_cr_db_validation.totalNOInstall.value;
				}
			}
		}
	}else{
		misstype="DB";
	}
	//alert(""+rel_year);
	var amt1=0;
	var currRow = document.getElementById("row").value;
	//alert(""+currRow);
	if(false)
	{
	for(i=1;i<=parseInt(currRow);i++){ 
		//idle1();
	
		var rel_month1=document.getElementById("month"+i).value;
		var rel_year1=document.getElementById("year"+i).value;
		 amt1=amt1+parseInt(document.getElementById("amt"+i).value);
		var type1=document.getElementById("type"+i).value;
		//alert(rel_year1);
		
		if(rel_year!=rel_year1 || rel_month!=rel_month1)
		{
			alert("Month & Year Mismatch for CR/DB Entry");
			return false;
		}
		if(misstype==type1)
		{
			alert("select corresponding CR/DB");
			return false;
		}
		
	}
		
	if(amount!=amt1)
	{
		alert("Amount not equal");
		return false;
	}
	}
	//var category_type="";
	//if(document.GPF_missing_cr_db_validation.category_type[0].checked==true){
	//	category_type="Regular";
//	}
	//else if(document.GPF_missing_cr_db_validation.category_type[1].checked==true){
	//	category_type=document.getElementById("impound_type").value;
	//}
	
	var jno=document.getElementById("jno").value;
	var date=document.getElementById("date").value;
	
	var remark=document.getElementById("remarks").value;

	var jid=document.getElementById("jid").value;
	
	
	//if(remark=="")
	//{
	//	remark="noRemark";
	//}
		
var	url="../../../../../gpfMissingCRUpdation?command=updateMaster&unit="+unit+"&gpfno="+gpfno+"&misstype="+misstype;

url=url+"&amount="+amount+"&jno="+jno+"&date="+date+"&remark="+remark+"&rel_month="+rel_month;
//alert(url);
url=url+"&rel_year="+rel_year+"&jid="+jid+"&int_year="+int_year+"&int_month="+int_month;
url=url+"&slipPrint="+slipPrint+"&Install_no="+Install_no+"&totalNOInstall="+totalNOInstall;
//alert(url);
	xmlhttp.open("GET",url,true);
	 xmlhttp.onreadystatechange=function()
	    {
	        //requesthandle(req);
	        if(xmlhttp.readyState==4)
	        { 
	        	
	            if(xmlhttp.status==200)
	            {  
	            	if(check=='yes')
	            	{
	            	alert('Updated Successfully');
	            	}
	            }
	        }
	    }
 		xmlhttp.send(null);
 	
 		 	
 		
      }
}
}
}
}
xmlhttp.send(null); 
}

}


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