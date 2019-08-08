<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主页</title>
    <style type="text/css">
        body {
            background-image: url(img/index.jpg);
            background-size: 1450px 700px;
            background-repeat: no-repeat;

        }

        #main {
            width: 65px;
            height: 350px;

            display: -webkit-flex;
            display: flex;
            -webkit-flex-wrap: wrap;
            flex-wrap: wrap;
            -webkit-align-content: center;
            align-content: center;
        }

        p {
            margin-left: 650px;
            font-size: 200%;
            color: #00bfff;
        }

        h1 {
            text-align: center;
        }

        #abd {
            font-size: larger;
        }
    </style>

</head>
<body>
<div id="main">
    <div style=""></div>
    <div style=""></div>
    <div style=""></div>
</div>

<h1><font color="#00bfff" size="50pm">歡迎使用DELTA-MES</font></h1>
<p>--數據管理系統</p>
<%--<div id="abd"><a href="${Request.getContextPath}/pages/main.jsp">請點擊登錄</a></div>--%>
<input id="abd" type="button" style="margin: 0 auto; display: block;" value="請點擊登錄"
       onclick="location.href='${Request.getContextPath}/pages/main.jsp'">
<%--<button id="abd" onclick="location.href='${Request.getContextPath}/pages/main.jsp'" style="margin: 0 auto; display: block;">dianjidenglu</button>--%>
</body>
</html>