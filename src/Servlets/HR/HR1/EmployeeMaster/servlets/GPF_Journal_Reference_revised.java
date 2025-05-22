package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class GPF_Journal_Reference
 */
public class GPF_Journal_Reference_revised extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Journal_Reference_revised() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
	  	  System.out.println("welcome to GPF Journal Details Update ");
	  	  
		        try{
		        	LoadDriver driver=new LoadDriver();
		            con=driver.getConnection();

		        	
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
		      
		        String command,acchead_sql=null;
		        command = request.getParameter("command");
		        
		        session =request.getSession(false);
		        String updatedby=(String)session.getAttribute("UserId");
		        long l=System.currentTimeMillis();
		        java.sql.Timestamp ts=new java.sql.Timestamp(l);
		            System.out.println("got");
		            if(command.equalsIgnoreCase("Get")) {
			                   
			          
			            xml="<response><command>get</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("acc_unit").trim());
			            	System.out.print(unitId);
			            	int officeId=0;
			            	//Integer.parseInt(request.getParameter("office_id").trim());
			            	
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	int acchead=Integer.parseInt(request.getParameter("acchead").trim());
			            	System.out.print("achead"+acchead);
			         /*   if(acchead==390302 || acchead==390303)
			            		acchead_sql= "select * from hrm_gpf_subscriptionnew_temp where accounting_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR' ";
			            	else if(acchead==390305)
			            			acchead_sql= "select * from HRM_GPF_WITHDRAWALNEW where acc_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR'";
			            		else if(acchead==391002||acchead==391003||acchead==391302||acchead==391303)
			            				acchead_sql= "select * from hrm_gpf_impound_disbnew where accounting_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR'";
			            	
			            	    System.out.print("acchead"+acchead);
			            	
			            	    ps=con.prepareStatement(acchead_sql);

			                    ps.setInt(1,unitId);
			                   // ps.setInt(2,officeId);
			                    ps.setInt(2,cashMonth);
			                    ps.setInt(3,cashYear);
			                    rs=ps.executeQuery();
			                    System.out.print("finished");
			                    if(rs.next()){
			                   System.out.print("values already freezed");
			                   xml=xml+"<flag>Exist</flag>";
			                    }
			                    else*/
			                    {
			                        System.out.print("else");
			                    ps=con.prepareStatement("select VOUCHER_NO,VOUCHER_DATE,ACCOUNT_HEAD_CODE,SL_NO,CR_DR_INDICATOR,AMOUNT,decode(AC_YEAR,null,0,AC_YEAR) as AC_YEAR  ,decode(ac_month,null,0,ac_month) as ac_month from \n"+
			                    		" ( \n"+
			                    		 " select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,a.VOUCHER_NO,to_char(a.VOUCHER_DATE,'dd/mm/yy') as VOUCHER_DATE,a.ACCOUNT_HEAD_CODE,a.SL_NO,a.CR_DR_INDICATOR,a.AMOUNT from \n"+
			                    		 " ( \n"+
			                    		  "  SELECT TRN.ACCOUNTING_UNIT_ID, \n"+
			                    		    "  TRN.ACCOUNTING_FOR_OFFICE_ID, \n"+
			                    		   "   TRN.CASHBOOK_YEAR, \n"+
			                    		    "  TRN.CASHBOOK_MONTH, \n"+
			                    		    "  TRN.VOUCHER_NO, \n"+
			                    		    "  TRN.SL_NO, \n"+
			                    		     " TRN.ACCOUNT_HEAD_CODE, \n"+
			                    		    "  TRN.CR_DR_INDICATOR, \n"+
			                    		     " TRN.AMOUNT, \n"+
			                    		    "  MST.VOUCHER_DATE \n"+
			                    		  "  FROM \n"+
			                    		   "  FAS_JOURNAL_MASTER MST, \n"+
			                    		   "  FAS_JOURNAL_TRANSACTION TRN \n"+
			                    		  "  where \n"+
			                    		   "   MST.ACCOUNTING_UNIT_ID=TRN.ACCOUNTING_UNIT_ID \n"+
			                    		    "  and MST.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID \n"+
			                    		    "  and MST.CASHBOOK_YEAR=TRN.CASHBOOK_YEAR \n"+
			                    		   "   and MST.CASHBOOK_MONTH=TRN.CASHBOOK_MONTH \n"+
			                    		    "  and MST.VOUCHER_NO=TRN.VOUCHER_NO \n"+
			                    		    " and MST.JOURNAL_TYPE_CODE in ( 53,61) and mst.journal_status='L'\n"+
			                    		    " and TRN.accounting_unit_id=? \n"+
			                    		       " and TRN.cashbook_month=? \n"+
			                    		    " and TRN.cashbook_year=? \n"+
			                    		  //  " and TRN.account_head_code in(390302,391502,390303,390305,391503,391002,391003,391302,391303)  \n"+ 
			                    		    " and Trn.account_head_code in(?)"+
			                    		"  )a \n"+
			                    		"  where VOUCHER_NO not in \n"+
			                    		 " ( \n"+
			                    		  "    select VOUCHER_NO from hrm_gpf_jrnl_adj_trn1 where accounting_unit_id=a.accounting_unit_id and cashbook_year=a.cashbook_year and cashbook_month=a.cashbook_month \n"+
			                    		  "    and voucher_no=a.voucher_no and jrnl_trn_slno=a.sl_no and VALIDATED is not null \n"+
			                    		 " ) \n"+
			                    		" ) aa left outer join \n"+
			                    		" ( \n"+
			                    		 " select VOUCHER_NO as vno,JRNL_TRN_SLNO,decode(AC_YEAR,null,0,AC_YEAR) as AC_YEAR,decode(AC_MONTH,null,0,AC_MONTH) as AC_MONTH from HRM_GPF_JRNL_ADJ_TRN1 where accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n"+
			                    		" )bb on aa.VOUCHER_NO=bb.vno and aa.SL_NO=bb.JRNL_TRN_SLNO order by VOUCHER_NO,SL_NO ");
			                    					                 		  
			                    		
			                    					                    ps.setInt(1,unitId);
			                    					                   // ps.setInt(2,officeId);
			                    					                    ps.setInt(2,cashMonth);
			                    					                    ps.setInt(3,cashYear);
			                    					                    ps.setInt(5,unitId);
			                    					                   // ps.setInt(6,officeId);
			                    					                    ps.setInt(6,cashMonth);
			                    					                    ps.setInt(7,cashYear);
			                    					                    ps.setInt(4,acchead);
			                    			                 		               		                   		                    
			                    					                    rs=ps.executeQuery();
			                    					                    
			                    					                    if(rs.next()){
			                    					                    xml=xml+"<flag>success</flag>";
			                    					                    }else{
			                    					                    	xml=xml+"<flag>success123</flag>";
			                    					                    }
			                    					                    xml=xml+"<unit>"+unitId+"</unit>";
			                    					                    xml=xml+"<officeid>"+officeId+"</officeid>";
			                    					                    xml=xml+"<cashyear>"+cashYear+"</cashyear>";
			                    					                    xml=xml+"<cashmonth>"+cashMonth+"</cashmonth>";
			                    					                    rs=ps.executeQuery();
			                    				                        while(rs.next()){
			                    				                        	
			                    				                        	int acHeadCode=rs.getInt("ACCOUNT_HEAD_CODE");
			                    				                        	  xml=xml+"<vno>"+rs.getInt("VOUCHER_NO")+"</vno>";
			                    				                        	  xml=xml+"<vdate>"+rs.getString("VOUCHER_DATE")+"</vdate>";
			                    				                        	  xml=xml+"<headcode>"+acHeadCode+"</headcode>";
			                    				                        	  xml=xml+"<sno>"+rs.getInt("SL_NO")+"</sno>";
			                    				                        	  xml=xml+"<crdr>"+rs.getString("CR_DR_INDICATOR")+"</crdr>";
			                    				                        	  xml=xml+"<amount>"+df.format(rs.getFloat("AMOUNT"))+"</amount>";
			                    				                        	  System.out.println("acmonth"+rs.getInt("AC_MONTH"));
			                    				                        	  if(rs.getInt("AC_MONTH")!=0)
			                    				                        	  {
			                    				                        		  xml=xml+"<acmonth>"+rs.getInt("AC_MONTH")+"</acmonth>";  
			                    				                        	  }else{
			                    				                        		  xml=xml+"<acmonth>0</acmonth>";  
			                    				                        	  }
			                    				                        	  System.out.println("acYear"+rs.getInt("AC_YEAR"));
			                    				                        	  if(rs.getInt("AC_YEAR")!=0)
			                    				                        	  {
			                    				                        		  xml=xml+"<acyear>"+rs.getInt("AC_YEAR")+"</acyear>";  
			                    				                        	  }else{
			                    				                        		  xml=xml+"<acyear>0</acyear>";  
			                    				                        	  }
			                    				                        	    
			                    				                       ps1= con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");	  
			                    				                         ps1.setInt(1,acHeadCode);
			                    				                         rs1=ps1.executeQuery();
			                    				                         rs1.next();
			                    				                         xml=xml+"<headdesc>"+rs1.getString("ACCOUNT_HEAD_DESC")+"</headdesc>";   
			                    				                  	 
			                    				                        }
			                    }
			                    				                    
			            }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		            System.out.println(xml);
		        }
		            
		            if(command.equalsIgnoreCase("update")) {
		                   
				          
			            xml="<response><command>update</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("unitid").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	int acHeadCode=Integer.parseInt(request.getParameter("acheadcode").trim());
			            	int voucharNo=Integer.parseInt(request.getParameter("vno").trim());	
			            	if(acHeadCode==390302 || acHeadCode==390303)
			            		acchead_sql= "select * from hrm_gpf_subscriptionnew_temp where accounting_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR' ";
			            	else if(acHeadCode==390305)
			            			acchead_sql= "select * from HRM_GPF_WITHDRAWALNEW where acc_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR'";
			            		else if(acHeadCode==391002||acHeadCode==391003||acHeadCode==391302||acHeadCode==391303)
			            				acchead_sql= "select * from hrm_gpf_impound_disbnew where accounting_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR'";
			            	
			            	    System.out.print("acchead"+acHeadCode);
			            	
			            	    ps=con.prepareStatement(acchead_sql);

			                    ps.setInt(1,unitId);
			                   // ps.setInt(2,officeId);
			                    ps.setInt(2,cashMonth);
			                    ps.setInt(3,cashYear);
			                    rs=ps.executeQuery();
			                    System.out.print("finished");
			                    if(rs.next()){
			                   System.out.print("values already freezed");
			                   xml=xml+"<flag>Exist</flag>";
			                    }
			                    else
			                    {
			            String voucharDate=request.getParameter("vdate");
			           float amount=Float.parseFloat(request.getParameter("trnamount").trim());	
			            	
			                    ps=con.prepareStatement("update HRM_GPF_JRNL_ADJ_TRN1 set VOUCHER_NO=?,VOUCHER_DATE=to_date(?,'dd-mm-yyyy') where ACCOUNTING_UNIT_ID=? and JRNL_TOBE_MONTH=? and JRNL_TOBE_YEAR=? and AC_HEAD_CODE=? and ACCOUNTING_FOR_OFFICE_ID=? and DIFFERENCE_VALUE=?");
			                    ps.setInt(1,voucharNo);
			                    ps.setString(2,voucharDate);
			                   
			                    ps.setInt(3,unitId);
			                    ps.setInt(4,cashMonth);
			                    ps.setInt(5,cashYear);
			                    ps.setInt(6,acHeadCode);
			                    ps.setInt(7,officeId);    
			                    ps.setFloat(8,amount);    
			                    rs=ps.executeQuery();
			                    
			                    xml=xml+"<flag>success</flag>";  
			                    }
			            }catch(Exception e){
			            	System.out.println(e);
			            }
			            xml=xml+"</response>";
		            }
			                    
		            
		            
		            
		            
		            out.print(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String strCommand="";
	        Connection con=null;        
	        PreparedStatement ps=null,ps1=null,ps2=null,ps4=null;     
	        Statement st=null;
	        ResultSet rs=null,result=null,rs2=null;
	        //-----------------------------------------------------------------------------------------------
	        PrintWriter out=response.getWriter();
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
	             //        ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
	                String CONTENT_TYPE = "text/xml; charset=windows-1252";
	                response.setContentType(CONTENT_TYPE);
	                System.out.print("add--------->");
	                String xml="<response>";
                    // changes here
	                String update_user=(String)session.getAttribute("UserId");
	                long l=System.currentTimeMillis();
	                Timestamp ts=new Timestamp(l);
	                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                 
	                
	                int cmbAcc_UnitCode=0;
	                int acchead=0;
	                int cashYear=0;
	                int cashMonth=0;
	                int office_id=0;
	                int accUnitId=0;
	            
	                try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("unit_name"));}
	                catch(NumberFormatException e){System.out.println("exception in cmbAcc_UnitCode"+e );}
	                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	                
	                try{acchead=Integer.parseInt(request.getParameter("acchead"));}
	                catch(NumberFormatException e){System.out.println("exception in acchead"+e );}
	                System.out.println("acchead "+cmbAcc_UnitCode);
	                
	             
	                
	                
	                try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
	                catch(NumberFormatException e){System.out.println("exception in txtCB_Year"+e );}
	               
	                
	                try{cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
	                catch(NumberFormatException e){System.out.println("exception in txtCB_Month "+e );}
	               
	                System.out.print("after try--------->");
	                try {
						ps=con.prepareStatement("select accounting_unit_id,accounting_for_office_id from FAS_MST_ACCT_UNIT_OFFICES where accounting_unit_id=?");
						 ps.setInt(1, cmbAcc_UnitCode);
			                rs=ps.executeQuery();
			                if (rs.next()){
			                	office_id=rs.getInt("accounting_for_office_id");
			                	
			                }
			                
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	               
	                try{
	                System.out.print("after try--------->");
	                String voucher1=request.getParameter("vocherno");  
	                String vDate1=request.getParameter("vdate");  
	                String headCode1=request.getParameter("headcode");  
	                String sNo1=request.getParameter("serialno");  
	                String cRDR1=request.getParameter("crdr");  
	                String amount1=request.getParameter("amount");  
	                String adjYear1=request.getParameter("adjyear");  
	                String adjMonth1=request.getParameter("adjmonth"); 
	               
	                String[] voucher= voucher1.split(",");
	                String[] vDate= vDate1.split(",");
	                String[] headCode= headCode1.split(",");
	                String[] sNo= sNo1.split(",");
	                String[] cRDR= cRDR1.split(",");
	                String[] amount= amount1.split(","); 
	                String[] adjYear= adjYear1.split(",");
	                String[] adjMonth= adjMonth1.split(",");
	              

	                con.setAutoCommit(false);
	                System.out.print("after try--dfdf-------for--->");
	                int vocherNo=0;
	                String date="";
	                int acHeadCode=0;
	                int serialNo=0;
	                String cRorDR="";
	                float trnamount=0;
	                int adjustYear=0;
	                int adjustMonth=0;
	                int totalCount=0;
	                int insertflag=0;
	                boolean freezcheck=true;
	                String freezCheckMsg="";
	                System.out.println(voucher.length);
	                //check for validation check starts
	                for(int i=0;i<voucher.length;i++)
	                {
	                	try{
		                	if((adjYear[i]!=null || !adjYear[i].equalsIgnoreCase("")|| adjYear[i].length()>0))
		                		 adjustYear=Integer.parseInt(adjYear[i]);	
		                		
		                	else
		                	{
		                		adjustYear=0;
		                		insertflag=1;
		                	}
		                		 
		                		
		                	if(adjMonth[i]!=null || !adjMonth[i].equalsIgnoreCase("") || adjMonth[i].length()>0)
		                		
		                	   	adjustMonth=Integer.parseInt(adjMonth[i]);
		                	else	
		                		{
		                		adjustMonth=0;
		                		insertflag=1;
		                		}
		                	System.out.println("after integer convention---->");
		                	}
		                	catch(Exception e)
		                	{
		                		System.out.print("integer error"+e);
		                	}	
		                	
		                	String acchead_sql="";
		                	   if(acchead==390302 || acchead==390303)
				            		acchead_sql= "select * from hrm_gpf_subscriptionnew_temp where accounting_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR' ";
				            	else if(acchead==390305 || acchead==391503)
				            			acchead_sql= "select * from HRM_GPF_WITHDRAWALNEW where acc_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR'";
				            		else if(acchead==391002||acchead==391003||acchead==391302||acchead==391303)
				            				acchead_sql= "select * from hrm_gpf_impound_disbnew where accounting_unit_id=? and ac_month=? and ac_year=? and process_flow_id='FR'";
				            	
				            	    System.out.print("acchead"+acchead);
				            	
				            	    ps=con.prepareStatement(acchead_sql);

				                    ps.setInt(1,cmbAcc_UnitCode);
				                   // ps.setInt(2,officeId);
				                    ps.setInt(2,adjustMonth);
				                    ps.setInt(3,adjustYear);
				                    rs=ps.executeQuery();
				                    System.out.print("finished");
				                    if(rs.next()){
				                   System.out.print("values already freezed");
				                 //  xml=xml+"<flag>Exist</flag>";
				                   freezCheckMsg=freezCheckMsg+"AdjustmentMonth/year -"+adjustMonth+"/"+adjustYear+" , ";
				                   freezcheck=false;
				                    }
	                	
	                }
	                
	                
	                //validation check ends
	                if(freezcheck)
	                {
	                for(int i=0;i<voucher.length;i++)
	                {
	                	
	                	vocherNo=Integer.parseInt(voucher[i]);
	                	date=vDate[i];
	                	acHeadCode=Integer.parseInt(headCode[i]);
	                	serialNo=Integer.parseInt(sNo[i]);
	                	cRorDR=cRDR[i];
	                	trnamount=Float.parseFloat(amount[i]);
	                	try{
	                	if((adjYear[i]!=null || !adjYear[i].equalsIgnoreCase("")|| adjYear[i].length()>0))
	                		 adjustYear=Integer.parseInt(adjYear[i]);	
	                		
	                	else
	                	{
	                		adjustYear=0;
	                		insertflag=1;
	                	}
	                		 
	                		
	                	if(adjMonth[i]!=null || !adjMonth[i].equalsIgnoreCase("") || adjMonth[i].length()>0)
	                		
	                	   	adjustMonth=Integer.parseInt(adjMonth[i]);
	                	else	
	                		{
	                		adjustMonth=0;
	                		insertflag=1;
	                		}
	                	System.out.println("after integer convention---->");
	                	}
	                	catch(Exception e)
	                	{
	                		System.out.print("integer error"+e);
	                	}
	                	try{
	                	/*System.out.println("vocherNo"+vocherNo);
	                	System.out.println("date"+date);
	                	System.out.println("acHeadCode"+acHeadCode);
	                	System.out.println("serialNo"+serialNo);
	                	System.out.println("cRorDR"+cRorDR);
	                	System.out.println("adjustYear"+adjustYear);
	                	System.out.println("adjustMonth"+adjustMonth);*/
	                	totalCount++;
	                	
	                	  ps=con.prepareStatement("delete from HRM_GPF_JRNL_ADJ_TRN1 where ACCOUNTING_UNIT_ID=?   and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and JRNL_TRN_SLNO=? and AC_HEAD_CODE=? ");
	                	  ps.setInt(1, cmbAcc_UnitCode);
		                 ps.setInt(2, cashYear);
		                	ps.setInt(3, cashMonth);
		                	ps.setInt(4, vocherNo);
		                	ps.setInt(5, serialNo);
		                    ps.setInt(6,acHeadCode);
		                	ps.execute();
	                	
	                	
	                	
	                	System.out.println("before Add");
	                	
	                ps=con.prepareStatement("insert into HRM_GPF_JRNL_ADJ_TRN1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JRNL_TRN_SLNO,AC_HEAD_CODE,VOUCHER_DATE,CR_DR_INDICATOR,AMOUNT,AC_YEAR,AC_MONTH,JOURNAL_STATUS,UPDATED_BY,UPDATED_DATE)values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?)");
	                	ps.setInt(1, cmbAcc_UnitCode);
	                	ps.setInt(2,office_id);
	                	ps.setInt(3, cashYear);
	                	ps.setInt(4, cashMonth);
	                	ps.setInt(5, vocherNo);
	                	ps.setInt(6, serialNo);
	                	ps.setInt(7, acHeadCode);
	                	ps.setString(8, date);
	                	ps.setString(9, cRorDR);
	                	ps.setFloat(10, trnamount);
	                	ps.setInt(11, adjustYear);
	                	ps.setInt(12, adjustMonth);
	                	ps.setString(13, "L");
	                	ps.setString(14, update_user);
	                	ps.setTimestamp(15, ts);
	                	             	
	                	
	                	ps.execute();
	                	System.out.println("before Add");
	                	
	                		}catch(Exception e){
	                			e.printStackTrace();
	                			System.out.println("Insert Skip");
	                			}
	                	
	                	
	                	
	                }
	                
	                
	                con.commit();
	              //  sendMessage(response,""+totalCount+" Records Inserted Into Database Successfully ","ok");   
	                xml=xml+"<status>Insert</status>";
	                xml=xml+"<count>"+totalCount+"</count>";
	                }
	                else
	                {
	                	 xml=xml+"<status>"+freezCheckMsg+"</status>";
	                	//sendMessage(response,""+totalCount+" Records Inserted Into Database Successfully ","ok");   
	                }
	                
	                }catch(Exception e){
	                	System.out.println("GPF-Journal Reference"+e);
	                	e.printStackTrace();
	                	}
	                xml=xml+"</response>";
	                System.out.println(xml);
	                out.println(xml);
	                return;
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
