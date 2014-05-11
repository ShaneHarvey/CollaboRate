<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/function.tld" prefix="fn"%>
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
					<a href="/home">Home</a> / ${subject.title}
				</div>
				<div class="right-float">
					<c:if test="${fn:userTrusted(subject, account)}">
						<div class="tl">
							<a href="/verify?sid=${subject.keyAsString}">Help Verify
								Questions!</a>
						</div>
					</c:if>
					<c:if test="${account != null}">
						<div class="tl">
							<a
								href="/RequestSubtopic?subjectname=${subject.title}&sid=${subject.keyAsString}">Request
								a subtopic!</a>
						</div>
					</c:if>
				</div>
				<h1 class="tc">${subject.title}</h1>
				<div class="row">
					<div class="col-lg-4 content-holder shift-left-5">
						<h3 class="tc">Topics</h3>
						<c:choose>
							<c:when test="${!accountIsAdmin}">
								<table class="content-table">
									<c:forEach items="${subject.subtopics}" var="st">
										<c:set var="testdata"
											value="${account == null ? null : fn:getTest(account, st)}" />
										<c:set var="passed"
											value="${(testdata == null || !testdata.passed) ? false : true}" />
										<tr>
											<td><span><a class="${passed ? " glyphicon
													glyphicon-ok
													green" : ""}" href="/subtopic?stid=${st.keyAsString}">${st.title}</a></span></td>
										</tr>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								<ul class="sortable list">
									<c:forEach items="${subject.subtopics}" var="st">
										<c:set var="testdata"
											value="${account == null ? null : fn:getTest(account, st)}" />
										<c:set var="passed"
											value="${(testdata == null || !testdata.passed) ? false : true}" />
										<li class="tc"><a class="${passed ? " glyphicon glyphicon-ok
											green" : ""}" href="/subtopic?stid=${st.keyAsString}">${st.title}</a>
										</li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-lg-8 content-holder shift-right-5">
						<h3 class="tc">Statistics</h3>
						<div class="col-lg-6">
							<h4 class="tc">Global</h4>
							<span>Total Topics:</span><span class="detail">${numTopicsInSubject}</span>
							<br /> <span>Total Questions:</span><span class="detail">${numQuestionsInSubject}</span>
							<br /> <span>Questions / Topic (average):</span><span
								class="detail"><fmt:formatNumber
									value="${numQuestionsInSubject / numTopicsInSubject}"
									maxFractionDigits="1" /></span> <br />
						</div>
						<div class="col-lg-6">
							<h4 class="tc">Personal</h4>
							<span>Topics Completed:</span><span class="detail">${numTopicsCompleted}</span>
							<br />
							<span>Topics Not Started:</span><span class="detail">${numSubtopicsNotStarted}</span>
							<br /> <span>Questions Completed:</span><span class="detail">${numQuestionsCompleted}</span>
							<br /> <span>Questions Correct:</span><span class="detail">${numQuestionsCorrect}</span>
							<br /> <span>Percentage Questions Correct:</span><span
								class="detail">${percentCorrect}</span> <br />
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/jquery.sortable.min.js"></script>
<script src="/js/subjectsorting.js"></script>
</html>