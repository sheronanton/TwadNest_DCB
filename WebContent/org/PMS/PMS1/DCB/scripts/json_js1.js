function json(pr,name,value)
{
	 document.getElementById("pr_status1").value=0;; 
	 var xmlobj2=createObject();
	 var process=pr;
	 var url="../../../../../json_servlet?pr="+pr+"&value="+value;; 
	 xmlobj2.open("GET",url,true);
	 ck( )
	 try
	 {
		     xmlobj2.onreadystatechange=function()
		     {
		    	 divshow_(xmlobj2,process,name);  
		     }
		     xmlobj2.send(null);
	 }catch(e) {}
}   

function divshow_(xmlobj,process,input_value)
{
   try
   {
	var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
	 
		var len=bR.getElementsByTagName("sno").length;
			document.getElementById(input_value).options.length = 0;
			for (i=0;i<len;i++)
			{
				var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			    var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			    addOption(document.getElementById(input_value),name,sno);
		    }
			 document.getElementById("pr_status1").value=1; 
	      
   }catch(e){} 
}
function ck()
{
		var s = document.getElementById("pr_status1").value;
		if (s==0 )      
		{
		document.getElementById("msg").innerHTML="<font size=5 color='red'><b>Bill List Processing...Please Wait</b></font>";
		}
		else
		{    try { 
			     document.getElementById("msg").innerHTML="";
			     document.getElementById("bcount").value="";
				 }catch(e) {}
		}
		setTimeout('ck()',1);
}