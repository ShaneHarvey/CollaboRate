<%@page import="settings.Settings"%>
<%@page import="account.Account"%>
<% 
    // Try to get account from session
    Account acc = (Account)session.getAttribute(Settings.ACCOUNT); 
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
                            <li class="dropdown-header">Science</li>
                            <li><a href="/subject.jsp">Chemistry</a></li>
                            <li><a href="#">Earth Science</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-header">Math</li>
                            <li><a href="#">Calculus I</a></li>
                            <li><a href="#">Calculus II</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-header"><a href="#">Request a Subject</a></li>
                        </ul>
                    </li>
                    <% 
                        // If this person is a user, display add content
                        if(acc != null && Settings.USER.equals(acc.getType())) {
                    %>
                            <li><a href="/add-content.jsp">Contribute</a></li>
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