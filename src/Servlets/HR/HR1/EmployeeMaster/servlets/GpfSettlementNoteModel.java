package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

//import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleCallableStatement;
import oracle.jdbc.driver.OracleTypes;

import oracle.sql.ARRAY;



public class GpfSettlementNoteModel {
	private static Connection connection=null;	
	
	public static Connection getDatabaseConnection() {
		return connection;
	}
	
	public GpfSettlementNoteModel() {
		// TODO Auto-generated constructor stub	
			openConnection();
	}	
	
	public  static synchronized void openConnection(){
		try {
				if(connection==null){LoadDriver driver=new LoadDriver();
	            connection=driver.getConnection();
}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static  synchronized void closeConnection(){
		try {
			if(connection!=null){
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static Connection getConnectionObject(){
		return GpfSettlementNoteModel.getDatabaseConnection();	
	}
	
	private static GpfSettlementNoteData setStatusData(GpfSettlementNoteData gpfSettlementNoteData){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//setting missing_interest_data 
            ps=conn1.prepareStatement("select count(*) from HRM_GPF_INT_CREDITED where GPF_NO=? and FIN_YEAR=? and PARTIAL_FULL='P' ");
            ps.setInt(1,gpfSettlementNoteData.getGpf_no());
            ps.setString(2, gpfSettlementNoteData.getFinance_year());
            rs=ps.executeQuery();
            int countMissInt=0;
            if(rs.next()){
            	 countMissInt=rs.getInt(1);	            	
            }
            if(countMissInt==0){	            	
            	gpfSettlementNoteData.setMissing_interest_data("no");
            }
            else if(countMissInt>0){
            	gpfSettlementNoteData.setMissing_interest_data("yes");
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		//setting interest_rate_data
		try {
			ps=conn1.prepareStatement("select INTEREST_RATE from HRM_GPF_MST_INT_RATE where FINANCIAL_YEAR=?");
			ps.setString(1,gpfSettlementNoteData.getFinance_year());
            rs=ps.executeQuery();
            if(rs.next())
            {
            	int intr=rs.getInt(1);
            	if(intr==0){
            		gpfSettlementNoteData.setInterest_rate_data("no");
            	}else{
            		gpfSettlementNoteData.setInterest_rate_data("yes");
            	}
            }
            else{
            	gpfSettlementNoteData.setInterest_rate_data("no");
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		//count rows
		try {
			ps=conn1.prepareStatement("select count(GPF_NO) from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=?");
			 ps.setInt(1,gpfSettlementNoteData.getGpf_no());
            rs=ps.executeQuery();
            if(rs.next())
            {
            	int count=rs.getInt(1);
            	gpfSettlementNoteData.setCount_rows(count);	            	
            }
            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		//last financial year
		try {
			ps=conn1.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED ");
            rs=ps.executeQuery();
            if(rs.next())
            {
            	String lstFinYr=rs.getString(1);
            	gpfSettlementNoteData.setLast_finance_year(lstFinYr);	            	
            }
            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		return gpfSettlementNoteData;
	}
	public  static GpfSettlementNoteData getGpfDetails(int gpfNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		GpfSettlementNoteData gpfSettlementNoteData=new GpfSettlementNoteData();	
			try {
				ps=conn1.prepareStatement("select a.REG_CB ,a.REG_DEPOSIT ,a.REG_INT ,a.REG_MISSING_CR_DB ,a.REG_INT_MISSING_CR_DB ,a.REG_WITHDRAWALS ,a.REG_EXCESS_CR_DB  ,a.REG_INT_EXCESS_CR_DB ," +
	            		"  a.REG_AMT_ALREADY_AUTH  ,a.IMP_REG_CB ,IMP_REG_DEPOSIT ,a.IMP_INT ,a.IMP_MISS_CR_DB ,a.IMP_INT_MISS_CR_DB ,a.IMP_WITH ,a.IMP_EXCESS_CR_DB ," +
	            		"  a.IMP_INT_MISS_EXCESS_CR_DB ,a.IMP_AMT_ALREADY_AUTH ,a.IMP_AMT_BAL_AUTH ,a.REG_AMT_BAL_AUTH ,a.REG_DEPOSIT_TOTAL ," +
	            		"  a.REG_DEDUCT_TOTAL ,a.IM_DEPOSIT_TOTAL ,a.IM_DEDUCT_TOTAL,a.GPF_NO,a.FINANCIAL_YEAR,a.PROC_NO,a.PROC_DATE,a.SETTLEMENT_REASON,a.RETIRED_OR_DIED_DATE,a.INTEREST_CALC_RATE,a.PROCESS_FLOW_STATUS_ID,a.INT_TOBE_CALC_DATE,a.AUTH_SLNO,a.CURRENT_FIN_END_DATE" +
	            		" from(" +
	            		"  (select REG_CB ,REG_DEPOSIT ,REG_INT ,REG_MISSING_CR_DB ,REG_INT_MISSING_CR_DB ,REG_WITHDRAWALS ,REG_EXCESS_CR_DB  ,REG_INT_EXCESS_CR_DB ," +
	            		"    REG_AMT_ALREADY_AUTH  ,IMP_REG_CB ,IMP_REG_DEPOSIT ,IMP_INT ,IMP_MISS_CR_DB ,IMP_INT_MISS_CR_DB ,IMP_WITH ,IMP_EXCESS_CR_DB ," +
	            		"    IMP_INT_MISS_EXCESS_CR_DB ,IMP_AMT_ALREADY_AUTH ,IMP_AMT_BAL_AUTH ,REG_AMT_BAL_AUTH ,REG_DEPOSIT_TOTAL ," +
	            		"    REG_DEDUCT_TOTAL ,IM_DEPOSIT_TOTAL ,IM_DEDUCT_TOTAL,GPF_NO,FINANCIAL_YEAR,PROC_NO,to_char(PROC_DATE,'dd/mm/yyyy') as PROC_DATE,SETTLEMENT_REASON,to_char(RETIRED_OR_DIED_DATE,'dd/mm/yyyy') as RETIRED_OR_DIED_DATE,INTEREST_CALC_RATE,PROCESS_FLOW_STATUS_ID,to_char(INT_TOBE_CALC_DATE,'dd/mm/yyyy') as INT_TOBE_CALC_DATE,AUTH_SLNO,to_char(CURRENT_FIN_END_DATE,'dd/mm/yyyy') as CURRENT_FIN_END_DATE" +
	            		"    from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=?)a" +
	            		"   join" +
	            		"  (select max(AUTH_SLNO) as AUTH_SLNO1,gpf_no as gpf_no1 from HRM_GPF_SETTLEMENT_NOTE group by gpf_no)b" +
	            		"  on a.gpf_no=b.gpf_no1 and a.AUTH_SLNO=b.AUTH_SLNO1" +
	            		")" 							
						); 
	            ps.setInt(1,gpfNo);
	            rs=ps.executeQuery();
	            if(rs.next()){
	        		
	            	gpfSettlementNoteData.setGpf_no(gpfNo);
	        		gpfSettlementNoteData.setCb_ac_re(rs.getDouble(1));
	        		gpfSettlementNoteData.setSubseq_deposit_re(rs.getDouble(2));
	        		gpfSettlementNoteData.setInt_amount_re(rs.getDouble(3));
	        		gpfSettlementNoteData.setDep_amount_re(rs.getDouble(4));
	        		gpfSettlementNoteData.setDep_interest_re(rs.getDouble(5));
	        		gpfSettlementNoteData.setSubseq_withdraw_re(rs.getDouble(6));
	        		gpfSettlementNoteData.setWithdraw_amount_re(rs.getDouble(7));
	        		gpfSettlementNoteData.setWithdraw_interest_re(rs.getDouble(8));
	        		gpfSettlementNoteData.setAmount_already_re(rs.getDouble(9));
	        		gpfSettlementNoteData.setCb_ac_im(rs.getDouble(10));
	        		gpfSettlementNoteData.setSubseq_deposit_im(rs.getDouble(11));
	        		gpfSettlementNoteData.setInt_amount_im(rs.getDouble(12));
	        		gpfSettlementNoteData.setDep_amount_im(rs.getDouble(13));
	        		gpfSettlementNoteData.setDep_interest_im(rs.getDouble(14));
	        		gpfSettlementNoteData.setSubseq_withdraw_im(rs.getDouble(15));
	        		gpfSettlementNoteData.setWithdraw_amount_im(rs.getDouble(16));
	        		gpfSettlementNoteData.setWithdraw_interest_im(rs.getDouble(17));
	        		gpfSettlementNoteData.setAmount_already_im(rs.getDouble(18));
	        		
	        		gpfSettlementNoteData.setImp_amt_bal_auth(rs.getDouble(19));
	        		gpfSettlementNoteData.setReg_amt_bal_auth(rs.getDouble(20));
	        		
	        		gpfSettlementNoteData.setReg_deposit_total(rs.getDouble(21));
	        		gpfSettlementNoteData.setReg_deduct_total(rs.getDouble(22));
	        		gpfSettlementNoteData.setIm_deposit_total(rs.getDouble(23));
	        		gpfSettlementNoteData.setIm_deduct_total(rs.getDouble(24));
	        		
	        		gpfSettlementNoteData.setGpf_no(rs.getInt(25));
	        		gpfSettlementNoteData.setFinance_year(rs.getString(26));
	        		gpfSettlementNoteData.setProc_no(rs.getString(27));
	        		gpfSettlementNoteData.setProc_date(rs.getString(28));
	        		gpfSettlementNoteData.setRelieve_status(rs.getString(29));
	        		gpfSettlementNoteData.setRelieve_date(rs.getString(30));
	        		gpfSettlementNoteData.setInterest_calc_rate(rs.getDouble(31));
	        		gpfSettlementNoteData.setProcess_flow_id(rs.getString(32));
	        		gpfSettlementNoteData.setInt_tobe_calc_date(rs.getString(33));
	        		gpfSettlementNoteData.setAuth_no(rs.getInt(34));
	        		gpfSettlementNoteData.setCurrent_fin_end_date(rs.getString(35));
	        		
	        		int tYr=0;
	        		int currentOpeningYear=0;
	        		boolean fiYe=false;
	            	String []tmpYr=gpfSettlementNoteData.getFinance_year().split("-");
	            	if(tmpYr.length==2){
	            		fiYe=true;
	            		int iT=Integer.parseInt(tmpYr[0]);
	            		if(tYr<iT){
	            			tYr=iT;
	            		}
	            	}
		            if(fiYe){
		            	currentOpeningYear=tYr;
		            }
		            gpfSettlementNoteData.setCurrent_opening_year(currentOpeningYear);
	            }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			gpfSettlementNoteData=GpfSettlementNoteModel.setStatusData(gpfSettlementNoteData);
			
			
			
			return gpfSettlementNoteData;
	}
	public static GpfSettlementNoteData getGpfDetails(int gpfNo,int authNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		GpfSettlementNoteData gpfSettlementNoteData=new GpfSettlementNoteData();	
			try {
				ps=conn1.prepareStatement(
	            		"  select REG_CB ,REG_DEPOSIT ,REG_INT ,REG_MISSING_CR_DB ,REG_INT_MISSING_CR_DB ,REG_WITHDRAWALS ,REG_EXCESS_CR_DB  ,REG_INT_EXCESS_CR_DB ," +
	            		"    REG_AMT_ALREADY_AUTH  ,IMP_REG_CB ,IMP_REG_DEPOSIT ,IMP_INT ,IMP_MISS_CR_DB ,IMP_INT_MISS_CR_DB ,IMP_WITH ,IMP_EXCESS_CR_DB ," +
	            		"    IMP_INT_MISS_EXCESS_CR_DB ,IMP_AMT_ALREADY_AUTH ,IMP_AMT_BAL_AUTH ,REG_AMT_BAL_AUTH ,REG_DEPOSIT_TOTAL ," +
	            		"    REG_DEDUCT_TOTAL ,IM_DEPOSIT_TOTAL ,IM_DEDUCT_TOTAL,GPF_NO,FINANCIAL_YEAR,PROC_NO,to_char(PROC_DATE,'dd/mm/yyyy') as PROC_DATE,SETTLEMENT_REASON,to_char(RETIRED_OR_DIED_DATE,'dd/mm/yyyy') as RETIRED_OR_DIED_DATE,INTEREST_CALC_RATE,PROCESS_FLOW_STATUS_ID,to_char(INT_TOBE_CALC_DATE,'dd/mm/yyyy') as INT_TOBE_CALC_DATE,AUTH_SLNO,to_char(CURRENT_FIN_END_DATE,'dd/mm/yyyy') as CURRENT_FIN_END_DATE" +
	            		"    from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=? and AUTH_SLNO=?" 			
						); 
	            ps.setInt(1,gpfNo);
	            ps.setInt(2,authNo);
	            rs=ps.executeQuery();
	            if(rs.next()){
	        		
	            	gpfSettlementNoteData.setGpf_no(gpfNo);
	        		gpfSettlementNoteData.setCb_ac_re(rs.getDouble(1));
	        		gpfSettlementNoteData.setSubseq_deposit_re(rs.getDouble(2));
	        		gpfSettlementNoteData.setInt_amount_re(rs.getDouble(3));
	        		gpfSettlementNoteData.setDep_amount_re(rs.getDouble(4));
	        		gpfSettlementNoteData.setDep_interest_re(rs.getDouble(5));
	        		gpfSettlementNoteData.setSubseq_withdraw_re(rs.getDouble(6));
	        		gpfSettlementNoteData.setWithdraw_amount_re(rs.getDouble(7));
	        		gpfSettlementNoteData.setWithdraw_interest_re(rs.getDouble(8));
	        		gpfSettlementNoteData.setAmount_already_re(rs.getDouble(9));
	        		gpfSettlementNoteData.setCb_ac_im(rs.getDouble(10));
	        		gpfSettlementNoteData.setSubseq_deposit_im(rs.getDouble(11));
	        		gpfSettlementNoteData.setInt_amount_im(rs.getDouble(12));
	        		gpfSettlementNoteData.setDep_amount_im(rs.getDouble(13));
	        		gpfSettlementNoteData.setDep_interest_im(rs.getDouble(14));
	        		gpfSettlementNoteData.setSubseq_withdraw_im(rs.getDouble(15));
	        		gpfSettlementNoteData.setWithdraw_amount_im(rs.getDouble(16));
	        		gpfSettlementNoteData.setWithdraw_interest_im(rs.getDouble(17));
	        		gpfSettlementNoteData.setAmount_already_im(rs.getDouble(18));
	        		
	        		gpfSettlementNoteData.setImp_amt_bal_auth(rs.getDouble(19));
	        		gpfSettlementNoteData.setReg_amt_bal_auth(rs.getDouble(20));
	        		
	        		gpfSettlementNoteData.setReg_deposit_total(rs.getDouble(21));
	        		gpfSettlementNoteData.setReg_deduct_total(rs.getDouble(22));
	        		gpfSettlementNoteData.setIm_deposit_total(rs.getDouble(23));
	        		gpfSettlementNoteData.setIm_deduct_total(rs.getDouble(24));
	        		
	        		gpfSettlementNoteData.setGpf_no(rs.getInt(25));
	        		gpfSettlementNoteData.setFinance_year(rs.getString(26));
	        		gpfSettlementNoteData.setProc_no(rs.getString(27));
	        		gpfSettlementNoteData.setProc_date(rs.getString(28));
	        		gpfSettlementNoteData.setRelieve_status(rs.getString(29));
	        		gpfSettlementNoteData.setRelieve_date(rs.getString(30));
	        		gpfSettlementNoteData.setInterest_calc_rate(rs.getDouble(31));
	        		gpfSettlementNoteData.setProcess_flow_id(rs.getString(32));
	        		gpfSettlementNoteData.setInt_tobe_calc_date(rs.getString(33));
	        		gpfSettlementNoteData.setAuth_no(rs.getInt(34));
	        		gpfSettlementNoteData.setCurrent_fin_end_date(rs.getString(35));
	        		
	        		int tYr=0;
	        		int currentOpeningYear=0;
	        		boolean fiYe=false;
	            	String []tmpYr=gpfSettlementNoteData.getFinance_year().split("-");
	            	if(tmpYr.length==2){
	            		fiYe=true;
	            		int iT=Integer.parseInt(tmpYr[0]);
	            		if(tYr<iT){
	            			tYr=iT;
	            		}
	            	}
		            if(fiYe){
		            	currentOpeningYear=tYr;
		            }
		            gpfSettlementNoteData.setCurrent_opening_year(currentOpeningYear);
		            
	            }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			gpfSettlementNoteData=GpfSettlementNoteModel.setStatusData(gpfSettlementNoteData);
			
			
			
			return gpfSettlementNoteData;
	}
	public static GpfSettlementNoteData getPartiallyValidatedGpfDetails(int gpfNo,int authNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		GpfSettlementNoteData gpfSettlementNoteData=new GpfSettlementNoteData();	
			try {
				ps=conn1.prepareStatement(
	            		"  select REG_CB ,REG_DEPOSIT ,REG_INT ,REG_MISSING_CR_DB ,REG_INT_MISSING_CR_DB ,REG_WITHDRAWALS ,REG_EXCESS_CR_DB  ,REG_INT_EXCESS_CR_DB ," +
	            		"    REG_AMT_ALREADY_AUTH  ,IMP_REG_CB ,IMP_REG_DEPOSIT ,IMP_INT ,IMP_MISS_CR_DB ,IMP_INT_MISS_CR_DB ,IMP_WITH ,IMP_EXCESS_CR_DB ," +
	            		"    IMP_INT_MISS_EXCESS_CR_DB ,IMP_AMT_ALREADY_AUTH ,IMP_AMT_BAL_AUTH ,REG_AMT_BAL_AUTH ,REG_DEPOSIT_TOTAL ," +
	            		"    REG_DEDUCT_TOTAL ,IM_DEPOSIT_TOTAL ,IM_DEDUCT_TOTAL,GPF_NO,FINANCIAL_YEAR,PROC_NO,to_char(PROC_DATE,'dd/mm/yyyy') as PROC_DATE,SETTLEMENT_REASON,to_char(RETIRED_OR_DIED_DATE,'dd/mm/yyyy') as RETIRED_OR_DIED_DATE,INTEREST_CALC_RATE,PROCESS_FLOW_STATUS_ID,to_char(INT_TOBE_CALC_DATE,'dd/mm/yyyy') as INT_TOBE_CALC_DATE,AUTH_SLNO" +
	            		"    from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=? and AUTH_SLNO=?" 			
						); 
	            ps.setInt(1,gpfNo);
	            ps.setInt(2,authNo);
	            rs=ps.executeQuery();
	            if(rs.next()){
	        		
	            	gpfSettlementNoteData.setGpf_no(gpfNo);
	        		gpfSettlementNoteData.setCb_ac_re(rs.getDouble(1));
	        		gpfSettlementNoteData.setSubseq_deposit_re(rs.getDouble(2));
	        		gpfSettlementNoteData.setInt_amount_re(rs.getDouble(3));
	        		gpfSettlementNoteData.setDep_amount_re(rs.getDouble(4));
	        		gpfSettlementNoteData.setDep_interest_re(rs.getDouble(5));
	        		gpfSettlementNoteData.setSubseq_withdraw_re(rs.getDouble(6));
	        		gpfSettlementNoteData.setWithdraw_amount_re(rs.getDouble(7));
	        		gpfSettlementNoteData.setWithdraw_interest_re(rs.getDouble(8));
	        		gpfSettlementNoteData.setAmount_already_re(rs.getDouble(9));
	        		gpfSettlementNoteData.setCb_ac_im(rs.getDouble(10));
	        		gpfSettlementNoteData.setSubseq_deposit_im(rs.getDouble(11));
	        		gpfSettlementNoteData.setInt_amount_im(rs.getDouble(12));
	        		gpfSettlementNoteData.setDep_amount_im(rs.getDouble(13));
	        		gpfSettlementNoteData.setDep_interest_im(rs.getDouble(14));
	        		gpfSettlementNoteData.setSubseq_withdraw_im(rs.getDouble(15));
	        		gpfSettlementNoteData.setWithdraw_amount_im(rs.getDouble(16));
	        		gpfSettlementNoteData.setWithdraw_interest_im(rs.getDouble(17));
	        		gpfSettlementNoteData.setAmount_already_im(rs.getDouble(18));
	        		
	        		gpfSettlementNoteData.setImp_amt_bal_auth(rs.getDouble(19));
	        		gpfSettlementNoteData.setReg_amt_bal_auth(rs.getDouble(20));
	        		
	        		gpfSettlementNoteData.setReg_deposit_total(rs.getDouble(21));
	        		gpfSettlementNoteData.setReg_deduct_total(rs.getDouble(22));
	        		gpfSettlementNoteData.setIm_deposit_total(rs.getDouble(23));
	        		gpfSettlementNoteData.setIm_deduct_total(rs.getDouble(24));
	        		
	        		gpfSettlementNoteData.setGpf_no(rs.getInt(25));
	        		gpfSettlementNoteData.setFinance_year(rs.getString(26));
	        		gpfSettlementNoteData.setProc_no(rs.getString(27));
	        		gpfSettlementNoteData.setProc_date(rs.getString(28));
	        		gpfSettlementNoteData.setRelieve_status(rs.getString(29));
	        		gpfSettlementNoteData.setRelieve_date(rs.getString(30));
	        		gpfSettlementNoteData.setInterest_calc_rate(rs.getDouble(31));
	        		gpfSettlementNoteData.setProcess_flow_id(rs.getString(32));
	        		gpfSettlementNoteData.setInt_tobe_calc_date(rs.getString(33));
	        		gpfSettlementNoteData.setAuth_no(rs.getInt(34));
	        		
	        		int tYr=0;
	        		int currentOpeningYear=0;
	        		boolean fiYe=false;
	            	String []tmpYr=gpfSettlementNoteData.getFinance_year().split("-");
	            	if(tmpYr.length==2){
	            		fiYe=true;
	            		int iT=Integer.parseInt(tmpYr[0]);
	            		if(tYr<iT){
	            			tYr=iT;
	            		}
	            	}
		            if(fiYe){
		            	currentOpeningYear=tYr;
		            }
		            gpfSettlementNoteData.setCurrent_opening_year(currentOpeningYear);
		            
	            }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			gpfSettlementNoteData=GpfSettlementNoteModel.setStatusData(gpfSettlementNoteData);
			
			
			
			return gpfSettlementNoteData;
	}
	
	public static GpfSettlementNoteData processCalculation(GpfSettlementNoteData gpfSettlementNoteData5){
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		String openingFinanceDate="",previousFinanceDate="";
        
        int currentOpeningYear=0,currentClosingyear=0;
       
       
		double interestCalcRate=0;
		
		int tYr=0;boolean fiYe=true;
		String tempYear="";
		tempYear =gpfSettlementNoteData5.getFinance_year().trim();
		String []tmpYr=tempYear.split("-");
		if(tmpYr.length==2){
			fiYe=true;
    		int iT=Integer.parseInt(tmpYr[0]);
    		if(tYr<iT){
    			tYr=iT;
    		}
    	}
		if(fiYe){
        	previousFinanceDate=tYr+"-"+(tYr+1);
        	openingFinanceDate=(tYr+1)+"-"+(tYr+2);
        	currentOpeningYear=tYr;
        	currentClosingyear=tYr+1;
        	System.out.println("openingFinanceDate ="+openingFinanceDate);
        	
        }	
		
		
		gpfSettlementNoteData5.setCurrent_opening_year(currentOpeningYear);
		
		System.out.println("currentOpeningYear = "+currentOpeningYear+" currentClosingyear = "+currentClosingyear);
		
		try {//interest calculation
			ps=conn1.prepareStatement("select INTEREST_RATE from HRM_GPF_MST_INT_RATE where FINANCIAL_YEAR=?");
			ps.setString(1,gpfSettlementNoteData5.getFinance_year());
            rs=ps.executeQuery();
            if(rs.next())
            {
            	interestCalcRate=rs.getDouble(1);
            	gpfSettlementNoteData5.setInterest_calc_rate(interestCalcRate);
            	System.out.println("interestCalcRate =" +interestCalcRate);
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		String currenttFinancialYear=GpfSettlementNoteModel.getCurrentFinanceYear();
		
		
		int []processedValue;
					
		processedValue=GpfSettlementNoteModel.processSettlementCalculation(gpfSettlementNoteData5.getGpf_no(),gpfSettlementNoteData5.getFinance_year(),currentOpeningYear,currenttFinancialYear);				
		
		gpfSettlementNoteData5.setCb_ac_re(processedValue[0]);
		gpfSettlementNoteData5.setSubseq_deposit_re(processedValue[1]);
		gpfSettlementNoteData5.setInt_amount_re(processedValue[2]);
		gpfSettlementNoteData5.setDep_amount_re(processedValue[3]);
		gpfSettlementNoteData5.setDep_interest_re(processedValue[4]);
		gpfSettlementNoteData5.setSubseq_withdraw_re(processedValue[5]);
		gpfSettlementNoteData5.setWithdraw_amount_re(processedValue[6]);
		gpfSettlementNoteData5.setWithdraw_interest_re(processedValue[7]);
		gpfSettlementNoteData5.setAmount_already_re(processedValue[8]);
		
		gpfSettlementNoteData5.setCb_ac_im(processedValue[9]);
		gpfSettlementNoteData5.setSubseq_deposit_im(processedValue[10]);
		gpfSettlementNoteData5.setInt_amount_im(processedValue[11]);
		gpfSettlementNoteData5.setDep_amount_im(processedValue[12]);
		gpfSettlementNoteData5.setDep_interest_im(processedValue[13]);
		gpfSettlementNoteData5.setSubseq_withdraw_im(processedValue[14]);
		gpfSettlementNoteData5.setWithdraw_amount_im(processedValue[15]);
		gpfSettlementNoteData5.setWithdraw_interest_im(processedValue[16]);
		gpfSettlementNoteData5.setAmount_already_im(processedValue[17]);
		
		gpfSettlementNoteData5.setReg_deposit_total(processedValue[18]);
		gpfSettlementNoteData5.setReg_deduct_total(processedValue[19]);
		gpfSettlementNoteData5.setIm_deposit_total(processedValue[20]);
		gpfSettlementNoteData5.setIm_deduct_total(processedValue[21]);
		
		gpfSettlementNoteData5.setReg_amt_bal_auth(processedValue[22]);
		gpfSettlementNoteData5.setImp_amt_bal_auth(processedValue[23]);
		
		setStatusData(gpfSettlementNoteData5);
		
		return gpfSettlementNoteData5;
	}
	
	public static int[] processSettlementCalculation(int gpfno,String financeYear,int currYear,String lastSlipIssuedFinYear){
		int []a=new int[24];// this value is important
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		OracleCallableStatement oracleCallStatement=null;
		try {
			oracleCallStatement=(OracleCallableStatement) conn1.prepareCall ( "{call HRM_GPF_SETTLEMENT_NOTE_PROC(?,?,?,?,?)}" );
			
			oracleCallStatement.setInt(1, gpfno);
			oracleCallStatement.setString(2, financeYear);
			oracleCallStatement.setInt(3, currYear);
			oracleCallStatement.setString(4, lastSlipIssuedFinYear);
			oracleCallStatement.registerOutParameter( 5, OracleTypes.ARRAY,"HRM_GPF_SETTLEMENT_NOTE_ARRAY");
			
			
			
			oracleCallStatement.executeUpdate();
			
			ARRAY calculatedArray=oracleCallStatement.getARRAY(5);
			Object[] arr=(Object[]) calculatedArray.getArray();
			
			for (int i = 0; i < arr.length; i++) {
				
				try {
					a[i]=Integer.parseInt(arr[i].toString().trim());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				System.out.println("row " + i + " = '" + a[i] +"'" );
				
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(oracleCallStatement!=null){
					oracleCallStatement.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return a;
		
	}
	
	
	
	public static boolean updateCalculation(GpfSettlementNoteData gpfSettlementNoteData,int authNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		boolean result=false;
		String noteGenerated="Y";
		try {
			
			ps=conn1.prepareStatement("update HRM_GPF_SETTLEMENT_NOTE set " +
					"REG_CB=? ,REG_DEPOSIT=? ,REG_INT=? ,REG_MISSING_CR_DB=? ,REG_INT_MISSING_CR_DB =?,REG_WITHDRAWALS =?,REG_EXCESS_CR_DB =? ,REG_INT_EXCESS_CR_DB =?,\n"+
					"REG_AMT_ALREADY_AUTH=?  ,IMP_REG_CB =?,IMP_REG_DEPOSIT=? ,IMP_INT=? ,IMP_MISS_CR_DB =?,IMP_INT_MISS_CR_DB =?,IMP_WITH =?,IMP_EXCESS_CR_DB =?,\n"+
					"IMP_INT_MISS_EXCESS_CR_DB =?,IMP_AMT_ALREADY_AUTH=? ,IMP_AMT_BAL_AUTH=? ,REG_AMT_BAL_AUTH=? ,REG_DEPOSIT_TOTAL =?,\n"+
					"REG_DEDUCT_TOTAL=? ,IM_DEPOSIT_TOTAL=? ,IM_DEDUCT_TOTAL=?,NOTE_GENERATED=?" +
					"where gpf_no=? " +
					"and auth_slno =?");
     
			
            
            ps.setDouble(1, gpfSettlementNoteData.getCb_ac_re());
            ps.setDouble(2, gpfSettlementNoteData.getSubseq_deposit_re());
            ps.setDouble(3, gpfSettlementNoteData.getInt_amount_re());
            ps.setDouble(4, gpfSettlementNoteData.getDep_amount_re());
            ps.setDouble(5, gpfSettlementNoteData.getDep_interest_re());
            ps.setDouble(6, gpfSettlementNoteData.getSubseq_withdraw_re());
            ps.setDouble(7, gpfSettlementNoteData.getWithdraw_amount_re());
            ps.setDouble(8, gpfSettlementNoteData.getWithdraw_interest_re());
            ps.setDouble(9, gpfSettlementNoteData.getAmount_already_re());
            ps.setDouble(10, gpfSettlementNoteData.getCb_ac_im());
            ps.setDouble(11, gpfSettlementNoteData.getSubseq_deposit_im());
            ps.setDouble(12, gpfSettlementNoteData.getInt_amount_im());
            ps.setDouble(13, gpfSettlementNoteData.getDep_amount_im());
            ps.setDouble(14, gpfSettlementNoteData.getDep_interest_im());
            ps.setDouble(15, gpfSettlementNoteData.getSubseq_withdraw_im());
            ps.setDouble(16, gpfSettlementNoteData.getWithdraw_amount_im());
            ps.setDouble(17, gpfSettlementNoteData.getWithdraw_interest_im());
            ps.setDouble(18, gpfSettlementNoteData.getAmount_already_im());
            
            
            ps.setDouble(19, gpfSettlementNoteData.getImp_amt_bal_auth());
            ps.setDouble(20, gpfSettlementNoteData.getReg_amt_bal_auth());
            ps.setDouble(21, gpfSettlementNoteData.getReg_deposit_total());
            ps.setDouble(22, gpfSettlementNoteData.getReg_deduct_total() );
            ps.setDouble(23, gpfSettlementNoteData.getIm_deposit_total());
            ps.setDouble(24, gpfSettlementNoteData.getIm_deduct_total());
           
            ps.setString(25,noteGenerated);
            ps.setInt(26, gpfSettlementNoteData.getGpf_no());
            ps.setInt(27, authNo);
            
            ps.executeUpdate();
            ps.close();
            
            result=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		
		return result;
	}
	
	public static  boolean insertGpfSettlementNoteData(GpfSettlementNoteData gpfSettlementNoteData,Timestamp updatedDate,String updatedByUserId){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String status_id="CR";
		boolean resultStatus=false;
		int authSlNo=1;
		try {
			ps=conn1.prepareStatement("select max(AUTH_SLNO) as AUTH_SLNO1 from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=?");
			ps.setInt(1,gpfSettlementNoteData.getGpf_no());
			rs=ps.executeQuery();
			if(rs.next()){
				System.out.println("insertgpfSettlementNoteData():max(AUTH_SLNO)="+rs.getInt(1));
				int tempauthSlNo=rs.getInt(1);
				authSlNo=++tempauthSlNo;
				System.out.println("insertgpfSettlementNoteData():try:authSlNo="+authSlNo);
				System.out.println("insertgpfSettlementNoteData():try:tempauthSlNo="+tempauthSlNo);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("insertgpfSettlementNoteData():authSlNo="+authSlNo);
		try {
			ps=conn1.prepareStatement("insert into HRM_GPF_SETTLEMENT_NOTE(\n"    +
							"GPF_NO, FINANCIAL_YEAR ,PROC_NO ,PROC_DATE ,SETTLEMENT_REASON ,RETIRED_OR_DIED_DATE ,PROCESS_FLOW_STATUS_ID ,UPDATED_DATE ,UPDATED_BY_USER_ID ,\n"+
							"INTEREST_CALC_RATE ,AUTH_SLNO,INT_TOBE_CALC_DATE)\n" +
							"values	(?,?,?,to_date(?,'dd-MM-yyyy') ,?,to_date(?,'dd-MM-yyyy') ,?,?,?,?,?,to_date(?,'dd-MM-yyyy'))\n");
			
            ps.setInt(1,gpfSettlementNoteData.getGpf_no());
            ps.setString(2,gpfSettlementNoteData.getFinance_year());
            ps.setString(3,gpfSettlementNoteData.getProc_no());
            ps.setString(4,gpfSettlementNoteData.getProc_date());
            ps.setString(5,gpfSettlementNoteData.getRelieve_status());
            ps.setString(6,gpfSettlementNoteData.getRelieve_date());
            ps.setString(7,status_id);
            ps.setTimestamp(8,updatedDate);
            ps.setString(9,updatedByUserId);
            ps.setDouble(10, gpfSettlementNoteData.getInterest_calc_rate()); //interest
            
            ps.setInt(11, authSlNo);
            ps.setString(12, gpfSettlementNoteData.getInt_tobe_calc_date());
            
            ps.executeUpdate();
            resultStatus=true;
            System.out.println("came here");
            ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		return resultStatus;
		
	}
	public static boolean deleteGpfSettlementNoteData(int gpfNo){
		boolean resultStatus=false;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;	
		try{	
			ps=conn1.prepareStatement("delete from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=?" +
			"and auth_slno =(select max(auth_slno ) from HRM_GPF_SETTLEMENT_NOTE where gpf_no=?)");
        	ps.setInt(1,gpfNo);
        	ps.setInt(2,gpfNo);
			int y=0;
            y=ps.executeUpdate();
            if(y>0)
            	resultStatus=true;
            
            ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		return resultStatus;
	}
	public static boolean firstGpfUpdate(GpfSettlementNoteData gpfSettlementNoteData,Timestamp updatedDate,String updatedByUserId,String statusFlowId){
		boolean resultStatus=false;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;			
		
		
		try {
			
			
			ps=conn1.prepareStatement("update HRM_GPF_SETTLEMENT_NOTE set SETTLEMENT_REASON=?,RETIRED_OR_DIED_DATE=to_date(?,'dd-MM-yyyy'),INT_TOBE_CALC_DATE=to_date(?,'dd-MM-yyyy')," +
					"PROC_NO=?,PROC_DATE=to_date(?,'dd-MM-yyyy'),FINANCIAL_YEAR=?," +
					"PROCESS_FLOW_STATUS_ID=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where gpf_no=?" +
					"and auth_slno =(select max(auth_slno ) from HRM_GPF_SETTLEMENT_NOTE where gpf_no=?)");
			
			ps.setString(1,gpfSettlementNoteData.getRelieve_status());
            ps.setString(2,gpfSettlementNoteData.getRelieve_date());
			ps.setString(3, gpfSettlementNoteData.getInt_tobe_calc_date());
			ps.setString(4,gpfSettlementNoteData.getProc_no());
            ps.setString(5,gpfSettlementNoteData.getProc_date());
            ps.setString(6,gpfSettlementNoteData.getFinance_year());
            
            ps.setString(7,statusFlowId);
            ps.setTimestamp(8,updatedDate);
            ps.setString(9,updatedByUserId);
            
            ps.setInt(10,gpfSettlementNoteData.getGpf_no());
            ps.setInt(11,gpfSettlementNoteData.getGpf_no());
            
            int y=0;
            y=ps.executeUpdate();
            if(y>0)
            	resultStatus=true;
            
            ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		updateCurrFinEndDate(gpfSettlementNoteData);
		
		return resultStatus;
	}
	public static boolean nextGpfUpdate(GpfSettlementNoteData gpfSettlementNoteData,Timestamp updatedDate,String updatedByUserId,String statusFlowId){
		boolean resultStatus=false;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;	
		try {
			
			ps=conn1.prepareStatement("update HRM_GPF_SETTLEMENT_NOTE set SETTLEMENT_REASON=?,RETIRED_OR_DIED_DATE=to_date(?,'dd-MM-yyyy'),INT_TOBE_CALC_DATE=to_date(?,'dd-MM-yyyy')," +						
					"PROCESS_FLOW_STATUS_ID=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where gpf_no=?" +
					"and auth_slno =(select max(auth_slno ) from HRM_GPF_SETTLEMENT_NOTE where gpf_no=?)");
			
			ps.setString(1,gpfSettlementNoteData.getRelieve_status());
            ps.setString(2,gpfSettlementNoteData.getRelieve_date());
			ps.setString(3, gpfSettlementNoteData.getInt_tobe_calc_date());
			
            
            ps.setString(4,statusFlowId);
            ps.setTimestamp(5,updatedDate);
            ps.setString(6,updatedByUserId);
            
            ps.setInt(7,gpfSettlementNoteData.getGpf_no());
            ps.setInt(8,gpfSettlementNoteData.getGpf_no());
            
            int y=0;
            y=ps.executeUpdate();
            if(y>0)
            	resultStatus=true;
            
            ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		updateCurrFinEndDate(gpfSettlementNoteData);
		
		return resultStatus;
	}
	private static boolean updateCurrFinEndDate(GpfSettlementNoteData gpfSettlementNoteData){
		boolean resultStatus=false;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;	
		
		String currFinEndDate="";
		
		int totalRecords=GpfSettlementNoteModel.calcTotalfinancialYearInSettlement(gpfSettlementNoteData);
		if(gpfSettlementNoteData.getAuth_no()==totalRecords){
			currFinEndDate=gpfSettlementNoteData.getInt_tobe_calc_date();
		}else{			
			String []arrStr=gpfSettlementNoteData.getFinance_year().split("-");
			try {
				currFinEndDate="31/03"+arrStr[1].trim();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		try {
			ps=conn1.prepareStatement("update HRM_GPF_SETTLEMENT_NOTE set CURRENT_FIN_END_DATE=to_date(?,'dd-MM-yyyy')" +
					"where gpf_no=? and AUTH_SLNO=?");
			ps.setString(1,currFinEndDate);
			ps.setInt(2, gpfSettlementNoteData.getGpf_no());
			ps.setInt(3, gpfSettlementNoteData.getAuth_no());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return resultStatus;
	}
	public  static String[] convertDateToMnthAndYr(String fullDate){
		String []arr=new String[2];
		String []tmpArr=fullDate.split("/");
		for (int i = 1; i < tmpArr.length; i++) {
			arr[i-1]=tmpArr[i];
		}			
		return arr;
	}
	public static String convertMnthAndYrToDate(String month,String year){
		int Idate=0;
		int Imonth=0;
		String date="28";
		try {
			Imonth=Integer.parseInt(month.trim());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		if(Imonth==1){
			date="31";
		}
		else if(Imonth==2){
			int yr=0;
			try {
				yr=Integer.parseInt(year.trim());
				if(yr%4==0){
					date="29";
				}
				else{
					date="28";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		else if(Imonth==3){
			date="31";
		}
		else if(Imonth==4){
			date="30";
		}
		else if(Imonth==5){
			date="31";
		}
		else if(Imonth==6){
			date="30";
		}
		else if(Imonth==7){
			date="31";
		}
		else if(Imonth==8){
			date="31";
		}
		else if(Imonth==9){
			date="30";
		}
		else if(Imonth==10){
			date="31";
		}
		else if(Imonth==11){
			date="30";
		}
		else if(Imonth==12){
			date="31";
		}
		
		String fullDate="";			
		fullDate=date+"/"+month+"/"+year;
		
		return fullDate;
	}
	
	
	public static String xmlCalculation(GpfSettlementNoteData gpfSettlementNoteData1){
		String xml="";        	
    	xml+="<cb_ac_re>"+(int)gpfSettlementNoteData1.getCb_ac_re()+"</cb_ac_re>";
		xml+="<subseq_deposit_re>"+(int)gpfSettlementNoteData1.getSubseq_deposit_re()+"</subseq_deposit_re>";
		xml+="<int_amount_re>"+(int)gpfSettlementNoteData1.getInt_amount_re()+"</int_amount_re>";
		xml+="<dep_amount_re>"+(int)gpfSettlementNoteData1.getDep_amount_re()+"</dep_amount_re>";
		xml+="<dep_interest_re>"+(int)gpfSettlementNoteData1.getDep_interest_re()+"</dep_interest_re>";
		xml+="<subseq_withdraw_re>"+(int)gpfSettlementNoteData1.getSubseq_withdraw_re()+"</subseq_withdraw_re>";
		xml+="<withdraw_amount_re>"+(int)gpfSettlementNoteData1.getWithdraw_amount_re()+"</withdraw_amount_re>";
		xml+="<withdraw_interest_re>"+(int)gpfSettlementNoteData1.getWithdraw_interest_re()+"</withdraw_interest_re>";
		xml+="<amount_already_re>"+(int)gpfSettlementNoteData1.getAmount_already_re()+"</amount_already_re>";
		xml+="<cb_ac_im>"+(int)gpfSettlementNoteData1.getCb_ac_im()+"</cb_ac_im>";
		xml+="<subseq_deposit_im>"+(int)gpfSettlementNoteData1.getSubseq_deposit_im()+"</subseq_deposit_im>";
		xml+="<int_amount_im>"+(int)gpfSettlementNoteData1.getInt_amount_im()+"</int_amount_im>";
		xml+="<dep_amount_im>"+(int)gpfSettlementNoteData1.getDep_amount_im()+"</dep_amount_im>";
		xml+="<dep_interest_im>"+(int)gpfSettlementNoteData1.getDep_interest_im()+"</dep_interest_im>";
		xml+="<subseq_withdraw_im>"+(int)gpfSettlementNoteData1.getSubseq_withdraw_im()+"</subseq_withdraw_im>";
		xml+="<withdraw_amount_im>"+(int)gpfSettlementNoteData1.getWithdraw_amount_im()+"</withdraw_amount_im>";
		xml+="<withdraw_interest_im>"+(int)gpfSettlementNoteData1.getWithdraw_interest_im()+"</withdraw_interest_im>";
		xml+="<amount_already_im>"+(int)gpfSettlementNoteData1.getAmount_already_im()+"</amount_already_im>";
		
		
		xml+="<imp_amt_bal_auth>"+(int)gpfSettlementNoteData1.getImp_amt_bal_auth()+"</imp_amt_bal_auth>";			
		xml+="<reg_amt_bal_auth>"+(int)gpfSettlementNoteData1.getReg_amt_bal_auth()+"</reg_amt_bal_auth>";			
		xml+="<reg_deposit_total>"+(int)gpfSettlementNoteData1.getReg_deposit_total()+"</reg_deposit_total>";
		xml+="<reg_deduct_total>"+(int)gpfSettlementNoteData1.getReg_deduct_total()+"</reg_deduct_total>";
		xml+="<im_deposit_total>"+(int)gpfSettlementNoteData1.getIm_deposit_total()+"</im_deposit_total>";
		xml+="<im_deduct_total>"+(int)gpfSettlementNoteData1.getIm_deduct_total()+"</im_deduct_total>";
		
		//data load
		xml+="<note_generated>"+gpfSettlementNoteData1.getNote_generated()+"</note_generated>";
		
		
		xml+="<current_opening_year>"+gpfSettlementNoteData1.getCurrent_opening_year()+"</current_opening_year>";
		xml+="<last_finance_year>"+gpfSettlementNoteData1.getLast_finance_year()+"</last_finance_year>";
		xml+="<current_fin_end_date>"+gpfSettlementNoteData1.getCurrent_fin_end_date()+"</current_fin_end_date>";
		
		System.out.println("xmlCalculation()="+xml);
		
		return xml;
		
	}
	public static String xmlCalculationCheck(GpfSettlementNoteData gpfSettlementNoteData1){
		String xml="";
		
		xml+="<missing_interest_data>"+gpfSettlementNoteData1.getMissing_interest_data()+"</missing_interest_data>";
		xml+="<interest_rate_data>"+gpfSettlementNoteData1.getInterest_rate_data()+"</interest_rate_data>";
		xml+="<current_fin_end_date>"+gpfSettlementNoteData1.getCurrent_fin_end_date()+"</current_fin_end_date>";
		
		return xml;
	}
	public static String xmlEmployee(GpfSettlementNoteData gpfSettlementNoteData){
		String xml="";
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION,e.EMPLOYEE_ID FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.GPF_NO=?");
            ps.setInt(1,gpfSettlementNoteData.getGpf_no());
            rs=ps.executeQuery();
            if(rs.next()) {
              
                xml+="<emp_name>"+rs.getString(1)+" </emp_name>";
                xml+="<date_of_birth>"+rs.getString(2)+" </date_of_birth>";
                xml+="<gpf_no>"+rs.getString(3)+" </gpf_no>";
                xml+="<designation>"+rs.getString(4)+" </designation>";
                xml+="<emp_id>"+rs.getString(5)+" </emp_id>";
                
                }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("xmlGetBasicData()="+xml);
		
		
		return xml;
	}
	
	public static String xmlSettlement(GpfSettlementNoteData gpfSettlementNoteData){
		
		String xml="";
		
		//xml+="<proc_no>"+gpfSettlementNoteData.getProc_no()+"</proc_no>";	
		//xml+="<proc_date>"+gpfSettlementNoteData.getProc_date()+"</proc_date>";
		//xml+="<relieve_status>"+gpfSettlementNoteData.getRelieve_status()+"</relieve_status>";
		//xml+="<relieve_date>"+gpfSettlementNoteData.getRelieve_date()+"</relieve_date>";							
		xml+="<status_id>"+gpfSettlementNoteData.getProcess_flow_id()+"</status_id>";			
		//xml+="<missing_interest_data>"+gpfSettlementNoteData.getMissing_interest_data()+"</missing_interest_data>";
		//xml+="<interest_rate_data>"+gpfSettlementNoteData.getInterest_rate_data()+"</interest_rate_data>";
		xml+="<count_rows>"+gpfSettlementNoteData.getCount_rows()+"</count_rows>";
		//xml+="<auth_no>"+gpfSettlementNoteData.getAuth_no()+"</auth_no>";
		//xml+="<current_opening_year>"+gpfSettlementNoteData.getCurrent_opening_year()+"</current_opening_year>";
		
		if(GpfSettlementNoteModel.countValidatedSettlements(gpfSettlementNoteData.getGpf_no())==0){    			
			xml=xml+"<firstGpf>yes</firstGpf>";
		}else{
			xml=xml+"<firstGpf>no</firstGpf>";
		}
		System.out.println("xmlSettlementSchema()="+xml);
		return xml;
	}
	public static String xmlRelieveDetails(GpfSettlementNoteData gpfSettlementNoteData){
		String xml="";
		xml+="<relieve_status>"+gpfSettlementNoteData.getRelieve_status()+"</relieve_status>";
		xml+="<relieve_date>"+gpfSettlementNoteData.getRelieve_date()+"</relieve_date>";	
		xml=xml+"<listYear>"+GpfSettlementNoteModel.xmlLoadListYear(gpfSettlementNoteData.getRelieve_date())+"</listYear>";
		xml=xml+"<selectedYear>"+GpfSettlementNoteModel.getSelectedInterestYear(gpfSettlementNoteData)+"</selectedYear>";
		xml=xml+"<listMonth>"+GpfSettlementNoteModel.xmlLoadListMonth(gpfSettlementNoteData.getRelieve_date(), GpfSettlementNoteModel.getSelectedInterestYear(gpfSettlementNoteData))+"</listMonth>"; 
		xml=xml+"<selectedMonth>"+GpfSettlementNoteModel.getSelectedInterestMonth(gpfSettlementNoteData)+"</selectedMonth>";
		xml=xml+"<int_tobe_calc_date>"+gpfSettlementNoteData.getInt_tobe_calc_date()+"</int_tobe_calc_date>";
		return xml;
	}
	public static String xmlFirstRelieveDetails(GpfSettlementNoteData gpfSettlementNoteData){
		String xml="";
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String empStatusIdStr="";
		String dateOfBirthStr="";
		String relievedDate="";
		
		System.out.println("inside function");
		try {
			ps=conn1.prepareStatement("select A.EMPLOYEE_STATUS_ID,to_char(B.DATE_OF_BIRTH,'dd/mm/yyyy') as DATE_OF_BIRTH,to_char(A.DATE_EFFECTIVE_FROM,'dd/mm/yyyy') as DATE_EFFECTIVE_FROM " +
					"from HRM_EMP_CURRENT_POSTING A ,hrm_mst_employees B where " +					
					"B.GPF_NO =? and B.employee_id = A.employee_id");
            ps.setInt(1,gpfSettlementNoteData.getGpf_no());
            rs=ps.executeQuery();
            if(rs.next()) {
            	empStatusIdStr=rs.getString(1).trim();
            	dateOfBirthStr=rs.getString(2).trim();  
            	relievedDate=rs.getString(3).trim();
           }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("empStatusIdStr-------------------->"+empStatusIdStr);
		if(empStatusIdStr.equalsIgnoreCase("SAN")){
			xml+="<firstRelieveStatus>SAN</firstRelieveStatus>";
			xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
		}else if(empStatusIdStr.equalsIgnoreCase("DTH")){
			xml+="<firstRelieveStatus>DTH</firstRelieveStatus>";
			xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
		}else if(empStatusIdStr.equalsIgnoreCase("VRS")){
			xml+="<firstRelieveStatus>VRS</firstRelieveStatus>";
			xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
		}else {
			String futureRetiredDate="";
			java.sql.Date sqlDate=null;
			
			try {
				ps=conn1.prepareStatement("select to_char(A.RETIREDATE,'dd/mm/yyyy') as RETIREDATE  from ALLRETIREMENTVIEW A,HRM_MST_EMPLOYEES B" +
						"where A.EMPLOYEE_ID = b.employee_id and b.gpf_no=?");
				
	            ps.setInt(1,gpfSettlementNoteData.getGpf_no());
	            
	            rs=ps.executeQuery();
	            if(rs.next()) {
	            	futureRetiredDate=rs.getString(1).trim(); 
	            	
	           }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			String STR_FORMAT="dd/MM/yyyy";
			SimpleDateFormat smpFormat=new SimpleDateFormat(STR_FORMAT);
			Calendar c1= Calendar.getInstance();
			System.out.println("today is ="+smpFormat.format(c1.getTime()));
			String todaysDate=smpFormat.format(c1.getTime());
			
			String []tmpArray=futureRetiredDate.split("/");
			String []todayArray=todaysDate.split("/");
			
			String elseRelieveDate="";
			
			int todayMonth=-1,todayYear=-2,relievedMonth=-3,relievedyear=-4;
			try {
				todayMonth=Integer.parseInt(todayArray[1].trim());
				todayYear=Integer.parseInt(todayArray[2].trim());
				
				relievedMonth=Integer.parseInt(tmpArray[1].trim());
				relievedyear=Integer.parseInt(tmpArray[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
			if((todayMonth==relievedMonth) && (todayYear==relievedyear) ){
				xml+="<firstRelieveStatus>ThisMonthSAN</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}else{
				xml+="<firstRelieveStatus>NONE</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
			
				
		}
		
		
		return xml;
	}
	public static String xmlAuthorisationDetails(GpfSettlementNoteData gpfSettlementNoteData){
		String xml="";
		xml+="<proc_no>"+gpfSettlementNoteData.getProc_no()+"</proc_no>";	
		xml+="<proc_date>"+gpfSettlementNoteData.getProc_date()+"</proc_date>";
		xml+="<auth_no>"+gpfSettlementNoteData.getAuth_no()+"</auth_no>";
		xml=xml+"<listFinanceYear>"+GpfSettlementNoteModel.xmlFinanceYear(gpfSettlementNoteData)+"</listFinanceYear>"; 
		xml=xml+"<selectedFinanceYear>"+GpfSettlementNoteModel.getSelectedFinanceYear(gpfSettlementNoteData)+"</selectedFinanceYear>";
		xml=xml+"<totalRecords>"+GpfSettlementNoteModel.countSettlementRecord(gpfSettlementNoteData.getGpf_no())+"</totalRecords>";
		xml+="<finance_year>"+gpfSettlementNoteData.getFinance_year().trim()+"</finance_year>";
		
		return xml;
	}
	public static String xmlLoadListYear(String relvDate){
		String xml="";
		String []tmpYr=relvDate.split("/");
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		if((mnthInt==10)||(mnthInt==11)||(mnthInt==12)){
			xml+="<year>"+yrInt+"</year>";
			xml+="<year>"+(yrInt+1)+"</year>";
		}
		else if((mnthInt>=1)||(mnthInt<=9)){
			xml+="<year>"+yrInt+"</year>";
		}
		
		return xml;
	}
	public static String xmlLoadListMonth(String relvDate,String Year){
		String xml="";
		
		String []tmpYr=relvDate.split("/");
		int mnthInt=0;
		int yrInt=0;
		int sentinel=0;
		int currentYear=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
				currentYear=Integer.parseInt(Year.trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if((mnthInt==10)||(mnthInt==11)||(mnthInt==12)){
			sentinel=1;
		}
		else if((mnthInt>=1)||(mnthInt<=9)){
			sentinel=2;
		}
		if(sentinel==1){
			if(mnthInt==10){
				if(currentYear==yrInt){
					xml+="<month><monthName>10</monthName><monthValue>"+monthInString(10)+"</monthValue></month>";
					xml+="<month><monthName>11</monthName><monthValue>"+monthInString(11)+"</monthValue></month>";
					xml+="<month><monthName>12</monthName><monthValue>"+monthInString(12)+"</monthValue></month>";
				}
				else {
					xml+="<month><monthName>1</monthName><monthValue>"+monthInString(1)+"</monthValue></month>";
				}
				
				
			}
			if(mnthInt==11){
				if(currentYear==yrInt){
					xml+="<month><monthName>11</monthName><monthValue>"+monthInString(11)+"</monthValue></month>";
					xml+="<month><monthName>12</monthName><monthValue>"+monthInString(12)+"</monthValue></month>";
				}
				else {
					xml+="<month><monthName>1</monthName><monthValue>"+monthInString(1)+"</monthValue></month>";
					xml+="<month><monthName>2</monthName><monthValue>"+monthInString(2)+"</monthValue></month>";
				}
				
				
			}
			if(mnthInt==12){
				if(currentYear==yrInt){
					xml+="<month><monthName>12</monthName><monthValue>"+monthInString(12)+"</monthValue></month>";
				}
				else {
					xml+="<month><monthName>1</monthName><monthValue>"+monthInString(1)+"</monthValue></month>";
					xml+="<month><monthName>2</monthName><monthValue>"+monthInString(2)+"</monthValue></month>";
					xml+="<month><monthName>3</monthName><monthValue>"+monthInString(3)+"</monthValue></month>";
				}

				
			}
			
		}
		else if(sentinel==2){
			xml+="<month><monthName>"+(mnthInt)+"</monthName><monthValue>"+monthInString(mnthInt)+"</monthValue></month>";
			xml+="<month><monthName>"+(mnthInt+1)+"</monthName><monthValue>"+monthInString(mnthInt+1)+"</monthValue></month>";
			xml+="<month><monthName>"+(mnthInt+2)+"</monthName><monthValue>"+monthInString(mnthInt+2)+"</monthValue></month>";
			xml+="<month><monthName>"+(mnthInt+3)+"</monthName><monthValue>"+monthInString(mnthInt+3)+"</monthValue></month>";
			
		}
		System.out.println("xmlLoadListMonth="+xml);
		return xml;
	}
	public static String xmlFinanceYear(GpfSettlementNoteData gpfSettlementNoteData9){
		String xml="";
		
		List<String> financialYearArrayList=GpfSettlementNoteModel.financialYearArray(gpfSettlementNoteData9);
		for (Iterator<String> iterator = financialYearArrayList.iterator(); iterator.hasNext();) {
			String str1 = (String) iterator.next();
			xml+="<financeYear>"+str1+"</financeYear>";
		}		
		
		
		return xml;
	}
	public static List<String> financialYearArray(GpfSettlementNoteData gpfSettlementNoteData9){		
		List<String> lst=new ArrayList<String>();
		
		
		String currFinYear=getCurrentFinanceYear();
		String []tmpYr=gpfSettlementNoteData9.getRelieve_date().split("/");
		String []tmpCurrFin=currFinYear.split("-");
		
		int startyear=0;
		int endYear=0;
		
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("Year=  ...."+yrInt);
		System.out.println("month=  ...."+mnthInt);
		
		if(mnthInt>=1 && mnthInt<=4){
			endYear=(yrInt-1);
			System.out.println("got here 1");
		}
		else if(mnthInt>=5 && mnthInt<=12){
			System.out.println("got here 2");
			endYear=(yrInt);
		}
		System.out.println("Year434=  ...."+endYear);
		try {
			startyear=Integer.parseInt(tmpCurrFin[0].trim());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(startyear>endYear){
			lst.add(startyear+"-"+(startyear+1));
		}else{
			if (startyear!=0) {
				for (int i = startyear; i <= endYear; i++) {
					lst.add(i+"-"+(i+1));
				}
			}
			
		}
		System.out.println(" startyear= "+startyear+" endYear= "+endYear);
		
		return lst;		
	}
	public static String xmlLoadMasterForms(int gpfNo){
		String xml="";
		String html="";
		GpfSettlementNoteData gpfSettlementNoteData12=null;
		System.out.println("xmlLoadMasterForms:GPFNo="+gpfNo);
		int cnt=countSettlementRecordWithoutParValidation(gpfNo);
		xml+="<loadedMasterForm>";
		System.out.println("count:"+cnt);
		
		for (int r = 1; r <=cnt; r++) {
			gpfSettlementNoteData12= GpfSettlementNoteModel.getGpfDetails(gpfNo, r);
			xml+="<record>";
			xml+="<authSlNo>"+gpfSettlementNoteData12.getAuth_no()+"</authSlNo>";
			xml+="<rlvReason>"+gpfSettlementNoteData12.getRelieve_status()+"</rlvReason>";
			xml+="<rlvDate>"+gpfSettlementNoteData12.getRelieve_date()+"</rlvDate>";
			xml+="<interestDate>"+gpfSettlementNoteData12.getInt_tobe_calc_date()+"</interestDate>";
			xml+="<finYear>"+gpfSettlementNoteData12.getFinance_year()+"</finYear>";
			xml+="<procNo>"+gpfSettlementNoteData12.getProc_no()+"</procNo>";
			xml+="<procDate>"+gpfSettlementNoteData12.getProc_date()+"</procDate>";
			xml+="<statusId>"+gpfSettlementNoteData12.getProcess_flow_id()+"</statusId>";
			xml+="</record>";
		}
		xml+="</loadedMasterForm>";
		System.out.println("xmlLoadMasterForms():"+html);
		return xml;
	}
	public static int calcTotalfinancialYearInSettlement(GpfSettlementNoteData gpfSettlementNoteData9){
		int resultT=0;
		resultT=GpfSettlementNoteModel.financialYearArray(gpfSettlementNoteData9).size();
		
		return resultT;
	}
	
	private static String monthInString(int mnth){
		String tmp="";
		if(mnth==1){
			tmp="January";
		}
		else if(mnth==2){
			tmp="February";
		}
		else if(mnth==3){
			tmp="March";
		}
		else if(mnth==4){
			tmp="April";
		}
		else if(mnth==5){
			tmp="May";
		}
		else if(mnth==6){
			tmp="June";
		}
		else if(mnth==7){
			tmp="July";
		}
		else if(mnth==8){
			tmp="August";
		}
		else if(mnth==9){
			tmp="September";
		}
		else if(mnth==10){
			tmp="October";
		}
		else if(mnth==11){
			tmp="November";
		}
		else if(mnth==12){
			tmp="December";
		}

		return tmp;
	}
	/*
	 * check current status of Given GPF number..  
	 * 0 for NO GPF in settlement table
	 * 1 for gpf process validated
	 * 2 for gpf process edited
	 * 3 for gpf calculation validated
	 * 4 for gpf settlement Note is finished
	 */
	public static int checkSettlementGpf(int gpfNo ){
		System.out.println("checkSettlementGpf() entry");
		boolean bl=false;
		int flagStatus=0;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		GpfSettlementNoteData gpfSettlementNoteData9=GpfSettlementNoteModel.getGpfDetails(gpfNo);
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement("select a.auth_slno,a.PROCESS_FLOW_STATUS_ID from(" +
					"  (select PROCESS_FLOW_STATUS_ID,auth_slno,gpf_no from HRM_GPF_SETTLEMENT_NOTE where gpf_no=?)a" +
					"   join" +
					"  (select max(auth_slno)as auth_slno1,gpf_no from HRM_GPF_SETTLEMENT_NOTE group by gpf_no )b" +
					"  on a.auth_slno=b.auth_slno1 and a.gpf_no=b.gpf_no" +
					")");
			ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next())
            {
            	System.out.println("process flow "+rs.getString(2));
            	flagStatus=2;
            	if(rs.getString(2).equalsIgnoreCase("FR")){
            		flagStatus=1;
            	}	
            }
            else{
            	flagStatus=0;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
            
            if(flagStatus==1){
            	try {
            		ps=conn1.prepareStatement("select a.NOTE_GENERATED from(" +
    						"  (select NOTE_GENERATED,auth_slno,gpf_no from HRM_GPF_SETTLEMENT_NOTE where gpf_no=?)a" +
    						"   join" +
    						"  (select max(auth_slno)as auth_slno1,gpf_no from HRM_GPF_SETTLEMENT_NOTE group by gpf_no )b" +
    						"  on a.auth_slno=b.auth_slno1 and a.gpf_no=b.gpf_no" +
    						")");
            		ps.setInt(1,gpfNo);
    	            rs=ps.executeQuery();
    	            if(rs.next()){
    	            	String se=rs.getString(1).trim();
    	            	if(se.equalsIgnoreCase("Y")){
    	            		flagStatus=3;
    	            	}
    	            }
				} catch (Exception e) {
					// TODO: handle exception
				}
				finally{
					try {
						if(rs!=null){
							rs.close();
						}				
					} catch (Exception e2) {
						// TODO: handle exception
					}
					try {				
						if(ps!=null){
							ps.close();
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
            }
            if(flagStatus==3){
            	int totNoteGenerated=0;
            	try {
            		ps=conn1.prepareStatement("select count(NOTE_GENERATED) from HRM_GPF_SETTLEMENT_NOTE where gpf_no=? and NOTE_GENERATED='Y' " );
                	ps.setInt(1,gpfNo);
                	rs=ps.executeQuery();
                	 if(rs.next()){
                		 totNoteGenerated=rs.getInt(1);
                	 }
				} catch (Exception e) {
					// TODO: handle exception
				}
				finally{
					try {
						if(rs!=null){
							rs.close();
						}				
					} catch (Exception e2) {
						// TODO: handle exception
					}
					try {				
						if(ps!=null){
							ps.close();
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
            	
            	 if(totNoteGenerated==calcTotalfinancialYearInSettlement(gpfSettlementNoteData9)){
            		 flagStatus=4;
            	 }
            }
            
		
		
		return flagStatus;
	}
	public static int checkSettlementGpf(int gpfNo,int authNo ){
		System.out.println("checkSettlementGpf() entry");
		boolean bl=false;
		int flagStatus=0;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement("select auth_slno,PROCESS_FLOW_STATUS_ID from HRM_GPF_SETTLEMENT_NOTE where gpf_no=? and auth_slno=?");
			ps.setInt(1,gpfNo);
			ps.setInt(2, authNo);
            rs=ps.executeQuery();
            if(rs.next())
            {
            	System.out.println("process flow "+rs.getString(2));
            	flagStatus=2;
            	if(rs.getString(2).equalsIgnoreCase("FR")){
            		flagStatus=1;
            	}	
            }
            else{
            	flagStatus=0;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
            
            if(flagStatus==1){
            	try {
            		ps=conn1.prepareStatement("select a.NOTE_GENERATED from HRM_GPF_SETTLEMENT_NOTE where gpf_no=? and auth_slno=?" );
            		ps.setInt(1,gpfNo);
            		ps.setInt(2, authNo);
    	            rs=ps.executeQuery();
    	            if(rs.next()){
    	            	String se=rs.getString(1).trim();
    	            	if(se.equalsIgnoreCase("Y")){
    	            		flagStatus=3;
    	            	}
    	            }
				} catch (Exception e) {
					// TODO: handle exception
				}
				finally{
					try {
						if(rs!=null){
							rs.close();
						}				
					} catch (Exception e2) {
						// TODO: handle exception
					}
					try {				
						if(ps!=null){
							ps.close();
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
            }
        
		return flagStatus;
	}
	public static boolean checkGpf(int gpfNo ){
		System.out.println("checkGpf() entry");
		boolean bl=false;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement("select gpf_no from HRM_MST_EMPLOYEES where gpf_no=?");
            ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next())
            {
            		bl=true;
            		System.out.println("checkSettlementGpf() value "+bl +" :gpf value  exist");
            }
            else{
            	System.out.println("checkSettlementGpf() value "+bl +" :gpf value doesnt exist");
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		return bl;
	}
	public  static int countValidatedSettlements(int gpfNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		int countValidated=0;
		try {
			ps=conn1.prepareStatement("select count(AUTH_SLNO) from HRM_GPF_SETTLEMENT_NOTE where gpf_no=? and  process_flow_status_id='FR'");
			ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()){
            	countValidated=rs.getInt(1);
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return countValidated;
	}
	public static int countSettlementRecordWithoutParValidation(int gpfNo){
		int countSettlements=0;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			 ps=conn1.prepareStatement("select count(AUTH_SLNO) from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=?  group by GPF_NO");
			 ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()){
            	countSettlements=rs.getInt(1);
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("countSettlementRecord="+countSettlements+";;gpfNo="+gpfNo);	
		
		
		return countSettlements;
	}
	public static int countSettlementRecord(int gpfNo){
		int countSettlements=0;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			 ps=conn1.prepareStatement("select count(AUTH_SLNO) from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=? and PROCESS_FLOW_STATUS_ID='FR' group by GPF_NO");
			 ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()){
            	countSettlements=rs.getInt(1);
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("countSettlementRecord="+countSettlements+";;gpfNo="+gpfNo);	
		
		
		return countSettlements;
	}
	public static int checkFinancialYear(int gpfNo,String financeYear){
		/*
		 * -1 for invalid
		 * 0 for success
		 * 1 for redundant in HRM_GPF_SETTLEMENT_NOTE
		 * 2 for not allowed (financial year is not added)
		 */
		int resultInt=0;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=conn1.prepareStatement("select FINANCIAL_YEAR from HRM_GPF_SETTLEMENT_NOTE where PROCESS_FLOW_STATUS_ID='FR' and gpf_no=? and FINANCIAL_YEAR=?");
			ps.setInt(1,gpfNo);
			ps.setString(2, financeYear.trim());
            rs=ps.executeQuery();
            
            
            if(rs.next()){
            	resultInt=1;
            	return resultInt; 
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		if(financeYear.length()==9){
			String []tmpArr=financeYear.split("-");
			if(tmpArr.length==2){
				try {
					Integer.parseInt(tmpArr[0]);
					Integer.parseInt(tmpArr[1]);
				} catch (Exception e) {
					// TODO: handle exception
					resultInt=-1;
				}
			}
			else {
				resultInt=-1;
			}
		}else {
			resultInt=-1;
		}
		
		
		
		return resultInt;
	}
	public static int checkGpfRelieveStatus(int gpfNo){
		/*
		 * 0 for failure
		 * 1 for success
		 */
		int resultInt=0;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		System.out.println("checkGpfRelieveStatus():gpfno="+gpfNo);
		try {
			
			ps=conn1.prepareStatement("select a.EMPLOYEE_STATUS_ID from \n" +
					"(select employee_id,EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID in ('DTH','SAN','VRS','RES','MEV','CMR','DIS'))a \n" +
					"join \n" +
					"(select gpf_no,employee_id  from HRM_MST_EMPLOYEES where gpf_no=?)b \n" +
					"on a.employee_id=b.employee_id \n" );
			ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            System.out.println("checkGpfRelieveStatus():EMPLOYEE_STATUS_ID=...");
            if(rs.next()){
            	System.out.println("checkGpfRelieveStatus():EMPLOYEE_STATUS_ID="+rs.getString(1));
            	resultInt=1;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return resultInt;
	}
	public static int checkGeneratedNote(int gpfNo,int authNo){
		/*
		 * 0 for NOTE_GENERATED='N'
		 * 1 for NOTE_GENERATED='y'
		 */
		int resultInt=0;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			
			ps=conn1.prepareStatement("select NOTE_GENERATED from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=? and AUTH_SLNO=?");
			ps.setInt(1,gpfNo);
			ps.setInt(2,authNo);
            rs=ps.executeQuery();
            if(rs.next()){
            	if(rs.getString(1).trim().equalsIgnoreCase("Y")){
            		resultInt=1;
            	}
            	else{
            		resultInt=0;
            	}
            	
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		return resultInt;
	}
	public static int getLastAuthNoWithFR(int gpfNo){
		int resultInt=0;
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			
			ps=conn1.prepareStatement("select max(AUTH_SLNO) from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=? and PROCESS_FLOW_STATUS_ID='FR' ");
			ps.setInt(1,gpfNo);
			
            rs=ps.executeQuery();
            if(rs.next()){
            	resultInt=rs.getInt(1);
            	
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	
		
		return resultInt;
	}
	public static double getInterestRate(String financeYear){
		double resultInt=0;
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			
			ps=conn1.prepareStatement("select INTEREST_RATE from HRM_GPF_MST_INT_RATE where FINANCIAL_YEAR=?");
			ps.setString(1,financeYear);
			
            rs=ps.executeQuery();
            if(rs.next()){
            	resultInt=rs.getDouble(1);
            	
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return resultInt;
	}
	public static String getCurrentFinanceYear(){
		String resFinYear="";
		
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String tempFinyear="";
		int closeYr=0;
		String []splStr=null;
		
		try {
			
			ps=conn1.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED");			
			
            rs=ps.executeQuery();
            if(rs.next()){
            	splStr=rs.getString(1).trim().split("-");
            	if(splStr.length==2){
            		try {
            			closeYr=Integer.parseInt(splStr[1].trim());
					} catch (Exception e) {
						// TODO: handle exception
					}
            	}
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		resFinYear=closeYr+"-"+(closeYr+1);
		System.out.println("getCurrentFinanceYear()::current finance year= "+tempFinyear);
		
		
		return resFinYear;
	}
	public static String getSelectedInterestYear(GpfSettlementNoteData gpfSettlementNoteData){
		String selectyear="";
		
		String []tmpYr=gpfSettlementNoteData.getInt_tobe_calc_date().split("/");
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
				selectyear=tmpYr[2];
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return selectyear;
	}
	public static String getSelectedInterestMonth(GpfSettlementNoteData gpfSettlementNoteData){
		String selectMonth="";
		
		String []tmpYr=gpfSettlementNoteData.getInt_tobe_calc_date().split("/");
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
				selectMonth=(mnthInt+"").trim();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return selectMonth;			
	}
	public static String getSelectedFinanceYear(GpfSettlementNoteData gpfSettlementNoteData){
		String selectFinanceYear="";		
		selectFinanceYear=gpfSettlementNoteData.getFinance_year();
		return selectFinanceYear;
	}
	public static String getLastProcessFlowStatusId(int gpfNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String processStatus="";
		try {			
			ps=conn1.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=?" +
			"and auth_slno =(select max(auth_slno ) from HRM_GPF_SETTLEMENT_NOTE where gpf_no=?)");
	
			ps.setInt(1,gpfNo);
			ps.setInt(2,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()){
        		processStatus=rs.getString(1).trim();
        	}            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return processStatus;		
	}
	public static String getProcessFlowStatusId(int gpfNo,int authNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String processStatus="";
		try {			
			ps=conn1.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_GPF_SETTLEMENT_NOTE where GPF_NO=? and AUTH_SLNO=?");
	
			ps.setInt(1,gpfNo);
			ps.setInt(2,authNo);
            rs=ps.executeQuery();
            if(rs.next()){
        		processStatus=rs.getString(1).trim();
        	}            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		processStatus=processStatus.trim();
		return processStatus;		
	}
	public static int countGpfSettlementNote(int gpfNo){
		Connection conn1=GpfSettlementNoteModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		int count=0;
		try {			
			ps=conn1.prepareStatement("select count(*) from hrm_gpf_settlement_note where gpf_no=? group by gpf_no ");
	
			ps.setInt(1,gpfNo);			
            rs=ps.executeQuery();
            if(rs.next()){
            	count=rs.getInt(1);
        	}            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		
		return count;
	}


}
