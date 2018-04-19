<%@page import="util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<meta charset="utf-8"/>
	<link href="resources/images/favicon.png" rel="shortcut icon" />
	<link href="resources/css/smartguide.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="resources/js/jquery-1.5.2.min.js"></script>	
	<script type="text/javascript" src="resources/js/scriptbreaker-multiple-accordion-1.js"></script>			
	<script src="resources/js/smartguide.js" type="text/javascript"></script>	
	<script>
	  $(document).ready(function() {	
			$('#banner').bjqs({
			  'animation' : 'fade',
			  'width' : "100%",
			  'height' : 300,
			  'animationDuration' :1000
			});	
			$(".topnav").accordion({
				accordion:false,
				speed: 500,
				closedSign: '+',
				openedSign: '-'
			});	
	  });
	</script>
</head>
<body>		
	<header class="headerSection">
		<div class="message" align="center">
			<s:property value="#session.message"/>
			<% ActionContext.getContext().getSession().remove(Constants.MESSAGE); %>
		</div>
		<div class="headerLeft">
			<hgroup>
				<img src="resources/images/smartGuidelogo.png" alt="Smart Guide" class="logoImg"/>
			</hgroup>
		</div>
		<div class="headerRight">
<!-- 			<a href="registrationpage.action" class="regBtn">Registration</a> -->
			<a href="#" class="loginBtn">Login</a>
			<div class="LoginDiv">
				<header>
					<a class="hideSlide" href="#"></a>
					<form action="adminLogin" METHOD="post">
					<input type="text" placeholder="Username" class="usertext" name="userName" id="textBox"/>
					<input type="password" placeholder="Password" class="userPass" name="password" id="textBox"/>
					<section>
						<input type="submit" class="submitBtn" value="Submit"/>
					</section>
					</form>
				</header>
			</div>
		</div>
	</header>
</body>