<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Educator</title>
<%@include file="/includes/css.html" %>
</head>
<body>

    <jsp:include page="/includes/header.jsp"></jsp:include>
    
    <div class="body">
        <div class="body-center-div">
            <div class="bread-crumbs"><a href="/home">Home</a> / <a href="#">Science</a> /  <a href="#">Chemistry</a> / <a href="#"> Chemical Bonding </a> / Add Content </div>
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
<%@include file="/includes/js.html" %>
</html>