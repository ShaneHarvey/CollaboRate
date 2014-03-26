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

    <%@include file="/includes/header-logged-out.html"%>

  <div class="body">

    <!-- Container holding login div -->
      
        <div class="login-center-div cut-vertical-padding">
            
             <h3>Email:</h3>
             <input type="email" class="login-input">

             <h3>Password:</h3>
             <input type="password" class="login-input">

             <h3>Confirm Password:</h3>
             <input type="password" class="login-input">

             <input type="checkbox" id="tcp-agree"><label class="checkbox-label" for="tcp-agree">I agree to the <a href="terms.jsp">Terms and Conditions</a> and <a href="privacy.jsp">Privacy Policy</a>.</label>

             <br />
             <br />
             <div class="tc">
                <a href="home.jsp" class="btn btn-cg tc">Submit</a>
             </div>
        </div>

  </div>

    <%@include file="/includes/footer.html"%>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>