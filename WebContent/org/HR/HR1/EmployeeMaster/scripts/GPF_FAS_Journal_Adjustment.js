
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
function datefun()
{
        var fmon=document.Hrm_TransJoinForm.ac_month.value;
        var fyear=document.Hrm_TransJoinForm.ac_year.value;
        var tmon=document.Hrm_TransJoinForm.ToBeMadeMonth.value;
        var tyear=document.Hrm_TransJoinForm.ToBeMadeYear.value;
       if(fyear>tyear)
        {
            alert('The Selected Year should be greater than Accounting Year');
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('The Selected Month should be greater than Accounting Month');
                    return false;
                }
               
        }
       
}
function loadMonth()
{
	 document.Hrm_TransJoinForm.fin_year.value=0;
	// document.Hrm_TransJoinForm.JournalRef.value=0;
	 var minorcmb = document.Hrm_TransJoinForm.ToBeMadeMonth;
	// var cmn1=document.Hrm_TransJoinForm.ActualMadeMonth;
	 var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	    var months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	  for(var i=0; i<month.length; i++){
          var opt1 = document.createElement('option');
          opt1.value = months_val[i];
          opt1.innerHTML = month[i];
          minorcmb.appendChild(opt1);
	  }
         /* for(var i=0; i<month.length; i++){
              var opt2 = document.createElement('option');
              opt2.value = months_val[i];
              opt2.innerHTML = month[i];
             
          cmn1.appendChild(opt2);
} */
          loadYear();
}

function loadYear()
{ 
	   var now=new Date();
	   var fyear=now.getFullYear();
	   var k=document.Hrm_TransJoinForm.ToBeMadeYear;
	  
	   var opt1 = document.createElement('option');
	   opt1.Value=parseInt(fyear-1,10);
	   opt1.innerHTML=fyear-1;
	   k.appendChild(opt1);
	   
	   var opt2 = document.createElement('option');
	   opt2.Value=parseInt(fyear,10);
	   opt2.innerHTML=fyear;
	   k.appendChild(opt2);
	   
	   opt2 = document.createElement('option');
	   opt2.Value=parseInt(fyear+1,10);
	   opt2.innerHTML=fyear+1;
	   k.appendChild(opt2);
	   
	  /* k=document.Hrm_TransJoinForm.ActualMadeYear;
	   var opt1 = document.createElement('option');
	   opt1.Value=parseInt(fyear-1,10);
	   opt1.innerHTML=fyear-1;
	   k.appendChild(opt1);
	   
	   var opt2 = document.createElement('option');
	   opt2.Value=parseInt(fyear,10);
	   opt2.innerHTML=fyear;
	   k.appendChild(opt2);
	   
	   opt2 = document.createElement('option');
	   opt2.Value=parseInt(fyear+1,10);
	   opt2.innerHTML=fyear+1;
	   k.appendChild(opt2);*/
}
function getTobeMadeMonthYear()
{
	var url="../../../../../GPF_FAS_Journal_Adjustment?command=loadToBeMonYear"+"&headcode="+acc_head_code+"&acc_unit="+acc_unit+"&ac_year="+ac_year+"&ac_month="+ac_month;
	 
    var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 handleJournalResponse(req);
    }   
            req.send(null);
}
function updateAdjustment()
{
	 var JRNL_refNo=document.Hrm_TransJoinForm.JournalRef.value;
	 
	 var acc_unit=document.Hrm_TransJoinForm.Acc_unit_code.value;
	 var ac_year=document.Hrm_TransJoinForm.ac_year.value;
	 var ac_month=document.Hrm_TransJoinForm.ac_month.value;
	 var ac_head_code=document.Hrm_TransJoinForm.Acc_Head_Code.value;
	 var val_as_data=document.Hrm_TransJoinForm.Total_Up_Data.value;
	 var val_as_TR=document.Hrm_TransJoinForm.Total_Trial_Balance.value;
	 var tobeMonth=document.Hrm_TransJoinForm.ToBeMadeMonth.value;
	 var tobeYear=document.Hrm_TransJoinForm.ToBeMadeYear.value;
	// var actualMonth=document.Hrm_TransJoinForm.ActualMadeMonth.value;
	 //var actualyear=document.Hrm_TransJoinForm.ActualMadeYear.value;
	 var JRNL_refNo=document.Hrm_TransJoinForm.JournalRef.value;
	 var remarks=document.Hrm_TransJoinForm.txtRemarks.value;
	 if(acc_unit=="0" || ac_year=="0" || ac_month=="0")
		 alert("Required field cannot be left blank ");
	 else
	 {
	// var url="../../../../../GPF_FAS_Journal_Adjustment";
	var url="../../../../../GPF_FAS_Journal_Adjustment?command=submitDetails"+"&acc_unit="+acc_unit+"&ac_year="+ac_year+"&ac_month="+ac_month+"&ac_head_code="+ac_head_code+"&val_as_data="+val_as_data+"&val_as_TR="+val_as_TR;
	 url=url+"&tobeMonth="+tobeMonth+"&tobeYear="+tobeYear;
	 //url=url+"&actualMonth="+actualMonth+"&actualyear="+actualyear;
	 url=url+"&JRNL_refNo="+JRNL_refNo+"&remarks="+remarks;
     var req=getTransport();
     req.open("GET",url,true); 
     
     req.onreadystatechange=function()
     {
    	 handleJournalResponse(req);
     }   
             req.send(null);
	 }
}
function handleJournalResponse(req)
{
	if(req.readyState==4)
    {
	  
         if(req.status==200)
        {
                 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(tagcommand=="loaddata")
            {
            	var updata= baseResponse.getElementsByTagName("upData")[0].firstChild.nodeValue;
            	var trialData= baseResponse.getElementsByTagName("trialData")[0].firstChild.nodeValue;
            	var Diff= baseResponse.getElementsByTagName("Diff")[0].firstChild.nodeValue;
            	var headcodeDesc= baseResponse.getElementsByTagName("headDesc")[0].firstChild.nodeValue;
            	var tobeMonth=baseResponse.getElementsByTagName("toBeMonth")[0].firstChild.nodeValue;
            	var tobeYear=baseResponse.getElementsByTagName("toBeYear")[0].firstChild.nodeValue;
            	document.Hrm_TransJoinForm.headCodeDesc.value=headcodeDesc;
            	document.Hrm_TransJoinForm.Total_Up_Data.value=updata;
            	document.Hrm_TransJoinForm.Total_Trial_Balance.value=trialData;
            	 document.Hrm_TransJoinForm.Total_Diff.value=Diff;
            	  var monYear= document.getElementById("toBeMonthYear");
            	  monYear.innerHTML="";
            	  var months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
            	  var tbl     = document.createElement("table");
                  var tblBody = document.createElement("tbody");
                  var row = document.createElement("tr");
                
                  var cell = document.createElement("th");
                  var cellText1 = document.createTextNode(months[parseInt(tobeMonth-1,10)]);
                  cell.appendChild(cellText1);
                  row.appendChild(cell);
                 cell = document.createElement("th");
                  var cellText2 = document.createTextNode(tobeYear);
                  cell.appendChild(cellText2);
                  
                  
                  row.appendChild(cell);
                  tblBody.appendChild(row);
                  tbl.appendChild(tblBody);
                if(tobeMonth!="0" && tobeMonth!=null && tobeYear!="0" && tobeYear!=null)
                  monYear.appendChild(tbl);
                  tbl.setAttribute("border", "0");
                  if(updata=="0" && trialData=="0" && Diff=="0")
            		 alert("There is no data to load");
            }
            else if(tagcommand=="headcodes")
            {
            	var headcode= baseResponse.getElementsByTagName("headcode");
            	document.Hrm_TransJoinForm.Acc_Head_Code.length=1;
                var combo=document.Hrm_TransJoinForm.Acc_Head_Code;
            	for(var i=0;i<headcode.length;i++)
            	{
            		 var opt = document.createElement('option');
            		 opt.value =headcode[i].firstChild.nodeValue;
            		 opt.innerHTML =headcode[i].firstChild.nodeValue;
            		 combo.appendChild(opt);
            	}
            }
            else
            {
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
           
           
            if(flag!="0"){
            	alert("Successfully Updated");
            	window.close();
            }
            else
            	alert("Updation failed");
            }
        }
    }
            
}


function selectActMonthByActYear1(){
    var acyear=document.getElementById("ac_year");
    var year1=acyear.options[1].value;  
    
    //var year2=acyear.options[2].value;
    var sel_year=acyear.options[acyear.selectedIndex].value;
    var start_months = new Array('APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    var start_months_val = new Array(4,5,6,7,8,9,10,11,12);
    var end_months = new Array('JAN','FEB','MAR');
    var end_months_val = new Array(1,2,3);
    var minorcmb = document.Hrm_TransJoinForm.ac_month;
     document.Hrm_TransJoinForm.ac_month.length=1;
    if(parseInt(sel_year)==parseInt(year1)){
        for(var i=0; i<start_months.length; i++){
            var opt1 = document.createElement('option');
            opt1.value = start_months_val[i];
            opt1.innerHTML = start_months[i];
            minorcmb.appendChild(opt1);
        }
    }
    else{
    for(var i=0; i<end_months.length; i++){
            var opt1 = document.createElement('option');
            opt1.value = end_months_val[i];
            opt1.innerHTML = end_months[i];
            minorcmb.appendChild(opt1);
        }
    }

}


function check()
{
	var acc_unit=document.Hrm_TransJoinForm.Acc_unit_code.value;
	 
	 var ac_year=document.Hrm_TransJoinForm.ac_year.value;
	 var ac_month=document.Hrm_TransJoinForm.ac_month.value;
	 if(acc_unit=="0")
	 {
		 alert("Select Accounting Unit");
		 return false;
	 }
	 else if(ac_year=="0")
	 {
		 alert("Select Accounting Year");
		 return false;
	 }
	 else if(ac_month=="0")
	 {
		 alert("Select Accounting Month");
		 return false;
	 }
	 else
	 {
		 return true;
	 }
}
function loadData()
{
	 if(check())
	 {
	 var acc_unit=document.Hrm_TransJoinForm.Acc_unit_code.value;
	 
	 var ac_year=document.Hrm_TransJoinForm.ac_year.value;
	 var ac_month=document.Hrm_TransJoinForm.ac_month.value;
	 
	var acc_head_code=document.Hrm_TransJoinForm.Acc_Head_Code.value;

	var url="../../../../../GPF_FAS_Journal_Adjustment?command=load"+"&headcode="+acc_head_code+"&acc_unit="+acc_unit+"&ac_year="+ac_year+"&ac_month="+ac_month;
	var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 handleJournalResponse(req);
    }   
            req.send(null);
	 }
}
function giveAlert()
{
	var c=document.Hrm_TransJoinForm.fin_year.value;
	if(c=="0")
		alert("Select Financial Year");
}

function callfun1()
{
	document.Hrm_TransJoinForm.ac_year.value="0";
	document.Hrm_TransJoinForm.ac_month.value="0";
	document.Hrm_TransJoinForm.Acc_Head_Code.value="0";
	document.Hrm_TransJoinForm.Total_Up_Data.value=0;
	document.Hrm_TransJoinForm.Total_Trial_Balance.value=0;
	document.Hrm_TransJoinForm.Total_Diff.value=0;
	
var unit_id=document.getElementById("unit_name").value;

document.getElementById("Acc_unit_code").value=unit_id;
}
function loadAccHeadCodes()
{
	document.Hrm_TransJoinForm.headCodeDesc.value="";
	var acc_unit=document.Hrm_TransJoinForm.Acc_unit_code.value;
	var ac_year=document.Hrm_TransJoinForm.ac_year.value;
	var ac_month=document.Hrm_TransJoinForm.ac_month.value;
	var url="../../../../../GPF_FAS_Journal_Adjustment?command=headcode"+"&acc_unit="+acc_unit+"&ac_year="+ac_year+"&ac_month="+ac_month;
	var req=getTransport();
    req.open("GET",url,true); 
    
    req.onreadystatechange=function()
    {
   	 handleJournalResponse(req);
    }   
            req.send(null);
}
function clearData()
{
	document.Hrm_TransJoinForm.Acc_Head_Code.value="0";
	document.Hrm_TransJoinForm.Total_Up_Data.value=0;
	document.Hrm_TransJoinForm.Total_Trial_Balance.value=0;
	document.Hrm_TransJoinForm.Total_Diff.value=0;
	document.Hrm_TransJoinForm.headCodeDesc.value="";
}

function clearYear()
{
	document.Hrm_TransJoinForm.ToBeMadeYear.value="0";
}