
<%@page import="java.text.DecimalFormat"%>  
<%@ page language="java"
	import="Servlets.PMS.PMS1.DCB.servlets.*,java.sql.*,java.io.*"
	%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
 
 <%   
 	String new_cond=Controller.new_cond;
	 response.setContentType("text/html");
	//response.setHeader("Cache-Control", "no-cache");
	 java.sql.Connection con = null;
	 Controller obj = new Controller();
	 con = obj.con();
	 obj.createStatement(con);
	 String strCommand=obj.setValue("command", request);
	 String bensno=obj.setValue("ben", request);
	 String rate=obj.setValue("rate", request);
	 ResultSet result=null;
	 Statement statement=null;
	 String xml;
	 statement=con.createStatement();
	 con.clearWarnings();
		DecimalFormat d=new DecimalFormat("0.00");	  
	  try
	  {
		  
		  if (strCommand.trim().equalsIgnoreCase("update"))
		  {
			  PreparedStatement update_rs=con.prepareStatement("update PMS_DCB_MST_BENEFICIARY set INT_RATE="+rate+" where BENEFICIARY_SNO="+bensno);
			  update_rs.executeUpdate();
		  }else
		  {
			  rate=obj.getValue("PMS_DCB_MST_BENEFICIARY","INT_RATE"," where "+new_cond+" BENEFICIARY_SNO="+bensno);
		  }
		  
		  
		  response.getWriter().write("text" + "|" +d.format(Double.parseDouble(rate)));  
		  
		  
	  }catch(Exception e) {}
%>