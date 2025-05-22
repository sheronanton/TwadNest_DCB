/* 
 		* Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By			Mode 
 *-----------------------------------------------------------------------------
 * 
 * 26/08/2013	added code for generate statment II		Panneer			M		 
 *              Report 
 * 27,28,29,30/08/2013	added code for generate statment II		Panneer			M
 * 
 * 02/09/13		Tariff Revised Report Start 
 *             
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
public class Pms_Dcb_Ledger_Report extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    public Pms_Dcb_Ledger_Report() {
        super();
    } 
	private static final String cr_label="Rs in Crores";  
	private static final String lk_label="Rs in Lakhs";
	private static final String ac_label="Rs";
	private static final int cr_fig=10000000;
	private static final int lk_fig=100000;
	private static final int ak_fig=1;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		   response.setContentType(CONTENT_TYPE);
           Connection connection = null;
           Controller obj=new Controller();
           String imagespath="";
           String ctxpath="";
  		   try
  		   {
  			  connection =obj.con(); 
  			  obj.createStatement(connection);  
  		   } catch (Exception ex)
           {
               String connectMsg ="Could not create the connection" + ex.getMessage() + " " +ex.getLocalizedMessage();
           }
           Calendar cal = Calendar.getInstance();
		   int dy=cal.get(Calendar.DAY_OF_YEAR);   
           Map parameters = new HashMap();
           String path = "",  select_count="0";
           HttpSession session = request.getSession(false);
           String userid="",Office_id="0";
           try { userid = (String) session.getAttribute("UserId"); } catch(Exception e) {userid="0";} 
           if (userid == null)
           {
        				response.sendRedirect(request.getContextPath() + "/index.jsp");
           }
        		try {
        	
       	 	//	  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

        			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	
        //			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
        		} catch (Exception e1) {
        			e1.printStackTrace(); 
        		}
           try {  
        	   
        	     String pr=obj.setValue("pr", request);
        	     String rin=obj.setValue("rin", request);
        	     String month=obj.setValue("month", request);
        	     String year=obj.setValue("year", request);
        	     select_count=obj.setValue("select_count", request);
        	     System.out.println("DCB->Pms_Dcb_Ledger_Report ->process_code->"+pr+"->rin->" +rin+"-->select_count->"+select_count);
        	     String ten_word="";
        	     int tbcount=0;
        	     int divcount=0;
        	     String userquery="select  dcount,tbcount from ( select count(distinct m.office_id) as dcount from PMS_DCB_DIV_DIST_MAP m where  m.office_id in (select office_id from PMS_DCB_TRN_MONTHLY_PR ) ) div "+
        	     " join ( select count(distinct m.office_id) as tbcount  from FAS_TRIAL_BALANCE_STATUS t ,FAS_MST_ACCT_UNITS v ,PMS_DCB_DIV_DIST_MAP m where t.cashbook_month="+month+"  and t.cashbook_year= "+year+
        	     " and t.accounting_unit_id=v.accounting_unit_id and m.office_id=v.accounting_unit_office_id and t.TB_STATUS='Y' and m.office_id in (select office_id from PMS_DCB_TRN_MONTHLY_PR ) "+
        	     " )tb on (1=1)";
        	     ResultSet rsc=obj.getRS(userquery);        	     
        	     if (rsc.next())
        	     {  
        	    	 tbcount=rsc.getInt("tbcount");
        	    	 divcount=rsc.getInt("dcount");
        	     }
        	       userquery="SELECT  sum( sum1) as s1,sum(sum2) as s2  FROM ( "+
        	    		    " SELECT count(DISTINCT beneficiary_sno) as sum1,office_id  FROM PMS_DCB_TRN_MONTHLY_PR  WHERE MONTH="+month+" AND YEAR   ="+year+" and beneficiary_sno in (select beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L')  group by office_id "+  
        	    		   " )tot_ben left join  ( "+
        	    		   " SELECT count(DISTINCT beneficiary_sno)  as sum2,office_id  FROM PMS_DCB_LEDGER_ACTUAL   WHERE  MONTH="+month+"  AND  YEAR   ="+year+" and beneficiary_sno in (select beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L')  group by office_id"+  
        	    		    " )tot_bill  on tot_bill.office_id=tot_ben.office_id";
        	       rsc=null;
        	     int benct=0,ledgerct=0;
        	     rsc=obj.getRS(userquery);        	     
          	     if (rsc.next())
          	     {
          	    	benct=rsc.getInt("s1");
          	    	ledgerct=rsc.getInt("s2");
          	     }
          	   userquery="SELECT  sum( sum1) as s1,sum(sum2) as s2  FROM ( "+ 
          	   			 " SELECT count(DISTINCT beneficiary_sno) as sum1,office_id  FROM PMS_DCB_TRN_MONTHLY_PR  WHERE MONTH="+month+" AND YEAR   ="+year+" and beneficiary_sno in (select beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L')  group by office_id"+  
          	   			 " )tot_ben left join  ( "+
          				 "  SELECT count(DISTINCT beneficiary_sno)  as sum2,office_id  FROM PMS_DCB_TRN_BILL_DMD   WHERE  Bill_MONTH="+month+"  AND  Bill_YEAR   ="+year+" and beneficiary_sno in (select beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L')  group by office_id "+  
          				 " )tot_bill  on tot_bill.office_id=tot_ben.office_id";
          	 rsc=null;
          	 rsc=obj.getRS(userquery);     
    	     int benct2=0,billct=0; 
    	     rsc=obj.getRS(userquery);        	     
      	     if (rsc.next())
      	     {
      	    	benct2=rsc.getInt("s1");
      	    	billct=rsc.getInt("s2");
      	     }  
        	     if (tbcount==divcount && benct2==billct && benct==ledgerct )
        	    	 ten_word="";
        	    	 else  
        	    	 ten_word="Tentative";
    			  if (Integer.parseInt(pr)==1)
    			 {	
    				  //DCB_RPT_Latest old 
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RPT_Latest.jasper");
    				 parameters.put("office_id",obj.setValue("div", request));
    				 imagespath = getServletContext().getRealPath("/images/")+File.separator;
					 parameters.put("imgpath", imagespath);
					 System.out.println("parameters"+parameters);
    			 } else if (Integer.parseInt(pr)==2) 
    			 {      
     				if (Integer.parseInt(rin)==1) 
         			{	
     					System.out.println("rinnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
     				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RPT_Div_Latest.jasper");
     				imagespath = getServletContext().getRealPath("/images/")+File.separator;
 					parameters.put("imgpath", imagespath);
         			}
     				else
     				{
     				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RPT_Actual.jasper");
     				imagespath = getServletContext().getRealPath("/images/")+File.separator;
     				parameters.put("imgpath", imagespath);
     				}
     				parameters.put("office_id",obj.setValue("div", request));
     				parameters.put("off_name",obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+obj.setValue("div", request)));
     			 
    			 System.out.println("parameterssssssssssssssssssssssssss"+parameters);
    			 }else if (Integer.parseInt(pr)==31)
    			 {	
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RPT_Actual_int.jasper");
      				imagespath = getServletContext().getRealPath("/images/")+File.separator;
      				parameters.put("imgpath", imagespath);
      				parameters.put("office_id",obj.setValue("div", request));
     				parameters.put("off_name",obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+obj.setValue("div", request)));
    				 
    			 }
    			 
    			 else if (Integer.parseInt(pr)==3)
    			 {	
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/dcb_stmt_ho.jasper");
    				 parameters.put("office_id",obj.setValue("div", request));
    				 parameters.put("office_name",obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+obj.setValue("div", request)));
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    			 }else if (Integer.parseInt(pr)==4)
    			 {	
    				 String YEAR=obj.setValue("year", request);
    				 String YEAR2=obj.setValue("year2", request);
    				 String daay=obj.setValue("daay", request);
    				 String daay2=obj.setValue("daay2", request);
    				 String monnth=obj.setValue("monnth", request);
    				 String monnth2=obj.setValue("monnth2", request);
    				 
    				 
    				 
    				 String day=obj.setValue("day", request);
    				 String splflag=obj.setValue("splflag", request);
    				 if (splflag.equalsIgnoreCase("1"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/div_Wise_sch_qty_new_HO.jasper");
    				 else if (splflag.equalsIgnoreCase("2"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/schwise_supply_fin.jasper"); 
    				 else if (splflag.equalsIgnoreCase("3"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/schwise_supply_fin_priv.jasper"); 
    				 else if (splflag.equalsIgnoreCase("5"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/Scheme_bentype.jasper"); 
    				 else if (splflag.equalsIgnoreCase("6"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/schwise_pr.jasper"); 
    				 else if (splflag.equalsIgnoreCase("7"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/schwise_ben.jasper"); 
    				 else if (splflag.equalsIgnoreCase("4"))  
    				 {
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/arrear_div.jasper"); 
        				 parameters.put("office_name",obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+obj.setValue("div", request)));
    				 }
    				 else
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/div_Wise_sch_qty_new_Div.jasper");
    				 parameters.put("daay",Integer.parseInt(daay));
    				 parameters.put("daay2",Integer.parseInt(daay2));
    				 parameters.put("monnth",Integer.parseInt(monnth));
    				 parameters.put("monnth2",Integer.parseInt(monnth2));
    				 parameters.put("year1",Integer.parseInt(YEAR));
    				 parameters.put("year2",(Integer.parseInt(YEAR2)));  
    				 parameters.put("dy",(Integer.parseInt(day)));    				  
    				 parameters.put("office_id",Integer.parseInt(obj.setValue("div", request)));
    			 }else if (Integer.parseInt(pr)==5)
    			 {	        
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_GOVT_REPORT1.jasper");
    				 parameters.put("office_id",obj.setValue("div", request));
    				 parameters.put("office_name",obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+obj.setValue("div", request)));
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    			 } 
    			 
    			 // Newly added for private_details.jsp
    			 else if (Integer.parseInt(pr) == 1111)
    			 {	        
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Private_detail_all.jasper");
    				 parameters.put("month", month);
    				 parameters.put("year", year );
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    			 } 
    			  // Newly added for private_details.jsp
    			 else if (Integer.parseInt(pr) ==1112)
    			 {	        
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Private_detail_div.jasper");
    				 parameters.put("office_id",obj.setValue("div", request));
    				 parameters.put("month", month);
    				 parameters.put("year", year );
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    			 } 
    			 
    			 
    			 
    			 
    			 else if (Integer.parseInt(pr)==7)
    			 {	      
    				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Dash_Board_Div.jasper");
     				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
     				 String cdate =obj.setValue("cdate", request);
     				 String []mt=cdate.split("/");  //   
     				 int prv_month=Integer.parseInt(mt[1])-2;
     				 if (prv_month >=4 && prv_month <=12)
     				 {  
     					 year=mt[2];
     				 }else       
     				 {
     					 year=Integer.toString(Integer.parseInt(mt[2])-1);
     				 }
     				 String current_month=Integer.toString(Integer.parseInt(mt[1])-1);      
     				 month=current_month;     				    
     				 String current_month_sdate="01/"+mt[1]+"/"+mt[2];
     				 String current_month_edate=cdate;
     				 String current_year_upmonth=Integer.toString(prv_month);  
     				 String current_year_sdate="01/04/"+mt[2];  
     				 String current_year_edate= obj.month_val2(prv_month)+"/"+Integer.toString(prv_month)+"/"+mt[2];
     				 parameters.put("mvalue", obj.month_val(month));
     				 parameters.put("cdatelue", obj.month_val(obj.setValue("month", request)));
     				 parameters.put("days", obj.month_val2(Integer.parseInt(month)));
     				 parameters.put("current_month_sdate",current_month_sdate);
     				 parameters.put("current_month_edate",current_month_edate);
     				 parameters.put("current_year_upmonth",current_year_upmonth);
     				 parameters.put("current_year_sdate",current_year_sdate);
     				 parameters.put("current_year_edate",current_year_edate);
     				 parameters.put("prv_month",prv_month);    
    			 }else if (Integer.parseInt(pr)==9)  
    			 {	      
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Scheme_wise_Collection.jasper");
    				 String off_id=obj.setValue("off_id", request);
    				 obj.dis(Office_id);
    				 if (Office_id.equalsIgnoreCase("5000") || Office_id.equalsIgnoreCase("0")) Office_id=off_id;
     				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
     				 parameters.put("office_id",Office_id);
     				 parameters.put("office_name",obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+Office_id));
    			 } else if (Integer.parseInt(pr)==10)  
    			 {	      
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Scheme_Wise_Collection_Head.jasper");
    				   ctxpath =request.getRequestURL().toString();
    				 parameters.put("ctxpath", ctxpath);   
     				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
     				 /// Changes Here 
    			 }   else if (Integer.parseInt(pr)==6)  
    			 {	    
    				 String splflag=obj.setValue("splflag", request);    			 
    				 if (splflag.equalsIgnoreCase("1"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/sch_performance_HO_MonthWise.jasper");    
    				 else if (splflag.equalsIgnoreCase("2"))    
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/schwise_supply_month.jasper"); 
    				 else
    					 path = getServletContext().getRealPath("/WEB-INF/PDF/sch_performance_Div_monthwise.jasper");
    			   //  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Dash_Board_Div_Revised2.jasper");
    				 parameters.put("dy",obj.month_val2(Integer.parseInt(obj.setValue("month", request))));	   
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    				 parameters.put("office_id",Integer.toString(Integer.parseInt(obj.setValue("div", request))));
    			 } else if (Integer.parseInt(pr)==11)  
    			 {
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Replaced_MD_Review_Head_Abs.jasper");
    				 ctxpath = path.substring(0, path.lastIndexOf("New_Replaced_MD_Review_Head_Abs.jasper"));
    				 String curset=Integer.parseInt(month)+"/"+Integer.parseInt(year);
    				 String arr_set="";       
    				 int prv_month=0,prv_year=0;  
    				 if (Integer.parseInt(month)==1)
    				 {  
    					 prv_month=12;
    					 prv_year=Integer.parseInt(year)-1;
    					 arr_set =Integer.parseInt("12")+"/"+(Integer.parseInt(year)-1);
    				 }
    				 else  
    				 {
    					 prv_month=Integer.parseInt(month)-1;
    					 prv_year=Integer.parseInt(year);
    					 arr_set =(Integer.parseInt(month)-1)+"/"+(Integer.parseInt(year));
    				 }
    				 parameters.put("prv_year",prv_year);    
    				 parameters.put("prv_month",prv_month);  
    				 parameters.put("arr_set", arr_set);   
     				 parameters.put("curset",curset);
    			 }  else if (Integer.parseInt(pr)==12)  
    			 {	     
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Replaced_MD_Review_Head.jasper");   
    				 ctxpath = path.substring(0, path.lastIndexOf("New_Replaced_MD_Review_Head.jasper"));
    				 String curset=Integer.parseInt(month)+"/"+Integer.parseInt(year);
    				 String arr_set="";  
    				 int prv_month=0,prv_year=0;
    				 if (Integer.parseInt(month)==1)
    				 {
    					 prv_month=12;
    					 prv_year=Integer.parseInt(year)-1;
    					 arr_set =Integer.parseInt("12")+"/"+(Integer.parseInt(year)-1);
    				 }
    				 else    
    				 {
    					 prv_month=Integer.parseInt(month)-1;
    					 prv_year=Integer.parseInt(year);
    					 arr_set =(Integer.parseInt(month)-1)+"/"+(Integer.parseInt(year));
    				 }
    				 parameters.put("monthval", obj.month_val(obj.setValue("month", request)));
    				 parameters.put("prv_year",prv_year);    
    				 parameters.put("prv_month",prv_month);    
    				 parameters.put("arr_set", arr_set);     
     				 parameters.put("curset",curset);  
    			 }else if (Integer.parseInt(pr)==121)  
    			 {	        
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Replaced_MD_Review_Head_ben.jasper");   
    				 ctxpath = path.substring(0, path.lastIndexOf("New_Replaced_MD_Review_Head_ben.jasper"));
    				 String curset=Integer.parseInt(month)+"/"+Integer.parseInt(year);
    				 String arr_set="";  
    				 int prv_month=0,prv_year=0;
    				 if (Integer.parseInt(month)==1)
    				 {
    					 prv_month=12;
    					 prv_year=Integer.parseInt(year)-1;
    					 arr_set =Integer.parseInt("12")+"/"+(Integer.parseInt(year)-1);
    				 }
    				 else    
    				 {
    					 prv_month=Integer.parseInt(month)-1;
    					 prv_year=Integer.parseInt(year);
    					 arr_set =(Integer.parseInt(month)-1)+"/"+(Integer.parseInt(year));
    				 }
    				 parameters.put("monthval", obj.month_val(obj.setValue("month", request)));
    				 parameters.put("prv_year",prv_year);    
    				 parameters.put("prv_month",prv_month);    
    				 parameters.put("arr_set", arr_set);   
     				 parameters.put("curset",curset);  
    			 } else if (Integer.parseInt(pr)==14)  
    			 {	    
    				 String YEAR=obj.setValue("year", request);
    				 int month1 = Integer.parseInt(month)+1 ;
 					int year1=0;
 					if (Integer.parseInt(month)==12)
 					{
 						year1=Integer.parseInt(YEAR)+1;
 						month1=1;	
 					}else  
 					{
 						year1=Integer.parseInt(YEAR);
 						month1=Integer.parseInt(month);	
 					}
 					String sdate=1+"/"+month1+"/"+year1;
 					 int next_month=(Integer.parseInt(month)==12)?1:Integer.parseInt(month);
 					 String edate=obj.month_val2(next_month)+"/"+month1+"/"+year1;
    			     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Dash_Board_Div_Revisednew.jasper");
    			     System.out.println("###################"+path);
    			     ctxpath = path.substring(0, path.lastIndexOf("DCB_Dash_Board_Div_Revisednew.jasper"));  
    			     System.out.println("###################subreport"+ctxpath);
    			    parameters.put("days",obj.month_val2(Integer.parseInt(month)));	     
    				 parameters.put("current_month_sdate",sdate);
     				 parameters.put("current_month_edate",edate);     
    				 parameters.put("mvalue", obj.month_val(month));  
    				 parameters.put("ctxpath", ctxpath);  
    			 } else if (Integer.parseInt(pr)==15)     
    			 {	         
    				 String splflag=obj.setValue("splflag", request);
    			     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Dash_Board_Div_Revised2.jasper");
    				 parameters.put("dy",obj.month_val2(Integer.parseInt(obj.setValue("month", request))));	   
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    				 parameters.put("office_id",Integer.toString(Integer.parseInt(obj.setValue("div", request))));
    			 }else if (Integer.parseInt(pr)==160)     
    			 {

    				 String YEAR=obj.setValue("year", request);
    				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/FIN_SCH_WISE.jasper");
    				 parameters.put("year1",Integer.parseInt(YEAR));
    				 parameters.put("year2",(Integer.parseInt(YEAR)+1));
    				 parameters.put("finyear",(Integer.parseInt(YEAR))+"-"+( Integer.parseInt(YEAR)+1));
    				 String Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
    				 parameters.put("office_name",Office_name);
    				 parameters.put("office_id",Office_id);
    			 }
    			 else if (Integer.parseInt(pr)==161)     
    			 {

    				 String YEAR=obj.setValue("year", request);
    				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Month_SCH_WISE.jasper");
    				 parameters.put("year",Integer.parseInt(YEAR));
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    				 String Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
    				 parameters.put("office_name",Office_name);
    				 parameters.put("office_id",Office_id);
    			 }     			 
    			 else if (Integer.parseInt(pr)==16)
    			 {	  
    				 String week1_sdate="",week1_edate="";
    				 String week2_sdate="",week2_edate="";
    				 String week3_sdate="",week3_edate="";
    				 String week4_sdate="",week4_edate="";
    				 int prv_month=0,prv_year=0;
    				 if (Integer.parseInt(month)==1)    
    				 {
    					 prv_month=12;
    					 prv_year=Integer.parseInt(year)-1;
    				 }  
    				 else    
    				 {
    					 prv_month=Integer.parseInt(month)-1;
    					 prv_year=Integer.parseInt(year);
    				 }
    				parameters.put("prv_month",prv_month);
    				parameters.put("prv_year",prv_year);   
    				String day1 = obj.month_val2(Integer.parseInt(month));
    				int  april_year=0;
    				
    				if (Integer.parseInt(month)<=3)
    					april_year=Integer.parseInt(year)-1;
    				else  
    					april_year=Integer.parseInt(year);
    				
    				int up_to_month=0,next_up_to_month1=0,next_up_to_month2=0,next_year=0;
    				if (Integer.parseInt(month)==1)
    				{
    					up_to_month=12;   
    					next_up_to_month1=0;
    					next_up_to_month2=0;
    					next_year=Integer.parseInt(year);
    				}else if (Integer.parseInt(month)>1 && (Integer.parseInt(month)<4))
    				{
    					up_to_month=12;   
    					next_up_to_month1=1;
    					next_up_to_month2=Integer.parseInt(month)-1;
    					next_year=Integer.parseInt(year);
    				}else
    				{
    					up_to_month=12;   
    					next_up_to_month1=0;
    					next_up_to_month2=0;
    					next_year=0;
    				}
    				parameters.put("next_up_to_month2",next_up_to_month2);  
    				parameters.put("next_up_to_month1",next_up_to_month1);   	
    				parameters.put("up_to_month",up_to_month);
    				parameters.put("next_year",next_year);   
  					String current_year_sdate=1+"/5/"+april_year;        
  					String current_year_edate=day1+"/"+Integer.parseInt(month)+"/"+year;  
    				 week1_sdate=obj.setValue("sdate1", request);week1_edate=obj.setValue("edate1", request);
					 week2_sdate=obj.setValue("sdate2", request);week2_edate=obj.setValue("edate2", request);
					 week3_sdate=obj.setValue("sdate3", request);week3_edate=obj.setValue("edate3", request);
					 week4_sdate=obj.setValue("sdate4", request);week4_edate=obj.setValue("edate4", request);
    				   parameters.put("week1_sdate",week1_sdate);parameters.put("week1_edate",week1_edate);
					   parameters.put("week2_sdate",week2_sdate);parameters.put("week2_edate",week2_edate);
					   parameters.put("week3_sdate",week3_sdate);parameters.put("week3_edate",week3_edate);  
					   parameters.put("week4_sdate",week4_sdate);parameters.put("week4_edate",week4_edate);
					   
					   					   
    				
    				 
					 
    				 if (select_count.equalsIgnoreCase("4"))
    				 {  
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revised2_4_week.jasper");
    					 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revised2_4_week.jasper"));
    				 }else if (select_count.equalsIgnoreCase("3"))     
    				 {      
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revised2_3_week.jasper");
    					 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revised2_3_week.jasper"));
    				 }else if (select_count.equalsIgnoreCase("2"))
    				 {
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revised2_2_week.jasper");
    					 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revised2_2_week.jasper"));
    				 }else   
        			 {
        			 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revised2_1_week.jasper");
        			 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revised2_1_week.jasper"));
        			 }      
    				
    				 String current_year_sdate_ap=1+"/4/"+april_year;         
	  				 String current_year_edate_ap="30/4/"+april_year;
    				 parameters.put("april_year",april_year);
    				 parameters.put("prv_month",prv_month); 
    				 parameters.put("current_year_sdate",current_year_sdate);
    				 parameters.put("current_year_edate",current_year_edate);    	   			    				
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    				 parameters.put("current_year_sdate_ap",current_year_sdate_ap);
	     			 parameters.put("current_year_edate_ap",current_year_edate_ap);  
					 parameters.put("ctxpath", ctxpath);    
	     			 
//	     			System.out.println(" current_year_sdate is--> "+current_year_sdate);
//	     			System.out.println(" current_year_edate is--> "+current_year_edate);
//	     			System.out.println(" current_year_sdate_ap is--> "+current_year_sdate_ap);
//	     			System.out.println(" current_year_edate_ap is--> "+current_year_edate_ap);
//	     			System.out.println(" week1_sdate is--> "+week1_sdate);
//	     			System.out.println(" week1_edate is--> "+week1_edate);
//	     			
//	     			
//	     			System.out.println(" prv_month is--> "+prv_month);
//	     			System.out.println(" next_year is--> "+next_year);
//	     			System.out.println(" next_up_to_month1 is--> "+next_up_to_month1);
//	     			System.out.println(" next_up_to_month2 is--> "+next_up_to_month2);
	     			
	     			
	     			 
    			 }else if (Integer.parseInt(pr)==17)      
    			 {
       			  // Tariff Revised Report Start 
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Type_Wise_tariff_revision.jasper");	 
          		  // Tariff Revised Report Start
    						parameters.put("year1",(Integer.toString(Integer.parseInt(obj.setValue("year", request))+1)) ); 
    			 }
    			 else if (Integer.parseInt(pr)==18)     
    			 {
    				 String splflag=obj.setValue("splflag", request);
    				 if (splflag.equals("18"))
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_Ben_Type_Demand.jasper");	 
    				 else if (splflag.equals("19"))
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_Ben_Type_Demand1.jasper");
    				 else if (splflag.equals("20"))
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_Ben_Type_Demand2.jasper");
    				 else if (splflag.equals("4"))
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_Ben_Type_Demand3.jasper");
    				 else if (splflag.equals("5"))
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_Ben_Type_Demand4.jasper");
    			 
    			 }else if (Integer.parseInt(pr)==19)     
    			 {	  
    				 String week1_sdate="",week1_edate="";
    				 String week2_sdate="",week2_edate="";
    				 String week3_sdate="",week3_edate="";
    				 String week4_sdate="",week4_edate="";
    				 int prv_month=0,prv_year=0;
    				 if (Integer.parseInt(month)==1)    
    				 {
    					 prv_month=12;
    					 prv_year=Integer.parseInt(year)-1;
    				 }  
    				 else    
    				 {
    					 prv_month=Integer.parseInt(month)-1;
    					 prv_year=Integer.parseInt(year);
    				 }
    				parameters.put("prv_month",prv_month);
    				parameters.put("prv_year",prv_year);   
    				String day1 = obj.month_val2(Integer.parseInt(month));
    				int  april_year=0;
    				
    				if (Integer.parseInt(month)<=3)
    					april_year=Integer.parseInt(year)-1;
    				else  
    					april_year=Integer.parseInt(year);
    				
    				int up_to_month=0,next_up_to_month1=0,next_up_to_month2=0,next_year=0;
    				if (Integer.parseInt(month)==1)
    				{
    					up_to_month=12;   
    					next_up_to_month1=0;
    					next_up_to_month2=0;
    					next_year=Integer.parseInt(year);
    				}else if (Integer.parseInt(month)>1 && (Integer.parseInt(month)<4))
    				{
    					up_to_month=12;   
    					next_up_to_month1=1;
    					next_up_to_month2=Integer.parseInt(month)-1;
    					next_year=Integer.parseInt(year);
    				}else
    				{
    					up_to_month=12;   
    					next_up_to_month1=0;
    					next_up_to_month2=0;
    					next_year=0;
    				}
    				parameters.put("next_up_to_month2",next_up_to_month2);  
    				parameters.put("next_up_to_month1",next_up_to_month1);   	
    				parameters.put("up_to_month",up_to_month);
    				parameters.put("next_year",next_year);   
  					String current_year_sdate=1+"/5/"+april_year;        
  					String current_year_edate=day1+"/"+Integer.parseInt(month)+"/"+year;  
    				 week1_sdate=obj.setValue("sdate1", request);week1_edate=obj.setValue("edate1", request);
					 week2_sdate=obj.setValue("sdate2", request);week2_edate=obj.setValue("edate2", request);
					 week3_sdate=obj.setValue("sdate3", request);week3_edate=obj.setValue("edate3", request);
					 week4_sdate=obj.setValue("sdate4", request);week4_edate=obj.setValue("edate4", request);
    				   parameters.put("week1_sdate",week1_sdate);parameters.put("week1_edate",week1_edate);
					   parameters.put("week2_sdate",week2_sdate);parameters.put("week2_edate",week2_edate);
					   parameters.put("week3_sdate",week3_sdate);parameters.put("week3_edate",week3_edate);  
					   parameters.put("week4_sdate",week4_sdate);parameters.put("week4_edate",week4_edate);
					   
					   
					   
    				 if (select_count.equalsIgnoreCase("4"))
    				 {  
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revabs2_4_week.jasper");
    					 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revabs2_4_week.jasper"));
    				 }else if (select_count.equalsIgnoreCase("3"))      
    				 {      
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revabs2_3_week.jasper");
    					 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revabs2_3_week.jasper"));
    				 }else if (select_count.equalsIgnoreCase("2"))
    				 {
    					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revabs2_2_week.jasper");
    					 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revabs2_2_week.jasper"));
    				 }else   
        			 {
        			 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Board_Revabs2_1_week.jasper");
        			 ctxpath = path.substring(0, path.lastIndexOf("DCB_Board_Revabs2_1_week.jasper"));  
        			 }      
    				 String current_year_sdate_ap=1+"/4/"+april_year;         
	  				 String current_year_edate_ap="30/4/"+april_year;
    				 parameters.put("april_year",april_year);
    				 parameters.put("prv_month",prv_month); 
    				 parameters.put("current_year_sdate",current_year_sdate);
    				 parameters.put("current_year_edate",current_year_edate);    	   			    				
    				 parameters.put("mvalue", obj.month_val(obj.setValue("month", request)));
    				 parameters.put("current_year_sdate_ap",current_year_sdate_ap);
	     			 parameters.put("current_year_edate_ap",current_year_edate_ap);  
					 parameters.put("ctxpath", ctxpath);    
					 
    			 } else if (Integer.parseInt(pr)==20)  
    			 {
    				 String arr_label="",arr_year_label="";
    				   
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_STMT_NEW.jasper");
    				 int next_month=0,next_year=0;
    				 ctxpath = request.getRequestURL().toString(); 
    				 if (Integer.parseInt(month)==12)
    				 {
    					 next_month=1;
    					 next_year=Integer.parseInt(year)+1;
    				 }
    				 else  
    				 {
    					 next_month=Integer.parseInt(month)+1;
    					 next_year=Integer.parseInt(year);
    				 }  
    				 int prv_month=0,prv_year=0;
    				 if (Integer.parseInt(month)==1)    
    				 {
    					 prv_month=12;    					 
    					 prv_year=Integer.parseInt(year)-1;    					 
    				 }  
    				 else      
    				 {
    					 prv_month=Integer.parseInt(month)-1;    					
    					 prv_year=Integer.parseInt(year);
    				 }
    				 if (Integer.parseInt(month)==2)    
    				 {
    					 arr_label="12";
    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
    				 }else if(Integer.parseInt(month)==1)   
    				 {
    					 arr_label="11";    
    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
    				 }else   
    				 {    					 
    					 arr_label=Integer.toString(Integer.parseInt(month)-2);
    					 arr_year_label=year;
    				 }
    				 parameters.put("year2",Integer.toString(next_year));    
    				 parameters.put("month2",Integer.toString(next_month));
     				 parameters.put("ctxpath",ctxpath);    
    				 //parameters.put("month2",year);
    				 parameters.put("arr_label",arr_label);
    				 parameters.put("arr_year_label",arr_year_label);  
    				 parameters.put("arryear",prv_year);            
    				 parameters.put("arrmonth",prv_month); 
    			 } else if (Integer.parseInt(pr)==200)  
    			 {
    				 String arr_label="",arr_year_label="";    				   
    				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_STMT_Latest.jasper");
    				 int next_month=0,next_year=0;
    				 ctxpath = request.getRequestURL().toString(); 
    				 if (Integer.parseInt(month)==12)
    				 {
    					 next_month=1;     
    					 next_year=Integer.parseInt(year)+1;
    				 }
    				 else  
    				 {
    					 next_month=Integer.parseInt(month)+1;
    					 next_year=Integer.parseInt(year);
    				 }  
    				 int prv_month=0,prv_year=0;
    				 if (Integer.parseInt(month)==1)    
    				 {
    					 prv_month=12;    					 
    					 prv_year=Integer.parseInt(year)-1;    					 
    				 }  
    				 else      
    				 {
    					 prv_month=Integer.parseInt(month)-1;    					
    					 prv_year=Integer.parseInt(year);
    				 }
    				 if (Integer.parseInt(month)==2)    
    				 {
    					 arr_label="12";
    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
    				 }else if(Integer.parseInt(month)==1)   
    				 {
    					 arr_label="11";    
    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
    				 }else   
    				 {    					 
    					 arr_label=Integer.toString(Integer.parseInt(month)-2);
    					 arr_year_label=year;
    				 }
    				 parameters.put("year2",Integer.toString(next_year));    
    				 parameters.put("month2",Integer.toString(next_month));
     				 parameters.put("ctxpath",ctxpath);    
    				 //parameters.put("month2",year);
    				 parameters.put("arr_label",arr_label);
    				 parameters.put("arr_year_label",arr_year_label);  
    				 parameters.put("arryear",prv_year);            
    				 parameters.put("arrmonth",prv_month); 
    			 } else if (Integer.parseInt(pr)==21)     
    				 
        			 {
    				 
     				int month_set = 0,year_set = 0;
     				String arr_set = "",curset = "";
 					if ((Integer.parseInt(obj.setValue("fmonth", request))) == 1) {
 						month_set = 12;
 						year_set = Integer.parseInt(obj.setValue("fyear", request)) - 1;
 						arr_set = month_set + "/" + year_set;
 						curset = "   " + obj.setValue("fmonth", request)+ "/" + obj.setValue("fyear", request);
 					} else {
 						month_set = (Integer.parseInt(obj.setValue("fmonth",request)) - 1);
 						year_set = Integer.parseInt(obj.setValue("fyear", request));
 						arr_set = month_set + "/" + year_set;
 						curset = "   " + obj.setValue("fmonth", request)+ "/" + obj.setValue("fyear", request);
 						}
 					 String arr_label="",arr_year_label="";
  					month=obj.setValue("fmonth", request);
  					year=obj.setValue("fyear", request);
  					 if (Integer.parseInt(month)==2)    
     				 {
     					 arr_label="12";
     					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
     				 }else if(Integer.parseInt(month)==1)   
     				 {
     					 arr_label="11";    
     					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
     				 }else   
     				 {    					 
     					 arr_label=Integer.toString(Integer.parseInt(month)-2);
     					 arr_year_label=year;
     				 }
 	 					Office_id=obj.setValue("div", request);
 	 					String Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
 	 					parameters.put("monthval", obj.month_val(month));
 	 					parameters.put("office_id", Office_id);
 	 					parameters.put("arr_set", arr_set);
 						parameters.put("curset", curset);
 						 parameters.put("arr_label",arr_label);
 	    				 parameters.put("arr_year_label",arr_year_label);  
 						parameters.put("off_name", Office_name);
 			//			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Replaced_MD_Review.jasper");
 						 ctxpath = request.getRequestURL().toString(); 	 
 						parameters.put("ctxpath", ctxpath);		 
    				 
    				 if(Integer.parseInt(month)==5)
						{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/MD_Review_Ben_Type_May.jasper");
						response.setHeader("Content-Disposition","attachment; filename=\"MD_Review_Ben_Type_May.pdf\"");
						System.out.println("parameterssssssssssssssssssss****"+parameters);
						}else
						{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/MD_Review_Ben_Type.jasper");
						response.setHeader("Content-Disposition","attachment; filename=\"ANNEXURE_I_MD_Review_lks.pdf\"");
					System.out.println("parameterssssssssssssssssssss****"+parameters);
											
					}
    				 
    				 
    			 }else if (Integer.parseInt(pr)==22)     
    			 {
     				int month_set = 0,year_set = 0;
     				String arr_set = "",curset = "";
					if ((Integer.parseInt(obj.setValue("fmonth", request))) == 1) {
						month_set = 12;
						year_set = Integer.parseInt(obj.setValue("fyear", request)) - 1;
						arr_set = month_set + "/" + year_set;
						curset = "   " + obj.setValue("fmonth", request)+ "/" + obj.setValue("fyear", request);
					} else {
						month_set = (Integer.parseInt(obj.setValue("fmonth",request)) - 1);
						year_set = Integer.parseInt(obj.setValue("fyear", request));
						arr_set = month_set + "/" + year_set;
						curset = "   " + obj.setValue("fmonth", request)+ "/" + obj.setValue("fyear", request);
						}
					 String arr_label="",arr_year_label="";
  					month=obj.setValue("fmonth", request);
  					year=obj.setValue("fyear", request);
  					 if (Integer.parseInt(month)==2)    
    				 {
    					 arr_label="12";
    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
    				 }else if(Integer.parseInt(month)==1)   
    				 {
    					 arr_label="11";    
    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
    				 }else   
    				 {    					 
    					 arr_label=Integer.toString(Integer.parseInt(month)-2);
    					 arr_year_label=year;
    				 }
 	 					Office_id=obj.setValue("div", request);
 	 					String Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
 	 					parameters.put("monthval", obj.month_val(month));
 	 					parameters.put("office_id", Office_id);
 						parameters.put("off_name", Office_name);
 						parameters.put("arr_set", arr_set);
 						 parameters.put("arr_label",arr_label);
 	    				 parameters.put("arr_year_label",arr_year_label);  
						parameters.put("curset", curset);  
 						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Review_BEN.jasper");
 					}else if (Integer.parseInt(pr)==51 || Integer.parseInt(pr)==151  || Integer.parseInt(pr)==251 )			
    				{
		   				  switch(Integer.parseInt(pr)) 
		   				  {
		   				  case 51  :  
		   					  	parameters.put("label", cr_label );	
		   					  	parameters.put("divby", cr_fig);
		   					  	break;
		   		  case 151:
		   			  	parameters.put("label", lk_label );	
		   			  	parameters.put("divby", lk_fig);
		   			  	break;				  			
		   		  case 251:
		   			  	parameters.put("label", ac_label);	
		   			  	parameters.put("divby", ak_fig);
		   			  	break;  
		   		  }
		   		  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_dist_Corp_net_due_abs.jasper");
   			}else if (Integer.parseInt(pr)==52 || Integer.parseInt(pr)==152  || Integer.parseInt(pr)==252 )			
			{
 				  switch(Integer.parseInt(pr))
 				  {
 				  case 52  :
 					  	parameters.put("label", cr_label );	
 					  	parameters.put("divby", cr_fig);
 					  	break;
 				  case 152:
 					  	parameters.put("label", lk_label );	
 					  	parameters.put("divby", lk_fig);
 					  	break;				  			
 				  case 252:
 					  	parameters.put("label", ac_label);	
 					  	parameters.put("divby", ak_fig);
 					  	break;  
 				  }
 				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_dist_MUN_net_due_abs.jasper");
 			}else if (Integer.parseInt(pr)==156 )			
			{
				  switch(Integer.parseInt(pr))
				  {
				  
				  case 156:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig); 
					  	break;				  			
				      
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_dist_Private_net_due_abs.jasper");
			}else if (Integer.parseInt(pr)==53 || Integer.parseInt(pr)==153  || Integer.parseInt(pr)==253 )			
			{
				  switch(Integer.parseInt(pr))
				  {
				  case 53  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 153:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 253:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;  
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_dist_RTP_net_due_abs.jasper");
			}else if (Integer.parseInt(pr)==54 || Integer.parseInt(pr)==154  || Integer.parseInt(pr)==254 )			
			{
				  switch(Integer.parseInt(pr))
				  {
				  case 54  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 154:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 254:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;  
				  }			  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_dist_UTP_net_due_abs.jasper");
			}else if (Integer.parseInt(pr)==256  )			
			{
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Corp_3_Col_Det.jasper");	
			}else if (Integer.parseInt(pr)==257  )			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Corp_3_Col.jasper");	
			}else if (Integer.parseInt(pr)==258  )			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_4Col_Det.jasper");	
			}else if (Integer.parseInt(pr)==259)			
			{   
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_4Col_Det.jasper");	
			}else if (Integer.parseInt(pr)==260)			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_4Col_Det.jasper");	
			} else if (Integer.parseInt(pr)==261)			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_4Col_Det.jasper");	
			} else if (Integer.parseInt(pr)==262)			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_4Col.jasper");	
			} else if (Integer.parseInt(pr)==263)			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_4Col.jasper");	
			} else if (Integer.parseInt(pr)==264)			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_4Col.jasper");	
			} else if (Integer.parseInt(pr)==265)			
			{  
				parameters.put("label", lk_label );	
			  	parameters.put("divby", lk_fig);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_4Col.jasper");	
			} 
			else if (Integer.parseInt(pr)==55 || Integer.parseInt(pr)==155  || Integer.parseInt(pr)==255 )
			{
				  switch(Integer.parseInt(pr))
				  {
				  case 55  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 155:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 255:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;  
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_dist_VP_net_due_abs.jasper");
			}else if (Integer.parseInt(pr)==351 || Integer.parseInt(pr)==451  || Integer.parseInt(pr)==551 )			
			{
				switch(Integer.parseInt(pr))
 				  {
 				  case 351  :  
 					  	parameters.put("label", cr_label );	
 					  	parameters.put("divby", cr_fig);
 					  	break;
 				  case 451:
 					  	parameters.put("label", lk_label );	
 					  	parameters.put("divby", lk_fig);
 					  	break;				  			
 				  case 551:
 					  	parameters.put("label", ac_label);	
 					  	parameters.put("divby", ak_fig);
 					  	break;  
 				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Corp_bal_due_abs.jasper");
			}else if (Integer.parseInt(pr)==352 || Integer.parseInt(pr)==452  || Integer.parseInt(pr)==552 )			
			{
				switch(Integer.parseInt(pr))
 				  {
 				  case 352  :  
 					  	parameters.put("label", cr_label );	
 					  	parameters.put("divby", cr_fig);
 					  	break;
 				  case 452:
 					  	parameters.put("label", lk_label );	
 					  	parameters.put("divby", lk_fig);
 					  	break;				  			
 				  case 552:
 					  	parameters.put("label", ac_label);	
 					  	parameters.put("divby", ak_fig);
 					  	break;  
 				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_MUN_bal_due_abs.jasper");
			}else if (Integer.parseInt(pr)==353 || Integer.parseInt(pr)==453 || Integer.parseInt(pr)==553)			
			{
				switch(Integer.parseInt(pr))
 				  {
 				  case 353:  
 					  	parameters.put("label", cr_label );	
 					  	parameters.put("divby", cr_fig);
 					  	break;
 				  case 453:
 					  	parameters.put("label", lk_label );	
 					  	parameters.put("divby", lk_fig);
 					  	break;				  			
 				  case 553:
 					  	parameters.put("label", ac_label);	
 					  	parameters.put("divby", ak_fig);
 					  	break;  
 				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_abs_due_abs.jasper");
			}else if (Integer.parseInt(pr)==354 || Integer.parseInt(pr)==454 || Integer.parseInt(pr)==554)			
			{
				switch(Integer.parseInt(pr))
 				  {
 				  case 354:  
 					  	parameters.put("label", cr_label );	
 					  	parameters.put("divby", cr_fig);
 					  	break;
 				  case 454:
 					  	parameters.put("label", lk_label );	
 					  	parameters.put("divby", lk_fig);
 					  	break;				  			
 				  case 554:
 					  	parameters.put("label", ac_label);	
 					  	parameters.put("divby", ak_fig);
 					  	break;  
 				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_bal_due_abs.jasper");
			}else if (Integer.parseInt(pr)==355 || Integer.parseInt(pr)==455 || Integer.parseInt(pr)==555)			
			{
				switch(Integer.parseInt(pr))
 				  {
 				  case 355:  
 					  	parameters.put("label", cr_label );	
 					  	parameters.put("divby", cr_fig);
 					  	break;
 				  case 455:
 					  	parameters.put("label", lk_label );	
 					  	parameters.put("divby", lk_fig);
 					  	break;				  			
 				  case 554:
 					  	parameters.put("label", ac_label);	
 					  	parameters.put("divby", ak_fig);
 					  	break;  
 				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_bal_due_abs.jasper");
			     }
    			
    			  	imagespath = getServletContext().getRealPath("/images/")+File.separator;    			  	
					parameters.put("imgpath", imagespath);
					parameters.put("mvalue", obj.month_val(month));    
    			  	parameters.put("year",year);   
    			  	parameters.put("month",month);
    			    parameters.put("ten_word",ten_word);     
    			    PreparedStatement  proc_stmt=null;
    			    if (Office_id.equalsIgnoreCase("5000"))
    			    {
    			    	proc_stmt=null;
    			    	proc_stmt = connection.prepareCall("call pms_dcb_freeze_state_update (?,?) ");    		  		
    			    	proc_stmt.setInt(1, Integer.parseInt(month));	          					
    			    	proc_stmt.setInt(2,Integer.parseInt(year) );	           		  			   // P Month         month_process       
		            // 	P Year=year_process   C Year	=year_var  C Month=month_var
    			    	proc_stmt.execute();
    			    }
    			    parameters.put("ctxpath",ctxpath);  
    			    System.out.println("Dks  :"+path+" Para  :"+parameters+" connection  :"+connection);
    			    JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
    			    
    			    
    			    int option=Integer.parseInt(obj.setValue("option", request));
    			    OutputStream outuputStream = response.getOutputStream();
	               if (option==1)  
	               {  
	            	   	JRExporter exporter = null;                  
	            	   	response.setContentType("application/pdf");
	            	   	response.setHeader("Content-Disposition", "attachment; filename=\"Report"+pr+".pdf\"");
	            	   	exporter = new JRPdfExporter();
	            	   	exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);  
	            	   	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
	            	   	exporter.exportReport();
	            	   	outuputStream.close();
	               }
	               else  if (option==2)  
	               {  
	            	   response.setContentType("application/vnd.ms-excel");
	                   response.setHeader ("Content-Disposition", "attachment;filename=\"Report"+pr+".xls\"");
	                   JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                   exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                   ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                   exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                   
	             //    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
	                 
	                   
	                   exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                   exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                   exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                   exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                   exporterXLS.exportReport(); 
	                   byte []bytes;
	                   bytes = xlsReport.toByteArray();
	                   ServletOutputStream ouputStream = response.getOutputStream();
	                   ouputStream.write(bytes, 0, bytes.length);
	                   ouputStream.flush();
	                   ouputStream.close();
	               }
	               else    
	               {
	            	   htmlshow(  response,  jasperPrint,  outuputStream);
	               }  
           }
           catch (JRException e)   
           {
               throw new ServletException(e);
           } catch (Exception e) 
           {  
			e.printStackTrace();
		}
           obj.conClose(connection);    
	}
	public void htmlshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		try {    
			  JRExporter exporter = new JRHtmlExporter();  
			  response.setContentType("application/pdf");      
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.html\"");
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "build/reports/BatchExportReport.html");
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream);
 			  exporter.exportReport();
			  outuputStream.close();  
			  outuputStream.flush();  
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	//9245700285  
}
