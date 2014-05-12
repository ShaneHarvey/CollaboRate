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
					/ Top Rated Questions
				</div>

				<h1 class="tc">Top Questions</h1>
				<ol>
					<c:forEach items="${topQuestions}" var="q" begin="${sIndex}">
						<c:set var="metadata"
							value="${account == null ? null : fn:getQuestionMetadata(account.key, q)}" />
						<c:set var="correct"
							value="${(metadata == null || !metadata.answerCorrect) ? false : true}" />
						<li><span><a class="${correct ? " glyphicon
								glyphicon-ok green" : ""}" href="/question?qid=${q.keyAsString}">${q.shortTitle}</a></span></li>
					</c:forEach>
				</ol>
				<c:if test="${hasMore}">
				    <div class="tc"> <a href="/top-content?stid=${subtopic.keyAsString}&ctype=question&from=${from + 10}&to=${to + 10}"><c:out value="${from + 10} to ${to + 10}"/></a></div>
			    </c:if>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
</html>