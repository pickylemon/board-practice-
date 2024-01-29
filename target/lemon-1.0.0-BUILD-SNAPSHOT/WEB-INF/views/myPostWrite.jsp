<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<c:set var="loginId" value="${empty sessionScope.id? '': sessionScope.id }"/>
<c:set var="loginOutLink" value="${empty loginId ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${empty loginId? 'Login' : 'Logout'}"/>
<c:set var="bno" value="${param.bno}"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/boardList'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>
<h1>게시물 쓰기</h1>
<form id="myPost">
    <%--    <c:set var="boardDto" value="${boardDto}"/>--%>
    <input name="bno" type="hidden" value=""/>
    제목 : <input name="title" type="text"  />
    작성자 : <input name="writer" type="text" value='${loginId}' readonly="readonly"/>
    내용 : <textarea name="content" class="readonly" id="" cols="30" rows="10" ></textarea>
</form>
<%--<form id="imgPost" enctype="multipart/form-data">--%>
<%--    파일 첨부: <input name="image" type="file" accept="image/png, image/jpg, image/jpeg"/>--%>
<%--</form>--%>
<button type="button" class="wrtBtn">등록</button>
<button type="button" class="delBtn">취소</button>

<script>
    window.onload = function(){
        const wrtBtn = document.querySelector(".wrtBtn");
        wrtBtn.addEventListener("click",function(){
            const form = document.querySelector("#myPost");
            // const imgForm = document.querySelector("#imgPost");
            form.action = "<c:url value='/board/write'/>"
            form.method = "post";
            <%--imgForm.action = "<c:url value='/board/write'/>"--%>
            <%--imgForm.method = "post";--%>
            form.submit();
        });

        const delBtn = document.querySelector(".delBtn");
        delBtn.addEventListener("click",function(){
            let result = confirm("게시글 작성을 취소하고 목록으로 돌아가시겠습니까?");
            if(result){
                location.href="<c:url value='/board/boardList'/>"
            }
        })
    }
</script>

</body>
</html>
