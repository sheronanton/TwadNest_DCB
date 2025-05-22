package Servlets.HR.HR1.EmployeeMaster.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class GPF_Journal_Creation extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
    {
    	System.out.println("inside servlet");
    	/**
	       * Set Content Type 
	      */
	      PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	     String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
	     
	      /**
	       * Session Checking 
	      */
	      HttpSession session=request.getSession(false);
	      try
	      {	           
                    if(session==null)
                    {
                             System.out.println(request.getContextPath()+"/index.jsp");
                             response.sendRedirect(request.getContextPath()+"/index.jsp");
                             return;
                    }
                    System.out.println(session);
	               
	      }catch(Exception e)
	      {
                    System.out.println("Redirect Error :"+e);
	      }
	      
	     
	      /**
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null;        
	      ResultSet rs2=null;
	      String sql="";
	      String txtFrom_date=null,txtTo_date=null;
	      /**
	       * Database Connection 
	      */
	      try
	      {
	    	  LoadDriver driver=new LoadDriver();
	            con=driver.getConnection();

            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cashbookyear=0,cashbookmonth=0,headCode=0;
            String xml=null,cmd="",type="",dcbdate="";    
            Calendar c;
            Date gpfdate=null;
     
            try{cmd=request.getParameter("Command");
            System.out.println("command"+cmd);
            }
            catch(Exception e){System.out.println(e);}
 
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("unitcode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("unitid "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("officecode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("officeid "+cmbOffice_code);
            
            String[] sd1=request.getParameter("vdate").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
            java.util.Date d1=c.getTime();
            gpfdate=new Date(d1.getTime());
        
            try{cashbookyear=Integer.parseInt(sd1[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cashbookyear "+cashbookyear);
            
            try{cashbookmonth=Integer.parseInt(sd1[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cashbookmonth "+cashbookmonth);
                       
            xml="<response>";
            if(cmd.equalsIgnoreCase("detload"))
            {  
            	System.out.println("load in servlet");
            	xml=xml+"<command>headAll</command>";
                
                sql="select AC_HEAD_CODE,AC_YEAR,AC_MONTH,DIFFERENCE_VALUE,REMARKS,VOUCHER_NO from hrm_gpf_jrnl_adj_trn where VOUCHER_NO is null or VOUCHER_NO=0 and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and JRNL_TOBE_YEAR=? and JRNL_TOBE_MONTH=? ORDER BY AC_MONTH DESC";
                 
           //	sql="select AC_HEAD_CODE,AC_YEAR,AC_MONTH,DIFFERENCE_VALUE,REMARKS from hrm_gpf_jrnl_adj_trn where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and JRNL_TOBE_YEAR=? and JRNL_TOBE_MONTH=? and VOUCHER_NO=0 ORDER BY AC_HEAD_CODE DESC";
                System.out.println(" SQL :: "+sql);
            	try
                {
                 ps2=con.prepareStatement(sql);
                 ps2.setInt(1,cmbAcc_UnitCode);
                 ps2.setInt(2,cmbOffice_code);
                 ps2.setInt(3,cashbookyear);
                 ps2.setInt(4,cashbookmonth);
                 rs2=ps2.executeQuery(); 
                 System.out.println("rs2"+rs2);
                 xml+="<flag>success</flag>";
                 if(rs2.next()) 
                    {
                         xml+="<acheadcode>"+rs2.getInt("AC_HEAD_CODE")+"</acheadcode>";
                         xml+="<acyear>"+rs2.getInt("AC_YEAR")+"</acyear>";
                         xml+="<acmonth>"+rs2.getInt("AC_MONTH")+"</acmonth>";
                         xml+="<difference>"+rs2.getInt("DIFFERENCE_VALUE")+"</difference>";
                         xml+="<remarks>"+rs2.getString("REMARKS")+"</remarks>";
                       
                    }					              
               }
                catch(Exception e) 
                {
                         System.out.println("Exception in loading journal type..."+e.getMessage());
                         xml+="<flag>"+e.getMessage()+"</flag>";
                }       
            }
         
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    }
    
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        System.out.println("dopost method");
        String strCommand="";
        Connection con=null;        
        PreparedStatement ps=null,ps1=null,ps2=null;     
        Statement st=null;
        ResultSet rs=null,result=null,rs2=null;
        //-----------------------------------------------------------------------------------------------
        
        HttpSession session=request.getSession(false);
        try
        {
           
            if(session==null)
            {
                     System.out.println(request.getContextPath()+"/index.jsp");
                     response.sendRedirect(request.getContextPath()+"/index.jsp");
                     return;
            }
            System.out.println(session);
               
        }catch(Exception e)
        {
            System.out.println("Redirect Error :"+e);
        } 
        //-----------------------------------------------------------------------------------------------        
                
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
           //          ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                     ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
                     Class.forName(strDriver.trim());
                     con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                     st=con.createStatement();
                 }
                 catch(Exception e)
                 {
                     System.out.println("Exception in opening connection :"+e);
                     //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

                 }
                         
         //-----------------------------------------------------------------------------------------------        
          try
          {        
               strCommand=request.getParameter("Command");
               System.out.println("assign..here command..."+strCommand);
             
          }
          
          catch(Exception e) 
          {
               System.out.println("Exception in assigning..."+e);
          }
          
          //-----------------------------------------------------------------------------------------------
           if(strCommand.equalsIgnoreCase("Add")) 
           {
                String CONTENT_TYPE = "text/html; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
               
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                Calendar c;
                int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;            
                Date voucher_date=null;
                String cr_dr_indicator="",sql="",flag="",particulars="",remarks="",voucher_display="";
                String txtCrea_date=null,financial_year[]=null;
                int account_head_code=0,sub_ledger_code=0,trn_records=0,count=0,trn_count=0,grid_count=0,depriciation_rate=0,cashbook_year=0,cashbook_month=0,cb_ref_no=0,acYear=0,acMonth=0;
                
                String paid_to=""; 
                    int sub_ledger_type=0;
                    double amount=0;
              
                                        // changes here
                String update_user=(String)session.getAttribute("UserId");
                long l=System.currentTimeMillis();
                Timestamp ts=new Timestamp(l);
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                   
                try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                
                try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("cmbOffice_code "+cmbOffice_code);
                              
                String[] sd=request.getParameter("txtCrea_date").split("/");
             
                 
                txtCrea_date=request.getParameter("txtCrea_date");
                System.out.println("txtCrea_date "+txtCrea_date);
                
                System.out.println("b4 getting month and year");
                try{txtCash_year=Integer.parseInt(sd[2]);}
                catch(Exception e){System.out.println("exception"+e );}
                System.out.println("txtCash_year "+txtCash_year);
                
                try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                catch(Exception e){System.out.println("exception"+e );}
                System.out.println("txtCash_Month_hid :::::::::;"+txtCash_Month_hid);
                
               try{acYear=Integer.parseInt(request.getParameter("ACYear"));}
               catch(NumberFormatException e){System.out.println("exception"+e );}
               System.out.println("acYear "+acYear);
               
               try{acMonth=Integer.parseInt(request.getParameter("ACMonth"));}
               catch(NumberFormatException e){System.out.println("exception"+e );}
               System.out.println("acMonth "+acMonth);
               
               String[] hcode=request.getParameterValues("H_code");
               System.out.println("hcode"+hcode.length);
               int trnrecords=0;
               for(int i=0;i<hcode.length;i++)
               {
                   trnrecords++;
                  
               }
               System.out.println("trnrecords"+trnrecords);
               int maxVoucherNo=0;
               try
               {
                        ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
                        ps.setInt(1,cmbAcc_UnitCode);
                        ps.setInt(2,cmbOffice_code);
                        ps.setInt(3,txtCash_year);
                        ps.setInt(4,txtCash_Month_hid);                      
                        rs=ps.executeQuery();
                        if(rs.next()) 
                        {
                                  maxVoucherNo = rs.getInt(1); 
                                  System.out.println("maxVoucherNo"+maxVoucherNo);
                        }
                        maxVoucherNo=maxVoucherNo+1;
                        rs.close();
               }           
               catch(Exception e){System.out.println("exception"+e );}
               System.out.println("maxVoucherNo:::::::::"+maxVoucherNo);
                
                 try {
                     ps.close();
                     con.setAutoCommit(false);
                     System.out.println("be4 master");
                     ps1=con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JOURNAL_TYPE_CODE,TOTAL_TRN_RECORDS,MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,SUB_LEDGER_CODE,DEPRECIATION_RATE)values(?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?)");
                     System.out.println("ps1"+ps1);
                     ps1.setInt(1,cmbAcc_UnitCode);
                     ps1.setInt(2,cmbOffice_code);
                     ps1.setString(3,txtCrea_date);
                     ps1.setInt(4,txtCash_year);
                     ps1.setInt(5,txtCash_Month_hid);  
                     ps1.setInt(6,maxVoucherNo);
                     ps1.setInt(7,73);
                     ps1.setInt(8,trnrecords);
                     ps1.setString(9,"D");
                     ps1.setString(10,"GJV");
                     ps1.setString(11,"L");
                     ps1.setString(12,update_user);
                     ps1.setTimestamp(13,ts);
                     ps1.setInt(14,sub_ledger_code);
                     ps1.setInt(15,depriciation_rate);
                     System.out.println("ps1 ::::::::;;"+ps1);
                     int kk=ps1.executeUpdate();
                     System.out.println("kk executed"+kk);
                     if(kk==0)
                     {         
                                 System.out.println("redirect");                                
                                 sendMessage(response,"Master Creation Failed ","ok");     
                     }
                     else
                     {  
                         System.out.println("inside success");
                         trn_count=0;
                         ps1.close();
                        // con.commit(); datas permanently stored in database
                         String Grid_H_code[]=request.getParameterValues("H_code");
                         String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
                         String Grid_SL_type[]=request.getParameterValues("SL_type");
                         String Grid_SL_code[]=request.getParameterValues("SL_code");                          
                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
                         String adj_year[]=request.getParameterValues("adj_year");                         
                         String adj_month[]=request.getParameterValues("adj_month");                         
                         String Grid_particular[]=request.getParameterValues("sl_particular"); 
                         String bill_nos[]=request.getParameterValues("bill_no1"); 
                         System.out.println("bill_nos[0]"+bill_nos[0]);
                         System.out.println("bill_nos[1]"+bill_nos[1]);
                         String bill_types[]=request.getParameterValues("bill_types"); 
                         String bill_dates[]=request.getParameterValues("bill_dates"); 
                         
                         int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,txtAcc_HeadCode=0,adjyear=0,adjmonth=0,billno=0,billtype=0;
                         String billdate=null;
                         double txtsub_Amount=0.0;
                        try{
                         sql="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,BILL_NO,BILL_TYPE,BILL_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'))";
                            System.out.println("sql"+sql);
                             ps=con.prepareStatement(sql);
                              int inc=0;
                              int serialno=0;
                              inc=trnrecords;
                              
                                for(int k=0;k<Grid_H_code.length;k++) 
                                {
                                     try{
                                       if(inc>1)
                                         {
                                               serialno++;
                                             System.out.println("serialno"+serialno);
                                         }
                                         else
                                         {
                                         System.out.println("else condition 4 serialno");
                                            serialno=1;
                                         }
                                            
                                            cmbSL_type=0;cmbSL_Code=0;ref_num=0;
                                            txtAcc_HeadCode=0;  txtsub_Amount=0; 
                                            ps.setInt(1,cmbAcc_UnitCode);
                                           
                                            ps.setInt(2,cmbOffice_code);
                                            ps.setInt(3,txtCash_year);
                                            ps.setInt(4,txtCash_Month_hid);
                                            ps.setInt(5,maxVoucherNo);
                                            ps.setInt(6,serialno);
                                            txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
                                            ps.setInt(7,txtAcc_HeadCode);
                                            String rad_sub_CR_DR=Grid_CR_DR_type[k];                               
                                            ps.setString(8,rad_sub_CR_DR);   
                                            try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
                                            catch(NumberFormatException e){System.out.println("exception"+e );}
                                            ps.setInt(9,cmbSL_type); 
                                            
                                            try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
                                            catch(NumberFormatException e){System.out.println("exception"+e );}
                                            ps.setInt(10,cmbSL_Code);
                                            
                                            try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
                                            catch(NumberFormatException e){System.out.println("exception"+e );}
                                            ps.setDouble(11,txtsub_Amount);
                                            
                                            ps.setString(12,Grid_particular[k]);
                                            ps.setString(13,update_user);
                                            ps.setTimestamp(14,ts);
                                            ps.setInt(15,cb_ref_no); 
                                            try{adjyear=Integer.parseInt(adj_year[k]);}
                                            catch(NumberFormatException e){System.out.println("exception"+e );}
                                            ps.setInt(16,adjyear);  
                                            
                                            try{adjmonth=Integer.parseInt(adj_month[k]);}
                                            catch(NumberFormatException e){System.out.println("exception"+e );}
                                            ps.setInt(17,adjmonth); 
                                            ps.setString(18,bill_nos[k]);
                                            System.out.println("bill_no"+bill_nos[k]);            
                                            ps.setString(19,bill_types[k]);
                                            System.out.println("bill_types"+bill_types[k]);
                                            ps.setString(20,bill_dates[k]);
                                            System.out.println("bill_dates"+bill_dates[k]);
                                           
                                            System.out.println("be4 transaction");
                                            int kkkk=ps.executeUpdate();
                                            System.out.println("executeUpdate"+kkkk);
                                            if(kkkk>0)
                                               count++;
                                        }
                                           catch(Exception ssl)
                                           {
                                                    System.out.println("Exception while 2 nd table insert ::: "+ssl.getMessage());
                                           }
                                        
                                       }
                                      if(count==Grid_H_code.length) 
                                      {
                                      System.out.println("count"+count);
                                          flag="success";
                                      }
                                      else
                                          flag="failure";
                        }
                         catch(Exception e)
                         {                                                                      
                                     e.getStackTrace();
                                     con.rollback();
                                     sendMessage(response,"transaction Sl.No. Creation Failed ","ok");     
                                       
                         }
                     }
                 }
                catch(Exception e) {
                System.out.println("exception"+e.getMessage());
                     flag="failure";
                 }
               try {
                   if(flag.equals("success")) 
                   {
                       System.out.println("flag success");
                       ps.close();
                       con.commit();
                       int acheadcode1=0;
                       try{
                           ps2=con.prepareStatement("select AC_HEAD_CODE,AC_YEAR,AC_MONTH from HRM_GPF_JRNL_ADJ_TRN where AC_YEAR=? and AC_MONTH=?");
                           ps2.setInt(1,acYear);
                           ps2.setInt(2,acMonth);
                           result=ps2.executeQuery();
                           System.out.println("result"+result);
                           while(result.next())
                           {
                               acheadcode1 = result.getInt(1); 
                               
                           }
                             
                         }
                       catch(Exception ex){
                           System.out.println("Exception in select query be4 update"+ex.getMessage());
                       }
                       System.out.println("acheadcode1 :::::::::::::::::;"+acheadcode1);
                       System.out.println("maxVoucherNo"+maxVoucherNo);
                       System.out.println("txtCrea_date"+txtCrea_date);
                        
                       ps=con.prepareStatement("update HRM_GPF_JRNL_ADJ_TRN set VOUCHER_NO=?,VOUCHER_DATE=to_date(?,'dd-mm-yy') where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and AC_YEAR=? and AC_MONTH=? and AC_HEAD_CODE=?");
                       ps.setInt(1,maxVoucherNo);
                       ps.setString(2,txtCrea_date);
                       ps.setInt(3,cmbAcc_UnitCode);
                       System.out.println("cmbAcc_UnitCode >>>>> "+cmbAcc_UnitCode);
                       ps.setInt(4,cmbOffice_code);
                       System.out.println("cmbOffice_code >>>>> "+cmbOffice_code);
                       ps.setInt(5,acYear);
                       ps.setInt(6,acMonth);
                       ps.setInt(7,acheadcode1);
                       
                       int kk=ps.executeUpdate();
                       System.out.println("updatedddddddd"+kk);
                       if(kk>0)
                       {
                       
                       sendMessage(response,"The DCB Journal Number "+maxVoucherNo+" has been Created Successfully ","ok");   
                       con.commit();
                       }
                   }
                   else {
                       System.out.println("b4 Rollback");
                       con.rollback();
                       sendMessage(response,"The DCB Journal Number Creation Failed ","ok");        
                   }
               }
               catch(Exception e) 
               {
                   System.out.println("Err in insertion ::: "+e.getMessage());    
               }
               finally
               {
                   System.out.println("done");
                   try{con.setAutoCommit(true);  }catch(SQLException sqle){}
               }
                      
           }  
    }
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
                  System.out.println("sendMessage");
                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                  response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }     
}

	    
	    
           