<html xhtml="true">
<%@page
	import="Servlets.PMS.PMS1.DCB.servlets.*,Servlets.Security.classes.UserProfile"%>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

<title>Insert title here</title>
<style type="text/css">
 
			A:link {text-decoration: none}
			A:visited {text-decoration: none}
			A:active {text-decoration: none}
			A:hover {font-size:15; font-weight:bold; color: red;}
 

</style>
<style type="text/css">
.NewsTable
{
background-color: White;
filter: alpha(opacity=75)-moz-opacity:0.75; 
opacity: 0.75;
border: 2pxsolidblack;
}
.dDIV 
{
position: absolute;
	width: 90%;
	height: 180em;
	top: 12%;
	right: auto;
	bottom: 300px;
	left: 30px;
 	BORDER-RIGHT: #1ceeb 1px solid;
  PADDING-RIGHT: 2px;
  BORDER-TOP: #87ceeb 1px solid;
   PADDING-LEFT: 2px;
  FONT-WEIGHT: bolder;
  FONT-SIZE: x-large;
  PADDING-BOTTOM: 2px;
  MARGIN: 1px 10% 10px;
  TEXT-TRANSFORM: none;
  BORDER-LEFT: #7ceeb 1px solid;
  COLOR: #191970;
  PADDING-TOP: 2px;
  BORDER-BOTTOM: #87ceeb 1px solid;
  FONT-STYLE: normal;
  FONT-FAMILY: Verdana, Arial;
  BACKGROUND-COLOR: #b0c4de;
  TEXT-ALIGN: left;
  FONT-VARIANT: normal;
  TEXT-DECORATION: none 
}
.tab1
{

    border-color: #600;
    border-width: 1px 1px 1px 1px;
    border-style: solid;

}
 .bt
 {
     
	 
		 
		border:0px solid #000fff;
		background:#fff url('./images/ragged-left.gif') repeat-x left top;
		 
 }
.divs {
	position: absolute;
	width: 90%;  
	height: 180em;
	top: 8%;
	right: auto;
	bottom: 100px;
	left: 0;
}

.divs1 {
	position: absolute;
	width: 90%;
	height: 180em;
	top: 12%;
	right: auto;
	bottom: 300px;
	left: 30px;
}
  
.divs2 {
	position: absolute;
	width: 90%;
	height: 180em;
	top: 12%;
	right: auto;
	bottom: 300px;
	left: 150px;
}
  
.divs3 {
	position: absolute;
	width: 90%;
	height: 180em;
	top: 12%;
	right: auto;  
	bottom: 300px;  
	left: 350;
}
</style>
<script type="text/javascript">
var prvid="";
var i=0;
var sid="";
var msid="";
var mprvid="";
function catshow(event,id,k,sc) 
{
	var e = document.getElementById("catmrid"+id+""+sc);  
		e.style.visibility = "";
		prvid="catmrid"+id+""+sc;
	if (sid!="")
	{
		try
		{
		    var e1 = document.getElementById(sid);  
			    e1.style.visibility = "hidden";
			    sid="";
		}catch(e) {}
				sid=prvid;
	}else
	{ 
			sid=prvid;
	}
} 
 
 
function prvid(a)
{
	var sid="";

	if (sid!=prvid)
	{
		sid=a;	
	}else
	{
		prvid="";
	}
	
}
function findPosX(obj)
{
  var curleft = 0;
  if(obj.offsetParent)
      while(1) 
      {
        curleft += obj.offsetLeft; 
        if(!obj.offsetParent)
          break;
        obj = obj.offsetParent;
      }
  else if(obj.x)
      curleft += obj.x;
  return curleft;
}

function findPosY(obj)
{
  var curtop = 0;
  if(obj.offsetParent)
      while(1)
      {
        curtop += obj.offsetTop;
        if(!obj.offsetParent)
          break;
        obj = obj.offsetParent;
      }
  else if(obj.y)
      curtop += obj.y;
  return curtop;
}
	function show1(event,id) {

		var tot=document.getElementById("tot").value;
		for (i=1;i<=parseInt(tot);i++)
		{
			if (parseInt(i)!=parseInt(id))
				{
					var e = document.getElementById("rid"+i);		
					e.style.visibility = "hidden";
				}
		}
  
	}  
	 
	function menushowlast(id)
	{
		 
		 try
		 {
				var e = document.getElementById(id);
				 e.style.visibility = "";

					 mprvid=id;
					if (msid!="")
					{
						try
						{
						    var e1 = document.getElementById(msid);  
							    e1.style.visibility = "hidden";
							    msid="";
						}catch(e) {}
						msid=mprvid;
					}else
					{ 
						msid=mprvid;
					}
    
				    
		 }catch(e) {
 
			 }	 
	} 	     
	
	function show(event,id) {
		 
		var e = document.getElementById("rid"+id);
		 e.style.visibility = "";
		
		show1(event,id) 
	} 
  
	function mshow1(event,id,k) {
		 
	
		var tot1=document.getElementById("tot").value;

		   
		for (var i=1;i<=parseInt(tot1);i++)
		{  
			var tot2=document.getElementById(i+"tot2").value;
			for (var j=1;j<=parseInt(tot2);j++)
			{
				if (id!=(""+parseInt(i)+""+j))
				{
				var e = document.getElementById("mrid"+parseInt(i)+""+j);		
				e.style.visibility = "hidden";
				} 
			}  

		}
		
		/*var tot=document.getElementById(k+"tot2").value;

		var idstr=new String (id);
		for (i=1;i<=parseInt(tot);i++)
		{	

			var str=new String (k+""+i);
			
			if ( parseInt(str)!=parseInt(idstr))
				{
				 alert(""+str)
					var e = document.getElementById("mrid"+parseInt(str));		
					e.style.visibility = "hidden";
				}
		}
		*/
	} 


	function mshow(event,id,k) {
		 
		var e = document.getElementById("mrid"+id);
		 e.style.visibility = "";
	//	 e.style.position = "absolute";
		// e.style.float="left";
		// e.style.bottom =500;
		// e.style.left = 10;  
		 mshow1(event,id,k) 


			 
	} 

	function fopen(path)
	{
		var s = window.open(path,"","status=0,toolbar=0,menubar=0,resizable=yes");

		
	}
</script>

</head>
<body>

<%
	Connection con;
	Controller obj = null;
	int menucat = 0;
	try {
		obj = new Controller();
		con = obj.con();
		obj.createStatement(con);
		UserProfile up = null;
		up = (UserProfile) session.getAttribute("UserProfile");
		String profile = (String) session.getAttribute("profile");
		String userid = (String) session.getAttribute("UserId");
		System.out.println("TEST ************** " + profile
				+ up.getEmployeeId());
		String cond = "";
		if (profile.equalsIgnoreCase("twad")) {
			cond = " SEC_MST_USER_ROLES where Employee_Id ="
					+ up.getEmployeeId();
		} else {
			cond = " SEC_MST_OTHER_USER_ROLES where USER_ID ='"
					+ userid + "'";
		}

		ResultSet rs_id = obj.getRS("select Role_Id from     " + cond
				+ " order by List_Seq_No");
		String temp = "";
		while (rs_id.next()) {
			if (temp == "") {
				System.out.println("TEST ************** ");

				temp = rs_id.getString("Role_Id");
			} else {
				temp += "," + rs_id.getString("Role_Id");
			}
		}
		String major_id = "";
		StringBuffer menucategoryCheck = new StringBuffer();
		StringBuffer menusubCheck = new StringBuffer();
		try {
			major_id = request.getParameter("majorid");
			System.out.println("major_id=" + major_id);
		} catch (Exception e) {
			major_id = "0";
		}
		System.out.println("major_id=" + major_id);

		String sub = " select Major_System_Short_Desc,Major_System_Id from SEC_MST_MAJOR_SYSTEMS where Major_System_Id in( select distinct(Major_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in("
				+ temp + "))";
		ResultSet rs_sub = obj.getRS(sub);
		ResultSet rs_sub2 = rs_sub;
%>
<table border=0 style="vertical-align: top;" width="100%" id='mtab' class="NewsTable">
	<tr>
		<%  
			int i = 0;
				while (rs_sub.next()) {
					i++;
		%>
		<td align="center"> <input type="button" value="<%=rs_sub.getString("Major_System_Short_Desc")%>"  onmouseover="show(event,<%=i%>)"  class="bt"></td>
		<%
			}
		%>
	</tr>
</table>
<%
	int k = 0;
		int srow = 0;
		int srow1 = 0, sc = 0;;
		rs_sub.close();
		rs_sub = obj.getRS(sub);
		while (rs_sub.next()) {
			major_id = rs_sub.getString("Major_System_Id");

			k++;
			String minor = "select * from SEC_MST_MINOR_SYSTEMS where  Minor_System_Id in (select distinct(Minor_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in ("
					+ temp
					+ ") and Major_System_Id='"
					+ major_id
					+ "')";

			PreparedStatement pst = con.prepareStatement(minor);
			PreparedStatement pstS = con.prepareStatement(minor);
			ResultSet rs_min = pst.executeQuery();
			ResultSet rs_min2 = pstS.executeQuery();
			String div = "<div id=rid"
					+ k
					+ " style='visibility: hidden;' class='divs'  ><table border='0'    align=center  class='tab1' width='95%'><tr>";

			srow = 0;
			while (rs_min.next()) {
				srow++;
				div += "<Td ><label onmouseover=mshow(event," + k + ""
						+ srow + "," + k + ")>"
						+ rs_min.getString("Minor_System_Short_Desc")
						+ "</label></a></td>";
			}
%>
<input type="hidden" id="<%=k%>tot2" value="<%=srow%>">
<%
	div += " </tr></table></div>";
			srow = 0;
			while (rs_min2.next()) {
				srow++;
				String subsystem = "select * from SEC_MST_SUBSYSTEMS where sub_system_id in(select distinct(sub_system_id) from SEC_MST_ROLE_MENUS where Role_Id in("
						+ temp
						+ ") and Major_System_Id='"
						+ rs_min2.getString("Major_System_Id")
						+ "' and minor_system_id='"
						+ rs_min2.getString("Minor_System_Id")
						+ "') and Major_System_Id='"
						+ rs_min2.getString("Major_System_Id")
						+ "' and minor_system_id='"
						+ rs_min2.getString("Minor_System_Id")
						+ "' order by sub_system_id";
 
				System.out.println(subsystem);
				PreparedStatement pst1 = con  
						.prepareStatement(subsystem);
				ResultSet rs_min1 = pst1.executeQuery();
				String div1 = "<div   class='dDiv'  id=mrid"
						+ k
						+ ""   
						+ srow
						// + "  class='divs1'   style='visibility: hidden;'   ><table border='0'  width='25%' align=left><tr>";
						 + "  class='divs1'   style='visibility: hidden;'>";
				String subname = "", minorid = "", submenuid = "";
				while (rs_min1.next()) {

					sc++;  
					subname = rs_min1
							.getString("Sub_System_Short_Desc");
					minorid = rs_min1.getString("MINOR_SYSTEM_ID");
					submenuid = rs_min1.getString("SUB_SYSTEM_ID");
				//	 div1 += "<Tr style='background-image:url(images\\newimg2.jpg); background-repeat:no-repeat;' ><td><font size=2><a href='javascript:catshow(event,"+k+""+srow+","+k+","+sc+ ")'>" + subname + "</a></font> </td></tr>";
				 	div1 += "<font size=2><a href='javascript:catshow(event,"+k+""+srow+","+k+","+sc+ ")'>" + subname + "</a><Br></font>  ";
					String category = "SELECT DISTINCT(Menu_Category) as Menu_Category,CATEGORY_SEQ_NO "
							+ "   "
							+ " FROM Sec_Mst_Intranet_Menus "
							+ " WHERE Major_System_Id='"
							+ major_id
							+ "' "
							+ "AND Minor_System_Id  ='"
							+ minorid
							+ "' "
							+ "AND Sub_System_Id    ='"
							+ submenuid
							+ "' "
							+ " and MENU_ID in(select MENU_ID from SEC_MST_ROLE_MENUS where Major_System_Id='"
							+ major_id
							+ "'  "
								+ " And Minor_System_Id='"
							+ minorid
							+ "'  "
							+ " And Sub_System_Id  ='"
							+ submenuid
							+ "'  "
							+ " and ROLE_ID in ("
							+ temp
							+ "))  "
							+ "AND Menu_Category   <>'null' "
							+ " order by CATEGORY_SEQ_NO";

					PreparedStatement pst1_menucat = con
							.prepareStatement(category);
					ResultSet rs_min1_menucat = pst1_menucat
							.executeQuery();
					
					     
					
					String div1_cat = "<br><div     id=catmrid"
							+ k
							+ ""
							+ srow 
							+ ""   
							+ sc  
						//	+ "  class='divs2'   style='visibility: hidden;'>&nbsp;&nbsp;<table border='1'  width='20%'>";
							+ "  class='divs2'   style='visibility: hidden;'>";
					String cat = ""; 
					int subcat = 0; 
					while (rs_min1_menucat.next()) {  
						subcat++;
						cat = rs_min1_menucat
								.getString("Menu_Category");        
						//div1_cat += "<Tr style='background-image:url(images\\newimg2.jpg); background-repeat:no-repeat;' ><Td><font size=2><a href=javascript:menushowlast('menudiv"
						div1_cat += " <font size=3><a href=javascript:menushowlast('menudiv"
								+ k
								+ ""
								+ srow  
								+ ""
								+ subcat
								+ ""
								+ sc
								+ "')>" + cat + "</font></a> <Br>";

						String menu = "SELECT MENU_DESC, "
								+ "  file_path, "
								+ "  category_seq_no "
								+ "FROM SEC_MST_INTRANET_MENUS "
								+ "WHERE Major_System_Id='"
								+ major_id
								+ "' "
								+ "AND Minor_System_Id='"
								+ minorid
								+ "' "
								+ "AND sub_system_id  ='"
								+ submenuid
								+ "' "
								+ " and MENU_ID in(select MENU_ID from SEC_MST_ROLE_MENUS where Major_System_Id='"
								+ major_id
								+ "' "
								+ " And Minor_System_Id='"
								+ minorid
								+ "' "
								+ " And Sub_System_Id  ='"
								+ submenuid
								+ "' "
								+ " and ROLE_ID in ("
								+ temp
								+ ")) "
								+ "AND Menu_Category  ='"
								+ cat
								+ "' "
								+ "ORDER BY category_seq_no, "
								+ "  ORDER_SEQ_NO";
						PreparedStatement pst1_menucat_menu = con
								.prepareStatement(menu);
						ResultSet rs_min1_menucat_menu = pst1_menucat_menu
								.executeQuery();

						String div_menu = "<div id='menudiv"
								+ k
								+ ""
								+ srow
								+ ""
								+ subcat
								+ ""
								+ sc
								+ "' class='divs3' style='visibility: hidden;'><table border=1>";
						while (rs_min1_menucat_menu.next()) {

							div_menu += "<tr><td><font size=2><a href='#' onclick=javascript:fopen('"+rs_min1_menucat_menu.getString("file_path")+"')>"
									+ rs_min1_menucat_menu
											.getString("MENU_DESC")
									+ "</a></font></td></tr>";
						}
						div_menu += "</table></div>";
						out.println(div_menu);

					}
					div1_cat += " </tr></table></div>";
					out.println(div1_cat);
				}
				div1 += "</tr></table></div>";
				out.println(div1);

			}  

			out.println(div);

		}
%>
<input type="hidden" id="tot" value="<%=i%>">
<input type="hidden" id="tot2" value="<%=sc%>">
<%     
	} catch (Exception e) {

		System.out.println("TEST Exception " + e);

	    }
%>  

 
</body>
 
</html>
