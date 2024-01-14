<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<c:set var="loginId" value="${empty sessionScope.id? '': 'sessionScope.id'}"/>
<c:set var="loginOutLink" value="${empty loginId ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${empty loginId? 'Login' : 'Logout'}"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<style>
    .container {
        position: relative;
        width: 100%;
        display: flex;
        justify-content: center;
    }
    .board-container {
        position: absolute;
        width: 80%;
        margin : 0 auto;
        padding: 10px;
        text-align: center;
    }
    table {
        /*position: absolute;*/
        top: 70px;
        border-collapse: collapse;
        width: 100%;
        border-top: 2px solid rgb(39, 39, 39);
    }
    tr:nth-child(even) {
        background-color: #f0f0f070;
    }
    tr.hover:hover {
        background-color: #f3dbdb;
    }
    th,
    td {
        width:300px;
        text-align: center;
        padding: 10px 12px;
        border-bottom: 1px solid #ddd;
    }
    td {
        font-size: 12px;
        height: 15px;
        color: rgb(53, 53, 53);
    }

    .no      { width:150px;}
    .title   { width:50%;  }
    .view_cnt { width:150px;}
    .reg_date { width:200px;}

    td.title   { text-align: left;  }
    td.view_cnt { text-align: right; }

    td.title:hover {
        text-decoration: underline;
    }

    .wrtBtn {
        /*position: absolute;*/
        right: 0;
        margin: 10px;
        width: 100px;
        padding: 10px;
        border: 2px solid #333;
        border-radius: 50px;
        color: #333;
        font-weight: 700;
        text-align: center;
        cursor: pointer;
        box-sizing: border-box;
    }
</style>
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
<div class="container">
    <div class="board-container">
        <table>
            <tr>
                <th class="no"> 글번호 </th>
                <th class="title"> 제목 </th>
                <th class="writer"> 작성자 </th>
                <th class="view_cnt"> 조회수 </th>
                <th class="reg_date"> 등록일 </th>
            </tr>
            <c:forEach var="boardDto" items="${list}">
                <tr class="hover">
                    <td class="no">  ${boardDto.bno} </td>
                    <td class="no" style="cursor:pointer" onclick="location.href='<c:url value="/board/read?bno=${boardDto.bno}&page=${ph.page}"/>'"> ${boardDto.title} </a></td>
                        <%--                <a href="<c:url value='/board/read?bno=${boardDto.bno}'/>">--%>
                    <td class="writer">  ${boardDto.writer} </td>
                    <td class="view_cnt">  ${boardDto.view_cnt} </td>
                    <fmt:formatDate value="${boardDto.reg_date}" type="both" pattern="hh:mm YY/MM/dd" var="date"/>
                    <td class="reg_date">${date}</td>
                </tr>
            </c:forEach>
        </table>
        <div id="naviBar">
            <c:if test="${ph.prevPage}">
                <span><a href="<c:url value='/board/boardList?page=${ph.startPage-1}'/>">[PREV] </a></span>
            </c:if>
            <c:forEach var="i" begin="${ph.startPage}" end="${ph.endPage}">
                <span><a href="<c:url value='/board/boardList?page=${i}'/>">${i} </a></span>
            </c:forEach>
            <c:if test="${ph.nextPage}">
                <span><a href="<c:url value='/board/boardList?page=${ph.endPage+1}'/>">[NEXT]</a></span>
            </c:if>
            <button type="button" class="wrtBtn">글쓰기</button>
        </div>
    </div>
</div>

<script>
    window.onload = function(){
        let msg = "${msg}"
        if(msg==="DEL_OK")
            alert("게시글이 성공적으로 삭제되었습니다.");
        if(msg==="MOD_OK")
            alert("게시글이 성공적으로 수정되었습니다.");
        if(msg==="WRT_OK")
            alert("게시글이 성공적으로 등록되었습니다.");

        const wrtBtn = document.querySelector(".wrtBtn");
        wrtBtn.addEventListener("click",function(){
            location.href = "/lemon/board/write";
        })
    }
</script>
</body>
</html>