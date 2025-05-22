package Servlets.PMS.PMS1.DCB.servlets;

import java.sql.Connection;
import java.sql.ResultSet;

public class rate_update 
{
	public static void main(String[] args) 
	{
		Controller obj = new Controller();
		Controller obj1 = new Controller();
		Controller obj2 = new Controller();
		try {
			Connection con = obj.con();
			obj.createStatement(con);
			obj1.createStatement(con);
			obj2.createStatement(con);
			ResultSet rs = obj.getRS("select * from PMS_DCB_MST_BENEFICIARY where status='L' and   office_id=5099 order by office_id ");

			String ben = "", off_id = "";
			while (rs.next()) 
			{
				ben = rs.getString("BENEFICIARY_SNO");
				off_id = rs.getString("OFFICE_ID");
				String qry = "select * from PMS_DCB_MST_BENEFICIARY_METRE  where BENEFICIARY_SNO="+ben+" and OFFICE_ID="
						+ off_id+" and TARIFF_FLAG is not null and TARIFF_RATE is null";
				ResultSet rs1 = obj1.getRS(qry);
				while (rs1.next()) 
				{
					String METRE_SNO = obj.isNull(rs1.getString("METRE_SNO"), 1);
					String TARIFF_FLAG = obj.isNull(rs1.getString("TARIFF_FLAG"), 1);
					String SCHEME_SNO = obj.isNull(rs1.getString("SCHEME_SNO"),1);
					String eqry = "";
					if (TARIFF_FLAG.equalsIgnoreCase("L")) 
					{
						eqry = " and METRE_SNO=" + METRE_SNO;
					}
					String qry1 = "select QTY_FROM,QTY_TO ,TARIFF_RATE from PMS_DCB_TARIFF_SLAB where  BENEFICIARY_SNO= "
							+ ben+" and OFFICE_ID="+off_id+" and ACTIVE_STATUS='A' and SCH_SNO="
							+ SCHEME_SNO+" "+eqry
							+ " order by TARIFF_SLAB_SNO";
					ResultSet rs2 = obj2.getRS(qry1);
					String QTY_FROM = "", TARIFF_RATE = "", QTY_TO = "";
					while (rs2.next()) 
					{
						TARIFF_RATE += rs2.getString("TARIFF_RATE") + ",";
						QTY_FROM += rs2.getString("QTY_FROM") + "-"+ rs2.getString("QTY_TO") + ",";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
