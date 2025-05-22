package Servlets.AME.AME1.AMEM.servlets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
public class Ame_Data_Upload 
{
	public Connection con = null;
	public Connection con1 = null; 
	public Connection con2 = null; 
	public Connection con3 = null;
	public void dataUpload(int office_id,int month,int year) 
	{
		try {
			Controller obj = new Controller();
			Controller obj1 = new Controller();
			con = obj.con();
			con1 = obj.con();  
			con2 = obj.con(); 
			con3 = obj.con();
			obj.createStatement(con);  
			obj1.createStatement(con);
			System.out.println("TEST");
			ResultSet rs = obj.getRS("select * from PMS_AME_MST_ITEM_ACC_CODE  where ACTIVE_STATUS='A' and COMMONHEAD is not null  order by ACC_SEQ");
			ResultSet rs2 = obj1.getRS("select * from PMS_AME_MST_ITEM_ACC_CODE  where ACTIVE_STATUS='A' and COMMONHEAD is not null  order by ACC_SEQ");	 		
			int rc = obj.getCount("PMS_AME_MST_ITEM_ACC_CODE"," where ACTIVE_STATUS='A' and COMMONHEAD is not null "); 
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			String str = "";
			String ins ="";
			int c=0;  
			try 
			{ 
				 str = "drop table PMS_AME_DIVISION_RPT_HEAD";
				 str = "delete from  PMS_AME_DIVISION_RPT_VALUE where office_id="+office_id+" and month="+month+" and year="+year;
				 System.out.println(str);
				 con.createStatement();
				 rs=st.executeQuery(str);
				 c=obj.getCount("PMS_AME_DIVISION_RPT_HEAD","");
			} catch (Exception e) { 
				System.out.println(" Head Value Removing Place " + e);   
			}
			
			System.out.println(" count c" + c);
			str = "";
			if(c==0) 
			{
				 str = "drop table PMS_AME_DIVISION_RPT_HEAD";
				 try { }catch(Exception e) {}
				 str = "create table PMS_AME_DIVISION_RPT_HEAD (sdesc varchar(200)";  
					for (int i = 1; i <= rc; i++) 
					{
						str += ",head" + i + " varchar(115) ";
					}
					str += ")"; 
			  ins = " insert into PMS_AME_DIVISION_RPT_HEAD  values('-'";
				while (rs.next()) 
				{ 
					ins += ",'" + rs.getString("COMMONHEAD") + "'";
				}
				ins += ")";  
				ins = " insert into PMS_AME_DIVISION_RPT_HEAD  values('-'";
				while (rs2.next()) 
				{
					ins += ",'" + rs2.getString("ACC_DESC") + "'"; 
				}  
				ins += ")";
			}
			String str1="create table PMS_AME_DIVISION_RPT_VALUE (sdesc varchar(130) ";
				for (int i = 1; i <=rc; i++) 
				{
					str1 += ",headvalue" + i + " varchar(15) ";
				} 
			str1 += ",month int ,year int ,office_id int)";
			 try
			 { 
			 }catch (Exception e) {
			}
			str1=" SELECT DISTINCT b.scheme_sno, " +
					" a.sch_name, a.sch_sno, b.office_id  FROM PMS_SCH_MASTER a ," +
					" PMS_DCB_MST_BENEFICIARY_METRE b  WHERE b.office_id ="+office_id+
					//" AND a.office_id   =b.office_id " +  
					" AND b.meter_status='L' " +     
					" AND a.sch_sno     =b.scheme_sno AND a.sch_type_id =b.sch_type_id ";
			st=con.createStatement();
			rs=st.executeQuery(str1);
			while (rs.next())
			{    
				String ssno=rs.getString(1);
				Statement  st1=con.createStatement();
				ResultSet rs1=st1.executeQuery("select * from PMS_AME_DIVISION_RPT_HEAD ");
				int i=1;
				ins = " insert into PMS_AME_DIVISION_RPT_VALUE  values('"+rs.getString(2)+"'";
				if (rs1.next())
				{
					    
					while (i<=rc  )  
					{ 
						CallableStatement proc_stmt = null;
						String dramt="0";
						proc_stmt = con1.prepareCall("{call PMS_AME_ACCOUNT_HEAD_WISE_AMT (?,?,?,?,?,?) }");
						proc_stmt.setInt(1, month);
						proc_stmt.setInt(2, year);
						proc_stmt.setInt(3, office_id);
						proc_stmt.setInt(4, Integer.parseInt(ssno));
						String name="HEAD"+i;
						String val=rs1.getString(name);
						
						try
						{
						if (val.equalsIgnoreCase(null)) val="0";
						proc_stmt.setString(5, val);
						proc_stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
						proc_stmt.execute();
						dramt = obj.isNull(proc_stmt.getString(6),1) ;
						}catch(Exception e) 
						{
							System.out.println( e);  
							dramt="0";							
						}
						System.out.println(dramt);
						String cramt="0";  
						proc_stmt = con2.prepareCall("{call PMS_AME_ACCOUNT_HEAD_WISE_AMT2 (?,?,?,?,?,?) }");
						proc_stmt.setInt(1, month);
						proc_stmt.setInt(2, year);
						proc_stmt.setInt(3, office_id);
						proc_stmt.setInt(4, Integer.parseInt(ssno));
						try
						{
						if (val.equalsIgnoreCase(null)) val="0";
							proc_stmt.setString(5, val);
							proc_stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
							proc_stmt.execute();
						cramt =obj.isNull(proc_stmt.getString(6) ,1);
						}catch(Exception e) 
						{
							System.out.println( e);  
							cramt="0";							
						}
						System.out.println(cramt);
						Double total_amt=Double.parseDouble(dramt)-Double.parseDouble(cramt);
						ins += "," + total_amt+ "";
						i++;
						}
					
				}  
				ins += ","+month+","+year+","+office_id+")";
			 System.out.println(ins);
				  try { st2.execute(ins); } catch(Exception e){System.out.println("e last" +e);			
				  }
			}
			con.close(); 
			 
		} catch (Exception e) {
			System.out.println("Issue " + e);
		}
	}
}
