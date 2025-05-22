<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  <script type="text/javascript">
	function callReport()
	{
	 var fyear=document.getElementById('fyear').value;
	 var fmonth=document.getElementById('fmonth').value;
     window.open("../../../../../Bill_Demand?command=prmispdf&fmonth="+fmonth+"&fyear="+fyear);
	}
  </script> 
</head>
<%
		String new_cond=Controller.new_cond;
		ResultSet rs=null;
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="";
		Controller obj=null;
		try
		{
		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		  
		try
		{
		   userid=(String)session.getAttribute("UserId");
		}catch(Exception e) {userid="0";}
		if(userid==null)
		{ 
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		 
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " 	");
		smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		String qry="select "+
		      	"ben.BENEFICIARY_NAME,"+
		      	" btype.BEN_TYPE_DESC, DECODE(met.RC,NULL,0,met.RC) AS count1,"+
  				" DECODE(met2.RC,NULL,0,met2.RC) AS count2 ,"+ 
  				" DECODE(met.RC-DECODE(met2.RC,NULL,0,met2.RC),NULL,0,met.RC-DECODE(met2.RC,NULL,0,met2.RC))AS missing ,"+  			 
  				" DECODE(met_sett.set_count,NULL,0,met_sett.set_count)            AS setflag "+ 
  			 
		      " from "+
		      "( "+
		         "( "+
		             "select "+ 
		                 "  BENEFICIARY_NAME, "+
		                "   BENEFICIARY_TYPE_ID, "+
		               "    OFFICE_ID ,BENEFICIARY_SNO"+
		             " from  "+
		             "     PMS_DCB_MST_BENEFICIARY "+
		            " where "+new_cond+"   OFFICE_ID="+Office_id+
		         " order by BENEFICIARY_TYPE_ID)ben "+	         
		         "join "+
		         "( "+
		               " select "+
		                   " BEN_TYPE_ID,"+
		                  " BEN_TYPE_DESC "+
		               " from "+
		                   " PMS_DCB_BEN_TYPE"+
		         ")btype"+
		         " on btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID "+
		         " left join "+
		         "   ( select "+ 
		        		 "     count(*) as RC, "+		           
		           "     BENEFICIARY_SNO  "+
		           "  from  "+
		       "    PMS_DCB_MST_BENEFICIARY_METRE  where meter_status='L' "+
		           "  GROUP by BENEFICIARY_SNO "+ 
		           "  )met "+
		       "  on met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "+
		       "  left join "+
		       "   ( select  "+
		        		 "   count(*) as RC, "+		           
		           "      BENEFICIARY_SNO  "+
		           " from  "+
		       "    PMS_DCB_MST_BENEFICIARY_METRE  "+
		           " where  meter_status='L' and "+
		       "  METRE_SNO Not in ( "+   
		        		   "  SELECT METRE_SNO "+
		           "  FROM PMS_DCB_TRN_MONTHLY_PR "+
		           "  WHERE OFFICE_ID= "+Office_id+
		           "  AND MONTH      = "+smonth+
		           "  AND YEAR       = "+syear+
		           "  ) "+
		           " GROUP by BENEFICIARY_SNO "+ 
		       " )met2 "+
		       " on met2.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "+     
		       " left outer join "+ 
		       " ( "+ 
		       " SELECT "+ 
		       "     BENEFICIARY_SNO, "+ 
		       "      count(*) as val "+ 
		       "     FROM PMS_DCB_TRN_MONTHLY_PR "+ 
		       "      WHERE OFFICE_ID= "+Office_id+
		       "      AND MONTH      = "+smonth+
		       "     AND YEAR       = "+syear+		       
		       "  GROUP BY BENEFICIARY_SNO "+ 
		       "  ) pr  "+ 
		       "  on pr.BENEFICIARY_SNO=met2.BENEFICIARY_SNO "+   
		       " LEFT JOIN  "+ 
		       " ( "+ 
		    		   " SELECT COUNT(*) AS set_count, "+ 
		       " BENEFICIARY_SNO "+ 
		           " FROM PMS_DCB_MST_BENEFICIARY_METRE where meter_status='L' and  setting_flag is null "+ 
		         " GROUP BY BENEFICIARY_SNO  "+ 
		         " )met_sett "+ 
		       " ON met_sett.BENEFICIARY_SNO=met.BENEFICIARY_SNO"+ 
		       
		        " )"; 
		 
		  rs=obj.getRS(qry);
		
		
		 
		  
		 
		 
	 
		 
		
%> 
		<body> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table class="fb2" valign=top  id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr> 
				<tr>
					<td class="tdText">Month&nbsp;&nbsp;:&nbsp;&nbsp;<%=obj.month_val(smonth)%></td>
					<td class="tdText" align="right">Year&nbsp;&nbsp;:&nbsp;&nbsp;<%=syear%></td>
				</tr>
				<% while (rs.next()){%>
				<tr>
					<td>
						<%=rs.getString(1)%>
					</td>
				<td>
						<%=rs.getString(2) %>
					</td><td>
						<%=rs.getString(3) %>
					</td><td>
						<%=rs.getString(4) %>
					</td> 
				</tr>	
					
					
					
				<%}%>
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Print" onclick="callReport()"/> <input class="fb2" type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>
<%
con.close(); 
		}catch (Exception e) {out.println(e);
		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");
			
		}

%>

</html>