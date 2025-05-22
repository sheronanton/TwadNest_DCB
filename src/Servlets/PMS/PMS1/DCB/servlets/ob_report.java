/* 
  * Created on : dd-mm-yy 
  * Author     : Panneer Selvam.K
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  *---------|---------------|--------------------------------------------------- 
  */


package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
public class ob_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public ob_report() {
		super();
		//   Auto-generated constructor stub
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		ResultSet rs = null; 
		Connection con = null;
		String qry = "",xml = " ";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		Controller obj = new Controller();
		String new_cond=Controller.new_cond; 
		String userid = "0", Office_id = "", Office_Name = "";
		try {
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
				userid = "0";
			}
			if (userid == null) {
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
	 		  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	
			
	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		if (Office_id.equals(""))
		Office_id = "0";
		} catch (Exception e) {
		}
		
		try {  
			String year = obj.setValue("YEAR", request);
			String MONTH = obj.setValue("MONTH", request);
			String bentype = obj.setValue("bentype", request);
			String option= obj.setValue("option", request);
			String process_code = obj.setValue("process_code", request);
			String input_value = obj.setValue("input_value", request);
			String cond = "", cond1 = "";
			cond1 = "where office_id="+Office_id+" and  MONTH=" + MONTH + " and FIN_YEAR=" + year;
			int m_select=0,y_select=0;
			String command= obj.setValue("command", request);
			if (command.trim().equalsIgnoreCase("localbodyintreport"))
			{
				Map parameters = new HashMap();
				MONTH = obj.isNull(obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);
				year = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
				parameters.put("year", year);
				parameters.put("month", MONTH);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(MONTH));
				parameters.put("office_name", obj.getValue("COM_MST_OFFICES","OFFICE_NAME", "where OFFICE_ID=" + Office_id));
				String path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Int_localbody_new.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				OutputStream outuputStream = response.getOutputStream();
				JRExporter exporter = null;
				response.setContentType("application/pdf");     
				response.setHeader("Content-Disposition","attachment; filename=\"LocalBodyInterst.pdf\"");
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter( JRExporterParameter.OUTPUT_STREAM,outuputStream);
				exporter.exportReport();
				outuputStream.close();
			}
			else 
			{
				if (Integer.parseInt(MONTH)==1)
				{
							m_select=12;
							y_select=Integer.parseInt(year)-1;							
				}
				else
				{
							m_select=Integer.parseInt(MONTH)-1;
							y_select=Integer.parseInt(year);							
				}
				if (process_code.equals("1")) {
					cond = "where "+new_cond+" OFFICE_ID=" + Office_id;
				} else if (process_code.equals("2") || process_code.equals("7")) 
				{
					String blocksno=obj.setValue("block", request);
					if (!blocksno.equalsIgnoreCase("0") && bentype.equalsIgnoreCase("6"))							
					cond = " where "+new_cond+" BLOCK_SNO="+blocksno +" and BENEFICIARY_TYPE_ID=" + bentype+" and OFFICE_ID=" + Office_id;
					else
					cond = " where "+new_cond+"  BENEFICIARY_TYPE_ID=" + bentype+" and OFFICE_ID=" + Office_id;
					
					
				} else if (process_code.equals("4")) {
					cond = " where "+new_cond+"  BENEFICIARY_TYPE_ID=" + bentype
							+ " and OFFICE_ID=" + Office_id
							+ " and BENEFICIARY_SNO=" + input_value;
				} else if (process_code.equals("3")) {
					cond = "";
					cond1 += " and BENEFICIARY_OB_SNO=" + input_value;
				}
				
				
				
				
						
						qry = " select " + " BEN.BENEFICIARY_SNO,"
								+ " BEN.BENEFICIARY_NAME," + " BEN.BENEFICIARY_TYPE_ID,"
								+ "  BEN.OFFICE_ADDRESS1," + " BEN.OFFICE_ADDRESS2,"
								+ "  BEN.OFFICE_ADDRESS3," + "  BEN.OFFICE_PIN_CODE,"
								+ "  BEN.OFFICE_LANDLINE_NO," + "  BEN.OFFICE_MOBILE_NO,"
								+ "BENTYPE.BEN_TYPE_DESC," + "   OB.ob,"
								+ "  OB.BENEFICIARY_OB_SNO," + "  OB.addifany,"
								+ "  OB.collection," + "  OB.yesteryear,OB.SCH_SNO,"
								+ "  OB.currentyear," + "  OB.demandupto,OB.OB_INT_AMT_WC,"
								+ "  OB.main_int_up_to_prv_fyear,"
								+ "  OB.main_int_collected,"
								+ "  OB.water_int_up_to_prv_fyear,"
								+ "  OB.water_int_levied,"
								+ "  OB.water_int_collected_prv_month,"
								+ "  OB.coll_up_prv_wcharge,"
								+ "  OB.coll_up_yester_year ,"
								+ "  OB.OB_FOR_MTH_MAINT_CHARGES," 
								+ "  OB.OB_FOR_MTH_YESTER_YR_WC,"
								+ "  OB.OB_FOR_MTH_CUR_YR_WC, "
								+ "  OB.OB_FOR_MTH_INT_AMT_WC,OB.DMD_INT_FOR_MTH_WC," + "  sch.SCH_NAME,sch.SCH_SNO "
								+ " from " + " (" + " (" + " select " + " BENEFICIARY_SNO,"
								+ "       BENEFICIARY_NAME," + "BENEFICIARY_TYPE_ID,"
								+ "       DISTRICT_CODE," + "BLOCK_SNO," + "OFFICE_ADDRESS1,"
								+ "       OFFICE_ADDRESS2," + "OFFICE_ADDRESS3,"
								+ "       OFFICE_PIN_CODE," + "OFFICE_LANDLINE_NO,"
								+ "       OFFICE_MOBILE_NO " + " from  "
								+ "  PMS_DCB_MST_BENEFICIARY "
								+ cond
								+ "  order by  "
								+ "        BENEFICIARY_NAME "
								+ ")BEN  "
								+ "  join ( select"
								+ "        BEN_TYPE_ID, "
								+ "        BEN_TYPE_SDESC,"
								+ "        BEN_TYPE_DESC "
								+ " from PMS_DCB_BEN_TYPE ) BENTYPE "
								+ "  on  BENTYPE.BEN_TYPE_ID=BEN.BENEFICIARY_TYPE_ID"
								+ " "
								+ " join "
								+ "( "
								+ " select "
								+ "     	BENEFICIARY_SNO, "
								+ "	        BENEFICIARY_OB_SNO,	"
								+ "     	OB_MAINT_CHARGES as ob,"
								+ "         OB_INT_PRV_YR_MAINT as addifany,"
								+ "         COLN_UPTO_PRV_MTH_MAINT as collection,"
								+ "         OB_YESTER_YR_WC as yesteryear,"
								+ "         OB_CUR_YR_WC as currentyear,"
								+ "         DMD_UPTO_PRV_MTH_WC as demandupto,"
								+ "         OB_INT_CUR_YR_MAINT as main_int_up_to_prv_fyear,"
								+ "         COLN_INT_UPTO_PRV_MTH_MAINT as main_int_collected,OB_INT_AMT_WC as OB_INT_AMT_WC,"
								+ "         OB_INT_AMT_WC as water_int_up_to_prv_fyear,"
								+ "         DMD_INT_UPTO_PRV_MTH_WC as water_int_levied,"
								+ "         COLN_INT_UPTO_PRV_MTH_WC as water_int_collected_prv_month ,"
								+ "         COLN_UPTO_PRV_MTH_YESTER_YR as coll_up_yester_year, "
								+ "         COLN_UPTO_PRV_MTH_WC as coll_up_prv_wcharge, "
								+ "         OB_FOR_MTH_YESTER_YR_WC  as OB_FOR_MTH_YESTER_YR_WC ,"
								+ "         OB_FOR_MTH_MAINT_CHARGES as OB_FOR_MTH_MAINT_CHARGES,"
								+ "         OB_FOR_MTH_CUR_YR_WC as OB_FOR_MTH_CUR_YR_WC ,"
								+ "         OB_FOR_MTH_INT_AMT_WC as OB_FOR_MTH_INT_AMT_WC,DMD_INT_FOR_MTH_WC as DMD_INT_FOR_MTH_WC, "
								+ "         SCH_SNO "
								+ " from  "
								+ "       PMS_DCB_OB_YEARLY "
							 	+ cond1
								+ "  and  sch_sno in (select distinct scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE where  METER_STATUS='L')  "
								+ ""
								+ " and beneficiary_sno in (SELECT BENEFICIARY_SNO FROM pms_dcb_beneficiary_ob_request WHERE STATUS = 'L' )"
								+ "			and sch_sno in (SELECT scheme_sno FROM pms_dcb_beneficiary_ob_request WHERE STATUS = 'L')) OB  "
								+ " on OB.BENEFICIARY_SNO=BEN.BENEFICIARY_SNO "
								+ " left join"
								+ "(" + " select SCH_SNO," + " SCH_NAME " + " from PMS_SCH_MASTER ) " + "sch" + " on sch.SCH_SNO =OB.SCH_SNO " + "" +")";
						
					 
						xml = "<result>";
						int prow = 0;
						rs = obj.getRS(qry);
						while (rs.next()) {
							prow++;
							String address1 = "", address2 = "", address3 = "";
							address1 = obj.isNull(rs.getString("OFFICE_ADDRESS1"), 2);
							address2 = obj.isNull(rs.getString("OFFICE_ADDRESS2"), 2);
							address3 = obj.isNull(rs.getString("OFFICE_ADDRESS3"), 2);
							String ben_sno=rs.getString("BENEFICIARY_SNO");
							String month_sno=obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "BENEFICIARY_OB_SNO"," where BENEFICIARY_SNO=" +ben_sno+" and FIN_YEAR="+y_select+"  and MONTH="+m_select+" and SCH_SNO="+rs.getString("SCH_SNO"));
							String ben_type = "";// obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC"," where BEN_TYPE_ID="+obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"),
													// 2));
							if (ben_type.equals("") || ben_type == "") 	ben_type = "0";
							
							String bname = obj.isNull(rs.getString("BENEFICIARY_NAME"), 2);
							xml += "<month_sno>" + month_sno+"</month_sno>";
							xml += "<fschar>" + bname.charAt(0)+ "</fschar><BENEFICIARY_NAME>"+ obj.isNull(rs.getString("BENEFICIARY_NAME"), 2)+ "</BENEFICIARY_NAME>";
							xml += "<BENEFICIARY_SNO>" + ben_sno+ "</BENEFICIARY_SNO>";
							xml += "<address1>" + address1 + "</address1>";
							xml += "<address2>" + address2 + "</address2>";
							xml += "<address3>" + address3 + "</address3>";
							xml += "<ob>" + obj.isNull(rs.getString("ob"), 2) + "</ob>";
							xml += "<addifany>" + obj.isNull(rs.getString("addifany"), 1)+ "</addifany>";
							xml += "<collection>"+ obj.isNull(rs.getString("collection"), 1)+ "</collection>";
							xml += "<yesteryear>"+ obj.isNull(rs.getString("yesteryear"), 1)+ "</yesteryear>";
							xml += "<currentyear>"+ obj.isNull(rs.getString("currentyear"), 1)+ "</currentyear>";
							xml += "<demandupto>"+ obj.isNull(rs.getString("demandupto"), 1)+ "</demandupto>";
							xml += "<SCH_NAME><![CDATA[" + obj.isNull(rs.getString("SCH_NAME"), 2)+ "]]></SCH_NAME>";
							xml += "<SCH_SNO>" + obj.isNull(rs.getString("SCH_SNO"), 2)+ "</SCH_SNO>";				 
							xml += "<main_int_up_to_prv_fyear>"+ obj.isNull(rs.getString("main_int_up_to_prv_fyear"),1) + "</main_int_up_to_prv_fyear>";
							xml += "<main_int_collected>"+ obj.isNull(rs.getString("main_int_collected"), 1)+ "</main_int_collected>";
							xml += "<water_int_levied>"+ obj.isNull(rs.getString("water_int_levied"), 1)+ "</water_int_levied>";
							xml += "<water_int_collected_prv_month>"+ obj.isNull(rs.getString("water_int_collected_prv_month"), 1)+ "</water_int_collected_prv_month>";
							xml += "<water_int_up_to_prv_fyear>"+ obj.isNull(rs.getString("water_int_up_to_prv_fyear"),1) + "</water_int_up_to_prv_fyear>";
							xml += "<coll_up_yester_year>"+ obj.isNull(rs.getString("coll_up_yester_year"), 1)+ "</coll_up_yester_year>";
							xml += "<coll_up_prv_wcharge>"+ obj.isNull(rs.getString("coll_up_prv_wcharge"), 1)+ "</coll_up_prv_wcharge>";
							xml += "<OB_FOR_MTH_YESTER_YR_WC>"+ obj.isNull(rs.getString("OB_FOR_MTH_YESTER_YR_WC"),1) + "</OB_FOR_MTH_YESTER_YR_WC>";
							xml += "<OB_FOR_MTH_MAINT_CHARGES>"+ obj.isNull(rs.getString("OB_FOR_MTH_MAINT_CHARGES"),1) + "</OB_FOR_MTH_MAINT_CHARGES>";
							xml += "<OB_FOR_MTH_CUR_YR_WC>"+ obj.isNull(rs.getString("OB_FOR_MTH_CUR_YR_WC"), 1)+ "</OB_FOR_MTH_CUR_YR_WC>";
							xml += "<OB_FOR_MTH_INT_AMT_WC>"+ obj.isNull(rs.getString("water_int_levied"), 1)+ "</OB_FOR_MTH_INT_AMT_WC>";
							xml += "<OB_INT_AMT_WC>"+ obj.isNull(rs.getString("OB_INT_AMT_WC"), 1)+ "</OB_INT_AMT_WC>";
							xml += "<BENEFICIARY_OB_SNO>" + obj.isNull(rs.getString("BENEFICIARY_OB_SNO"), 2)+ "</BENEFICIARY_OB_SNO>";
							xml += "<BENEFICIARY_TYPE>"+ obj.isNull(rs.getString("BEN_TYPE_DESC"), 2)+ "</BENEFICIARY_TYPE>";
						}    
				  
						xml += "<prow>" + prow + "</prow>";
						xml += "</result>";
				
						PrintWriter pr = response.getWriter();
						pr.write(xml);
						pr.close();
						pr.flush();
			}
			obj.conClose(con);
		} catch (Exception e) {
			System.out.println("----------------\n" + e);
		}
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		 
	}
}
