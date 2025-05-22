package Servlets.AME.AME1.AMEM.Bean;

public class Pms_Ame_Mst_Desc {

	public Pms_Ame_Mst_Desc() {
	 
	}

 
	private String Performdescsno;
	public String getPerformdescsno() {
		return Performdescsno;
	}
	public void setPerformdescsno(String performdescsno) {
		Performdescsno = performdescsno;
	}
	public String getPerformdesc() {
		return Performdesc;
	}
	public void setPerformdesc(String performdesc) {
		Performdesc = performdesc;
	}
	public String getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}
	public String getTobeprinted() {
		return tobeprinted;
	}
	public void setTobeprinted(String tobeprinted) {
		this.tobeprinted = tobeprinted;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}

	private String Performdesc;
	private String displayorder;
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	private String tobeprinted;
	private String units;
	private String datatype;
}
