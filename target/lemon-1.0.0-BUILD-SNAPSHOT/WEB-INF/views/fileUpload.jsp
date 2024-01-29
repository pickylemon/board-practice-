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
    <form action="multipartFile" method="POST" enctype="multipart/form-data">
        제목 : <input type="text" name="title"><br/>
        파일 : <input type="file" name="f"><br/>
        <input type="submit" value="MultipartFile 예로 전송"/>
    </form>
<script>
    if("${msg}"=="UPLOAD_ERR"){
        alert("업로드에 실패했습니다. 다시 시도해주세요.");
    }
</script>
</body>
</html>