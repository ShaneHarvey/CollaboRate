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
			<div class="body-center-div">
				<c:choose>
					<c:when
						test="${subject != null && subtopic != null && post != null}">
						<div class="bread-crumbs">
							<a href="/home">Home</a> / <a href="/discuss">Discuss</a> / <a
								href="/discuss?sid=${subject.keyAsString}">${subject.title}</a>
							/ <a
								href="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}">${subtopic.title}</a>
							/ Post
						</div>
						<h1 class="tc">Discussion Board</h1>
						<div class="content-holder discussion-board-question">
							<p class="discussion-question">${post.body}</p>
							<c:if test="${fn:canDelete(post, account)}">
								<div class="del-button-holder-ques">
									<a id="btn_deletePost" pid="${post.keyAsString}"
										url="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}"><span
										class="glyphicon glyphicon-remove remove-button"></span></a>
								</div>
							</c:if>
							<div class="author">By: ${post.author.displayNameOrEmail}</div>
							<div class="timestamp">${post.date}</div>
							<br />
							<c:forEach items="${post.comments}" var="c">
								<div class="content-holder discussion-board-question">
									<div>${c.body}</div>
									<c:if test="${fn:canDelete(c, account)}">
										<div class="del-button-holder-ques">
											<a id="btn_deleteComment" cid="${c.keyAsString}"
												url="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}&pid=${post.keyAsString}"><span
												class="glyphicon glyphicon-remove remove-button"></span></a>
										</div>
									</c:if>
									<div class="author">By: ${c.author.displayNameOrEmail}</div>
									<div class="timestamp">${c.date}</div>
								</div>
							</c:forEach>
							<c:if test="${account != null}">
								<textarea id="commentText" class="discussion-question rt"></textarea>
								<div class="tc">
									<a id="btn_createComment" class="btn btn-cg"
										sid="${subject.keyAsString}" stid="${subtopic.keyAsString}"
										pid="${post.keyAsString}">Respond</a>
								</div>
								<div id="commentLoading" class="tc loadingDiv"
									style="display: none;">
									<img src="/images/ajax-loader.gif" alt="loading"><br />
									<br />
								</div>
							</c:if>
						</div>
					</c:when>
					<c:when test="${subject != null && subtopic != null}">
						<div class="bread-crumbs">
							<a href="/home">Home</a> / <a href="/discuss">Discuss</a> / <a
								href="/discuss?sid=${subject.keyAsString}">${subject.title}</a>
							/ ${subtopic.title}
						</div>
						<h1 class="tc">Discussion Board</h1>
						<c:forEach items="${subtopic.posts}" var="p">
							<div class="content-holder discussion-board-question">
								<a
									href="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}&pid=${p.keyAsString}">${p.body}</a>
								<c:if test="${fn:canDelete(p, account)}">
									<div class="del-button-holder">
										<a id="btn_deletePost" pid="${p.keyAsString}"
											url="/discuss?sid=${subject.keyAsString}&stid=${subtopic.keyAsString}"><span
											class="glyphicon glyphicon-remove remove-button"></span></a>
									</div>
								</c:if>
								<div class="author">By: ${p.author.displayNameOrEmail}</div>
								<div class="timestamp">Posted at ${p.date}</div>
							</div>
						</c:forEach>
						<c:if test="${account != null}">
							<textarea id="postText" class="discussion-question"></textarea>
							<div class="tc">
								<a id="btn_createPost" class="btn btn-cg"
									sid="${subject.keyAsString}" stid="${subtopic.keyAsString}">Post</a>
							</div>
							<div id="postLoading" class="tc loadingDiv"
								style="display: none;">
								<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
							</div>
						</c:if>
					</c:when>
					<c:when test="${subject != null}">
						<div class="bread-crumbs">
							<a href="/home">Home</a> / <a href="/discuss">Discuss</a> /
							${subject.title}
						</div>
						<h1 class="tc">Discussion Board</h1>
						<c:forEach items="${subject.subtopics}" var="st">
							<div class="content-holder discussion-board-question">
								<a
									href="/discuss?sid=${subject.keyAsString}&stid=${st.keyAsString}">${st.title}</a>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="bread-crumbs">
							<a href="/home">Home</a> / Discuss
						</div>
						<h1 class="tc">Discussion Board</h1>

						<c:forEach items="${fn:getAllSubjects()}" var="sub">
							<div class="content-holder discussion-board-question">
								<a href="/discuss?sid=${sub.keyAsString}">${sub.title}</a>
							</div>
						</c:forEach>

					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/tinymce/tinymce.min.js"></script>
<script src="/js/plugins/tinymce/tiny.js"></script>
<script src="/js/discuss.js"></script>
</html>