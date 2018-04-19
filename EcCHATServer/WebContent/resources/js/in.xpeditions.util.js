(function($) {

    var XpAjaxLink = function(element, options){
		//Defaults are below
		
    	var settings = $.extend({}, $.fn.xpAjaxLink.defaults, options);
		
		var vars = {
            thisSectionMayUseLater : 0
        };
		element = $(element);
		element.click(function(){
			var url = $(this).attr('href');
			var targetSection = $('#' + settings.target);
			var overlay = Xpeditions.createOverlaySection (targetSection);
			$.ajax({
				  url: url,
				  context: document.body,
				  success: function(data){
					  targetSection.html(data);
					  overlay.remove();
			  	  }
			});
			return false;
		});
	};
    $.fn.xpAjaxLink = function(options) {
    
        return this.each(function(){
            var element = $(this);
            // Return early if this element already has a plugin instance
            if (element.data('xpAjaxLink')) return;
            // Pass options to plugin constructor
            var xpAjaxLink = new XpAjaxLink(this, options);
            // Store plugin object in this element's data
            element.data('xpAjaxLink', xpAjaxLink);
        });

	};
	
	//Default settings
	$.fn.xpAjaxLink.defaults = {
			target: ""
	};
	
	$.fn._reverse = [].reverse;
	
})(jQuery);
(function($) {
	
	var XpAddImageAfter = function(element, options){
		//Defaults are below
		
		var settings = $.extend({}, $.fn.xpAddImageAfter.defaults, options);
		
		var vars = {
				thisSectionMayUseLater : 0
		};
		
		element = $(element);
		var removeimg = $( document.createElement('img') );
		removeimg.attr("src",settings.removeimgsrc);
		removeimg.addClass(settings.removeimgclass);
		element.addClass('removerowimage');
		removeimg.insertAfter(element);
		
		var img = $( document.createElement('img') );
		img.attr("src",settings.addimgsrc);
		img.addClass(settings.addimgclass);
		element.addClass('newrowimage');
		img.insertAfter(element);
	};
	$.fn.xpAddImageAfter = function(options) {
		
		return this.each(function(){
			var element = $(this);
			// Return early if this element already has a plugin instance
			if (element.data('xpAddImageAfter')) return;
			// Pass options to plugin constructor
			var xpAddImageAfter = new XpAddImageAfter(this, options);
			// Store plugin object in this element's data
			element.data('xpAddImageAfter', xpAddImageAfter);
		});
		
	};
	
	//Default settings
	$.fn.xpAddImageAfter.defaults = {
			target: ""
	};
	
	$.fn._reverse = [].reverse;
	
})(jQuery);

jQuery.covertToAjaxFileUpload = function(cForm, options){

	cForm = $(cForm);
	var iFrame = $(createFileUploadIframe(cForm.attr('id'), "#"));
		iFrame.bind('load', {dataTableId:options.dataTableId, dataTableUrl:options.dataTableUrl},options['successhandler']);
		var iFrameId = iFrame.attr('id');
		cForm.attr("target", iFrameId);
		function createFileUploadIframe(id, uri){
			//create frame
            var frameId = 'jUploadFrame' + id;
            
            if(window.ActiveXObject) {
                var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
                if(typeof uri== 'boolean'){
                    io.src = 'javascript:false';
                }
                else if(typeof uri== 'string'){
                    io.src = uri;
                }
            }
            else {
                var io = document.createElement('iframe');
                io.id = frameId;
                io.name = frameId;
            }
            io.style.position = 'absolute';
            io.style.top = '-1000px';
            io.style.left = '-1000px';

            document.body.appendChild(io);

            return io;		
    }
};
Xpeditions = {};
Xpeditions.createOverlaySection = function(targetSection){
	var height = targetSection.height();
	var width = targetSection.width();
	
	height = 300;
	width = 300;
	var targetPostion  = targetSection.position();
	var left = 0;
	var top = 0;
	if(targetPostion != undefined){
		left = targetPostion.left;
		top = targetPostion.top;
	}
	var overlaySection = $("<div></div>");
	overlaySection.height(height).width(width).css( { 
		position: 'absolute',
		'text-align': 'center',
		'vertical-align': 'center',
		'margin-top': height/2,
		zIndex: 3,
		left: left, 
		top: top
	} ).html();//("<img src='../StateBank/resources/images/loading.gif'/>");
	$(document.body).append(overlaySection);
	return overlaySection;
};

Xpeditions.validateField = function(input){
	var cValue = input.val();
	if(input.hasClass('required')){
		if($.trim(cValue) == ""){
			return "*";
		}
	}
	if(input.hasClass('ipaddress')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return "Please enter a proper ip address (1-255).(1-255).(1-255).(1-255).";
			}
		}
	}
	if(input.hasClass('macid')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^([0-9A-Fa-f]{2}[:]){5}[0-9A-Fa-f]{2}$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return "Please enter a proper macid(00:C0:02:7B:7F:98).";
			}
		}
	}
	if(input.hasClass('makenumber')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^[0-999]+$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return "Please enter a proper number (0-999).";
			}else{ if(cValue < 0 || cValue > 999){
				return "Please enter a proper number (0-999).";
			}
			}
		}
	}
	if(input.hasClass('number')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^[0-9]+$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return "Please enter a proper number here.";
			}
		}
		var minValue = input.attr('minvalue');
		if(minValue != undefined){
			minValue = 1 * minValue;
			cNumber = 1 * cValue;
			if(cNumber < minValue){
				return "Please enter a value, greater than or equal to " + minValue;
			}
		}
		var maxValue = input.attr('maxvalue');
		if(maxValue != undefined){
			maxValue = 1 * maxValue;
			cNumber = 1 * cValue;
			if(cNumber > maxValue){
				return "Please enter a value, less than or equal to " + maxValue;
			}
		}
		var notequal = input.attr('equalTo');
		if(notequal != undefined){
			cNumber = cValue.length;
			if(cNumber != notequal){
				return "Please enter a value equal to " + notequal;
			}
		}
	}
	if(input.hasClass('nameString')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^[a-zA-z]+$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return "Please enter a proper input here.";
			}
		}
	}
	
	if(input.hasClass('double')){
		if($.trim(cValue) != ""){
			var  doublePattern = new RegExp("^[0-9]*[\\.]?[0-9]+$","gi");
			if(!doublePattern.test($.trim(cValue))){
				return "Please enter a valid Amount";
			}
		}
	}
	if(input.hasClass('phonenumber')){
		if($.trim(cValue) != ""){
			var  phoneNumberPattern = new RegExp("^[\\+]{0,1}[0-9]{6,12}$","gi");
			if(!phoneNumberPattern.test($.trim(cValue))){
				return "Please enter a valid phone number.";
			} 
		}
	}
	if(input.hasClass('mobilenumber')){
		if($.trim(cValue) != ""){
			var  mobileNumberPattern = new RegExp("^([\\+][9][1]){0,1}[0-9]{10}$","gi");
			if(!mobileNumberPattern.test($.trim(cValue))){
				return "Please enter a valid mobile number.";
			} 
		}
	}
	if(input.hasClass('email')){
		if($.trim(cValue) != ""){
			var  emailPattern = new RegExp(/^[+a-zA-Z0-9._-]+@([a-zA-Z0-9.-]{2,100})+\.[a-zA-Z]{2,4}$/i);
			if(!emailPattern.test($.trim(cValue))){
				return "Please enter a valid email.";
			} 
		}
	}
	if(input.hasClass('valuerange')){
		if($.trim(cValue) != ""){
			var  numberPattern = new RegExp("^([0-9]{1,2})$","gi");
			if(!numberPattern.test($.trim(cValue))){
				return "two digit only";
			}
		}
	}
	if(input.hasClass('crosscheck')){
		if($.trim(cValue) != ""){
			var nowValue = $.trim(cValue);
			var otherInputs = $("." + input.attr('commonclass'));
			var isSame = true;
			otherInputs.each(function(){
				if(nowValue != $(this).val()){
					isSame = false;
				}
			});
			if(!isSame){
				return "Mismatched Values";
			}
		}
	}
	
	if(input.hasClass('passwordLength')){
		var greaterThan = input.attr('greaterThan');
		if(greaterThan != undefined){
			cNumber = cValue.length;
			if(cNumber < greaterThan){
				return "Password should be atleast " + greaterThan + " char";
			}
		}
	}
	
	return true;
};

Xpeditions.validateForm = function(cForm){
	var inputs = cForm.find('input');
	$.merge(inputs, cForm.find('select'));
	var isEveryThingOk = true;
	inputs.each(function(index, inp){
		var input = $(inp);
		var resultOfValidation = Xpeditions.validateField(input);
		
		if(resultOfValidation != true){
			var errorSpan = input.nextAll('.errormessageclass');
			if(errorSpan.length < 1){
//				$("<span class='errormessageclass'></span>").insertAfter(input);
				input.parent().append("<span class='errormessageclass'></span>");
				errorSpan = input.nextAll('.errormessageclass');
			}
			input.addClass('errorclass');
			errorSpan.show();
			errorSpan.html(resultOfValidation);
			isEveryThingOk = false;
		} else {
			input.removeClass('errorclass');
			input.nextAll('.errormessageclass').hide();
		}
	});
	if(!isEveryThingOk){
		cForm.find('input[type=submit]').attr('disabled', 'disabled');
	}else{
		cForm.find('input[type=submit]').removeAttr('disabled');
	}
	return isEveryThingOk;
};

$(document).ready(function() {
	$(".duplicatemyrow").live('click', function(){
		var parentRow = $(this).parent().parent();
		var clonedRow = parentRow.clone();
		clonedRow.insertAfter(parentRow);
	});
	
	$(".removemyrow").live('click', function(){
		if($('.removemyrow').size() < 2){
			return false;
		}
		var parentRow = $(this).parent().parent();
		parentRow.remove();
	});
	//Hover style for Menu Stars
	$(".ulmenu li").bind('mouseover', function(){
		$(this).addClass($(this).parent().attr('lihoverclass'));
	});
	$(".ulmenu li").bind('mouseout', function(){
		$(this).removeClass($(this).parent().attr('lihoverclass'));
	});
	$(".ulmenu li, .ulmenu li a").bind('click', function(){
		if($(this).is('a')){
			var clickClass = $(this).parent().attr('liclickedstyle');
			clickClass = $(this).parent().parent().attr('liclickedstyle');
			var nowLi = $(this).parent();
			if(clickClass != undefined){
				$(".ulmenu li").removeClass(clickClass);
				nowLi.addClass(clickClass);
			}
		}else {
			$(this).find('a').click();
		}
	});
	
	
	$('.removedrowbybutton').live('click', function(){
		var selectboxes =$(this).parent().parent();
		var selects = selectboxes.find('select');
		var dSelect = $(selects.get(0));
		var selectboxvalue =dSelect.val();
		var confirMessage="";
					if(selectboxvalue ==""){
						confirMessage = "There is no selected device in this row.";
					}
					if(confirMessage == ""){
						confirMessage = "Are you sure you want to remove this row ?";
					}
		$("#confirm").dialog("destroy");
		$("#confirm").html(confirMessage);
		$("#confirm").dialog({
			stackfix: true,
			modal: true,
			buttons: {
				Ok: function() {
					$(this).dialog('close');
					$("#confirm").dialog("destroy");
					var cRow = $(this).parent().parent();
					var className = cRow.attr("class");
					//$input = $('#mainTable tbody>tr:last').closest("tr").find("td > input:text");
				//	var selectboxes =$(this).parent().parent();
					var selects = selectboxes.find('select');
					var dSelect = $(selects.get(0));
					var selectboxvalue =dSelect.val();
					if(selectboxvalue ==""){
						return false;
					}
					if(selectboxvalue !=""){
						//var parentRow = $(this).parent().parent();
						selectboxes.remove();
					}
				},
				Cancel: function() {
					$(this).dialog('close');
					$("#confirm").dialog("destroy");
				}
					
			}
		
		});
		return false;
	});
	//remove enter in descriptions
	$(".Details").live('change', function(){
		var textArea = $('#Details').val().replace(/\r\n|\r|\n/g," ");
		$('#Details').val(textArea);
	});
	//Hover style for Menu Ends
	$('.ajaxchangelistener').live('blur', function(){
		var curField = $(this);
		var curVal = curField.val();
		var targetUrl = curField.attr('validateurl');
		var nameOfFieldName = curField.attr('name');
		var params = {nameOfFieldName: curVal};
	});
});
function showResultAlert(message) {
	$("#confirm").dialog("destroy");
	 $("#confirm").html(message);
		$("#confirm").dialog('open');
		$("#confirm" ).dialog({
			stackfix: true,
			modal: true,
			buttons: {
			Ok:function(){
				$(this).dialog('close');
				$("#confirm").dialog("destroy");
			}
		}
		});
	}
