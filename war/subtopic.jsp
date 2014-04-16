<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/function.tld" prefix="fn"%>
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
					href="/subject?sid=${subject.keyAsString}">${subject.title}</a> /
				${subtopic.title}
			</div>
			<h1 class="tc">${subject.title}</h1>
			<div class="row">
				<div class="col-lg-6 content-holder shift-left-5">
					<h4 class="tc">Highest Rated Questions</h4>
					<ol>
						<c:forEach items="${subtopic.topQuestions}" var="q">
							<li><a href="/question?qid=${q.keyAsString}">${q.shortTitle}</a></li>
						</c:forEach>
						<!--<li><a href="question.jsp">The electrons in a nonpolar...</a></li>
						<li>What is the charge on the i...</li>
						<li><span class="glyphicon glyphicon-ok green"></span>What is the most correct na...</li>
						<li>What type of bonds are form...</li>
						<li><span class="glyphicon glyphicon-ok green"></span>The bond between sulfur and...</li>
					     -->
					</ol>
					<div class="tc">
						<a>Load More...</a>
					</div>
				</div>
				<div class="col-lg-6 content-holder shift-right-5">
					<h4 class="tc">Highest Rated Videos</h4>
					<ol>
						<c:forEach items="${subtopic.topVideos}" var="v">
							<li><a href="/video?vid=${v.keyAsString}">${v.shortTitle}</a></li>
						</c:forEach>
						<!--<li><a href="video.jsp">Chemical Bonds: Covalent vs...</a></li>
						<li>Atomic Hook-Ups - Types of ...</li>
						<li>Bonding Models and Lewis St...</li>
						<li>Chemistry 4.1 Chemical Bonding</li>
						<li>Chemical Bonding - Bonding</li>-->
					</ol>
					<div class="tc">
						<a>Load More...</a>
					</div>
				</div>
				<div class="col-lg-6 content-holder shift-left-5">
					<h4 class="tc">Highest Rated Notes</h4>
					<ol>
						<c:forEach items="${subtopic.topNotes}" var="n">
							<c:set var="metadata"
								value="${fn:getUserMaterialMetadata((account == null ? null : account.key), n.key)}" />
							<li><a href="${n.URL}" class="fbHover">${n.shortTitle}</a>
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
				<c:if test="${account != null}">
					<div class="col-lg-6 shift-right-5">
						<div class="tc">
							<br /> <br /> <a class="mega-btn btn btn-cg"
								href="take-test.jsp">Take Test</a> <br /> <br /> <a
								class="mega-btn btn btn-cg"
								href="
								/addcontent?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}">Contribute</a>
							<br /> <br /> <a class="mega-btn btn btn-cg"
								href="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}">Discuss</a>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/FeedbackDisplay.js"></script>
<script src="/js/subtopic.js"></script>
</html>