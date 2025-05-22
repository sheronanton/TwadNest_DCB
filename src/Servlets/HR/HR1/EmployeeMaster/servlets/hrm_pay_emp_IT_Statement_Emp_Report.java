package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class hrm_pay_emp_IT_Statement_Emp_Report extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	String sub_type = "";
	String acc_no = "", netpay = "", name = "", employee_id = "", bname = "",
			braname = "";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;

		ResultSet result = null;

		PreparedStatement ps = null;
		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		String strCommand = "", year, dateofRequest, sql;
		String xml = "";
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		
		// System.out.println("hai=========>");
		try {
			if (session == null) {
//				System.out.println(request.getContextPath()	+ "/index.jsp?message=sessionout");
				response.sendRedirect(request.getContextPath()+ "/index.jsp?message=sessionout");
				return;
			}
//			System.out.println(session);
		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			LoadDriver con1 = new LoadDriver();
			connection = con1.getConnection();

		} catch (Exception e) {

			pw.write(xml);
			pw.flush();
			pw.close();
//			System.out.println("databse connection error");
			return;
		}

		if (strCommand.equalsIgnoreCase("Getemp")) {

			xml = "";
			xml += "<response><command>Getemp</command>";
			try {
				int off_id = Integer.parseInt(request.getParameter("off_id"));

				String fin_year = request.getParameter("fin_year");
//				int smonth = Integer.parseInt(request.getParameter("smonth"));
				
				sql = "SELECT SLNO, " + "  EMPLOYEE_ID "
						+ " FROM HRM_PAY_EMP_IT_STMT_MST "
						+ " WHERE ACCOUNTING_FOR_OFFICE_ID ='" + off_id + "' "
						+ " AND FNINANCIAL_YEAR               ='" + fin_year
						+ "'";

				ps = connection.prepareStatement(sql);

				result = null;
				result = ps.executeQuery(sql);

				while (result.next()) {

					xml = xml + "<count><SLNO>" + result.getInt("SLNO")
							+ "</SLNO>";
					xml = xml + "<EMPLOYEE_ID>" + result.getInt("EMPLOYEE_ID")
							+ "</EMPLOYEE_ID></count>";
				}

				xml += "<status>success</status>";

				result.close();
				ps.close();
			} catch (Exception e) {
				System.out.println("exception" + e);
				xml += "<status>failure</status>";
			}
			xml += "</response>";
		}

		System.out.println(xml);
		pw.write(xml);
		pw.flush();
		pw.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		ResultSet results3 = null;
		PreparedStatement ps = null;
		response.setContentType("text/xml");
		Calendar cal = Calendar.getInstance();
		int year1 = cal.get(Calendar.YEAR);

		int month = cal.get(Calendar.MONTH) + 1;
//		System.out.println("Current month & Year: " + month + " " + year1);
		String strCommand = "", year, dateofRequest, sql;
		String xml = "";
		response.setHeader("Cache-Control", "no-cache");

		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
//				System.out.println(request.getContextPath()	+ "/index.jsp?message=sessionout");
				response.sendRedirect(request.getContextPath()+ "/index.jsp?message=sessionout");
				return;
			}
//			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {

			LoadDriver con1 = new LoadDriver();
			connection = con1.getConnection();
		} catch (Exception e) {
			if (strCommand.equalsIgnoreCase("Get")) {
				xml = "Database Service not Available";
			} else {
				xml = "<response><status>success</status><value>databaseError</value></response>";
			}

//			System.out.println("databse connection error");
			return;
		}
//		System.out.println("----->inside It");

		String month_name = "";
		String monthNames[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
				"JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

		String fin_year = request.getParameter("fin_year");
		int from_years=Integer.parseInt(fin_year.substring(0, 4));
//		System.out.println("FROM YEAR :"+from_year);
		int from_months=3;
		int to_months=2;
		int to_years=Integer.parseInt(fin_year.substring(5, 9));
		int acc_office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
		month_name = monthNames[month - 1];

		String output = request.getParameter("output");
		PreparedStatement psq=null,psm=null;
		ResultSet rst=null;
		String emp_id1 = request.getParameter("eid");
		int emp_id = 0;
		// / String month_name=monthNames[salmonths-1];

//		System.out.println("EID : " + emp_id);
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			HSSFCellStyle style = hwb.createCellStyle();
			style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
			HSSFFont font = hwb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 10);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.BLUE.index);
			style.setFont(font);

			HSSFCellStyle style1 = hwb.createCellStyle();
			HSSFFont font1 = hwb.createFont();
			font1.setFontName(HSSFFont.FONT_ARIAL);
			font1.setFontHeightInPoints((short) 10);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setColor(HSSFColor.GREEN.index);
			style1.setAlignment(style1.ALIGN_CENTER);
			style1.setFont(font1);

			HSSFCellStyle style2 = hwb.createCellStyle();
			HSSFFont font2 = hwb.createFont();
			font2.setFontName(HSSFFont.FONT_ARIAL);
			font2.setFontHeightInPoints((short) 10);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font2.setColor(HSSFColor.BROWN.index);
			style2.setAlignment(style2.ALIGN_RIGHT);
			style2.setFont(font2);

			HSSFCellStyle style3 = hwb.createCellStyle();
			HSSFFont font3 = hwb.createFont();
			font3.setFontName(HSSFFont.FONT_ARIAL);
			font3.setFontHeightInPoints((short) 10);
			font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font3.setColor(HSSFColor.PINK.index);
			style3.setAlignment(style3.ALIGN_RIGHT);
			style3.setFont(font3);

			HSSFCellStyle style4 = hwb.createCellStyle();
			style4.setAlignment(style3.ALIGN_CENTER);
			style4.setFont(font);

			HSSFCellStyle style5 = hwb.createCellStyle();
			style5.setAlignment(style5.ALIGN_LEFT);
			style5.setFont(font2);

			
				int rowlen = 1;

				emp_id = Integer.parseInt(emp_id1);

				//
				// We also define the font that we are going to use for
				// displaying the
				// data of the cell. We set the font to ARIAL with 20pt in size
				// and
				// make it BOLD and give blue as the color.
				//

				sql = "SELECT SLNO, " + "  EMPLOYEE_ID, " + "  FROM_YEAR, "
						+ "  FROM_MONTH, " + "  TO_YEAR, " + "  TO_MONTH, "
						+ "  ACCOUNTING_FOR_OFFICE_ID, " + "  UPDATED_BY, "
						+ "  UPDATE_TIME, " + "  PROCESS_FLOW_ID, "
						+ "  PROCESS_YEAR, " + "  PROCESS_MONTH "
						+ " FROM HRM_PAY_EMP_IT_STMT_MST "
						+ " WHERE EMPLOYEE_ID=? " + " AND FNINANCIAL_YEAR =? ";
						
				ps = connection.prepareStatement(sql);
				ps.setInt(1, emp_id);
				ps.setString(2, fin_year);
				
				result = ps.executeQuery();
				if (result.next()) {

					String emp_name = "";
					String desig = "";
					String empid = "";
					String emp_desig = "";
					String from_year = "";
					String from_month = "";
					String to_year = "";
					String to_month = "";
					String frm_to_year = "";
					// String [] empid =new String[500] ;
					// String [] emp_name =new String[500] ;
					String[] payid = new String[500];
					String[] salyear = new String[500];
					String[] salmonth = new String[500];
					String[] pay_ele_val = new String[500];
					String[] pay_tye_id = new String[500];
					String[] text = new String[1000];

					String[] pay_ele_id = new String[500];
					String[] pay_ele_type_id = new String[500];
					String[] pay_el_name = new String[500];
					String[] pay_tot = new String[500];

					HSSFRow row = null;
					int i = 0;
					int l = 3;

					int slno = result.getInt("SLNO");

					
					int months =3;
					int years=from_years;
					for(int k=0;k<12;k++)
					{
						

						String sqls="INSERT INTO HRM_PAY_EMP_IT_STMT_TRN " +
								"SELECT SLNO, " +
								"  "+years+","+months+", " +
								"  x.pay_element_id, " +
								"  NVL(pay_element_value,0) AS pay_value " +
								"FROM " +
								"  (SELECT DISTINCT a.pay_element_id, " +
								"    B.PAY_ELEMENT_TYPE_ID, " +
								"    SLNO " +
								"  FROM HRM_PAY_EMP_IT_STMT_TRN a , " +
								"    HRM_PAY_ELEMENTS_MST B " +
								"  WHERE SLNO          ="+slno+" " +
								"  AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID " +
								"  )x " +
								"LEFT OUTER JOIN " +
								"  (SELECT PAY_ELEMENT_ID AS PAY_ID , " +
								"    SALARY_YEAR, " +
								"    SALARY_MONTH, " +
								"    PAY_ELEMENT_VALUE " +
								"  FROM HRM_PAY_EMP_IT_STMT_TRN " +
								"  WHERE SLNO      ="+slno+" " +
								"  AND salary_year ="+years+" " +
								"  AND salary_month="+months+" " +
								"  )y " +
								"ON y.pay_id              =x.pay_element_id " +
								"WHERE pay_element_value IS NULL" ;
//								System.out.println(sqls);
								psm=connection.prepareStatement(sqls);
								psm.executeUpdate();
						

						months=months+1;
						
						if(months==13)
						{
							months=1;
							years=years+1;
						}
						
					}
					sql = "SELECT SLNO, "
							+ "  mst.EMPLOYEE_ID AS EMPLOYEE_ID, "
							+ "  empname, " + "  DESIGNATION, "
							+ "  FROM_YEAR, " + "  FROM_MONTH, "
							+ "  TO_YEAR, " + "  TO_MONTH, "
							+ "  ACCOUNTING_FOR_OFFICE_ID, "
							+ "  PROCESS_YEAR, " + "  PROCESS_MONTH " + "FROM "
							+ "  (SELECT SLNO, " + "    EMPLOYEE_ID, "
							+ "    FROM_YEAR, " + "    FROM_MONTH, "
							+ "    TO_YEAR, " + "    TO_MONTH, "
							+ "    ACCOUNTING_FOR_OFFICE_ID, "
							+ "    PROCESS_YEAR, " + "    PROCESS_MONTH "
							+ "  FROM HRM_PAY_EMP_IT_STMT_MST "
							+ "  WHERE EMPLOYEE_ID= ? " + "  )mst " + "JOIN "
							+ "  (SELECT EMPLOYEE_ID, "
							+ "    EMPLOYEE_INITIAL " + "    || '.' "
							+ "    || EMPLOYEE_NAME AS empname, "
							+ "    DESIGNATION " + "  FROM VIEW_EMPLOYEE2 "
							+ "  )sub " + "ON sub.EMPLOYEE_ID=mst.EMPLOYEE_ID";
					ps = connection.prepareStatement(sql);
					ps.setInt(1, emp_id);
					ResultSet rs1 = ps.executeQuery();
					if (rs1.next()) {
						empid = rs1.getString("EMPLOYEE_ID");
						emp_name = rs1.getString("empname");
						desig = rs1.getString("DESIGNATION");
						emp_desig = emp_name + "  " + desig;
					}
					rs1.close();
					ps.close();
					String sqls = " SELECT EMPLOYEE_ID, " + "  FROM_YEAR, "
							+ "  FROM_MONTH, " + "  TO_YEAR, " + "  TO_MONTH, "
							+ "  PROCESS_YEAR, " + "  PROCESS_MONTH "
							+ " FROM HRM_PAY_EMP_IT_STMT_MST "
							+ " WHERE EMPLOYEE_ID= ? "
							+ "  AND FNINANCIAL_YEAR =? "
							;
//					System.out.println("after sqls-------------->");
					ps = connection.prepareStatement(sqls);
					ps.setInt(1, emp_id);					
					ps.setString(2, fin_year);
					ResultSet rs2 = ps.executeQuery();

					while (rs2.next()) {

						from_year = rs2.getString("FROM_YEAR");
						from_month = rs2.getString("FROM_MONTH");
						to_year = rs2.getString("TO_YEAR");
						to_month = rs2.getString("TO_MONTH");
						frm_to_year = from_month + "/" + from_year + " to "
								+ to_month + "/" + to_year;
					}

					HSSFRow rowsh = sheet.createRow((short) rowlen);

					sheet.addMergedRegion(new Region(rowlen, (short) 0, rowlen,
							(short) ((short) 16)));

					HSSFCell cell = rowsh.createCell((short) 0);
					cell.setCellValue("Details of Salary Income For The Period From  : "
							+ frm_to_year + " .");
					cell.setCellStyle(style4);

					rowlen = rowlen + 1;

					HSSFRow rowheads = sheet.createRow((short) rowlen);
					sheet.addMergedRegion(new Region(rowlen, (short) 0, rowlen,
							(short) ((short) 16)));
					cell = rowheads.createCell((short) 0);
					cell.setCellValue("Name & Designation :" + " " + emp_desig);
					cell.setCellStyle(style);

					rowlen = rowlen + 1;

					HSSFRow rowhe = sheet.createRow((short) rowlen);
					sheet.addMergedRegion(new Region(rowlen, (short) 0, rowlen,
							(short) ((short) 16)));
					HSSFCell cell1 = null;
					cell1 = rowhe.createCell((short) 0);
					cell1.setCellValue("E.Code No :" + " " + empid);
					cell1.setCellStyle(style);
					rowlen = rowlen + 2;

					sql = "SELECT C.PAY_ELEMENT_ID AS PAY_ELEMENT_ID , "
							+ "  PAY_ELEMENT_TYPE_ID, "
							+ "  PAY_ELEMENT_SHORT_NAME, "
							+ "  PAYTOTAL "
							+ "FROM "
							+ "  (SELECT DISTINCT A.PAY_ELEMENT_ID, "
							+ "    SLNO, "
							+ "    B.PAY_ELEMENT_TYPE_ID, "
							+ "    B.PAY_ELEMENT_SHORT_NAME AS PAY_ELEMENT_SHORT_NAME "
							+ "  FROM HRM_PAY_EMP_IT_STMT_TRN A, "
							+ "    HRM_PAY_ELEMENTS_MST B "
							+ "  WHERE A.SLNO        =" + slno + " "
							+ "  AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID "
							+ "  )c " + "JOIN "
							+ "  (SELECT SUM(PAY_ELEMENT_VALUE) AS PAYTOTAL, "
							+ "    PAY_ELEMENT_ID, " + "    SLNO "
							+ "  FROM HRM_PAY_EMP_IT_STMT_TRN "
							+ "  GROUP BY PAY_ELEMENT_ID, " + "    SLNO "
							+ "  )D " + "ON C.SLNO           =D.SLNO "
							+ "AND C.PAY_ELEMENT_ID=D.PAY_ELEMENT_ID "
							+ "ORDER BY PAY_ELEMENT_TYPE_ID DESC, "
							+ "  C.PAY_ELEMENT_ID";
//					System.out.println("SQL 1 : " + sql);

					Statement st = connection.createStatement();
					// System.out.println("query :"+sql2);
					ResultSet result6 = null;
					result6 = st.executeQuery(sql);
					while (result6.next()) {
						pay_ele_id[i] = result6.getString("PAY_ELEMENT_ID");
						pay_ele_type_id[i] = result6
								.getString("PAY_ELEMENT_TYPE_ID");
						pay_el_name[i] = result6
								.getString("PAY_ELEMENT_SHORT_NAME");
						pay_tot[i] = result6.getString("PAYTOTAL");

						i++;
					}
					result6.close();
					st.close();
					int earncount = 0;
					int dedcount = 0;
					int k = 0;
					HSSFRow rowhead = sheet.createRow((short) rowlen);
					// rowhead.createCell((short)k).setCellValue("MONTHS");
					HSSFCell cell2 = rowhead.createCell((short) k);
					cell2.setCellValue("MONTHS");
					cell2.setCellStyle(style1);
					k = k + 1;
					rowlen = rowlen + 1;

					for (int j = 0; j < i; j++) {
						if (pay_ele_type_id[j].equalsIgnoreCase("E")) {

							// rowhead.createCell((short)
							// k).setCellValue(pay_el_name[j]);

							HSSFCell cell3 = rowhead.createCell((short) k);
							cell3.setCellValue(pay_el_name[j]);
							cell3.setCellStyle(style1);
							k++;
							earncount++;

						}

					}

//					System.out.println(" K K : " + k);
					// rowhead.createCell((short) k).setCellValue("TOTAL");
					HSSFCell cell4 = rowhead.createCell((short) k);
					cell4.setCellValue("TOTAL");
					cell4.setCellStyle(style1);
					k++;
					for (int j = 0; j < i; j++) {
						if (pay_ele_type_id[j].equalsIgnoreCase("D")) {

							// rowhead.createCell((short) ((short)
							// k)).setCellValue(pay_el_name[j]);
							HSSFCell cell5 = rowhead.createCell((short) k);
							cell5.setCellValue(pay_el_name[j]);
							cell5.setCellStyle(style1);
							dedcount++;
							k++;
						}

					}

//					System.out.println(" NEXT 1");
					sql = "SELECT DISTINCT SALARY_YEAR AS SALARY_YEAR, "
							+ "  SALARY_MONTH "
							+ "FROM HRM_PAY_EMP_IT_STMT_TRN "
							+ "WHERE slno=" + slno + "  "
							+ "ORDER BY salary_year, " + "  salary_month";
					st = connection.createStatement();
					ResultSet rs = st.executeQuery(sql);

					while (rs.next()) {

						HSSFRow rowhead1 = sheet.createRow((short) rowlen);
						rowlen = rowlen + 1;

						int syear = rs.getInt("SALARY_YEAR");
						int smonth = rs.getInt("SALARY_MONTH");
						String str = monthNames[smonth - 1];

						int q = 0;
						// rowhead1.createCell((short)
						// q).setCellValue(str+" -"+syear);

						HSSFCell cell6 = rowhead1.createCell((short) q);
						cell6.setCellValue(str + " -" + syear);
						cell6.setCellStyle(style2);
//						System.out.println(" NEXT 2");

						q = q + 1;
						int p = 0;
						int total = 0;
						String sql1 = "SELECT A.PAY_ELEMENT_ID AS PAY_ID , "
								+ "  SALARY_YEAR, " + "  SALARY_MONTH, "
								+ "  PAY_ELEMENT_VALUE, "
								+ "  B.PAY_ELEMENT_TYPE_ID "
								+ "FROM HRM_PAY_EMP_IT_STMT_TRN A, "
								+ "  HRM_PAY_ELEMENTS_MST B "
								+ "WHERE A.SLNO        =" + slno + "  "
								+ "AND a.salary_year   =" + syear + " "
								+ "AND a.salary_month  =" + smonth + " "
								+ "AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID "
								+ "ORDER BY SALARY_YEAR, " + "  SALARY_MONTH, "
								+ "  PAY_ELEMENT_TYPE_ID DESC, "
								+ "  A.PAY_ELEMENT_ID";
						ps = connection.prepareStatement(sql1);
						ResultSet rss = ps.executeQuery();
						while (rss.next()) {
//							System.out.println(" NEXT 3");
							if (rss.getString("PAY_ELEMENT_TYPE_ID")
									.equalsIgnoreCase("E")) {
								int val = rss.getInt("PAY_ELEMENT_VALUE");
								total = total + val;
								// rowhead1.createCell((short)
								// q).setCellValue(val);
								HSSFCell cell7 = rowhead1.createCell((short) q);
								cell7.setCellValue(val);
								cell7.setCellStyle(style2);
							} else {
								if (p == 0) {
									// rowhead1.createCell((short)
									// q).setCellValue(total);
									HSSFCell cell8 = rowhead1
											.createCell((short) q);
									cell8.setCellValue(total);
									cell8.setCellStyle(style2);
									p++;
									q++;
								}
								// rowhead1.createCell((short)
								// q).setCellValue(rss.getInt("PAY_ELEMENT_VALUE"));
								HSSFCell cell9 = rowhead1.createCell((short) q);
								cell9.setCellValue(rss
										.getInt("PAY_ELEMENT_VALUE"));
								cell9.setCellStyle(style2);
							}
							q++;

						}

						rss.close();
						ps.close();

					}
					int final_addl=0;
					int pay_val=0;
					rs.close();
					st.close();
					try {
//						System.out.println(" NEXT 4");
						sql = "SELECT ADDITIONAL_PAY_DESC , "
								+ "  ADDITIONAL_PAY_VALUE, "
								+ "  ADDITIONAL_PAY_TYPE "
								+ "FROM HRM_PAY_EMP_IT_STMT_ADDL "
								+ "WHERE SLNO=" + slno + "";
//						System.out.println(sql);
						PreparedStatement pss = connection
								.prepareStatement(sql);
						rs2.close();
						rs2 = pss.executeQuery();
//						System.out.println(" NEXT 5");
						
						while (rs2.next()) {

							HSSFRow rowhead2 = sheet.createRow((short) rowlen);
							rowlen = rowlen + 1;

							int r = 0;
							String add_desc = rs2
									.getString("ADDITIONAL_PAY_DESC");
							 pay_val = rs2
									.getInt("ADDITIONAL_PAY_VALUE");

							sheet.addMergedRegion(new Region(rowlen - 1,
									(short) 0, rowlen - 1,
									(short) ((short) earncount)));
							HSSFCell cell9 = rowhead2.createCell((short) r);
							cell9.setCellValue(add_desc);
							cell9.setCellStyle(style5);

							cell9 = rowhead2
									.createCell((short) ((short) earncount + 1));
							cell9.setCellValue(pay_val);
							cell9.setCellStyle(style2);
							final_addl=final_addl+pay_val;
						}
						
						
					//	System.out.println("filal_addl--->"+final_addl);
						rs2.close();
						pss.close();

					} catch (Exception e) {
						System.out.println("ERROR IN ADD");
					}

//					System.out.println(" NEXT 6");

					HSSFRow rowhead3 = sheet.createRow((short) rowlen);
					rowlen = rowlen + 1;
					int total = 0;
					int e = 0;
					k = 0;
					// rowhead3.createCell((short) k).setCellValue("TOTAL");

					HSSFCell cell9 = rowhead3.createCell((short) k);
					cell9.setCellValue("TOTAL");
					cell9.setCellStyle(style3);

					k++;
					for (int j = 0; j < i; j++) {
						if (pay_ele_type_id[j].equalsIgnoreCase("E")) {

							// rowhead3.createCell((short)
							// k).setCellValue(pay_tot[j]);

							cell9 = rowhead3.createCell((short) k);
							cell9.setCellValue(pay_tot[j]);
							cell9.setCellStyle(style3);
							total = total + Integer.parseInt(pay_tot[j]);

							k++;

						}

					}

					// rowhead3.createCell((short) k).setCellValue(total);

					cell9 = rowhead3.createCell((short) k);
					cell9.setCellValue(total+final_addl);
					cell9.setCellStyle(style3);
					k++;
					for (int j = 0; j < i; j++) {

						if (pay_ele_type_id[j].equalsIgnoreCase("D")) {

							// rowhead3.createCell((short) ((short)
							// k)).setCellValue(pay_tot[j]);

							cell9 = rowhead3.createCell((short) k);
							cell9.setCellValue(pay_tot[j]);
							cell9.setCellStyle(style3);
							total = total + Integer.parseInt(pay_tot[j]);

							k++;
						}

					}

				}
				result.close();
				ps.close();
				rowlen = rowlen + 5;

			

			

			if (output.equalsIgnoreCase("EXCEL")) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Consolidated Pay Drawn Report.xls\"");

				ServletOutputStream fileOut = response.getOutputStream();
				hwb.write(fileOut);
				fileOut.close();
			}

		} catch (Exception es) {
			System.out.println(es);
		}

	}

}
