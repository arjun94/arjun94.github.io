<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>EcCHAT :: Simply share your Thoughts</title>		
	</head>
	<body>
		<header class="mainContainer">
			<div class="bodyMargin">
				<s:include value="header.jsp" />
				<div id="slidersection">
					<div id="slider">
						<div id="banner">
							<!-- start Basic Jquery Slider -->
							<ul class="bjqs">
							 <!--  <li><img src="resources/images/slide1.png"></li> -->
							  <li><img src="resources/images/slide2.png"></li>
							  <li><img src="resources/images/slide3.png"></li>							  
							  <li><img src="resources/images/slide4.png"></li>							  
							</ul>
							<!-- end Basic jQuery Slider -->
						</div>
					</div>
				</div>
				<header class="captionBottom">
					<h2 class="indexCaption">
						Android Application that shares your thoughts
					</h2>
				</header>
				<header>
					<div class="bodyMargin">
						<header>
							<div class="ThumbImgContainer">
								<img src="resources/images/highperformance.png" alt="smartGuide" class="imgThumb"/>
								<h3 class="imgDescription">User friendly </h3>
							</div>
							<div class="ThumbImgContainerMiddle">
								<img src="resources/images/mapping.png" alt="smartGuide" class="imgThumbMiddle"/>
								<h3 class="imgDescription">Native focused</h3>
							</div>
							<div class="ThumbImgContainerRight">
								<img src="resources/images/easy.png" alt="smartGuide" class="imgThumb"/>
								<h3 class="imgDescription">Easy to use</h3>
							</div>
						</header>
					</div>
				</header>
				<footer>
					<s:include value="footer.jsp" />
				</footer>
			</div>
		</header>
	</body>
</html>