package Servlets.PMS.PMS1.DCB.Impl;
import java.util.ArrayList;
 

public class TypeForm extends org.apache.struts.action.ActionForm {
	private String type;
    private ArrayList typeList;
	 public TypeForm() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setTypeList(ArrayList typeList) {
		this.typeList = typeList;
	}
	public ArrayList getTypeList() {
		return typeList;
	}
}
