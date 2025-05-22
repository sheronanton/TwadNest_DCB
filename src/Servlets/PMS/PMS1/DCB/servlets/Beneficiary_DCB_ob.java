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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
public class Beneficiary_DCB_ob extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public Beneficiary_DCB_ob() {super();}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		PreparedStatement ps = null;
		Connection con;
		int error_flag = 0,row = 0,ins_row = 0;
		String count = "0";
		String new_cond=Controller.new_cond;
		String meter_status=Controller.meter_status;
		Controller obj = new Controller();
		String OFFICE_LEVEL_ID="";
		String command = request.getParameter("command");
		CallableStatement proc_stmt = null;
		
		System.out.println("command==========>"+command);
		
		if (command == null || command.equals(""))  command = "no command";
		String input_value = request.getParameter("input_value"); // input value
		if (input_value == null || input_value.equals(""))input_value = "0";
		String process_code = request.getParameter("process_code");// process
		System.out.println("DCB->Beneficiary_DCB_Ob->command->"+command+"->process_code->"+process_code+"->Start"); 
		if (process_code == null || process_code.equals(""))
			process_code = "0";
		String ben_sno = request.getParameter("ben_sno");// process code 1->
		if (ben_sno == null || ben_sno.equals(""))
			ben_sno = "0";
		String year = obj.setValue("year", request);
		if (year == null || year.equals("")) year = "0";
		String month = obj.setValue("month", request);
		if (year == null || year.equals(""))  month = "0";
		String divcode = "", METRE_SNO = "";
		divcode = request.getParameter("divcode");// Command ->view,insert etc
		String xml = "", BENEFICIARY_SNO = "", BENEFICIARY_TYPE_SNO = "";
		if (divcode == null || divcode.equals("0"))  divcode = "0";
		String qry = "";
		String bentype = request.getParameter("bentype"); // input value
		if (bentype == null || bentype.equals(""))
			bentype = "0";
		try {
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) { 
			}
			if (userid == null) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
	 	//	  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	 			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			
		// 	Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
			if (Office_id.equals("0")) Office_id = "0"; 
			OFFICE_LEVEL_ID=obj.officeLevelId(Office_id);
			String MONTH = obj.setValue("month", request);
			String YEAR = obj.setValue("year", request);
			BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);
			LocalDate currentDate = LocalDate.now();
			if (command.equals("scheme_selection")) 
			{
				
				
				String obShow = request.getParameter("obShow");
				
				if (obShow.equalsIgnoreCase("true")) {
					
					xml += obj.combo_lkup("SCH_SNO","SCH_NAME","PMS_SCH_MASTER","where SCH_SNO in (select SCHEME_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO="
							+ BENEFICIARY_SNO + " and OFFICE_ID="+Office_id+" and "
									+ " sch_sno in (select scheme_sno from pms_dcb_beneficiary_ob_request where  BENEFICIARY_sno="+ BENEFICIARY_SNO +" and OFFICE_ID="+Office_id+"  and date(created_on) <= TO_DATE('" + currentDate + "', 'YYYY-MM-DD') "
											+ ") )", 2,"--Select--");
				}
				else {
					xml += obj.combo_lkup("SCH_SNO","SCH_NAME","PMS_SCH_MASTER","where SCH_SNO in (select SCHEME_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO="
							+ BENEFICIARY_SNO + " and OFFICE_ID="+Office_id+" )", 2,"--Select--");
				}

				
				
			}
			if (command.equals("newmispdf")) 
			{ 
					String ben_type = obj.setValue("ben_type", request);
					JasperPrint jasperPrint=null;
					OutputStream outuputStream=null; 
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					String path="";
					try {
							Map parameters = new HashMap();
							parameters.put("month", obj.setValue("fmonth", request));
							parameters.put("year", obj.setValue("fyear", request));
							parameters.put("mvalue",obj.month_val(obj.setValue("fmonth", request)));
							String imagespath = getServletContext().getRealPath("/images/")+File.separator;
							parameters.put("imgpath", imagespath); 
						if (process_code.equalsIgnoreCase("1"))
				 		{
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/HO_ABC_NEW.jasper");
							  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  outuputStream = response.getOutputStream();
							  response.setHeader("Content-Disposition","attachment; filename=\"HO_ABC_NEW.pdf\"");
						}
						else if (process_code.equalsIgnoreCase("2"))  
						{
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/HO_VP.jasper");
							  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  outuputStream = response.getOutputStream();
							  response.setHeader("Content-Disposition","attachment; filename=\"HO_VP.pdf\"");
						}else if (process_code.equalsIgnoreCase("3"))  
						{
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/HO_TownPan.jasper");
							  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  outuputStream = response.getOutputStream();
							  response.setHeader("Content-Disposition","attachment; filename=\"HO_TownPan.pdf\"");
						}else if (process_code.equalsIgnoreCase("4"))  
						{
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/HO_Mun.jasper");
							  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  outuputStream = response.getOutputStream();
							  response.setHeader("Content-Disposition","attachment; filename=\"HO_Mun.pdf\"");
						}else if (process_code.equalsIgnoreCase("5"))  
						{
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/HO_Corporation.jasper");
							  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  outuputStream = response.getOutputStream();
							  response.setHeader("Content-Disposition","attachment; filename=\"HO_Corporation.pdf\"");
						}
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
						exporter.exportReport();
						outuputStream.close();  
						} catch (JRException e) {
						throw new ServletException(e);
						}
			}else if (command.equals("pdf")) 
			{
				if (process_code.equals("1")) 
				{
					String ben_type = obj.setValue("ben_type", request);
					try {
							Map parameters = new HashMap();
							parameters.put("OFFICE_ID", Integer.parseInt(Office_id));
							parameters.put("BENEFICIARY_TYPE_ID", Integer.parseInt(ben_type));
							parameters.put("FIN_YEAR", Integer.parseInt(year));
							parameters.put("MONTH", Integer.parseInt(month));
							String path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ob.jasper");
							JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							OutputStream outuputStream = response.getOutputStream();
							JRExporter exporter = null;
							response.setContentType("application/pdf");
							response.setHeader("Content-Disposition","attachment; filename=\"Ob.pdf\"");
							exporter = new JRPdfExporter();
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
							exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
							exporter.exportReport();
							outuputStream.close();
						} catch (JRException e) {
						throw new ServletException(e);
						} 
					}
			} else 
			{
				String SCHEME_SNO = obj.setValue("Scheme", request);				 
				if (process_code.equals("1"))
				{
					String div=obj.setValue("div", request);
					if ("0".equals(div) || div==null) div=Office_id;
					xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_all_offices_view","where DIVISION_OFFICE_ID=" + div+ " and OFFICE_LEVEL_ID='SD'", 2,"--Select--");
					//xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_offices","where  OFFICE_ID  in (select SUB_DIV_ID from PMS_DCB_MST_BENEFICIARY_METRE where office_id="+div+") and OFFICE_LEVEL_ID='SD'", 2,"--Select--");
				}
				if (process_code.equals("2")) 
				{
					String ben_type_fltr = obj.setValue("ben_type_fltr",request);
					if (Integer.parseInt(ben_type_fltr) == 1) 
					{
						String sub_div = obj.setValue("sub_div", request);
						xml = obj.combo_lkup("BEN_TYPE_ID","BEN_TYPE_DESC","PMS_DCB_BEN_TYPE"," where BEN_TYPE_ID in (select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" SUB_DIV_ID="+ sub_div+ " )order by BEN_TYPE_ID", 2,"--Select--");
					} else {
						xml = obj.combo_lkup("BEN_TYPE_ID", "BEN_TYPE_DESC","PMS_DCB_BEN_TYPE", "order by BEN_TYPE_ID", 2,"--Select--");
					}
				}
				if ((process_code.equals("3") || process_code.equals("5")) && command.equals("data")) 
				{
					
					String Child_Div=obj.setValue("Child_div", request);
			 
					  
					 
					
					String cond = "",div_cond="";
					String subdiv = request.getParameter("subdiv");// input
					String divcode1= request.getParameter("divcode"); // input value
					String obShow = request.getParameter("ob_show");
					
					if (obShow == null) {
						obShow = "false";
					}
					if (subdiv == null || subdiv.equals(""))	subdiv = "0";
					String dis_value = request.getParameter("dis_value"); // input value
					if (dis_value == null || dis_value.equals(""))  dis_value = "0";
					String block_value = request.getParameter("block_value"); // input value
					if (block_value == null || block_value.equals(""))  block_value = "0";
					String sub_process = request.getParameter("sub_process"); // input value
					if (sub_process == null || sub_process.equals(""))  sub_process = "0";
					if (sub_process.equals("1")) 
					{
						if (dis_value.equals("0"))
							cond = "";
						else
							cond = " and DISTRICT_CODE=" + dis_value;
						if (block_value.equals("0"))
							cond = "";
						else
							cond = " and BLOCK_SNO=" + block_value;
					}
					if (divcode.equalsIgnoreCase("0"))
						div_cond="";
					else
//						OLD 
						div_cond="and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" OFFICE_ID="+Office_id+ " and  SUB_DIV_ID="+divcode+ " ) ";				
						
					
//					sheron
					if (obShow.equalsIgnoreCase("true")){
						System.out.println("Inside if");
						
						if ("DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
							xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where "+new_cond+" OFFICE_ID="+ Office_id + " "+div_cond+"     and      BENEFICIARY_TYPE_ID="+ bentype + " " + cond+ "and BENEFICIARY_SNO IN (SELECT BENEFICIARY_SNO FROM pms_dcb_beneficiary_ob_request WHERE  date(created_on) <= TO_DATE(' "+ currentDate +" ', 'YYYY-MM-DD')"
									+ "  ) order by BENEFICIARY_NAME", 2,"--Select--");
							else
							xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where "+new_cond+" OFFICE_ID_BEN="+ Child_Div + " "+div_cond+"     and      BENEFICIARY_TYPE_ID="+ bentype + " " + cond+ " order by BENEFICIARY_NAME", 2,"--Select--");							
					}else  {
						
						if ("DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
							xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where "+new_cond+" OFFICE_ID="+ Office_id + " "+div_cond+"     and      BENEFICIARY_TYPE_ID="+ bentype + " " + cond+ ""
									+ "  order by BENEFICIARY_NAME", 2,"--Select--");
							else
							xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where "+new_cond+" OFFICE_ID_BEN="+ Child_Div + " "+div_cond+"     and      BENEFICIARY_TYPE_ID="+ bentype + " " + cond+ " order by BENEFICIARY_NAME", 2,"--Select--");							
						
					}

					
					
				}
				if (process_code.equals("5") && command.equals("show")) {
					BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);				 
					count = obj.getValue("PMS_DCB_OB_YEARLY", "count(*)",
									"where FIN_YEAR="+year+" and MONTH="+month+ " and BENEFICIARY_SNO=" + BENEFICIARY_SNO
									+ " and SCH_SNO=" + SCHEME_SNO +" and OFFICE_ID="+ Office_id);
					String obsno = obj.isNull(obj.getValue("PMS_DCB_OB_YEARLY","BENEFICIARY_OB_SNO", "where FIN_YEAR=" + year
	//								+ " and MONTH=" +month+" and BENEFICIARY_SNO=" + BENEFICIARY_SNO 
							+ " and MONTH=" +month+" and BENEFICIARY_SNO=" + BENEFICIARY_SNO +" and OFFICE_ID="+ Office_id 
									+ "and SCH_SNO=" + SCHEME_SNO), 1);
					xml = "<result>";
					int y_select=0;			
					if (Integer.parseInt(month)<=3)
					{			 
						y_select=Integer.parseInt(year)-1;							
					}
					else
					{  
						y_select=Integer.parseInt(year);							
					}
					String count_apr = obj.getValue("PMS_DCB_OB_YEARLY","count(*)", "where FIN_YEAR=" + y_select+ " and MONTH=4 and BENEFICIARY_SNO="+ BENEFICIARY_SNO + " and SCH_SNO="+ SCHEME_SNO+" and OFFICE_ID="+ Office_id);
					Connection con_apr = obj.con();
					String apr_qry=" select  " + 
				      " 	OB_MAINT_CHARGES, " + 
				      " 	OB_CUR_YR_WC, " +
				      " 	OB_YESTER_YR_WC, " +
				      " 	OB_INT_PRV_YR_MAINT, " +
				      " 	OB_INT_CUR_YR_MAINT, " +
				      "		OB_INT_AMT_WC " +
				      " from  " +
				      " 	pms_dcb_ob_yearly " +
				      " where  " +    
				      " 	FIN_YEAR=" +y_select+
				      " and MONTH=4 " +
				      " and BENEFICIARY_SNO="+BENEFICIARY_SNO+
		//		      " and SCH_SNO="+SCHEME_SNO ;
					" and SCH_SNO="+SCHEME_SNO+" and OFFICE_ID="+ Office_id  ;
					
					
					Statement st_april=con_apr.createStatement();
					ResultSet rs_april= st_april.executeQuery(apr_qry);
					if (Integer.parseInt(count_apr)!=0)
					{
						if (rs_april.next())
						{
						xml += "<apr_OB_MAINT_CHARGES>"+rs_april.getString(1)+"</apr_OB_MAINT_CHARGES>";
						xml += "<apr_OB_CUR_YR_WC>"+rs_april.getString(2)+"</apr_OB_CUR_YR_WC>";
						xml += "<apr_OB_YESTER_YR_WC>"+rs_april.getString(3)+"</apr_OB_YESTER_YR_WC>";
						xml += "<apr_OB_INT_PRV_YR_MAINT>"+rs_april.getString(4)+"</apr_OB_INT_PRV_YR_MAINT>";
						xml += "<apr_OB_INT_CUR_YR_MAINT>"+rs_april.getString(5)+"</apr_OB_INT_CUR_YR_MAINT>";
						xml += "<apr_OB_INT_AMT_WC>"+rs_april.getString(6)+"</apr_OB_INT_AMT_WC>";
						}
					} 
					else 
					{
						xml += "<apr_OB_MAINT_CHARGES>0</apr_OB_MAINT_CHARGES>";
						xml += "<apr_OB_CUR_YR_WC>0</apr_OB_CUR_YR_WC>";
						xml += "<apr_OB_YESTER_YR_WC>0</apr_OB_YESTER_YR_WC>";
						xml += "<apr_OB_INT_PRV_YR_MAINT>0</apr_OB_INT_PRV_YR_MAINT>";
						xml += "<apr_OB_INT_CUR_YR_MAINT>0</apr_OB_INT_CUR_YR_MAINT>";
						xml += "<apr_OB_INT_AMT_WC>0</apr_OB_INT_AMT_WC>";
					}
					xml += "<status>" + count + "</status><obsno>" + obsno+ "</obsno><count_apr>" + count_apr+ "</count_apr>";
					xml += "</result>";
					rs_april.close();
					st_april.close();
					con_apr.close();
				}
				if (process_code.equals("3") && command.equals("show")) 
				{
				xml+=obj.combo_lkup("BENEFICIARY_SNO","BENEFICIARY_NAME", "PMS_DCB_MST_BENEFICIARY","where "+new_cond+" OFFICE_ID="+Office_id+"  and  BENEFICIARY_TYPE_ID=" + bentype,2, "--Select--");
				}
				if (process_code.equals("4") && command.equals("data")) {
					String smonth = obj.setValue("month", request);
					String sub_div = obj.setValue("sub_div", request);
					String divcond = "";
					if (sub_div.equalsIgnoreCase("0"))
						divcond = "";
					else
						divcond = "and SUB_DIV_ID=" + sub_div;
					int month_set=0;
					int year_set=0;
					if (Integer.parseInt(smonth)==1) {
						month_set=12;
						year_set= Integer.parseInt(year)-1 ;
					}
					else
					{
						month_set=(Integer.parseInt(smonth) - 1);
						year_set=Integer.parseInt(year)     ;
					}
					int pc = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE","where "+meter_status+" BENEFICIARY_SNO=" + (String) ben_sno + "" + divcond);
					String select_query = "select SCH.SCH_NAME, MET.PARENT_METRE,MET.SCHEME_SNO,MET.METRE_SNO,MET.METRE_LOCATION,MET.METRE_INIT_READING,"
							+ "case METRE_FIXED when 'y' Then 'Yes' when 'Y' Then 'Yes' "
							+ "when 'n' Then 'No' when 'N' Then 'No'"
							+ "END as METRE_FIXED, case METRE_WORKING when 'y' Then 'Yes'"
							+ "when 'Y' Then 'Yes'"
							+ "when 'n' Then 'No'"
							+ "when 'N' Then 'No'"
							+ "When null Then 'NR'"
							+ "END as METRE_WORKING,     "
							+ "METRE_TYPE "
							+ " from   PMS_DCB_MST_BENEFICIARY_METRE  MET,PMS_SCH_MASTER SCH    "
							//+ " where MET.METER_STATUS='L' and  MET.BENEFICIARY_SNO="
							+ " where "+meter_status+"    MET.BENEFICIARY_SNO="
							+ ben_sno
							+ " "
							+ divcond
							+ " and METRE_SNO not in (select METRE_SNO from PMS_DCB_TRN_MONTHLY_PR where BENEFICIARY_SNO="
							+ ben_sno
							+ " and  MONTH="
							+ smonth
							+ " and  YEAR="
							+ year
							+ ")"
							+ "and SCH.SCH_SNO=MET.SCHEME_SNO "
							+ " order by METRE_SNO ";
					xml = "<result>";
					int i = 0;
					Connection con1 = obj.con();
					Statement stmt = con1.createStatement();
					ResultSet rs = stmt.executeQuery(select_query);
					String msno = "", mfixed = "", mworking = "", lastmonth = "";
					while (rs.next()) 
					{
						i++;
						msno = "";
						lastmonth = "";
						msno = rs.getString("METRE_SNO");
						mworking = obj.isNull(rs.getString("METRE_WORKING"), 2);
						if (mworking.equals(""))  mworking = "No";
						mfixed = obj.isNull(rs.getString("METRE_FIXED"), 2);
						if (mworking.equals("Yes") || mworking.equals("yes") || mfixed.equalsIgnoreCase("Yes"))							
						lastmonth = obj.getValue("PMS_DCB_TRN_MONTHLY_PR","METRE_CLOSING_READING","where BENEFICIARY_SNO=" + ben_sno+ " and METRE_SNO=" + msno+ " and MONTH="+ month_set + " and YEAR=" + year_set);
						if ((lastmonth == null) || lastmonth.equals("")) lastmonth = "0";
						if (lastmonth.equals("0") && mworking.equals("Yes")) lastmonth = rs.getString("METRE_INIT_READING");
						xml += "<sno>" + msno + "</sno><METRE_LOCATION><![CDATA["+ obj.isNull(rs.getString("METRE_LOCATION"),2)+"]]></METRE_LOCATION>";
						xml += "<METRE_FIXED>" + mfixed + "</METRE_FIXED>";
						xml += "<METRE_TYPE>"+ obj.isNull(rs.getString("METRE_TYPE"), 1)+ "</METRE_TYPE>";
						xml += "<PARENT_METRE>"+ obj.isNull(rs.getString("PARENT_METRE"), 1)+ "</PARENT_METRE>";
						xml += "<METRE_INIT_READING>"+ obj.isNull(lastmonth, 1)+ "</METRE_INIT_READING>";
						xml += "<METRE_WORKING>" + mworking+ "</METRE_WORKING>";
						xml += "<ALLOTED_QTY>-</ALLOTED_QTY>";
						xml += "<SCHEME_NAME><![CDATA["+ obj.isNull(rs.getString("SCH_NAME"), 1)+ "]]></SCHEME_NAME>";
						
					//	System.out.println("lastmonth==========>"+mworking);
				//		System.out.println("command==========>"+rs.getString("METRE_INIT_READING"));
					}
					if (pc != 0) 
					{
						if (i == 0)
							xml += "<status>Pumping Return Entry already completed</status>";
						else
							xml += "<status>" + i + "</status>";
					} else {
						xml += "<status>Location Data Not Found</status>";
					}
					xml += "</result>";
					con1.close();
		} else if (process_code.equals("4") && command.equals("add"))
		{
					/*
					 * Table : PMS_DCB_OB_YEARLY Work : for the current month
					 * 
					 * OB_MAINT_CHARGES 4 OB_CUR_YR_WC 4 OB_YESTER_YR_WC 4
					 */
				xml = "<result>";
				int maxsno = 0;
				String OB_MAINT_CHARGES = obj.setValue("OB_MAINT_CHARGES",request);
				String COLN_UPTO_PRV_MTH_MAINT = obj.setValue("COLN_UPTO_PRV_MTH_MAINT", request);
				String OB_YESTER_YR_WC = obj.setValue("OB_YESTER_YR_WC",request);
				String OB_CUR_YR_WC = obj.setValue("OB_CUR_YR_WC", request);
				String DMD_UPTO_PRV_MTH_WC = obj.setValue("DMD_UPTO_PRV_MTH_WC", request);
				String COLN_UPTO_PRV_MTH_YESTER_YR = obj.setValue("COLN_UPTO_PRV_MTH_YESTER_YR", request);
				String COLN_UPTO_PRV_MTH_WC = obj.setValue("COLN_UPTO_PRV_MTH_WC", request);
				String OB_FOR_MTH_CUR_YR_WC = obj.setValue("OB_FOR_MTH_CUR_YR_WC", request);
				String OB_FOR_MTH_YESTER_YR_WC = obj.setValue("OB_FOR_MTH_YESTER_YR_WC", request);
				String OB_FOR_MTH_MAINT_CHARGES = obj.setValue("OB_FOR_MTH_MAINT_CHARGES", request);
				String OFFICE_ID = Office_id;
				year = obj.setValue("year", request);
				month = obj.setValue("month", request);
				BENEFICIARY_TYPE_SNO = obj.setValue("BENEFICIARY_TYPE_SNO",request);
				count = "0";
				maxsno = obj.getMax("PMS_DCB_OB_YEARLY","BENEFICIARY_OB_SNO", "");
				qry="";	
				qry = "insert into PMS_DCB_OB_YEARLY ( BENEFICIARY_OB_SNO"
					+ ",BENEFICIARY_SNO" + ",OFFICE_ID" + ",FIN_YEAR"
					+ ",MONTH" + ",OB_MAINT_CHARGES" + ",OB_CUR_YR_WC"
					+ ",OB_YESTER_YR_WC " + ",UPDATED_BY_USER_ID"
					+ ",UPDATED_DATE,SCH_SNO,OB_FOR_MTH_CUR_YR_WC,OB_FOR_MTH_YESTER_YR_WC,OB_FOR_MTH_MAINT_CHARGES)";
				qry += " values (" + maxsno + "," + BENEFICIARY_SNO + ","+ OFFICE_ID + "," + year + ",4," + OB_MAINT_CHARGES
					+ "," + OB_CUR_YR_WC + "," + OB_YESTER_YR_WC + "";
				qry += ",'" + userid + "',clock_timestamp()," + SCHEME_SNO + ","+OB_FOR_MTH_CUR_YR_WC+","+OB_FOR_MTH_YESTER_YR_WC+","+OB_FOR_MTH_MAINT_CHARGES+")";
		//		String apr_count = obj.getValue("PMS_DCB_OB_YEARLY","count(*)", "where FIN_YEAR=" + year+ " and MONTH=4 and BENEFICIARY_SNO="+ BENEFICIARY_SNO + " and SCH_SNO="+ SCHEME_SNO);
		 	
	        	String apr_count = obj.getValue("PMS_DCB_OB_YEARLY","count(*)", "where FIN_YEAR=" + year+ " and MONTH=4 and BENEFICIARY_SNO="+ BENEFICIARY_SNO + " and SCH_SNO="+ SCHEME_SNO +" and OFFICE_ID="+OFFICE_ID);

				
				if (Integer.parseInt(apr_count) == 0) 
		 		{
								row = obj.setUpd(qry);
		 		}
		 		if (Integer.parseInt(month) !=4)
		 		{
		 			maxsno = obj.getMax("PMS_DCB_OB_YEARLY","BENEFICIARY_OB_SNO", "");
		 	//		count = obj.getValue("PMS_DCB_OB_YEARLY", "count(*)","where FIN_YEAR=" + year + " and MONTH=" + month+ " and BENEFICIARY_SNO=" + BENEFICIARY_SNO+ " and SCH_SNO=" + SCHEME_SNO);
		 			
		 			count = obj.getValue("PMS_DCB_OB_YEARLY", "count(*)","where FIN_YEAR=" + year + " and MONTH=" + month+ " and BENEFICIARY_SNO=" + BENEFICIARY_SNO+ " and SCH_SNO=" + SCHEME_SNO +" and OFFICE_ID="+OFFICE_ID);
	
		 			
		 			qry = "insert into PMS_DCB_OB_YEARLY ( BENEFICIARY_OB_SNO"
									+ ",BENEFICIARY_SNO" + ",OFFICE_ID" + ",FIN_YEAR"
									+ ",MONTH" + ",OB_MAINT_CHARGES"
									+ ",COLN_UPTO_PRV_MTH_MAINT" + ",OB_YESTER_YR_WC"
									+ ",OB_CUR_YR_WC," + "DMD_UPTO_PRV_MTH_WC,"
									+ "COLN_UPTO_PRV_MTH_YESTER_YR," + "SCH_SNO,"
									+ "COLN_UPTO_PRV_MTH_WC" + ",UPDATED_BY_USER_ID,"
									+ "UPDATED_DATE," + "OB_FOR_MTH_CUR_YR_WC,"
									+ "OB_FOR_MTH_YESTER_YR_WC,"
									+ "OB_FOR_MTH_MAINT_CHARGES)";
		 			qry += " values (" + maxsno + "," + BENEFICIARY_SNO + ","+ OFFICE_ID + "," + year + "," + month + ","+ OB_MAINT_CHARGES + "," + COLN_UPTO_PRV_MTH_MAINT+ ","; // OB_INT_PRV_YR_MAINT+","+
		 			qry += OB_YESTER_YR_WC + "," + OB_CUR_YR_WC + ","+ DMD_UPTO_PRV_MTH_WC + ","+ COLN_UPTO_PRV_MTH_YESTER_YR + "," + SCHEME_SNO+ "," + COLN_UPTO_PRV_MTH_WC + ",'" + userid + "'";
		 			qry += ",clock_timestamp()," + OB_FOR_MTH_CUR_YR_WC + ","+ OB_FOR_MTH_YESTER_YR_WC + ","+ OB_FOR_MTH_MAINT_CHARGES + ")";
		 			row = 0;
		 			xml = "<result>";
							if (Integer.parseInt(count) == 0 )    
							{
								row = obj.setUpd(qry); // INSERT TO YEARLY 
								if (row > 0)
								{
									xml += "<status>1</status><astatus>0</astatus><maxsno>" + maxsno+ "</maxsno>";
									xml += "<BENEFICIARY_NAME>"+ obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME"," where "+new_cond+" BENEFICIARY_SNO="+ BENEFICIARY_SNO)+ "</BENEFICIARY_NAME>";
									xml += "<SCHEME_NAME><![CDATA["+ obj.getValue("PMS_SCH_MASTER","SCH_NAME", " where SCH_SNO="+ SCHEME_SNO)+ "]]></SCHEME_NAME>";
									xml += "<OFFICE_NAME>"+ obj.getValue("com_mst_all_offices_view","OFFICE_NAME","where DIVISION_OFFICE_ID="+ divcode+ " and OFFICE_LEVEL_ID='DN'")+ "</OFFICE_NAME>";
									xml += "<BENEFICIARY_TYPE>"+ obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC"," where BEN_TYPE_ID="+ BENEFICIARY_TYPE_SNO)+ "</BENEFICIARY_TYPE>";
									xml += "<entryfor>DCB</entryfor>";
								}
							}
							else {
									xml += "<status>0</status><astatus>"+count+"</astatus>";
								}
		 		}else
		 		{		if (Integer.parseInt(apr_count) == 0) 
		 				{
		 					xml += "<status>1</status><astatus>0</astatus>";
		 				}else
		 				{
		 					xml += "<status>0</status><astatus>"+apr_count+"</astatus>";
		 				}
		 		}
		int month_set=0 , year_set=0;
		if ((Integer.parseInt(month))==1)
		{
			month_set=12;
			year_set=Integer.parseInt(year)-1;
		}
		else
		{
			month_set=(Integer.parseInt(month)-1) ;
			year_set=Integer.parseInt(year);
		}
					// ///////////////////////////////////////////////////////////////
					// CLOSING BALANCE of PRV month
					// ///////////////////////////////////////////////////////////////
			int mnt = month_set;
		String count_monthly = obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "count(*)","where FIN_YEAR=" + year_set + " and MONTH=" + month_set+ " and BENEFICIARY_SNO=" + BENEFICIARY_SNO+ " and SCH_SNO=" + SCHEME_SNO);
			qry = "INSERT INTO PMS_DCB_TRN_CB_MONTHLY " + "( "
				+ "   BENEFICIARY_OB_SNO, "
				+ "   BENEFICIARY_SNO, " + "   OFFICE_ID, "
				+ "   FIN_YEAR, " + "   MONTH, " + "   SCH_SNO, "
				+ "	 CB_MAINT_CHARGES, " + "	 CB_YESTER_YR_WC, "
				+ "	 CB_CUR_YR_WC, "
				+ "   COLN_UPTO_MTH_YESTER_YR,"
				+ "	 COLN_UPTO_MTH_WC,"
				+ "   COLN_UPTO_MTH_MAINT ,"
				+ "	 UPDATED_BY_USER_ID, "
				+ "   UPDATED_DATE,DMD_UPTO_MTH_WC " + ") ";
			qry += " VALUES (" + maxsno + "," + BENEFICIARY_SNO + ","
				+ OFFICE_ID + "," + year_set + "," + month_set + ","
				+ SCHEME_SNO + "," + OB_FOR_MTH_MAINT_CHARGES + ",";
			qry += OB_FOR_MTH_YESTER_YR_WC + "," + OB_FOR_MTH_CUR_YR_WC
				+ "," + "" + COLN_UPTO_PRV_MTH_YESTER_YR + ","
				+ COLN_UPTO_PRV_MTH_WC + ","
				+ COLN_UPTO_PRV_MTH_MAINT + ",'" + userid
				+ "',clock_timestamp()," + DMD_UPTO_PRV_MTH_WC + ")";
			//CB_INT_CUR_YR_MAINT
			// ///////////////////////////////////////////////////////////////
			
			
			
			 System.out.println("OB Updated for the Beneficiary");
			 proc_stmt = con.prepareCall("call pms_dcb_beneficiary_ob_log (?,?,?,?,?::numeric,?::numeric,?) ");
			 proc_stmt.setInt(  1, Integer.parseInt(BENEFICIARY_SNO));
			 proc_stmt.setInt( 2,Integer.parseInt(SCHEME_SNO));
			 proc_stmt.setInt(3,Integer.parseInt(year));
			 proc_stmt.setInt(4,Integer.parseInt(month));
			 proc_stmt.setLong( 5,0 );
			 proc_stmt.setLong(6,0);
			 proc_stmt.setString(7,userid);
			 

			 proc_stmt.execute();
			 
			if (Integer.parseInt(count_monthly) == 0) 
			{
				int up_row = obj.setUpd(qry);  
			}
			xml += "</result>";
			} else if (process_code.equals("10") && command.equals("add")) 
			{
			xml = "<result>";
			
			
			
			
			
		
			
			
			
			String eOB_YESTER_YR_WC = obj.setValue("OB_YESTER_YR_WC",request);
			String eOB_CUR_YR_WC = obj.setValue("OB_CUR_YR_WC", request);
			String eOB_MAINT_CHARGES = obj.setValue("OB_MAINT_CHARGES",request);
			
			String sch_sno = obj.setValue("mSCH_SNO",request);
			  year = obj.setValue("year",request);
			  month = obj.setValue("month",request);
			  ben_sno = obj.setValue("BENEFICIARY_SNO",request);
			  
			  long current_wc_ob=0;
			  long current_int_ob=0;

			  String previous_ob_qry="	Select * from ("
						+ "					Select fin_year as year , month, beneficiary_sno ,sch_sno,  sum(coalesce(ob_for_mth_yester_yr_wc,0) +coalesce(ob_for_mth_cur_yr_wc,0)+ coalesce(ob_for_mth_maint_charges,0)) as ob_wc,"
						+ "					sum(coalesce(ob_for_mth_int_amt_wc,0)) as ob_int "
						+ "					  from pms_dcb_ob_yearly "
						+ "						group by fin_year ,month, beneficiary_sno ,sch_sno"
						+ "						)  as ob where "
						+ "					beneficiary_sno = "+ben_sno+" and year="+year+" and month="+month+" and sch_sno="+sch_sno+"";
			
	             ps = con.prepareStatement(previous_ob_qry) ;
	             ResultSet rs = ps.executeQuery();
	             while (rs.next()) {
	            	    current_wc_ob = (long) Double.parseDouble(rs.getString("ob_wc"));  // Convert to double, then cast to long
	            	    current_int_ob = (long) Double.parseDouble(rs.getString("ob_int")); // Convert to double, then cast to long
	            	}

			  
			
			String eDMD_UPTO_PRV_MTH_WC = obj.setValue("DMD_UPTO_PRV_MTH_WC", request);
			String eCOLN_UPTO_PRV_MTH_MAINT = obj.setValue("COLN_UPTO_PRV_MTH_MAINT", request);
			String eCOLN_UPTO_PRV_MTH_YESTER_YR = obj.setValue("COLN_UPTO_PRV_MTH_YESTER_YR", request);
			String eCOLN_UPTO_PRV_MTH_WC = obj.setValue("COLN_UPTO_PRV_MTH_WC", request);
			String selsno = obj.setValue("selsno", request);
			String selsno_month = obj.setValue("mobsno", request);
			String OB_FOR_MTH_CUR_YR_WC = obj.setValue("OB_FOR_MTH_CUR_YR_WC", request);
			String OB_FOR_MTH_YESTER_YR_WC = obj.setValue("OB_FOR_MTH_YESTER_YR_WC", request);
			String OB_FOR_MTH_MAINT_CHARGES = obj.setValue("OB_FOR_MTH_MAINT_CHARGES", request);
			qry = "";
			qry = "update PMS_DCB_OB_YEARLY " + "set OB_YESTER_YR_WC="
					+ eOB_YESTER_YR_WC + "," + "OB_CUR_YR_WC="
					+ eOB_CUR_YR_WC + "," + "OB_MAINT_CHARGES="
					+ eOB_MAINT_CHARGES + "," + "DMD_UPTO_PRV_MTH_WC="
					+ eDMD_UPTO_PRV_MTH_WC + ","
					+ " COLN_UPTO_PRV_MTH_MAINT="
					+ eCOLN_UPTO_PRV_MTH_MAINT + ","
					+ "COLN_UPTO_PRV_MTH_YESTER_YR="
					+ eCOLN_UPTO_PRV_MTH_YESTER_YR + ","
					+ "COLN_UPTO_PRV_MTH_WC=" + eCOLN_UPTO_PRV_MTH_WC+" , "
					+ "OB_FOR_MTH_CUR_YR_WC="+OB_FOR_MTH_CUR_YR_WC+""+" , "
					+ "OB_FOR_MTH_YESTER_YR_WC="+OB_FOR_MTH_YESTER_YR_WC+" , "
					+ "OB_FOR_MTH_MAINT_CHARGES="+OB_FOR_MTH_MAINT_CHARGES+""+""
				//	+ "" + " where BENEFICIARY_OB_SNO =" + selsno;
								+" where BENEFICIARY_SNO="+ben_sno+" and FIN_YEAR="+year+" and month="+month+" and sch_sno="+sch_sno+" and office_id="+Office_id;  
			 int up_row = obj.setUpd(qry);
			 
			// ///////////////////////////////////////////////////////////////
			// CLOSING BALANCE
			// ///////////////////////////////////////////////////////////////
		 	int month_set_2=Integer.parseInt(obj.getValue("PMS_DCB_OB_YEARLY","month","where BENEFICIARY_OB_SNO =" + selsno));
		 	int year_set_2=Integer.parseInt(obj.getValue("PMS_DCB_OB_YEARLY","FIN_YEAR","where BENEFICIARY_OB_SNO =" + selsno));
			if (month_set_2==1)
			{  
				month_set_2=12;
				year_set_2=year_set_2-1;
			}
			else
			{  
				month_set_2=month_set_2-1 ;
				year_set_2=Integer.parseInt(year);
			}     
			int monthly_count=obj.getCount("PMS_DCB_TRN_CB_MONTHLY", " where fin_year="+year_set_2+" and month="+month_set_2+" and office_id="+Office_id+" and BENEFICIARY_SNO="+ben_sno+" and sch_sno="+obj.getValue("PMS_DCB_OB_YEARLY","SCH_SNO","where BENEFICIARY_OB_SNO =" + selsno));
			 if (selsno_month.equalsIgnoreCase("0") && !selsno.equalsIgnoreCase("0"))
			{  
					String DMD_UPTO_PRV_MTH_WC_yearly=obj.getValue("PMS_DCB_OB_YEARLY","DMD_UPTO_PRV_MTH_WC","where BENEFICIARY_OB_SNO =" + selsno);
					String DMD_INT_UPTO_PRV_MTH_WC_yearly=obj.getValue("PMS_DCB_OB_YEARLY","DMD_INT_UPTO_PRV_MTH_WC","where BENEFICIARY_OB_SNO =" + selsno);
					String COLN_UPTO_PRV_MTH_MAINT_yearly=obj.getValue("PMS_DCB_OB_YEARLY","COLN_UPTO_PRV_MTH_MAINT","where BENEFICIARY_OB_SNO =" + selsno);
					String COLN_UPTO_PRV_MTH_YESTER_YR_yearly=obj.getValue("PMS_DCB_OB_YEARLY","COLN_UPTO_PRV_MTH_YESTER_YR","where BENEFICIARY_OB_SNO =" + selsno);
					String COLN_UPTO_PRV_MTH_WC_yearly=obj.getValue("PMS_DCB_OB_YEARLY","COLN_UPTO_PRV_MTH_WC","where BENEFICIARY_OB_SNO =" + selsno);
					String COLN_INT_UPTO_PRV_MTH_MAINT_yearly=obj.getValue("PMS_DCB_OB_YEARLY","COLN_INT_UPTO_PRV_MTH_MAINT","where BENEFICIARY_OB_SNO =" + selsno);
					String COLN_INT_UPTO_PRV_MTH_WC_yearly=obj.getValue("PMS_DCB_OB_YEARLY","COLN_INT_UPTO_PRV_MTH_WC","where BENEFICIARY_OB_SNO =" + selsno);
					int maxsno2 = obj.getMax("PMS_DCB_TRN_CB_MONTHLY","BENEFICIARY_OB_SNO", "");
					qry = "";
					qry = "INSERT INTO PMS_DCB_TRN_CB_MONTHLY " + "( "
						+ "   BENEFICIARY_OB_SNO, "
						+ "   BENEFICIARY_SNO, " + "   OFFICE_ID, "
						+ "   FIN_YEAR, " + "   MONTH, " + "   SCH_SNO, "
						+ "	 CB_MAINT_CHARGES, " + "	 CB_YESTER_YR_WC, "
						+ "	 CB_CUR_YR_WC, "
						+ "	 UPDATED_BY_USER_ID, "  
						+ "  UPDATED_DATE," +
								"DMD_UPTO_MTH_WC," +
								"DMD_INT_UPTO_MTH_WC," +
								"COLN_UPTO_MTH_MAINT," +
								"COLN_UPTO_MTH_YESTER_YR," +
								"COLN_UPTO_MTH_WC," +
								"COLN_INT_UPTO_MTH_MAINT," +
								"COLN_INT_UPTO_MTH_WC) ";
					qry += " VALUES (" + maxsno2 + "," + BENEFICIARY_SNO + ","
					+ Office_id + "," + year_set_2 + "," + month_set_2 + ","
					+ obj.getValue("PMS_DCB_OB_YEARLY","SCH_SNO","where BENEFICIARY_OB_SNO =" + selsno) + "," + OB_FOR_MTH_MAINT_CHARGES + ",";
					qry += OB_FOR_MTH_YESTER_YR_WC + "," + OB_FOR_MTH_CUR_YR_WC+",'" + userid
					+ "',clock_timestamp(),"+DMD_UPTO_PRV_MTH_WC_yearly+","+DMD_INT_UPTO_PRV_MTH_WC_yearly+","+COLN_UPTO_PRV_MTH_MAINT_yearly+","+COLN_UPTO_PRV_MTH_YESTER_YR_yearly
					+","+COLN_UPTO_PRV_MTH_WC_yearly+","+COLN_INT_UPTO_PRV_MTH_MAINT_yearly+","+COLN_INT_UPTO_PRV_MTH_WC_yearly+")";
					 ///////////////////////////////////////////////////////////////
				 	up_row = obj.setUpd(qry);
					
			}else
			{
			int prv_year=obj.prv_year(Integer.parseInt(year), Integer.parseInt(month));
			int prv_month=obj.prv_month(Integer.parseInt(year), Integer.parseInt(month));
			qry = "UPDATE PMS_DCB_TRN_CB_MONTHLY " + "SET "    
					+ "	 CB_MAINT_CHARGES=" + OB_FOR_MTH_MAINT_CHARGES + ", "
					+ "	 CB_YESTER_YR_WC=" + OB_FOR_MTH_YESTER_YR_WC + ", "
					+ "	 CB_CUR_YR_WC=" + OB_FOR_MTH_CUR_YR_WC + ", "
					+ "	 UPDATED_BY_USER_ID='" + userid + "',COLN_UPTO_MTH_WC= "+eCOLN_UPTO_PRV_MTH_WC+",DMD_UPTO_MTH_WC="+eDMD_UPTO_PRV_MTH_WC+","+ ""
					+ "  COLN_UPTO_MTH_MAINT="+eCOLN_UPTO_PRV_MTH_MAINT+",COLN_UPTO_MTH_YESTER_YR="+eCOLN_UPTO_PRV_MTH_YESTER_YR+", UPDATED_DATE=clock_timestamp() "
					//+ "WHERE BENEFICIARY_OB_SNO=" + selsno_month;
							
				+" where BENEFICIARY_SNO="+ben_sno+" and FIN_YEAR="+prv_year+" and month="+prv_month+" and sch_sno="+sch_sno+" and office_id="+Office_id;
			 ///////////////////////////////////////////////////////////////
			 up_row = obj.setUpd(qry);
			  
			 }
			 
			 
			 
			 
			 System.out.println("OB Updated for the Beneficiary");
			 proc_stmt = con.prepareCall("call pms_dcb_beneficiary_ob_log (?,?,?,?,?::numeric,?::numeric,?) ");
			 proc_stmt.setInt(  1, Integer.parseInt(ben_sno));
			 proc_stmt.setInt( 2,Integer.parseInt(sch_sno));
			 proc_stmt.setInt(3,Integer.parseInt(year));
			 proc_stmt.setInt(4,Integer.parseInt(month));
			 proc_stmt.setLong( 5,current_wc_ob );
			 proc_stmt.setLong(6,current_int_ob);
			 proc_stmt.setString(7,userid);
			 

			 proc_stmt.execute();

			 
			 
			xml += "<up_row>" + up_row + "</up_row></result>";
			} else if (process_code.equals("6") && command.equals("add")) {
					//    INTEREST     UPDATE
				 year = obj.setValue("year",request);
				  month = obj.setValue("month",request);
				int prv_year=obj.prv_year(Integer.parseInt(year), Integer.parseInt(month));
				int prv_month=obj.prv_month(Integer.parseInt(year), Integer.parseInt(month));
					xml = "<result>";
					String OB_INT_CUR_YR_MAINT = obj.setValue("OB_INT_CUR_YR_MAINT", request);
					String OB_INT_PRV_YR_MAINT = obj.setValue("OB_INT_PRV_YR_MAINT", request);
					String COLN_INT_UPTO_PRV_MTH_MAINT = obj.setValue("COLN_INT_UPTO_PRV_MTH_MAINT", request);
					String OB_INT_AMT_WC = obj.setValue("OB_INT_AMT_WC",	request);
					String DMD_INT_UPTO_PRV_MTH_WC = obj.setValue("DMD_INT_UPTO_PRV_MTH_WC", request);
					String COLN_INT_UPTO_PRV_MTH_WC = obj.setValue("COLN_INT_UPTO_PRV_MTH_WC", request);
					String OB_FOR_MTH_INT_AMT_WC = obj.setValue("OB_FOR_MTH_INT_AMT_WC", request);
					String obsno = obj.setValue("obsno", request);
					String mobsno= obj.setValue("mobsno", request);
					String mobsno_int = obj.setValue("mobsno_int", request);
					String sch_sno = obj.setValue("sch_sno", request);
					  BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO",request);  
					BENEFICIARY_TYPE_SNO = obj.setValue("BENEFICIARY_TYPE_SNO",request);  
					
					System.out.println("OB_INT_CUR_YR_MAINT  "+OB_INT_CUR_YR_MAINT);
					System.out.println("OB_INT_PRV_YR_MAINT  "+OB_INT_PRV_YR_MAINT);
					System.out.println("COLN_INT_UPTO_PRV_MTH_MAINT   "+COLN_INT_UPTO_PRV_MTH_MAINT);
					System.out.println("OB_INT_AMT_WC  "+OB_INT_AMT_WC);
					System.out.println("DMD_INT_UPTO_PRV_MTH_WC  "+DMD_INT_UPTO_PRV_MTH_WC);
					System.out.println("COLN_INT_UPTO_PRV_MTH_WC  "+COLN_INT_UPTO_PRV_MTH_WC);
					System.out.println("OB_FOR_MTH_INT_AMT_WC  "+OB_FOR_MTH_INT_AMT_WC);
				
					  long current_wc_ob=0;
					  long current_int_ob=0;

					  String previous_ob_qry="	Select * from ("
								+ "					Select fin_year as year , month, beneficiary_sno ,sch_sno,  sum(coalesce(ob_for_mth_yester_yr_wc,0) +coalesce(ob_for_mth_cur_yr_wc,0)+ coalesce(ob_for_mth_maint_charges,0)) as ob_wc,"
								+ "					sum(coalesce(ob_for_mth_int_amt_wc,0)) as ob_int "
								+ "					  from pms_dcb_ob_yearly "
								+ "						group by fin_year ,month, beneficiary_sno ,sch_sno"
								+ "						)  as ob where "
								+ "					beneficiary_sno = "+BENEFICIARY_SNO+" and year="+year+" and month="+month+" and sch_sno="+sch_sno+"";
					
			             ps = con.prepareStatement(previous_ob_qry) ;
			             ResultSet rs = ps.executeQuery();
			             while (rs.next()) {
			            	    current_wc_ob = (long) Double.parseDouble(rs.getString("ob_wc"));  // Convert to double, then cast to long
			            	    current_int_ob = (long) Double.parseDouble(rs.getString("ob_int")); // Convert to double, then cast to long
			            	}
					
					
				String upqry = "update PMS_DCB_OB_YEARLY set OB_INT_CUR_YR_MAINT="
						+ OB_INT_CUR_YR_MAINT
						+ ", OB_FOR_MTH_INT_CY_MAINT=("	+( Double.parseDouble(OB_INT_CUR_YR_MAINT)+ Double.parseDouble(OB_INT_PRV_YR_MAINT) )+ "-"+ COLN_INT_UPTO_PRV_MTH_MAINT	+ ")"
						+ ", OB_INT_PRV_YR_MAINT="+ OB_INT_PRV_YR_MAINT+ "," +
						"   COLN_INT_UPTO_PRV_MTH_MAINT="+ COLN_INT_UPTO_PRV_MTH_MAINT
						+ ", OB_INT_AMT_WC="+ OB_INT_AMT_WC
						+ ", DMD_INT_UPTO_PRV_MTH_WC="+ OB_FOR_MTH_INT_AMT_WC
						+ ",COLN_INT_UPTO_PRV_MTH_WC="+ COLN_INT_UPTO_PRV_MTH_WC+"," +
						" OB_FOR_MTH_INT_AMT_WC="+(Double.parseDouble(OB_INT_AMT_WC)+Double.parseDouble(OB_FOR_MTH_INT_AMT_WC)-Double.parseDouble(COLN_INT_UPTO_PRV_MTH_WC))
						//+ "      where BENEFICIARY_OB_SNO=" + mobsno_int;
						+" where BENEFICIARY_SNO="+BENEFICIARY_SNO+" and FIN_YEAR="+year+" and month="+month+" and sch_sno="+sch_sno+" and office_id="+Office_id;
						
				//BENEFICIARY_SNO = obj.getValue("PMS_DCB_OB_YEARLY","BENEFICIARY_SNO", "where BENEFICIARY_OB_SNO="+ obsno);
					 	int urow = obj.setUpd(upqry);					
					 	String pr_year = obj.getValue("PMS_DCB_OB_YEARLY","FIN_YEAR", "where BENEFICIARY_OB_SNO=" + mobsno_int);
					 			upqry = " update PMS_DCB_OB_YEARLY set  "
					 				+ " OB_INT_PRV_YR_MAINT=" + OB_INT_PRV_YR_MAINT+ "," 
					 				+ " OB_INT_CUR_YR_MAINT=" + OB_INT_CUR_YR_MAINT + "," 
					 				+"  OB_FOR_MTH_INT_AMT_WC="+OB_INT_AMT_WC+", "
					 				+"  OB_INT_AMT_WC="+ OB_INT_AMT_WC + " where BENEFICIARY_SNO=" 
					 				+  BENEFICIARY_SNO + " and MONTH=4 and FIN_YEAR="
					 				+ year+" and SCH_SNO="+sch_sno;
					 			
					 			int urow2 = obj.setUpd(upqry);
					 			xml = "<result>";
					 			if (urow > 0) {
					 					xml += "<status>1</status><obsno>" + obsno + "</obsno>";
					 					xml += "<BENEFICIARY_NAME>"+ obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME"," where "+new_cond+" BENEFICIARY_SNO="+ BENEFICIARY_SNO)+ "</BENEFICIARY_NAME>";
					 					xml += "<BENEFICIARY_TYPE>"+ obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC", " where BEN_TYPE_ID="+ BENEFICIARY_TYPE_SNO)+ "</BENEFICIARY_TYPE>";
					 					xml += "<entryfor>Interest</entryfor>";
					 				}  
					// ///////////////////////////////////////////////////////////////
					// CLOSING BALANCE
					// ///////////////////////////////////////////////////////////////
		String mon_DMD_INT_UPTO_PRV_MTH_WC = obj.getValue("PMS_DCB_OB_YEARLY", "DMD_INT_UPTO_PRV_MTH_WC"," where BENEFICIARY_OB_SNO=" + obsno);
		
		qry = "UPDATE PMS_DCB_TRN_CB_MONTHLY " + " SET "	+ "	CB_INT_CUR_YR_MAINT=("							+ OB_INT_CUR_YR_MAINT
							+ "-"
							+ COLN_INT_UPTO_PRV_MTH_MAINT
							+ ") + "+OB_INT_PRV_YR_MAINT
							// + OB_INT_CUR_YR_MAINT
							+ ", "
							+" CB_INT_AMT_WC="+(Double.parseDouble(OB_INT_AMT_WC)+Double.parseDouble(OB_FOR_MTH_INT_AMT_WC)-Double.parseDouble(COLN_INT_UPTO_PRV_MTH_WC))
							+ " , "
							/*
							 * + "	CB_INT_AMT_WC=" + OB_INT_AMT_WC + " , "
							 */
							+ "	COLN_INT_UPTO_MTH_WC="
							+ COLN_INT_UPTO_PRV_MTH_WC
							+ ","
							+ "	COLN_INT_UPTO_MTH_MAINT="
							+ COLN_INT_UPTO_PRV_MTH_MAINT
							+ ","
							+ "	DMD_INT_UPTO_MTH_WC="
							+ mon_DMD_INT_UPTO_PRV_MTH_WC
							// + (Double.parseDouble(OB_INT_AMT_WC) +
							// Double.parseDouble(DMD_INT_UPTO_PRV_MTH_WC))
							+ ","
							+ "	UPDATED_BY_USER_ID='"  
							+ userid
							+ "', " 
							+ "  UPDATED_DATE=clock_timestamp() "
						//	+ " WHERE BENEFICIARY_OB_SNO=" + mobsno;
						+" where BENEFICIARY_SNO="+BENEFICIARY_SNO+" and FIN_YEAR="+prv_year+" and month="+prv_month+" and sch_sno="+sch_sno+" and office_id="+Office_id;
					urow2 = obj.setUpd(qry);
					
					
					
					 System.out.println("OB INT Updated for the Beneficiary");
					 proc_stmt = con.prepareCall("call pms_dcb_beneficiary_ob_log (?,?,?,?,?::numeric,?::numeric,?) ");
					 proc_stmt.setInt(  1, Integer.parseInt(BENEFICIARY_SNO));
					 proc_stmt.setInt( 2,Integer.parseInt(sch_sno));
					 proc_stmt.setInt(3,Integer.parseInt(year));
					 proc_stmt.setInt(4,Integer.parseInt(month));
					 proc_stmt.setLong( 5,current_wc_ob );
					 proc_stmt.setLong(6,current_int_ob);
					 proc_stmt.setString(7,userid);
					 

					 proc_stmt.execute();
					      
					// ///////////////////////////////////////////////////////////////
					xml +="<status>"+urow2+"</status><up_row>"+urow2+ "</up_row></result>";
				} else if (process_code.equals("12") && command.equals("add")) 
				{
					
					System.out.println("WELCOME=========>"+process_code+"============>"+command);
					
					xml += "<result>";
					qry = "";  
					String rowcnt_meter1="";  
					int ins_row1=0;
					String MONPR_SNO = obj.setValue("MONPR_SNO".trim(), request);
					String netunit = obj.setValue("netunit".trim(), request);
					//int validated_row=obj.getCount("PMS_DCB_MONTHLY_PR", "where MONPR_SNO="+MONPR_SNO+"  and ( PROCESS_FLAG='V' or PROCESS_FLAG='FR')");
					int validated_row=obj.getCount("PMS_DCB_MONTHLY_PR", "where MONPR_SNO="+MONPR_SNO+"  and (  PROCESS_FLAG='FR')");
					System.out.println("validated_row==="+validated_row);
					
					if (validated_row==0)
					{
					 rowcnt_meter1 = obj.setValue("rowcnt_meter", request); 
					qry = "update PMS_DCB_MONTHLY_PR set NET_CONSUMED=? where MONPR_SNO=?  and PROCESS_FLAG='CR'";
					System.out.println("qry-------------->"+qry);
				 	 ps = con.prepareStatement(qry);
					ps.setDouble(1, Double.parseDouble(netunit));
					ps.setInt(2, Integer.parseInt(MONPR_SNO));
					ps.executeUpdate();
					qry = "";
					for (row = 1; row <= Integer.parseInt(rowcnt_meter1); row++)
					{
						METRE_SNO = "";
						METRE_SNO = obj.setValue("METRE_SNO" + row, request);
						String mf1 = obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","MULTIPLY_FACTOR", "where  "+meter_status+"  METRE_SNO="+ METRE_SNO);
						String METRE_INITIAL_READING = obj.setValue("METRE_INITIAL_READING" + row, request);
						String METRE_CLOSING_READING = obj.setValue("METRE_CLOSING_READING" + row, request);
						String QTY_CONSUMED = obj.setValue("QTY_CONSUMED" + row, request);
						String PR_SNO = obj.setValue("PR_SNO" + row, request);
						String ALLOTED_QTY = obj.setValue("ALLOTED_QTY" + row,request);
						String EXCESS_QTY = obj.setValue("EXCESS_QTY" + row,request);
						String METRE_FIXED = obj.setValue("METRE_FIXED" + row,request);
						String METRE_WORKING = obj.setValue("METRE_WORKING"+ row, request);
						Double QTY_CONSUMED_CALC = Double.parseDouble(QTY_CONSUMED);
						Double QTY_CONSUMED_NET = QTY_CONSUMED_CALC* Double.parseDouble(mf1);
						qry = "update PMS_DCB_TRN_MONTHLY_PR  set "
								+ "METRE_INITIAL_READING=?,"
								+ "METRE_CLOSING_READING=?,"
								+ "QTY_CONSUMED=?,"
								+ "QTY_CONSUMED_CALC=?,"
								+ "QTY_CONSUMED_NET="
								+ QTY_CONSUMED_NET+ ","
								+ "METRE_FIXED=?,"
								+ "METRE_WORKING=? where PR_SNO=? and PROCESS_FLAG='CR'";
						ps = con.prepareStatement(qry);
						ps.setDouble(1, Double.parseDouble(METRE_INITIAL_READING));
						ps.setDouble(2, Double.parseDouble(METRE_CLOSING_READING));
						ps.setDouble(3, Double.parseDouble(QTY_CONSUMED));
						ps.setDouble(4, Double.parseDouble(QTY_CONSUMED));
						ps.setString(5, METRE_FIXED);
						ps.setString(6, METRE_WORKING);
						ps.setInt(7, Integer.parseInt(PR_SNO));
						int validated_row_location=obj.getCount("PMS_DCB_TRN_MONTHLY_PR", "where PR_SNO="+PR_SNO+"  and ( PROCESS_FLAG='V' or PROCESS_FLAG='FR')");
						if (validated_row_location==0)
						{
							ps.execute();
							String short_qry = "";
							if (METRE_WORKING.equals("Y")) 
							{
								short_qry = ",METRE_INIT_READING="+ METRE_INITIAL_READING;
							} else {
								short_qry = ",METRE_INIT_READING=0";
							}
							String wstatus = "";
							if (METRE_WORKING.equals("Y"))
							wstatus = " set  METRE_W_WEF='" + MONTH + "/"+ YEAR + "',METRE_NW_WEF='' " + short_qry;
							else
							wstatus = " set  METRE_NW_WEF='" + MONTH + "/"+ YEAR + "',METRE_W_WEF='' " + short_qry;
							qry = "";
							qry = "update PMS_DCB_MST_BENEFICIARY_METRE " + wstatus+ "  where "+meter_status+"  METRE_SNO=" + METRE_SNO;
							ins_row1 = obj.setUpd(qry);
						}
					}
					xml += "<ins_row1>"+ins_row1+"</ins_row1>";
					}
					else
					{
						xml += "<ins_row1>0</ins_row1>";
					}
					xml += "<row>" + rowcnt_meter1 + "</row></result>"; 
				} else if ((process_code.equals("7") || process_code.equals("9")) && command.equals("add")) 
				{
					xml += "<result>";
					String rowcnt_meter = obj.setValue("rowcnt_meter", request);
					row = 0;
					ins_row = 0;
					String OFFICE_ID = Office_id;
					BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);
					String netunit = obj.setValue("netunit", request);
					BENEFICIARY_TYPE_SNO = obj.setValue("BENEFICIARY_TYPE_SNO",request);
					String selected_item = obj.setValue("selected_item",request);
					int temp = 0;
					count = "1";
					for (row = 1; row <= Integer.parseInt(rowcnt_meter); row++)
					{
									if (process_code.equals("9")) 
										temp = Integer.parseInt(selected_item);
									else
										temp = row;
									int PR_SNO = obj.getMax("PMS_DCB_TRN_MONTHLY_PR","PR_SNO", "");
									qry = "";
									METRE_SNO = obj.setValue("METRE_SNO" + temp, request);
									String SUBDIV_OFFICE_ID =obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SUB_DIV_ID","where  "+meter_status+" METRE_SNO=" + METRE_SNO);  
									String mf = obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","MULTIPLY_FACTOR", "where "+meter_status+" METRE_SNO="+ METRE_SNO);
									String schsno = obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SCHEME_SNO","where  "+meter_status+" METRE_SNO=" + METRE_SNO);
									String off_meter_= obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "OFFICE_ID","where  "+meter_status+" METRE_SNO=" + METRE_SNO);
									String ben_meter_= obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "BENEFICIARY_SNO","where  "+meter_status+" METRE_SNO=" + METRE_SNO);
									int cc=1;
								if (Integer.parseInt(ben_meter_)==Integer.parseInt(BENEFICIARY_SNO) && Integer.parseInt(OFFICE_ID)!=0 && Integer.parseInt(off_meter_)==Integer.parseInt(Office_id) && Integer.parseInt(BENEFICIARY_SNO)!=0 )
								{
									String METRE_INITIAL_READING = obj.setValue("METRE_INITIAL_READING" + temp, request);
									String METRE_CLOSING_READING = obj.setValue("METRE_CLOSING_READING" + temp, request);
									String QTY_CONSUMED = obj.setValue("QTY_CONSUMED"+temp, request);
									Double QTY_CONSUMED_CALC = Double.parseDouble(QTY_CONSUMED);
									String ALLOTED_QTY = obj.setValue("ALLOTED_QTY" + temp,request);
									String EXCESS_QTY = obj.setValue("EXCESS_QTY" + temp,request);
									String METRE_FIXED = obj.setValue("METRE_FIXED" + temp,request);
									String METRE_WORKING = obj.setValue("METRE_WORKING"+temp, request);
									String PRVMETRE_WORKING = obj.setValue("PRVMETRE_WORKING" + temp, request);
									String CHILD_METER_QTY = obj.setValue("CHILD_METER_QTY"+temp, request);
									String difference = obj.setValue("difference" + temp,request);
									Double QTY_CONSUMED_NET = QTY_CONSUMED_CALC*Double.parseDouble(mf);
									String AMT_RECTIFY= obj.setValue("AMT_RECTIFY"+temp, request);
									String QTY_RECTIFY= obj.setValue("QTY_RECTIFY"+temp, request);
									String MONTH_RECTIFY= obj.setValue("MONTH_RECTIFY"+temp, request);
									String YEAR_RECTIFY= obj.setValue("YEAR_RECTIFY"+temp, request);
									qry = "insert into   PMS_DCB_TRN_MONTHLY_PR   (PR_SNO,"
											+ "BENEFICIARY_SNO,"
											+ "OFFICE_ID,SUBDIV_OFFICE_ID,"
											+ "MONTH,"
											+ "YEAR,"
											+ "METRE_SNO,"
											+ "METRE_INITIAL_READING,"
											+ "METRE_CLOSING_READING,SCH_SNO,"
											+ "QTY_CONSUMED,"
											+ "METRE_FIXED,"
											+ "METRE_WORKING,UPDATED_BY_USER_ID,"
											+ "UPDATED_DATE,PROCESS_FLAG ,QTY_CONSUMED_CALC,CHILD_METER_QTY,QTY_CONSUMED_NET,AMT_RECTIFY,QTY_RECTIFY) values ("
											+ PR_SNO+ ","+BENEFICIARY_SNO+ ","+ OFFICE_ID+ ","+ SUBDIV_OFFICE_ID+ ","+ MONTH+ ","+ YEAR+","+METRE_SNO
											+ ","+ METRE_INITIAL_READING+ ","+ METRE_CLOSING_READING+ ","+ schsno+ ","+ difference+ " ,'"+ METRE_FIXED+ "',"
											+ "'"+ METRE_WORKING+ "','"+ userid+ "',clock_timestamp(),'CR',"+ QTY_CONSUMED_CALC+ ","+ CHILD_METER_QTY+ ","
											+ QTY_CONSUMED_NET + ","+AMT_RECTIFY+","+QTY_RECTIFY+")";
									count = "1";
									if (Integer.parseInt(METRE_SNO)!=0)
									{
									count = obj.getValue("PMS_DCB_TRN_MONTHLY_PR",
											"count(*)", "where BENEFICIARY_SNO="
													+ BENEFICIARY_SNO + " and METRE_SNO="
													+ METRE_SNO + " and MONTH=" + MONTH
													+ " and YEAR=" + YEAR);
									}else
									{
										count = "1";
									}
									if ( Double.parseDouble(QTY_CONSUMED) >=0	|| BENEFICIARY_TYPE_SNO.equals("6")) {
										if (count.equals("0")) 
										{
											String his_qry = "", short_qry = "";
											// Previous working status change
											String temp_flag = (PRVMETRE_WORKING.equals("Yes")) ? "Y" : "N";
											if (!temp_flag.equals(METRE_WORKING))
											{
												// Record Move to history Table
												his_qry = " insert into PMS_DCB_MST_BEN_METRE_HIST select * from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+"  METRE_SNO="+ METRE_SNO;
												// Working Status no to yes -> get initial
												// reading from user
												// Working Status yes to no -> set initial
												// to zero
												if (METRE_WORKING.equals("Y")) 
												{
													short_qry = ",METRE_INIT_READING="+ METRE_INITIAL_READING;
												}
											}
											int history_row1 = 0;
											try {
												 
												history_row1 = obj.setUpd(his_qry);			
											} catch (Exception e) {
												xml += "<error>Problem Occur in  Meter Move </error>";
											}
											if (Integer.parseInt(count)==0)
											{
												  
												ins_row += obj.setUpd(qry);
											}
											qry = "";
											// Monthly value store
											count = "0";
											count = obj.getValue("PMS_DCB_MONTHLY_PR","count(*)", "where BENEFICIARY_SNO="
															+ BENEFICIARY_SNO
															+ " and MONTH=" + MONTH
															+ " and YEAR=" + YEAR);
											// No Previouse Record
											if (count.equals("0")) {
												qry = "";
												int MONPR_SNO = obj.getMax("PMS_DCB_MONTHLY_PR", "MONPR_SNO","");
												qry = "insert into PMS_DCB_MONTHLY_PR ";
												qry += "(MONPR_SNO,MONTH,YEAR,BENEFICIARY_SNO,NET_CONSUMED,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLAG,OFFICE_ID,MONTH_RECTIFY,YEAR_RECTIFY)";
												qry += " values (" + MONPR_SNO + ","
														+ MONTH + "," + YEAR + ","
														+ BENEFICIARY_SNO + "," + netunit
														+ ",'" + userid
														+ "',clock_timestamp(),'CR',"
														+ OFFICE_ID + ","+MONTH_RECTIFY+","+YEAR_RECTIFY+")";
												int ins_row_mr = obj.setUpd(qry);
												count = "0";
											} else {
												qry = "";   
												String table_MONPR_SNO = obj.getValue("PMS_DCB_MONTHLY_PR", "MONPR_SNO","where BENEFICIARY_SNO="+ BENEFICIARY_SNO+ " and MONTH=" + MONTH+ " and YEAR=" + YEAR);
												String table_NET_CONSUMED = obj.getValue("PMS_DCB_MONTHLY_PR","NET_CONSUMED", "where MONPR_SNO="+ table_MONPR_SNO);
												table_NET_CONSUMED = Float.toString(Float.parseFloat(table_NET_CONSUMED)+ Float.parseFloat(QTY_CONSUMED));
												qry = "update PMS_DCB_MONTHLY_PR set NET_CONSUMED="+table_NET_CONSUMED+",OFFICE_ID="+OFFICE_ID+" where MONPR_SNO="+table_MONPR_SNO;
													 int pr_row1 = obj.setUpd(qry);  
											}   
											String wstatus = "";
											if (METRE_WORKING.equals("Y"))
												wstatus = " set  METRE_W_WEF='"+MONTH+"/"+YEAR+"',METRE_NW_WEF='', METRE_WORKING='"+METRE_WORKING+"'"+short_qry;
											else
												wstatus = " set  METRE_NW_WEF='"+MONTH+"/"+YEAR+"',METRE_W_WEF='', METRE_WORKING='"+METRE_WORKING +"'"+short_qry;
											qry = "";
											qry = "update PMS_DCB_MST_BENEFICIARY_METRE "+wstatus+" where "+meter_status+"  METRE_SNO="+METRE_SNO;
											  
											int ins_row1 = obj.setUpd(qry);
										}
								}
									//System.out.println("For Loop");
						 }// Validation End
					}//System.out.println("For End");
					if (ins_row != 0) {
						xml += "<BENEFICIARY_NAME>"+ obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME"," where "+new_cond+" BENEFICIARY_SNO="+ BENEFICIARY_SNO)+ "</BENEFICIARY_NAME>";
						xml += "<BENEFICIARY_TYPE>"+ obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC", " where BEN_TYPE_ID="+ BENEFICIARY_TYPE_SNO)+ "</BENEFICIARY_TYPE>";
						xml += "<MONTH>" + MONTH + "</MONTH>";
						xml += "<BENEFICIARY_SNO>" + BENEFICIARY_SNO+ "</BENEFICIARY_SNO>";
						xml += "<METRE_SNO>" + METRE_SNO + "</METRE_SNO>";
						xml += "<YEAR>" + YEAR + "</YEAR>";
						xml += "<selected_item>" + selected_item+ "</selected_item><status>" + ins_row+ "</status> ";
					  } 
					xml += "<status>" + ins_row+ "</status><ins_row>" + ins_row + "</ins_row>";
					xml += "</result>";
				}
				PrintWriter pr = response.getWriter();
				pr.println(xml);
 				//pr.flush();
				//pr.close(); 
			}  
			obj.conClose(con);
		} catch (Exception e) 
		{
			System.out.println("------------------------------------" + e);
			e.printStackTrace();
			error_flag = 1;
		}
		if (error_flag == 1) {
			String erxml = "";
			erxml = "<result><status> Loading is in Progress ...............Please Wait </status></result>";
			PrintWriter er = response.getWriter();
			er.write(erxml);
			er.flush();
		}
		System.out.println("DCB->Beneficiary_DCB_ob->command->"+command+"->process_code->"+process_code+"->END"); 
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//    Auto-generated method stub
	}
}