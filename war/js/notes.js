/**
 * notes.js used for display hover on notes page
 */

$(function() {
	
	$('.fbHover').each(function(){
		feedbackify($(this), 585, 325);
	});
	
	$('.fbnHover').each(function(){
		feedbackify($(this), 180, 125);
	});
});

function feedbackify(div, toffset, loffset) {
	div.parent().find('.feedback').each(function(){
		$(this).FeedbackDisplay();
	});
	
	div.mouseover(function(){
		var holder = $(this);
	    $(this).addClass('hover');
	    setTimeout(function(){
			if(holder.hasClass('hover')){
			    var offset = holder.parent().offset();
			    var width = holder.parent().outerWidth();
			    holder.parent().find('.feedback-tooltip').each(function(){
			    	$(this).css({top:offset.top - toffset, left:offset.left - loffset}).fadeIn();
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
	div.mouseout(function(){
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
}