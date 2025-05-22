function div_verfiy()  
{
	var pmonth=document.getElementById("pmonth").value;
	var pyear=document.getElementById("pyear").value;
	var url="";
	url = "../../../../../../count_report_serv?command=dataverificiaton_div&pmonth="+pmonth+"&pyear="+pyear;
	var xmlobj_new=createObject();
	xmlobj_new.open("GET", url, true);  
	xmlobj_new.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	xmlobj_new.send(null);
	xmlobj_new.onreadystatechange = function() 
	{
		div_verfiy_show(xmlobj_new); 
	}
}
function div_select(div,office_name)  
{  
	var pmonth=document.getElementById("pmonth").value;
	var pyear=document.getElementById("pyear").value;
	var url="";
	url = "../../../../../../count_report_serv?command=dataverificiaton_ben&office_id="+div+"&pmonth="+pmonth+"&pyear="+pyear;
	office_name=office_name.replace("@", " ");
	
	document.getElementById("div_name").innerHTML=office_name+"  ( "+div+" ) ";
	var xmlobj_new=createObject();
	xmlobj_new.open("GET", url, true);  
	xmlobj_new.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	xmlobj_new.send(null);
	xmlobj_new.onreadystatechange = function() 
	{
		div_select_show(xmlobj_new); 
	}
}
function div_select_show(xmlobj_new)
{
	try 
	{  
		
		if (xmlobj_new.readyState == 4) 
		{
			if (xmlobj_new.status == 200) 
			{ 
				var bR = xmlobj_new.responseXML.getElementsByTagName("response")[0];
				var len = bR.getElementsByTagName("BENEFICIARY_NAME").length;				
				var tbody = document.getElementById("entred_body2");  
				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				var Head_new_row_1 = cell1("TR", "", "", "row","",2,"","", "", "", "", "", "","");
				var Head_sno_cell_1 = cell1("TD", "label", " ", " " ,"Sl.No", 2, "", "", "", "5%", "center", "", "","");
				var Head_sno_cell = cell1("TD", "label", " ", " " ,"SNo", 2, "", "", "", "5%", "center", "", "","");
				var Head_name_cell = cell1("TD", "label", "", "","Beneficiary Name",2,"label", "", "", "45%", "center", "", "","");
				
				var Head_namesno_cell = cell1("TD", "label", "", "","Beneficiary Sno",2,"label", "", "", "45%", "center", "", "","");
				
				var Head_bentype_cell = cell1("TD", "label", "", "","Beneficiary Type",2,"label", "", "", "45%", "center", "", "","");

				
				var Head_wcdmd2_cell = cell1("TD", "label",  "","", "Actual Demand", 2,"label", "", "", "15%", "center", "", "","");
				var Head_actdmd_cell = cell1("TD", "label", "","", "DCB Posted Demand", 2,"label", "", "", "15%", "center", "", "","");
				var Head_wcdmd3_cell = cell1("TD", "label",  "","", "Demand Difference", 2,"label", "", "", "15%", "center", "", "","");
				var Head_wcdmd4_cell = cell1("TD", "label",  "","", "Collection Difference", 2,"label", "", "", "15%", "center", "", "","");
				
				var Head_wcdmd5_cell = cell1("TD", "label",  "","", "Arrear Demand", 2,"label", "", "", "15%", "center", "", "","");

				
				var Head_fas_tot_collection_cell = cell1("TD", "label", "", "","FAS Total Collection(1)",2,"label", "", "", "15%", "center", "", "","");
				var Head_actual_adj_coll_cell = cell1("TD", "label", "", "","Journal Adjustment(2) ",2,"label", "", "", "15%", "center", "", "","");
				var Head_actual_tot_coll_cell = cell1("TD", "label", "", "","Total Collection(1+2) ",2,"label", "", "", "15%", "center", "", "","");
				var Head_actual_coll_cell = cell1("TD", "label", "", "","Posted Collection ",2,"label", "", "", "15%", "center", "", "","");
				var Head_new_row = cell1("TR", "", "", "row","",2,"","", "", "", "", "", "","");
				Head_new_row.appendChild(Head_sno_cell);
				Head_new_row.appendChild(Head_name_cell);
				
				Head_new_row.appendChild(Head_namesno_cell);
				Head_new_row.appendChild(Head_bentype_cell);

				
				Head_new_row.appendChild(Head_wcdmd2_cell);
				Head_new_row.appendChild(Head_actdmd_cell);
				Head_new_row.appendChild(Head_fas_tot_collection_cell);
				Head_new_row.appendChild(Head_actual_adj_coll_cell);
				Head_new_row.appendChild(Head_actual_tot_coll_cell);		 		
				Head_new_row.appendChild(Head_actual_coll_cell);
				Head_new_row.appendChild(Head_wcdmd3_cell);
				Head_new_row.appendChild(Head_wcdmd4_cell);
				
				Head_new_row.appendChild(Head_wcdmd5_cell);

				
				tbody.appendChild(Head_new_row);
				var tot1=0,tot2=0,tot3=0,tot4=0;
				var net_tot1=0,net_tot2=0,net_tot3=0,net_tot4=0,net_tot5=0,net_tot4_adj=0,net_tot7=0,net_tot6=0,net_tot8=0,net_tot9=0;
				for(var i=0;i<len;i++)
				{  
					var BENEFICIARY_NAME=xmlobj_new.responseXML.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
					var actdmd=xmlobj_new.responseXML.getElementsByTagName("actdmd")[i].firstChild.nodeValue;
					var BENEFICIARY_SNO=xmlobj_new.responseXML.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					
					var BEN_TYPE_DESC=xmlobj_new.responseXML.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
					
					var wcdmd2=isnan(xmlobj_new.responseXML.getElementsByTagName("wcdmd2")[i].firstChild.nodeValue);
					var fas_tot_collection=isnan(xmlobj_new.responseXML.getElementsByTagName("fas_tot_collection")[i].firstChild.nodeValue);
					var fas_collection=isnan(xmlobj_new.responseXML.getElementsByTagName("fas_collection")[i].firstChild.nodeValue);
					var actual_coll=isnan(xmlobj_new.responseXML.getElementsByTagName("actual_coll")[i].firstChild.nodeValue);
					var journal_adj=isnan(xmlobj_new.responseXML.getElementsByTagName("journal_adj")[i].firstChild.nodeValue);
					
					var arrdmd=isnan(xmlobj_new.responseXML.getElementsByTagName("arrdmd")[i].firstChild.nodeValue);

					
					var sno_cell = cell1("TD", "label", " ", " " , (i + 1), 2, "", "", "", "2%", "", "", "","");
					//var name_cell = cell1("TD", "A", BENEFICIARY_NAME, "EDIT", BENEFICIARY_NAME+"("+BENEFICIARY_SNO+")", 2, "","javascript:div_select("+BENEFICIARY_SNO+")", "","45%", "left", "", "");
					 var name_cell = cell1("TD", "label", "", "disvalue"+(i+1),BENEFICIARY_NAME,2,"label", "", "", "90%", "left", "", "","");
					 					 
					 var nsno_cell = cell1("TD", "label", "", "disvalue"+(i+1),BENEFICIARY_SNO,2,"label", "", "", "90%", "left", "", "","");
					 var bentype_cell = cell1("TD", "label", "", "disvalue"+(i+1),BEN_TYPE_DESC,2,"label", "", "", "90%", "left", "", "","");

					 
					 var actdmd_cell = cell1("TD", "label", "", "disvalue"+(i+1),actdmd,2,"label", "", "", "15%", "", "", "","");
					var wcdmd2_cell = cell1("TD", "label", "", "disvalue"+(i+1),wcdmd2,2,"label", "", "", "15%", "", "", "","");
					var fas_collection_cell= cell1("TD", "label", "", "disvalue"+(i+1),fas_collection,2,"label", "", "", "15%", "", "", "","");
					var fas_journal_adj_cell = cell1("TD", "label", "", "journal_adj"+(i+1),journal_adj,2,"label", "", "", "15%", "", "", "","");
					var fas_tot_collection_cell = cell1("TD", "label", "", "disvalue"+(i+1),fas_tot_collection,2,"label", "", "", "15%", "", "", "","");
					var actual_coll_cell = cell1("TD", "label", "", "disvalue"+(i+1),actual_coll,2,"label", "", "", "15%", "", "", "","");
					var journal_adj_cell= cell1("TD", "label", "", "journal_adj"+(i+1),journal_adj,2,"label", "", "", "15%", "", "", "","");
					
					var arrdmd_cell= cell1("TD", "label", "", "arrdmd"+(i+1),arrdmd,2,"label", "", "", "15%", "", "", "","");

					
					var color=""; 
					if (parseFloat(actdmd)==parseFloat(wcdmd2)&&  parseFloat(actual_coll)==parseFloat(fas_tot_collection))
					{
						color="";		 			
					}else  
					{
						color="#CCAA66";     
					}
					var difference1=0,difference2=0;					
					difference1=parseFloat(wcdmd2)-parseFloat(actdmd);
					difference2=parseFloat(fas_tot_collection)-parseFloat(actual_coll);
					var difference1_cell= cell1("TD", "label", "", "difference1_cell"+(i+1),difference1,2,"label", "", "", "15%", "", "", "","");
					var difference2_cell = cell1("TD", "label", "", "difference2_cell"+(i+1),difference2,2,"label", "", "", "15%", "", "", "","");
					
					var new_row = cell1("TR", "", "", "row" +(i+1),(i+1),2,"","", "", "", "", "", "",color);
					new_row.appendChild(sno_cell);
					new_row.appendChild(name_cell);
					
					new_row.appendChild(nsno_cell);
					new_row.appendChild(bentype_cell);


					new_row.appendChild(wcdmd2_cell); 
					new_row.appendChild(actdmd_cell);			
					new_row.appendChild(fas_collection_cell);
					new_row.appendChild(fas_journal_adj_cell);
					new_row.appendChild(fas_tot_collection_cell);
					new_row.appendChild(actual_coll_cell);			
					new_row.appendChild(difference1_cell);
					new_row.appendChild(difference2_cell);
					
					new_row.appendChild(arrdmd_cell);

					
					tbody.appendChild(new_row);
					net_tot1=parseFloat(net_tot1)+parseFloat(wcdmd2);
					net_tot2=parseFloat(net_tot2)+parseFloat(actdmd);
					net_tot3=parseFloat(net_tot3)+parseFloat(fas_collection);
					net_tot8=parseFloat(net_tot8)+parseFloat(journal_adj);
					net_tot4=parseFloat(net_tot4)+parseFloat(fas_tot_collection);
					net_tot5=parseFloat(net_tot5)+parseFloat(actual_coll);
					net_tot6=parseFloat(net_tot6)+parseFloat(difference1);
					net_tot7=parseFloat(net_tot7)+parseFloat(difference2);
					
					net_tot9=parseFloat(net_tot9)+parseFloat(arrdmd);

				}          
				var Footer_new_row = cell1("TR", "", "", "row","",2,"","", "", "", "", "", "","");
				var Footer_empty_cell = cell1("TD", "label", "", "","",2,"label", "", "", "5%", "center", "", "","");
				var Footer_empty_cell1 = cell1("TD", "label", "", "","",2,"label", "", "", "5%", "center", "", "","");
				var Footer_empty_cell2 = cell1("TD", "label", "", "","",2,"label", "", "", "5%", "center", "", "","");

				var Footer_name_cell = cell1("TD", "label", "", "","Total",2,"label", "", "", "45%", "right", "", "","");
				var Footer_wcdmd2_cell = cell1("TD", "label",  "","", net_tot1, 2,"label", "", "", "15%", "center", "", "","");
				var Footer_actdmd_cell = cell1("TD", "label", "","", net_tot2, 2,"label", "", "", "15%", "center", "", "","");
				var Footer_fas_collection_cell = cell1("TD", "label", "", "",net_tot3,2,"label", "", "", "15%", "center", "", "","");
				var Footer_actual_adj_coll_cell = cell1("TD", "label", "", "",net_tot8,2,"label", "", "", "15%", "center", "", "","");
				var Footer_fas_tot_collection_cell = cell1("TD", "label", "", "",net_tot4,2,"label", "", "", "15%", "center", "", "","");
				var Footer_actual_coll_cell = cell1("TD", "label", "", "",net_tot5,2,"label", "", "", "15%", "center", "", "","");
				var Footer_differ1 = cell1("TD", "label", "", "",net_tot6,2,"label", "", "", "15%", "center", "", "","");
				var Footer_differ2 = cell1("TD", "label", "", "",net_tot7,2,"label", "", "", "15%", "center", "", "","");
				var Footer_journal_adj = cell1("TD", "label", "", "",net_tot8,2,"label", "", "", "15%", "center", "", "","");
				
				var Footer_arrdmd = cell1("TD", "label", "", "",net_tot9,2,"label", "", "", "15%", "center", "", "","");

				
				Footer_new_row.appendChild(Footer_empty_cell);
				Footer_new_row.appendChild(Footer_empty_cell1);
				Footer_new_row.appendChild(Footer_empty_cell2);

				Footer_new_row.appendChild(Footer_name_cell); 
				Footer_new_row.appendChild(Footer_wcdmd2_cell);			
				Footer_new_row.appendChild(Footer_actdmd_cell);
				Footer_new_row.appendChild(Footer_fas_collection_cell);
				Footer_new_row.appendChild(Footer_actual_adj_coll_cell);
				Footer_new_row.appendChild(Footer_fas_tot_collection_cell);				
				Footer_new_row.appendChild(Footer_actual_coll_cell);
				Footer_new_row.appendChild(Footer_differ1);
				Footer_new_row.appendChild(Footer_differ2); 
				
				Footer_new_row.appendChild(Footer_arrdmd); 

				
				tbody.appendChild(Footer_new_row);
				
			}
		} 
	}catch(e){}  
}
function div_verfiy_show(xmlobj_new)
{
	try 
	{  
		
		if (xmlobj_new.readyState == 4) 
		{
			if (xmlobj_new.status == 200) 
			{     
				var bR = xmlobj_new.responseXML.getElementsByTagName("response")[0];
				var len = bR.getElementsByTagName("office_name").length;				
				var tbody = document.getElementById("entred_body1");
				for (t = tbody.rows.length - 1; t >= 0; t--) {
					tbody.deleteRow(0);
				}
				var Head_sno_cell = cell1("TD", "label", " ", " " ,"SNo", 2, "", "", "", "5%", "center", "", "","");
				var Head_name_cell = cell1("TD", "label", "", "","Division Name",2,"label", "", "", "45%", "center", "", "","");
				var Head_actdmd_cell = cell1("TD", "label", "","", "Actual Demand", 2,"label", "", "", "15%", "center", "", "","");
				var Head_wcdmd2_cell = cell1("TD", "label",  "","", "DCB Posted Demand", 2,"label", "", "", "15%", "center", "", "","");
				var Head_wcdmd3_cell = cell1("TD", "label",  "","", "Demand Difference", 2,"label", "", "", "15%", "center", "", "","");
				var Head_wcdmd4_cell = cell1("TD", "label",  "","", "Collection Difference", 2,"label", "", "", "15%", "center", "", "","");
				
				var arrdmd = cell1("TD", "label",  "","", "Arrear Demand", 2,"label", "", "", "15%", "center", "", "","");

				
				var Head_fas_tot_collection_cell = cell1("TD", "label", "", "","FAS Collection(1)",2,"label", "", "", "15%", "center", "", "","");
				var Head_actual_adj_coll_cell = cell1("TD", "label", "", "","Journal Adjustment(2) ",2,"label", "", "", "15%", "center", "", "","");
				var Head_actual_tot_coll_cell = cell1("TD", "label", "", "","Total Collection(1+2) ",2,"label", "", "", "15%", "center", "", "","");
				var Head_actual_coll_cell = cell1("TD", "label", "", "","DCB Posted Collection ",2,"label", "", "", "15%", "center", "", "","");
				var Head_new_row = cell1("TR", "", "", "row","",2,"","", "", "", "", "", "","");
				Head_new_row.appendChild(Head_sno_cell);
				Head_new_row.appendChild(Head_name_cell);
				Head_new_row.appendChild(Head_actdmd_cell);
				Head_new_row.appendChild(Head_wcdmd2_cell);
				Head_new_row.appendChild(Head_fas_tot_collection_cell);
				Head_new_row.appendChild(Head_actual_adj_coll_cell);
				Head_new_row.appendChild(Head_actual_tot_coll_cell);
				Head_new_row.appendChild(Head_actual_coll_cell);
				Head_new_row.appendChild(Head_wcdmd3_cell);
				Head_new_row.appendChild(Head_wcdmd4_cell);
				
				Head_new_row.appendChild(arrdmd);
			
				
				tbody.appendChild(Head_new_row);
				var net_tot1=0,net_tot2=0,net_tot3=0,net_tot4=0,net_tot5=0,net_tot4_adj=0,net_tot7=0,net_tot6=0,net_tot8=0;
				for(var i=0;i<len;i++)
				{  
					var office_name=new String(xmlobj_new.responseXML.getElementsByTagName("office_name")[i].firstChild.nodeValue);
					var office_name1=office_name.replace(", ", "");
					 office_name1=office_name1.replace(" ", "@");  
					var actdmd=isnan(xmlobj_new.responseXML.getElementsByTagName("actdmd")[i].firstChild.nodeValue);
					var office_id=isnan(xmlobj_new.responseXML.getElementsByTagName("office_id")[i].firstChild.nodeValue);
					var wcdmd2=isnan(xmlobj_new.responseXML.getElementsByTagName("wcdmd2")[i].firstChild.nodeValue);
					var fas_tot_collection=isnan(xmlobj_new.responseXML.getElementsByTagName("fas_tot_collection")[i].firstChild.nodeValue);
					var fas_collection=isnan(xmlobj_new.responseXML.getElementsByTagName("fas_collection")[i].firstChild.nodeValue);
					var actual_coll=isnan(xmlobj_new.responseXML.getElementsByTagName("actual_coll")[i].firstChild.nodeValue);
					var journal_adj=isnan(xmlobj_new.responseXML.getElementsByTagName("journal_adj")[i].firstChild.nodeValue);
					
					var arrdmd=isnan(xmlobj_new.responseXML.getElementsByTagName("arrdmd")[i].firstChild.nodeValue);

					
					var difference1=0,difference2=0;
					difference1=parseFloat(wcdmd2)-parseFloat(actdmd);
					difference2=parseFloat(fas_tot_collection)-parseFloat(actual_coll);
					var sno_cell = cell1("TD", "label", " ", " " ,+ (i + 1), 2, "", "", "", "5%", "", "", ""); 
					var name_cell = cell1("TD", "A", office_name, "EDIT", office_name, 2, "","javascript:div_select("+office_id+",'"+office_name1+"')", "","45%", "left", "", "","");
					//var name_cell = cell1("TD", "label", "", "disvalue"+(i+1),office_name,2,"label", "", "", "90%", "left", "", "");
					var actdmd_cell = cell1("TD", "label", "", "disvalue"+(i+1),actdmd,2,"label", "", "", "15%", "", "", "","");
					var wcdmd2_cell = cell1("TD", "label", "", "disvalue"+(i+1),wcdmd2,2,"label", "", "", "15%", "", "", "","");
					var fas_tot_collection_cell = cell1("TD", "label", "", "disvalue"+(i+1),fas_tot_collection,2,"label", "", "", "15%", "", "", "","");
					var fas_collection_cell= cell1("TD", "label", "", "disvalue"+(i+1),fas_collection,2,"label", "", "", "15%", "", "", "","");
					var journal_adj_cell= cell1("TD", "label", "", "journal_adj"+(i+1),journal_adj,2,"label", "", "", "15%", "", "", "","");
					var actual_coll_cell = cell1("TD", "label", "", "disvalue"+(i+1),actual_coll,2,"label", "", "", "15%", "", "", "","");
					var difference1_cell= cell1("TD", "label", "", "difference1_cell"+(i+1),difference1,2,"label", "", "", "15%", "", "", "","");
					var difference2_cell = cell1("TD", "label", "", "difference2_cell"+(i+1),difference2,2,"label", "", "", "15%", "", "", "","");
					
					var arrdmd_cell = cell1("TD", "label", "", "arrdmd"+(i+1),arrdmd,2,"label", "", "", "15%", "", "", "","");

					
					var color="";
					if (parseFloat(actdmd)==parseFloat(wcdmd2)&&  parseFloat(actual_coll)==parseFloat(fas_tot_collection))
					{
						color="";					
					}else    
					{ 
						 
						color="#CCAA66";     
					}
					var new_row = cell1("TR", "", "", "row" +(i+1),(i+1),2,"","", "", "", "", "", "",color);
					new_row.appendChild(sno_cell);
					new_row.appendChild(name_cell);
					new_row.appendChild(wcdmd2_cell);
					new_row.appendChild(actdmd_cell);
					new_row.appendChild(fas_collection_cell);
					new_row.appendChild(journal_adj_cell);
					new_row.appendChild(fas_tot_collection_cell);					
					new_row.appendChild(actual_coll_cell);
					new_row.appendChild(difference1_cell);
					new_row.appendChild(difference2_cell);
					new_row.appendChild(arrdmd_cell);

					
					tbody.appendChild(new_row);
					net_tot1=parseFloat(net_tot1)+parseFloat(wcdmd2);
					net_tot2=parseFloat(net_tot2)+parseFloat(actdmd);
					net_tot3=parseFloat(net_tot3)+parseFloat(fas_collection);
					net_tot4_adj=parseFloat(net_tot4_adj)+parseFloat(journal_adj);
					net_tot4=parseFloat(net_tot4)+parseFloat(fas_tot_collection);
					net_tot5=parseFloat(net_tot5)+parseFloat(actual_coll);
					net_tot6=parseFloat(net_tot6)+parseFloat(difference1);
					net_tot7=parseFloat(net_tot7)+parseFloat(difference2);
					
					net_tot8=parseFloat(net_tot8)+parseFloat(arrdmd);

				}  
					var Footer_new_row = cell1("TR", "", "", "row","",2,"","", "", "", "", "", "","");
					var Footer_empty_cell = cell1("TD", "label", "", "","",2,"label", "", "", "5%", "center", "", "","");
					var Footer_name_cell = cell1("TD", "label", "", "","Total",2,"label", "", "", "45%", "center", "", "","");
					var Footer_wcdmd2_cell = cell1("TD", "label",  "","", net_tot1, 2,"label", "", "", "15%", "center", "", "","");
					var Footer_actdmd_cell = cell1("TD", "label", "","", net_tot2, 2,"label", "", "", "15%", "center", "", "","");				
					var Footer_fas_tot_collection_cell = cell1("TD", "label", "", "",net_tot3,2,"label", "", "", "15%", "center", "", "","");
					var Footer_jour_adj_coll_cell = cell1("TD", "label", "", "",net_tot4_adj,2,"label", "", "", "15%", "center", "", "","");
					var Footer_actual_adj_coll_cell = cell1("TD", "label", "", "",net_tot4,2,"label", "", "", "15%", "center", "", "","");
					var Footer_actual_coll_cell = cell1("TD", "label", "", "",net_tot5,2,"label", "", "", "15%", "center", "", "","");
					var Footer_differ1 = cell1("TD", "label", "", "",net_tot6,2,"label", "", "", "15%", "center", "", "","");
					var Footer_differ2 = cell1("TD", "label", "", "",net_tot7,2,"label", "", "", "15%", "center", "", "","");
					
					var Footer_arrdmd_cell = cell1("TD", "label", "", "",net_tot8,2,"label", "", "", "15%", "center", "", "","");
					
					Footer_new_row.appendChild(Footer_empty_cell);
					Footer_new_row.appendChild(Footer_name_cell); 
					Footer_new_row.appendChild(Footer_wcdmd2_cell);			
					Footer_new_row.appendChild(Footer_actdmd_cell);
					Footer_new_row.appendChild(Footer_fas_tot_collection_cell);
					Footer_new_row.appendChild(Footer_jour_adj_coll_cell);
					Footer_new_row.appendChild(Footer_actual_adj_coll_cell);
					Footer_new_row.appendChild(Footer_actual_coll_cell);
					Footer_new_row.appendChild(Footer_differ1);
					Footer_new_row.appendChild(Footer_differ2);
					
					Footer_new_row.appendChild(Footer_arrdmd_cell);
					
					tbody.appendChild(Footer_new_row);
			}
		}
	}catch(e)
	{
		alert(e)
	}
}
function isnan(v)
{
	if (isNaN(v))
	return 0;
	else
	return v;
}