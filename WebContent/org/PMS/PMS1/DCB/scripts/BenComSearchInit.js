
		function searchConsumer(val)
		{
			var ctype = val;

			if(ctype!="")
			{
				var winSearchConsumer;
				if (winSearchConsumer && winSearchConsumer.open && !winSearchConsumer.closed) 
			    {
			       winSearchConsumer.resizeTo(500,500);
			       winSearchConsumer.moveTo(250,250); 
			       winSearchConsumer.focus();
			    }
			    else
			    {
			       winSearchConsumer=null
			    }        
			    winSearchConsumer= window.open("../../../../../org/PMS/PMS1/DCB/jsps/BenComSearch.jsp?ctype="+ctype,"searchConsumer","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
			    winSearchConsumer.moveTo(250,250);  
			    winSearchConsumer.focus();
			}
			else
			{
				alert("Please select a Beneficiary Type");
			}
		}


