<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>등록</title>
</head>
<body>
    <div id= "main_content_wrap">
            <div class ="selection_cell">
                <c:out value = "${board.category}"/>
                <div style="display:flex">
                    공지 기간
                    <c:out value = "${board.startdate}"/></input>
                    ~
                     <c:out value = "${board.enddate}"/></input>
                </div>
            </div>
            <div class ="selection_cell" >
                제목  <c:out value = "${board.title}"/>
            </div>
            <div class ="selection_cell">
                주소
                 <c:out value = "${board.mainaddr}"/>
            </div>

            <div class ="selection_cell">
                상세 주소  <c:out value = "${board.detailaddr}"/>
            </div>
            <div class ="selection_cell">
                우편 번호   <c:out value = "${board.postalcode}"/>
            </div>

            <div class ="selection_cell">
                선택
                <c:if test="${!empty checklist}">
                    <c:forEach items="${checklist}" var="num" >
                        <c:out value = "${num}"/>
                    </c:forEach>
                </c:if>
            </div>
            <div class ="selection_cell">
                패스워드  <c:out value="${board.password}"/></input>
            </div>
            <div class ="selection_cell"> <!--선택필수-->
                주소 구분
                  <c:out value="${board.typeaddr}"/>

            </div>
            <div class ="selection_cell">
                내용
               <c:out value="${board.content}"/>
            </div>
            <div  id="pastedfile">
                첨부파일
                <c:if test="${!empty files}">
                    <c:forEach items="${files}" var="file">
                        <c:out value="${file}"/>
                    </c:forEach>
                </c:if>
            </div>

            <div class ="selection_cell">
                <input type="button" value="수정" onclick="window.location.href='/edit?boardSeq=${board.boardSeq}'"></input>
                <input type="button" value="목록" onclick="window.location.href='/list'"></input>
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
        //공유 날짜 지정
        today = new Date();
        today = today.toISOString().slice(0, 10);
        document.getElementById("startDate").value = today;

        week = new Date();
        week.setDate(week.getDate() + 7);
        week = week.toISOString().slice(0,10);
        document.getElementById("endDate").value = week;
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

        //if(filesArr.length != 0) {//첨부파일이 있음
        //    var form = document.querySelector("#saveForm");
        //    var formData = new FormData(form);
        //    for (var i = 0; i < filesArr.length; i++) {
                // 삭제되지 않은 파일만 폼데이터에 담기
        //        if (!filesArr[i].is_delete) {
        //            formData.append("files", filesArr[i]);
        //        }
        //   }
        //}

        //if(validationTitle == '') {
        //    alert("제목을 작성해 주세요");
        //    return;
        //}
        //else if(validationPw == false) { //비밀번호 조건 충족여부
        //    alert("비밀번호를 확인해주세요");
        //    return;
        //}
        //else if(validationMainAddr == '') {
        //    alert("주소를 작성해 주세요");
        //    return ;
        //}
        //else{
        alert("!");
            $('#saveForm').submit();

        //}

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
            htmlData += '<div id="file' + fileNo + '" class="filebox">';
            htmlData += '   <p class="name">' + file.name + '</p>';
            htmlData += '   <a class="delete" onclick="deleteFile(' + fileNo + ');"><i class="far fa-minus-square"></i></a>';
            htmlData += '</div>';

            $('.file-list').append(htmlData);
            fileNo++;
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

/* 첨부파일 삭제 */
//function deleteFile(num) {
//    document.querySelector("#file" + num).remove();
//    filesArr[num].is_delete = true;
//}

function addbox() {
    var str = "<input type='file' name='attached_file'><a href='#this' class ='delete' name='file-delete'><i class='far fa-minus-square'></i></a>";
    $(".file-list").append(str);
    $("a[name='file-delete']").on("click", function(e) {
        e.preventDefault();
        deleteFile($(this));
    });
}

function deleteFile(obj) {
    obj.parent().remove();
}
</script>
</html>