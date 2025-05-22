//alert("welcome to this page");
var baseRe;
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
function getcadre()
{
	    document.getElementById("pro_id").innerHTML="";
		var cadre_id="";
	    cadre_id=document.getElementById("cadre_id").value;
		var req=getTransport();
		var url="../../../../../../Seniority_rep_DO_EE?command=getcadre&cadre_id="+cadre_id ;
		req.open("GET",url,true);          

		req.onreadystatechange=function()
		{
		   
		    if(req.readyState==4)
		    { 
		        if(req.status==200)
		        {  
//alert(req.responseText)
        	//var response=req.responseXML.getElementsByTagName("response")[0];
        	baseRe=req.responseXML.getElementsByTagName("response")[0];
      
           var tagcommand=baseRe.getElementsByTagName("command")[0];
          
           var tot=baseRe.getElementsByTagName("count").length;
           var Command=tagcommand.firstChild.nodeValue;
          
            if(Command=="getcadre")
           {
           	  var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
           	  if(flag=="success")
               	{
           		var pro_id=document.getElementById("pro_id");
           		var option=document.createElement("OPTION");
                option.text="--Select Proceeding No--";
                option.value="0";
                try
                {
                	pro_id.add(option);
                }catch(errorObject)
                {
                	pro_id.add(option,null);
                } 
           		  for(var i=0;i<tot;i++)
           			  {
		           			var PROCEEDINGS_REF_NO = baseRe.getElementsByTagName("PROCEEDINGS_REF_NO")[i].firstChild.nodeValue;	
		           			var option=document.createElement("OPTION");
		                    option.text=PROCEEDINGS_REF_NO;
		                    option.value=PROCEEDINGS_REF_NO;
		                    //alert(pro_id);
		                    //Making Browser Independent
		                    try
		                    {
		                    	pro_id.add(option);
		                    }
		                    catch(errorObject)
		                    {
		                    	pro_id.add(option,null);
		                    }
           			  }
               	}
           }
        }
    }
};
req.send(null);
}
function show()
{
document.getElementById("proc_no").style.display="";
}
function hide()
{
	document.getElementById("proc_no").style.display="none";	
}
function getdata()
{
	var val=valid();
	if(val == true)
	{
	var url="../../../../../../Seniority_rep_DO_EE?OLevel=submit";
	
	var cadre_id="";
	var pro_id="";
	cadre_id=document.getElementById("cadre_id").value;
	pro_id=document.getElementById("pro_id").value;
	
	url=url+"&cadre_id="+cadre_id;
	
	url=url+"&pro_id="+pro_id;
	
	if(document.Seniority_rep_DO_EE.Seni_sta[0].checked==true)
    {
            url=url+"&Seni_sta=PRO";
           
            document.Seniority_rep_DO_EE.Seni_sta.value='PRO';
    }
    else if(document.Seniority_rep_DO_EE.Seni_sta[1].checked==true)
    {
            url=url+"&Seni_sta=All";
            document.Seniority_rep_DO_EE.Seni_sta.value='All';
    }
    else if(document.Seniority_rep_DO_EE.Seni_sta[2].checked==true)
    {
            url=url+"&Seni_sta=WRK";
             document.Seniority_rep_DO_EE.Seni_sta.value='WRK';
    }
	
	
	if(document.Seniority_rep_DO_EE.optoutputtype[0].checked==true)
    {
            url=url+"&optoutputtype=pdf";
           // document.HRM_seniority_rep.outputtype.value='pdf';
    }
    else if(document.Seniority_rep_DO_EE.optoutputtype[1].checked==true)
    {
            url=url+"&optoutputtype=excel";
            //document.HRM_seniority_rep.outputtype.value='excel';
    }
    else if(document.Seniority_rep_DO_EE.optoutputtype[2].checked==true)
    {
            url=url+"&optoutputtype=html";
            // document.HRM_seniority_rep.outputtype.value='html';
    }
    


document.Seniority_rep_DO_EE.action="../../../../../../Seniority_rep_DO_EE";
document.Seniority_rep_DO_EE.method="POST";
document.Seniority_rep_DO_EE.submit();
	}
}
function valid()
{
   if(document.getElementById("cadre_id").selectedIndex == 0)
	{
			alert("Please Select Cadre !");
			return false;
	}
   else if(document.Seniority_rep_DO_EE.Seni_sta[0].checked == false && document.Seniority_rep_DO_EE.Seni_sta[1].checked == false && document.Seniority_rep_DO_EE.Seni_sta[2].checked == false)
	{
			alert("Please Select Status !");
			return false;
	}
   else if(document.Seniority_rep_DO_EE.Seni_sta[0].checked == true)
	{
	  if(document.getElementById("pro_id").selectedIndex == 0)
		{
			alert("Please Select Proceeding No !");
			return false;
		}
	}
   return true;
}
