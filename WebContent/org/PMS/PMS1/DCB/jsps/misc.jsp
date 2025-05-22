<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.io.File,java.util.ResourceBundle"  %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	long f=Runtime.getRuntime().freeMemory() / 1024;
	long t=Runtime.getRuntime().totalMemory()  ;
    out.println(t + " - "+ f);
    long maxMemory = Runtime.getRuntime().maxMemory();
    out.println( " maxMemory "+ maxMemory);
    
    File[] roots = File.listRoots();

    /* For each filesystem root, print some info */
    for (File root : roots) {
       out.println("File system root: " + root.getAbsolutePath()+"<br>");
       out.println("Total space (bytes): " + ((root.getTotalSpace()/1024)/1024)/1024+"<br>");
       out.println("Free space (bytes): " + ((root.getFreeSpace()/1024)/1024)/1024+"<br>");
       out.println("Usable space (bytes): " + ((root.getUsableSpace()/1024)/1024)/1024);
    }
 %>  
  



</body>
</html>