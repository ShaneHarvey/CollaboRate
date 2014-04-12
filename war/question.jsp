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
			<div class="bread-crumbs">
				<a href="/home">Home</a> / <a
					href="/subject?sid=${subject.keyAsString}">${subject.title}</a> / <a
					href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a>
				/ Question
			</div>
			<h1 class="tc">${question.title}</h1>

			<div class="question-answers">
				<ol id="question-list">
					<c:forEach items="${question.answerChoices}" var="choice">
						<li><span>${choice}</span></li>
					</c:forEach>
					<!--<li>Gained</li>
					<li>Lost</li>
					<li><span class="selected-answer">Shared Equally</span></li>
					<li>Shared Unequally</li>-->
				</ol>
			</div>
			<div class="tc">
				<a id="btn_submitAnswer" class="btn btn-cg" qid="${question.keyAsString}">Submit</a>
			</div>
			<div class="feedback">
				<div>
					<span class="hoverHand">Rate this question: <span
						class="rating glyphicon glyphicon-star yellow" rating="1" qid="${question.keyAsString}"></span> <span
						class="rating glyphicon glyphicon-star yellow" rating="2" qid="${question.keyAsString}"></span> <span
						class="rating glyphicon glyphicon-star yellow" rating="3" qid="${question.keyAsString}"></span> <span
						class="rating glyphicon glyphicon-star" rating="4" qid="${question.keyAsString}"></span> <span
						class="rating glyphicon glyphicon-star" rating="5" qid="${question.keyAsString}"></span>
					</span>
				</div>
				<c:if test="${account != null}">
					<div>
						<span class="dark-red hoverHand" id="btn_flagQuestion" qid="${question.keyAsString}"><span
							class="glyphicon glyphicon-exclamation-sign"></span>Flag this
							question</span>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/question.js"></script>
</html>