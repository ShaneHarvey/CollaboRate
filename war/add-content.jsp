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
            <div class="bread-crumbs"><a href="home.jsp">Home</a> / <a href="#">Science</a> /  <a href="#">Chemistry</a> / <a href="#"> Chemical Bonding </a> / Add Content </div>
            <br />
            <div class="content-holder tc">
                <h1 class="tc">Create Question</h1>
                <span class="title">Question Description:</span>
                <br />
                <textarea></textarea>
                <br />
                <span class="title">Answers:</span>
                <br />
                <input type="text"><input type="radio">
                <br />
                <input type="text"><input type="radio">
                <br />
                <input type="text"><input type="radio" selected>
                <br />
                <span class="glyphicon glyphicon-plus"></span>
                <br />
                <br />
                <a class="btn btn-cg white">Create</a>
            </div>
            <div class="content-holder tc">
                <h1 class="tc">Add Video</h1>
                <span class="title">Video Description:</span>
                <br />
                <textarea></textarea>
                <br />
                <span class="title">Video URL:</span>
                <br />
                <input type="text">
                <br />
                <br />
                <span class="title">Preview:</span>
                <br />
                <iframe width="640" height="480" src="https://www.youtube-nocookie.com/embed/7DjsD7Hcd9U?wmode=transparent" frameborder="0" allowfullscreen></iframe>
                <br />
                <br />
                <a class="btn btn-cg white">Add</a>
            </div>
            <div class="content-holder tc">
                <h1 class="tc">Add Lecture</h1>
                <span class="title">Lecture Description:</span>
                <br />
                <textarea></textarea>
                <br />
                <span class="title">Lecture URL:</span>
                <br />
                <input type="text">
                <br />
                <br />
                <a class="btn btn-cg white">Add</a>
            </div>
        </div>
    </div>

    <%@include file="/includes/footer.html"%>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>