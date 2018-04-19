<%@page import="util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<meta charset="utf-8"/>
	<link href="../../resources/images/favicon.png" rel="shortcut icon" />
	
	<script type="text/javascript" src="resources/js/jquery-1.5.2.min.js"></script>	
	<script type="text/javascript" src="resources/js/scriptbreaker-multiple-accordion-1.js"></script>			
	<script type="text/javascript" src="resources/js/smartguide.js"></script>	
<%-- 	<script type="text/javascript" src="../../resources/js/jquery-ui-1.10.3.custom.min.js"></script> --%>
	<script type="text/javascript" src="resources/css/jquery-ui-1.8.13.custom.min.js" ></script>
	<script type="text/javascript" src="resources/js/in.xpeditions.js"></script>			
	<script type="text/javascript" src="resources/js/in.xpeditions.util.js"></script>	
	<script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>

	<link rel="stylesheet" type="text/css" href="resources/css/jquery-ui-1.8.4.custom.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/demo_table_jui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/smartguide.css" />
	
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
				<img src="resources/images/smartGuidelogo.png" alt="Smart Chart" class="logoImg" />
			</hgroup>
		</div>
		<div class="headerRight">
			<a href="logout.action" class="headerBtn">Logout</a>
		</div>
	</header>
</body>