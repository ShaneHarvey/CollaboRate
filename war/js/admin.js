/**
 *  admin.js contains all javascript related to admin functionality
 *  requires jQuery and jQuery-ui
 */

$(function(){
	
	// Add another row to sub topic table
	$('#btn_AddSubtopic').click(function(){
		$('#subTopicTable').append('<tr><td><input type="text"/></td></tr>');
	});
	
	// Try to add this subject and it's subtopics
	$('#btn_addSubject').click(function(){
		// Make sure subject name is populated 
		if($('#subjectName').val() === '') {
			$('#subjectName').effect('shake');
			return;
		}
		
		// Make sure all inputs have a value in subtopic table and add them to list
		var allValid = true;
		var subTopics = [];
		$('#subTopicTable input').each(function(){
			if($(this).val() === '') {
				allValid = false;
				$(this).effect('shake');
			}
			else
				subTopics.push($(this).val());
		});
		if(!allValid)
			return;
		
		// Create json string for subtopics list
		var subTopicsString = JSON.stringify(subTopics);
		
		// Show spinner
		$('#subjectLoading').show();
		// Try to change account info
		$.ajax({
            type: 'POST',
            data: 'subjectName=' + encodeURIComponent($('#subjectName').val()) + '&subTopicList=' + encodeURIComponent(subTopicsString) + '&action=createsubject',
            url: '/admin',
            success: function(data) {
            	$('#subjectLoading').hide();
            	if(data === 'success') {
            		$('#subjectName').val("");
            		$('#subTopicTable tr:gt(0)').remove();
            		$('#subTopicTable input').each(function(){$(this).val('')});
            	}
            	else {
            		$('#subjectName').effect('shake');
            		$('#subTopicTable input').each(function(){
            			$(this).effect('shake');
            		});
            	}
            },
            error: function(data) {
            	$('#subjectLoading').hide();
            	$('#subjectName').effect('shake');
        		$('#subTopicTable input').each(function(){
        			$(this).effect('shake');
        		});
            	console.log(data);
            }
        });
	});
	
	$('.remove').click(function(){
		var btn = $(this);
		$.ajax({
            type: 'POST',
            data: 'cid=' + $(this).attr('cid') + '&ctype=' + $(this).attr('ctype') + '&action=removecontent',
            url: '/admin',
            success: function(data){
            	btn.closest('.content-holder').remove();
            }
        });
	});
	
	$('.unflag').click(function(){
		var btn = $(this);
		$.ajax({
            type: 'POST',
            data: 'cid=' + $(this).attr('cid') + '&ctype=' + $(this).attr('ctype') + '&action=unflagcontent',
            url: '/admin',
            success: function(data){
            	btn.closest('.content-holder').remove();
            }
        });
	});
	
});