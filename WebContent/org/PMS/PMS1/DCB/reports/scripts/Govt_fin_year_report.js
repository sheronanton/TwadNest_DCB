function rld1(a)
{
	document.forms["myform"].submit();
}     
function rpt()  
{var D1=document.getElementById("d1").value;  
var D2=document.getElementById("d2").value; 
var k1=new Date(D1);
var k2=k1.getFullYear();
var k3=k1.getMonth();
var k4=k1.getDate();

var q1=new Date(D2);
var q2=q1.getFullYear();
var q3=q1.getMonth();
var q4=q1.getDate();


//month need to be +1
var m1=k3+1;
var m2 =q3+1;

//alert("year of d1 is "+k2); 

var date1=Date.parse(D1);
var date2=Date.parse(D2);

var timeDiff = date2 - date1;
    daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
    var day=daysDiff+1;
   
   
	var year = k2;
  	var monnth = m1;
  	var daay =k4;
	var year2= q2;
   	var monnth2 = m2;
	var daay2 =q4;
	
	
	
		var option=document.getElementById("rtype").value;  

	//alert("starting year is "+year)
	var DV= document.getElementById("DV").value;
	
	if (year=="0" || DV=="0")
	{
		alert("Select Financial Year / Division ! ");
	}
	else
	{
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?splflag=0&option="+option+"&pr=4&option="+option+"&day="+day+"&year="+year+"&year2="+year2+"&div="+DV+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}        
//Arrear for selected division Financial
function rpt13()  
{
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
	var option=document.getElementById("rtype").value;  
//	var year= document.getElementById("year").value;
	var DV= document.getElementById("DV").value;
	//var year= document.getElementById("year").value;
	if (year=="0" || DV=="0")
	{
		alert("Select Financial Year / Division ! ");
	}
	else
	{
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?splflag=4&option="+option+"&pr=4&option="+option+"&day="+day+"&year2="+year2+"&year="+year+"&&div="+DV+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}             



function rpt1()    
{	
	
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
        	
        
        	
        	
	var option=document.getElementById("rtype").value;  
	//var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=1&option="+option+"&pr=4&year="+year+"&div="+DV+"&day="+day+"&year2="+year2+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}
function rpt11()    
{
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
	// alert("Select Financial Year! ");
	var option=document.getElementById("rtype").value;  
	//var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=2&option="+option+"&pr=4&year="+year+"&div="+DV+"&day="+day+"&year2="+year2+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}
function rpt16()    
{
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
	// alert("Select Financial Year! ");
	var option=document.getElementById("rtype").value;  
//	var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=7&option="+option+"&pr=4&year="+year+"&month=0&div="+DV+"&day="+day+"&year2="+year2+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}
function rpt15()    
{
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
	// alert("Select Financial Year! ");
	var option=document.getElementById("rtype").value;  
//	var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=6&option="+option+"&pr=4&year="+year+"&month=0&div="+DV+"&day="+day+"&year2="+year2+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}
function rpt14()    
{
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
	// alert("Select Financial Year! ");
	var option=document.getElementById("rtype").value;  
//	var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=5&option="+option+"&pr=4&year="+year+"&month=0&div="+DV+"&day="+day+"&year2="+year2+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}
function rpt12()    
{
	var D1=document.getElementById("d1").value;  
	var D2=document.getElementById("d2").value; 
		var k1=new Date(D1);
	var k2=k1.getFullYear();
	var k3=k1.getMonth();
	var k4=k1.getDate();
	
	var q1=new Date(D2);
	var q2=q1.getFullYear();
	var q3=q1.getMonth();
	var q4=q1.getDate();
	
	
	//month need to be +1
	var m1=k3+1;
	var m2 =q3+1;
	
//	alert("year of d1 is "+k2); 
	
	var date1=Date.parse(D1);
	var date2=Date.parse(D2);
	
var timeDiff = date2 - date1;
            daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            var day=daysDiff+1;
           
           
        	var year = k2;
          	var monnth = m1;
          	var daay =k4;
        	var year2= q2;
           	var monnth2 = m2;
        	var daay2 =q4;
	// alert("Select Financial Year! ");
	var option=document.getElementById("rtype").value;  
//	var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=3&option="+option+"&pr=4&year="+year+"&month=0&div="+DV+"&day="+day+"&year2="+year2+"&monnth="+monnth+"&monnth2="+monnth2+"&daay="+daay+"&daay2="+daay2);
	}
}
function rpt3()    
{
	var process_year= document.getElementById("process_year").value;
	var process_month= document.getElementById("process_month").value;
	var option=document.getElementById("rtype").value;  
	if (process_year=="0" || process_month=="0")
	{
		alert("Select Month and Year! ");
	}  
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=1&pr=6&year="+process_year+"&month="+process_month+"&div="+DV);
	}
}
function rpt4()        
{
	var option=document.getElementById("rtype").value;  
	var process_year= document.getElementById("process_year").value;
	var process_month= document.getElementById("process_month").value;
	var DV= document.getElementById("DV").value;
	if (process_year=="0" || process_month=="0" || DV=="0")
	{
		alert("Select Month ,Year and  Division Name! ");
	}
	else
	{
	 		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=0&pr=6&year="+process_year+"&month="+process_month+"&div="+DV);
	}
}
function rpt5()    
{ 
	
	var process_year= document.getElementById("process_year").value;
	var process_month= document.getElementById("process_month").value;
	var option=document.getElementById("rtype").value;  
	if (process_year=="0" || process_month=="0")
	{
		alert("Select Month and Year! ");
	}  
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=2&pr=6&year="+process_year+"&month="+process_month+"&div="+DV);
	}
}