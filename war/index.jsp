<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="bc-white">
<head>
<meta charset="utf-8">
<title>CollaboRate</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
</head>
<body class="bc-white">

	<div class="wrapper">
		<jsp:include page="/includes/header.jsp"></jsp:include>
		<!-- Carousel Wrapper -->
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Carousel -->
			<div class="carousel-inner">
				<!-- Question item -->
				<div class="item active">
					<div class="container">
						<div class="carousel-caption home-questions-display">
							<h1>Answer and Contribute Questions!</h1>
							<br />
							<div class="home-question">
								<div class="question-title">${question.title}</div>

								<div class="question-answers">
									<ol id="question-list">
										<c:forEach items="${question.answerChoices}" var="choice">
											<li class="tl">${choice}</li>
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
									<h2 id="incorrectAnswer" class="dark-red"
										style="display: none;">Incorrect</h2>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Video item -->
				<div class="item">
					<div class="container">
						<div class="carousel-caption video-display">
							<h1>Watch Lecture Videos!</h1>
							<div class="video-embed">
								<iframe width="534" height="400" src="${video.URL}"
									frameborder="0" allowfullscreen></iframe>
							</div>
						</div>
					</div>
				</div>
				<!-- Notes item -->
				<div class="item">
					<div class="container">
						<div class="carousel-caption notes-display">
							<h1>Get Links to the Best Study Material!</h1>
							<a href="${notes.URL}" target="_blank">${notes.title}</a>
						</div>
					</div>
				</div>
			</div>
			<!-- Bottom Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
		</div>
	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/video.js"></script>
<script src="/js/question.js"></script>
<script src="/js/plugins/FeedbackDisplay.js"></script>
</html>