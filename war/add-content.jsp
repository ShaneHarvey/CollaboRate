<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<c:when test="${subject != null && subtopic != null}">
						<div class="bread-crumbs">
							<a href="/home">Home</a> / <a href="/addcontent">Add Content</a>
							/ <a href="/addcontent?sid=${subject.keyAsString}">${subject.title}</a>
							/ ${subtopic.title}
						</div>
						<br />
						<div class="tabbable">
							<ul class="nav nav-tabs nav-justified">
								<li class="active"><a href="#tabs1-pane1" data-toggle="tab">Create
										Question</a></li>
								<li><a href="#tabs1-pane2" data-toggle="tab">Add Video</a></li>
								<li><a href="#tabs1-pane3" data-toggle="tab">Add Notes</a></li>
							</ul>
							<br />
							<div class="tab-content">
								<div class="tab-pane active" id="tabs1-pane1">
									<div class="content-holder tc">
										<h2 class="tc">Question Title:</h2>
										<input id="questionTitle" class="title-area" type="text"
											maxlength="25" />
										<h2 class="tc">Question Description:</h2>
										<div id="questionDescription" class="rt"></div>
										<br />
										<h2 class="tc">Answer Description:</h2>
										<div id="answerDescription" class="rt"></div>
										<h2 class="tc">Answers:</h2>
										<div class="question-table-wrapper">
											<table id="answerChoiceTable">
												<tr>
													<td><div class="rt"></div></td>
													<td><input type="radio" name="answers" value="0"
														checked /></td>
												</tr>
												<tr>
													<td><div class="rt"></div></td>
													<td><input type="radio" name="answers" value="1" /></td>
												</tr>
											</table>
										</div>
										<span id="btn_addAnswer"
											class="glyphicon glyphicon-plus hoverHand"></span> <br /> <br />
										<a id="btn_addQuestion" class="btn btn-cg white"
											stid="${subtopic.keyAsString}" sid="${subject.keyAsString}">Create</a>
										<div id="questionLoading" class="tc loadingDiv"
											style="display: none;">
											<img src="/images/ajax-loader.gif" alt="loading"><br />
											<br />
										</div>
									</div>
								</div>
								<div class="tab-pane" id="tabs1-pane2">
									<div class="content-holder tc">
										<h1 class="tc">Add Video</h1>
										<span class="title">Video Description:</span> <br /> <input
											id="videoDescription" class="title-area" type="text"
											maxlength="25" /> <br /> <span class="title">Video
											URL:</span> <br /> <input id="videoURL" type="text" /> <br /> <br />
										<a id="btn_addVideo" class="btn btn-cg white"
											sid="${subject.keyAsString}" stid="${subtopic.keyAsString}">Add</a>
										<div id="videoLoading" class="tc loadingDiv"
											style="display: none;">
											<img src="/images/ajax-loader.gif" alt="loading"><br />
											<br />
										</div>
									</div>
								</div>
								<div class="tab-pane" id="tabs1-pane3">
									<div class="content-holder tc">
										<h1 class="tc">Add Notes</h1>
										<span class="title">Notes Description:</span> <br /> <input
											id="notesDescription" class="title-area" type="text"
											maxlength="25" /> <br /> <span class="title">Notes
											URL:</span> <br /> <input id="notesURL" type="text"> <br />
										<br /> <a id="btn_addNotes" class="btn btn-cg white"
											sid="${subject.keyAsString}" stid="${subtopic.keyAsString}">Add</a>
										<div id="notesLoading" class="tc loadingDiv"
											style="display: none;">
											<img src="/images/ajax-loader.gif" alt="loading"><br />
											<br />
										</div>
									</div>
								</div>
							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.tabbable -->
					</c:when>
					<c:when test="${subject != null}">
						<div class="bread-crumbs">
							<a href="/home">Home</a> / <a href="/addcontent">Add Content</a>
							/ ${subject.title}
						</div>
						<br />
						<div class="content-holder tc">
							<h3>Choose a Subtopic</h3>
							<select id="subtopicSelector">
								<c:forEach items="${subject.subtopics}" var="st">
									<option value="${st.keyAsString}">${st.title}</option>
								</c:forEach>
							</select> <br /> <br /> <a id="btn_chooseSubtopic" class="btn btn-cg"
								sid="${subject.keyAsString}">Next</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="bread-crumbs">
							<a href="/home">Home</a> / Add Content
						</div>
						<br />
						<div class="content-holder tc">
							<h3>Choose a Subject</h3>
							<select id="subjectSelector">
								<c:forEach items="${subjectList}" var="sub">
									<option value="${sub.keyAsString}">${sub.title}</option>
								</c:forEach>
							</select> <br /> <br /> <a id="btn_chooseSubject" class="btn btn-cg">Next</a>
						</div>
					</c:otherwise>
				</c:choose>
				<!-- <a href="http://www.codecogs.com" target="_blank">Text editor powered by Code Cogs</a>-->
			</div>
		</div>
	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/content.js"></script>
<script type="text/javascript" src="/js/plugins/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="/js/plugins/tinymce/tiny.js"></script>
</html>