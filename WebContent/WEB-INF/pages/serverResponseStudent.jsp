<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.StringTokenizer" %>

<%
	String datos = request.getSession().getAttribute("data").toString();
%>

<html>
	<meta charset = "utf-8">
	<head>
		    <!-- CSS
	    ================================================== -->       
	    <!-- Bootstrap css file-->
	    <link href="../css/bootstrap.min.css" rel="stylesheet">
	    <!-- Font awesome css file-->
	    <link href="../css/font-awesome.min.css" rel="stylesheet">
	    <!-- Superslide css file-->
	    <link rel="stylesheet" href="../css/superslides.css">
	    <!-- Slick slider css file -->
	    <link href="../css/slick.css" rel="stylesheet"> 
	    <!-- Circle counter cdn css file -->
	    <link rel='stylesheet prefetch' href='https://cdn.rawgit.com/pguso/jquery-plugin-circliful/master/css/jquery.circliful.css'>  
	    <!-- smooth animate css file -->
	    <link rel="stylesheet" href="../css/animate.css"> 
	    <!-- gallery slider css -->
	    <link type="text/css" media="all" rel="stylesheet" href="../css/jquery.tosrus.all.css" />    
	    <!-- Main structure css file -->
	    <link href="../css/style.css" rel="stylesheet">
	      <!-- Custom css file -->
   		 <link href="../css/mystyles.css" rel="stylesheet" type="text/css">

		<title>Students List</title>
	</head>
	<body>
		<div class=".CSSTableGenerator">
			<table border = 1 >
				<tr><th align="LEFT">Student ID</th><th align="LEFT">Name</th><th align="LEFT">Address</th><th align="LEFT">Phone</th><th align="LEFT">Career</th><th align="LEFT">Plan</th></tr>
				
				<%
				System.out.println("Response from page with data: ");
				System.out.println(datos);
				StringTokenizer stringTokenizer = new StringTokenizer(datos, "*");
				while (stringTokenizer.hasMoreTokens()) {
					StringTokenizer sTokenizer = new StringTokenizer(stringTokenizer.nextToken(), "_");
				%>
					<tr><td><%=sTokenizer.nextToken()%></td><td><%=sTokenizer.nextToken()%></td><td><%=sTokenizer.nextToken()%></td><td><%=sTokenizer.nextToken()%></td><td><%=sTokenizer.nextToken()%></td><td><%=sTokenizer.nextToken()%></td></tr>
				<%
				}
				%>
			</table>
		</div>
	</body>
</html>