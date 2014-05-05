<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<div class="body-center-div">
				<div class="bread-crumbs">
					<a href="/home">Home</a> / <a
						href="/subject?sid=${subject.keyAsString}">${subject.title}</a> /
					<a href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a>
					/ Question
				</div>
				<div class="right-float">
					<div class="tl">Progress:</div>
					<div class="progress">
						<div id="progress-bar" class="progress-bar" style="width: 0%;">
							0%</div>
					</div>
				</div>
				<br />
				<h1 class="tc" id="question-title">${question.title}</h1>
				<div class="question-answers">
					<ol id="question-list">
						<c:forEach items="${question.answerChoices}" var="choice">
							<li><span>${choice}</span></li>
						</c:forEach>
					</ol>
				</div>
				<div class="tc">
					<a id="btn_testSubmitAnswer" class="btn btn-cg"
						qid="${question.keyAsString}">Submit</a>
					<div id="answerLoading" class="tc loadingDiv"
						style="display: none;">
						<img src="/images/ajax-loader.gif" alt="loading">
					</div>
					<h2 id="correctAnswer" class="green" style="display: none;">Correct!</h2>
					<h2 id="incorrectAnswer" class="dark-red" style="display: none;">Incorrect</h2>
					<h2 id="testPassed" class="green" style="display: none;">Test
						Passed!</h2>
					<h2 id="testFailed" class="dark-red" style="display: none;">Test
						Failed</h2>
				</div>
				<div id="feedback" class="feedback" loggedin="true" url="question"
					cid="${question.keyAsString}"
					ur="${metadata == null ? -1 : metadata.rating}"
					uf="${metadata == null ? false : metadata.flagged}"
					gr="${question.globalRating}"></div>
			</div>
		</div>

	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/FeedbackDisplay.js"></script>
<script src="/js/question.js"></script>
</html>