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
			<h1 class="tc">User Content Panel</h1>
			<div class="tabbable">
				<ul class="nav nav-tabs nav-justified">
					<li class="active"><a href="#tabs1-pane1" data-toggle="tab">Questions</a></li>
					<li><a href="#tabs1-pane2" data-toggle="tab">Videos</a></li>
					<li><a href="#tabs1-pane3" data-toggle="tab">Notes</a></li>
				</ul>
				<br />
				<div class="tab-content">
					<div class="tab-pane active" id="tabs1-pane1">
						<div class="content-holder tc">
							<h2 class="tc">My Questions</h2>
							<br />
							<div class="table-wrapper">
								<c:forEach items="${userQuestions}" var="q">
								<div class="content-holder side-margins-10">
									<h3 class="tc">${q.title}</h3>
									<div class="question-answers">
										<ol>
											<c:forEach items="${q.answerChoices}" var="c">
												<li><span>${c}</span></li>
											</c:forEach>
										</ol>
									</div>
								</div>
							</c:forEach>
							</div>
							
						</div>
					</div>
					<div class="tab-pane" id="tabs1-pane2">
						<div class="content-holder">
							<h2 class="tc">My Videos</h2>
							<c:forEach items="${userVideos}" var="v">
								<div class="content-holder side-margins-10">
									<h3 class="tc">${v.title}</h3>
									<div class="video-embed">
										<iframe width="640" height="480" src="${v.URL}"
											frameborder="0" allowfullscreen></iframe>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="tab-pane" id="tabs1-pane3">
						<div class="content-holder">
							<h2 class="tc">My Notes</h2>
							<c:forEach items="${userNotes}" var="n">
								<div class="content-holder side-margins-10 tc">
									<h3 class="tc">${n.title}</h3>
									<a href="${n.URL}" target="_blank">${n.URL}</a> <br /> <br />
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- /.tab-content -->
			</div>
			<!-- /.tabbable -->
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/admin.js"></script>
</html>