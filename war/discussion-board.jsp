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
		    <div class="bread-crumbs"><a href="/home">Home</a> / <a href="subject.jsp">Chemistry</a> / <a href="topic.jsp"> Chemical Bonding </a> / Discussion Board </div>
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
<%@include file="/includes/js.html" %>
</html>