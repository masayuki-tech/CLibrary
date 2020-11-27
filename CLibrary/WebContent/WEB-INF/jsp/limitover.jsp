<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="model.rentlogsDTO"%>
<%
List<rentlogsDTO> limitOverList = (List<rentlogsDTO>) session.getAttribute("limitOver");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>
	<table border="1">
	<tr><td>貸出ID</td><td>貸出日付</td><td>返却日</td><td>スタッフID</td><td>本の名前</td></tr>
	<%for(rentlogsDTO limitOverList1:limitOverList){ %>
	<tr><td><%= limitOverList1. getRent_Id()%></td>
	<td><%=limitOverList1.getRent_Date()%></td>
	<td><%=limitOverList1.getReturn_Date()%></td>
	<td><%=limitOverList1. getStaff_Id() %></td>
	<td><%=limitOverList1.getBook_Id()%></td>
	</tr>
	<%}%>
	</table>
</body>
</html>