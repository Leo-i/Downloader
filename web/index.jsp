
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@page import="Provider.FileListProvider"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<File> files = FileListProvider.getFiles(getServletContext());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Загрузка файла</h1>
        <form method="post" action="upload.jsp" enctype="multipart/form-data">
            <input type="file" name="file"><br><br>
            <input type="submit" value="Загрузить">
        </form>
        <br>
        
        <%for (File file : files) {%>
        <li><a href="download.jsp?filename=<%=file.getName()%>">Загрузить <%=file.getName()%></a></li>
        <%}%>
</body>
</html>
