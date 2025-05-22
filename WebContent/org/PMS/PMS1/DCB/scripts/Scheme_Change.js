var process_code=0;
 function div_load (process_code){
	var input_value="div";
	 var obj=createObject();
	var url="../../../../../Scheme_Change?process_code="+process_code;					 
	obj.open("GET",url,true);	
	obj.onreadystatechange = function()
	 {
		 load_(obj,input_value,process_code);  	
	 }
	obj.send(null);
	}       
 function  load_(obj_,input_value,process_code)
{

		if(obj_.readyState==4)  
		{  
			 if(obj_.status==200)
			{  
				var bR = obj_.responseXML.getElementsByTagName("result")[0];
				var len = bR.getElementsByTagName("sno").length;
				document.getElementById(input_value).options.length = 0;
				if (len > 1) 
				{  
						var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
						var temp=0;
						for (i = 0; i < len; i++) 
		   		 	    { 
							var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
							var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
							addOption(document.getElementById(input_value), name, sno)
							temp=sno;
						}
		
					}
			}
		}
		 	 
}   
 function div_sch(process_code,a,row)
{
	var input_value="div";
	var div=a.value;
	var url="../../../../../Scheme_Change?process_code="+process_code+"&div="+div;
	 var obj=createObject();
	obj.open("GET",url,true);	
	obj.onreadystatechange = function()
	 {
		div_sch_load_(obj,'div_sch'+row,process_code,row);  	
	 }  
	obj.send(null);
}
 function div_sch_load_(obj_,input_value,process_code,controlrow)
{
 
 
		var row=document.getElementById("totalben").value;
		if(obj_.readyState==4)  
		{  
			 if(obj_.status==200)
			{  
				   var bR = obj_.responseXML.getElementsByTagName("result")[0];
					var len = bR.getElementsByTagName("sno").length;
					document.getElementById(input_value).options.length = 0;
					if (len > 1) 
					{  
							var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
							var temp=0;
							for (i = 0; i < len; i++) 
			   		 	    { 
								var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
								var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
								addOption(document.getElementById(input_value), name, sno)
								temp=sno;
							}
						}
			}
		}
}
 function sub_div_load(process_code,row)
{
	var input_value="div";
	var div=document.getElementById("DV"+row).value;
	var url="../../../../../Scheme_Change?process_code="+process_code+"&div="+div;
	 var obj=createObject();
	obj.open("GET",url,true);	
 
	obj.onreadystatechange = function()
	 {
		sub_div_load_(obj,input_value,process_code,row);  	
	 }  
	obj.send(null);
}  
 function sub_div_load_(obj_,input_value,process_code,controlrow)
{
		if(obj_.readyState==4)  
		{  
			 if(obj_.status==200)
			{  
				 try
				 {
						   var input_value="sub"+controlrow;
						    var bR = obj_.responseXML.getElementsByTagName("result")[0];
							var len = bR.getElementsByTagName("sno").length;
							document.getElementById(input_value).options.length = 0;
							if (len > 1) 
							{  
									var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
									var temp=0;
									for (i = 0; i < len; i++) 
					   		 	    { 
										var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
										var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
										addOption(document.getElementById(input_value), name, sno)
										temp=sno;
									}
								}
				 }catch(e) {}
			}
		}   
}
 function sch_load(process_code,input_value)
{
 document.getElementById('but1').disabled = true;
	var url="";
 if (process_code==1)
 {
	 url="../../../../../SourceChange?process_code="+process_code;
 }
 if (process_code==2)
 {
	var sch=document.getElementById("sch").value;
	var BEN_TYPE_ID=document.getElementById("BEN_TYPE_ID").value;
	url="../../../../../Scheme_Change?sch="+sch+"&process_code="+process_code+"&BEN_TYPE_ID="+BEN_TYPE_ID;
 }
	 if (BEN_TYPE_ID!=0)
	 {
		  var obj=createObject();
		  obj.open("GET",url,true);
			
			obj.onreadystatechange = function()
			{
				load_sch(obj,input_value,process_code);  	
			}  
			obj.send(null);
	 }
}
 function load_sch(obj_,input_value,process_code)
{
	if(obj_.readyState==4)  
	{  
		if(obj_.status==200)
	 	{  
				if (process_code==1)
	    	 	{
					var bR = obj_.responseXML.getElementsByTagName("result")[0];
	   				var len = bR.getElementsByTagName("sno").length;
					document.getElementById(input_value).options.length = 0;
	   				if (len > 1) 
	   				{
			   				var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
			   				 
			   				var temp=0;
			   				for (i = 0; i < len; i++) 
				   				{ 
			   						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
			   						var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
			   						addOption(document.getElementById(input_value), name, sno)
			   						temp=sno;
			   					}
	   					}  
	    	 	}  
				if (process_code==2)
				{
					  var bR = obj_.responseXML.getElementsByTagName("response")[0];
					  var temp;
					  var row="0";
					  var benrow="0";
					  var metsno="0"
					  var subrow="0";
					  try
					  {  
						  var tbody = document.getElementById("data_tbody");
						  var t=0;
						  for(t=tbody.rows.length-1;t>=0;t--)
						  {
								tbody.deleteRow(0);
						  }
						  var benmove=0;
						  var metermove=0;
						  var flag=2;
						  var len=obj_.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
						  if (len==0)
							  alert("No Beneficiary found for selected beneficiary type");
						  for ( var i = 0; i < len; i++) 
						  {
							row++; 
							var new_row=cell("TR","","","");
							var BENEFICIARY_SNO=xmlValue(bR, "BENEFICIARY_SNO", i,1);
							var BENEFICIARY_NAME=xmlValue(bR, "BENEFICIARY_NAME", i,1);
							if (BENEFICIARY_SNO==temp)
						    {
								BENEFICIARY_NAME="";
						    }   
							var BENEFICIARY_SNO_cell="";  
							var METRE_Count_ben="";
						    if (parseInt(BENEFICIARY_SNO)==parseInt(temp))  
						    {
						    	  flag=0;
						    	  var ben_ck_cell=cell("TD","label","label","ss","",2,"","","","","","","");
							      new_row.appendChild(ben_ck_cell);
							      var ben_ck_cell1=cell("TD","label","label","ss","",2,"","","","","","","");
							      new_row.appendChild(ben_ck_cell1);
							      metsno++;
							      metermove++;
							      var ct=document.getElementById("count_cell"+subrow).value;
							      document.getElementById("count_cell"+subrow).value=parseInt(ct)+1;
							      document.getElementById("METRE_count"+subrow).value=metermove;
						    }  
						    else    
						    {  
						    	  flag=1;
								  benrow++;
						    	  metsno=1;  
						    	  metermove=1;
						    	  var BENEFICIARY_NAME_celll=cell("TD","label","label","BENEFICIARY_NAME"+benrow,BENEFICIARY_NAME,2,"","","","","","","");
								      new_row.appendChild(BENEFICIARY_NAME_celll);
						    	  subrow=(i+1);  
						    	  BENEFICIARY_SNO_cell=cell("TD","input","hidden","BENEFICIARY_SNO"+benrow,BENEFICIARY_SNO,2,"","","","","","","");
						    	  new_row.appendChild(BENEFICIARY_SNO_cell);
						    	  var ben_ck_cell=cell("TD","input","checkbox","ben_ck_cell"+benrow,"",2,"","","","","","","");
						    	  new_row.appendChild(ben_ck_cell);
						    	  var count_cell=cell("TD","input","hidden","count_cell"+benrow,"1",2,"","","","","","","");
						    	  new_row.appendChild(count_cell);
						    	    METRE_Count_ben=cell("TD","input","hidden","METRE_count"+benrow,metermove,2,"","","","","","","");
						    } 
						    var METRE_LOCATION=xmlValue(bR, "METRE_LOCATION", i,1);						    
						    var METRE_LOCATION_cell=cell("TD","label","label","METRE_LOCATION"+benrow+""+metsno,METRE_LOCATION,2,"","","","","","","");
						    var METRE_SNO=xmlValue(bR, "METRE_SNO", i,1);						    
						    var METRE_SNO_cell=cell("TD","input","hidden","METRE_SNO"+benrow+""+metsno,METRE_SNO,2,"","","","","","","");  
						    var ck_cell=cell("TD","input","checkbox","locationcheckbox"+benrow+""+metsno,METRE_LOCATION,2,"","","","","","","");
						    new_row.appendChild(METRE_SNO_cell);
						    if (flag==1)
						    	new_row.appendChild(METRE_Count_ben);
						      
						      
						    new_row.appendChild(METRE_LOCATION_cell);
						    new_row.appendChild(ck_cell);
						  //  new_row.appendChild(name_cell);  
			                 tbody.appendChild(new_row);
			                 temp=BENEFICIARY_SNO;
						}  
					  }catch(e)  
					  {
						  
						  
					  }   
					    document.getElementById("totallocal").value=row;
						document.getElementById("totalben").value=benrow;
				}	
	 	}
	}
}
 function change(process_code)
{
	var row=document.getElementById("bencount").value;
	var ss="";
	var subdiv="",sch="",dv="";
	var meter_sno="";
	
		var verify_flag=0;
		var ben="";
		ben+="&row="+row;
		var effective_month=document.getElementById("pmonth").value;
		var effective_year=document.getElementById("pyear").value;
	 
		var orderno=document.getElementById("orderno").value;
		for (i=1;i<=row;i++)
		{
				var bensno=document.getElementById("bensno"+i).value;
				ben+="&bensno"+i+"="+bensno;
				var totallocal=document.getElementById("metcount"+i).value;
				var action_flag=document.getElementById("action_flag"+i).value;
				ben+="&metrow"+i+"="+totallocal;  
				for (j=1;j<=totallocal;j++)
				{
					var metsno=document.getElementById("metsno"+i+""+j).value;
					var subdiv=document.getElementById("sub"+i+""+j).value;
					var div_sh=document.getElementById("div_sch"+i+""+j).value;
					var dv=document.getElementById("DV"+i+""+j).value;
					ben+="&metsno"+i+""+j+"="+metsno;
					ben+="&subdiv"+i+""+j+"="+subdiv;
					ben+="&div_sh"+i+""+j+"="+div_sh;
					ben+="&dv"+i+""+j+"="+dv;  
					
					if (div_sh==0 || subdiv==0 || div_sh==0 )
					{ 
						verify_flag=1;
						document.getElementById("chrow"+i).style.backgroundColor="#F7819F";
					} else if (orderno=="" || effective_month==0 || effective_year==0)
					{
						verify_flag=1;	
						document.getElementById("chrow"+i).style.backgroundColor="#FFFFFF";
						document.getElementById("mtd").style.backgroundColor="#F7819F";						
					}else						
					{ 
						verify_flag=0;	
						document.getElementById("chrow"+i).style.backgroundColor="#FFFFFF";
						document.getElementById("mtd").style.backgroundColor="#FFFFFF";	
					}  
				}
		}

		var obj=createObject();
		var url="../../../../../Scheme_Change?effective_month="+effective_month+"&effective_year="+effective_year+"&orderno="+orderno+"&process_code="+process_code+"&bencount="+ss+ben;
	 
		if (verify_flag==0)
		{
			obj.open("GET",url,true);	
			obj.onreadystatechange = function()
			 {
				res(obj);    
			 }
			obj.send(null);		
		}else
		{
			alert("All inputs marked * as mandatory");
		}
}
 function res(obj_)
{
	if(obj_.readyState==4)  
	{  
		if(obj_.status==200)
	 	{  
			 var bR = obj_.responseXML.getElementsByTagName("result")[0];
			 var ben_rec = bR.getElementsByTagName("ben_rec")[0].firstChild.nodeValue;
			 
			 alert("Changes Successfully Completed. ")
	 	}
	}
}
 function move()
{
		var totallocal=document.getElementById("totallocal").value;
		var row=document.getElementById("totalben").value;
		var ss="";
		var meter_sno="";
		var tab="<table width='100%' border='0' cellpadding='2' cellspacing='0'><tr><th align='center'>Beneficiary</th><th align='center'> Meter</th><th align='center'> <font color='red'>*</font>&nbsp;Division</th><th align='center'><font color='red'>*</font>&nbsp;Sub Division</th><th align='center'> <font color='red'>*</font>&nbsp;Scheme</th></tr>";
			tab+="<tr id='chrow0'>";			
		var sdv=document.getElementById("sdv").value;
		var innerrow=0;	
		var ben_count=0;
		var met_count=0;
		var check_count=0;   
		var ben_incr=0;
		var chkrow=0;
		for (i=1;i<=row;i++)
		{
				try
				{
					var c=document.getElementById("count_cell"+i).value;
					var met_count=document.getElementById("METRE_count"+i).value;
					var sel_met_count=0;
 
						if (document.getElementById("ben_ck_cell"+i).checked)
						{
							ben_incr++;
							chkrow++;
							var ben=document.getElementById("BENEFICIARY_NAME"+i).innerHTML;
							var ben_sno=document.getElementById("BENEFICIARY_SNO"+i).value;
							tab+="<input type='hidden' id='bensno"+ben_incr+"' value='"+ben_sno+"' >";
							ben_count++;
							check_count=0;
							innerrow=0;
								for (j=1;j<=c;j++)
								{
									if (document.getElementById("locationcheckbox"+i+""+j).checked)
									{
										sel_met_count++;
										check_count++;
										innerrow++;     
										try
										{
											var str="";  
											var ss="";
											var p=0;
											
										for (p = 0; p < sdv.length; p++)
										{
												var chr = sdv.charAt(p);
												if (chr=='#')		
												{						
													ss+=ben_incr;
												}  
												else
												{
													ss+=chr;
												}
												
										}
									    var  sss="";
										for (p = 0; p < ss.length; p++)
										{
											
												var chr2 = ss.charAt(p);
												if (chr2=='@')	  	
												{						
													sss+=innerrow;
												}    
												else
												{
													sss+=chr2;
												}
										}
										str=sss;   
											tab+="<tr id='chrow"+i+"'><td align='left'>"+ben+" </td>";
										   var metsno=document	.getElementById("METRE_SNO"+i+""+j).value;	
										   var met=document.getElementById("METRE_LOCATION"+i+""+j).innerHTML;
											tab+=" <td align='left'>"+met+"</td><td>"+str+"</td>";
											tab+="<td align='left'><select id=sub"+ben_incr+""+innerrow+"><option value='0'>select</option></select></td>";
											tab+="<td align='left'><select id=div_sch"+ben_incr+""+innerrow+"><option value='0'>select</option></select></td>";
											tab+="</tr>"; 
											tab+="<input type='hidden' id='metsno"+ben_incr+""+innerrow+"' value='"+metsno+"' >";
										}catch(e) {  }     
									}  
								}    
							     var action_flag=0;
							     if (check_count==met_count)
							    	 action_flag=1;
							     else
							    	 action_flag=2;
								 tab+="<input size='10' type='hidden' id='action_flag"+ben_incr+"' value='"+action_flag+"' ><input size='10' type='hidden' id='metcount"+ben_incr+"' value='"+check_count+"' >";
						}
						if (chkrow > 0) 
							document.getElementById('but1').disabled = false;
						else
							document.getElementById('but1').disabled = true;
				
			  } catch(e) {
				  
				  
			  }
			   
		}
		tab+="<input type='hidden' id='bencount' value='"+ben_count+"' >"; 
	document.getElementById("movetodiv").innerHTML=tab;
}
 function ob_fetch()
{  
	    var obj=createObject();
	    var y=document.getElementById("year").value;
	    var m=document.getElementById("month").value;
	    var DV=document.getElementById("DV").value;
		var url="../../../../../Scheme_Change?process_code=11&m="+m+"&y="+y+"&DV="+DV;
		obj.open("GET",url,true);
		
		obj.onreadystatechange = function()
		 {
			ob_fetch_process(obj);
		 }
		obj.send(null);
}
 function ob_fetch_process(obj_)
{
	
	if(obj_.readyState==4)  
	{  
		if(obj_.status==200)
	 	{  
			  var tbody = document.getElementById("ob_data_tbody");
			 var bR = obj_.responseXML.getElementsByTagName("response")[0];
			 var len=obj_.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
			 var row=0;
			 for(t=tbody.rows.length-1;t>=0;t--)
			  {
					tbody.deleteRow(0);
			  }
			 for ( var i = 0; i < len; i++) 
			  {
				row++;
				var new_row=cell("TR","","","");
				var Sl_no_celll=cell("TD","label","label","",row,2,"","","","","","","");
				var BENEFICIARY_NAME=xmlValue(bR, "BENEFICIARY_NAME", i,1);
				var sch_name=xmlValue(bR, "sch_name", i,1);
		    	var BENEFICIARY_NAME_celll=cell("TD","label","label","BENEFICIARY_NAME"+row,BENEFICIARY_NAME,2,"","","","","right","","");
		    	var sch_name_celll=cell("TD","label","label","BENEFICIARY_NAME"+row,BENEFICIARY_NAME,2,"","","","","right","","");
			    var ck_cell=cell("TD","input","checkbox","sch_ck"+row,"",2,"","","","","","","");
			    var OB_MAINT_CHARGES=xmlValue(bR, "OB_MAINT_CHARGES", i,1);	
			    var OB_MAINT_CHARGES_cell=cell("TD","label","label","OB_MAINT_CHARGES_cell"+row,OB_MAINT_CHARGES,2,"","","","","right","","");
			    var APR_CUR_YR_WC=xmlValue(bR, "APR_CUR_YR_WC", i,1);
			    var BENEFICIARY_SNO=xmlValue(bR, "BENEFICIARY_SNO", i,1);
			    var OLD_BENEFICIARY_SNO=xmlValue(bR, "OLD_BENEFICIARY_SNO", i,1);
			    var BENEFICIARY_OB_SNO=xmlValue(bR, "BENEFICIARY_OB_SNO", i,1);
			    var scheme_sno=xmlValue(bR, "SCHEME_SNO", i,1);
			    
			    var ben_hidden=cell("TD","input","hidden","bensno"+row,BENEFICIARY_SNO,2,"","","","","","","");
			    var old_bensno=cell("TD","input","hidden","old_bensno"+row,OLD_BENEFICIARY_SNO,2,"","","","","","","");
			    var obsno=cell("TD","input","hidden","obsno"+row,BENEFICIARY_OB_SNO,2,"","","","","","","");
			    var scheme_sno_cell=cell("TD","input","hidden","scheme_sno"+row,scheme_sno,2,"","","","","","","");

			    
			    
			    var OB_CUR_YR_WC=xmlValue(bR, "OB_CUR_YR_WC", i,1);
			    var OB_YESTER_YR_WC=xmlValue(bR, "OB_YESTER_YR_WC", i,1);
			    var DMD_UPTO_PRV_MTH_WC=xmlValue(bR, "DMD_UPTO_PRV_MTH_WC", i,1);
			    var DMD_UPTO_PRV_MTH_WC_cell=cell("TD","label","label","DMD_UPTO_PRV_MTH_WC"+row,DMD_UPTO_PRV_MTH_WC,2,"","","width: 200;  color: maroon;","","right","","");
			    
			    
			    var APR_CUR_YR_WC_cell=cell("TD","label","label","APR_CUR_YR_WC"+row,APR_CUR_YR_WC,2,"","","","","right","","");

			    var COLN_UPTO_PRV_MTH_MAINT=xmlValue(bR, "COLN_UPTO_PRV_MTH_MAINT", i,1);
			    var COLN_UPTO_PRV_MTH_MAINT_cell=cell("TD","label","label","COLN_UPTO_PRV_MTH_MAINT"+row,COLN_UPTO_PRV_MTH_MAINT,2,"","","","","right","","");
			    
			    
			    var COLN_UPTO_PRV_MTH_YESTER_YR=xmlValue(bR, "COLN_UPTO_PRV_MTH_YESTER_YR", i,1);
			    var COLN_UPTO_PRV_MTH_WC=xmlValue(bR, "OB_FOR_MTH_MAINT_CHARGES", i,1);
			    var COLN_cell=cell("TD","label","label","COLN_cell"+row,parseFloat(COLN_UPTO_PRV_MTH_YESTER_YR)+parseFloat(COLN_UPTO_PRV_MTH_WC),2,"","","","","right","","");
			    
			    var COLN_UPTO_PRV_MTH_YESTER_YR=xmlValue(bR, "COLN_UPTO_PRV_MTH_YESTER_YR", i,1);
			    var COLN_UPTO_PRV_MTH_WC=xmlValue(bR, "OB_FOR_MTH_MAINT_CHARGES", i,1);
			    
			    var OB_FOR_MTH_MAINT_CHARGES=xmlValue(bR, "OB_FOR_MTH_MAINT_CHARGES", i,1);
			    var OB_FOR_MTH_YESTER_YR_WC=xmlValue(bR, "OB_FOR_MTH_YESTER_YR_WC", i,1);
			    var OB_FOR_MTH_CUR_YR_WC=xmlValue(bR, "OB_FOR_MTH_CUR_YR_WC", i,1);
			    var cb_cell=cell("TD","label","label","COLN_cell"+row,parseFloat(OB_FOR_MTH_YESTER_YR_WC)+parseFloat(OB_FOR_MTH_CUR_YR_WC),2,"","","","","right","","");
			    var  but_cell=cell("TD","input","button","but"+row,"Submit",2,"","","","","","onclick","opening_balance_convert("+(i+1)+")");
			    var  newbut_cell=cell("TD","input","button","new"+row,"New Entry",2,"","","","","","","");
			    
			    
		    	  new_row.appendChild(Sl_no_celll);
		    	  new_row.appendChild(BENEFICIARY_NAME_celll);
		    	  new_row.appendChild(sch_name_celll);
		    	  new_row.appendChild(OB_MAINT_CHARGES_cell);
		    	  
		    	  // hidden field add   
		    	  new_row.appendChild(ben_hidden);
		    	  new_row.appendChild(old_bensno);
		    	  new_row.appendChild(obsno);
		    	  new_row.appendChild(scheme_sno_cell);
		    	  
		    	  new_row.appendChild(COLN_UPTO_PRV_MTH_MAINT_cell);
		    	  new_row.appendChild(APR_CUR_YR_WC_cell);
		    	  new_row.appendChild(DMD_UPTO_PRV_MTH_WC_cell);
		    	  new_row.appendChild(COLN_cell);
		    	  new_row.appendChild(cb_cell);
		    	  
		    	  new_row.appendChild(ck_cell);
		    	  new_row.appendChild(but_cell);
		    	  new_row.appendChild(newbut_cell);
				  tbody.appendChild(new_row);
			  }
			 
			 document.getElementById("totalrec").value=row;
			 
	 	}
	}
}  
 function opening_balance_convert(row)
{
	 
	 var url_param="&";
	 var ben="",office="",sch_="",obsno="",old_bensno="";
	 var i=row;
	
	 var y=document.getElementById("year").value;
	 var m=document.getElementById("month").value;
	 if (document.getElementById("sch_ck"+i).checked)
	 {
			 ben=document.getElementById("bensno"+i).value;
			 office=document.getElementById("DV").value
			 sch_=document.getElementById("scheme_sno"+i).value;
			 obsno=document.getElementById("obsno"+i).value;
			 old_bensno=document.getElementById("old_bensno"+i).value;
			 url_param+="&ben"+i+"="+ben;
			 url_param+="&y="+y;
			 url_param+="&m="+m;
			 url_param+="&office="+office;
			 url_param+="&sch_"+i+"="+sch_;
			 url_param+="&obsno"+i+"="+obsno;
			 url_param+="&old_bensno"+i+"="+old_bensno;
	 }
	         url_param+="&row=1";
	
	    var obj=createObject();
	    var url="../../../../../Scheme_Change?process_code=12&bencount="+row+url_param;
		obj.open("GET",url,true);	
		obj.onreadystatechange = function()
		{
			opening_balance_convert_process(obj);
		}
		obj.send(null);
}
 function opening_balance_convert_process(obj_)
 {
	 if(obj_.readyState==4)  
		{  
			if(obj_.status==200)
		 	{

				   var br = obj_.responseXML.getElementsByTagName("result")[0];
				   var benrow=obj_.responseXML.getElementsByTagName("ben_row")[0].firstChild.nodeValue;
				   if (benrow >=1)
				   {
					   alert("Opening Balance Successfully Stored ! ");
				   } 
		 	}
		}
	 
 }
 function rate_fetch_ben_Load()
 {  
 	    var obj=createObject();
 	    var DV=document.getElementById("DV").value;
 		var url="../../../../../Scheme_Change?process_code=14&DV="+DV; 
 		obj.open("GET",url,true);
 		obj.onreadystatechange = function()
 		{
 				rate_fetch_ben_Load_process(obj);
 		}
 		obj.send(null);
 }
 function rate_fetch_ben_Load_process(obj_)
 {
 	
	 	if(obj_.readyState==4)  
	 	{  
	 		if(obj_.status==200)
	 	 	{  
	 			  	var tbody = document.getElementById("ben_load_");
	 			  	var bR = obj_.responseXML.getElementsByTagName("response")[0];
	 			  	var len=obj_.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
	 			  	var row=0;
	 			  	for(t=tbody.rows.length-1;t>=0;t--)
	 			  	{
	 					tbody.deleteRow(0);
	 			  	}
	 			  	for ( var i = 0; i < len; i++) 
	 			  	{  
	 			  		row++;
	 			  		var new_row=cell("TR","","","");
	 			  		var Sl_no_celll=cell("TD","label","label","",row,2,"","","","","","","");
	 			  		var BENEFICIARY_NAME=xmlValue(bR, "BENEFICIARY_NAME", i,1);
	 			  		var BENEFICIARY_NAME_celll=cell("TD","label","label","BENEFICIARY_NAME"+row,BENEFICIARY_NAME,2,"","","","","left","","");
	 			  		var ck=cell("TD","input","checkbox","ck"+row,"",2,"","","","","center","","");
	 			  		var but_cell=cell("TD","input","button","but"+row,"Submit",2,"","","","","","onclick","rate_store_ben_Load("+(i+1)+")");
	 			  		var BENEFICIARY_SNO=xmlValue(bR, "BENEFICIARY_SNO", i,1);
	 			  		var BENEFICIARY_SNO_celll=cell("TD","input","hidden","BENEFICIARY_SNO"+row,BENEFICIARY_SNO,2,"","","","","left","","");  
	 			  			new_row.appendChild(Sl_no_celll);
	 			  			new_row.appendChild(BENEFICIARY_NAME_celll); 
	 			  			new_row.appendChild(BENEFICIARY_SNO_celll);
	 			  			new_row.appendChild(ck);
	 			  			new_row.appendChild(but_cell);
	 			  			tbody.appendChild(new_row);
				  }
	 	 	}
	 	}
 }  
 function rate_store_ben_Load(row)
 {  
 	    var obj=createObject();
 	    var BENEFICIARY_SNO=document.getElementById("BENEFICIARY_SNO"+row).value;
 		var url="../../../../../Scheme_Change?process_code=15&BENEFICIARY_SNO="+BENEFICIARY_SNO; 
 		obj.open("GET",url,true);
 		obj.onreadystatechange = function()
 		{
 			rate_store_ben_Load_process(obj);
 		}
 		obj.send(null);
 }
 function rate_store_ben_Load_process(obj_)
 {
 	if(obj_.readyState==4)  
 	{  
 		if(obj_.status==200)
 	 	{ 
			   var br = obj_.responseXML.getElementsByTagName("result")[0];
			   var benrow=obj_.responseXML.getElementsByTagName("ben_row")[0].firstChild.nodeValue;
			   if (benrow >=1)
			   {
				   alert("Rate Successfully Stored ! ");
			   } 
			   else
			   {
				   alert("Rate Not Successfully Stored ! ");
			   }
 	 	}  
 	}
 }
 