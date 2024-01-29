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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <style>
         .container {
            width: 800px;
            height: 300px;
            margin: 0 auto;
            box-sizing: border-box;
            position: relative;
            display: flex;
            flex-direction: column;
        }
        .container input {
            border: none;
            outline: none;
            font-size : 16px;
            box-sizing: border-box;
        }

        input[name="title"]{
            display: block;
            position: absolute;
            /*left: 50%;*/
            /*margin: 0 auto;*/
            left: calc(50% - 600px/2);
            text-align: center;
            background-color: #74b6a5;
            width: 600px;
        }
        div.writer {
            box-sizing: border-box;
            position: absolute;
            top: 50px;
            right: 0;
        }
         div.writer input {
             width: 100px;
         }
        textarea {
            box-sizing: border-box;
            width: 100%;
            display: inline;
            outline: none;
            /*width: 800px;*/
            /*height: 600px;*/
            /*border-radius: 10px;*/
            /*border: 1px solid black;*/
            border : none;
            background-color: #74b6a5;
            position: absolute;
            top: 100px;

        }
        .buttons {
            position : absolute;
            bottom: 0;
            right: 0;
        }
        .comments {
            width: 800px;
            margin: 30px auto 0;
            top: 300px;
            background-color: royalblue;
        }
        .comments ul {
            display: block;
            background-color: transparent;
        }
        .comments ul li {
            width: 100%;
        }
        .comments input {
            width: 90%;
        }
        .comment input, .comment button {
            margin: 0 auto;
        }

        .comments .inputs {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
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
<h1>게시물 읽기</h1>
<div class="container">
    <form id="myPost">
        <%--    <c:set var="boardDto" value="${boardDto}"/>--%>
        <input name="bno" type="hidden" value="${boardDto.bno}"/>
        <input name="title" type="text" class="readonly" value="${boardDto.title}" ${empty boardDto? "" : 'readonly="readonly"'}/>
        <div class="writer"><i class="fas fa-solid fa-user"></i>&nbsp<input name="writer" type="text" value='${boardDto.writer}' readonly="readonly"/></div>
        <textarea name="content" class="readonly" id="" cols="30" rows="10" ${empty boardDto? "" : 'readonly="readonly"'}>${boardDto.content}</textarea>
    </form>
    <div class="buttons">
        <button type="button" class="wrtBtn">새글쓰기</button>
        <button type="button" class="modBtn">수정</button>
        <button type="button" class="delBtn">삭제</button>
        <button type="button" class="listBtn">목록</button>
    </div>
</div>
<div class="comments">
    <div class="comments inputs">
        <input name="comment" type="text" placeholder="댓글을 입력해주세요.">
        <button type="button" id="wrtCmtBtn">댓글 등록</button>
    </div>
    <div id="commentList">
    </div>
</div>



<script>
    let bno = ${boardDto.bno};
    let showList = function(bno){
        $.ajax({
            type: 'GET',
            url: '/lemon/comments/read?bno='+bno,
            success: function(result){
                console.dir(result);
                $('#commentList').html(toHTML(result));
            },
            error: function(){alert("error")}
        });
    }

    let toHTML = function(comments){
        let str = "<ul>";
        comments.forEach(function(comment){
            let up_date = new Date(comment.up_date);
            str += '<li data-cno=' + comment.cno;
            str += ' data-pcno=' + comment.pcno;
            str += ' data-bno=' + comment.bno + '>';
            // if(comment.cno!=comment.pcno)
            //     str += 'ㄴ';
            str += ' <i class="fas fa-solid fa-user"></i>&nbsp<span class="commenter">' + comment.commenter + '</span>';
            str += ' <span class="comment">&nbsp' +comment.comment + '</span>&nbsp';
            str += ' up_date=' + getFormatDate(new Date(comment.up_date));
            str += '&nbsp<button class="delBtn">삭제</button>';
            str += '&nbsp<button class="modBtn">수정</button>';
            str += '&nbsp<button class="replyBtn">댓글</button>';
            str += '</li>';
        })
        return str+"</ul>";
    }

    const getFormatDate = function(date){
        let year = date.getFullYear();
        let month = (1+date.getMonth());
        month = month >= 10? month : '0'+month;
        let day = date.getDate();
        day = day >= 10? day : '0'+day;
        let hour = date.getHours();
        hour = hour>=10? hour: '0'+hour;
        let minute = date.getMinutes();
        minute = minute>=10? minute : '0'+minute;
        return year+'/'+month+'/'+day+' '+hour+":"+minute;
    }
    window.onload = function(){
        //댓글 가져오기
        showList(bno);


        const wrtBtn = document.querySelector(".wrtBtn");
        const modBtn = document.querySelector(".modBtn");
        const delBtn = document.querySelector(".delBtn");
        const listBtn = document.querySelector(".listBtn");
        // const wrtCmtBtn = document.querySelector("#wrtCmtBtn");

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
            location.href = "<c:url value='/board/boardList${sc.queryParam}'/>";
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
            location.href = '<c:url value="/board/delete${sc.queryParam}&bno=${boardDto.bno}"/>';
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
                form.action = '<c:url value="/board/modify${sc.queryParam}"/>';
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

        $("#wrtCmtBtn").click(function(){
            let comment = $("input[name=comment]").val();
            // alert(comment);

            if(comment.trim()===""){
                alert("댓글을 입력해주세요.");
                $("input[name=comment]").focus();
                return;
            }
            <%--alert(comment);--%>
            <%--alert(${boardDto.bno});--%>

            $.ajax({
                type: 'POST',
                <%--url: '<c:url value="/comments/write${sc.queryParam}&bno=${boardDto.bno}"/>',--%>
                url: '/lemon/comments/write',
                headers: {"content-type": "application/json"},
                data: JSON.stringify({bno:${boardDto.bno}, comment:comment}),
                success: function(result){
                    if(result){
                        alert("댓글이 성공적으로 등록되었습니다.");
                        showList(bno);
                    }
                    else {
                        alert("no result");
                    }
                },
                error: function(result){ alert(result)}
            });


        });


    }

</script>
</body>
</html>
