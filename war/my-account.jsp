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

    <%@include file="/includes/header-logged-in.html" %>

	<div class="body">
		<div class="body-center-div">
		    <div class="bread-crumbs"><a href="home.jsp">Home</a> / My Account </div>
			<h1 class="tc">My Account</h1>
			<br />
			<div class="account-info">
				<span class="title">Display Name:</span><input type="text" value="cse 308" disabled>
				<br />
				<span class="title">Email:</span><input type="email" value="cse.308.cyber.grapes@gmail.com" disabled>
				<br />
			    <span class="title">Password</span><input type="password" value="password" disabled>
		    </div>
		    <br />
		    <br />
            <div class="tc">
                <a href="#">Edit Info</a>
            </div>
		</div>
	</div>

    <%@include file="/includes/footer.html" %>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>