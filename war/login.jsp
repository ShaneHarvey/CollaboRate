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
      
        <div class="login-center-div">
            
            <h3>Email:</h3>
            <input type="email" name="email" class="login-input">

            <h3>Password:</h3>
            <input type="password" name="password" class="login-input">
            <br />
            <div class="tc">
                <a href="home.jsp" class="btn btn-cg">Submit</a>
            </div>
            <br />
            <a href="#">Forgot Password?</a>
      
        </div>

  </div>

    <%@include file="/includes/footer.html" %>

</body>
</html>