
var regionflag=false;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;
var auditflag=true;
var labflag=true;

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


function CompareDate(fromDate,toDate)
{
	var ret=true;
	if(fromDate =="" || toDate == "")
	{
	}
	else
	{
		var fret=check_dateformat(fromDate);
		var tret=check_dateformat(toDate);
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
	
	fromYear=parseInt(f_date[2]);
	fromMonth=parseInt(f_date[1]);
	fromDay=parseInt(f_date[0]);
	
	toYear=parseInt(t_date[2]);
	toMonth=parseInt(t_date[1]);
	toDay=parseInt(t_date[0]);
	
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
		alert("From Date Should be less than TO Date");
		document.getElementById(txtfromdate).value="";
		document.getElementById(txttodate).value="";
		return false;
	}
	else
	{
		return true;
	}
	
}
function check_dateformat(field)
{
	
	var arr=new Array();
	
	var field_value=field;
	if(field_value=="")
	{
	}
	else
	{

	arr=field_value.split("/");

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
function frmsubmit()
{

	var Date1=document.frmValidationSummaryRep.txtfromdate.value;
		var Date2=document.frmValidationSummaryRep.txttodate.value;
	var compare=null;
	if (val())
	{
	if(Date1==""  || Date2=="")
	{
		alert("Please Fill the Date");
		return false;
	}
	else
	{
	compare=CompareDate(Date1,Date2);
	}
	
	
		if(compare==true)
		{
	
        var url="../../../../../../HRM_Employee_service_for_retirement_list?OLevel=submit";
           
        if(document.frmValidationSummaryRep.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmValidationSummaryRep.outputtype.value='pdf';
        }
        else if(document.frmValidationSummaryRep.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmValidationSummaryRep.outputtype.value='excel';
        }
        else if(document.frmValidationSummaryRep.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmValidationSummaryRep.outputtype.value='html';
        }
        
    document.frmValidationSummaryRep.action="../../../../../../HRM_Employee_service_for_retirement_list";
    document.frmValidationSummaryRep.submit();
  		}
	}
}
function checkdat1()
{
     var dt=document.frmValidationSummaryRep.txttodate.value;
     // alert('dt...'+dt);
     
         var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
    if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.frmValidationSummaryRep.txttodate.value="";
           document.frmValidationSummaryRep.txttodate.focus();
       }
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
        {
           alert('Month should be greater than current Month');
           document.frmValidationSummaryRep.txttodate.value="";
           document.frmValidationSummaryRep.txttodate.focus();
       }
       if(selectedMonth==(mon+1))
        {
          if(selectedDay < d)
           {
           alert('Date should be greater than current Date');
           document.frmValidationSummaryRep.txttodate.value="";
           document.frmValidationSummaryRep.txttodate.focus();
           
           }
       }
       }
       
       return true;

}
function checkdat()
{
     var dt=document.frmValidationSummaryRep.txtfromdate.value;
        var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
                
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
        if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.frmValidationSummaryRep.txtfromdate.value="";
           document.frmValidationSummaryRep.txtfromdate.focus();
       }
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
       {
         alert('Month should be greater than current Month');
           document.frmValidationSummaryRep.txtfromdate.value="";
           document.frmValidationSummaryRep.txtfromdate.focus();
       }
       
       if(selectedMonth==(mon+1))
        {
          
           if(selectedDay<d)
           {
           alert('Date should be greater than current Date');
           document.frmValidationSummaryRep.txtfromdate.value="";
           document.frmValidationSummaryRep.txtfromdate.focus();
           }
       }
       }
       
       return true;

}


function val(){
	
	
	
	if(document.frmValidationSummaryRep.optselect.checked==true)
	{
	
	if(document.frmValidationSummaryRep.cmbsgroup1.selectedIndex==0){
		  
		alert("Select the Service Group");
		
		return false;  
	  }
	
	else
	{
	
		var flag1 = 0;
		for (var i = 0; i< document.frmValidationSummaryRep.chkrank.length; i++) {
		if(document.frmValidationSummaryRep.chkrank[i].checked){
		flag1 ++;
		}
		}
		
		if (flag1<=0) {
		alert ("Please select the rank");
		return false;
		}
		return true;
	}
	  }
	 else{
			return true;  
		  }

}
