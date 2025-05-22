<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DcbLis  | TWAD Nest - Phase II t</title>
<script type="text/javascript"
          src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
<script type="text/javascript">
          function exit()
          {
          		self.close();
          }
          </script>
</head>
<body class="table">
<form name="DCB_List" method="Get">
	
	 <%
		  Connection con=null;
		  ResultSet rs=null;
		  PreparedStatement ps=null;
		  ResultSet results=null;
		  ResultSet results1=null;
		  ResultSet results2=null;
		  try
		  {
		  
		            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		            String ConnectionString="";
		
		            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
		            String strdsn=rs1.getString("Config.DSN");
		            String strhostname=rs1.getString("Config.HOST_NAME");
		            String strportno=rs1.getString("Config.PORT_NUMBER");
		            String strsid=rs1.getString("Config.SID");
		            String strdbusername=rs1.getString("Config.USER_NAME");
		            String strdbpassword=rs1.getString("Config.PASSWORD");
		
		     //       ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		      ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
		
		            Class.forName(strDriver.trim());
		            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		  }
		  catch(Exception e)
		  {
		    	System.out.println("Exception in connection...."+e);
		  }
  		  int  accunitid=0,officeid=0,cashbookyear=0,cashbookmonth=0,schemeId=0;

          try{
        	  accunitid=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	  officeid=Integer.parseInt(request.getParameter("cmbOffice_code"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	  cashbookyear=Integer.parseInt(request.getParameter("cashbook_yr"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	  cashbookmonth=Integer.parseInt(request.getParameter("cashbook_mn"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	  schemeId=Integer.parseInt(request.getParameter("schemeId"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
         
          System.out.println("cmbAcc_UnitCode===>"+accunitid+" cmbOffice_code====>"+officeid+" yr====>"+cashbookyear+" mon====>"+cashbookmonth);
  %>
	
		<table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
              <tr class="tdH">
                    <th>Scheme Type</th>
              	    <th>Account Head Code</th>
                    <th>SL Type</th>
                    <th>SL Code</th>
                    <th>DR Amount</th>  
                    <th>Particulars</th>
                    
              </tr>
              <tbody id="tbody" class="table">
              	<%
              	
              ResultSet rs2=null,rs3=null,rs4=null;
              PreparedStatement ps2=null,ps3=null,ps4=null;
              try
              {
                    System.out.println("inside try");
                    String sql="select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,SCHEME_TYPE_ID,trim(to_char(AMOUNT,'99999999999999.99')) as AMOUNT,REMARKS from PMS_DCB_JOURNAL_DETAILS where ACCOUNTING_UNIT_ID="+accunitid+" and ACCOUNTING_FOR_OFFICE_ID="+officeid+" and CASHBOOK_YEAR="+cashbookyear+" and CASHBOOK_MONTH="+cashbookmonth+" and SCHEME_TYPE_ID="+schemeId+" and CR_DR_INDICATOR='DR'";
                     
                    System.out.println("SQL ::: "+sql);
                    ps2=con.prepareStatement(sql);
                    rs2=ps2.executeQuery();
                    while(rs2.next())
                    {
                        System.out.println("inside while loop");
                        out.println("<tr>"); 
                        
                        ps4=con.prepareStatement("select SCH_TYPE_DESC from PMS_SCH_LKP_TYPE where SCH_TYPE_ID=?");
                        ps4.setInt(1,rs2.getInt("SCHEME_TYPE_ID"));
                        rs4=ps4.executeQuery();
                        if(rs4.next())
                        out.println("<td align='left'>"+rs4.getString("SCH_TYPE_DESC")+"</td>");
                    
                        ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                        ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                        rs3=ps3.executeQuery();
                        if(rs3.next())
                        out.println("<td align='left'>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"-"+rs3.getString("ACCOUNT_HEAD_DESC")+"</td>");
                       
                        if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                        {
                                    System.out.println("take SL DESC");
                                    ps=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                    ps.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                                    rs=ps.executeQuery();
                                    if(rs.next())
                                    out.println("<td align='left'>"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</td>");
                        }
                        else
                            out.println("<td align='left'>"+"   --  "+"</td>");
                     
                        // view in database table
                             if(rs2.getInt("SUB_LEDGER_CODE")!=0)
                            {
                                        ps=con.prepareStatement("select SCH_SHORT_DESC from PMS_SCH_MASTER where SCH_SNO=?");
                                        ps.setInt(1,rs2.getInt("SUB_LEDGER_CODE"));
                                        rs=ps.executeQuery();
                                        if(rs.next())
                                        out.println("<td align='left'>"+rs.getString("SCH_SHORT_DESC")+"</td>");
                            }
                        else
                            out.println("<td align='left'>"+"   --  "+"</td>");
                        
                        out.println("<td align='right'>"+rs2.getString("AMOUNT").trim()+"</td>");
                        out.println("<td align='left'>"+rs2.getString("REMARKS").trim()+"</td></tr>");
                    }
              }
              catch(Exception e)
              {
                    System.out.println("Exception in grid.."+e.getMessage());
              }
          %>
       </tbody>
		</table>
	<table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick="exit()"></input>
            </div>
          </td>
        </tr>
      </table>
</form>
</body>
</html>