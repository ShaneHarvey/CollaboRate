<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
<link rel="stylesheet" href="/css/custom-jquery-ui.css">
</head>
<body>

	<jsp:include page="/includes/header.jsp"></jsp:include>

	<div class="body">

		<!-- Container holding login div -->

		<div id="loginCenterDiv" class="login-center-div">

			<h3>Email:</h3>
			<input id="loginEmail" type="email" name="email" class="login-input">

			<h3>Password:</h3>
			<input id="loginPassword" type="password" name="password" class="login-input"> <br />
			<div class="tc">
				<a id="loginButton" class="btn btn-cg">Submit</a>
			</div>
			<div id="loginLoading" class="tc loadingDiv" style="display:none;"><img src="/images/ajax-loader.gif" alt="loading"></div>
			<!--
            <br />
            NOT YET IMPLEMENTED
            <a href="#">Forgot Password?</a>-->

		</div>

	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/jquery-ui-1.10.4.min.js"></script>
<script src="/js/login.js"></script>
</html>