<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
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
				<div class="tc">
					<display:table name="sessionScope.results">
						<display:column property="type" sortable="true" />
  						<display:column property="title" sortable="true" />
  						<display:column property="author" sortable="true" />
  						<display:column property="date" format="{0,date,dd-MM-yyyy}" sortable="true" />
					</display:table>
				</div>
			</div>
		</div>

	</div>

	<jsp:include page="/includes/footer.jsp"></jsp:include>
</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<link rel="stylesheet" href="/css/displaytag.css">
</html>