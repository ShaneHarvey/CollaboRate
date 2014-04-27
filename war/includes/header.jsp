<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/function.tld" prefix="fn"%>
<!-- Navbar -->
<div class="navbar navbar-fixed-top" role="navigation">
	<div class="navbar inner">
		<div class="container">
			<!-- This will collapse to dropdown menu if not enough room to display list -->
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a class="brand-name" href="/home">CollaboRate</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Learn<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<c:forEach items="${fn:getAllSubjects()}" var="sub">
								<li><a href="/subject?sid=${sub.keyAsString}">${sub.title}</a></li>
							</c:forEach>
							<li class="divider"></li>
							<li class="dropdown-header"><a href="/RequestSubject">Request a
									Subject</a></li>
						</ul></li>
					<c:if test="${account != null}">
						<li><a href="/addcontent">Contribute</a></li>
					</c:if>
					<li><a href="/discuss">Discuss</a></li>
				</ul>
				<!-- Right side of nav bar -->
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test="${account != null}">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">${account.email}<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="/account">My Account</a></li>
									<li><a href="/UserContent">My Contributions</a></li>
									<li><a href="/logout">Log Out</a></li>
								</ul></li>
						</c:when>
						<c:otherwise>
							<li><a href="/login">Log In</a></li>
							<li><a href="/signup">Sign Up</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</div>
</div>