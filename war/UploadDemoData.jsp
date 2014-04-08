<%-- 
    Document   : UploadDemoData
    Created on : Apr 7, 2014, 10:26:21 PM
    Author     : soumadipmukherjee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo Data Upload</title>
    </head>
    <body>
        <h1>Demo Data Upload Page</h1>
        <form action="DemoDataServlet" method="post">
            <br>
            Select what type of data you will upload:
            <select name="uploadDataType">
            	<option value="subject">Subject</option>
            	<option value="subtopic">Subtopic</option>
                <option value="video">Video</option>
                <option value="notes">Notes</option>
                <option value="question">Question</option>
            </select>
            <hr>
            <p>For Subtopic</p>
            <br>
            Subject Key: <input type="text" name="subjectKey">
            <hr>
            <p>For Videos, Notes, and Questions</p>
            <br>
            Subtopic Key:<input type="text" name="subtopicKey">
            <br>
            Account Key:<input type="text" name="accountKey">
            <hr>
            <p>In this text area provide the attributes for the entity selected seperated by a semicolon. ";"</p>
            <textarea style="width:1000px;height:500px" name="text"></textarea>
   			<br>	
            <input type="submit" value="Upload Data">
        </form>
    </body>
</html>
