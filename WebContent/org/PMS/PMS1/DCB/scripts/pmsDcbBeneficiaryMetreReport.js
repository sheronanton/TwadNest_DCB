 
function divisionname()
{
    var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     url="../../../../../pmsDcbBeneficiaryMetreReport?command=divisionname";
     url=url+"&sid="+Math.random();
     
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange= function()
     {
    	 stateChangeddivisionname(xmlhttp);
     }
     xmlhttp.send(null);
}
function stateChangeddivisionname(xmlhttp)
{
	var baseres,commandres,flagres;
	
    if(xmlhttp.readyState==4)
    {
    	   	   	   	
        if(xmlhttp.status==200)
        {
        	if(window.ActiveXObject){ // If IE Windows
          		baseres = new ActiveXObject("Microsoft.XMLDOM");
              	baseres.loadXML(xmlhttp.responseText);
          		} else {
                  	baseres = xmlhttp.responseXML;
          		}
        	 baseres=baseres.getElementsByTagName("response")[0];
         	commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
         	flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
         	
        	 try
        	 {
             if(commandres=="divisionname")
            {
                if(flagres=='success')
                {
                    officename=baseres.getElementsByTagName("officename")[0].firstChild.nodeValue;
                    
                    if(officename!=0)
                    {
                        document.getElementById("divisionname").innerHTML=' - '+officename;
                        
                    }
                    else
                    {
                        alert("Division name is not loaded");
                    }
                }
            }
        	}
        	catch(e)
        	{
        		alert("not getting");
        	}
        }
    }
}
function getSchBenName()
{
    var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    
    var beneficiarySno=document.getElementById("beneficiarySno").value;
    var schemeSno= document.getElementById("schemeSno").value;	
    
     url="../../../../../pmsDcbBeneficiaryMetreReport?command=schemeBeneficiaryName";
     url+="&beneficiarySno="+beneficiarySno+"&schemeSno="+schemeSno;
     url=url+"&sid="+Math.random();
     
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange= function()
     {
    	 stateChangedSchBenName(xmlhttp);
     }
     xmlhttp.send(null);
}
function stateChangedSchBenName(xmlhttp)
{
	var baseres,commandres,flagres;
	
    if(xmlhttp.readyState==4)
    {
    	
    	   	   	
        if(xmlhttp.status==200)
        {
        	if(window.ActiveXObject){ // If IE Windows
          		baseres = new ActiveXObject("Microsoft.XMLDOM");
              	baseres.loadXML(xmlhttp.responseText);
          		} else {
                  	baseres = xmlhttp.responseXML;
          		}
        	
        	baseres=baseres.getElementsByTagName("response")[0];
        	commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        	flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        	
        	
             if(commandres=="schemeBeneficiaryName")
            {
                if(flagres=='success')
                {
                   var  beneficiaryName=baseres.getElementsByTagName("BeneficiaryName")[0].firstChild.nodeValue;
                   var  SchemeName=baseres.getElementsByTagName("SchemeName")[0].firstChild.nodeValue;
                    if(beneficiaryName!="-" && SchemeName!="-")
                    {
                        document.getElementById("ben").innerHTML=beneficiaryName;
                        document.getElementById("sch").innerHTML=SchemeName;
    
                    }
                    else
                    {
                        alert("SchemeName  is not loaded");
                    }
                }
            }
        	
        }
    }
}

function loadReport()
{
	var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var beneficiarySno=document.getElementById("beneficiarySno").value;
    var schemeSno= document.getElementById("schemeSno").value;
    url="../../../../../pmsDcbBeneficiaryMetreReport?command=loadReport" +
    		"&beneficiarySno="+beneficiarySno+"&schemeSno="+schemeSno;
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
    {
    	stateChangedLoadReport(xmlhttp);
    };
    xmlhttp.send(null);

}

function stateChangedLoadReport(xmlhttp)
{
    var baseres,commandres,flagres="success";
    if(xmlhttp.readyState==4)
    {
        var tbody=document.getElementById("getvaluerows");
        var t=0;	
   	   for(t=tbody.rows.length-1;t>=0;t--)
   	   {
   		   tbody.deleteRow(0);
   	   }
  	   if(xmlhttp.status==200)
       {
  		
  		   if(window.ActiveXObject){ // If IE Windows
       		baseres = new ActiveXObject("Microsoft.XMLDOM");
           	baseres.loadXML(xmlhttp.responseText);
       		} else {
               	baseres = xmlhttp.responseXML;
       		}
  		 baseres=baseres.getElementsByTagName("response")[0];
         commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
             if(commandres=="loadReport")
            {
                if(flagres=='success')
                {
                 var html=""; 
                 var tempmetreSno=0;
                 var loc_len=0;
                 loc_len=baseres.getElementsByTagName("METRE_SNO").length;
                 var temp=0;
                 var c=1;
                 var lenth=0;
                 var html=""
                 for(var i=0;i<loc_len;i++)
                 {
                	 var metreSno=baseres.getElementsByTagName("METRE_SNO")[i].firstChild.nodeValue;
                	 
                	  if(parseInt(temp)!=parseInt(metreSno))
                      {
                		  
                		 
                		 var len=baseres.getElementsByTagName("count")[i].firstChild.nodeValue;
                		  
                		 //alert("lenth:"+lenth);
     	                 if (len!=0)
     	                 {	  
     	                 	 
     	                 	if (parseInt(temp)!=parseInt(metreSno))
     	                 		html=""   
     	                 			;
     	                	 html="<table valign=top width='100%' align='center' border='1'  cellspacing='0' cellpadding='0' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>";
     	                	 html+="<tr class='tdH'><td class='tdText' align='center'>Quantity From</td><td class='tdText' align='center'>Quantity To</td>";
     	                 	 html+="<td class='tdText' align='center'>Tariff Rate</td></tr><tr>";
     	                 	
     			             for(var k=0;k<len;k++)
     			             {
     			            	 
     			            	 try
     			            	 {
     			            		 
     			            	   if (tempmetreSno!=metreSno)
     			            	   {
     			            		 
     			            		 html+="<tr>";
     			                	 var qntyFrom_new=baseres.getElementsByTagName("QTY_FROM")[lenth].firstChild.nodeValue;
     			                	 html+="<td align=right>"+qntyFrom_new+"</td>";
     			                	 var qntyTo_new=baseres.getElementsByTagName("QTY_TO")[lenth].firstChild.nodeValue;
     			                	 html+="<td align=right>"+qntyTo_new+"</td>";
     			                	 var traiffRate_new=baseres.getElementsByTagName("TARIFF_RATE")[lenth].firstChild.nodeValue;
     			                	 
     			                	 html+="<td align=right>"+traiffRate_new+"</td>";
     			                	 html+="<tr>";     			                	
     			                	lenth++;
     			            	   }
     			            	 }catch(e){}
     			            	
     			              }
     			            tempmetreSno=metreSno;
     			              html+="</table>"; 
     	                 }
     	                 else
     	                 {
     	                	 html="<table valign=top width='100%' align='center' border='1'  cellspacing='0' cellpadding='0' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>" +
     	                	 		"<tr><td align=center>Rate Not Entered</td></tR></table>";
     	                	 
     	                 }
                		  
                	 var new_row=cell("tr","","","row","",2,"","","","","","","");
                	 
                	 var metreLocation=baseres.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue
                	 var metreFixed=baseres.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;
                	 metreFixed=apprivation(metreFixed);
                	 var metreWorking=baseres.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
                	 metreWorking=apprivation(metreWorking);
                	 var metreType=baseres.getElementsByTagName("METRE_TYPE")[i].firstChild.nodeValue;
                	 var multifyingFactor=baseres.getElementsByTagName("MULTIPLY_FACTOR")[i].firstChild.nodeValue;
                	 var metreInitReading=baseres.getElementsByTagName("METRE_INIT_READING")[i].firstChild.nodeValue;
                	 var min_Qnty=baseres.getElementsByTagName("MIN_QTY")[i].firstChild.nodeValue;
                	 if(parseInt(min_Qnty)!=0)
                	 {
                		 min_Qnty="  ("+min_Qnty +")";
                	 }
                	 else
                	 {
                		 min_Qnty="";
                	 }	 
                	 var allot_Qnty=baseres.getElementsByTagName("ALLOT_QTY")[i].firstChild.nodeValue;
                	 if(parseInt(allot_Qnty)!=0)
                	 {
                		 allot_Qnty="  ("+allot_Qnty +")";
                	 }
                	 else
                	 {
                		 allot_Qnty="";
                	 }
                	 var parentMetre=baseres.getElementsByTagName("PARENT_METRE")[i].firstChild.nodeValue;
                	 parentMetre=apprivation(parentMetre);
                	 var minQnty=baseres.getElementsByTagName("MIN_FLAG")[i].firstChild.nodeValue;
                	 minQnty=apprivation(minQnty);
                	 var allotQnty=baseres.getElementsByTagName("ALLOT_FLAG")[i].firstChild.nodeValue;
                	 allotQnty=apprivation(allotQnty);
                 	 var cell1=cell("TD","label","","",c,4,"","","valign:top;","","","","");
                	 var cell2=cell("TD","label","","",metreLocation,2,"","","valign:top;","","","","");
                	 var cell3=cell("TD","label","","",metreFixed,2,"","","","","","","");
                	 var cell4=cell("TD","label","","",metreWorking,2,"","","","","","","");
                	 var cell5=cell("TD","label","","",metreType,2,"","","","","right","","");
                     var cell6=cell("TD","label","","",multifyingFactor,2,"","","","","right","","");
                     var cell7=cell("TD","label","","",metreInitReading,2,"","","","","right","","");
                     var cell8=cell("TD","label","","",parentMetre,2,"","","","","","","");
                     var cell9=cell("TD","label","","",minQnty+min_Qnty,2,"","","","","left","","");
                     var cell10=cell("TD","label","","",allotQnty+allot_Qnty,2,"","","","","left","","");
                     var empty=cell("TD","div","","",html,"","","","","","","","");
                      
                     new_row.appendChild(cell1);
                     new_row.appendChild(cell2);
                    new_row.appendChild(cell3);
                    new_row.appendChild(cell4);
                    new_row.appendChild(cell5);
                    new_row.appendChild(cell6);
                    new_row.appendChild(cell7);
                    new_row.appendChild(cell8);
                    new_row.appendChild(cell9);
                    new_row.appendChild(cell10);
                    new_row.appendChild(empty);
                    tbody.appendChild(new_row);	 
                   c++
                    	 
                     }	 
                     else
                     {
                    	
                     }	 
                     	 
                     
                	  
                   //  new_row.appendChild(cell11);
                   //  new_row.appendChild(cell12);
                 //    new_row.appendChild(cell13);
                     
                     temp=metreSno;
                 }
                 
                 
                 
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
  
	function apprivation(fieldVal)
	{
		var returnValue="-";
		if(fieldVal=="y" || fieldVal=="Y")
		{
			returnValue="Yes";
		}	
		if(fieldVal=="n" || fieldVal=="N")
		{
			returnValue="No";
		}
		
		return returnValue;
	}
	
	function exitwindow()
	{
		window.close();
	}