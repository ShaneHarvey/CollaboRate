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
			<div class="body-center-div too-thin-hack">
				<h1 class="tc">Admin Panel</h1>
				<div class="tabbable">
					<ul class="nav nav-tabs nav-justified">
						<li class="active"><a href="#tabs1-pane1" data-toggle="tab">Add
								Subjects</a></li>
						<li><a href="#tabs1-pane2" data-toggle="tab">Flagged
								Questions</a></li>
						<li><a href="#tabs1-pane3" data-toggle="tab">Flagged
								Videos</a></li>
						<li><a href="#tabs1-pane4" data-toggle="tab">Flagged
								Lectures</a></li>
						<li><a href="#tabs1-pane5" data-toggle="tab">Verify
								Questions</a></li>
						<li><a href="#tabs1-pane6" data-toggle="tab">Manage
								Subject</a></li>
					</ul>
					<br />
					<div class="tab-content">
						<div class="tab-pane active" id="tabs1-pane1">
							<div class="content-holder tc">
								<h2 class="tc">Add Subjects</h2>
								<br />
								<div class="table-wrapper">
									<table>
										<tr>
											<td><span class="title">Subject:</span></td>
											<td><input id="subjectName" type="text" /></td>
										</tr>
										<tr>
											<td valign="top"><span class="title">SubTopics:</span></td>
											<td class="subTopicTableWrapper">
												<table id="subTopicTable">
													<tr>
														<td><input type="text" /></td>
													</tr>
												</table> <span id="btn_AddSubtopic"
												class="glyphicon glyphicon-plus hoverHand"></span>
											</td>
										</tr>
									</table>
								</div>
								<a id="btn_addSubject" class="btn btn-cg">Add Subject</a>
								<div id="subjectLoading" class="tc loadingDiv"
									style="display: none;">
									<img src="/images/ajax-loader.gif" alt="loading"><br />
									<br />
								</div>
							</div>
						</div>
						<div class="tab-pane" id="tabs1-pane2">
							<div class="content-holder">
								<h2 class="tc">Flagged Questions</h2>
								<c:forEach items="${flaggedQuestions}" var="q">
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
											<a class="btn btn-cg remove" cid="${q.keyAsString}" ctype="1">Remove</a>
											<a class="btn btn-cg unflag" cid="${q.keyAsString}" ctype="1">Unflag</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="tab-pane" id="tabs1-pane3">
							<div class="content-holder">
								<h2 class="tc">Flagged Videos</h2>
								<c:forEach items="${flaggedVideos}" var="v">
									<div class="content-holder side-margins-10">
										<h3 class="tc">${v.title}</h3>
										<div class="video-embed">
											<iframe width="640" height="480" src="${v.URL}"
												frameborder="0" allowfullscreen></iframe>
										</div>
										<div class="tc">
											<a class="btn btn-cg remove" cid="${v.keyAsString}" ctype="0">Remove</a>
											<a class="btn btn-cg unflag" cid="${v.keyAsString}" ctype="0">Unflag</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="tab-pane" id="tabs1-pane4">
							<div class="content-holder">
								<h2 class="tc">Flagged Notes</h2>
								<c:forEach items="${flaggedNotes}" var="n">
									<div class="content-holder side-margins-10 tc">
										<h3 class="tc">${n.title}</h3>
										<a href="${n.URL}" target="_blank">${n.URL}</a> <br /> <br />
										<div class="tc">
											<a class="btn btn-cg remove" cid="${n.keyAsString}" ctype="2">Remove</a>
											<a class="btn btn-cg unflag" cid="${n.keyAsString}" ctype="2">Unflag</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="tab-pane" id="tabs1-pane5">
							<div class="content-holder">
								<h2 class="tc">Unverified Questions</h2>
								<c:forEach items="${fn:getAllUnverifiedQuestions()}" var="q">
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
											<a class="btn btn-cg verify" cid="${q.keyAsString}">Verify</a>
											<a class="btn btn-cg rm" cid="${q.keyAsString}">Remove</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>

						<div class="tab-pane" id="tabs1-pane6">
							<div class="content-holder">
								<h3>Choose a Subject</h3>
								<div class="content-holder side-margins-10 tc">
									<select id="subjectSelector" name="subjectSelector">
										<option value=""></option>
										<c:forEach items="${subjectList}" var="sub">
											<option value="${sub.keyAsString}">${sub.title}</option>
										</c:forEach>
									</select> <br /> <br />
								</div>

								<div class="content-holder side-margins-10 tc" id="subtopicList">


								</div>


								<div class="content-holder side-margins-10 tc"
									id="requestedSubtopics"></div>
							</div>
						</div>
					</div>
					<!-- /.tab-content -->
				</div>
				<!-- /.tabbable -->
			</div>
		</div>

	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/admin.js"></script>
<script src="/js/verify.js"></script>
</html>