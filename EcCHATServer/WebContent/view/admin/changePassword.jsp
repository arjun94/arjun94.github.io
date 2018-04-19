<%@page import="util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
	
		$('.oldPassword').blur(function() {
			var query = $('.oldPassword').val();
			var id = $('#userId').val();
			var result="";
			$.ajax({
				url : 'checkPassword',
				type : "POST",
				data : 'iputFromPage=' + query+"-"+id,
				dataType : 'json',
				success : function(data) {
					result=data.result;
					alert(result);
				},
				error : function(x, status, error) {
					 $('.oldPassword').val("");
					 $('.password').val("");
					 //$('.oldPassword').addattr('class','required');
					alert("No such password exists..");
				}
			});
		});

		var cForm = $("#changePassword");
		Xpeditions.validateForm(cForm);
	});
</script>

<article class="loginPop minPadding">
	<div class="marginCenter">
		<article>
			<h2 class="headWhite">Change password</h2>
		</article>
		<span style="color: red;"><s:property
				value="#session.message" /></span>
		<%
			ActionContext.getContext().getSession().remove(Constants.MESSAGE);
		%>
	</div>
</article>

<%-- <section class="minPadding indexBottom"> --%>
<div class="marginCenter" align="center"
	style="padding-top: 84px; padding--left: 206px;">

	<!-- <article> -->
	<div class="">
		<form id="changePassword" class="registrationForm ajaxformsubmit "
			method="post" action="changePassword">
			<table align="left" style="width: 100%">
				<tr>
					<td class="td"><span class="label">Old password</span></td>
					<td style="width: 50%;"><input id="textBox" type="password"
						name="oldPassword" class="required oldPassword" /></td>
				</tr>
				<tr>
					<td class="td"><span class="label">New password</span></td>
					<td><input id="textBox" type="password" name="password"
						class="password required confirmPassword crossckeck" /></td>
				</tr>
				<tr>
					<td class="td"><span class="label" >Confirm password</span></td>
					<td>
					<input id="textBox" type="password"
						class="password  required  crosscheck "  commonclass="confirmPassword"/></td>
				</tr>
				<tr>
					<td><input id="userId" type="hidden" name="id"
						value="<s:property value='1'/>"></td>
					<td><input type="submit" value="Update"></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- </article> -->
</div>
