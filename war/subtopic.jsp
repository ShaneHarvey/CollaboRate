<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/function.tld" prefix="fn"%>
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
				${subtopic.title}
			</div>
			<h1 class="tc">${subject.title}</h1>
			<div class="row">
				<div class="col-lg-6 content-holder shift-left-5">
					<h4 class="tc">Highest Rated Questions</h4>
					<ol>
						<c:forEach items="${topQuestions}" var="q">
							<c:set var="metadata"
								value="${account == null ? null : fn:getQuestionMetadata(account.key, q)}" />
							<c:set var="correct"
								value="${(metadata == null || !metadata.answerCorrect) ? false : true}" />
							<li><span><a class="${correct ? " glyphicon
									glyphicon-ok
									green" : ""}" href="/question?qid=${q.keyAsString}">${q.shortTitle}</a></span></li>
						</c:forEach>
					</ol>
					<div class="tc">
						<a>Load More...</a>
					</div>
				</div>
				<div class="col-lg-6 content-holder shift-right-5">
					<h4 class="tc">Highest Rated Videos</h4>
					<ol>
						<c:forEach items="${topVideos}" var="v">
							<li><a href="/video?vid=${v.keyAsString}">${v.shortTitle}</a></li>
						</c:forEach>
					</ol>
					<div class="tc">
						<a>Load More...</a>
					</div>
				</div>
				<div class="col-lg-6 content-holder shift-left-5">
					<h4 class="tc">Highest Rated Notes</h4>
					<ol>
						<c:forEach items="${topNotes}" var="n">
							<c:set var="metadata"
								value="${account == null ? null : fn:getNotesMetadata(account.key, n)}" />
							<li><a href="${n.URL}" class="fbHover" target="_blank">${n.shortTitle}</a>
								<div class="feedback feedback-tooltip"
									loggedin="${account == null ? false : true}" url="notes"
									cid="${n.keyAsString}"
									ur="${metadata == null ? -1 : metadata.rating}"
									uf="${metadata == null ? false : metadata.flagged}"
									gr="${n.globalRating}"></div></li>
						</c:forEach>
					</ol>
					<div class="tc">
						<a>Load More...</a>
					</div>
				</div>
				<div class="col-lg-6 shift-right-5">
					<div class="tc">
						<c:if test="${account != null}">
							<c:choose>
								<c:when test="${test}">
                                    <h3 class="green">You have already completed this subtopic.</h3>
								</c:when>
								<c:otherwise>
									<a class="mega-btn btn btn-cg"
										href="/test?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}">Take
										Test</a>
										<br />
										<br />
								</c:otherwise>
							</c:choose>
							
							<a class="mega-btn btn btn-cg"
								href="
								/addcontent?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}">Contribute</a>
						</c:if>
						<br /> <br /> <a class="mega-btn btn btn-cg"
							href="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}">Discuss</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/FeedbackDisplay.js"></script>
<script src="/js/subtopic.js"></script>
</html>