<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>등록</title>
</head>
<body>
    <div id="main_content_wrap">
        <div >
            <form id="searchForm" action="/search" method="post">
                검색
                <select id="searchcondition" name="searchcondition">
                    <option value="allsearch" >전체</option>
                    <option value="titlesearch">제목</option>
                    <option value="namesearch">이름</option>
                </select>
                등록 기간
                <input type="date" id="regstart" name="regstart" max="9999-12-31"/>
                ~
                <input type="date" id="regend" name="regend" max="9999-12-31"/>

                <input type="text" id="keyword" name="keyword" placeholder="검색어를 입력하세요"/>

                주소구분
                <input type="radio" name="typeAddr" id="newAddr" value="new" checked>도로명</input>
                <input type="radio" name="typeAddr" id="oldAddr" value="old">지번</input>

                <input type="button" class="search" id="search" value="검색" onclick="searchkeyword()"/>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th class="board_con_hd flex-auto">선택</th>
                    <th class="board_con_hd flex-auto">번호</th>
                    <th class="board_con_hd flex-auto">주소구분</th>
                    <th class="board_con_hd flex-auto">카테고리</th>
                    <th class="board_con_hd flex-auto">제목</th>
                    <th class="board_con_hd flex-auto">작성자</th>
                    <th class="board_con_hd flex-auto">등록일자</th>
                    <th class="board_con_hd flex-auto">파일여부</th>
                </tr>
            </thead>
            <tbody id="tablebody">
                <c:if test="${!empty list}">
                    <c:forEach items="${list}" var="item" varStatus="loop">
                        <tr class="board_list__box">
                            <td class="board_con flex-auto">
                                <input type="checkbox" id="boardSeq" name="boardSeq" value="<c:out value='${item.boardSeq}'/>"/>
                            </td>
                            <td class="board_con flex-auto"><c:out value="${loop.index + 1}"/></td>
                            <td class="board_con flex-auto">
                                <c:if test="${item.typeaddr eq 'new'}">도로명</c:if>
                                <c:if test="${item.typeaddr eq 'old' or item.typeaddr eq '' or item.typeaddr == null}">지번</c:if>
                            </td>
                            <td class="board_con flex-auto"><c:out value="${item.category}"/></td>
                            <td class="board_con flex-auto">
                                <a href="/findById?boardSeq=${item.boardSeq}">
                                    <c:out value="${item.title}"/>
                                </a>
                            </td>
                            <td class="board_con flex-auto"><c:out value="${item.writer}"/></td>
                            <td class="board_con flex-auto"><c:out value="${item.regTime}"/></td>
                            <td class="board_con flex-auto">
                                <c:if test="${item.attachedFileCount eq 0}">N</c:if>
                                <c:if test="${item.attachedFileCount > 0}">Y</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>


        <input type="button" value="삭제" onclick="deleteBoard()"></input>


    </div>
     <div>
        <form id="page" action "/list" method="post">

            <c:if test="${!empty totalPage}">
                <c:forEach begin="1" end="${totalPage}" var="page">
                    <input type="button" class="pageInfo" id="pageInfo" value=" <c:out value="${page}"/>" />
                </c:forEach>
            </c:if>
        </form>
    </div>

</body>
<link rel="stylesheet" href="../../css/addForm.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="../_resource_user/js/vendor/jquery-3.7.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
var selectedAddrType = '';
window.onload = function() {


        $(".pageInfo").click(function() {
           // 클릭된 페이지 값을 가져와서 hidden input에 설정
           var clickedPage = $(this).val();
           $("#page").append("<input type='hidden' name='currentPage' value='" + clickedPage + "' />");

           // form을 submit하여 해당 페이지로 이동
           $("#page").submit();
       });
    }

$(document).ready(function() {
   //공유 날짜 지정
        today = new Date();
        today = today.toISOString().slice(0, 10);
        document.getElementById("regend").value = today;

        week = new Date();
        week.setDate(week.getDate() - 7);
        week = week.toISOString().slice(0,10);
        document.getElementById("regstart").value = week;

       var currentPage = '${currentPageInfo}';
       $(".pageInfo").each(function() {
           // 현재 페이지와 요소의 값이 같으면 배경색을 보라색으로 변경
           if ($(this).val().trim() === currentPage.trim()) {
               $(this).css("background-color", "pink");
           }
       });
});

function searchkeyword() {
    $('#searchForm').submit();
    //$.ajax({
    //       type:"Post",
    //       url:"/search",
    //       dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    //       data: {
 	//	       "searchcondition": $('#searchcondition').val(),
    //           "regstart": $('#regstart').val(),
    //            "regend": $('#regend').val(),
    //            "keyword" : $('#keyword').val(),
    //            "typesearch" :$('input[name="typeAddr"]:checked').val()

    //       },
    //       success : function(data) {
    //         makeList(data);
    //       },
    //       complete : function(data) {
    //             // 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
    //       },
    //       error : function(xhr, status, error) {
    //            console.log(error)
    //       }
    // });
}

function makeList(data) {
    // tablebody 요소 가져오기
    var tablebody = document.getElementById("tablebody");

    // tablebody 내용 비우기
    tablebody.innerHTML = "";

     data.forEach(function(item, index) {
            var tr = document.createElement("tr");
            tr.innerHTML  = "<tr class='board_list__box'>" +
                                    "<td class='board_con flex-auto'>" +
                                        "<input type='checkbox' name='boardSeq' value='" + item.boardSeq + "'>" +
                                    "</td>" +
                                    "<td class='board_con flex-auto'>" + (index + 1) + "</td>" +
                                    "<td class='board_con flex-auto'>" + item.typeaddr + "</td>" +
                                    "<td class='board_con flex-auto'>" + item.category + "</td>" +
                                    "<td class='board_con flex-auto'>" +
                                        "<a href='/findById?boardSeq=" + item.boardSeq + "'>" + item.title + "</a>" +
                                    "</td>" +
                                    "<td class='board_con flex-auto'>" + item.writer + "</td>" +
                                    "<td class='board_con flex-auto'>" + item.regTime + "</td>" +
                                    "<td class='board_con flex-auto'>" + (item.attachedFileCount > 0 ? 'Y' : 'N') + "</td>" +
                                "</tr>";

            // 생성한 테이블 행(tr)을 tablebody에 추가
            tablebody.appendChild(tr);
        });
//
}

function deleteBoard(){
    var lists = [];
      $("input[name='boardSeq']:checked").each(function(i){   //jQuery로 for문 돌면서 check 된값 배열에 담는다
       lists.push($(this).val());
      });

    $.ajax({
           type:"Post",
           url:"/delete",
           dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
           data: {"boardSeq" :lists},
           success : function(data) {
                alert("삭제되었습니다.");
                location.href = 'redirect: /list';
           },
           complete : function(data) {
                 // 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
           },
           error : function(xhr, status, error) {
                console.log(error)
           }
     });
}
</script>
</html>