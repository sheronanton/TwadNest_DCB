package dcb.reports;

import java.util.ArrayList;

public class Dcb_Collection_Difference {

	private int processmonth;
	private int processyear;
	private String officename;
	private String collection;
	
	private ArrayList list;
	private Dcb_Collection_Difference_DAO obj_dao=new Dcb_Collection_Difference_DAO();
	public ArrayList getList() 
	{
		return list;
	}
	public void setList() {
		
		this.list = obj_dao.getValues(  processmonth,  processyear);
	}
	 
	public void setProcessmonth(int processmonth) {
		this.processmonth = processmonth;
	}
	public void setProcessyear(int processyear) {
		this.processyear = processyear;  
	}
	public void setCollection(String collection) {
		System.out.println(collection);
		this.collection = collection;
	}
	public String getCollection() {
		System.out.println(collection);
		return collection;
	}
	public void setOfficename(String officename) {
		this.officename = officename;
	}
	public String getOfficename() {
		return officename;
	}
	
	
}
