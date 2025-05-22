package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class GPF_Authorisation_Interest_Notification extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        GpfSettlementNoteModel.openConnection();
    }
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	super.destroy();
    	GpfSettlementNoteModel.closeConnection();
    }
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException {    	 	
    		
    	 	GpfSettlementNoteModel.closeConnection();
    	 	GpfSettlementNoteModel.openConnection();
	        
	        String xml="";
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
	                        System.out.println(xml);
	                        out.close();
	                        return;
	                    }
	                    //System.out.println(session);

	        }catch(Exception e)
	        {
	                //System.out.println("Redirect Error :"+e);
	        }
	        
	        String command="";
	        try {
	        	command = request.getParameter("command");
			} catch (Exception e) {
				// TODO: handle exception
			}
	        boolean gpfValidity=false;
	        
	        session =request.getSession(false);
	        String updatedby=(String)session.getAttribute("UserId");
	        String financeYear="";
			try {
				financeYear=request.getParameter("finance_year").trim();
			} catch (Exception e) {
				// TODO: handle exception
			}
	        long l=System.currentTimeMillis();
	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	            System.out.println("got");
	         if(command.equalsIgnoreCase("Get")) {
	            int gpfNo=0;	 
	            try {
	            	gpfNo = Integer.parseInt(request.getParameter("gpf_no").trim());
	            	gpfValidity=true;
				} catch (Exception e) {
					// TODO: handle exception
					gpfValidity=false;
				}
	            
	            String finalReport="";
	            try {
	            	finalReport=request.getParameter("finalReport").trim();
				} catch (Exception e) {
					// TODO: handle exception
				}
				int aNo=0;	
				try {
					System.out.println("authNo.....="+request.getParameter("auth_no").trim());
					aNo = Integer.parseInt(request.getParameter("auth_no").trim());
					
				} catch (Exception e) {
					// TODO: handle exception
					//e.printStackTrace();
				}
				
	            System.out.println("authNo in GET="+aNo);
	            System.out.println("GpfNo in GET="+gpfNo);
	            xml="<response><command>get</command>";
	            
	            GpfSettlementNoteData gpfData6=null;
		 		int settlementStatus=0;
		 		/*settlementStatus=GpfSettlementNoteModel.checkSettlementGpf(gpfNo);
		 		if(aNo>0){
		 			settlementStatus=GpfSettlementNoteModel.checkSettlementGpf(gpfNo,aNo);	
            	}
	 			if(settlementStatus==2){
	            	gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo);	
	            	if(aNo>0){
	            		gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo,aNo);	
	            	}
	            }
	 			else if(settlementStatus==1){
	            	gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo);	
	            	if(aNo>0){
	            		gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo,aNo);	
	            	}
	            }
	            else if(settlementStatus==0){
	            	gpfData6=new GpfSettlementNoteData();;
	            	gpfData6.setGpf_no(gpfNo);
	            }
	            else if(settlementStatus==3){
	            	gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo);	
	            	if(aNo>0){
	            		gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo,aNo);	
	            	}
	            }
	            else if(settlementStatus==4){
	            	gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo);	
	            	if(aNo>0){
	            		gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo,aNo);	
	            	}
	            }
	 			//Data for final report
	 			boolean gpffinalRecordBool=false;
	 			if(finalReport.equalsIgnoreCase("yes")){
	 				int authNum=0;
	 				authNum=GpfSettlementNoteModel.getLastAuthNoWithFR(gpfNo);
	 				if(authNum==0){
	 					gpffinalRecordBool=false;
	 				}else{
	 					gpffinalRecordBool=true;
	 					if(aNo>0){
		            		gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo,aNo);	
		            	}else{
		            		gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo,authNum);		 					
		            	}
	 					if(GpfSettlementNoteModel.checkGeneratedNote(gpfNo,gpfData6.getAuth_no())==0){
	 						gpfData6=GpfSettlementNoteModel.processCalculation(gpfData6);
	 					}
	 					
	 				}	 				
	 				System.out.println("auth no in final report======="+gpfData6.getAuth_no());
	 				
	 			}*/
	            
	            String temp1=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	            
	            if(temp1.length()!=0){
	            	xml=xml+"<flag>success</flag>";
	            	            	
	            	/*if(finalReport.equalsIgnoreCase("yes")){
	            		if(GpfSettlementNoteModel.checkGeneratedNote(gpfNo,gpfData6.getAuth_no())==1){
	            			xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
		            		xml+=GpfSettlementNoteModel.xmlSettlement(gpfData6);
		            		xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData6);
		            		xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData6);
		            		xml+=GpfSettlementNoteModel.xmlCalculation(gpfData6);
		            		xml=xml+"<noteGenerated>Y</noteGenerated>";
		            		xml=xml+"<status>GpffinalReport</status>";
	            		}else{
	            			if(gpffinalRecordBool==true){
	            				String PFSID="";
	            				PFSID=GpfSettlementNoteModel.getProcessFlowStatusId(gpfData6.getGpf_no(),gpfData6.getAuth_no()).trim();
	            				if(PFSID.equalsIgnoreCase("FR")){
	            					xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	    		            		xml+=GpfSettlementNoteModel.xmlSettlement(gpfData6);
	    		            		xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData6);
	    		            		xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData6);	
	    		            		xml+=GpfSettlementNoteModel.xmlCalculation(gpfData6);
	    		            		xml+=GpfSettlementNoteModel.xmlCalculationCheck(gpfData6);		            		
	    		            		xml=xml+"<noteGenerated>N</noteGenerated>";
	    		            		xml=xml+"<status>GpffinalReport</status>";
	    		            		xml=xml+"<partialValidated>yes</partialValidated>";
	            				}
	            				else {
	            					xml=xml+"<noteGenerated>N</noteGenerated>";
	    		            		xml=xml+"<status>GpffinalReport</status>";
	    		            		xml=xml+"<partialValidated>no</partialValidated>";
	            				}
	            			}else{
	            				xml=xml+"<noteGenerated>N</noteGenerated>";
			            		xml=xml+"<status>GpffinalReport</status>";
			            		xml=xml+"<partialValidated>norecord</partialValidated>";
	            			}
	            			
	            		}	            		
	            	}*/
	            	System.out.println("settlementStatus"+settlementStatus);
	            	if(settlementStatus==0){
	            		xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	            		//xml+=GpfSettlementNoteModel.xmlLoadMasterForms(gpfData6.getGpf_no());
	            		xml+=GpfSettlementNoteModel.xmlFirstRelieveDetails(gpfData6);
	            		xml=xml+"<status>GpfNotExist</status>";
	            		if(GpfSettlementNoteModel.checkGpfRelieveStatus(gpfData6.getGpf_no())==0){
	            			xml=xml+"<relieved>no</relieved>";
	            		}
	            		else if(GpfSettlementNoteModel.checkGpfRelieveStatus(gpfData6.getGpf_no())==1){
	            			xml=xml+"<relieved>yes</relieved>";
	            		}
	            	}
	            	if(settlementStatus==1){
	            		xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlSettlement(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData6);
	            		//xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData6);
	            		//xml+=GpfSettlementNoteModel.xmlLoadMasterForms(gpfData6.getGpf_no());
	            		System.out.println("Edited...........gpfNo="+gpfData6.getGpf_no());
	            		xml=xml+"<status>GpfEdited</status>";
	            	}
	            	if(settlementStatus==2){
	            		xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlSettlement(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData6);
	            		//xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData6);
	            		//xml+=GpfSettlementNoteModel.xmlLoadMasterForms(gpfData6.getGpf_no());
	            		xml=xml+"<status>GpfPartiallyValidated</status>";
	            	}
	            	
	            	/*if(settlementStatus==3){
	            		xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlSettlement(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlLoadMasterForms(gpfData6.getGpf_no());
	            		xml=xml+"<status>GpfFullyValidated</status>";
	            		if(GpfSettlementNoteModel.checkGpfRelieveStatus(gpfData6.getGpf_no())==0){
	            			xml=xml+"<relieved>no</relieved>";
	            		}
	            		else if(GpfSettlementNoteModel.checkGpfRelieveStatus(gpfData6.getGpf_no())==1){
	            			xml=xml+"<relieved>yes</relieved>";
	            		}
	            		
	            	}*/
	            	/*if(settlementStatus==4){
	            		xml+=GpfSettlementNoteModel.xmlEmployee(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlSettlement(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData6);
	            		xml+=GpfSettlementNoteModel.xmlLoadMasterForms(gpfData6.getGpf_no());
	            		xml=xml+"<status>AllGpfFullyValidated</status>";
	            	}*/
	            }else{
	            	xml=xml+"<status>inValidGpf</status>";
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	            	int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	            	String proc_no=request.getParameter("proc_no").trim();
	            	String proc_date=request.getParameter("proc_date").trim();
	            	String relieve_status=request.getParameter("relieve_status").trim();
	            	String relieve_date=request.getParameter("relieve_date").trim();
	            	String int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
	            	String int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
	            	String int_tobe_calc_date="";
	            	
	            	
	            xml="<response><command>add</command>";
	            xml=xml+"<status>add</status>";
	            
	            GpfSettlementNoteData gpfData2=null;
	            GpfSettlementNoteData gpfData1=new GpfSettlementNoteData();
	            int settlementStatus=0;
	            settlementStatus=GpfSettlementNoteModel.checkSettlementGpf(gpfNo);
	            
	            if((settlementStatus==0)||(settlementStatus==3)){
	            	gpfData2=gpfData1;
	            	gpfData2.setGpf_no(gpfNo);
	            	int_tobe_calc_date=GpfSettlementNoteModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	            	
	            	gpfData2.setRelieve_status(relieve_status);
		            gpfData2.setRelieve_date(relieve_date);
		            gpfData2.setInt_tobe_calc_date(int_tobe_calc_date);
	            	gpfData2.setProc_no(proc_no);
		            gpfData2.setProc_date(proc_date);		            
		            gpfData2.setFinance_year(financeYear);
		            gpfData2.setInterest_calc_rate(GpfSettlementNoteModel.getInterestRate(financeYear));
		            
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData2.getGpf_no(), gpfData2.getFinance_year())==-1){
		            	xml=xml+"<flag>failure</flag>";
		            	xml=xml+"<validate_finance_year>invalid</validate_finance_year>";
		            }
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData2.getGpf_no(), gpfData2.getFinance_year())==1){
		            	xml=xml+"<flag>failure</flag>";
		            	xml=xml+"<validate_finance_year>reduntant</validate_finance_year>";
		            }
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData2.getGpf_no(), gpfData2.getFinance_year())==2){
		            	xml=xml+"<flag>failure</flag>";
		            	xml=xml+"<validate_finance_year>notexist</validate_finance_year>";
		            }
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData2.getGpf_no(), gpfData2.getFinance_year())==0){
		            	boolean result=GpfSettlementNoteModel.insertGpfSettlementNoteData(gpfData2,ts,updatedby);
			            if(result){
			            	xml=xml+"<flag>success</flag>";
			            	xml=xml+"<validate_finance_year>success</validate_finance_year>";
			            	xml+=GpfSettlementNoteModel.xmlEmployee(gpfData2);		            	
		                	xml+=GpfSettlementNoteModel.xmlSettlement(gpfData2);
		                	xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData2);
		            		xml+=GpfSettlementNoteModel.xmlAuthorisationDetails(gpfData2);
			            }
			            else{
			            	xml=xml+"<flag>failure</flag>";
			            	xml=xml+"<validate_finance_year>failure</validate_finance_year>";
			            }
		            }

		            
	            }
	            else {
	            	xml=xml+"<flag>failure</flag>";
	            }
	            System.out.println("adddd...");
	            
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Update")) {
	           
	            
	            int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
            	String proc_no=request.getParameter("proc_no").trim();
            	String proc_date=request.getParameter("proc_date").trim();
            	String relieve_status=request.getParameter("relieve_status").trim();
            	String relieve_date=request.getParameter("relieve_date").trim();
            	String int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
            	String int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
            	String int_tobe_calc_date="";
            	
            	
            	String status_id="ED";
            	GpfSettlementNoteData gpfData7=new GpfSettlementNoteData();
            	GpfSettlementNoteData gpfData8=null;
	            int settlementStatus=0;
	            settlementStatus=GpfSettlementNoteModel.checkSettlementGpf(gpfNo);
	            
	            if(settlementStatus==2){
	            	gpfData8=GpfSettlementNoteModel.getGpfDetails(gpfNo);		            	
	            }
	            int_tobe_calc_date=GpfSettlementNoteModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	            
	            xml="<response><command>update</command>";
	            xml=xml+"<status>update</status>";
	            if(settlementStatus==2){
	            	
            		gpfData8.setRelieve_status(relieve_status);
            		gpfData8.setRelieve_date(relieve_date);
            		gpfData8.setInt_tobe_calc_date(int_tobe_calc_date);
            		gpfData8.setProc_no(proc_no);
            		gpfData8.setProc_date(proc_date);	            		
            		gpfData8.setFinance_year(financeYear);
            		boolean boolSt=false;
            		
            		if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==-1){
		            	xml=xml+"<flag>failure</flag>";
		            	xml=xml+"<validate_finance_year>invalid</validate_finance_year>";
		            }
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==1){
		            	xml=xml+"<flag>failure</flag>";
		            	xml=xml+"<validate_finance_year>reduntant</validate_finance_year>";
		            }
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==2){
		            	xml=xml+"<flag>failure</flag>";
		            	xml=xml+"<validate_finance_year>notexist</validate_finance_year>";
		            }
		            if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==0){
		            	if(GpfSettlementNoteModel.countValidatedSettlements(gpfData8.getGpf_no())==0){
	            			boolSt=GpfSettlementNoteModel.firstGpfUpdate(gpfData8, ts, updatedby, status_id);
	            			xml=xml+"<firstGpf>yes</firstGpf>";
	            		}
	            		else{
	            			boolSt=GpfSettlementNoteModel.nextGpfUpdate(gpfData8, ts, updatedby, status_id);
	            			xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData8);
	            			xml=xml+"<firstGpf>no</firstGpf>";
	            		}
	            		
	            		
	            		if(boolSt){
	            			xml=xml+"<flag>success</flag>";
	            			xml=xml+"<validate_finance_year>success</validate_finance_year>";
	    	            	xml+=GpfSettlementNoteModel.xmlEmployee(gpfData8);    	            	
	                    	xml+=GpfSettlementNoteModel.xmlSettlement(gpfData8);
	                    	
		            		
	            		}else{
	            			xml=xml+"<flag>failure</flag>";
	            			xml=xml+"<validate_finance_year>failure</validate_finance_year>";
	            		}
		            }
		            
            		
	                
		            
	            }else{
	            	xml=xml+"<flag>failure</flag>";
	            }
	            
	            
	            xml=xml+"</response>";
	        }
	            else if(command.equalsIgnoreCase("Validate")) {
	                System.out.println("validate");
	                
	                int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	            	String proc_no=request.getParameter("proc_no").trim();
	            	String proc_date=request.getParameter("proc_date").trim();
	            	String relieve_status=request.getParameter("relieve_status").trim();
	            	String relieve_date=request.getParameter("relieve_date").trim();
	            	String int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
	            	String int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
	            	String int_tobe_calc_date="";
	            	
	            	
	            	String status_id="FR";
	            	GpfSettlementNoteData gpfData7=new GpfSettlementNoteData();
	            	GpfSettlementNoteData gpfData8=null;
		            int settlementStatus=0;
		            settlementStatus=GpfSettlementNoteModel.checkSettlementGpf(gpfNo);
		            
		            if(settlementStatus==2){
		            	gpfData8=GpfSettlementNoteModel.getGpfDetails(gpfNo);		            	
		            }
		            int_tobe_calc_date=GpfSettlementNoteModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
		            
		            xml="<response><command>validate</command>";
		            xml=xml+"<status>validate</status>";
		            if(settlementStatus==2){
		            	
	            		gpfData8.setRelieve_status(relieve_status);
	            		gpfData8.setRelieve_date(relieve_date);
	            		gpfData8.setInt_tobe_calc_date(int_tobe_calc_date);
	            		gpfData8.setProc_no(proc_no);
	            		gpfData8.setProc_date(proc_date);	            		
	            		gpfData8.setFinance_year(financeYear);
	            		boolean boolSt=false;
	            		if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==-1){
			            	xml=xml+"<flag>failure</flag>";
			            	xml=xml+"<validate_finance_year>invalid</validate_finance_year>";
			            }
			            if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==1){
			            	xml=xml+"<flag>failure</flag>";
			            	xml=xml+"<validate_finance_year>reduntant</validate_finance_year>";
			            }
			            if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==2){
			            	xml=xml+"<flag>failure</flag>";
			            	xml=xml+"<validate_finance_year>notexist</validate_finance_year>";
			            }
			            if(GpfSettlementNoteModel.checkFinancialYear(gpfData8.getGpf_no(), gpfData8.getFinance_year())==0){
			            	if(GpfSettlementNoteModel.countValidatedSettlements(gpfData8.getGpf_no())==0){
		            			boolSt=GpfSettlementNoteModel.firstGpfUpdate(gpfData8, ts, updatedby, status_id);
		            			xml=xml+"<firstGpf>yes</firstGpf>";
		            		}
		            		else{
		            			boolSt=GpfSettlementNoteModel.nextGpfUpdate(gpfData8, ts, updatedby, status_id);
		            			xml+=GpfSettlementNoteModel.xmlRelieveDetails(gpfData8);
		            			xml=xml+"<firstGpf>no</firstGpf>";
		            		}
		            		
		            		if(boolSt){
		            			xml=xml+"<flag>success</flag>";
		            			xml=xml+"<validate_finance_year>success</validate_finance_year>";
		    	            	xml+=GpfSettlementNoteModel.xmlEmployee(gpfData8);    	            	
		                    	xml+=GpfSettlementNoteModel.xmlSettlement(gpfData8);	
		            		}else{
		            			xml=xml+"<flag>failure</flag>";
		            			xml=xml+"<validate_finance_year>failure</validate_finance_year>";
		            		}
			            }
		            }else{
		            	xml=xml+"<flag>failure</flag>";
		            }
		            
		            
		            xml=xml+"</response>";
	            }	            
	            else if(command.equalsIgnoreCase("Delete")) {
	                System.out.println("Delete");
	                int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	                xml="<response><command>delete</command>";
	                xml=xml+"<status>delete</status>";
	                String processStatus=GpfSettlementNoteModel.getLastProcessFlowStatusId(gpfNo);
	                
	                
	                	if(!processStatus.equals("FR")){
	                		boolean boolDS=GpfSettlementNoteModel.deleteGpfSettlementNoteData(gpfNo);
	                		if (boolDS) {
	                			xml=xml+"<flag>success</flag>"; 
							} else {
								xml=xml+"<flag>failure</flag>";
							}
		                	
	                	}else{
	                		xml=xml+"<flag>failure</flag>";
	                	}
	                	
					
					xml=xml+"</response>";
	            }
	            else if(command.equalsIgnoreCase("CalcInterest")) {
	                System.out.println("calcInterest");
	                int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	                xml="<response><command>CalcInterest</command>";
	                xml=xml+"<status>CalcInterest</status>";
	                String processStatus="";
	                int authNo=0;
	                try {
	                	authNo=Integer.parseInt(request.getParameter("auth_no").trim());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					GpfSettlementNoteData gpfData6=GpfSettlementNoteModel.getGpfDetails(gpfNo, authNo);
					//updateCalculation(GpfData gpfData,int authNo)
					gpfData6=GpfSettlementNoteModel.processCalculation(gpfData6);
					boolean updatedcal=GpfSettlementNoteModel.updateCalculation(gpfData6,gpfData6.getAuth_no());
					if(updatedcal){
						xml=xml+"<flag>success</flag>";
					}
					else{
						xml=xml+"<flag>failure</flag>";
					}
					
					xml=xml+"</response>";
	            }
	            else if(command.equalsIgnoreCase("LoadYear")){
	            	
	            	xml="<response><command>LoadYear</command>";
	            	xml=xml+"<status>LoadYear</status>";
	            	xml=xml+"<flag>success</flag>"; 
	            	String relvDate="";
	            	try {
	            		relvDate=request.getParameter("relieve_date");
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("LoadYear:rel_date="+relvDate);
					
	            	
					xml=xml+"<listYear>"+GpfSettlementNoteModel.xmlLoadListYear(relvDate)+"</listYear>"; 
					
	            	xml=xml+"</response>";
	            	System.out.println("LoadYear xml="+xml);
	            }
	            else if(command.equalsIgnoreCase("LoadMonth")){
	            	xml="<response><command>LoadMonth</command>";
	            	xml=xml+"<status>LoadMonth</status>";
	            	xml=xml+"<flag>success</flag>"; 
	            	String relvDate="";
	            	String listYear="";
	            	
	            	try {
	            		relvDate=request.getParameter("relieve_date");
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {						
						listYear=request.getParameter("listYear");
					} catch (Exception e) {
						// TODO: handle exception
					}
					
	            	
					xml=xml+"<listMonth>"+GpfSettlementNoteModel.xmlLoadListMonth(relvDate, listYear)+"</listMonth>"; 
					
	            	xml=xml+"</response>";
	            }
	            else if(command.equalsIgnoreCase("LoadFinanceYear")){
	            	
	            	xml="<response><command>LoadFinanceYear</command>";
	            	xml=xml+"<status>LoadFinanceYear</status>";
	            	xml=xml+"<flag>success</flag>"; 
	            	String relvDate="";
	            	
	            	try {
	            		relvDate=request.getParameter("relieve_date");
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					GpfSettlementNoteData gpfData11=new GpfSettlementNoteData();
					gpfData11.setRelieve_date(relvDate);
	            	
					xml=xml+"<listFinanceYear>"+GpfSettlementNoteModel.xmlFinanceYear(gpfData11)+"</listFinanceYear>"; 
					
	            	xml=xml+"</response>";
	            	System.out.println("listFinanceYear xml="+xml);
	            }
	            
	        
	        GpfSettlementNoteModel.closeConnection();
	        out.println(xml);
	        out.close();	        

        }
    
    /*@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	System.out.println("java report called");
    	
	 	
        ResultSet rs=null,rs1=null,rs2=null;
        CallableStatement cs=null;
        PreparedStatement ps=null,ps1=null,ps2=null;
        String xml="";
        DecimalFormat df=new DecimalFormat("#0.00");
        
        GpfSettlementNoteModel.closeConnection();
        GpfSettlementNoteModel.openConnection();
        Connection conn1=GpfSettlementNoteModel.getConnectionObject();
        
        String txtGpfNo="";
        String txtAuthNo="";
        String reportType="";
        int gpfNo=0;
        int authNo=1;
        try {
        	txtGpfNo=request.getParameter("txtGpfNo").trim();        	
        	gpfNo=Integer.parseInt(txtGpfNo);        	
        	txtAuthNo=request.getParameter("listAuth").trim();
        	authNo=Integer.parseInt(txtAuthNo);  
        	reportType=request.getParameter("listReporttype").trim();
        	System.out.println("report="+gpfNo+" "+authNo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        

        File reportFile=null;
    try 
    {
        System.out.println("calling servlet...");
        reportFile = new File(getServletContext().getRealPath("/Reports/GPF_Authorisation_Interest_Notification.jasper"));
       
        if (!reportFile.exists())
        	throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        Map<String,Integer> map=null;
        map = new HashMap<String,Integer>();
        map.put("gpf_no",gpfNo);
        map.put("auth_no",authNo);
        
        
        //map.put("vouType",txtCreat_By_Module);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,conn1);
            System.out.println("upto");
        String rtype="PDF";// request.getParameter("cmbReportType");
        System.out.println(rtype);
        if (reportType.equalsIgnoreCase("HTML"))   
        {
                    response.setContentType("text/html");
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"GPF_Authorisation_Interest_Notification.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                    //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                    //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                    //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.exportReport();
                     out.flush();
                    out.close();
        }
        else if (reportType.equalsIgnoreCase("PDF"))   
        {       System.out.println(rtype);
                    byte buf[] = 
                      JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                   // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                   //response.setContentType("application/force-download");
                
                    response.setHeader ("Content-Disposition", "attachment;filename=\"GPF_Authorisation_Interest_Notification.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (reportType.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"GPF_Authorisation_Interest_Notification.xls\"");
                 JRXlsExporter exporterXLS = new JRXlsExporter(); 
                 exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                 
                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                 exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                 exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                 exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
                 exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
                 exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                 exporterXLS.exportReport(); 
                 byte []bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
        
        }
        else if (reportType.equalsIgnoreCase("TXT"))   
        {
        
                response.setContentType("text/plain");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"GPF_Authorisation_Interest_Notification.txt\"");
                 
            JRTextExporter exporter = new JRTextExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
            exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
            exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
            exporter.exportReport(); 
            
                 byte []bytes;
                bytes = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
        
        }
        
        } 
        catch (Exception ex) 
        {
        String connectMsg = "Could not create the report " + ex.getMessage() + " uu " +  ex.getLocalizedMessage();
        System.out.println(connectMsg);
        }
    	
        GpfSettlementNoteModel.closeConnection();  
    	
    }*/


       
}
