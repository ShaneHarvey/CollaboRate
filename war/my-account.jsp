<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<jsp:include page="/includes/css.html"></jsp:include>
<%//@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/custom-jquery-ui.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp"></jsp:include>

	<div class="body">
		<div class="body-center-div">
		    <div class="bread-crumbs"><a href="/home">Home</a> / My Account </div>
			<h1 class="tc">My Account</h1>
			<br />
			<div class="account-info">
			     <table>
			         <tr>
                         <td><span class="title">Email:</span></td>
                         <td><c:out><span>${email}</span></c:out></td>
                     </tr>
			         <tr>
			             <td><span class="title">Display Name:</span></td>
			             <td><c:out><input id="displayName" type="text" value="${displayName}" placeholder="none" disabled></c:out></td>
			         </tr>
			         <tr class="hideGroup" style="display:none;">
                          <td><span class="title">Current Password:</span></td>
                          <td><input id="currentPassword" type="password" disabled></td>                 
                     </tr>
			         <tr class="hideGroup" style="display:none;">
			             <td><span class="title">New Password:</span></td>
			             <td><input id="newPassword" type="password" disabled></td>
			         </tr>
			         <tr class="hideGroup" style="display:none;">
			             <td><span class="title">Confirm Password:</span></td>
			             <td><input id="confirmNewPassword" type="password" disabled></td>
			         </tr>
			     </table>
			    <br />
		        <div class="hideGroup tc" style="display:none;">
                    <a id="editInfoButton" class="hoverHand">Submit Changes</a>
                </div>
		    </div>
		    <br />
		    <br />
            <div class="tc">
                <a id="allowEditButton" class="hoverHand">Edit Info</a>
                <br />
                <br />
                <a id="deactiveButton" class="hoverHand">Deactivate Account</a>
            </div>
            <div id="accountLoading" class="tc loadingDiv" style="display:none;"><img src="/images/ajax-loader.gif" alt="loading"><br/><br /></div>
		</div>
	</div>

    <jsp:include page="/includes/footer.html"></jsp:include>

</body>
<jsp:include page="/includes/js.html"></jsp:include>
<script src="/js/jquery-ui-1.10.4.min.js"></script>
<script src="/js/account.js"></script>
</html>