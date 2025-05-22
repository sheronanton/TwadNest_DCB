		var AREA='';
		var GRADE='';
		var ULBtype='';
		var PRVLB='';
		var CONS='Get';
		var CTYPEDESC='';
		
		function init()
		{
			if((PRVLB=='L') && (CTYPE!=6))
			{
				//TEMP// CONS = 'ULB';
				document.getElementById('box2').style.display = 'inline';
				document.getElementById('box1').style.display = 'none';
				document.getElementById('box3').style.display = 'none';
				
	            
				//Corporation
				if(CTYPE==1)
	            {
	            	GRADE=1;
	            	ULBtype=2;        // ULBtype=2 stands for 'Corporation'   -   COM_MST_URBAN_LB_TYPE
	            }
				
				//III grade Municipality
	            else if(CTYPE==2)
	            {
	            	ULBtype=3;        // ULBtype=3 stands for 'Municipality'  -   COM_MST_URBAN_LB_TYPE
	            	GRADE=3;				// III grade (Municipality)
	            }
				
				//Municipality
	            else if(CTYPE==3)
	            {
	            	ULBtype=3;        // ULBtype=3 stands for 'Municipality'  -   COM_MST_URBAN_LB_TYPE
	            }
				
				//Urban Town Panchayat
	            else if(CTYPE==4)
	            {
	            	ULBtype=1;        // ULBtype=1 stands for 'Town Panchayat'   -   COM_MST_URBAN_LB_TYPE
	            	//GRADE=5;
	            	AREA='U';					// Urban (Town Panchayat)
				}
				
				//Rural Town Panchayat
	            else if(CTYPE==5)
	            {
	            	ULBtype=1;        // ULBtype=1 stands for 'Town Panchayat'   -   COM_MST_URBAN_LB_TYPE
	            	//GRADE=5;
	            	AREA='R';					// Rural (Town Panchayat)
	            }
				

				callServer('ULBgrade');
			
				
				if((CTYPE==4)||(CTYPE==5))
				{
					//callServer('areaType');
				}
				

				
				
        		document.getElementById('tdNoData').colspan="3";
        		document.getElementById('tblHead').colspan="3";
    		
        		var colHead = document.getElementById('colHead');
        		
        		var th = document.createElement('th');
        		th.innerHTML='Select';
        		colHead.appendChild(th);
        		

        		var th = document.createElement('th');
        		th.innerHTML='District';
        		colHead.appendChild(th);

        		var th = document.createElement('th');
        		th.innerHTML=CTYPEDESC;
        		colHead.appendChild(th);
        		
			}
			else if(CTYPE==6)
			{
				//TEMP// CONS = 'Panch';
				document.getElementById('box1').style.display = 'inline';
				document.getElementById('box2').style.display = 'none';
				document.getElementById('box3').style.display = 'none';

				
				
				document.getElementById('tdNoData').colspan="4";
        		document.getElementById('tblHead').colspan="4";
        		
        		var colHead = document.getElementById('colHead');
        		
        		var th = document.createElement('th');
        		th.innerHTML='Select';
        		colHead.appendChild(th);
        		
        		var th = document.createElement('th');
        		th.innerHTML='District';
        		colHead.appendChild(th);

        		var th = document.createElement('th');
        		th.innerHTML='Block';
        		colHead.appendChild(th);
        		
        		var th = document.createElement('th');
        		th.innerHTML='Panchayat';
        		colHead.appendChild(th);
        		
        		callServer('District'); // body onload  ==  after calling init()

			}
			else if(PRVLB=='P')
			{
				//TEMP// CONS = 'Private';
				
				 
				
				document.getElementById('box1').style.display = 'none';
				document.getElementById('box2').style.display = 'none';
				document.getElementById('box3').style.display = 'inline';

				
				
				document.getElementById('tdNoData').colspan="4";
        		document.getElementById('tblHead').colspan="4";
        		
        		var colHead = document.getElementById('colHead');
        		
        		var th = document.createElement('th');
        		th.innerHTML='Select';
        		colHead.appendChild(th);

        		var th = document.createElement('th');
        		th.innerHTML='District';
        		colHead.appendChild(th);
        		
        		var th = document.createElement('th');
        		th.innerHTML=CTYPEDESC;
        		colHead.appendChild(th);

			}
			else 
			{
				//TEMP// CONS='NOT SELECTED';
				alert("Please Select a Consumer Type");
				Exit();
			}
			
	
			search();
		}
		

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
		
		
		function Exit()
		{
		   self.close();
		}
		
		
		function nullCheck(idx)
		{
			var ele = document.getElementById(idx);
			if((ele.value=="")||(ele.value==null))
			{ 
			     return false;
			}
			return true;
		}

		

		function unloadCombo(eleName)
		{
			var cmb = document.getElementById(eleName);
			var len = cmb.length; 
			for(var i=len; i>1; i--)
			{
				cmb.removeChild(cmb.lastChild);
			}
		}

		
		
		function search()
		{
			//if(  (nullCheck('dis')||nullCheck('blk')||nullCheck('pan')&&(CONS=='Panch'))   ||   (nullCheck('ulb')&&(CONS=='ULB'))   ||   (nullCheck('priv')&&(CONS=='Private'))  )  //nullCheck('area')||nullCheck('ULBgrade')||
			{
				CONS='Get';
				document.getElementById('cmbpage').value=1;
				callServer(CONS);
			}
/*			else
			{
				clearGrid();
			}
*/			

		}
		

		function callServer(param)
		{
			 if(param=='TypeCategory')
             {
				 	url="../../../../../BenComSearch?command=TypeCategory&ctype="+CTYPE;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }
                    req.send(null);
             }
			 
			 else if(param=='District')
             {
				 	url="../../../../../BenComSearch?command=District";
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }
                    req.send(null);
             }
			 
			 else if(param=='Block')
             {
				 	var dis = document.getElementById('dis').value;
             		if(dis!="")
             		{
             		 	url="../../../../../BenComSearch?command=Block&dis="+dis;
	                    var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
             		}
             }

			 else if(param=='ULBgrade')
             {
				 	url="../../../../../BenComSearch?command=ULBgrade";
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
             }
			 else if(param=='Get')
             {
				 	var cname=null;
				 	
				 	var dis = document.getElementById('dis').value;
				 	var blk = document.getElementById('blk').value;
				 	var pan = (document.getElementById('pan').value).toLowerCase();
				 	
				 	var area = AREA;//document.getElementById('area').value;
				 	var ULBgrade = document.getElementById('ULBgrade').value; // =GRADE;
				 	var ulb = (document.getElementById('ulb').value).toLowerCase();
				 	
				 	var priv = (document.getElementById('priv').value).toLowerCase();
				 	
				 	var flg='none';
				 	if(PRVLB=='L')
				 	{
					 	if(CTYPE==6)
					 	{
					 		cname = pan;
					 		flg='pan';
					 	}
					 	else 
					 	{
					 		cname = ulb;
					 		flg='ulb';
					 	}
				 	}
				 	else
				 	{
				 		cname = priv;
				 		flg='priv';
				 	}
				 	
				 	document.getElementById('divcmbpage').style.display = "inline";
				 	document.getElementById('divpage').style.display = "inline";

				 	var page = document.getElementById('cmbpage').value;
				 	var limit = document.getElementById('cmbpagination').value;
             		
				 	url="../../../../../BenComSearch?command=Get&dis="+dis+"&blk="+blk+"&area="+area+"&ULBgrade="+ULBgrade+"&cname="+cname+"&ctype="+CTYPE+"&page="+page+"&limit="+limit;
				 	
				 	
				 	var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }
                    req.send(null);
             }
			 else
			 {
				 alert("callServer('"+param+"') does not match");
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
                    if(cmd == 'TypeCategory')
                    {
                    	if(flag=='success')
                    	{
                            var type = baseResponse.getElementsByTagName('type')[0].firstChild.nodeValue;
                            var categ = baseResponse.getElementsByTagName('categ')[0].firstChild.nodeValue;
                            
                            CTYPEDESC=type;
                            PRVLB=categ;
                            
                            init();// body onload
                    	}
                    	else
                    	{
                    		alert("Failed to find Beneficiary Type and Category");
                    	}
                    }                    
                    
                    
                    else if(cmd == 'District')
                    {
                    	if(flag=='success')
                    	{
                    		document.getElementById('cmbpage').value=1;
                    		
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
                    		document.getElementById('cmbpage').value=1;
                    		
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
                            
                            search();
	                	}
	                	else
	                	{
	                		alert("Failed to load Blocks");
	                	}
                    }                    

                    else if(cmd == 'Get')
                    {
                    	if(flag=='success')
                    	{
                    	    var tbody = document.getElementById('tbody');
                            
                            var row = baseResponse.getElementsByTagName('row');
                            var len = row.length;

                            unloadChildren('tbody');
                            unloadCombo('cmbpage');
                            
                            var page = new Number(baseResponse.getElementsByTagName('page')[0].firstChild.nodeValue);
                            var totpg = new Number(baseResponse.getElementsByTagName('total')[0].firstChild.nodeValue);
                            

                            
                            
                            /****** Load 'Page No.' Combo & 'Total Pages' **********/ 
                            
                            document.getElementById('divpage').innerHTML = totpg;
                            var cmbpage = document.getElementById('cmbpage');
                            
                            for(var i=2; i<=totpg; i++)
                            {
                            	var opt = document.createElement('option');
                            	opt.value = i;
                            	opt.innerHTML = i;
                            	cmbpage.appendChild(opt);
                            }
                            cmbpage.value = page; 
                            
                            /*******************************************************/
                            
                            
                            
                            
                            
                            /************* 'Next' & 'Previous' links **************/
                            
                            if(page<totpg)
                            {
                            	document.getElementById('divnext').style.display = 'inline';
                            }
                            else
                            {
                            	document.getElementById('divnext').style.display = 'none';
                            }
                            
                            
                            if(page>1)
                            {
                            	document.getElementById('divpre').style.display = 'inline';
                            }
                            else
                            {
                            	document.getElementById('divpre').style.display = 'none';
                            }
                            /*******************************************************/
                            
                            
                            
                            document.getElementById('nodata').style.display="none";     // Hide 'No Data Found' msg
                            
                            if(totpg==0)
                            {
                            	hidePag();
                            }
                            
                            
                            for(var i=0; i<len; i++)
                            {
	                            	var sno = row[i].getElementsByTagName('sno')[0].firstChild.nodeValue;
                            		var cname = row[i].getElementsByTagName('cname')[0].firstChild.nodeValue;
                            		var district = row[i].getElementsByTagName('district')[0].firstChild.nodeValue;
                            		var block = row[i].getElementsByTagName('block')[0].firstChild.nodeValue;
                          	      
                            		
                                    var tr = document.createElement('tr');
                                    tr.id = sno;
                                    
                                    var tdRad = document.createElement('td');
                                    var rad;
                                    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                    {

                                    	rad = document.createElement("<input type='radio' name='rad' id='rad' value="+sno+" onclick='fixRow(this.value);'>");
                                    }
                                    else
                                    {
                                    	rad = document.createElement('input');
                                        rad.name = 'rad';
                                        rad.type = 'radio';
                                        rad.value = sno;
                                        rad.setAttribute('onclick',"fixRow(" + sno + ")");
                                    }

                                    tdRad.appendChild(rad);
                                    tr.appendChild(tdRad);

                                    
                                    td = document.createElement('td');
                                    td.innerHTML = sno;
                                    td.style.display='none';
                                    tr.appendChild(td);
                                    
                                    
                                    var td;

                                    td = document.createElement('td');
                                    td.innerHTML = district;
                                    tr.appendChild(td);

                                    if(PRVLB=='L')
                                    {
                                    	if (CTYPE==6)
                                    	{
		                                    td = document.createElement('td');
		                                    td.innerHTML = block;
		                                    tr.appendChild(td);
                                    	}
                                    }
                           
                                    td = document.createElement('td');
                                    td.innerHTML = cname;
                                    tr.appendChild(td);
                            

                                    tbody.appendChild(tr);
                            }
	                	}
	                	else
	                	{
	                		alert("Unable to search Beneficiary");
	                	}
                    }
                    
                    else if(cmd == 'ULBgrade')
                    {
                    	if(flag=='success')
                    	{
                    		document.getElementById('cmbpage').value=1;
                    		
                    		var cmb = document.getElementById('ULBgrade');
                            
                            var row = baseResponse.getElementsByTagName('row');
                            var len = row.length;
                            unloadCombo('ULBgrade');

                            for(var i=0; i<len; i++)
                            {
                                    var gid = row[i].getElementsByTagName('gid')[0].firstChild.nodeValue;
                                    var gdesc = row[i].getElementsByTagName('gdesc')[0].firstChild.nodeValue;
                                    
                                    
                                    /******************************************************
                                     * 	If Municipality - grade III should not be displayed
                                     ******************************************************/
                                    if(CTYPE==3) // Municipality
                                    {
                                    	document.getElementById('ULBgrade').disabled = false;
                                    	if(gid==3)
                                    	{
                                    		continue; // Skip Grade-III
                                    	}
                                    }
                                    else if((CTYPE>6)||(CTYPE==4)||(CTYPE==5))
                                    {
                                    	document.getElementById('ULBgrade').disabled = false;
                                    }
                                    else
                                    {
                                    	document.getElementById('ULBgrade').disabled = true;
                                    }
                                    /******************************************************/
                                    
                                    
                                    var opt = document.createElement('option');
                                    opt.value = gid;
                                    opt.innerHTML = gdesc;
                                    
                                    cmb.appendChild(opt);
                            }
                            
                            
                            if(CTYPE==1)
                            {
                            	cmb.value=1;
                            	
                            }
                            else if(CTYPE==2)
                            {
                            	cmb.value=3;
                            }
                            else if(CTYPE==4) // UTP
                            {
                            	//cmb.value=5; // Spl grade  (UTP Spl)
                            }
                            else if(CTYPE==5) // RTP
                            {
                            	//cmb.value=5; // Spl grade  (RTP Spl)
                            }
                            
                            search();
	                	}
	                	else
	                	{
	                		alert("Unable to load Grades");
	                	}
                    }                 
                    
                    else if(cmd == 'Duplicate')
                    {
                    	if(flag=='duplicate')
                    	{
                    		alert('Beneficiary already Exists. Please select another Beneficiary.');
                    	}
                    }                    
                    

                    
                    
                    
                    
                }
            }
   		}
   		

		
		
		  
/*
		function clearGrid()
		{
			unloadChildren('tbody');
			hidePag();
		}
*/
		function clearSrchFrm()
		{
			document.forms[0].reset();
			callServer('Get');
		}
		
		
		
		function unloadChildren(eleName)
		{
			var prnt = document.getElementById(eleName);
			var len = prnt.childNodes.length;
			for(var i=len; i>0; i--)
			{
				prnt.removeChild(prnt.lastChild);
			}
		}
	
	
	   
	   function prev()
	   {
		   var page = document.getElementById('cmbpage');
		   
		   if(new Number(page.value>1))
		   {
			   (page.value)--;
		   }
		   callServer(CONS);
	   }
	

	   
	   function next()
	   {
		   var page = document.getElementById('cmbpage');
		   var totpg = new Number(document.getElementById('divpage').firstChild.nodeValue);
		   if(new Number(page.value<totpg))
		   {
			   (page.value)++;
		   }
		   callServer(CONS);
	   }
	   
	   
	   
	   function hidePag()
	   {
		   document.getElementById('divnext').style.display="none";
		   document.getElementById('divpre').style.display="none";
		   document.getElementById('divpage').style.display="none";
		   document.getElementById('divcmbpage').style.display="none";
		   
		   document.getElementById('nodata').style.display="inline";     // Display 'No Data Found' msg
	   }
	   
	   var SELSNO=0;
	   function fixRow(sno)
	   {
		   SELSNO=sno;
	   }
	   
	   function done()
	   {
		   var selRow = document.getElementById(SELSNO);
		   var sno = selRow.childNodes[1].lastChild.nodeValue;
		   var cname = selRow.lastChild.lastChild.nodeValue;
		   opener.doParent(cname,sno);
		   Exit();
	   }
	 