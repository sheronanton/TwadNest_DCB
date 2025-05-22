<%@page import="java.text.DecimalFormat"%><%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
Connection db_con;
Controller obj_new=new Controller();
Controller obj_new2=new Controller();
try    
{     
	db_con=obj_new.con();
	
	obj_new.createStatement(db_con);
 	response.setContentType("text/xml");
 	String pyear=obj_new.setValue("pyear",request);
 	String pmonth=obj_new.setValue("pmonth",request);
 	String add_cond=conv(obj_new.isNull(obj_new.setValue("add_cond",request),3));
 	String off=conv(obj_new.isNull(obj_new.setValue("off",request),3));
 	String add_cond2=conv(obj_new.setValue("add_cond2",request));   
 	String schtype=conv(obj_new.setValue("schtype",request));
 	String BEN_TYPE_GROUP=conv(obj_new.setValue("BEN_TYPE_GROUP",request));
	if (Integer.parseInt(schtype)==1) 
		add_cond=" ";// and BEN_TYPE_GROUP not in (8) ";  
	else
		add_cond=" ";
	if (Integer.parseInt(schtype)==1) 
		add_cond2=" BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"";  
	else
		add_cond2=" BEN_TYPE_ID_SUB in (6) ";  
 	String user_query=" SELECT ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,3),2) AS ob,"+
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
	" )rpt  ORDER BY DISTRICT_CODE,  DISTRICT_NAME ";   
 	 XmlDataGenerator xml=new XmlDataGenerator(db_con,obj_new);
 	   
	 String []head_columns={"Attribute","Rs. (in lakhs)"};
 	 String []aHead={"Opening Balance","Demand","Collection","Balance"}; 
 	 String []colorcode={"886A08" ,"610B5E","81DAF5","0174DF"};  
 	 String []qry_column={"ob","dmd","collection","balance"}; 
 	   
 	String xml_string="";  
	 try  
	 {  
		 xml.district_char(user_query,head_columns,aHead,colorcode,qry_column);  
		  xml_string=xml.getRes_xml();
	 }catch(Exception e){}
 	  
 	out.println(xml_string);    
}catch(Exception e) 
{  
	out.println(e);	     
}
%>
<%!
public String conv(String sv)
{  
	String res="";
	if (sv.equalsIgnoreCase("0")) res=""; else res=sv;
	return res;
}
%>