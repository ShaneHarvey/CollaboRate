/**
 *  admin.js contains all javascript related to admin functionality
 *  requires jQuery and jQuery-ui
 */

$(function(){
	
	// Try to add this subject and it's subtopics
	$('#btn_addCategory').click(function(){
		// Make sure subject name is populated 
		if($('#categoryName').val() === '') {
			$('#categoryName').effect('shake');
			return;
		}
		$('#subjectLoading').show();
		// Try to change account infoCopyOfRequestSubtopicServlet
		$.ajax({
            type: 'POST',
            data: 'categoryName=' + encodeURIComponent($('#categoryName').val()) +'&action=addcategory',
            url: '/AddCategory',
            success: function(data) {
            	$('#subjectLoading').hide();
            	if(data === 'success') {
            		$('#categoryName').val("");

            	}
            	else {
            		$('#categoryName').effect('shake');
            	}
            },
            error: function(data) {
            	$('#subjectLoading').hide();
            	$('#categoryName').effect('shake');
            	console.log(data);
            }
        });
	});
});