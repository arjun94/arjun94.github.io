<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#example').dataTable({
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"aoColumns": [
							{ "sType": "numeric" },
							null,
							null,
							null
						]									
		});
	});
</script>
</head>
<body>
<article class="loginPop minPadding">
	<div class="marginCenter">
		<article>
			<h2 class="headWhite">User List </h2>
		</article>
		<span style="color: red;"><s:property
				value="#session.message" /></span>
		<%
			ActionContext.getContext().getSession().remove(Constants.MESSAGE);
		%>
	</div>
</article>
	<div class="datatablediv">	
	
	<div class="innerdatatablediv" style="margin-top:50px;margin-left: 10px;margin-right: 10px; color: black;">
        <table class="display" id="example" style="overflow: hidden; text-align: center;">
			<thead>
		          <tr>
		          	<th >Sl. No</th>         	
					<th >Name</th>
					<th >Email</th>
					<th >Mobile</th>
	            </tr>
	        </thead>
            <%
            	int rowcount = 1;
            %>
            <%-- <s:if test="role!=0"> --%>
            <s:iterator value="users" var="user">
				<tr class="<%= rowcount%2 == 0 ? "treven" : "trodd" %>">
					<td height="25" width="10" class="drw10"><%=rowcount %> </td>
					
					<td ><span class="drw10"><s:property value="name"/></span></td>
					<td ><span class="drw10"><s:property value="email"/></span></td>
					<td ><span class="drw10"><s:property value="mobile"/></span></td>
				</tr>
				<%rowcount++; %>
			</s:iterator>
					<%-- </s:if>			 --%>				
		</table>
	</div>
</div>