<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
<link rel="stylesheet" href="/css/custom-jquery-ui.css">
</head>
<body>

	<jsp:include page="/includes/header.jsp"></jsp:include>

	<div class="body">
		<div class="body-center-div">
			<div class="bread-crumbs">
				<a href="/home">Home</a> / <a href="#">Science</a> / <a href="#">Chemistry</a>
				/ <a href="#"> Chemical Bonding </a> / Add Content
			</div>
			<br />
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
				<span id="btn_addAnswer" class="glyphicon glyphicon-plus hoverHand"></span>
				<br /> <br /> <a id="btn_addQuestion" class="btn btn-cg white">Create</a>
				<div id="questionLoading" class="tc loadingDiv"
					style="display: none;">
					<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
				</div>
			</div>
			<div class="content-holder tc">
				<h1 class="tc">Add Video</h1>
				<span class="title">Video URL:</span> <br /> <input id="videoURL"
					type="text" /> <br /> <span class="title">Video
					Description:</span> <br />
				<textarea id="videoDescription"></textarea>
				<br /> <br /> <a id="btn_addVideo" class="btn btn-cg white">Add</a>
				<div id="videoLoading" class="tc loadingDiv" style="display: none;">
					<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
				</div>
			</div>
			<div class="content-holder tc">
				<h1 class="tc">Add Notes</h1>
				<span class="title">Notes URL:</span> <br /> <input id="notesURL"
					type="text"> <br /> <span class="title">Notes
					Description:</span> <br />
				<textarea id="notesDescription"></textarea>
				<br /> <br /> <a id="btn_addNotes" class="btn btn-cg white">Add</a>
				<div id="notesLoading" class="tc loadingDiv" style="display: none;">
					<img src="/images/ajax-loader.gif" alt="loading"><br /> <br />
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/jquery-ui-1.10.4.min.js"></script>
<script src="/js/content.js"></script>
</html>