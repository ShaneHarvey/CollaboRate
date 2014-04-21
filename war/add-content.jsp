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
						<a href="/home">Home</a> / <a href="/addcontent">Add Content</a> /
						<a href="/addcontent?sid=${subject.keyAsString}">${subject.title}</a>
						/ ${subtopic.title}
					</div>
					<br />
				<div class="tabbable">
				<ul class="nav nav-tabs nav-justified">
					<li class="active"><a href="#tabs1-pane1" data-toggle="tab">Create Question</a></li>
					<li><a href="#tabs1-pane2" data-toggle="tab">Add Video</a></li>
					<li><a href="#tabs1-pane3" data-toggle="tab">Add Notes</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tabs1-pane1">
						<div class="content-holder tc">
							<h1 class="tc">Create Question</h1>
							<span class="title">Question Description:</span> <br />
							<textarea id="questionDescription"></textarea>
							<br /> <span class="title">Answers:</span> <br />
							<div class="question-table-wrapper">
								<table id="answerChoiceTable">
									<tr>
										<td><textarea rows="1"></textarea></td>
										<td><input type="radio" name="answers" value="0" checked /></td>
									</tr>
									<tr>
										<td><textarea rows="1"></textarea></td>
										<td><input type="radio" name="answers" value="1" /></td>
									</tr>
								</table>
							</div>
							<span id="btn_addAnswer"
								class="glyphicon glyphicon-plus hoverHand"></span> <br /> <br />
							<a id="btn_addQuestion" class="btn btn-cg white" stid="${subtopic.keyAsString}">Create</a>
							<div id="questionLoading" class="tc loadingDiv"
								style="display: none;">
								<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs1-pane2">
						<div class="content-holder tc">
							<h1 class="tc">Add Video</h1>
							<span class="title">Video URL:</span> <br /> <input id="videoURL"
								type="text" /> <br /> <span class="title">Video
								Description:</span> <br />
							<textarea id="videoDescription"></textarea>
							<br /> <br /> <a id="btn_addVideo" class="btn btn-cg white" stid="${subtopic.keyAsString}">Add</a>
							<div id="videoLoading" class="tc loadingDiv"
								style="display: none;">
								<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs1-pane3">
						<div class="content-holder tc">
							<h1 class="tc">Add Notes</h1>
							<span class="title">Notes URL:</span> <br /> <input id="notesURL"
								type="text"> <br /> <span class="title">Notes
								Description:</span> <br />
							<textarea id="notesDescription"></textarea>
							<br /> <br /> <a id="btn_addNotes" class="btn btn-cg white" stid="${subtopic.keyAsString}">Add</a>
							<div id="notesLoading" class="tc loadingDiv"
								style="display: none;">
								<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
							</div>
						</div>
					</div>
				</div><!-- /.tab-content -->
			</div><!-- /.tabbable -->
				</c:when>
				<c:when test="${subject != null}">
					<div class="bread-crumbs">
						<a href="/home">Home</a> / <a href="/addcontent">Add Content</a> /
						${subject.title}
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
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/content.js"></script>
</html>