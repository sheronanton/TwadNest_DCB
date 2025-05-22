  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.Connection,java.sql.PreparedStatement,java.sql.ResultSet" %> 
<%@page import="java.text.DecimalFormat"%><html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement </title>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>  
</head>
<body>      
 <% 
 	String  Office_Name="",userid="",Office_id="",BEN_TYPE_GROUP="",schtype="",pmonth="",pyear="",off="",add_cond="",add_cond2=""  ;
	DecimalFormat df = new DecimalFormat("0.00");
	Connection db_con=null,db_con1=null;
	Controller obj_new=new Controller();
	Controller obj_new2=new Controller();  
	Controller obj_new3=new Controller();
	try  
	{
		db_con=obj_new.con();
		db_con1=obj_new3.con();
		obj_new.createStatement(db_con);
		obj_new3.createStatement(db_con1);
		pmonth=obj_new.setValue("pmonth",request);
		pyear=obj_new.setValue("pyear",request);
		schtype=obj_new.setValue("schtype",request);
		obj_new2.createStatement(db_con);
		BEN_TYPE_GROUP=obj_new.setValue("BEN_TYPE_GROUP",request);
		String ben_group_name=obj_new2.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+BEN_TYPE_GROUP);
		System.out.println("TEST234");
		String stype_value=obj_new2.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" + schtype);
		userid=(String)session.getAttribute("UserId");
		if(userid==null)  
		{
		  response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj_new.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_Name = obj_new.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
	 	XmlDataGenerator xml=new XmlDataGenerator(db_con,obj_new);
	 	if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
		off=Office_id.equals("5000")?"":" and Office_id="+Office_id;	 	
		Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
		String mvalue=obj_new.month_val(pmonth);
		int prv_month=obj_new.prv_month(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		int prv_year=obj_new.prv_year(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		String prv_mvalue=obj_new.month_val(Integer.toString(prv_month));
		 String path=getServletContext().getRealPath("\\data")+"\\test.xml";
	 	 xml.fpath=path;
		if (Integer.parseInt(schtype)==1) 
			add_cond=" ";// and BEN_TYPE_GROUP not in (8) ";  
		else
			add_cond=" ";
		if (Integer.parseInt(schtype)==1) 
			add_cond2=" BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"";  
		else
			add_cond2=" BEN_TYPE_ID_SUB in (6) ";  
		System.out.println("TEST");
 %> 
 
<table border=1  width="72%" cellpadding="5" cellspacing="0" style="border-collapse: collapse;"  align="center"> 
	<tr class="tdH">
		<td colspan=6 align="center"><%=Office_Name%>Demand,Collection and Balance Statement		
		<br><%=stype_value%>-<%=ben_group_name%><br>
				<font size=2>(for the month of <%=mvalue%> <%=pyear%>)</font>   
				<br>
				<font size=2>(Rupees in Lakhs)</font>  
		</td>
	</tr> 
	   
	<tr>
		<th width='5%' align='center'>Sl.No</th>
		<th width='45%' align='center'>District</th>
		<th width='15%' align='center'>Opening Balance for <%=prv_mvalue%>-<%=prv_year%></th>
		<th width='15%' align='center' >Demand for <%=prv_mvalue%>-<%=prv_year%> </th>		
		<th width='10%' align='center'>Collection during <%=mvalue%>-<%=pyear%></th>
		<th width='10%' align='center'>Balance</th>
	</tr>
	<tr>
		<th width='5%' align='center'>&nbsp;</th>
		<th width='45%' align='center'>&nbsp;</th>
		<th width='10%' align='center'>A</th>
		<th width='10%' align='center'>B</th>		
		<th width='10%' align='center'>C</th>
		<th width='10%' align='center'>D=(A+B)-C</th>
	</tr>	
<%	



		String select_count=obj_new.setValue("select_count", request);
		PreparedStatement  proc_stmt=null;
		try
		{ 
				proc_stmt = db_con.prepareCall("{call PMS_DCB_REVIEW_DISTRICT_DATA (?,?,?,?,?,?) }");		        		  		 
				proc_stmt.setInt(1, Integer.parseInt(pyear));
				proc_stmt.setInt(2, Integer.parseInt(pmonth));    					 
			 	proc_stmt.setString(3, add_cond);
				proc_stmt.setString(4, off);					
				proc_stmt.setString(5,add_cond2);
				proc_stmt.setString(6,schtype);          
				proc_stmt.execute();									
		}catch(Exception e){ out.println(e);}  
    



				/* String user_query=" SELECT ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,3),2) AS ob,"+
				" ROUND(TRUNC(DMD_FOR_MTH_WC_10,3),2)AS dmd, ROUND(TRUNC((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2)AS collection,"+
				" ROUND(TRUNC(BALANCE_18,3),2)AS balance,rpt.DISTRICT_NAME,rpt.DISTRICT_CODE "+
				" FROM "+  
				" (SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC))     / 100000 AS OPENING_BAL_WC_8, "+
				"  DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))         / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
				"  DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC))     / 100000 AS DMD_FOR_MTH_WC_10, "+
				"  DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
				"  DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
				"  DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))     / 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
				"  DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))   / 100000 AS COLN_FOR_MTH_WC_15, "+
				"  DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))           / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
				"  DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))       / 100000 AS BALANCE_18, "+
				"  SCH_TYPE_ID_SUB, DISTRICT_CODE,DISTRICT_NAME, MONTH, YEAR FROM PMS_DCB_LEDGER_ACTUAL "+
				"  WHERE YEAR="+pyear+"  AND MONTH="+pmonth+" "+add_cond+" "+off+" and "+add_cond2+"  AND SCH_TYPE_ID_SUB="+schtype+"  GROUP BY YEAR, MONTH,SCH_TYPE_ID_SUB,DISTRICT_CODE, DISTRICT_NAME ORDER BY DISTRICT_CODE,DISTRICT_NAME "+
				" )rpt  ORDER BY DISTRICT_CODE,  DISTRICT_NAME ";*/
				//String user_query="select DISTRICT_NAME, DISTRICT_CODE ,OPENINGBALANCE as ob, DEMAND as dmd,  COLLECTION  as collection , BALANCE as balance  from PMS_DCB_REVIEW_DISTRICT_WISE where month="+pmonth+" and year="+pyear;
				//String user_query="select DISTRICT_NAME, DISTRICT_CODE ,OPENINGBALANCE as ob, DEMAND as dmd,  COLLECTION  as collection , BALANCE as balance  from PMS_DCB_REVIEW_DISTRICT_WISE where month="+prv_month+" and year="+prv_year;
				
				String user_query=" SELECT DISTRICT_NAME,DISTRICT_CODE,sum(DMD_FOR_MTH_WC_10) as dmd,((sum(OPENING_BAL_WC_8)+sum(DMD_UPTO_PRV_MNTH_WC_9))-(sum(COLN_UPTO_PRV_MTH_YES_YR_WC_12)+sum(COLN_UPTO_PRV_MTH_CR_YR_DMD_13))) as ob, "+
       					" SCH_TYPE_ID_SUB, MONTH,YEAR  FROM PMS_DCB_NEW_REVIEW_DMD "+ 
    					" WHERE  MONTH="+prv_month+" AND YEAR="+prv_year+" and SCH_TYPE_ID_SUB="+schtype+" and  "+add_cond2+
    					" GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,DISTRICT_NAME,DISTRICT_CODE   ORDER BY SCH_TYPE_ID_SUB";
				
				user_query=" SELECT rpt_prv.DISTRICT_NAME,rpt_prv.DISTRICT_CODE,ob,dmd,collection, (ob+dmd)-collection AS balance "+
						   " from (SELECT DISTRICT_NAME,DISTRICT_CODE,SUM(DMD_FOR_MTH_WC_10) AS dmd, "+
					  	   " ((SUM(OPENING_BAL_WC_8)+SUM(DMD_UPTO_PRV_MNTH_WC_9))-(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC_12)+SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD_13))) AS ob, "+
			    		   " SCH_TYPE_ID_SUB,MONTH,YEAR "+
			     		   " FROM PMS_DCB_NEW_REVIEW_DMD "+
			  				" WHERE MONTH="+prv_month+" AND YEAR="+prv_year+" AND SCH_TYPE_ID_SUB="+schtype+" and  "+add_cond2+
			  				" GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,DISTRICT_NAME,DISTRICT_CODE "+
			  				" ORDER BY SCH_TYPE_ID_SUB "+
			  				" )rpt_prv "+
			  				" JOIN "+
							" (SELECT (SUM(COLN_FOR_MTH_YES_YR_WC) + SUM(COLN_INCLUDE_CHARGES)) / 100000 AS collection, DISTRICT_NAME, DISTRICT_CODE,SCH_TYPE_ID_SUB "+
					  		" FROM PMS_DCB_LEDGER_ACTUAL WHERE MONTH        ="+pmonth+" AND YEAR="+pyear+" AND SCH_TYPE_ID_SUB="+schtype+" and  "+add_cond2+ " GROUP BY YEAR, "+
			   				" MONTH,SCH_TYPE_ID_SUB, DISTRICT_NAME,DISTRICT_CODE ORDER BY SCH_TYPE_ID_SUB "+
			    			"  )rpt_coll "+
			 				" ON rpt_prv.SCH_TYPE_ID_SUB=rpt_coll.SCH_TYPE_ID_SUB AND rpt_prv.DISTRICT_CODE =rpt_coll.DISTRICT_CODE ";

				 String []head_columns={"Attribute","Rs. (in lakhs)"};   
				 String []aHead={"Opening Balance","Demand","Collection","Balance"}; 
			 	 String []colorcode={"339966" ,"FF6666","FF2366","456966"};   
				 String []qry_column={"ob","dmd","collection","balance"}; 
			 	 xml.district_char(user_query,head_columns,aHead,colorcode,qry_column);  
				ResultSet rs_dis2=obj_new3.getRS(user_query);
				double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
				int row=0;
				while(rs_dis2.next())  
				{   
					row++;	    
					String DISTRICT_NAME=rs_dis2.getString("DISTRICT_NAME");
					String DISTRICT_CODE=rs_dis2.getString("DISTRICT_CODE");
					String ob=rs_dis2.getString("ob");
					String dmd=rs_dis2.getString("dmd"); 
					String collection=rs_dis2.getString("collection");
					String balance=rs_dis2.getString("balance");
					tot1+=Double.parseDouble(ob);
					tot2+=Double.parseDouble(dmd);
					tot4+=Double.parseDouble(balance); 
					if (row%2==1)
						out.println("<tr><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
						else
						out.println("<tr bgcolor='#CCFFFF'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>"); 
				
					//String collection=obj_new.getValue("PMS_DCB_LEDGER_ACTUAL","(SUM(COLN_FOR_MTH_YES_YR_WC) + SUM(COLN_INCLUDE_CHARGES))","collection","where MONTH ="+pmonth+"  and  "+add_cond2+ " AND YEAR="+pyear+" AND DISTRICT_CODE="+DISTRICT_CODE+" AND SCH_TYPE_ID_SUB ="+schtype);
					tot3+=Double.parseDouble(collection);
					
					 
					
					out.println("<td align='left' width='45%' ><a href=md_abstract_blockwise.jsp?pmonth="+pmonth+"&pyear="+pyear+"&schtype="+schtype+"&DISTRICT_CODE="+DISTRICT_CODE+">"+DISTRICT_NAME+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(ob))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(dmd))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(collection))+"&nbsp;</td>");
					out.println("<td align='right' width='11%'>"+df.format(Double.parseDouble(balance))+"&nbsp;</td></tr>"); 					  
				}  
				out.println("<tr bgcolor='#E8E8E8'><td align='right' width='5%'>&nbsp;</td><td align='right'width='45%' >Total  </td>");
				out.println("<td align='right' width='10%'>"+df.format(tot1)+"&nbsp;</td>");
				out.println("<td align='right' width='10%'>"+df.format(tot2)+"&nbsp;</td>");  
				out.println("<td align='right' width='10%'>"+df.format(tot3)+"&nbsp;</td>");
				out.println("<td align='right' width='11%'>"+df.format(tot4)+"&nbsp;</td></tr>");
				out.println("</table></td></tr>");  
			db_con.close(); 
		
		   
		db_con1.close();
		db_con.close();   
	}catch(Exception e) 
	{    
		e.printStackTrace();      
	}    
		//out.println("http://10.163.2.108:8080/twadonline/org/PMS/PMS1/DCB/reports/jsps/distxml.jsp?schtype="+schtype+"&BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"&add_cond="+add_cond+"&off="+off+"&add_cond2="+add_cond2+"&pmonth="+pmonth+"&pyear="+pyear);
%>        
</table><center><a href="http://10.163.29.109/chart/chart.php?xmlfile=http://10.163.2.108:8080/twadonline/org/PMS/PMS1/DCB/reports/jsps/distxml.jsp?schtype=<%=schtype%>&BEN_TYPE_GROUP=<%=BEN_TYPE_GROUP%>&add_cond=<%=add_cond%>&off=<%=off%>&add_cond2=<%=add_cond2%>&pmonth=<%=pmonth%>&pyear=<%=pyear%>">.</a>
<table border=0 width="72%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">  
<tr><td align="left"><input type=button value="Back" onclick="window.history.go(-1)"></td></tr></table></center>
</body> 
</html>  