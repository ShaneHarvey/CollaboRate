/**
 * subtopic.js used for scripting on subtopic page
 */

$(function() {

	$('.fbHover').each(function(){
		$(this).parent().find('.feedback').each(function(){
			$(this).FeedbackDisplay();
		});
		
		$(this).mouseover(function(){
			var holder = $(this);
		    $(this).addClass('hover');
		    setTimeout(function(){
				if(holder.hasClass('hover')){
				    var offset = holder.parent().offset();
				    var width = holder.parent().outerWidth();
				    holder.parent().find('.feedback-tooltip').each(function(){
				    	$(this).css({top:offset.top - 565, left:offset.left - 325}).fadeIn();
				    	$(this).mouseover(function(){
				    		holder.mouseover();
				    	});
				    	$(this).mouseout(function(){
				    		holder.mouseout();
				    	});
				    });
				}
			}, 200);
		});
		$(this).mouseout(function(){
			var holder = $(this);
			$(this).removeClass('hover');
			setTimeout(function(){
				if(!holder.hasClass('hover')){
					holder.parent().find('.feedback-tooltip').each(function(){
				    	$(this).fadeOut();
				    });
				}
			}, 200);
		});
	});
});