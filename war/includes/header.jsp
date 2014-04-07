<%@page import="constants.Keys"%>
<%@page import="account.Account"%>
<%@page import="material.Subject" %>
<%
	response.setHeader("Cache-Control","no-store");
	// Try to get account from session
    Account acc = (Account)session.getAttribute(Keys.ACCOUNT);
%>
<!-- Navbar -->
<div class="navbar navbar-fixed-top" role="navigation">
    <div class="navbar inner">
        <div class="container">
            <!-- This will collapse to dropdown menu if not enough room to display list -->
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a class="brand-name" href="/home">Educator</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Learn<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <%
                                 for(Subject s: Subject.getSubjects())  {
                            %>
                                    <li><a href="/subject?<%=Keys.SUBJECT_KEY + "=" + s.getSubjectKeyString()%>"><%=s.getTitle()%></a></li>
                            <%
                                 } 
                            %>
                            <li class="divider"></li>
                            <li class="dropdown-header"><a href="#">Request a Subject</a></li>
                        </ul>
                    </li>
                    <%
                    	// If this person is a user, display add content
                        if(acc != null) {
                    %>
                            <li><a href="/addcontent">Contribute</a></li>
                    <% 
                        } 
                    %>
                    <li><a href="/discussion-board.jsp">Discuss</a></li>
                </ul>
                <!-- Right side of nav bar -->
                <ul class="nav navbar-nav navbar-right">
                    <% if(acc != null) {%>
	                    <li class="dropdown">
	                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=acc.getEmail()%> <span class="caret"></span></a>
	                        <ul class="dropdown-menu">
	                            <li><a href="/account">My Account</a></li>
	                            <li><a href="/logout">Log Out</a></li>
	                        </ul>
	                    </li>
                    <%
                        } else { 
                    %>
	                    <li><a href="/login">Log In</a></li>
	                    <li><a href="/signup">Sign Up</a></li>
                    <%
                        } 
                    %>
                </ul>
            </div>
        </div>
    </div>
</div>