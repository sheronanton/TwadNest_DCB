package Servlets.AME.AME1.AMEM.servlets;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.postgresql.jdbc2.optional.ConnectionPool;

//import Servlets.PMS.PMS1.DCB.servlets.Controller;
//import sun.rmi.transport.proxy.HttpReceiveSocket;

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
    public DecimalFormat for_mat=new DecimalFormat("0.000"); 
	 public Connection con() throws Exception {
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
		ConnectionString=strdsn.trim()+"://"+strhostname.trim()+":"+strportno.trim()+"/"+strsid.trim();//PostgresDB Connection
		Class.forName(strDriver.trim());
		con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		return con;
		/* public ConnectionPool(String driver, String url,
                 String username, String password,
                 int initialConnections,
                 int maxConnections,
                 boolean waitIfBusy)
		
		*/
		  
	}

	 public void createStatement(Connection con)
			throws SQLException {
		stmt = con.createStatement();
	}

	 public int setUpd(String qry) throws SQLException {

		int row = 0;
		try {
			row = stmt.executeUpdate(qry);			
		} catch (Exception e) {
			//System.out.println("TEST Qry" + e);
		}
		return row;
	}  

	 public int getMax(String table, String field, String userquery)
			throws SQLException {  
		String value = "0";
		String qry = "select max(" + field + ") from "+table+" "+userquery;
		rs = stmt.executeQuery(qry);
		try {
			if (rs.next()) {
				value = rs.getString(1);
				if (value == "" || value == null || value.equals(""))
					value = "0";
			}
		} catch (Exception e) {
			value = "0";
		} 
		return Integer.parseInt(value) + 1;

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
	  
	 public String setValue(String input, HttpServletRequest request) 
	 {
		input = request.getParameter(input);
		
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
 
	 public String officeLevelId(String Office_id)
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
	 public String getValue(String tablename, String name,String userquery) throws Exception 
	{
		String fld = ""; 
		fld = name;
		String qry = "select " + fld + " from " + tablename + " " + userquery;
	 
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
			res_value = obj_loc.isNull(rs_cont.getString(fld),1);			 
		}
		else 
		{
			res_value = "0";
		}	 
		       
		if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
			res_value = "0";
		}catch (Exception e) {
			res_value = "0";
			System.out.println(e);      
		} 
		rs_cont.close();
		con_loc.close();
		ps.close();
		con_loc = null;
		rs_cont = null;
		ps=null;
		return res_value;
	} 
	 public String getGSValue(String tablename, String name,String userquery,String groupby) throws Exception 
		{
			String fld = ""; 
			fld = name;
			String qry = "select sum(" + fld + ") as "+fld+"  from " + tablename + " " + userquery+" group by "+groupby;
		 ////System.out.println(qry); 
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
				res_value = obj_loc.isNull(rs_cont.getString(fld),1);			 
			}
			else 
			{
				res_value = "0";
			}	 
			       
			if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
				res_value = "0";
			}catch (Exception e) {
				res_value = "0";
				System.out.println(e);      
			} 
			rs_cont.close();
			con_loc.close();
			ps.close();
			con_loc = null;
			rs_cont = null;
			ps=null;
			return res_value;
		} 
	 public String getValueA(String tablename, String name,String table_alias,String userquery) throws Exception 
	{
		String fld = ""; 
		fld = name;
		String qry = "select " + fld + " from " + tablename + "    "+table_alias+"" + userquery;
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
			res_value = obj_loc.isNull(rs_cont.getString(fld),1);			 
		}
		else 
		{
			res_value = "0";
		}	 
		       
		if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
			res_value = "0";
		}catch (Exception e) {
			res_value = "0";
			System.out.println(e);      
		} 
		rs_cont.close();
		con_loc.close();
		ps.close();
		 
		return res_value;
	}
	 public String getValue(String tablename, String name,String userquery,int flag) throws Exception 
	{
		String fld = "";
		fld = name;
		String qry = "select " + fld + " from " + tablename + " " + userquery;		
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
			res_value = rs_cont.getString(fld);			 
		}
		else 
		{  
			res_value = "";
		}	 
		       
		if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
			res_value = "";
		}catch (Exception e) {
			res_value = "";    
			System.out.println(e);      
		}
		 
		rs_cont.close();
		con_loc.close();
		ps.close();
		 
		return res_value;
	}
 
	 public String getDate(String tablename, String name,
			String userquery) throws Exception {
		String fld = "";
		fld = name;
		String qry = "select TO_CHAR(" + fld + " , 'DD/MM/YYYY') AS " + fld + " from " + tablename + " " + userquery;
		String res_value = ""; 
		Connection con_loc = null;
		Controller obj_loc = new Controller();  
		con_loc = obj_loc.con();
		ResultSet rs_cont = null;
		PreparedStatement ps = con_loc.prepareStatement(qry);

		rs_cont = ps.executeQuery();

		try 
		{
		if (rs_cont.next()) 
		{
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
			System.out.println(e);      
		}		 
		rs_cont.close();
		con_loc.close();
		ps.close();		 
		return res_value;
	}
	
	 public String getValue(String tablename, String qrycond,String name,String userquery) throws Exception 
	 {
		String fld = "";
		fld = qrycond+" as "+name;   
		String qry = "select " + fld + " from " + tablename + " " + userquery;
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
		return res_value;
	}
	public int getCount(String tablename, String userquery) throws Exception 
	{
		String qry = "select count(*) from " + tablename + " " + userquery;
	    System.out.println(qry);
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
		return Integer.parseInt(res_value);
	}
	 public int getDistinctCount(String tablename, String column,String userquery) throws Exception 
	 {
		 	String qry = "select count(distinct "+column+") from " + tablename + " " + userquery;
		 	String res_value = "";			
			ResultSet rs2 = null;
			Connection con_loc = null;
			Controller obj_loc = new Controller();
			con_loc = obj_loc.con();
			PreparedStatement ps = con_loc.prepareStatement(qry);
			rs2 = ps.executeQuery();			
			if (rs2.next()) {
				res_value = rs2.getString(1);
			}
			if (res_value.equals("") || res_value == null)
				res_value = "0";
			con_loc.close();  
			rs2.close();
			ps.close();
			return Integer.parseInt(res_value);
    }
	public ResultSet getRS(String userquery) throws Exception 
	{  
		rs2 = stmt.executeQuery(userquery);
		return rs2;
	}
	public String combo_lkup(String value, String text, String table,String userquery, int type, String defaultvalue) throws Exception 
	{
		Statement stmt1 = con.createStatement();
		String xml = "<response>";
		String qry = "select " + value + "," + text + " from " + table + " "+ userquery;
		int row = 0;
		if (type == 2) 
		{
			xml += "<name>" + defaultvalue + "</name>";
			xml += "<sno>0</sno>";
		}
		ResultSet rs_loc = stmt1.executeQuery(qry);
		while (rs_loc.next()) 
		{
			row++;
			xml += "<name>"+rs_loc.getString(text)+ "</name>";
			xml += "<sno>" +rs_loc.getInt(value)+ "</sno>";
		}
		xml += "<status>";
		if (row == 0)
			xml += "Data Not Found";
		else
			xml += "Data Found";
		xml += "</status>";
		xml += "</response>";
		stmt1.close();
		rs_loc.close();
		return xml;
	}
	public void conClose(Connection con) 
	{
		try {
			con.close();
		} catch (Exception e) {
		}  
	}
	public void testQry(String s) 
	{
		System.out.println(s);
	}

	public String isNull(String input, int type) 
	{
		if (type == 1) {
			if (input == null || input == "'null'" || input.equals(""))
				input = "0";
			if (input.equals("")   || input == "'null'" ||input.equals(""))
				input = "0";
		} else if (type == 2) {
			if (input == null || input == "'null'" || input.equals("") || input.equals("0"))
				input = "-";
			if (input.equals("") || input == "" || input == null)
				input = "-";
		} else if (type == 3) { 
			if (input == null || input == "'null'")
				input = "";
			if (input.equals("") || input == "" || input == null || input.equals("0"))
				input = "";
		} else if (type == 4) {
			if (input == null || input == "'null'" || input.equals("0"))
				input = ".";
			if (input.equals("") || input == "" || input == null)
				input = ".";
		}
		return input;
	}

	public String genReport(String qry, String table_header,
			String table_column, String property, String table_heading,
			String table_td_set) throws Exception 
	{
		String s = "";    
		System.out.println("DCB->Controller->genReport");
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
	 	System.out.println("DCB->Controller->genReport");
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);		
		//System.out.println(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
 		// html+="<tr><td colspan='"+(table_header_f.length+1)+"'>"+table_heading+"</td></tr>";
		html += "<tr class='tdH'>";
		html += "<td class='tdText' width='5%' >Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) 
		{
			html += " <td class='tdText' align=center >" + table_header_f[i] + "</td>";			 
		}		 
		html += "</tr>";
		int row = 0;
		while (rs_loc.next()) 
		{
			row++;
			html += "<tr><td class='tdText' width='5%'>" + (row) + "</td>";
			for (int i = 0; i < table_header_f.length; i++) 
			{
			html += "<td class='tdText' " +  obj_loc.isNull(table_td_set_f[i],1) + ">"
				 + obj_loc.isNull(rs_loc.getString(table_column_f[i]),3) + "</td>";
			}
			html += "</tr>";
		} 		 
		html += "</table>";
		rs_loc.close();
		con_loc.close();
		return html;
	}
	public String genReport(String qry, String table_header,
			String table_column, String property, String table_heading,
			String table_td_set,int control_type,int position,String control_name) throws Exception {
		String s = "";
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
	//	System.out.println("DCB->Controller->genReport");
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
 		// html+="<tr><td colspan='"+(table_header_f.length+1)+"'>"+table_heading+"</td></tr>";
		html += "<tr class='tdH'>";
		html += "<td class='tdText' width='5%' >Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) 
		{
			html += " <td class='tdText' align=center >" + table_header_f[i] + "</td>"; 
		}		 
		html += "</tr>";
		int row = 0;
		//System.out.println(table_td_set_f.length);
		while (rs_loc.next()) 
		{
			row++;
			html += "<tr><td class='tdText' width='5%'>" + (row) + "</td>";
			for (int i = 0; i < table_td_set_f.length; i++) 
			{
				//System.out.println(i +"" + position);				
				html += "<td class='tdText' " +  obj_loc.isNull(table_td_set_f[i],1) + ">"+obj_loc.isNull(rs_loc.getString(table_column_f[i]),3) + "</td>";
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
	public String month_val(String no) 
	{
		String[] cmonth = { "Select", "January", "February", "March", "April",
							"May", "June", "July", "August", "September", "October", "November", "December" };
		return cmonth[Integer.parseInt(no)];
	}  
	public String month_combo(String name,String fun,String defaultvalue) 
	{   
		String[] cmonth = { "Select", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
		String res="<select name="+name+" id="+name+" "+fun+">";
		for(int i=0;i<cmonth.length;i++)    
		{    
			if (defaultvalue.equalsIgnoreCase(Integer.toString(i)))
			res+="<option value='"+i+"' selected>"+cmonth[i]+"</option>";
			else
			res+="<option value='"+i+"'>"+cmonth[i]+"</option>";
		}  
		res+="</select>";    
		return res;
	}
	public String year_combo(String name,String fun,String defaultvalue) 
	{   
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String[] cmonth = { "Select", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
		String res="<select name="+name+" id="+name+" "+fun+">";
		res+="<option value='0'>Select Year</option>";		
		for(int i=year-1;i<year+1;i++)
		{       
			if (defaultvalue.equalsIgnoreCase(Integer.toString(i)))
			res+="<option value='"+i+"' selected>"+i+"</option>";		  	
			else
			res+="<option value='"+i+"'>"+i+"</option>";
				
		}  
		res+="</select>";    
		return res;
	}
	public String combo_str(String table, String text, String value,
			String cond, String id, String attributes) throws Exception

	{
		String combo = "<select id='" + id + "' name='" + id + "' "+ attributes + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text + "," + value + " from " + table + "  "+ cond;
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>--Select--</option>";

		try {
			while (rs_loc.next()) 
			{
				combo += "<option value='" + rs_loc.getString(value)+"'>"+rs_loc.getString(text) + " </option>";
			}
		} catch (Exception e) {
			System.out.println("Combo ----------------------> " + e);
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		return combo;
	}
	public String combo_str(String table, String text, String value,
			String cond, String id,String initial_value, String attributes) throws Exception
 
	{
		String combo = "<select id='" + id + "' name='" + id + "' "+ attributes + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text + "," + value + " from " + table + "  "+ cond;
		//System.out.println(qry);   
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select</option>";  
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
			System.out.println("Combo ----------------------> " + e);
		}
		
		   
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		con_loc=null;
		return combo;
	}
	
	
	public String combo_str(String table, String text,String combinedcolumn, String value,
			String cond, String id,String initial_value, String attributes) throws Exception

	{
		String combo = "<select id='" + id + "' name='" + id + "' "+ attributes + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry="";
		if (!combinedcolumn.equalsIgnoreCase("")) 
		 qry = "Select " + text + ","+combinedcolumn+"," + value + " from " + table + "  "+ cond;
		else
		qry = "Select " + text + "," + value + " from " + table + "  "+ cond;  
		
		//  System.out.println(qry);
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select</option>";   
		String vl="";
		try {
			while (rs_loc.next()) {    
				vl=rs_loc.getString(value); 
				
				if (initial_value.equalsIgnoreCase(vl))
				{
				combo += "<option value='" +vl + "' selected >"+ rs_loc.getString(text) + " </option>";
				}
				else  
				{
					if (!combinedcolumn.equalsIgnoreCase(""))
					combo += "<option value='" +vl + "'>"+rs_loc.getString(text)+"   ("+rs_loc.getString(combinedcolumn)+")" + " </option>";
					else
					combo += "<option value='" +vl + "'>"+rs_loc.getString(text)+" </option>";	
				}
  
			}
		} catch (Exception e) {
			System.out.println("Combo ----------------------> " + e);
		}
		
		   
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		con_loc=null;
		return combo;
	}
	public String combo_str(String table, String text,
			String cond, String id, String attributes) throws Exception

	{
		String combo = "<select id='" + id + "' name='" + id + "' "+ attributes + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "select " + text + " from " + table + cond;

		//System.out.println(qry);
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>--Select--</option>";

		try {  
			while (rs_loc.next()) {
				String val=rs_loc.getString("SCH_YEAR");
				combo += "<option value='" + val + "'>"+ val + " </option>";

			}
		} catch (Exception e) {
			System.out.println("Combo ----------------------> " + e);
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		return combo;
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
		//System.out.println("path=====> " + f.getAbsolutePath());
		WritableWorkbook wc = Workbook.createWorkbook(f);
		WritableSheet sheet = wc.createSheet("First Sheet", 0);
        WritableFont wf = new WritableFont(WritableFont.ARIAL, 
                9, WritableFont.NO_BOLD);    
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
  
			while (rs_loc.next()) {
				
				 int s=0;
				while (s <table_header_f.length)
				{
				 sheet.addCell(new Label(s, j, rs_loc.getString(table_column_f[s])));
				 s++;
				} 
				j++;
				k++;
			} 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		wc.write();
		wc.close();
		con_loc.close();
	} 
  
	
	public long days(int fyear,int fmonth ,int fday,int lyear,int lmonth,int lday) {
		
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
	public int  rowUpdate(String qry) {
		
		int rows=0;
		
		Connection con_locUpdate = null;
		Controller obj_locUpdate = new Controller();
		try {
			
			  
			con_locUpdate = obj_locUpdate.con();
			PreparedStatement ps_locUpdate = con_locUpdate.prepareStatement(qry);
			rows=ps_locUpdate.executeUpdate();
			ps_locUpdate.close();
			con_locUpdate.commit();
			con_locUpdate.close();
			
			
			 
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		 
		return rows;
		
	}
	public String month_val2(int no) {

		String[] cmonthday = { "0", "31", "28", "31", "30","31", "30", "31", "31", "30", "31", "30", "31" };
		return cmonthday[no];
	}
	public String month_val2(int no,int year) {

		String res="0";
	 
		if (year%4==0)
		{
			String[] cmonthday = { "0", "31", "29", "31", "30","31", "30", "31", "31", "30", "31", "30", "31" };
			res=cmonthday[no];
		}else  
		{
			String[] cmonthday = { "0", "31", "28", "31", "30","31", "30", "31", "31", "30", "31", "30", "31" };
			res=cmonthday[no];
			
		}
	  
		return res; 
	}
	public String generateXML(String tagName,String value,int NullValue,Controller Obj)
	{
		String xml="<"+tagName+"><![CDATA["+ Obj.isNull(value,NullValue)+"]]></"+tagName+">";
		
		return xml;
	} 
	public int prv_month(String sel_month,String sel_year)
	{
		
		int month_process =0;
        if ( Integer.parseInt(sel_month)==1)  
        {
      	  month_process=12;
        } 
        else
        {
      	  month_process=Integer.parseInt(sel_month)-1;
        }
		return month_process;
	}
	public int prv_year(String sel_month,String sel_year)
	{
        int year_process=0;
        if ( Integer.parseInt(sel_month)==1)  
        {
        	year_process=Integer.parseInt(sel_year)-1;
        } 
        else
        {
      	year_process=Integer.parseInt(sel_year);
        }
		return year_process;
	}
	public String recordShow(ArrayList values,Hashtable cond,String table,Connection con) throws SQLException
	{
		  String res="";    
		  int row_count=0;
		  Controller obj_local=new Controller();
		  
		  Iterator ir_value=values.iterator();
		  String qry_format="select ";
		  int v_row=0;
		  while (ir_value.hasNext())
		  {
			  Object column_name=ir_value.next();
			  if (v_row==0)
				  qry_format+=" "+column_name.toString();
			  else
				  qry_format+=" ,"+column_name.toString();
		  }
		  qry_format+=" from "+table;
		  
		  Enumeration ir_cond=cond.keys();
		  int c_row=0;
		  while (ir_cond.hasMoreElements())
		  {
			  Object cond_name=ir_cond.nextElement();
			  if (c_row==0)
				  qry_format+=" where "+cond_name.toString()+"="+cond.get(cond_name);
			  else
				  qry_format+=" ,"+cond_name.toString()+"="+cond.get(cond_name);
		  }
		  PreparedStatement ps=con.prepareStatement(qry_format);
		  
		  ResultSet local_rs=ps.executeQuery();
		  ResultSetMetaData rsmd = local_rs.getMetaData();
		  String ColumnName ="";
		  String ColumnValue="";
		 //  System.out.println(query); 
		  int NumOfCol=rsmd.getColumnCount();
		  while (local_rs.next())
		  {
			  row_count++;
			  for (int i=1;i<=c_row;i++)
			  {
				  ColumnName = rsmd.getColumnName(i);
				  ColumnValue=local_rs.getString(ColumnName);
				  res+=obj_local.generateXML(ColumnName,  obj_local.isNull(ColumnValue, 1), 1, obj_local);
			  }
		  }
		  res+="<row_count>"+row_count+"</row_count>";
		  local_rs=null;
		  obj_local=null;
		  return res;
	}
	public int recordSave(Hashtable values,String table,Connection con) throws SQLException
	{
		int row=0;
		Enumeration  er=values.keys();
		
		String key="";
		String qry="insert into "+table;
		String column=" ( ";
		Object  pr_s=null;
		String value =" values ( ";
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
		String value ="    ";
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
		  PreparedStatement pn_=prcon.prepareStatement(str);
		  return pn_.executeUpdate();
	} 
	public String resultXML(String query,Connection prcon, Controller Obj) throws SQLException
	{
	  String res="";
	  PreparedStatement pn_=prcon.prepareStatement(query);
	  Hashtable hashprocess=new Hashtable();
	  ResultSet local_rs=pn_.executeQuery(); 
	  ResultSetMetaData rsmd = local_rs.getMetaData();
	  String ColumnName ="";
	  String ColumnValue="";
	 //  System.out.println(query); 
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
			  String type=rsmd.getColumnTypeName(i);
			  ColumnValue=local_rs.getString(ColumnName);
			  ColumnValue=Obj.isNull(ColumnValue, 1);
		 	  ColumnValue=ColumnValue.replace('&', '#');
		 	  /*if (type.equalsIgnoreCase("NUMBER"))
			  {
		 		 int rindex=ColumnValue.indexOf('.');
		 		 if (rindex!=0)
		 			ColumnValue=for_mat.format(Double.parseDouble(ColumnValue));
			  }*/
		 	   
			  res+=Obj.generateXML(ColumnName,  ColumnValue, 1, Obj);
			 
		  }
	  }
	  res+="<row_count>"+row_count+"</row_count> ";
	  local_rs.close();
	  pn_.close();
	  
		return res;
	}  
	public void resposeWr(HttpServletResponse response,String resxml) throws IOException 
	{
	  PrintWriter pr = response.getWriter();
	  pr.write(resxml);
	  pr.flush();
	  pr.close();
	}
	
	public String delRecord(String table,String cond,Connection prcon) throws SQLException 
	{
		  String res="";
		  res="<response>";		 
		  System.out.println("delete from "+table + " " + cond );
		  PreparedStatement pn_=prcon.prepareStatement("delete from "+table + " " + cond );
		  int row =pn_.executeUpdate();   
		  pn_=null;
		  res+="<rows>"+row+"</rows></response>";  
		  return res;  
		     
	}
	public int delRecord(String table,String cond,Connection prcon,int flag) throws SQLException 
	{
		  PreparedStatement pn_=prcon.prepareStatement("delete from "+table + " " + cond );
		  int row =pn_.executeUpdate();
		  pn_=null;
		  return row; 
	}
	public String delRecord_err()
	{
		  String res="";
		  res="<response> <rows>0</rows></response>";
		  return res; 
	}
	public String  prvfinyear(String input,int prv) {
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
	
	public int next_year(int year, int month)
	{
		 if (month<=3)
		 {
			 year=year+1;		 
		 }else
		 {
			 year=year;
		 }
		 return year;
	}
	public int april_year(int year, int month)
	{
		 if (month<=3)
		 {
			 year=year-1;		 
		 }else
		 {
			 year=year;
		 }
		 return year;
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
	public String  prvfinyear(String input,int prv,int flag) {
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
	public int daysBetween(Date d1, Date d2){  
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		 }
}
