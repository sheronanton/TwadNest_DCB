 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	
  <html:form action="/inputAction" >
	  
 
 
 
 	 <table>
                <tr>
                    <td>
                        Select Country :
                    </td>
                    <td>
                        <html:select property="type" >
                            <html:option value="0">Select Country</html:option>
                            <html:optionsCollection name="TypeForm" property="typeList" label="typeName" value="typeId" />
                        </html:select>
                    </td>
                </tr>
    </table>
    </html:form>
</body>
</html>