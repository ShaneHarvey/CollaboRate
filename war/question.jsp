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
			<div class="body-center-div question-body">
				<div class="bread-crumbs">
					<a href="/home">Home</a> / <a
						href="/subject?sid=${subject.keyAsString}">${subject.title}</a> /
					<a href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a>
					/ Question
				</div>
				
				<br />
				<div class="question-title">${question.title}</div>

				<div class="question-answers">
					<ol id="question-list">
						<c:forEach items="${question.answerChoices}" var="choice">
							<li>${choice}</li>
						</c:forEach>
					</ol>
				</div>
				<div class="tc">
					<a id="btn_submitAnswer" class="btn btn-cg"
						qid="${question.keyAsString}">Submit</a>
					<div id="answerLoading" class="tc loadingDiv"
						style="display: none;">
						<img src="/images/ajax-loader.gif" alt="loading">
					</div>
					<h2 id="correctAnswer" class="green" style="display: none;">Correct!</h2>
					<h2 id="incorrectAnswer" class="dark-red" style="display: none;">Incorrect</h2>
				</div>
				<h3 id="explanation" style="display:none;">Explanation:</h3>
				<div id="answerExplanation" style="display:none;"></div>
				<div id="feedback" class="feedback"
					loggedin="${account == null ? false : true}" url="question"
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