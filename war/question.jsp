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
			<div class="bread-crumbs">
				<a href="/home">Home</a> / <a
					href="/subject?sid=${subject.keyAsString}">${subject.title}</a> / <a
					href="/subtopic?stid=${subtopic.keyAsString}">${subtopic.title}</a>
				/ Question
			</div>
			<!--
			<textarea id="editor">
			TinyMce!
			</textarea>
			  -->
			<h1 class="tc">${question.title}</h1>

			<div class="question-answers">
				<ol id="question-list">
					<c:forEach items="${question.answerChoices}" var="choice">
						<li><span>${choice}</span></li>
					</c:forEach>
				</ol>
			</div>
			<div class="tc">
				<a id="btn_submitAnswer" class="btn btn-cg"
					qid="${question.keyAsString}">Submit</a>
				<div id="answerLoading" class="tc loadingDiv"
					style="display: none;">
					<img src="/images/ajax-loader.gif" alt="loading">
				</div>
				<h2 id="correctAnswer" class="green" style="display: none;">Correct!</h2>
				<h2 id="incorrectAnswer" class="dark-red" style="display: none;">Incorrect</h2>
			</div>
			<div id="feedback" class="feedback"
				loggedin="${account == null ? false : true}" url="question"
				cid="${question.keyAsString}"
				ur="${metadata == null ? -1 : metadata.rating}"
				uf="${metadata == null ? false : metadata.flagged}"
				gr="${question.globalRating}"></div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/plugins/FeedbackDisplay.js"></script>
<script src="/js/question.js"></script>
<!-- 
<script src="/js/tinymce/tinymce.min.js"></script>
<script>
	tinymce.init({
		selector:'textarea',
		forced_root_block: false,
	    plugins: "noneditable",
	    readonly : 1,
	    noneditable_leave_contenteditable: true
	});
	$('.mce-menubar').remove();
	$('.mce-toolbar-grp').remove();
	$('.mce-statusbar').remove();
</script>
 -->
</html>