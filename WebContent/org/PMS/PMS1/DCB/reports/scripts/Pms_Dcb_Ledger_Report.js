 
function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
         //  code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {
        //  code for IE6, IE5
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function funcmdprint1()  
{
 
	 	var year=document.getElementById("year").value;
		var month=document.getElementById("month").value;
		var div=document.getElementById("div").value;
		var res=month_year_check(month,year);
		if (res!=1 )
		{
			window.open("../../../../../../Pms_Dcb_Ledger_Report?pr=3&year="+year+"&month="+month+"&option=1&div="+div);
		}
}  

function funcmdprint3()  
{
 
	 	var year=document.getElementById("year").value;
		var month=document.getElementById("month").value;
		var div=document.getElementById("div").value;
		var rin=document.getElementById("rin").value;
		var pr_type=document.getElementById("pr_type").value;
		
		window.open("../../../../../../Pms_Dcb_Ledger_Report?pr=31&year="+year+"&month="+month+"&option="+pr_type+"&div="+div+"&rin="+rin);
		
}  


function funcmddata(option)
{
 
    var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var div=document.getElementById("div").value;
	var rin=document.getElementById("rin").value;
	 
	//alert(url);
	//alert(url);
	
	
	var res=month_year_check(month,year);
	if (res!=1 )
	{
	//if (div==5000 || div==0){
	//	var url="../../../../../../Pms_Dcb_Ledger_Report?pr=1&year="+year+"&month="+month+"&option="+option+"&rin="+rin;
	//	alert(url);
	//	window.open(url);}
 //	else{
 		var url="../../../../../../Pms_Dcb_Ledger_Report?pr=2&year="+year+"&month="+month+"&option="+option+"&div="+div+"&rin="+rin;
	//alert(url);
 		window.open(url);
 //	}
 	}
}



function funcmdprint(option)
{
 
    var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var div=document.getElementById("div").value;
	var rin=document.getElementById("rin").value;
	 
	var res=month_year_check(month,year);
	if (res!=1 )
	{
	if (div==5000 || div==0){
		var url="../../../../../../Pms_Dcb_Ledger_Report?pr=1&year="+year+"&month="+month+"&option="+option+"&rin="+rin;
	//	alert(url);
		window.open(url);}
 	else{
 		var url="../../../../../../Pms_Dcb_Ledger_Report?pr=2&year="+year+"&month="+month+"&option="+option+"&div="+div+"&rin="+rin;
	//alert(url);
 		window.open(url);
 	}
 	}
}
function funcmdprint2(option)
{
   
    var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var div=document.getElementById("div").value;
	var rin=document.getElementById("rin").value;    
	if(option==1) 
		//window.open("../../../../../../Pms_Dcb_Ledger_Report?pr=4&year="+year+"&month="+month+"&option=2&div="+div+"&rin="+rin);
		window.open("./Govt_fin_year_report.jsp");
	else if (option==2)
		window.open("../../../../../../Pms_Dcb_Ledger_Report?pr=5&year="+year+"&month="+month+"&option=2&div="+div+"&rin="+rin);
 
}
// Newly added for private_details.jsp
function funcmdprintnew(option)
{
   
    var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	
//	var div=document.getElementById("div").value;
	
	window.open("../../../../../../Pms_Dcb_Ledger_Report?pr=1111&year="+year+"&month="+month+"&option="+option);
 
}

function funcmdprintnew1(option)
{
   
    var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var div=document.getElementById("div").value;
	
	window.open("../../../../../../Pms_Dcb_Ledger_Report?pr=1112&year="+year+"&month="+month+"&option="+option+"&div="+div);
 
}


         
function exitwindow()
{
    window.close();  
}
function funcheck()
{
	alert("Dfdfdf");
}