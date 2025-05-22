	/*  Common Master Js  */	
	/*  type 1 - Group master 
	 *  processcode {1 - save,2-edit,3-delete,4-show }
	 *  
	 * 
	 * */
	var process_code=0;
	var selected_type;
	var sel_type=0;
	var url="";
	var flag=1;
	function sch_transaction(type,processcode)
	{
				document.getElementById('divcmbpage').style.display = "inline";
				  document.getElementById('divpage').style.display = "inline";
				var page = document.getElementById('cmbpage').value;
				var limit = document.getElementById('cmbpagination').value; 
				
				process_code=processcode;
				sel_type=type;
				var xmlobj=createObject();
				url="../../../../../sch_transaction";
				var params="page="+page+"&limit="+limit;
				xmlobj.open("POST",url,true);	
				xmlobj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xmlobj.setRequestHeader("Content-length", params.length);
				xmlobj.setRequestHeader("Connection", "close");
				
				var category_sno=document.getElementById("category_sno").value;
				var pyear=document.getElementById("pyear").value;
				var sch_type=document.getElementById("sch_type").value;
				var sch_code=document.getElementById("sch_code").value;
				var sch_name=document.getElementById("sch_name").value;
				var search=document.getElementById("search").value;
				  params+="&search="+search+"&process_code="+process_code+"&type="+type+"&pyear="+pyear+"&sch_type="+sch_type+"&sch_code="+sch_code+"&sch_name="+sch_name+"&category_sno="+category_sno;
	
				   xmlobj.onreadystatechange=function()
					{	 
						sch_process(xmlobj);
					}
			    	xmlobj.send(params);
				 
	}
	function master(type,processcode)
	{
		 
		process_code=processcode;
		sel_type=type;
		var xmlobj=createObject();
		url="../../../../../ame_master";
		var params="";
	// Group - Insert 	
	if (sel_type==1)
	{
		
		if (process_code==1) 
		{
			if (document.getElementById("desc").value!="")
			  {
			  params="desc='"+document.getElementById("desc").value+"'&process_code="+process_code+"&type="+type;
	 		   	  
	 		  flag=0;  
	 		  }
	 	}else if (process_code==2)
		{
	 		   
	 		   var rowcount=document.getElementById("rowcount").value;		
	 		   params="type="+type;
	 		   var j=0;
	 		   var i=0;
	 		   j++;
	 		   i=document.getElementById("uprow").value
			  // for (i=1;i<=parseInt(rowcount);i++)
			   //{
				   //if (document.getElementById("chkGROUP_SNO"+i).checked)
				   //{   j++; 
					   params+="&desc"+j+"='"+document.getElementById("desc").value+"'&GROUP_SNO"+j+"="+document.getElementById("GROUP_SNO"+i).value;
				  // }
			   //}  
			   
			   params+="&rowcount="+j+"&process_code="+process_code;
			  
	 		   flag=0;  
	 		  
	 	}else if (process_code==3)
		{
			  params="process_code="+process_code+"&type="+type;;
			  flag=0;
		}  
	 	else if (process_code==4)
		{ 	
	 			params="process_code="+process_code+"&type="+type;
	 			var rowcount=document.getElementById("rowcount").value;
	 			for (i=1;i<=parseInt(rowcount);i++)
			   {
	 					if (document.getElementById("chkGROUP_SNO"+i).checked)
	 					{
	 						    
	 						params+="&GROUP_SNO="+document.getElementById("GROUP_SNO"+i).value
	 					}
			   }
			  flag=0;
		} 
	}else if (sel_type==2)
	{
	//   New MainItem insert to table 
		if (process_code==1)
		{
		
			 if (document.getElementById("desc").value!="" && document.getElementById("group").value!=0)
			  {
			   params="group="+document.getElementById("group").value+"&process_code="+process_code+"&type="+type+"&desc="+document.getElementById("desc").value;		  
			  // params+="&cap="+( (document.getElementById("cenageapl").checked==true) ?1:0);
				 
			   flag=0;
	 		  }		    
		}
		//   Update  The Main item Set value to url
		else if (process_code==2)
		{
			 
			   params="type="+type;       
			   params+="&desc="+document.getElementById("desc").value+"&GROUP_DESC="+document.getElementById("group").value;			   		   
			   params+="&process_code="+process_code;
			   params+="&key_value="+document.getElementById("key_value").value;
			 //  params+="&chkAPPLY_CENTAGE="+document.getElementById("cenageapl").checked;
			   	if (document.getElementById("desc").value!="" && document.getElementById("group").value!=0)
				  {
				   flag=0;			 
				  }	
			   	 
		}
		//  List of Main Item From Table 
		else if (process_code==3)
		{
				params="process_code="+process_code+"&type="+type;;
				flag=0;
		}
		// Delete the Main Item
		else if (process_code==4)
		{
			   params="type="+type;
			   params+="&process_code="+process_code;
			   params+="&key_value="+document.getElementById("key_value").value;
			   if (document.getElementById("key_value").value!=0)
			   {
				   flag=0;
			   }else
			   {
				   flag=1;
			   }
		}	
		
	}else if (sel_type==3)
	{
		// New Sub Item Insert 
		if (process_code==1) 
		{  
			
			if (document.getElementById("desc").value!="" && document.getElementById("main").value!=0)
			{
			 
			 params="main="+document.getElementById("main").value+"&shortdesc="+document.getElementById("shortdesc").value+"&process_code="+process_code+"&type="+type+"&desc="+document.getElementById("desc").value;
			 params+="&cap="+( (document.getElementById("cenageapl").checked==true) ?1:0);
			 flag=0;
	 		 }  
			
			  
		}
		//   Update The Sub item Set value to url
		else if (process_code==2)
		{
			 
			   params="type="+type;      
			   params+="&desc="+document.getElementById("desc").value+"&shortdesc="+document.getElementById("shortdesc").value+"&MAIN_ITEM_SNO="+document.getElementById("main").value;			   		   
			   params+="&process_code="+process_code;
			   params+="&chkAPPLY_CENTAGE="+document.getElementById("cenageapl").checked;
			   params+="&key_value="+document.getElementById("key_value").value;
			   if (document.getElementById("desc").value!="" && document.getElementById("main").value!=0)
			   {
			      flag=0;			  
			    }
		 
		}
		//  List of Sub item
		else if (process_code==3)
		{
			 params="process_code="+process_code+"&type="+type;;
			 flag=0;
		}
		// Delete the Sub Item
		else if (process_code==4)
		{
			   params="type="+type;
			   params+="&process_code="+process_code;
			   params+="&key_value="+document.getElementById("key_value").value;
			   if (document.getElementById("key_value").value!=0)
			   {
				   flag=0;
			   }else
			   {
				   flag=1;
			   }
		}	 
		
	}
	else 	if (sel_type==4)
	{
		//centage master
		if (process_code==1) 
		{
			// ADD - Centage
			var centage_Rate=document.getElementById("centage_Rate").value;
			var centage_wef=document.getElementById("centage_wef").value;
			var status=document.getElementById("status").value;
			var sch_sno=document.getElementById("sch_sno").value;
			if(centage_Rate=="")
			{
				alert("Enter Centage Rate");
				return false;
			}
			else if(centage_wef=="")
			{
				alert("Enter Cetage W.E.F");
				return false;
			}
			else if(status=="")
			{
				alert("Enter Cetage Status");
				return false;
			}
			
				params="centage_Rate="+centage_Rate+"&sch_sno="+sch_sno+"&centage_wef="+centage_wef+"&status="+status+"&process_code="+process_code+"&type="+type;
				flag=0;  
			
		}
		else if(process_code==2)
		{
			var centage_Id=document.getElementById("centage_Id").value;
			var centage_Rate=document.getElementById("centage_Rate").value;
			var centage_wef=document.getElementById("centage_wef").value;
			var status=document.getElementById("status").value;
			var sch_sno=document.getElementById("sch_sno").value;
			if(centage_Rate=="")
			{
				alert("Enter Centage Rate");
				return false;
			}
			else if(centage_wef=="")
			{
				("Enter Cetage W.E.F");
				return false;
			}
			else if(status=="")
			{
				alert("Enter Cetage Status");
				return false;
			}
			params="centage_Id="+centage_Id+"&sch_sno="+sch_sno+"&centage_Rate="+centage_Rate+"&centage_wef="+centage_wef+"&status="+status+"&process_code="+process_code+"&type="+type;
			flag=0;  
		
		}
		else if (process_code==3) 
		{
			params="process_code="+process_code+"&type="+type;
			flag=0;
		}
		
	}
	if (sel_type==5)
	{
		// scheme details
		if (process_code==1) 
		{
			// 22/12/2011
			
			// ADD - design qty
			var sch_sno=document.getElementById("sch_sno").value;
			var sch_qty=document.getElementById("sch_qty").value;
			var yearcomm=document.getElementById("yearcomm").value;
			var pumphours=document.getElementById("pumphours").value;
			var noofstaff=document.getElementById("noofstaff").value;
			var noofhab=document.getElementById("noofhab").value;
			
			 var sch_sno1=0;//document.getElementById("sch_sno1").value;
			if(sch_sno==0)
			{
				alert("Select Scheme Name");
				return false;
			}
			else if(sch_qty=="")
			{
				alert("Enter Scheme Design Quantity");
				return false;
			}
			 
				params="process_code="+process_code+"&type="+type+"&sch_sno="+sch_sno+"&sch_qty="+sch_qty;
				params+="&yearcomm="+yearcomm+"&pumphours="+pumphours+"&noofstaff="+noofstaff+"&noofhab="+noofhab+"&sch_sno1="+sch_sno1;
				flag=0;  
				document.getElementById("cmdupdate").disabled=true;   
		}   
		else if (process_code==2) 
		{
			// update - design qty
			var key_value=document.getElementById("key_value").value;
			var sch_sno=document.getElementById("sch_sno").value;
			var sch_qty=document.getElementById("sch_qty").value;
			// 22/12/2011
			
			var yearcomm=document.getElementById("yearcomm").value;
			var pumphours=document.getElementById("pumphours").value;
			var noofstaff=document.getElementById("noofstaff").value;
			var noofhab=document.getElementById("noofhab").value;
			if(sch_sno==0)
			{
				alert("Select Scheme Name");
				return false;
			}
			else if(sch_qty=="")
			{
				alert("Enter Scheme Design Quantity");
				return false;
			}
			
				params="process_code="+process_code+"&type="+type+"&sch_sno="+sch_sno+"&sch_qty="+sch_qty+"&key_value="+key_value;
				params+="&yearcomm="+yearcomm+"&pumphours="+pumphours+"&noofstaff="+noofstaff+"&noofhab="+noofhab;
				flag=0;  
				document.getElementById("cmdadd").disabled=false;
				
		}
		else if(process_code == 3)
		{
			params="process_code="+process_code+"&type="+type;
			flag=0;
			
		}else if (process_code==7) 
		{
			var sch_sno=document.getElementById("sch_sno").value;
			if(sch_sno==0)
			{
				alert("Select Scheme Name");
				return false;
			}else
			{
			var key_value=document.getElementById("key_value").value;
			params="process_code="+process_code+"&type="+type+"&sch_sno="+sch_sno+"&key_value="+key_value;
			flag=0;
			}    
		}
	}else if (sel_type==7)
	{
		//Accounts Group Master
		if (process_code==1) 
		{
			if (document.getElementById("desc").value!="")
			  {
			  params="desc='"+document.getElementById("desc").value+"'&process_code="+process_code+"&type="+type;
	 		   	  
	 		  flag=0;  
	 		  }
	 	}else if (process_code==2)
		{
	 		   
	 		   var rowcount=document.getElementById("rowcount").value;		
	 		   params="type="+type;
	 		   var j=0;
	 		   var i=0;
	 		   j++;
	 		   i=document.getElementById("uprow").value
			  // for (i=1;i<=parseInt(rowcount);i++)
			   //{
				   //if (document.getElementById("chkGROUP_SNO"+i).checked)
				   //{   j++; 
					   params+="&desc"+j+"='"+document.getElementById("desc").value+"'&ACC_GROUP_SNO"+j+"="+document.getElementById("ACC_GROUP_SNO"+i).value;
				  // }
			   //}  
			   
			   params+="&rowcount="+j+"&process_code="+process_code;
			  
	 		   flag=0;  
	 		  
	 	}else if (process_code==3)
		{
			  params="process_code="+process_code+"&type="+type;;
			  flag=0;
		}  
	 	else if (process_code==4)
		{ 	
	 			params="process_code="+process_code+"&type="+type;
	 			var rowcount=document.getElementById("rowcount").value;
	 			var j=0;
	 	 		var i=0;
	 	 		j++;
	 	 		i=document.getElementById("uprow").value
	 			params+="&ACC_GROUP_SNO="+document.getElementById("ACC_GROUP_SNO"+i).value
	 		 
			  flag=0;
		} 
	}

				if (parseInt(flag)==0)
				{
					xmlobj.open("POST",url,true);
				    xmlobj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					xmlobj.setRequestHeader("Content-length", params.length);
					xmlobj.setRequestHeader("Connection", "close");
					 
				    xmlobj.onreadystatechange=function()
				    {	 
						master_process(xmlobj);
				    }  
				    
				    	xmlobj.send(params);
					 
				}
				else
				{
					alert(" Please Enter All Details ");					
				}
}
	function master_process(xml_obj)
{
	
	if (xml_obj.readyState==4)
	 {	   
		 
		if (xml_obj.status==200)
	    {
				master_process_result(xml_obj);
				flag=1;
	    }
	 }
}
	function sch_process(xml_obj)
{
	
	if (xml_obj.readyState==4)
	 {	   
		 
		if (xml_obj.status==200)
	    {
			sch_process_result(xml_obj);
				flag=1;
	    }
	 }
}
	function sch_process_result(xml_obj)
{
	var tbody = document.getElementById("tblist");
	 
	var t=0;
	for(t=tbody.rows.length-1;t>=0;t--)
	{
		tbody.deleteRow(0);
	}	
	var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
	var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
	
	 var SCH_CODE="",  SCH_NAME="",  SCH_TYPE_DESC="",acompdate="",DPR_AMOUNT="";
	 unloadChildren('tblist');
     unloadCombo('cmbpage');
	for (var i=0;i<len;i++)
 	{ 
		SCH_CATEGORY_ID=xmlValue(bR,"SCH_CATEGORY_ID",i);	
		SCH_YEAR=xmlValue(bR,"SCH_YEAR",i);	
		SCH_TYPE_ID=xmlValue(bR,"SCH_TYPE_ID",i);	
		SCH_TYPE_DESC=xmlValue(bR,"SCH_TYPE_DESC",i);	
		SCH_CODE=xmlValue(bR,"SCH_CODE",i);	
		SCH_NAME=xmlValue(bR,"SCH_NAME",i);	
		 var capv =new String(SCH_NAME);
	        var str="";
	        for (var k=0;k<capv.length;k++)
	        {
	        	var cr=capv.charAt(k);  
	        	var ss=new String(cr);
	        	ss=ss.replace('#', '&');
	        	str+=ss;
	        }
	    SCH_NAME=str;
		SCH_SHORT_DESC=xmlValue(bR,"SCH_SHORT_DESC",i);
		var SCH_SNO=xmlValue(bR,"SCH_SNO",i);
		DPR_AMOUNT=xmlValue(bR,"DPR_AMOUNT",i);
		ACTUAL_DATE_COMMENCEMENT=xmlValue(bR,"ACTUAL_DATE_COMMENCEMENT",i);	
		acompdate=xmlValue(bR,"ACOMPDATE",i);	
		 var new_row=cell("TR","","","row2" ,1,2,"","","","20%","","","");
 		 var Sno_cell=cell("TD", "input", "radio", "chdcb", "","", "", "", "", "2%", "", "onclick", "edit_sch("+(i+1)+")");
		 var SCH_CATEGORY_ID_cell=cell("TD","input","hidden","SCH_CATEGORY_ID"+(i+1),SCH_CATEGORY_ID,2,"","","","2%","","","");
		 var SCH_YEAR_cell=cell("TD","input","hidden","SCH_YEAR"+(i+1),SCH_YEAR,2,"","","","2%","","","");
		 var SCH_TYPE_DESC_cell=cell("TD","label","","SCH_TYPE_DESC"+(i+1),SCH_TYPE_DESC,2,"lbl",""," ","","left","","");	
		 var SCH_TYPE_ID_cell=cell("TD","input","hidden","SCH_TYPE_ID"+(i+1),SCH_TYPE_ID,2,"","","","2%","","","");
		 var SCH_CODE_ID_cell=cell("TD","input","hidden","SCH_CODE"+(i+1),SCH_CODE,2,"","","","2%","","","");
		 var SCH_SNO_cell=cell("TD","input","hidden","SCH_SNO"+(i+1),SCH_SNO,2,"","","","2%","","","");
		 var SCH_NAME_cell=cell("TD","label","","SCH_NAME"+(i+1),SCH_NAME,2,"lbl",""," ","","left","","");	
		 var SCH_SHORT_DESC_cell=cell("TD","input","hidden","SCH_SHORT_DESC"+(i+1),SCH_SHORT_DESC,2,"","","","2%","","","");
		 var DPR_AMOUNT_cell=cell("TD","label","","DPR_AMOUNT"+(i+1),DPR_AMOUNT,2,"lbl",""," ","","center","","");
		 var ACTUAL_DATE_COMMENCEMENT_cell=cell("TD","input","hidden","ACTUAL_DATE_COMMENCEMENT"+(i+1),ACTUAL_DATE_COMMENCEMENT,2,"","","","2%","","","");
		 var acompdate_cell=cell("TD","label","","acompdate"+(i+1),acompdate,2,"lbl",""," ","","center","","");
		 	new_row.appendChild(Sno_cell);
		 	new_row.appendChild(SCH_CATEGORY_ID_cell);
		 	new_row.appendChild(SCH_YEAR_cell);
		 	new_row.appendChild(SCH_TYPE_ID_cell);
			new_row.appendChild(SCH_TYPE_DESC_cell);
			new_row.appendChild(SCH_CODE_ID_cell);
			new_row.appendChild(SCH_SNO_cell);
			new_row.appendChild(SCH_NAME_cell);
			new_row.appendChild(SCH_SHORT_DESC_cell); 

			new_row.appendChild(DPR_AMOUNT_cell);
			new_row.appendChild(ACTUAL_DATE_COMMENCEMENT_cell);
			new_row.appendChild(acompdate_cell);
			
			tbody.appendChild(new_row);   
 	}
	
     var page = new Number(xml_obj.responseXML.getElementsByTagName('page')[0].firstChild.nodeValue);
     var totpg = new Number(xml_obj.responseXML.getElementsByTagName('total')[0].firstChild.nodeValue);
     
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
}
	function edit_sch(rid)
{
	document.getElementById('category_sno').value = document.getElementById("SCH_CATEGORY_ID"+rid).value;
	document.getElementById('pyear').value = document.getElementById("SCH_YEAR"+rid).value;
	document.getElementById('sch_type').value = document.getElementById("SCH_TYPE_ID"+rid).value;
	document.getElementById('sch_code').value =(document.getElementById("SCH_CODE"+rid).value ==0) ? "" :document.getElementById("SCH_CODE"+rid).value ;
	document.getElementById('sch_name').value = document.getElementById("SCH_NAME"+rid).innerHTML;
	document.getElementById('sch_desc').value = document.getElementById("SCH_TYPE_DESC"+rid).innerHTML;
	document.getElementById('dpr_amt').value = document.getElementById("DPR_AMOUNT"+rid).innerHTML;
	document.getElementById('date_of_comm').value = document.getElementById("ACTUAL_DATE_COMMENCEMENT"+rid).value;
	document.getElementById('date_of_compl').value = document.getElementById("acompdate"+rid).innerHTML;
	document.getElementById("sel_sch").value=document.getElementById("SCH_SNO"+rid).value;;   
}
	function master_process_result(xml_obj)
{
	//  Group master	
	if (sel_type==1)
	{
				// Insert - Group
				if (process_code==1)
				{
					var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
					var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
					if (parseInt(rows)==1)
					{  
						alert("Record Succesfully Saved  ")
						document.getElementById("desc").value="";
						master(1,3);
					}  
				}
				//  Edit - Group
				else if (process_code==2)
				{
					var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
					var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
					if (parseInt(rows)>=1)
					{  
						alert("Record Succesfully Updated  ")
						document.getElementById("desc").value="";
						master(1,3);
					}					
				}
				//Delete  - Group
				else if (process_code==4)
				{
					var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
					var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
					if (parseInt(rows)>=1)
					{  
						alert("Record Succesfully Deleted  ")
						document.getElementById("desc").value="";
						master(1,3);
					}				
				}
				// List   - Group				
				else if (process_code==3)
				{
						var cc="#CEF6E3";
						var c=""; 
						var tbody = document.getElementById("edata");
						var table = document.getElementById("etable");
						var t=0;
						for(t=tbody.rows.length-1;t>=0;t--)
						{
							tbody.deleteRow(0);
						}
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;						
						document.getElementById("rowcount").value=len;						
						for (var i=0;i<len;i++)
					 	{ 
							 var GROUP_SNO=bR.getElementsByTagName("GROUP_SNO")[i].firstChild.nodeValue;
							 var GROUP_DESC=bR.getElementsByTagName("GROUP_DESC")[i].firstChild.nodeValue;
							 
							 
							 var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","",c);
							 
							 var SNO_cell=cell("TD","input","hidden","GROUP_SNO"+(i+1),GROUP_SNO,2,"tdText","","","2%","","","");
							 var Chk_cell=cell("TD","A","","chkGROUP_SNO"+(i+1),"EDIT",2,"tdText","javascript:vset("+(i+1)+")","","2%","","","");
							 var NAME_cell=cell("TD","label","","GROUP_DESC"+(i+1),GROUP_DESC,0,"",""," ","","left","","");
							 
							 new_row.appendChild(SNO_cell);							 
							 new_row.appendChild(Chk_cell);
							 new_row.appendChild(NAME_cell);							 
							 tbody.appendChild(new_row);
					 	}
				}
        }
		//  Main item Master
		else if (sel_type==2)
        {			
			//  Insert -  Main item  	
		        	if (process_code==1)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						if (parseInt(rows)==1)
						{
							alert("Record Succesfully Saved  ")
							document.getElementById("desc").value="";
							master(2,3);
						}
					}
		        	//  Update -  Main item  
		        	else if (process_code==2)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						if (parseInt(rows)>=1)
						{  
							alert("Record Succesfully Updated  ")
							document.getElementById("desc").value="";
							window.location.reload()
							master(2,3);
							document.getElementById("desc").style.background="#ffffff";
						}
					}
		        	//  List -  Main item  
		        	else if (process_code==3)
					{
		        			var cc="#CEF6E3";
		        			var c=""; 
							var tbody = document.getElementById("edata");
							var table = document.getElementById("etable");
							var t=0;
							for(t=tbody.rows.length-1;t>=0;t--)
							{
								tbody.deleteRow(0);
							}
							var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
							var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;;
							document.getElementById("rowcount").value=len;
							for (var i=0;i<len;i++)
						 	{ 
								if (i%2==0) c=cc;else c=""; 
								 var MAIN_ITEM_SNO=bR.getElementsByTagName("MAIN_ITEM_SNO")[i].firstChild.nodeValue;
								 var MAIN_ITEM_DESC=bR.getElementsByTagName("MAIN_ITEM_DESC")[i].firstChild.nodeValue;
								 var GROUP_DESC=bR.getElementsByTagName("GROUP_DESC")[i].firstChild.nodeValue;
								 var GROUP_SNO=bR.getElementsByTagName("GROUP_SNO")[i].firstChild.nodeValue;							
								 var APPLY_CENTAGE=bR.getElementsByTagName("APPLY_CENTAGE")[i].firstChild.nodeValue;
								 var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","",c);
								 var SNO_cell=cell("TD","input","hidden","MAIN_ITEM_SNO"+(i+1),MAIN_ITEM_SNO,2,"tdText","","","2%","","","");
								 var Chk_cell=cell("TD","A","","chkMAIN_ITEM_SNO"+(i+1),"EDIT",2,"","javascript:set("+GROUP_SNO+","+(parseInt(i)+1)+")","","12%","","","");
								 var NAME_cell=cell("TD","label","","MAIN_ITEM_DESC"+(i+1),MAIN_ITEM_DESC,0,"",""," ","","left","","");
								  
								 if (parseInt(APPLY_CENTAGE)==0 || APPLY_CENTAGE=="" || APPLY_CENTAGE=="null")
									 APPLY_CENTAGE="n"; 
								 else
									 APPLY_CENTAGE="y";
								 var APPLY_CENTAGE_cell=cell("TD","input","checkbox","chkAPPLY_CENTAGE"+(i+1),APPLY_CENTAGE,2,"tdText","","","","center","","");
								 var GROUP_DESC_cell=cell("TD","label","","GROUP_DESC"+(i+1),GROUP_DESC,0,"","","#readonly","25%","left","","");
								 new_row.appendChild(SNO_cell);
								 
								 new_row.appendChild(Chk_cell);
								 new_row.appendChild(GROUP_DESC_cell);
								 new_row.appendChild(NAME_cell); 
								 //new_row.appendChild(APPLY_CENTAGE_cell);
								 tbody.appendChild(new_row);
						 	}  
					
					}
		        	//  Delete -  Main item  
		        	else if (process_code==4)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						if (parseInt(rows)>=1)
						{  
							alert("Record Succesfully Deleted  ")
							document.getElementById("desc").value="";
							master(2,3);
							document.getElementById("desc").style.background="#ffffff";
						}
						else
						{
							alert("Warning : Record Not Succesfully Deleted  ")
							master(2,3);
							document.getElementById("desc").style.background="#ffffff";
						}
					}
        } 
		//  Sub item master
		else if (sel_type==3)
    	{
		        	//Insert - Sub item
					if (process_code==1)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						if (parseInt(rows)==1)
						{
							alert("Record Succesfully Saved  ")
							document.getElementById("desc").value="";
							master(3,3);
							document.getElementById("desc").style.background="#ffffff";
						}
					}
					//Update - Sub item
					else if (process_code==2)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						if (parseInt(rows)>=1)
						{  
							alert("Record Succesfully Updated  ")
							document.getElementById("desc").value="";
							master(3,3);
							document.getElementById("desc").style.background="#ffffff";
						}
						
					}
					//List - Sub item
					else if (process_code==3) 
 					{
							var cc="#CEF6E3";
							var c=""; 
							var tbody = document.getElementById("edata");
							var table = document.getElementById("etable");
							var t=0;
							for(t=tbody.rows.length-1;t>=0;t--)
							{
								tbody.deleteRow(0);
							}
							var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
							var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
							for (var i=0;i<len;i++)
						 	{  
								 if (i%2==0) c=cc;else c=""; 
								 var SUB_ITEM_SNO=bR.getElementsByTagName("SUB_ITEM_SNO")[i].firstChild.nodeValue;
								 var SUB_ITEM_DESC=bR.getElementsByTagName("SUB_ITEM_DESC")[i].firstChild.nodeValue;
								 var MAIN_ITEM_DESC=bR.getElementsByTagName("MAIN_ITEM_DESC")[i].firstChild.nodeValue;
								 var MAIN_ITEM_SNO=bR.getElementsByTagName("MAIN_ITEM_SNO")[i].firstChild.nodeValue;
								 var SHORT_DESC=bR.getElementsByTagName("SUB_ITEM_SDESC")[i].firstChild.nodeValue;
								 var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","",c);
								 var SNO_cell=cell("TD","input","hidden","SUB_ITEM_SNO"+(i+1),SUB_ITEM_SNO,2,"","","","2%","","","");
								 var Chk_cell=cell("TD","A","","chkSUB_ITEM_SNO"+(i+1),"EDIT",0,"","javascript:set("+MAIN_ITEM_SNO+","+(parseInt(i)+1)+")","","2%","","","");
								 var NAME_cell=cell("TD","label","","SUB_ITEM_DESC"+(i+1),SUB_ITEM_DESC,0,"",""," ","","left","","");
								 var APPLY_CENTAGE=bR.getElementsByTagName("APPLY_CENTAGE")[i].firstChild.nodeValue;
								 var MAIN_ITEM_DESC_cell=cell("TD","label","","MAIN_ITEM_DESC"+(i+1),MAIN_ITEM_DESC,0,"",""," ","","left","","");
								 var SHORT_DESC_cell=cell("TD","label","","SHORT_DESC"+(i+1),SHORT_DESC,0,"",""," ","","left","","");
								 
								 if (parseInt(APPLY_CENTAGE)==0 || APPLY_CENTAGE=="" || APPLY_CENTAGE=="null")
									 APPLY_CENTAGE="n"; 
								 else
									 APPLY_CENTAGE="y";
								 var APPLY_CENTAGE_cell=cell("TD","input","checkbox","chkAPPLY_CENTAGE"+(i+1),APPLY_CENTAGE,2,"tdText","","","","center","","");

								 new_row.appendChild(SNO_cell);
								 new_row.appendChild(Chk_cell);
								 new_row.appendChild(MAIN_ITEM_DESC_cell);								
								 new_row.appendChild(NAME_cell);
								 new_row.appendChild(SHORT_DESC_cell);	
								 new_row.appendChild(APPLY_CENTAGE_cell);
								 tbody.appendChild(new_row);
						 	}
					
					}
		        	
					//Delete - Sub item
		        	else if (process_code==4)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						if (parseInt(rows)>=1)
						{  
							alert("Record Succesfully Deleted  ")
							document.getElementById("desc").value="";
							master(3,3);
							document.getElementById("desc").style.background="#ffffff";
						}
						else
						{
							alert("Warning : Record Not Succesfully Deleted  ")
							master(3,3);
							document.getElementById("desc").style.background="#ffffff";
						}
					}
        	
    	}else  if (sel_type==4)
    	{
    		//   centage master 
    		if (process_code==1)
    		{
    			var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
    			var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
    			var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
    			if (parseInt(rows)==1)
    			{  
    				alert(msg);
    				document.getElementById("centage_Rate").value="";
    				document.getElementById("centage_wef").value="";
    				document.getElementById("status").selectedIndex="";
    				
    				master(4,3);
    			}
    			else
    				{
    				alert(msg);
    				}

    		}
    		// Edit - centage
    		else if (process_code==2)
    		{
    			var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
    			var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
    			var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
    			if (parseInt(rows)>0)
    			{  
    				alert(msg);
    				document.getElementById("centage_Id").value="";
    				document.getElementById("centage_Rate").value="";
    				document.getElementById("centage_wef").value="";
    				document.getElementById("status").selectedIndex="";
    				master(4,3);
    				document.getElementById("cmdadd").disabled=false;
    				document.getElementById("cmdupdate").disabled=true;
    			}
    			else if(parseInt(rows)==0)
    			{
    					alert(msg);
    					document.getElementById("cmdadd").disabled=false;
        				document.getElementById("cmdupdate").disabled=true;
    			}

    		}
    		//List load - centage
    		else if (process_code==3)
    		{
    			var tbody = document.getElementById("edata");
    			var table = document.getElementById("etable");
    			var t=0;
    			for(t=tbody.rows.length-1;t>=0;t--)
    			{
    				tbody.deleteRow(0);
    			}
    			var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
    			var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
    			document.getElementById("rowcount").value=len;

    			for (var i=0;i<len;i++)
    			{ 
    				var CENTAGE_SNO=bR.getElementsByTagName("CENTAGE_SNO")[i].firstChild.nodeValue;
    				var ACTIVE_STATUS=bR.getElementsByTagName("ACTIVE_STATUS")[i].firstChild.nodeValue;
//    				if(ACTIVE_STATUS=="A")
//    					ACTIVE_STATUS="Active";
    				var CENTAGE=bR.getElementsByTagName("CENTAGE")[i].firstChild.nodeValue;
    				var CENTAGE_WEF=bR.getElementsByTagName("CENTAGE_WEF")[i].firstChild.nodeValue;
    				
    				var sch_name=xmlValue(bR, "SCH_NAME",i);
    				
    				
    				
    				
    				var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
    				var j=parseInt(i+1);
    				var edit_cell=cell("TD","A","","CENTAGE_SNOED"+(i+1),"Edit",2,"lbl","javascript:ed("+j+")","","","","","");
    				var SNO_cell=cell("TD","input","hidden","CENTAGE_SNO"+(i+1),CENTAGE_SNO,2,"tdText","","","","","","");
    				var centage_cell=cell("TD","label","","CENTAGE"+(i+1),CENTAGE,2,"tdText",""," ","","","","");
    				var wef_cell=cell("TD","label","","CENTAGE_WEF"+(i+1),CENTAGE_WEF,2,"tdText",""," ","","","","");
    				var stat_cell=cell("TD","label","","ACTIVE_STATUS"+(i+1),ACTIVE_STATUS,2,"tdText","","","","","","");
    				var sch_name_cell=cell("TD","label","","sch_name"+(i+1),sch_name,2,"tdText","","","","","","");
    				new_row.appendChild(edit_cell);
    				new_row.appendChild(sch_name_cell);
    				new_row.appendChild(SNO_cell);
    				new_row.appendChild(centage_cell);
    				new_row.appendChild(wef_cell);
    				new_row.appendChild(stat_cell);
    				tbody.appendChild(new_row);
    			}
    		}
    	}
    	else if (sel_type==5)
        {			
			// Scheme Details
			
		        	if (process_code==1)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						alert(msg)
						try {
							var f=document.f1;
							var default_id='sch_sno';
								
							frm_clear(f,default_id);
						}catch(e)
						{    
								  alert("Type ---" + sel_type +" and Process Code " + process_code+" ###");
						}
						 master(5,3); 
						
					}else if (process_code==7)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						alert(msg)  
						master(5,3);
					}
		        	else if (process_code==2)
					{
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
						var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
						
						
							alert(msg)
						try {
							var f=document.f1;
							var default_id='sch_sno';								
							frm_clear(f,default_id);
						}catch(e)
						{    
								  alert("Type ---" + sel_type +" and Process Code " + process_code+" ###");
						}
							master(5,3);
						
					}
		        	else if (process_code==3)
		    		{
		    			var tbody = document.getElementById("edata");
		    			var table = document.getElementById("etable");
		    			var t=0;
		    			for(t=tbody.rows.length-1;t>=0;t--)
		    			{
		    				tbody.deleteRow(0);
		    			}
		    			var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
		    			var msg=bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
		    			var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		    			document.getElementById("rowcount").value=len;
		    			 
		    			for (var i=0;i<len;i++)
		    			{ 
		    				  
		    				var SCH_DETAILS_SNO=xmlValue(bR, "SCH_DETAILS_SNO", i);
		    				var sch_name=xmlValue(bR, "sch_name", i);
		    				 var QTY_DESIGN= xmlValue(bR, "QTY_DESIGN", i);
		    				var SCH_SNO=xmlValue(bR, "SCH_SNO", i);
		    				// Date : 22/12/2011 		    				
		    				var YEAR_OF_COMMISSION=xmlValue(bR, "YEAR_OF_COMMISSION", i);
		    				var DESIGN_PUMPING_HOURS=xmlValue(bR, "DESIGN_PUMPING_HOURS", i);
		    				var TOTAL_NO_STAFF=xmlValue(bR, "TOTAL_NO_STAFF", i);
		    				var TOTAL_NO_HABITATION=xmlValue(bR, "TOTAL_NO_HABITATION", i);
		    				var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
		    				var j=parseInt(i+1);
		    				var row_cell=cell("TD","label","","row"+(i+1),(i+1),2,"lbl","javascript:ed_qty("+j+")","","5%","","","");
		    				var edit_cell=cell("TD","A","","SCH_DETAILS_SNO"+(i+1),"Edit",2,"lbl","javascript:ed_qty("+j+")","","5%","","","");
		    				var sch_name_cell=cell("TD","label","","sch_name"+(i+1),sch_name,2,"tdText","","","30%","left","","");
		    				var sch_sno_cell=cell("TD","input","hidden","SCH_SNO"+(i+1),SCH_SNO,2,"tdText","","","","","","");
		    				var SCH_DETAILS_SNO_cell=cell("TD","input","hidden","SCH_DETAILS_SNO_key"+(i+1),SCH_DETAILS_SNO,2,"tdText","","","","","","");
		    				var QTY_DESIGN_cell=cell("TD","label","","QTY_DESIGN"+(i+1),QTY_DESIGN+"",2,"tdText",""," ","10%","right","","");
		    				// Date : 22/12/2011 
		    				var YEAR_OF_COMMISSION_cell=cell("TD","label","","YEAR_OF_COMMISSION"+(i+1),YEAR_OF_COMMISSION,2,"tdText",""," ","10%","","","");		    					    				
		    				var DESIGN_PUMPING_HOURS_cell=cell("TD","label","","DESIGN_PUMPING_HOURS"+(i+1),DESIGN_PUMPING_HOURS,2,"tdText",""," ","10%","","","");
		    				var TOTAL_NO_STAFF_cell=cell("TD","label","","TOTAL_NO_STAFF"+(i+1),TOTAL_NO_STAFF,2,"tdText",""," ","10%","","","");
		    				var TOTAL_NO_HABITATION_cell=cell("TD","label","","TOTAL_NO_HABITATION"+(i+1),TOTAL_NO_HABITATION,2,"tdText",""," ","10%","","","");
		    				new_row.appendChild(sch_sno_cell);new_row.appendChild(SCH_DETAILS_SNO_cell);
		    				new_row.appendChild(row_cell);
		    				new_row.appendChild(edit_cell);
		    				new_row.appendChild(sch_name_cell);
		    				new_row.appendChild(QTY_DESIGN_cell);
		    				// Date : 22/12/2011 
		    				new_row.appendChild(YEAR_OF_COMMISSION_cell);
		    				new_row.appendChild(DESIGN_PUMPING_HOURS_cell);
		    				new_row.appendChild(TOTAL_NO_STAFF_cell);
		    				new_row.appendChild(TOTAL_NO_HABITATION_cell);
		    				
		    				tbody.appendChild(new_row);
		    			}
		    		}
        }else if (sel_type==7)
	{
				//   Account Group master 
				if (process_code==1)
				{
					var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
					var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
					if (parseInt(rows)==1)
					{  
						alert("Record Succesfully Saved  ")
						document.getElementById("desc").value="";
						master(7,3);
					}  
				}
				// Edit - Group
				else if (process_code==2)
				{
					var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
					var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
					if (parseInt(rows)>=1)
					{  
						alert("Record Succesfully Updated  ")
						document.getElementById("desc").value="";
						master(7,3);
					}
					
				}
				//Delete  - Group
				else if (process_code==4)
				{
					var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
					var rows=bR.getElementsByTagName("rows")[0].firstChild.nodeValue;
					if (parseInt(rows)>=1)
					{  
						alert("Record Succesfully Deleted  ")
						document.getElementById("desc").value="";
						master(7,3);
					} 
					
				}
				// List  - Group
				
				else if (process_code==3)
				{
				
						var tbody = document.getElementById("edata");
						var table = document.getElementById("etable");
						var t=0;
						for(t=tbody.rows.length-1;t>=0;t--)
						{
							tbody.deleteRow(0);
						}
						var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
						var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
						
						document.getElementById("rowcount").value=len;
						
						for (var i=0;i<len;i++)
					 	{ 
							 var GROUP_SNO=bR.getElementsByTagName("ACC_GROUP_SNO")[i].firstChild.nodeValue;
							 var GROUP_DESC=bR.getElementsByTagName("ACC_GROUP_DESC")[i].firstChild.nodeValue;
							 var new_row=cell("TR","","","row"+(i+1),(i+1),2,"","","","","","","");
							 var SNO_cell=cell("TD","input","hidden","ACC_GROUP_SNO"+(i+1),GROUP_SNO,2,"tdText","","","2%","","","");
							 
							 var Chk_cell=cell("TD","A","","chkGROUP_SNO"+(i+1),"EDIT",2,"tdText","javascript:vset("+(i+1)+")","","2%","","","");
							 var NAME_cell=cell("TD","label","","GROUP_DESC"+(i+1),GROUP_DESC,0,"",""," ","","left","","");
							 new_row.appendChild(SNO_cell);
							 
							 new_row.appendChild(Chk_cell);
							 new_row.appendChild(NAME_cell);
							 
							 tbody.appendChild(new_row);
					 	}
				}
        }
}
	function master_load_url(input_value,processcode)
{
	url="../../../../../ame_master";
	var xmlobj2=createObject();
	xmlobj2.open("POST",url,true);
	var params="";
	xmlobj2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlobj2.setRequestHeader("Content-length", params.length);
	xmlobj2.setRequestHeader("Connection", "close");	 
	var params1="process_code="+processcode+"&type=6";;
	xmlobj2.onreadystatechange=function()
    {	 
		master_load_process(xmlobj2,input_value);
    }      
    xmlobj2.send(params1);
}
	function master_load_process(xml_obj2,input_value)
{
	
	if (xml_obj2.readyState==4)
	 {	   
		 
		if (xml_obj2.status==200)
	    {
			 master_load(xml_obj2,input_value);
	    }
	 }
}
	function master_load( xml_obj2,input_value)
{
	 
			var bR=xml_obj2.responseXML.getElementsByTagName("response")[0];
			var len = bR.getElementsByTagName("sno").length;
		 	var status = bR.getElementsByTagName("status")[0].firstChild.nodeValue;
			 
			 
			for (i = 0; i < len; i++) {
				var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
				var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
				addOption(document.getElementById(input_value), name, sno)
			}
			
		 
}
	function ed(rid)
{
	var i=0;
	var len = document.getElementById("sch_sno").options.length;
	var selsch_name=document.getElementById("sch_name"+rid).innerHTML;
	
	for (i = 0; i < len; i++) 
	{
	   
		 var optn = document.getElementById("sch_sno").options[i];
		 var value=optn.text;		
		 
		 	if (selsch_name==value) 
	     	 {
		 		document.getElementById("sch_sno").options[i].selected = true;
		 		    
			  
	     	 }  
		 	value="";
	}    
	document.getElementById("cmdadd").disabled=true;
	document.getElementById("cmdupdate").disabled=false;
	document.getElementById('centage_Id').value = document.getElementById("CENTAGE_SNO"+rid).innerHTML;
	document.getElementById('centage_Rate').value = document.getElementById("CENTAGE"+rid).innerHTML;
	document.getElementById('centage_wef').value = document.getElementById("CENTAGE_WEF"+rid).innerHTML;  
	document.getElementById('status').value = document.getElementById("ACTIVE_STATUS"+rid).innerHTML;  
}
	function ed_qty(rid)
{
	document.getElementById("cmdadd").disabled=true;
	document.getElementById("cmdupdate").disabled=false;
	document.getElementById('SCH_DETAILS_SNO').value = document.getElementById("SCH_DETAILS_SNO"+rid).innerHTML;
	document.getElementById('key_value').value=document.getElementById("SCH_DETAILS_SNO_key"+rid).value;
	
	document.getElementById('sch_sno').value = document.getElementById("SCH_SNO"+rid).value;  
	document.getElementById('sch_qty').value = document.getElementById("QTY_DESIGN"+rid).innerHTML;
	
	// 22/12/2011
	
	document.getElementById('yearcomm').value = document.getElementById("YEAR_OF_COMMISSION"+rid).innerHTML;  
	document.getElementById('pumphours').value = document.getElementById("DESIGN_PUMPING_HOURS"+rid).innerHTML;  
	document.getElementById('noofstaff').value = document.getElementById("TOTAL_NO_STAFF"+rid).innerHTML;  
	document.getElementById('noofhab').value = document.getElementById("TOTAL_NO_HABITATION"+rid).innerHTML;  
	
}
	function decimalFormat(input,nodigit)
{
	var res="0";
	var str=new String(input);
	var index=str.indexOf(".", 0);
	if (index==0)
	{
		res="0"+input;
	}
	return res;
}
