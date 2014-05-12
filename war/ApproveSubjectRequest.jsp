<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/function.tld" prefix="fn"%>
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
			<div class="body-center-div too-thin-hack">
				<h1 class="tc">Admin Panel</h1>
				<div class="content-holder tc">						
					<form name="input" action="/ApproveSubjectRequest" method="get">
						<input type="hidden" name="action" value="insert">
						<table>
							<tr>
								<td><span class="title">Category</span></td>
								<td>
								<select name="categoryKey">
									<c:forEach var="category" items="${categoryList}">
										<option value="${category.keyAsString}">${category.title}</option>
									</c:forEach>
								</select>
								</td>
							</tr>
							<tr>
								<td><span class="title">Subject Request</span></td>
								<td><span>${subjectRequest.title}</span>
							</tr>
							<input type="hidden" name="subKey" value="${subjectRequest.keyAsString}">
							<c:forEach items="${subjectRequest.subtopicRequests}" var="subtopicsReq">
								<tr>
									<td>${subtopicsReq.title}</td>
									<td><input type="checkbox" name="${subtopicsReq.keyAsString}" value="true"></td>
								</tr>
							</c:forEach>
							<tr>
								<td><input type="submit" value="Insert Subject Request"></td>
							</tr>
						</table>
						
					</form>
					<form name="input" action="/DeleteSubjectRequest" method="get">
						<input type="hidden" name="subKey" value="${subjectRequest.keyAsString}">
						<input type="submit" value="Delete">
						<table></table>
						
					</form>
				</div>
				<!-- /.tab-content -->
			</div>
		</div>

	</div>
	<jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/verify.js"></script>
<script src="/js/AddCategory.js"></script>
</html>