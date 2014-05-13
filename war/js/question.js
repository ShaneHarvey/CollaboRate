/**
 * question.js contains all javascript required for answering questions and
 * testing. Requires jquery and jquery-ui
 */

$(function() {
	
	// Can user click button
	var canClick = true;

	// Add click lister to questions on view question page
	$('#question-list li').click(function() {
		$('#question-list li').each(function() {
			$(this).removeClass('selected-answer');
		});
		$(this).addClass('selected-answer');
	});

	// Add click listener to button on view question page
	$('#btn_submitAnswer').click(function() {
				var index = -1;
				var counter = 0;
				// Find the user's answer
				$('#question-list li').each(function() {
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
						var obj = JSON.parse(data);
						$('#btn_rques').attr('href', 'question?qid=' + obj.rqkey);
						$('#btn_rques').show();
						var answer = obj.answer;
						if (answer === index) {
							$('#correctAnswer').show();
							$('#question-list li').each(function() {
								if ($(this).hasClass('selected-answer')) {
									$(this).removeClass('selected-answer');
									$(this).addClass('correct-answer');
								}
							});
						} else {
							$('#incorrectAnswer').show();
							var counter = 0;
							$('#question-list li').each(function() {
								if ($(this).hasClass('selected-answer')) {
									$(this).removeClass('selected-answer');
									$(this).addClass('incorrect-answer');
								}
								if (counter == answer)
									$(this).addClass('correct-answer');
								counter++;
							});
							$('#answerExplanation').append(obj.explanation);
							$('#answerExplanation').show();
							$('#explanation').show();
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
	
	// Add click listener to button on view question page
	$('#btn_testSubmitAnswer').click( function() {
				// Prevent user from clicking to submit question multiple times
				if(!canClick)
					return;
				var index = -1;
				var counter = 0;
				// Find the user's answer
				$('#question-list li').each(function() {
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
				canClick = false;
				// Try to answer the question
				$.ajax({
					type : 'POST',
					data : 'qid=' + $(this).attr('qid') + '&answer=' + index
							+ '&action=answerquestion',
					url : 'test',
					success : function(data) {
						$('#answerLoading').hide();
						var response = JSON.parse(data);
						if(typeof response.testResult === 'undefined'){
							canClick = true;
							$('#progress-bar').attr('style', 'width: ' + response.pct + '%');
							$('#progress-bar').text(response.pct + '%');
							// Load the next question
							$('#question-title').html(response.nextQuestion.title);
							$('#question-list').empty();
							
							for(var i =0; i< response.nextQuestion.answerChoices.length; i++){
								$('#question-list').append('<li>' + response.nextQuestion.answerChoices[i] + '</li>');
							}
							// Add click lister to questions on view question page
							$('#question-list li').click(function() {
								$('#question-list li').each(function() {
									$(this).removeClass('selected-answer');
								});
								$(this).addClass('selected-answer');
							});
							$('#btn_testSubmitAnswer').attr('qid', response.nextQuestion.qid);
							$('#correctAnswer').hide();
							$('#incorrectAnswer').hide();
							// Update feedback display for new question
							$('#feedback').attr('cid', response.nextQuestion.qid); // Content id
							$('#feedback').attr('ur', response.userRating); // User rating
							$('#feedback').attr('uf', response.flagged); // User flagged?
							$('#feedback').attr('gr', response.nextQuestion.globalRating); // Global Rating
							$('#feedback').FeedbackDisplay();
						} else if(response.testResult ){
							// Test passed
							$('#testPassed').show();
							$('#progress-bar').attr('style', 'width: 100%');
							$('#progress-bar').text('100%');
						} else {
							// Test failed
							$('#testFailed').show();
						}
					},
					error : function(data) {
						canClick = true;
						// Couldn't connect to the server
						$('#answerLoading').hide();
						$('#question-list').effect('shake');
						console.log(data);
					}
				});
			});
	
	$('#feedback').FeedbackDisplay();
});