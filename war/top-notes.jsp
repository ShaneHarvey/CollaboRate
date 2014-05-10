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

	<div class="wrapper">
		<jsp:include page="/includes/header.jsp"></jsp:include>

		<div class="body">
			<div class="body-center-div question-body">
				<div class="bread-crumbs">
					<a href="/home">Home</a> / <a
						href="/subject?sid=${subject.keyAsString}">${subject.title}</a> /
					<a href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a>
					/ Top Rated Notes
				</div>

				<h1 class="tc">Top Notes</h1>
				<ol>
					<c:forEach items="${topNotes}" var="n">
						<c:set var="metadata"
							value="${account == null ? null : fn:getNotesMetadata(account.key, n)}" />
						<li><a href="${n.URL}" class="fbnHover" target="_blank">${n.shortTitle}</a>
							<div class="feedback feedback-tooltip"
								loggedin="${account == null ? false : true}" url="notes"
								cid="${n.keyAsString}"
								ur="${metadata == null ? -1 : metadata.rating}"
								uf="${metadata == null ? false : metadata.flagged}"
								gr="${n.globalRating}"></div></li>
					</c:forEach>
				</ol>
				<c:if test="${hasMore}">
					<div class="tc">
						<a
							href="/top-content?stid=${subtopic.keyAsString}&ctype=notes&from=${from + 10}&to=${to + 10}"><c:out
								value="${from + 10} to ${to + 10}" /></a>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/FeedbackDisplay.js"></script>
<script src="/js/notes.js"></script>
</html>