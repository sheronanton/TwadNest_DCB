<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <meta http-equiv="cache-control" content="no-cache">
    <title>Account Number List  | TWAD Nest - Phase II </title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function btnsubmit()
    {

    	        
        var sele=document.getElementsByName("choice").length;
        var val=0;
        var getId='',stateid='',statedesc='',statusid='',statusdesc='',schemetypeid='',schemetypedesc='',schemeno='',schemename='',compono='',componame='',serialno='';
        
        if(sele>0)
        {
           
            if(sele==1)
            {
             
                if(document.frm_scheme.choice.checked==true)
                {
                 getId=document.frm_scheme.choice.value;
                 r=document.getElementById(getId);
               //  stateid=document.getElementById("area"+getId).value;
                 statusid=document.getElementById("status"+getId).value;
                 schemetypeid=document.getElementById("schtype"+getId).value;
                 
                 rcells=r.cells;
               ///  statedesc=rcells.item(1).firstChild.nodeValue;
                 statusdesc=rcells.item(2).firstChild.nodeValue;
                 schemetypedesc=rcells.item(3).firstChild.nodeValue;
                 schemeno=rcells.item(4).firstChild.nodeValue;
                 schemename=rcells.item(5).firstChild.nodeValue;
                 compono=rcells.item(6).firstChild.nodeValue;
                 componame=rcells.item(7).firstChild.nodeValue;
                 serialno=document.getElementById("serialno").value;
                 
                }
            }
            else
            {      //alert("else"+sele);
                    for(i=0;i<sele;i++)
                    { 
                        //alert(document.frm_AccNo_Popup_Form.choice[i].checked)
                        if(document.frm_scheme.choice[i].checked==true)
                        {
                             getId=document.frm_scheme.choice[i].value;
                             
                             r=document.getElementById(getId);

                           //  stateid=document.getElementById("area"+getId).value;
                             statusid=document.getElementById("status"+getId).value;
                             schemetypeid=document.getElementById("schtype"+getId).value;
                             
                             rcells=r.cells;
                            // statedesc=rcells.item(1).firstChild.nodeValue;
                             statusdesc=rcells.item(2).firstChild.nodeValue;
                             schemetypedesc=rcells.item(3).firstChild.nodeValue;
                             schemeno=rcells.item(4).firstChild.nodeValue;
                             schemename=rcells.item(5).firstChild.nodeValue;
                             compono=rcells.item(6).firstChild.nodeValue;
                             componame=rcells.item(7).firstChild.nodeValue;
                             serialno=document.getElementById("serialno").value;
                             break;
                        }
                    }
             }
           
            
        }
            
        Minimize();
        //alert(Acc_Head_Code+"    "+Bank_Acc_No+"    "+bankid+"    "+br_id+"    "+B_name)
        opener.doParentScheme(stateid,statusid,schemetypeid,statedesc,statusdesc,schemetypedesc,schemeno,schemename,compono,componame,serialno);
        
        self.close();
        return true;
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
      <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
  </head>
  <body>
  
  <form name="frm_scheme" id="frm_scheme">
     
        <%
  
  Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
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

       //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
      
      
     
      <table  border="0" width="100%" align="center">
      <tr><td>
       <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%" style="BORDER-COLLAPSE: collapse"   borderColor="#92c2d8">
         <tr  >
        <td align="center" colspan="2" class="tdText">
                <font size=4>Selection of Schemes</font>
                </td>
           </tr>
     </table>
      
         
        
         <table id="mytable" align="center"  cellspacing="0" class="fb2"  cellpadding="2"  width="100%"   style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
          <tr class="tdText">
            <th>
              Select
            </th>
            <th>
                Office/State based Ref
            </th>
             <th>
               Scheme Status
            </th>
            <th>
                Scheme Type
            </th>
            <th>
                Scheme No
            </th>
            <th>
             scheme Name
            </th>
            <th>
           Component No
            </th>
            <th>
           Component Name
            </th>
            
            
          </tr>
          <tbody id="tb" class="table">
          
          
           <%
           ResultSet  rs2=null,rs3=null;
           PreparedStatement ps2=null,ps3=null;
       try
       {
           int Office_code=0,serialNo=0,y=0;
           String txtModule_Type="",cr_dr_indi="",unspent_OR_col="";
           
            try{
             Office_code=Integer.parseInt(request.getParameter("Office_code"));
            }catch(Exception e){System.out.println("Exception in getting Office_code:"+e);}
           
            try{
            	serialNo=Integer.parseInt(request.getParameter("serialno"));
               }catch(Exception e){System.out.println("Exception in getting serialno:"+e);}
              
            
            
            
            String sql="select a.* ,c.sch_type_desc,d.sch_status_desc from  \n"+
            	" ( \n"+
                " select  sch_status_id,sch_type_id,sch_sno,sch_name from PMS_SCH_MASTER  where sch_sno in ( select SCH_SNO from  PMS_DCB_DIV_SCHEME_MAP where  OFFICE_ID=? ) \n"+
                " )a  \n"+
       			"   \n"+
            	" left outer join pms_sch_lkp_type c on c.sch_type_id=a.sch_type_id  \n"+
            	" left outer join pms_sch_lkp_status d on d.sch_status_id=a.sch_status_id ";
            System.out.println(Office_code);
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            ps.setInt(1,Office_code);
            rs=ps.executeQuery();

            int rowid=0,schNo=0,componentNo=0;   
            String ComponentName="--";
            while(rs.next())
            {
                rowid++;
                out.println("<tr id="+rowid+">");   
               if(y==0)
                {
                out.println("<td><input type='radio' id='choice' name='choice' checked value='"+rowid+"'/></td>");
                y=1;
                }
                else
                out.println("<td><input type='radio' id='choice' name='choice' value='"+rowid+"'/></td>");
              // out.println("<input type=hidden value="+rs.getInt("sch_area_level_id")+" id=area"+rowid+" >");
               out.println("<input type=hidden value="+rs.getInt("sch_status_id")+" id=status"+rowid+" >");
               out.println("<input type=hidden value="+rs.getInt("sch_type_id")+" id=schtype"+rowid+" >");
               	String dis_1="";//rs.getString("sch_area_level_desc");
                out.println("<td align='left'>"+ ((dis_1!=null)?dis_1:"-")+"</td>");
                out.println("<td align='left'>"+rs.getString("sch_status_desc")+"</td>");
                out.println("<td align='left'>"+rs.getString("sch_type_desc")+"</td>");
                schNo=rs.getInt("sch_sno");
                out.println("<td align='left'>"+schNo+"</td>");
                out.println("<td align='left'>"+rs.getString("sch_name")+"</td>");
                
                ps2=con.prepareStatement("Select COMP_SNO,COMP_ID from PMS_SCH_COMPONENT where SCH_SNO=?");
                ps2.setInt(1,schNo); 
                //ps2.setInt(2,Office_code);
                rs2=ps2.executeQuery();
                
             if(rs2.next())
             {
            	 componentNo=rs2.getInt("COMP_SNO");
            	 ps3=con.prepareStatement("Select COMP_DESC from PMS_SCH_LKP_COMPONENT where COMP_ID=?");
                 ps3.setInt(1,rs2.getInt("COMP_ID")); 
                 
                 rs3=ps3.executeQuery();
                 if(rs3.next())
                 {
                	 ComponentName=rs3.getString("COMP_DESC");
                 }
                 
            	 
             }else{
            	 componentNo=0;
            	 ComponentName="--";
             }
                
              if(componentNo==0)                  
            	  out.println("<td align='left'>--</td>");
              else
            	  out.println("<td align='left'>"+componentNo+"</td>");
                            
                out.println("<td align='left'>"+ComponentName+"</td>");
               
            }
            if(y!=0)
             {
                    rs2.close();
                    ps2.close();
             }
             else
             out.println("<tr><td align='left'>No data found</td><td></td><td></td><td></td><td></td><td></td></tr>");



%>
          
          
          
</tbody>
</table>
<input type="hidden" value="<%=serialNo %>" id="serialno">

 <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdText">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick=" btnsubmit()" class="fb2">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()"  class="fb2">
      </div>
      </td>
      </tr>
      
      </table>
 
</div>
</td>
</tr>
</table>
</form>
<%}
       catch(Exception e)
       {
       System.out.println("Exception in grid.."+e);
       } %>
</body>
</html>