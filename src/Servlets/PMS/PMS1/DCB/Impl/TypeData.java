package Servlets.PMS.PMS1.DCB.Impl;

public class TypeData {
	private String typeId;
	TypeData(String typeId, String typeName)
    {
        this.typeId = typeId;
        this.typeName = typeName;
    }

	private String typeName;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
