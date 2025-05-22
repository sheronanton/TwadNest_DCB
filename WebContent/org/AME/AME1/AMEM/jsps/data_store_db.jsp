<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="Servlets.AME.AME1.AMEM.servlets.excel2"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.io.*"%>
<%@page import="java.sql.Connection"%> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

 
<%  Controller obj=new Controller();
	Connection con=obj.con();
	obj.createStatement(con);
	 String	userid = (String) session.getAttribute("UserId");
	 String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		if (Office_id.equals("")) Office_id="0";
		String itemName=obj.setValue("itemName",request);
	String []col={"TWAD_SCH_SNO","DESIGN_QTY","PUMPING_QTY","MONTH","YEAR"};
	File f1=new File(application.getRealPath("/excel")) ;
	File f2=  new File(f1,itemName);  
 
     String month=obj.setValue("month",request);
     String year=obj.setValue("year",request);
     excel2.read(f2,con,obj,Office_id,"PMS_AME_TRN_SCH_DPQTY",col,6);   
    //String []col={"PROJECT_ID","PROJECT_DESC"};  
    //excel2.read(f2,con,obj,Office_id,"TWAD_SCH_MASTER",col,7);
    //   
      
    
	//System.out.println(" batchupdate Before " + month +"-"+year);
 	excel2.batchupdate(1,Office_id,month,year,obj,con); 
    //System.out.println(" batchu pdate Last ");
    
    response.sendRedirect("excelUpload.jsp");
    
 // NEW 
 /*
    String []col={"TWAD_SCH_SNO","DESIGN_QTY","PUMPING_QTY","MONTH","YEAR"}; 
    excel2.read(f2,con,obj,Office_id,"PMS_AME_TRN_SCH_DPQTY",col,6);   
 */   
     
 
 
 
 %>
</body>
</html>