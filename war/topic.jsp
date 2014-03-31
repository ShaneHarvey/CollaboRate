<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<%@include file="/includes/css.html" %>
</head>
<body>

    <jsp:include page="/includes/header.jsp"></jsp:include>

	<div class="body">
		<div class="body-center-div">
			<div class="bread-crumbs"><a href="/home">Home</a> / <a href="subject.jsp">Chemistry</a> / Chemical Bonding</div>
			<h1 class="tc">Chemical Bonding</h1>
			<div class="row">
				<div class="col-lg-6 content-holder shift-left-5">
					<h4 class="tc">Highest Rated Questions</h4>
					<ol>
						<li><a href="question.jsp">The electrons in a nonpolar...</a></li>
						<li>What is the charge on the i...</li>
						<li><span class="glyphicon glyphicon-ok green"></span>What is the most correct na...</li>
						<li>What type of bonds are form...</li>
						<li><span class="glyphicon glyphicon-ok green"></span>The bond between sulfur and...</li>
					</ol>
					<div class="tc"><a>Load More...</a></div>
				</div>
				<div class="col-lg-6 content-holder shift-right-5">
					<h4 class="tc">Highest Rated Videos</h4>
					<ol>
						<li><a href="video.jsp">Chemical Bonds: Covalent vs...</a></li>
						<li>Atomic Hook-Ups - Types of ...</li>
						<li>Bonding Models and Lewis St...</li>
						<li>Chemistry 4.1 Chemical Bonding</li>
						<li>Chemical Bonding - Bonding</li>
					</ol>
					<div class="tc"><a>Load More...</a></div>
				</div>
				<div class="col-lg-6 content-holder shift-left-5">
					<h4 class="tc">Highest Rated Notes</h4>
					<ol>
						<li><a href="http://www.sparknotes.com/chemistry/bonding/intro/">Introduction to Chemical Bonding</a></li>
						<li>Properties of Chemical Bonds</li>
						<li>Ionic Bonds</li>
						<li>Covalent Bonds</li>
						<li>Molecular Orbitals</li>
					</ol>
					<div class="tc"><a>Load More...</a></div>
				</div>
				<div class="col-lg-6 shift-right-5">
				    <div class="tc">
				        <br />
                        <br />
                        <br />
                        <br />
				        <a class="mega-btn btn btn-cg" href="take-test.jsp">Take Test</a>
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