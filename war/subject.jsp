<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/function.tld" prefix="fn"%>
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
				<a href="/home">Home</a> / ${subjectName}
			</div>
			<h1 class="tc">${subjectName}</h1>
			<div class="row">
				<div class="col-lg-4 content-holder shift-left-5">
					<h3 class="tc">Topics</h3>
					<table class="content-table">
						<c:forEach items="${stlist}" var="st">
							<c:set var="testdata"
								value="${account == null ? null : fn:getTest(account.key, sid, st.key)}" />
							<c:set var="passed"
								value="${(testdata == null || !testdata.passed) ? false : true}" />
							<tr>
								<td><span><a class="${passed ? " glyphicon
										glyphicon-ok
										green" : ""}" href="/subtopic?stid=${st.keyAsString}">${st.title}</a></span></td>
							</tr>
						</c:forEach>
						<!--<tr><td><span class="glyphicon glyphicon-ok green"></span>Math Skills needed for Chemistry</td></tr>
						<tr><td><span class="glyphicon glyphicon-ok green"></span>Atomic Concepts</td></tr>
						<tr><td><span class="glyphicon glyphicon-asterisk yellow"></span>Periodic Table</td></tr>
						<tr><td><span class="glyphicon glyphicon-asterisk yellow"></span>Moles/Stoichiometry</td></tr>
						<tr><td><a href="topic.jsp">Chemical Bonding</a></td></tr>
						<tr><td>Physical Behavior of Matter</td></tr>
						<tr><td>Kinetics/Equilibrium</td></tr>
						<tr><td>Organic Chemistry</td></tr>
						<tr><td>Oxidation-Reduction</td></tr>
						<tr><td class="not-available">Acids, Bases and Salts</td></tr>
						<tr><td class="not-available">Nuclear Chemistry</td></tr>-->
					</table>
				</div>
				<div class="col-lg-8 content-holder shift-right-5">
					<h3 class="tc">Statistics</h3>
					<div class="col-lg-6">
						<h4 class="tc">Global</h4>
						<span>Total Topics:</span><span class="detail">${numTopicsInSubject}</span> <br />
						<span>Total	Questions:</span><span class="detail">${numQuestionsInSubject}</span> <br /> 
						<span>Questions / Topic (average):</span><span class="detail"><fmt:formatNumber value="${numQuestionsInSubject / numTopicsInSubject}" maxFractionDigits="1"/></span> <br />
						<!--
						<span>Active Users:</span><span class="detail">327</span> <br />
						<span>Average Topics Completed:</span><span class="detail">4</span> <br />
						<span>Number of Users Completed:</span><span class="detail">16</span> <br />
						-->
					</div>
					<div class="col-lg-6">
						<h4 class="tc">Personal</h4>
						<span>Topics Completed:</span><span class="detail">${numTopicsCompleted}</span> <br />
						<!--
						<span>Topics In Progress Completed:</span><span class="detail">2</span> <br /> 
						-->
						<span>Topics Not Started:</span><span class="detail">${numSubtopicsNotStarted}</span> <br /> 
						<span>Questions Completed:</span><span class="detail">${numQuestionsCompleted}</span> <br />  
						<span>Questions Correct:</span><span class="detail">${numQuestionsCorrect}</span> <br /> 
						<span>Percentage Questions Correct:</span><span class="detail">${percentCorrect}</span> <br />
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
</html>