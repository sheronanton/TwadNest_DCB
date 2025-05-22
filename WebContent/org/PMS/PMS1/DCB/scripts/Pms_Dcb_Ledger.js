
function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
         // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {
        // code for IE6, IE5
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}



function funcmdgenerate_actual()
{
	var valvar=validate();
	 
    if(valvar==true)
   {
    	xmlhttp=GetXmlHttpObject();
    	if (xmlhttp==null)
    	{	
    		alert("Your browser does not support AJAX!")
    		
    		return;
    	}
    	month_var=document.pms_dcb_ledger.month.value;
    	year_var=document.pms_dcb_ledger.year.value;
    	divname=document.pms_dcb_ledger.divisionid.value;
    	
    	 for(var i = 0; i < document.pms_dcb_ledger.divselection.length; i++ )
    	 {
    	     
    		 if(document.pms_dcb_ledger.divselection[i].checked)
    		 {
    			 flag=1;
    			 radiovalue = document.pms_dcb_ledger.divselection[i].value;
    			
    		 }
    	 }
        url="../../../../../pms_dcb_ledger_actual?command=generate&month_var="+month_var+"&year_var="+year_var+"&divname="+divname+"&radiovalue="+radiovalue;
    	document.getElementById("pr_status").value = 0;
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
        {
        	funcheckgenerate_actual(xmlhttp);
        }
        xmlhttp.send(null);
   }

}
function funcmdgenerate()
{
	var valvar=validate();
	 
    if(valvar==true)
   {
    	xmlhttp=GetXmlHttpObject();
    	if (xmlhttp==null)
    	{	
    		alert("Your browser does not support AJAX!")
    		return;
    	}
    	month_var=document.pms_dcb_ledger.month.value;
    	year_var=document.pms_dcb_ledger.year.value;
    	divname=document.pms_dcb_ledger.divisionid.value;
    	 for(var i = 0; i < document.pms_dcb_ledger.divselection.length; i++ )
    	 {
    	     
    		 if(document.pms_dcb_ledger.divselection[i].checked)
    		 {
    			 flag=1;
    			 radiovalue = document.pms_dcb_ledger.divselection[i].value;
    			
    		 }
    	 }
        url="../../../../../pms_dcb_ledger?command=generate&month_var="+month_var+"&year_var="+year_var+"&divname="+divname+"&radiovalue="+radiovalue;
       
    	document.getElementById("pr_status").value = 0;
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
        {
        	funcheckgenerate(xmlhttp);
        }
        xmlhttp.send(null);
   }

}


function funcheckgenerate(xmlhttp)
{
	
	var baseres,commandres,flagres;
	month=new Array(12);
	month[1]="January";
	month[2]="February";
	month[3]="March";
	month[4]="April";
	month[5]="May";
	month[6]="June";
	month[7]="July";
	month[8]="August";
	month[9]="September";
	month[10]="October";
	month[11]="November";
	month[12]="December";
	var error_splflag=0;
	if(xmlhttp.readyState==4)
	{
		try
		{
		baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
		countvalue=baseres.getElementsByTagName("countvalue")[0].firstChild.nodeValue;
		month_var=baseres.getElementsByTagName("month_var")[0].firstChild.nodeValue;
		year_var=baseres.getElementsByTagName("year_var")[0].firstChild.nodeValue;
		error_splflag=baseres.getElementsByTagName("error_splflag")[0].firstChild.nodeValue;
		}
		catch(e)
		{
		}  
		
		if(xmlhttp.status==200)
		{
			if(commandres=="generate")
			{
				if(countvalue==1)
				{
					countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
					if(countinsert==1)
					{
						alert("Data Uploaded Succcessfully ");
						document.getElementById("pr_status").value = 1;
						req_pro();  
						window.close(); 
					}
					if(countinsert==0)
					{
						if (error_splflag==3)
						{
							alert("\n Generate Demand Bill Before DCB Monthly Ledger Data Posting.... \n")
						}
						else
						{
							alert("Data already exists for " + month[month_var]+ "," + year_var);
							var answer = confirm("Do You Want to Delete?");
							if (answer == true) {
								deletereportgen();
							} else {
								document.getElementById("pr_status").value = 1;
							}
						}
					}  
					if(countinsert==2)
					{
						alert("Error in Generation");
					}
				}
				else
				{
					alert("Data does not exists for "+ month[month_var] +","+ year_var);
				}
			}
		}
	}
}
function funcheckgenerate_actual(xmlhttp)
{
	
	var baseres,commandres,flagres;
	month=new Array(12);
	month[1]="January";
	month[2]="February";
	month[3]="March";
	month[4]="April";
	month[5]="May";
	month[6]="June";
	month[7]="July";
	month[8]="August";
	month[9]="September";
	month[10]="October";
	month[11]="November";
	month[12]="December";
	var error_splflag=0;
	if(xmlhttp.readyState==4)
	{
		try
		{
			baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
			commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
			flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
			countvalue=baseres.getElementsByTagName("countvalue")[0].firstChild.nodeValue;
			month_var=baseres.getElementsByTagName("month_var")[0].firstChild.nodeValue;
			year_var=baseres.getElementsByTagName("year_var")[0].firstChild.nodeValue;
		}
		catch(e)
		{
		}
		
		if(xmlhttp.status==200)
		{
			if(commandres=="generate")
			{
				if(countvalue==1)
				{
					countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
					error_splflag=baseres.getElementsByTagName("error_splflag")[0].firstChild.nodeValue;  
					if(countinsert==1)
					{   
						alert("Data Uploaded Succcessfully ");
						document.getElementById("pr_status").value = 1;
						window.close(); 
					}  
					if(countinsert==0)
					{
						if (error_splflag==3)
						{
							alert("\n Generate Demand Bill Before DCB Monthly Ledger Data Posting.... \n \n")
							document.getElementById("pr_status").value = 1;
						}
						else
						{    
							 alert("Data already exists for "+ month[month_var] +","+ year_var);
							var answer =new Boolean(confirm("Do You Want to Delete?")); 						 
	 	                	if (answer==true) 
	 	                	{  
	 	                		deletereportgen_actual(); 
	 	                	} 
	 	                	else
							{
								document.getElementById("pr_status").value = 1; 
							}
						}
					}
					if(countinsert==2)
					{
						alert("Error in Generation");
					}
					
				}
				else
				{
					alert("Data does not exists for "+ month[month_var] +","+ year_var);
					 
					
				}
			}
		}
	}
}
function deletereportgen()
{
	
    
	 
    	var xmlhttp1=GetXmlHttpObject();
    	if (xmlhttp1==null)
    	{
    		  alert ("Your browser does not support AJAX!");
    		return;
    	}
    	 
    	month_var=document.pms_dcb_ledger.month.value;
    	year_var=document.pms_dcb_ledger.year.value;
    	divname=document.pms_dcb_ledger.divisionid.value;
    	
     
    	for(var i = 0; i < document.pms_dcb_ledger.divselection.length; i++ )
    	{
   	     
   		 if(document.pms_dcb_ledger.divselection[i].checked)
   		 {
   			 flag=1;
   			 radiovalue = document.pms_dcb_ledger.divselection[i].value;
   			
   		 }
   	 	}
   	var  url2=""
    	url2="../../../../../pms_dcb_ledger?command=deletegenerate&month_var="+month_var+"&year_var="+year_var+"&radiovalue='"+radiovalue+"'&divname="+divname;
   	 
     
        xmlhttp1.open("GET",url2,true);
        xmlhttp1.onreadystatechange= function()
        {    
        	 
        	deletereportgenres(xmlhttp1);
        	  
        }   
        xmlhttp1.send(null);
  

}
function deletereportgen_actual()
{
	
    
	 
    	var xmlhttp1=GetXmlHttpObject();
    	if (xmlhttp1==null)
    	{
    		  alert ("Your browser does not support AJAX!");
    		return;
    	}
    	 
    	month_var=document.pms_dcb_ledger.month.value;
    	year_var=document.pms_dcb_ledger.year.value;
    	divname=document.pms_dcb_ledger.divisionid.value;
    	
     
    	for(var i = 0; i < document.pms_dcb_ledger.divselection.length; i++ )
    	{
   	     
   		 if(document.pms_dcb_ledger.divselection[i].checked)
   		 {
   			 flag=1;
   			 radiovalue = document.pms_dcb_ledger.divselection[i].value;
   			
   		 }
   	 	}
   	var  url2=""
    	url2="../../../../../pms_dcb_ledger_actual?command=deletegenerate&month_var="+month_var+"&year_var="+year_var+"&radiovalue='"+radiovalue+"'&divname="+divname;
   	 
     
        xmlhttp1.open("GET",url2,true);
        xmlhttp1.onreadystatechange= function()
        {    
        	 
        	deletereportgenres_actual(xmlhttp1);
        	  
        }   
        xmlhttp1.send(null);
  

}
function deletereportgenres_actual(xmlhttp)
{
	 
	 
	var baseres,commandres,flagres;
	
	 
	if(xmlhttp.readyState==4)
	{
		baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
		 
		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
		 
		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
		 
	
		if(xmlhttp.status==200)
		{
			if(commandres=="deletegenerate")
			{
					 
						document.getElementById("pr_status").value = 1;
					    alert("Successfully Deleted ["+ month[month_var] +"],"+ year_var +"  Upload Data Again !!!");
					    window.close(); 
					 
			}
			else
			{
				 alert("Error in generation");
			}
			
			
		}
	}
}

function deletereportgenres(xmlhttp)
{
	 
	var baseres,commandres,flagres;
	if(xmlhttp.readyState==4)
	{
		baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
		 
		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
		 
		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
		 
	
		if(xmlhttp.status==200)
		{
			if(commandres=="deletegenerate")
			{
					 
						document.getElementById("pr_status").value = 1;
					    alert("Successfully Deleted ["+ month[month_var] +"],"+ year_var +"  Upload Data Again !!!");
					    window.close(); 
					 
			}
			else
			{
				 alert("Error in generation");
			}
			
			
		}
	}
}


function exitwindow()
{
    window.close();
}



    
function refresh()
{
	 document.pms_dcb_ledger.month.value=0;
	 document.pms_dcb_ledger.year.value=0;
}

function validate()
{
	
	month_var=document.pms_dcb_ledger.month.value;
    year_var=document.pms_dcb_ledger.year.value;
    div_name=document.pms_dcb_ledger.divisionid.value;
    for(var i = 0; i < document.pms_dcb_ledger.divselection.length; i++ )
	 {
	     
		 if(document.pms_dcb_ledger.divselection[i].checked)
		 {
			
			 radioval = document.pms_dcb_ledger.divselection[i].value;
			 
		 }
	 }
	if(month_var==0)
	{
		 alert("Select Month");
		return false;
	}	
	if(year_var==0)
	{
		  alert("Select Year");
		return false;
	}
	if(radioval=='SelectDivision')
	{
		flag=1;
	}
	if(radioval=='AllDivision')
	{
		flag=0;
	}
	if(flag==1)
	{
		if(div_name==0)
		{
			  alert("Select Division");
			return false;
		}
		else
		{
			return true;
		}
	}	
	
	else
    {
        return true;
    }
}
function showdiv()
{
	
	var radioval;
	

	 for(var i = 0; i < document.pms_dcb_ledger.divselection.length; i++ )
	 {
	     
		 if(document.pms_dcb_ledger.divselection[i].checked)
		 {
			 flag=1;
			 radioval = document.pms_dcb_ledger.divselection[i].value;
			
		 }
	 }
	 if(radioval=="SelectDivision")
	 {
		 var showdiv= document.getElementById("showdivload");
		 showdiv.style.display="block";
	 }
	 if(radioval=="AllDivision")   
	 {
		 var showdiv= document.getElementById("showdivload");
		 showdiv.style.display="none";
	 }
}


function divload()
{
	
	 var showdiv= document.getElementById("showdivload");
	 showdiv.style.display="none";
}
