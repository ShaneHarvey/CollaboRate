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

    <%@include file="/includes/header-logged-in.html"%>

	<div class="body">
		<div class="body-center-div">
			<div class="bread-crumbs"><a href="home.jsp">Home</a> / <a href="subject.jsp">Chemistry</a> / <a href="topic.jsp"> Chemical Bonding </a> / Video #984649 </div>
			<h1 class="tc">Chemical Bonds: Covalent vs. Ionic</h1>
			<div class="video-embed">
				<iframe width="640" height="480" src="https://www.youtube-nocookie.com/embed/7DjsD7Hcd9U?wmode=transparent" frameborder="0" allowfullscreen></iframe>
				<div class="feedback">
				    <div>
				        <span>Rate this video: <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> </span>
			        </div>
			        <div>
			            <span class="dark-red"><span class="glyphicon glyphicon-exclamation-sign"></span>Flag this video</span>
			        </div>
			    </div>
			</div>
		</div>
	</div>

    <%@include file="/includes/footer.html"%>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>