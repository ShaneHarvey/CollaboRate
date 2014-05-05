/**
 * discuss.js contains all javascript required to use the discussion board
 * requires jquery
 */

$(function() {

	$('#btn_createPost').click(
			function() {
				var postText = $('#postText').val();
				if (postText === '' || postText.length >= 500) {
					$('#postText').effect('shake');
					return;
				}
				var subID = $(this).attr('sid');
				var stID = $(this).attr('stid');
				// Fade in loading
				$('#postLoading').show();
				// Create post
				$.ajax({
					type : 'POST',
					data : 'content=' + encodeURIComponent(postText)
							+ '&subtopic=' + $(this).attr('stid')
							+ '&action=createpost',
					url : 'discuss',
					success : function(data) {
						$('#postLoading').hide();
						// If successful Login, redirect to home, if email is
						// used already shake email, else shake all
						if (data === 'success')
							window.location.href = '/discuss?sid=' + subID
									+ '&stid=' + stID;
						else
							$('#postText').effect('shake');
					},
					error : function(data) {
						// Couldn't connect to the server
						$('#postLoading').hide();
						$('#postText').effect('shake');
						console.log(data);
					}
				});
			});

	$('#btn_createComment').click(
			function() {
				var commentText = tinymce.get('commentText').getContent();
				if (!validTinyInput(commentText)) {
					$('#commentText').effect('shake');
					return;
				}
				var subID = $(this).attr('sid');
				var stID = $(this).attr('stid');
				var pID = $(this).attr('pid');
				// Fade in loading
				$('#commentLoading').show();
				// Create post
				$.ajax({
					type : 'POST',
					data : 'content=' + encodeURIComponent(commentText)
							+ '&post=' + $(this).attr('pid')
							+ '&action=createcomment',
					url : 'discuss',
					success : function(data) {
						$('#commentLoading').hide();
						// If successful Login, redirect to home, if email is
						// used already shake email, else shake all
						if (data === 'success')
							window.location.href = '/discuss?sid=' + subID
									+ '&stid=' + stID + '&pid=' + pID;
						else
							$('#postText').effect('shake');
					},
					error : function(data) {
						// Couldn't connect to the server
						$('#commentLoading').hide();
						$('#commentText').effect('shake');
						console.log(data);
					}
				});
			});

	$('#btn_deletePost').click(function() {

		// Get url to redirect to after deleted
		var redirect = $(this).attr('url');

		// Try to delete this post
		$.ajax({
			type : 'POST',
			data : 'post=' + $(this).attr('pid') + '&action=deletepost',
			url : 'discuss'
		}).done(function() {
			window.location.href = redirect;
		});
	});

	$('#btn_deleteComment').click(function() {
		
		// Get url to redirect to after deleted
		var redirect = $(this).attr('url');
		
		// Try to delete this comment
		$.ajax({
			type : 'POST',
			data : 'comment=' + $(this).attr('cid') + '&action=deletecomment',
			url : 'discuss'
		}).done(function() {
			window.location.href = redirect;
		});
	});
	
	// Create text text editors using tinymce
	tinyitize();
});