<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
</head>
<body>

    <jsp:include page="/includes/header.jsp"></jsp:include>

	<div class="body">
		<div class="body-center-div">
			<div class="bread-crumbs"><a href="/home">Home</a> / <a href="/subject?sid=${subject.keyString}">${subject.title}</a> / <a href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a> / Question </div>
			<h1 class="tc">${question.title}</h1>

			<div class="question-answers">
				<ol>
				    <c:forEach items="${question.answerChoices}" var="a">
				        <li>${a}</li>
				    </c:forEach>
					<!--<li>Gained</li>
					<li>Lost</li>
					<li><span class="selected-answer">Shared Equally</span></li>
					<li>Shared Unequally</li>-->
				</ol>
			</div>
			<div class="tc">
			   <a class="btn btn-cg">Submit</a>
			</div>
			<div class="feedback">
                <div>
                    <span>Rate this question: <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> </span>
                </div>
                <div>
                    <span class="dark-red"><span class="glyphicon glyphicon-exclamation-sign"></span>Flag this question</span>
                </div>
            </div>
		</div>
	</div>

    <jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
</html>