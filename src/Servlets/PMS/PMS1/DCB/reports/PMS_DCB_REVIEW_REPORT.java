package Servlets.PMS.PMS1.DCB.reports;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

public class PMS_DCB_REVIEW_REPORT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PMS_DCB_REVIEW_REPORT() {
		super();
	}

	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		Connection connection = null;
		Controller obj = new Controller();
		String imagespath = "";
		String ctxpath = "";
		try 
		{
			connection = obj.con();
			obj.createStatement(connection);
		} catch (Exception ex) 
		{
			String connectMsg = "Could not create the connection"+ ex.getMessage() + " " + ex.getLocalizedMessage();
		}
		Calendar cal = Calendar.getInstance();
		int dy = cal.get(Calendar.DAY_OF_YEAR);
		Map parameters = new HashMap();
		String path = "";
		HttpSession session = request.getSession(false);
		String userid = "", Office_id = "0";
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			userid = "0";
		}
		if (userid == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try 
		{
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String pmonth=obj.setValue("pmonth", request);
 	    String pyear=obj.setValue("pyear", request);
 	    String mvalue=obj.month_val(pmonth);
		int prv_month=obj.prv_month(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		int prv_year=obj.prv_year(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		String prv_mvalue=obj.month_val(Integer.toString(prv_month));
		 Map parameters1 = new HashMap(); 
			
		 parameters1.put("pyear",pyear );
		 parameters1.put("pmonth",pmonth);  
		 parameters1.put("prv_year",prv_year );
		 parameters1.put("prv_month",prv_month);
		 String process_code=obj.setValue("process_code", request);
		 System.out.println("PMS-->DCB-->PMS_DCB_REVIEW_REPORT-->process_code-->"+process_code);   
		 String head2="Beneficiary Type";
		 String head3="Opening Balance for "+prv_mvalue+"-"+prv_year;
  	     String head4="Demand  for "+prv_mvalue+"-"+prv_year;
  	     String head5="Collection during "+mvalue+"-"+pyear;
  	     String title="(for the month of "+mvalue+" "+pyear+")";
  	     String head6="Balance";
  	    
  	       
  	     OutputStream outuputStream =null;
  	     outuputStream = response.getOutputStream();
  	     if (Integer.parseInt(process_code)==1)
  	     {
  	       	     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_1.jasper");
  	     }else if (Integer.parseInt(process_code)==2)
  	     {
  	    	 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_2.jasper");
  	    	    parameters1.put("MD_GROUP",obj.setValue("MD_GROUP", request));
  	    	    System.out.println("MD Group "+ obj.setValue("MD_GROUP", request)) ;
  	     }else if (Integer.parseInt(process_code)==3)
  	     {
  	    	 	String ben_grph=obj.setValue("BEN_TYPE_GROUP", request);
  	    	 	head2="Beneficiary Name";
  	    	 	String ben_type_group_new="";    
  	    	 	if (Integer.parseInt(ben_grph)==6)  
  	    	 	{  
  	    	 		  
  	    	 		path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_4.jasper");
  	    	 		ben_type_group_new=obj.setValue("BEN_TYPE_GROUP", request);
  	    	 	}    
  	    	 	else  
  	    	 	{  
  	    	 		ben_type_group_new=obj.setValue("BEN_TYPE_GROUP", request);
  	    	 		if (Integer.parseInt(ben_grph)==8)  
  	    	 		{  
  	    	 			ben_type_group_new="8,9,10,11,12,13,14,15,16,17,18";
  	    	 			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_3_prv.jasper");
  	    	 		}else
  	    	 		{
  	    	 			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_3.jasper");	
  	    	 		}
  	    	 		System.out.println(ben_type_group_new);      
  	    	 			
  	    	 	}  
	    	 	String ben_group_name="";
	    		try 
	    		{
					  ben_group_name=obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+ben_grph);
					
				} catch (Exception e) 
				{
					e.printStackTrace();  
				}
				parameters1.put("ben_grph_desc",ben_group_name);
				parameters1.put("BEN_TYPE_GROUP",ben_type_group_new);
	     }else if (Integer.parseInt(process_code)==4)
  	     {		  
	    	 	String dis_name="";
	    	 	head2="Block Name";
	    	 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_5.jasper");
	    	 	try 
	    		{
	    	 		dis_name=obj.getValue("COM_MST_DISTRICTS", "DISTRICT_NAME","where DISTRICT_CODE="+obj.setValue("DISTRICT_CODE", request));
				} catch (Exception e) 
				{
					e.printStackTrace();      
				}	  
				parameters1.put("dis_name",dis_name);
	    	    parameters1.put("DISTRICT_CODE",obj.setValue("DISTRICT_CODE", request));
	     }else if (Integer.parseInt(process_code)==5)
  	     {		  
	    	 	head2="Beneficiary Name";
	    	 	String dis_name="",block_name=""; 
	    	 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_6.jasper");  
	    	 	try 
	    		{
	    	 		dis_name=obj.getValue("COM_MST_DISTRICTS", "DISTRICT_NAME","where DISTRICT_CODE="+obj.setValue("DISTRICT_CODE", request));
	    	 		block_name=obj.getValue("COM_MST_BLOCKS", "BLOCK_NAME","where BLOCK_SNO=" + obj.setValue("BLOCK_SNO", request));
				} catch (Exception e) 
				{  
					e.printStackTrace();         
				}	  
				parameters1.put("dis_name",dis_name);
				parameters1.put("Block_dis",block_name);			 
	    	    parameters1.put("BLOCK_SNO",obj.setValue("BLOCK_SNO", request));
	     }else if (Integer.parseInt(process_code)==7)
  	     {
	    		 head2="Scheme Type";
	       	     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_7.jasper");
	     }else if (Integer.parseInt(process_code)==8)
  	     {
    		 head2="Beneficiary Type";
    		 
    		 String stype_value="";
    		 try 
	    		{
    			 stype_value=obj.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" +obj.setValue("schtype", request));
	    		}catch(Exception e)
	    		{}
       	     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_dcb_md_hod_8.jasper");
       	    // block_name=obj.getValue("COM_MST_BLOCKS", "BLOCK_NAME","where BLOCK_SNO=" + obj.setValue("BLOCK_SNO", request));
       	   parameters1.put("stype_desc",stype_value);
         }    
  	     parameters1.put("head2",head2);
	     parameters1.put("head3",head3);
	     parameters1.put("head4",head4);
	     parameters1.put("head5",head5);
	     parameters1.put("head6",head6);  
	     parameters1.put("title",title);
  	     try   
  	     {
  	    	 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, connection);
  	    	 obj.pdfshow( response,  jasperPrint,  outuputStream);
  	     }catch(Exception e)
  	     {
  	    	 System.out.println(e);
  	     }
  	     
  	     
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
