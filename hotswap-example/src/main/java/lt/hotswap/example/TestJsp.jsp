<%@ page import="java.lang.*"%>
<%@ page import="java.io.*"%>
<%@ page import="lt.hotswap.example.*"%>
<%
    InputStream is = new FileInputStream("c:/TestClass.class");
    byte[] b = new byte[is.available()];
    is.read(b);
    is.close();

    out.println("===============");
    out.println(JavaClassExecuter.execute(b));
    out.println("===============");

%>