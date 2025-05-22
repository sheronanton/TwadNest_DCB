var fin_year;
function filter(type, processcode,input_value) 
{
	process_code = processcode;
	sel_type = type;
	var reg=0;
	var pmonth=document.getElementById("fmonth").value;
	var fin_year=document.getElementById("pyear").value;
	document.getElementById("pr_status").value=0;
	var monthArr = Array("Select","January","February","March","April","May","June","July","August","September","October","November","December");
	try
	{
	document.getElementById("td2").innerHTML=fin_year;
	var year2=0; 
    if (parseInt(pmonth)>3)  
		 year2=parseInt(fin_year.split("-")[0]);
	 else
		 year2=parseInt(fin_year.split("-")[1]);  
	document.getElementById("td3").innerHTML=monthArr[pmonth]+'-'+year2;
	}catch(e) {}
	
	 try
	 {
		 reg=input_value;
	 }catch(e) {}
	var xmlobj = createObject();
	url = "../../../../../AME_Entry_Status_Monitoring?reg_id="+reg+"&option="+type+"&process_code="+processcode+"&pmonth="+pmonth+"&fin_year="+fin_year+"";
	var params = "";
	xmlobj.open("POST", url, true);
	xmlobj.onreadystatechange = function() 
	{ 
		filter_process(xmlobj,type, processcode,pmonth,fin_year);
	}  
	xmlobj.send(null);
}
function filter_process(xmlobj,type, processcode,pmonth,fin_year)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
		
			var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
			
			if (type==1) 
			{
				document.getElementById("pr_status").value=1;
				if (process_code==1)
				{
					var tbody1 = document.getElementById("tbody1");
					var table = document.getElementById("table1");
					var t = 0;
					for (t = tbody1.rows.length - 1; t >= 0; t--) {
						tbody1.deleteRow(0);
					}
					
					var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
					var head1 = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","5%", "", "", "");
					var head2 = cell("TD", "label", "", "", "Division Name", 2, "", "", "", "25%","", "", "");
					var head3 = cell("TD", "label", "", "", "Total No.of Maint.Schemes", 2,"lbl", "", "", "5%", "center", "", "");
					var head4 = cell("TD", "label", "", "","Scheme AME Master", 2, "lbl", "", "", "5%", "center","", "");
					var head5 = cell("TD", "label", "", "","Budget Est. (No of Schemes)", 2, "lbl", "", "", "5%","center", "", "");
					var head6 = cell("TD", "label", "", "","Total Budget Amount(Rs.in lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head7 = cell("TD", "label", "", "","No of A.M Estimate Entered", 2, "lbl", "", "", "5%","center", "", "");
					var head8 = cell("TD", "label", "", "","Total AME Amount(in lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head9 = cell("TD", "label", "", "","No of Monthly Scheme  Performance ", 2, "lbl", "", "", "5%","center", "", "");
					var head10 = cell("TD", "label", "", "","Total Supplied Qty (ML)", 2, "lbl", "", "", "5%","center", "", "");
					var head11= cell("TD", "label", "", "","Total Demand Raised (Rs.in Lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head12 = cell("TD", "label", "", "","Total Collection (Rs. in Lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head13 = cell("TD", "label", "", "","Total Expenditure (Rs. in Lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					
					 
					head_row.appendChild(head1);
					head_row.appendChild(head2);
					head_row.appendChild(head3);
					head_row.appendChild(head4);
					head_row.appendChild(head5);
					head_row.appendChild(head6);
					head_row.appendChild(head7);
					head_row.appendChild(head8);
					head_row.appendChild(head9);
					head_row.appendChild(head10);
					head_row.appendChild(head11);
					head_row.appendChild(head12);
					head_row.appendChild(head13);
				  
					tbody1.appendChild(head_row); 
					
					var net_tot1=0,net_tot2=0,net_tot3=0,net_tot4=0,net_tot5=0,net_tot6=0,net_tot7=0;
					var net_tot01=0,net_tot02=0,net_tot03=0,net_tot04=0,net_tot05=0,net_tot06=0;
					var len = xmlobj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
					for ( var i = 0; i < len; i++)
					{
						
						var REG_id_v = xmlValue(bR, "REGION_OFFICE_ID", i);
						var REG_v = xmlValue(bR, "REG", i);
						var SCH_COUNT= xmlValue(bR, "SCH_COUNT", i);
						var BUD_COUNT = xmlValue(bR, "BUD_COUNT", i);
						var ABS_COUNT = xmlValue(bR, "ABS_COUNT", i);
						var YEAR_COUNT = xmlValue(bR, "YEAR_COUNT", i);
						var MONTHCOUNT = xmlValue(bR, "MONTHCOUNT", i);
						var SCHEME_DETAILS = xmlValue(bR, "SCHEME_DETAILS", i);
						var AME_AMOUNT = digit_control_new(xmlValue(bR, "AME_AMOUNT", i),2);
						var BUD_AMOUNT = digit_control_new(xmlValue(bR, "BUD_AMOUNT", i),2);
						var SCHITEM_COUNT = xmlValue(bR, "SCHITEM_COUNT", i);
						var TOTAL_SUPPLIED_QTY =  digit_control_new(xmlValue(bR, "TOTAL_SUPPLIED_QTY", i),3) ;
						var TOTALEXP = digit_control_new(xmlValue(bR, "TOTALEXP", i),2);
						var AMT =digit_control_new(xmlValue(bR, "AMT", i),2);
						var COL = digit_control_new(xmlValue(bR, "COL", i),2);
						net_tot04=parseFloat(net_tot04)+parseFloat(YEAR_COUNT);
						net_tot01=parseFloat(net_tot01)+parseFloat(SCH_COUNT);
						net_tot02=parseFloat(net_tot02)+parseFloat(BUD_COUNT);
						net_tot03=parseFloat(net_tot03)+parseFloat(ABS_COUNT);
						net_tot05=parseFloat(net_tot05)+parseFloat(MONTHCOUNT);
						net_tot06=parseFloat(net_tot06)+parseFloat(SCHEME_DETAILS);
						net_tot1=parseFloat(net_tot1)+parseFloat(AME_AMOUNT);
						net_tot2=parseFloat(net_tot2)+parseFloat(BUD_AMOUNT);
						net_tot3=parseFloat(net_tot3)+parseFloat(SCHITEM_COUNT);						
						net_tot4=parseFloat(net_tot4)+parseFloat(TOTAL_SUPPLIED_QTY);
						net_tot5=parseFloat(net_tot5)+parseFloat(TOTALEXP);
						net_tot6=parseFloat(net_tot6)+parseFloat(AMT);
						net_tot7=parseFloat(net_tot7)+parseFloat(COL);
						 
						
						var head_row_c = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
						var head1_c = cell("TD", "label", "", "", (i+1), 2, "lbl", "", "","5%", "", "", "");
						//var head2_c   = cell("TD", "A", "", "", REG_v, 2, "", "", "", "35%","left", "", "");
						var head2_c     = cell("TD", "A", "", "", REG_v, "EDIT", "","javascript:filter(1,2,"+REG_id_v +")", "", "", "left", "", "");
						//var edit_cell = cell("TD", "A", "", "", "EDIT", "EDIT", "","javascript:edit_bud(" +len +")", "", "", "", "", "");

						var head3_c = cell("TD", "label", "", "", SCH_COUNT, 2,"lbl", "", "", "5%", "center", "", "");
						var head4_c = cell("TD", "label", "", "",SCHEME_DETAILS, 2, "lbl", "", "", "5%", "center","", "");
						var head5_c = cell("TD", "label", "", "",BUD_COUNT, 2, "lbl", "", "", "5%","center", "", "");
						var head6_c = cell("TD", "label", "", "",BUD_AMOUNT, 2, "lbl", "", "", "5%","right", "", "");
						var head7_c = cell("TD", "label", "", "",ABS_COUNT, 2, "lbl", "", "", "5%","center", "", "");
						var head8_c = cell("TD", "label", "", "",AME_AMOUNT, 2, "lbl", "", "", "5%","right", "", "");
						var head9_c = cell("TD", "label", "", "",MONTHCOUNT, 2, "lbl", "", "", "5%","center", "", "");
						var head10_c = cell("TD", "label", "", "",TOTAL_SUPPLIED_QTY, 2, "lbl", "", "", "5%","right", "", "");
						var head11_c= cell("TD", "label", "", "",AMT, 2, "lbl", "", "", "5%","right", "", "");
						var head12_c = cell("TD", "label", "", "",COL, 2, "lbl", "", "", "5%","right", "", "");
						var head13_c = cell("TD", "label", "", "",TOTALEXP, 2, "lbl", "", "", "5%","right", "", "");
						head_row_c.appendChild(head1_c);
						head_row_c.appendChild(head2_c);
						head_row_c.appendChild(head3_c);
						head_row_c.appendChild(head4_c);
						head_row_c.appendChild(head5_c);
						head_row_c.appendChild(head6_c);
						head_row_c.appendChild(head7_c);
						head_row_c.appendChild(head8_c);
						head_row_c.appendChild(head9_c);
						head_row_c.appendChild(head10_c);
						head_row_c.appendChild(head11_c);
						head_row_c.appendChild(head12_c);
						head_row_c.appendChild(head13_c);					  
						tbody1.appendChild(head_row_c); 
					}	    
					var head_row_t = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
					var head1_t = cell("TD", "label", "", "", "", 2, "lbl", "", "","5%", "", "", "");
					//var head2_c   = cell("TD", "A", "", "", REG_v, 2, "", "", "", "35%","left", "", "");
					var head2_t     = cell("TD", "label", "", "", "Total", "EDIT", "","", "", "", "right", "", "");
					var head3_t = cell("TD", "label", "", "", net_tot01, 2,"lbl", "", "", "5%", "center", "", "");
					var head4_t = cell("TD", "label", "", "",net_tot06, 2, "lbl", "", "", "5%", "center","", "");
					var head5_t = cell("TD", "label", "", "",net_tot02, 2, "lbl", "", "", "5%","center", "", "");
					var head6_t = cell("TD", "label", "", "",digit_control_new(net_tot2,2), 2, "lbl", "", "", "5%","right", "", "");
					var head7_t = cell("TD", "label", "", "",net_tot03, 2, "lbl", "", "", "5%","center", "", "");
					var head8_t = cell("TD", "label", "", "",digit_control_new(net_tot1,2), 2, "lbl", "", "", "5%","right", "", "");
					var head9_t = cell("TD", "label", "", "",net_tot05, 2, "lbl", "", "", "5%","center", "", "");
					var head10_t = cell("TD", "label", "", "",digit_control_new(net_tot4,3), 2, "lbl", "", "", "5%","right", "", "");
					var head11_t= cell("TD", "label", "", "",digit_control_new(net_tot6,2), 2, "lbl", "", "", "5%","right", "", "");
					var head12_t = cell("TD", "label", "", "",digit_control_new(net_tot7,2), 2, "lbl", "", "", "5%","right", "", "");
					var head13_t = cell("TD", "label", "", "",digit_control_new(net_tot5,2), 2, "lbl", "", "", "5%","right", "", "");
					head_row_t.appendChild(head1_t);
					head_row_t.appendChild(head2_t);
					head_row_t.appendChild(head3_t);
					head_row_t.appendChild(head4_t);
					head_row_t.appendChild(head5_t);
					head_row_t.appendChild(head6_t);
					head_row_t.appendChild(head7_t);
					head_row_t.appendChild(head8_t);
					head_row_t.appendChild(head9_t);
					head_row_t.appendChild(head10_t);
					head_row_t.appendChild(head11_t);
					head_row_t.appendChild(head12_t);
					head_row_t.appendChild(head13_t);					  
					tbody1.appendChild(head_row_t); 
				}if (process_code==2)
				{
					var tbody1 = document.getElementById("tbody2");
					var table = document.getElementById("table2");
					var t = 0;
					for (t = tbody1.rows.length - 1; t >= 0; t--) {
						tbody1.deleteRow(0);
					}
					
					
					
					
					
					var REG= xmlValue(bR, "REG", 0);
					document.getElementById("td1_new").innerHTML=REG;
					var head_row = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
					var head1 = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","5%", "", "", "");
					var head2 = cell("TD", "label", "", "", "Division Name", 2, "", "", "", "25%","", "", "");
					var head3 = cell("TD", "label", "", "", "Total No.of Maint.Schemes", 2,"lbl", "", "", "5%", "center", "", "");
					var head4 = cell("TD", "label", "", "","Scheme AME Master", 2, "lbl", "", "", "5%", "center","", "");
					var head5 = cell("TD", "label", "", "","Budget Est. (No of Schemes)", 2, "lbl", "", "", "5%","center", "", "");
					var head6 = cell("TD", "label", "", "","Total Budget Amount(Rs.in lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head7 = cell("TD", "label", "", "","No of A.M Estimate Entered", 2, "lbl", "", "", "5%","center", "", "");
					var head8 = cell("TD", "label", "", "","Total AME Amount(in lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head9 = cell("TD", "label", "", "","No of Monthly Scheme  Performance ", 2, "lbl", "", "", "5%","center", "", "");
					var head10 = cell("TD", "label", "", "","Total Supplied Qty (ML)", 2, "lbl", "", "", "5%","center", "", "");
					var head11= cell("TD", "label", "", "","Total Demand Raised (Rs.in Lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head12 = cell("TD", "label", "", "","Total Collection (Rs. in Lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					var head13 = cell("TD", "label", "", "","Total Expenditure (Rs. in Lakhs)", 2, "lbl", "", "", "5%","center", "", "");
					
					 
					head_row.appendChild(head1);
					head_row.appendChild(head2);
					head_row.appendChild(head3);
					head_row.appendChild(head4);
					head_row.appendChild(head5);
					head_row.appendChild(head6);
					head_row.appendChild(head7);
					head_row.appendChild(head8);
					head_row.appendChild(head9);
					head_row.appendChild(head10);
					head_row.appendChild(head11);
					head_row.appendChild(head12);
					head_row.appendChild(head13);
				  
					tbody1.appendChild(head_row); 
					var net_tot1=0,net_tot2=0,net_tot3=0,net_tot4=0,net_tot5=0,net_tot6=0,net_tot7=0;
					var net_tot01=0,net_tot02=0,net_tot03=0,net_tot04=0,net_tot05=0,net_tot06=0;
					var len = xmlobj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
					for ( var i = 0; i < len; i++)
					{
						
						var REG_id_v = xmlValue(bR, "REGION_OFFICE_ID", i);
						
						var REG_v = xmlValue(bR, "DIV", i);
						var OFFICE_ID= xmlValue(bR, "OFFICE_ID", i);
						var SCH_COUNT= xmlValue(bR, "SCH_COUNT", i);
						var BUD_COUNT = xmlValue(bR, "BUD_COUNT", i);
						var ABS_COUNT = xmlValue(bR, "ABS_COUNT", i);
						var YEAR_COUNT = xmlValue(bR, "YEAR_COUNT", i);
						var MONTHCOUNT = xmlValue(bR, "MONTHCOUNT", i);
						var SCHEME_DETAILS = xmlValue(bR, "SCHEME_DETAILS", i);
						var AME_AMOUNT = digit_control_new(xmlValue(bR, "AME_AMOUNT", i),2);
						var BUD_AMOUNT = digit_control_new(xmlValue(bR, "BUD_AMOUNT", i),2);
						var SCHITEM_COUNT = xmlValue(bR, "SCHITEM_COUNT", i);
						var TOTAL_SUPPLIED_QTY = digit_control_new(xmlValue(bR, "TOTAL_SUPPLIED_QTY", i),3);
						var TOTALEXP = digit_control_new(xmlValue(bR, "TOTALEXP", i),2);
						var AMT = digit_control_new(xmlValue(bR, "AMT", i),2);
						var COL = digit_control_new(xmlValue(bR, "COL", i),2);									 
						
						var head_row_c = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
						var head1_c = cell("TD", "label", "", "", (i+1), 2, "lbl", "", "","5%", "", "", "");
						//var head2_c   = cell("TD", "A", "", "", REG_v, 2, "", "", "", "35%","left", "", "");
						var head2_c     = cell("TD", "A", "", "", REG_v, "EDIT", "","javascript:process("+OFFICE_ID+","+pmonth+",'"+fin_year+"')", "", "", "left", "", "");
						//var edit_cell = cell("TD", "A", "", "", "EDIT", "EDIT", "","javascript:edit_bud(" +len +")", "", "", "", "", "");
						net_tot04=parseFloat(net_tot04)+parseFloat(YEAR_COUNT);
						net_tot01=parseFloat(net_tot01)+parseFloat(SCH_COUNT);
						net_tot02=parseFloat(net_tot02)+parseFloat(BUD_COUNT);
						net_tot03=parseFloat(net_tot03)+parseFloat(ABS_COUNT);
						net_tot05=parseFloat(net_tot05)+parseFloat(MONTHCOUNT);
						net_tot06=parseFloat(net_tot06)+parseFloat(SCHEME_DETAILS);
						net_tot1=parseFloat(net_tot1)+parseFloat(AME_AMOUNT);
						net_tot2=parseFloat(net_tot2)+parseFloat(BUD_AMOUNT);
						net_tot3=parseFloat(net_tot3)+parseFloat(SCHITEM_COUNT);						
						net_tot4=parseFloat(net_tot4)+parseFloat(TOTAL_SUPPLIED_QTY);
						net_tot5=parseFloat(net_tot5)+parseFloat(TOTALEXP);
						net_tot6=parseFloat(net_tot6)+parseFloat(AMT);
						net_tot7=parseFloat(net_tot7)+parseFloat(COL);
						var head3_c = cell("TD", "label", "", "", SCH_COUNT, 2,"lbl", "", "", "5%", "center", "", "");
						var head4_c = cell("TD", "label", "", "",SCHEME_DETAILS, 2, "lbl", "", "", "5%", "center","", "");
						var head5_c = cell("TD", "label", "", "",BUD_COUNT, 2, "lbl", "", "", "5%","center", "", "");
						var head6_c = cell("TD", "label", "", "",BUD_AMOUNT, 2, "lbl", "", "", "5%","right", "", "");
						var head7_c = cell("TD", "label", "", "",ABS_COUNT, 2, "lbl", "", "", "5%","center", "", "");
						var head8_c = cell("TD", "label", "", "",AME_AMOUNT, 2, "lbl", "", "", "5%","right", "", "");
						var head9_c = cell("TD", "label", "", "",MONTHCOUNT, 2, "lbl", "", "", "5%","center", "", "");
						var head10_c = cell("TD", "label", "", "",TOTAL_SUPPLIED_QTY, 2, "lbl", "", "", "5%","right", "", "");
						var head11_c= cell("TD", "label", "", "",AMT, 2, "lbl", "", "", "5%","right", "", "");
						var head12_c = cell("TD", "label", "", "",COL, 2, "lbl", "", "", "5%","right", "", "");
						var head13_c = cell("TD", "label", "", "",TOTALEXP, 2, "lbl", "", "", "5%","right", "", "");
						head_row_c.appendChild(head1_c);
						head_row_c.appendChild(head2_c);
						head_row_c.appendChild(head3_c);
						head_row_c.appendChild(head4_c);
						head_row_c.appendChild(head5_c);
						head_row_c.appendChild(head6_c);
						head_row_c.appendChild(head7_c);
						head_row_c.appendChild(head8_c);
						head_row_c.appendChild(head9_c);
						head_row_c.appendChild(head10_c);
						head_row_c.appendChild(head11_c);
						head_row_c.appendChild(head12_c);
						head_row_c.appendChild(head13_c);					  
						tbody1.appendChild(head_row_c); 
					}	 
					var head_row_t = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
					var head1_t = cell("TD", "label", "", "", "", 2, "lbl", "", "","5%", "", "", "");
					//var head2_c   = cell("TD", "A", "", "", REG_v, 2, "", "", "", "35%","left", "", "");
					var head2_t     = cell("TD", "label", "", "", "Total", "EDIT", "","", "", "", "right", "", "");
					var head3_t = cell("TD", "label", "", "", net_tot01, 2,"lbl", "", "", "5%", "center", "", "");
					var head4_t = cell("TD", "label", "", "",net_tot06, 2, "lbl", "", "", "5%", "center","", "");
					var head5_t = cell("TD", "label", "", "",net_tot02, 2, "lbl", "", "", "5%","center", "", "");
					var head6_t = cell("TD", "label", "", "",digit_control_new(net_tot2,2), 2, "lbl", "", "", "5%","right", "", "");
					var head7_t = cell("TD", "label", "", "",net_tot03, 2, "lbl", "", "", "5%","center", "", "");
					var head8_t = cell("TD", "label", "", "",digit_control_new(net_tot1,2), 2, "lbl", "", "", "5%","right", "", "");
					var head9_t = cell("TD", "label", "", "",net_tot05, 2, "lbl", "", "", "5%","center", "", "");
					var head10_t = cell("TD", "label", "", "",digit_control_new(net_tot4,3), 2, "lbl", "", "", "5%","right", "", "");
					var head11_t= cell("TD", "label", "", "",digit_control_new(net_tot6,2), 2, "lbl", "", "", "5%","right", "", "");
					var head12_t = cell("TD", "label", "", "",digit_control_new(net_tot7,2), 2, "lbl", "", "", "5%","right", "", "");
					var head13_t = cell("TD", "label", "", "",digit_control_new(net_tot5,2), 2, "lbl", "", "", "5%","right", "", "");
					head_row_t.appendChild(head1_t);
					head_row_t.appendChild(head2_t);
					head_row_t.appendChild(head3_t);
					head_row_t.appendChild(head4_t);
					head_row_t.appendChild(head5_t);
					head_row_t.appendChild(head6_t);
					head_row_t.appendChild(head7_t);
					head_row_t.appendChild(head8_t);
					head_row_t.appendChild(head9_t);
					head_row_t.appendChild(head10_t);
					head_row_t.appendChild(head11_t);
					head_row_t.appendChild(head12_t);
					head_row_t.appendChild(head13_t);					  
					tbody1.appendChild(head_row_t); 
				}else 	if (process_code==3)
				{
					var tbody1 = document.getElementById("tbody1");
					var table = document.getElementById("table1");
					var t = 0;
					for (t = tbody1.rows.length - 1; t >= 0; t--) {
						tbody1.deleteRow(0);
					}

					 	
					
					
					
					
					var head_row_new = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
					var head1_new = cell("TD", "label", "", "", "Sl.No", 2, "lbl", "", "","5%", "", "", "");
					var head2_new = cell("TD", "label", "", "", "Scheme Name", 2, "", "", "", "55%","", "", "");
					var head3_new = cell("TD", "label", "", "", "Scheme Year", 2,"lbl", "", "", "1%", "center", "", "");
					var head3_1_new = cell("TD", "label", "", "","Budget Estimate\n" + fin_year+" \n(Rs.Lakhs)", 2, "lbl", "", "", "15%", "center","", "");
					var head4_new = cell("TD", "label", "", "","AM Estimate \n" +fin_year+"\n (Rs.Lakhs) ", 2, "lbl", "", "", "15%", "center","", "");					
					var head5_new = cell("TD", "label", "", "","Total Supplied Qty (ML) ", 2, "lbl", "", "", "15%", "center","", "");
					var head6_new = cell("TD", "label", "", "","Total Demand Raised (Rs.in Lakhs) ", 2, "lbl", "", "", "15%", "center","", "");
					var head7_new = cell("TD", "label", "", "","Total Collection (Rs. in Lakhs) ", 2, "lbl", "", "", "15%", "center","", "");
					var head8_new = cell("TD", "label", "", "","Total Expenditure (Rs. in Lakhs) ", 2, "lbl", "", "", "15%", "center","", "");
					
					
								
					head_row_new.appendChild(head1_new);
					head_row_new.appendChild(head3_new);					
					head_row_new.appendChild(head2_new);
					head_row_new.appendChild(head3_1_new);
					head_row_new.appendChild(head4_new);
					
					head_row_new.appendChild(head5_new);
					head_row_new.appendChild(head6_new);
					head_row_new.appendChild(head7_new);
					head_row_new.appendChild(head8_new);
					
					tbody1.appendChild(head_row_new); 
					var len_new = xmlobj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
					
					var tot_bud=0;
					var tot_est=0;
					
					var tot_qty=0;
					var tot_dmd=0;
					var tot_col=0;
					var tot_exp=0;
					for ( var i = 0; i < len_new; i++)
					{
						var SCH_NAME = xmlValue(bR, "SCH_NAME", i);
						var AMT_= xmlValue(bR, "AMT", i);
						var SCH_YEAR= xmlValue(bR, "SCH_YEAR", i);
						var BUDAMT= xmlValue(bR, "BUDAMT", i);
						var WCAMT= xmlValue(bR, "WCAMT", i);
						var COL= xmlValue(bR, "COL", i);
						var QTY= xmlValue(bR, "QTY", i);
						var EXP_AMT= xmlValue(bR, "EXP_AMT", i);
						var head_row_c_new = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
						var head1_c_new= cell("TD", "label", "", "", (i+1), 2, "lbl", "", "","5%", "", "", "");
						var head2_c_new= cell("TD", "label", "", "", SCH_NAME, 2, "lbl", "", "","45%", "left", "", "");
						var head3_c_new= cell("TD", "label", "", "", SCH_YEAR, 2, "", "", "", "15%","left", "", "");						
						var head4_c_new= cell("TD", "label", "", "", dec(AMT_,2), 2, "", "", "", "15%","right", "", "");
						var head5_c_new= cell("TD", "label", "", "", dec(BUDAMT,2), 2, "", "", "", "15%","right", "", "");
						
						var head6_c_new= cell("TD", "label", "", "", dec(QTY,3), 2, "", "", "", "15%","right", "", "");
						var head7_c_new= cell("TD", "label", "", "", dec(WCAMT,2), 2, "", "", "", "15%","right", "", "");
						var head8_c_new= cell("TD", "label", "", "", dec(COL,2), 2, "", "", "", "15%","right", "", "");
						var head9_c_new= cell("TD", "label", "", "", dec(EXP_AMT,2), 2, "", "", "", "15%","right", "", "");
						  
						  tot_qty=parseFloat(tot_qty)+parseFloat(QTY);
						  tot_dmd=parseFloat(tot_dmd)+parseFloat(WCAMT);
						  tot_col=parseFloat(tot_col)+parseFloat(COL);
						  tot_exp=parseFloat(tot_exp)+parseFloat(EXP_AMT);
						 
						tot_bud=parseFloat(tot_bud)+parseFloat(BUDAMT);
						tot_est=parseFloat(tot_est)+parseFloat(AMT_);
						head_row_c_new.appendChild(head1_c_new);
						head_row_c_new.appendChild(head3_c_new);
						head_row_c_new.appendChild(head2_c_new);
						head_row_c_new.appendChild(head5_c_new);
						head_row_c_new.appendChild(head4_c_new);						
						head_row_c_new.appendChild(head6_c_new);
						head_row_c_new.appendChild(head7_c_new);
						head_row_c_new.appendChild(head8_c_new);
						head_row_c_new.appendChild(head9_c_new);
						 
						tbody1.appendChild(head_row_c_new); 
					}
					
					var total_new = cell("TR", "", "", "row0", "", 2, "", "", "", "","", "", "");
					var c0_new= cell("TD", "label", "", "", "", 2, "lbl", "", "","5%", "", "", "");
				 
					var c11_new= cell("TD", "label", "", "", "", 2, "lbl", "", "","5%", "", "", "");
					var c2_new= cell("TD", "label", "", "", "Total", 2, "lbl", "", "","5%", "", "", "");
					var tot_bud_new= cell("TD", "label", "", "",digit_control_new(tot_bud,2), 2, "", "", "", "15%","right", "", "");
					var tot_est_new= cell("TD", "label", "", "", digit_control_new(tot_est,2), 2, "", "", "", "15%","right", "", "");
					
					var tot_qty_new= cell("TD", "label", "", "",digit_control_new(tot_qty,3), 2, "", "", "", "15%","right", "", "");
					var tot_dmd_new= cell("TD", "label", "", "", digit_control_new(tot_dmd,2), 2, "", "", "", "15%","right", "", "");
					var tot_col_new= cell("TD", "label", "", "",digit_control_new(tot_col,2), 2, "", "", "", "15%","right", "", "");
					var tot_exp_new= cell("TD", "label", "", "", digit_control_new(tot_exp,2), 2, "", "", "", "15%","right", "", "");
					
					total_new.appendChild(c0_new);
				 
					total_new.appendChild(c11_new);
					total_new.appendChild(c2_new);
					total_new.appendChild(tot_bud_new);
					total_new.appendChild(tot_est_new);
					
					total_new.appendChild(tot_qty_new);
					total_new.appendChild(tot_dmd_new);
					total_new.appendChild(tot_col_new);
					total_new.appendChild(tot_exp_new);
					
					tbody1.appendChild(total_new); 
				}
			}
			
			
			
			
		}
	}
	
}