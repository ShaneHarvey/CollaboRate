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
				<h1 class="tc">User Panel</h1>
				<div class="content-holder tc">
					<h2 class="tc">Request Subtopic</h2>
					<br />
					<div class="table-wrapper">
						<table>
							<tr>
								<td><span class="title">Subject:</span></td>
								<td>${subjectname}</td>
								<input id="subjectid" type="hidden" value="${sid}" />
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
					<a id="btn_addSubject" class="btn btn-cg">Add Topic</a>
					<div id="subjectLoading" class="tc loadingDiv"
						style="display: none;">
						<img src="/images/ajax-loader.gif" alt="loading"><br />
						<br />
					</div>
				</div>
			</div>
		</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>
	</div>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/RequestSubtopic.js"></script>
</html>