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
		    <div class="bread-crumbs"><a href="home.jsp">Home</a> / <a href="subject.jsp">Chemistry</a> / <a href="topic.jsp"> Chemical Bonding </a> / Discussion Board </div>
			<h1 class="tc">Discussion Board</h1>
			<div class="content-holder discussion-board-question">
			    <a href="discussion-question.jsp">What is the difference between an ionic and covalent bond?</a>
			    <div class="del-button-holder"><a href="#"><span class="glyphicon glyphicon-remove remove-button"></span></a></div>
			    <div class="author">By: Cyber Grape Learning</div>
			    <div class="timestamp">Posted at 2:06am on March 09, 2014</div>
			</div>
			<div class="content-holder discussion-board-question">
                <a href="discussion-question.jsp">How many valence electrons do alkali earth metals have?</a>
                <div class="author">By: Jamie Lapine</div>
                <div class="timestamp">Posted at 12:03am on March 09, 2014</div>
            </div>
            <textarea class="discussion-question"></textarea>
            <div class="tc">
                <a class="btn btn-cg">Post</a>
            </div>
		</div>
	</div>

    <%@include file="/includes/footer.html" %>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>