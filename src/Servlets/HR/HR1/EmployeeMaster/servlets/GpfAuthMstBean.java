package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.sql.Timestamp;

public class GpfAuthMstBean {
	private int gpfNo;
	private String settlementReason;
	private String RelievalDate;
	private String intToBeCalcDate;
	private String statusId;
	private Timestamp updatedDate;
	private String updateByUserId;
	
	public GpfAuthMstBean() {
		// TODO Auto-generated constructor stub
	}

	public int getGpfNo() {
		return gpfNo;
	}

	public void setGpfNo(int gpfNo) {
		this.gpfNo = gpfNo;
	}

	public String getSettlementReason() {
		return settlementReason;
	}

	public void setSettlementReason(String settlementReason) {
		this.settlementReason = settlementReason;
	}

	public String getRelievalDate() {
		return RelievalDate;
	}

	public void setRelievalDate(String relievalDate) {
		RelievalDate = relievalDate;
	}

	public String getIntToBeCalcDate() {
		return intToBeCalcDate;
	}

	public void setIntToBeCalcDate(String intToBeCalcDate) {
		this.intToBeCalcDate = intToBeCalcDate;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdateByUserId() {
		return updateByUserId;
	}

	public void setUpdateByUserId(String updateByUserId) {
		this.updateByUserId = updateByUserId;
	}
	
}
