var process_code=0;
var selected_type;
var sel_type=0;
var url="";
var flag=1;
var flag_c=0; 
function report(process)
{
	var process_code=process;
	var pyear=document.getElementById("pyear").value;
	if(process_code==2)
	{
		var sch_sno=document.getElementById("sch_sno").value;	
		var pmonth=0;
		try  
		{
			pmonth=document.getElementById("pmonth").value;
		}catch(e){}
		  
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&sch_sno="+sch_sno+"&pmonth="+pmonth);
	}
	else if(process_code==3)
	{
		var pyear=document.getElementById("pyear").value;
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear);
	}	else if(process_code==111)
	{
		var pyear=document.getElementById("pyear").value;
		window.open("../../../../../ame_report?process_code="+process_code+"&fin_year="+pyear);
	}		 
	else if(process_code==5)
	{
		window.open("../../../../../ame_report?process_code="+process_code);
	}else if(process_code==1)
	{
		var pyear=document.getElementById("pyear").value;
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear);
	
	}	else if(process_code==6)
	{
		var pyear=document.getElementById("pyear").value;
		var sch_sno=document.getElementById("sch_sno").value;	
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&sch_sno="+sch_sno);
	
	}
	else if(process_code==7)
	{
		var sch_sno=document.getElementById("sch_sno").value;
		var pyear=document.getElementById("pyear").value;
		var pmonth=document.getElementById("pmonth").value;

		 
		
		
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&sch_sno="+sch_sno+"&pmonth="+pmonth);
	
	}else if(process_code==8)
	{
		var sch_sno=document.getElementById("sch_sno").value;
		var pyear=document.getElementById("pyear").value;

		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&sch_sno="+sch_sno);
	
	}else if(process_code==9)
	{
		  
		var pyear=document.getElementById("pyear").value;
		var pmonth=document.getElementById("pmonth").value;
		 
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&pmonth="+pmonth);
	  
	} else if(process_code==99)
	{
		  
		var pyear=document.getElementById("pyear").value;
		var pmonth=document.getElementById("pmonth").value;
		 
		window.open("../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&pmonth="+pmonth);
	  
	} 
	else if(process_code==14)  
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
    else if(process_code==11)
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
    else if(process_code==12)
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
    else if(process_code==13)
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
    else if(process_code==15)
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
    else if(process_code==16)
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
    else if(process_code==17)
    {
        window.open("../../../../../ame_report?process_code="+process_code);
    }
} 