package Servlets.AME.AME1.AMEM.servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ame_Report_Data 
{
	
	public int HeadWiseExp(String offid,int month,int year)
	{
		
		try {
			Connection con = null;
			Controller obj = new Controller();
			con = obj.con();
			obj.createStatement(con);
			ResultSet rs = obj.getRS("select * from PMS_AME_MST_ITEM_ACC_CODE  where ACTIVE_STATUS='A' order by ACC_CODE_SNO");
			int rc = obj.getCount("PMS_AME_MST_ITEM_ACC_CODE"," where ACTIVE_STATUS='A' ");
			Statement st = con.createStatement();
			String str = "";
			int r = 0;
			try 
			{
				str = "drop table PMS_AME_DIVISION_RPT_HEAD";
				r = st.executeUpdate(str);
				str = "drop table PMS_AME_DIVISION_RPT_VALUE";
				r = st.executeUpdate(str);
			} catch (Exception e) { }
			
			str = "";
			str = "create table PMS_AME_DIVISION_RPT_HEAD (sdesc varchar(30)";
			for (int i = 1; i <= rc; i++) 
			{
				str += ",head" + i + " varchar(15) ";
			}
			str += ")"; 

			r = st.executeUpdate(str);
			String ins = " insert into PMS_AME_DIVISION_RPT_HEAD  values('-'";
			while (rs.next()) 
			{
				ins += ",'" + rs.getString("COMMONHEAD") + "'";
			}
			ins += ")";
			System.out.println("HEAD DONE");
			r = st.executeUpdate(ins);
			String str1="create table PMS_AME_DIVISION_RPT_VALUE (sdesc varchar(30) ";
			for (int i = 1; i <= rc; i++) 
			{
				str1 += ",headvalue" + i + " varchar(15) ";
			} 
			    
			str1 += ",month int ,year int ,office_id int)";
			r = st.executeUpdate(str1);
			System.out.println("VALUE DONE");
			
			str1=" SELECT DISTINCT b.scheme_sno, " +
					" a.sch_name, a.sch_sno, b.office_id  FROM PMS_SCH_MASTER a ," +
					" PMS_DCB_MST_BENEFICIARY_METRE b  WHERE a.office_id =5086" +
					" AND a.office_id   =b.office_id AND b.meter_status='L' " +
					" AND a.sch_sno     =b.scheme_sno AND a.sch_type_id =b.sch_type_id ";
			
			st=con.createStatement();
			rs=st.executeQuery(str1);
			while (rs.next())
			{
				String ssno=rs.getString(1);
				System.out.println(ssno);
				Statement  st1=con.createStatement();
				ResultSet rs1=st1.executeQuery("select * from PMS_AME_DIVISION_RPT_HEAD ");
				int i=1;
				  ins = " insert into PMS_AME_DIVISION_RPT_VALUE  values('"+rs.getString(2)+"'";
				  
				  String amt="0";
				if (rs1.next())
				{
					while (i<=rc  )
					{
						
						CallableStatement proc_stmt = null;					
						proc_stmt = con.prepareCall("{call PMS_AME_ACCOUNT_HEAD_WISE_AMT (?,?,?,?,?,?) }");
						proc_stmt.setString(1, Integer.toString(month));
						proc_stmt.setString(2, Integer.toString(year));
						proc_stmt.setString(3, ssno);
						proc_stmt.setString(4, ssno);
						String name="HEAD"+i;
						String val=rs1.getString(name);
						System.out.println(val);
						proc_stmt.setString(5, val);
						proc_stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
						proc_stmt.execute();
						amt = proc_stmt.getString(6) ;
						ins += ",'" + amt+ "'";
						i++;
						}
				}
				ins += ","+month+","+year+","+offid+")";
				r = st.executeUpdate(ins);
			}  
			
		} catch (Exception e) {
			System.out.println("Issue " + e);
		}
		
	return 1;	
	}
	
	
	
	
}
