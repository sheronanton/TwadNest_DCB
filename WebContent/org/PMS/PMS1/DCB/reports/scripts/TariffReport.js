		
		function callServer(cmd)
		{
			 if(cmd=='Type')
             {
				 url="../../../../../../Consumer?command=Type";
	             var req=createObject();
	             req.open("GET",url,true);        
	             req.onreadystatechange=function()
	             {
	                processResponse(req);
	             }
	             req.send(null);
             }
			 /*
			 else if(cmd=='Year')
             {
				 url="../../../../../../OpeningBalanceReport?command=Year&count=2";
	             var req=createObject();
	             req.open("GET",url,true);        
	             req.onreadystatechange=function()
	             {
	                processResponse(req);
	             }
	             req.send(null);
             }
             */
			 else if(cmd=='Tariff')
			 {
				 var ben=document.getElementById('ben').value;
				 url="../../../../../../TariffReport?action=Report&ben="+ben;
				
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 }
		}
		
		function processResponse(req)
		{
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var baseResponse = req.responseXML.getElementsByTagName('response')[0];
                    var cmd = baseResponse.getElementsByTagName('command')[0].firstChild.nodeValue;
                    var flag = baseResponse.getElementsByTagName('flag')[0].firstChild.nodeValue;
                    if(cmd == 'Type')
                    {
                        if(flag=='success')
                        {
                        		var cmb = document.getElementById("bentype");
                                var row = baseResponse.getElementsByTagName('row');
                                for(var i=0; i<row.length; i++)
                                {
                                    var typ = row[i].getElementsByTagName('typ')[0].firstChild.nodeValue;
                                    var type = row[i].getElementsByTagName('type')[0].firstChild.nodeValue;
                                    
                                    var opt = document.createElement('option');
                                    opt.value = typ;
                                    opt.innerHTML = type;
                                    
                                    cmb.appendChild(opt);
                                }
                        }
                        else
                        {
                                alert("Failed to load Beneficiary Types");
                        }
                    }                    
/*
                    else if(cmd == 'Year')
                    {
                        if(flag=='success')
                        {
                        		var cmb = document.getElementById("year");
                                var row = baseResponse.getElementsByTagName('row');
                                for(var i=0; i<row.length; i++)
                                {
                                    var year = row[i].getElementsByTagName('year')[0].firstChild.nodeValue;
                                    
                                    var opt = document.createElement('option');
                                    opt.value = year;
                                    opt.innerHTML = year;
                                    
                                    cmb.appendChild(opt);
                                }
                        }
                        else
                        {
                                alert("Failed to load Years");
                        }
                    }                    
                    
*/
                }
            }
		}
		
		

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
			    winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/BenComSearch.jsp?ctype="+ctype,"searchConsumer","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
			    winSearchConsumer.moveTo(250,250);  
			    winSearchConsumer.focus();
			}
			else
			{
				alert("Please select a Beneficiary Type");
			}
		}

		function doParent(bname,sno)
		{
			document.getElementById('beneficiary').value=bname;
			document.getElementById('ben').value=sno;
		}