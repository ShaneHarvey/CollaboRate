/**
 * question.js contains all javascript required for answering questions.
 * Requires jquery and jquery-ui
 */

$(function() {

	// Add click lister to questions on view question page
	$('#question-list li').click(function() {
		$('#question-list span').each(function() {
			$(this).removeClass('selected-answer');
		});
		$(this).children('span').addClass('selected-answer');
	});

	// Add click listener to button on view question page
	$('#btn_submitAnswer').click(
			function() {
				var index = -1;
				var counter = 0;
				// Find the user's answer
				$('#question-list span').each(function() {
					if ($(this).hasClass('selected-answer'))
						index = counter;
					counter++;
				});
				// If user didn't answer, return
				if (index === -1) {
					$('#question-list').effect('shake');
					return;
				}
				
				// Remove click listeners from answers
				$('#question-list li').each(function() {
					$(this).unbind();
				});
				
				$('#btn_submitAnswer').hide();
				$('#answerLoading').show()
				// Try to answer the question
				$.ajax({
					type : 'POST',
					data : 'qid=' + $(this).attr('qid') + '&answer=' + index
							+ '&action=answerquestion',
					url : 'question',
					success : function(data) {
						$('#answerLoading').hide();
						var answer = JSON.parse(data).answer;
						if (answer === index) {
							$('#correctAnswer').show();
							$('#question-list span').each(function() {
								if ($(this).hasClass('selected-answer')) {
									$(this).removeClass('selected-answer');
									$(this).addClass('correct-answer');
								}
							});
						} else {
							$('#incorrectAnswer').show();
							var counter = 0;
							$('#question-list span').each(function() {
								if ($(this).hasClass('selected-answer')) {
									$(this).removeClass('selected-answer');
									$(this).addClass('incorrect-answer');
								}
								if (counter == answer)
									$(this).addClass('correct-answer');
								counter++;
							});
						}
					},
					error : function(data) {
						// Couldn't connect to the server
						$('#answerLoading').hide();
						$('#question-list').effect('shake');
						console.log(data);
					}
				});
			});
	$('#feedback').FeedbackDisplay();
});