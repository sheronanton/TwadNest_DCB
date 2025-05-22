package Servlets.HR.HR1.EmployeeMaster.servlets;


import java.util.List;

public class GpfSettlementNoteData {
	
	public GpfSettlementNoteData() {
		// TODO Auto-generated constructor stub
	}
	
	int gpf_no;
	String proc_no;
	String proc_date;
	String relieve_status;
	String relieve_date;
	String int_tobe_calc_date;
	
	private String finance_year;
	
	private int current_opening_year;
	private double cb_ac_re;
	private double subseq_deposit_re;
	private double int_amount_re;
	private double dep_amount_re;
	private double dep_interest_re;
	private double subseq_withdraw_re;
	private double withdraw_amount_re;
	private double withdraw_interest_re;
	private double amount_already_re;
	private double cb_ac_im;
	private double subseq_deposit_im;
	private double int_amount_im;
	private double dep_amount_im;
	private double dep_interest_im;
	private double subseq_withdraw_im;
	private double withdraw_amount_im;
	private double withdraw_interest_im;
	private double amount_already_im;
	private double imp_amt_bal_auth;	//total im
	private double reg_amt_bal_auth;	//total reg	
	private double reg_deposit_total;
	private double reg_deduct_total;
	private double im_deposit_total;
	private double im_deduct_total;
	
	private double interest_calc_rate;
	private String process_flow_id;	
	private int auth_no;
	private String note_generated;
	
	
	//status Data
	private String missing_interest_data;	
	private String interest_rate_data;
	private int count_rows;	
	private String last_finance_year;
	private List<String> financial_year_list;
	private List<String> finished_financial_year_list;
	private String current_fin_end_date;
	
	

	public int getGpf_no() {
		return gpf_no;
	}

	public void setGpf_no(int gpfNo) {
		gpf_no = gpfNo;
	}

	public String getProc_no() {
		return proc_no;
	}

	public void setProc_no(String procNo) {
		proc_no = procNo;
	}

	public String getProc_date() {
		return proc_date;
	}

	public void setProc_date(String procDate) {
		proc_date = procDate;
	}

	public String getRelieve_status() {
		return relieve_status;
	}

	public void setRelieve_status(String relieveStatus) {
		relieve_status = relieveStatus;
	}

	public String getRelieve_date() {
		return relieve_date;
	}

	public void setRelieve_date(String relieveDate) {
		relieve_date = relieveDate;
	}

	public String getInt_tobe_calc_date() {
		return int_tobe_calc_date;
	}

	public void setInt_tobe_calc_date(String intTobeCalcDate) {
		int_tobe_calc_date = intTobeCalcDate;
	}

	public String getFinance_year() {
		return finance_year;
	}

	public void setFinance_year(String financeYear) {
		finance_year = financeYear;
	}

	public String getLast_finance_year() {
		return last_finance_year;
	}

	public void setLast_finance_year(String lastFinanceYear) {
		last_finance_year = lastFinanceYear;
	}

	public int getCurrent_opening_year() {
		return current_opening_year;
	}

	public void setCurrent_opening_year(int currentOpeningYear) {
		current_opening_year = currentOpeningYear;
	}

	public double getCb_ac_re() {
		return cb_ac_re;
	}

	public void setCb_ac_re(double cbAcRe) {
		cb_ac_re = cbAcRe;
	}

	public double getSubseq_deposit_re() {
		return subseq_deposit_re;
	}

	public void setSubseq_deposit_re(double subseqDepositRe) {
		subseq_deposit_re = subseqDepositRe;
	}

	public double getInt_amount_re() {
		return int_amount_re;
	}

	public void setInt_amount_re(double intAmountRe) {
		int_amount_re = intAmountRe;
	}

	public double getDep_amount_re() {
		return dep_amount_re;
	}

	public void setDep_amount_re(double depAmountRe) {
		dep_amount_re = depAmountRe;
	}

	public double getDep_interest_re() {
		return dep_interest_re;
	}

	public void setDep_interest_re(double depInterestRe) {
		dep_interest_re = depInterestRe;
	}

	public double getSubseq_withdraw_re() {
		return subseq_withdraw_re;
	}

	public void setSubseq_withdraw_re(double subseqWithdrawRe) {
		subseq_withdraw_re = subseqWithdrawRe;
	}

	public double getWithdraw_amount_re() {
		return withdraw_amount_re;
	}

	public void setWithdraw_amount_re(double withdrawAmountRe) {
		withdraw_amount_re = withdrawAmountRe;
	}

	public double getWithdraw_interest_re() {
		return withdraw_interest_re;
	}

	public void setWithdraw_interest_re(double withdrawInterestRe) {
		withdraw_interest_re = withdrawInterestRe;
	}

	public double getAmount_already_re() {
		return amount_already_re;
	}

	public void setAmount_already_re(double amountAlreadyRe) {
		amount_already_re = amountAlreadyRe;
	}

	public double getCb_ac_im() {
		return cb_ac_im;
	}

	public void setCb_ac_im(double cbAcIm) {
		cb_ac_im = cbAcIm;
	}

	public double getSubseq_deposit_im() {
		return subseq_deposit_im;
	}

	public void setSubseq_deposit_im(double subseqDepositIm) {
		subseq_deposit_im = subseqDepositIm;
	}

	public double getInt_amount_im() {
		return int_amount_im;
	}

	public void setInt_amount_im(double intAmountIm) {
		int_amount_im = intAmountIm;
	}

	public double getDep_amount_im() {
		return dep_amount_im;
	}

	public void setDep_amount_im(double depAmountIm) {
		dep_amount_im = depAmountIm;
	}

	public double getDep_interest_im() {
		return dep_interest_im;
	}

	public void setDep_interest_im(double depInterestIm) {
		dep_interest_im = depInterestIm;
	}

	public double getSubseq_withdraw_im() {
		return subseq_withdraw_im;
	}

	public void setSubseq_withdraw_im(double subseqWithdrawIm) {
		subseq_withdraw_im = subseqWithdrawIm;
	}

	public double getWithdraw_amount_im() {
		return withdraw_amount_im;
	}

	public void setWithdraw_amount_im(double withdrawAmountIm) {
		withdraw_amount_im = withdrawAmountIm;
	}

	public double getWithdraw_interest_im() {
		return withdraw_interest_im;
	}

	public void setWithdraw_interest_im(double withdrawInterestIm) {
		withdraw_interest_im = withdrawInterestIm;
	}

	public double getAmount_already_im() {
		return amount_already_im;
	}

	public void setAmount_already_im(double amountAlreadyIm) {
		amount_already_im = amountAlreadyIm;
	}

	public double getImp_amt_bal_auth() {
		return imp_amt_bal_auth;
	}

	public void setImp_amt_bal_auth(double impAmtBalAuth) {
		imp_amt_bal_auth = impAmtBalAuth;
	}

	public double getReg_amt_bal_auth() {
		return reg_amt_bal_auth;
	}

	public void setReg_amt_bal_auth(double regAmtBalAuth) {
		reg_amt_bal_auth = regAmtBalAuth;
	}

	public double getReg_deposit_total() {
		return reg_deposit_total;
	}

	public void setReg_deposit_total(double regDepositTotal) {
		reg_deposit_total = regDepositTotal;
	}

	public double getReg_deduct_total() {
		return reg_deduct_total;
	}

	public void setReg_deduct_total(double regDeductTotal) {
		reg_deduct_total = regDeductTotal;
	}

	public double getIm_deposit_total() {
		return im_deposit_total;
	}

	public void setIm_deposit_total(double imDepositTotal) {
		im_deposit_total = imDepositTotal;
	}

	public double getIm_deduct_total() {
		return im_deduct_total;
	}

	public void setIm_deduct_total(double imDeductTotal) {
		im_deduct_total = imDeductTotal;
	}

	public double getInterest_calc_rate() {
		return interest_calc_rate;
	}

	public void setInterest_calc_rate(double interestCalcRate) {
		interest_calc_rate = interestCalcRate;
	}

	public String getProcess_flow_id() {
		return process_flow_id;
	}

	public void setProcess_flow_id(String processFlowId) {
		process_flow_id = processFlowId;
	}

	public int getAuth_no() {
		return auth_no;
	}

	public void setAuth_no(int authNo) {
		auth_no = authNo;
	}

	public String getNote_generated() {
		return note_generated;
	}

	public void setNote_generated(String noteGenerated) {
		note_generated = noteGenerated;
	}

	public String getMissing_interest_data() {
		return missing_interest_data;
	}

	public void setMissing_interest_data(String missingInterestData) {
		missing_interest_data = missingInterestData;
	}

	public String getInterest_rate_data() {
		return interest_rate_data;
	}

	public void setInterest_rate_data(String interestRateData) {
		interest_rate_data = interestRateData;
	}

	public int getCount_rows() {
		return count_rows;
	}

	public void setCount_rows(int countRows) {
		count_rows = countRows;
	}

	public List<String> getFinancial_year_list() {
		return financial_year_list;
	}

	public void setFinancial_year_list(List<String> financialYearList) {
		financial_year_list = financialYearList;
	}

	public List<String> getFinished_financial_year_list() {
		return finished_financial_year_list;
	}

	public void setFinished_financial_year_list(
			List<String> finishedFinancialYearList) {
		finished_financial_year_list = finishedFinancialYearList;
	}

	public String getCurrent_fin_end_date() {
		return current_fin_end_date;
	}

	public void setCurrent_fin_end_date(String currentFinEndDate) {
		current_fin_end_date = currentFinEndDate;
	}

	
	
	
	

}
