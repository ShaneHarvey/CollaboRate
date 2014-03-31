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
            <div class="bread-crumbs"><a href="/home">Home</a> / <a href="subject.jsp">Chemistry</a> / <a href="topic.jsp"> Chemical Bonding </a> / Question #37638 </div>
            <div class="questions-remaining">Answer <b>3</b> more questions in this topic <br /> to prove you have learned this top.</div>
            <h1 class="tc">The electrons in a nonpolar covalent bond are:</h1>

            <div class="question-answers">
                <ol>
                    <li>Gained</li>
                    <li>Lost</li>
                    <li><span class="selected-answer">Shared Equally</span></li>
                    <li>Shared Unequally</li>
                </ol>
            </div>
            <div class="tc">
               <a class="btn btn-cg">Submit</a>
            </div>
            <div class="feedback">
                <div>
                    <span>Rate this question: <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star yellow"></span> <span class="glyphicon glyphicon-star"></span> <span class="glyphicon glyphicon-star"></span> </span>
                </div>
                <div>
                    <span class="dark-red"><span class="glyphicon glyphicon-exclamation-sign"></span>Flag this question</span>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/includes/footer.html"%>

</body>
<!-- Scripts at end of page to speed up page loading -->
<script src="js/jquery-2.1.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>