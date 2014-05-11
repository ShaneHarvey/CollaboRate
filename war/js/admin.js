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
		if($('#categoryKey').val() === ''){
			$('#categoryKey').effect('shake');
			return;
		}
		
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
            data: 'categoryKey='+ encodeURIComponent($('#categoryKey').val())+'&subjectName=' + encodeURIComponent($('#subjectName').val()) + '&subTopicList=' + encodeURIComponent(subTopicsString) + '&action=createsubject',
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
	
	$('select[name="categorySelector"]').change(function(){
		var cid = $(this).val();
		$('#subjectSelectDiv').empty();
		$('#requestedSubtopics').empty();
		$('#subtopicList').empty();
		if(cid === '')
			return;
		$.ajax({
            type: 'POST',
            data: 'catid=' + cid + '&action=getsubjects',
            url: '/admin',
            success: function(data){
            	$('#subjectSelectDiv').empty();
            	$('#requestedSubtopics').empty();
            	$('#subtopicList').empty();
            	var subList = JSON.parse(data);
            	var subSelect = $('<select id="' + cid + '" class="subjectSelector"></select>');
            	subSelect.append('<option></option');
            	for(var i = 0; i < subList.length; i++) {
            		subSelect.append('<option value="' + subList[i].second + '">' + subList[i].first + '</option>');
            	}
            	$('#subjectSelectDiv').append('<h3>Current Subjects</h3>');
            	$('#subjectSelectDiv').append(subSelect);
            	
            	$(subSelect).change(function(){
            		var sid = $(this).val();
            		$('#requestedSubtopics').empty();
            		$('#subtopicList').empty();
            		if(sid === '')
            			return;
            		$.ajax({
                        type: 'POST',
                        data: 'sid=' + sid + '&catid=' + $(this).attr('id') + '&action=getsubtopics',
                        url: '/admin',
                        success: function(data){ 
                        	var obj = JSON.parse(data);
                        	var stList = obj.sts;
                        	var strList = obj.strs;
                        	displaySubTopicList(stList);
                        	
                        	$('#requestedSubtopics').empty();
                        	var strTable = $('<table></table>');
                        	for(var i = 0; i < strList.length; i++) {
                        		var row = $('<tr></tr>');
                        		var col = $('<td></td>');
                        		row.append(col);
                        		var span = $('<span id="' + strList[i].second + '" class="glyphicon glyphicon-plus hoverHand">' + strList[i].first + '</span>');
                        		col.append(span);
                        		span.click(insertInOrder);
                        		strTable.append(row);
                        	}
                        	$('#requestedSubtopics').append('<h3>Requested Subtopics</h3>');
                        	$('#requestedSubtopics').append(strTable);
                        }//success
                    });   //ajax
            	});//subjectSeletor
            }//success function
        });   //ajax
	});//category selector
});//document ready

/*function reOrder(){
	if($(this).val() === '')
		return;
	$.ajax({
        type: 'POST',
        data: 'order=' + $(this).val() + '&stid=' + $(this).attr('id') + '&sid=' + $('.subjectSelector').val() + '&action=changeOrder',
        url: '/admin',
        success: function(data){    	
        	var stList = JSON.parse(data);
        	displaySubTopicList(stList);
        }
    });
}*/

function insertInOrder(){
	var div = $(this);
	div.remove();
	$.ajax({
        type: 'POST',
        data: 'rstid=' + $(this).attr('id') + '&sid=' + $('.subjectSelector').val() + '&action=insertOrder',
        url: '/admin',
        success: function(data){
        	var stList = JSON.parse(data);
        	displaySubTopicList(stList);
        }
    });
}

function displaySubTopicList(stList){
	$('#subtopicList').empty();
	$('#subtopicList').append('<h3>Current Subtopics</h3>');
	var optionList = $('<ol class="big-list"></ol>');
	for(var i = 0; i < stList.length; i++) {
		var li = $('<li class="tl" stid="' + stList[i].second + '">' + stList[i].first + '&nbsp&nbsp&nbsp&nbsp</li>');
		var btn_del = $('<span class="glyphicon glyphicon-remove red hoverHand"></span>');
		li.append(btn_del);
		btn_del.click(function(){
			var div = $(this).parent().remove();
			$.ajax({
		        type: 'POST',
		        data: 'stid=' + div.attr('stid') + '&action=deletesubtopic',
		        url: '/admin'
		    });
		});
		optionList.append(li);
	}
	$('#subtopicList').append(optionList);
}
