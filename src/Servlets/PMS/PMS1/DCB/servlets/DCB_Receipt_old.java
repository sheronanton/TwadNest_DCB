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
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class DCB_Receipt
 */
public class DCB_Receipt_old extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DCB_Receipt_old() {
        super();
        //   Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
	  	  System.out.println("welcome to DCB Receipt Details");
	         String new_cond=Controller.new_cond;
	 		  String meter_status=Controller.meter_status;

		        try{
		        	 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		             String ConnectionString="";

		             String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
		             String strdsn=rs1.getString("Config.DSN");
		             String strhostname=rs1.getString("Config.HOST_NAME");
		             String strportno=rs1.getString("Config.PORT_NUMBER");
		             String strsid=rs1.getString("Config.SID");
		             String strdbusername=rs1.getString("Config.USER_NAME");
		             String strdbpassword=rs1.getString("Config.PASSWORD");

		         //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

		              Class.forName(strDriver.trim());
		              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		        	
		        }
		        catch(Exception e){
		        	System.out.println("Exception in opening connection :"+e); 
		        }
		        ResultSet rs=null,rs1=null,rs2=null;
		        CallableStatement cs=null;
		        PreparedStatement ps=null,ps1=null,ps2=null;
		        String xml="",sql="";
		        DecimalFormat df=new DecimalFormat("#0.00");
		      
		        response.setContentType(CONTENT_TYPE);
		        PrintWriter out = response.getWriter();
		        response.setHeader("Cache-Control","no-cache");  
		        HttpSession session=request.getSession(false);
		        try
		        {
		                if(session==null)
		                {
		                        xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
		                        out.println(xml);
		                    //    System.out.println(xml);
		                        out.close();
		                        return;

		                    }
		                    //System.out.println(session);

		        }catch(Exception e)
		        {
		                //System.out.println("Redirect Error :"+e);
		        }
		        System.out.println("java");
		        String command;
		        command = request.getParameter("command");
		        
		        session =request.getSession(false);
		        String updatedby=(String)session.getAttribute("UserId");
		        long l=System.currentTimeMillis();
		        java.sql.Timestamp ts=new java.sql.Timestamp(l);
		            System.out.println("got");
		            if(command.equalsIgnoreCase("Get")) {
			                   
			          
			            xml="<response><command>get</command>";
			            try{
			                   
			            	int typeId=Integer.parseInt(request.getParameter("beneficerytypeid").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	String str=" SELECT  "+
		            		  "  BEN.BENEFICIARY_SNO, "+  
		            		  "  BEN.BENEFICIARY_NAME, "+
		            		  "  BL.BLOCK_NAME "+
		            		"  FROM  "+
		            		"  (   "+
		            		"    ( "+
		            		"      SELECT  "+
		            		"    BLOCK_SNO,  "+ 
		            		"    BENEFICIARY_SNO,  "+
		            		"    BENEFICIARY_NAME  "+
		            		"  FROM  "+ 
		            		"    PMS_DCB_MST_BENEFICIARY  "+
		            		"  WHERE "+new_cond+" BENEFICIARY_TYPE_ID=?"+
		            		"   AND OFFICE_ID            =?"+
		            		"  )BEN "+
		            		" left join "+
		            		"  ( "+
		            		"      SELECT "+
		            		   "         BLOCK_SNO, "+
		            		      "      BLOCK_NAME "+
		            		"      FROM "+
		            		"           COM_MST_BLOCKS    "+

		            		"  )BL "+
		            		"  on BL.BLOCK_SNO=BEN.BLOCK_SNO "+
		            		"  )ORDER BY  BLOCK_NAME ,BENEFICIARY_NAME ";
			                  //  ps=con.prepareStatement("SELECT BENEFICIARY_SNO,BENEFICIARY_NAME  FROM PMS_DCB_MST_BENEFICIARY WHERE BENEFICIARY_TYPE_ID=? and OFFICE_ID=? order by BENEFICIARY_NAME");
			            	  ps=con.prepareStatement(str);
			                    ps.setInt(1,typeId);
			                    ps.setInt(2,officeId);
			                  	                 		               		                   		                    
			                    rs=ps.executeQuery();
			                    xml=xml+"<flag>success</flag>";
			                    
			                    while(rs.next()){
			                    
			                    xml=xml+"<benefi_sno>"+rs.getInt("BENEFICIARY_SNO")+"</benefi_sno>";
			                    
			                    if (typeId==6)
			                    {
			                    xml=xml+"<benefi_name>"+rs.getString("BLOCK_NAME")+"--"+rs.getString("BENEFICIARY_NAME")+"</benefi_name>";
			                    }
			                    else
			                    {
			                    	xml=xml+"<benefi_name>"+rs.getString("BENEFICIARY_NAME")+"</benefi_name>";
			                    }
				                    
		                        }
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		         
		            } 
		            
		            if(command.equalsIgnoreCase("Getsl")) {
		                   
				          
			            xml="<response><command>Getsl</command>";
			            try{
			                   
			            	int typeId=Integer.parseInt(request.getParameter("beneficerytypeid").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	
			            	
			            		
			            	
			                     ps=con.prepareStatement("SELECT BENEFICIARY_SNO,BENEFICIARY_NAME  FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+" BENEFICIARY_TYPE_ID=? and OFFICE_ID=?");
			                  
			                    ps.setInt(1,typeId);
			                    ps.setInt(2,officeId);
			                  	                 		               		                   		                    
			                    rs=ps.executeQuery();
			                    xml=xml+"<flag>success</flag>";
			                    
			                    while(rs.next()){
			                    
			                    xml=xml+"<benefi_sno>"+rs.getInt("BENEFICIARY_SNO")+"</benefi_sno>";
			                    xml=xml+"<benefi_name>"+rs.getString("BENEFICIARY_NAME")+"</benefi_name>";
			                  
				                    
		                        }
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		            } 
		            
		            
		            
		            else  if(command.equalsIgnoreCase("getachead")) {
			                   
			          
			            xml="<response><command>getachead</command>";
			            try{
			                   
			            	int typeId=Integer.parseInt(request.getParameter("beneficerytypeid").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int schemeNo=Integer.parseInt(request.getParameter("schemesno").trim());
			            
			            	
			            	System.out.println("bid="+typeId);
			            	System.out.println("officeId="+officeId);
			            	System.out.println("SchemeNo="+schemeNo);
			            	
			                    ps=con.prepareStatement("SELECT distinct SCH_TYPE_ID  FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE "+meter_status+" BENEFICIARY_TYPE_ID=? and OFFICE_ID=? and BENEFICIARY_SNO=?");
			                    ps.setInt(1,typeId); 
			                    ps.setInt(2,officeId);
			                    ps.setInt(3,schemeNo);                		               		                   		                    
			                    rs=ps.executeQuery();
			                    
			                    xml=xml+"<flag>success</flag>";
			                    int schemeTypeId=0;
			                    while(rs.next())
			                    {
			                    schemeTypeId=rs.getInt("SCH_TYPE_ID");
			                    			                    
			                    System.out.println("Schemin"+schemeTypeId);
			                    
			                    ps1=con.prepareStatement("SELECT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP WHERE SCH_TYPE_ID=?");
			                    ps1.setInt(1,schemeTypeId); 
			                    rs1=ps1.executeQuery();
			                 
			                    
			                    while(rs1.next()){
			                    
			                    	int acHead=rs1.getInt("ACCOUNT_HEAD_CODE");
			                    	System.out.println("acHead"+acHead);
			                    xml=xml+"<acheadcode>"+acHead+"</acheadcode>";
			                    ps2=con.prepareStatement("SELECT ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS WHERE ACCOUNT_HEAD_CODE=?");
			                    ps2.setInt(1,acHead); 
			                    rs2=ps2.executeQuery();
			                    if(rs2.next());
			                    xml=xml+"<acheaddesc>"+rs2.getString("ACCOUNT_HEAD_DESC")+"</acheaddesc>"; 
		                        }
			                    }
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		            
		           
		            }
		            
		            if(command.equalsIgnoreCase("scheme")) {
		                   
				          
			            xml="<response><command>scheme</command>";
			            try{
			                   
			            	int acHeadCode=Integer.parseInt(request.getParameter("acheadcode").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int benId=Integer.parseInt(request.getParameter("beneficeryid").trim());
			            	int schemeTypeId=0;
			            	
			                    ps=con.prepareStatement("SELECT SCH_TYPE_ID FROM PMS_DCB_RECEIPT_ACCOUNT_MAP WHERE ACCOUNT_HEAD_CODE=?");
			                    ps.setInt(1,acHeadCode);
 			                    rs=ps.executeQuery();
			                    if(rs.next())
			                    {
			                    	schemeTypeId=rs.getInt("SCH_TYPE_ID");	
			                    }
			                    xml=xml+"<flag>success</flag>";
			                    // DATE : 27/12/2010
			                   // ps1=con.prepareStatement("SELECT SCH_NAME,SCH_SNO FROM PMS_SCH_MASTER WHERE SCH_SNO in (select SCHEME_SNO from PMS_DCB_MST_BENEFICIARY_METRE where BENEFICIARY_SNO=?  and SCH_TYPE_ID=? ) and SCH_TYPE_ID=? and OFFICE_ID=? ");
       			                    ps1=con.prepareStatement("SELECT  p.sch_sno, s.sch_name FROM PMS_DCB_DIV_SCHEME_MAP p ,PMS_SCH_MASTER  s WHERE p.sch_sno in (select SCHEME_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO=?  and SCH_TYPE_ID=?) and  p.OFFICE_ID= ? and s.sch_sno= p.sch_sno"); 
			                    
 			                    ps1.setInt(1,benId);
			                    ps1.setInt(2,schemeTypeId);
			                    //ps1.setInt(3,schemeTypeId);
			                    ps1.setInt(3,officeId);  	                
			                    rs1=ps1.executeQuery();
			                    
			                    while(rs1.next()){
			                    
			                    xml=xml+"<scheme_sno>"+rs1.getInt("SCH_SNO")+"</scheme_sno>";
			                    xml=xml+"<scheme_name>"+rs1.getString("SCH_NAME")+"</scheme_name>";
			                  				                    
		                        }
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		            } 
		            
		            if(command.equalsIgnoreCase("getproject")) {
		                   
				          
			            xml="<response><command>getproject</command>";
			            try{
			                   
			            	
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int SchemeNo=Integer.parseInt(request.getParameter("schemeno").trim());
			            	
			            	
			                    ps=con.prepareStatement("SELECT PROJECT_ID FROM PMS_MST_PROJECTS_VIEW WHERE SCH_SNO=? and OFFICE_ID=?");
			                    ps.setInt(1,SchemeNo);
			                    ps.setInt(2,officeId);                 		                    
			                    rs=ps.executeQuery();
			                    if(rs.next())
			                    {
			                    	 xml=xml+"<flag>success</flag>";
			                    	 xml=xml+"<projectid>"+rs.getInt("PROJECT_ID")+"</projectid>";
			                    	 
			                    }else{
			                    	 xml=xml+"<flag>failure</flag>";
			                    	
			                    }
			                   			                   			                   	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure123</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		            } 
		            
		            System.out.println(xml);
		            out.print(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strCommand="";
        Connection con=null;
        ResultSet rs=null;
        CallableStatement cs=null;
        CallableStatement cs1=null;
        PreparedStatement ps=null;
        String xml="";
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
        
        try {
                                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                String ConnectionString="";

                                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                String strdsn=rs1.getString("Config.DSN");
                                String strhostname=rs1.getString("Config.HOST_NAME");
                                String strportno=rs1.getString("Config.PORT_NUMBER");
                                String strsid=rs1.getString("Config.SID");
                                String strdbusername=rs1.getString("Config.USER_NAME");
                                String strdbpassword=rs1.getString("Config.PASSWORD");
                             //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
               }
               catch(Exception e)
                   {
                      System.out.println("Exception in opening connection :"+e);
                      //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

                   }
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        if(strCommand.equalsIgnoreCase("Add")) 
        {
             String CONTENT_TYPE = "text/html; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             xml="<response><command>Add</command>";
            Calendar c;
            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtReceipt_No=0;
            int txtCash_Acc_code=0,Total_TRN_Rec=0;
            double txtAmount=0;
            String  txtReceipt_type="B",txtCR_DB="",txtRecei_from="";
            Date txtCrea_date=null,txtRef_date=null;
            String txtRef_no="",txtRemarks="";
            //String txtD_ODep_Id="";
            //int txtD_ODep_OffId=0;
            int cmbMas_SL_type=0,cmbMas_SL_Code=0;//,cmbMas_offid=0;                           // changes here
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
                        
             
            String txtRec_Vou_type="",txtMode_of_creat="M",txtCreat_By_Module="BR";
            int txtJournal_code=0;
            Date txtCha_Date=null,txtRec_Vou_date=null;
            int txtBankId=0,txtBranchId=0,txtNo_of_pay_voucher=0,txtCha_No=0,txtRec_Vou_No=0;
            long txtBankAccountNo=0;
            
            // Special case this flag iis used to Modify the receipt ,even after Bank Remittance made    ********** important
            String rad_ReClass="";
            rad_ReClass=request.getParameter("rad_ReClass");
            
            String rem_current_month="";
            
            rem_current_month=request.getParameter("rem_current_month");
            System.out.println("remittance in current month   "+ rem_current_month);
            
            if(rad_ReClass.equalsIgnoreCase("Y"))
                txtMode_of_creat="S";
            else
                txtMode_of_creat="M";
            
            try{txtBankId=Integer.parseInt(request.getParameter("txtBankId"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBankId "+txtBankId);
            
            try{txtBranchId=Integer.parseInt(request.getParameter("txtBranchId"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBranchId "+txtBranchId);
            
            try{txtBankAccountNo=Long.parseLong(request.getParameter("txtBankAccountNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBankAccountNo "+txtBankAccountNo);
            
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code "+txtCash_Acc_code);
          
            String[] sd=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
            
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_year "+txtCash_year);
                        
                        try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
            
           /* try{txtCash_year=Integer.parseInt(request.getParameter("txtCash_year"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
            
            try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCash_Month_hid"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);*/
            
            try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtReceipt_No "+txtReceipt_No);
            
            try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);
            
            txtCR_DB=request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB "+txtCR_DB);
            
            try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
            catch(Exception e){System.out.println("exception"+e );}
            
            try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
            catch(Exception e){System.out.println("exception"+e );}
            
            /* try{cmbMas_offid=Integer.parseInt(request.getParameter("cmbMas_offid"));}
            catch(Exception e){System.out.println("exception"+e );}*/
            
            System.out.println("cmbMas_SL and office "+cmbMas_SL_type+" "+cmbMas_SL_Code);//+" "+cmbMas_offid);
            
            txtRecei_from=request.getParameter("txtRecei_from");
            System.out.println("txtRecei_from "+txtRecei_from);
            
            txtRef_no=request.getParameter("txtRef_no");
            System.out.println("txtRef_no "+txtRef_no);
            
            String Ref_date=request.getParameter("txtRef_date");
            System.out.println("txtRef_date "+txtRef_date);
           
            if(!Ref_date.equals(""))                                // if not a empty string, convert to SQL date 
            {
            sd=request.getParameter("txtRef_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtRef_date=new Date(d.getTime());
            }
            System.out.println("after txtRef_date "+txtRef_date);
            txtRemarks=request.getParameter("txtRemarks");
            System.out.println("txtRemarks "+txtRemarks);
          
           //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                    
                      /** Get Receipt Creation Date */          
                        String Receipt_Creation_Date=request.getParameter("txtCrea_date");
                  
                      /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */    
                        Com_CashBook1 cb=new Com_CashBook1();
                      
                      /** Assign Cashbook Year and Month to year_month Variable */
                        String year_month=cb.cb_date(Receipt_Creation_Date).toString();  
                      
                      /** Split Cash Book Year and Month */
                        String []ym=year_month.split("/");
                      
                      /** Assign Year and Month */
                        txtCash_year=Integer.parseInt(ym[0]);
                        txtCash_Month_hid=Integer.parseInt(ym[1]);
                                 
           //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~                               
            
             try 
                 {   
                     con.clearWarnings();
                     con.setAutoCommit(false);
                     System.out.println("inside proc");
                     String No_TRN_Rec[]=request.getParameterValues("H_code");
                     //int NTR=No_TRN_Rec.length;
                      //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                     Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
                     System.out.println(Total_TRN_Rec+" Total_TRN_Rec");
                         cs=con.prepareCall("{call FAS_RECEIPT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                         cs.setInt(1,cmbAcc_UnitCode);
                         cs.setInt(2,txtCash_year);
                         cs.setInt(3,txtCash_Month_hid);
                         cs.setInt(4,txtReceipt_No);
                         cs.setInt(5,cmbOffice_code);
                         cs.setDate(6,txtCrea_date);
                         cs.setString(7,txtReceipt_type);
                         cs.setInt(8,txtCash_Acc_code);
                         cs.setString(9,txtCR_DB);
                         cs.setInt(10,txtBankId);
                         cs.setInt(11,txtBranchId);
                         cs.setLong(12,txtBankAccountNo);
                         cs.setString(13,txtRecei_from);
                         cs.setString(14,txtRef_no);
                         cs.setDate(15,txtRef_date);
                         cs.setInt(16,txtNo_of_pay_voucher);
                         cs.setInt(17,txtCha_No);
                         cs.setDate(18,txtCha_Date);
                         cs.setDouble(19,txtAmount);
                         cs.setInt(20,Total_TRN_Rec);
                         cs.setString(21,txtRec_Vou_type);
                         cs.setInt(22,txtRec_Vou_No);
                         cs.setDate(23,txtRec_Vou_date);
                         cs.setInt(24,txtJournal_code);
                         cs.setString(25,txtRemarks);
                         cs.setString(26,txtMode_of_creat);
                         cs.setString(27,txtCreat_By_Module);
                         cs.setString(28,"insert");                     
                         cs.registerOutParameter(4,java.sql.Types.NUMERIC);
                         cs.registerOutParameter(29,java.sql.Types.NUMERIC); 
                         cs.setInt(30,cmbMas_SL_type);
                         cs.setInt(31,cmbMas_SL_Code);
                         //cs.setInt(32,cmbMas_offid);
                         cs.setString(32,update_user);
                         cs.setTimestamp(33,ts);
                         cs.setString(34,rem_current_month);
                         System.out.println("b4 exe ");
                     cs.execute();
                     txtReceipt_No=cs.getInt(4);
                     int errcode=cs.getInt(29);
                     System.out.println("SQLCODE:::"+errcode);
                     if(errcode!=0)
                     {         
                       System.out.println("The Bank Receipt Creation Failed to INSERT values");
                       sendMessage(response,"The Bank Receipt Creation Failed ","ok");
                       xml=xml+"<flag>failure</flag>";                          
                     }
                     else
                     {  
                         String Ben_code[]=request.getParameterValues("Ben_code");
                         String Grid_H_code[]=request.getParameterValues("H_code");
                         String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
                         String Grid_SL_type[]=request.getParameterValues("SL_type");
                         String Grid_SL_code[]=request.getParameterValues("SL_code_one");
                         String Grid_Cheque_DD[]=request.getParameterValues("Cheque_DD");
                         String Grid_Cheque_DD_NO[]=request.getParameterValues("Cheque_DD_NO");
                         String Grid_Cheque_DD_date[]=request.getParameterValues("Cheque_DD_date");
                         String Grid_Bank_Name[]=request.getParameterValues("Bank_Name");
                         String Grid_Draw_BR[]=request.getParameterValues("Draw_BR");
                         String Grid_Bank_M_Code[]=request.getParameterValues("Bank_M_Code");
                        // String Grid_rec_from[]=request.getParameterValues("rec_from");
                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
                         String Grid_particular[]=request.getParameterValues("particular");
                         
                         String sql="insert into FAS_RECEIPT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                         "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, RECEIPT_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                         "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, RECEIVED_FROM," +
                         "CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, BANK_NAME, DRAWEE_BRANCH, " +
                         "BANK_MICR_CODE, AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,BENEFICIARY_SNO,SINGLE_MULTIPLE ) "+
                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                         
                        
                         int SL_NO=1,cmbSL_type=0,cmbSL_Code=0;
                         String  rad_sub_CR_DR="",txtParticular="",txtCheque_DD="",txtCheque_DD_NO="",txtBank_Name="",txtDraw_BR="",txtBank_M_Code="",txtsub_Recei_from="";
                         Date txtCheque_DD_date=null;
                         String Remittance_Type="";
                         double txtsub_Amount=0;
                          
                         
                         		String sin_mul=request.getParameter("chequerectype"); 
                         
                         
                               ps=con.prepareStatement(sql);
                               for(int k=0;k<Grid_H_code.length;k++) 
                               {
                            	   
                            	   int ben_code=0;
                                   try{txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   rad_sub_CR_DR=Grid_CR_DR_type[k];
                                   
                                   try{
                                	     ben_code =Integer.parseInt(Ben_code[k]);
                                	   System.out.println("ben_code" + ben_code);
                                	   }catch(Exception e){System.out.println("exception in trans "+e);}
                                       
                                     
                                   try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   
                                   
                                   try{ txtCheque_DD=Grid_Cheque_DD[k];
                                       Remittance_Type = txtCheque_DD;
                                   }catch(Exception e){System.out.println(e);}
                                   try{ txtCheque_DD_NO=Grid_Cheque_DD_NO[k];}catch(Exception e){System.out.println(e);}
                                   
                                   
                                   
                                   try
                                   {
                                   if(!Grid_Cheque_DD_date[k].equalsIgnoreCase(""))
                                   {
                                   sd=Grid_Cheque_DD_date[k].split("/");
                                   c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                   d=c.getTime();
                                   txtCheque_DD_date=new Date(d.getTime());
                                   }
                                   }
                                   catch(Exception e) {
                                       System.out.println(e);
                                   }
                                   
                                   txtBank_Name=Grid_Bank_Name[k];
                                   txtDraw_BR=Grid_Draw_BR[k];
                                   txtBank_M_Code=Grid_Bank_M_Code[k];
                                   
                                   System.out.println("Grid_H_code[k] "+Grid_H_code[k]);
                                   System.out.println("Grid_CR_DR_type[k] "+Grid_CR_DR_type[k]);
                                   System.out.println("Grid_SL_type[k]"+Grid_SL_type[k]);
                                   System.out.println("Grid_SL_code[k]"+Grid_SL_code[k]+"from here"+cmbSL_Code);
                                   System.out.println("Grid_Cheque_DD[k]"+Grid_Cheque_DD[k]);
                                   System.out.println("Grid_Cheque_DD_NO[k]"+Grid_Cheque_DD_NO[k]);
                                   System.out.println("txtCheque_DD_date"+txtCheque_DD_date+"date"+Grid_Cheque_DD_date[k]);
                                   System.out.println("Grid_Bank_Name[k]"+Grid_Bank_Name[k]);
                                   System.out.println("Grid_Draw_BR[k]"+Grid_Draw_BR[k]);
                                   System.out.println("Grid_Bank_M_Code[k]"+Grid_Bank_M_Code[k]);

                                   
                                  // txtsub_Recei_from=Grid_rec_from[k];
                                   System.out.println("amount");
                                   txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
                                   txtParticular=Grid_particular[k];
                                   System.out.println("amount");
                                   System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);
                                  // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                                   System.out.println("Grid_particular[k] "+Grid_particular[k]);
                                   
                                   ps.setInt(1,cmbAcc_UnitCode);
                                   ps.setInt(2,cmbOffice_code);
                                   ps.setInt(3,txtCash_year);
                                   ps.setInt(4,txtCash_Month_hid);
                                   ps.setInt(5,txtReceipt_No);
                                   ps.setInt(6,SL_NO);
                                   ps.setInt(7,txtAcc_HeadCode);
                                   ps.setString(8,rad_sub_CR_DR);
                                   ps.setInt(9,cmbSL_type);
                                   ps.setInt(10,cmbSL_Code);
                                   ps.setString(11,txtsub_Recei_from);
                                   //ps.setString(12,DPN_deptId);
                                   //ps.setInt(13,DPN_offId);
                                   ps.setString(12,txtCheque_DD);
                                   ps.setString(13,txtCheque_DD_NO);
                                   ps.setDate(14,txtCheque_DD_date);
                                   ps.setString(15,txtBank_Name);
                                   ps.setString(16,txtDraw_BR);
                                   ps.setString(17,txtBank_M_Code);
                                   ps.setDouble(18,txtsub_Amount);
                                   ps.setString(19,txtParticular);
                                   ps.setString(20,update_user);
                                   ps.setTimestamp(21,ts);
                                   ps.setInt(22,ben_code);
                                   ps.setString(23,sin_mul);
                                   SL_NO++;
                                   ps.executeUpdate(); 
                                   
                                   txtAcc_HeadCode=0;
                                   rad_sub_CR_DR="";
                                   cmbSL_type=0;
                                   cmbSL_Code=0;
                                   txtsub_Recei_from="";
                                   //DPN_deptId="";
                                  // DPN_offId=0;
                                   txtCheque_DD="";
                                   txtCheque_DD_NO="";
                                   txtCheque_DD_date=null;
                                   txtBank_Name="";
                                   txtDraw_BR="";
                                   txtBank_M_Code="";
                                   txtsub_Amount=0;
                                   txtParticular="";
                               }
                               ps.close();
                              System.out.println("b4 commit");
                             
                             
                       /**
                        * Auto Generation of Bank Remittance for ECS Transaction 
                        */
                       
                       System.out.println("Remittance_Type----><--"+Remittance_Type);
                       int  Verified_Authority=0;
                       
                       
                       if (Remittance_Type.equalsIgnoreCase("E"))      
                       {
                       
                         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                         Verified_Authority= empProfile.getEmployeeId();                 
                         System.out.println("Verified_Authority::"+Verified_Authority);
                                       
                        
                         System.out.println("inside E ");
                         cs1=con.prepareCall("{call FAS_ECS_REMITTANCE_PROC(?,?,?,?,?,?,?,?,?,?)}") ;                          
                         cs1.setInt(1,cmbAcc_UnitCode);
                         cs1.setInt(2,cmbOffice_code);
                         cs1.setInt(3,txtCash_year);
                         cs1.setInt(4,txtCash_Month_hid);
                         cs1.setDate(5,txtCrea_date);
                         cs1.setString(6,"B");
                         cs1.setDouble(7,txtAmount);
                         cs1.setInt(8,Verified_Authority);
                         cs1.setString(9,update_user);                         
                         cs1.registerOutParameter(10,java.sql.Types.NUMERIC); 
                         cs1.execute();
                         int err_code=cs1.getInt(10);                         
                         if (err_code == 0)
                         {
                           con.commit();
                           sendMessage(response,"The Bank Receipt Number '"+txtReceipt_No+"' has been Created Successfully ","ok");
                         }
                         else {
                           sendMessage(response,"The Bank Receipt Creation Failed ","ok"); 
                         }
                         
                         
                       }   
                       else {
                           con.commit(); 
                           sendMessage(response,"The Bank Receipt Number '"+txtReceipt_No+"' has been Created Successfully ","ok");
                       }
                      
                     }
                    
                 }
                 
                 catch(Exception e) 
                 {
                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                     sendMessage(response,"The Bank Receipt Creation Failed ","ok");
                     System.out.println("Exception occur due to insert in receipt transaction"+e);
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
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(IOException e)
        {}
    }
}
