package dcb.reports;

public class Fas_Master_Transaction {

	private String master_amount;
	private String transaction_amount;
	private int month;
	private int year;
	private int office_id; 
	public void setMaster_amount(String master_amount) {
		this.master_amount = master_amount;
	}
	public String getMaster_amount() {
		return master_amount;
	}
	public void setTransaction_amount(String transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	public String getTransaction_amount() { 
		return transaction_amount;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getMonth() {
		return month;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getYear() {
		return year;
	}
	public void setOffice_id(int office_id) {
		this.office_id = office_id;
	}
	public int getOffice_id() {
		return office_id;
	}
	 
} 
