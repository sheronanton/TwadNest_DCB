package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.*;
import java.util.Calendar;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
public class New_HrmTransJoinServ_New extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con=null;
        try {
            
        	LoadDriver driver=new LoadDriver();
	     	con=driver.getConnection();
        }
        catch(Exception e) {
            System.out.println("Exception in connection..."+e);
        }         ResultSet rs=null;
         ResultSet rs1=null;
         //CallableStatement cs=null;

         PreparedStatement ps=null;
         PreparedStatement ps1=null;
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(false);
        try
        {
                if(session==null)
                {
                        String xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
                        out.println(xml); 
                        System.out.println(xml);  
                        out.close();
                        return;
                      
                    }
                    System.out.println(session);
                        
        }catch(Exception e)
        {
                System.out.println("Redirect Error :"+e);
        }


        /*var OffCode=document.HrmTransJoinForm.txtOffId.value;
           var OffName=document.HrmTransJoinForm.txtOffName.value;
           var OffAddr=document.HrmTransJoinForm.txtOffAdd.value;
           var EmpId=document.HrmTransJoinForm.txtEmpId.value;
           var EmpName=document.HrmTransJoinForm.txtEmpName.value;
           var DOB=document.HrmTransJoinForm.txtDOB.value;
           var GPFNO=document.HrmTransJoinForm.txtGPFNO.value;
           var JRId=document.HrmTransJoinForm.txtJRId.value;
           var DOJ=document.HrmTransJoinForm.txtDOJ.value;
           var design=document.HrmTransJoinForm.Comdesig.value;
           var postcount=document.HrmTransJoinForm.ComposCount.value;
           var rem=document.HrmTransJoinForm.txtRemarks.value;*/
        // java.sql.Date t = java.sql.Date.valueOf( fromDate );

         java.sql.Date f=null;   
        java.sql.Date cdt=null; 
        int strOffcode=0;
        int strEmpId=0;
        int strEId=0;
      
        String strNoon="";
        String strDOJ="";
        String strDesign="";
        String strgrade="";
        String strpostcount="";
        String strrem="",compsession="";
        String currentoffice="",optjoin="";
        int currentofficecode=0;
       //java.sql.Date strDOJ=null;
        String xml="";
        String strCommand="";
        String empstatus="",dept_id="";
        int user_offid=0;
       
        int Year1=0;
        int b=0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        
        System.out.println("hai");
       
        try {
        
            /*strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);*/
            
             
            System.out.println("hai");
            
            strEId=Integer.parseInt(request.getParameter("comEmpId"));
            System.out.println("emp id...."+strEId);
        }
            catch(Exception e)
            {
            System.out.println("Exception in first assigning..."+e);
            }
            
            try {
                
                System.out.println("Year is....."+request.getParameter("JYear"));
                Year1=Integer.parseInt(request.getParameter("JYear"));
                
            }
            
            catch(Exception e) {
                System.out.println("in third.."+e);
            }
            try
            {
            
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            
            strOffcode=Integer.parseInt(request.getParameter("txtOffId"));
            System.out.println("txtOffId....."+strOffcode);
            
            strEmpId=Integer.parseInt(request.getParameter("txtEmpId"));
            System.out.println("txtEmpId...."+strEmpId);
            
            
            
        
            
            
            
            strNoon=request.getParameter("radFNAN");
            System.out.println("radFNAN..."+strNoon);
            
            empstatus=request.getParameter("empstatus");
            System.out.println("empstatus..."+empstatus);

            
            strDOJ=request.getParameter("txtDOJ");
            
            //Date Conversion for Date
                // java.sql.Date f=null;
                 System.out.println("before converting date");
                 String dateString = strDOJ;
                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                 java.util.Date d;                    
                 d = dateFormat.parse(strDOJ.trim());
                 dateFormat.applyPattern("yyyy-MM-dd");
                 dateString = dateFormat.format(d);
                 f = java.sql.Date.valueOf(dateString);
                 
                 
               /*  if(empstatus!=null && !empstatus.equalsIgnoreCase("WKG")) {
                     String strDOC=request.getParameter("compdate");
                     dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                     d = dateFormat.parse(strDOC.trim());
                     dateFormat.applyPattern("yyyy-MM-dd");
                     dateString = dateFormat.format(d);
                     cdt = java.sql.Date.valueOf(dateString);
                }*/
            
            
            //f=java.sql.Date.valueOf(strDOJ);
            System.out.println("txtDOJ....."+f);
             //f = Date.valueOf(strDOJ);
             
             strgrade=request.getParameter("txtgrade");
             System.out.println("strgrade..."+strgrade);

            
            strDesign=request.getParameter("comDesign");
            System.out.println("comDesign...."+strDesign);
            
            
            strpostcount=request.getParameter("comPostTow");
            System.out.println("comPostTow....."+strpostcount);
            
            strrem=request.getParameter("txtRemarks");
            System.out.println("txtRemarks...."+strrem);
            
            System.out.println(request.getQueryString());
            optjoin=request.getParameter("optjoin");
            System.out.println("optjoin...."+optjoin);
            
            if(optjoin!=null )//&& optjoin.equalsIgnoreCase("S"))
            {
                    currentofficecode=Integer.parseInt(request.getParameter("currentoffice"));
                    System.out.println("currentoffice...."+currentofficecode);
            }
            
          //  compsession=request.getParameter("compsession");
          //  System.out.println("compsession...."+compsession);
          
           user_offid=Integer.parseInt(request.getParameter("useroff"));
           System.out.println("user office id..."+user_offid);
           dept_id=request.getParameter("dept_id");
           System.out.println("user office id..."+dept_id); 
            
            
            
        }
        
        catch(Exception e) {
            System.out.println("Exception in second assigning..."+e);
        }
         
        
         if(strCommand.equalsIgnoreCase("Add")) {
         
           /* 
            *  Necessary data's are retrieved from the javascript url. 
            *  
            *  Checking whether the values are already in the corresponding table.
            *  
            *  If not then the values are inserted. If the values are present then it throw alert that 
            *  the employee id is exists.
            *  
            *  After entering the join details in corresponding table then the details are also inserted into the current posting
            *  details.
            *  
            *  Framing the xml with the corresponding data.
            * */
           
         int count=0;
         boolean flag1=false,flag2=false,flag3=false;
            xml="<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
           // int id=0;
            int paybill=0;
            String subgroupid=null,groupid=null;
            int acc_unit=0;
            session =request.getSession(false);
            String updatedby=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            java.sql.Timestamp ts=new java.sql.Timestamp(l);
            
            int wing=0;
            System.out.println(request.getParameter("wing"));
            if(request.getParameter("wing")!=null) {
               
               wing=Integer.parseInt(request.getParameter("wing"));
               
            }
            System.out.println("Wing ="+wing);
            
            try {
            
            System.out.println("Inside Add"+strOffcode);
                System.out.println("Inside Add"+Year1);
               
                System.out.println("Inside Add"+strEmpId);
                System.out.println("Inside Add"+f);
                System.out.println("Inside Add"+strNoon);
                System.out.println("Inside Add"+strDesign);
                System.out.println("Inside Add"+strpostcount);
                System.out.println("Inside Add"+strrem);
                
                ps=con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') and date_of_joining  in (To_Date('"+f+"','yyyy-mm-dd'))");
                ps.setInt(1,strEmpId);
                System.out.println("select EMPLOYEE_ID from HRM_EMP_JOIN_REPORTS where EMPLOYEE_ID="+strEmpId+" and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') and date_of_joining  in (To_Date('"+f+"','yyyy-mm-dd'))");
                rs=ps.executeQuery();
                if(!rs.next())
                {
                        
                  
                        ps=con.prepareStatement("INSERT INTO HRM_EMP_JOIN_REPORTS(OFFICE_ID,JOINING_YEAR,EMPLOYEE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID,POST_COUNTED_ID,REMARKS,PROCESS_FLOW_STATUS_ID,EMP_PRE_STATUS,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,OFFICE_WING_SINO,OFFICE_GRADE,JOINING_OFFICE_ID,DEPARTMENT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1,user_offid);
                        ps.setInt(2,Year1);
                   
                        ps.setInt(3,strEmpId);
                        ps.setDate(4,f);
                        ps.setString(5,strNoon);
                        ps.setString(6,strDesign);
                        ps.setString(7,strpostcount);
                        ps.setString(8,strrem);
                        ps.setString(9,"CR");
                       // ps.setDate(11,cdt);
                        ps.setString(10,empstatus);
                        ps.setString(11,optjoin);
                        ps.setInt(12,currentofficecode);          
                        ps.setString(13,updatedby);
                        ps.setTimestamp(14,ts);
                        ps.setInt(15,wing);
                        ps.setString(16,strgrade);
                        ps.setInt(17,strOffcode);
                        ps.setString(18,dept_id);
                       // ps.setString(13,compsession);
                       
                        System.out.println("user office id :"+user_offid);
                        System.out.println("OFFICE_ID:"+strOffcode);
                    System.out.println("JOINING_YEAR:"+Year1);
                   
                    System.out.println("EMPLOYEE_ID:"+strEmpId);
                    System.out.println("DATE_OF_JOINING:"+f);
                    System.out.println("FN_OR_AN:"+strNoon);
                    System.out.println("DESIGNATION_ID:"+strDesign);

                    System.out.println("POST_COUNTED_ID:"+strpostcount);

                    System.out.println("REMARKS:"+strrem);

                    System.out.println("PROCESS_FLOW_STATUS_ID:"+"CR");
                    System.out.println("EMP_PRE_STATUS:"+empstatus);

                    System.out.println("JOINED_SUBDIVISION:"+optjoin);

                    System.out.println("SUBDIVISION_OFFICE_ID:"+currentofficecode);

                    System.out.println("UPDATED_BY_USER_ID:"+updatedby);
                    System.out.println("UPDATED_DATE:"+ts);
                    System.out.println("OFFICE_WING_SINO:"+wing);
                    System.out.println("OFFICE_GRADE:"+strgrade);
                    System.out.println("Department id:"+dept_id);


                    int y=ps.executeUpdate();
                    flag1=true; 
                        
                        System.out.println("y is "+y);
                   /*   System.out.println("INSERT INTO HRM_EMP_JOIN_REPORTS" +
                        " (OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,DATE_OF_JOINING,FN_OR_AN,DESIGNATION_ID," +
                    " POST_COUNTED_ID,REMARKS,PROCESS_FLOW_STATUS_ID,EMP_PRE_STATUS,JOINED_SUBDIVISION,SUBDIVISION_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,OFFICE_WING_SINO,OFFICE_GRADE )" +
                        " VALUES(strOffcode,Year1,strJR,strEmpId,f,strNoon,strDesign,strpostcount,strrem,CR,?,?,?,?,?)");*/
                        System.out.println("ok");
                        
                        try
                        {
                        PreparedStatement psn=con.prepareStatement("select employee_id from hrm_emp_current_posting where employee_id =?");
                        psn.setInt(1,strEmpId);
                        ResultSet rsn=psn.executeQuery();
                        if(!rsn.next())
                        {
                        PreparedStatement psnew=con.prepareStatement("insert into hrm_emp_current_posting(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID) values(?,?,?,?,?,?,?,?,?,?)");
                        psnew.setInt(1,strEmpId);
                        psnew.setInt(2,strOffcode);
                        psnew.setString(3,strDesign);
                        psnew.setDate(4,f);
                        psnew.setString(5,strrem);
                        psnew.setString(6,updatedby);
                        psnew.setTimestamp(7,ts);
                        psnew.setString(8,strgrade);
                        psnew.setString(9,"CR");
                        psnew.setString(10,"TRT");
                        
                        int x=psnew.executeUpdate();
                        
                        System.out.println("Inserted itno the hrm_emp current posting successfully");
                        }
                        flag2=true;
                        }
                        catch(Exception e) {
                              System.out.println("err in insetign into hrm_emp_current posting");
                          }
                        paybill=Integer.parseInt(request.getParameter("paybill"));
                        if(paybill==1)
                        {
                        	groupid=request.getParameter("groupid");
                        	System.out.println("groupid"+groupid);
                        	subgroupid=request.getParameter("subgroup");
                        	try
                        	{
                        		PreparedStatement pst=null;
                        		
                        		String sql="select hrm_pay_get_control_id("+strOffcode+") as offid from dual";
                        		pst=con.prepareStatement(sql);
                        		rs=pst.executeQuery();
                        		if(rs.next())
                        		{
                        			strOffcode=rs.getInt("offid");
                        		}
                        		
                        		System.out.println("OFFICE ID ============================> : "+strOffcode);
//                        		String sql12="delete from HRM_PAY_BILL_GROUP_EMP_LINK where employee_id="+strEmpId;
//                        		pst=con.prepareStatement(sql12);
//                        		pst.executeUpdate();
                        		 PreparedStatement psnew1=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?  and ACCOUNTING_UNIT_ID not in (999,5,6)");
                                 psnew1.setInt(1,strOffcode);
                                 rs=psnew1.executeQuery();
                                 if(rs.next())
                                	 acc_unit=rs.getInt("ACCOUNTING_UNIT_ID");
                                 
                                 int joinrep=0;
                                 PreparedStatement pst1=con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) as JOINING_REPORT_ID FROM HRM_EMP_JOIN_REPORTS WHERE EMPLOYEE_ID="+strEmpId+"");
                                 rs=pst1.executeQuery();
                                 if(rs.next())
                                	 joinrep=rs.getInt("JOINING_REPORT_ID");
                                 
                                 System.out.println("JOINREP_ID : "+ joinrep);
                                 psnew1=con.prepareStatement("insert into HRM_PAY_BILL_GROUP_EMP_TEMP(EMPLOYEE_ID,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PAY_BILL_GROUP_ID,PAY_BILL_SUBGROUP_ID,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_ID,REMARKS,JOINING_REPORT_ID ) values(?,?,?,?,?,?,?,?,?,?,?)");
                                 psnew1.setInt(1,strEmpId);
                                 psnew1.setInt(2,acc_unit);
                                 psnew1.setInt(3,strOffcode);
                                 psnew1.setString(4,groupid);
                                 psnew1.setString(5,subgroupid);
                                 psnew1.setDate(6,f);
                                 psnew1.setString(9,"CR");
                                 psnew1.setString(7,updatedby);
                                 psnew1.setTimestamp(8,ts);
                                 psnew1.setString(10,"Joining Report");
                                 psnew1.setInt(11,joinrep);
                                 int z=psnew1.executeUpdate();
                        	}
                        	catch(Exception e)
                        	{
                        		System.out.println("excepetion e"+e);
                        	}
                        }
                /*    try
                    {
                    PreparedStatement psnew1=con.prepareStatement("insert into hrm_emp_controlling_office(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)");
                    psnew1.setInt(1,strEmpId);
                    psnew1.setInt(2,strOffcode);
                    psnew1.setString(3,"CR");
                    psnew1.setString(4,updatedby);
                    psnew1.setTimestamp(5,ts);
                    int z=psnew1.executeUpdate();
                    flag3=true;
                    System.out.println("Inserted itno the hrm_emp controlling office successfully");
                    }
                    catch(Exception e) {
                          System.out.println("err in insetign into hrm_emp controlling office");
                      }*/
                        count++;
                        
                        
                }
                if(flag1&&flag2) {
                    xml=xml+"<flag>success</flag>";
                }
                else {
                    xml=xml+"<flag>failure1</flag>";
                }
            }
        
        catch(Exception e) {
        
         System.out.println("Exception in inserting emp details........"+e);
        xml=xml+"<flag>failure</flag>";
        }
        xml=xml+"</response>";
        }
                
                 /*cs=con.prepareCall("{call HRM_TRANS_JOIN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                 
                     cs.setInt(1,strOffcode);
                     cs.setString(2,strOffName);
                     cs.setString(3,strOffAddr);
                     cs.setInt(4,strEmpId);
                     cs.setString(5,strEmpName);
                     cs.setString(6,strDOB);
                     cs.setInt(7,strGPFNO);
                    cs.setInt(8,strJRId);
                    cs.setString(9,strDOJ);
                    cs.setString(10,strDesign);
                    cs.setString(11,strpostcount);
                    cs.setString(12,strrem);
                    cs.setString(13,"insert");
                    cs.registerOutParameter(14,java.sql.Types.NUMERIC);
                    cs.registerOutParameter(15,java.sql.Types.NUMERIC);            
                    cs.execute();
                    int errcode=cs.getInt(14);
                    id=cs.getInt(1);
                    System.out.println(id);
                    System.out.println("SQLCODE:::"+errcode);
                    if(errcode!=0)
                    {                   
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>failure</flag>";                          
                     }
                     else
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>success</flag>";
                     }
                     catch(Exception e)
                     {
                     System.out.println("insert exception fg :"+e);
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>failure</flag>";
                     }
                              
                     xml=xml+"</response>";
                     }*/
                  /*if(strCommand.equalsIgnoreCase("inc")) {
                  System.out.println("hai");
                      String sxml="<response><command>inc</command>";
                      try {
                          ps=con.prepareStatement("SELECT MAX(D) FROM HRM_TRANS_JOIN_REPORT");
                          
                          rs=ps.executeQuery();
                          //System.out.println("rs.."+rs.getInt(1));
                          int i=0;
                          while(rs.next()) {
                          i=rs.getInt(1);
                      
                          }
                          if(i==0)
                          {
                          i=1;
                          System.out.println("i...."+i);
                          xml=sxml+"<flag>first</flag><i>"+i+"</i>";
                          }
                          else
                          {
                              i=i+1;
                              System.out.println("i.."+i);
                              xml=sxml+"<flag>incremented</flag><i>"+i+"</i>";
                          }
                      }
                      catch(Exception e) {
                          System.out.println("catch...."+e);
                          xml=xml+"<flag>failure</flag>";
                      }
                      xml=xml+"</response>";
                      }*/
                      
         if(strCommand.equalsIgnoreCase("getsupgroup")){
 			xml=xml+"<response><command>getsubgroup</command>";
 			
 			int off_id=Integer.parseInt(request.getParameter("off_id"));
 			System.out.print("off_id");
 			String group_id=request.getParameter("group_id");
 			System.out.println(group_id);
 			
 			try
 			{
 			PreparedStatement pst=con.prepareStatement("select hrm_pay_get_control_id("+off_id+") cont from dual");
 			ResultSet Rst=pst.executeQuery();
 			if(Rst.next())
 			{
 				off_id=Rst.getInt("cont");
 			}
 			}
 			catch(Exception e)
 			{
 				
 			}
 			
 			
 			try{
 				int cnt=0;
 			ps=con.prepareStatement("select  PAY_BILL_SUBGROUP_ID,PAY_BILL_SUB_GROUP_DESC from HRM_PAY_BILL_SUBGROUP_MST where OFFICE_ID=?  and  PAY_BILL_GROUP_ID=? order by PAY_BILL_SUBGROUP_ID");
 			ps.setInt(1,off_id );
 			
 			ps.setString(2,group_id );
 			rs=ps.executeQuery();
 			while(rs.next()){
 				xml=xml+"<count>";
 				xml=xml+"<sub_id>"+rs.getString("PAY_BILL_SUBGROUP_ID")+"</sub_id>";
 				xml=xml+"<sub_desc>"+rs.getString("PAY_BILL_SUB_GROUP_DESC")+"</sub_desc>";
 				xml=xml+"</count>";
 				cnt++;
 			}
 			if(cnt>0)
 				xml=xml+"<flag>success</flag></response>";
 			else
 				xml=xml+"<flag>failure</flag></response>";	
 			}
 			catch(Exception e){
 				xml=xml+"<flag>failure</flag></response>";
 			}
 	
 	}
         if(strCommand.equalsIgnoreCase("getpaybillgroup")){
  			xml=xml+"<response><command>getpaybillgroup</command>";
  			
  			int off_id=Integer.parseInt(request.getParameter("off_id"));
  			System.out.println(off_id);
  			
  			try
  			{
  			PreparedStatement pst=con.prepareStatement("select hrm_pay_get_control_id("+off_id+") cont from dual");
  			ResultSet Rst=pst.executeQuery();
  			if(Rst.next())
  			{
  				off_id=Rst.getInt("cont");
  			}
  			}
  			catch(Exception e)
  			{
  				
  			}
  			
  			
  			try{
  				int cnt=0;
  			ps=con.prepareStatement("select  PAY_BILL_GROUP_ID,PAY_BILL_GROUP_DESC from HRM_PAY_BILL_GROUP_MST where OFFICE_ID=?   order by PAY_BILL_GROUP_ID");
  			ps.setInt(1,off_id );
  			
  			
  			rs=ps.executeQuery();
  			while(rs.next()){
  				xml=xml+"<count>";
  				xml=xml+"<sub_id>"+rs.getString("PAY_BILL_GROUP_ID")+"</sub_id>";
  				xml=xml+"<sub_desc>"+rs.getString("PAY_BILL_GROUP_DESC")+"</sub_desc>";
  				xml=xml+"</count>";
  				cnt++;
  			}
  			if(cnt>0)
  				xml=xml+"<flag>success</flag></response>";
  			else
  				xml=xml+"<flag>failure</flag></response>";	
  			}
  			catch(Exception e){
  				xml=xml+"<flag>failure</flag></response>";
  			}
  	
  	}          
                      
        else if(strCommand.equalsIgnoreCase("dispDesign")) {
        System.out.println("Insiden Year "+Year1);
            String sxml="<response><command>dispDesign</command>";
                int empid=0;
                int oid=0;
                ResultSet results=null;
            
                session=request.getSession(false);
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                empid=empProfile.getEmployeeId();
                
                System.out.println("login user..."+empid);
                
                try
                {
                ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                ps.setInt(1,empid);
                results=ps.executeQuery();
                     if(results.next()) 
                     {
                        oid=results.getInt("OFFICE_ID");
                        System.out.println("user office id.."+oid);
                                 
                     }
                }
                catch(Exception e)
                {
                  System.out.println(e.getMessage());
                }
            
            try {
                //ps=con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
                ps=con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_JOIN_REPORTS where OFFICE_ID=?");
                ps.setInt(1,oid);
                
                //ps.setInt(1,strOffcode);
                //ps.setInt(2,Year1);
                
                rs=ps.executeQuery();
                if(rs.next())
                {
                            b=rs.getInt("b");
                            System.out.println("b is "+b);
                            
                }
                 int j=b;
                System.out.println("b is"+j);
                
                /*int i=0;
                System.out.println("hai");
                if(rs.next()) {
                j=rs.getInt("JOIN_REPORT_ID");
                i=i+1;
                System.out.println(".."+i);
                }*/
                if(j==0)
                {
                j=1;
                System.out.println("i...."+j);
                xml=sxml+"<flag>success</flag><j>"+j+"</j>";
                }
                else
                {
                    j=j+1;
                    System.out.println("i.."+j);
                    xml=sxml+"<flag>success</flag><j>"+j+"</j>";
                }
            }
            catch(Exception e) {
                System.out.println("catch in x...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            }
        
        else if(strCommand.equalsIgnoreCase("disp")) {
        
            xml="<response><command>disp</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            try {
               
                ps=con.prepareStatement("SELECT EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                              
                ps.setInt(1,strEmpId);
                               
                rs=ps.executeQuery();
                
                while(rs.next())
                {
                
                    //java.sql.Date DateOfFormation = rs.getDate("DATE_OF_BIRTH");  
                    String[] sd=rs.getDate("DATE_OF_BIRTH").toString().split("-");
                    String od=sd[2]+"/"+sd[1]+"/"+sd[0];
                     String name=rs.getString("EMPLOYEE_NAME");
                     System.out.println(name);
                   // String dob=rs.getString("DATE_OF_BIRTH");
                    System.out.println("od::"+od);
                    int gpfno=rs.getInt("GPF_NO");
                    System.out.println(gpfno);
                xml=xml+"<flag>success</flag><ename>"+name+"</ename><dob>"+od+"</dob><gpfno>"+gpfno+"</gpfno>";
                }
                
                ////////////////////////
                /*
                 PreparedStatement cs=con.prepareStatement("select DATE_TO,DATE_TO_SESSION from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=? and DATE_TO=(select max(DATE_TO) from  HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=? )" );
                 cs.setInt(1,strEmpId);
                 cs.setInt(2,strEmpId);
                 Calendar c=null;
                 rs=cs.executeQuery();
                 if(rs.next()) {
                       Date dt=rs.getDate("DATE_TO");
                       String ses=rs.getString("DATE_TO_SESSION");
                       String strdt=dt.toString();
                       System.out.println("Date is :"+strdt);
                       String[] sp=strdt.split("-");
                       System.out.println(sp[1]);
                       System.out.println("session:"+ses);
                       String nextses="";
                       if(ses.equalsIgnoreCase("AN"))
                       {
                           c=new GregorianCalendar(Integer.parseInt(sp[0]),Integer.parseInt(sp[1])-1,Integer.parseInt(sp[2])+1);
                           nextses="FN";
                       }
                       else
                       {
                           c=new GregorianCalendar(Integer.parseInt(sp[0]),Integer.parseInt(sp[1])-1,Integer.parseInt(sp[2]));
                           nextses="AN";
                       }
                     //java.util.Date d=c.getTime();
                     System.out.println("after conversion:"+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
                     String nextdate=new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                     xml=xml+"<nextdate>"+nextdate+"</nextdate><nextsession>"+nextses+"</nextsession>";
                     xml=xml+"<flag>success</flag>";
                 }
                 */
                 
                ///////////////////////
            }
            catch(Exception e) {
            
                 System.out.println("Exception in displaying emp details........"+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        
        else if(strCommand.equalsIgnoreCase("dispEmp")) {
        
            xml="<response><command>dispEmp</command>";
            
            int user_emp=0;
           
            boolean flag=true;
            String of_name="";
            
            //String sql="insert into TEST_STATE values(?,?)";
            try {
                  System.out.println("office id"+user_offid);
             int   officeid=  Integer.parseInt(request.getParameter("office_id"));
                System.out.println("office id"+officeid);
                session=request.getSession(false);
                UserProfile up=null;
                up=(UserProfile)session.getAttribute("UserProfile");
                user_emp=up.getEmployeeId();
              
                System.out.println("user emp"+ user_emp);
                
                System.out.println("Admin Session:"+session.getAttribute("Admin"));
                
                ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
               ps.setInt(1,user_emp);
                //ps.setInt(1,strEmpId);
                
                rs=ps.executeQuery();
                if(!rs.next())
                {
                   xml=xml+"<flag>failure</flag>";
                   flag=false;
                }
                else
                {
                
                if(up.getEmployeeId()!=strEId)
                {
                
                  int OfficeId=0;
                  String sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                  ps=con.prepareStatement(sql);
                  ps.setInt(1,user_emp);
                  rs=ps.executeQuery();
                              
                  if(rs.next())
                  {
                     OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                     System.out.println("user controlling OfficeId"+ OfficeId);
                  }
                  
                if(OfficeId!=0)
                {
                       sql="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps=con.prepareStatement(sql);
                       ps.setInt(1,up.getEmployeeId());
                        //ps.setInt(1,strEmpId);
                        rs=ps.executeQuery();  
                         if(rs.next()) {
                            int offid=rs.getInt("OFFICE_ID");
                            System.out.println("user office id"+ offid);
                            
                           /* 
                            * Due to Issue commented
                            * if(offid!=OfficeId)
                            {
                             System.out.println("Admin Session:"+session.getAttribute("Admin"));
                             if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                             {//response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                xml=xml+"<flag>failure1</flag>";
                                 //xml=xml+"<flag>failurenew</flag>";
                                flag=false;
                             }
                            }*/
                        }
                     /*   else
                        {
                               // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                                xml=xml+"<flag>failure2</flag>";
                               flag=false;
                        }*/
                
                }
                else{
                    //if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                    {
                    xml=xml+"<flag>failure3</flag>";
                    flag=false;
                    }
                }
                }

                }
            
            
            
            
            
            
            
            if(flag)
            {           
            
            
            System.out.println("disp emp");
            System.out.println("emp id:"+strEId);
                ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1,strEId);
                rs=ps.executeQuery();
                if(!rs.next()) 
                {
                    xml=xml+"<flag>failure</flag>";
                }
                else
                {
                
                System.out.println("This is the checking part...");
                ps=con.prepareStatement("SELECT EMPLOYEE_STATUS_ID,OFFICE_ID  FROM HRM_EMP_CURRENT_POSTING  WHERE EMPLOYEE_ID=?");
                ps.setInt(1,strEId);
                rs=ps.executeQuery();
                System.out.println("before while");
                
                if(!rs.next()) 
                {
                   System.out.println("inside new emp add");
                    //xml=xml+"<flag>failure1</flag>";
                    
                    
                    try
                    {
                     ps1=con.prepareStatement("select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no,b.designation_id,c.cadre_id,d.cadre_name," +
                                             " d.cadre_short_name from" +
                                             " (" +
                                             " select employee_id,employee_name,employee_initial,date_of_birth,gpf_no from hrm_mst_employees" +
                                             " where employee_id=?" +
                                             " ) a" +
                                             " left outer join" +
                                             " (" +
                                             " select employee_id,designation_id from hrm_emp_current_posting" +
                                             " ) b" +
                                             " on a.employee_id=b.employee_id" +
                                             " left outer join" +
                                             " (" +
                                             " select designation_id,cadre_id from hrm_mst_designations" +
                                             " ) c" +
                                             " on b.designation_id=c.designation_id" +
                                             " left outer join" +
                                             " (" +
                                             " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre" +
                                             " ) d" +
                                             " on c.cadre_id=d.cadre_id");    
                                             
                              System.out.println("after query");               
                              System.out.println("strEId..."+strEId);
                           ps1.setInt(1,strEId);
                           rs1=ps1.executeQuery();
                           System.out.println("after rs1");
                           int c=0;
                           try
                           {
                                   while(rs1.next())
                                   {
                                     System.out.println("retrieve fields");
                                   
                                       java.sql.Date DateOfFormation = rs1.getDate("DATE_OF_BIRTH");  
                                       String DateToBeDisplayed="";
                                       if(DateOfFormation==null)
                                       {
                                           DateToBeDisplayed="Not Specified";
                                       }
                                       else
                                       {
                                           try
                                           {
                                               java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                               DateToBeDisplayed=sdf.format(DateOfFormation);
                                           }
                                           catch(Exception e)
                                           {
                                               System.out.println("error while formatting date : " + e);
                                               DateToBeDisplayed="Not Specified";
                                           }
                                       }     
                                        String id=rs1.getString("EMPLOYEE_ID");
                                        System.out.println(id);
                                       String name=rs1.getString("EMPLOYEE_NAME");
                                       System.out.println(name);
                                       String cadre=rs1.getString("CADRE_NAME");
                                       System.out.println(cadre);
                                       String cad_name=rs1.getString("CADRE_SHORT_NAME");
                                       System.out.println(cad_name);
                                       
                                       //String dob=rs.getString("DATE_OF_BIRTH");
                                       //System.out.println(dob);
                                       int gpfno=rs1.getInt("GPF_NO");
                                       System.out.println(gpfno);
                                                                             
                                       //ps=con.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                      /*
                                        ps1=con.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                                     
                                       ps1.setInt(1,strEId);
                                                      
                                       rs1=ps1.executeQuery();
                                       String maxtodate="";
                                       String maxsession="";
                                       if(rs1.next())
                                       {
                                           if(rs1.getDate("maxtodate")!=null)
                                           {
                                               maxtodate=new SimpleDateFormat("dd/MM/yyyy").format(rs1.getDate("maxtodate"));
                                               System.out.println("max to date :"+rs1.getDate("maxtodate"));
                                           }
                                           else {
                                               
                                               maxtodate="empty";
                                           }
                                           if(rs1.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                               maxsession=rs1.getString("DATE_EFFECTIVE_FROM_SESSION");
                                           }
                                           else {
                                               maxsession="FN";
                                           }
                                       }
                                       */
                                       /*
                                       ps1=con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                       ps1.setInt(1,strEId);
                                       rs1=ps1.executeQuery();
                                       
                                       if(rs1.next())
                                       {
                                           if(rs1.getString("EMPLOYEE_STATUS_ID")!=null)
                                           {
                                              empstatus=rs1.getString("EMPLOYEE_STATUS_ID");
                                           }
                                           else {
                                               
                                               empstatus="WKG";
                                           }
                                       }
                                       */
                                       if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                       {
                                           xml=xml+"<admin>YES</admin>";
                                       }
                                       else 
                                       {
                                           xml=xml+"<admin>NO</admin>";
                                       }
                                       
                                       String maxtodate=null;
                                       String maxsession=null;
                                       empstatus=null;
                                       
                                   xml=xml+"<flag>success</flag><inclusion>Yes</inclusion><eid>"+id+"</eid><dob>"+DateToBeDisplayed+"</dob><gpfno>"+gpfno+"</gpfno>" +
                                   "<name>"+name+"</name><cadre>"+cadre+"</cadre><cad_name>"+cad_name+"</cad_name>"+
                                   "<maxtodate>"+maxtodate+"</maxtodate><workingstatus>"+empstatus+"</workingstatus>" +
                                  "<maxsession>"+maxsession+"</maxsession>";
                                   c++;
                                   System.out.println("This is the end of checking part.....");
                                   }
                    }
                    catch(Exception e)
                    {
                       System.out.println(e.getMessage());
                    }
                                   if(c==0) {
                                       xml=xml+"<flag>failure4_14</flag>";
                                       System.out.println("No employee in this current posting.....");
                                   }
                                   
                                   System.out.println("End of this module... ");
                                   
                       }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                
               else
               {
                     System.out.println("inside another else ....while");
                        if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")) {
                        	
                        	// New changes.................//
                        	int acc_unit=0,accunit1=0,exist=0;

                        	   // 22.01.2013  as per the request of sir , check pay bill grp of login user and join emp then proceeded for next 
                        	PreparedStatement pss1=con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from HRM_PAY_BILL_GROUP_EMP_LINK  where EMPLOYEE_ID=?");
                        	                        	 pss1.setInt(1,user_emp);
                        	                             ResultSet rss1 =pss1.executeQuery();
                        	                             if(rss1.next())
                        	                             {
                        	                     	        acc_unit=rss1.getInt("ACCOUNTING_FOR_OFFICE_ID");
                        	                             }
                        	                        	                     	
                        	                             
                        	                        	 pss1=con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from HRM_PAY_BILL_GROUP_EMP_LINK  where EMPLOYEE_ID=?");
                        	                        	 pss1.setInt(1,strEId);
                        	                             rss1 =pss1.executeQuery();
                        	                             if(rss1.next())
                        	                             {
                        	                     	        accunit1=rss1.getInt("ACCOUNTING_FOR_OFFICE_ID");
                        	                     	        exist=1;
                        	                             }
                        	                            if(accunit1==acc_unit)
                        	                            	 xml=xml+"<flag>success</flag><inclusion>NO</inclusion>";
                            
                             else
                            	 
                             {
                            
                          //ps=con.prepareStatement("SELECT EMPLOYEE_ID,EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                           System.out.println("inside else part");      
                           ps=con.prepareStatement("SELECT A.Employee_Id, " +
                        		   "  A.Employee_Name " +
                        		   "  ||DECODE(A.Employee_Initial,NULL,' ','.' " +
                        		   "  ||A.Employee_Initial) AS Employee_Name, " +
                        		   "  A.Date_Of_Birth, " +
                        		   "  A.Gpf_No, " +
                        		   "  B.Designation_Id, " +
                        		   "  C.Cadre_Id, " +
                        		   "  D.Cadre_Name, " +
                        		   "  d.cadre_short_name, " +
                        		   "  e.office_name " +
                        		   "FROM " +
                        		   "  (SELECT Employee_Id, " +
                        		   "    Employee_Name, " +
                        		   "    Employee_Initial, " +
                        		   "    Date_Of_Birth, " +
                        		   "    Gpf_No " +
                        		   "  FROM Hrm_Mst_Employees " +
                        		   "  WHERE employee_id=? " +
                        		   "  ) a " +
                        		   "LEFT OUTER JOIN " +
                        		   "  ( SELECT employee_id,designation_id,office_id FROM hrm_emp_current_posting " +
                        		   "  ) b " +
                        		   "ON a.employee_id=b.employee_id " +
                        		   "LEFT OUTER JOIN " +
                        		   "  ( SELECT designation_id,cadre_id FROM hrm_mst_designations " +
                        		   "  ) c " +
                        		   "ON b.designation_id=c.designation_id " +
                        		   "LEFT OUTER JOIN " +
                        		   "  ( SELECT cadre_id,cadre_name,cadre_short_name FROM hrm_mst_cadre " +
                        		   "  ) d " +
                        		   "ON C.Cadre_Id=D.Cadre_Id " +
                        		   "LEFT OUTER JOIN " +
                        		   "  (SELECT office_id,office_name FROM Com_Mst_Offices " +
                        		   "  )E " +
                        		   "ON b.office_id=E.office_id");       
                                 
                                 ps.setInt(1,strEId);
                                 rs=ps.executeQuery();
                                 int c=0;
                                         while(rs.next())
                                         {
                                         
                                             java.sql.Date DateOfFormation = rs.getDate("DATE_OF_BIRTH");  
                                             String DateToBeDisplayed="";
                                             if(DateOfFormation==null)
                                             {
                                                 DateToBeDisplayed="Not Specified";
                                             }
                                             else
                                             {
                                                 try
                                                 {
                                                     java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                                     DateToBeDisplayed=sdf.format(DateOfFormation);
                                                 }
                                                 catch(Exception e)
                                                 {
                                                     System.out.println("error while formatting date : " + e);
                                                     DateToBeDisplayed="Not Specified";
                                                 }
                                             }     
                                              String id=rs.getString("EMPLOYEE_ID");
                                              System.out.println(id);
                                             String name=rs.getString("EMPLOYEE_NAME");
                                             System.out.println(name);
                                             String cadre=rs.getString("CADRE_NAME");
                                             System.out.println(cadre);
                                             String cad_name=rs.getString("CADRE_SHORT_NAME");
                                             System.out.println(cad_name);
                                             of_name=rs.getString("office_name");
                                             System.out.println("offname.........."+of_name);
                                             
                                             //String dob=rs.getString("DATE_OF_BIRTH");
                                             //System.out.println(dob);
                                             int gpfno=rs.getInt("GPF_NO");
                                             System.out.println(gpfno);
                                             
                                             //ps=con.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                              ps=con.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                                           
                                             ps.setInt(1,strEId);
                                                            
                                             rs=ps.executeQuery();
                                             String maxtodate="";
                                             String maxsession="";
                                             if(rs.next())
                                             {
                                                 if(rs.getDate("maxtodate")!=null)
                                                 {
                                                     maxtodate=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("maxtodate"));
                                                     System.out.println("max to date :"+rs.getDate("maxtodate"));
                                                 }
                                                 else {
                                                     
                                                     maxtodate="empty";
                                                 }
                                                 if(rs.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                                     maxsession=rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                                 }
                                                 else {
                                                     maxsession="FN";
                                                 }
                                             }
                                             
                                             ps=con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                             ps.setInt(1,strEId);
                                             rs=ps.executeQuery();
                                             
                                             if(rs.next())
                                             {
                                                 if(rs.getString("EMPLOYEE_STATUS_ID")!=null)
                                                 {
                                                    empstatus=rs.getString("EMPLOYEE_STATUS_ID");
                                                 }
                                                 else {
                                                     
                                                     empstatus="WKG";
                                                 }
                                             }
                                             
                                             if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                             {
                                                 xml=xml+"<admin>YES</admin>";
                                             }
                                             else 
                                             {
                                                 xml=xml+"<admin>NO</admin>";
                                             }
                                             
                                         xml=xml+"<flag>success</flag><inclusion>WKG</inclusion><eid>"+id+"</eid><dob>"+DateToBeDisplayed+"</dob><gpfno>"+gpfno+"</gpfno>" +
                                         "<name>"+name+"</name><cadre>"+cadre+"</cadre><cad_name>"+cad_name+"</cad_name>" +
                                         "<maxtodate>"+maxtodate+"</maxtodate><workingstatus>"+empstatus+"</workingstatus>" +
                                         "<maxsession>"+maxsession+"</maxsession><of_name>"+of_name+"</of_name>";
                                         
                                         c++;
                                         }
                                         if(c==0) {
                                             xml=xml+"<flag>failure</flag>";
                                         }
                            // xml=xml+"<inclusion>NO</inclusion>";
                             }
                             
                        }
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SAN")) {
                            xml=xml+"<flag>failure3_2</flag>";
                        }
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DIS")) {
                            xml=xml+"<flag>failure3_3</flag>";
                        }
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("VRS")) {
                            xml=xml+"<flag>failure3_4</flag>";
                        }
                        /*
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")) {
                                xml=xml+"<flag>failure3_5</flag>";
                        }*/
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")) {
                                xml=xml+"<flag>failure3_6</flag>";
                        }
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU")) {
                                xml=xml+"<flag>failure3_7</flag>";
                        }
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STT")) {
                                xml=xml+"<flag>failure3_8</flag>";
                        }
                        
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DTH")) {
                                xml=xml+"<flag>failure3_9</flag>";
                        }
                        else    if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("ABR")) {
                                xml=xml+"<flag>failure3_10</flag>";
                        }
                        else if((rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT"))||(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LLV")) || (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")) ||  (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SUS"))||(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("UAL"))){
                                    rs.close();
                                    ps.close();
                                    System.out.println("inside trt,dpt,sus");
                                   // if(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT"))
                                  //  {
                                        ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_JOIN_REPORTS WHERE EMPLOYEE_ID=? and OFFICE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                                        ps.setInt(1,strEId);
                                        ps.setInt(2, user_offid);
                                        rs=ps.executeQuery();
                                        if(rs.next()) 
                                        {
                                            xml=xml+"<flag>failure2</flag>";
                                            // System.out.println("hai");
                                        }
                                    //}  
                                    else 
                                        {
                                            
                                                
                                                    rs.close();
                                                    ps.close();
                                             //ps=con.prepareStatement("SELECT EMPLOYEE_ID,EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,DATE_OF_BIRTH,GPF_NO FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                                              System.out.println("inside else part");      
                                              ps=con.prepareStatement("select a.employee_id,a.employee_name ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,a.date_of_birth,a.gpf_no,b.designation_id,c.cadre_id,d.cadre_name," +
                                                                      " d.cadre_short_name from" +
                                                                      " (" +
                                                                      " select employee_id,employee_name,employee_initial,date_of_birth,gpf_no from hrm_mst_employees" +
                                                                      " where employee_id=?" +
                                                                      " ) a" +
                                                                      " left outer join" +
                                                                      " (" +
                                                                      " select employee_id,designation_id from hrm_emp_current_posting" +
                                                                      " ) b" +
                                                                      " on a.employee_id=b.employee_id" +
                                                                      " left outer join" +
                                                                      " (" +
                                                                      " select designation_id,cadre_id from hrm_mst_designations" +
                                                                      " ) c" +
                                                                      " on b.designation_id=c.designation_id" +
                                                                      " left outer join" +
                                                                      " (" +
                                                                      " select cadre_id,cadre_name,cadre_short_name from hrm_mst_cadre" +
                                                                      " ) d" +
                                                                      " on c.cadre_id=d.cadre_id");       
                                                    
                                                    ps.setInt(1,strEId);
                                                    rs=ps.executeQuery();
                                                    int c=0;
                                                            while(rs.next())
                                                            {
                                                            
                                                                java.sql.Date DateOfFormation = rs.getDate("DATE_OF_BIRTH");  
                                                                String DateToBeDisplayed="";
                                                                if(DateOfFormation==null)
                                                                {
                                                                    DateToBeDisplayed="Not Specified";
                                                                }
                                                                else
                                                                {
                                                                    try
                                                                    {
                                                                        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                                                        DateToBeDisplayed=sdf.format(DateOfFormation);
                                                                    }
                                                                    catch(Exception e)
                                                                    {
                                                                        System.out.println("error while formatting date : " + e);
                                                                        DateToBeDisplayed="Not Specified";
                                                                    }
                                                                }     
                                                                 String id=rs.getString("EMPLOYEE_ID");
                                                                 System.out.println(id);
                                                                String name=rs.getString("EMPLOYEE_NAME");
                                                                System.out.println(name);
                                                                String cadre=rs.getString("CADRE_NAME");
                                                                System.out.println(cadre);
                                                                String cad_name=rs.getString("CADRE_SHORT_NAME");
                                                                System.out.println(cad_name);
                                                                
                                                                //String dob=rs.getString("DATE_OF_BIRTH");
                                                                //System.out.println(dob);
                                                                int gpfno=rs.getInt("GPF_NO");
                                                                System.out.println(gpfno);
                                                                
                                                                //ps=con.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                                                 ps=con.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                                                              
                                                                ps.setInt(1,strEId);
                                                                               
                                                                rs=ps.executeQuery();
                                                                String maxtodate="";
                                                                String maxsession="";
                                                                if(rs.next())
                                                                {
                                                                    if(rs.getDate("maxtodate")!=null)
                                                                    {
                                                                        maxtodate=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("maxtodate"));
                                                                        System.out.println("max to date :"+rs.getDate("maxtodate"));
                                                                    }
                                                                    else {
                                                                        
                                                                        maxtodate="empty";
                                                                    }
                                                                    if(rs.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                                                        maxsession=rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                                                    }
                                                                    else {
                                                                        maxsession="FN";
                                                                    }
                                                                }
                                                                
                                                                ps=con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                                                ps.setInt(1,strEId);
                                                                rs=ps.executeQuery();
                                                                
                                                                if(rs.next())
                                                                {
                                                                    if(rs.getString("EMPLOYEE_STATUS_ID")!=null)
                                                                    {
                                                                       empstatus=rs.getString("EMPLOYEE_STATUS_ID");
                                                                    }
                                                                    else {
                                                                        
                                                                        empstatus="WKG";
                                                                    }
                                                                }
                                                                
                                                                if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                                                {
                                                                    xml=xml+"<admin>YES</admin>";
                                                                }
                                                                else 
                                                                {
                                                                    xml=xml+"<admin>NO</admin>";
                                                                }
                                                                
                                                            xml=xml+"<flag>success</flag><inclusion>Yes</inclusion><eid>"+id+"</eid><dob>"+DateToBeDisplayed+"</dob><gpfno>"+gpfno+"</gpfno>" +
                                                            "<name>"+name+"</name><cadre>"+cadre+"</cadre><cad_name>"+cad_name+"</cad_name>" +
                                                            "<maxtodate>"+maxtodate+"</maxtodate><workingstatus>"+empstatus+"</workingstatus>" +
                                                            "<maxsession>"+maxsession+"</maxsession>";
                                                            c++;
                                                            }
                                                            if(c==0) {
                                                                xml=xml+"<flag>failure</flag>";
                                                            }
                                                }
                                                
                                        
                                    } 
                           
                    }
                        
                }
              }
            con.close();
            }
            catch(Exception e) {
            
                System.out.println("Exception in displaying emp details........"+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
            
        else if(strCommand.equalsIgnoreCase("getPostid")) {
            
            xml="<response><command>getPostid</command>";
            
            int desigcode=Integer.parseInt(request.getParameter("desigcode"));
            int postid=0;
            try {
               
                ps=con.prepareStatement("select post_rank_id from hrm_mst_designations  where designation_id=?");
                              
                ps.setInt(1,desigcode);
                               
                rs=ps.executeQuery();
                
                while(rs.next())
                {
                
                    //java.sql.Date DateOfFormation = rs.getDate("DATE_OF_BIRTH");  
                    
                     postid=rs.getInt("post_rank_id");
                    System.out.println("post_rank_id======"+postid);
                xml=xml+"<flag>success</flag><postid>"+postid+"</postid>";
                }
                
               
            }
            catch(Exception e) {
            
                 System.out.println("Exception in displaying emp details........"+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        
        System.out.println("xml is:dddddddd"+xml);
        out.write(xml);
        out.flush();
        out.close();
        
    }
}