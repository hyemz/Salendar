<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function login() {
	if(document.getElementById("id").value == "") {
		alert("아이디 입력!!!");
	} else if(document.getElementById("password").value == "") {
		alert("비밀번호 입력!!!");
	} else {
		document.getElementById("loginform").action = "${root}/user/login";
		document.getElementById("loginform").submit();
	}
}

/* function registAccount() {
	if(document.getElementById("addId").value == "") {
		alert("아이디 입력!!!");
	} else if(document.getElementById("addPassword").value == "") {
		alert("비밀번호 입력!!!");
	} else if(document.getElementById("addName").value == "") {
		alert("이름 입력!!!");
	}else if(document.getElementById("addAddress").value == "") {
		alert("주소 입력!!!");
	}else if(document.getElementById("addCellphone").value == "") {
		alert("전화번호 입력!!!");
	}else {
		document.getElementById("info").action = "${root}/member/regist";
		document.getElementById("info").submit();
	}
} */
</script>
</head>
<body>
	<!-- 로그인 modal -->
	<div id="LoginModal" class="modal fade" style="height: 50% width:50%">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">로그인</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<form id="loginform" method="post">
						<div class="form-group">
							<label for="usr_email">EMAIL</label> <input type="text" class="form-control" id="usr_email" name="usr_email" maxlength="30" placeholder="email">
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input type="password" class="form-control" id="usr_password" name="usr_password" placeholder="Password">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="login()">Login</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 회원가입 modal -->
<!-- 	<div id="SignUpModal" class="modal fade">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">회원가입</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<form id="info" name="info" method="post">
						<div class="form-group">
							<label for="id">ID</label> <input type="text" class="form-control" id="addId" name="id" maxlength="20" placeholder="ID">
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input type="password" class="form-control" id="addPassword" name="Password" placeholder="Password">
						</div>
						<div class="form-group">
							<label for="id">이름</label> <input type="text" class="form-control" id="addName" name="name" maxlength="20" placeholder="Name">
						</div>
						<div class="form-group">
							<label for="id">주소</label> <input type="text" class="form-control" id="addAddress" name="address" placeholder="Address">
						</div>
						<div class="form-group">
							<label for="id">전화번호</label> <input type="tel" class="form-control" id="addCellphone" name="cellPhone" pattern="(010)\d{8}" maxlength="20" placeholder="01000000000">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="registAccount()">등록</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
						</div>
					</form>
				</div>
				
			</div>
		</div>
	</div> -->
</body>
</html>