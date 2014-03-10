<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/custom-bootstrap.css">
<link rel="stylesheet" href="css/main.css">
<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
</head>
<body>

    <%@include file="/includes/header-admin.html" %>

	<div class="body">
		<div class="body-center-div">
			<h1 class="tc">Admin Panel</h1>
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
                      <a class="btn btn-cg">Remove</a>
                      <a class="btn btn-cg">Unflag</a>
                    </div>
                </div>
			</div>
			<div class="content-holder">
			    <h2 class="tc">Flagged Videos</h2>
			    <div class="content-holder side-margins-10">
			        <h3 class="tc">Bad Video</h3>
		            <div class="video-embed">
		                <iframe width="640" height="480" src="https://www.youtube-nocookie.com/embed/7DjsD7Hcd9U?wmode=transparent" frameborder="0" allowfullscreen></iframe>
                    </div>
                    <div class="tc">
                      <a class="btn btn-cg">Remove</a>
                      <a class="btn btn-cg">Unflag</a>
                    </div>
                </div>
			</div>
			<div class="content-holder">
			    <h2 class="tc">Flagged Lectures</h2>
			    <div class="content-holder side-margins-10 tc">
                    <h3 class="tc">Bad Lecture</h3>
                    <a href="https://www.google.com">https://www.google.com</a>
                    <br />
                    <br />
                    <div class="tc">
                      <a class="btn btn-cg">Remove</a>
                      <a class="btn btn-cg">Unflag</a>
                    </div>
                </div>
			</div>
		</div>
	</div>

    <%@include file="/includes/footer.html" %>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>