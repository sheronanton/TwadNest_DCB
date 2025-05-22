package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Hrm_pay_emp_IT_Updation
 */
public class Hrm_pay_emp_IT_Updation_Emp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       String xml="",command="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hrm_pay_emp_IT_Updation_Emp() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection con = null;
		ResultSet res;
		int cnt=0;
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		PreparedStatement psq=null,psm=null;
		ResultSet rst=null;
		int month = cal.get(Calendar.MONTH) + 1;
//		System.out.println("Current month & Year: " + month + " " + year);
		
		try {
		LoadDriver con1=new LoadDriver();
		con=con1.getConnection();
			System.out.println("connection success");
		} catch (Exception e) {
			System.out.println("Exception in connection..." + e);
		}
		ResultSet rs = null;
		PreparedStatement ps = null;
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				String xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
//				System.out.println(xml);
				out.close();
				return;

			}
//			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

xml="";
xml="<response>";
		int sgroup = 0;
		try {
			command = request.getParameter("command");
		
			// sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
			// System.out.println("sgroup is"+sgroup);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
//		System.out.println("assign....." + command);
		
		if(command.equalsIgnoreCase("processing"))
		{

			cnt=0;
//			System.out.println("Exsisting details");		
			xml=xml+"<command>processing</command>";
			try{
				
//				int from_year=Integer.parseInt(request.getParameter("from_year"));
//				int from_month=Integer.parseInt(request.getParameter("from_month"));
//				int to_month=Integer.parseInt(request.getParameter("to_month"));
//				int to_year=Integer.parseInt(request.getParameter("to_year"));
//				int proc_year=Integer.parseInt(request.getParameter("proc_year"));
//				int proc_month=Integer.parseInt(request.getParameter("proc_month"));
				int office_id=Integer.parseInt(request.getParameter("office_id"));
				int empId=Integer.parseInt(request.getParameter("empId"));
				
				String financial_year=request.getParameter("fin_year");
				
				int from_year=Integer.parseInt(financial_year.substring(0, 4));
//				System.out.println("FROM YEAR :"+from_year);
				int from_month=3;
				int to_month=2;
				int to_year=Integer.parseInt(financial_year.substring(5, 9));
//				System.out.println("TO YEAR :"+to_year);
				int proc_year=year;
				int proc_month=month;
				
				int MSLNO =0;
				
			
					
					  CallableStatement call3=con.prepareCall("{call HRM_PAY_IT_STMT_PROCESS_EMP(?,?,?,?,?,?,?,?)}");
				     
				      call3.setInt(1,from_year);
				      call3.setInt(2,from_month);
				      call3.setInt(3,to_year);
				      call3.setInt(4,to_month);				      
				      call3.setInt(5,proc_year);		  
				      call3.setInt(6,proc_month);
				      call3.setInt(7,office_id);
				      call3.setInt(8, empId);
				      call3.execute();
				      
					cnt++;
								
				
			}
			catch(Exception e){
				xml=xml+"<flag>failure</flag>";
				System.out.println("ERROR : "+e);
				
			}
			
			if(cnt>0)
				xml=xml+"<flag>success</flag>";
			else{
				xml=xml+"<flag>failure</flag>";
			}	 
		
		}
		if(command.equalsIgnoreCase("getDetails")){
			cnt=0;
//			System.out.println("Exsisting details");		
			xml=xml+"<command>details</command>";
			try{
				int EMPLOYEE_ID=Integer.parseInt(request.getParameter("emp_id"));
				int office_id=Integer.parseInt(request.getParameter("office_id"));
				
				String financial_year=request.getParameter("fin_year");
				
				int from_year=Integer.parseInt(financial_year.substring(0, 4));
//				System.out.println("FROM YEAR :"+from_year);
				int from_month=3;
				int to_month=2;
				int to_year=Integer.parseInt(financial_year.substring(5, 9));
//				System.out.println("TO YEAR :"+to_year);
				int proc_year=year;
				int proc_month=month;
				int MSLNO =0;
				
				String sql1="SELECT SLNO " +
						"FROM HRM_PAY_EMP_IT_STMT_MST " +
						"WHERE EMPLOYEE_ID="+EMPLOYEE_ID+" " +
						"AND FNINANCIAL_YEAR    ='"+financial_year+"'";
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				if(rs.next())
				{
					MSLNO=rs.getInt("SLNO");
				}
				else
				{
					
					  CallableStatement call3=con.prepareCall("{call HRM_PAY_IT_STMT_EMP(?,?,?,?,?,?,?,?)}");
				      call3.setInt(1,EMPLOYEE_ID);
				      call3.setInt(2,from_year);
				      call3.setInt(3,from_month);
				      call3.setInt(4,to_year);
				      call3.setInt(5,to_month);				      
				      call3.setInt(6,proc_year);		  
				      call3.setInt(7,proc_month);
				      call3.setInt(8,office_id);		  
				      call3.execute();
				       sql1="SELECT SLNO FROM HRM_PAY_EMP_IT_STMT_MST WHERE EMPLOYEE_ID="+EMPLOYEE_ID+" AND FNINANCIAL_YEAR='"+financial_year+"'";
						ps=con.prepareStatement(sql1);
						rs=ps.executeQuery();
						if(rs.next())
						{
							MSLNO=rs.getInt("SLNO");
						}
				}
//				System.out.println("MSLNO : "+MSLNO);
				String sql="SELECT C.PAY_ELEMENT_ID, " +
						"  PAY_ELEMENT_TYPE_ID, " +
						"  PAY_ELEMENT_SHORT_NAME, " +
						"  PAYTOTAL " +
						"FROM " +
						"  (SELECT DISTINCT A.PAY_ELEMENT_ID, " +
						"    SLNO, " +
						"    B.PAY_ELEMENT_TYPE_ID, " +
						"    LOWER(B.PAY_ELEMENT_SHORT_NAME) AS PAY_ELEMENT_SHORT_NAME " +
						"  FROM HRM_PAY_EMP_IT_STMT_TRN A, " +
						"    HRM_PAY_ELEMENTS_MST B " +
						"  WHERE A.SLNO        ="+MSLNO+" " +
						"  AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID " +
						"  )c " +
						"JOIN " +
						"  (SELECT SUM(PAY_ELEMENT_VALUE) AS PAYTOTAL, " +
						"    PAY_ELEMENT_ID, " +
						"    SLNO " +
						"  FROM HRM_PAY_EMP_IT_STMT_TRN " +
						"  GROUP BY PAY_ELEMENT_ID, " +
						"    SLNO " +
						"  )D " +
						"ON C.SLNO           =D.SLNO " +
						"AND C.PAY_ELEMENT_ID=D.PAY_ELEMENT_ID " +
						"ORDER BY PAY_ELEMENT_TYPE_ID DESC, " +
						"  C.PAY_ELEMENT_ID"  ;
//				System.out.println(sql);
				ps=con.prepareStatement(sql);
					

				res=ps.executeQuery();
				cnt=0;
				while(res.next()){
					
					xml=xml+"<count><pay_element_id>"+res.getString("PAY_ELEMENT_ID")+"</pay_element_id>";
					xml=xml+"<pay_element_short_name>"+res.getString("PAY_ELEMENT_SHORT_NAME")+"</pay_element_short_name>";					
					xml=xml+"<PAYTOTAL>"+res.getInt("PAYTOTAL")+"</PAYTOTAL>";
					xml=xml+"<pay_element_type_id>"+res.getString("PAY_ELEMENT_TYPE_ID")+"</pay_element_type_id></count>";
					cnt++;
				}
				res.close();
				ps.close();
				
				int months =3;
				int years=from_year;
				for(int i=0;i<12;i++)
				{
					
//					String s="SELECT SLNO, " +
//							"  "+years+","+months+", " +
//							"  x.pay_element_id, " +
//							"  NVL(pay_element_value,0) AS pay_value " +
//							"FROM " +
//							"  (SELECT DISTINCT a.pay_element_id, " +
//							"    B.PAY_ELEMENT_TYPE_ID, " +
//							"    SLNO " +
//							"  FROM HRM_PAY_EMP_IT_STMT_TRN a , " +
//							"    HRM_PAY_ELEMENTS_MST B " +
//							"  WHERE SLNO          ="+MSLNO+" " +
//							"  AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID " +
//							"  )x " +
//							"LEFT OUTER JOIN " +
//							"  (SELECT PAY_ELEMENT_ID AS PAY_ID , " +
//							"    SALARY_YEAR, " +
//							"    SALARY_MONTH, " +
//							"    PAY_ELEMENT_VALUE " +
//							"  FROM HRM_PAY_EMP_IT_STMT_TRN " +
//							"  WHERE SLNO      ="+MSLNO+" " +
//							"  AND salary_year ="+years+" " +
//							"  AND salary_month="+months+" " +
//							"  )y " +
//							"ON y.pay_id              =x.pay_element_id " +
//							"WHERE pay_element_value IS NULL" ;
//					System.out.println(s);
//					psq=con.prepareStatement(s);
//					rst=psq.executeQuery();
//					if(rst.next())
//					{
					String sqls="INSERT INTO HRM_PAY_EMP_IT_STMT_TRN " +
							"SELECT SLNO, " +
							"  "+years+","+months+", " +
							"  x.pay_element_id, " +
							"  NVL(pay_element_value,0) AS pay_value " +
							"FROM " +
							"  (SELECT DISTINCT a.pay_element_id, " +
							"    B.PAY_ELEMENT_TYPE_ID, " +
							"    SLNO " +
							"  FROM HRM_PAY_EMP_IT_STMT_TRN a , " +
							"    HRM_PAY_ELEMENTS_MST B " +
							"  WHERE SLNO          ="+MSLNO+" " +
							"  AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID " +
							"  )x " +
							"LEFT OUTER JOIN " +
							"  (SELECT PAY_ELEMENT_ID AS PAY_ID , " +
							"    SALARY_YEAR, " +
							"    SALARY_MONTH, " +
							"    PAY_ELEMENT_VALUE " +
							"  FROM HRM_PAY_EMP_IT_STMT_TRN " +
							"  WHERE SLNO      ="+MSLNO+" " +
							"  AND salary_year ="+years+" " +
							"  AND salary_month="+months+" " +
							"  )y " +
							"ON y.pay_id              =x.pay_element_id " +
							"WHERE pay_element_value IS NULL" ;
//							System.out.println(sqls);
							psm=con.prepareStatement(sqls);
							psm.executeUpdate();
					
//					}
					months=months+1;
					
					if(months==13)
					{
						months=1;
						years=years+1;
					}
					
				}
				
				
				
				String sql2="SELECT A.PAY_ELEMENT_ID as PAY_ID , " +
						"  SALARY_YEAR, " +
						"  SALARY_MONTH, " +
						"  PAY_ELEMENT_VALUE, " +
						"  B.PAY_ELEMENT_TYPE_ID " +
						"FROM HRM_PAY_EMP_IT_STMT_TRN A, " +
						"  HRM_PAY_ELEMENTS_MST B " +
						"WHERE A.SLNO        ="+MSLNO+" " +
						"AND A.PAY_ELEMENT_ID=B.PAY_ELEMENT_ID " +
						"ORDER BY SALARY_YEAR, " +
						"  SALARY_MONTH, " +
						"  PAY_ELEMENT_TYPE_ID DESC, " +
						"  A.PAY_ELEMENT_ID";
				ps=con.prepareStatement(sql2);
				

				res=ps.executeQuery();
				
				while(res.next()){					
					xml=xml+"<PAYCOUNT><PAY_ID>"+res.getString("PAY_ID")+"</PAY_ID>";
					xml=xml+"<SALARY_YEAR>"+res.getInt("SALARY_YEAR")+"</SALARY_YEAR>";
					xml=xml+"<SALARY_MONTH>"+res.getInt("SALARY_MONTH")+"</SALARY_MONTH>";
					xml=xml+"<PAY_ELEMENT_VALUE>"+res.getInt("PAY_ELEMENT_VALUE")+"</PAY_ELEMENT_VALUE>";
					xml=xml+"<PAY_ELEMENT_TYPE_ID>"+res.getString("PAY_ELEMENT_TYPE_ID")+"</PAY_ELEMENT_TYPE_ID></PAYCOUNT>";
					cnt++;
				}
				
				
				String sql3="SELECT ADDITIONAL_PAY_DESC ,ADDITIONAL_PAY_VALUE,ADDITIONAL_PAY_TYPE FROM HRM_PAY_EMP_IT_STMT_ADDL WHERE SLNO="+MSLNO ;
				ps=con.prepareStatement(sql3);
				

				res=ps.executeQuery();
				
				while(res.next()){					
					xml=xml+"<ADDCOUNT><ADDITIONAL_PAY_DESC>"+res.getString("ADDITIONAL_PAY_DESC")+"</ADDITIONAL_PAY_DESC>";
					xml=xml+"<ADDITIONAL_PAY_VALUE>"+res.getInt("ADDITIONAL_PAY_VALUE")+"</ADDITIONAL_PAY_VALUE>";
					xml=xml+"<ADDITIONAL_PAY_TYPE>"+res.getString("ADDITIONAL_PAY_TYPE")+"</ADDITIONAL_PAY_TYPE></ADDCOUNT>";
					cnt++;
				}
				
				if(cnt>0)
					xml=xml+"<flag>success</flag>";
				else{
					xml=xml+"<flag>failure</flag>";
				}
			}
			catch(Exception e){
				xml=xml+"<flag>failure</flag>";
				System.out.println("ERROR : "+e);
				
			}
			
				 
		}
		else if(command.equalsIgnoreCase("SAVEDETAILS"))
		{
			cnt=0;
//			System.out.println("SAVEDETAILS");		
			
			String [] SAL_YEAR;
			String [] SAL_MONTH;
			String [] PAY_ELEMENT;
			String [] PAY_AMOUNT;
			String [] ADDITIONAL_PAY_DESC;
			String [] ADDITIONAL_PAY_VALUE;
			String [] ADDITIONAL_PAY_TYPE;
		
			xml=xml+"<command>SAVEDETAILS</command>";
			try{
				int EMPLOYEE_ID=Integer.parseInt(request.getParameter("emp_id"));
				int office_id=Integer.parseInt(request.getParameter("office_id"));
				
				String financial_year=request.getParameter("fin_year");
				
				int from_year=Integer.parseInt(financial_year.substring(0, 4));
//				System.out.println("FROM YEAR :"+from_year);
				int from_month=3;
				int to_month=2;
				int to_year=Integer.parseInt(financial_year.substring(5, 9));
//				System.out.println("TO YEAR :"+to_year);
				int proc_year=year;
				int proc_month=month;
				SAL_YEAR=request.getParameterValues("SAL_YEAR");
				SAL_MONTH=request.getParameterValues("SAL_MONTH");
				PAY_ELEMENT=request.getParameterValues("PAY_ELEMENT");
				PAY_AMOUNT=request.getParameterValues("PAY_AMOUNT");
				
				int MSLNO =0;
				
				String sql1="SELECT SLNO " +
						"FROM HRM_PAY_EMP_IT_STMT_MST " +
						"WHERE EMPLOYEE_ID="+EMPLOYEE_ID+" " +
						"AND FNINANCIAL_YEAR    ='"+financial_year+"'";

				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				if(rs.next())
				{
					MSLNO=rs.getInt("SLNO");
				}
				
				for(int i=0;i<SAL_YEAR.length;i++)
				{
					ps=null;
					String sql="UPDATE HRM_PAY_EMP_IT_STMT_TRN " +
							"SET PAY_ELEMENT_VALUE = ? " +
							"WHERE SLNO            = ? " +
							"AND SALARY_YEAR       = ? " +
							"AND SALARY_MONTH      = ? " +
							"AND PAY_ELEMENT_ID    = ?" ;
					ps=con.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(PAY_AMOUNT[i]));
					ps.setInt(2, MSLNO);
					ps.setInt(3, Integer.parseInt(SAL_YEAR[i]));
					ps.setInt(4, Integer.parseInt(SAL_MONTH[i]));
					ps.setString(5,PAY_ELEMENT[i]);
					ps.executeUpdate();
					cnt++;
				}
				sql1="DELETE FROM HRM_PAY_EMP_IT_STMT_ADDL WHERE SLNO="+MSLNO;
				ps=con.prepareStatement(sql1);
				ps.executeUpdate();
				try
				{
				ADDITIONAL_PAY_DESC=request.getParameterValues("ADDITIONAL_PAY_DESC");
				ADDITIONAL_PAY_VALUE=request.getParameterValues("ADDITIONAL_PAY_VALUE");
				ADDITIONAL_PAY_TYPE=request.getParameterValues("ADDITIONAL_PAY_TYPE");
				
				
				for(int i=0;i<ADDITIONAL_PAY_DESC.length;i++)
				{
					ps=null;
					String sql="INSERT " +
							"INTO HRM_PAY_EMP_IT_STMT_ADDL " +
							"  ( " +
							"    SLNO, " +
							"    ADDITIONAL_PAY_DESC, " +
							"    ADDITIONAL_PAY_VALUE, " +
							"    ADDITIONAL_PAY_TYPE " +
							"  ) " +
							"  VALUES " +
							"  ( " +
							"    ?, " +
							"    ? , " +
							"    ?, " +
							"    ? " +
							"  )" ;
					ps=con.prepareStatement(sql);
					ps.setInt(1, MSLNO);
					ps.setString(2,ADDITIONAL_PAY_DESC[i]);
					ps.setInt(3, Integer.parseInt(ADDITIONAL_PAY_VALUE[i]));				
					ps.setString(4,ADDITIONAL_PAY_TYPE[i]);
					ps.executeUpdate();
					cnt++;
				}
				}
				catch(Exception e){
					xml=xml+"<flag>failure</flag>";
					System.out.println("ERROR : "+e);
					
				}
				if(cnt>0)
					xml=xml+"<flag>success</flag>";
				else{
					xml=xml+"<flag>failure</flag>";
				}
			}
			catch(Exception e){
				xml=xml+"<flag>failure</flag>";
				System.out.println("ERROR : "+e);
				
			}
		}
		
		xml=xml+"</response>";
//		System.out.println(xml);
		out.println(xml);
		out.close();
	}

}
