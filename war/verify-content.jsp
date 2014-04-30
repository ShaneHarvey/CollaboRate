<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>CollaboRate</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
</head>
<body>

	<jsp:include page="/includes/header.jsp"></jsp:include>

	<div class="body">
		<div class="body-center-div">
			<div class="bread-crumbs">
				<a href="/home">Home</a> / <a
					href="/subject?sid=${subject.keyAsString}">${subject.title}</a> /
				Verify Content
			</div>
			<h1 class="tc">Verify Content Panel</h1>
			<div class="content-holder">
				<h2 class="tc">Unverified Questions</h2>
				<c:forEach items="${subject.unverifiedQuestions}" var="q">
					<div class="content-holder side-margins-10">
						<h3 class="tc">${q.title}</h3>
						<div class="question-answers">
							<ol>
								<c:forEach items="${q.answerChoices}" var="c">
									<li><span>${c}</span></li>
								</c:forEach>
							</ol>
						</div>
						<div class="tc">
							<a class="btn btn-cg verify" cid="${q.keyAsString}">Verify</a> <a
								class="btn btn-cg rm" cid="${q.keyAsString}">Remove</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/verify.js"></script>
</html>