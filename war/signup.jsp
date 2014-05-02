<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>CollaboRate</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
</head>
<body>

	<div class="wrapper">
		<jsp:include page="/includes/header.jsp"></jsp:include>

		<div class="body">

			<!-- Container holding login div -->

			<div class="login-center-div cut-vertical-padding">

				<h3>Email:</h3>
				<input id="signupEmail" type="text" class="login-input">

				<h3>Password:</h3>
				<input id="signupPassword" type="password" class="login-input">

				<h3>Confirm Password:</h3>
				<input id="signupConfirmPassword" type="password"
					class="login-input">

				<div id="termsHolder">
					<input id="agreeTermsCheckbox" type="checkbox"><label
						class="checkbox-label" for="agreeTermsCheckbox">I agree to
						the <a href="terms.jsp">Terms and Conditions</a> and <a
						href="privacy.jsp">Privacy Policy</a>.
					</label>
				</div>
				<br />
				<div class="tc">
					<a id="signupButton" class="btn btn-cg tc">Submit</a>
				</div>
				<div id="signupLoading" class="tc loadingDiv" style="display: none;">
					<img src="/images/ajax-loader.gif" alt="loading"><br />
					<br />
				</div>
			</div>

		</div>

	</div>
	
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/account.js"></script>
</html>