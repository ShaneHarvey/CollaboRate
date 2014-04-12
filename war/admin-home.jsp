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
			<h1 class="tc">Admin Panel</h1>
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
				<div id="subjectLoading" class="tc loadingDiv" style="display: none;">
					<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
				</div>
			</div>
			<div class="content-holder">
				<h2 class="tc">Flagged Questions</h2>
				<div class="content-holder side-margins-10">
					<h3 class="tc">Curse Words:</h3>
					<div class="question-answers">
						<ol>
							<li>Bad word 1</li>
							<li>Bad word 2</li>
							<li>Bad word 3</li>
							<li>Bad word 4</li>
						</ol>
					</div>
					<div class="tc">
						<a class="btn btn-cg">Remove</a> <a class="btn btn-cg">Unflag</a>
					</div>
				</div>
			</div>
			<div class="content-holder">
				<h2 class="tc">Flagged Videos</h2>
				<div class="content-holder side-margins-10">
					<h3 class="tc">Bad Video</h3>
					<div class="video-embed">
						<iframe width="640" height="480"
							src="https://www.youtube-nocookie.com/embed/7DjsD7Hcd9U?wmode=transparent"
							frameborder="0" allowfullscreen></iframe>
					</div>
					<div class="tc">
						<a class="btn btn-cg">Remove</a> <a class="btn btn-cg">Unflag</a>
					</div>
				</div>
			</div>
			<div class="content-holder">
				<h2 class="tc">Flagged Lectures</h2>
				<div class="content-holder side-margins-10 tc">
					<h3 class="tc">Bad Lecture</h3>
					<a href="https://www.google.com">https://www.google.com</a> <br />
					<br />
					<div class="tc">
						<a class="btn btn-cg">Remove</a> <a class="btn btn-cg">Unflag</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/admin.js"></script>
</html>