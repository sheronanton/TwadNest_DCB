<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="Servlets.Security.classes.UserProfile"%>	
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Beneficiary Water Charges | TWAD Nest - Phase II</title>
</head>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
  <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<body >
<%

Connection con=null;
String new_cond=Controller.new_cond;
String tab="<table   width=95% align='center' cellpadding='5' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>";
tab+="<tr class='tdH'>";
tab+="<td align=center>Sl.No</td>";
tab+="<td align=center>Beneficiary Name</td>";
tab+="<td align=center>Beneficiary Type</td>";
tab+="<td align=center> Scheme Name</td>";
tab+="<td align=center>Scheme Type</td>";
tab+="<td align=center>Current Net<bR>Consumptoin[KL] </td>";
tab+="<td align=center>Amount(Rs)</td>";
tab+="</tr>";



		

Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) ;
ResultSet rs=null;
 Statement st=null;
int year = cal.get(Calendar.YEAR);

 if (month >=4)
	year=year;
else
	year=year;

int pumpingfalg=0;
/*

	flag from setting table
	pumpingfalg=1

*/
 
String Date_dis=day+"/"+(month+1)+"/"+year;
String userid="0",Office_id="",Office_Name="";
Controller obj=new Controller();
 
 try
{
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
		String oid=obj.setValue("oid",request);		
		if (!oid.trim().equalsIgnoreCase("0"))
			Office_id=oid;
	
	
	Office_Name=obj.isNull(obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  "),2);
 	 
   	 String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	 String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		 
		String  spl_flag=obj.setValue("spl_flag",request);
		
		String flag=obj.setValue("flag",request);
		
	if (smonth.equalsIgnoreCase("0"))
	 month=month;
	else
	 month=Integer.parseInt(smonth);
	 
	 if (syear.equalsIgnoreCase("0"))
	  year= year;
	else
	 year=Integer.parseInt(syear);
	String cond="";
	if (spl_flag.equals("2"))
	{
		cond=" and BENEFICIARY_SNO="+obj.setValue("BENEFICIARY_SNO",request);
	}else
	{
		cond="";
	}
	 
	 
 	
 	String pmonth="",pyear="";
 	pmonth=obj.setValue("pmonth",request);
 	pyear=obj.setValue("pyear",request);
 	
	if (!pmonth.trim().equalsIgnoreCase("0")) 		month=Integer.parseInt(pmonth);   
	if (!pyear.trim().equalsIgnoreCase("0")) 		year=Integer.parseInt(pyear);
	
	
	
	
	 // command => pr_select  process =2  start 
		   st =con.createStatement();
	String	sel_qry="select "+ 
 	"  			 ben.BENEFICIARY_NAME,"+
     "   		 ben.BENEFICIARY_SNO,"+
     "    		 wc.amt,"+
     "    		 wc.SCHEME_SNO,"+
     "     		 sch.SCH_NAME,"+
     "     		 schtype.SCH_TYPE_DESC,"+
     "			BEN_TYPE.BEN_TYPE_DESC  "+
     "  from "+
     " ( "+
 		"  select  "+
 		"      	 BENEFICIARY_SNO, "+
         "      	 OFFICE_ID, "+
         "      	 MONTH, "+
         "      	 YEAR, "+
         "      	 SCHEME_SNO, "+
         "      	 sum(QTY_CONSUMED) as qty,  "+
         "      	 sum(TOTAL_AMT) as amt  "+
         "  from " +
         "      	 PMS_DCB_WC_BILLING  "+
         "  where   "+
         " 	  	 month="+smonth+" and  "+
         "     	 year="+syear+" and  "+
         "     	 office_id="+Office_id+""+  cond +                
         "  group by  "+
         "      		OFFICE_ID, "+
         "      		BENEFICIARY_SNO, "+
         "      		SCHEME_SNO, "+
         "      		MONTH, "+
         "      		YEAR "+
         "  )wc "+
         "  join  "+
         "  ( "+
 		"  select  "+
 		"      		BENEFICIARY_SNO, "+
         "      	BENEFICIARY_NAME,"+
         "			BENEFICIARY_TYPE_ID "+
         "  from "+
         "     		PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+""+
         "  order by  "+
         "    		BENEFICIARY_NAME "+
         "  )ben "+
         "  			on ben.BENEFICIARY_SNO=wc.BENEFICIARY_SNO "+
         " join " +
		" (" +
		" select " +
		"BEN_TYPE_DESC," +
		"BEN_TYPE_ID " +
		" from " +
		" PMS_DCB_BEN_TYPE " +
		"" +
		" " +
		")BEN_TYPE " +
		" on BEN_TYPE.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID  " + 
         "  join "+
         "  ( "+
 		"  select   "+
 		"  				SCH_SNO,  "+
 		"  				SCH_NAME,  "+
 		"    			SCH_TYPE_ID  "+
 		"  from  "+
 		" 				PMS_SCH_MASTER  "+        		
         "  )sch  "+
         "  			on sch.SCH_SNO=wc.SCHEME_SNO  "+
         "  join   "+
         " (  "+
 		"  select  "+
 		"   	SCH_TYPE_ID,  "+
 		" 		SCH_TYPE_DESC  "+
 		"  from  "+
 		"  		PMS_SCH_LKP_TYPE  "+             
      	" )schtype  "+        
      	" on schtype.SCH_TYPE_ID=sch.SCH_TYPE_ID order by SCHEME_SNO  ";
 		try {
 			 
 		 
 			 
			 rs=st.executeQuery(sel_qry);
			 
			//xml="<result>";      
			String ben_sno,temp="0",temp_sch="0",sch="0",last_sch="";
			
			int slno=1	;
			long tot_=0,sch_tot=0,sch_tot1=0;;
			double net_pr=0,net_pr2=0;
			double grand_pr=0;
			int c=0;
			while (rs.next())
			{
				ben_sno=obj.isNull(rs.getString("BENEFICIARY_SNO"), 1);
				sch=obj.isNull(rs.getString("SCHEME_SNO"), 1);
				
				
				
				String  net_=obj.getValue("PMS_DCB_TRN_MONTHLY_PR",
							"sum(QTY_CONSUMED_NET)",
							"where BENEFICIARY_SNO="+ben_sno+
							" and MONTH="+smonth+" and SCH_SNO="+sch+" and  YEAR =" +syear+"  and  OFFICE_ID="+Office_id); 
				                
				
				
			 
				
				if (Integer.parseInt(sch)==Integer.parseInt(temp_sch))
				{
					sch_tot+=sch_tot1+Long.parseLong(obj.isNull(rs.getString("amt"), 1));
					net_pr+=net_pr2+Double.parseDouble(net_);
					sch_tot1=0;
					net_pr2=0;
				 	c++;	
				}else
				{  
					c=0;
					 
					if (c==1)
					{
						sch_tot+=Long.parseLong(obj.isNull(rs.getString("amt"), 1));
						net_pr+=Double.parseDouble(net_);
						sch_tot1=0;
						net_pr2=0;
						c++;	
					}
					else
					{
						  
					//	sch_tot+=Double.parseDouble(obj.isNull(rs.getString("amt"), 1));
				 	 if (sch_tot!=0 )
					  {
						tab+="<tr><Td align=right colspan=5><font size='3' color=blue><b>Total</b></font></td><td class='tdText'  align=right  ><font size='3' color='green'><b>"+net_pr+"</b></font></td><td class='tdText'  align=right><font size='3' color=blue><b>"+sch_tot+"</b></font></td></tr>";
						sch_tot1=Long.parseLong(obj.isNull(rs.getString("amt"), 1));
						net_pr2=Double.parseDouble(net_);
						sch_tot=0;
						net_pr=0;
						c=0;
					  }else
					  {
						  sch_tot1=Long.parseLong(obj.isNull(rs.getString("amt"), 1));
						  net_pr2=Double.parseDouble(net_);
						  c=0;
					  } 
				 	 
			}
		} 
				
				
				tab+="<tr>";
				String name="",no="",btype="";
				 
				if (Integer.parseInt(ben_sno)!=Integer.parseInt(temp))
				{
					name="<td class='tdText'>"+obj.isNull(rs.getString("BENEFICIARY_NAME"), 1)+"</td>";
					no="<td class='tdText'>"+slno+"</td>";
					btype="<td class='tdText'>"+obj.isNull(rs.getString("BEN_TYPE_DESC"), 1)+"</td>";
					slno++;
				}
				else
				{
					name="<td>&nbsp;</td>";
					no="<td>&nbsp;</td>";
					btype="<td>&nbsp;</td>";
				
				}
				
				tab+=no;
				tab+=name;
				tab+=btype;
			 	tab+="<td class='tdText'>"+obj.isNull(rs.getString("SCH_NAME"), 1)+"</td>";
			 	tab+="<td class='tdText'>"+obj.isNull(rs.getString("SCH_TYPE_DESC"), 1)+"</td>";
			 	
			 	tab+="<td class='tdText'  align=right>"+net_+"</td>";
				tab+="<td class='tdText' align=right>"+obj.isNull(rs.getString("amt"), 1)+"</td></tr>";
				tot_+=Double.parseDouble(obj.isNull(rs.getString("amt"), 1));
				grand_pr+=Double.parseDouble(net_);
				
			//	sch_tot
					
				temp=ben_sno;
				temp_sch=sch;
				tab+="</tr>";
				last_sch=sch;
			}
			if (!flag.equalsIgnoreCase("1"))
			tab+="<tr><Td align=right colspan=5><font size='3' color=blue><b>Total</b></font></td><td></td><td class='tdText'  align=right><font size='3' color=blue><b>"+sch_tot+"</b></font></td></tr>";
			tab+="<tr ><td colspan='5' align=right class=tdText><font color='red' size=4>Net Total</font></td><td align=right class=tdText><font color='red' size=4><b>"+grand_pr+"</b></font></td><td align=right class=tdText><font color='red' size=4><b>"+tot_+"</b></font></td></tr></table>";
			 
	// command => pr_select  process =2  end 		
 } catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
   
		
	 		
// command => pr_select  	
	
	
	
	
	
	
	
	
	

}catch(Exception e) {
	 
	out.println(e);
	 //response.sendRedirect(request.getContextPath()+"/index.jsp");
}	

  String []cmonth ={"-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
	 
%>
 <input type="hidden" id="month" value="<%=month%>">
<input type="hidden" id="year" value="<%=year%>"> 
 <form>

<table  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  width="75%" cellpadding="5"  cellspacing="0" align="center">
<tr  class="tdText">
<td align="center" colspan="4">Beneficiary Water Charges - <%=Office_Name%> <label id="msg"></label> 
</td>
</tr>
 <tr  > 

    <td colspan="2" align="left" class="tdText">  Month/Year : </td><td colspan="2" class="tdText"> <%=cmonth[month]%>&nbsp;/&nbsp;<%=year%></td>
    
	 
</tr>
 <tr > 
 	<td colspan="4" align="center"><input class="fb2" type="button" onclick="window.history.go(-1)" value="Back">&nbsp;<input  class="fb2" type=button value="Exit" onclick="window.close()"></td>
 </tr>
<tr>
<td colspan="4"><%=tab%></td>
 </tr>
  <tr>
 	<td colspan="4" align="center"><input class="fb2" type="button" onclick="window.history.go(-1)" value="Back">&nbsp;<input  class="fb2" type=button value="Exit" onclick="window.close()"></td>
 </tr>
 </table>
		   <input type="hidden" id="pr_status" name="pr_status" value="0"> 
		   <input type="hidden" id="row_count" name="row_count" value="0">
		   <input type="hidden" id="freezs" name="freezs" value="1">
		   <input type="hidden" id="sp_flag" name="freezs" value="2">
		   <input type="hidden" id="subdiv" name="subdiv" value="1">
		   

</form>
<%
	obj.conClose(con);
%>
</body>
</html>