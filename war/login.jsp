<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/custom-bootstrap.css">
<link rel="stylesheet" href="css/main.css">
<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
</head>
<body>

	<%@include file="/includes/header-logged-out.html"%>

	<div class="body">

		<!-- Container holding login div -->

		<div class="login-center-div">

			<h3>Email:</h3>
			<input id="loginEmail" type="email" name="email" class="login-input">

			<h3>Password:</h3>
			<input id="loginPassword" type="password" name="password" class="login-input"> <br />
			<div class="tc">
				<a id="loginButton" class="btn btn-cg">Submit</a>
			</div>
			<!--
            <br />
            NOT YET IMPLEMENTED
            <a href="#">Forgot Password?</a>-->

		</div>

	</div>

	<%@include file="/includes/footer.html"%>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="js/login.js"></script>
</html>