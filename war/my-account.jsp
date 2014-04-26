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
		    <div class="bread-crumbs"><a href="/home">Home</a> / My Account </div>
			<h1 class="tc">My Account</h1>
			<br />
			<div class="table-wrapper">
			     <table>
			         <tr>
                         <td><span class="title">Email:</span></td>
                         <td><span>${email}</span></td>
                     </tr>
			         <tr>
			             <td><span class="title">Display Name:</span></td>
			             <td><input id="displayName" type="text" value="${displayName}" placeholder="none" disabled></td>
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

    <jsp:include page="/includes/footer.jsp"></jsp:include>

</body>
<jsp:include page="/includes/js.jsp"></jsp:include>
<script src="/js/account.js"></script>
</html>