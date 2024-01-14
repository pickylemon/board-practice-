<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session ="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOut" value="${loginId==null? 'Login':'Logout'}"/>
<c:set var="loginOutLink" value="${loginId==null? '/login/login':'/login/logout'}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">

<%--    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>--%>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }

        .container {
            width : 50%;
            margin : auto;
        }

        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }

        input {
            width: 100%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }

        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }

        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
        }

        .btn:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li>
            <div id="login">
                <a href="<c:url value='${loginOutLink}'/>">${loginOut}</a><br>
                <b>ID = ${loginId}</b>
            </div>
        </li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<h2>게시물 ${mode eq "new" ? "글쓰기" : "읽기"}</h2>
<form action="" id="form">
    <input type=${mode=="new"? "hidden" : "text"} name="bno" value="${boardDto.bno}" readonly="readonly">
    <input type="text" name="title" value="${boardDto.title}" ${mode=="new"? "":'readonly="readonly"'}>
    <textarea name="content" id="" cols="30" rows="10" ${mode eq "new"? "" : 'readonly="readonly"'}>${boardDto.content}</textarea>
    <button type="button" id="writeBtn" class="btn">글쓰기</button>
    <button type="button" id="modifyBtn" class="btn">수정</button>
    <button type="button" id="removeBtn" class="btn">삭제</button>
    <button type="button" id="listBtn" class="btn">목록</button>
</form>
<script>
    let msg = "${msg}";
    if(msg=="WRT_ERR") alert("게시물 등록에 실패했습니다. 다시 시도해주세요.");
    if(msg=="MOD_ERR") alert("게시물 수정에 실패했습니다. 다시 시도해주세요.");
    $(document).ready(function(){ //main()
        $('#listBtn').on("click",function(){
            location.href="<c:url value='/board/list'/>?page=${param.page}&pageSize=${param.pageSize}";
            //location은 브라우저의 주소창을 의미
            //주소창에서 바로 이동하는 것은 Get Mapping //read가 board.jsp로 연결되는데 요청이 get이라서 param.page로 접근해야 하는듯.
        });
        $('#removeBtn').on("click",function(){
            if(!confirm('게시물을 정말로 삭제하시겠습니까?')) return;
            let form = $('#form'); //form의 참조를 얻어오기
            form.attr("action","<c:url value='/board/remove'/>?page=${param.page}&pageSize=${param.pageSize}"); //form의 action attribute를 지정
            form.attr("method","post");
            form.submit();
        });
        $('#writeBtn').on("click",function(){
            let form = $('#form');
            form.attr("action","<c:url value='/board/write'/>");
            form.attr("method","post");
            form.submit();
        });
        $('#modifyBtn').on("click",function(){
            //1. 읽기 상태이면(읽기 상태에 클릭했을 때) 수정 상태로 변경
            let form = $('#form');
            let isReadOnly = $("input[name=title]").attr('readonly');
            if(isReadOnly=='readonly') {
                // title과 textarea의 readonly를 false로 바꾸기
                $("input[name=title]").attr('readonly',false);
                $("textarea").attr('readonly',false);
                $("#modifyBtn").html("등록");
                $("h2").html("게시물 수정");
                return; //읽기 상태일때는 위의 코드만 수정하고 빠져 나오기. return문 안 써주면 form 전송됨.
            }

            //2. 수정 상태이면(수정상태에 클릭했을 때) 수정된 내용을 서버로 전송
            form.attr("action","<c:url value='/board/modify'/>?page=${param.page}&pageSize=${param.pageSize}");
            form.attr("method","post");
            form.submit();


        });
    });
</script>
</body>
</html>