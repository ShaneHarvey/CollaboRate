/**
 *  admin.js contains all javascript related to admin functionality
 *  requires jQuery and jQuery-ui
 */

$(function(){
	// Set enter key to click the #btn_addSubject button
	$("#subjectName, #subTopicTable").keyup(function(event){
	    if(event.keyCode == 13){
	        $("#btn_addSubject").click();
	    }
	});
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
            success: function(data, textStatus, jqXHR) {
            	$('#subjectLoading').hide();
            	if(data === 'success')
            		window.location.href = '/admin';
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
	
	$('select[name="subjectSelector"]').change(function(){
		$.ajax({
            type: 'POST',
            data: 'sid=' + $(this).val(),
            url: '/admin',
            success: function(data){
            	var subList = data.split('<html>')[0];
            	subList = subList.split('<!')[0];
            	$('#subtopicList').html(subList);
            	
            	/*$('.subtopicInput').blur(function(){
            		$.ajax({
                        type: 'POST',
                        data: 'order=' + $(this).val() + '&stid=' + $(this).attr('id') + '&action=changeOrder&sid=' + $('.subtopicList').filter(":first").attr('id') ,
                        url: '/admin',
                        success: function(data2){
                        	
                        	var subList2 = data2.split('<html>')[0];
                        	$('#subtopicList').html(subList2);
                        }
                    });   
            	});*/
            }
        });   
	});
	
	
	
	
	/* change the order of subtopics
	$('#btn_AddSubtopic').click(function(){
		var subtopi
		
		$.ajax({
            type: 'POST',
            data: 'cid=' + $(this).attr('cid') + '&ctype=' + $(this).attr('ctype') + '&action=unflagcontent',
            url: '/admin',
            success: function(data){
            	btn.closest('.content-holder').remove();
            }
        });
	});
	*/
	
	
});

function reOrder(id){
	$.ajax({
        type: 'POST',
        data: 'order=' + $('#'+id).val() + '&stid=' + id + '&action=changeOrder&sid=' + $('.subtopicList').filter(":first").attr('id') ,
        url: '/admin',
        success: function(data2){
        	
        	var subList2 = data2.split('<html>')[0];
        	$('#subtopicList').html(subList2);
        }
    });
}

function insertInOrder(id){
	$('#' + id).remove();
	$.ajax({
        type: 'POST',
        data: 'rstid=' + id + '&action=insertOrder&sid=' + $('.subtopicList').filter(":first").attr('id') ,
        url: '/admin',
        success: function(data2){
        	
        	var subList2 = data2.split('<html>')[0];
        	$('#subtopicList').html(subList2);
        }
    });
}
