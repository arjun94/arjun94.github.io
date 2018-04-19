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
			$.ajax({
				  url: url,
				  context: document.body,
				  success: function(data){
				    $('#' + settings.target).html(data);
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

$(document).ready(function() {
	$('.ajaxlink').live("mouseover", function(){
		$(this).xpAjaxLink({target: 'rightSide'});
		return false;
	});
	
	$('.ajaxlinkContent').live("mouseover",function(){
		$(this).xpAjaxLink({target: 'innerContentMiddle'});
		return false;
	});
	
	$('.ajaxformsubmit').live('submit', function() {
		 var params = $(this).serialize();
		 $.post($(this).attr('action'), params, function(data){
			 $('#rightSide').html(data);
		 });	 
		 return false;
	});
	
	$('.ajaxonchange').live('click', function() {
		 var params = $(this).closest('form').serialize();
		 $.post($(this).attr('action'), params, function(data){
			 alert(data);
			 $('#innerRight').html(data);
		 });
		 return false;
	});
	
	$('.unsubscribe').live('submit', function() {
		var params = $(this).serialize();
		 $.post($(this).attr('action'), params, function(data){
			 $('#innerContentMiddle').html(data);
		 });
		 return false;
	});
	
	$('.candidateformsubmit').live('submit', function() {
		 var params = $(this).serialize();
		 $.post($(this).attr('action'), params, function(data){
			 $('.fancybox-inner').html(data);
			 var msg = $(data).find('.message').text();
			 if(msg.indexOf("Failure") == -1){
				 $.fancybox.close();
				 $('.message').text(msg);
			 }
		 });
		 return false;
	});
	
	jQuery('form').live('change', function() {
		Xpeditions.validateForm(jQuery(this));
	});
	
	
});


