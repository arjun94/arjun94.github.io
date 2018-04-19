<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<link href="resources/images/favicon.png" rel="shortcut icon" />
	<head>
		<title>EcCHAT :: Simply share your Thoughts</title>		
	</head>
	<body>
		<header class="mainContainer" style="background-color: #E8E8E8 ;">
			<div class="bodyMargin">
				<s:include value="header.jsp" />
				<div id="innerContainer">
					<div id="leftSide">
						<ul class="topnav">
							<li><a href="#">    View User</a>
								<ul>
									<!-- <li><a href="listForApprovingUser.action" class="ajaxlink">View new users</a></li> -->
									<li><a href="listAllUsers.action" class="ajaxlink">View all users</a></li>
								</ul>
							</li>
						 <li><a href="#">      Feedback</a>
								<ul>
								<li><a href="viewFeedback.action" class="ajaxlink">View</a></li>
								</ul>
							</li>
							<li><a href="#">   Settings</a>
								<ul>
								<li><a href="changePasswordLink.action" class="ajaxlink">Change Password</a></li>
								</ul>
							</li>
							<!-- <li><a href="#">Ranking</a>
								<ul>
								<li><a href="listCustomerRanking.action" class="ajaxlink">Customer Ranking</a></li>
								<li><a href="listLocationRanking.action" class="ajaxlink">Location Ranking</a></li>
								</ul>
							</li> -->
						</ul>
					</div>
					<div id="rightSide" class="innerSideBox rightSide" align="center" style="overflow:auto"; "background-color: #E8E8E8 ;">
					<img src="resources/images/newImg4.gif" width="100%"/>
					
					<marquee  onmouseover="this.stop()" onmouseout="this.start()"
						scrolldelay="50" behavior="alternate">
					
					<img src="resources/images/newImg4.gif" width="300" height="152"/>
					<img src="resources/images/newImg5.gif" width="300" height="155"/>
					<img src="resources/images/newImg2.jpg" width="300" height="155"/>
					<img src="resources/images/img12.jpg" width="300" height="158"/>
					
					</marquee>
						
					</div>
				</div>
				<footer>
					<s:include value="footer.jsp" />
				</footer>
			</div>
		</header>
	</body>
</html>