<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<div id="list">

</div>
<script>
    if("${msg}"=="UPLOAD_OK") {
        alert("성공적으로 업로드되었습니다.");
    }
    window.onload = function(){
        console.log(${list});
    }
</script>
</body>
</html>
