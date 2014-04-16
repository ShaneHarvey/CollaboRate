/**
 * Requires jquery Plugin to handle rating and flagging content
 */

if (!jQuery)
	throw "You need jQuery to use feedbackDisplay";

// global id display counter
var fb_id = 0;

(function($) {

	$.fn.FeedbackDisplay = function() {
		// Get attributes from object
		var user = $(this).attr('loggedin');
		var postURL = $(this).attr('url');
		var contentID = $(this).attr('cid');
		var userRating = parseInt($(this).attr('ur'));
		var userFlagged = $(this).attr('uf');
		var globalRating = parseInt($(this).attr('gr'));

		// Create div and span to hold rating stuff
		var ratingDiv = $('<div></div>');
		$(this).append(ratingDiv);
		var ratingSpan = $('<span'
				+ (user === 'true' ? ' class="hoverHand"' : '') + '>'
				+ (user === 'true' ? 'Rate this content:' : 'Content Rating:')
				+ '</span>');
		ratingDiv.append(ratingSpan);
		
		// id of this feedback display
		var id = fb_id++;
		
		// Fill in rating spans
		if (user === 'true') {
			// Person is logged in, let them rate and flag content
			for (var x = 1; x <= 5; x++) {
				var span = $('<span class="rating glyphicon glyphicon-star" rating="'
						+ x + '" cid="' + contentID + '" fbid="' + id + '"></span>');
				span.click(function() {
					// Get rating
					var rating = $(this).attr('rating');

					fillRatings(rating, 'yellow', id);

					// Place rating in database
					$.ajax({
						type : 'POST',
						data : 'cid=' + $(this).attr('cid') + '&rating='
								+ rating + '&action=ratecontent',
						url : postURL
					});
				});
				ratingSpan.append(span);
			}
			if (userRating === -1)
				fillRatings(globalRating, 'red', id);
			else
				fillRatings(userRating, 'yellow', id);

			// Create a div to contain user flagging
			var flagDiv = $('<div></div>');
			$(this).append(flagDiv);
			var flagSpan = $('<span class="dark-red hoverHand'
					+ (userFlagged === "true" ? ' flagged' : '') + '" cid="'
					+ contentID + '"></span>');
			flagDiv.append(flagSpan);
			flagSpan.click(function() {
				var flagged = $(this).hasClass('flagged');
				if (flagged)
					$(this).removeClass('flagged');
				else
					$(this).addClass('flagged');
				$.ajax({
					type : 'POST',
					data : 'cid=' + $(this).attr('cid') + '&flag=' + !flagged
							+ '&action=flagcontent',
					url : postURL
				});
				$(this).empty();
				$(this).append(
						'<span class="glyphicon glyphicon-exclamation-sign"></span>'
								+ (!flagged ? 'unf' : 'F')
								+ 'lag this content</span>');
			});
			flagSpan
					.append('<span class="glyphicon glyphicon-exclamation-sign"></span>'
							+ (userFlagged === "true" ? 'unf' : 'F')
							+ 'lag this content</span>');
		} else {
			for (var x = 1; x <= 5; x++) {
				var span = $('<span class="rating glyphicon glyphicon-star"  rating="'
						+ x + '" fbid="' + id + '"></span>');
				ratingSpan.append(span);
			}
			fillRatings(globalRating, 'red', id);
		}
	};
})(jQuery);

function fillRatings(rating, color, id) {
	// Highlight stars up to clicked star
	$('.rating[fbid=' + id + ']').each(function() {
		$(this).removeClass('red');
		$(this).removeClass('yellow');
		if (parseInt($(this).attr('rating')) <= parseInt(rating))
			$(this).addClass(color);
	});
}