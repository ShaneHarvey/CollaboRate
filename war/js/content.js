/**
 *  content.js contains all functionality related to adding new content   
 *  requires jquery and jquery ui
 */

$(function(){
	
	// Choose this subject
	$('#btn_chooseSubject').click(function(){
		window.location.href='/addcontent?sid=' + $('#subjectSelector').val();
	});
	
	// Choose this subtopic
	$('#btn_chooseSubtopic').click(function(){
		window.location.href='/addcontent?sid=' + $(this).attr('sid') + '&stid=' + $('#subtopicSelector').val();
	});
	
	// Add another answer to the list
	$('#btn_addAnswer').click(function(){
		// Only allow a maximum of 6 answers
		var count = 0;
		$('#answerChoiceTable tr').each(function(){
			count++;
		});
		if(count < 6) {
			$('#answerChoiceTable').append('<tr><td><textarea class="richText" rows="1"></textarea></td><td><input type="radio" name="answers" value="'+ count + '"/></td></tr>');
			tinymce.init({
				plugins: [
				"eqneditor advlist autolink lists link image charmap print preview anchor",
				"searchreplace visualblocks code fullscreen",
				"insertdatetime media table contextmenu paste" ],
				toolbar: "undo redo | eqneditor link image | styleselect | bold italic | bullist numlist outdent indent ",
				selector : "textarea.richText"
				});
			if(count === 5)
				$('#btn_addAnswer').hide();
		}
	});
	
	// Try to add a question
	$('#btn_addQuestion').click(function(){
		// Make sure valid description
		if($('#questionDescription').val() === '') {
			$('#questionDescription').effect('shake');
			return;
		}
		// Make sure all answers filled
		var hasEmpty = false;
		var answerList = [];
		$('#answerChoiceTable textarea').each(function(){
			if($(this).val() === '')  {
				hasEmpty = true;
				$(this).effect('shake');
			}
			else
				answerList.push($(this).val());
		});
		if(hasEmpty)
			return;
		
		// Get json string for answer list
		var answersString = JSON.stringify(answerList);
		// Get correct answer
		var answerIndex = $("input[type='radio'][name='answers']:checked").val();
		
		// Show spinner
		$('#questionLoading').show();
		// Try to change account info
		$.ajax({
            type: 'POST',
            data: 'description=' + encodeURIComponent($('#questionDescription').val()) + '&answersList=' + encodeURIComponent(answersString) + '&answerIndex=' + answerIndex + '&stid=' + $(this).attr('stid') + '&sid=' + $(this).attr('sid') + '&action=createquestion',
            url: '/addcontent',
            success: function(data) {
            	$('#questionLoading').hide();
            	if(data === 'success') {
            		$('#questionDescription').val("");
            		$('#answerChoiceTable tr:gt(1)').remove();
            		$('#answerChoiceTable textarea').each(function(){$(this).val('')});
            	}
            	else {
                	$('#questionDescription').effect('shake');
            		$('#answerChoiceTable textarea').each(function(){
            			$(this).effect('shake');
            		});
            	}
            },
            error: function(data) {
            	$('#questionLoading').hide();
            	$('#questionDescription').effect('shake');
        		$('#answerChoiceTable textarea').each(function(){
        			$(this).effect('shake');
        		});
            	console.log(data);
            }
        });
	});
	
	// Try to add a video
	$('#btn_addVideo').click(function(){
		// TODO: Add check for url to match valid regex here instead of just empty.
		// Make sure theres a url
		if($('#videoURL').val() === '') {
			$('#videoURL').effect('shake');
			return;
		}
		// Make sure theres a description
		if($('#videoDescription').val() === '') {
			$('#videoDescription').effect('shake');
			return;
		}
		//Replace watch?v= with embed/ so that the video can be embed-ed
		var videoUrl = $('#videoURL').val()
		videoUrl = videoUrl.replace('youtube.com/watch?v=', 'youtube.com/embed/');
		videoUrl = videoUrl.replace('youtu.be/', 'youtube.com/embed/');
		// Show spinner
		$('#videoLoading').show();
		// Try to change account info
		$.ajax({
            type: 'POST',
            data: 'description=' + encodeURIComponent($('#videoDescription').val()) + '&url=' + encodeURIComponent(videoUrl) + '&stid=' + $(this).attr('stid') + '&sid=' + $(this).attr('sid') + '&action=addvideo',
            url: '/addcontent',
            success: function(data) {
            	$('#videoLoading').hide();
            	if(data === 'success') {
            		$('#videoDescription').val('');
            		$('#videoURL').val('');
            	}
            	else {
            		$('#videoDescription').effect('shake');
            		$('#videoURL').effect('shake');
            	}
            },
            error: function(data) {
            	$('#videoLoading').hide();
            	$('#videoDescription').effect('shake');
        		$('#videoURL').effect('shake');
            	console.log(data);
            }
        });
		
	});
	
	// Try to add notes
	$('#btn_addNotes').click(function(){
		// TODO: Add check for url to match valid regex here instead of just empty.
		// Make sure theres a url
		if($('#notesURL').val() === '') {
			$('#notesURL').effect('shake');
			return;
		}
		// Make sure theres a description
		if($('#notesDescription').val() === '') {
			$('#notesDescription').effect('shake');
			return;
		}
		
		// Show spinner
		$('#notesLoading').show();
		// Try to change account info
		$.ajax({
            type: 'POST',
            data: 'description=' + encodeURIComponent($('#notesDescription').val()) + '&url=' + encodeURIComponent($('#notesURL').val()) + '&stid=' + $(this).attr('stid') + '&sid=' + $(this).attr('sid') + '&action=addnotes',
            url: '/addcontent',
            success: function(data) {
            	$('#notesLoading').hide();
            	if(data === 'success') {
            		$('#notesDescription').val('');
            		$('#notesURL').val('');
            	}
            	else {
            		$('#notesDescription').effect('shake');
            		$('#notesURL').effect('shake');
            	}
            },
            error: function(data) {
            	$('#notesLoading').hide();
            	$('#notesDescription').effect('shake');
        		$('#notesURL').effect('shake');
            	console.log(data);
            }
        });
		
	});
	
});