		
		
		
		
		function fst() {
   				 var selectElement = document.getElementById('div');
   				 var selectedOption = selectElement.options[selectElement.selectedIndex].value;
   				 console.log(selectedOption);
			};

		
		
		
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
			 else if(cmd=='DCB')
			 {
				 var month=document.getElementById('month').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 
				 url="../../../../../../OpeningBalanceReport?command=Report&flag=DCB&month="+month+"&year="+year+"&ben="+ben+"&process="+process;
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 } 
			 else if(cmd=='Int')
			 {
				 var month=document.getElementById('month').value;
				 var year=document.getElementById('year').value;
				 var ben=0;
				 url="../../../../../../OpeningBalanceReport?command=Report&flag=Int&month="+month+"&year="+year+"&ben="+ben;
				 
				 
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 } 
		}
		function callServer3(cmd)
		{
			
			var month=document.getElementById('smonth').value;
			 var year=document.getElementById('year').value;
			 var process=1
			 var ben=0;
			 
			 
			 
			 url="../../../../../../OpeningBalanceReport?command=Report&flag=DCB&month="+month+"&year="+year+"&ben="+ben+"&process="+process;
			 
		        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
		        winemp.moveTo(250,250); 
		        winemp.focus();
		}
		
		function callServer2(cmd)
		{
			 var reporttype=document.getElementById('reporttype').value;
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
			 else if(cmd=='Year')
             {
				 url="../../../../../../OpeningBalanceReport?command=Year&option="+reporttype+"&count=2";
	             var req=createObject();
	             req.open("GET",url,true);        
	             req.onreadystatechange=function()
	             {
	                processResponse(req);
	             }
	             req.send(null);
             }else if(cmd=='DivisionWise')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;  
				 var ben=0;
				 
				 url="../../../../../../OpeningBalanceReport?command=Report&flag=DivisionWise&option="+reporttype+"&month="+month+"&year="+year+"&ben="+ben+"&process="+process;
				
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 }
			
			 
			 else if(cmd=='DCB')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
				 url="../../../../../../OpeningBalanceReport?command=Report&flag=DCB&option="+reporttype+"&month="+month+"&year="+year+"&ben="+ben+"&process="+process+"&report_id="+report_id;
				 
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 }
			 else if(cmd=='Int')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var report_id=0;
				 try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
				 var ben=0;  
				 url="../../../../../../OpeningBalanceReport?command=Report&option="+reporttype+"&flag=Int&month="+month+"&year="+year+"&ben="+ben+"&report_id="+report_id;;
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 }
			 
			 //  Ledger data 
			 else if(cmd=='Ldata')
			 {
				 
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
				//	 report_id=document.getElementById('Office_id').value;
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
		
				 
			
		
			     var src="../../../../../../Water_Charges_Report?command=Ldata&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
					
					
					window.open(src);
			 }
			 
			   else if(cmd=='dashboard')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;  
				  try 
				 { 
				//	 report_id=document.getElementById('Office_id').value;
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
				 
				 var src="../../../../../../Water_Charges_Report?command=dashboard&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
			 }
			 
			  else if(cmd=='dbd')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;  
				  try 
				 { 
				//	 report_id=document.getElementById('Office_id').value;
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
				 
				 var src="../../../../../../Water_Charges_Report?command=dbd&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
			 }
			 
			 
			    else if(cmd=='watercharges')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;  
				 
				 const currentDate = new Date();

				const year1 = currentDate.getFullYear();
				const month1 = currentDate.getMonth() + 1; 
				
				
				  try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){
					 console.log(e);
				 }
				 
				 if(year ==  year1 && month == month1){
					 alert('Report not available for current month')
					
				 }else{
					  var src="../../../../../../Water_Charges_Report?command=watercharges&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
				 }
				 
			 }
			 
			 else if (cmd == 'monthEndBalance' ){
				 
				  var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value; 
				 var bentype = document.getElementById('bentype').value;
				 
				  const currentDate = new Date();

				const year1 = currentDate.getFullYear();
				const month1 = currentDate.getMonth() + 1; 
				 
				  try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){
					 console.log(e);
				 }
				 
				  if(year ==  year1 && month == month1){
					 alert('Report not available for current month')
					
				 }else{
					  var src="../../../../../../Water_Charges_Report?command=monthEndBalance&month="+month+"&year="+year+"&bentype="+bentype+"&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
				 }
			 }
			 
			  else if (cmd == 'monthEndAbstract' ){
				 
				  var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value; 
				 var bentype = document.getElementById('bentype').value;
				 
				  const currentDate = new Date();

				const year1 = currentDate.getFullYear();
				const month1 = currentDate.getMonth() + 1; 
				 
				  try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){
					 console.log(e);
				 }
				 
				  if(year ==  year1 && month == month1){
					 alert('Report not available for current month')
					
				 }else{
					  var src="../../../../../../Water_Charges_Report?command=monthEndAbstract&month="+month+"&year="+year+"&bentype="+bentype+"&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
				 }
			 }
			 
			 
			 else if (cmd == 'DmdColn' ){
				 
				  var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value; 
				 
				  const currentDate = new Date();

				const year1 = currentDate.getFullYear();
				const month1 = currentDate.getMonth() + 1; 
				 
				  try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){
					 console.log(e);
				 }
				 
				  if(year ==  year1 && month == month1){
					 alert('Report not available for current month')
					
				 }else{
					  var src="../../../../../../Water_Charges_Report?command=DmdColn&month="+month+"&year="+year+ "&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
				 }
			 }
			 
			 else if (cmd == 'CurColn' ){
				 
				  var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value; 
				 
				  const currentDate = new Date();

				const year1 = currentDate.getFullYear();
				const month1 = currentDate.getMonth() + 1; 
				 
				  try 
				 { 
					 report_id=document.getElementById('div').value;
				 }catch(e){
					 console.log(e);
				 }
				 
				 
					  var src="../../../../../../Water_Charges_Report?command=CurColn&month="+month+"&year="+year+ "&report_id="+report_id+"&option="+reporttype;;
				
			       window.open(src);
				 
			 }
			 //   Ledger data over 
			
		
			 
		//	 SCHEME WISE
			 
			 else if(cmd=='Scheme')
			 {
				 
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
				//	 report_id=document.getElementById('Office_id').value;
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
		
				 
			
			//	 url="../../../../../../OpeningBalanceReport?command=Report&option="+reporttype+"&flag=Int&month="+month+"&year="+year+"&ben="+ben+"&report_id="+report_id;;
			 //       winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			 //       winemp.moveTo(250,250); 
			  //      winemp.focus();
			        
			  //      var src="../../../../../../Water_Charges_Report?command=Ldata&month="+month+"&year="+year+"&ben="+ben+"&report_id="+report_id;;
			        
			 //    var src="../../../../../../Water_Charges_Report?command=Scheme&month="+month+"&year="+year+"&report_id="+report_id;
			     var src="../../../../../../Water_Charges_Report?command=Scheme&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
							
					window.open(src);
			 }
			 
			 
			 //   Ledger data over 
		
			 
			 //SCHEMEWISE  OVER
			 //   DCB_REP91      starts
			 
			 else if(cmd=='DCB_REP91')
			 {
				 
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
				
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
		
				 
			
			     var src="../../../../../../Water_Charges_Report?command=DCB_REP91&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
							
					window.open(src);
			 }
		
			 
			 else if(cmd=='DCB_REP92')
			 {
				 
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
				
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
		
				 
			
			     var src="../../../../../../Water_Charges_Report?command=DCB_REP92&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
							
					window.open(src);
			 }
			 
			 /////////  DCB_REP93
			 
			 else if(cmd=='DCB_REP93')
			 {
				 
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
				
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
		
				 
			
			     var src="../../../../../../Water_Charges_Report?command=DCB_REP93&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
							
					window.open(src);
			 }
		
			 
			 else if(cmd=='DCB_REP94')
			 {
				 
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				 var process=document.getElementById('process').value;
				 var ben=0;
				 var report_id=0;
				 try 
				 { 
				
					 
					 report_id=document.getElementById('div').value;
				 }catch(e){}
		
				 
			
			     var src="../../../../../../Water_Charges_Report?command=DCB_REP93&month="+month+"&year="+year+"&report_id="+report_id+"&option="+reporttype;
							
					window.open(src);
			 }
		
			 
			 
			 
			 //  DCB_REP91 over 
			 
			 else if(cmd=='missing')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				
				 var ben=0;
				 url="../../../../../../OpeningBalanceReport?command=Report&option="+reporttype+"&flag=missing&month="+month+"&year="+year;
			        winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
			        winemp.moveTo(250,250); 
			        winemp.focus();
			 }
			 else if(cmd=='missing2')
			 {
				 var month=document.getElementById('smonth').value;
				 var year=document.getElementById('year').value;
				
				 var ben=0;
				
				 url="../../../../../../OpeningBalanceReportnew?command=Report&option="+reporttype+"&flag=missing2&month="+month+"&year="+year;
			    // alert(url);
				 
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

                    else if(cmd == 'Year')
                    { 
                        if(flag=='success')
                        {
                        	 
                        		var cmb = document.getElementById("year");
                                var row = baseResponse.getElementsByTagName('row');
                                
                                for(var i=0; i<=row.length; i++)
                                {
                                	try
                                	{
                                    var year = row[i].getElementsByTagName('year')[0].firstChild.nodeValue;
                                    var opt = document.createElement('option');
                                    opt.value = year;
                                    opt.innerHTML = year;
                                    cmb.appendChild(opt);
                                	}catch(e){}
                                }
                        }
                        else
                        {
                                alert("Failed to load Years");
                        }
                    }                    
                    
  /*  
                    else if(cmd == 'District')
                    {
                    	if(flag=='success')
                    	{
                    		var cmb = document.getElementById('dis');
                            
                            var row = baseResponse.getElementsByTagName('row');
                            var len = row.length;
                            unloadCombo('dis');

                            for(var i=0; i<len; i++)
                            {
                                    var dis = row[i].getElementsByTagName('dis')[0].firstChild.nodeValue;
                                    var district = row[i].getElementsByTagName('district')[0].firstChild.nodeValue;

                                    var opt = document.createElement('option');
                                    opt.value = dis;
                                    opt.innerHTML = district;
                                    
                                    cmb.appendChild(opt);
                            }
                    	}
                    	else
                    	{
                    		alert("Failed to load Districts");
                    	}
                    }                    
                    
                    else if(cmd == 'Block')
                    {
                    	if(flag=='success')
                    	{
                    		var cmb = document.getElementById('blk');
                            
                            var row = baseResponse.getElementsByTagName('row');
                            var len = row.length;
                            unloadCombo('blk');

                            for(var i=0; i<len; i++)
                            {
                                    var pan = row[i].getElementsByTagName('blk')[0].firstChild.nodeValue;
                                    var panch = row[i].getElementsByTagName('block')[0].firstChild.nodeValue;

                                    var opt = document.createElement('option');
                                    opt.value = pan;
                                    opt.innerHTML = panch;
                                    
                                    cmb.appendChild(opt);
                            }
                            
	                	}
	                	else
	                	{
	                		alert("Failed to load Blocks");
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
			       winSearchConsumer=null;
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
		
		function divChange(e){
			console.log(e.value);
		}
		