<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<c:set var="loginId" value="${empty sessionScope.id? '': sessionScope.id }"/>
<c:set var="loginOutLink" value="${empty loginId ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${empty loginId? 'Login' : 'Logout'}"/>
<%--<c:set var="bno" value="${param.bno}"/>--%>
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
<h1>게시물 읽기</h1>
<form id="myPost">
<%--    <c:set var="boardDto" value="${boardDto}"/>--%>
    <input name="bno" type="hidden" value="${boardDto.bno}"/>
    제목 : <input name="title" type="text" class="readonly" value="${boardDto.title}" ${empty boardDto? "" : 'readonly="readonly"'}/>
    작성자 : <input name="writer" type="text" value='${boardDto.writer}' readonly="readonly"/>
    내용 : <textarea name="content" class="readonly" id="" cols="30" rows="10" ${empty boardDto? "" : 'readonly="readonly"'}>${boardDto.content}</textarea>
</form>
<button type="button" class="wrtBtn">새글쓰기</button>
<button type="button" class="modBtn">수정</button>
<button type="button" class="delBtn">삭제</button>
<button type="button" class="listBtn">목록</button>
<script>
    window.onload = function(){

        const wrtBtn = document.querySelector(".wrtBtn");
        const modBtn = document.querySelector(".modBtn");
        const delBtn = document.querySelector(".delBtn");
        const listBtn = document.querySelector(".listBtn");

        if('${loginId}'!=='${boardDto.writer}'){

            modBtn.style.display='none';
            delBtn.style.display='none';
        }

        let msg = "${msg}"
        if (msg==="DEL_FAIL") alert("게시글 삭제에 실패했습니다.");
        if (msg==="MOD_FAIL") alert("게시글 수정에 실패했습니다.");
        if (msg==="WRT_FAIL") alert("게시글 등록에 실패했습니다.")
        //사실 삭제버튼은 해당 유저일때만
        // const listBtn = document.querySelector(".listBtn");
        listBtn.addEventListener("click",function(){
            <%--alert("${boardDto}");--%>
            <%--alert(${page});--%>
            location.href = "<c:url value='/board/boardList?page=${page}'/>";
        });
        // const delBtn = document.querySelector(".delBtn");
        delBtn.addEventListener("click",function(){
            if(!confirm("게시글을 정말로 삭제하시겠습니까?")) return;
            //bno정보를 들고 delete 메서드로 이동
            //bno는 hidden input에 넣어둘 수도 있고, param읽어서 pageContext에 변수로 저장해둔거 쓸 수도 있는듯
            //근데 Get? Post?
            <%--location.href="/lemon/board/delete?bno=${bno}";--%>
            //get으로만 보내보려했는데, 결국엔 전체 내용이 필요해서 form태그+bno hidden으로 넣어주기

            //삭제 버튼 클릭시
            //form의 action, method 설정 후 form 전송
            // let form = document.querySelector("#myPost");
            // form.action = '/lemon/board/delete';
            // form.method = 'post';
            // form.submit(); //전송은 되는데......400에러가 나네....

            //그냥 post말고 get으로 보내보기
            <%--let bno = ${boardDto.bno};--%>
            location.href = '<c:url value="/board/delete?bno=${boardDto.bno}&page=${page}"/>';
        });
        // const modBtn = document.querySelector(".modBtn");
        modBtn.addEventListener("click",function(){
            //1. 현재 readonly상태라면 readonly를 해제
            //대표로 하나의 input의 readonly만 살펴보아도 될듯
            const title = document.querySelector("input[name='title']");
            if(title.readOnly===true){
                document.querySelector("h1").innerHTML = "게시물 수정";
                this.innerHTML = "등록";
                const elems = document.querySelectorAll(".readonly");
                elems.forEach(function(elem) {
                    elem.readOnly = false;
                }); //title과 content만 readonly 속성을 변경. 작성자는 변경되지 않아야함.
                return; //여기까지만 하고 빠져나가야 전송이 되지 않음.
            }
            else{ //readonly가 false면
                let form = document.querySelector("#myPost");
                form.action = '<c:url value="/board/modify?page=${page}"/>';
                form.method = 'post';
                form.submit();
            }

        });

        // const wrtBtn = document.querySelector(".wrtBtn");
        wrtBtn.addEventListener("click", function(){
            //제목 수정 - 게시글 쓰기 -
            document.querySelector("h1").innerHTML = "게시물 쓰기";
            //제목과 내용을 비운다 (작성 form을 보여주기)
            const title = document.querySelector("input[name='title']");
            if(title.readOnly==true){
                wrtBtn.innerHTML = '등록';
                const inputs = document.querySelectorAll(".readonly");
                inputs.forEach(function(input){
                    input.readOnly = false;
                    input.value="";
                });
                //작성자를 session에 저장된 id로 뿌려준다.
                const id = "${loginId}";
                const writer = document.querySelector("input[name='writer']");
                writer.value = id;

                return;
            }

            // readonly해제 된 상태(작성중)이면 버튼 누르면 폼 전송하기.
            //form 전송한다.
            let form = document.querySelector("#myPost");
            form.action = '<c:url value="/board/write"/>';
            form.method = 'post';
            form.submit();
        });

    }

</script>
</body>
</html>
