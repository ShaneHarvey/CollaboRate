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
			<div class="bread-crumbs">
				<a href="/home">Home</a> / <a
					href="/subject?sid=${subject.keyAsString}">${subject.title}</a> / <a
					href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a>
				/ Video
			</div>
			<h1 class="tc">${video.title}</h1>
			<div class="video-embed">
				<iframe width="640" height="480" src="${video.URL}" frameborder="0"
					allowfullscreen></iframe>
				<div id="feedback" class="feedback"
					loggedin="${account == null ? false : true}" url="video"
					cid="${video.keyAsString}"
					ur="${metadata == null ? -1 : metadata.rating}"
					uf="${metadata == null ? false : metadata.flagged}"
					gr="${video.globalRating}"></div>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>
</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/FeedbackDisplay.js"></script>
<script src="/js/video.js"></script>
</html>