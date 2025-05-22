	package Servlets.PMS.PMS1.DCB.servlets;
/* 
 *  
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 20/09/2011
   
  */
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;  
import java.util.ResourceBundle;
import java.lang.Boolean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

 
import java.util.Hashtable;
 
import java.io.*;
import java.util.Date;
import jxl.*;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;

public class Controller {
	public Statement stmt = null;
	public static ResultSet rs = null;  
	public Connection con;
	public static ResultSet rs2 = null;    
	//public static String new_cond=" INACTIVE is null and STATUS='L' and ";
	public static String new_cond=" STATUS='L' and ";  
	public static String meter_status="  METER_STATUS='L' and ";
	public static String meter_status2=" PMS_DCB_MST_BENEFICIARY_METRE.METER_STATUS='L' and  ";
	public static String meter_status3=" PMS_DCB_MST_BENEFICIARY_METRE.METER_STATUS='L' and (1=1)  ";  
	public static String meter_ben_same=" METRE_SNO  in (select METRE_SNO  from PMS_DCB_MST_BENEFICIARY_METRE  where  meter_status='L'  ";
	public static String set_office="0";
	public static String meter_not_for_ben="";
	private String receipt_trouble="";
	public static String recp_month="";
	public static String recp_year="";
	public static String recp_off="";
	  
	synchronized public Connection con() throws Exception {
		ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		String ConnectionString = "";
		String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
		String strdsn = rs1.getString("Config.DSN");
		String strhostname = rs1.getString("Config.HOST_NAME");
		String strportno = rs1.getString("Config.PORT_NUMBER");
		String strsid = rs1.getString("Config.SID");
		String strdbusername = rs1.getString("Config.USER_NAME");
		String strdbpassword = rs1.getString("Config.PASSWORD");
//		ConnectionString = strdsn.trim()+"@"+strhostname.trim()+":"+strportno.trim()+":"+strsid.trim();
//		ConnectionPool c_p=new ConnectionPool(strDriver,ConnectionString,strdbusername,strdbpassword,0,500,true);
		
		 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			ConnectionPool c_p=new ConnectionPool(strDriver,ConnectionString,strdbusername,strdbpassword,0,500,true);
		
		con=c_p.getConnection();		
		return con;
	}
	synchronized public String officeLevelId(String Office_id)
	 {
		 String OFFICE_LEVEL_ID="";
		 Connection con_loc = null;
		 Controller obj_loc = new Controller();   
		 try 
		 {
			con_loc = obj_loc.con();
			OFFICE_LEVEL_ID=obj_loc.getValue("com_mst_all_offices_view", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
			con_loc.close();
			con_loc = null;
		 
		 } catch (Exception e) 
		 { 
				e.printStackTrace();
		 }
		 return OFFICE_LEVEL_ID;
		 
	 }
	synchronized public void createStatement(Connection con)
			throws SQLException {
		stmt = con.createStatement();
		   
	}
	public String month_List(int month)
	{
		String res="";
		
					switch(month)
					{
					case 1:
							res="4,5,6,7,8,9,10,11,12,1";
							break;
					case 2:
							res="4,5,6,7,8,9,10,11,12,1,2";
							break;
					case 3:
							res="4,5,6,7,8,9,10,11,12,1,2,3";
							break;
					case 4:
							res="4";
							break;
					case 5:
							res="4,5";
							break;
					case 6:
							res="4,5,6";
							break;
					case 7:
							res="4,5,6,7";
							break;
					case 8:
							res="4,5,6,7,8";
							break;
					case 9:
							res="4,5,6,7,8,9";
							break;
					case 10:
							res="4,5,6,7,8,9,10";
							break;
					case 11:
						res="4,5,6,7,8,9,10,11";
						break;
					case 12:
						res="4,5,6,7,8,9,10,11,12";  
						break;
					}
					 
					
		return  res;
	}
	public String delRecord(String table,String cond,Connection prcon) throws SQLException 
	{
		  String res="";  
		  res="<response>";		 
		  PreparedStatement pn_=prcon.prepareStatement("delete from "+table + " " + cond );
		  int row =pn_.executeUpdate();  
		  res+="<rows>"+row+"</rows></response>";
		  return res; 
	}
	synchronized public int setUpd(String qry) throws SQLException 
	{
		System.out.println("qry"+qry);
		int row = 0;
		try {
			row = stmt.executeUpdate(qry);
		} catch (Exception e) {
			System.out.println("PMS-->DCB-->Controller --> setUpd ()-->" + e +"--" +qry);
			
		}
		return row;  
	}    
	public String valueSum(String table,String field,String alias,String cond,String grouphby)
	{
		double value=0.0f;
		Connection con_loc = null;  
		Controller obj_loc = new Controller();
		try {
			con_loc = obj_loc.con();
			String qry="select sum("+field+") as "+alias+" from "+table+" where "+cond;
			if(!grouphby.equals("1"))
				 qry="select sum("+field+") as "+alias+" from "+table+" where "+cond+" group by "+grouphby;
			
			System.out.println(qry);
			//if(field.contains("count(")) {
				//qry="SELECT SUM("+name+") as "+name+" FROM (SELECT "+qrycond.replace("sum(", "").replace("))", ")")+" as "+name+ " from "+tablename+ " "+userquery+") as valu";
			//}
			PreparedStatement ps = con_loc.prepareStatement(qry );
			ResultSet rs_temp=null;
			
			rs_temp=ps.executeQuery();
			if (rs_temp.next()) 
			{
				value=rs_temp.getDouble(1);		   		
			}
			ps=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		con_loc=null; 
		obj_loc=null;
		return new DecimalFormat("0.00").format(value);	
	}
	public int getMax(String table, String field, String userquery)
			throws SQLException 
	{
		String value = "0";
		String qry = "select max(" + field + ") from " + table + " "
				+ userquery;
		rs = stmt.executeQuery(qry);
		try {
			if (rs.next()) {
				value = rs.getString(1);
				if (value == "" || value == null || value.equals(""))
					value = "0";
			}
		} catch (Exception e) {
			value = "0";
			System.out.println("PMS-->DCB-->Controller --> getMax ()-->" + e);
		}
		return Integer.parseInt(value) + 1;
	}
	  public String setValue(String input, HttpServletRequest request) {
		input = request.getParameter(input);
		System.out.println("input"+input);
		if (input == null || input.equals(""))
			input = "0";
			input = input.replace ('<','0');
			input = input.replace ('>','0');
			input = input.replace ('"','0');
			input = input.replace ('\'','0');
			input = input.replace ('%','0');
			input = input.replace (';','0');
			input = input.replace ('(','0');
			input = input.replace (')','0');
			input = input.replace ('&','0');
			input = input.replace ('+','0');
			input = input.replace ('=','0');  
	 return input;
	 
	}
	  public String getValue(String tablename, String name,String userquery) throws Exception {
		String fld = "";
		fld = name;
		String qry = "select " + fld + " from " + tablename + " " + userquery;
		
		String res_value = "0";      
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();  
		ResultSet rs_cont = null;
		PreparedStatement ps_loca = con_loc.prepareStatement(qry);
		rs_cont = ps_loca.executeQuery();
		try   
		{
				if (rs_cont.next()) {
					if(fld.trim().toLowerCase().startsWith("sum(") ||fld.trim().toLowerCase().startsWith("count("))
						res_value = rs_cont.getString(1);
					else
						res_value = rs_cont.getString(fld);
				}
				else
				{  
					res_value = "0";
				}
				if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
					res_value = "0";
		}catch (Exception e) {
			  res_value = "0"; 
			  System.out.println("PMS-->DCB-->Controller --> getValue ()-->" + e  );
		}
		rs_cont.close();
		rs_cont = null;
		con_loc.close();
		con_loc = null;
		ps_loca.close();
		obj_loc =  null;
		System.out.println("Query:"+qry);
		return res_value;
	}
	  // NEWLY created for temp Office id
	  public String getValu(String tablename, String name,String userquery,String col) throws Exception {
		String fld = "";String fld1 = "";
		fld = name;
		fld1= col;
		String qry = "select " + fld + " from " + tablename + " " + userquery;
  System.out.println("qry"+qry);
		String res_value = "0";      
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();  
		ResultSet rs_cont = null;
		PreparedStatement ps_loca = con_loc.prepareStatement(qry);
		rs_cont = ps_loca.executeQuery();
		try   
		{
				if (rs_cont.next()) {
					res_value = rs_cont.getString(fld1);
				}
				else
				{  
					res_value = "0";
				}
				if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
					res_value = "0";
		}catch (Exception e) {
			  res_value = "0"; 
			  System.out.println("PMS-->DCB-->Controller --> getValue ()-->" + e  );
		}
		rs_cont.close();
		rs_cont = null;
		con_loc.close();
		con_loc = null;
		ps_loca.close();
		obj_loc =  null;
		return res_value;
	}
	  
	//New For water charges freeze 
	  
	  
	  public String getData(String month, String year,String off,String con1) throws Exception {
	  		String DAT = "DAT";
		//	String qry = "select (sum(a."+con1+") - sum(b."+con2+")) as DAT   from pms_dcb_trn_bill_dmd a , pms_dcb_trn_bill_dmd_sch b  where a.bill_month="+month+" and a.bill_year="+year+" and a.office_id="+off+" and a.bill_sno=b.bill_sno";  
		//	String qry = "select (sum(a."+con1+") - sumse( b.k )) as DAT from (select bill_sno,"+con1+",beneficiary_sno from pms_dcb_trn_bill_dmd where bill_month="+month+" and bill_year="+year+" and beneficiary_sno in (select beneficiary_sno from pms_dcb_mst_beneficiary where status='L' and  office_id="+off+" ) )a left outer join ( select BILL_SNO , beneficiary_sno,  sum( cb_int_amt_wc-ob_int_amt_wc ) as k from pms_dcb_trn_bill_dmd_sch group by BILL_SNO, beneficiary_sno )b on a.bill_sno=b.bill_sno and b.beneficiary_sno=a.beneficiary_sno ";
	  	//	String qry = "select (sum(a."+con1+") - sum( b.k )) as DAT from (select bill_sno,"+con1+",beneficiary_sno from pms_dcb_trn_bill_dmd where bill_month="+month+" and bill_year="+year+" and beneficiary_sno in (select beneficiary_sno from pms_dcb_mst_beneficiary where status='L' and  office_id="+off+" ) )a left outer join (  SELECT BILL_SNO, beneficiary_sno ,k FROM ( SELECT BILL_SNO , beneficiary_sno, (cb_int_amt_wc-ob_int_amt_wc+COLN_INT_WC) AS k, ROW_NUMBER() OVER (PARTITION BY BILL_SNO,cb_int_amt_wc,ob_int_amt_wc ORDER BY BILL_SNO) num FROM pms_dcb_trn_bill_dmd_sch ) inn WHERE inn.num = 1 )b on a.bill_sno=b.bill_sno and b.beneficiary_sno=a.beneficiary_sno ";
	  		String qry = " SELECT (SUM(a."+con1+") - SUM( b."+con1+" )) AS DAT FROM (SELECT bill_sno, "+con1+", beneficiary_sno  FROM pms_dcb_trn_bill_dmd WHERE bill_month ="+month+" AND bill_year ="+year+" AND beneficiary_sno IN (SELECT beneficiary_sno FROM pms_dcb_mst_beneficiary  WHERE status ='L'   AND office_id="+off+"  ) )a LEFT OUTER JOIN (select beneficiary_sno,sum("+con1+") as "+con1+"  from PMS_DCB_TRN_CB_MONTHLY where FIN_YEAR="+year+" and MONTH="+month+" GROUP BY beneficiary_sno  )b ON b.beneficiary_sno=a.beneficiary_sno  ";
						
			System.out.println("GET DATA "+con1);
			System.out.println(qry);
			String res_value = "";
			Connection con_loc = null;  
			Controller obj_loc = new Controller();
			con_loc = obj_loc.con();
			ResultSet rs_cont = null;
			PreparedStatement ps = con_loc.prepareStatement(qry);
			rs_cont = ps.executeQuery();
			try   
			{
					if (rs_cont.next()) {
						res_value = rs_cont.getString(DAT);
					}
					else
					{
						res_value = "0";
					}
			 
			
					if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
						res_value = "0";
			}catch (Exception e) {  
				 res_value = "0";
				 System.out.println("PMS-->DCB-->Controller --> getValue ()-->" + e.getMessage() );
			}  
			 
			rs_cont.close();
			con_loc.close();
			ps.close();
			rs_cont = null;
			obj_loc = null;
			ps=null;
			return res_value;
		}
	  
	  
	  	  public String getData(String month, String year,String off,String con1,String con2) throws Exception {
	  		String DAT = "DAT";
		//	String qry = "select (sum(a."+con1+") - sum(b."+con2+")) as DAT   from pms_dcb_trn_bill_dmd a , pms_dcb_trn_bill_dmd_sch b  where a.bill_month="+month+" and a.bill_year="+year+" and a.office_id="+off+" and a.bill_sno=b.bill_sno";  
			String qry = "select (sum(a."+con1+") - sum( b."+con2+")) as DAT from (select bill_sno,"+con1+",beneficiary_sno from pms_dcb_trn_bill_dmd where bill_month="+month+" and bill_year="+year+" and beneficiary_sno in (select beneficiary_sno from pms_dcb_mst_beneficiary where status='L' and  office_id="+off+" ) )a left outer join ( select BILL_SNO , beneficiary_sno,  sum("+con2+") as "+con2+" from pms_dcb_trn_bill_dmd_sch group by BILL_SNO, beneficiary_sno )b on a.bill_sno=b.bill_sno and b.beneficiary_sno=a.beneficiary_sno ";
	
			System.out.println("GET DATA "+con1+"  "+ con2);
			System.out.println(qry);
			String res_value = "";
			Connection con_loc = null;  
			Controller obj_loc = new Controller();
			con_loc = obj_loc.con();
			ResultSet rs_cont = null;
			PreparedStatement ps = con_loc.prepareStatement(qry);
			rs_cont = ps.executeQuery();
			try   
			{
					if (rs_cont.next()) {
						res_value = rs_cont.getString(DAT);
					}
					else
					{
						res_value = "0";
					}
			 
			
					if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
						res_value = "0";
			}catch (Exception e) {  
				 res_value = "0";
				 System.out.println("PMS-->DCB-->Controller --> getValue ()-->" + e );
			}  
			 
			rs_cont.close();
			con_loc.close();
			ps.close();
			rs_cont = null;
			obj_loc = null;
			ps=null;
			return res_value;
		}
	  
	  
	  	
	  
	  
	  public String getValue(String tablename, String qrycond,String name,String userquery) throws Exception {
		String fld = "";  
		fld = qrycond+" as "+name;   
		String qry = "select " + fld + " from " + tablename + " " + userquery;
		if(qrycond.contains("sum(count(")) {
			qry="SELECT SUM("+name+") as "+name+" FROM (SELECT "+qrycond.replace("sum(", "").replace("))", ")")+" as "+name+ " from "+tablename+ " "+userquery+") as valu";
		}
		
	 System.out.println("GET DATA "+ qrycond +  "   "+ name+"   "+ userquery);
	 System.out.println(qry);
	 
		String res_value = "";
		Connection con_loc = null;  
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		ResultSet rs_cont = null;
		PreparedStatement ps = con_loc.prepareStatement(qry);
		rs_cont = ps.executeQuery();
		try   
		{
				if (rs_cont.next()) {
					res_value = rs_cont.getString(name);
				}
				else
				{
					res_value = "0";
				}
		 
		
				if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
					res_value = "0";
		}catch (Exception e) {  
			 res_value = "0";
			 System.out.println("PMS-->DCB-->Controller --> getValue ()-->" + e );
		}  
		 
		rs_cont.close();
		con_loc.close();
		ps.close();
		rs_cont = null;
		obj_loc = null;
		ps=null;
		return res_value;
	}
	  public int dist_getCount(String tablename, String userquery,String columnt_dis) throws Exception 
	  {
		  	String qry = "select count(distinct "+columnt_dis+") from " + tablename + " " + userquery; 
		  	String res_value = "";
		  	ResultSet rs2 = null;
		  	Connection con_loc = null;
		  	Controller obj_loc = new Controller();
		  	con_loc = obj_loc.con();
		  	PreparedStatement ps = con_loc.prepareStatement(qry);
		  	rs2 = ps.executeQuery();
		  	if (rs2.next()) 
		  	{
		  		res_value = rs2.getString(1);
		  	}  	
		  	if (res_value.equals("") || res_value == null)
		  		res_value = "0";
		  	con_loc.close();
		  	rs2.close();
		  	ps.close();
		  	rs2 = null;
		  	con_loc = null;
		  	ps =null;
		  	return Integer.parseInt(res_value);
	  }
	
	  public int getCount(String tablename, String userquery)	throws Exception 
	  {
		  String qry = "select count(*) from " + tablename + " " + userquery;
		  System.out.println("qryyyyyyyyyyyyyyyyy"+qry);
		  String res_value = "";
		  ResultSet rs2 = null;
	
		  Connection con_loc = null;
		  Controller obj_loc = new Controller();
		  con_loc = obj_loc.con();
		  PreparedStatement ps = con_loc.prepareStatement(qry);
		  rs2 = ps.executeQuery();
		  if (rs2.next()) 
		  {
			res_value = rs2.getString(1);
		  }  
		  if (res_value.equals("") || res_value == null) res_value = "0";
		  con_loc.close();
		  rs2.close();
		  ps.close();
		  rs2 = null;
		  con_loc = null;
		  ps =null;
		  return Integer.parseInt(res_value);
	}

	synchronized public ResultSet getRS(String userquery) throws Exception {
  		System.out.println("userqueryyyyyyyyyyyyyyyyyy"+userquery);
		rs2 = stmt.executeQuery(userquery);
  		
		return rs2;

	}  

// Modified Data 
	public String combo_Mod(String va,String text,String value, int type, String defaultvalue) throws Exception
	{
		Statement stmt1 = con.createStatement();
		String xml = "<result>";
		String qry = "select SELECT DISTINCT OFFICE_ID , (SELECT OFFICE_NAME from com_mst_all_offices_view WHERE OFFICE_ID=PMS_DCB_MST_BENEFICIARY.OFFICE_ID) AS OFFICE_NAME FROM PMS_DCB_MST_BENEFICIARY WHERE status='L' AND OFFICE_ID NOT IN (" +va+ ")";
		  System.out.println(qry);
		int row = 0;
		if (type == 2)
		{
			xml += "<name><![CDATA[" + defaultvalue + "]]></name>";
			xml += "<sno>0</sno>";
		}
		ResultSet rs_loc = stmt1.executeQuery(qry);
		while (rs_loc.next())   
		{
			row++;
			xml += "<name><![CDATA[" + rs_loc.getString(text) + "]]></name>";
			xml += "<sno>" + rs_loc.getInt(value) + "</sno>";
		}
		xml += "<status>";
		//System.out.println(xml);
		if (row == 0)
			xml += "Data Not Found";
		else
			xml += "Data Found";
		xml += "</status>";
		xml += "</result>";
		stmt1.close();
		rs_loc.close();
		stmt1 = null;
		return xml;
	}
	
	
	
	
	
	public String combo_lkup(String value, String text, String table,String userquery, int type, String defaultvalue) throws Exception
	{
		Statement stmt1 = con.createStatement();
		String xml = "<result>";
		String qry = "select " + value + "," + text + " from " + table + " "+ userquery;
		  System.out.println(qry);
		int row = 0;
		if (type == 2)
		{
			xml += "<name><![CDATA[" + defaultvalue + "]]></name>";
			xml += "<sno>0</sno>";
		}
		ResultSet rs_loc = stmt1.executeQuery(qry);
		while (rs_loc.next())   
		{
			row++;
			xml += "<name><![CDATA[" + rs_loc.getString(text) + "]]></name>";
			xml += "<sno>" + rs_loc.getInt(value) + "</sno>";
		}
		xml += "<status>";
		//System.out.println(xml);
		if (row == 0)
			xml += "Data Not Found";
		else
			xml += "Data Found";
		xml += "</status>";
		xml += "</result>";
		stmt1.close();
		rs_loc.close();
		stmt1 = null;
		return xml;
	}
	synchronized public void conClose(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
	public void testQry(String s) {
		System.out.println(s);
	}

	public String isNull(String input, int type) {
		if (type == 1) {
			if (input == null || input == "'null'")
				input = "0";
			if (input.equals("") || input == "")
				input = "0";
		} else if (type == 2) {
			if (input == null || input == "'null'")
				input = "-";
			if (input.equals("") || input == "" || input == null)
				input = "-";
		} else if (type == 3) {
			if (input == null || input == "'null'")
				input = "";
			if (input.equals("") || input == "" || input == null)
				input = "";

		} else if (type == 4) {
			if (input == null || input == "'null'")
				input = ".";
			if (input.equals("") || input == "" || input == null)
				input = ".";

		}else if (type == 5) {
			if (input == "0")
				input = "";
		}
		return input;
	}
	public String isNull(String input, double type) {
		if (type == 1) {
			if (input == null || input == "'null'")
				input = "0";
			if (input.equals("") || input == "")
				input = "0";
		} else if (type == 2) {
			if (input == null || input == "'null'")
				input = "-";
			if (input.equals("") || input == "" || input == null)
				input = "-";
		} else if (type == 3) {
			if (input == null || input == "'null'")
				input = "";
			if (input.equals("") || input == "" || input == null)
				input = "";

		} else if (type == 4) {
			if (input == null || input == "'null'")
				input = ".";
			if (input.equals("") || input == "" || input == null)
				input = ".";

		}else if (type == 5) {
			if (input == "0")
				input = "";
		}
		return input;
	}
	public String genReport(String qry, String table_header,String table_column, String property, String table_heading,String table_td_set) throws Exception {
		String s = "";
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
		html += "<tr   bgcolor='skyblue'>";
		html += "<td class='tdText' width='5%'  align='center'>Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) {

			html += " <td class='tdText' align=center ><font   size=2><b>" + table_header_f[i].trim() + "</b></font></td>";
		}  
		html += "</tr>"; 
		int row = 0;
		while (rs_loc.next()) {
			row++;
			html += "<tr><td class='tdText' width='5%' align='center'>" + (row) + "</td>";
		 
			for (int i = 0; i < table_header_f.length; i++) {
				 
				html += "<td  " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + "><font   size=2>"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
			}
			html += "</tr>";
		}  
		html += "</table>"; 
		rs_loc.close();
		con_loc.close();
		return html;

	}
	public String genReport3(String qry, String table_header,String table_column, String property, String table_heading,String table_td_set) throws Exception {
		String s = "";
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
		html += "<tr   bgcolor='skyblue'>";
		html += "<td class='tdText' width='5%'  align='center'>Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) {

			html += " <td class='tdText' align=center ><b>" + table_header_f[i].trim() + "</td>";
		}  
		html += "</tr>"; 
		int row = 0;
		while (rs_loc.next()) {
			row++;
			html += "<tr><td class='tdText' width='5%' align='center'>" + (row) + "</td>";
		 
			for (int i = 0; i < table_header_f.length; i++) {
				 
				html += "<td  " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + ">"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
			}
			html += "</tr>";
		}  
		html += "</table>"; 
		rs_loc.close();
		con_loc.close();
		return html;

	}
	public String genReport2(String qry,String table_column, String property, String table_heading,String table_td_set) throws Exception {
		String s = "";
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
		int row = 0;
		while (rs_loc.next()) {
			row++;
			html += "<tr><td class='tdText' width='5%' align='center'>" + (row) + "</td>";
		 
			for (int i = 0; i < table_column_f.length; i++) {
				 
				html += "<td  " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + "><font   size=2>"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
			}
			html += "</tr>";
		}  
		html += "</table>"; 
		rs_loc.close();
		con_loc.close();
		return html;

	}
	public String genReport(String qry, String table_header,String table_column, String property, String table_heading,String table_td_set,int control_type,int position,String control_name) throws Exception {
		String s = "";
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		//System.out.println("DCB->Controller->genReport");
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
		html += "<tr class='tdH'>";
		html += "<td class='tdText' width='5%' >Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) {

			html += " <td class='tdText' align=center >" + table_header_f[i].trim() + "</td>";
		}
		html += "</tr>";
		int row = 0;
		while (rs_loc.next()) 
		{
			row++;
			html += "<tr><td class='tdText' width='5%'>" + (row) + "</td>";
			for (int i = 0; i < table_td_set_f.length; i++) 
			{
				html += "<td class='tdText' " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + " >"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
			}
		
				if (control_type==3)
				{
				html += "<td width='5%' align=center><input type=checkbox id="+(control_name+row)+" name="+(control_name+row)+"></td>";
				}
			
			html += "</tr>";
		} 
		html += "</table>";
		rs_loc.close();
		con_loc.close(); 
		return html;

	}
	public String month_val(String no) {
		
		String[] cmonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
		return cmonth[Integer.parseInt(no)];
	}
	public String month_val(int no) {
		
		String[] cmonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
		return cmonth[no];
	}
	public String month_val(String no,int flag) {

		String[] cmonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
		return cmonth[Integer.parseInt(no)];
	}
	
	//New
	public String com(String id,String Off_id,String Off_name,String def_off) throws Exception
	{
		String combo = "<select id='" + id + "' name='" + id + "'  >";
		System.out.println(combo);
		Connection con_loc = null;  
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select  CLOSED_OFFICE_ID,(select office_name from com_mst_offices where office_id=PMS_DCB_CLOSED_OFFICE_MAP.CLOSED_OFFICE_ID) office_name from PMS_DCB_CLOSED_OFFICE_MAP where ACTIVE_OFFICE_ID="+Off_id;
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		System.out.println("qryrrrrrrrrrrrrrrrrr"+qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='"+Off_id+"'>"+Off_name+"</option>";
		String cvalue="";
		String ctext="";
		try {
			while (rs_loc.next()) 
			{
				cvalue=rs_loc.getString("CLOSED_OFFICE_ID");
				ctext=rs_loc.getString("office_name");
				if (Off_id.equals(def_off))
				{
					combo += "<option value='" +cvalue+ "'>"+ctext+"</option>";
				}
				 else 
				 {
					combo += "<option value='" +cvalue+ "' selected=selected>"+ctext+"</option>";
				}
				
			}
			combo += "</select>";
		} catch (Exception e) {
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		con_loc = null; 
		return combo;
	}
		
	// 6 parameters
	public String combo_str(String table, String text, String value,String cond, String id, String attributes) throws Exception
	{
		String combo = "<select id='" + id + "' name='" + id + "' "+ attributes + " >";
		System.out.println(combo);
		Connection con_loc = null;  
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text + "," + value + " from " + table + "  "+ cond;
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		System.out.println("qryrrrrrrrrrrrrrrrrr"+qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select </option>";
		String cvalue="";
		String ctext="";
		try {
			while (rs_loc.next()) 
			{
				cvalue=rs_loc.getString(value);
				ctext=rs_loc.getString(text)+" ("+cvalue+")";
				combo += "<option value='" +cvalue+ "'>"+ctext+"</option>";
				
			}
		} catch (Exception e) {
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		con_loc = null; 
		return combo;
	}
	public String combo_str(String table, String text, String value,String cond, String id,String initial_value, String attributes) throws Exception
	{
		String combo = "<select id='" + id + "' name='" + id + "' "	+ attributes + " onchange=fst();>";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text + "," + value + " from " + table + "  "+ cond;
		System.out.println("qryyyyyyyyyyyyyyyyy##"+qry);
		
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select </option>";
		String vl="";
		try {  
			while (rs_loc.next()) {
				vl=rs_loc.getString(value); 
				 
				if (initial_value.equalsIgnoreCase(vl))
				combo += "<option value='" +vl + "' selected >"
					+ rs_loc.getString(text) + " </option>";
				else
				combo += "<option value='" +vl + "'>"
						+ rs_loc.getString(text) + " </option>";

			}
		} catch (Exception e) {
			//System.out.println("Combo ----------------------> " + e);
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		return combo;
	}
	//New added
	public String combo_str1(String month, String year,String id,String idname) throws Exception
	{
				String data=month;
		return data;
	}
	
	
	
	
	
	public String combo_str(String table, String text, String as,String value,  
			String cond, String id,String initial_value, String attributes) throws Exception

	{
		String combo = "<select id='" + id + "' name='" + id + "' "
				+ attributes + ">";
		Connection con_loc = null;  
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text+"  as "+as+","+value + " from " + table + "  "
				+ cond;
		 System.out.println(qry);  
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select </option>";
		String vl="";
		try {
			while (rs_loc.next()) {
				vl=rs_loc.getString(value); 
				
				if (initial_value.equalsIgnoreCase(vl))
				combo += "<option value='" +vl + "' selected >"
					+ rs_loc.getString(text) + " </option>";
				else
				combo += "<option value='" +vl + "'>"
						+ rs_loc.getString(as) + " </option>";

			}
		} catch (Exception e) {
			//System.out.println("Combo ----------------------> " + e);
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		return combo;
	}
	public String resultXML(String query,Connection prcon, Controller Obj) throws SQLException
	{
		 
		   
	  String res="";
	  PreparedStatement pn_=prcon.prepareStatement(query);
 //   System.out.println(query);     
	  Hashtable hashprocess=new Hashtable();
	  ResultSet local_rs=pn_.executeQuery(); 
	  ResultSetMetaData rsmd = local_rs.getMetaData();
	  String ColumnName ="";
	  String ColumnValue="";
	  int NumOfCol=rsmd.getColumnCount();
	  res="<response>";  
	 
	  int row_count=0;
	  while (local_rs.next())
	  {
		  row_count++;
		  for (int i=1;i<=NumOfCol;i++)
		  {
			  ColumnName = rsmd.getColumnName(i);
			  ColumnValue=local_rs.getString(ColumnName);
			  res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
		  }
		  
	  }
	  res+="<row_count>"+row_count+"</row_count></response>";
	  local_rs.close();
	  pn_.close();
	  
		return res;
	}  
	public String resultXML(String query,Hashtable condition_value,Connection prcon, Controller Obj,int flag) throws SQLException
	{
	  String res="";
	  PreparedStatement pn_=prcon.prepareStatement(query);
	  String key="",key_value="";	 
	  Enumeration er=condition_value.keys();
	  int j=1;
	  while (er.hasMoreElements())  
		{ 
			 
			key=(String)er.nextElement();
			String value=(String)condition_value.get(key);
			pn_.setString(j, value);
			j++;
		}
	   
	  ResultSet local_rs=pn_.executeQuery(); 
	  ResultSetMetaData rsmd = local_rs.getMetaData();
	  String ColumnName ="";
	  String ColumnValue="";
	  int NumOfCol=rsmd.getColumnCount();
	  res+=Obj.generateXML("select", "0" , 1, Obj); 
	  int row_count=0;
	  while (local_rs.next())
	  {
		  row_count++;
		  for (int i=1;i<=NumOfCol;i++)
		  {
			  ColumnName = rsmd.getColumnName(i);
			  ColumnValue=local_rs.getString(ColumnName);
			  res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
		  }
		   
	  }
	  res+="<row_count>"+row_count+"</row_count> ";
	  local_rs.close();
	  pn_.close();
	  
		return res;
	}  
	public String resultXML(String query,Hashtable condition_value,Connection prcon, Controller Obj) throws SQLException
	{
	  String res="";
	  PreparedStatement pn_=prcon.prepareStatement(query);
	  String key="",key_value="";	 
	  Enumeration er=condition_value.keys();
	  int j=1;
	  while (er.hasMoreElements())  
		{ 
			 
			key=(String)er.nextElement();
			String value=(String)condition_value.get(key);
			pn_.setString(j, value);
			j++;
		}
	   
	  ResultSet local_rs=pn_.executeQuery(); 
	  ResultSetMetaData rsmd = local_rs.getMetaData();
	  String ColumnName ="";
	  String ColumnValue="";
	  int NumOfCol=rsmd.getColumnCount();
	  res="<response>";  
	  int row_count=0;
	  while (local_rs.next())
	  {
		  row_count++;
		  for (int i=1;i<=NumOfCol;i++)
		  {
			  ColumnName = rsmd.getColumnName(i);
			  ColumnValue=local_rs.getString(ColumnName);
			  res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
		  }
		   
	  }
	  res+="<row_count>"+row_count+"</row_count></response>";
	  local_rs.close();
	  pn_.close();
	  
		return res;
	}  
	public String generateXML(String tagName,String value,int NullValue,Controller Obj)
	{
		String xml="<"+tagName+">"+ Obj.isNull(value,NullValue)+"</"+tagName+">";
		
		return xml;   
	} 
	public String generateXML(String tagName,String value,double NullValue,Controller Obj)
	{
		String xml="<"+tagName+">"+ Obj.isNull(value,NullValue)+"</"+tagName+">";
		
		return xml;
	} 
	public String resultXML(String query,Connection prcon, Controller Obj,int flag) throws SQLException
	{
		 
		 
	  String res="";
	  PreparedStatement pn_=prcon.prepareStatement(query);
	  Hashtable hashprocess=new Hashtable();
	  ResultSet local_rs=pn_.executeQuery(); 
	  ResultSetMetaData rsmd = local_rs.getMetaData();
	  String ColumnName ="";
	  String ColumnValue="";
	  int NumOfCol=rsmd.getColumnCount();
	
	  int row_count=0;
	  while (local_rs.next())
	  {
		  row_count++;
		  for (int i=1;i<=NumOfCol;i++)
		  {
			  ColumnName = rsmd.getColumnName(i);
			  ColumnValue=local_rs.getString(ColumnName);
			  
			  
			  res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
		  }
		  
	  }
	  res+="<row_count>"+row_count+"</row_count> ";
	  local_rs.close();
	  pn_.close();
	   
		return res;
	}  
	public void ben_excel(String qry, String table_header, String table_column,
			String property, String table_heading, String table_td_set,
			String fname) throws Exception {
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		File f=new File (fname+"/bentype.xls");
		WritableWorkbook wc = Workbook.createWorkbook(f);
		WritableSheet sheet = wc.createSheet("First Sheet", 0);
        WritableFont wf = new WritableFont(WritableFont.ARIAL,9, WritableFont.NO_BOLD);    
        WritableCellFormat cf = new WritableCellFormat(wf);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        cf.setWrap(true);
        cf.setAlignment(Alignment.CENTRE);
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);  
        Label labe0 = new Label(0, 0, "Heading",cf);
		sheet.addCell(labe0);
		CellView cv = new CellView();
		cv.setAutosize(true);
		for (int i = 0; i < table_header_f.length; i++) {
			Label label = new Label(i, 1, table_header_f[i],cf);
			sheet.addCell(label);
			sheet.setColumnView(i, 20);
		} 
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		int i = 0;
		int j = 2;
		int k=0;
		try {  
			while (rs_loc.next()) 
			{
				int s=0;
				while (s <table_header_f.length)
				{
				 sheet.addCell(new Label(s, j, rs_loc.getString(table_column_f[s].trim()))); 
				 s++;
				}
				j++;
				k++;
			} 
		} catch (Exception e) 
		{  
		System.out.println(e)  ;
		}
		wc.write();
		wc.close();
		con_loc.close();
	} 
	public void ben_excel(String qry, String table_header, String table_column,
			String property, String table_heading, String table_td_set,
			String fname,int flag) throws Exception {
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();		
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		File f=new File (fname+"/bentype.xls");
		WritableWorkbook wc = Workbook.createWorkbook(f);
		WritableSheet sheet = wc.createSheet("First Sheet", 0);
        WritableFont wf = new WritableFont(WritableFont.ARIAL,9, WritableFont.NO_BOLD);    
        WritableCellFormat cf = new WritableCellFormat(wf);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        cf.setWrap(true);
        cf.setAlignment(Alignment.CENTRE);
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);  
        Label labe0 = new Label(0, 0, "Heading",cf);
		sheet.addCell(labe0);
		CellView cv = new CellView();
		cv.setAutosize(true);
		
		
		
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int rowCount = metaData.getColumnCount();
		
		for (int i = 0; i < rowCount; i++) {
			Label label = new Label(i, 1, metaData.getColumnName(i + 1),cf);
			sheet.addCell(label);
			sheet.setColumnView(i, 20);
		}   
		String cname="";
		int i = 0;
		int j = 2;
		int k=0;
		try {  
			while (rs_loc.next()) 
			{
				int s=0;
				for (  s = 0; s< rowCount; s++) 
				{
					
					cname=metaData.getColumnName(s + 1);
					String type=metaData.getColumnTypeName(s + 1);
					if (type.equalsIgnoreCase("VARCHAR"))
						sheet.addCell(new Label(s, j, rs_loc.getString(cname)));
					if (type.equalsIgnoreCase("NUMBER")) 
						sheet.addCell(new Label(s, j, Integer.toString(rs_loc.getInt(cname)))); 
				}
				j++;
				k++;
			} 
		} catch (Exception e) 
		{  
		System.out.println(e)  ;
		}
		wc.write();
		wc.close();
		con_loc.close();
	} 
	public long days(int fyear,int fmonth ,int fday,int lyear,int lmonth,int lday)
	{
		  Calendar cal1 = Calendar.getInstance();
		   Calendar cal2 = Calendar.getInstance();
		   cal1.set(fyear, fmonth, fday);
		   cal2.set(lyear, lmonth, lday);
		   long milis1 = cal1.getTimeInMillis();
		   long milis2 = cal2.getTimeInMillis();
		   long diff = milis2 - milis1;
		   long diffSeconds = diff / 1000;
		   long diffMinutes = diff / (60 * 1000);
		   long diffHours = diff / (60 * 60 * 1000);
		   long diffDays = diff / (24 * 60 * 60 * 1000);
		   return diffDays ;
	}
	public int  rowUpdate(String qry) 
	{
		int rows=0;
		Connection con_locUpdate = null;
		Controller obj_locUpdate = new Controller();
		try {
			con_locUpdate = obj_locUpdate.con();
			PreparedStatement ps_locUpdate = con_locUpdate.prepareStatement(qry);
			rows=ps_locUpdate.executeUpdate();
			ps_locUpdate.close();
		//	con_locUpdate.commit();
			con_locUpdate.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}  
		return rows;
	}
	public String month_val2(int month) 
	{
		String[] cmonthday = { "0", "31", "28", "31", "30","31", "30", "31", "31", "30", "31", "30", "31" };
		return cmonthday[month];
	}
	public int month_val2(int month,int year) 
	{
		String value="0";
		String[] cmonthday = { "0", "31", "28", "31", "30","31", "30", "31", "31", "30", "31", "30", "31" };
		if (month==2)
		{
			if  ((year%4==0 && year%100!=0) || (year%400==0) )
			{
				value="29";
			}else
			{
				value="28";
			}
		}else
		{
			value=cmonthday[month];
		}
		return Integer.parseInt(value);
	}
	public String  prvfinyear(String input,int prv) 
	{
		String val="";
		String []sr1=input.split("-");
		String ss1=sr1[0];
		String ss4=Integer.toString(Integer.parseInt(sr1[0])); 
		for (int i=1;i<=prv;i++)
		{			
			ss4=Integer.toString(Integer.parseInt(ss4)-1);
			if (i==1)
			val+=""+ss4+"-"+ss1;			 
			else
			val+=","+ss4+"-"+ss1;  
			ss1 =ss4;			
		}
		return val;
	}
	public String prvfinyear(String input,int prv,int flag) 
	{
		String val="";
		String []sr1=input.split("-");
		String ss1=sr1[0];
		String ss4=Integer.toString(Integer.parseInt(sr1[0])); 
		for (int i=1;i<=prv;i++)
		{
			ss4=Integer.toString(Integer.parseInt(ss4)-1);
			if (i==1)
			val+="'"+ss4+"-"+ss1+"'";			 
			else
			val+=",'"+ss4+"-"+ss1+"'";
			ss1 =ss4;
		}
		return val;
	}
	public int recordSave(Hashtable values,String table,Connection con) throws SQLException
	{   
		int row=0;
		Enumeration  er=values.keys();
		String key="";
		String qry="insert into "+table;
		System.out.println("qry"+qry);
		String column=" ( ";
		Object  pr_s=null;
		String value =" values ( ";
		System.out.println("valuessssssssss"+values);
		int size=values.size();
		int count=0;
		while (er.hasMoreElements())  
		{
			key=(String)er.nextElement();
			if (count==(size-1))
			{ 
			column+=""+key+")";
			pr_s=values.get(key);
			value+=pr_s+")";
			}else
			{  
				column+=""+key+",";
				pr_s=values.get(key);
				value+=pr_s+",";
			}  
			count++;
		}	
		
		return insert((qry+column+value),con);
	}
	public int recordSave(Hashtable cols,Hashtable cond,String table,Connection con) throws SQLException
	{
		int row=0;
		Enumeration  er=cols.keys();
		String key="";  
		String qry="update "+table;
		String column=" set ";
		Object  pr_s=null;
		int size=cols.size();
		int count=0;
		while (er.hasMoreElements())
		{
			key=(String)er.nextElement();
			if (count==(size-1))
			{ 
			column+=""+key+"=";
			pr_s=cols.get(key);
			column+=pr_s+"";
			}else  
			{  
				column+=""+key+"="; 			
				pr_s=cols.get(key);
				column+=pr_s+",";
			}  
			count++;
		}	
		Enumeration  cond_er=cond.keys();
		String condcolumn="  where (1=1)  and   ";
		int condsize=cond.size();
		pr_s=""; 
		count=0;
		while (cond_er.hasMoreElements())
		{
			key=(String)cond_er.nextElement();
			if (count==(condsize-1))
			{ 
				condcolumn+=" "+key+"=";
				pr_s= cond.get(key);
				condcolumn+=pr_s+"";
			}else  
			{  
				condcolumn+="  "+key+"=";			
				pr_s= cond.get(key);
				condcolumn+=pr_s+" and  ";		 
			} 
			count++;     
		}
		 
		return insert((qry+column+condcolumn),con);
	}
	public int insert(String str,Connection prcon) throws SQLException  
	{  
		  System.out.println("inser table"+str);  
		  PreparedStatement pn_=prcon.prepareStatement(str);
		  return pn_.executeUpdate();
	}
	public ArrayList Records(String qry,Connection rcon) throws SQLException
	{
		Connection Rec_con=rcon;
		ArrayList<Object[]> val=new ArrayList<Object[]>();
		PreparedStatement Rec_ps=Rec_con.prepareStatement(qry);
		ResultSet Rec_rs=Rec_ps.executeQuery();
		while(Rec_rs.next())
		{
		    int cols = Rec_rs.getMetaData().getColumnCount();
		    Object[] arr = new Object[cols];
		    for(int i=0; i<cols; i++)
		    {
		      arr[i] = Rec_rs.getObject(i+1);
		    }
		    val.add(arr);
		}
		 Rec_rs.close();   
		 Rec_ps.close();
		 Rec_con.close();
		return val;
	}
	public ArrayList Records1(String qry,Connection rcon) throws SQLException
	{
		Connection Rec_con=rcon;
		ArrayList<String>  val=new ArrayList<String>();
		PreparedStatement Rec_ps=Rec_con.prepareStatement(qry);
		ResultSet Rec_rs=Rec_ps.executeQuery();
		int key=-1;
		while(Rec_rs.next())
		{
		    int cols = Rec_rs.getMetaData().getColumnCount();
		    key++;
		    for(int i=1; i<=cols; i++)
		    {		     
		      val.add(key, Rec_rs.getString(i));
		    }
		}
		 Rec_rs.close();   
		 Rec_ps.close();
		 Rec_con.close();
		return val;
	}
	public void writeInCsvFormat(String query,String fileName) throws Exception
	{        
	      //String res="";
		  Controller obj_locUpdate = new Controller();
		  Connection Rec_con=obj_locUpdate.con();
	      PreparedStatement preparedStatement=Rec_con.prepareStatement(query);
	      //Hashtable hashprocess=new Hashtable();
	      FileWriter writer = new FileWriter(fileName);
	      ResultSet local_rs=preparedStatement.executeQuery();
	      ResultSetMetaData rsmd = local_rs.getMetaData();
	      String columnName ="";
	      String columnValue="";
	      int NumOfCol=rsmd.getColumnCount();
	      //res="<response>";
	      //int row_count=0;
	      for(int i=1;i<=NumOfCol;i++){
	          columnName = rsmd.getColumnName(i);
	          writer.append(columnName);
	          writer.append(',');
	      }
	      writer.append('\n');
	      while (local_rs.next()){
	          //row_count++;         
	          for (int i=1;i<=NumOfCol;i++)
	          {
	              columnName = rsmd.getColumnName(i);    
	              ////System.out.println("check --> "+local_rs.getString(columnName));
	              columnValue = local_rs.getString(columnName);
	              writer.append(columnValue);
	              writer.append(',');
	              //res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
	          }
	          writer.append('\n');
	      }
	      //res+="<row_count>"+row_count+"</row_count></response>";
	      local_rs.close();
	      preparedStatement.close();    
	      //return res;
	    }
public static void  condition(int type,String input)
{ 
	String res="";
	res="beneficiary_sno not in ( SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY a WHERE  1 >   ( SELECT COUNT(*)  FROM PMS_DCB_MST_BENEFICIARY_METRE b   WHERE a.office_id    = b.office_id   AND a.beneficiary_sno=b.beneficiary_sno and b.METER_STATUS='L'   )  AND a.office_id="+input+" ) ";
	meter_not_for_ben=res;
}
public   String[] columnName(Connection con,String table) throws SQLException
{
	DatabaseMetaData metadata = con.getMetaData();
	ResultSet resultSet = metadata.getColumns(null, null, table, null);
	ResultSet resultSet1 = metadata.getColumns(null, null, table, null);
	int c = 0;
	while (resultSet1.next()) 
	{  
		c++;
	}
	String[] col = new String[c];
	int c1 = 0;
	while (resultSet.next()) 
	{
			
		col[c1] =new String ();
		String name = resultSet.getString("COLUMN_NAME");
		col[c1] = name;
		c1++;
	}
	return col;
}  
public ArrayList tableName(Connection con) throws SQLException
{
	ArrayList tbname= new ArrayList();
	stmt = con.createStatement();  
    rs = stmt.executeQuery("select object_name from user_objects where object_type = 'TABLE' and (  object_name not like '%TEMP%' and  object_name not like '%BIN%') order by object_name");
    while (rs.next()) {
      String tableName =""+rs.getString(1)+"";  
      tbname.add(tableName);   
    }
	return tbname;
}

public   String columnName(Connection con,String table,int flag) throws SQLException  
{
	DatabaseMetaData metadata = con.getMetaData();
	ResultSet resultSet = metadata.getColumns(null, null, table, null);
	ResultSet resultSet1 = metadata.getColumns(null, null, table, null);
	String  col = "";
	while (resultSet.next()) 
	{
		String name = resultSet.getString("COLUMN_NAME");
		col+="<td><font size=2>"+name+"</font></td>"; 
	}  
	return col;
} 
public   String columnValues(Connection con,String table,int flag) throws SQLException  
{
	DatabaseMetaData metadata = con.getMetaData();
	ResultSet resultSet = metadata.getColumns(null, null, table, null);
	ResultSet resultSet1 = metadata.getColumns(null, null, table, null);
	int c = 0;
	while (resultSet1.next()) {  
		c++;
	}
	String  col = "";
	int crow = 0;
	String []cl=new String[c];
	while (resultSet.next()) 
	{
		String name = resultSet.getString("COLUMN_NAME");
		 cl[crow]=name;
		crow++;    
	}
	PreparedStatement ps=con.prepareStatement("select * from " + table);
	String outp=" ";
	ResultSet rs_new=ps.executeQuery();
	while (rs_new.next())
	{
		outp+="<tr>";
		for (int i=0;i<crow;i++)
		{
			outp+="<td><font size=2>"+rs_new.getString(cl[i])+"</font></td>";
		}
		outp+="</tr>";
	}
	return outp;
} 
public   ArrayList columnNames(Connection con,String qry) throws SQLException  
{
	ArrayList ar=new ArrayList();
	Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(qry);
    ResultSetMetaData rsmd = rs.getMetaData();
    int numColumns = rsmd.getColumnCount();
    String outp=" ";
    outp="<tr><td><font size=2 color='black'>Sl.No</font></td>"; 
    ar.add(outp);
    for (int i=1; i<numColumns+1; i++) 
    {
        String columnName = rsmd.getColumnName(i);
        outp="<td><font size=2 color='black'>"+columnName+"</font></td>"; 
        ar.add(outp);
    }
    outp="</tr>";
    ar.add(outp);
	return ar;  
//	return outp;
}   
public   ArrayList columnValues(Connection con,String qry,Controller obj) throws SQLException  
{
	ArrayList ar=new ArrayList();
	Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(qry);
    ResultSetMetaData rsmd = rs.getMetaData();  
    int numColumns = rsmd.getColumnCount();
    String outp=" ";
    ar.add(outp);
    int row=0;
    while (rs.next())
    {   
    	row++;
    	outp="<tr><td><font size=2 color='black'>"+row+"</font></td>"; 
    	  ar.add(outp);  
    	   for (int i=1; i<numColumns+1; i++) 
    	    {
    	        String columnName = rsmd.getColumnName(i);
    	        String cname = rs.getString(columnName);   
    	        outp="<td><font size=2 color='black'>&nbsp;"+cname+"</font></td>";  
    	        ar.add(outp);
    	    }
	    outp="</tr>";  
	    ar.add(outp);
    }
	return ar;  
}     
public int daysBetween(Date d1, Date d2){  
	 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	 }

  
public String rec_trouble()  
{
	String receipt_troubleshoot=" select mas.SUB_LEDGER_CODE,trans.SUB_LEDGER_CODE ,mas.TOTAL_AMOUNT,trans.AMOUNT,mas.RECEIPT_NO,ben.BENEFICIARY_NAME,off_.OFFICE_NAME,mas.SUB_LEDGER_CODE,mas.ACCOUNTING_FOR_OFFICE_ID "+  
						" from ( select ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SUB_LEDGER_TYPE_CODE,"+
						" SUB_LEDGER_CODE,AMOUNT from FAS_RECEIPT_TRANSACTION where  ACCOUNTING_FOR_OFFICE_ID="+recp_off+" and  CASHBOOK_YEAR="+recp_year+" and  CASHBOOK_MONTH="+recp_month+"" +
						"  and  (    SUB_LEDGER_CODE=0 or SUB_LEDGER_CODE is null )" +
						" )trans join (  select ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH, RECEIPT_NO, SUB_LEDGER_TYPE_CODE," +
						" SUB_LEDGER_CODE ,TOTAL_AMOUNT from FAS_RECEIPT_MASTER where RECEIPT_STATUS='L'  and CASHBOOK_YEAR="+recp_year+" and  CASHBOOK_MONTH="+recp_month+" and " +
						" SUB_LEDGER_TYPE_CODE=14 ) mas on mas.ACCOUNTING_FOR_OFFICE_ID=trans.ACCOUNTING_FOR_OFFICE_ID and " +
						" mas.CASHBOOK_YEAR= trans.CASHBOOK_YEAR and mas.CASHBOOK_MONTH=trans.CASHBOOK_MONTH and  mas.RECEIPT_NO=trans.RECEIPT_NO " +
						" join ( SELECT BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where status='L' ) ben on ben.BENEFICIARY_SNO=mas.SUB_LEDGER_CODE join( select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES " +
						" where OFFICE_LEVEL_ID='DN')off_ on off_.OFFICE_ID=mas.ACCOUNTING_FOR_OFFICE_ID order by mas.ACCOUNTING_FOR_OFFICE_ID"; 
   return receipt_troubleshoot;
}
 
public  ArrayList  week_dates_start(int year,int month) 
{  
	ArrayList<String> a1=new ArrayList<String>();	 	
    List<List<String>> weekdates = getNumberOfWeeks(year, month);
    for(List<String> weekDatesLoop:weekdates)
    {
        String date1=weekDatesLoop.get(0).toString();      
        String y1=date1.substring(0, 4);
        String m1=date1.substring(4, 6);
        String d1=date1.substring(6, 8);
        String res1=d1+"/"+m1+"/"+y1;     
                a1.add(res1);  
    }
      
    return a1;
  }
public  ArrayList  week_dates_end(int year,int month) 
{
	
	ArrayList<String> a2=new ArrayList<String>();	
    List<List<String>> weekdates = getNumberOfWeeks(year, month);
    for(List<String> weekDatesLoop:weekdates)
    {
        String date2=weekDatesLoop.get(1).toString();
        String y2=date2.substring(0, 4);
        String m2=date2.substring(4, 6);
        String d2=date2.substring(6, 8);
        String res2=d2+"/"+m2+"/"+y2;
        a2.add(res2);  
    }   
    return a2;
  }
public static List<List<String>> getNumberOfWeeks(int year, int month) 
{		       
      month = month-1;
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
      List<List<String>> weekdates = new ArrayList<List<String>>();
      List<String> dates = new ArrayList<String>();
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, month);
      c.set(Calendar.DAY_OF_MONTH, 1);
      dates.add(format.format(c.getTime()));
      //int numOfWeeksInMonth = 1;
      while (c.get(Calendar.MONTH) == month) {
        //System.out.println(c.get(Calendar.DAY_OF_WEEK) );
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          dates.add(format.format(c.getTime()));
          weekdates.add(dates);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
          dates = new ArrayList<String>();
          dates.add(format.format(c.getTime()));
          //numOfWeeksInMonth++;
        }
        c.add(Calendar.DAY_OF_MONTH, 1);
      }
      if(dates.size() < 2){
        c.add(Calendar.DAY_OF_MONTH, -1);
        dates.add(format.format(c.getTime()));
        weekdates.add(dates);
      }
      
      return weekdates;  
      }
public int number_of_week(int year, int month, int current_date)
{
	int res=0;
	Calendar calendar = Calendar.getInstance(); 
    calendar.set(year, month, current_date); 
    int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
    res = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
      
	
	return res;
}
public int prv_year(int year, int month)
{
	 int prv_year=0;
	 if (month==1)
	 {
		 prv_year=year-1;		 
	 }
	 else  
	 {
		 prv_year=year;		  
	 }
	return prv_year;
}
public int prv_year(String month, String year)
{
	 int prv_year=0;
	 if (Integer.parseInt(month)==1)
	 {
		 prv_year=Integer.parseInt(year)-1;		 
	 }
	 else   
	 {
		 prv_year=Integer.parseInt(year);		 
	 }
	return prv_year;
}
public int prvfinyear(int year, int month)
{  
	 int prv_year=0;
	 if (month<=3)
	 {
		 prv_year=year-1;		 
	 }
	 else  
	 {
		 prv_year=year;		 
	 }
	return prv_year;
}
public int prv_month(int year, int month)
{
	int prv_month=0;
	 if (month==1)
	 {
		 prv_month=12;		 
	 }
	 else  
	 {
		 prv_month=month-1;		 
	 }
	return prv_month;	
}
public int prv_month(String month ,String year)
{
	int prv_month=0;
	 if (Integer.parseInt(month)==1)
	 {
		 prv_month=12;		 
	 }
	 else  
	 {
		 prv_month=Integer.parseInt(month)-1;		 
	 }
	return prv_month;	
}
public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
{
	try {
		  JRExporter exporter = new JRPdfExporter();
		  response.setContentType("application/pdf");
		  response.setHeader("Content-Disposition","attachment; filename=\"Report.pdf\"");
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
		  exporter.exportReport();
		  outuputStream.close();  
		  outuputStream.flush();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	
}
public void excelshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
{ 
		try {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","inline; filename=\"Report.xls\"");
		response.addHeader("Content-Disposition", "attachment");
		//response.setHeader("Content-Disposition","attachment;filename=\"Report.xls\"");
		JRXlsExporter exporterXLS = new JRXlsExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.FALSE);
		exporterXLS.exportReport();
		byte[] bytes;
		bytes = xlsReport.toByteArray(); 
		outuputStream.write(bytes, 0, bytes.length);
		outuputStream.flush();
		outuputStream.close();
		} catch (Exception e) {
			 
			e.printStackTrace();
		}    
	 
}
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
}
public int intConv(String v)
{
		int res=0;
		if (v==null) res=0;
		else
			res=Integer.parseInt(v);
		return res;
		
}
public float floatConv(String v)
{
		float res=0.0f;
		if (v==null)
			res=0;
		else
			res=Integer.parseInt(v);
		return res;		
}
public String strConv(int value)
{
	return Integer.toString(value);
}
public String officeName(int Office_id)
{
	String off_name="";
	try 
	{
		off_name = this.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID="+Office_id);
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	return off_name;
}
 
public String regionID(int Office_id)
{
	String off_name="";
	try 
	{
		off_name = this.getValue("com_mst_all_offices_view", "region_office_id","where OFFICE_ID="+Office_id);
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	return off_name;
}
public String accountUID(int Office_id)
{
	String off_name="";
	try  
	{
		off_name = this.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID","where ACCOUNTING_UNIT_OFFICE_ID="+Office_id);
	} catch (Exception e) 
	{ 
		e.printStackTrace();
	}
	return off_name;
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

	public void dis(int value)
	{
		try	{System.out.println("Value : " + value);}catch(Exception e){}
	}
	public void dis(double value)
	{
		try	{System.out.println("Value : " + value);}catch(Exception e)	{}  
	}
	public void dis(String value)
	{
		try{System.out.println("Value : " + value);	}catch(Exception e){}
	}
	public void dis(float value)
	{
		try	{System.out.println("Value : " + value);}catch(Exception e){}
	}
} 
