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
			<c:choose>
				<c:when test="${subject != null && subtopic != null}">
					<div class="bread-crumbs">
						<a href="/home">Home</a> / <a href="/discuss">Discuss</a> / <a
							href="/discuss?sid=${subject.keyAsString}">${subject.title}</a> / ${subtopic.title}
					</div>
					<h1 class="tc">Discussion Board</h1>
					<c:forEach items="${subtopic.posts}" var="p">
						<c:set var="author" value="p.author" />
						<div class="content-holder discussion-board-question">
							<a href="/discuss?pid=${p.keyAsString}">${p.title}</a>
							<c:if test="${canDelete}">
								<div class="del-button-holder">
									<a href="#"><span
										class="glyphicon glyphicon-remove remove-button"></span></a>
								</div>
							</c:if>
							<div class="author">By: ${author.displayNameOrEmail}</div>
							<div class="timestamp">Posted at ${p.date}</div>
						</div>
					</c:forEach>
					<c:if test="${account != null}">
						<textarea class="discussion-question"></textarea>
						<div class="tc">
							<a class="btn btn-cg">Post</a>
						</div>
					</c:if>
				</c:when>
				<c:when test="${subject != null}">
					<div class="bread-crumbs">
						<a href="/home">Home</a> / <a href="/discuss">Discuss</a> / ${subject.title}
					</div>
					<h1 class="tc">Discussion Board</h1>
					<c:forEach items="${subject.subtopics}" var="st">
						<div class="content-holder discussion-board-question">
							<a href="/discuss?sid=${subject.keyAsString}&stid=${st.keyAsString}">${st.title}</a>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="bread-crumbs">
						<a href="/home">Home</a> / Discuss
					</div>
					<h1 class="tc">Discussion Board</h1>

					<c:forEach items="${allsubjectslist}" var="sub">
						<div class="content-holder discussion-board-question">
							<a href="/discuss?sid=${sub.keyAsString}">${sub.title}</a>
						</div>
					</c:forEach>

				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
</html>