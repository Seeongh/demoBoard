<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>등록</title>
</head>
<body>
    <div id= "main_content_wrap">
        <form action="/changeForm" id="changeForm"  method="post" enctype="multipart/form-data">
            <div class ="selection_cell">
                <select id="category" name="category">
                    <option value = "question">단순 질문</option>
                    <option value = "complain">민원</option>
                    <option value = "etc">기타</option>
                </select>
                <div style="display:flex">
                    공지 기간
                    <input type="date" id= "startDate" name = "startDate" max="9999-12-31" disabled></input>
                    ~
                    <input type="date" id= "endDate" name = "endDate" max="9999-12-31" disabled></input>
                </div>
            </div>
            <div class ="selection_cell">
                제목 <input id="title" type="text" name="title"></input>
            </div>
            <div class ="selection_cell">
                주소
                <input type="text"  id="mainAddr" name = "mainAddr"/>  <input type="button" id="" onclick="execDaumPostcode()" value="주소 찾기"></input>
            </div>

            <div class ="selection_cell">
                상세 주소 <input type="text" id = "detailAddr" name="detailAddr"/>
            </div>
            <div class ="selection_cell">
                우편 번호  <input type="text" id = "postalcode" name="postalcode" placeholder="우편번호"/>
            </div>

            <div class ="selection_cell">
                선택

                <input type="checkbox" name="checklist" id="checklist" value="1">1</input>
                <input type="checkbox" name="checklist" id="checklist" value="2">2</input>
                <input type="checkbox" name="checklist" id="checklist" value="3">3</input>
                <input type="checkbox" name="checklist" id="checklist" value="4">4</input>
                <input type="checkbox" name="checklist" id="checklist" value="5">5</input>
                <input type="checkbox" name="checklist" id="checklist" value="6">6</input>
            </div>
            <div class ="selection_cell">
                패스워드 <input id="password"  minlength="8" type="password" name="password" disabled></input>
            </div>
            <div class ="selection_cell">

            </div>
            <div class ="selection_cell"> <!--선택필수-->
                주소 구분
                <input type="radio" name="typeAddr" id="newAddr" value="new">도로명</input>
                <input type="radio" name="typeAddr" id="oldAddr" value="old">지번</input>
            </div>
            <div class ="selection_cell">
                내용
                <input  id ="content" type="textarea"  name="content"></input>
            </div>
            <div  id="pastedfile">
                첨부파일
                <c:if test="${!empty files}">
                    <c:forEach items="${files}" var="file" >
                         <div id="fileorigin">
                                <c:out value="${file}"/>
                                <a class="change" onclick="deleteorigin(this,'${file}')">
                                    <i class="far fa-minus-square"></i>
                                </a>
                         </div>
                    </c:forEach>
                </c:if>
                <a href="#this" onclick="addbox()">파일추가</a>
                <div class="file-list"></div>
            </div>
            <input type='hidden' name='boardSeq' value = '${board.boardSeq}'/>

            <div class ="selection_cell">
                <input type="button" value="취소" onclick="window.location.href='/findById?boardSeq=${board.boardSeq}'"></input>
                <input type="button" id="change" value="저장" onclick="validation()"></input>
            </div>
        </form>
    </div>
</body>
<link rel="stylesheet" href="../../css/addForm.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
var selectedAddrType = '';
var validationPw = false;
var fileNo = 0;
var filesArr = new Array();

window.onload = function() {
       //사용자가 선택한 category
       $('#category option[value="${board.category}"]').prop('selected', true);
       $('#startDate').val( "${board.startdate}");
       $('#endDate').val( "${board.enddate}");
       $('#title').val("${board.title}");
       $('#mainAddr').val("${board.mainaddr}");
       $('#detailAddr').val("${board.detailaddr}");
       $('#postalcode').val("${board.postalcode}");
       $('#password').val("${board.password}");
       $('#content').val("${board.content}");

       //radio
       if("${board.typeaddr}" == 'new') {
          $('#newAddr').prop("checked",true);
       }
       else if("${board.typeaddr}" == 'old') {
          $('#oldAddr').prop("checked",true);
       }

       //checklist
       <c:if test="${!empty checklist}">
           <c:forEach items="${checklist}" var="num" >
                $("input[name='checklist'][value='${num}']").prop("checked", true);
           </c:forEach>
       </c:if>


}

//주소 처리
function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                selectedAddrType = data.userSelectedType;
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }


                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postalcode').value = data.zonecode;
                document.getElementById("mainAddr").value = addr;

                // typeAddr 라는 이름을 가진 라디오 버튼 그룹 내의 모든 라디오 버튼을 체크 해제합니다.
                var radioButtons = document.getElementsByName('typeAddr');
                for (var i = 0; i < radioButtons.length; i++) {
                    radioButtons[i].checked = false;
                }

                if (data.userSelectedType === 'R') { //도로명 주소
                    // id가 'new'인 라디오 버튼을 선택합니다.
                    document.getElementById('newAddr').checked = true;
                }
                else{
                    // id가 'new'인 라디오 버튼을 선택합니다.
                    document.getElementById('oldAddr').checked = true;
                }

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAddr").focus();
            }
        }).open();
    }

    $('#password').keyup(function(event) {
        var password = $(this).val();
        var passwordLength = password.length;

        if(passwordLength < 8) {
            $('#pwInfo').text("8글자 이상 써주세요").css("color","red");
        }
        else{ //8자 이상일경우
            var containsSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
            var containsLetter = /[a-zA-Z]/.test(password);
            var containsNumber = /\d/.test(password);
            var containsSequentialNumbers = /123|234|345|456|567|678|789|890/.test(password);
            validationPw = false;

            if(!containsSpecialChar) { //특수문자 포함되어있지않음
                $('#pwInfo').text("특수문자를 하나이상 포함해야합니다.").css("color","red");
            }
            else if(!containsSpecialChar) { //영어 포함되어있지않음
               $('#pwInfo').text("영어를 하나이상 포함해야합니다.").css("color","red");
            }
            else if(! containsNumber) {//숫자가 포함되어있지 않음
               $('#pwInfo').text("숫자를 하나이상 포함해야합니다.").css("color","red");
            }
            else if(containsSequentialNumbers) {//연속된 숫자 포함시
               $('#pwInfo').text("연속된 세자리 숫자는 불가합니다.").css("color","red");
            }
            else {
               $('#pwInfo').text("사용 가능한 비밀번호 입니다.").css("color","green");
               validationPw = true;
            }
        }
    });

    //라디오 버튼 선택시
    $('#oldAddr').click(function() { //지번 선택시
        if (selectedAddrType === 'R') { //주소는 도로명 주소인경우
            alert("주소가 도로명 주소입니다.");
            document.getElementById('oldAddr').checked = false;
            document.getElementById('newAddr').checked = true;
        }
    });

    //라디오 버튼 선택시
    $('#newAddr').click(function() { //도로명 선택시
        if (selectedAddrType === 'J') { //주소가 지번 주소인경우
            alert("주소가 지번 주소입니다.");
            document.getElementById('oldAddr').checked = true;
            document.getElementById('newAddr').checked = false;
        }
    });

    function validation() {
        var validationTitle = $('#title').val();
        var validationMainAddr = $('#mainAddr').val();

            $('#changeForm').submit();

    }

/* 첨부파일 추가 */
function addFile(obj){
    alert("?");
    for (const file of obj.files) {
        // 첨부파일 검증
        if (validationFile(file)) {
            // 파일 배열에 담기
            var reader = new FileReader();
            reader.onload = function () {
                filesArr.push(file);
            };
            reader.readAsDataURL(file);

            // 목록 추가
            let htmlData = '';
            htmlData += '   <a class="delete" onclick="deleteFile(this);"> <p class="name">' + file.name + '</p><i class="far fa-minus-square"></i></a>';
            $('.file-list').append(htmlData);
            //fileNo++;
        } else {
            continue;
        }
    }
    // 초기화
    document.querySelector("input[type=file]").value = "";
}

function validationFile(obj) {
     const fileTypes = ['application/pdf', 'image/jpeg', 'image/png', 'application/haansofthwp', 'application/x-hwp'];
         if (obj.size > (5 * 1024 * 1024)) {
            alert("최대 파일 용량인 5MB를 초과한 파일은 업로드 불가합니다.");
            return false;
        } else if (obj.name.lastIndexOf('.') == -1) {
            alert("확장자가 없는 파일은 업로드 불가합니다.");
            return false;
        } else if (!fileTypes.includes(obj.type)) {
            alert("첨부가 불가능한 파일은 업로드 불가합니다.");
            return false;
        } else {
            return true;
        }
}

function addbox() {
      alert("?");
      let str = '';
            str += '<div id="file' + fileNo + '" class="filebox">';
            str += '   <input type="file" id = "attachedfile'+fileNo+'" name="attached_file">';
            str += '   <a class="delete" onclick="deleteFile('+fileNo+');"><i class="far fa-minus-square"></i></a>';
            str += '</div>';
    $(".file-list").append(str);
//    $("a[name='file-delete']").on("click", function(e) {
//        e.preventDefault();
//        deleteFile($(this));
//    });
    fileNo++;
}

function deleteFile(fileNum) {
    var fileDiv = document.getElementById("file" + fileNum);
        if (fileDiv) {
            fileDiv.remove();
        }
    document.querySelector("#attachedfile" + fileNum).remove();
}

function deleteorigin(obj,filename) {
    alert(filename);
    var str = '   <input type="hidden"  name="deleted_file" value="'+filename+'"/>';

    $(".file-list").append(str);
    obj.parentNode.remove();
}
</script>
</html>