<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <h2>commentTest</h2>
    comment : <input type="text" name="comment"><br>
    <button id="sendBtn" type="button">SEND</button>
    <button id="modBtn" type="button">수정</button>
    <div id="commentList"></div>
    <div id="replyForm" style="display: none">
        <input type="text" name="replyComment">
        <button id="wrtRepBtn" type="button">등록</button>
    </div>
    <script>
        //test.jsp는 SimpleRestController.java에 연결.

        let bno = 1;
        let showList = function(bno){
            $.ajax({
                type: 'GET',
                url: '/ch4/comments?bno='+bno,
                // headers: {"content-type": "application/json"}, GET이라 보낼 데이터가 없어서
                // dataType: 'text', 생략시 default는 JSON
                success: function(result){
                    console.dir(result); //result가 array라서 그냥 .html(result)하면 출력이 안됨.
                    $('#commentList').html(toHTML(result));
                },
                error: function(){ alert("error")}
            });
        }

        let toHTML = function(comments){
            let tmp = "<ul>";
            comments.forEach(function(comment){
                tmp += '<li data-cno=' + comment.cno;
                tmp += ' data-pcno=' + comment.pcno;
                tmp += ' data-bno=' + comment.bno + '>'
                if(comment.cno!=comment.pcno)
                    tmp += 'ㄴ'
                tmp += ' commenter=<span class="commenter">' + comment.commenter + '</span>';
                tmp += ' comment=<span class="comment">' + comment.comment + '</span>';
                tmp += ' up_date='+comment.up_date;
                tmp += '<button class="delBtn">삭제</button>'
                tmp += '<button class="modBtn">수정</button>'
                tmp += '<button class="replyBtn">댓글</button>'
                tmp += '</li>'
            })
            return tmp + '</ul>';
        }

        $(document).ready(function(){
            showList(bno);
            $("#sendBtn").click(function(){ //댓글 등록 버튼 누르면-
                let comment = $("input[name=comment]").val();

                if(comment.trim()===''){
                    alert('댓글을 입력해주세요');
                    $("input[name=comment]").focus(); //왜 위에서 정의한 변수 comment.focus()하면 안될까.
                    return;
                }

                $.ajax({
                    type: 'POST',
                    url: '/ch4/comments?bno='+bno,
                    headers: {"content-type": "application/json"},
                    data: JSON.stringify({"bno": bno, "comment": comment}),
                    //dataType: 'text', 전송받을 데이터. 생략시 default는 JSON
                    success: function(result){
                        alert('댓글이 성공적으로 등록되었습니다.');
                        showList(bno); //댓글 등록 성공시 목록 보여주기
                    },
                    error: function(){ alert("error")}
                });

            });
            $("#modBtn").click(function(){ //댓글 등록 버튼 누르면-
                let cno = $(this).attr("data-cno");
                let comment = $("input[name=comment]").val();

                if(comment.trim()===''){
                    alert('댓글을 입력해주세요');
                    $("input[name=comment]").focus(); //왜 위에서 정의한 변수 comment.focus()하면 안될까.
                    return;
                }

                $.ajax({
                    type: 'PATCH',
                    url: '/ch4/comments/'+cno,
                    headers: {"content-type": "application/json"},
                    data: JSON.stringify({cno: cno, comment: comment}),
                    //dataType: 'text', 전송받을 데이터. 생략시 default는 JSON
                    success: function(result){
                        alert('댓글이 성공적으로 수정되었습니다.');
                        showList(bno); //댓글 등록 성공시 목록 보여주기
                    },
                    error: function(){ alert("error")}
                });

            });
            // $(".delBtn").click(function(){ //페이지 로딩된 시점에 delBtn이 없어서 이벤트 안걸림.
            $("#commentList").on("click",".delBtn",function(){ //고정된 요소에 걸어줘야한다.
                //동적으로 생성되는 요소에 이벤트 걸기.
                if(!confirm("댓글을 정말로 삭제하시겠습니까?")) return;
                let cno = $(this).parent().attr("data-cno");
                let bno = $(this).parent().attr("data-bno");
                $.ajax({
                    type: 'DELETE',
                    url: '/ch4/comments/'+cno+'?bno='+bno,
                    // headers: {"content-type": "application/json"}, GET이라 보낼 데이터가 없어서
                    // dataType: 'text', 생략시 default는 JSON
                    success: function(result){
                        alert('댓글이 성공적으로 삭제되었습니다.');
                        showList(bno); //갱신된 목록 가져오기
                    },
                    error: function(){ alert("댓글 삭제에 실패했습니다.")}
                });
            });
            $("#commentList").on("click",".modBtn",function(){
                let cno = $(this).parent().attr("data-cno");
                // let bno = $(this).parent().attr("data-bno");
                //수정은 cno만 있으면 된다.

                //1.comment의 내용을 input에 뿌려주기
                let comment = $("span.comment", $(this).parent()).text();
                $("input[type=text]").val(comment);
                //2.cno전달하기
                $("#modBtn").attr("data-cno",cno);
                //.modBtn과 #modBtn 혼동 주의
            });
            $("#commentList").on("click",".replyBtn",function(){
               //1. pcno의 commenter를 @태그하는 문자열 뿌려주기
                let reply_commenter = '@'+$("span.commenter",$(this).parent()).text()+' ';
                $("input[name=replyComment]").val(reply_commenter);
               //2. 답글을 입력할 form의 위치를 바로 아래로 옮겨주고, display: block으로 바꾸기
                $("#replyForm").appendTo($(this).parent()); //<li>의 자식 위치의 맨 뒤에 붙이기
                $("#replyForm").css("display","block");

            });

            $("#wrtRepBtn").click(function(){
                //1. pcno와 reply내용을 전송한다.
                let comment = $("input[name=replyComment]").val();
                let pcno = $("#replyForm").parent().attr("data-pcno"); //대댓글도 대대댓글도 모두 한 부모 밑에 묶이게.

                if(comment.trim()===''){
                    alert('댓글을 입력해주세요');
                    $("input[name=replyComment]").focus(); //왜 위에서 정의한 변수 comment.focus()하면 안될까.
                    return;
                }

                $.ajax({
                    type: 'POST',
                    url: '/ch4/comments?bno='+bno,
                    headers: {"content-type": "application/json"},
                    data: JSON.stringify({pcno: pcno, bno: bno, comment: comment}),
                    //dataType: 'text', 전송받을 데이터. 생략시 default는 JSON
                    success: function(result){
                        alert(result);
                        showList(bno); //댓글 등록 성공시 목록 보여주기
                    },
                    error: function(){ alert("error")}
                });
                //2. replyForm을 다시 감춘다.
                $("#replyForm").css("display","none");
                $("input[name=replyComment]").val("");
                $("#replyForm").appendTo("body");
            });
            //대댓글의 위치를 댓글 아래로 위치시켜주려면 sql의 order by를 손봐야함
        });
    </script>
</body>
</html>
