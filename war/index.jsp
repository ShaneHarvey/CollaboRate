<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="bc-white">
<head>
<meta charset="utf-8">
<title>CollaboRate</title>
<jsp:include page="/includes/css.jsp"></jsp:include>
</head>
<body class="bc-white">

	<div class="wrapper">
		<jsp:include page="/includes/header.jsp"></jsp:include>
		<!-- Carousel Wrapper -->
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Carousel -->
			<div class="carousel-inner">
				<!-- Question item -->
				<div class="item active">
					<div class="container">
						<div class="carousel-caption home-questions-display">
							<h1>Answer and Contribute Questions!</h1>
							<br /> <br />
							<div>
								<div class="question-title">${question.title}</div>

								<div class="question-answers">
									<ol id="question-list">
										<c:forEach items="${question.answerChoices}" var="choice">
											<li class="tl">${choice}</li>
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
									<h2 id="incorrectAnswer" class="dark-red"
										style="display: none;">Incorrect</h2>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Video item -->
				<div class="item">
					<div class="container">
						<div class="carousel-caption video-display">
							<h1>Watch Lecture Videos!</h1>
							<div class="video-embed">
								<iframe width="534" height="400" src="${video.URL}"
									frameborder="0" allowfullscreen></iframe>
							</div>
						</div>
					</div>
				</div>
				<!-- Notes item -->
				<div class="item">
					<div class="container">
						<div class="carousel-caption notes-display">
							<h1>Get Links to the Best Study Material!</h1>
							<a href="${notes.URL}" target="_blank">${notes.title}</a>
						</div>
					</div>
				</div>
			</div>
			<!-- Bottom Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
		</div>

		<!--<div class="container">

		<h1 class="section-header tc">About Us:</h1>

		<div class="col-lg-6">
			<img data-src="holder.js/200x200/auto/sky" class="img-circle ce"
				alt="200x200" src="images/JamieLapine.jpg">
			<h2>Jamie Lapine</h2>
			<h4 class="role">Lead Designer</h4>
			<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis
				euismod. Nullam id dolor id nibh ultricies vehicula ut id elit.
				Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
				Praesent commodo cursus magna.</p>
		</div>
		<div class="col-lg-6">
			<img data-src="holder.js/200x200/auto/sky" class="img-circle ce"
				alt="200x200" src="images/SoumadipMukherjee.jpg">
			<h2>Soumadip Mukherjee</h2>
			<h4 class="role">Data Designer</h4>
			<p>Duis mollis, est non commodo luctus, nisi erat porttitor
				ligula, eget lacinia odio sem nec elit. Cras mattis consectetur
				purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo,
				tortor mauris condimentum nibh.</p>
		</div>
		<div class="col-lg-6">
			<img data-src="holder.js/200x200/auto/sky" class="img-circle ce"
				alt="200x200" src="images/PhilipAmmirato.jpg">
			<h2>Philip Ammirato</h2>
			<h4 class="role">Lead Programmer</h4>
			<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
				egestas eget quam. Vestibulum id ligula porta felis euismod semper.
				Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
				nibh, ut fermentum massa justo sit amet risus.</p>
		</div>
		<div class="col-lg-6">
			<img data-src="holder.js/200x200/auto/sky" class="img-circle ce"
				alt="200x200" src="images/ShaneHarvey.jpg">
			<h2>Shane Harvey</h2>
			<h4 class="role">Project Manager</h4>
			<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis
				euismod. Nullam id dolor id nibh ultricies vehicula ut id elit.
				Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
				Praesent commodo cursus magna.</p>
		</div>

	</div>
	<!-- Contact us -->
		<!--
    <div class="container">

    	<hr />

    	<h1 class="section-header tc">Contact Us:</h1>

    	    <div class="col-lg-6">
    			<h3 class="tc">General</h3>
    			<div class="left-padding-150">
    				<p>Cyber Grape Learning</p>
    				<p>100 Nicolls Road<br/>Stony Brook, NY <br/> United States </p>
    				<p>Telephone: (999) 999-9999 <br /> Fax: (888) 888-8888 <br /> Email: CSE308.Cyber.Grapes@gmail.com</p>
    			</div>
    		</div>

    		<div class="col-lg-6">
    			<h3 class="tc">Email</h3>
    			<div class="left-padding-150">
		          <h5>Name:</h5>
			      <input type="text" name="name"/>		
		          <h5>Email Address:</h5>
			      <input type="email" name="email"/>
		          <h5>Message:</h5>
			      <textarea name="message"></textarea>		
		          <br />
			      <a class="btn btn-cg">Submit</a>
    			</div>
    		</div>
    </div>
    -->
	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/video.js"></script>
<script src="/js/question.js"></script>
<script src="/js/plugins/FeedbackDisplay.js"></script>
</html>