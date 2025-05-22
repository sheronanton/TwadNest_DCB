package Servlets.PMS.PMS1.DCB.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class DCBCash_Receipt_Cancel extends HttpServlet {

	private String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strCommand = "";
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null, cs1 = null;
		PreparedStatement ps = null;
		String xml = "";
		HttpSession session = request.getSession(false);
		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
		//			+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		try {

			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		if (strCommand.equalsIgnoreCase("Cancel")) {
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			xml = "<response><command>Add</command>";
			Calendar c;
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtReceipt_No = 0;
			Date txtCrea_date = null;
			String update_user = (String) session.getAttribute("UserId");
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbOffice_code " + cmbOffice_code);

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			System.out.println("b4 getting month and year");
			try {
				txtCash_year = Integer.parseInt(sd[2]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_year " + txtCash_year);

			try {
				txtCash_Month_hid = Integer.parseInt(sd[1]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

			/*
			 * try{txtCash_year=Integer.parseInt(request.getParameter("txtCash_year"
			 * ));} catch(Exception e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_year "+txtCash_year);
			 * 
			 * try{txtCash_Month_hid=Integer.parseInt(request.getParameter(
			 * "txtCash_Month_hid"));} catch(Exception
			 * e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
			 */

			try {
				txtReceipt_No = Integer.parseInt(request
						.getParameter("txtReceipt_No"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtReceipt_No " + txtReceipt_No);

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			/** Get Receipt Creation Date */
			String Receipt_Creation_Date = request.getParameter("txtCrea_date");

			/**
			 * Call Com_CashBook Servlet for Calculating Cash Book Month and
			 * Year
			 */
			Com_CashBook1 cb = new Com_CashBook1();

			/** Assign Cashbook Year and Month to year_month Variable */
			String year_month = cb.cb_date(Receipt_Creation_Date).toString();

			/** Split Cash Book Year and Month */
			String[] ym = year_month.split("/");

			/** Assign Year and Month */
			txtCash_year = Integer.parseInt(ym[0]);
			txtCash_Month_hid = Integer.parseInt(ym[1]);

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			/* Old Procedure for finding Cash Book Month and Year */
			/*
			 * 
			 * String[] sp=request.getParameter("txtCrea_date").split("/");
			 * System.out.println(sp[0]+" "+sp[1]+" "+sp[2]); int
			 * check_year=Integer.parseInt(sp[2]); // to check in while loop int
			 * check_day=Integer.parseInt(sp[0]); // to check in while loop
			 * System.out.println(Integer.parseInt(sp[2]));
			 * System.out.println("here"+check_year);
			 * 
			 * String check_date=request.getParameter("txtCrea_date");
			 * sp=request.getParameter("txtCrea_date").split("/");
			 * check_date=sp[2]+"/"+sp[1]+"/"+sp[0];
			 * 
			 * System.out.println(check_date); // to check in while loop with
			 * d/b date it converted to yyyy/mm/dd form try { String
			 * sql1="select FINANCIAL_YEAR," +
			 * "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ,"
			 * + "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
			 * "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  "
			 * + "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";
			 * 
			 * // date is taken as string from database in above format for
			 * checking with receipt date variable ( check_date is string type)
			 * // checking of dates performed in form of String checking
			 * ps=con.prepareStatement(sql1); rs=ps.executeQuery(); int
			 * Begin_yr,End_yr; while(rs.next()) { String[]
			 * yr=rs.getString("FINANCIAL_YEAR").split("-");
			 * Begin_yr=Integer.parseInt(yr[0]); End_yr=Integer.parseInt(yr[1]);
			 * System.out.println("while"); System.out.println(Begin_yr+
			 * " "+End_yr);
			 * System.out.println(rs.getString("mar_beg")+" "+rs.getString
			 * ("mar_end"));
			 * 
			 * if(check_year==Begin_yr) // to check which financial year it
			 * belongs { if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12) {
			 * System.out.println("if 4");
			 * if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0)
			 * ) { txtCash_Month_hid=03;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"mar"+txtCash_Month_hid); } else
			 * if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 )
			 * ) { txtCash_Month_hid=04;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"apr"+txtCash_Month_hid); } else
			 * if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH")) {
			 * txtCash_Month_hid=txtCash_Month_hid+1; if(txtCash_Month_hid>12) {
			 * txtCash_Month_hid=1; txtCash_year=txtCash_year+1;
			 * System.out.println("hello"+txtCash_year); }
			 * System.out.println(rs.
			 * getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid); } else
			 * if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH")) {
			 * //txtCash_Month_hid=txtCash_Month_hid;
			 * System.out.println(rs.getInt
			 * ("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid); } }
			 * 
			 * } else if(check_year==End_yr) { if(txtCash_Month_hid>=1 &&
			 * txtCash_Month_hid<=3) {
			 * txtCash_year=End_yr;System.out.println("if 3");
			 * if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0)
			 * ) { txtCash_Month_hid=03;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"mar"+txtCash_Month_hid); } else
			 * if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 )
			 * ) { txtCash_Month_hid=04;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"apr"+txtCash_Month_hid); } else
			 * if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH")) {
			 * txtCash_Month_hid=txtCash_Month_hid+1; if(txtCash_Month_hid>12)
			 * // No chance for this condition { txtCash_Month_hid=1;
			 * txtCash_year=txtCash_year+1;
			 * System.out.println("hello"+txtCash_year); }
			 * System.out.println(rs.
			 * getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid); } else
			 * if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH")) {
			 * //txtCash_Month_hid=txtCash_Month_hid;
			 * System.out.println(rs.getInt
			 * ("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid); } } } }
			 * ps.close(); rs.close(); } catch(Exception e) {
			 * sendMessage(response
			 * ,"The Bank Receipt Modification Failed finding Bank book month"
			 * ,"ok"); System.out.println("exception"+e); }
			 */

			String sql_del = "update FAS_RECEIPT_MASTER set RECEIPT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and "
					+ " ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
			try {
				con.clearWarnings();
				con.setAutoCommit(false);
				ps = con.prepareStatement(sql_del);
				ps.setString(1, update_user);
				ps.setTimestamp(2, ts);
				ps.setInt(3, cmbAcc_UnitCode);
				ps.setInt(4, cmbOffice_code);
				ps.setInt(5, txtCash_year);
				ps.setInt(6, txtCash_Month_hid);
				ps.setInt(7, txtReceipt_No);
				ps.executeUpdate();
				String txtReferNO_edit = "", txtRemak_edit = "", txtRefdate = ""; // for
																					// cross
																					// reference
				Date txtReferDate_edit = null;
				String radAuth_MC = "";
				int txtAuth_By = 0;
				/*
				 * try{txtAuth_By=Integer.parseInt(request.getParameter("txtAuth_By"
				 * ));} catch(Exception e){System.out.println("exception"+e );}
				 */
				System.out.println("txtAuth_By  " + txtAuth_By);
				System.out.println("txtReferNO_edit  " + txtReferNO_edit);
				System.out.println("txtRemak_edit  " + txtRemak_edit);
				System.out.println("txtReferDate_edit " + txtReferDate_edit);

				cs1 = con
						.prepareCall("{call FAS_CROSS_REFERENCE_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs1.setInt(1, cmbAcc_UnitCode);
				cs1.setInt(2, txtCash_year);
				cs1.setInt(3, txtCash_Month_hid);
				cs1.setInt(4, txtReceipt_No);
				cs1.setInt(5, cmbOffice_code);
				cs1.setDate(6, txtCrea_date);
				cs1.setString(7, "CR");
				cs1.setString(8, txtReferNO_edit);
				cs1.setDate(9, txtReferDate_edit);
				cs1.setString(10, txtRemak_edit);
				cs1.setInt(11, txtAuth_By);
				cs1.setString(12, "insert");
				cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
				cs1.setString(14, update_user);
				cs1.setTimestamp(15, ts);
				cs1.setString(16, radAuth_MC);
				cs1.execute(); // insertion into cross reference table
				int errcode = cs1.getInt(13);
				System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
				System.out.println("cmbOffice_code " + cmbOffice_code);
				System.out.println("txtCrea_date " + txtCrea_date);
				System.out.println("txtCash_year " + txtCash_year);
				System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
				System.out.println("SQLCODE:::" + errcode);
				if (errcode != 0) {
					con.rollback();
					sendMessage(response,
							"The Bank Receipt Cancellation Failed ", "ok");
					xml = xml + "<flag>failure</flag>";
				}
				con.commit();
				sendMessage(response, "The Cash Receipt Number '"
						+ txtReceipt_No + "' has been cancelled Successfully ",
						"ok");
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException sqle) {
					System.out.println("Excep" + sqle);
				}
				sendMessage(response, "The Cash Receipt Cancellation Failed ",
						"ok");
				System.out.println("Exception occur due to " + e);
			} finally {
				System.out.println("done");
				try {
					con.setAutoCommit(true);
				} catch (SQLException sqle) {
					System.out.println("Excep" + sqle);
				}
			}

		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
		// CallableStatement cs=null,cs1=null;
		PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;

		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
//			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//					+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String strCommand = "";

		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign.. command..." + strCommand);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
		Date txtCrea_date = null;
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (NumberFormatException e) {
			System.out.println("exception" + e);
		}
		System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
		} catch (NumberFormatException e) {
			System.out.println("exception" + e);
		}
		System.out.println("cmbOffice_code " + cmbOffice_code);

		if (strCommand.equalsIgnoreCase("load_Receipt_No")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			Calendar c;
			String xml = "";
			xml = "<response><command>load_Receipt_No</command>";

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			try {
				// ps=con.prepareStatement("select RECEIPT_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C'");

				ps = con
						.prepareStatement("select i.RECEIPT_NO from FAS_RECEIPT_MASTER i,FAS_CROSS_REFERENCE c where "
								+ " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.RECEIPT_DATE=? and i.RECEIPT_STATUS!='C' and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' "
								+ " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID "
								+ " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.RECEIPT_NO=c.VOUCHER_NO "
								+ " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='DCC'");
 
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				rs = ps.executeQuery();

				int count = 0;
				while (rs.next()) {

					xml = xml + "<Rec_No>" + rs.getInt("RECEIPT_NO")
							+ "</Rec_No>";
					count++;
				}
				if (count == 0)
					xml = xml + "<flag>failure</flag>";
				else
					xml = xml + "<flag>success</flag>";
				System.out.println("count  " + count);
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load head code." + e);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);

		}

		else if (strCommand.equalsIgnoreCase("load_Receipt_Details")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			Calendar c;
			String xml = "";
			xml = "<response><command>load_Receipt_Details</command>";
			int txtReceipt_No = 0;
			// Date txtCrea_date;

			try {
				txtReceipt_No = Integer.parseInt(request
						.getParameter("txtReceipt_No"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtReceipt_No " + txtReceipt_No);

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			try {
				ps = con
						.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH,REF_NO,to_char(REF_DATE,'DD/MM/YYYY')as ref_date,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,TOTAL_TRN_RECORDS,RECEIVED_FROM,REMARKS,SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE  from  FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and  RECEIPT_DATE=? and RECEIPT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				ps.setInt(4, txtReceipt_No);
				rs = ps.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>success</flag>";
					xml = xml + "<Ref_No>" + rs.getString("REF_NO")
							+ "</Ref_No><Ref_Date>" + rs.getString("ref_date")
							+ "</Ref_Date><Total_amt>"
							+ rs.getString("TOTAL_AMOUNT")
							+ "</Total_amt><No_TRN_Rec>"
							+ rs.getInt("TOTAL_TRN_RECORDS")
							+ "</No_TRN_Rec><Rec_From>"
							+ rs.getString("RECEIVED_FROM")
							+ "</Rec_From><Remak>" + rs.getString("REMARKS")
							+ "</Remak><Mas_SL_type>"
							+ rs.getInt("SUB_LEDGER_TYPE_CODE")
							+ "</Mas_SL_type><Mas_SL_code>"
							+ rs.getInt("SUB_LEDGER_CODE") + "</Mas_SL_code>";
				}
				System.out.println("total..." + rs.getDouble("TOTAL_AMOUNT"));

				if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
				//	SL_TYPE_CODE_NAME_GENERAL obj_gen = new SL_TYPE_CODE_NAME_GENERAL();
					//ResultSet rs_get = obj_gen.getResult_General(
							//cmbAcc_UnitCode, cmbOffice_code, rs
								//	.getInt("SUB_LEDGER_TYPE_CODE"), rs
								//	.getInt("SUB_LEDGER_CODE"), 0);
					 

					int ledgercode_ben = rs.getInt("SUB_LEDGER_CODE");

					PreparedStatement ps5 = con
							.prepareStatement("SELECT BENEFICIARY_TYPE_ID FROM PMS_DCB_MST_BENEFICIARY	WHERE status='L' and BENEFICIARY_SNO=?	AND OFFICE_ID=?");
					ps5.setInt(1, rs.getInt("SUB_LEDGER_CODE"));
					ps5.setInt(2, cmbOffice_code);
					ResultSet rs5 = ps5.executeQuery();
					rs5.next();
					int benificiaryTypeId = rs5.getInt("BENEFICIARY_TYPE_ID");

					xml = xml + "<beneficiaryid>" + benificiaryTypeId
							+ "</beneficiaryid>";
					ps4 = con
							.prepareStatement("SELECT BENEFICIARY_SNO,BENEFICIARY_NAME  FROM PMS_DCB_MST_BENEFICIARY WHERE status='L' and BENEFICIARY_TYPE_ID=? and OFFICE_ID=?");
					ps4.setInt(1, benificiaryTypeId);
					ps4.setInt(2, cmbOffice_code);
					rs4 = ps4.executeQuery();

					while (rs4.next()) {
						xml = xml + "<cid>" + rs4.getInt("BENEFICIARY_SNO")
								+ "</cid><cname>"
								+ rs4.getString("BENEFICIARY_NAME")
								+ "</cname>";
					}
					ps5.close();
					rs5.close();
					ps4.close();
					rs4.close();

				}

				System.out.println("in b/w here");

				ps2 = con
						.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE "
								+ ",RECEIVED_FROM ,trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS  from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and "
								+ "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?");
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setString(3, rs.getString("CASHBOOK_YEAR"));
				ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
				ps2.setInt(5, txtReceipt_No);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					xml = xml + "<AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE")
							+ "</AHcode>";
					ps3 = con
							.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
					ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
					rs3 = ps3.executeQuery();
					if (rs3.next())
						xml = xml + "<AHdesc>"
								+ rs3.getString("ACCOUNT_HEAD_DESC")
								+ "</AHdesc>";
					ps3.close();
					rs3.close();
					xml = xml + "<CR_DR_ind>"
							+ rs2.getString("CR_DR_INDICATOR")
							+ "</CR_DR_ind><SL_Type>"
							+ rs2.getInt("SUB_LEDGER_TYPE_CODE") + "</SL_Type>";

					if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
						System.out.println("take SL DESC");
						ps3 = con
								.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
						ps3.setInt(1, rs2.getInt("SUB_LEDGER_TYPE_CODE"));
						rs3 = ps3.executeQuery();
						if (rs3.next())
							xml = xml + "<SL_Desc>"
									+ rs3.getString("SUB_LEDGER_TYPE_DESC")
									+ "</SL_Desc>";
					} else {
						xml = xml + "<SL_Desc>" + null + "</SL_Desc>"; // it
																		// also
																		// return
																		// null
																		// value
						System.out.println("else part  23");
					}

					rs3.close();
					ps3.close();

					xml = xml + "<SL_Code>" + rs2.getInt("SUB_LEDGER_CODE")
							+ "</SL_Code>";

					if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {

						System.out.println("SUB CODE "
								+ rs2.getInt("SUB_LEDGER_CODE"));

						PreparedStatement ps6 = con
								.prepareStatement("SELECT SCH_SNO FROM PMS_MST_PROJECTS_VIEW WHERE PROJECT_ID=? and OFFICE_ID=? and STATUS='L'");
						ps6.setInt(1, rs2.getInt("SUB_LEDGER_CODE"));
						ps6.setInt(2, cmbOffice_code);
						int schemeNo = 0;
						ResultSet rs6 = ps6.executeQuery();
						if (rs6.next()) {
							schemeNo = rs6.getInt("SCH_SNO");
						}
						System.out.println("Scheme no" + schemeNo);
						xml = xml + "<schemeno>" + schemeNo + "</schemeno>";
						ps4 = con
								.prepareStatement("SELECT SCH_NAME FROM PMS_SCH_MASTER WHERE SCH_SNO =? and OFFICE_ID=?");
						ps4.setInt(1, schemeNo);
						ps4.setInt(2, cmbOffice_code);

						rs4 = ps4.executeQuery();
						rs4.next();
						xml = xml + "<desc_type>" + rs4.getString("SCH_NAME")
								+ "</desc_type>";

						 

					} else {
						xml = xml + "<desc_type>" + null + "</desc_type>";
					}

					xml = xml + "<sub_rec_frm>"
							+ rs2.getString("RECEIVED_FROM")
							+ "</sub_rec_frm><sub_amount>"
							+ rs2.getString("AMOUNT")
							+ "</sub_amount><sub_part>"
							+ rs2.getString("PARTICULARS") + "</sub_part>";
				}

			} catch (Exception e) {
				System.out.println("catch..HERE.in failure to retrieve." + e);
				xml = "<response><command>load_Receipt_Details</command><flag>failure</flag>";
			}
			xml = xml + "</response>";
		 
			out.println(xml);
		}
	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			System.out.println("Excep" + e);
		}
	}
}
