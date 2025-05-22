 <%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<%@ page language="java" contentType="text/xml; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="pms.dcb.webservices.GIS"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.Connection"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<%
try
{
	response.setContentType("text/xml");
	Controller obj_con=new Controller();
	Connection con1=obj_con.con();
	String type=obj_con.setValue("type",request);
	String year=obj_con.setValue("year",request);
	String month=obj_con.setValue("month",request);
	String desc=obj_con.setValue("desc",request);
	System.out.println(desc);  
	desc=desc.replaceAll("\\.","&nbsp;");     
String qry="SELECT DISTRICT_CODE,DISTRICT_NAME,ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,3),2) AS ob,"+
" ROUND(TRUNC(DMD_FOR_MTH_WC_10,3),2)   AS dmd,ROUND(TRUNC((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2) AS collection,"+ 
" ROUND(TRUNC(BALANCE_18,3),2) AS balance "+
" FROM "+
" (SELECT SUM( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8))AS OPENING_BAL_WC_8, "+
  " SUM( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))     AS DMD_UPTO_PRV_MNTH_WC_9, "+
  " SUM( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10))     AS DMD_FOR_MTH_WC_10, "+
  " SUM( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
  " SUM( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
  " SUM( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))AS COLN_FOR_MTH_YES_YR_WC_14, "+
  " SUM( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))   AS COLN_FOR_MTH_WC_15, "+
  " SUM( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))       AS BALANCE_18,ac.MONTH ,ac.YEAR ,ac.DISTRICT_CODE ,ac.DISTRICT_NAME "+
" FROM "+
  " (SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC))          / 100000 AS OPENING_BAL_WC_8, "+
    " DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))    / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
    " DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC))     / 100000 AS DMD_FOR_MTH_WC_10, "+
    " DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
    " DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
    " DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))/ 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
    " DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))    / 100000 AS COLN_FOR_MTH_WC_15, "+
    " DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))      / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
    " DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))  / 100000 AS BALANCE_18,DISTRICT_CODE,DISTRICT_NAME,MONTH,YEAR "+
  " FROM PMS_DCB_LEDGER_ACTUAL WHERE YEAR="+year+" AND MONTH="+month+" and BEN_TYPE_ID_SUB="+type+" GROUP BY YEAR,MONTH,DISTRICT_CODE,DISTRICT_NAME "+
  " )ac GROUP BY YEAR,MONTH,DISTRICT_CODE,DISTRICT_NAME "+
" ) rpt ORDER BY DISTRICT_CODE ";


String mvalue=obj_con.month_val(month);
String []head_columns={"Attribute","Rs. (in lakhs)"};   
String []aHead={"Opening Balance","Demand","Collection","Balance"}; 
String []colorcode={"30EAFF" ,"6C4FFF","63FF69","FF1F44"};  
String []qry_column={"ob","dmd","collection","balance"};

obj_con.createStatement(con1);   
XmlDataGenerator xml=new XmlDataGenerator(con1,obj_con);        
   
	String xml_string="";
 try  
 {  
	  xml.title="DCB District Abstract for the month of "+mvalue+"  "+year+" - "+desc ;     
	  xml.district_char_test(qry,head_columns,aHead,colorcode,qry_column);
	  xml_string=xml.getRes_xml();
 }catch(Exception e){ out.println(e);}
	  
	 out.println(xml_string);   
}catch(Exception e) {out.println(e);}
%>
