/**
 *   accountSettings.js contains all javascript required for accountSettings page.
 *   Requires jquery and jquery-ui
 */

var editing = false;

$(function(){
	
	$('#allowEditButton').click(function(){
		if(!editing) {
			editing = true;
			// Show all hidden stuff
			$('.hideGroup').show();
			$('input').removeAttr('disabled');
		}
		else {
			editing = false;
			$('.hideGroup').hide();
			$('input').attr('disabled', 'disabled');
		}
	});
	
	$('#editInfoButton').click(function(){
		// Make sure display name not null
		// Make sure email not null
		// Make sure password is valid and they match
		// Try to change password
		// Inform success / failure
	});
	
});