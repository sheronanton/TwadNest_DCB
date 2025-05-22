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
public class GPF_Journal_Reference extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Journal_Reference() {
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
			                   
			            	int unitId=Integer.parseInt(request.getParameter("acc_unit").trim());
			            	int officeId=Integer.parseInt(request.getParameter("office_id").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	
			            	
			                  /*  ps=con.prepareStatement("select a.VOUCHER_NO,to_char(a.VOUCHER_DATE,'dd/mm/yy') as VOUCHER_DATE,a.ACCOUNT_HEAD_CODE,a.SL_NO,a.CR_DR_INDICATOR,a.AMOUNT from \n"+	
			        			        "    ( \n"+
						            	 " SELECT TRN.ACCOUNTING_UNIT_ID, \n"+	
						            	"  TRN.ACCOUNTING_FOR_OFFICE_ID, \n"+	
						            	  "  TRN.CASHBOOK_YEAR, \n"+	
						            	 "   TRN.CASHBOOK_MONTH, \n"+	
						            	  "  TRN.VOUCHER_NO, \n"+	
						            	  "  TRN.SL_NO, \n"+	
						            	  "  TRN.ACCOUNT_HEAD_CODE, \n"+	
						            	  "  TRN.CR_DR_INDICATOR, \n"+	
						            	 "   TRN.AMOUNT, \n"+	
						            	"    MST.VOUCHER_DATE \n"+	
						            	"  FROM \n"+	
						            	 "  FAS_JOURNAL_MASTER MST, \n"+	
						            	 "  FAS_JOURNAL_TRANSACTION TRN \n"+	
						            	"  where \n"+	
						            	 "   MST.ACCOUNTING_UNIT_ID=TRN.ACCOUNTING_UNIT_ID \n"+	
						            	  "  and MST.ACCOUNTING_FOR_OFFICE_ID=TRN.ACCOUNTING_FOR_OFFICE_ID \n"+	
						            	  "  and MST.CASHBOOK_YEAR=TRN.CASHBOOK_YEAR  \n"+	
						            	  "  and MST.CASHBOOK_MONTH=TRN.CASHBOOK_MONTH \n"+	
						            	  "  and MST.VOUCHER_NO=TRN.VOUCHER_NO \n"+	
						            	  " and MST.JOURNAL_TYPE_CODE=53 \n"+
						            	   " and TRN.accounting_unit_id=?  \n"+	
						            	  "  and TRN.accounting_for_office_id=? \n"+	
						            	 "   and TRN.cashbook_month=? \n"+	
						            	 "   and TRN.cashbook_year=? \n"+	
						            	 "   and TRN.account_head_code in(390301,390302,390303,390305,390502,390503,391002,391003,391302,391303) \n"+	
						            	" )a full join \n"+	
						            	"( \n"+	
						            	"  SELECT ACCOUNTING_UNIT_ID,  \n"+	
						            	 "   ACCOUNTING_FOR_OFFICE_ID, \n"+	
						            	  "  VOUCHER_NO, \n"+	
						            	 "   CASHBOOK_YEAR, \n"+	
						            	  "  CASHBOOK_MONTH, \n"+	
						            	  "  JRNL_TRN_SLNO \n"+	
						            	"  FROM HRM_GPF_JRNL_ADJ_TRN1 \n"+	
						            	"  where JOURNAL_STATUS='L' \n"+	
						            	 "   and accounting_unit_id=?  \n"+	
						            	 "   and accounting_for_office_id=?  \n"+	
						            	 "   and cashbook_month=? \n"+	
						            	 "   and cashbook_year=? \n"+	
						            	")b on a.VOUCHER_NO=b.VOUCHER_NO and a.SL_NO=b.JRNL_TRN_SLNO \n"+	
						            "	where a.VOUCHER_NO is not null and b.voucher_no is null ");*/
			            	
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
			                    		    " and MST.JOURNAL_TYPE_CODE=53 \n"+
			                    		    " and TRN.accounting_unit_id=? \n"+
			                    		    " and TRN.accounting_for_office_id=? \n"+
			                    		    " and TRN.cashbook_month=? \n"+
			                    		    " and TRN.cashbook_year=? \n"+
			                    		    " and TRN.account_head_code in(390302,391502,390303,390305,391503,391002,391003,391302,391303)  \n"+  
			                    		"  )a \n"+
			                    		"  where VOUCHER_NO not in \n"+
			                    		 " ( \n"+
			                    		  "    select VOUCHER_NO from hrm_gpf_jrnl_adj_trn1 where accounting_unit_id=a.accounting_unit_id and accounting_for_office_id=a.accounting_for_office_id and cashbook_year=a.cashbook_year and cashbook_month=a.cashbook_month \n"+
			                    		  "    and voucher_no=a.voucher_no and jrnl_trn_slno=a.sl_no and VALIDATED is not null \n"+
			                    		 " ) \n"+
			                    		" ) aa left outer join \n"+
			                    		" ( \n"+
			                    		 " select VOUCHER_NO as vno,JRNL_TRN_SLNO,decode(AC_YEAR,null,0,AC_YEAR) as AC_YEAR,decode(AC_MONTH,null,0,AC_MONTH) as AC_MONTH from HRM_GPF_JRNL_ADJ_TRN1 where accounting_unit_id=? and accounting_for_office_id=? and cashbook_month=? and cashbook_year=? \n"+
			                    		" )bb on aa.VOUCHER_NO=bb.vno and aa.SL_NO=bb.JRNL_TRN_SLNO order by VOUCHER_NO,SL_NO ");
			                    					                 		  
			                    		
			                    					                    ps.setInt(1,unitId);
			                    					                    ps.setInt(2,officeId);
			                    					                    ps.setInt(3,cashMonth);
			                    					                    ps.setInt(4,cashYear);
			                    					                    ps.setInt(5,unitId);
			                    					                    ps.setInt(6,officeId);
			                    					                    ps.setInt(7,cashMonth);
			                    					                    ps.setInt(8,cashYear);
			                    					                 
			                    			                 		               		                   		                    
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
			            	
			            String voucharDate=request.getParameter("vdate");
			           float amount=Float.parseFloat(request.getParameter("trnamount").trim());	
			            	
			                    ps=con.prepareStatement("update HRM_GPF_JRNL_ADJ_TRN set VOUCHER_NO=?,VOUCHER_DATE=to_date(?,'dd-mm-yyyy') where ACCOUNTING_UNIT_ID=? and JRNL_TOBE_MONTH=? and JRNL_TOBE_YEAR=? and AC_HEAD_CODE=? and ACCOUNTING_FOR_OFFICE_ID=? and DIFFERENCE_VALUE=?");
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
	                //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
	                
	                
                    // changes here
	                String update_user=(String)session.getAttribute("UserId");
	                long l=System.currentTimeMillis();
	                Timestamp ts=new Timestamp(l);
	                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                 
	                
	                int cmbAcc_UnitCode=0;
	                int cmbOffice_code=0;
	                int cashYear=0;
	                int cashMonth=0;
	                try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	                catch(NumberFormatException e){System.out.println("exception"+e );}
	                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	                
	                try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	                catch(NumberFormatException e){System.out.println("exception"+e );}
	                System.out.println("cmbOffice_code "+cmbOffice_code);
	                
	                try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
	                catch(NumberFormatException e){System.out.println("exception"+e );}
	               
	                
	                try{cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
	                catch(NumberFormatException e){System.out.println("exception"+e );}
	               
	                
	                
	                try{
	                
	                
	                String[] voucher=request.getParameterValues("vocherno");  
	                String[] vDate=request.getParameterValues("vdate");  
	                String[] headCode=request.getParameterValues("headcode");  
	                String[] sNo=request.getParameterValues("serialno");  
	                String[] cRDR=request.getParameterValues("crdr");  
	                String[] amount=request.getParameterValues("amount");  
	                String[] adjYear=request.getParameterValues("adjyear");  
	                String[] adjMonth=request.getParameterValues("adjmonth");  
	                con.setAutoCommit(false);
	                int vocherNo=0;
	                String date="";
	                int acHeadCode=0;
	                int serialNo=0;
	                String cRorDR="";
	                float trnamount=0;
	                int adjustYear=0;
	                int adjustMonth=0;
	                int totalCount=0;
	                for(int i=0;i<voucher.length;i++)
	                {
	                	
	                	vocherNo=Integer.parseInt(voucher[i]);
	                	date=vDate[i];
	                	acHeadCode=Integer.parseInt(headCode[i]);
	                	serialNo=Integer.parseInt(sNo[i]);
	                	cRorDR=cRDR[i];
	                	trnamount=Float.parseFloat(amount[i]);
	                	
	                	if((adjYear[i]!=null || !adjYear[i].equalsIgnoreCase("") || adjYear[i].length()>0) || (adjMonth[i]!=null || !adjMonth[i].equalsIgnoreCase("") || adjMonth[i].length()>0) )
	                	{
	                		try{
	                	adjustYear=Integer.parseInt(adjYear[i]);
	                	adjustMonth=Integer.parseInt(adjMonth[i]);
	                	
	                	/*System.out.println("vocherNo"+vocherNo);
	                	System.out.println("date"+date);
	                	System.out.println("acHeadCode"+acHeadCode);
	                	System.out.println("serialNo"+serialNo);
	                	System.out.println("cRorDR"+cRorDR);
	                	System.out.println("adjustYear"+adjustYear);
	                	System.out.println("adjustMonth"+adjustMonth);*/
	                	totalCount++;
	                	
	                	  ps=con.prepareStatement("delete from HRM_GPF_JRNL_ADJ_TRN1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and JRNL_TRN_SLNO=?");
	                	  ps.setInt(1, cmbAcc_UnitCode);
		                	ps.setInt(2, cmbOffice_code);
		                	ps.setInt(3, cashYear);
		                	ps.setInt(4, cashMonth);
		                	ps.setInt(5, vocherNo);
		                	ps.setInt(6, serialNo);
		                	ps.execute();
	                	
	                	
	                	
	                	
	                ps=con.prepareStatement("insert into HRM_GPF_JRNL_ADJ_TRN1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JRNL_TRN_SLNO,AC_HEAD_CODE,VOUCHER_DATE,CR_DR_INDICATOR,AMOUNT,AC_YEAR,AC_MONTH,JOURNAL_STATUS,UPDATED_BY,UPDATED_DATE)values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?)");
	                	ps.setInt(1, cmbAcc_UnitCode);
	                	ps.setInt(2, cmbOffice_code);
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
	                		}catch(Exception e){System.out.println("Insert Skip");}
	                	}
	                	
	                	
	                }
	                
	                
	                con.commit();
	                sendMessage(response,""+totalCount+" Records Inserted Into Database Successfully ","ok");   
	                
	                
	                
	                }catch(Exception e){System.out.println("GPF-Journal Reference"+e);}
	                
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
