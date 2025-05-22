





<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>



</head>
<body>
<table>
<tr><td>items</td><td>Expenditure</td></tr>

 <%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;
            
             Connection connection=null;
        
             ResultSet results=null,rs2=null;
             ResultSet results1=null;
             ResultSet results2=null;
             try
             {
                   ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                   String ConnectionString="";
                   String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                   String strdsn=rs1.getString("Config.DSN");
                   String strhostname=rs1.getString("Config.HOST_NAME");
                   String strportno=rs1.getString("Config.PORT_NUMBER");
                   String strsid=rs1.getString("Config.SID");
                   String strdbusername=rs1.getString("Config.USER_NAME");
                   String strdbpassword=rs1.getString("Config.PASSWORD");
                //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
                   Class.forName(strDriver.trim());
                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
	      catch(Exception e)
             {
                       System.out.println("Exception in opening connection :"+e);
             }     
                          // Office id
                String ben="";        // office name
           
            try
            {
                   
                    ps=con.prepareStatement("SELECT Mitem.Main_Item_Sno, " +
                    		"  Sitem.Sub_Item_Sno, " +
                    		"  Mitem.Main_Item_Desc " +
                    		"  || '-' " +
                    		"  ||sitem.sub_item_desc AS description " +
                    		"FROM Pms_Ame_Mst_Main_Item Mitem " +
                    		"LEFT JOIN Pms_Ame_Mst_Sub_Item Sitem " +
                    		"ON MITEM.MAIN_ITEM_SNO=sitem.main_item_sno" );
                    results=ps.executeQuery();
                         while(results.next()) 
                         {
                           ben=results.getString("description");
                           %>
                           <tr>
                           <td>
                   		<input type="text" size="50" maxlength="50" value="<%=ben %>" /></td><td> <input type="text" size="50" maxlength="50" value="" >    
                   	</td></tr></table>
                   	<% 
                            System.out.println(results.getString("description"));
                         }
                    results.close();
                    ps.close();
                    
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>



</body>
</html>
