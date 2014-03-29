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

    <%@include file="/includes/header-logged-in.html" %>

	<div class="body">
		<div class="body-center-div">
		    <div class="bread-crumbs"><a href="home.jsp">Home</a> / My Account </div>
			<h1 class="tc">My Account</h1>
			<br />
			<div class="account-info">
			     <table>
			         <tr>
			             <td><span class="title">Display Name:</span></td>
			             <td><input id="displayName" type="text" value="cse 308" disabled></td>
			         </tr>
			         <tr>
			             <td><span class="title">Email:</span></td>
			             <td><input id="email" type="email" value="cse.308.cyber.grapes@gmail.com" disabled></td>
			         </tr>
			         <tr class="hideGroup" style="display:none;">
			             <td><span class="title">New Password:</span></td>
			             <td><input id="newPassword" type="password" disabled></td>
			         </tr>
			         <tr class="hideGroup" style="display:none;">
			             <td><span class="title">Confirm Password:</span></td>
			             <td><input id="confirmNewPassword" type="password" disabled></td>
			         </tr>
			         <tr class="hideGroup" style="display:none;">
	                      <td><span class="title">Current Password:</span></td>
	                      <td><input id="currentPassword" type="password" disabled></td>		         
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
		</div>
	</div>

    <%@include file="/includes/footer.html" %>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="js/accountSettings.js"></script>
</html>