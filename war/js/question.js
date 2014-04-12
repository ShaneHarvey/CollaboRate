/**
 *   question.js contains all javascript required for answering questions.
 *   Requires jquery and jquery-ui
 */

$(function(){
	
	// Add click lister to questions on view question page
	$('#question-list li').click(function(){
		$('#question-list span').each(function(){
			$(this).removeClass('selected-answer');
		});
		$(this).children('span').addClass('selected-answer');
	});
	
	// Add click listener to button on view question page
	$('#btn_submitAnswer').click(function(){
		var index = -1;
		var counter = 0;
		// Find the user's answer
		$('#question-list span').each(function(){
			if($(this).hasClass('selected-answer'))
				index = counter;
			counter++;
		});
		// If user didn't answer, return
		if(index === -1) {
			$('#question-list').effect('shake');
			return;
		}
    	//$('#answerLoading').show();
		// Try to answer the question
		$.ajax({
            type: 'POST',
            data: 'qid=' + $(this).attr('qid') + '&answer=' + index + '&action=answerquestion',
            url: 'question',
            success: function(data) {
            	//$('#answerLoading').hide();
            	// If successful Login, redirect to home else  
            	/*if(data === 'success') 
            		window.location.href = "home";
            	else {
            		$('#loginEmail').effect('shake');
            		$('#loginPassword').effect('shake');
            	} */  		
            },
            error: function(data) {
            	// Couldn't connect to the server
            	$('#answerLoading').hide();
            	$('#question-list').effect('shake');
            	console.log(data);
            }
        });
		
	});
	
	// Try rating this question
	$('.rating').click(function(){
		// Get rating
		var rating = $(this).attr('rating');
		// Highlight stars up to clicked star
		$('.rating').each(function(){
			if($(this).attr('rating') <= rating)
				$(this).addClass('yellow');
			else
			$(this).removeClass('yellow')
		});
		
		// Place rating in database
		$.ajax({
            type: 'POST',
            data: 'qid=' + $(this).attr('qid') + '&rating=' + rating + '&action=ratequestion',
            url: 'question'
		});
	});
	
	// Flag question
	$('#btn_flagQuestion').click(function(){
		// Place rating in database
		$.ajax({
            type: 'POST',
            data: 'qid=' + $(this).attr('qid') +  '&action=flagquestion',
            url: 'question'
		});
	});
	
});